package com.zycw.tuotui.readdao.sysrole;

import com.zycw.tuotui.entity.sysrole.SysRole;
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
 * b角色 Mapper 接口
 * </p>
 *
 * @author junxu.yang
 * @since 2021-06-01
 */
public interface SysRoleMapper extends Mapper<SysRole> {
	
	public void deleteById(@Param("sysRoleId") String sysRoleId);
	
	public void deleteByIdLogic(@Param("sysRoleId") String sysRoleId);
	
	
	
	public void updateObjById(SysRole sysRole);
	
	
	
	public void insertObj(SysRole sysRole);
	
	public void batchInsertObj(List<SysRole> list);
	
	
	public Integer countNum(@Param("param") HashMap<String,Object> params);
	
	public HashMap<String,Object> selectObjById(@Param("sysRoleId") String sysRoleId);
	
	public List<SysRole> pageList(@Param("param") HashMap<String,Object> param);
	
	public List<SysRole> allList(@Param("param") HashMap<String,Object> param);
	
	
	
	
	
}
