package com.zycw.common.util.adapter;

/**
 * @ClassName XMLUtil
 * @Author kangjianwei
 * @Date 2019/9/18
 */
import org.jdom.*;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;

/**
 * 解析返回soap报文，获取目标节点值
 * @author Wtao
 * @date 2019-06-04
 */
public class XMLUtil {

    /**
     * 将字符串类型的XML 转化成Docunent文档结构
     * @param parseStrXml 待转换的XML字符串
     * @return
     */
    public static Document strXmlToDocument(String parseStrXml) {
        StringReader read = new StringReader(parseStrXml);
        //创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
        InputSource source = new InputSource(read);
        //创建一个新的SAXBuilder
        // 新建立构造器
        SAXBuilder sb = new SAXBuilder();
        Document doc = null;
        try {
            doc = sb.build(source);
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;

    }

    /**
     * 获取目标文档以根节点起
     * @param doc 文档结构
     * @return
     */
    public static Queue<String> getRootElementName(Document doc) {
        Element root = doc.getRootElement();
        Queue<String> queue_node = printAllNodes(root);
        return queue_node;
    }

    /**
     * 获取目标文档以根节点起
     * @param doc 文档结构
     * @return
     */
    public static Element getRootElementName(Document doc,String nodeName) {
        Element root = doc.getRootElement();
        Element e = printAllBodyNodes(root,nodeName);
        root.removeContent (e);
        return root;
    }
    /**
     * 获取目标文档以Body节点起
     *      * @param doc 文档结构
     * @return
     */
    public static Queue<String> getBodyElementName(Document doc) {
        if (queue_node != null) {
            queue_node.clear ();
        }
        Element root = doc.getRootElement();
        Queue<String> queue_node = printAllBodyNodes(root);
        return queue_node;
    }

    /**
     * 获取目标文档以Body节点起
     *      * @param doc 文档结构
     * @return
     */
    public static Boolean getBodyElementName(Document doc,String ruleNode,String ruleValue,String expSymbol) {
        Element root = doc.getRootElement();
        Boolean flag = printAllBodyNodes(root,ruleNode,ruleValue,expSymbol);
        return flag;
    }
    /**
     * 获取目标文档以Body节点起
     * @param node
     * @return
     */
    public static  Queue<String>  printAllBodyNodes(Element node) {
        // 递归遍历当前节点所有的子节点
        List<Element> listElement = node.getChildren ();
        // 遍历所有一级子节点
        for (Element e : listElement) {
            if (e.getName () == "Header" || e.getName ().equals ("Header")) {
                continue;
            }
            queue_node.add (e.getName ());
            printAllBodyNodes(e);
        }
        return queue_node;
    }

    /**
     * 获取目标文档以Body节点起
     * @param node
     * @return
     */
    public static  Element  printAllBodyNodes(Element node,String nodeName) {
        // 递归遍历当前节点所有的子节点
        List<Element> listElement = node.getChildren ();
        // 遍历所有一级子节点
        for (Element e : listElement) {
            if (e.getName () == nodeName || e.getName ().equals (nodeName)) {
                return e;
            }
            printAllBodyNodes(e);
        }
        return null;
    }

    /**
     * 获取目标文档以Body节点起,判断节点及值
     * @param node
     * @return
     */
    public static  Boolean  printAllBodyNodes(Element node,String ruleNode,String ruleValue,String expSymbol) {
        // 递归遍历当前节点所有的子节点
        List<Element> listElement = node.getChildren ();
        // 遍历所有一级子节点
        for (Element e : listElement) {
            if (e.getName () == "Header" || e.getName ().equals ("Header")) {
                continue;
            }
            //判断节点是否为规则节点
            if (e.getName () == ruleNode || e.getName ().equals (ruleNode)) {

                if (ruleValue.indexOf (e.getValue ()) != -1) {
                    return true;
                }
            }
            printAllBodyNodes(e,ruleNode,ruleValue,expSymbol);
        }
        return false;
    }

    /**
     * body中所有节点
     */
    public static Queue<String> queue_node = new LinkedList<String>();
    /**
     * 标签中所有属性及值
     */
    public static Queue<String> queue_attr = new LinkedList<String>();
    /**
     * 标签中所有前缀
     */
    public static Queue<String> queue_prefix = new LinkedList<String>();

    /**
     * 从指定节点开始,向控制台打印所有节点 ，包括其子节点<br/>
     * 表示方案： [属性]，{节点内容}
     * @param node 所指定的节点
     */
    public static  Queue<String>  printAllNodes(Element node) {
        if (queue_node != null) {
            queue_node.clear ();
        }
        // 递归遍历当前节点所有的子节点
        List<Element> listElement = node.getChildren();
        // 遍历所有一级子节点
        for (Element e : listElement) {
            queue_node.add (e.getName ());
            if (!e.getNamespacePrefix().isEmpty()) {
                queue_prefix.add(e.getNamespacePrefix());
            }
            if (!e.getNamespaceURI().isEmpty()) {
                queue_prefix.add(e.getNamespaceURI());
            }
            if (!e.getAttributes().isEmpty()) {
                List<Attribute> list = e.getAttributes();
                Attribute element = list.get(0);
                String name = element.getName();
                String valStr = element.getValue();
                Namespace space = element.getNamespace();
                String prefix = space.getPrefix();
                String urlStr = space.getURI();

                queue_attr.add(prefix+":"+name+"=\""+valStr+"\"");
            }
            if (!e.getAdditionalNamespaces().isEmpty()) {
                List<Namespace> list = e.getAdditionalNamespaces();
                Namespace space = list.get(0);
                String prefix = space.getPrefix();
                String urlStr = space.getURI();

                queue_attr.add("xmlns:"+prefix+"=\""+urlStr+"\"");
            }
            printAllNodes(e);// 递归.append(",")
        }

        return queue_node;
    }


}

