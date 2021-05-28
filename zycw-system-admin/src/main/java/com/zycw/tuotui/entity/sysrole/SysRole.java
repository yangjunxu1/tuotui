package com.zycw.tuotui.entity.sysrole;

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
 * b角色
 * </p>
 *
 * @author junxu.yang
 * @since 2021-05-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name= "sys_role")
@ApiModel(value="SysRole", description="b角色")
public class SysRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name="sysRoleId",value = "ID")
	
    	@Id
    	@Column(name = "id")
	private Integer sysRoleId;

    @ApiModelProperty(name="sysRoleName",value = "lvM名称")
    	@Column(name = "name")
	private String sysRoleName;


}
