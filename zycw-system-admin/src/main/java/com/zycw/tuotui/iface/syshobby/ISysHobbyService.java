package com.zycw.tuotui.iface.syshobby;



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
import com.zycw.tuotui.readdao.syshobby.SysHobbyMapper;
import com.zycw.tuotui.entity.syshobby.SysHobby;
import com.zycw.tuotui.iface.autaskhobby.IAuTaskHobbyService;
import com.zycw.tuotui.iface.auuserhobby.IAuUserHobbyService;
import com.zycw.tuotui.iface.auuserhobby.IAuUserHobbyService;

import java.lang.String;
import java.lang.Integer;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
/**
 * b爱好 服务类
 */
    		


@Service("ISysHobbyService")
public class ISysHobbyService extends BaseService<SysHobbyMapper,SysHobby> {

   
    @Autowired
    private IAuTaskHobbyService iAuTaskHobbyService;
    @Autowired
    private IAuUserHobbyService iAuUserHobbyService;
    @Autowired
    private IAuUserHobbyService iAuUserHobbyService;
   /**
    * 根据主键物理删除
    * @param String 主键ID
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-06-01
    */
	public void deleteById(String sysHobbyId){
		mapper.deleteById(sysHobbyId);
     	iAuTaskHobbyService.deleteByauTaskHobbyHobbyId(sysHobbyId);
     	iAuUserHobbyService.deleteByauUserHobbyUserId(sysHobbyId);
     	iAuUserHobbyService.deleteByauUserHobbyHobbyId(sysHobbyId);
	}
	
   /**
    * 根据主键逻辑删除
    * @param String 主键ID
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-06-01
    */
	public void deleteByIdLogic(String sysHobbyId){
		mapper.deleteByIdLogic(sysHobbyId);
	}
	

	
	public void updateObjById(SysHobby sysHobby){
		mapper.updateObjById(sysHobby);
	}
	
	
	
   /**
    * 添加
    * @param SysHobby 对象
    * @return 返回结果 void
	* @author junxu.yang
	* @since 2021-06-01
    */
	public void insertObj(SysHobby sysHobby){
		mapper.insertObj(sysHobby);
	}
	
	
   /**
    * 批量删除
    * @param List<SysHobby> 对象
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-06-01
    */
	public void batchInsertObj(List<SysHobby> list){
		mapper.batchInsertObj(list);
	}
	
	 /**
    * 根据主键查询对象
    * @param String 主键ID
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-06-01
    */
	public HashMap<String,Object> selectObjById(String sysHobbyId){
		return mapper.selectObjById(sysHobbyId);
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
	public PageInfo<SysHobby> pageList(HashMap<String,Object> params) throws Exception {
		Integer pageNum = (Integer)params.get("pageNum");
		Integer pageSize = (Integer)params.get("pageSize");
		Integer pageStart = (pageNum-1)* pageSize;
		params.put("pageStart",pageStart);
		List<SysHobby> list = mapper.pageList(params);
		PageInfo pagelist = new PageInfo(list);
		pagelist.setPageSize(pageSize);
		pagelist.setPageNum(pageNum);
		return new PageInfo(list);

	}
	
	
	public List<SysHobby> allList(HashMap<String,Object> param){
		return mapper.allList(param);
	}
	
	
	
}
