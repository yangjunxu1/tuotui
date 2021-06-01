package com.zycw.tuotui.entity.auchatfriends;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @since 2021-06-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name= "au_chat_friends")
@ApiModel(value="AuChatFriends", description="")
public class AuChatFriends implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name="auChatFriendsId",value = "ID")
	
    	@Id
    	@Column(name = "id")
	private Integer auChatFriendsId;

    @ApiModelProperty(name="auChatFriendsUserId",value = "用户")
    	@Column(name = "user_id")
	private Integer auChatFriendsUserId;

    @ApiModelProperty(name="auChatFriendsFuserId",value = "好友")
    	@Column(name = "fuser_id")
	private Integer auChatFriendsFuserId;

    @ApiModelProperty(name="auChatFriendsAddtime",value = "i添加时间")
    	@Column(name = "addtime")
	private Date auChatFriendsAddtime;

    @ApiModelProperty(name="auChatFriendsDelFlag",value = "逻辑删除:0-未删除,1-已删除")
        	@Column(name = "delFlag")
	private Integer auChatFriendsDelFlag;


}
