package com.zycw.tuotui.readdao.aucustomtask;

import com.zycw.tuotui.entity.aucustomtask.AuCustomTask;
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
 * 企业任务 Mapper 接口
 * </p>
 *
 * @author junxu.yang
 * @since 2021-06-01
 */
public interface AuCustomTaskMapper extends Mapper<AuCustomTask> {
	
	public void deleteById(@Param("auCustomTaskId") String auCustomTaskId);
	
	public void deleteByIdLogic(@Param("auCustomTaskId") String auCustomTaskId);
	
	public void deleteByauCustomTaskCompaynId(@Param("id") String id);
    
	public void deleteByauCustomTaskUserId(@Param("id") String id);
    
	public void deleteByauCustomTaskAppId(@Param("id") String id);
    
	public void deleteByauCustomTaskTaskType(@Param("id") String id);
    
	public void deleteByauCustomTaskTaskDepth(@Param("id") String id);
    
	public void deleteByauCustomTaskTaskActivity(@Param("id") String id);
    
	public void deleteByauCustomTaskProvinceId(@Param("id") String id);
    
	public void deleteByauCustomTaskCityId(@Param("id") String id);
    
	public void deleteByauCustomTaskAreaId(@Param("id") String id);
    
	public void deleteByauCustomTaskOccupationId(@Param("id") String id);
    
	
	
	public void updateObjById(AuCustomTask auCustomTask);
	
	
	
	public void insertObj(AuCustomTask auCustomTask);
	
	public void batchInsertObj(List<AuCustomTask> list);
	
	
	public Integer countNum(@Param("param") HashMap<String,Object> params);
	
	public HashMap<String,Object> selectObjById(@Param("auCustomTaskId") String auCustomTaskId);
	
	public List<AuCustomTask> pageList(@Param("param") HashMap<String,Object> param);
	
	public List<AuCustomTask> allList(@Param("param") HashMap<String,Object> param);
	
	
	
	
	
}
