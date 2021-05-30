package com.zycw.tuotui.controller.auusertask;


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
import com.zycw.tuotui.iface.auusertask.IAuUserTaskService;
import com.zycw.tuotui.entity.auusertask.AuUserTask;
import java.util.Date;
import java.util.List;
import java.util.HashMap;


@ApiOperation(value = "会员任务")
@Api(value = "会员任务接口", tags = { "会员任务接口" })
@Slf4j
@RestController
@RequestMapping("/auUserTask")
public class AuUserTaskController extends BaseController <IAuUserTaskService, AuUserTask>{

    private static Logger logger = LoggerFactory.getLogger(AuUserTaskController.class);
       
    		   
    @Autowired
    private ResultBeanFactory resultBeanFactory;

     @ApiOperation(value = "根据id物理删除")
     @ApiImplicitParam(name = "auUserTaskId", value = "ID", required = true, dataType = "String")
     @RequestMapping(value = "/deleteById", method = RequestMethod.GET)
     public ResultBean<String> deleteById(@RequestParam("auUserTaskId") String auUserTaskId) {
     	logger.info("auUserTask.deleteById页面请求参数："+auUserTaskId);
		try {
			baseService.deleteById(auUserTaskId);
			return resultBeanFactory.getBean("删除成功");
		} catch (Exception e) {
			logger.error("失败原因:{}", e);
			return resultBeanFactory.getException(e);
		}
     }

     @ApiOperation(value = "根据id逻辑删除")
     @ApiImplicitParam(name = "auUserTaskId", value = "ID", required = true, dataType = "String")
     @RequestMapping(value = "/deleteByIdLogic", method = RequestMethod.GET)
     public ResultBean<String> deleteByIdLogic(@RequestParam("auUserTaskId") String auUserTaskId) {
		logger.info("auUserTask.deleteByIdLogic页面请求参数："+auUserTaskId);
		try {
			baseService.deleteByIdLogic(auUserTaskId);
			return resultBeanFactory.getBean("删除成功");
		} catch (Exception e) {
			logger.error("失败原因:{}", e);
			return resultBeanFactory.getException(e);
		}
     }
     

     @ApiOperation(value = "根据主键修改")
     @ApiImplicitParam(name = "auUserTask", value = "会员任务对象", required = true, dataType = "AuUserTask")
     @RequestMapping(value = "/updateObjById", method = RequestMethod.POST)
     public ResultBean<String> updateObjById(@RequestBody AuUserTask auUserTask) {
         logger.info("auUserTask.updateObjById页面请求参数："+auUserTask);
		try {
			auUserTask.setAuUserTaskStartTime(new Date());
			auUserTask.setAuUserTaskEndTime(new Date());
			logger.info("auUserTask.update页面请求参数："+auUserTask);
			baseService.updateObjById(auUserTask);
			return resultBeanFactory.getBean("修改成功");
        } catch (Exception e) {
            logger.error("修改失败:{}", e);
            return resultBeanFactory.getException(e);
        }
     }
     
     

     @ApiOperation(value = "新增")
     @ApiImplicitParam(name = "auUserTask", value = "会员任务对象", required = true, dataType = "AuUserTask")
     @RequestMapping(value = "/insertObj", method = RequestMethod.POST)
     public ResultBean<String> insertObj(@RequestBody AuUserTask auUserTask) {
		try {
			logger.info("auUserTask.insertObj页面请求参数："+auUserTask);
			auUserTask.setAuUserTaskStartTime(new Date());
			auUserTask.setAuUserTaskEndTime(new Date());
	        baseService.insert(auUserTask);
	        return resultBeanFactory.getBean("插入记录成功");
        } catch (Exception e) {
            logger.error("插入记录失败:{}", e);
            return resultBeanFactory.getException(e);
        }
     }
     
     @ApiOperation(value = "分页查询")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "auUserTaskUserId", value = "lv用户", required = false, dataType = "${dataType}"),
    	@ApiImplicitParam(name = "auUserTaskTaskId", value = "lv任务", required = false, dataType = "${dataType}"),
		@ApiImplicitParam(name = "pageSize", value = "每页条数", required = false, dataType = "int"),
		@ApiImplicitParam(name = "pageNum", value = "当前页数", required = false, dataType = "int"),





    	@ApiImplicitParam(name = "auUserTaskEndTime", value = "return-lv结束时间", required = false, dataType = "Date"),
     })
     @RequestMapping(value = "/pageList", method = RequestMethod.POST)
     public ResultBeanFactory.ResultBean<PageInfo<AuUserTask>> pageList(@RequestBody HashMap<String,Object> params) {
		try {
			logger.info("auUserTask.pageList页面请求参数："+params);
            int count = baseService.countNum(params);
            PageInfo<AuUserTask> result=baseService.pageList(params);
            result.setTotal(count);
            return resultBeanFactory.getBean(result);
		} catch (Exception e) {
			logger.error("AuUserTaskController.pageList Exception:{}", e);
			return resultBeanFactory.getException(e);
		}
     }


     @ApiOperation(value = "通过id查询")
     @ApiImplicitParam(name = "auUserTaskId", value = "ID", required = true, dataType = "String")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "auUserTaskId", value = "return-id", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auUserMobile", value = "return-lvM手机号", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCustomTaskTaskTitle", value = "return-lvM任务标题", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auUserTaskTaskStatus", value = "return-lv完成状态:0-已完成，1-未开始，2-进行中，3-已完成，未结算，4-已结算", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auUserTaskStartTime", value = "return-lv开始时间", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auUserTaskEndTime", value = "return-lv结束时间", required = false, dataType = "Date"),
		})
     @RequestMapping(value = "/selectObjById", method = RequestMethod.GET)
     public ResultBean<HashMap<String,Object>> info(@RequestParam("auUserTaskId") String auUserTaskId) {
			logger.info("auUserTask.selectObjById页面请求参数："+auUserTaskId);
         HashMap<String,Object> auUserTask = baseService.selectObjById(auUserTaskId);
         return resultBeanFactory.getBean(auUserTask);
     }
     
     
	
     @ApiOperation(value = "allList")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "auUserTaskUserId", value = "lv用户", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auUserTaskTaskId", value = "lv任务", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auUserTaskId", value = "return-id", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auUserMobile", value = "return-lvM手机号", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCustomTaskTaskTitle", value = "return-lvM任务标题", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auUserTaskTaskStatus", value = "return-lv完成状态:0-已完成，1-未开始，2-进行中，3-已完成，未结算，4-已结算", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auUserTaskStartTime", value = "return-lv开始时间", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auUserTaskEndTime", value = "return-lv结束时间", required = false, dataType = "Date"),
     })
     @RequestMapping(value = "/allList", method = RequestMethod.POST)
	public ResultBean<List<AuUserTask>> allList(@RequestBody HashMap<String,Object> param){
			logger.info("auUserTask.allList页面请求参数："+param);
		List<AuUserTask> result = baseService.allList(param);
		 return resultBeanFactory.getBean(result);
	}
	
	     
	

}

