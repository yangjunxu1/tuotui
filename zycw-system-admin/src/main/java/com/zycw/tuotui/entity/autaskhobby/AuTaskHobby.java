package com.zycw.tuotui.entity.autaskhobby;

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
 * 爱好任务关联
 * </p>
 *
 * @author junxu.yang
 * @since 2021-05-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name= "au_task_hobby")
@ApiModel(value="AuTaskHobby", description="爱好任务关联")
public class AuTaskHobby implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name="auTaskHobbyId",value = "id")
	
    	@Id
    	@Column(name = "id")
	private Integer auTaskHobbyId;

    @ApiModelProperty(name="auTaskHobbyTaskId",value = "任务id")
    	@Column(name = "task_id")
	private Integer auTaskHobbyTaskId;

    @ApiModelProperty(name="auTaskHobbyHobbyId",value = "任务id")
    	@Column(name = "hobby_id")
	private Integer auTaskHobbyHobbyId;


}
