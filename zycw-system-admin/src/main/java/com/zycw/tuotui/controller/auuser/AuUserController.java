package com.zycw.tuotui.controller.auuser;


import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.zycw.distributed.database.controller.BaseController;
import com.zycw.common.util.wxwUtil.ResultBeanFactory;
import com.zycw.common.util.wxwUtil.ResultBeanFactory.ResultBean;
import com.zycw.common.util.wxwUtil.CommonUtil;
import com.zycw.common.util.Page;
import com.zycw.common.util.wxwUtil.IdGenerateUtil;
import com.zycw.tuotui.iface.auuser.IAuUserService;
import com.zycw.tuotui.entity.auuser.AuUser;
import java.util.Date;
import java.util.List;
import java.util.HashMap;


@ApiOperation(value = "用户")
@Api(value = "用户接口", tags = { "用户接口" })
@Slf4j
@RestController
@RequestMapping("/auUser")
public class AuUserController extends BaseController <IAuUserService, AuUser>{

    private static Logger logger = LoggerFactory.getLogger(AuUserController.class);
       
    		   
    @Autowired
    private ResultBeanFactory resultBeanFactory;

     @ApiOperation(value = "根据id物理删除")
     @ApiImplicitParam(name = "auUserId", value = "ID", required = true, dataType = "String")
     @RequestMapping(value = "/deleteById", method = RequestMethod.GET)
     public ResultBean<String> deleteById(@RequestParam("auUserId") String auUserId) {
     	logger.info("auUser.deleteById页面请求参数："+auUserId);
		try {
			baseService.deleteById(auUserId);
			return resultBeanFactory.getBean("删除成功");
		} catch (Exception e) {
			logger.error("失败原因:{}", e);
			return resultBeanFactory.getException(e);
		}
     }

     @ApiOperation(value = "根据id逻辑删除")
     @ApiImplicitParam(name = "auUserId", value = "ID", required = true, dataType = "String")
     @RequestMapping(value = "/deleteByIdLogic", method = RequestMethod.GET)
     public ResultBean<String> deleteByIdLogic(@RequestParam("auUserId") String auUserId) {
		logger.info("auUser.deleteByIdLogic页面请求参数："+auUserId);
		try {
			baseService.deleteByIdLogic(auUserId);
			return resultBeanFactory.getBean("删除成功");
		} catch (Exception e) {
			logger.error("失败原因:{}", e);
			return resultBeanFactory.getException(e);
		}
     }
     

     @ApiOperation(value = "根据主键修改")
     @ApiImplicitParam(name = "auUser", value = "用户对象", required = true, dataType = "AuUser")
     @RequestMapping(value = "/updateObjById", method = RequestMethod.POST)
     public ResultBean<String> updateObjById(@RequestBody AuUser auUser) {
         logger.info("auUser.updateObjById页面请求参数："+auUser);
		try {
			logger.info("auUser.update页面请求参数："+auUser);
			baseService.updateObjById(auUser);
			return resultBeanFactory.getBean("修改成功");
        } catch (Exception e) {
            logger.error("修改失败:{}", e);
            return resultBeanFactory.getException(e);
        }
     }
     
     

     @ApiOperation(value = "新增")
     @ApiImplicitParam(name = "auUser", value = "用户对象", required = true, dataType = "AuUser")
     @RequestMapping(value = "/insertObj", method = RequestMethod.POST)
     public ResultBean<String> insertObj(@RequestBody AuUser auUser) {
		try {
			logger.info("auUser.insertObj页面请求参数："+auUser);
	        baseService.insert(auUser);
	        return resultBeanFactory.getBean("插入记录成功");
        } catch (Exception e) {
            logger.error("插入记录失败:{}", e);
            return resultBeanFactory.getException(e);
        }
     }
     
     @ApiOperation(value = "分页查询")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "auUserCityId", value = "lvS市", required = false, dataType = "${dataType}"),
    	@ApiImplicitParam(name = "auUserProvinceId", value = "lvS省", required = false, dataType = "${dataType}"),
    	@ApiImplicitParam(name = "auUserAreaId", value = "lvS区", required = false, dataType = "${dataType}"),
    	@ApiImplicitParam(name = "auUserOccupationId", value = "lvS职业", required = false, dataType = "${dataType}"),
		@ApiImplicitParam(name = "pageSize", value = "每页条数", required = false, dataType = "int"),
		@ApiImplicitParam(name = "pageNum", value = "当前页数", required = false, dataType = "int"),















    	@ApiImplicitParam(name = "auUserUpdatedTime", value = "return-修改时间", required = false, dataType = "Date"),
     })
     @RequestMapping(value = "/pageList", method = RequestMethod.POST)
     public ResultBeanFactory.ResultBean<PageInfo<AuUser>> pageList(@RequestBody HashMap<String,Object> params) {
		try {
			logger.info("auUser.pageList页面请求参数："+params);
            int count = baseService.countNum(params);
            PageInfo<AuUser> result=baseService.pageList(params);
            result.setTotal(count);
            return resultBeanFactory.getBean(result);
		} catch (Exception e) {
			logger.error("AuUserController.pageList Exception:{}", e);
			return resultBeanFactory.getException(e);
		}
     }


     @ApiOperation(value = "通过id查询")
     @ApiImplicitParam(name = "auUserId", value = "ID", required = true, dataType = "String")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "auUserId", value = "return-id", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auUserMobile", value = "return-lvM手机号", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auUserType", value = "return-lvS用户类型:0-普通会员,1-代理商,2-企业会员", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auUserPassword", value = "return-密码", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auUserTrueName", value = "return-lv真实姓名", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auUserSex", value = "return-lvr性别:0-男,1-女", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysProvinceName", value = "return-lvM省名称", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysCityName", value = "return-lvM市名称", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysAreaName", value = "return-lvM区名称", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auOccupationOccupationName", value = "return-Mlv职业", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auUserAgentCode", value = "return-lv代理商编号", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auUserStatus", value = "return-lvS用户状态:0-启用,1-禁用", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auUserWxid", value = "return-lv微信id", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auUserZfbid", value = "return-lv支付宝id", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auUserCreatedTime", value = "return-l创建时间", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auUserUpdatedTime", value = "return-修改时间", required = false, dataType = "Date"),
		})
     @RequestMapping(value = "/selectObjById", method = RequestMethod.GET)
     public ResultBean<HashMap<String,Object>> info(@RequestParam("auUserId") String auUserId) {
			logger.info("auUser.selectObjById页面请求参数："+auUserId);
         HashMap<String,Object> auUser = baseService.selectObjById(auUserId);
         return resultBeanFactory.getBean(auUser);
     }
     
     
	
     @ApiOperation(value = "allList")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "auUserCityId", value = "lvS市", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auUserProvinceId", value = "lvS省", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auUserAreaId", value = "lvS区", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auUserOccupationId", value = "lvS职业", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auUserId", value = "return-id", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auUserMobile", value = "return-lvM手机号", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auUserType", value = "return-lvS用户类型:0-普通会员,1-代理商,2-企业会员", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auUserPassword", value = "return-密码", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auUserTrueName", value = "return-lv真实姓名", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auUserSex", value = "return-lvr性别:0-男,1-女", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysProvinceName", value = "return-lvM省名称", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysCityName", value = "return-lvM市名称", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysAreaName", value = "return-lvM区名称", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auOccupationOccupationName", value = "return-Mlv职业", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auUserAgentCode", value = "return-lv代理商编号", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auUserStatus", value = "return-lvS用户状态:0-启用,1-禁用", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auUserWxid", value = "return-lv微信id", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auUserZfbid", value = "return-lv支付宝id", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auUserCreatedTime", value = "return-l创建时间", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auUserUpdatedTime", value = "return-修改时间", required = false, dataType = "Date"),
     })
     @RequestMapping(value = "/allList", method = RequestMethod.POST)
	public ResultBean<List<AuUser>> allList(@RequestBody HashMap<String,Object> param){
			logger.info("auUser.allList页面请求参数："+param);
		List<AuUser> result = baseService.allList(param);
		 return resultBeanFactory.getBean(result);
	}
	
	     
	

}

