package com.zycw.tuotui.entity.systaskdepth;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
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
 * b任务深度
 * </p>
 *
 * @author junxu.yang
 * @since 2021-06-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name= "sys_task_depth")
@ApiModel(value="SysTaskDepth", description="b任务深度")
public class SysTaskDepth implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name="sysTaskDepthId",value = "ID")
	
    	@Id
    	@Column(name = "id")
	private Integer sysTaskDepthId;

    @ApiModelProperty(name="sysTaskDepthDepthTitle",value = "lvM任务深度标题")
    	@Column(name = "depth_title")
	private String sysTaskDepthDepthTitle;

    @ApiModelProperty(name="sysTaskDepthDepthPrice",value = "lv价格")
    	@Column(name = "depth_price")
	private Float sysTaskDepthDepthPrice;

    @ApiModelProperty(name="sysTaskDepthDepthContent",value = "v简介")
    	@Column(name = "depth_content")
	private String sysTaskDepthDepthContent;

    @ApiModelProperty(name="sysTaskDepthDelFlag",value = "逻辑删除:0-未删除,1-已删除")
        	@Column(name = "delFlag")
	private Integer sysTaskDepthDelFlag;


}
