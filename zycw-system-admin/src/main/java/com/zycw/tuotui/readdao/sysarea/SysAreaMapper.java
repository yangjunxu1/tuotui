package com.zycw.tuotui.readdao.sysarea;

import com.zycw.tuotui.entity.sysarea.SysArea;
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
 * b3区 Mapper 接口
 * </p>
 *
 * @author junxu.yang
 * @since 2021-05-28
 */
public interface SysAreaMapper extends Mapper<SysArea> {
	
	public void deleteById(@Param("sysAreaId") String sysAreaId);
	
	public void deleteByIdLogic(@Param("sysAreaId") String sysAreaId);
	
	
	public void updateObjById(SysArea sysArea);
	
	
	
	public void insertObj(SysArea sysArea);
	
	public void batchInsertObj(List<SysArea> list);
	
	
	public Integer countNum(@Param("param") HashMap<String,Object> params);
	
	public HashMap<String,Object> selectObjById(@Param("sysAreaId") String sysAreaId);
	
	public List<SysArea> pageList(@Param("param") HashMap<String,Object> param);
	
	public List<SysArea> allList(@Param("param") HashMap<String,Object> param);
	
	
	
	
	
}
