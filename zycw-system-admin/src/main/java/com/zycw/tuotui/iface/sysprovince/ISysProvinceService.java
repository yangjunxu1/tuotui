package com.zycw.tuotui.iface.sysprovince;



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
import com.zycw.tuotui.readdao.sysprovince.SysProvinceMapper;
import com.zycw.tuotui.entity.sysprovince.SysProvince;
import com.zycw.tuotui.iface.aucustomtask.IAuCustomTaskService;
import com.zycw.tuotui.iface.auuser.IAuUserService;
import com.zycw.tuotui.iface.sysarea.ISysAreaService;
import com.zycw.tuotui.iface.syscity.ISysCityService;

import java.lang.String;
import java.lang.Integer;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
/**
 * b1省 服务类
 */
    		


@Service("ISysProvinceService")
public class ISysProvinceService extends BaseService<SysProvinceMapper,SysProvince> {

   
    @Autowired
    private IAuCustomTaskService iAuCustomTaskService;
    @Autowired
    private IAuUserService iAuUserService;
    @Autowired
    private ISysAreaService iSysAreaService;
    @Autowired
    private ISysCityService iSysCityService;
   /**
    * 根据主键物理删除
    * @param String 主键ID
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-06-01
    */
	public void deleteById(String sysProvinceId){
		mapper.deleteById(sysProvinceId);
     	iAuCustomTaskService.deleteByauCustomTaskProvinceId(sysProvinceId);
     	iAuUserService.deleteByauUserProvinceId(sysProvinceId);
     	iSysAreaService.deleteBysysAreaProvinceId(sysProvinceId);
     	iSysCityService.deleteBysysCityProvinceId(sysProvinceId);
	}
	
   /**
    * 根据主键逻辑删除
    * @param String 主键ID
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-06-01
    */
	public void deleteByIdLogic(String sysProvinceId){
		mapper.deleteByIdLogic(sysProvinceId);
	}
	

	
	public void updateObjById(SysProvince sysProvince){
		mapper.updateObjById(sysProvince);
	}
	
	
	
   /**
    * 添加
    * @param SysProvince 对象
    * @return 返回结果 void
	* @author junxu.yang
	* @since 2021-06-01
    */
	public void insertObj(SysProvince sysProvince){
		mapper.insertObj(sysProvince);
	}
	
	
   /**
    * 批量删除
    * @param List<SysProvince> 对象
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-06-01
    */
	public void batchInsertObj(List<SysProvince> list){
		mapper.batchInsertObj(list);
	}
	
	 /**
    * 根据主键查询对象
    * @param String 主键ID
    * @return 返回结果
	* @author junxu.yang
	* @since 2021-06-01
    */
	public HashMap<String,Object> selectObjById(String sysProvinceId){
		return mapper.selectObjById(sysProvinceId);
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
	public PageInfo<SysProvince> pageList(HashMap<String,Object> params) throws Exception {
		Integer pageNum = (Integer)params.get("pageNum");
		Integer pageSize = (Integer)params.get("pageSize");
		Integer pageStart = (pageNum-1)* pageSize;
		params.put("pageStart",pageStart);
		List<SysProvince> list = mapper.pageList(params);
		PageInfo pagelist = new PageInfo(list);
		pagelist.setPageSize(pageSize);
		pagelist.setPageNum(pageNum);
		return new PageInfo(list);

	}
	
	
	public List<SysProvince> allList(HashMap<String,Object> param){
		return mapper.allList(param);
	}
	
	
	
}
