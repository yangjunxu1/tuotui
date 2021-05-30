package com.zycw.tuotui.iface.aucompany;



import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.zycw.system.admin.util.MapUtil;
import com.zycw.distributed.database.service.BaseService;
import com.zycw.common.util.wxwUtil.DateUtil;
import com.zycw.common.util.wxwUtil.IdGenerateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zycw.common.util.Page;
import com.zycw.tuotui.readdao.aucompany.AuCompanyMapper;
import com.zycw.tuotui.entity.aucompany.AuCompany;

import java.lang.String;
import java.lang.Integer;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
/**
 * 企业信息 服务类
 */
    		


@Service("IAuCompanyService")
public class IAuCompanyService extends BaseService<AuCompanyMapper,AuCompany> {

   /**
    * 根据主键物理删除
    * @param String 主键ID
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-05-30
    */
	public void deleteById(String auCompanyId){
		mapper.deleteById(auCompanyId);
	}
	
   /**
    * 根据主键逻辑删除
    * @param String 主键ID
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-05-30
    */
	public void deleteByIdLogic(String auCompanyId){
		mapper.deleteByIdLogic(auCompanyId);
	}
	
	
	/**
	 * <p>
	 * delByName 根据公司名称删除
	 * </p>
	 *
	 * @author junxu.yang
	 * @since 2021-05-30
	 */
	public void delByName(){
		mapper.delByName();
	}
	
	
	
	public void updateObjById(AuCompany auCompany){
		mapper.updateObjById(auCompany);
	}
	
	
	
	/**
	 * <p>
	 * testupdate 更新公司名称
	 * </p>
	 *
	 * @author junxu.yang
	 * @since 2021-05-30
	 */
	public void testupdateUpdate(){
		mapper.testupdateUpdate();
	}
	/**
	 * <p>
	 * testupdate 更新公司名称
	 * </p>
	 *
	 * @author junxu.yang
	 * @since 2021-05-30
	 */
	public void testupdateInsert(){
		mapper.testupdateInsert();
	}
	
   /**
    * 添加
    * @param AuCompany 对象
    * @return 返回结果 void
	* @author junxu.yang
	* @since 2021-05-30
    */
	public void insertObj(AuCompany auCompany){
		mapper.insertObj(auCompany);
	}
	
	
   /**
    * 批量删除
    * @param List<AuCompany> 对象
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-05-30
    */
	public void batchInsertObj(List<AuCompany> list){
		mapper.batchInsertObj(list);
	}
	
	 /**
    * 根据主键查询对象
    * @param String 主键ID
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-05-30
    */
	public HashMap<String,Object> selectObjById(String auCompanyId){
		return mapper.selectObjById(auCompanyId);
	}

   /**
    * 获取总数
    * @param page 分页条件
    * @param HashMap<String,Object> 对象
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-05-30
    */
	public Integer countNum(HashMap<String,Object> params) {
		return mapper.countNum(params);
	}
	   
	/**
	* 分页查询
	* @param page 分页条件
	* @param HashMap<String,Object> 对象
	* @return 返回结果 PageInfo
	* @author junxu.yang
	* @since 2021-05-30
	 */
	public PageInfo<AuCompany> pageList(HashMap<String,Object> params) throws Exception {
		Integer pageNum = (Integer)params.get("pageNum");
		Integer pageSize = (Integer)params.get("pageSize");
		Integer pageStart = (pageNum-1)* pageSize;
		params.put("pageStart",pageStart);
		List<AuCompany> list = mapper.pageList(params);
		PageInfo pagelist = new PageInfo(list);
		pagelist.setPageSize(pageSize);
		pagelist.setPageNum(pageNum);
		return new PageInfo(list);

	}
   
	/**
	 * <p>
	 * selectByProject 自定义查询
	 * </p>
	 *
	 * @author junxu.yang
	 * @since 2021-05-30
	 */
	
	public List<HashMap> selectByProject( HashMap<String,Object> param){
		List<HashMap> result = mapper.selectByProject( param);
		return result;
	}
	
	
	public List<AuCompany> allList(HashMap<String,Object> param){
		return mapper.allList(param);
	}
	
	
	
}
