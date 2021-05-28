package com.zycw.tuotui.readdao.syshobby;

import com.zycw.tuotui.entity.syshobby.SysHobby;
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
 * b爱好 Mapper 接口
 * </p>
 *
 * @author junxu.yang
 * @since 2021-05-28
 */
public interface SysHobbyMapper extends Mapper<SysHobby> {
	
	public void deleteById(@Param("sysHobbyId") String sysHobbyId);
	
	public void deleteByIdLogic(@Param("sysHobbyId") String sysHobbyId);
	
	
	public void updateObjById(SysHobby sysHobby);
	
	
	
	public void insertObj(SysHobby sysHobby);
	
	public void batchInsertObj(List<SysHobby> list);
	
	
	public Integer countNum(@Param("param") HashMap<String,Object> params);
	
	public HashMap<String,Object> selectObjById(@Param("sysHobbyId") String sysHobbyId);
	
	public List<SysHobby> pageList(@Param("param") HashMap<String,Object> param);
	
	public List<SysHobby> allList(@Param("param") HashMap<String,Object> param);
	
	
	
	
	
}
