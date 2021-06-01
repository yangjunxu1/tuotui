package com.zycw.tuotui.entity.auscheme;

import java.math.BigDecimal;
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
 * 套餐表
 * </p>
 *
 * @author junxu.yang
 * @since 2021-06-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name= "au_scheme")
@ApiModel(value="AuScheme", description="套餐表")
public class AuScheme implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name="auSchemeId",value = "i-主键")
	
    	@Id
    	@Column(name = "id")
	private Long auSchemeId;

    @ApiModelProperty(name="auSchemeName",value = "lv-套餐名称")
    	@Column(name = "name")
	private String auSchemeName;

    @ApiModelProperty(name="auSchemePrice",value = "lv-价格")
    	@Column(name = "price")
	private BigDecimal auSchemePrice;

    @ApiModelProperty(name="auSchemeContent",value = "lv-套餐内容")
    	@Column(name = "content")
	private String auSchemeContent;

    @ApiModelProperty(name="auSchemeStatus",value = "lvs-套餐状态 1-启用 0-停用")
    	@Column(name = "status")
	private Integer auSchemeStatus;

    @ApiModelProperty(name="auSchemeExtensionType",value = "推广类型")
    	@Column(name = "extension_type")
	private Integer auSchemeExtensionType;

    @ApiModelProperty(name="auSchemeTaskType",value = "lv任务类型")
    	@Column(name = "task_type")
	private Integer auSchemeTaskType;

    @ApiModelProperty(name="auSchemeTaskDepth",value = "lv任务深度")
    	@Column(name = "task_depth")
	private Integer auSchemeTaskDepth;

    @ApiModelProperty(name="auSchemeCreatedTime",value = "创建时间")
    	@Column(name = "created_time")
	private Date auSchemeCreatedTime;

    @ApiModelProperty(name="auSchemeUpdatedTime",value = "i-更新时间")
    	@Column(name = "updated_time")
	private Date auSchemeUpdatedTime;

    @ApiModelProperty(name="auSchemeDelFlag",value = "逻辑删除:0-未删除,1-已删除")
        	@Column(name = "delFlag")
	private Integer auSchemeDelFlag;


}
