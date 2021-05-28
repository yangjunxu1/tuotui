package com.zycw.tuotui.iface.auusertask;



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
import com.zycw.tuotui.readdao.auusertask.AuUserTaskMapper;
import com.zycw.tuotui.entity.auusertask.AuUserTask;

import java.lang.String;
import java.lang.Integer;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
/**
 * 会员任务 服务类
 */
    		


@Service("IAuUserTaskService")
public class IAuUserTaskService extends BaseService<AuUserTaskMapper,AuUserTask> {

   /**
    * 根据主键物理删除
    * @param String 主键ID
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-05-28
    */
	public void deleteById(String auUserTaskId){
		mapper.deleteById(auUserTaskId);
	}
	
   /**
    * 根据主键逻辑删除
    * @param String 主键ID
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-05-28
    */
	public void deleteByIdLogic(String auUserTaskId){
		mapper.deleteByIdLogic(auUserTaskId);
	}
	
	
	public void updateObjById(AuUserTask auUserTask){
		mapper.updateObjById(auUserTask);
	}
	
	
	
   /**
    * 添加
    * @param AuUserTask 对象
    * @return 返回结果 void
	* @author junxu.yang
	* @since 2021-05-28
    */
	public void insertObj(AuUserTask auUserTask){
		mapper.insertObj(auUserTask);
	}
	
	
   /**
    * 批量删除
    * @param List<AuUserTask> 对象
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-05-28
    */
	public void batchInsertObj(List<AuUserTask> list){
		mapper.batchInsertObj(list);
	}
	
	 /**
    * 根据主键查询对象
    * @param String 主键ID
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-05-28
    */
	public HashMap<String,Object> selectObjById(String auUserTaskId){
		return mapper.selectObjById(auUserTaskId);
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
	public PageInfo<AuUserTask> pageList(HashMap<String,Object> params) throws Exception {
		Integer pageNum = (Integer)params.get("pageNum");
		Integer pageSize = (Integer)params.get("pageSize");
		Integer pageStart = (pageNum-1)* pageSize;
		params.put("pageStart",pageStart);
		List<AuUserTask> list = mapper.pageList(params);
		PageInfo pagelist = new PageInfo(list);
		pagelist.setPageSize(pageSize);
		pagelist.setPageNum(pageNum);
		return new PageInfo(list);

	}
	
	
	public List<AuUserTask> allList(HashMap<String,Object> param){
		return mapper.allList(param);
	}
	
	
	
}
