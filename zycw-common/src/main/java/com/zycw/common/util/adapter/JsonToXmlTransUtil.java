package com.zycw.common.util.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.Node;
import org.dom4j.XPath;

import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonToXmlTransUtil extends AbsTransUtil<Element> {

	private static Logger logger =  LoggerFactory.getLogger(JsonToXmlTransUtil.class);

	private Document doc=null;
	private ObjectMapper om=null;
	private JsonNode jsonRoot=null;
	private HashMap<String,Namespace> ns=null;


	public HashMap<String, Namespace> getNs() {
		return ns;
	}

	public void setNs(HashMap<String, Namespace> ns) {
		this.ns = ns;
	}

	public JsonNode getJsonRoot() {
		return jsonRoot;
	}

	public void setJsonRoot(JsonNode jsonRoot) {
		this.jsonRoot = jsonRoot;
	}

	public ObjectMapper getOm() {
		return om;
	}

	public void setOm(ObjectMapper om) {
		this.om = om;
	}

	public Document getDoc() {
		return doc;
	}

	public void setDoc(Document doc) {
		this.doc = doc;
	}

	public JsonToXmlTransUtil(){

	}

	public JsonToXmlTransUtil(String jsonStr) throws Exception{
		this.setDoc(DocumentHelper.createDocument());
		this.setOm(new ObjectMapper());
		this.setJsonRoot(this.getOm().readTree(jsonStr));
	}

	/**
	 * Json到Xml的转换
	 */
	@Override
	public Element deepTransForm(ArrayList<String[]> mappingList, String srcParentDataPath, Element tgtParentNode,
								 HashMap pathVisited) {

		String subNodeName="";
		String subSrcDataPath="";//遇到数组节点递归下一层次时的数据查询路径
		String[] pathInfoArr={"",""};
		String[] tgtPathInfoArr={"",""};
		String tgtParentPath="";
		String[] subPathInfoArr={"",""};
		String[] arrNodeInfo={"",""};
		String parentPathNode="";
		Element tmpObjectNode=null;
		ArrayList<String[]> subMapping=null;
		String firstArr="";
		HashMap thisLevelVisitedPath=new HashMap();
		String path1="";
		String path2="";//去掉所有的 [n] 字符串
		String restPath="";//取超出的路径


		//1循环映射配置
		for(int i=0;i<mappingList.size();i++){

			//如果当前节点被遍历过则不用遍历了
			if(thisLevelVisitedPath.containsKey(mappingList.get(i)[0])){
				continue;
			}else{
				//如果当前节点不是第一级数组节点，且当前节点的上级数组被遍历过，则不用遍历了
				firstArr=isMulitiArrNode(mappingList.get(i)[0]);
				if(!firstArr.equals("")&&thisLevelVisitedPath.containsKey(firstArr)){
					continue;
				}
			}

			//2.1如果当前源路径节点是对象
			pathInfoArr= AbsTransUtil.getRightPathInfo(mappingList.get(i)[0], this.getPathSpator());
			if(AbsTransUtil.getNodeType(pathInfoArr[0])== AbsTransUtil.NodeType.OBJ){

				//2.1则根据源路径获取目标路径，逐级生成目标路径节点，如果存在则不用重复创建
				tgtParentPath=String.valueOf(pathVisited.get("tgtParentPath"));
				if(tgtParentNode==null){
					restPath=mappingList.get(i)[1];
				}else{
					path1=(this.getPathSpator()+getXMLAbsolutePath(tgtParentNode,this.getPathSpator())).replaceAll("\\[\\d+\\]", "");//递归获取完整的上级路径
					path2=(tgtParentPath+mappingList.get(i)[1]).replaceAll("\\[\\d+\\]", "");//去掉所有的 [n] 字符串
					//pathVisited.put("tgtParentPath", tgtParentPath+path1);//设置已经递归过的路径
					restPath=getSubCatPath(path1,path2);//取超出的路径
				}
				if(tgtParentNode==null){//如果是初始状态，直接创建
					tgtParentNode=deepCreateNode(tgtParentNode,restPath,this.getPathSpator());
					//tmpObjectNode=tgtParentNode;
				}else{
					if(isLeftSubStr(path1,path2)){//如果path1是path2的子路径（从0开始计算）且确实存在多余的路径可创建
						if(tgtParentNode.selectSingleNode(getRelativePath(restPath,this.getPathSpator()))==null){//如果超出的路径不存在则直接创建
							tgtParentNode=deepCreateNode(tgtParentNode,restPath,this.getPathSpator());
						}else{
							//如果节点存在就定位
							tgtParentNode=(Element)tgtParentNode.selectSingleNode(getRelativePath(restPath,this.getPathSpator()));
						}
					}else{//如果不是子节点，则创建
						//取路径交集定位在上级
						path1=getIntersectionPath(path1,path2,this.getPathSpator());
						tgtParentNode=(Element)tgtParentNode.getDocument().selectSingleNode(path1);
						//求出还需创建多少
						restPath=getSubCatPath(path1,path2);
						//在交集基础上创建
						tgtParentNode=deepCreateNode(tgtParentNode,restPath,this.getPathSpator());

						//	tgtParentNode.
					}
				}
			}

			//3.1如果当前节点是数组对象
			if(AbsTransUtil.getNodeType(pathInfoArr[0])== AbsTransUtil.NodeType.ARR){

				//3.1根据映射目标路径生成目标数组对象
				//3.1获取目标数组对象所有下级路径的映射配置，将当前节点由数组转为单一节点
				subMapping=this.getSubLevelMapping(mappingList, mappingList.get(i)[0], mappingList.get(i)[1]);
				//3.1循环获取数组对象“SelectNodes("//item[1...n]")”获取父节点，生成对应的目标节点，递归调用自己，传入当前节点和子节点的映射列表，父节点，和遍历过的路径map
				//3.1.1获取完整数据查询路径，对于获取json数据，要取数组的上一级为取数节点
				String[] jsonQueryPathInfo=getRightPathInfo(this.getFullDataPath(srcParentDataPath, mappingList.get(i)[0]),this.getPathSpator());
				subSrcDataPath=jsonQueryPathInfo[1];

				//通过xml方法查询所有的子节点
				JsonNode list=selectNodes(this.getJsonRoot(),subSrcDataPath,this.getPathSpator());

				if(list!=null&&list.isArray()&&list.size()>0){

					if(tmpObjectNode==null){//如果为空给tmpObjectNode赋予默认值
						tmpObjectNode=tgtParentNode;
					}

					//将目标xml element对象节点定位在目标路径  mappingList.get(i)[1]
					tgtPathInfoArr= AbsTransUtil.getRightPathInfo(mappingList.get(i)[1], this.getPathSpator());
					tgtParentPath=String.valueOf(pathVisited.get("tgtParentPath"));
					//为确保目标节点一定存在因此深度创建一下
					if(tgtPathInfoArr[1]!=null&&!tgtPathInfoArr[1].equals("")){//上级节点存在才创造
						if(tgtParentPath==null||tgtParentPath.equals("")){//上级为空，则定位在根
							tmpObjectNode=(Element)tmpObjectNode.getDocument().getRootElement();
						}else{
							tmpObjectNode=(Element)tmpObjectNode.getDocument().selectSingleNode(tgtParentPath);
						}
						path1=(this.getPathSpator()+getXMLAbsolutePath(tmpObjectNode,this.getPathSpator())).replaceAll("\\[\\d+\\]", "");
						path2=(tgtParentPath+tgtPathInfoArr[1]).replaceAll("\\[\\d+\\]", "");////去掉所有的 [] 字符串;
						restPath=getSubCatPath(path1,path2);//取超出的路径
						if(restPath!=null&&!restPath.equals("")){
							if(tmpObjectNode.selectSingleNode(getRelativePath(restPath,this.getPathSpator()))==null){//如果超出的路径不存在，则创建
								tmpObjectNode=deepCreateNode(tmpObjectNode,restPath,this.getPathSpator());
							}else{//如果不为空则定位到这里
								tmpObjectNode=(Element)tmpObjectNode.selectSingleNode(getRelativePath(restPath,this.getPathSpator()));//如果存在则定位
							}
						}
					}

					//3.1.2将数组对象插入父级节点
					//3.1.2.1获取目标数组父节点名字
					subPathInfoArr= AbsTransUtil.getRightPathInfo(mappingList.get(i)[1], this.getPathSpator());
					arrNodeInfo=getArrNodeInfo(subPathInfoArr[0]);
					//3.1.2.2将数组节点插入父节点

					if(arrNodeInfo[0]!=null&&!arrNodeInfo[0].equals("")){
						if(tmpObjectNode.selectNodes(getRelativePath(arrNodeInfo[0],this.getPathSpator())).size()<=0){//如果数组的父节点不为空则定位到父节点，如果为空就不管直接使用tmpObjectNode
							//tmpObjectNode=(Element)this.getDoc().selectSingleNode(parentPathNode);
							tmpObjectNode=tmpObjectNode.addElement(arrNodeInfo[0]);//增加数组的父节点
						}else{
							tmpObjectNode=(Element)tmpObjectNode.selectSingleNode(getRelativePath(arrNodeInfo[0],this.getPathSpator()));//定位到这里
						}
					}

					//3.1.2.3循环递归生成每个数组节点的子节点数据
					int elementCount=0;
					for (int k=0;k<list.size();k++) {

						//Element attribute = (Element) iter.next();
						//3.1.2.3.1新增子节点，拼递归调用
						Element item=tmpObjectNode.addElement(arrNodeInfo[1]);

						//3.1.2.3.3递归新增子节点
						HashMap subPathVisited=new HashMap();

						//String pathTemp=tgtParentPath+tgtPathInfoArr[1]+this.getPathSpator()+arrNodeInfo[0]+this.getPathSpator()+arrNodeInfo[1]+"["+(elementCount+1)+"]";
						if(arrNodeInfo[0]==null||arrNodeInfo[0].equals("")){//如果数组上级为空则跳过拼路径
							subPathVisited.put("tgtParentPath", tgtParentPath+tgtPathInfoArr[1]+this.getPathSpator()+arrNodeInfo[1]+"["+(elementCount+1)+"]");
						}else{
							subPathVisited.put("tgtParentPath", tgtParentPath+tgtPathInfoArr[1]+this.getPathSpator()+arrNodeInfo[0]+this.getPathSpator()+arrNodeInfo[1]+"["+(elementCount+1)+"]");
						}
						//pathVisited.put("tgtParentPath", tgtParentPath+tgtPathInfoArr[1]+this.getPathSpator()+arrNodeInfo[0]+"["+(elementCount)+"]"+this.getPathSpator()+arrNodeInfo[1]);//设置已经递归过的路径
						deepTransForm(subMapping,subSrcDataPath+"["+elementCount+"]",item,subPathVisited);

						elementCount++;
					}
					subMapping.clear();
					subMapping=null;
					list=null;
				}
			}

			//4如果是数值映射，直接取值，给parentNode赋值
			if(AbsTransUtil.getNodeType(pathInfoArr[0])== AbsTransUtil.NodeType.ATTR){

				//直接按路径取节点，取text，赋值给parent的属性
				//获取数据源路径及值节点名称
				String[] srcTmpPathInfo=getRightPathInfo(mappingList.get(i)[0],this.getPathSpator());
				String srcTmpPathArrtName=getAttrName(srcTmpPathInfo[0]);
				JsonNode node = selectSingleNode(this.getJsonRoot(),this.getFullDataPath(srcParentDataPath, srcTmpPathInfo[1]),this.getPathSpator());//取除了属性以外的路径作为取值路径
				if(node!=null){
					//将目标xml对象节点定位在目标路径  mappingList.get(i)[1]
					tgtPathInfoArr= AbsTransUtil.getRightPathInfo(mappingList.get(i)[1], this.getPathSpator());
					//赋值
					if(node.findValue(srcTmpPathArrtName)!=null){
						//定位到需要的节点

						tgtParentPath=String.valueOf(pathVisited.get("tgtParentPath"));
						if(tgtParentPath==null||tgtParentPath.equals("")){//如果数组上级为空则跳过拼路径

							tgtParentNode=(Element)tgtParentNode.getDocument().selectSingleNode(tgtPathInfoArr[1]);
						}else{
							tgtParentNode=(Element)tgtParentNode.getDocument().selectSingleNode(tgtParentPath+tgtPathInfoArr[1]);
						}
						tgtParentNode.addElement(getAttrName(tgtPathInfoArr[0])).addText(node.findValue(srcTmpPathArrtName).asText());

					}
				}
			}

			thisLevelVisitedPath.put(mappingList.get(i)[0], mappingList.get(i)[1]);//域内访问过路径做个存储

		}

		//用完释放资源
		subNodeName="";
		subSrcDataPath="";//遇到数组节点递归下一层次时的数据查询路径
		pathInfoArr=null;
		tgtPathInfoArr=null;
		tgtParentPath="";
		subPathInfoArr=null;
		arrNodeInfo=null;
		parentPathNode="";
		tmpObjectNode=null;
		subMapping=null;
		firstArr="";
		thisLevelVisitedPath=null;
		path1="";
		path2="";//去掉所有的 [n] 字符串
		restPath="";//取超出的路径

		return null;
	}

	/**
	 * 比较当前路径和目标路径
	 * @param path1
	 * @param path2
	 * @return
	 */
	public static String getSubCatPath(String path1,String path2){
		String ret="";
		if(path1.length()>path2.length()){//如果path1大于path2，则返回本身
			ret=path1;
		}else{
			ret=path2.replaceFirst(path1, "");
		}
		return ret;
	}

	/**
	 * 获取从开始起的路径交集
	 * @param path1
	 * @param path2
	 * @return
	 */
	public static String getIntersectionPath(String path1,String path2,String seperator){
		String ret="";
		//去掉当前路径的左边第一个/
		if(path1.length()>=1&&path1.indexOf(seperator)>-1&&path1.substring(0,1).equals(seperator)){
			path1=path1.substring(1);
		}

		//去掉当前路径的左边第一个/
		if(path2.length()>=1&&path2.indexOf(seperator)>-1&&path2.substring(0,1).equals(seperator)){
			path2=path2.substring(1);
		}

		String[] path1Arr=path1.split(seperator);
		String[] path2Arr=path2.split(seperator);
		int count=0;
		//取最小的路径级数为循环节点数
		if(path2Arr.length>path1Arr.length){
			count=path1Arr.length;
		}else{
			count=path2Arr.length;
		}

		for(int i=0;i<count;i++){
			if(path1Arr[i]!=null&&path2Arr[i]!=null&&path1Arr[i].equals(path2Arr[i])){//下一个节点不为空才创造
				ret=ret+seperator+path1Arr[i];//拼路径
			}
		}

		return ret;
	}



	/**
	 * 获取节点完整路径
	 * @param e
	 * @param pathSeperator
	 * @return
	 */
	public static String getXMLAbsolutePath(Element e,String pathSeperator) {
		if (e.getParent() != null) {
			//e.getQualifiedName()  包含前缀的名字
			return getXMLAbsolutePath(e.getParent(),pathSeperator) + pathSeperator + e.getQualifiedName();
		} else {
			return e.getQualifiedName();
		}
	}


	/**
	 * 判断path1是否是path2从左边开始的子字符串
	 * @param path1
	 * @param path2
	 * @return
	 */
	public static Boolean isLeftSubStr(String path1,String path2){
		Boolean ret=false;
		if(path1.length()<path2.length()){
			String tmpPath=path2.substring(0, path1.length());
			if(tmpPath.equals(path1)){
				ret=true;
			}
		}

		return ret;
	}

	/**
	 * 根据当前路径和父级路径拼实际查询数据的路径
	 * @param parentPath 上级路径
	 * @param curPath 当前路径
	 * @return 返回完整的数据查询路径
	 */
	public String getFullDataPath(String parentPath,String curPath){

		//去掉当前路径的左边第一个/
		if(curPath.length()>=1&&curPath.indexOf(this.getPathSpator())>-1&&curPath.substring(0,1).equals(this.getPathSpator())){
			curPath=curPath.substring(1);
		}
		if(parentPath.length()>=2&&parentPath.indexOf("//")<=-1){
			if(parentPath.substring(0,1).equals(this.getPathSpator())){
				if(curPath.equals("")){
					parentPath=this.getPathSpator()+parentPath.substring(1);
				}else{
					parentPath=this.getPathSpator()+parentPath.substring(1)+this.getPathSpator()+curPath;
				}
			}else{
				if(curPath.equals("")){
					parentPath=this.getPathSpator()+parentPath;
				}else{
					parentPath=this.getPathSpator()+parentPath+this.getPathSpator()+curPath;
				}
			}
		}else{//如果包含了绝对路径直接累加
			if(curPath.equals("")){
				parentPath=this.getPathSpator()+parentPath;
			}else{
				parentPath=this.getPathSpator()+parentPath+this.getPathSpator()+curPath;
			}
		}

		//如果存在数组结构则将  | 替换为路径
		parentPath=parentPath.replace("|", this.getPathSpator());
		parentPath=parentPath.replace("////", this.getPathSpator());
		parentPath=parentPath.replace("///", this.getPathSpator());
		parentPath=parentPath.replace("//", this.getPathSpator());
		parentPath=parentPath.replace("[]", "");
		parentPath=parentPath.replace("{", "");//去掉值的大括号{}
		parentPath=parentPath.replace("}", "");

		return parentPath;

	}

	/**
	 * 去掉左边的分隔符
	 * @param path
	 * @param splitor
	 * @return
	 */
	public static String getRelativePath(String path,String splitor){
		String ret=path;
		if(ret.substring(0,1).equals(splitor)){
			ret=ret.substring(1);//如果左边有分隔符，则去掉
		}
		return ret;
	}

	/**
	 * 根据Json当前路径和父级路径拼实际查询数据的路径，主要碰到数组节点时只取到数组的上级
	 * @param parentPath 上级路径
	 * @param curPath 当前路径
	 * @return 返回完整的数据查询路径
	 */
	public String getJsonQueryDataPath(String parentPath,String curPath){

		//去掉当前路径的左边第一个/
		if(curPath.length()>=1&&curPath.indexOf(this.getPathSpator())>-1&&curPath.substring(0,1).equals(this.getPathSpator())){
			curPath=curPath.substring(1);
		}
		if(parentPath.length()>=2&&parentPath.indexOf("//")<=-1){
			if(parentPath.substring(0,1).equals(this.getPathSpator())){
				if(curPath.equals("")){
					parentPath=this.getPathSpator()+parentPath.substring(1);
				}else{
					parentPath=this.getPathSpator()+parentPath.substring(1)+this.getPathSpator()+curPath;
				}
			}else{
				if(curPath.equals("")){
					parentPath=this.getPathSpator()+parentPath;
				}else{
					parentPath=this.getPathSpator()+parentPath+this.getPathSpator()+curPath;
				}
			}
		}else{//如果包含了绝对路径直接累加
			if(curPath.equals("")){
				parentPath=this.getPathSpator()+parentPath;
			}else{
				parentPath=this.getPathSpator()+parentPath+this.getPathSpator()+curPath;
			}
		}

		//如果存在数组结构则将  | 替换为路径
		parentPath=parentPath.replace("|", this.getPathSpator());
		parentPath=parentPath.replace("////", this.getPathSpator());
		parentPath=parentPath.replace("///", this.getPathSpator());
		parentPath=parentPath.replace("//", this.getPathSpator());
		parentPath=parentPath.replace("[]", "");
		parentPath=parentPath.replace("{", "");//去掉值的大括号{}
		parentPath=parentPath.replace("}", "");

		return parentPath;

	}


	/**
	 * 创建路径
	 * @param parentNode 父节点
	 * @param nodePath 路径
	 * @param pathSplitor 路径分隔符
	 */
	public Element deepCreateNode(Element parentNode,String nodePath,String pathSplitor){
		//如果最左边以分隔符开始，则去掉分隔符
		if(nodePath!=null&&nodePath.length()>0&&nodePath.substring(0, 1).equals(pathSplitor)){
			nodePath=nodePath.substring(1);
		}
		String[] arr=nodePath.split(pathSplitor);
		String curPath="";
		String nextPath="";
		Node pointNode=null;
		Node tmpNode=null;
		String[] nodeNameInfoArr=null;

		if(arr!=null&&arr.length>0){
			if(parentNode==null){//如果是空节点，即是根节点，则直接创建

				nodeNameInfoArr=super.getNameSpaceNodeInfo(arr[0]);
				if(nodeNameInfoArr[0].equals("")){
					parentNode=this.getDoc().addElement(nodeNameInfoArr[1]);
				}else{
					parentNode=this.getDoc().addElement(arr[0], this.getNs().get(nodeNameInfoArr[0]).getURI());
					//parentNode=this.getDoc().addElement(nodeNameInfoArr[1]);
					//parentNode.add(this.getNs().get(nodeNameInfoArr[0]));
				}
				//设置所有命名空间
				setNameSpaces(parentNode,this.getNs());
				//parentNode.addNamespace("soapenv", "http://schemas.xmlsoap.org/soap/envelope/").addNamespace("soa", "http://www.e-chinalife.com/soa/").addNamespace("xsd", "http://cancelapplicationvo.server.soa.aslss.chinalife.com/xsd");
				//this.getNs().get(nodeNameInfoArr[0]).getURI()

				curPath=pathSplitor+arr[0];//拼从根开始的完整路径
			}else{
				curPath=pathSplitor+arr[0];//拼从根开始的完整路径
				//pointNode=parentNode.path(arr[0]);
				if(parentNode.selectSingleNode(getRelativePath(arr[0],pathSplitor))==null){
					nodeNameInfoArr=super.getNameSpaceNodeInfo(arr[0]);
					if(nodeNameInfoArr[0].equals("")){
						parentNode=parentNode.addElement(nodeNameInfoArr[1]);
					}else{
						parentNode=parentNode.addElement(arr[0], this.getNs().get(nodeNameInfoArr[0]).getURI());
						parentNode.add(this.getNs().get(nodeNameInfoArr[0]));
					}
				}
			}
			for(int i=1;i<arr.length;i++){
				if(arr[i]!=null&&!arr[i].equals("")){//下一个节点不为空才创造
					nextPath=curPath+pathSplitor+arr[i];//拼路径

					if(parentNode.selectSingleNode(getRelativePath(arr[i],pathSplitor))==null){

						nodeNameInfoArr=super.getNameSpaceNodeInfo(arr[i]);
						if(nodeNameInfoArr[0].equals("")){
							parentNode=parentNode.addElement(nodeNameInfoArr[1]);
						}else{
							parentNode=parentNode.addElement(arr[i], this.getNs().get(nodeNameInfoArr[0]).getURI());
							//parentNode=parentNode.addElement(nodeNameInfoArr[1]);
							//parentNode.add(this.getNs().get(nodeNameInfoArr[0]));
						}

						//parentNode=parentNode.addElement(arr[i]);
						//parentNode.addNamespace("soapenv", "http://schemas.xmlsoap.org/soap/envelope/").addNamespace("soa", "http://www.e-chinalife.com/soa/").addNamespace("xsd", "http://cancelapplicationvo.server.soa.aslss.chinalife.com/xsd");
					}
				}
			}
		}
		//最终定位在最后一个节点
		//parentNode.setDocument(this.getDoc());
		return parentNode;
	}

	/**
	 * 根据namespace map 设置 element的ns
	 * @param el
	 * @param nsHash
	 */
	public static void setNameSpaces(Element el,HashMap<String,Namespace> nsHash){
		if(nsHash!=null&&nsHash.keySet().size()>0){
			Iterator ik=nsHash.keySet().iterator();
			while(ik.hasNext()){
				el.add(nsHash.get(ik.next()));
			}
		}
	}


	public Element deepCreateNodeFromRoot1(Element parentNode,String nodePath,String pathSplitor){
		//如果最左边以分隔符开始，则去掉分隔符
		if(nodePath!=null&&nodePath.length()>0&&nodePath.substring(0, 1).equals(pathSplitor)){
			nodePath=nodePath.substring(1);
		}
		String[] arr=nodePath.split(pathSplitor);
		String curPath="";
		String nextPath="";
		Node pointNode=null;
		Node tmpNode=null;
		parentNode=this.getDoc().getRootElement();
		if(arr!=null&&arr.length>0){
			if(parentNode==null){//如果是空节点，即是根节点，则直接创建

				parentNode=this.getDoc().addElement(arr[0]);
				//parentNode.addNamespace("soapenv", "http://schemas.xmlsoap.org/soap/envelope/").addNamespace("soa", "http://www.e-chinalife.com/soa/").addNamespace("xsd", "http://cancelapplicationvo.server.soa.aslss.chinalife.com/xsd");
				curPath=pathSplitor+arr[0];//拼从根开始的完整路径
			}else{
				curPath=pathSplitor+arr[0];//拼从根开始的完整路径
				parentNode=(Element)this.getDoc().selectSingleNode(curPath);
				if(parentNode.selectSingleNode(curPath)==null){
					parentNode=this.getDoc().addElement(arr[0]);
				}
			}
			for(int i=1;i<arr.length;i++){
				if(arr[i]!=null&&!arr[i].equals("")){//下一个节点不为空才创造
					nextPath=curPath+pathSplitor+arr[i];//拼路径

					if(this.getDoc().selectSingleNode(nextPath)==null){
						parentNode=parentNode.addElement(arr[i]);
						//parentNode.addNamespace("soapenv", "http://schemas.xmlsoap.org/soap/envelope/").addNamespace("soa", "http://www.e-chinalife.com/soa/").addNamespace("xsd", "http://cancelapplicationvo.server.soa.aslss.chinalife.com/xsd");
					}else{
						parentNode=(Element)this.getDoc().selectSingleNode(nextPath);

					}
				}
			}
		}
		//parentNode=(Element)this.getDoc().selectSingleNode(nodePath);
		return parentNode;
	}

	public static JsonNode selectSingleNode(JsonNode node,String xPath,String splitor){
		JsonNode jn=selectNodes(node,xPath,splitor);
		if(jn!=null&&!jn.isEmpty()&&jn.isArray()){
			return jn.get(0);
		}
		return jn;
	}


	/**
	 * 根据路径选取节点
	 * @param node 父节点
	 * @param xPath 查询路径，例如： /data/itemList/items[1]/item/subitems[2]/value
	 * @return 返回JsonNode
	 */
	public static JsonNode selectNodes(JsonNode node,String xPath,String splitor){
		String curPath="";
		String nextPath="";
		String nodeNum="";
		JsonPointer jp=null;
		JsonNode tmpNode=null;
		///String queryPath="";
		String[] pathArr=xPath.split("\\[\\d+\\]");
		if(xPath.indexOf("[")>-1){
			if(pathArr.length>0){
				curPath=pathArr[0];
				tmpNode=node;

				for(int i=0;i<pathArr.length;i++){
					if(i<pathArr.length-1){
						if(!pathArr[i+1].equals("")){
							nodeNum=getArrNumInPath(xPath,curPath);
							//logger.debug(curPath);
							jp=jp.valueOf(pathArr[i]);
							// tmpNode=tmpNode.path(pathArr[i]);
							tmpNode=tmpNode.at(jp);//查询sql必须是去上级的第n个下级为属性
							//logger.debug(tmpNode);
							tmpNode=tmpNode.get(Integer.valueOf(nodeNum));//获取指定位置的数组节点项
							//logger.debug(pathArr[i+1]);
							//logger.debug(tmpNode);
						}
						curPath=curPath+"["+nodeNum+"]"+pathArr[i+1];//更新为下一个路径
						//queryPath=queryPath+pathArr[i+1];
					}

				}

				//如果最后的节点是一个数组，则还需追加处理
				if(xPath.substring(xPath.length()-1).equals("]")){
					String[] rightPathInfo=getRightPathInfo(xPath,splitor);
					String rightNode=rightPathInfo[0];
					rightNode=rightPathInfo[0].substring(0,rightNode.indexOf("["));//取不包含数组的部分
					nodeNum=xPath.substring(xPath.indexOf(curPath)+curPath.length()+1,xPath.length()-1);
					// tmpNode=tmpNode.path(curPath);//走到最后一个节点去掉中括号的
					tmpNode=tmpNode.findValue(rightNode);//取最后一个节点的值
					//jp=jp.valueOf(curPath);
					// tmpNode=tmpNode.at(jp);//如果最后一个数组了，则直接取第几个元素
					tmpNode=tmpNode.get(Integer.valueOf(nodeNum));//数组的话，就取第nodeNum个元素

				}else{//如果最后不是数组，则直接定位
					//jp=jp.valueOf(pathArr[pathArr.length-1]);
					//最后一段不是数组但可能有多层节点，因此循环遍历
					String lastPath=pathArr[pathArr.length-1];
					if(lastPath.indexOf(splitor)>-1){
						String[] lastPathNodeArr=lastPath.split(splitor);
						for(int i=0;i<lastPathNodeArr.length;i++){
							if(!lastPathNodeArr[i].equals("")){
								if(tmpNode!=null){
									tmpNode=tmpNode.findValue(lastPathNodeArr[i]);//pathArr[pathArr.length-1]);//查询sql必须是去上级的第n个下级为属性
								}
							}
						}
					}else{//没有多层，直接创建
						tmpNode=tmpNode.findValue(pathArr[pathArr.length-1]);
					}
				}

			}
		}
		else{//如果不存在数组，则直接选取
			jp=jp.valueOf(xPath);
			// tmpNode=tmpNode.path(pathArr[i]);
			tmpNode=node.at(jp);//查询sql必须是去上级的第n个下级为属性
		}


		return tmpNode;
	}

	/**
	 * 获取当前数组路径是第几个
	 * @param xPath 完整全路径
	 * @param curArrPath 当前数组路径
	 * @return 返回
	 */
	public static String getArrNumInPath(String xPath,String curArrPath){
		int begin=xPath.indexOf(curArrPath)+curArrPath.length()+1;
		int end=begin;
		for(int i=begin;i<xPath.length();i++){
			if(xPath.substring(i,i+1).equals("]")){
				end=i;
				break;
			}
		}
		String num=xPath.substring(begin,end);

		return num;

	}
	
	
	
	/*
	    List list = document.selectNodes( //foo/bar );
        Node node = document.selectSingleNode(//foo/bar/author);
        String name = node.valueOf( @name );
	 */

	public static void main(String[] args){

		//JsonToXmlTransUtil jt=new JsonToXmlTransUtil();
		//Element root=new Element("root");


		ArrayList<String[]> mappingList=new ArrayList<String[]>();
/*

		
		


		mappingList.add(new String[]{"/data/basic","/data/basic"});
		mappingList.add(new String[]{"/data/basic/{prodName}","/data/basic/{prodName}"});
		mappingList.add(new String[]{"/data/basic/{prodFee}","/data/basic/{fee}"});
//		mappingList.add(new String[]{"/data/items","/data/itemList/items"});
		mappingList.add(new String[]{"/data/itemList/items|item[]","/data/items|item[]"});
		mappingList.add(new String[]{"/data/itemList/items|item[]/{itemName}","/data/items|item[]/{name}"});
		mappingList.add(new String[]{"/data/itemList/items|item[]/{itemFee}","/data/items|item[]/{fee}"});
//		
		mappingList.add(new String[]{"/data/itemList/items|item[]/subItem|item[]","/data/items|item[]/items|item[]"});
		mappingList.add(new String[]{"/data/itemList/items|item[]/subItem|item[]/{subItemName}","/data/items|item[]/items|item[]/{name}"});
		mappingList.add(new String[]{"/data/itemList/items|item[]/subItem|item[]/{subItemFee}","/data/items|item[]/items|item[]/{fee}"});

--------------------------
		//子节点中存在属性和数组并列的情况
mappingList.add(new String[]{"/A","/A"});
mappingList.add(new String[]{"/A/{B}","/A/{B}"});
mappingList.add(new String[]{"/A/LIST","/A/LIST"});
mappingList.add(new String[]{"/A/LIST/{C}","/A/LIST/{C}"});
mappingList.add(new String[]{"/A/LIST/ARR|D[]","/A/LIST|D[]"});
mappingList.add(new String[]{"/A/LIST/ARR|D[]/{name}","/A/LIST|D[]/{name}"});
mappingList.add(new String[]{"/A/LIST/ARR|D[]/ARR|L[]","/A/LIST|D[]/|L[]"});
mappingList.add(new String[]{"/A/LIST/ARR|D[]/ARR|L[]/{name}","/A/LIST|D[]/|L[]/{name}"});

---------------
	mappingList.add(new String[]{"/data","/data"});
		mappingList.add(new String[]{"/data/basic","/data/basic"});
		mappingList.add(new String[]{"/data/basic/{prodName}","/data/basic/{prodName}"});
		mappingList.add(new String[]{"/data/basic/{prodFee}","/data/basic/{prodFee}"});
		
		mappingList.add(new String[]{"/data/itemList/items|item[]","/data/items|item[]"});
		mappingList.add(new String[]{"/data/itemList/items|item[]/{itemName}","/data/items|item[]/{name}"});
		mappingList.add(new String[]{"/data/itemList/items|item[]/{itemFee}","/data/items|item[]/{fee}"});
		//mappingList.add(new String[]{"/data/itemList/items|item[]/subItem","/data/items|item[]/subItem"});
		mappingList.add(new String[]{"/data/itemList/items|item[]/subItem","/data/items|item[]/subItem"});
		//mappingList.add(new String[]{"/data/itemList/items|item[]/subItem/{flag}","/data/items|item[]/subItem/{flag}"});
		mappingList.add(new String[]{"/data/itemList/items|item[]/subItem/items|item[]","/data/items|item[]/subItem/items|item[]"});
		mappingList.add(new String[]{"/data/itemList/items|item[]/subItem/items|item[]/{subItemName}","/data/items|item[]/subItem/items|item[]/{name}"});
		mappingList.add(new String[]{"/data/itemList/items|item[]/subItem/items|item[]/{subItemFee}","/data/items|item[]/subItem/items|item[]/{fee}"});


------------------------------------
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Header","/Envelope/Header"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Header/soa:HEADER","/Envelope/Header/HEADER"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Header/soa:HEADER/AUTH","/Envelope/Header/HEADER/AUTH"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Header/soa:HEADER/AUTH/{BRANCHNO}","/Envelope/Header/HEADER/AUTH/{BRANCHNO}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Header/soa:HEADER/AUTH/{TOKENID}","/Envelope/Header/HEADER/AUTH/{TOKENID}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Header/soa:HEADER/AUTH/{USERID}","/Envelope/Header/HEADER/AUTH/{USERID}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Header/soa:HEADER/{FROMSYS}","/Envelope/Header/HEADER/AUTH/{FROMSYS}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Header/soa:HEADER/{ORISYS}","/Envelope/Header/HEADER/AUTH/{ORISYS}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Header/soa:HEADER/ROUTE","/Envelope/Header/HEADER/AUTH/ROUTE"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Header/soa:HEADER/ROUTE/{BRANCHNO}","/Envelope/Header/HEADER/AUTH/ROUTE/{BRANCHNO}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Header/soa:HEADER/ROUTE/{DESTSYS}","/Envelope/Header/HEADER/AUTH/ROUTE/{DESTSYS}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Header/soa:HEADER/SECURITY","/Envelope/Header/HEADER/SECURITY"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Header/soa:HEADER/SECURITY/{SIGNATURE}","/Envelope/Header/HEADER/SECURITY/{SIGNATURE}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Header/soa:HEADER/{SVCCODE}","/Envelope/Header/HEADER/{SVCCODE}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Header/soa:HEADER/{SVCNAME}","/Envelope/Header/HEADER/{SVCNAME}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Header/soa:HEADER/{SVCVER}","/Envelope/Header/HEADER/{SVCVER}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Header/soa:HEADER/{TRANNO}","/Envelope/Header/HEADER/{TRANNO}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Header/soa:HEADER/{TRANTIME}","/Envelope/Header/HEADER/{TRANTIME}"});

		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body","/Envelope/Body"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl","/Envelope/Body/commitGroupAppl"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo","/Envelope/Body/commitGroupAppl/vo"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:AGREEMENT","/Envelope/Body/commitGroupAppl/vo/AGREEMENT"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:AGREEMENT/{xsd:INSURDUR}","/Envelope/Body/commitGroupAppl/vo/AGREEMENT/{INSURDUR}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:AGREEMENT/PRODUCTS","/Envelope/Body/commitGroupAppl/vo/AGREEMENT/PRODUCTS"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:AGREEMENT/PRODUCTS/{PRDCOUNT}","/Envelope/Body/commitGroupAppl/vo/AGREEMENT/PRODUCTS/{PRDCOUNT}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:AGREEMENT/PRODUCTS/PRODUCT","/Envelope/Body/commitGroupAppl/vo/AGREEMENT/PRODUCTS/PRODUCT"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:AGREEMENT/PRODUCTS/PRODUCT/POLICYS","/Envelope/Body/commitGroupAppl/vo/AGREEMENT/PRODUCTS/PRODUCT/POLICYS"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:AGREEMENT/PRODUCTS/PRODUCT/POLICYS/{POLCOUNT}","/Envelope/Body/commitGroupAppl/vo/AGREEMENT/PRODUCTS/PRODUCT/POLICYS/{POLCOUNT}"});

		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:AGREEMENT/PRODUCTS/PRODUCT/POLICYS|POLICY[]","/Envelope/Body/commitGroupAppl/vo/AGREEMENT/PRODUCTS/PRODUCT/POLICYS/POLICYLIST|POLICY[]"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:AGREEMENT/PRODUCTS/PRODUCT/POLICYS|POLICY[]/{POLAMNT}","/Envelope/Body/commitGroupAppl/vo/AGREEMENT/PRODUCTS/PRODUCT/POLICYS/POLICYLIST|POLICY[]/{POLAMNT}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:AGREEMENT/PRODUCTS/PRODUCT/POLICYS|POLICY[]/{POLCODE}","/Envelope/Body/commitGroupAppl/vo/AGREEMENT/PRODUCTS/PRODUCT/POLICYS/POLICYLIST|POLICY[]/{POLCODE}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:AGREEMENT/PRODUCTS/PRODUCT/POLICYS/{PRDCODE}","/Envelope/Body/commitGroupAppl/vo/AGREEMENT/PRODUCTS/PRODUCT/POLICYS/{PRDCODE}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:AGREEMENT/{xsd:VALIDDATE}","/Envelope/Body/commitGroupAppl/vo/AGREEMENT/{VALIDDATE}"});

		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:APPLICANT","/Envelope/Body/commitGroupAppl/vo/APPLICANT"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:APPLICANT/{xsd:APPTYPE}","/Envelope/Body/commitGroupAppl/vo/APPLICANT/{APPTYPE}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:APPLICANT/{xsd:GRPIDNO}","/Envelope/Body/commitGroupAppl/vo/APPLICANT/{GRPIDNO}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:APPLICANT/{xsd:GRPIDTYPE}","/Envelope/Body/commitGroupAppl/vo/APPLICANT/{GRPIDTYPE}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:APPLICANT/{xsd:GRPNAME}","/Envelope/Body/commitGroupAppl/vo/APPLICANT/{GRPNAME}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:APPLICANT/{OCCCLASSCODE}","/Envelope/Body/commitGroupAppl/vo/APPLICANT/{OCCCLASSCODE}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:APPLICANT/{xsd:PSNGENDER}","/Envelope/Body/commitGroupAppl/vo/APPLICANT/{PSNGENDER}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:APPLICANT/{xsd:PSNBIRTH}","/Envelope/Body/commitGroupAppl/vo/APPLICANT/{PSNBIRTH}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:APPLICANT/{CONTACTPSN}","/Envelope/Body/commitGroupAppl/vo/APPLICANT/{CONTACTPSN}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:APPLICANT/{ONTACTPSNIDNO}","/Envelope/Body/commitGroupAppl/vo/APPLICANT/{ONTACTPSNIDNO}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:APPLICANT/{PSNOCCTYPE}","/Envelope/Body/commitGroupAppl/vo/APPLICANT/{PSNOCCTYPE}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:APPLICANT/{PSNOCCCLASSCODE}","/Envelope/Body/commitGroupAppl/vo/APPLICANT/{PSNOCCCLASSCODE}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:APPLICANT/{PSNEMAILADDRESS}","/Envelope/Body/commitGroupAppl/vo/APPLICANT/{PSNEMAILADDRESS}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:APPLICANT/{xsd:APPEMAIL}","/Envelope/Body/commitGroupAppl/vo/APPLICANT/{APPEMAIL}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:APPLICANT/{CONTRACTMOBIL}","/Envelope/Body/commitGroupAppl/vo/APPLICANT/{CONTRACTMOBIL}"});

		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:MANAGE","/Envelope/Body/commitGroupAppl/vo/MANAGE"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:MANAGE/{DISCOUNT}","/Envelope/Body/commitGroupAppl/vo/MANAGE/{DISCOUNT}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:MANAGE/{xsd:INSUREPEOPLES}","/Envelope/Body/commitGroupAppl/vo/MANAGE/{INSUREPEOPLES}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:MANAGE/{xsd:AGENTNO}","/Envelope/Body/commitGroupAppl/vo/MANAGE/{AGENTNO}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:MANAGE/{xsd:APPLDATE}","/Envelope/Body/commitGroupAppl/vo/MANAGE/{APPLDATE}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:MANAGE/{xsd:SALESBRANCHNO}","/Envelope/Body/commitGroupAppl/vo/MANAGE/{SALESBRANCHNO}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:MANAGE/{xsd:SALESCHANNEL}","/Envelope/Body/commitGroupAppl/vo/MANAGE/{SALESCHANNEL}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:MANAGE/{xsd:SALESCODE}","/Envelope/Body/commitGroupAppl/vo/MANAGE/{SALESCODE}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:MANAGE/{xsd:APPLTYPE}","/Envelope/Body/commitGroupAppl/vo/MANAGE/{APPLTYPE}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:MANAGE/{xsd:LISTFLAG}","/Envelope/Body/commitGroupAppl/vo/MANAGE/{LISTFLAG}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:MANAGE/{xsd:DOCNO}","/Envelope/Body/commitGroupAppl/vo/MANAGE/{DOCNO}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:MANAGE/{xsd:ORDERNO}","/Envelope/Body/commitGroupAppl/vo/MANAGE/{ORDERNO}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:MANAGE/{APPLNO}","/Envelope/Body/commitGroupAppl/vo/MANAGE/{APPLNO}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:MANAGE/{xsd:STORECODE}","/Envelope/Body/commitGroupAppl/vo/MANAGE/{STORECODE}"});

		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/IO_IPSN_GRP_INFO","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/IO_IPSN_GRP_INFO/{grp_info_flag}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/{grp_info_flag}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/IO_IPSN_GRP_INFO/{ipsn_grp_num}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/{ipsn_grp_num}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{pol_code}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{pol_code}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{ipsn_grp}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{ipsn_grp}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{ipsn_grp_name}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{ipsn_grp_name}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{ipsn_num}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{ipsn_num}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{sex}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{sex}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{age_from}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{age_from}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{age_to}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{age_to}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{occ_class_no}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{occ_class_no}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{sum_face_amnt}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{sum_face_amnt}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{sum_premium}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{sum_premium}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{face_amnt}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{face_amnt}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{premium}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{premium}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{prem_rate}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{prem_rate}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{prem_discount}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{prem_discount}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{ipsn_sss}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{ipsn_sss}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{ipsn_ssc}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{ipsn_ssc}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{ipsn_type}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{ipsn_type}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{deduction_ho}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{deduction_ho}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{deduction_hh}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{deduction_hh}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{cpnst_pct_ho}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{cpnst_pct_ho}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{cpnst_pct_hh}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{cpnst_pct_hh}"});
		

		-----------------------------
		mappingList.add(new String[]{"/Envelope/Header","/Envelope/Header"});
		mappingList.add(new String[]{"/Envelope/Header/HEADER","/Envelope/Header/HEADER"});
		mappingList.add(new String[]{"/Envelope/Header/HEADER/AUTH","/Envelope/Header/HEADER/AUTH"});
		mappingList.add(new String[]{"/Envelope/Header/HEADER/AUTH/{BRANCHNO}","/Envelope/Header/HEADER/AUTH/{BRANCHNO}"});
		mappingList.add(new String[]{"/Envelope/Header/HEADER/AUTH/{TOKENID}","/Envelope/Header/HEADER/AUTH/{TOKENID}"});
		mappingList.add(new String[]{"/Envelope/Header/HEADER/AUTH/{USERID}","/Envelope/Header/HEADER/AUTH/{USERID}"});
		mappingList.add(new String[]{"/Envelope/Header/HEADER/{FROMSYS}","/Envelope/Header/HEADER/AUTH/{FROMSYS}"});
		mappingList.add(new String[]{"/Envelope/Header/HEADER/{ORISYS}","/Envelope/Header/HEADER/AUTH/{ORISYS}"});
		mappingList.add(new String[]{"/Envelope/Header/HEADER/ROUTE","/Envelope/Header/HEADER/AUTH/ROUTE"});
		mappingList.add(new String[]{"/Envelope/Header/HEADER/ROUTE/{BRANCHNO}","/Envelope/Header/HEADER/AUTH/ROUTE/{BRANCHNO}"});
		mappingList.add(new String[]{"/Envelope/Header/HEADER/ROUTE/{DESTSYS}","/Envelope/Header/HEADER/AUTH/ROUTE/{DESTSYS}"});
		mappingList.add(new String[]{"/Envelope/Header/HEADER/SECURITY","/Envelope/Header/HEADER/SECURITY"});
		mappingList.add(new String[]{"/Envelope/Header/HEADER/SECURITY/{SIGNATURE}","/Envelope/Header/HEADER/SECURITY/{SIGNATURE}"});
		mappingList.add(new String[]{"/Envelope/Header/HEADER/{SVCCODE}","/Envelope/Header/HEADER/{SVCCODE}"});
		mappingList.add(new String[]{"/Envelope/Header/HEADER/{SVCNAME}","/Envelope/Header/HEADER/{SVCNAME}"});
		mappingList.add(new String[]{"/Envelope/Header/HEADER/{SVCVER}","/Envelope/Header/HEADER/{SVCVER}"});
		mappingList.add(new String[]{"/Envelope/Header/HEADER/{TRANNO}","/Envelope/Header/HEADER/{TRANNO}"});
		mappingList.add(new String[]{"/Envelope/Header/HEADER/{TRANTIME}","/Envelope/Header/HEADER/{TRANTIME}"});

		mappingList.add(new String[]{"/Envelope/Body","/Envelope/Body"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl","/Envelope/Body/commitGroupAppl"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo","/Envelope/Body/commitGroupAppl/vo"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/AGREEMENT","/Envelope/Body/commitGroupAppl/vo/AGREEMENT"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/AGREEMENT/{INSURDUR}","/Envelope/Body/commitGroupAppl/vo/AGREEMENT/{INSURDUR}"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/AGREEMENT/PRODUCTS","/Envelope/Body/commitGroupAppl/vo/AGREEMENT/PRODUCTS"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/AGREEMENT/PRODUCTS/{PRDCOUNT}","/Envelope/Body/commitGroupAppl/vo/AGREEMENT/PRODUCTS/{PRDCOUNT}"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/AGREEMENT/PRODUCTS/PRODUCT","/Envelope/Body/commitGroupAppl/vo/AGREEMENT/PRODUCTS/PRODUCT"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/AGREEMENT/PRODUCTS/PRODUCT/POLICYS","/Envelope/Body/commitGroupAppl/vo/AGREEMENT/PRODUCTS/PRODUCT/POLICYS"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/AGREEMENT/PRODUCTS/PRODUCT/POLICYS/{POLCOUNT}","/Envelope/Body/commitGroupAppl/vo/AGREEMENT/PRODUCTS/PRODUCT/POLICYS/{POLCOUNT}"});

		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/AGREEMENT/PRODUCTS/PRODUCT/POLICYS|POLICY[]","/Envelope/Body/commitGroupAppl/vo/AGREEMENT/PRODUCTS/PRODUCT/POLICYS/POLICYLIST|POLICY[]"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/AGREEMENT/PRODUCTS/PRODUCT/POLICYS|POLICY[]/{POLAMNT}","/Envelope/Body/commitGroupAppl/vo/AGREEMENT/PRODUCTS/PRODUCT/POLICYS/POLICYLIST|POLICY[]/{POLAMNT}"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/AGREEMENT/PRODUCTS/PRODUCT/POLICYS|POLICY[]/{POLCODE}","/Envelope/Body/commitGroupAppl/vo/AGREEMENT/PRODUCTS/PRODUCT/POLICYS/POLICYLIST|POLICY[]/{POLCODE}"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/AGREEMENT/PRODUCTS/PRODUCT/POLICYS/{PRDCODE}","/Envelope/Body/commitGroupAppl/vo/AGREEMENT/PRODUCTS/PRODUCT/POLICYS/{PRDCODE}"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/AGREEMENT/{VALIDDATE}","/Envelope/Body/commitGroupAppl/vo/AGREEMENT/{VALIDDATE}"});

		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/APPLICANT","/Envelope/Body/commitGroupAppl/vo/APPLICANT"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/APPLICANT/{APPTYPE}","/Envelope/Body/commitGroupAppl/vo/APPLICANT/{APPTYPE}"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/APPLICANT/{GRPIDNO}","/Envelope/Body/commitGroupAppl/vo/APPLICANT/{GRPIDNO}"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/APPLICANT/{GRPIDTYPE}","/Envelope/Body/commitGroupAppl/vo/APPLICANT/{GRPIDTYPE}"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/APPLICANT/{GRPNAME}","/Envelope/Body/commitGroupAppl/vo/APPLICANT/{GRPNAME}"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/APPLICANT/{OCCCLASSCODE}","/Envelope/Body/commitGroupAppl/vo/APPLICANT/{OCCCLASSCODE}"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/APPLICANT/{PSNGENDER}","/Envelope/Body/commitGroupAppl/vo/APPLICANT/{PSNGENDER}"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/APPLICANT/{PSNBIRTH}","/Envelope/Body/commitGroupAppl/vo/APPLICANT/{PSNBIRTH}"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/APPLICANT/{CONTACTPSN}","/Envelope/Body/commitGroupAppl/vo/APPLICANT/{CONTACTPSN}"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/APPLICANT/{ONTACTPSNIDNO}","/Envelope/Body/commitGroupAppl/vo/APPLICANT/{ONTACTPSNIDNO}"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/APPLICANT/{PSNOCCTYPE}","/Envelope/Body/commitGroupAppl/vo/APPLICANT/{PSNOCCTYPE}"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/APPLICANT/{PSNOCCCLASSCODE}","/Envelope/Body/commitGroupAppl/vo/APPLICANT/{PSNOCCCLASSCODE}"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/APPLICANT/{PSNEMAILADDRESS}","/Envelope/Body/commitGroupAppl/vo/APPLICANT/{PSNEMAILADDRESS}"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/APPLICANT/{APPEMAIL}","/Envelope/Body/commitGroupAppl/vo/APPLICANT/{APPEMAIL}"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/APPLICANT/{CONTRACTMOBIL}","/Envelope/Body/commitGroupAppl/vo/APPLICANT/{CONTRACTMOBIL}"});

		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/MANAGE","/Envelope/Body/commitGroupAppl/vo/MANAGE"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/MANAGE/{DISCOUNT}","/Envelope/Body/commitGroupAppl/vo/MANAGE/{DISCOUNT}"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/MANAGE/{INSUREPEOPLES}","/Envelope/Body/commitGroupAppl/vo/MANAGE/{INSUREPEOPLES}"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/MANAGE/{AGENTNO}","/Envelope/Body/commitGroupAppl/vo/MANAGE/{AGENTNO}"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/MANAGE/{APPLDATE}","/Envelope/Body/commitGroupAppl/vo/MANAGE/{APPLDATE}"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/MANAGE/{SALESBRANCHNO}","/Envelope/Body/commitGroupAppl/vo/MANAGE/{SALESBRANCHNO}"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/MANAGE/{SALESCHANNEL}","/Envelope/Body/commitGroupAppl/vo/MANAGE/{SALESCHANNEL}"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/MANAGE/{SALESCODE}","/Envelope/Body/commitGroupAppl/vo/MANAGE/{SALESCODE}"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/MANAGE/{APPLTYPE}","/Envelope/Body/commitGroupAppl/vo/MANAGE/{APPLTYPE}"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/MANAGE/{LISTFLAG}","/Envelope/Body/commitGroupAppl/vo/MANAGE/{LISTFLAG}"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/MANAGE/{DOCNO}","/Envelope/Body/commitGroupAppl/vo/MANAGE/{DOCNO}"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/MANAGE/{ORDERNO}","/Envelope/Body/commitGroupAppl/vo/MANAGE/{ORDERNO}"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/MANAGE/{APPLNO}","/Envelope/Body/commitGroupAppl/vo/MANAGE/{APPLNO}"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/MANAGE/{STORECODE}","/Envelope/Body/commitGroupAppl/vo/MANAGE/{STORECODE}"});

		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/{grp_info_flag}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/{grp_info_flag}"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/{ipsn_grp_num}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/{ipsn_grp_num}"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{pol_code}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{pol_code}"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{ipsn_grp}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{ipsn_grp}"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{ipsn_grp_name}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{ipsn_grp_name}"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{ipsn_num}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{ipsn_num}"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{sex}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{sex}"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{age_from}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{age_from}"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{age_to}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{age_to}"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{occ_class_no}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{occ_class_no}"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{sum_face_amnt}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{sum_face_amnt}"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{sum_premium}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{sum_premium}"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{face_amnt}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{face_amnt}"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{premium}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{premium}"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{prem_rate}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{prem_rate}"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{prem_discount}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{prem_discount}"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{ipsn_sss}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{ipsn_sss}"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{ipsn_ssc}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{ipsn_ssc}"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{ipsn_type}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{ipsn_type}"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{deduction_ho}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{deduction_ho}"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{deduction_hh}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{deduction_hh}"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{cpnst_pct_ho}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{cpnst_pct_ho}"});
		mappingList.add(new String[]{"/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{cpnst_pct_hh}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{cpnst_pct_hh}"});
		
		ArrayList<String[]> reverseMappingList=new ArrayList<String[]>();
		for(int i=0;i<mappingList.size();i++){
			String[] oneMap=new String[2];
			oneMap[0]=mappingList.get(i)[1];
			oneMap[1]=mappingList.get(i)[0];
			reverseMappingList.add(oneMap);
		}


 *
 */
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Header","/Envelope/Header"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Header/soa:HEADER","/Envelope/Header/HEADER"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Header/soa:HEADER/AUTH","/Envelope/Header/HEADER/AUTH"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Header/soa:HEADER/AUTH/{BRANCHNO}","/Envelope/Header/HEADER/AUTH/{BRANCHNO}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Header/soa:HEADER/AUTH/{TOKENID}","/Envelope/Header/HEADER/AUTH/{TOKENID}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Header/soa:HEADER/AUTH/{USERID}","/Envelope/Header/HEADER/AUTH/{USERID}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Header/soa:HEADER/{FROMSYS}","/Envelope/Header/HEADER/AUTH/{FROMSYS}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Header/soa:HEADER/{ORISYS}","/Envelope/Header/HEADER/AUTH/{ORISYS}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Header/soa:HEADER/ROUTE","/Envelope/Header/HEADER/AUTH/ROUTE"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Header/soa:HEADER/ROUTE/{BRANCHNO}","/Envelope/Header/HEADER/AUTH/ROUTE/{BRANCHNO}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Header/soa:HEADER/ROUTE/{DESTSYS}","/Envelope/Header/HEADER/AUTH/ROUTE/{DESTSYS}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Header/soa:HEADER/SECURITY","/Envelope/Header/HEADER/SECURITY"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Header/soa:HEADER/SECURITY/{SIGNATURE}","/Envelope/Header/HEADER/SECURITY/{SIGNATURE}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Header/soa:HEADER/{SVCCODE}","/Envelope/Header/HEADER/{SVCCODE}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Header/soa:HEADER/{SVCNAME}","/Envelope/Header/HEADER/{SVCNAME}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Header/soa:HEADER/{SVCVER}","/Envelope/Header/HEADER/{SVCVER}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Header/soa:HEADER/{TRANNO}","/Envelope/Header/HEADER/{TRANNO}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Header/soa:HEADER/{TRANTIME}","/Envelope/Header/HEADER/{TRANTIME}"});

		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body","/Envelope/Body"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl","/Envelope/Body/commitGroupAppl"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo","/Envelope/Body/commitGroupAppl/vo"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:AGREEMENT","/Envelope/Body/commitGroupAppl/vo/AGREEMENT"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:AGREEMENT/{xsd:INSURDUR}","/Envelope/Body/commitGroupAppl/vo/AGREEMENT/{INSURDUR}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:AGREEMENT/PRODUCTS","/Envelope/Body/commitGroupAppl/vo/AGREEMENT/PRODUCTS"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:AGREEMENT/PRODUCTS/{PRDCOUNT}","/Envelope/Body/commitGroupAppl/vo/AGREEMENT/PRODUCTS/{PRDCOUNT}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:AGREEMENT/PRODUCTS/PRODUCT","/Envelope/Body/commitGroupAppl/vo/AGREEMENT/PRODUCTS/PRODUCT"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:AGREEMENT/PRODUCTS/PRODUCT/POLICYS","/Envelope/Body/commitGroupAppl/vo/AGREEMENT/PRODUCTS/PRODUCT/POLICYS"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:AGREEMENT/PRODUCTS/PRODUCT/POLICYS/{POLCOUNT}","/Envelope/Body/commitGroupAppl/vo/AGREEMENT/PRODUCTS/PRODUCT/POLICYS/{POLCOUNT}"});

		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:AGREEMENT/PRODUCTS/PRODUCT/POLICYS|POLICY[]","/Envelope/Body/commitGroupAppl/vo/AGREEMENT/PRODUCTS/PRODUCT/POLICYS/POLICYLIST|POLICY[]"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:AGREEMENT/PRODUCTS/PRODUCT/POLICYS|POLICY[]/{POLAMNT}","/Envelope/Body/commitGroupAppl/vo/AGREEMENT/PRODUCTS/PRODUCT/POLICYS/POLICYLIST|POLICY[]/{POLAMNT}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:AGREEMENT/PRODUCTS/PRODUCT/POLICYS|POLICY[]/{POLCODE}","/Envelope/Body/commitGroupAppl/vo/AGREEMENT/PRODUCTS/PRODUCT/POLICYS/POLICYLIST|POLICY[]/{POLCODE}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:AGREEMENT/PRODUCTS/PRODUCT/POLICYS/{PRDCODE}","/Envelope/Body/commitGroupAppl/vo/AGREEMENT/PRODUCTS/PRODUCT/POLICYS/{PRDCODE}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:AGREEMENT/{xsd:VALIDDATE}","/Envelope/Body/commitGroupAppl/vo/AGREEMENT/{VALIDDATE}"});

		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:APPLICANT","/Envelope/Body/commitGroupAppl/vo/APPLICANT"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:APPLICANT/{xsd:APPTYPE}","/Envelope/Body/commitGroupAppl/vo/APPLICANT/{APPTYPE}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:APPLICANT/{xsd:GRPIDNO}","/Envelope/Body/commitGroupAppl/vo/APPLICANT/{GRPIDNO}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:APPLICANT/{xsd:GRPIDTYPE}","/Envelope/Body/commitGroupAppl/vo/APPLICANT/{GRPIDTYPE}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:APPLICANT/{xsd:GRPNAME}","/Envelope/Body/commitGroupAppl/vo/APPLICANT/{GRPNAME}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:APPLICANT/{OCCCLASSCODE}","/Envelope/Body/commitGroupAppl/vo/APPLICANT/{OCCCLASSCODE}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:APPLICANT/{xsd:PSNGENDER}","/Envelope/Body/commitGroupAppl/vo/APPLICANT/{PSNGENDER}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:APPLICANT/{xsd:PSNBIRTH}","/Envelope/Body/commitGroupAppl/vo/APPLICANT/{PSNBIRTH}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:APPLICANT/{CONTACTPSN}","/Envelope/Body/commitGroupAppl/vo/APPLICANT/{CONTACTPSN}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:APPLICANT/{ONTACTPSNIDNO}","/Envelope/Body/commitGroupAppl/vo/APPLICANT/{ONTACTPSNIDNO}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:APPLICANT/{PSNOCCTYPE}","/Envelope/Body/commitGroupAppl/vo/APPLICANT/{PSNOCCTYPE}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:APPLICANT/{PSNOCCCLASSCODE}","/Envelope/Body/commitGroupAppl/vo/APPLICANT/{PSNOCCCLASSCODE}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:APPLICANT/{PSNEMAILADDRESS}","/Envelope/Body/commitGroupAppl/vo/APPLICANT/{PSNEMAILADDRESS}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:APPLICANT/{xsd:APPEMAIL}","/Envelope/Body/commitGroupAppl/vo/APPLICANT/{APPEMAIL}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:APPLICANT/{CONTRACTMOBIL}","/Envelope/Body/commitGroupAppl/vo/APPLICANT/{CONTRACTMOBIL}"});

		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:MANAGE","/Envelope/Body/commitGroupAppl/vo/MANAGE"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:MANAGE/{DISCOUNT}","/Envelope/Body/commitGroupAppl/vo/MANAGE/{DISCOUNT}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:MANAGE/{xsd:INSUREPEOPLES}","/Envelope/Body/commitGroupAppl/vo/MANAGE/{INSUREPEOPLES}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:MANAGE/{xsd:AGENTNO}","/Envelope/Body/commitGroupAppl/vo/MANAGE/{AGENTNO}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:MANAGE/{xsd:APPLDATE}","/Envelope/Body/commitGroupAppl/vo/MANAGE/{APPLDATE}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:MANAGE/{xsd:SALESBRANCHNO}","/Envelope/Body/commitGroupAppl/vo/MANAGE/{SALESBRANCHNO}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:MANAGE/{xsd:SALESCHANNEL}","/Envelope/Body/commitGroupAppl/vo/MANAGE/{SALESCHANNEL}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:MANAGE/{xsd:SALESCODE}","/Envelope/Body/commitGroupAppl/vo/MANAGE/{SALESCODE}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:MANAGE/{xsd:APPLTYPE}","/Envelope/Body/commitGroupAppl/vo/MANAGE/{APPLTYPE}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:MANAGE/{xsd:LISTFLAG}","/Envelope/Body/commitGroupAppl/vo/MANAGE/{LISTFLAG}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:MANAGE/{xsd:DOCNO}","/Envelope/Body/commitGroupAppl/vo/MANAGE/{DOCNO}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:MANAGE/{xsd:ORDERNO}","/Envelope/Body/commitGroupAppl/vo/MANAGE/{ORDERNO}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:MANAGE/{APPLNO}","/Envelope/Body/commitGroupAppl/vo/MANAGE/{APPLNO}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/xsd:MANAGE/{xsd:STORECODE}","/Envelope/Body/commitGroupAppl/vo/MANAGE/{STORECODE}"});

		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/IO_IPSN_GRP_INFO","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/IO_IPSN_GRP_INFO/{grp_info_flag}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/{grp_info_flag}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/IO_IPSN_GRP_INFO/{ipsn_grp_num}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/{ipsn_grp_num}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{pol_code}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{pol_code}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{ipsn_grp}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{ipsn_grp}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{ipsn_grp_name}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{ipsn_grp_name}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{ipsn_num}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{ipsn_num}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{sex}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{sex}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{age_from}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{age_from}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{age_to}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{age_to}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{occ_class_no}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{occ_class_no}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{sum_face_amnt}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{sum_face_amnt}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{sum_premium}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{sum_premium}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{face_amnt}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{face_amnt}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{premium}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{premium}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{prem_rate}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{prem_rate}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{prem_discount}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{prem_discount}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{ipsn_sss}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{ipsn_sss}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{ipsn_ssc}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{ipsn_ssc}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{ipsn_type}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{ipsn_type}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{deduction_ho}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{deduction_ho}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{deduction_hh}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{deduction_hh}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{cpnst_pct_ho}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{cpnst_pct_ho}"});
		mappingList.add(new String[]{"/soapenv:Envelope/soapenv:Body/soa:commitGroupAppl/soa:vo/IO_IPSN_GRP_INFO/|t_ipsn_grp[]/{cpnst_pct_hh}","/Envelope/Body/commitGroupAppl/vo/IO_IPSN_GRP_INFO/t_ipsn_grp_list|t_ipsn_grp[]/{cpnst_pct_hh}"});


		ArrayList<String[]> reverseMappingList=new ArrayList<String[]>();
		for(int i=0;i<mappingList.size();i++){
			String[] oneMap=new String[2];
			oneMap[0]=mappingList.get(i)[1];
			oneMap[1]=mappingList.get(i)[0];
			reverseMappingList.add(oneMap);
		}

		try{

			//JsonToXmlTransUtil jxt=new JsonToXmlTransUtil("{\"data\": {\"basic\": {\"prodName\": \"康悦C\",\"prodFee\": \"5.00\"},\"itemList\": {\"items\": [{\"item\": {\"itemName\": \"责任1\",\"itemFee\": \"5.00\",\"subItem\": {}}}, {\"item\": {\"itemName\": \"责任2\",\"itemFee\": \"4.00\"}}, {\"item\": {\"itemName\": \"责任3\",\"itemFee\": \"3.00\",\"subItem\": {\"items\": [{\"item\": {\"subItemName\": \"子责任1\",\"subItemFee\": \"0.01\"}}, {\"item\": {\"subItemName\": \"子责任2\",\"subItemFee\": \"0.02\"}}]}}}]}}}");
			//子节点中存在数组和属性并列的情况
			//JsonToXmlTransUtil jxt=new JsonToXmlTransUtil("{\"A\":{\"B\":\"bbbb\",\"LIST\":{\"C\":\"cccccc\",\"ARR\":[{\"D\":{\"name\":\"d1\"}},{\"D\":{\"name\":\"d3\",\"ARR\":[{\"L\":{\"name\":\"lll1\"}},{\"L\":{\"name\":\"lll2\"}},{\"L\":{\"name\":\"lll3\"}}]}}]}}}");
			//
			JsonToXmlTransUtil jxt=new JsonToXmlTransUtil("{\"Envelope\": {\"Header\": {\"HEADER\": {\"AUTH\": {\"BRANCHNO\": \"\",\"TOKENID\": \"\",\"USERID\": \"\",\"FROMSYS\": \"\",\"ORISYS\": \"ASLSS\",\"ROUTE\": {\"BRANCHNO\": \"000000\",\"DESTSYS\": \"ASLSS\"}},\"SECURITY\": {\"SIGNATURE\": \"\"},\"SVCCODE\": \"\",\"SVCNAME\": \"CommitGroupAppl\",\"SVCVER\": \"1.0\",\"TRANNO\": \"2012032812303000001\",\"TRANTIME\": \"2014-03-28 19:03:02\"}},\"Body\": {\"commitGroupAppl\": {\"vo\": {\"AGREEMENT\": {\"INSURDUR\": \"D7\",\"PRODUCTS\": {\"PRDCOUNT\": \"1\",\"PRODUCT\": {\"POLICYS\": {\"POLCOUNT\": \"1\",\"POLICYLIST\": [{\"POLICY\": {\"POLAMNT\": \"10000\",\"POLCODE\": \"850-4\"}}, {\"POLICY\": {\"POLAMNT\": \"100\",\"POLCODE\": \"869-1\"}}]}}},\"VALIDDATE\": \"2019-03-02\"},\"APPLICANT\": {\"APPTYPE\": \"2\",\"GRPIDNO\": \"123456789012345678\",\"GRPIDTYPE\": \"T\",\"GRPNAME\": \"团体单位团体单位\",\"OCCCLASSCODE\": \"01\",\"PSNGENDER\": \"M\",\"PSNBIRTH\": \"\",\"CONTACTPSN\": \"某某单位联系人\",\"ONTACTPSNIDNO\": \"11010119800101007X\",\"PSNOCCTYPE\": \"01\",\"PSNOCCCLASSCODE\": \"010101\",\"PSNEMAILADDRESS\": \"1234@qq.com\",\"APPEMAIL\": \"825558@qq.com\",\"CONTRACTMOBIL\": \"0086-13160883011\"},\"MANAGE\": {\"DISCOUNT\": \"0.5\",\"INSUREPEOPLES\": \"3\",\"AGENTNO\": \"11031201019000\",\"APPLDATE\": \"2019-03-01\",\"SALESBRANCHNO\": \"120101\",\"SALESCHANNEL\": \"PA\",\"SALESCODE\": \"1017\",\"APPLTYPE\": \"0\",\"LISTFLAG\": \"M\",\"DOCNO\": \"\",\"ORDERNO\": \"\",\"APPLNO\": \"\",\"STORECODE\": \"JCJK\"},\"IO_IPSN_GRP_INFO\": {\"grp_info_flag\": \"0\",\"ipsn_grp_num\": \"2\",\"t_ipsn_grp_list\": [{\"t_ipsn_grp\": {\"pol_code\": \"850\",\"ipsn_grp\": \"1\",\"ipsn_grp_name\": \"被保人分组1\",\"ipsn_num\": \"2\",\"sex\": \"M\",\"age_from\": \"19\",\"age_to\": \"29\",\"occ_class_no\": \"1\",\"sum_face_amnt\": \"500\",\"sum_premium\": \"\",\"face_amnt\": \"\",\"premium\": \"\",\"prem_rate\": \"\",\"prem_discount\": \"11\",\"ipsn_sss\": \"22\",\"ipsn_ssc\": \"33\",\"ipsn_type\": \"44\",\"deduction_ho\": \"55\",\"deduction_hh\": \"66\",\"cpnst_pct_ho\": \"77\",\"cpnst_pct_hh\": \"88\"}}, {\"t_ipsn_grp\": {\"pol_code\": \"869\",\"ipsn_grp\": \"1\",\"ipsn_grp_name\": \"被保人分组1\",\"ipsn_num\": \"2\",\"sex\": \"F\",\"age_from\": \"39\",\"age_to\": \"49\",\"occ_class_no\": \"1\",\"sum_face_amnt\": \"\",\"sum_premium\": \"\",\"face_amnt\": \"\",\"premium\": \"\",\"prem_rate\": \"\",\"prem_discount\": \"11\",\"ipsn_sss\": \"22\",\"ipsn_ssc\": \"33\",\"ipsn_type\": \"44\",\"deduction_ho\": \"55\",\"deduction_hh\": \"66\",\"cpnst_pct_ho\": \"77\",\"cpnst_pct_hh\": \"88\"}}, {\"t_ipsn_grp\": {\"pol_code\": \"869\",\"ipsn_grp\": \"2\",\"ipsn_grp_name\": \"被保人分组2\",\"ipsn_num\": \"1\",\"sex\": \"F\",\"age_from\": \"39\",\"age_to\": \"49\",\"occ_class_no\": \"1\",\"sum_face_amnt\": \"\",\"sum_premium\": \"\",\"face_amnt\": \"\",\"premium\": \"\",\"prem_rate\": \"\",\"prem_discount\": \"11\",\"ipsn_sss\": \"22\",\"ipsn_ssc\": \"33\",\"ipsn_type\": \"44\",\"deduction_ho\": \"55\",\"deduction_hh\": \"66\",\"cpnst_pct_ho\": \"77\",\"cpnst_pct_hh\": \"88\"}}]}}}}}}");

			//public void createNode(ObjectNode parentNode,String nodePath,String pathSplitor)
			//jxt.getDoc().addElement("data");
			HashMap<String,Namespace> nameSpaces=new HashMap<String,Namespace>();
			nameSpaces.put("soapenv", new Namespace("soapenv", "http://schemas.xmlsoap.org/soap/envelope/"));
			nameSpaces.put("soa", new Namespace("soa", "http://www.e-chinalife.com/soa/"));
			nameSpaces.put("xsd", new Namespace("xsd", "http://cancelapplicationvo.server.soa.aslss.chinalife.com/xsd"));
			jxt.setNs(nameSpaces);

			Element el=jxt.getDoc().getRootElement();
			HashMap pathVisited=new HashMap();

			pathVisited.put("tgtParentPath", "");
			jxt.deepTransForm(reverseMappingList, "", el, pathVisited);
			//jxt.deepTransForm(mappingList, "", el, pathVisited);

			logger.debug(jxt.getDoc().asXML());





			//logger.debug(getRelativePath("/data/ta","/"));
			// /a/b/c
			//Element aa=jxt.getDoc().addElement("a");
			//Element bb=aa.addElement("b");
			//Element cc=bb.addElement("c");

			//if(bb.matches("/a/b/c")){//matches,只是检测当前节点的正确位置，不是检测当前节点下面有多少
			//	logger.debug(bb.asXML());
			//}

			//logger.debug(bb.selectSingleNode("/a/b/c").toString());//这个方法可以按照相对路径检索也可以按照完整的绝对路径检索


//		//对于xml需要默认生成一个根
//		Element el=jxt.getDoc().addElement("data");
//		jxt.deepCreateNode(el,  "/basic/items/item", jxt.getPathSpator());	
//		logger.debug(el.getPath());//getPath可以获取到该节点自身的完整路径
//		
//		logger.debug(el.asXML());
//		logger.debug(jxt.getDoc().asXML());
//		
//		String p="/data/itemList/items[2]/item/subItem[1]/item";
//		logger.debug(selectNodes(jxt.getJsonRoot(),p).findValue("subItemName"));
		
		/*
		 * 		String[] pathArr=p.split("\\[\\d\\]");
		logger.debug(Arrays.toString(pathArr));
		 		//String num=jxt.getArrNumInPath(p, "/data/itemList/items[2]/subitems[1]/item");
		//logger.debug(num);
		 */
//		
//		jp=jp.valueOf("/data/itemList/items");
//	    tmpNode=root.at(jp);//查询sql必须是去上级的第n个下级为属性
//	    tmpNode=tmpNode.get(2).get("item");
//	    logger.debug(tmpNode);
//	    
//	    jp=jp.valueOf("/subItem");
//	    tmpNode=tmpNode.at(jp);//查询sql必须是去上级的第n个下级为属性
//	    tmpNode=tmpNode.get(1).get("item");
//	    logger.debug(tmpNode);

		}catch(Exception e){
			logger.error(e.toString()+e);
		}

	}


	/**el.addElement("basic");
	 Element e0=(Element)el.selectSingleNode("basic");
	 //e0.setParent(el);
	 logger.debug(e0.asXML());//对于子节点的遍历仍然需要完整的路径，子节点具备了父节点的引用，它创建了子节点相当于父节点创建了子节点
	 if(e0.matches("/data/basic/items")){
	 logger.debug("data");
	 }else{
	 e0.addElement("items");
	 }
	 logger.debug(el.asXML());
	 */

}
