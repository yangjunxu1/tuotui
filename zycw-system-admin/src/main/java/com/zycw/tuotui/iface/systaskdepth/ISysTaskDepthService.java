package com.zycw.tuotui.iface.systaskdepth;



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
import com.zycw.tuotui.readdao.systaskdepth.SysTaskDepthMapper;
import com.zycw.tuotui.entity.systaskdepth.SysTaskDepth;
import com.zycw.tuotui.iface.aucustomtask.IAuCustomTaskService;

import java.lang.String;
import java.lang.Integer;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
/**
 * b任务深度 服务类
 */
    		


@Service("ISysTaskDepthService")
public class ISysTaskDepthService extends BaseService<SysTaskDepthMapper,SysTaskDepth> {

   
    @Autowired
    private IAuCustomTaskService iAuCustomTaskService;
   /**
    * 根据主键物理删除
    * @param String 主键ID
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-06-01
    */
	public void deleteById(String sysTaskDepthId){
		mapper.deleteById(sysTaskDepthId);
     	iAuCustomTaskService.deleteByauCustomTaskTaskDepth(sysTaskDepthId);
	}
	
   /**
    * 根据主键逻辑删除
    * @param String 主键ID
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-06-01
    */
	public void deleteByIdLogic(String sysTaskDepthId){
		mapper.deleteByIdLogic(sysTaskDepthId);
	}
	

	
	public void updateObjById(SysTaskDepth sysTaskDepth){
		mapper.updateObjById(sysTaskDepth);
	}
	
	
	
   /**
    * 添加
    * @param SysTaskDepth 对象
    * @return 返回结果 void
	* @author junxu.yang
	* @since 2021-06-01
    */
	public void insertObj(SysTaskDepth sysTaskDepth){
		mapper.insertObj(sysTaskDepth);
	}
	
	
   /**
    * 批量删除
    * @param List<SysTaskDepth> 对象
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-06-01
    */
	public void batchInsertObj(List<SysTaskDepth> list){
		mapper.batchInsertObj(list);
	}
	
	 /**
    * 根据主键查询对象
    * @param String 主键ID
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-06-01
    */
	public HashMap<String,Object> selectObjById(String sysTaskDepthId){
		return mapper.selectObjById(sysTaskDepthId);
	}

   /**
    * 获取总数
    * @param page 分页条件
    * @param HashMap<String,Object> 对象
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-06-01
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
	* @since 2021-06-01
	 */
	public PageInfo<SysTaskDepth> pageList(HashMap<String,Object> params) throws Exception {
		Integer pageNum = (Integer)params.get("pageNum");
		Integer pageSize = (Integer)params.get("pageSize");
		Integer pageStart = (pageNum-1)* pageSize;
		params.put("pageStart",pageStart);
		List<SysTaskDepth> list = mapper.pageList(params);
		PageInfo pagelist = new PageInfo(list);
		pagelist.setPageSize(pageSize);
		pagelist.setPageNum(pageNum);
		return new PageInfo(list);

	}
	
	
	public List<SysTaskDepth> allList(HashMap<String,Object> param){
		return mapper.allList(param);
	}
	
	
	
}
