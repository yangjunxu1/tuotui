package com.zycw.common.util.adapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;


/**
 * 该类用来根据节点路径创建、检索、赋值json数据
 * 路径配置规范   节点/节点/节点数组[]/{属性}
 * @author lenovo
 *
 */
public class XmlToJsonTransUtil extends  AbsTransUtil<ObjectNode>{

	private static Logger logger =  LoggerFactory.getLogger(XmlToJsonTransUtil.class);

	private Document doc=null;
	private ObjectMapper om=null;


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

	public XmlToJsonTransUtil(){

	}

	public XmlToJsonTransUtil(String xmlStr) throws Exception{
		this.setDoc(DocumentHelper.parseText(xmlStr));
		this.setOm(new ObjectMapper());
	}

	/**
	 * 拷贝xml到json
	 */
	public ObjectNode deepTransForm(ArrayList<String[]> mappingList,String srcParentDataPath,ObjectNode tgtParentNode,HashMap pathVisited){

		String subNodeName="";
		String subSrcDataPath="";//遇到数组节点递归下一层次时的数据查询路径
		String[] pathInfoArr={"",""};
		String[] tgtPathInfoArr={"",""};
		String[] subPathInfoArr={"",""};
		String[] arrNodeInfo={"",""};
		String parentPathNode="";
		ObjectNode tmpObjectNode=null;
		JsonNode pointNode=null;
		JsonPointer jp=null;
		ArrayList<String[]> subMapping=null;
		String firstArr="";
		HashMap thisLevelVisitedPath=new HashMap();
		String queryDataPaht="";
		//1循环映射配置
		for(int i=0;i<mappingList.size();i++){
			System.out.println(" 当前遍历节点 :"+mappingList.get(i)[0].toString());
			System.out.println(" 当前转换节点 :"+tgtParentNode);
			if(mappingList.get(i)[0].equals("/data/items|item[]/subItem/{flag}")){
				String a="asdfdasf";
			}

			//如果当前节点被遍历过则不用遍历了
			if(thisLevelVisitedPath.containsKey(mappingList.get(i)[0])){
				if(!"".equals(mappingList.get(i)[0])){
					continue;
				}
			}else{
				//如果当前节点不是第一级数组节点，且当前节点的上级数组被遍历过，则不用遍历了
				firstArr=isMulitiArrNode(mappingList.get(i)[0]);
				if(!firstArr.equals("")&&thisLevelVisitedPath.containsKey(firstArr)){
					continue;
				}
			}

			//2.1如果当前源路径节点是对象
			pathInfoArr=AbsTransUtil.getRightPathInfo(mappingList.get(i)[0], this.getPathSpator());
			if(AbsTransUtil.getNodeType(pathInfoArr[0])==AbsTransUtil.NodeType.OBJ){

				//2.1则根据源路径获取目标路径，调用递归方法逐级生成目标路径节点
				deepCreateNode(tgtParentNode,mappingList.get(i)[1],this.getPathSpator());
				//将tgtParentNode定位到当前路径
				//tgtParentNode=jp.point
			}

			//3.1如果当前节点是数组对象
			if(AbsTransUtil.getNodeType(pathInfoArr[0])==AbsTransUtil.NodeType.ARR){

				//3.1根据映射目标路径生成目标数组对象
				//3.1获取目标数组对象所有下级路径的映射配置，将当前节点由数组转为单一节点
				subMapping=this.getSubLevelMapping(mappingList, mappingList.get(i)[0], mappingList.get(i)[1]);
				//3.1循环获取数组对象“SelectNodes("//item[1...n]")”获取父节点，生成对应的目标节点，递归调用自己，传入当前节点和子节点的映射列表，父节点，和遍历过的路径map
				//3.1.1获取完整数据查询路径
				subSrcDataPath=this.getFullDataPath(srcParentDataPath, mappingList.get(i)[0]);

				//通过xml方法查询所有的子节点
				List list = this.getDoc().selectNodes(subSrcDataPath);

				if(list.size()>0){

					//将目标json对象节点定位在目标路径  mappingList.get(i)[1]
					tgtPathInfoArr=AbsTransUtil.getRightPathInfo(mappingList.get(i)[1], this.getPathSpator());
					//为确保目标节点一定存在因此深度创建一下
					if(tgtPathInfoArr[1]!=null&&!tgtPathInfoArr[1].equals("")){//上级节点存在才创造
						deepCreateNode(tgtParentNode,tgtPathInfoArr[1],this.getPathSpator());
						jp=JsonPointer.valueOf(tgtPathInfoArr[1]);
						tmpObjectNode=(ObjectNode)tgtParentNode.at(jp);

					}else{//上级节点不存在直接就指定当前的上级节点
						tmpObjectNode=tgtParentNode;
					}

					//3.1.2将数组对象插入父级节点
					//3.1.2.1获取目标数组父节点名字
					subPathInfoArr=AbsTransUtil.getRightPathInfo(mappingList.get(i)[1], this.getPathSpator());
					arrNodeInfo=getArrNodeInfo(subPathInfoArr[0]);
					//3.1.2.2将数组节点插入父节点
					ArrayNode an=this.getOm().createArrayNode();
					//an=tmpObjectNode.arrayNode();
					tmpObjectNode.set(arrNodeInfo[0], an);

					//3.1.2.3循环递归生成每个数组节点的子节点数据
					int elementCount=1;
					for (int k=0;k<list.size();k++) {

						//Element attribute = (Element) iter.next();
						//3.1.2.3.1新增子节点，拼递归调用
						ObjectNode itemOn=this.getOm().createObjectNode();

						//3.1.2.3.3给单个数组节点增加子属性节点以便向下传递
						ObjectNode itemAttrNode=this.getOm().createObjectNode();
						itemOn.set(arrNodeInfo[1], itemAttrNode);

						//3.1.2.3.2将单个数组节点赋值给数组
						an.add(itemOn);

						//3.1.2.3.3递归新增子节点
						deepTransForm(subMapping,subSrcDataPath+"["+elementCount+"]",itemAttrNode,pathVisited);
						elementCount++;
					}
					subMapping.clear();
					subMapping=null;
					list.clear();
					list=null;

				}

			}

			//4如果是数值映射，直接取值，给parentNode赋值
			if(AbsTransUtil.getNodeType(pathInfoArr[0])==AbsTransUtil.NodeType.ATTR){

				//直接按路径取节点，取text，赋值给parent的属性

//				if(srcParentDataPath==null||srcParentDataPath.equals("")){
//					queryDataPaht=srcParentDataPath+this.getPathSpator()+this.getFullDataPath("", mappingList.get(i)[0])
//				}else{
//					queryDataPaht=srcParentDataPath+this.getPathSpator()+this.getFullDataPath("", mappingList.get(i)[0]);
//				}
				Node node = this.getDoc().selectSingleNode(this.getFullDataPath(srcParentDataPath, mappingList.get(i)[0]));
				if(node!=null){
					//将目标json对象节点定位在目标路径  mappingList.get(i)[1]
					tgtPathInfoArr=AbsTransUtil.getRightPathInfo(mappingList.get(i)[1], this.getPathSpator());
					jp=JsonPointer.valueOf(tgtPathInfoArr[1]);
					tmpObjectNode=(ObjectNode)tgtParentNode.at(jp);
					//tmpObjectNode.put();
					tmpObjectNode.put(getAttrName(tgtPathInfoArr[0]),node.getText());
				}
			}

			thisLevelVisitedPath.put(mappingList.get(i)[0], mappingList.get(i)[1]);//域内访问过路径做个存储

		}

		//用完释放资源
		subNodeName="";
		subSrcDataPath="";//遇到数组节点递归下一层次时的数据查询路径
		pathInfoArr=null;
		tgtPathInfoArr=null;
		subPathInfoArr=null;
		arrNodeInfo=null;
		parentPathNode="";
		tmpObjectNode=null;
		pointNode=null;
		jp=null;
		subMapping=null;
		firstArr="";
		thisLevelVisitedPath=null;

		//无需返回，仅为后续预留接口
		return null;
	}

	/**
	 * 根据当前路径和父级路径拼实际查询数据的路径
	 * @param parentPath 上级路径
	 * @param curPath 当前路径
	 * @return 返回完整的数据查询路径
	 */
	public String getFullDataPath(String parentPath,String curPath){
		if(StringUtils.isBlank(curPath)){
			parentPath="/";
			return parentPath;
		}
		//去掉当前路径的左边第一个/
		if(curPath.length()>=1&&curPath.indexOf(this.getPathSpator())>-1&&curPath.substring(0,1).equals(this.getPathSpator())){
			curPath=curPath.substring(1);
		}
		if(parentPath.length()>=2&&parentPath.indexOf("//")<=-1){
			if(parentPath.substring(0,1).equals(this.getPathSpator())){
				parentPath="//"+parentPath.substring(1)+this.getPathSpator()+curPath;
			}else{
				parentPath="//"+parentPath+this.getPathSpator()+curPath;
			}
		}else{//如果包含了绝对路径直接累加
			parentPath="//"+parentPath+this.getPathSpator()+curPath;
		}

		//如果存在数组结构则将  | 替换为路径
		parentPath=parentPath.replace("|", this.getPathSpator());
		parentPath=parentPath.replace("////", "//");
		parentPath=parentPath.replace("///", "//");
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
	public void deepCreateNode(ObjectNode parentNode,String nodePath,String pathSplitor){
		//如果最左边以分隔符开始，则去掉分隔符
		if(nodePath!=null&&nodePath.length()>0&&nodePath.substring(0, 1).equals(pathSplitor)){
			nodePath=nodePath.substring(1);
		}
		String[] arr=nodePath.split(pathSplitor);
		String curPath="";
		String nextPath="";
		JsonNode pointNode=null;
		ObjectNode tmpNode=null;
		JsonPointer pointer=null;
		if(arr!=null&&arr.length>0){
			curPath=pathSplitor+arr[0];
			pointNode=parentNode.path(arr[0]);
			if(pointNode.isEmpty()){
				pointer =JsonPointer.valueOf(nextPath);
				tmpNode=(ObjectNode)parentNode.at(pointer);
				tmpNode.set(arr[0],this.getOm().createObjectNode());
			}

			for(int i=1;i<arr.length;i++){
				if(arr[i]!=null&&!arr[i].equals("")){//下一个节点不为空才创造
					nextPath=curPath+pathSplitor+arr[i];
					pointNode=pointNode.path(arr[i]);
					if(pointNode==null||pointNode.isEmpty()){//如果不存在这个节点，则跳转到上一个节点创建该节点
						//pointer =JsonPointer.valueOf(nextPath);
						pointer =JsonPointer.valueOf(curPath);
						tmpNode=(ObjectNode)parentNode.at(pointer);
						tmpNode.set(arr[i],this.getOm().createObjectNode());

					}
					//当前路径变为下一个路径
					curPath=nextPath;
				}
			}
		}
	}

	public ObjectNode createNode(){
		return this.getOm().createObjectNode();
	}

	/**
	 * 正则替换字符串
	 * @param content 字符串
	 * @param pattern 正则表达式
	 * @param newString 替换的新字符串
	 * @return
	 */
	public static String regReplace(String content,String pattern,String newString){
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(content);
		String result = m.replaceAll(newString);
		return result;
	}


	public static void main(String arg[]){
		long start = System.currentTimeMillis();
		ArrayList<String[]> mappingList=new ArrayList<String[]>();

		mappingList.add(new String[]{"/Envelope/Header",""});
		mappingList.add(new String[]{"/Envelope/Header/HEADER",""});
		mappingList.add(new String[]{"/Envelope/Header/HEADER/AUTH",""});
		mappingList.add(new String[]{"/Envelope/Header/HEADER/AUTH/{BRANCHNO}","/{BRANCHNO}"});
		mappingList.add(new String[]{"/Envelope/Header/HEADER/AUTH/{TOKENID}","/{TOKENID}"});
		mappingList.add(new String[]{"/Envelope/Header/HEADER/AUTH/{USERID}","/{USERID}"});
		mappingList.add(new String[]{"/Envelope/Header/HEADER/{FROMSYS}","/{FROMSYS}"});
		mappingList.add(new String[]{"/Envelope/Header/HEADER/{ORISYS}","/{ORISYS}"});
		mappingList.add(new String[]{"/Envelope/Header/HEADER/ROUTE","/ROUTE"});
		mappingList.add(new String[]{"/Envelope/Header/HEADER/ROUTE/{BRANCHNO}","/ROUTE/{BRANCHNO}"});
		mappingList.add(new String[]{"/Envelope/Header/HEADER/ROUTE/{DESTSYS}","/ROUTE/{DESTSYS}"});
		mappingList.add(new String[]{"/Envelope/Header/HEADER/SECURITY","/SECURITY"});
		mappingList.add(new String[]{"/Envelope/Header/HEADER/SECURITY/{SIGNATURE}","/SECURITY/{SIGNATURE}"});
		mappingList.add(new String[]{"/Envelope/Header/HEADER/{SVCCODE}","/{SVCCODE}"});
		mappingList.add(new String[]{"/Envelope/Header/HEADER/{SVCNAME}","/{SVCCODE}"});
		mappingList.add(new String[]{"/Envelope/Header/HEADER/{SVCVER}","/{SVCVER}"});
		mappingList.add(new String[]{"/Envelope/Header/HEADER/{TRANNO}","/{TRANNO}"});
		mappingList.add(new String[]{"/Envelope/Header/HEADER/{TRANTIME}","/{TRANTIME}"});

		mappingList.add(new String[]{"/Envelope/Body",""});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl",""});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo",""});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/MANAGE",""});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/MANAGE/{APPLNO}","/{prtNo}"});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/MANAGE/{ORDERNO}","/{orderNo}"});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/MANAGE/{DOCNO}",""});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/MANAGE/{APPLTYPE}",""});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/MANAGE/{APPLDATE}","/{polApplyDate}"});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/MANAGE/{SALESCHANNEL}","/{saleChnl}"});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/MANAGE/{SALESBRANCHNO}",""});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/MANAGE/{SALESCODE}",""});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/MANAGE/{SALESNAME}",""});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/MANAGE/{AGENTNO}",""});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/MANAGE/{STORECODE}",""});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/MANAGE/{APPLPROPERTY}",""});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/MANAGE/{QUOTEPRICENO}",""});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/MANAGE/{PROCFLAG}",""});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/MANAGE/{BILLPRINTFLAG}",""});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/MANAGE/{PRTIPSNLSTTYPE}",""});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/MANAGE/{VOUCHERPRINTFLAG}",""});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/MANAGE/{LISTFLAG}",""});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/MANAGE/{MUAPPROVE}",""});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/MANAGE/{MUUDW}",""});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/MANAGE/{ABNORMITYNOTICE}",""});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/MANAGE/{REMARK}","/{remark}"});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/MANAGE/{DISCOUNT}",""});

		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/AGREEMENT",""});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/AGREEMENT/{INVALIDDATETYPE}",""});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/AGREEMENT/{VALIDDATE}","/{coValiDate}"});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/AGREEMENT/{VALIDTIME}",""});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/AGREEMENT/{ISRUNIT}",""});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/AGREEMENT/{INSURDUR}","/{insuYear}"});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/AGREEMENT/{MONEYINITRVL}","/{payIntv}"});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/AGREEMENT/{FIRSTMONEYINTYPE}",""});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/AGREEMENT/{MONEYINTYPE}",""});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/AGREEMENT/{CURRENCY_CODE}",""});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/AGREEMENT/{CONTONUEFLAG}",""});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/AGREEMENT/{RENEWEDCGNO}",""});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/AGREEMENT/{CONVENTIONTEXT}",""});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/AGREEMENT/{EXTRAMES}",""});

		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/AGREEMENT/PRODUCTS",""});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/AGREEMENT/PRODUCTS/{PRDCOUNT}",""});

		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/AGREEMENT/PRODUCTS/PRODUCT",""});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/AGREEMENT/PRODUCTS/PRODUCT/{PRDCODE}","/{productCode}"});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/AGREEMENT/PRODUCTS/PRODUCT/{PRDPREM}","/{prem}"});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/AGREEMENT/PRODUCTS/PRODUCT/{PRDAMNT}",""});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/AGREEMENT/{SUMPREM}","/{sumPrem}"});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/AGREEMENT/{SUMAMNT}",""});

		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/APPLICANT","/appInsures|appInsures[]"});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/APPLICANT/{APPNAME}","/appInsures|appInsures[]/{custName}"});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/APPLICANT/{APPGENDER}","/appInsures|appInsures[]/{custSex}"});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/APPLICANT/{APPBIRTH}","/appInsures|appInsures[]/{custBirthday}"});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/APPLICANT/{APPIDTYPE}","/appInsures|appInsures[]/{certType}"});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/APPLICANT/{APPIDNO}","/appInsures|appInsures[]/{certNo}"});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/APPLICANT/{PRIMCONTACTWAY}",""});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/APPLICANT/{APPTEL}","/appInsures|appInsures[]/{appTel}"});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/APPLICANT/{APPMOBILE}","/appInsures|appInsures[]/{appMobile}"});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/APPLICANT/{APPFAX}",""});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/APPLICANT/{APPEMAIL}","/appInsures|appInsures[]/{appEmail}"});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/APPLICANT/{APPPOST}","/appInsures|appInsures[]/{appPost}"});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/APPLICANT/{APPADDR}","/appInsures|appInsures[]/{appAddr}"});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/APPLICANT/{APPPROFCODE}","/appInsures|appInsures[]/{appProfCode}"});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/APPLICANT/{APPPROFTYPE}","/appInsures|appInsures[]/{appProfType}"});

		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/INSUREDS",""});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/INSUREDS/{ISDCOUNT}",""});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED","/insuredVos|insuredVos[]"});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{ISDID}","/insuredVos|insuredVos[]/{}"});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{ISDTYPE}","/insuredVos|insuredVos[]/{}"});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{ISDNAME}","/insuredVos|insuredVos[]/{custName}"});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{ISDGENDER}","/insuredVos|insuredVos[]/{custSex}"});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{ISDBIRTH}","/insuredVos|insuredVos[]/{custBirthday}"});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{ISDIDTYPE}","/insuredVos|insuredVos[]/{certType}"});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{ISDIDNO}","/insuredVos|insuredVos[]/{cerNo}"});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{APPRLT}","/insuredVos|insuredVos[]/{apprlt}"});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{ISDMID}",""});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{BUYCOUNT}",""});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{ISDTEL}",""});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{ISDMOBILE}","/insuredVos|insuredVos[]/{isdMobile}"});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{ISDEMAIL}","/insuredVos|insuredVos[]/{isdEmail}"});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{ISDPOST}","/insuredVos|insuredVos[]/{isdPost}"});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{ISDADDR}","/insuredVos|insuredVos[]/{isdAddr}"});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{ISDPROFCODE}","/insuredVos|insuredVos[]/{isdProfCode}"});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{ISDPROFTYPE}",""});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{ISDHEALTHNOTICE}",""});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{CONTACTSHAREFLAG}","/{custShareFlag}"});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{INJOBFLAG}",""});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{ISDCOMPANYNAME}",""});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{ISDSSS}",""});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{ISDSSC}",""});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{ISDSSN}",""});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{BANKACCNAME}",""});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{BANKCODE}",""});
		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{BANKACCNO}",""});

		System.out.println("开始时间:"+System.currentTimeMillis());
		//正确的: String xx="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://commitsingleapplvo.server.soa.aslss.chinalife.com/xsd\" xmlns:soa=\"http://www.e-chinalife.com/soa/\" xmlns:cl=\"http://www.e-chinalife.com/soa/\"  ><soapenv:Header ><cl:HEADER ><AUTH><BRANCHNO /><TOKENID /><USERID /></AUTH><FROMSYS /><ORISYS>SCT-EPS</ORISYS><ROUTE><BRANCHNO>000000</BRANCHNO><DESTSYS>ASLSS</DESTSYS></ROUTE><SECURITY><SIGNATURE /></SECURITY><SVCCODE>ASLSS-0001</SVCCODE><SVCNAME>CommitSingleAppl</SVCNAME><SVCVER>1.0</SVCVER><TRANNO>2016-02-19 12:52:31284291</TRANNO><TRANTIME>2016-02-19 12:52:31</TRANTIME></cl:HEADER></soapenv:Header><soapenv:Body xmlns:xsd=\"http://commitsingleapplvo.server.soa.aslss.chinalife.com/xsd\" xmlns:soa=\"http://www.e-chinalife.com/soa/\"><soa:commitSingleAppl><soa:vo><MANAGE><APPLNO/><ORDERNO></ORDERNO><DOCNO></DOCNO><APPLTYPE/><APPLDATE>2019-07-18</APPLDATE><SALESCHANNEL>OA</SALESCHANNEL><SALESBRANCHNO>120109</SALESBRANCHNO><SALESCODE/><SALESNAME/><AGENTNO>11041201092009</AGENTNO><STORECODE>JCJK</STORECODE><APPLPROPERTY>0</APPLPROPERTY><QUOTEPRICENO/><PROCFLAG>1</PROCFLAG><BILLPRINTFLAG/><PRTIPSNLSTTYPE/><VOUCHERPRINTFLAG/><LISTFLAG/><MUAPPROVE>0</MUAPPROVE><MUUDW>0</MUUDW><ABNORMITYNOTICE>0</ABNORMITYNOTICE><REMARK/><DISCOUNT>1</DISCOUNT></MANAGE><AGREEMENT><INVALIDDATETYPE>1</INVALIDDATETYPE><VALIDDATE>2019-07-19</VALIDDATE><VALIDTIME>11:00:00</VALIDTIME><ISRUNIT/><INSURDUR>Y1</INSURDUR><MONEYINITRVL>W</MONEYINITRVL><FIRSTMONEYINTYPE/><MONEYINTYPE>V</MONEYINTYPE><CURRENCY_CODE/><CONTONUEFLAG>0</CONTONUEFLAG><RENEWEDCGNO/><CONVENTIONTEXT/><EXTRAMES/><PRODUCTS><PRDCOUNT>1</PRDCOUNT><PRODUCT><PRDCODE>P120000360147</PRDCODE><PRDPREM/><PRDAMNT/></PRODUCT></PRODUCTS><SUMPREM/><SUMAMNT/></AGREEMENT><APPLICANT><APPNAME>投保人姓名</APPNAME><APPGENDER>F</APPGENDER><APPBIRTH>2002-01-01</APPBIRTH><APPIDTYPE>P</APPIDTYPE><APPIDNO>110101199001010066</APPIDNO><PRIMCONTACTWAY/><APPTEL/><APPMOBILE>13812345678</APPMOBILE><APPFAX></APPFAX><APPEMAIL> wqeq.wq_wr@yn.e-chinalife.com </APPEMAIL><APPPOST/><APPADDR/><APPPROFCODE/><APPPROFTYPE/></APPLICANT><INSUREDS><ISDCOUNT>1</ISDCOUNT><INSURED><ISDID>1</ISDID><ISDTYPE>I</ISDTYPE><ISDNAME>包八婆人姓名</ISDNAME><ISDGENDER>M</ISDGENDER><ISDBIRTH>1980-01-01</ISDBIRTH><ISDIDTYPE>I</ISDIDTYPE><ISDIDNO>110101198001010117</ISDIDNO><APPRLT>P</APPRLT><ISDMID>0</ISDMID><BUYCOUNT>1</BUYCOUNT><ISDTEL/><ISDMOBILE>13812345678</ISDMOBILE><ISDEMAIL>  wqeq.wq_wr@yn.e-chinalife.com.tw</ISDEMAIL><ISDPOST></ISDPOST><ISDADDR>G102928374</ISDADDR><ISDPROFCODE/><ISDPROFTYPE/><ISDHEALTHNOTICE/><CONTACTSHAREFLAG/><INJOBFLAG/><ISDCOMPANYNAME/><ISDSSS/><ISDSSC/><ISDSSN/><BANKACCNAME/><BANKCODE/><BANKACCNO/></INSURED></INSUREDS></soa:vo></soa:commitSingleAppl></soapenv:Body></soapenv:Envelope>";
		String xx="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://commitsingleapplvo.server.soa.aslss.chinalife.com/xsd\" xmlns:soa=\"http://www.e-chinalife.com/soa/\" xmlns:cl=\"http://www.e-chinalife.com/soa/\"  ><soapenv:Header ><cl:HEADER ><AUTH><BRANCHNO /><TOKENID /><USERID /></AUTH><FROMSYS /><ORISYS>SCT-EPS</ORISYS><ROUTE><BRANCHNO>000000</BRANCHNO><DESTSYS>ASLSS</DESTSYS></ROUTE><SECURITY><SIGNATURE /></SECURITY><SVCCODE>ASLSS-0001</SVCCODE><SVCNAME>CommitSingleAppl</SVCNAME><SVCVER>1.0</SVCVER><TRANNO>2016-02-19 12:52:31284291</TRANNO><TRANTIME>2016-02-19 12:52:31</TRANTIME></cl:HEADER></soapenv:Header><soapenv:Body xmlns:xsd=\"http://commitsingleapplvo.server.soa.aslss.chinalife.com/xsd\" xmlns:soa=\"http://www.e-chinalife.com/soa/\"><soa:commitSingleAppl><soa:vo><MANAGE><APPLNO/><ORDERNO></ORDERNO><DOCNO></DOCNO><APPLTYPE/><APPLDATE>2019-07-18</APPLDATE><SALESCHANNEL>OA</SALESCHANNEL><SALESBRANCHNO>120109</SALESBRANCHNO><SALESCODE/><SALESNAME/><AGENTNO>11041201092009</AGENTNO><STORECODE>JCJK</STORECODE><APPLPROPERTY>0</APPLPROPERTY><QUOTEPRICENO/><PROCFLAG>1</PROCFLAG><BILLPRINTFLAG/><PRTIPSNLSTTYPE/><VOUCHERPRINTFLAG/><LISTFLAG/><MUAPPROVE>0</MUAPPROVE><MUUDW>0</MUUDW><ABNORMITYNOTICE>0</ABNORMITYNOTICE><REMARK/><DISCOUNT>1</DISCOUNT></MANAGE><AGREEMENT><INVALIDDATETYPE>1</INVALIDDATETYPE><VALIDDATE>2019-07-19</VALIDDATE><VALIDTIME>11:00:00</VALIDTIME><ISRUNIT/><INSURDUR>Y1</INSURDUR><MONEYINITRVL>W</MONEYINITRVL><FIRSTMONEYINTYPE/><MONEYINTYPE>V</MONEYINTYPE><CURRENCY_CODE/><CONTONUEFLAG>0</CONTONUEFLAG><RENEWEDCGNO/><CONVENTIONTEXT/><EXTRAMES/><PRODUCTS><PRDCOUNT>1</PRDCOUNT><PRODUCT><PRDCODE>P120000360147</PRDCODE><PRDPREM/><PRDAMNT/></PRODUCT></PRODUCTS><SUMPREM/><SUMAMNT/></AGREEMENT><APPLICANT><APPNAME>投保人姓名</APPNAME><APPGENDER>F</APPGENDER><APPBIRTH>2002-01-01</APPBIRTH><APPIDTYPE>P</APPIDTYPE><APPIDNO>110101199001010066</APPIDNO><PRIMCONTACTWAY/><APPTEL/><APPMOBILE>13812345678</APPMOBILE><APPFAX></APPFAX><APPEMAIL> wqeq.wq_wr@yn.e-chinalife.com </APPEMAIL><APPPOST/><APPADDR/><APPPROFCODE/><APPPROFTYPE/></APPLICANT><INSUREDS><ISDCOUNT>1</ISDCOUNT><INSURED><ISDID>1</ISDID><ISDTYPE>I</ISDTYPE><ISDNAME>包八婆人姓名</ISDNAME><ISDGENDER>M</ISDGENDER><ISDBIRTH>1980-01-01</ISDBIRTH><ISDIDTYPE>I</ISDIDTYPE><ISDIDNO>110101198001010117</ISDIDNO><APPRLT>P</APPRLT><ISDMID>0</ISDMID><BUYCOUNT>1</BUYCOUNT><ISDTEL/><ISDMOBILE>13812345678</ISDMOBILE><ISDEMAIL>  wqeq.wq_wr@yn.e-chinalife.com.tw</ISDEMAIL><ISDPOST></ISDPOST><ISDADDR>G102928374</ISDADDR><ISDPROFCODE/><ISDPROFTYPE/><ISDHEALTHNOTICE/><CONTACTSHAREFLAG/><INJOBFLAG/><ISDCOMPANYNAME/><ISDSSS/><ISDSSC/><ISDSSN/><BANKACCNAME/><BANKCODE/><BANKACCNO/></INSURED></INSUREDS></soa:vo></soa:commitSingleAppl></soapenv:Body></soapenv:Envelope>";
		String ss="\\b[a-zA-Z]+[:]{1}[a-zA-Z]+[=][\\\"]+[a-z]*[\\\"]|\\b[a-zA-Z]+[:]{1}[a-zA-Z]+[=][\\\"]+[a-zA-z]+://[^\\s]*[\\\"]";
		String s2="\\<[0-9a-zA-Z]+\\:";
		String s3="\\<\\/[0-9a-zA-Z]+\\:";
		String str1 = XmlToJsonTransUtil.regReplace(xx, ss, "");
		System.out.println("去除命名空间:"+str1);
		str1= XmlToJsonTransUtil.regReplace(str1, s2, "<");
		System.out.println("去除前缀:"+str1);
		str1= XmlToJsonTransUtil.regReplace(str1, s3, "</");
		System.out.println("去除后缀:"+str1);
		System.out.println("替换命名空间时间:"+(System.currentTimeMillis()-start));
		try{
			XmlToJsonTransUtil jt=new XmlToJsonTransUtil(str1);
			ObjectNode n=jt.createNode();
			HashMap pathVisited=new HashMap();
			jt.deepTransForm(mappingList, "", n, pathVisited);
			System.out.println("转换完成:"+n);
			System.out.println("总需要时间:"+(System.currentTimeMillis()-start));
		}catch(Exception e){
			e.printStackTrace();
		}
	}

//	    ArrayList<String[]> mappingList=new ArrayList<String[]>();
//		mappingList.add(new String[]{"/Envelope/Header","/Envelope/Header"});
//		mappingList.add(new String[]{"/Envelope/Header/HEADER","/Envelope/Header/HEADER"});
//		mappingList.add(new String[]{"/Envelope/Header/HEADER/AUTH","/Envelope/Header/HEADER/AUTH"});
//		mappingList.add(new String[]{"/Envelope/Header/HEADER/AUTH/{BRANCHNO}","/Envelope/Header/HEADER/AUTH/{BRANCHNO}"});
//		mappingList.add(new String[]{"/Envelope/Header/HEADER/AUTH/{TOKENID}","/Envelope/Header/HEADER/AUTH/{TOKENID}"});
//		mappingList.add(new String[]{"/Envelope/Header/HEADER/AUTH/{USERID}","/Envelope/Header/HEADER/AUTH/{USERID}"});
//		mappingList.add(new String[]{"/Envelope/Header/HEADER/{FROMSYS}","/Envelope/Header/HEADER/{FROMSYS}"});
//		mappingList.add(new String[]{"/Envelope/Header/HEADER/{ORISYS}","/Envelope/Header/HEADER/{ORISYS}"});
//		mappingList.add(new String[]{"/Envelope/Header/HEADER/ROUTE","/Envelope/Header/HEADER/ROUTE"});
//		mappingList.add(new String[]{"/Envelope/Header/HEADER/ROUTE/{BRANCHNO}","/Envelope/Header/HEADER/ROUTE/{BRANCHNO}"});
//
//
//		mappingList.add(new String[]{"/Envelope/Header/HEADER/ROUTE/{DESTSYS}","/Envelope/Header/HEADER/ROUTE/{DESTSYS}"});
//		mappingList.add(new String[]{"/Envelope/Header/HEADER/SECURITY","/Envelope/Header/HEADER/SECURITY"});
//		mappingList.add(new String[]{"/Envelope/Header/HEADER/SECURITY/{SIGNATURE}","/Envelope/Header/HEADER/SECURITY/{SIGNATURE}"});
//		mappingList.add(new String[]{"/Envelope/Header/HEADER/{SVCCODE}","/Envelope/Header/HEADER/{SVCCODE}"});
//		mappingList.add(new String[]{"/Envelope/Header/HEADER/{SVCNAME}","/Envelope/Header/HEADER/{SVCNAME}"});
//		mappingList.add(new String[]{"/Envelope/Header/HEADER/{SVCVER}","/Envelope/Header/HEADER/{SVCVER}"});
//		mappingList.add(new String[]{"/Envelope/Header/HEADER/{TRANNO}","/Envelope/Header/HEADER/{TRANNO}"});
//
//
//		mappingList.add(new String[]{"/Envelope/Header/HEADER/{TRANTIME}","/Envelope/Header/HEADER/{TRANTIME}"});
//
//		mappingList.add(new String[]{"/Envelope/Body","/Envelope/Body"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl","/Envelope/Body/commitSingleAppl"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo","/Envelope/Body/commitSingleAppl/vo"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/MANAGE","/Envelope/Body/commitSingleAppl/vo/MANAGE"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/MANAGE/{APPLNO}","/Envelope/Body/commitSingleAppl/vo/MANAGE/{APPLNO}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/MANAGE/{ORDERNO}","/Envelope/Body/commitSingleAppl/vo/MANAGE/{ORDERNO}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/MANAGE/{DOCNO}","/Envelope/Body/commitSingleAppl/vo/MANAGE/{DOCNO}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/MANAGE/{APPLTYPE}","/Envelope/Body/commitSingleAppl/vo/MANAGE/{APPLTYPE}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/MANAGE/{APPLDATE}","/Envelope/Body/commitSingleAppl/vo/MANAGE/{APPLDATE}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/MANAGE/{SALESCHANNEL}","/Envelope/Body/commitSingleAppl/vo/MANAGE/{SALESCHANNEL}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/MANAGE/{SALESBRANCHNO}","/Envelope/Body/commitSingleAppl/vo/MANAGE/{SALESBRANCHNO}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/MANAGE/{SALESCODE}","/Envelope/Body/commitSingleAppl/vo/MANAGE/{SALESCODE}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/MANAGE/{SALESNAME}","/Envelope/Body/commitSingleAppl/vo/MANAGE/{SALESNAME}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/MANAGE/{AGENTNO}","/Envelope/Body/commitSingleAppl/vo/MANAGE/{AGENTNO}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/MANAGE/{STORECODE}","/Envelope/Body/commitSingleAppl/vo/MANAGE/{STORECODE}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/MANAGE/{APPLPROPERTY}","/Envelope/Body/commitSingleAppl/vo/MANAGE/{APPLPROPERTY}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/MANAGE/{QUOTEPRICENO}","/Envelope/Body/commitSingleAppl/vo/MANAGE/{QUOTEPRICENO}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/MANAGE/{PROCFLAG}","/Envelope/Body/commitSingleAppl/vo/MANAGE/{PROCFLAG}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/MANAGE/{BILLPRINTFLAG}","/Envelope/Body/commitSingleAppl/vo/MANAGE/{BILLPRINTFLAG}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/MANAGE/{PRTIPSNLSTTYPE}","/Envelope/Body/commitSingleAppl/vo/MANAGE/{PRTIPSNLSTTYPE}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/MANAGE/{VOUCHERPRINTFLAG}","/Envelope/Body/commitSingleAppl/vo/MANAGE/{VOUCHERPRINTFLAG}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/MANAGE/{LISTFLAG}","/Envelope/Body/commitSingleAppl/vo/MANAGE/{LISTFLAG}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/MANAGE/{MUAPPROVE}","/Envelope/Body/commitSingleAppl/vo/MANAGE/{MUAPPROVE}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/MANAGE/{MUUDW}","/Envelope/Body/commitSingleAppl/vo/MANAGE/{MUUDW}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/MANAGE/{ABNORMITYNOTICE}","/Envelope/Body/commitSingleAppl/vo/MANAGE/{ABNORMITYNOTICE}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/MANAGE/{REMARK}","/Envelope/Body/commitSingleAppl/vo/MANAGE/{REMARK}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/MANAGE/{DISCOUNT}","/Envelope/Body/commitSingleAppl/vo/MANAGE/{DISCOUNT}"});
//
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/AGREEMENT","/Envelope/Body/commitSingleAppl/vo/AGREEMENT"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/AGREEMENT/{INVALIDDATETYPE}","/Envelope/Body/commitSingleAppl/vo/AGREEMENT/{INVALIDDATETYPE}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/AGREEMENT/{VALIDDATE}","/Envelope/Body/commitSingleAppl/vo/AGREEMENT/{VALIDDATE}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/AGREEMENT/{VALIDTIME}","/Envelope/Body/commitSingleAppl/vo/AGREEMENT/{VALIDTIME}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/AGREEMENT/{ISRUNIT}","/Envelope/Body/commitSingleAppl/vo/AGREEMENT/{ISRUNIT}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/AGREEMENT/{INSURDUR}","/Envelope/Body/commitSingleAppl/vo/AGREEMENT/{INSURDUR}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/AGREEMENT/{MONEYINITRVL}","/Envelope/Body/commitSingleAppl/vo/AGREEMENT/{MONEYINITRVL}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/AGREEMENT/{FIRSTMONEYINTYPE}","/Envelope/Body/commitSingleAppl/vo/AGREEMENT/{FIRSTMONEYINTYPE}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/AGREEMENT/{MONEYINTYPE}","/Envelope/Body/commitSingleAppl/vo/AGREEMENT/{MONEYINTYPE}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/AGREEMENT/{CURRENCY_CODE}","/Envelope/Body/commitSingleAppl/vo/AGREEMENT/{CURRENCY_CODE}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/AGREEMENT/{CONTONUEFLAG}","/Envelope/Body/commitSingleAppl/vo/AGREEMENT/{CONTONUEFLAG}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/AGREEMENT/{RENEWEDCGNO}","/Envelope/Body/commitSingleAppl/vo/AGREEMENT/{RENEWEDCGNO}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/AGREEMENT/{CONVENTIONTEXT}","/Envelope/Body/commitSingleAppl/vo/AGREEMENT/{CONVENTIONTEXT}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/AGREEMENT/{EXTRAMES}","/Envelope/Body/commitSingleAppl/vo/AGREEMENT/{EXTRAMES}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/AGREEMENT/PRODUCTS","/Envelope/Body/commitSingleAppl/vo/AGREEMENT/PRODUCTS"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/AGREEMENT/PRODUCTS/{PRDCOUNT}","/Envelope/Body/commitSingleAppl/vo/AGREEMENT/PRODUCTS/{PRDCOUNT}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/AGREEMENT/PRODUCTS/PRODUCT","/Envelope/Body/commitSingleAppl/vo/AGREEMENT/PRODUCTS/PRODUCT"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/AGREEMENT/PRODUCTS/PRODUCT/{PRDCODE}","/Envelope/Body/commitSingleAppl/vo/AGREEMENT/PRODUCTS/PRODUCT/{PRDCODE}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/AGREEMENT/PRODUCTS/PRODUCT/{PRDPREM}","/Envelope/Body/commitSingleAppl/vo/AGREEMENT/PRODUCTS/PRODUCT/{PRDPREM}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/AGREEMENT/PRODUCTS/PRODUCT/{PRDAMNT}","/Envelope/Body/commitSingleAppl/vo/AGREEMENT/PRODUCTS/PRODUCT/{PRDAMNT}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/AGREEMENT/{SUMPREM}","/Envelope/Body/commitSingleAppl/vo/AGREEMENT/{SUMPREM}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/AGREEMENT/{SUMAMNT}","/Envelope/Body/commitSingleAppl/vo/AGREEMENT/{SUMAMNT}"});
//
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/APPLICANT","/Envelope/Body/commitSingleAppl/vo/APPLICANT"});
//		mappingList.add(new String[]{"","/Envelope/Body/commitSingleAppl/vo/APPLICANT/{APPNAME}"});////Envelope/Body/commitSingleAppl/vo/APPLICANT/{APPNAME}
//		mappingList.add(new String[]{"","/Envelope/Body/commitSingleAppl/vo/APPLICANT/{ceshi}"}); //测试
//		mappingList.add(new String[]{"","/Envelope/Body/commitSingleAppl/vo/APPLICANT/{APPGENDER}"});///Envelope/Body/commitSingleAppl/vo/APPLICANT/{APPGENDER}
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/APPLICANT/{APPBIRTH}","/Envelope/Body/commitSingleAppl/vo/APPLICANT/{APPBIRTH}"});///Envelope/Body/commitSingleAppl/vo/APPLICANT/{APPBIRTH}
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/APPLICANT/{APPIDTYPE}","/Envelope/Body/commitSingleAppl/vo/APPLICANT/{APPIDTYPE}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/APPLICANT/{APPIDNO}","/Envelope/Body/commitSingleAppl/vo/APPLICANT/{APPIDNO}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/APPLICANT/{PRIMCONTACTWAY}","/Envelope/Body/commitSingleAppl/vo/APPLICANT/{PRIMCONTACTWAY}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/APPLICANT/{APPTEL}","/Envelope/Body/commitSingleAppl/vo/APPLICANT/{APPTEL}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/APPLICANT/{APPMOBILE}","/Envelope/Body/commitSingleAppl/vo/APPLICANT/{APPMOBILE}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/APPLICANT/{APPFAX}","/Envelope/Body/commitSingleAppl/vo/APPLICANT/{APPFAX}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/APPLICANT/{APPEMAIL}","/Envelope/Body/commitSingleAppl/vo/APPLICANT/{APPEMAIL}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/APPLICANT/{APPPOST}","/Envelope/Body/commitSingleAppl/vo/APPLICANT/{APPPOST}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/APPLICANT/{APPADDR}","/Envelope/Body/commitSingleAppl/vo/APPLICANT/{APPADDR}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/APPLICANT/{APPPROFCODE}","/Envelope/Body/commitSingleAppl/vo/APPLICANT/{APPPROFCODE}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/APPLICANT/{APPPROFTYPE}","/Envelope/Body/commitSingleAppl/vo/APPLICANT/{APPPROFTYPE}"});
//
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/INSUREDS","/Envelope/Body/commitSingleAppl/vo/INSUREDS"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/INSUREDS/{ISDCOUNT}","/Envelope/Body/commitSingleAppl/vo/INSUREDS/{ISDCOUNT}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED","/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{ISDID}","/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{ISDID}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{ISDTYPE}","/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{ISDTYPE}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{ISDNAME}","/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{ISDNAME}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{ISDGENDER}","/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{ISDGENDER}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{ISDBIRTH}","/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{ISDBIRTH}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{ISDIDTYPE}","/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{ISDIDTYPE}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{ISDIDNO}","/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{ISDIDNO}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{APPRLT}","/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{APPRLT}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{ISDMID}","/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{ISDMID}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{BUYCOUNT}","/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{BUYCOUNT}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{ISDTEL}","/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{ISDTEL}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{ISDMOBILE}","/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{ISDMOBILE}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{ISDEMAIL}","/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{ISDEMAIL}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{ISDPOST}","/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{ISDPOST}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{ISDADDR}","/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{ISDADDR}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{ISDPROFCODE}","/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{ISDPROFCODE}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{ISDPROFTYPE}","/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{ISDPROFTYPE}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{ISDHEALTHNOTICE}","/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{ISDHEALTHNOTICE}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{CONTACTSHAREFLAG}","/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{CONTACTSHAREFLAG}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{INJOBFLAG}","/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{INJOBFLAG}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{ISDCOMPANYNAME}","/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{ISDCOMPANYNAME}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{ISDSSS}","/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{ISDSSS}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{ISDSSC}","/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{ISDSSC}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{ISDSSN}","/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{ISDSSN}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{BANKACCNAME}","/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{BANKACCNAME}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{BANKCODE}","/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{BANKCODE}"});
//		mappingList.add(new String[]{"/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{BANKACCNO}","/Envelope/Body/commitSingleAppl/vo/INSUREDS/INSURED/{BANKACCNO}"});
//
//		System.out.println("开始时间:"+System.currentTimeMillis());
//	//String xx="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://commitsingleapplvo.server.soa.aslss.chinalife.com/xsd\" xmlns:soa=\"http://www.e-chinalife.com/soa/\" xmlns:cl=\"http://www.e-chinalife.com/soa/\"  ><soapenv:Header ><cl:HEADER ><AUTH><BRANCHNO /><TOKENID /><USERID /></AUTH><FROMSYS /><ORISYS>SCT-EPS</ORISYS><ROUTE><BRANCHNO>000000</BRANCHNO><DESTSYS>ASLSS</DESTSYS></ROUTE><SECURITY><SIGNATURE /></SECURITY><SVCCODE>ASLSS-0001</SVCCODE><SVCNAME>CommitSingleAppl</SVCNAME><SVCVER>1.0</SVCVER><TRANNO>2016-02-19 12:52:31284291</TRANNO><TRANTIME>2016-02-19 12:52:31</TRANTIME></cl:HEADER></soapenv:Header><soapenv:Body xmlns:xsd=\"http://commitsingleapplvo.server.soa.aslss.chinalife.com/xsd\" xmlns:soa=\"http://www.e-chinalife.com/soa/\"><soa:commitSingleAppl><soa:vo><MANAGE><APPLNO/><ORDERNO></ORDERNO><DOCNO></DOCNO><APPLTYPE/><APPLDATE>2019-07-18</APPLDATE><SALESCHANNEL>OA</SALESCHANNEL><SALESBRANCHNO>120109</SALESBRANCHNO><SALESCODE/><SALESNAME/><AGENTNO>11041201092009</AGENTNO><STORECODE>JCJK</STORECODE><APPLPROPERTY>0</APPLPROPERTY><QUOTEPRICENO/><PROCFLAG>1</PROCFLAG><BILLPRINTFLAG/><PRTIPSNLSTTYPE/><VOUCHERPRINTFLAG/><LISTFLAG/><MUAPPROVE>0</MUAPPROVE><MUUDW>0</MUUDW><ABNORMITYNOTICE>0</ABNORMITYNOTICE><REMARK/><DISCOUNT>1</DISCOUNT></MANAGE><AGREEMENT><INVALIDDATETYPE>1</INVALIDDATETYPE><VALIDDATE>2019-07-19</VALIDDATE><VALIDTIME>11:00:00</VALIDTIME><ISRUNIT/><INSURDUR>Y1</INSURDUR><MONEYINITRVL>W</MONEYINITRVL><FIRSTMONEYINTYPE/><MONEYINTYPE>V</MONEYINTYPE><CURRENCY_CODE/><CONTONUEFLAG>0</CONTONUEFLAG><RENEWEDCGNO/><CONVENTIONTEXT/><EXTRAMES/><PRODUCTS><PRDCOUNT>1</PRDCOUNT><PRODUCT><PRDCODE>P120000360147</PRDCODE><PRDPREM/><PRDAMNT/></PRODUCT></PRODUCTS><SUMPREM/><SUMAMNT/></AGREEMENT><APPLICANT><APPNAME>投保人姓名</APPNAME><APPGENDER>F</APPGENDER><APPBIRTH>2002-01-01</APPBIRTH><APPIDTYPE>P</APPIDTYPE><APPIDNO>110101199001010066</APPIDNO><PRIMCONTACTWAY/><APPTEL/><APPMOBILE>13812345678</APPMOBILE><APPFAX></APPFAX><APPEMAIL> wqeq.wq_wr@yn.e-chinalife.com </APPEMAIL><APPPOST/><APPADDR/><APPPROFCODE/><APPPROFTYPE/></APPLICANT><INSUREDS><ISDCOUNT>1</ISDCOUNT><INSURED><ISDID>1</ISDID><ISDTYPE>I</ISDTYPE><ISDNAME>包八婆人姓名</ISDNAME><ISDGENDER>M</ISDGENDER><ISDBIRTH>1980-01-01</ISDBIRTH><ISDIDTYPE>I</ISDIDTYPE><ISDIDNO>110101198001010117</ISDIDNO><APPRLT>P</APPRLT><ISDMID>0</ISDMID><BUYCOUNT>1</BUYCOUNT><ISDTEL/><ISDMOBILE>13812345678</ISDMOBILE><ISDEMAIL>  wqeq.wq_wr@yn.e-chinalife.com.tw</ISDEMAIL><ISDPOST></ISDPOST><ISDADDR>G102928374</ISDADDR><ISDPROFCODE/><ISDPROFTYPE/><ISDHEALTHNOTICE/><CONTACTSHAREFLAG/><INJOBFLAG/><ISDCOMPANYNAME/><ISDSSS/><ISDSSC/><ISDSSN/><BANKACCNAME/><BANKCODE/><BANKACCNO/></INSURED></INSUREDS></soa:vo></soa:commitSingleAppl></soapenv:Body></soapenv:Envelope>";
//	String xx="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://commitsingleapplvo.server.soa.aslss.chinalife.com/xsd\" xmlns:soa=\"http://www.e-chinalife.com/soa/\" xmlns:cl=\"http://www.e-chinalife.com/soa/\"  ><soapenv:Header ><cl:HEADER ><AUTH><BRANCHNO /><TOKENID /><USERID /></AUTH><FROMSYS /><ORISYS>SCT-EPS</ORISYS><ROUTE><BRANCHNO>000000</BRANCHNO><DESTSYS>ASLSS</DESTSYS></ROUTE><SECURITY><SIGNATURE /></SECURITY><SVCCODE>ASLSS-0001</SVCCODE><SVCNAME>CommitSingleAppl</SVCNAME><SVCVER>1.0</SVCVER><TRANNO>2016-02-19 12:52:31284291</TRANNO><TRANTIME>2016-02-19 12:52:31</TRANTIME></cl:HEADER></soapenv:Header><soapenv:Body xmlns:xsd=\"http://commitsingleapplvo.server.soa.aslss.chinalife.com/xsd\" xmlns:soa=\"http://www.e-chinalife.com/soa/\"><soa:commitSingleAppl><soa:vo><MANAGE><APPLNO/><ORDERNO></ORDERNO><DOCNO></DOCNO><APPLTYPE/><APPLDATE>2019-07-18</APPLDATE><SALESCHANNEL>OA</SALESCHANNEL><SALESBRANCHNO>120109</SALESBRANCHNO><SALESCODE/><SALESNAME/><AGENTNO>11041201092009</AGENTNO><STORECODE>JCJK</STORECODE><APPLPROPERTY>0</APPLPROPERTY><QUOTEPRICENO/><PROCFLAG>1</PROCFLAG><BILLPRINTFLAG/><PRTIPSNLSTTYPE/><VOUCHERPRINTFLAG/><LISTFLAG/><MUAPPROVE>0</MUAPPROVE><MUUDW>0</MUUDW><ABNORMITYNOTICE>0</ABNORMITYNOTICE><REMARK/><DISCOUNT>1</DISCOUNT></MANAGE><AGREEMENT><INVALIDDATETYPE>1</INVALIDDATETYPE><VALIDDATE>2019-07-19</VALIDDATE><VALIDTIME>11:00:00</VALIDTIME><ISRUNIT/><INSURDUR>Y1</INSURDUR><MONEYINITRVL>W</MONEYINITRVL><FIRSTMONEYINTYPE/><MONEYINTYPE>V</MONEYINTYPE><CURRENCY_CODE/><CONTONUEFLAG>0</CONTONUEFLAG><RENEWEDCGNO/><CONVENTIONTEXT/><EXTRAMES/><PRODUCTS><PRDCOUNT>1</PRDCOUNT><PRODUCT><PRDCODE>P120000360147</PRDCODE><PRDPREM/><PRDAMNT/></PRODUCT></PRODUCTS><SUMPREM/><SUMAMNT/></AGREEMENT><APPLICANT><APPBIRTH>2002-01-01</APPBIRTH><APPIDTYPE>P</APPIDTYPE><APPIDNO>110101199001010066</APPIDNO><PRIMCONTACTWAY/><APPTEL/><APPMOBILE>13812345678</APPMOBILE><APPFAX></APPFAX><APPEMAIL> wqeq.wq_wr@yn.e-chinalife.com </APPEMAIL><APPPOST/><APPADDR/><APPPROFCODE/><APPPROFTYPE/></APPLICANT><INSUREDS><ISDCOUNT>1</ISDCOUNT><INSURED><ISDID>1</ISDID><ISDTYPE>I</ISDTYPE><ISDNAME>包八婆人姓名</ISDNAME><ISDGENDER>M</ISDGENDER><ISDBIRTH>1980-01-01</ISDBIRTH><ISDIDTYPE>I</ISDIDTYPE><ISDIDNO>110101198001010117</ISDIDNO><APPRLT>P</APPRLT><ISDMID>0</ISDMID><BUYCOUNT>1</BUYCOUNT><ISDTEL/><ISDMOBILE>13812345678</ISDMOBILE><ISDEMAIL>  wqeq.wq_wr@yn.e-chinalife.com.tw</ISDEMAIL><ISDPOST></ISDPOST><ISDADDR>G102928374</ISDADDR><ISDPROFCODE/><ISDPROFTYPE/><ISDHEALTHNOTICE/><CONTACTSHAREFLAG/><INJOBFLAG/><ISDCOMPANYNAME/><ISDSSS/><ISDSSC/><ISDSSN/><BANKACCNAME/><BANKCODE/><BANKACCNO/></INSURED></INSUREDS></soa:vo></soa:commitSingleAppl></soapenv:Body></soapenv:Envelope>";

}
