package com.zycw.common.util.adapter;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlParseUtils {


    public static Map<String,Object> map= new HashMap<String,Object>();

    /**
     *
     * 解析报文
     * @param xml
     * @param name
     * @return
     * @throws DocumentException
     */
    public static String parse(String xml,String name) throws DocumentException {
        Document doc = DocumentHelper.parseText(xml);//获取xml文件
        Element root = doc.getRootElement();//获取根目录
        getCode(root);
        if (!map.isEmpty()){
            String value = (String) map.get(name);
            return value;
        }
        return null;
    }


    public  static void getCode(Element root){
        if(root.elements()!=null){
            List<Element> list = root.elements();//如果当前跟节点有子节点，找到子节点
            for(Element e:list){//遍历每个节点
                if(e.elements().size()>0){
                    getCode(e);//当前节点不为空的话，递归遍历子节点；
                }
                if(e.elements().size()==0){

                    map.put(e.getName(), e.getTextTrim());
                }//如果为叶子节点，那么直接把名字和值放入map
            }
        }
    }

}
