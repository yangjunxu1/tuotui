package com.zycw.tuotui.entity.aucompany;

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
 * 企业信息
 * </p>
 *
 * @author junxu.yang
 * @since 2021-06-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name= "au_company")
@ApiModel(value="AuCompany", description="企业信息")
public class AuCompany implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name="auCompanyId",value = "id")
	
    	@Id
    	@Column(name = "id")
	private Integer auCompanyId;

    @ApiModelProperty(name="auCompanyName",value = "lvM企业名称")
    	@Column(name = "name")
	private String auCompanyName;

    @ApiModelProperty(name="auCompanyCreditCode",value = "lv企业信用编码")
    	@Column(name = "credit_code")
	private String auCompanyCreditCode;

    @ApiModelProperty(name="auCompanyBusinessLicense",value = "lv营业执照照片")
    	@Column(name = "business_license")
	private String auCompanyBusinessLicense;

    @ApiModelProperty(name="auCompanyCorporation",value = "lv法人姓名")
    	@Column(name = "corporation")
	private String auCompanyCorporation;

    @ApiModelProperty(name="auCompanyContactor",value = "lv联系人姓名")
    	@Column(name = "contactor")
	private String auCompanyContactor;

    @ApiModelProperty(name="auCompanyContactsPhone",value = "lv联系方式")
    	@Column(name = "contacts_phone")
	private String auCompanyContactsPhone;

    @ApiModelProperty(name="auCompanyContactsProxyStatement",value = "lv联系人委托书")
    	@Column(name = "contacts_proxy_statement")
	private String auCompanyContactsProxyStatement;

    @ApiModelProperty(name="auCompanyAddress",value = "lv企业地址")
    	@Column(name = "address")
	private String auCompanyAddress;

    @ApiModelProperty(name="auCompanyAccountPrice",value = "h公司账户金额")
    	@Column(name = "account_price")
	private Float auCompanyAccountPrice;

    @ApiModelProperty(name="auCompanyUserId",value = "lv用户id")
    	@Column(name = "user_id")
	private Long auCompanyUserId;

    @ApiModelProperty(name="auCompanyVerifyStatus",value = "lv审核状态:0-通过,1-未通过")
    	@Column(name = "verify_status")
	private Boolean auCompanyVerifyStatus;

    @ApiModelProperty(name="auCompanyVerifyRefuse",value = "拒绝原因")
    	@Column(name = "verify_refuse")
	private String auCompanyVerifyRefuse;

    @ApiModelProperty(name="auCompanyVerifyTime",value = "lvul审核时间")
    	@Column(name = "verify_time")
	private Date auCompanyVerifyTime;

    @ApiModelProperty(name="auCompanyReviewStatus",value = "lv复审状态:0-通过,1-未通过")
    	@Column(name = "review_status")
	private Integer auCompanyReviewStatus;

    @ApiModelProperty(name="auCompanyReviewRefuse",value = "v复审拒绝理由")
    	@Column(name = "review_refuse")
	private String auCompanyReviewRefuse;

    @ApiModelProperty(name="auCompanyReviewTime",value = "lv复审时间")
    	@Column(name = "review_time")
	private Date auCompanyReviewTime;

    @ApiModelProperty(name="auCompanyCreatedTime",value = "il创建时间")
    	@Column(name = "created_time")
	private Date auCompanyCreatedTime;

    @ApiModelProperty(name="auCompanyUpdatedTime",value = "u修改时间")
    	@Column(name = "updated_time")
	private Date auCompanyUpdatedTime;

    @ApiModelProperty(name="auCompanyDelfFlag",value = "逻辑删除:0-未删除,1-已删除")
        	@Column(name = "delfFlag")
	private Integer auCompanyDelfFlag;


}
