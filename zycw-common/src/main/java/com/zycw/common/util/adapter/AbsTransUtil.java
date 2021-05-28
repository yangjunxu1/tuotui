package com.zycw.common.util.adapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public abstract class AbsTransUtil<T> {
	
	private static Logger logger = LoggerFactory.getLogger(AbsTransUtil.class);
	
	public enum NodeType{OBJ,ARR,ATTR};

	/**
	 *根据映射关系列表将源报文拷贝到目标报文 
	 * @param mappingList 映射列表二位数组{{"源路径","目标路径"}}
	 * @param srcParentDataPath 数据源父级数据检索路径
	 * @param tgtParentNode 父级节点
	 * @param pathVisited 访问过的路径集合（暂时预留）
	 * @return 暂时不返回
	 */
	public abstract T deepTransForm(ArrayList<String[]> mappingList,String srcParentDataPath,T tgtParentNode,HashMap pathVisited);
	
	/**
	 * 节点类型识别
	 * @param node 节点字符串
	 * @return 返回节点类型
	 */
	public static NodeType getNodeType(String node){
		NodeType t=null;

		if(StringUtils.isNotBlank(node)){
			t=NodeType.OBJ;
			String left1=node.substring(0, 1);
			String right1=node.substring(node.length()-1);
			if(left1.equals("{")&&right1.equals("}")){
				t=NodeType.ATTR;
			}

			if(node.length()>=2&&node.substring(node.length()-2).equals("[]")){
				t=NodeType.ARR;
			}
		}else {
			t=NodeType.ATTR;
		}

		//logger.debug(left1+"----"+right1);
		//logger.debug("[]".substring("[]".length()-2));


		return t;
	}
	
	/**
	 * 从json节点的字符串（带节点类型标识的  [] {} 等）中解析出节点名称
	 * @param nodeStr 节点字符串
	 * @param nodeType 节点类型
	 * @return
	 */
	public static String getNodeName(String nodeStr,NodeType nodeType){
		String ret=nodeStr;
		if(nodeType==NodeType.ARR){//nodename[]
			ret=nodeStr.substring(0,nodeStr.length()-2);
		}
		
		if(nodeType==NodeType.ATTR){//{nodename}
			ret=nodeStr.substring(1,nodeStr.length()-1);
		}
		
		return ret;
	}
	
	/**
	 * 获取左边第一个路径节点
	 * @param path 解析第一个  /  前的字符串和剩余的路径
	 * @param splitor 分隔符
	 * @return 返回[0]第一个节点  [1]剩下的路径
	 */
	public static String[] getLeftPathInfo(String path,String splitor){
		//如果最左边以分隔符开始，则去掉分隔符
		if(path!=null&&path.length()>0&&path.substring(0, 1).equals(splitor)){
			path=path.substring(1);
		}
		String[] pathInfo=new String[2];
		pathInfo[0]="";
		pathInfo[1]="";
		if(path.indexOf(splitor)>-1){
			String[] pathArr=path.split(splitor);
			pathInfo[0]=pathArr[0];
			for(int i=1;i<pathArr.length;i++){
				if(i!=pathArr.length-1){
					pathInfo[1]+=pathArr[i]+splitor;
				}else{
					pathInfo[1]+=pathArr[i];
				}
			}
			//给左边加上/
			pathInfo[0]=splitor+pathInfo[0];
			
		}else{
			pathInfo[0]=path;
			pathInfo[1]="";
		}
		
		return pathInfo;
	}
	
	/**
	 * 获取带namespace前缀的节点信息，返回数组第一个是前缀，第二个是真正的名字，如果不带前缀则第一个为空
	 * @param nodeName
	 * @return 
	 */
	public static String[] getNameSpaceNodeInfo(String nodeName){
		String[] ns={"",""};
		if(nodeName.indexOf(":")>-1){
			ns=nodeName.split(":");
		}else{
			ns[1]=nodeName;
		}
		
		return ns;
	}
	
	
	/**
	 * 获取右边第一个路径节点
	 * @param path 解析最后一个  /  右边的字符串和左边的路径
	 * @param splitor 分隔符
	 * @return 返回[0]右边第一个节点  [1]左边剩下的路径
	 */
	public static String[] getRightPathInfo(String path,String splitor){
		//如果最左边以分隔符开始，则去掉分隔符
		if(path!=null&&path.length()>0&&path.substring(0, 1).equals(splitor)){
			path=path.substring(1);
		}
		String[] pathInfo=new String[2];
		pathInfo[0]="";
		pathInfo[1]="";
		if(path.indexOf(splitor)>-1){
			String[] pathArr=path.split(splitor);
			pathInfo[0]=pathArr[pathArr.length-1];
			for(int i=0;i<pathArr.length-1;i++){
				if(i!=pathArr.length-2){
					pathInfo[1]+=pathArr[i]+splitor;
				}else{
					pathInfo[1]+=pathArr[i];
				}
			}
			pathInfo[1]=splitor+pathInfo[1];//加上左边的路径
			
		}else{
			pathInfo[0]=path;
			pathInfo[1]="";
		}
		
		return pathInfo;
	}
	
	/**
	 * 获取数组父节点名字  
	 * @param arrNodeName 形如：parent|item[]
	 * @return {parent,item}
	 */
	public static String[] getArrNodeInfo(String arrNodeName){
		String[] ret={"",""};
		String name=arrNodeName;
		if(arrNodeName!=null&&arrNodeName.length()>0){
			name=arrNodeName.replace("[]", "");
			if(name.indexOf("|")>-1){
				ret=name.split("\\|");//正则表达式字符 “ | ” 需要转义一下
			}
		}
		return ret;
	}


	/**
	 * 获取当前路径下所有下级路径的映射配置
	 * @param fullMapping 全量的路径映射
	 * @param curSrcPath 当前源路径
	 * @param curTgtPath 当前目标路径
	 * @return 返回目标路径映射关系列表
	 */
	public ArrayList<String[]> getSubLevelMapping(ArrayList<String[]> fullMapping,String curSrcPath,String curTgtPath){
		ArrayList<String[]> subMapping=new ArrayList<String[]>();

		//取最右边路径
		String srcRightPath=AbsTransUtil.getRightPathInfo(curSrcPath, this.getPathSpator())[0];
		String tgtRightPath=AbsTransUtil.getRightPathInfo(curTgtPath, this.getPathSpator())[0];
		//去掉数组
		srcRightPath=srcRightPath.replace("[]", "");
		tgtRightPath=tgtRightPath.replace("[]", "");
		
		//遍历所有路径，取与当前路径相关的映射
		for(int i=0;i<fullMapping.size();i++){
			//判断当前路径是否与路径一样
			if(fullMapping.get(i)[0].equals(curSrcPath)){
			//直接跳过
			 continue;
			
			}else if(fullMapping.get(i)[0].indexOf(curSrcPath)>-1){//判断当前路径是否是子路径
				String[] mapping=new String[2];
				//直接获取剩余路径,
				mapping[0]=fullMapping.get(i)[0].substring(curSrcPath.length());
				mapping[1]=fullMapping.get(i)[1].substring(curTgtPath.length());
				subMapping.add(mapping);
			}
		}
		
		return subMapping;
	}	
	
	/**
	 * 判断是否有多级数组
	 * @param path 当前路径
	 * @return 如果是多级数组，则返回第一级数组路径，如果不是则返回空
	 */
	public static String isMulitiArrNode(String path){
		String ret="";
		String[] arrNodes=null;
		if(path!=null&&path.indexOf("[]")>-1){
			arrNodes=path.split("\\[\\]");
			if(arrNodes.length>1){
				ret=arrNodes[0]+"[]";
			}
		}

		return ret;
	}
	
	
	/**
	 * 根据对象创建json
	 * @param obj
	 * @return json串
	 */
	public static String createJsonByObj(Object obj){
		ObjectMapper om=new ObjectMapper();
		String ret="";
		try{
			ret=om.writeValueAsString(obj);
			logger.debug("ret:"+ret);
		}catch(Exception e){
			logger.error(e.toString()+e);
		}
		return ret;
	}
	
	/**
	 * 去掉值节点字符串的大括号
	 * @param attrMappingNode 值节点字符串
	 * @return 返回去掉括号的
	 */
	public static String getAttrName(String attrMappingNode){
		String ret=attrMappingNode.replace("{", "");
		ret=ret.replace("}", "");
		return ret;
	}
	
	/**
	 * 默认的路径分割字符串
	 */
	private String pathSpator="/";
	
	public String getPathSpator() {
		return pathSpator;
	}

	public void setPathSpator(String spator) {
		this.pathSpator = pathSpator;
	}
	
}
