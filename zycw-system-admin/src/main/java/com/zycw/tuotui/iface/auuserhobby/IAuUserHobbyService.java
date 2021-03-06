package com.zycw.tuotui.iface.auuserhobby;



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
import com.zycw.tuotui.readdao.auuserhobby.AuUserHobbyMapper;
import com.zycw.tuotui.entity.auuserhobby.AuUserHobby;

import java.lang.String;
import java.lang.Integer;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
/**
 * 用户爱好关联 服务类
 */
    		


@Service("IAuUserHobbyService")
public class IAuUserHobbyService extends BaseService<AuUserHobbyMapper,AuUserHobby> {

   
   /**
    * 根据主键物理删除
    * @param String 主键ID
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-06-01
    */
	public void deleteById(String auUserHobbyId){
		mapper.deleteById(auUserHobbyId);
	}
	
   /**
    * 根据主键逻辑删除
    * @param String 主键ID
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-06-01
    */
	public void deleteByIdLogic(String auUserHobbyId){
		mapper.deleteByIdLogic(auUserHobbyId);
	}
	
	
   /**
    * 根据外键auUserHobbyUserId删除
    * @param String 外键ID
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-06-01
    */
	public void deleteByauUserHobbyUserId(String id){
		mapper.deleteByauUserHobbyUserId(id);
	}
	
   /**
    * 根据外键auUserHobbyHobbyId删除
    * @param String 外键ID
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-06-01
    */
	public void deleteByauUserHobbyHobbyId(String id){
		mapper.deleteByauUserHobbyHobbyId(id);
	}

	
	public void updateObjById(AuUserHobby auUserHobby){
		mapper.updateObjById(auUserHobby);
	}
	
	
	
   /**
    * 添加
    * @param AuUserHobby 对象
    * @return 返回结果 void
	* @author junxu.yang
	* @since 2021-06-01
    */
	public void insertObj(AuUserHobby auUserHobby){
		mapper.insertObj(auUserHobby);
	}
	
	
   /**
    * 批量删除
    * @param List<AuUserHobby> 对象
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-06-01
    */
	public void batchInsertObj(List<AuUserHobby> list){
		mapper.batchInsertObj(list);
	}
	
	 /**
    * 根据主键查询对象
    * @param String 主键ID
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-06-01
    */
	public HashMap<String,Object> selectObjById(String auUserHobbyId){
		return mapper.selectObjById(auUserHobbyId);
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
	public PageInfo<AuUserHobby> pageList(HashMap<String,Object> params) throws Exception {
		Integer pageNum = (Integer)params.get("pageNum");
		Integer pageSize = (Integer)params.get("pageSize");
		Integer pageStart = (pageNum-1)* pageSize;
		params.put("pageStart",pageStart);
		List<AuUserHobby> list = mapper.pageList(params);
		PageInfo pagelist = new PageInfo(list);
		pagelist.setPageSize(pageSize);
		pagelist.setPageNum(pageNum);
		return new PageInfo(list);

	}
	
	
	public List<AuUserHobby> allList(HashMap<String,Object> param){
		return mapper.allList(param);
	}
	
	
	
}
