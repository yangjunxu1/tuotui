package com.zycw.tuotui.controller.autaskhobby;


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
import com.zycw.tuotui.iface.autaskhobby.IAuTaskHobbyService;
import com.zycw.tuotui.entity.autaskhobby.AuTaskHobby;
import java.util.Date;
import java.util.List;
import java.util.HashMap;


@ApiOperation(value = "爱好任务关联")
@Api(value = "爱好任务关联接口", tags = { "爱好任务关联接口" })
@Slf4j
@RestController
@RequestMapping("/auTaskHobby")
public class AuTaskHobbyController extends BaseController <IAuTaskHobbyService, AuTaskHobby>{

    private static Logger logger = LoggerFactory.getLogger(AuTaskHobbyController.class);
       
    		   
    @Autowired
    private ResultBeanFactory resultBeanFactory;

     @ApiOperation(value = "根据id物理删除")
     @ApiImplicitParam(name = "auTaskHobbyId", value = "ID", required = true, dataType = "String")
     @RequestMapping(value = "/deleteById", method = RequestMethod.GET)
     public ResultBean<String> deleteById(@RequestParam("auTaskHobbyId") String auTaskHobbyId) {
     	logger.info("auTaskHobby.deleteById页面请求参数："+auTaskHobbyId);
		try {
			baseService.deleteById(auTaskHobbyId);
			return resultBeanFactory.getBean("删除成功");
		} catch (Exception e) {
			logger.error("失败原因:{}", e);
			return resultBeanFactory.getException(e);
		}
     }

     @ApiOperation(value = "根据id逻辑删除")
     @ApiImplicitParam(name = "auTaskHobbyId", value = "ID", required = true, dataType = "String")
     @RequestMapping(value = "/deleteByIdLogic", method = RequestMethod.GET)
     public ResultBean<String> deleteByIdLogic(@RequestParam("auTaskHobbyId") String auTaskHobbyId) {
		logger.info("auTaskHobby.deleteByIdLogic页面请求参数："+auTaskHobbyId);
		try {
			baseService.deleteByIdLogic(auTaskHobbyId);
			return resultBeanFactory.getBean("删除成功");
		} catch (Exception e) {
			logger.error("失败原因:{}", e);
			return resultBeanFactory.getException(e);
		}
     }
     

     @ApiOperation(value = "根据主键修改")
     @ApiImplicitParam(name = "auTaskHobby", value = "爱好任务关联对象", required = true, dataType = "AuTaskHobby")
     @RequestMapping(value = "/updateObjById", method = RequestMethod.POST)
     public ResultBean<String> updateObjById(@RequestBody AuTaskHobby auTaskHobby) {
         logger.info("auTaskHobby.updateObjById页面请求参数："+auTaskHobby);
		try {
			logger.info("auTaskHobby.update页面请求参数："+auTaskHobby);
			baseService.updateObjById(auTaskHobby);
			return resultBeanFactory.getBean("修改成功");
        } catch (Exception e) {
            logger.error("修改失败:{}", e);
            return resultBeanFactory.getException(e);
        }
     }
     
     

     @ApiOperation(value = "新增")
     @ApiImplicitParam(name = "auTaskHobby", value = "爱好任务关联对象", required = true, dataType = "AuTaskHobby")
     @RequestMapping(value = "/insertObj", method = RequestMethod.POST)
     public ResultBean<String> insertObj(@RequestBody AuTaskHobby auTaskHobby) {
		try {
			logger.info("auTaskHobby.insertObj页面请求参数："+auTaskHobby);
	        baseService.insert(auTaskHobby);
	        return resultBeanFactory.getBean("插入记录成功");
        } catch (Exception e) {
            logger.error("插入记录失败:{}", e);
            return resultBeanFactory.getException(e);
        }
     }
     
     @ApiOperation(value = "分页查询")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "auTaskHobbyTaskId", value = "任务id", required = false, dataType = "${dataType}"),
    	@ApiImplicitParam(name = "auTaskHobbyHobbyId", value = "任务id", required = false, dataType = "${dataType}"),
		@ApiImplicitParam(name = "pageSize", value = "每页条数", required = false, dataType = "int"),
		@ApiImplicitParam(name = "pageNum", value = "当前页数", required = false, dataType = "int"),



    	@ApiImplicitParam(name = "auTaskHobbyDelFlag", value = "return-逻辑删除:0-未删除,1-已删除", required = false, dataType = "${dataType}"),
     })
     @RequestMapping(value = "/pageList", method = RequestMethod.POST)
     public ResultBeanFactory.ResultBean<PageInfo<AuTaskHobby>> pageList(@RequestBody HashMap<String,Object> params) {
		try {
			logger.info("auTaskHobby.pageList页面请求参数："+params);
            int count = baseService.countNum(params);
            PageInfo<AuTaskHobby> result=baseService.pageList(params);
            result.setTotal(count);
            return resultBeanFactory.getBean(result);
		} catch (Exception e) {
			logger.error("AuTaskHobbyController.pageList Exception:{}", e);
			return resultBeanFactory.getException(e);
		}
     }


     @ApiOperation(value = "通过id查询")
     @ApiImplicitParam(name = "auTaskHobbyId", value = "ID", required = true, dataType = "String")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "auTaskHobbyId", value = "return-id", required = false, dataType = "${dataType}"),
    	@ApiImplicitParam(name = "auCustomTaskTaskTitle", value = "return-lvM任务标题", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysHobbyHobbyTitle", value = "return-lvM爱好", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auTaskHobbyDelFlag", value = "return-逻辑删除:0-未删除,1-已删除", required = false, dataType = "String"),
		})
     @RequestMapping(value = "/selectObjById", method = RequestMethod.GET)
     public ResultBean<HashMap<String,Object>> info(@RequestParam("auTaskHobbyId") String auTaskHobbyId) {
			logger.info("auTaskHobby.selectObjById页面请求参数："+auTaskHobbyId);
         HashMap<String,Object> auTaskHobby = baseService.selectObjById(auTaskHobbyId);
         return resultBeanFactory.getBean(auTaskHobby);
     }
     
     
	
     @ApiOperation(value = "allList")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "auTaskHobbyTaskId", value = "任务id", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auTaskHobbyHobbyId", value = "任务id", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auTaskHobbyId", value = "return-id", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCustomTaskTaskTitle", value = "return-lvM任务标题", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysHobbyHobbyTitle", value = "return-lvM爱好", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auTaskHobbyDelFlag", value = "return-逻辑删除:0-未删除,1-已删除", required = false, dataType = "String")
     })
     @RequestMapping(value = "/allList", method = RequestMethod.POST)
	public ResultBean<List<AuTaskHobby>> allList(@RequestBody HashMap<String,Object> param){
			logger.info("auTaskHobby.allList页面请求参数："+param);
		List<AuTaskHobby> result = baseService.allList(param);
		 return resultBeanFactory.getBean(result);
	}
	
	     
	

}

