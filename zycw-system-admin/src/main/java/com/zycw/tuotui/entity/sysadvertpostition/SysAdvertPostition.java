package com.zycw.tuotui.entity.sysadvertpostition;

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
 * b广告协议位置
 * </p>
 *
 * @author junxu.yang
 * @since 2021-06-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name= "sys_advert_postition")
@ApiModel(value="SysAdvertPostition", description="b广告协议位置")
public class SysAdvertPostition implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name="sysAdvertPostitionId",value = "ID")
	
    	@Id
    	@Column(name = "id")
	private Integer sysAdvertPostitionId;

    @ApiModelProperty(name="sysAdvertPostitionName",value = "lnM位置名称")
    	@Column(name = "name")
	private String sysAdvertPostitionName;

    @ApiModelProperty(name="sysAdvertPostitionCode",value = "位置编码")
    	@Column(name = "code")
	private String sysAdvertPostitionCode;

    @ApiModelProperty(name="sysAdvertPostitionDelFlag",value = "逻辑删除:0-未删除,1-已删除")
        	@Column(name = "delFlag")
	private Integer sysAdvertPostitionDelFlag;


}
