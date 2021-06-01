package com.zycw.tuotui.readdao.sysroleresource;

import com.zycw.tuotui.entity.sysroleresource.SysRoleResource;
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
 *  Mapper 接口
 * </p>
 *
 * @author junxu.yang
 * @since 2021-06-01
 */
public interface SysRoleResourceMapper extends Mapper<SysRoleResource> {
	
	public void deleteById(@Param("sysRoleResourceId") String sysRoleResourceId);
	
	public void deleteByIdLogic(@Param("sysRoleResourceId") String sysRoleResourceId);
	
	public void deleteBysysRoleResourceRoleId(@Param("id") String id);
    
	public void deleteBysysRoleResourceResourceId(@Param("id") String id);
    
	
	
	public void updateObjById(SysRoleResource sysRoleResource);
	
	
	
	public void insertObj(SysRoleResource sysRoleResource);
	
	public void batchInsertObj(List<SysRoleResource> list);
	
	
	public Integer countNum(@Param("param") HashMap<String,Object> params);
	
	public HashMap<String,Object> selectObjById(@Param("sysRoleResourceId") String sysRoleResourceId);
	
	public List<SysRoleResource> pageList(@Param("param") HashMap<String,Object> param);
	
	public List<SysRoleResource> allList(@Param("param") HashMap<String,Object> param);
	
	
	
	
	
}
