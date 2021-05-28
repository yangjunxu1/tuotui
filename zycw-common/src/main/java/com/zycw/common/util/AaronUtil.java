package com.zycw.common.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.charset.Charset;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 自用工具
 * pitianyi
 */
@Component
public class AaronUtil<T,E> {
    /**
     *@Description 日志
     *@Param
     *@Return
     *@Author pitianyi
     *@Date 2019/8/10
     */
    private static Logger logger = LoggerFactory.getLogger(AaronUtil.class);

    /**
     *@Description 给sql操作结果使用,把0和1转成操作成功和失败
     *@Param [num]
     *@Return java.lang.String
     *@Author pitianyi
     *@Date 2019/8/8
     */
    public static String result(Integer num){
        return (num==0)?"操作失败":"操作成功";
    }

    /**
     * 常量单引号
     */
    private final static String DANYINHAO = "'";
    /**
     * 常量双引号
     */
    private final static String SHUANGYINHAO = "\"";
    /**
     * rn
     */
    private final static String RN = "\r\n";
    /**
     * n
     */
    private final static String N = "\n";
    /**
     *@Description 字符串是否为空
     *@Param [str]
     *@Return boolean
     *@Author pitianyi
     *@Date 2019/8/18
     */
    public static boolean stringIsNull(String str){
        if(null == str){
            return false;
        }
        if(str.trim().length()==0){
            return false;
        }
        return true;
    }

    /**
     * 把map转为java bean
     *
     * @param map
     * @param clazz
     * @return 实体
     */
    public static <T> T fromMapToBean(Map<String, Object> map, Class<T> clazz) {
        T t = null;
        try {
            t = clazz.newInstance();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (Modifier.isFinal(field.getModifiers()) || Modifier.isStatic(field.getModifiers())
                        || field.isSynthetic()) {
                    // 静态变量、synthetic和父类对象变量性忽略
                    continue;
                }
                // boolean类型
                if ("boolean".equals(field.getType().getName())) {
                    field.setBoolean(t, Boolean.valueOf((String) map.get(field.getName())));
                } else if ("java.lang.Integer".equals(field.getType().getName())) {
                    if (null != map.get((field.getName()))){
                        field.setInt(t, Integer.parseInt(String.valueOf(map.get(field.getName()))));
                    }
                } else if ("java.lang.Long".equals(field.getType().getName())) {
                    if (null != map.get((field.getName()))){
                        field.setLong(t, Long.parseLong(String.valueOf(map.get(field.getName()))));
                    }
                } else { // 其他类型
                    if (null != map.get((field.getName()))) {
                        field.set(t, map.get(field.getName()));
                    }
                }
            }
        } catch (Exception ex) {
            logger.error("", ex);
            throw new RuntimeException("", ex);
        }
        return t;
    }

    /**
     *@Description 把T里的值根据你给的字段匹配赋值到E里面去.
     *@Param [t 从t中取值, map 字段对应,key是t的,value是e的, e 把值赋值给e中]
     *@Return E 被赋值的bean
     *@Author pitianyi
     *@Date 2019/7/30
     */
    public E beanValueConvertByMap(T t, Map<String, String> map, E e){
        //将map遍历
        map.forEach((tColumn,eColumn)->{
            try {
                //获得t的该字段属性
                PropertyDescriptor tpd = new PropertyDescriptor(tColumn, t.getClass());
                Method readMethod = tpd.getReadMethod();
                Object tColumnValue = readMethod.invoke(t, null);
                //设置e的该字段属性
                PropertyDescriptor epd = new PropertyDescriptor(eColumn, e.getClass());
                Method writeMethod = epd.getWriteMethod();
                writeMethod.invoke(e,tColumnValue);//貌似差一个参数强转,test测试了没问题.
                logger.info("把"+tColumn+"的"+tColumnValue+"赋值给"+eColumn);
            } catch (Exception e1) {
                logger.error("AaronUtil的beanValueConvertByMap方法报错");
                e1.printStackTrace();
            }
        });
        logger.info("beanValueConvertByMap返回了 :" + e);
        return e;
    }

    /**
     *@Description 把两个bean里相同名字的字段赋值
     *@Param [t, e]
     *@Return E
     *@Author pitianyi
     *@Date 2019/7/30
     */
    public E beanValueConvertBySameColumn(T t,E e){
        logger.info("双bean同字段赋值");
        //t class字段转为list
        Class<?> tClass = t.getClass();
        Field[] tdeclaredFields = tClass.getDeclaredFields();
        List<String> tColumnNameList = new ArrayList<>();
        for (int i=0;i<tdeclaredFields.length;i++){
            String tColumnName = tdeclaredFields[i].getName();
            tColumnNameList.add(tColumnName);
        }
        //e class 字段转为 list
        Class<?> eClass = e.getClass();
        Field[] edeclaredFields = eClass.getDeclaredFields();
        List<String> eColumnNameList = new ArrayList<>();
        for(int j=0;j<edeclaredFields.length;j++){
            String eColumnName = edeclaredFields[j].getName();
            eColumnNameList.add(eColumnName);
        }
        //创建map,查看相同字段
        Map<String,String> colmap = new HashMap<>();
        for(int k=0;k<tColumnNameList.size();k++){
            String tcolumnName = tColumnNameList.get(k);
            if(eColumnNameList.contains(tcolumnName)){
                colmap.put(tcolumnName,tcolumnName);
            }
        }
        E e1 = beanValueConvertByMap(t, colmap, e);
        logger.info("双bean同字段赋值返回 :" + e1);
        return e1;
    }

    /**
     *@Description 把list元素中间插一个分隔符转为string
     *@Param [list 元素列表, separator 元素中间使用的分隔符]
     *@Return java.lang.String
     *@Author pitianyi
     *@Date 2019/7/30
     */
    public static String listToString(List list, String separator) {
        return StringUtils.join(list.toArray(),separator);
    }

    /**
     *@Description 实体类名转换成下划线表名
     *@Param [str 表名]
     *@Return java.lang.String
     *@Author pitianyi
     *@Date 2019/7/30
     */
    public static String camelToUnderline(String str) {
        if(str == null || str.trim().isEmpty()){
            return "";
        }
        int len = str.length();
        StringBuilder sb = new StringBuilder(len);
        sb.append(str.substring(0,1).toLowerCase());
        for (int i = 1; i < len ; i++) {
            char c = str.charAt(i);
            if (Character.isUpperCase(c)){
                sb.append("_");
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 生成.json格式文件
     */
    public static boolean createJsonFile(String jsonString, String filePath, String fileName) {
        // 标记文件生成是否成功
        boolean flag = true;

        // 拼接文件完整路径
        String fullPath = filePath + File.separator + fileName + ".js";

        Writer write = null;
        // 生成json格式文件
        try {
            // 保证创建一个新文件
            File file = new File(fullPath);
            // 如果父目录不存在，创建父目录
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            // 如果已存在,删除旧文件
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            if(jsonString.indexOf(DANYINHAO)!=-1){
                //将单引号转义一下，因为JSON串中的字符串类型可以单引号引起来的
                jsonString = jsonString.replaceAll("'", "\\'");
            }
            if(jsonString.indexOf(SHUANGYINHAO)!=-1){
                //将双引号转义一下，因为JSON串中的字符串类型可以单引号引起来的
                jsonString = jsonString.replaceAll("\"", "\\\"");
            }

            if(jsonString.indexOf(RN)!=-1){
                //将回车换行转换一下，因为JSON串中字符串不能出现显式的回车换行
                jsonString = jsonString.replaceAll("\r\n", "\\u000d\\u000a");
            }
            if(jsonString.indexOf(N)!=-1){
                //将换行转换一下，因为JSON串中字符串不能出现显式的换行
                jsonString = jsonString.replaceAll("\n", "\\u000a");
            }

            // 格式化json字符串
            jsonString = AaronUtil.formatJson(jsonString);

            // 将格式化后的字符串写入文件
            write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            write.write(jsonString);
            write.flush();
            write.close();
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }finally{
            if(null != write){
                try {
                    write.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // 返回是否成功的标记
        return flag;
    }

    /**
     * 单位缩进字符串。
     */
    private static String SPACE = "   ";

    /**
     * 返回格式化JSON字符串。
     *
     * @param json 未格式化的JSON字符串。
     * @return 格式化的JSON字符串。
     */
    public static String formatJson(String json) {
        StringBuffer result = new StringBuffer();

        int length = json.length();
        char key = 0;
        for (int i = 0; i < length; i++) {
            key = json.charAt(i);
            result.append(key);
        }
        return result.toString();
    }

    /**
     *@Description 把本地的zip文件解压缩,注意各个层级不要有中文
     *@Param [zipFilePath 带全文件名的文件路径, unzipFilePath 到文件夹层级]
     *@Return void
     *@Author pitianyi
     *@Date 2019/8/20
     */
    public static void unzip(String zipFilePath, String unzipFilePath) throws Exception {
        File zipFile = new File(zipFilePath);
        //创建解压缩文件保存的路径
        File unzipFileDir = new File(unzipFilePath);
        if (!unzipFileDir.exists() || !unzipFileDir.isDirectory()) {
            unzipFileDir.mkdirs();
        }
        //开始解压
        ZipEntry entry = null;
        String entryFilePath = null, entryDirPath = null;
        File entryFile = null, entryDir = null;
        int index = 0, count = 0;
        byte[] buffer = new byte[1024];
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        ZipFile zip = new ZipFile(zipFile, Charset.forName("GBK"));
        Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>)zip.entries();
        //循环对压缩包里的每一个文件进行解压
        while(entries.hasMoreElements()) {
            entry = entries.nextElement();
            //构建压缩包中一个文件解压后保存的文件全路径
            entryFilePath = unzipFilePath + File.separator + entry.getName();
            //构建解压后保存的文件夹路径
            index = entryFilePath.lastIndexOf(File.separator);
            if (index != -1) {
                entryDirPath = entryFilePath.substring(0, index);
            }
            else {
                entryDirPath = "";
            }
            entryDir = new File(entryDirPath);
            //如果文件夹路径不存在，则创建文件夹
            if (!entryDir.exists() || !entryDir.isDirectory()) {
                entryDir.mkdirs();
            }
            //创建解压文件
            entryFile = new File(entryFilePath);
            if (entryFile.exists()) {
                //检测文件是否允许删除，如果不允许删除，将会抛出SecurityException
                SecurityManager securityManager = new SecurityManager();
                securityManager.checkDelete(entryFilePath);
                //删除已存在的目标文件
                entryFile.delete();
            }
            //写入文件
            bos = new BufferedOutputStream(new FileOutputStream(entryFile));
            bis = new BufferedInputStream(zip.getInputStream(entry));
            while ((count = bis.read(buffer, 0, 1024)) != -1) {
                bos.write(buffer, 0, count);
            }
            bos.flush();
            bos.close();
        }
    }

    /**
     *@Description 上传文件到路径
     *@Param [filePath 文件夹,不含斜杠, uploadfile 上传文件,直接给过来]
     *@Return void
     *@Author pitianyi
     *@Date 2019/8/20
     */
    public static void uploadFileToPath(String filePath, MultipartFile uploadfile,String fileName){
        logger.info("AaronUtil的uploadFileToPath方法接参 : " + filePath);
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            inputStream = uploadfile.getInputStream();
            String fileContructor = filePath + File.separator + uploadfile.getOriginalFilename();
            if(null != fileName){
                logger.info("uploadfile.getOriginalFilename() :" + uploadfile.getOriginalFilename());
                String[] split = uploadfile.getOriginalFilename().split("\\.");
                String s = Arrays.toString(split);
                logger.info(s);
                logger.info("split.length :" + split.length);
                fileContructor = filePath + File.separator +fileName +"."+ split[split.length-1];
            }
            File file = new File(fileContructor);
            //没有父文件夹,创建父文件夹
            if(!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            //该文件已经有了,那就删掉.
            if(file.exists()){
                file.delete();
            }
            //创建新文件
            file.createNewFile();
            outputStream = new FileOutputStream(file);
            //两个流的信息交互
            while(true){
                byte[] bytes = new byte[1024];
                int read = inputStream.read(bytes);
                outputStream.write(bytes);
                if(-1 == read){
                    break;
                }
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null != inputStream){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(null != outputStream){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     *@Description 输入一个文件夹路径
     *@Param [path]
     *@Return 文件读出来的字符串内容
     *@Author pitianyi
     *@Date 2019/8/20
     */
    public static String readFile(String path){
        File file = new File(path);
        String result = null;
        //要是属于文件夹
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for(File onefile : files){
                if(onefile.getName().endsWith("html")){
                    try {
                        InputStream  inputStream = new FileInputStream(onefile);
                        Scanner s = new Scanner(inputStream).useDelimiter("\\A");
                        result = s.hasNext() ? s.next() : "";
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return result.trim();
    }
    /**
     *@Description 字符串是否为空
     *@Param [str]
     *@Return boolean
     *@Author pitianyi
     *@Date 2019/8/21
     */
    public static boolean strIsEmpty(String str){
        if(null != str && str.trim().length()>0){
            //字符串不为空
            return false;
        }else{
            //字符串为空
            return true;
        }
    }
    /**
     *@Description 集合是否为空
     *@Param [c]
     *@Return boolean
     *@Author pitianyi
     *@Date 2019/8/21
     */
    public static boolean collectionIsEmpty(Collection c){
        if(null != c && c.size()>0){
            return false;
        }else{
            return true;
        }
    }

}