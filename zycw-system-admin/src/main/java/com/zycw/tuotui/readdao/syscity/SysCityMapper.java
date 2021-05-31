package com.zycw.tuotui.readdao.syscity;

import com.zycw.tuotui.entity.syscity.SysCity;
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
 * b2市 Mapper 接口
 * </p>
 *
 * @author junxu.yang
 * @since 2021-06-01
 */
public interface SysCityMapper extends Mapper<SysCity> {
	
	public void deleteById(@Param("sysCityId") String sysCityId);
	
	public void deleteByIdLogic(@Param("sysCityId") String sysCityId);
	
	public void deleteBysysCityProvinceId(@Param("id") String id);
    
	
	
	public void updateObjById(SysCity sysCity);
	
	
	
	public void insertObj(SysCity sysCity);
	
	public void batchInsertObj(List<SysCity> list);
	
	
	public Integer countNum(@Param("param") HashMap<String,Object> params);
	
	public HashMap<String,Object> selectObjById(@Param("sysCityId") String sysCityId);
	
	public List<SysCity> pageList(@Param("param") HashMap<String,Object> param);
	
	public List<SysCity> allList(@Param("param") HashMap<String,Object> param);
	
	
	
	
	
}
