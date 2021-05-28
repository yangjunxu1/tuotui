package com.zycw.tuotui.controller.systasktype;


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
import com.zycw.tuotui.iface.systasktype.ISysTaskTypeService;
import com.zycw.tuotui.entity.systasktype.SysTaskType;
import java.util.Date;
import java.util.List;
import java.util.HashMap;


@ApiOperation(value = "b任务类型")
@Api(value = "b任务类型接口", tags = { "b任务类型接口" })
@Slf4j
@RestController
@RequestMapping("/sysTaskType")
public class SysTaskTypeController extends BaseController <ISysTaskTypeService, SysTaskType>{

    private static Logger logger = LoggerFactory.getLogger(SysTaskTypeController.class);
       
    		   
    @Autowired
    private ResultBeanFactory resultBeanFactory;

     @ApiOperation(value = "根据id物理删除")
     @ApiImplicitParam(name = "sysTaskTypeId", value = "ID", required = true, dataType = "String")
     @RequestMapping(value = "/deleteById", method = RequestMethod.GET)
     public ResultBean<String> deleteById(@RequestParam("sysTaskTypeId") String sysTaskTypeId) {
     	logger.info("sysTaskType.deleteById页面请求参数："+sysTaskTypeId);
		try {
			baseService.deleteById(sysTaskTypeId);
			return resultBeanFactory.getBean("删除成功");
		} catch (Exception e) {
			logger.error("失败原因:{}", e);
			return resultBeanFactory.getException(e);
		}
     }

     @ApiOperation(value = "根据id逻辑删除")
     @ApiImplicitParam(name = "sysTaskTypeId", value = "ID", required = true, dataType = "String")
     @RequestMapping(value = "/deleteByIdLogic", method = RequestMethod.GET)
     public ResultBean<String> deleteByIdLogic(@RequestParam("sysTaskTypeId") String sysTaskTypeId) {
		logger.info("sysTaskType.deleteByIdLogic页面请求参数："+sysTaskTypeId);
		try {
			baseService.deleteByIdLogic(sysTaskTypeId);
			return resultBeanFactory.getBean("删除成功");
		} catch (Exception e) {
			logger.error("失败原因:{}", e);
			return resultBeanFactory.getException(e);
		}
     }
     

     @ApiOperation(value = "根据主键修改")
     @ApiImplicitParam(name = "sysTaskType", value = "b任务类型对象", required = true, dataType = "SysTaskType")
     @RequestMapping(value = "/updateObjById", method = RequestMethod.POST)
     public ResultBean<String> updateObjById(@RequestBody SysTaskType sysTaskType) {
         logger.info("sysTaskType.updateObjById页面请求参数："+sysTaskType);
		try {
			logger.info("sysTaskType.update页面请求参数："+sysTaskType);
			baseService.updateObjById(sysTaskType);
			return resultBeanFactory.getBean("修改成功");
        } catch (Exception e) {
            logger.error("修改失败:{}", e);
            return resultBeanFactory.getException(e);
        }
     }
     
     

     @ApiOperation(value = "新增")
     @ApiImplicitParam(name = "sysTaskType", value = "b任务类型对象", required = true, dataType = "SysTaskType")
     @RequestMapping(value = "/insertObj", method = RequestMethod.POST)
     public ResultBean<String> insertObj(@RequestBody SysTaskType sysTaskType) {
		try {
			logger.info("sysTaskType.insertObj页面请求参数："+sysTaskType);
	        baseService.insert(sysTaskType);
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



    	@ApiImplicitParam(name = "sysTaskTypeTypeContent", value = "return-简介", required = false, dataType = "String"),
     })
     @RequestMapping(value = "/pageList", method = RequestMethod.POST)
     public ResultBeanFactory.ResultBean<PageInfo<SysTaskType>> pageList(@RequestBody HashMap<String,Object> params) {
		try {
			logger.info("sysTaskType.pageList页面请求参数："+params);
            int count = baseService.countNum(params);
            PageInfo<SysTaskType> result=baseService.pageList(params);
            result.setTotal(count);
            return resultBeanFactory.getBean(result);
		} catch (Exception e) {
			logger.error("SysTaskTypeController.pageList Exception:{}", e);
			return resultBeanFactory.getException(e);
		}
     }


     @ApiOperation(value = "通过id查询")
     @ApiImplicitParam(name = "sysTaskTypeId", value = "ID", required = true, dataType = "String")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "sysTaskTypeId", value = "return-ID", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysTaskTypeTypeTitle", value = "return-lvM分类名称", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysTaskTypeTypePrice", value = "return-lv任务分类价格", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysTaskTypeTypeContent", value = "return-简介", required = false, dataType = "String"),
		})
     @RequestMapping(value = "/selectObjById", method = RequestMethod.GET)
     public ResultBean<HashMap<String,Object>> info(@RequestParam("sysTaskTypeId") String sysTaskTypeId) {
			logger.info("sysTaskType.selectObjById页面请求参数："+sysTaskTypeId);
         HashMap<String,Object> sysTaskType = baseService.selectObjById(sysTaskTypeId);
         return resultBeanFactory.getBean(sysTaskType);
     }
     
     
	
     @ApiOperation(value = "allList")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "sysTaskTypeId", value = "return-ID", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysTaskTypeTypeTitle", value = "return-lvM分类名称", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysTaskTypeTypePrice", value = "return-lv任务分类价格", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysTaskTypeTypeContent", value = "return-简介", required = false, dataType = "String"),
     })
     @RequestMapping(value = "/allList", method = RequestMethod.POST)
	public ResultBean<List<SysTaskType>> allList(@RequestBody HashMap<String,Object> param){
			logger.info("sysTaskType.allList页面请求参数："+param);
		List<SysTaskType> result = baseService.allList(param);
		 return resultBeanFactory.getBean(result);
	}
	
	     
	

}

