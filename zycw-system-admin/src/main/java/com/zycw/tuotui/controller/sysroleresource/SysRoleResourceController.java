package com.zycw.tuotui.controller.sysroleresource;


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
import com.zycw.tuotui.iface.sysroleresource.ISysRoleResourceService;
import com.zycw.tuotui.entity.sysroleresource.SysRoleResource;
import java.util.Date;
import java.util.List;
import java.util.HashMap;


@ApiOperation(value = "")
@Api(value = "接口", tags = { "接口" })
@Slf4j
@RestController
@RequestMapping("/sysRoleResource")
public class SysRoleResourceController extends BaseController <ISysRoleResourceService, SysRoleResource>{

    private static Logger logger = LoggerFactory.getLogger(SysRoleResourceController.class);
       
    		   
    @Autowired
    private ResultBeanFactory resultBeanFactory;

     @ApiOperation(value = "根据id物理删除")
     @ApiImplicitParam(name = "sysRoleResourceId", value = "ID", required = true, dataType = "String")
     @RequestMapping(value = "/deleteById", method = RequestMethod.GET)
     public ResultBean<String> deleteById(@RequestParam("sysRoleResourceId") String sysRoleResourceId) {
     	logger.info("sysRoleResource.deleteById页面请求参数："+sysRoleResourceId);
		try {
			baseService.deleteById(sysRoleResourceId);
			return resultBeanFactory.getBean("删除成功");
		} catch (Exception e) {
			logger.error("失败原因:{}", e);
			return resultBeanFactory.getException(e);
		}
     }

     @ApiOperation(value = "根据id逻辑删除")
     @ApiImplicitParam(name = "sysRoleResourceId", value = "ID", required = true, dataType = "String")
     @RequestMapping(value = "/deleteByIdLogic", method = RequestMethod.GET)
     public ResultBean<String> deleteByIdLogic(@RequestParam("sysRoleResourceId") String sysRoleResourceId) {
		logger.info("sysRoleResource.deleteByIdLogic页面请求参数："+sysRoleResourceId);
		try {
			baseService.deleteByIdLogic(sysRoleResourceId);
			return resultBeanFactory.getBean("删除成功");
		} catch (Exception e) {
			logger.error("失败原因:{}", e);
			return resultBeanFactory.getException(e);
		}
     }
     

     @ApiOperation(value = "根据主键修改")
     @ApiImplicitParam(name = "sysRoleResource", value = "对象", required = true, dataType = "SysRoleResource")
     @RequestMapping(value = "/updateObjById", method = RequestMethod.POST)
     public ResultBean<String> updateObjById(@RequestBody SysRoleResource sysRoleResource) {
         logger.info("sysRoleResource.updateObjById页面请求参数："+sysRoleResource);
		try {
			logger.info("sysRoleResource.update页面请求参数："+sysRoleResource);
			baseService.updateObjById(sysRoleResource);
			return resultBeanFactory.getBean("修改成功");
        } catch (Exception e) {
            logger.error("修改失败:{}", e);
            return resultBeanFactory.getException(e);
        }
     }
     
     

     @ApiOperation(value = "新增")
     @ApiImplicitParam(name = "sysRoleResource", value = "对象", required = true, dataType = "SysRoleResource")
     @RequestMapping(value = "/insertObj", method = RequestMethod.POST)
     public ResultBean<String> insertObj(@RequestBody SysRoleResource sysRoleResource) {
		try {
			logger.info("sysRoleResource.insertObj页面请求参数："+sysRoleResource);
	        baseService.insert(sysRoleResource);
	        return resultBeanFactory.getBean("插入记录成功");
        } catch (Exception e) {
            logger.error("插入记录失败:{}", e);
            return resultBeanFactory.getException(e);
        }
     }
     
     @ApiOperation(value = "分页查询")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "sysRoleResourceRoleId", value = "", required = false, dataType = "${dataType}"),
    	@ApiImplicitParam(name = "sysRoleResourceResourceId", value = "", required = false, dataType = "${dataType}"),
		@ApiImplicitParam(name = "pageSize", value = "每页条数", required = false, dataType = "int"),
		@ApiImplicitParam(name = "pageNum", value = "当前页数", required = false, dataType = "int"),


    	@ApiImplicitParam(name = "${viewColumn.columnAlias}", value = "return-${viewColumn.comment}", required = false, dataType = "${dataType}")
     })
     @RequestMapping(value = "/pageList", method = RequestMethod.POST)
     public ResultBeanFactory.ResultBean<PageInfo<SysRoleResource>> pageList(@RequestBody HashMap<String,Object> params) {
		try {
			logger.info("sysRoleResource.pageList页面请求参数："+params);
            int count = baseService.countNum(params);
            PageInfo<SysRoleResource> result=baseService.pageList(params);
            result.setTotal(count);
            return resultBeanFactory.getBean(result);
		} catch (Exception e) {
			logger.error("SysRoleResourceController.pageList Exception:{}", e);
			return resultBeanFactory.getException(e);
		}
     }


     @ApiOperation(value = "通过id查询")
     @ApiImplicitParam(name = "sysRoleResourceId", value = "ID", required = true, dataType = "String")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "sysRoleResourceId", value = "return-id", required = false, dataType = "${dataType}"),
    	@ApiImplicitParam(name = "sysRoleName", value = "return-lvM名称", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "${viewColumn.columnAlias}", value = "return-${viewColumn.comment}", required = false, dataType = "String")
		})
     @RequestMapping(value = "/selectObjById", method = RequestMethod.GET)
     public ResultBean<HashMap<String,Object>> info(@RequestParam("sysRoleResourceId") String sysRoleResourceId) {
			logger.info("sysRoleResource.selectObjById页面请求参数："+sysRoleResourceId);
         HashMap<String,Object> sysRoleResource = baseService.selectObjById(sysRoleResourceId);
         return resultBeanFactory.getBean(sysRoleResource);
     }
     
     
	
     @ApiOperation(value = "allList")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "sysRoleResourceRoleId", value = "", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysRoleResourceResourceId", value = "", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysRoleResourceId", value = "return-id", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysRoleName", value = "return-lvM名称", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "${viewColumn.columnAlias}", value = "return-${viewColumn.comment}", required = false, dataType = "String")
     })
     @RequestMapping(value = "/allList", method = RequestMethod.POST)
	public ResultBean<List<SysRoleResource>> allList(@RequestBody HashMap<String,Object> param){
			logger.info("sysRoleResource.allList页面请求参数："+param);
		List<SysRoleResource> result = baseService.allList(param);
		 return resultBeanFactory.getBean(result);
	}
	
	     
	

}

