package com.zycw.tuotui.iface.sysresource;



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
import com.zycw.tuotui.readdao.sysresource.SysResourceMapper;
import com.zycw.tuotui.entity.sysresource.SysResource;

import java.lang.String;
import java.lang.Integer;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
/**
 * 菜单 服务类
 */
    		


@Service("ISysResourceService")
public class ISysResourceService extends BaseService<SysResourceMapper,SysResource> {

   /**
    * 根据主键物理删除
    * @param String 主键ID
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-05-30
    */
	public void deleteById(String sysResourceId){
		mapper.deleteById(sysResourceId);
	}
	
   /**
    * 根据主键逻辑删除
    * @param String 主键ID
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-05-30
    */
	public void deleteByIdLogic(String sysResourceId){
		mapper.deleteByIdLogic(sysResourceId);
	}
	
	
	public void updateObjById(SysResource sysResource){
		mapper.updateObjById(sysResource);
	}
	
	
	
   /**
    * 添加
    * @param SysResource 对象
    * @return 返回结果 void
	* @author junxu.yang
	* @since 2021-05-30
    */
	public void insertObj(SysResource sysResource){
		mapper.insertObj(sysResource);
	}
	
	
   /**
    * 批量删除
    * @param List<SysResource> 对象
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-05-30
    */
	public void batchInsertObj(List<SysResource> list){
		mapper.batchInsertObj(list);
	}
	
	 /**
    * 根据主键查询对象
    * @param String 主键ID
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-05-30
    */
	public HashMap<String,Object> selectObjById(String sysResourceId){
		return mapper.selectObjById(sysResourceId);
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
	public PageInfo<SysResource> pageList(HashMap<String,Object> params) throws Exception {
		Integer pageNum = (Integer)params.get("pageNum");
		Integer pageSize = (Integer)params.get("pageSize");
		Integer pageStart = (pageNum-1)* pageSize;
		params.put("pageStart",pageStart);
		List<SysResource> list = mapper.pageList(params);
		PageInfo pagelist = new PageInfo(list);
		pagelist.setPageSize(pageSize);
		pagelist.setPageNum(pageNum);
		return new PageInfo(list);

	}
	
	
	public List<SysResource> allList(HashMap<String,Object> param){
		return mapper.allList(param);
	}
	
	
	
}
