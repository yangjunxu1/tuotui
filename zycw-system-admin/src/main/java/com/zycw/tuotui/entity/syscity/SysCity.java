package com.zycw.tuotui.entity.syscity;

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
 * b2市
 * </p>
 *
 * @author junxu.yang
 * @since 2021-06-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name= "sys_city")
@ApiModel(value="SysCity", description="b2市")
public class SysCity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name="sysCityId",value = "ID")
	
    	@Id
    	@Column(name = "id")
	private Integer sysCityId;

    @ApiModelProperty(name="sysCityName",value = "lvM市名称")
    	@Column(name = "name")
	private String sysCityName;

    @ApiModelProperty(name="sysCityProvinceId",value = "省")
    	@Column(name = "province_id")
	private Integer sysCityProvinceId;

    @ApiModelProperty(name="sysCityDelFlag",value = "逻辑删除:0-未删除,1-已删除")
        	@Column(name = "delFlag")
	private Integer sysCityDelFlag;


}
