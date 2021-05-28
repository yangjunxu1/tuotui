package com.zycw.tuotui.entity.sysarea;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
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
 * b3区
 * </p>
 *
 * @author junxu.yang
 * @since 2021-05-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name= "sys_area")
@ApiModel(value="SysArea", description="b3区")
public class SysArea implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name="sysAreaId",value = "ID")
	
    	@Id
    	@Column(name = "id")
	private Integer sysAreaId;

    @ApiModelProperty(name="sysAreaName",value = "lvM区名称")
    	@Column(name = "name")
	private String sysAreaName;

    @ApiModelProperty(name="sysAreaCityId",value = "市")
    	@Column(name = "city_id")
	private Integer sysAreaCityId;

    @ApiModelProperty(name="sysAreaProvinceId",value = "省")
    	@Column(name = "province_id")
	private Integer sysAreaProvinceId;


}
