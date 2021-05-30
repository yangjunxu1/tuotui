package com.zycw.tuotui.readdao.chatfriends;

import com.zycw.tuotui.entity.chatfriends.ChatFriends;
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
 * @since 2021-05-30
 */
public interface ChatFriendsMapper extends Mapper<ChatFriends> {
	
	public void deleteById(@Param("chatFriendsId") String chatFriendsId);
	
	public void deleteByIdLogic(@Param("chatFriendsId") String chatFriendsId);
	
	
	public void updateObjById(ChatFriends chatFriends);
	
	
	
	public void insertObj(ChatFriends chatFriends);
	
	public void batchInsertObj(List<ChatFriends> list);
	
	
	public Integer countNum(@Param("param") HashMap<String,Object> params);
	
	public HashMap<String,Object> selectObjById(@Param("chatFriendsId") String chatFriendsId);
	
	public List<ChatFriends> pageList(@Param("param") HashMap<String,Object> param);
	
	public List<ChatFriends> allList(@Param("param") HashMap<String,Object> param);
	
	
	
	
	
}
