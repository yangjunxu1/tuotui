package com.zycw.system.admin.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

public class MapUtil {

	 private static Logger logger = LoggerFactory.getLogger(MapUtil.class);

   public static String MapToString(HashMap map,String key){
	   String result=(String)map.get(key);
	   if(result==null){
		   return "";
	   }else{
		   return result;
	   }
   }
   public static Integer MapToInteger(HashMap map,String key){
	   String result=MapToString(map,key);
	   if(result.equals("")){
		   return 0;
	   }else{
		   return Integer.parseInt(result);
	   }
   }
   public static String MapLongToString(Map<String, Object> map,String key){
	   Long result=(Long)map.get(key);
	   if(result!=null){
			  return Long.toString(result);
	   }else{
		   return null;
	   }
   }
   public static String MapBigIntegerToString(Map<String, Object> map,String key){
	   BigInteger result=(BigInteger)map.get(key);
	   if(result!=null){
		   int num=result.intValue();
		  return Integer.toString(num);
	   }else{
		   return null;
	   }
   }
}
