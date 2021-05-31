package com.zycw.tuotui.entity.systasktype;

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
 * b任务类型
 * </p>
 *
 * @author junxu.yang
 * @since 2021-06-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name= "sys_task_type")
@ApiModel(value="SysTaskType", description="b任务类型")
public class SysTaskType implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name="sysTaskTypeId",value = "ID")
	
    	@Id
    	@Column(name = "id")
	private Integer sysTaskTypeId;

    @ApiModelProperty(name="sysTaskTypeTypeTitle",value = "lvM分类名称")
    	@Column(name = "type_title")
	private String sysTaskTypeTypeTitle;

    @ApiModelProperty(name="sysTaskTypeTypePrice",value = "lv任务分类价格")
    	@Column(name = "type_price")
	private Float sysTaskTypeTypePrice;

    @ApiModelProperty(name="sysTaskTypeTypeContent",value = "简介")
    	@Column(name = "type_content")
	private String sysTaskTypeTypeContent;

    @ApiModelProperty(name="sysTaskTypeDelFlag",value = "逻辑删除:0-未删除,1-已删除")
        	@Column(name = "delFlag")
	private Integer sysTaskTypeDelFlag;


}
