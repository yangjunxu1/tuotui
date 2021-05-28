package com.zycw.tuotui.controller.sysuser;


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
import com.zycw.tuotui.iface.sysuser.ISysUserService;
import com.zycw.tuotui.entity.sysuser.SysUser;
import java.util.Date;
import java.util.List;
import java.util.HashMap;


@ApiOperation(value = "系统管理员")
@Api(value = "系统管理员接口", tags = { "系统管理员接口" })
@Slf4j
@RestController
@RequestMapping("/sysUser")
public class SysUserController extends BaseController <ISysUserService, SysUser>{

    private static Logger logger = LoggerFactory.getLogger(SysUserController.class);
       
    		   
    @Autowired
    private ResultBeanFactory resultBeanFactory;

     @ApiOperation(value = "根据id物理删除")
     @ApiImplicitParam(name = "sysUserId", value = "ID", required = true, dataType = "String")
     @RequestMapping(value = "/deleteById", method = RequestMethod.GET)
     public ResultBean<String> deleteById(@RequestParam("sysUserId") String sysUserId) {
     	logger.info("sysUser.deleteById页面请求参数："+sysUserId);
		try {
			baseService.deleteById(sysUserId);
			return resultBeanFactory.getBean("删除成功");
		} catch (Exception e) {
			logger.error("失败原因:{}", e);
			return resultBeanFactory.getException(e);
		}
     }

     @ApiOperation(value = "根据id逻辑删除")
     @ApiImplicitParam(name = "sysUserId", value = "ID", required = true, dataType = "String")
     @RequestMapping(value = "/deleteByIdLogic", method = RequestMethod.GET)
     public ResultBean<String> deleteByIdLogic(@RequestParam("sysUserId") String sysUserId) {
		logger.info("sysUser.deleteByIdLogic页面请求参数："+sysUserId);
		try {
			baseService.deleteByIdLogic(sysUserId);
			return resultBeanFactory.getBean("删除成功");
		} catch (Exception e) {
			logger.error("失败原因:{}", e);
			return resultBeanFactory.getException(e);
		}
     }
     

     @ApiOperation(value = "根据主键修改")
     @ApiImplicitParam(name = "sysUser", value = "系统管理员对象", required = true, dataType = "SysUser")
     @RequestMapping(value = "/updateObjById", method = RequestMethod.POST)
     public ResultBean<String> updateObjById(@RequestBody SysUser sysUser) {
         logger.info("sysUser.updateObjById页面请求参数："+sysUser);
		try {
			logger.info("sysUser.update页面请求参数："+sysUser);
			baseService.updateObjById(sysUser);
			return resultBeanFactory.getBean("修改成功");
        } catch (Exception e) {
            logger.error("修改失败:{}", e);
            return resultBeanFactory.getException(e);
        }
     }
     
     

     @ApiOperation(value = "新增")
     @ApiImplicitParam(name = "sysUser", value = "系统管理员对象", required = true, dataType = "SysUser")
     @RequestMapping(value = "/insertObj", method = RequestMethod.POST)
     public ResultBean<String> insertObj(@RequestBody SysUser sysUser) {
		try {
			logger.info("sysUser.insertObj页面请求参数："+sysUser);
	        baseService.insert(sysUser);
	        return resultBeanFactory.getBean("插入记录成功");
        } catch (Exception e) {
            logger.error("插入记录失败:{}", e);
            return resultBeanFactory.getException(e);
        }
     }
     
     @ApiOperation(value = "分页查询")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "sysUserRoleId", value = "lv管理组", required = false, dataType = "${dataType}"),
		@ApiImplicitParam(name = "pageSize", value = "每页条数", required = false, dataType = "int"),
		@ApiImplicitParam(name = "pageNum", value = "当前页数", required = false, dataType = "int"),







    	@ApiImplicitParam(name = "sysUserUpdatedTime", value = "return-修改日期", required = false, dataType = "Date"),
     })
     @RequestMapping(value = "/pageList", method = RequestMethod.POST)
     public ResultBeanFactory.ResultBean<PageInfo<SysUser>> pageList(@RequestBody HashMap<String,Object> params) {
		try {
			logger.info("sysUser.pageList页面请求参数："+params);
            int count = baseService.countNum(params);
            PageInfo<SysUser> result=baseService.pageList(params);
            result.setTotal(count);
            return resultBeanFactory.getBean(result);
		} catch (Exception e) {
			logger.error("SysUserController.pageList Exception:{}", e);
			return resultBeanFactory.getException(e);
		}
     }


     @ApiOperation(value = "通过id查询")
     @ApiImplicitParam(name = "sysUserId", value = "ID", required = true, dataType = "String")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "sysUserId", value = "return-id", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "sysUserUsername", value = "return-lvM用户名", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysUserPassword", value = "return-密码", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysRoleName", value = "return-lvM名称", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysUserTrueName", value = "return-lv真实姓名", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysUserMobile", value = "return-lv联系电话", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysUserCreatedTime", value = "return-lv添加日期", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "sysUserUpdatedTime", value = "return-修改日期", required = false, dataType = "Date"),
		})
     @RequestMapping(value = "/selectObjById", method = RequestMethod.GET)
     public ResultBean<HashMap<String,Object>> info(@RequestParam("sysUserId") String sysUserId) {
			logger.info("sysUser.selectObjById页面请求参数："+sysUserId);
         HashMap<String,Object> sysUser = baseService.selectObjById(sysUserId);
         return resultBeanFactory.getBean(sysUser);
     }
     
     
	
     @ApiOperation(value = "allList")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "sysUserRoleId", value = "lv管理组", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "sysUserId", value = "return-id", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "sysUserUsername", value = "return-lvM用户名", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysUserPassword", value = "return-密码", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysRoleName", value = "return-lvM名称", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysUserTrueName", value = "return-lv真实姓名", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysUserMobile", value = "return-lv联系电话", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysUserCreatedTime", value = "return-lv添加日期", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "sysUserUpdatedTime", value = "return-修改日期", required = false, dataType = "Date"),
     })
     @RequestMapping(value = "/allList", method = RequestMethod.POST)
	public ResultBean<List<SysUser>> allList(@RequestBody HashMap<String,Object> param){
			logger.info("sysUser.allList页面请求参数："+param);
		List<SysUser> result = baseService.allList(param);
		 return resultBeanFactory.getBean(result);
	}
	
	     
	

}

