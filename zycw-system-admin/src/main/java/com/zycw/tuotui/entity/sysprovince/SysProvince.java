package com.zycw.tuotui.entity.sysprovince;

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
 * b1省
 * </p>
 *
 * @author junxu.yang
 * @since 2021-06-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name= "sys_province")
@ApiModel(value="SysProvince", description="b1省")
public class SysProvince implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name="sysProvinceId",value = "ID")
	
    	@Id
    	@Column(name = "id")
	private Integer sysProvinceId;

    @ApiModelProperty(name="sysProvinceName",value = "lvM省名称")
    	@Column(name = "name")
	private String sysProvinceName;

    @ApiModelProperty(name="sysProvinceDelFlag",value = "逻辑删除:0-未删除,1-已删除")
        	@Column(name = "delFlag")
	private Integer sysProvinceDelFlag;


}
