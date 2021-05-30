package com.zycw.tuotui.entity.chatfriends;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author junxu.yang
 * @since 2021-05-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name= "chat_friends")
@ApiModel(value="ChatFriends", description="")
public class ChatFriends implements Serializable {

    private static final long serialVersionUID = 1L;

	
    	@Id
    	@Column(name = "id")
	private Integer chatFriendsId;

    @ApiModelProperty(name="chatFriendsUserid",value = "用户id")
    	@Column(name = "userid")
	private String chatFriendsUserid;

    @ApiModelProperty(name="chatFriendsFuserid",value = "好友id")
    	@Column(name = "fuserid")
	private String chatFriendsFuserid;

    	@Column(name = "addtime")
	private Date chatFriendsAddtime;


}
