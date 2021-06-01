package com.zycw.tuotui.entity.sysadverttype;

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
 * 广告类型
 * </p>
 *
 * @author junxu.yang
 * @since 2021-06-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name= "sys_advert_type")
@ApiModel(value="SysAdvertType", description="广告类型")
public class SysAdvertType implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name="sysAdvertTypeId",value = "ID")
	
    	@Id
    	@Column(name = "id")
	private Integer sysAdvertTypeId;

    @ApiModelProperty(name="sysAdvertTypeTypeName",value = "lvM类型名称")
    	@Column(name = "type_name")
	private String sysAdvertTypeTypeName;

    @ApiModelProperty(name="sysAdvertTypeTypeCode",value = "lv类型编码")
    	@Column(name = "type_code")
	private String sysAdvertTypeTypeCode;

    @ApiModelProperty(name="sysAdvertTypeDelFlag",value = "逻辑删除:0-未删除,1-已删除")
        	@Column(name = "delFlag")
	private Integer sysAdvertTypeDelFlag;


}
