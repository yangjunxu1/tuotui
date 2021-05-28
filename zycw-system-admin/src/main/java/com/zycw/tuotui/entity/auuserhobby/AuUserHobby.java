package com.zycw.tuotui.entity.auuserhobby;

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
 * 用户爱好关联
 * </p>
 *
 * @author junxu.yang
 * @since 2021-05-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name= "au_user_hobby")
@ApiModel(value="AuUserHobby", description="用户爱好关联")
public class AuUserHobby implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name="auUserHobbyId",value = "id")
	
    	@Id
    	@Column(name = "id")
	private Integer auUserHobbyId;

    @ApiModelProperty(name="auUserHobbyUserId",value = "用户id")
    	@Column(name = "user_id")
	private Integer auUserHobbyUserId;

    @ApiModelProperty(name="auUserHobbyHobbyId",value = "爱好id")
    	@Column(name = "hobby_id")
	private Integer auUserHobbyHobbyId;


}
