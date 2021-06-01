package com.zycw.tuotui.entity.sysuser;

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
 * 系统管理员
 * </p>
 *
 * @author junxu.yang
 * @since 2021-06-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name= "sys_user")
@ApiModel(value="SysUser", description="系统管理员")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name="sysUserId",value = "id")
	
    	@Id
    	@Column(name = "id")
	private Integer sysUserId;

    @ApiModelProperty(name="sysUserUsername",value = "lvM用户名")
    	@Column(name = "username")
	private String sysUserUsername;

    @ApiModelProperty(name="sysUserPassword",value = "密码")
    	@Column(name = "password")
	private String sysUserPassword;

    @ApiModelProperty(name="sysUserNick",value = "lv用户昵称")
    	@Column(name = "nick")
	private String sysUserNick;

    @ApiModelProperty(name="sysUserUimg",value = "lv用户头像")
    	@Column(name = "uimg")
	private String sysUserUimg;

    @ApiModelProperty(name="sysUserRoleId",value = "lv管理组")
    	@Column(name = "role_id")
	private Integer sysUserRoleId;

    @ApiModelProperty(name="sysUserTrueName",value = "lv真实姓名")
    	@Column(name = "true_name")
	private String sysUserTrueName;

    @ApiModelProperty(name="sysUserMobile",value = "lv联系电话")
    	@Column(name = "mobile")
	private String sysUserMobile;

    @ApiModelProperty(name="sysUserCreatedTime",value = "lv添加日期")
    	@Column(name = "created_time")
	private Date sysUserCreatedTime;

    @ApiModelProperty(name="sysUserUpdatedTime",value = "修改日期")
    	@Column(name = "updated_time")
	private Date sysUserUpdatedTime;

    @ApiModelProperty(name="sysUserDelFlag",value = "逻辑删除:0-未删除,1-已删除")
        	@Column(name = "delFlag")
	private Integer sysUserDelFlag;


}
