package com.zycw.tuotui.readdao.autaskhobby;

import com.zycw.tuotui.entity.autaskhobby.AuTaskHobby;
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
 * 爱好任务关联 Mapper 接口
 * </p>
 *
 * @author junxu.yang
 * @since 2021-05-28
 */
public interface AuTaskHobbyMapper extends Mapper<AuTaskHobby> {
	
	public void deleteById(@Param("auTaskHobbyId") String auTaskHobbyId);
	
	public void deleteByIdLogic(@Param("auTaskHobbyId") String auTaskHobbyId);
	
	
	public void updateObjById(AuTaskHobby auTaskHobby);
	
	
	
	public void insertObj(AuTaskHobby auTaskHobby);
	
	public void batchInsertObj(List<AuTaskHobby> list);
	
	
	public Integer countNum(@Param("param") HashMap<String,Object> params);
	
	public HashMap<String,Object> selectObjById(@Param("auTaskHobbyId") String auTaskHobbyId);
	
	public List<AuTaskHobby> pageList(@Param("param") HashMap<String,Object> param);
	
	public List<AuTaskHobby> allList(@Param("param") HashMap<String,Object> param);
	
	
	
	
	
}
