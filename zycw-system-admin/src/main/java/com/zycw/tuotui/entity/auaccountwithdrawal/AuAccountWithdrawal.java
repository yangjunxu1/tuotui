package com.zycw.tuotui.entity.auaccountwithdrawal;

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
 * 提现明细表
 * </p>
 *
 * @author junxu.yang
 * @since 2021-05-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name= "au_account_withdrawal")
@ApiModel(value="AuAccountWithdrawal", description="提现明细表")
public class AuAccountWithdrawal implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name="auAccountWithdrawalId",value = "ID")
	
    	@Id
    	@Column(name = "id")
	private Integer auAccountWithdrawalId;

    @ApiModelProperty(name="auAccountWithdrawalUserId",value = "lv用户")
    	@Column(name = "user_id")
	private Integer auAccountWithdrawalUserId;

    @ApiModelProperty(name="auAccountWithdrawalTaskId",value = "lv任务")
    	@Column(name = "task_id")
	private Integer auAccountWithdrawalTaskId;

    @ApiModelProperty(name="auAccountWithdrawalWithdrawalTime",value = "lv提现时间")
    	@Column(name = "withdrawal_time")
	private Date auAccountWithdrawalWithdrawalTime;

    @ApiModelProperty(name="auAccountWithdrawalWithdrawalStatus",value = "提现状态:0-已到账,1-未到账")
    	@Column(name = "withdrawal_status")
	private Integer auAccountWithdrawalWithdrawalStatus;

    @ApiModelProperty(name="auAccountWithdrawalWithdrawalPath",value = "提现途径:0-微信,1-支付宝,2-银行卡")
    	@Column(name = "withdrawal_path")
	private Integer auAccountWithdrawalWithdrawalPath;


}
