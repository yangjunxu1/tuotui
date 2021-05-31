package com.zycw.tuotui.entity.auchatmsg;

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
 * 客服信息记录
 * </p>
 *
 * @author junxu.yang
 * @since 2021-06-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name= "au_chat_msg")
@ApiModel(value="AuChatMsg", description="客服信息记录")
public class AuChatMsg implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name="auChatMsgId",value = "ID")
	
    	@Id
    	@Column(name = "id")
	private Integer auChatMsgId;

    @ApiModelProperty(name="auChatMsgSenduserid",value = "lv用户")
    	@Column(name = "senduserid")
	private Integer auChatMsgSenduserid;

    @ApiModelProperty(name="auChatMsgReciveuserid",value = "lv客服")
    	@Column(name = "reciveuserid")
	private Integer auChatMsgReciveuserid;

    @ApiModelProperty(name="auChatMsgSendtime",value = "lvi聊天时间")
    	@Column(name = "sendtime")
	private Date auChatMsgSendtime;

    @ApiModelProperty(name="auChatMsgSendtext",value = "vl聊天内容")
    	@Column(name = "sendtext")
	private String auChatMsgSendtext;

    @ApiModelProperty(name="auChatMsgMsgtype",value = "消息类型")
    	@Column(name = "msgtype")
	private String auChatMsgMsgtype;

    @ApiModelProperty(name="auChatMsgDelFlag",value = "逻辑删除:0-未删除,1-已删除")
        	@Column(name = "delFlag")
	private Integer auChatMsgDelFlag;


}
