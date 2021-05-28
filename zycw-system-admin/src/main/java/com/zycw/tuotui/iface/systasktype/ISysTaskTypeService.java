package com.zycw.tuotui.iface.systasktype;



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
import com.zycw.tuotui.readdao.systasktype.SysTaskTypeMapper;
import com.zycw.tuotui.entity.systasktype.SysTaskType;

import java.lang.String;
import java.lang.Integer;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
/**
 * b任务类型 服务类
 */
    		


@Service("ISysTaskTypeService")
public class ISysTaskTypeService extends BaseService<SysTaskTypeMapper,SysTaskType> {

   /**
    * 根据主键物理删除
    * @param String 主键ID
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-05-28
    */
	public void deleteById(String sysTaskTypeId){
		mapper.deleteById(sysTaskTypeId);
	}
	
   /**
    * 根据主键逻辑删除
    * @param String 主键ID
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-05-28
    */
	public void deleteByIdLogic(String sysTaskTypeId){
		mapper.deleteByIdLogic(sysTaskTypeId);
	}
	
	
	public void updateObjById(SysTaskType sysTaskType){
		mapper.updateObjById(sysTaskType);
	}
	
	
	
   /**
    * 添加
    * @param SysTaskType 对象
    * @return 返回结果 void
	* @author junxu.yang
	* @since 2021-05-28
    */
	public void insertObj(SysTaskType sysTaskType){
		mapper.insertObj(sysTaskType);
	}
	
	
   /**
    * 批量删除
    * @param List<SysTaskType> 对象
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-05-28
    */
	public void batchInsertObj(List<SysTaskType> list){
		mapper.batchInsertObj(list);
	}
	
	 /**
    * 根据主键查询对象
    * @param String 主键ID
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-05-28
    */
	public HashMap<String,Object> selectObjById(String sysTaskTypeId){
		return mapper.selectObjById(sysTaskTypeId);
	}

   /**
    * 获取总数
    * @param page 分页条件
    * @param HashMap<String,Object> 对象
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-05-28
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
	* @since 2021-05-28
	 */
	public PageInfo<SysTaskType> pageList(HashMap<String,Object> params) throws Exception {
		Integer pageNum = (Integer)params.get("pageNum");
		Integer pageSize = (Integer)params.get("pageSize");
		Integer pageStart = (pageNum-1)* pageSize;
		params.put("pageStart",pageStart);
		List<SysTaskType> list = mapper.pageList(params);
		PageInfo pagelist = new PageInfo(list);
		pagelist.setPageSize(pageSize);
		pagelist.setPageNum(pageNum);
		return new PageInfo(list);

	}
	
	
	public List<SysTaskType> allList(HashMap<String,Object> param){
		return mapper.allList(param);
	}
	
	
	
}
