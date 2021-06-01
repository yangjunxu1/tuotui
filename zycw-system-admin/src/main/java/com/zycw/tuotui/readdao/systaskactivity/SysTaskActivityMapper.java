package com.zycw.tuotui.readdao.systaskactivity;

import com.zycw.tuotui.entity.systaskactivity.SysTaskActivity;
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
 * 任务活动信息 Mapper 接口
 * </p>
 *
 * @author junxu.yang
 * @since 2021-06-01
 */
public interface SysTaskActivityMapper extends Mapper<SysTaskActivity> {
	
	public void deleteById(@Param("sysTaskActivityId") String sysTaskActivityId);
	
	public void deleteByIdLogic(@Param("sysTaskActivityId") String sysTaskActivityId);
	
	
	
	public void updateObjById(SysTaskActivity sysTaskActivity);
	
	
	
	public void insertObj(SysTaskActivity sysTaskActivity);
	
	public void batchInsertObj(List<SysTaskActivity> list);
	
	
	public Integer countNum(@Param("param") HashMap<String,Object> params);
	
	public HashMap<String,Object> selectObjById(@Param("sysTaskActivityId") String sysTaskActivityId);
	
	public List<SysTaskActivity> pageList(@Param("param") HashMap<String,Object> param);
	
	public List<SysTaskActivity> allList(@Param("param") HashMap<String,Object> param);
	
	
	
	
	
}
