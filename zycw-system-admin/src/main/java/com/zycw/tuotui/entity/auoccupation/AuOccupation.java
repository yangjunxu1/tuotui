package com.zycw.tuotui.entity.auoccupation;

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
 * b职业
 * </p>
 *
 * @author junxu.yang
 * @since 2021-06-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name= "au_occupation")
@ApiModel(value="AuOccupation", description="b职业")
public class AuOccupation implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name="auOccupationId",value = "id")
	
    	@Id
    	@Column(name = "id")
	private Integer auOccupationId;

    @ApiModelProperty(name="auOccupationOccupationName",value = "Mlv职业")
    	@Column(name = "occupation_name")
	private String auOccupationOccupationName;

    @ApiModelProperty(name="auOccupationDelFlag",value = "逻辑删除:0-未删除,1-已删除")
        	@Column(name = "delFlag")
	private Integer auOccupationDelFlag;


}
