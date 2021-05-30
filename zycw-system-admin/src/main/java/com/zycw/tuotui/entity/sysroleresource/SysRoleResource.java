package com.zycw.tuotui.entity.sysroleresource;

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
 * 
 * </p>
 *
 * @author junxu.yang
 * @since 2021-05-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name= "sys_role_resource")
@ApiModel(value="SysRoleResource", description="")
public class SysRoleResource implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name="sysRoleResourceId",value = "id")
	
    	@Id
    	@Column(name = "id")
	private Integer sysRoleResourceId;

    	@Column(name = "role_id")
	private Integer sysRoleResourceRoleId;

    	@Column(name = "resource_id")
	private Integer sysRoleResourceResourceId;


}
