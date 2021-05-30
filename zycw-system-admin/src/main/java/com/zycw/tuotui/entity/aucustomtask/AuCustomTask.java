package com.zycw.tuotui.entity.aucustomtask;

import java.math.BigDecimal;
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
 * 企业任务
 * </p>
 *
 * @author junxu.yang
 * @since 2021-05-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name= "au_custom_task")
@ApiModel(value="AuCustomTask", description="企业任务")
public class AuCustomTask implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name="auCustomTaskId",value = "ID")
	
    	@Id
    	@Column(name = "id")
	private Integer auCustomTaskId;

    @ApiModelProperty(name="auCustomTaskCompaynId",value = "lv公司")
    	@Column(name = "compayn_id")
	private Integer auCustomTaskCompaynId;

    @ApiModelProperty(name="auCustomTaskUserId",value = "lv用户")
    	@Column(name = "user_id")
	private Integer auCustomTaskUserId;

    @ApiModelProperty(name="auCustomTaskAppId",value = "lvapp")
    	@Column(name = "app_id")
	private Integer auCustomTaskAppId;

    @ApiModelProperty(name="auCustomTaskTaskTitle",value = "lvM任务标题")
    	@Column(name = "task_title")
	private String auCustomTaskTaskTitle;

    @ApiModelProperty(name="auCustomTaskExtensionType",value = "lvS推广类型")
    	@Column(name = "extension_type")
	private Integer auCustomTaskExtensionType;

    @ApiModelProperty(name="auCustomTaskSchemeId",value = "lvS套餐id")
    	@Column(name = "scheme_id")
	private Integer auCustomTaskSchemeId;

    @ApiModelProperty(name="auCustomTaskTaskType",value = "lvS任务类型")
    	@Column(name = "task_type")
	private Integer auCustomTaskTaskType;

    @ApiModelProperty(name="auCustomTaskTaskDepth",value = "lvS任务深度")
    	@Column(name = "task_depth")
	private Integer auCustomTaskTaskDepth;

    @ApiModelProperty(name="auCustomTaskProvinceId",value = "lvS任务要求省")
    	@Column(name = "province_id")
	private Integer auCustomTaskProvinceId;

    @ApiModelProperty(name="auCustomTaskCityId",value = "lvS任务要求市")
    	@Column(name = "city_id")
	private Integer auCustomTaskCityId;

    @ApiModelProperty(name="auCustomTaskAreaId",value = "lvS任务要求区")
    	@Column(name = "area_id")
	private Integer auCustomTaskAreaId;

    @ApiModelProperty(name="auCustomTaskOccupationId",value = "lvS职业")
    	@Column(name = "occupation_id")
	private Integer auCustomTaskOccupationId;

    @ApiModelProperty(name="auCustomTaskStartTime",value = "lv开始日期")
    	@Column(name = "start_time")
	private Date auCustomTaskStartTime;

    @ApiModelProperty(name="auCustomTaskEndTime",value = "lv结束日期")
    	@Column(name = "end_time")
	private Date auCustomTaskEndTime;

    @ApiModelProperty(name="auCustomTaskUserSex",value = "lv任务要求用户性别:0-男,1-女,2-全部")
    	@Column(name = "user_sex")
	private Integer auCustomTaskUserSex;

    @ApiModelProperty(name="auCustomTaskUserAgeMin",value = "lv任务要求用户最小年龄")
    	@Column(name = "user_age_min")
	private Integer auCustomTaskUserAgeMin;

    @ApiModelProperty(name="auCustomTaskUserAgeMax",value = "lv任务要求用户最大年龄")
    	@Column(name = "user_age_max")
	private Integer auCustomTaskUserAgeMax;

    @ApiModelProperty(name="auCustomTaskHobbyType",value = "任务要求用户爱好")
    	@Column(name = "hobby_type")
	private String auCustomTaskHobbyType;

    @ApiModelProperty(name="auCustomTaskExtensionItem",value = "v推广需求")
    	@Column(name = "extension_item")
	private String auCustomTaskExtensionItem;

    @ApiModelProperty(name="auCustomTaskExtensionNumsItem",value = "lv推广人数")
    	@Column(name = "extension_nums_item")
	private Integer auCustomTaskExtensionNumsItem;

    @ApiModelProperty(name="auCustomTaskCustomPrice",value = "lv定制价格")
    	@Column(name = "custom_price")
	private BigDecimal auCustomTaskCustomPrice;

    @ApiModelProperty(name="auCustomTaskVerifyStatus",value = "lvs审核状态:0-通过,1-未通过")
    	@Column(name = "verify_status")
	private Integer auCustomTaskVerifyStatus;

    @ApiModelProperty(name="auCustomTaskTaskContent",value = "v任务简介")
    	@Column(name = "task_content")
	private String auCustomTaskTaskContent;

    @ApiModelProperty(name="auCustomTaskVeruftRefuse",value = "v拒绝原因")
    	@Column(name = "veruft_refuse")
	private String auCustomTaskVeruftRefuse;

    @ApiModelProperty(name="auCustomTaskVerifyTime",value = "ul初审时间")
    	@Column(name = "verify_time")
	private Date auCustomTaskVerifyTime;

    @ApiModelProperty(name="auCustomTaskReviewStatus",value = "lvs复审状态:0-通过,1-未通过")
    	@Column(name = "review_status")
	private Integer auCustomTaskReviewStatus;

    @ApiModelProperty(name="auCustomTaskReviewRefuse",value = "v复审拒绝理由")
    	@Column(name = "review_refuse")
	private String auCustomTaskReviewRefuse;

    @ApiModelProperty(name="auCustomTaskReviewTime",value = "lv复审时间")
    	@Column(name = "review_time")
	private Date auCustomTaskReviewTime;

    @ApiModelProperty(name="auCustomTaskTaskReward",value = "任务奖励金额")
    	@Column(name = "task_reward")
	private Float auCustomTaskTaskReward;

    @ApiModelProperty(name="auCustomTaskTaskStatus",value = "lvS任务状态:0-已审核,1-未审核,2-未通过,3-未开始,4-进行中,5-已结束")
    	@Column(name = "task_status")
	private Integer auCustomTaskTaskStatus;

    @ApiModelProperty(name="auCustomTaskCreatedTime",value = "il创建时间")
    	@Column(name = "created_time")
	private Date auCustomTaskCreatedTime;

    @ApiModelProperty(name="auCustomTaskUpdatedTime",value = "u修改时间")
    	@Column(name = "updated_time")
	private Date auCustomTaskUpdatedTime;


}
