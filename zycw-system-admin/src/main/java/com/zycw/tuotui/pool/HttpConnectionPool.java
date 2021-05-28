package com.zycw.tuotui.pool;


import org.apache.commons.io.IOUtils;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.*;

/**
 * httpclient线程池
 * @author junxu.yang
 * @Date: 2019/7/5 15:08
 */
public class HttpConnectionPool {
    private static Logger logger = LoggerFactory.getLogger(HttpConnectionPool.class);

    /**
     * 获取连接池等待世家
     */
    private static final int CONNECT_REQUEST_TIMEOUT=500;
    /**
     * 设置连接建立的超时时间为30s
     */
    private static final int CONNECT_TIMEOUT =500;

    /**
     * 设置连接建立的超时时间为30s
     */
    private static final int SOCKET_TIMEOUT = 3000;

    /**
     * 设置连接池最大连接数
     */
    private static final int MAX_CONN = 2000; // 最大连接数

    /**
     * 设置route最大连接数
     */
    private static final int MAX_ROUTE =1000;
    /**
     * 发送请求的客户端单例模式
     */
    private static CloseableHttpClient httpClient;

    /**
     * 连接池管理类
     */
    private static PoolingHttpClientConnectionManager manager;

    /**
     * 监控线程池
     */
    private static ScheduledExecutorService monitorExecutor;


    private final static Object syncLock = new Object(); // 相当于线程锁,用于线程安全

    /**
     * 配置
     * @param httpRequestBase http请求
	 * @author junxu.yang
	 * @Date: 2019/7/5 15:08
	 * @Description: 配置
     */
    private static void setRequestConfig(HttpRequestBase httpRequestBase){
    }

    /**
     * 获取HttpClient
     * @param url http请求
	 * @author junxu.yang
	 * @Date: 2019/7/5 15:08
	 * @Description: 获取HttpClient
     */
    public static CloseableHttpClient getHttpClient(String url) throws Exception {
        String hostName = url.split("/")[2];
        String port;
        int portnum=80;
        if (hostName.contains(":")){
            String[] args = hostName.split(":");
            hostName = args[0];
            port = args[1];
            if (port.contains("?")) {
                port = port.split("\\?")[0];

            }
            portnum=Integer.parseInt(port);
        }

        if (httpClient == null){
            //多线程下多个线程同时调用getHttpClient容易导致重复创建httpClient对象的问题,所以加上了同步锁
            synchronized (syncLock){
                if (httpClient == null){
                    System.out.println("httpClient==null");
                    httpClient = createHttpClient(hostName, portnum);
                }else{
                    System.out.println("httpClient.toString()>>>>>"+httpClient.toString());
                }
            }
        }
        return httpClient;
    }

    /**
     * 根据host和port构建httpclient实例
	 * @author junxu.yang
	 * @Date: 2019/7/5 15:08
     * @param host 要访问的域名
     * @param port 要访问的端口
     * @return
     */
    public static CloseableHttpClient createHttpClient(String host, int port) throws Exception{
        ConnectionSocketFactory plainSocketFactory = PlainConnectionSocketFactory.getSocketFactory();
        LayeredConnectionSocketFactory sslSocketFactory = SSLConnectionSocketFactory.getSocketFactory();
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory> create()
                .register("http", plainSocketFactory)
                .register("https", sslSocketFactory).build();

        manager = new PoolingHttpClientConnectionManager(registry);
        //设置连接参数

        manager.setMaxTotal(MAX_CONN); // 最大连接数
        manager.setDefaultMaxPerRoute(MAX_ROUTE); // 路由最大连接数
        HttpHost httpHost = new HttpHost(host, port);
        manager.setMaxPerRoute(new HttpRoute(httpHost), MAX_ROUTE);

        //请求失败时,进行请求重试
        HttpRequestRetryHandler handler = new HttpRequestRetryHandler() {
            @Override
            public boolean retryRequest(IOException e, int i, HttpContext httpContext) {
                if (i > 10){
                    //重试超过3次,放弃请求
                    logger.error("retry has more than 3 time, give up request");
                    return false;
                }
                if (e instanceof NoHttpResponseException){
                    //服务器没有响应,可能是服务器断开了连接,应该重试
                    logger.error("receive no response from server, retry");
                    return true;
                }
                if (e instanceof SSLHandshakeException){
                    // SSL握手异常
                    logger.error("SSL hand shake exception");
                    return false;
                }
                if (e instanceof InterruptedIOException){
                    //超时
                    logger.error("InterruptedIOException");
                    return true;
                }
                if (e instanceof UnknownHostException){
                    // 服务器不可达
                    logger.error("server host unknown");
                    return false;
                }
                if (e instanceof ConnectTimeoutException){
                    // 连接超时
                    logger.error("Connection Time out");
                    return true;
                }
                if (e instanceof SocketTimeoutException){
                    // 连接超时
                    logger.error("Socket Connection Time out");
                    return true;
                }
                if (e instanceof SSLException){
                    logger.error("SSLException");
                    return false;
                }

                HttpClientContext context = HttpClientContext.adapt(httpContext);
                HttpRequest request = context.getRequest();
                if (!(request instanceof HttpEntityEnclosingRequest)){
                    //如果请求不是关闭连接的请求
                    return true;
                }
                return false;
            }
        };

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(CONNECT_REQUEST_TIMEOUT)
                .setConnectTimeout(CONNECT_TIMEOUT)
                .setSocketTimeout(SOCKET_TIMEOUT).build();

        CloseableHttpClient client = HttpClients.custom()
                .setConnectionManager(manager)
                .setDefaultRequestConfig(requestConfig)
                .setRetryHandler(handler).build();
        return client;
    }

    /**
     * 设置post请求的参数
     * @param httpPost
     * @param params
     */
    private static void setPostParams(HttpPost httpPost, Map<String, String[]> params) throws UnsupportedEncodingException {
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        Set<String> keys = params.keySet();
        for (String key: keys){
            String param=params.get(key)[0];

            //TODO 该处需再做调整
//            boolean isSOAP=param.toLowerCase().indexOf("SOAP")!=-1;
//            if(isSOAP){
//                //param= SoapToXml.xml2json(param);
//            }else{
//               // param = SoapToXml.json2xml(param);
//            }
            nvps.add(new BasicNameValuePair(key, param));
        }
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
    }

    /**
     * post方法
     * @param url 请求地址
     * @param headers 请求header
     * @param params 请求参数
     * @return String 请求后的结果
     */
    public static String post(String url,HashMap<String,String> headers, Map<String, String[]> params) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        setRequestConfig(httpPost);
        //设置header信息
        if ( headers != null && headers.size() > 0 ) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpPost.addHeader(entry.getKey(), entry.getValue());
            }
        }
        setPostParams(httpPost, params);
        CloseableHttpResponse response = null;
        InputStream in = null;
        String result =null;
        try {
            response = getHttpClient(url).execute(httpPost, HttpClientContext.create());
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                in = entity.getContent();
                result = IOUtils.toString(in, "utf-8");
            }
            EntityUtils.consumeQuietly(response.getEntity());
        } catch (ClientProtocolException e) {
            throw new ClientProtocolException(e);
        } catch (IOException e) {
        } finally {
            httpPost.releaseConnection();
            if (in != null){
                in.close();
            }
            if (response != null){
                response.getEntity().getContent().close();
                response.close();
            }
        }
        return result;
    }



    /**
     * GET请求URL获取内容
     *
     * @param url
     * @author junxu.yang
     * @create 2018年07月09日
     * @return String
     */
    public static String get(String url, HashMap<String,String> headers,Map<String, String[]> params) throws Exception {
        String apiUrl = getUrlWithParams(url, params);
        HttpGet httpget = new HttpGet(url);
        //设置header信息
        if ( headers != null && headers.size() > 0 ) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpget.addHeader(entry.getKey(), entry.getValue());
            }
        }
        setRequestConfig(httpget);
        CloseableHttpResponse response = null;
        try {
            response = getHttpClient(url).execute(httpget,
                    HttpClientContext.create());
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity, "utf-8");
            EntityUtils.consume(entity);
            return result;
        } catch (IOException e) {
            throw new IOException(e);
        } finally {
            httpget.releaseConnection();
            if (response != null) {
                response.getEntity().getContent().close();
                response.close();
            }
        }
    }

    /**
     * GET请求拼接参数
     *
     * @param url
     * @author junxu.yang
     * @create 2018年07月09日
     * @return String 
     */
    private static String getUrlWithParams(String url, Map<String,String[]> params) throws UnsupportedEncodingException {
        boolean first = true;
        StringBuilder sb = new StringBuilder(url);
        for (String key : params.keySet()) {
            char ch = '&';
            if (first == true) {
                ch = '?';
                first = false;
            }
            String value = params.get(key).toString();
            String sval = URLEncoder.encode(value, "UTF-8");
            sb.append(ch).append(key).append("=").append(sval);
        }
        return sb.toString();
    }


    /**
     * 监控线程，启动服务时启动，定时清除过时连接和错误连接
     * @author junxu.yang
     * @create 2018年07月09日
     * @return String
     */
    public static void IdleConnectionMonitor() throws Exception{
        //开启监控线程,对异常和空闲线程进行关闭
        logger.info("httpClient Monitor Thread start!!");
        monitorExecutor = Executors.newScheduledThreadPool(1);
        monitorExecutor.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(manager.getRoutes().size()>0) {
                    //关闭异常连接
                     manager.closeExpiredConnections();
                    //关闭300s空闲的连接
                     manager.closeIdleConnections(300000, TimeUnit.MILLISECONDS);
                    //logger.info("close expired and idle for over 30s connection");
                }
            }
        }, 100, 5000, TimeUnit.MILLISECONDS);
    }



}

