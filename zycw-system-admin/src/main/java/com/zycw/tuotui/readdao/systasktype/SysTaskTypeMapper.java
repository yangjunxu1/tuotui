package com.zycw.tuotui.readdao.systasktype;

import com.zycw.tuotui.entity.systasktype.SysTaskType;
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
 * b任务类型 Mapper 接口
 * </p>
 *
 * @author junxu.yang
 * @since 2021-05-30
 */
public interface SysTaskTypeMapper extends Mapper<SysTaskType> {
	
	public void deleteById(@Param("sysTaskTypeId") String sysTaskTypeId);
	
	public void deleteByIdLogic(@Param("sysTaskTypeId") String sysTaskTypeId);
	
	
	public void updateObjById(SysTaskType sysTaskType);
	
	
	
	public void insertObj(SysTaskType sysTaskType);
	
	public void batchInsertObj(List<SysTaskType> list);
	
	
	public Integer countNum(@Param("param") HashMap<String,Object> params);
	
	public HashMap<String,Object> selectObjById(@Param("sysTaskTypeId") String sysTaskTypeId);
	
	public List<SysTaskType> pageList(@Param("param") HashMap<String,Object> param);
	
	public List<SysTaskType> allList(@Param("param") HashMap<String,Object> param);
	
	
	
	
	
}
