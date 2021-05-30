package com.zycw.tuotui.readdao.sysprovince;

import com.zycw.tuotui.entity.sysprovince.SysProvince;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zycw.distributed.database.util.PageUtil;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import java.lang.String;
import java.lang.Integer;
import java.util.List;
import java.util.HashMap;

    		
/**
 * <p>
 * b1省 Mapper 接口
 * </p>
 *
 * @author junxu.yang
 * @since 2021-05-30
 */
public interface SysProvinceMapper extends Mapper<SysProvince> {
	
	public void deleteById(@Param("sysProvinceId") String sysProvinceId);
	
	public void deleteByIdLogic(@Param("sysProvinceId") String sysProvinceId);
	
	
	public void updateObjById(SysProvince sysProvince);
	
	
	
	public void insertObj(SysProvince sysProvince);
	
	public void batchInsertObj(List<SysProvince> list);
	
	
	public Integer countNum(@Param("param") HashMap<String,Object> params);
	
	public HashMap<String,Object> selectObjById(@Param("sysProvinceId") String sysProvinceId);
	
	public List<SysProvince> pageList(@Param("param") HashMap<String,Object> param);
	
	public List<SysProvince> allList(@Param("param") HashMap<String,Object> param);
	
	
	
	
	
}
