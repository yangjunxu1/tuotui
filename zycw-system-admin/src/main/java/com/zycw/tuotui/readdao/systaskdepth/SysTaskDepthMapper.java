package com.zycw.tuotui.readdao.systaskdepth;

import com.zycw.tuotui.entity.systaskdepth.SysTaskDepth;
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
 * b任务深度 Mapper 接口
 * </p>
 *
 * @author junxu.yang
 * @since 2021-05-30
 */
public interface SysTaskDepthMapper extends Mapper<SysTaskDepth> {
	
	public void deleteById(@Param("sysTaskDepthId") String sysTaskDepthId);
	
	public void deleteByIdLogic(@Param("sysTaskDepthId") String sysTaskDepthId);
	
	
	public void updateObjById(SysTaskDepth sysTaskDepth);
	
	
	
	public void insertObj(SysTaskDepth sysTaskDepth);
	
	public void batchInsertObj(List<SysTaskDepth> list);
	
	
	public Integer countNum(@Param("param") HashMap<String,Object> params);
	
	public HashMap<String,Object> selectObjById(@Param("sysTaskDepthId") String sysTaskDepthId);
	
	public List<SysTaskDepth> pageList(@Param("param") HashMap<String,Object> param);
	
	public List<SysTaskDepth> allList(@Param("param") HashMap<String,Object> param);
	
	
	
	
	
}
