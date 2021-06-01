package com.zycw.tuotui.iface.auchatfriends;



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
import com.zycw.tuotui.readdao.auchatfriends.AuChatFriendsMapper;
import com.zycw.tuotui.entity.auchatfriends.AuChatFriends;

import java.lang.String;
import java.lang.Integer;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
/**
 *  服务类
 */
    		


@Service("IAuChatFriendsService")
public class IAuChatFriendsService extends BaseService<AuChatFriendsMapper,AuChatFriends> {

   
   /**
    * 根据主键物理删除
    * @param String 主键ID
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-06-01
    */
	public void deleteById(String auChatFriendsId){
		mapper.deleteById(auChatFriendsId);
	}
	
   /**
    * 根据主键逻辑删除
    * @param String 主键ID
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-06-01
    */
	public void deleteByIdLogic(String auChatFriendsId){
		mapper.deleteByIdLogic(auChatFriendsId);
	}
	
	
   /**
    * 根据外键auChatFriendsUserId删除
    * @param String 外键ID
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-06-01
    */
	public void deleteByauChatFriendsUserId(String id){
		mapper.deleteByauChatFriendsUserId(id);
	}
	
   /**
    * 根据外键auChatFriendsFuserId删除
    * @param String 外键ID
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-06-01
    */
	public void deleteByauChatFriendsFuserId(String id){
		mapper.deleteByauChatFriendsFuserId(id);
	}

	
	public void updateObjById(AuChatFriends auChatFriends){
		mapper.updateObjById(auChatFriends);
	}
	
	
	
   /**
    * 添加
    * @param AuChatFriends 对象
    * @return 返回结果 void
	* @author junxu.yang
	* @since 2021-06-01
    */
	public void insertObj(AuChatFriends auChatFriends){
		mapper.insertObj(auChatFriends);
	}
	
	
   /**
    * 批量删除
    * @param List<AuChatFriends> 对象
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-06-01
    */
	public void batchInsertObj(List<AuChatFriends> list){
		mapper.batchInsertObj(list);
	}
	
	 /**
    * 根据主键查询对象
    * @param String 主键ID
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-06-01
    */
	public HashMap<String,Object> selectObjById(String auChatFriendsId){
		return mapper.selectObjById(auChatFriendsId);
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
	public PageInfo<AuChatFriends> pageList(HashMap<String,Object> params) throws Exception {
		Integer pageNum = (Integer)params.get("pageNum");
		Integer pageSize = (Integer)params.get("pageSize");
		Integer pageStart = (pageNum-1)* pageSize;
		params.put("pageStart",pageStart);
		List<AuChatFriends> list = mapper.pageList(params);
		PageInfo pagelist = new PageInfo(list);
		pagelist.setPageSize(pageSize);
		pagelist.setPageNum(pageNum);
		return new PageInfo(list);

	}
	
	
	public List<AuChatFriends> allList(HashMap<String,Object> param){
		return mapper.allList(param);
	}

	/**
	 * 获取总数
	 * @param page 分页条件
	 * @param HashMap<String,Object> 对象
	 * @return 返回结果
	 * @author junxu.yang
	 * @since 2021-05-30
	 */
	public Integer sysUserCountNum(HashMap<String,Object> params) {
		return mapper.sysUserCountNum(params);
	}

	/**
	 * 分页查询
	 * @param page 分页条件
	 * @param HashMap<String,Object> 对象
	 * @return 返回结果 PageInfo
	 * @author junxu.yang
	 * @since 2021-05-30
	 */
	public PageInfo<AuChatFriends> sysUserPageList(HashMap<String,Object> params) throws Exception {
		Integer pageNum = (Integer)params.get("pageNum");
		Integer pageSize = (Integer)params.get("pageSize");
		Integer pageStart = (pageNum-1)* pageSize;
		params.put("pageStart",pageStart);
		List<AuChatFriends> list = mapper.sysUserPageList(params);
		PageInfo pagelist = new PageInfo(list);
		pagelist.setPageSize(pageSize);
		pagelist.setPageNum(pageNum);
		return new PageInfo(list);

	}
}
