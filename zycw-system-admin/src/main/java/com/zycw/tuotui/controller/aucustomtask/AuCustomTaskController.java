package com.zycw.tuotui.controller.aucustomtask;


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
import com.zycw.tuotui.iface.aucustomtask.IAuCustomTaskService;
import com.zycw.tuotui.entity.aucustomtask.AuCustomTask;
import java.util.Date;
import java.util.List;
import java.util.HashMap;


@ApiOperation(value = "企业任务")
@Api(value = "企业任务接口", tags = { "企业任务接口" })
@Slf4j
@RestController
@RequestMapping("/auCustomTask")
public class AuCustomTaskController extends BaseController <IAuCustomTaskService, AuCustomTask>{

    private static Logger logger = LoggerFactory.getLogger(AuCustomTaskController.class);
       
    		   
    @Autowired
    private ResultBeanFactory resultBeanFactory;

     @ApiOperation(value = "根据id物理删除")
     @ApiImplicitParam(name = "auCustomTaskId", value = "ID", required = true, dataType = "String")
     @RequestMapping(value = "/deleteById", method = RequestMethod.GET)
     public ResultBean<String> deleteById(@RequestParam("auCustomTaskId") String auCustomTaskId) {
     	logger.info("auCustomTask.deleteById页面请求参数："+auCustomTaskId);
		try {
			baseService.deleteById(auCustomTaskId);
			return resultBeanFactory.getBean("删除成功");
		} catch (Exception e) {
			logger.error("失败原因:{}", e);
			return resultBeanFactory.getException(e);
		}
     }

     @ApiOperation(value = "根据id逻辑删除")
     @ApiImplicitParam(name = "auCustomTaskId", value = "ID", required = true, dataType = "String")
     @RequestMapping(value = "/deleteByIdLogic", method = RequestMethod.GET)
     public ResultBean<String> deleteByIdLogic(@RequestParam("auCustomTaskId") String auCustomTaskId) {
		logger.info("auCustomTask.deleteByIdLogic页面请求参数："+auCustomTaskId);
		try {
			baseService.deleteByIdLogic(auCustomTaskId);
			return resultBeanFactory.getBean("删除成功");
		} catch (Exception e) {
			logger.error("失败原因:{}", e);
			return resultBeanFactory.getException(e);
		}
     }
     

     @ApiOperation(value = "根据主键修改")
     @ApiImplicitParam(name = "auCustomTask", value = "企业任务对象", required = true, dataType = "AuCustomTask")
     @RequestMapping(value = "/updateObjById", method = RequestMethod.POST)
     public ResultBean<String> updateObjById(@RequestBody AuCustomTask auCustomTask) {
         logger.info("auCustomTask.updateObjById页面请求参数："+auCustomTask);
		try {
			auCustomTask.setAuCustomTaskStartTime(new Date());
			auCustomTask.setAuCustomTaskEndTime(new Date());
			auCustomTask.setAuCustomTaskVerifyTime(new Date());
			auCustomTask.setAuCustomTaskReviewTime(new Date());
			auCustomTask.setAuCustomTaskCreatedTime(new Date());
			auCustomTask.setAuCustomTaskUpdatedTime(new Date());
			logger.info("auCustomTask.update页面请求参数："+auCustomTask);
			baseService.updateObjById(auCustomTask);
			return resultBeanFactory.getBean("修改成功");
        } catch (Exception e) {
            logger.error("修改失败:{}", e);
            return resultBeanFactory.getException(e);
        }
     }
     
     

     @ApiOperation(value = "新增")
     @ApiImplicitParam(name = "auCustomTask", value = "企业任务对象", required = true, dataType = "AuCustomTask")
     @RequestMapping(value = "/insertObj", method = RequestMethod.POST)
     public ResultBean<String> insertObj(@RequestBody AuCustomTask auCustomTask) {
		try {
			logger.info("auCustomTask.insertObj页面请求参数："+auCustomTask);
			auCustomTask.setAuCustomTaskStartTime(new Date());
			auCustomTask.setAuCustomTaskEndTime(new Date());
			auCustomTask.setAuCustomTaskVerifyTime(new Date());
			auCustomTask.setAuCustomTaskReviewTime(new Date());
			auCustomTask.setAuCustomTaskCreatedTime(new Date());
			auCustomTask.setAuCustomTaskUpdatedTime(new Date());
	        baseService.insert(auCustomTask);
	        return resultBeanFactory.getBean("插入记录成功");
        } catch (Exception e) {
            logger.error("插入记录失败:{}", e);
            return resultBeanFactory.getException(e);
        }
     }
     
     @ApiOperation(value = "分页查询")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "auCustomTaskCompaynId", value = "lv公司", required = false, dataType = "${dataType}"),
    	@ApiImplicitParam(name = "auCustomTaskAppId", value = "lvapp", required = false, dataType = "${dataType}"),
    	@ApiImplicitParam(name = "auCustomTaskUserId", value = "lv用户", required = false, dataType = "${dataType}"),
    	@ApiImplicitParam(name = "auCustomTaskTaskType", value = "lvS任务类型", required = false, dataType = "${dataType}"),
    	@ApiImplicitParam(name = "auCustomTaskTaskDepth", value = "lvS任务深度", required = false, dataType = "${dataType}"),
    	@ApiImplicitParam(name = "auCustomTaskAreaId", value = "lvS任务要求区", required = false, dataType = "${dataType}"),
    	@ApiImplicitParam(name = "auCustomTaskCityId", value = "lvS任务要求市", required = false, dataType = "${dataType}"),
    	@ApiImplicitParam(name = "auCustomTaskOccupationId", value = "lvS职业", required = false, dataType = "${dataType}"),
    	@ApiImplicitParam(name = "auCustomTaskProvinceId", value = "lvS任务要求省", required = false, dataType = "${dataType}"),
		@ApiImplicitParam(name = "pageSize", value = "每页条数", required = false, dataType = "int"),
		@ApiImplicitParam(name = "pageNum", value = "当前页数", required = false, dataType = "int"),
































    	@ApiImplicitParam(name = "auCustomTaskUpdatedTime", value = "return-u修改时间", required = false, dataType = "Date"),
     })
     @RequestMapping(value = "/pageList", method = RequestMethod.POST)
     public ResultBeanFactory.ResultBean<PageInfo<AuCustomTask>> pageList(@RequestBody HashMap<String,Object> params) {
		try {
			logger.info("auCustomTask.pageList页面请求参数："+params);
            int count = baseService.countNum(params);
            PageInfo<AuCustomTask> result=baseService.pageList(params);
            result.setTotal(count);
            return resultBeanFactory.getBean(result);
		} catch (Exception e) {
			logger.error("AuCustomTaskController.pageList Exception:{}", e);
			return resultBeanFactory.getException(e);
		}
     }


     @ApiOperation(value = "通过id查询")
     @ApiImplicitParam(name = "auCustomTaskId", value = "ID", required = true, dataType = "String")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "auCustomTaskId", value = "return-ID", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auCompanyAppName", value = "return-lvMapp名称", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auUserMobile", value = "return-lvM手机号", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCompanyAppName", value = "return-lvMapp名称", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCustomTaskTaskTitle", value = "return-lvM任务标题", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCustomTaskExtensionType", value = "return-lvS推广类型", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCustomTaskSchemeId", value = "return-lvS套餐id", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysTaskTypeTypeTitle", value = "return-lvM分类名称", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysTaskDepthDepthTitle", value = "return-lvM任务深度标题", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysProvinceName", value = "return-lvM省名称", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysCityName", value = "return-lvM市名称", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysAreaName", value = "return-lvM区名称", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auOccupationOccupationName", value = "return-Mlv职业", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCustomTaskStartTime", value = "return-lv开始日期", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auCustomTaskEndTime", value = "return-lv结束日期", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auCustomTaskUserSex", value = "return-lv任务要求用户性别:0-男,1-女,2-全部", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auCustomTaskUserAgeMin", value = "return-lv任务要求用户最小年龄", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auCustomTaskUserAgeMax", value = "return-lv任务要求用户最大年龄", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auCustomTaskHobbyType", value = "return-任务要求用户爱好", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCustomTaskExtensionItem", value = "return-v推广需求", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCustomTaskExtensionNumsItem", value = "return-lv推广人数", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCustomTaskCustomPrice", value = "return-lv定制价格", required = false, dataType = "float"),
    	@ApiImplicitParam(name = "auCustomTaskVerifyStatus", value = "return-lvs审核状态:0-通过,1-未通过", required = false, dataType = "float"),
    	@ApiImplicitParam(name = "auCustomTaskTaskContent", value = "return-v任务简介", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCustomTaskVeruftRefuse", value = "return-v拒绝原因", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCustomTaskVerifyTime", value = "return-ul初审时间", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auCustomTaskReviewStatus", value = "return-lvs复审状态:0-通过,1-未通过", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auCustomTaskReviewRefuse", value = "return-v复审拒绝理由", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCustomTaskReviewTime", value = "return-lv复审时间", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auCustomTaskTaskReward", value = "return-任务奖励金额", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auCustomTaskTaskStatus", value = "return-lvS任务状态:0-已审核,1-未审核,2-未通过,3-未开始,4-进行中,5-已结束", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auCustomTaskCreatedTime", value = "return-il创建时间", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auCustomTaskUpdatedTime", value = "return-u修改时间", required = false, dataType = "Date"),
		})
     @RequestMapping(value = "/selectObjById", method = RequestMethod.GET)
     public ResultBean<HashMap<String,Object>> info(@RequestParam("auCustomTaskId") String auCustomTaskId) {
			logger.info("auCustomTask.selectObjById页面请求参数："+auCustomTaskId);
         HashMap<String,Object> auCustomTask = baseService.selectObjById(auCustomTaskId);
         return resultBeanFactory.getBean(auCustomTask);
     }
     
     
	
     @ApiOperation(value = "allList")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "auCustomTaskCompaynId", value = "lv公司", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auCustomTaskAppId", value = "lvapp", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auCustomTaskUserId", value = "lv用户", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auCustomTaskTaskType", value = "lvS任务类型", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auCustomTaskTaskDepth", value = "lvS任务深度", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auCustomTaskAreaId", value = "lvS任务要求区", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auCustomTaskCityId", value = "lvS任务要求市", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auCustomTaskOccupationId", value = "lvS职业", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auCustomTaskProvinceId", value = "lvS任务要求省", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auCustomTaskId", value = "return-ID", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auCompanyAppName", value = "return-lvMapp名称", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auUserMobile", value = "return-lvM手机号", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCompanyAppName", value = "return-lvMapp名称", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCustomTaskTaskTitle", value = "return-lvM任务标题", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCustomTaskExtensionType", value = "return-lvS推广类型", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCustomTaskSchemeId", value = "return-lvS套餐id", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysTaskTypeTypeTitle", value = "return-lvM分类名称", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysTaskDepthDepthTitle", value = "return-lvM任务深度标题", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysProvinceName", value = "return-lvM省名称", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysCityName", value = "return-lvM市名称", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysAreaName", value = "return-lvM区名称", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auOccupationOccupationName", value = "return-Mlv职业", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCustomTaskStartTime", value = "return-lv开始日期", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auCustomTaskEndTime", value = "return-lv结束日期", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auCustomTaskUserSex", value = "return-lv任务要求用户性别:0-男,1-女,2-全部", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auCustomTaskUserAgeMin", value = "return-lv任务要求用户最小年龄", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auCustomTaskUserAgeMax", value = "return-lv任务要求用户最大年龄", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auCustomTaskHobbyType", value = "return-任务要求用户爱好", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCustomTaskExtensionItem", value = "return-v推广需求", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCustomTaskExtensionNumsItem", value = "return-lv推广人数", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCustomTaskCustomPrice", value = "return-lv定制价格", required = false, dataType = "float"),
    	@ApiImplicitParam(name = "auCustomTaskVerifyStatus", value = "return-lvs审核状态:0-通过,1-未通过", required = false, dataType = "float"),
    	@ApiImplicitParam(name = "auCustomTaskTaskContent", value = "return-v任务简介", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCustomTaskVeruftRefuse", value = "return-v拒绝原因", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCustomTaskVerifyTime", value = "return-ul初审时间", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auCustomTaskReviewStatus", value = "return-lvs复审状态:0-通过,1-未通过", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auCustomTaskReviewRefuse", value = "return-v复审拒绝理由", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCustomTaskReviewTime", value = "return-lv复审时间", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auCustomTaskTaskReward", value = "return-任务奖励金额", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auCustomTaskTaskStatus", value = "return-lvS任务状态:0-已审核,1-未审核,2-未通过,3-未开始,4-进行中,5-已结束", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auCustomTaskCreatedTime", value = "return-il创建时间", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auCustomTaskUpdatedTime", value = "return-u修改时间", required = false, dataType = "Date"),
     })
     @RequestMapping(value = "/allList", method = RequestMethod.POST)
	public ResultBean<List<AuCustomTask>> allList(@RequestBody HashMap<String,Object> param){
			logger.info("auCustomTask.allList页面请求参数："+param);
		List<AuCustomTask> result = baseService.allList(param);
		 return resultBeanFactory.getBean(result);
	}
	
	     
	

}

