package com.zycw.tuotui.iface.sysadverttype;



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
import com.zycw.tuotui.readdao.sysadverttype.SysAdvertTypeMapper;
import com.zycw.tuotui.entity.sysadverttype.SysAdvertType;
import com.zycw.tuotui.iface.sysadvert.ISysAdvertService;

import java.lang.String;
import java.lang.Integer;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
/**
 * 广告类型 服务类
 */
    		


@Service("ISysAdvertTypeService")
public class ISysAdvertTypeService extends BaseService<SysAdvertTypeMapper,SysAdvertType> {

   
    @Autowired
    private ISysAdvertService iSysAdvertService;
   /**
    * 根据主键物理删除
    * @param String 主键ID
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-06-01
    */
	public void deleteById(String sysAdvertTypeId){
		mapper.deleteById(sysAdvertTypeId);
     	iSysAdvertService.deleteBysysAdvertTypeId(sysAdvertTypeId);
	}
	
   /**
    * 根据主键逻辑删除
    * @param String 主键ID
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-06-01
    */
	public void deleteByIdLogic(String sysAdvertTypeId){
		mapper.deleteByIdLogic(sysAdvertTypeId);
	}
	

	
	public void updateObjById(SysAdvertType sysAdvertType){
		mapper.updateObjById(sysAdvertType);
	}
	
	
	
   /**
    * 添加
    * @param SysAdvertType 对象
    * @return 返回结果 void
	* @author junxu.yang
	* @since 2021-06-01
    */
	public void insertObj(SysAdvertType sysAdvertType){
		mapper.insertObj(sysAdvertType);
	}
	
	
   /**
    * 批量删除
    * @param List<SysAdvertType> 对象
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-06-01
    */
	public void batchInsertObj(List<SysAdvertType> list){
		mapper.batchInsertObj(list);
	}
	
	 /**
    * 根据主键查询对象
    * @param String 主键ID
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-06-01
    */
	public HashMap<String,Object> selectObjById(String sysAdvertTypeId){
		return mapper.selectObjById(sysAdvertTypeId);
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
	public PageInfo<SysAdvertType> pageList(HashMap<String,Object> params) throws Exception {
		Integer pageNum = (Integer)params.get("pageNum");
		Integer pageSize = (Integer)params.get("pageSize");
		Integer pageStart = (pageNum-1)* pageSize;
		params.put("pageStart",pageStart);
		List<SysAdvertType> list = mapper.pageList(params);
		PageInfo pagelist = new PageInfo(list);
		pagelist.setPageSize(pageSize);
		pagelist.setPageNum(pageNum);
		return new PageInfo(list);

	}
	
	
	public List<SysAdvertType> allList(HashMap<String,Object> param){
		return mapper.allList(param);
	}
	
	
	
}
