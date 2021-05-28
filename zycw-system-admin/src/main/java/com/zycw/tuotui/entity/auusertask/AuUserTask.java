package com.zycw.tuotui.entity.auusertask;

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
 * 会员任务
 * </p>
 *
 * @author junxu.yang
 * @since 2021-05-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name= "au_user_task")
@ApiModel(value="AuUserTask", description="会员任务")
public class AuUserTask implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name="auUserTaskId",value = "id")
	
    	@Id
    	@Column(name = "id")
	private Integer auUserTaskId;

    @ApiModelProperty(name="auUserTaskUserId",value = "lv用户")
    	@Column(name = "user_id")
	private Integer auUserTaskUserId;

    @ApiModelProperty(name="auUserTaskTaskId",value = "lv任务")
    	@Column(name = "task_id")
	private Integer auUserTaskTaskId;

    @ApiModelProperty(name="auUserTaskTaskStatus",value = "lv完成状态:0-已完成，1-未开始，2-进行中，3-已完成，未结算，4-已结算")
    	@Column(name = "task_status")
	private Integer auUserTaskTaskStatus;

    @ApiModelProperty(name="auUserTaskStartTime",value = "lv开始时间")
    	@Column(name = "start_time")
	private Date auUserTaskStartTime;

    @ApiModelProperty(name="auUserTaskEndTime",value = "lv结束时间")
    	@Column(name = "end_time")
	private Date auUserTaskEndTime;


}
