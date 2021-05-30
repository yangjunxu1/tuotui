package com.zycw.tuotui.entity.auaccountconsume;

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
 * 公司账户消费明细
 * </p>
 *
 * @author junxu.yang
 * @since 2021-05-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name= "au_account_consume")
@ApiModel(value="AuAccountConsume", description="公司账户消费明细")
public class AuAccountConsume implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name="auAccountConsumeId",value = "ID")
	
    	@Id
    	@Column(name = "id")
	private Integer auAccountConsumeId;

    @ApiModelProperty(name="auAccountConsumeCompanyId",value = "公司")
    	@Column(name = "company_id")
	private Integer auAccountConsumeCompanyId;

    @ApiModelProperty(name="auAccountConsumeUserId",value = "会员")
    	@Column(name = "user_id")
	private Integer auAccountConsumeUserId;

    @ApiModelProperty(name="auAccountConsumePrice",value = "消费金额")
    	@Column(name = "price")
	private Float auAccountConsumePrice;

    @ApiModelProperty(name="auAccountConsumeTaskId",value = "任务")
    	@Column(name = "task_id")
	private Integer auAccountConsumeTaskId;

    @ApiModelProperty(name="auAccountConsumeConsumeTime",value = "消费时间")
    	@Column(name = "consume_time")
	private Date auAccountConsumeConsumeTime;


}
