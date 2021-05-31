package com.zycw.tuotui.controller.systaskactivity;


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
import com.zycw.tuotui.iface.systaskactivity.ISysTaskActivityService;
import com.zycw.tuotui.entity.systaskactivity.SysTaskActivity;
import java.util.Date;
import java.util.List;
import java.util.HashMap;


@ApiOperation(value = "任务活动信息")
@Api(value = "任务活动信息接口", tags = { "任务活动信息接口" })
@Slf4j
@RestController
@RequestMapping("/sysTaskActivity")
public class SysTaskActivityController extends BaseController <ISysTaskActivityService, SysTaskActivity>{

    private static Logger logger = LoggerFactory.getLogger(SysTaskActivityController.class);
       
    		   
    @Autowired
    private ResultBeanFactory resultBeanFactory;

     @ApiOperation(value = "根据id物理删除")
     @ApiImplicitParam(name = "sysTaskActivityId", value = "ID", required = true, dataType = "String")
     @RequestMapping(value = "/deleteById", method = RequestMethod.GET)
     public ResultBean<String> deleteById(@RequestParam("sysTaskActivityId") String sysTaskActivityId) {
     	logger.info("sysTaskActivity.deleteById页面请求参数："+sysTaskActivityId);
		try {
			baseService.deleteById(sysTaskActivityId);
			return resultBeanFactory.getBean("删除成功");
		} catch (Exception e) {
			logger.error("失败原因:{}", e);
			return resultBeanFactory.getException(e);
		}
     }

     @ApiOperation(value = "根据id逻辑删除")
     @ApiImplicitParam(name = "sysTaskActivityId", value = "ID", required = true, dataType = "String")
     @RequestMapping(value = "/deleteByIdLogic", method = RequestMethod.GET)
     public ResultBean<String> deleteByIdLogic(@RequestParam("sysTaskActivityId") String sysTaskActivityId) {
		logger.info("sysTaskActivity.deleteByIdLogic页面请求参数："+sysTaskActivityId);
		try {
			baseService.deleteByIdLogic(sysTaskActivityId);
			return resultBeanFactory.getBean("删除成功");
		} catch (Exception e) {
			logger.error("失败原因:{}", e);
			return resultBeanFactory.getException(e);
		}
     }
     

     @ApiOperation(value = "根据主键修改")
     @ApiImplicitParam(name = "sysTaskActivity", value = "任务活动信息对象", required = true, dataType = "SysTaskActivity")
     @RequestMapping(value = "/updateObjById", method = RequestMethod.POST)
     public ResultBean<String> updateObjById(@RequestBody SysTaskActivity sysTaskActivity) {
         logger.info("sysTaskActivity.updateObjById页面请求参数："+sysTaskActivity);
		try {
			logger.info("sysTaskActivity.update页面请求参数："+sysTaskActivity);
			baseService.updateObjById(sysTaskActivity);
			return resultBeanFactory.getBean("修改成功");
        } catch (Exception e) {
            logger.error("修改失败:{}", e);
            return resultBeanFactory.getException(e);
        }
     }
     
     

     @ApiOperation(value = "新增")
     @ApiImplicitParam(name = "sysTaskActivity", value = "任务活动信息对象", required = true, dataType = "SysTaskActivity")
     @RequestMapping(value = "/insertObj", method = RequestMethod.POST)
     public ResultBean<String> insertObj(@RequestBody SysTaskActivity sysTaskActivity) {
		try {
			logger.info("sysTaskActivity.insertObj页面请求参数："+sysTaskActivity);
	        baseService.insert(sysTaskActivity);
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



    	@ApiImplicitParam(name = "sysTaskActivityDelFlag", value = "return-逻辑删除:0-未删除,1-已删除", required = false, dataType = "${dataType}"),
     })
     @RequestMapping(value = "/pageList", method = RequestMethod.POST)
     public ResultBeanFactory.ResultBean<PageInfo<SysTaskActivity>> pageList(@RequestBody HashMap<String,Object> params) {
		try {
			logger.info("sysTaskActivity.pageList页面请求参数："+params);
            int count = baseService.countNum(params);
            PageInfo<SysTaskActivity> result=baseService.pageList(params);
            result.setTotal(count);
            return resultBeanFactory.getBean(result);
		} catch (Exception e) {
			logger.error("SysTaskActivityController.pageList Exception:{}", e);
			return resultBeanFactory.getException(e);
		}
     }


     @ApiOperation(value = "通过id查询")
     @ApiImplicitParam(name = "sysTaskActivityId", value = "ID", required = true, dataType = "String")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "sysTaskActivityId", value = "return-ID", required = false, dataType = "${dataType}"),
    	@ApiImplicitParam(name = "sysTaskActivityActivityName", value = "return-lvM活动名称", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysTaskActivityActivityCode", value = "return-lv活动编码", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysTaskActivityDelFlag", value = "return-逻辑删除:0-未删除,1-已删除", required = false, dataType = "String"),
		})
     @RequestMapping(value = "/selectObjById", method = RequestMethod.GET)
     public ResultBean<HashMap<String,Object>> info(@RequestParam("sysTaskActivityId") String sysTaskActivityId) {
			logger.info("sysTaskActivity.selectObjById页面请求参数："+sysTaskActivityId);
         HashMap<String,Object> sysTaskActivity = baseService.selectObjById(sysTaskActivityId);
         return resultBeanFactory.getBean(sysTaskActivity);
     }
     
     
	
     @ApiOperation(value = "allList")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "sysTaskActivityId", value = "return-ID", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysTaskActivityActivityName", value = "return-lvM活动名称", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysTaskActivityActivityCode", value = "return-lv活动编码", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysTaskActivityDelFlag", value = "return-逻辑删除:0-未删除,1-已删除", required = false, dataType = "String")
     })
     @RequestMapping(value = "/allList", method = RequestMethod.POST)
	public ResultBean<List<SysTaskActivity>> allList(@RequestBody HashMap<String,Object> param){
			logger.info("sysTaskActivity.allList页面请求参数："+param);
		List<SysTaskActivity> result = baseService.allList(param);
		 return resultBeanFactory.getBean(result);
	}
	
	     
	

}

