package com.zycw.tuotui.controller.sysresource;


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
import com.zycw.tuotui.iface.sysresource.ISysResourceService;
import com.zycw.tuotui.entity.sysresource.SysResource;
import java.util.Date;
import java.util.List;
import java.util.HashMap;


@ApiOperation(value = "菜单")
@Api(value = "菜单接口", tags = { "菜单接口" })
@Slf4j
@RestController
@RequestMapping("/sysResource")
public class SysResourceController extends BaseController <ISysResourceService, SysResource>{

    private static Logger logger = LoggerFactory.getLogger(SysResourceController.class);
       
    		   
    @Autowired
    private ResultBeanFactory resultBeanFactory;

     @ApiOperation(value = "根据id物理删除")
     @ApiImplicitParam(name = "sysResourceId", value = "ID", required = true, dataType = "String")
     @RequestMapping(value = "/deleteById", method = RequestMethod.GET)
     public ResultBean<String> deleteById(@RequestParam("sysResourceId") String sysResourceId) {
     	logger.info("sysResource.deleteById页面请求参数："+sysResourceId);
		try {
			baseService.deleteById(sysResourceId);
			return resultBeanFactory.getBean("删除成功");
		} catch (Exception e) {
			logger.error("失败原因:{}", e);
			return resultBeanFactory.getException(e);
		}
     }

     @ApiOperation(value = "根据id逻辑删除")
     @ApiImplicitParam(name = "sysResourceId", value = "ID", required = true, dataType = "String")
     @RequestMapping(value = "/deleteByIdLogic", method = RequestMethod.GET)
     public ResultBean<String> deleteByIdLogic(@RequestParam("sysResourceId") String sysResourceId) {
		logger.info("sysResource.deleteByIdLogic页面请求参数："+sysResourceId);
		try {
			baseService.deleteByIdLogic(sysResourceId);
			return resultBeanFactory.getBean("删除成功");
		} catch (Exception e) {
			logger.error("失败原因:{}", e);
			return resultBeanFactory.getException(e);
		}
     }
     

     @ApiOperation(value = "根据主键修改")
     @ApiImplicitParam(name = "sysResource", value = "菜单对象", required = true, dataType = "SysResource")
     @RequestMapping(value = "/updateObjById", method = RequestMethod.POST)
     public ResultBean<String> updateObjById(@RequestBody SysResource sysResource) {
         logger.info("sysResource.updateObjById页面请求参数："+sysResource);
		try {
			logger.info("sysResource.update页面请求参数："+sysResource);
			baseService.updateObjById(sysResource);
			return resultBeanFactory.getBean("修改成功");
        } catch (Exception e) {
            logger.error("修改失败:{}", e);
            return resultBeanFactory.getException(e);
        }
     }
     
     

     @ApiOperation(value = "新增")
     @ApiImplicitParam(name = "sysResource", value = "菜单对象", required = true, dataType = "SysResource")
     @RequestMapping(value = "/insertObj", method = RequestMethod.POST)
     public ResultBean<String> insertObj(@RequestBody SysResource sysResource) {
		try {
			logger.info("sysResource.insertObj页面请求参数："+sysResource);
	        baseService.insert(sysResource);
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






    	@ApiImplicitParam(name = "sysResourceRoles", value = "return-管理组", required = false, dataType = "String"),
     })
     @RequestMapping(value = "/pageList", method = RequestMethod.POST)
     public ResultBeanFactory.ResultBean<PageInfo<SysResource>> pageList(@RequestBody HashMap<String,Object> params) {
		try {
			logger.info("sysResource.pageList页面请求参数："+params);
            int count = baseService.countNum(params);
            PageInfo<SysResource> result=baseService.pageList(params);
            result.setTotal(count);
            return resultBeanFactory.getBean(result);
		} catch (Exception e) {
			logger.error("SysResourceController.pageList Exception:{}", e);
			return resultBeanFactory.getException(e);
		}
     }


     @ApiOperation(value = "通过id查询")
     @ApiImplicitParam(name = "sysResourceId", value = "ID", required = true, dataType = "String")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "sysResourceId", value = "return-ID", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysResourceName", value = "return-lv菜单名称", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysResourceRouter", value = "return-lv菜单路由", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysResourcePath", value = "return-lv文件路径", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysResourceIcon", value = "return-图标", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysResourceIshidden", value = "return-lv默认是否隐藏:0-隐藏,1-显示", required = false, dataType = "int"),
    	@ApiImplicitParam(name = "sysResourceRoles", value = "return-管理组", required = false, dataType = "String"),
		})
     @RequestMapping(value = "/selectObjById", method = RequestMethod.GET)
     public ResultBean<HashMap<String,Object>> info(@RequestParam("sysResourceId") String sysResourceId) {
			logger.info("sysResource.selectObjById页面请求参数："+sysResourceId);
         HashMap<String,Object> sysResource = baseService.selectObjById(sysResourceId);
         return resultBeanFactory.getBean(sysResource);
     }
     
     
	
     @ApiOperation(value = "allList")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "sysResourceId", value = "return-ID", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysResourceName", value = "return-lv菜单名称", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysResourceRouter", value = "return-lv菜单路由", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysResourcePath", value = "return-lv文件路径", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysResourceIcon", value = "return-图标", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysResourceIshidden", value = "return-lv默认是否隐藏:0-隐藏,1-显示", required = false, dataType = "int"),
    	@ApiImplicitParam(name = "sysResourceRoles", value = "return-管理组", required = false, dataType = "String")
     })
     @RequestMapping(value = "/allList", method = RequestMethod.POST)
	public ResultBean<List<SysResource>> allList(@RequestBody HashMap<String,Object> param){
			logger.info("sysResource.allList页面请求参数："+param);
		List<SysResource> result = baseService.allList(param);
		 return resultBeanFactory.getBean(result);
	}
	
	     
	

}

