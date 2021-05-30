package com.zycw.tuotui.entity.sysextensiontype;

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
 * b推广类型·
 * </p>
 *
 * @author junxu.yang
 * @since 2021-05-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name= "sys_extension_type")
@ApiModel(value="SysExtensionType", description="b推广类型·")
public class SysExtensionType implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name="sysExtensionTypeId",value = "ID")
	
    	@Id
    	@Column(name = "id")
	private Integer sysExtensionTypeId;

    @ApiModelProperty(name="sysExtensionTypeTitel",value = "lvM标题·")
    	@Column(name = "titel")
	private String sysExtensionTypeTitel;

    @ApiModelProperty(name="sysExtensionTypePrice",value = "lv价格")
    	@Column(name = "price")
	private Float sysExtensionTypePrice;

    	@Column(name = "content")
	private String sysExtensionTypeContent;


}
