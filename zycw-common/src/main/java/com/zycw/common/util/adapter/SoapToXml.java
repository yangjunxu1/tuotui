package com.zycw.common.util.adapter;

import de.odysseus.staxon.json.JsonXMLConfig;
import de.odysseus.staxon.json.JsonXMLConfigBuilder;
import de.odysseus.staxon.json.JsonXMLInputFactory;
import de.odysseus.staxon.xml.util.PrettyXMLEventWriter;
import org.json.JSONObject;
import org.json.XML;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import java.io.*;
import java.util.Queue;

/**
 * Soap报文XML与JSON互转
 *
 * @ClassName SoapToXml
 * @Author kangjianwei
 * @Date 2019-09-06
 */
public class SoapToXml {

    /**
     * 过滤xml标签中前缀、命名空间等
     * @param xml
     * @return
     */
    public static String xmlFilterStr (String xml){
        //获取所有标签namespace
        Queue<String> node_set = XMLUtil.getRootElementName(XMLUtil.strXmlToDocument(xml));
        Queue<String> queue_attrs = XMLUtil.queue_attr;
        for (String x : queue_attrs) {
            xml = xml.replaceAll(x,"");
        }
        Queue<String> queue_prefixs = XMLUtil.queue_prefix;
        for (String y : queue_prefixs) {
            if (xml.contains(y+":")) {
                xml = xml.replaceAll(y+":","");
            }
            xml = xml.replaceAll(y,"");
        }
        //防止漏删
        xml = xml.replaceAll("xmlns:=\"\"","");
        System.out.println("格式化xml==="+xml);
        StringBuffer stringBuffer = new StringBuffer();
        String[] xml_byte = xml.split(">");

            for (int i=0;i < xml_byte.length;i++) {
                String str = xml_byte[i];
                if (str == "<?xml version='1.0' encoding='utf-8'?" || str.contentEquals("<?xml version='1.0' encoding='utf-8'?")) {
                    continue;
                }
            stringBuffer.append(str).append(">");
        }

        return stringBuffer.toString();
    }
    /**
     * xml to json
     *
     * @param xml
     * @return
     */
//    public static String xml2json(String xml) {
//        //TODO 接口调用时需去掉此方法
//        String xmlStr = xmlFilterStr(xml);
//
//        JSONObject xmlJSONObj = XML.toJSONObject(xmlStr);
//        return xmlJSONObj.toString();
//    }


    /**
     * json to xml
     *
     * @param json
     * @return
     */
    public static String json2xml(String json) {
        StringReader input = new StringReader(json);
        StringWriter output = new StringWriter();
        JsonXMLConfig config = new JsonXMLConfigBuilder().multiplePI(false).repairingNamespaces(false).build();
        try {
            XMLEventReader reader = new JsonXMLInputFactory(config).createXMLEventReader(input);
            XMLEventWriter writer = XMLOutputFactory.newInstance().createXMLEventWriter(output);
            writer = new PrettyXMLEventWriter(writer);
            writer.add(reader);
            reader.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (output.toString().length() >= 38) {// remove <?xml version="1.0" encoding="UTF-8"?>
            System.out.println(output.toString().substring(39));
            return output.toString().substring(39);
        }
        return output.toString();
    }

    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     */
    public static String readFileByLines(String fileName) {
        StringBuffer stringBuffer = new StringBuffer();
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                stringBuffer.append(tempString);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return stringBuffer.toString();
    }

    public static void main(String[] args) {

        String path = SoapToXml.class.getClassLoader().getResource("static").getPath();

        String fileStr = readFileByLines(path +"/aa.txt");

        //String jsonStr = xml2json(fileStr);
        String jsonStr = json2xml(fileStr);
        //Queue<String> node_set = XMLUtil.getBodyElementName(XMLUtil.strXmlToDocument(fileStr));
        System.out.println("jsonStr ===="+jsonStr);
}


}
