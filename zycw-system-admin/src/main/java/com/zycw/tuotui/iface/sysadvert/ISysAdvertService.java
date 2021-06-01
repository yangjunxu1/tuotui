package com.zycw.tuotui.iface.sysadvert;



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
import com.zycw.tuotui.readdao.sysadvert.SysAdvertMapper;
import com.zycw.tuotui.entity.sysadvert.SysAdvert;

import java.lang.String;
import java.lang.Integer;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
/**
 * 广告协议信息 服务类
 */
    		


@Service("ISysAdvertService")
public class ISysAdvertService extends BaseService<SysAdvertMapper,SysAdvert> {

   
   /**
    * 根据主键物理删除
    * @param String 主键ID
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-06-01
    */
	public void deleteById(String sysAdvertId){
		mapper.deleteById(sysAdvertId);
	}
	
   /**
    * 根据主键逻辑删除
    * @param String 主键ID
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-06-01
    */
	public void deleteByIdLogic(String sysAdvertId){
		mapper.deleteByIdLogic(sysAdvertId);
	}
	
	
   /**
    * 根据外键sysAdvertPostitionId删除
    * @param String 外键ID
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-06-01
    */
	public void deleteBysysAdvertPostitionId(String id){
		mapper.deleteBysysAdvertPostitionId(id);
	}
	
   /**
    * 根据外键sysAdvertTypeId删除
    * @param String 外键ID
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-06-01
    */
	public void deleteBysysAdvertTypeId(String id){
		mapper.deleteBysysAdvertTypeId(id);
	}

	
	public void updateObjById(SysAdvert sysAdvert){
		mapper.updateObjById(sysAdvert);
	}
	
	
	
   /**
    * 添加
    * @param SysAdvert 对象
    * @return 返回结果 void
	* @author junxu.yang
	* @since 2021-06-01
    */
	public void insertObj(SysAdvert sysAdvert){
		mapper.insertObj(sysAdvert);
	}
	
	
   /**
    * 批量删除
    * @param List<SysAdvert> 对象
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-06-01
    */
	public void batchInsertObj(List<SysAdvert> list){
		mapper.batchInsertObj(list);
	}
	
	 /**
    * 根据主键查询对象
    * @param String 主键ID
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-06-01
    */
	public HashMap<String,Object> selectObjById(String sysAdvertId){
		return mapper.selectObjById(sysAdvertId);
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
	public PageInfo<SysAdvert> pageList(HashMap<String,Object> params) throws Exception {
		Integer pageNum = (Integer)params.get("pageNum");
		Integer pageSize = (Integer)params.get("pageSize");
		Integer pageStart = (pageNum-1)* pageSize;
		params.put("pageStart",pageStart);
		List<SysAdvert> list = mapper.pageList(params);
		PageInfo pagelist = new PageInfo(list);
		pagelist.setPageSize(pageSize);
		pagelist.setPageNum(pageNum);
		return new PageInfo(list);

	}
   
	/**
	 * <p>
	 * getAdvertBypositonAndType 根据广告位置和类型查询广告
	 * </p>
	 *
	 * @author junxu.yang
	 * @since 2021-06-01
	 */
	
	public List<HashMap> getAdvertBypositonAndType( HashMap<String,Object> param){
		List<HashMap> result = mapper.getAdvertBypositonAndType( param);
		return result;
	}
	
	
	public List<SysAdvert> allList(HashMap<String,Object> param){
		return mapper.allList(param);
	}
	
	
	
}
