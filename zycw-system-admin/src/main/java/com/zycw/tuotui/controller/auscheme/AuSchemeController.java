package com.zycw.tuotui.controller.auscheme;


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
import com.zycw.tuotui.iface.auscheme.IAuSchemeService;
import com.zycw.tuotui.entity.auscheme.AuScheme;
import java.util.Date;
import java.util.List;
import java.util.HashMap;


@ApiOperation(value = "套餐表")
@Api(value = "套餐表接口", tags = { "套餐表接口" })
@Slf4j
@RestController
@RequestMapping("/auScheme")
public class AuSchemeController extends BaseController <IAuSchemeService, AuScheme>{

    private static Logger logger = LoggerFactory.getLogger(AuSchemeController.class);
       
    		   
    @Autowired
    private ResultBeanFactory resultBeanFactory;

     @ApiOperation(value = "根据id物理删除")
     @ApiImplicitParam(name = "auSchemeId", value = "ID", required = true, dataType = "String")
     @RequestMapping(value = "/deleteById", method = RequestMethod.GET)
     public ResultBean<String> deleteById(@RequestParam("auSchemeId") String auSchemeId) {
     	logger.info("auScheme.deleteById页面请求参数："+auSchemeId);
		try {
			baseService.deleteById(auSchemeId);
			return resultBeanFactory.getBean("删除成功");
		} catch (Exception e) {
			logger.error("失败原因:{}", e);
			return resultBeanFactory.getException(e);
		}
     }

     @ApiOperation(value = "根据id逻辑删除")
     @ApiImplicitParam(name = "auSchemeId", value = "ID", required = true, dataType = "String")
     @RequestMapping(value = "/deleteByIdLogic", method = RequestMethod.GET)
     public ResultBean<String> deleteByIdLogic(@RequestParam("auSchemeId") String auSchemeId) {
		logger.info("auScheme.deleteByIdLogic页面请求参数："+auSchemeId);
		try {
			baseService.deleteByIdLogic(auSchemeId);
			return resultBeanFactory.getBean("删除成功");
		} catch (Exception e) {
			logger.error("失败原因:{}", e);
			return resultBeanFactory.getException(e);
		}
     }
     

     @ApiOperation(value = "根据主键修改")
     @ApiImplicitParam(name = "auScheme", value = "套餐表对象", required = true, dataType = "AuScheme")
     @RequestMapping(value = "/updateObjById", method = RequestMethod.POST)
     public ResultBean<String> updateObjById(@RequestBody AuScheme auScheme) {
         logger.info("auScheme.updateObjById页面请求参数："+auScheme);
		try {
			auScheme.setAuSchemeCreatedTime(new Date());
			auScheme.setAuSchemeUpdatedTime(new Date());
			logger.info("auScheme.update页面请求参数："+auScheme);
			baseService.updateObjById(auScheme);
			return resultBeanFactory.getBean("修改成功");
        } catch (Exception e) {
            logger.error("修改失败:{}", e);
            return resultBeanFactory.getException(e);
        }
     }
     
     

     @ApiOperation(value = "新增")
     @ApiImplicitParam(name = "auScheme", value = "套餐表对象", required = true, dataType = "AuScheme")
     @RequestMapping(value = "/insertObj", method = RequestMethod.POST)
     public ResultBean<String> insertObj(@RequestBody AuScheme auScheme) {
		try {
			logger.info("auScheme.insertObj页面请求参数："+auScheme);
			auScheme.setAuSchemeCreatedTime(new Date());
			auScheme.setAuSchemeUpdatedTime(new Date());
	        baseService.insert(auScheme);
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










    	@ApiImplicitParam(name = "auSchemeDelFlag", value = "return-逻辑删除:0-未删除,1-已删除", required = false, dataType = "${dataType}"),
     })
     @RequestMapping(value = "/pageList", method = RequestMethod.POST)
     public ResultBeanFactory.ResultBean<PageInfo<AuScheme>> pageList(@RequestBody HashMap<String,Object> params) {
		try {
			logger.info("auScheme.pageList页面请求参数："+params);
            int count = baseService.countNum(params);
            PageInfo<AuScheme> result=baseService.pageList(params);
            result.setTotal(count);
            return resultBeanFactory.getBean(result);
		} catch (Exception e) {
			logger.error("AuSchemeController.pageList Exception:{}", e);
			return resultBeanFactory.getException(e);
		}
     }


     @ApiOperation(value = "通过id查询")
     @ApiImplicitParam(name = "auSchemeId", value = "ID", required = true, dataType = "String")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "auSchemeId", value = "return-i-主键", required = false, dataType = "int"),
    	@ApiImplicitParam(name = "auSchemeName", value = "return-lv-套餐名称", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auSchemePrice", value = "return-lv-价格", required = false, dataType = "float"),
    	@ApiImplicitParam(name = "auSchemeContent", value = "return-lv-套餐内容", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auSchemeStatus", value = "return-lvs-套餐状态 1-启用 0-停用", required = false, dataType = "int"),
    	@ApiImplicitParam(name = "auSchemeExtensionType", value = "return-推广类型", required = false, dataType = "int"),
    	@ApiImplicitParam(name = "auSchemeTaskType", value = "return-lv任务类型", required = false, dataType = "int"),
    	@ApiImplicitParam(name = "auSchemeTaskDepth", value = "return-lv任务深度", required = false, dataType = "int"),
    	@ApiImplicitParam(name = "auSchemeCreatedTime", value = "return-创建时间", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auSchemeUpdatedTime", value = "return-i-更新时间", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auSchemeDelFlag", value = "return-逻辑删除:0-未删除,1-已删除", required = false, dataType = "Date"),
		})
     @RequestMapping(value = "/selectObjById", method = RequestMethod.GET)
     public ResultBean<HashMap<String,Object>> info(@RequestParam("auSchemeId") String auSchemeId) {
			logger.info("auScheme.selectObjById页面请求参数："+auSchemeId);
         HashMap<String,Object> auScheme = baseService.selectObjById(auSchemeId);
         return resultBeanFactory.getBean(auScheme);
     }
     
     
	
     @ApiOperation(value = "allList")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "auSchemeId", value = "return-i-主键", required = false, dataType = "int"),
    	@ApiImplicitParam(name = "auSchemeName", value = "return-lv-套餐名称", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auSchemePrice", value = "return-lv-价格", required = false, dataType = "float"),
    	@ApiImplicitParam(name = "auSchemeContent", value = "return-lv-套餐内容", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auSchemeStatus", value = "return-lvs-套餐状态 1-启用 0-停用", required = false, dataType = "int"),
    	@ApiImplicitParam(name = "auSchemeExtensionType", value = "return-推广类型", required = false, dataType = "int"),
    	@ApiImplicitParam(name = "auSchemeTaskType", value = "return-lv任务类型", required = false, dataType = "int"),
    	@ApiImplicitParam(name = "auSchemeTaskDepth", value = "return-lv任务深度", required = false, dataType = "int"),
    	@ApiImplicitParam(name = "auSchemeCreatedTime", value = "return-创建时间", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auSchemeUpdatedTime", value = "return-i-更新时间", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auSchemeDelFlag", value = "return-逻辑删除:0-未删除,1-已删除", required = false, dataType = "Date")
     })
     @RequestMapping(value = "/allList", method = RequestMethod.POST)
	public ResultBean<List<AuScheme>> allList(@RequestBody HashMap<String,Object> param){
			logger.info("auScheme.allList页面请求参数："+param);
		List<AuScheme> result = baseService.allList(param);
		 return resultBeanFactory.getBean(result);
	}
	
	     
	

}

