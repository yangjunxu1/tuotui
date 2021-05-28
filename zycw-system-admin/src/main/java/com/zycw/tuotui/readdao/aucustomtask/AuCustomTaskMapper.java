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
 * @since 2021-05-28
 */
public interface AuCustomTaskMapper extends Mapper<AuCustomTask> {
	
	public void deleteById(@Param("auCustomTaskId") String auCustomTaskId);
	
	public void deleteByIdLogic(@Param("auCustomTaskId") String auCustomTaskId);
	
	
	public void updateObjById(AuCustomTask auCustomTask);
	
	
	
	public void insertObj(AuCustomTask auCustomTask);
	
	public void batchInsertObj(List<AuCustomTask> list);
	
	
	public Integer countNum(@Param("param") HashMap<String,Object> params);
	
	public HashMap<String,Object> selectObjById(@Param("auCustomTaskId") String auCustomTaskId);
	
	public List<AuCustomTask> pageList(@Param("param") HashMap<String,Object> param);
	
	public List<AuCustomTask> allList(@Param("param") HashMap<String,Object> param);
	
	
	
	
	
}
