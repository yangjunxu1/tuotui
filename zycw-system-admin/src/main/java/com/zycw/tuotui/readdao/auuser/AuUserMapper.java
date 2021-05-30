package com.zycw.tuotui.readdao.auuser;

import com.zycw.tuotui.entity.auuser.AuUser;
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
 * 用户 Mapper 接口
 * </p>
 *
 * @author junxu.yang
 * @since 2021-05-30
 */
public interface AuUserMapper extends Mapper<AuUser> {
	
	public void deleteById(@Param("auUserId") String auUserId);
	
	public void deleteByIdLogic(@Param("auUserId") String auUserId);
	
	
	public void updateObjById(AuUser auUser);
	
	
	
	public void insertObj(AuUser auUser);
	
	public void batchInsertObj(List<AuUser> list);
	
	
	public Integer countNum(@Param("param") HashMap<String,Object> params);
	
	public HashMap<String,Object> selectObjById(@Param("auUserId") String auUserId);
	
	public List<AuUser> pageList(@Param("param") HashMap<String,Object> param);
	
	public List<AuUser> allList(@Param("param") HashMap<String,Object> param);
	
	
	
	
	
}
