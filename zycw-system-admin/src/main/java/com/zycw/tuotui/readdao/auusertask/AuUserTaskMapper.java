package com.zycw.tuotui.readdao.auusertask;

import com.zycw.tuotui.entity.auusertask.AuUserTask;
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
 * 会员任务 Mapper 接口
 * </p>
 *
 * @author junxu.yang
 * @since 2021-05-30
 */
public interface AuUserTaskMapper extends Mapper<AuUserTask> {
	
	public void deleteById(@Param("auUserTaskId") String auUserTaskId);
	
	public void deleteByIdLogic(@Param("auUserTaskId") String auUserTaskId);
	
	
	public void updateObjById(AuUserTask auUserTask);
	
	
	
	public void insertObj(AuUserTask auUserTask);
	
	public void batchInsertObj(List<AuUserTask> list);
	
	
	public Integer countNum(@Param("param") HashMap<String,Object> params);
	
	public HashMap<String,Object> selectObjById(@Param("auUserTaskId") String auUserTaskId);
	
	public List<AuUserTask> pageList(@Param("param") HashMap<String,Object> param);
	
	public List<AuUserTask> allList(@Param("param") HashMap<String,Object> param);
	
	
	
	
	
}
