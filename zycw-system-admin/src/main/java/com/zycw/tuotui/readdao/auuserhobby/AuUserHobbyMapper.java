package com.zycw.tuotui.readdao.auuserhobby;

import com.zycw.tuotui.entity.auuserhobby.AuUserHobby;
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
 * 用户爱好关联 Mapper 接口
 * </p>
 *
 * @author junxu.yang
 * @since 2021-06-01
 */
public interface AuUserHobbyMapper extends Mapper<AuUserHobby> {
	
	public void deleteById(@Param("auUserHobbyId") String auUserHobbyId);
	
	public void deleteByIdLogic(@Param("auUserHobbyId") String auUserHobbyId);
	
	public void deleteByauUserHobbyUserId(@Param("id") String id);
    
	public void deleteByauUserHobbyHobbyId(@Param("id") String id);
    
	
	
	public void updateObjById(AuUserHobby auUserHobby);
	
	
	
	public void insertObj(AuUserHobby auUserHobby);
	
	public void batchInsertObj(List<AuUserHobby> list);
	
	
	public Integer countNum(@Param("param") HashMap<String,Object> params);
	
	public HashMap<String,Object> selectObjById(@Param("auUserHobbyId") String auUserHobbyId);
	
	public List<AuUserHobby> pageList(@Param("param") HashMap<String,Object> param);
	
	public List<AuUserHobby> allList(@Param("param") HashMap<String,Object> param);
	
	
	
	
	
}
