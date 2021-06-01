package com.zycw.tuotui.readdao.auchatfriends;

import com.zycw.tuotui.entity.auchatfriends.AuChatFriends;
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
 *  Mapper 接口
 * </p>
 *
 * @author junxu.yang
 * @since 2021-06-01
 */
public interface AuChatFriendsMapper extends Mapper<AuChatFriends> {
	
	public void deleteById(@Param("auChatFriendsId") String auChatFriendsId);
	
	public void deleteByIdLogic(@Param("auChatFriendsId") String auChatFriendsId);
	
	public void deleteByauChatFriendsUserId(@Param("id") String id);
    
	public void deleteByauChatFriendsFuserId(@Param("id") String id);
    
	
	
	public void updateObjById(AuChatFriends auChatFriends);
	
	
	
	public void insertObj(AuChatFriends auChatFriends);
	
	public void batchInsertObj(List<AuChatFriends> list);
	
	
	public Integer countNum(@Param("param") HashMap<String,Object> params);
	
	public HashMap<String,Object> selectObjById(@Param("auChatFriendsId") String auChatFriendsId);
	
	public List<AuChatFriends> pageList(@Param("param") HashMap<String,Object> param);
	
	public List<AuChatFriends> allList(@Param("param") HashMap<String,Object> param);


	public Integer sysUserCountNum(@Param("param") HashMap<String,Object> params);

	public List<AuChatFriends> sysUserPageList(@Param("param") HashMap<String,Object> param);

	public Integer sysFriendsCountNum(@Param("param") HashMap<String,Object> params);

	public List<AuChatFriends> sysFriendsPageList(@Param("param") HashMap<String,Object> param);
}
