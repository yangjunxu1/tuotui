package com.zycw.tuotui.iface.auuser;



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
import com.zycw.tuotui.readdao.auuser.AuUserMapper;
import com.zycw.tuotui.entity.auuser.AuUser;

import java.lang.String;
import java.lang.Integer;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
/**
 * 用户 服务类
 */
    		


@Service("IAuUserService")
public class IAuUserService extends BaseService<AuUserMapper,AuUser> {

   /**
    * 根据主键物理删除
    * @param String 主键ID
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-05-28
    */
	public void deleteById(String auUserId){
		mapper.deleteById(auUserId);
	}
	
   /**
    * 根据主键逻辑删除
    * @param String 主键ID
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-05-28
    */
	public void deleteByIdLogic(String auUserId){
		mapper.deleteByIdLogic(auUserId);
	}
	
	
	public void updateObjById(AuUser auUser){
		mapper.updateObjById(auUser);
	}
	
	
	
   /**
    * 添加
    * @param AuUser 对象
    * @return 返回结果 void
	* @author junxu.yang
	* @since 2021-05-28
    */
	public void insertObj(AuUser auUser){
		mapper.insertObj(auUser);
	}
	
	
   /**
    * 批量删除
    * @param List<AuUser> 对象
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-05-28
    */
	public void batchInsertObj(List<AuUser> list){
		mapper.batchInsertObj(list);
	}
	
	 /**
    * 根据主键查询对象
    * @param String 主键ID
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-05-28
    */
	public HashMap<String,Object> selectObjById(String auUserId){
		return mapper.selectObjById(auUserId);
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
	public PageInfo<AuUser> pageList(HashMap<String,Object> params) throws Exception {
		Integer pageNum = (Integer)params.get("pageNum");
		Integer pageSize = (Integer)params.get("pageSize");
		Integer pageStart = (pageNum-1)* pageSize;
		params.put("pageStart",pageStart);
		List<AuUser> list = mapper.pageList(params);
		PageInfo pagelist = new PageInfo(list);
		pagelist.setPageSize(pageSize);
		pagelist.setPageNum(pageNum);
		return new PageInfo(list);

	}
	
	
	public List<AuUser> allList(HashMap<String,Object> param){
		return mapper.allList(param);
	}
	
	
	
}
