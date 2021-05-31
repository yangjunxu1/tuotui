package com.zycw.tuotui.readdao.sysresource;

import com.zycw.tuotui.entity.sysresource.SysResource;
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
 * 菜单 Mapper 接口
 * </p>
 *
 * @author junxu.yang
 * @since 2021-06-01
 */
public interface SysResourceMapper extends Mapper<SysResource> {
	
	public void deleteById(@Param("sysResourceId") String sysResourceId);
	
	public void deleteByIdLogic(@Param("sysResourceId") String sysResourceId);
	
	
	
	public void updateObjById(SysResource sysResource);
	
	
	
	public void insertObj(SysResource sysResource);
	
	public void batchInsertObj(List<SysResource> list);
	
	
	public Integer countNum(@Param("param") HashMap<String,Object> params);
	
	public HashMap<String,Object> selectObjById(@Param("sysResourceId") String sysResourceId);
	
	public List<SysResource> pageList(@Param("param") HashMap<String,Object> param);
	
	public List<SysResource> allList(@Param("param") HashMap<String,Object> param);
	
	
	
	
	
}
