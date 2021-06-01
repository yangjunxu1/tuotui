package com.zycw.tuotui.readdao.auchatmsg;

import com.zycw.tuotui.entity.auchatmsg.AuChatMsg;
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
 * 客服信息记录 Mapper 接口
 * </p>
 *
 * @author junxu.yang
 * @since 2021-06-01
 */
public interface AuChatMsgMapper extends Mapper<AuChatMsg> {
	
	public void deleteById(@Param("auChatMsgId") String auChatMsgId);
	
	public void deleteByIdLogic(@Param("auChatMsgId") String auChatMsgId);
	
	
	
	public void updateObjById(AuChatMsg auChatMsg);
	
	
	
	public void insertObj(AuChatMsg auChatMsg);
	
	public void batchInsertObj(List<AuChatMsg> list);
	
	
	public Integer countNum(@Param("param") HashMap<String,Object> params);
	
	public HashMap<String,Object> selectObjById(@Param("auChatMsgId") String auChatMsgId);
	
	public List<AuChatMsg> pageList(@Param("param") HashMap<String,Object> param);
	
	public List<AuChatMsg> allList(@Param("param") HashMap<String,Object> param);
	
	
	
	
	
}
