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
import com.zycw.tuotui.iface.auaccountconsume.IAuAccountConsumeService;
import com.zycw.tuotui.iface.auaccountwithdrawal.IAuAccountWithdrawalService;
import com.zycw.tuotui.iface.auchatfriends.IAuChatFriendsService;
import com.zycw.tuotui.iface.aucustomtask.IAuCustomTaskService;
import com.zycw.tuotui.iface.auusertask.IAuUserTaskService;

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

   
    @Autowired
    private IAuAccountConsumeService iAuAccountConsumeService;
    @Autowired
    private IAuAccountWithdrawalService iAuAccountWithdrawalService;
    @Autowired
    private IAuChatFriendsService iAuChatFriendsService;
    @Autowired
    private IAuCustomTaskService iAuCustomTaskService;
    @Autowired
    private IAuUserTaskService iAuUserTaskService;
   /**
    * 根据主键物理删除
    * @param String 主键ID
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-06-01
    */
	public void deleteById(String auUserId){
		mapper.deleteById(auUserId);
     	iAuAccountConsumeService.deleteByauAccountConsumeUserId(auUserId);
     	iAuAccountWithdrawalService.deleteByauAccountWithdrawalUserId(auUserId);
     	iAuChatFriendsService.deleteByauChatFriendsUserId(auUserId);
     	iAuCustomTaskService.deleteByauCustomTaskUserId(auUserId);
     	iAuUserTaskService.deleteByauUserTaskUserId(auUserId);
	}
	
   /**
    * 根据主键逻辑删除
    * @param String 主键ID
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-06-01
    */
	public void deleteByIdLogic(String auUserId){
		mapper.deleteByIdLogic(auUserId);
	}
	
	
   /**
    * 根据外键auUserProvinceId删除
    * @param String 外键ID
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-06-01
    */
	public void deleteByauUserProvinceId(String id){
		mapper.deleteByauUserProvinceId(id);
	}
	
   /**
    * 根据外键auUserCityId删除
    * @param String 外键ID
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-06-01
    */
	public void deleteByauUserCityId(String id){
		mapper.deleteByauUserCityId(id);
	}
	
   /**
    * 根据外键auUserAreaId删除
    * @param String 外键ID
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-06-01
    */
	public void deleteByauUserAreaId(String id){
		mapper.deleteByauUserAreaId(id);
	}
	
   /**
    * 根据外键auUserOccupationId删除
    * @param String 外键ID
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-06-01
    */
	public void deleteByauUserOccupationId(String id){
		mapper.deleteByauUserOccupationId(id);
	}

	
	public void updateObjById(AuUser auUser){
		mapper.updateObjById(auUser);
	}
	
	
	
   /**
    * 添加
    * @param AuUser 对象
    * @return 返回结果 void
	* @author junxu.yang
	* @since 2021-06-01
    */
	public void insertObj(AuUser auUser){
		mapper.insertObj(auUser);
	}
	
	
   /**
    * 批量删除
    * @param List<AuUser> 对象
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-06-01
    */
	public void batchInsertObj(List<AuUser> list){
		mapper.batchInsertObj(list);
	}
	
	 /**
    * 根据主键查询对象
    * @param String 主键ID
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-06-01
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
