package com.zycw.common.util;

import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import com.thoughtworks.xstream.XStream;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ClassToParamUtil {
/**
 * Title: 类与JSON、XML格式转换
 *
 * @author   yangjunxu
 * @version  1.0
 * @since    
 * @Date	 2018年4月4日  上午12:19:56	
 * @return 
 * @throws SecurityException 
 * @throws NoSuchMethodException 
 * @throws InvocationTargetException 
 * @throws IllegalArgumentException 
 * @throws IllegalAccessException 
 */
    private static  Object invokeMethod(Object owner, String fieldName, Object[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
    {
        Class<? extends Object> ownerClass = owner.getClass();
         
        String methodName = fieldName.substring(0, 1).toUpperCase()+ fieldName.substring(1);
        
        Method method = null;
        method = ownerClass.getMethod("get" + methodName);
        methodName=null;
        return method.invoke(owner);
        
        
    }

	public static String ClassToRequestParameter(Object owner) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Class<? extends Object> ownerClass = owner.getClass();
		Field [] fields = ownerClass.getDeclaredFields();
		StringBuilder sb=new StringBuilder();
		for(int i=0; i< fields.length; i++)
        {
			Field f = fields[i];
			String fieldName=f.getName().toString();
			
			//String methodName = fieldName.substring(0, 1).toUpperCase()+ fieldName.substring(1);
			//Method method = ownerClass.getMethod("get" + methodName);
			//System.out.println(fieldName+"----"+method);
			String paraValue=invokeMethod(owner, fieldName,null).toString();
			if(i==0)sb.append(fieldName).append("=").append(paraValue);
			else sb.append("&").append(fieldName).append("=").append(paraValue);
        }
		return sb.toString();
	}
	
	public static String ClassToJson(Object owner){
		JSONObject cvvo=JSONObject.fromObject(owner);
        String cvvs=cvvo.toString();
        System.out.println("ClassToJson:"+cvvs);
        cvvo=null;
		return cvvs;
	}
	
	public static String ClassToJsonArray(List<Object> list){
		JSONArray jsonarray = JSONArray.fromObject(list);
        String cvvs=jsonarray.toString();
        System.out.println("ClassToJsonArray:"+cvvs);
        jsonarray=null;
		return cvvs;
	}

	
	public static String ClassToXml(Object obj,Map<String,String> map) throws Exception{
		String classname=obj.getClass().getName();
		 XStream xstream = new XStream();             
	     String xmlString= xstream.toXML(obj);

			for (Map.Entry<String, String> entry : map.entrySet()) {
			    //System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
			    xmlString=xmlString.replaceAll(entry.getKey(),entry.getValue() );
			    entry=null;
			}  
	     return xmlString.replaceAll(classname, "xml");
	}
	
	public static Object JsonObjToClass(String result,Class classobj,Map<String,Class<?>> map){
		JSONObject jO = JSONObject.fromObject(result);
		Object object2=null;
		if(map==null)object2 = JSONObject.toBean(jO, classobj); 
		else object2 = JSONObject.toBean(jO, classobj,map);
		jO=null;
		return object2;
	}

	
	public static Object XmlObjToClass(String result,Class classobj,Map<String, String> map) throws Exception{

	

		for (Map.Entry<String, String> entry : map.entrySet()) {
		    //System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
		    result=result.replaceAll(entry.getKey(),entry.getValue() );
		    entry=null;
		}  
		XStream xstream=new XStream();
		System.out.println(result);
        xstream.processAnnotations(classobj);
        Object obj=(Object)xstream.fromXML(result);
        xstream=null;
        return obj; 
	}

}
