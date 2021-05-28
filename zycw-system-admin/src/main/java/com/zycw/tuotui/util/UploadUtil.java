package com.zycw.tuotui.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.UUID;

public class UploadUtil {

	 private static Logger logger = LoggerFactory.getLogger(UploadUtil.class);

    /**
     * 返回随机文件名称
     * @return
     */
	 public static String fileNameCreate(){
        return UUID.randomUUID().toString().replaceAll("-", "");
     }

	/**
     *@Description 上传文件到路径
     *@Param [filePath 文件夹,不含斜杠, uploadfile 上传文件,直接给过来]
     *@Return void
     *@Date 2019/8/20
     */
    public static void uploadFileToPath(String filePath, MultipartFile uploadfile, String fileName){
        logger.info("UploadUtil的uploadFileToPath方法接参 : " + filePath);
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
                fileContructor = filePath + File.separator +fileName;// +"."+ split[split.length-1];
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
            //创建一个缓冲区
            byte buffer[] = new byte[1024];
            //判断输入流中的数据是否已经读完的标识
            int len = 0;
            //两个流的信息交互
            while((len=inputStream.read(buffer))>0){
             //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
                outputStream.write(buffer, 0, len);
            }
//            while(true){
//                byte[] bytes = new byte[1024];
//                int read = inputStream.read(bytes);
//                outputStream.write(bytes);
//                if(-1 == read){
//                    break;
//                }
//                outputStream.flush();
//            }
            //update --by--shk
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        	if(inputStream != null ||outputStream != null) {
        		saleClose(inputStream, outputStream);
        	}
        }
    }
    
    /**
     *@Description 输入一个文件夹路径
     *@Param [path]
     *@Return 文件读出来的字符串内容
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
                	 InputStream  inputStream = null;
                	try {
                        inputStream = new FileInputStream(onefile);
                        Scanner s = new Scanner(inputStream).useDelimiter("\\A");
                        result = s.hasNext() ? s.next() : "";
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } finally {
                    	saleClose(inputStream);
					}
                }
            }
        }
        return result.trim();
    }
    
    /**
     * 关闭输入流
     * @param inputStream
     */
    public static void saleClose(InputStream inputStream) {
    	try {
    		if(inputStream != null) {
    			inputStream.close();
    		}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
	    		if(inputStream != null) {
	    			inputStream.close();
	    		}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    }
    /**
     * 关闭输入流和输出流
     * @param inputStream
     */
    public static void saleClose(InputStream inputStream,FileOutputStream outputStream) {
    	try {
    		if(inputStream != null) {
    			inputStream.close();
    		}
    		if(outputStream != null) {
    			outputStream.flush();
    			outputStream.close();
    		}
    	} catch (IOException e) {
    		e.printStackTrace();
    	} finally {
    		try {
    			if(inputStream != null) {
    				inputStream.close();
    			}
    			if(outputStream != null) {
    				outputStream.flush();
    				outputStream.close();
    			}
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    	}
    }
    
    public static void createDirectory(String path) {
    	File file = new File(path);
    	if(!file.exists()){
            file.mkdirs();
        }
    }
}
