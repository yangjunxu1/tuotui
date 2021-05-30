package com.zycw.tuotui.readdao.sysuser;

import com.zycw.tuotui.entity.sysuser.SysUser;
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
 * 系统管理员 Mapper 接口
 * </p>
 *
 * @author junxu.yang
 * @since 2021-05-30
 */
public interface SysUserMapper extends Mapper<SysUser> {
	
	public void deleteById(@Param("sysUserId") String sysUserId);
	
	public void deleteByIdLogic(@Param("sysUserId") String sysUserId);
	
	
	public void updateObjById(SysUser sysUser);
	
	
	
	public void insertObj(SysUser sysUser);
	
	public void batchInsertObj(List<SysUser> list);
	
	
	public Integer countNum(@Param("param") HashMap<String,Object> params);
	
	public HashMap<String,Object> selectObjById(@Param("sysUserId") String sysUserId);
	
	public List<SysUser> pageList(@Param("param") HashMap<String,Object> param);
	
	public List<SysUser> allList(@Param("param") HashMap<String,Object> param);
	
	
	
	
	
}
