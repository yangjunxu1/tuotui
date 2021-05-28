package com.zycw.tuotui.controller.sysrole;


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
import com.zycw.tuotui.iface.sysrole.ISysRoleService;
import com.zycw.tuotui.entity.sysrole.SysRole;
import java.util.Date;
import java.util.List;
import java.util.HashMap;


@ApiOperation(value = "b角色")
@Api(value = "b角色接口", tags = { "b角色接口" })
@Slf4j
@RestController
@RequestMapping("/sysRole")
public class SysRoleController extends BaseController <ISysRoleService, SysRole>{

    private static Logger logger = LoggerFactory.getLogger(SysRoleController.class);
       
    		   
    @Autowired
    private ResultBeanFactory resultBeanFactory;

     @ApiOperation(value = "根据id物理删除")
     @ApiImplicitParam(name = "sysRoleId", value = "ID", required = true, dataType = "String")
     @RequestMapping(value = "/deleteById", method = RequestMethod.GET)
     public ResultBean<String> deleteById(@RequestParam("sysRoleId") String sysRoleId) {
     	logger.info("sysRole.deleteById页面请求参数："+sysRoleId);
		try {
			baseService.deleteById(sysRoleId);
			return resultBeanFactory.getBean("删除成功");
		} catch (Exception e) {
			logger.error("失败原因:{}", e);
			return resultBeanFactory.getException(e);
		}
     }

     @ApiOperation(value = "根据id逻辑删除")
     @ApiImplicitParam(name = "sysRoleId", value = "ID", required = true, dataType = "String")
     @RequestMapping(value = "/deleteByIdLogic", method = RequestMethod.GET)
     public ResultBean<String> deleteByIdLogic(@RequestParam("sysRoleId") String sysRoleId) {
		logger.info("sysRole.deleteByIdLogic页面请求参数："+sysRoleId);
		try {
			baseService.deleteByIdLogic(sysRoleId);
			return resultBeanFactory.getBean("删除成功");
		} catch (Exception e) {
			logger.error("失败原因:{}", e);
			return resultBeanFactory.getException(e);
		}
     }
     

     @ApiOperation(value = "根据主键修改")
     @ApiImplicitParam(name = "sysRole", value = "b角色对象", required = true, dataType = "SysRole")
     @RequestMapping(value = "/updateObjById", method = RequestMethod.POST)
     public ResultBean<String> updateObjById(@RequestBody SysRole sysRole) {
         logger.info("sysRole.updateObjById页面请求参数："+sysRole);
		try {
			logger.info("sysRole.update页面请求参数："+sysRole);
			baseService.updateObjById(sysRole);
			return resultBeanFactory.getBean("修改成功");
        } catch (Exception e) {
            logger.error("修改失败:{}", e);
            return resultBeanFactory.getException(e);
        }
     }
     
     

     @ApiOperation(value = "新增")
     @ApiImplicitParam(name = "sysRole", value = "b角色对象", required = true, dataType = "SysRole")
     @RequestMapping(value = "/insertObj", method = RequestMethod.POST)
     public ResultBean<String> insertObj(@RequestBody SysRole sysRole) {
		try {
			logger.info("sysRole.insertObj页面请求参数："+sysRole);
	        baseService.insert(sysRole);
	        return resultBeanFactory.getBean("插入记录成功");
        } catch (Exception e) {
            logger.error("插入记录失败:{}", e);
            return resultBeanFactory.getException(e);
        }
     }
     
     @ApiOperation(value = "分页查询")
     @ApiImplicitParams({
		@ApiImplicitParam(name = "pageSize", value = "每页条数", required = false, dataType = "int"),
		@ApiImplicitParam(name = "pageNum", value = "当前页数", required = false, dataType = "int"),

    	@ApiImplicitParam(name = "sysRoleName", value = "return-lvM名称", required = false, dataType = "String"),
     })
     @RequestMapping(value = "/pageList", method = RequestMethod.POST)
     public ResultBeanFactory.ResultBean<PageInfo<SysRole>> pageList(@RequestBody HashMap<String,Object> params) {
		try {
			logger.info("sysRole.pageList页面请求参数："+params);
            int count = baseService.countNum(params);
            PageInfo<SysRole> result=baseService.pageList(params);
            result.setTotal(count);
            return resultBeanFactory.getBean(result);
		} catch (Exception e) {
			logger.error("SysRoleController.pageList Exception:{}", e);
			return resultBeanFactory.getException(e);
		}
     }


     @ApiOperation(value = "通过id查询")
     @ApiImplicitParam(name = "sysRoleId", value = "ID", required = true, dataType = "String")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "sysRoleId", value = "return-ID", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysRoleName", value = "return-lvM名称", required = false, dataType = "String"),
		})
     @RequestMapping(value = "/selectObjById", method = RequestMethod.GET)
     public ResultBean<HashMap<String,Object>> info(@RequestParam("sysRoleId") String sysRoleId) {
			logger.info("sysRole.selectObjById页面请求参数："+sysRoleId);
         HashMap<String,Object> sysRole = baseService.selectObjById(sysRoleId);
         return resultBeanFactory.getBean(sysRole);
     }
     
     
	
     @ApiOperation(value = "allList")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "sysRoleId", value = "return-ID", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysRoleName", value = "return-lvM名称", required = false, dataType = "String"),
     })
     @RequestMapping(value = "/allList", method = RequestMethod.POST)
	public ResultBean<List<SysRole>> allList(@RequestBody HashMap<String,Object> param){
			logger.info("sysRole.allList页面请求参数："+param);
		List<SysRole> result = baseService.allList(param);
		 return resultBeanFactory.getBean(result);
	}
	
	     
	

}

