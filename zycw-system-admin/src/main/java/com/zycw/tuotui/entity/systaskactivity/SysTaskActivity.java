package com.zycw.tuotui.entity.systaskactivity;

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
 * 任务活动信息
 * </p>
 *
 * @author junxu.yang
 * @since 2021-06-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name= "sys_task_activity")
@ApiModel(value="SysTaskActivity", description="任务活动信息")
public class SysTaskActivity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name="sysTaskActivityId",value = "ID")
	
    	@Id
    	@Column(name = "id")
	private Integer sysTaskActivityId;

    @ApiModelProperty(name="sysTaskActivityActivityName",value = "lvM活动名称")
    	@Column(name = "activity_name")
	private String sysTaskActivityActivityName;

    @ApiModelProperty(name="sysTaskActivityActivityCode",value = "lv活动编码")
    	@Column(name = "activity_code")
	private String sysTaskActivityActivityCode;

    @ApiModelProperty(name="sysTaskActivityDelFlag",value = "逻辑删除:0-未删除,1-已删除")
        	@Column(name = "delFlag")
	private Integer sysTaskActivityDelFlag;


}
