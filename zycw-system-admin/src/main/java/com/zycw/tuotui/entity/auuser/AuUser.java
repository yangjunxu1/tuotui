package com.zycw.tuotui.entity.auuser;

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
 * 用户
 * </p>
 *
 * @author junxu.yang
 * @since 2021-06-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name= "au_user")
@ApiModel(value="AuUser", description="用户")
public class AuUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name="auUserId",value = "id")
	
    	@Id
    	@Column(name = "id")
	private Integer auUserId;

    @ApiModelProperty(name="auUserMobile",value = "lvM手机号")
    	@Column(name = "mobile")
	private String auUserMobile;

    @ApiModelProperty(name="auUserIsAgent",value = "lvs是否是代理:0-不是，1-是")
    	@Column(name = "is_agent")
	private Integer auUserIsAgent;

    @ApiModelProperty(name="auUserNick",value = "lv昵称")
    	@Column(name = "nick")
	private String auUserNick;

    @ApiModelProperty(name="auUserUimg",value = "lv用户头像")
    	@Column(name = "uimg")
	private String auUserUimg;

    @ApiModelProperty(name="auUserType",value = "lvS用户类型:0-普通会员,1-代理商,2-企业会员")
    	@Column(name = "type")
	private String auUserType;

    @ApiModelProperty(name="auUserPassword",value = "密码")
    	@Column(name = "password")
	private String auUserPassword;

    @ApiModelProperty(name="auUserTrueName",value = "lv真实姓名")
    	@Column(name = "true_name")
	private String auUserTrueName;

    @ApiModelProperty(name="auUserUseruuid",value = "用户设备唯一编号")
    	@Column(name = "useruuid")
	private String auUserUseruuid;

    @ApiModelProperty(name="auUserSex",value = "lvr性别:0-男,1-女")
    	@Column(name = "sex")
	private Integer auUserSex;

    @ApiModelProperty(name="auUserProvinceId",value = "lvS省")
    	@Column(name = "province_id")
	private Integer auUserProvinceId;

    @ApiModelProperty(name="auUserCityId",value = "lvS市")
    	@Column(name = "city_id")
	private Integer auUserCityId;

    @ApiModelProperty(name="auUserAreaId",value = "lvS区")
    	@Column(name = "area_id")
	private Integer auUserAreaId;

    @ApiModelProperty(name="auUserOccupationId",value = "lvS职业")
    	@Column(name = "occupation_id")
	private Integer auUserOccupationId;

    @ApiModelProperty(name="auUserAgentCode",value = "lv代理商编号")
        	@Column(name = "agentCode")
	private String auUserAgentCode;

    @ApiModelProperty(name="auUserStatus",value = "lvS用户状态:0-启用,1-禁用")
    	@Column(name = "status")
	private Integer auUserStatus;

    @ApiModelProperty(name="auUserWxid",value = "lv微信id")
    	@Column(name = "wxid")
	private String auUserWxid;

    @ApiModelProperty(name="auUserZfbid",value = "lv支付宝id")
    	@Column(name = "zfbid")
	private String auUserZfbid;

    @ApiModelProperty(name="auUserCreatedTime",value = "l创建时间")
    	@Column(name = "created_time")
	private Date auUserCreatedTime;

    @ApiModelProperty(name="auUserUpdatedTime",value = "修改时间")
    	@Column(name = "updated_time")
	private Date auUserUpdatedTime;


}
