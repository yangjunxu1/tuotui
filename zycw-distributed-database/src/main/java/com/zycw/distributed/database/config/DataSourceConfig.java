package com.zycw.distributed.database.config;

import java.io.IOException;
import java.util.Properties;

import javax.transaction.UserTransaction;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.jta.JtaTransactionManager;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;


@Configuration
public class DataSourceConfig {

	@Value("${read.jdbc.url}")
	public String read_jdbc_url;
	@Value("${read.jdbc.username}")
	public String read_jdbc_username;
	@Value("${read.jdbc.password}")
	public String read_jdbc_password;
	
	//连接池初始化大小
	@Value("${read.initialSize:100}")
	public String read_initialSize;
	//连接池初始化最小
	@Value("${read.minIdle:10}")
	public String read_minIdle;
	//连接池初始化最大
	@Value("${read.maxActive:100}")
	public String read_maxActive;
	//配置获取连接等待超时的时间
	@Value("${read.maxWait:1800000}")
	public String read_maxWait;
	//配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
	@Value("${read.timeBetweenEvictionRunsMillis:1800000}")
	public String read_timeBetweenEvictionRunsMillis;
	//配置一个连接在池中最小生存的时间，单位是毫秒
	@Value("${read.minEvictableIdleTimeMillis:1200000}")
	public String read_minEvictableIdleTimeMillis;
	//配置一个连接在池中最小生存的时间，单位是毫秒
	@Value("${write.maxEvictableIdleTimeMillis:1800000}")
	public String read_maxEvictableIdleTimeMillis;
	//验证从连接池取出的连接
	@Value("${read.validationQuery:SELECT 1 FROM DUAL}")
	public String read_validationQuery;
	//指明连接是否被空闲连接回收器(如果有)进行检验.如果检测失败,则连接将被从池中去除.
	@Value("${read.testWhileIdle:true}")
	public String read_testWhileIdle;
	//指明是否在从池中取出连接前进行检验,如果检验失败,则从池中去除连接并尝试取出另一个
	@Value("${read.testOnBorrow:true}")
	public String read_testOnBorrow;
	//指明是否在归还到池中前进行检验
	@Value("${read.testOnReturn:true}")
	public String read_testOnReturn;

	//泄露的连接可以被删除的超时值, 单位秒
	@Value("${read.removeAbandonedTimeout:600}")
	public String read_removeAbandonedTimeout;
	/**
	 * 标记是否删除泄露的连接,如果他们超过了removeAbandonedTimout的限制.
	 * 如果设置为true, 连接被认为是被泄露并且可以被删除,如果空闲时间超过removeAbandonedTimeout. 
	 * 设置为true可以为写法糟糕的没有关闭连接的程序修复数据库连接.
	 */
	@Value("${read.removeAbandoned:false}")
	public String read_removeAbandoned;
	//标记当Statement或连接被泄露时是否打印程序的stack traces日志
	@Value("${read.logAbandoned:false}")
	public String read_logAbandoned;
	
	
	/*

	@Value("${write.jdbc.url:}")
	public String write_jdbc_url;
	@Value("${write.jdbc.username:}")
	public String write_jdbc_username;
	@Value("${write.jdbc.password:}")
	public String write_jdbc_password;
	
	//连接池初始化大小
	@Value("${write.initialSize:}")
	public String write_initialSize;
	//连接池初始化最小
	@Value("${write.minIdle:}")
	public String write_minIdle;
	//连接池初始化最大
	@Value("${write.maxActive:}")
	public String write_maxActive;
	//配置获取连接等待超时的时间
	@Value("${write.maxWait:600000}")
	public String write_maxWait;
	//配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
	@Value("${write.timeBetweenEvictionRunsMillis:600000}")
	public String write_timeBetweenEvictionRunsMillis;
	//配置一个连接在池中最小生存的时间，单位是毫秒
	@Value("${write.minEvictableIdleTimeMillis:2000000}")
	public String write_minEvictableIdleTimeMillis;
	
	//配置一个连接在池中最小生存的时间，单位是毫秒
	@Value("${write.maxEvictableIdleTimeMillis:2800000}")
	public String write_maxEvictableIdleTimeMillis;
	
	//验证从连接池取出的连接
	@Value("${write.validationQuery:SELECT 1 FROM DUAL}")
	public String write_validationQuery;
	//指明连接是否被空闲连接回收器(如果有)进行检验.如果检测失败,则连接将被从池中去除.
	@Value("${write.testWhileIdle:true}")
	public String write_testWhileIdle;
	//指明是否在从池中取出连接前进行检验,如果检验失败,则从池中去除连接并尝试取出另一个
	@Value("${write.testOnBorrow:true}")
	public String write_testOnBorrow;
	//指明是否在归还到池中前进行检验
	@Value("${write.testOnReturn:true}")
	public String write_testOnReturn;

	//泄露的连接可以被删除的超时值, 单位秒
	@Value("${write.removeAbandonedTimeout:3600}")
	public String write_removeAbandonedTimeout;
	*//**
	 * 标记是否删除泄露的连接,如果他们超过了removeAbandonedTimout的限制.
	 * 如果设置为true, 连接被认为是被泄露并且可以被删除,如果空闲时间超过removeAbandonedTimeout. 
	 * 设置为true可以为写法糟糕的没有关闭连接的程序修复数据库连接.
	 *//*
	@Value("${write.removeAbandoned:false}")
	public String write_removeAbandoned;
	//标记当Statement或连接被泄露时是否打印程序的stack traces日志
	@Value("${write.logAbandoned:false}")
	public String write_logAbandoned;
	
	*/
	
	
    private String PRIMARY_CLASSPATH_MAPPER_XML = "classpath:mapper/read/*.xml";
    private String BUSINESS_CLASSPATH_MAPPER_XML = "classpath:mapper/write/*.xml";



    @Bean(name="primaryDataSource")
    public AtomikosDataSourceBean primaryDataSource() {
        AtomikosDataSourceBean ds = new AtomikosDataSourceBean();
        Properties prop = new Properties();
        prop.put("url", read_jdbc_url);
        prop.put("user", read_jdbc_username);
        prop.put("password", read_jdbc_password);
        //prop.put("removeAbandonedTimeout", 600);
        ds.setXaDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlXADataSource");
        ds.setUniqueResourceName("primaryDataSource");
        ds.setBorrowConnectionTimeout(30000);
        ds.setMinPoolSize(10);
        ds.setPoolSize(20);
        ds.setMaxPoolSize(50);
        ds.setConcurrentConnectionValidation(true);
        ds.setMaxIdleTime(360000);
        ds.setMaxLifetime(360000);
        ds.setTestQuery("select 1");
        ds.setXaProperties(prop);
        return ds;

    }
/*
    @Bean(name ="businessDataSource")
    public AtomikosDataSourceBean businessDataSource() {
        AtomikosDataSourceBean ds = new AtomikosDataSourceBean();
        Properties prop = new Properties();
        if("".equals(write_jdbc_url))write_jdbc_url=read_jdbc_url;
        prop.put("url", write_jdbc_url);
        if("".equals(write_jdbc_username))write_jdbc_username=read_jdbc_username;
        prop.put("username", write_jdbc_username);
        if("".equals(write_jdbc_password))write_jdbc_password=read_jdbc_password;
        prop.put("password", write_jdbc_password);
        if("".equals(write_initialSize))write_initialSize=read_initialSize;
        prop.put("initialSize", write_initialSize);
        if("".equals(write_minIdle))write_minIdle=read_minIdle;
        prop.put("minIdle", write_minIdle);
        if("".equals(write_maxActive))write_maxActive=read_maxActive;
        prop.put("maxActive", write_maxActive);
        prop.put("maxWait", write_maxWait);
        prop.put("timeBetweenEvictionRunsMillis", write_timeBetweenEvictionRunsMillis);
        prop.put("minEvictableIdleTimeMillis", write_minEvictableIdleTimeMillis);
        prop.put("maxEvictableIdleTimeMillis", write_maxEvictableIdleTimeMillis);
        prop.put("testWhileIdle", write_testWhileIdle);
        prop.put("testOnBorrow", write_testOnBorrow);
        prop.put("testOnReturn", write_testOnReturn);
        prop.put("removeAbandonedTimeout", write_removeAbandonedTimeout);
        prop.put("removeAbandoned", write_removeAbandoned);
        prop.put("logAbandoned", write_logAbandoned);
        ds.setXaDataSourceClassName("com.alibaba.druid.pool.xa.DruidXADataSource");
        ds.setUniqueResourceName("businessDataSource");
        ds.setPoolSize(20);
        ds.setXaProperties(prop);

        return ds;
    }
*/
    @Bean(name = "primarySqlSessionFactoryBean")
    public SqlSessionFactoryBean primarySqlSessionFactoryBean(@Qualifier("primaryDataSource") AtomikosDataSourceBean primaryDataSource) {
    	//DataSource primaryDataSource=primaryDataSource();
    	SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(primaryDataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            sqlSessionFactoryBean.setMapperLocations(resolver.getResources(PRIMARY_CLASSPATH_MAPPER_XML));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sqlSessionFactoryBean;
    }
/*
    @Bean(name = "businessSqlSessionFactoryBean")
    public SqlSessionFactoryBean businessSqlSessionFactoryBean(@Qualifier("businessDataSource")  AtomikosDataSourceBean businessDataSource) {

      	SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(businessDataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            sqlSessionFactoryBean.setMapperLocations(resolver.getResources(BUSINESS_CLASSPATH_MAPPER_XML));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sqlSessionFactoryBean;
    }

    
    */
    
    /**
     * 注入事物管理器
     * @return
     */
    @Bean(name = "xatx")
    public JtaTransactionManager regTransactionManager () {
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        UserTransaction userTransaction = new UserTransactionImp();
        return new JtaTransactionManager(userTransaction, userTransactionManager);
    }


   

    
}
