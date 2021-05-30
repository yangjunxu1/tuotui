package com.zycw.tuotui.entity.sysadvert;

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
 * 广告协议信息
 * </p>
 *
 * @author junxu.yang
 * @since 2021-05-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name= "sys_advert")
@ApiModel(value="SysAdvert", description="广告协议信息")
public class SysAdvert implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name="sysAdvertId",value = "hID")
	
    	@Id
    	@Column(name = "id")
	private Integer sysAdvertId;

    @ApiModelProperty(name="sysAdvertTitle",value = "lv广告标题")
    	@Column(name = "title")
	private String sysAdvertTitle;

    	@Column(name = "postition_id")
	private Integer sysAdvertPostitionId;

    @ApiModelProperty(name="sysAdvertPicAddr",value = "lv广告图片地址")
    	@Column(name = "pic_addr")
	private String sysAdvertPicAddr;

    @ApiModelProperty(name="sysAdvertUrl",value = "lv外链地址")
    	@Column(name = "url")
	private String sysAdvertUrl;

    @ApiModelProperty(name="sysAdvertRouter",value = "lv内链地址")
    	@Column(name = "router")
	private String sysAdvertRouter;

    @ApiModelProperty(name="sysAdvertType",value = "lvS广告类型:0-协议,1-站内广告，2-外链广告")
    	@Column(name = "type")
	private Integer sysAdvertType;

    @ApiModelProperty(name="sysAdvertContent",value = "文字介绍")
    	@Column(name = "content")
	private String sysAdvertContent;

    @ApiModelProperty(name="sysAdvertCreatedTime",value = "il创建时间")
    	@Column(name = "created_time")
	private Date sysAdvertCreatedTime;

    @ApiModelProperty(name="sysAdvertUpdatedTime",value = "u修改时间")
    	@Column(name = "updated_time")
	private Date sysAdvertUpdatedTime;


}
