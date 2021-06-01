package com.zycw.tuotui.controller.aucompanyapp;


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
import com.zycw.tuotui.iface.aucompanyapp.IAuCompanyAppService;
import com.zycw.tuotui.entity.aucompanyapp.AuCompanyApp;
import java.util.Date;
import java.util.List;
import java.util.HashMap;


@ApiOperation(value = "企业app")
@Api(value = "企业app接口", tags = { "企业app接口" })
@Slf4j
@RestController
@RequestMapping("/auCompanyApp")
public class AuCompanyAppController extends BaseController <IAuCompanyAppService, AuCompanyApp>{

    private static Logger logger = LoggerFactory.getLogger(AuCompanyAppController.class);
       
    		   
    @Autowired
    private ResultBeanFactory resultBeanFactory;

     @ApiOperation(value = "根据id物理删除")
     @ApiImplicitParam(name = "auCompanyAppId", value = "ID", required = true, dataType = "String")
     @RequestMapping(value = "/deleteById", method = RequestMethod.GET)
     public ResultBean<String> deleteById(@RequestParam("auCompanyAppId") String auCompanyAppId) {
     	logger.info("auCompanyApp.deleteById页面请求参数："+auCompanyAppId);
		try {
			baseService.deleteById(auCompanyAppId);
			return resultBeanFactory.getBean("删除成功");
		} catch (Exception e) {
			logger.error("失败原因:{}", e);
			return resultBeanFactory.getException(e);
		}
     }

     @ApiOperation(value = "根据id逻辑删除")
     @ApiImplicitParam(name = "auCompanyAppId", value = "ID", required = true, dataType = "String")
     @RequestMapping(value = "/deleteByIdLogic", method = RequestMethod.GET)
     public ResultBean<String> deleteByIdLogic(@RequestParam("auCompanyAppId") String auCompanyAppId) {
		logger.info("auCompanyApp.deleteByIdLogic页面请求参数："+auCompanyAppId);
		try {
			baseService.deleteByIdLogic(auCompanyAppId);
			return resultBeanFactory.getBean("删除成功");
		} catch (Exception e) {
			logger.error("失败原因:{}", e);
			return resultBeanFactory.getException(e);
		}
     }
     

     @ApiOperation(value = "根据主键修改")
     @ApiImplicitParam(name = "auCompanyApp", value = "企业app对象", required = true, dataType = "AuCompanyApp")
     @RequestMapping(value = "/updateObjById", method = RequestMethod.POST)
     public ResultBean<String> updateObjById(@RequestBody AuCompanyApp auCompanyApp) {
         logger.info("auCompanyApp.updateObjById页面请求参数："+auCompanyApp);
		try {
			auCompanyApp.setAuCompanyAppVerifyTime(new Date());
			auCompanyApp.setAuCompanyAppReviewTime(new Date());
			auCompanyApp.setAuCompanyAppCreatedTime(new Date());
			auCompanyApp.setAuCompanyAppUpdatedTime(new Date());
			logger.info("auCompanyApp.update页面请求参数："+auCompanyApp);
			baseService.updateObjById(auCompanyApp);
			return resultBeanFactory.getBean("修改成功");
        } catch (Exception e) {
            logger.error("修改失败:{}", e);
            return resultBeanFactory.getException(e);
        }
     }
     
     

     @ApiOperation(value = "新增")
     @ApiImplicitParam(name = "auCompanyApp", value = "企业app对象", required = true, dataType = "AuCompanyApp")
     @RequestMapping(value = "/insertObj", method = RequestMethod.POST)
     public ResultBean<String> insertObj(@RequestBody AuCompanyApp auCompanyApp) {
		try {
			logger.info("auCompanyApp.insertObj页面请求参数："+auCompanyApp);
			auCompanyApp.setAuCompanyAppVerifyTime(new Date());
			auCompanyApp.setAuCompanyAppReviewTime(new Date());
			auCompanyApp.setAuCompanyAppCreatedTime(new Date());
			auCompanyApp.setAuCompanyAppUpdatedTime(new Date());
	        baseService.insert(auCompanyApp);
	        return resultBeanFactory.getBean("插入记录成功");
        } catch (Exception e) {
            logger.error("插入记录失败:{}", e);
            return resultBeanFactory.getException(e);
        }
     }
     
     @ApiOperation(value = "分页查询")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "auCompanyAppCompanyId", value = "lv公司", required = false, dataType = "${dataType}"),
		@ApiImplicitParam(name = "pageSize", value = "每页条数", required = false, dataType = "int"),
		@ApiImplicitParam(name = "pageNum", value = "当前页数", required = false, dataType = "int"),















    	@ApiImplicitParam(name = "auCompanyAppDelFlag", value = "return-逻辑删除:0-未删除,1-已删除", required = false, dataType = "${dataType}"),
     })
     @RequestMapping(value = "/pageList", method = RequestMethod.POST)
     public ResultBeanFactory.ResultBean<PageInfo<AuCompanyApp>> pageList(@RequestBody HashMap<String,Object> params) {
		try {
			logger.info("auCompanyApp.pageList页面请求参数："+params);
            int count = baseService.countNum(params);
            PageInfo<AuCompanyApp> result=baseService.pageList(params);
            result.setTotal(count);
            return resultBeanFactory.getBean(result);
		} catch (Exception e) {
			logger.error("AuCompanyAppController.pageList Exception:{}", e);
			return resultBeanFactory.getException(e);
		}
     }


     @ApiOperation(value = "通过id查询")
     @ApiImplicitParam(name = "auCompanyAppId", value = "ID", required = true, dataType = "String")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "auCompanyAppId", value = "return-id", required = false, dataType = "${dataType}"),
    	@ApiImplicitParam(name = "auCompanyAppName", value = "return-lvMapp名称", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCompanyAppName", value = "return-lvMapp名称", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCompanyAppRemark", value = "return-lvapp介绍", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCompanyAppIconPath", value = "return-lvapp图标", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCompanyAppAppSize", value = "return-app大小", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCompanyAppDownUrl", value = "return-lvapp下载地址", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCompanyAppVerifyStatus", value = "return-lv审核状态:0-通过,1-未通过", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCompanyAppVeruftRefuse", value = "return-v拒绝原因", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCompanyAppVerifyTime", value = "return-ul初审时间", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auCompanyAppReviewStatus", value = "return-lv复审状态:0-通过,1-未通过", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auCompanyAppReviewRefuse", value = "return-v复审拒绝理由", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCompanyAppReviewTime", value = "return-lv复审时间", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auCompanyAppCreatedTime", value = "return-i-创建时间", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auCompanyAppUpdatedTime", value = "return-i-修改时间", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auCompanyAppDelFlag", value = "return-逻辑删除:0-未删除,1-已删除", required = false, dataType = "Date"),
		})
     @RequestMapping(value = "/selectObjById", method = RequestMethod.GET)
     public ResultBean<HashMap<String,Object>> info(@RequestParam("auCompanyAppId") String auCompanyAppId) {
			logger.info("auCompanyApp.selectObjById页面请求参数："+auCompanyAppId);
         HashMap<String,Object> auCompanyApp = baseService.selectObjById(auCompanyAppId);
         return resultBeanFactory.getBean(auCompanyApp);
     }
     
     
	
     @ApiOperation(value = "allList")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "auCompanyAppCompanyId", value = "lv公司", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auCompanyAppId", value = "return-id", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auCompanyAppName", value = "return-lvMapp名称", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCompanyAppName", value = "return-lvMapp名称", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCompanyAppRemark", value = "return-lvapp介绍", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCompanyAppIconPath", value = "return-lvapp图标", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCompanyAppAppSize", value = "return-app大小", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCompanyAppDownUrl", value = "return-lvapp下载地址", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCompanyAppVerifyStatus", value = "return-lv审核状态:0-通过,1-未通过", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCompanyAppVeruftRefuse", value = "return-v拒绝原因", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCompanyAppVerifyTime", value = "return-ul初审时间", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auCompanyAppReviewStatus", value = "return-lv复审状态:0-通过,1-未通过", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auCompanyAppReviewRefuse", value = "return-v复审拒绝理由", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCompanyAppReviewTime", value = "return-lv复审时间", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auCompanyAppCreatedTime", value = "return-i-创建时间", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auCompanyAppUpdatedTime", value = "return-i-修改时间", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auCompanyAppDelFlag", value = "return-逻辑删除:0-未删除,1-已删除", required = false, dataType = "Date")
     })
     @RequestMapping(value = "/allList", method = RequestMethod.POST)
	public ResultBean<List<AuCompanyApp>> allList(@RequestBody HashMap<String,Object> param){
			logger.info("auCompanyApp.allList页面请求参数："+param);
		List<AuCompanyApp> result = baseService.allList(param);
		 return resultBeanFactory.getBean(result);
	}
	
	     
	

}

