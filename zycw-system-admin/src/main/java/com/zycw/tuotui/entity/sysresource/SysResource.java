package com.zycw.tuotui.entity.sysresource;

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
 * 菜单
 * </p>
 *
 * @author junxu.yang
 * @since 2021-06-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name= "sys_resource")
@ApiModel(value="SysResource", description="菜单")
public class SysResource implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name="sysResourceId",value = "ID")
	
    	@Id
    	@Column(name = "id")
	private Integer sysResourceId;

    @ApiModelProperty(name="sysResourceName",value = "lv菜单名称")
    	@Column(name = "name")
	private String sysResourceName;

    @ApiModelProperty(name="sysResourceRouter",value = "lv菜单路由")
    	@Column(name = "router")
	private String sysResourceRouter;

    @ApiModelProperty(name="sysResourcePath",value = "lv文件路径")
    	@Column(name = "path")
	private String sysResourcePath;

    @ApiModelProperty(name="sysResourceIcon",value = "图标")
    	@Column(name = "icon")
	private String sysResourceIcon;

    @ApiModelProperty(name="sysResourceIshidden",value = "lv默认是否隐藏:0-隐藏,1-显示")
    	@Column(name = "ishidden")
	private Boolean sysResourceIshidden;

    @ApiModelProperty(name="sysResourceRoles",value = "管理组")
    	@Column(name = "roles")
	private String sysResourceRoles;

    @ApiModelProperty(name="sysResourceDelFlag",value = "逻辑删除:0-未删除,1-已删除")
        	@Column(name = "delFlag")
	private Integer sysResourceDelFlag;


}
