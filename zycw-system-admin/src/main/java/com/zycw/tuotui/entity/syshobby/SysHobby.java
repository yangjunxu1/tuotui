package com.zycw.tuotui.entity.syshobby;

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
 * b爱好
 * </p>
 *
 * @author junxu.yang
 * @since 2021-06-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name= "sys_hobby")
@ApiModel(value="SysHobby", description="b爱好")
public class SysHobby implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name="sysHobbyId",value = "id")
	
    	@Id
    	@Column(name = "id")
	private Integer sysHobbyId;

    @ApiModelProperty(name="sysHobbyHobbyTitle",value = "lvM爱好")
    	@Column(name = "hobby_title")
	private String sysHobbyHobbyTitle;

    @ApiModelProperty(name="sysHobbyDelFlag",value = "逻辑删除:0-未删除,1-已删除")
        	@Column(name = "delFlag")
	private Integer sysHobbyDelFlag;


}
