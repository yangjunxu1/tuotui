package com.zycw.tuotui.entity.aucompanyapp;

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
 * 企业app
 * </p>
 *
 * @author junxu.yang
 * @since 2021-06-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name= "au_company_app")
@ApiModel(value="AuCompanyApp", description="企业app")
public class AuCompanyApp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name="auCompanyAppId",value = "id")
	
    	@Id
    	@Column(name = "id")
	private Integer auCompanyAppId;

    @ApiModelProperty(name="auCompanyAppCompanyId",value = "lv公司")
    	@Column(name = "company_id")
	private Integer auCompanyAppCompanyId;

    @ApiModelProperty(name="auCompanyAppName",value = "lvMapp名称")
    	@Column(name = "name")
	private String auCompanyAppName;

    @ApiModelProperty(name="auCompanyAppRemark",value = "lvapp介绍")
    	@Column(name = "remark")
	private String auCompanyAppRemark;

    @ApiModelProperty(name="auCompanyAppIconPath",value = "lvapp图标")
    	@Column(name = "icon_path")
	private String auCompanyAppIconPath;

    @ApiModelProperty(name="auCompanyAppAppSize",value = "app大小")
    	@Column(name = "app_size")
	private Integer auCompanyAppAppSize;

    @ApiModelProperty(name="auCompanyAppDownUrl",value = "lvapp下载地址")
    	@Column(name = "down_url")
	private String auCompanyAppDownUrl;

    @ApiModelProperty(name="auCompanyAppVerifyStatus",value = "lv审核状态:0-通过,1-未通过")
    	@Column(name = "verify_status")
	private Integer auCompanyAppVerifyStatus;

    @ApiModelProperty(name="auCompanyAppVeruftRefuse",value = "v拒绝原因")
    	@Column(name = "veruft_refuse")
	private String auCompanyAppVeruftRefuse;

    @ApiModelProperty(name="auCompanyAppVerifyTime",value = "ul初审时间")
    	@Column(name = "verify_time")
	private Date auCompanyAppVerifyTime;

    @ApiModelProperty(name="auCompanyAppReviewStatus",value = "lv复审状态:0-通过,1-未通过")
    	@Column(name = "review_status")
	private Integer auCompanyAppReviewStatus;

    @ApiModelProperty(name="auCompanyAppReviewRefuse",value = "v复审拒绝理由")
    	@Column(name = "review_refuse")
	private String auCompanyAppReviewRefuse;

    @ApiModelProperty(name="auCompanyAppReviewTime",value = "lv复审时间")
    	@Column(name = "review_time")
	private Date auCompanyAppReviewTime;

    @ApiModelProperty(name="auCompanyAppCreatedTime",value = "i-创建时间")
    	@Column(name = "created_time")
	private Date auCompanyAppCreatedTime;

    @ApiModelProperty(name="auCompanyAppUpdatedTime",value = "i-修改时间")
    	@Column(name = "updated_time")
	private Date auCompanyAppUpdatedTime;

    @ApiModelProperty(name="auCompanyAppDelFlag",value = "逻辑删除:0-未删除,1-已删除")
        	@Column(name = "delFlag")
	private Integer auCompanyAppDelFlag;


}
