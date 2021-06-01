package com.zycw.tuotui.controller.sysadverttype;


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
import com.zycw.tuotui.iface.sysadverttype.ISysAdvertTypeService;
import com.zycw.tuotui.entity.sysadverttype.SysAdvertType;
import java.util.Date;
import java.util.List;
import java.util.HashMap;


@ApiOperation(value = "广告类型")
@Api(value = "广告类型接口", tags = { "广告类型接口" })
@Slf4j
@RestController
@RequestMapping("/sysAdvertType")
public class SysAdvertTypeController extends BaseController <ISysAdvertTypeService, SysAdvertType>{

    private static Logger logger = LoggerFactory.getLogger(SysAdvertTypeController.class);
       
    		   
    @Autowired
    private ResultBeanFactory resultBeanFactory;

     @ApiOperation(value = "根据id物理删除")
     @ApiImplicitParam(name = "sysAdvertTypeId", value = "ID", required = true, dataType = "String")
     @RequestMapping(value = "/deleteById", method = RequestMethod.GET)
     public ResultBean<String> deleteById(@RequestParam("sysAdvertTypeId") String sysAdvertTypeId) {
     	logger.info("sysAdvertType.deleteById页面请求参数："+sysAdvertTypeId);
		try {
			baseService.deleteById(sysAdvertTypeId);
			return resultBeanFactory.getBean("删除成功");
		} catch (Exception e) {
			logger.error("失败原因:{}", e);
			return resultBeanFactory.getException(e);
		}
     }

     @ApiOperation(value = "根据id逻辑删除")
     @ApiImplicitParam(name = "sysAdvertTypeId", value = "ID", required = true, dataType = "String")
     @RequestMapping(value = "/deleteByIdLogic", method = RequestMethod.GET)
     public ResultBean<String> deleteByIdLogic(@RequestParam("sysAdvertTypeId") String sysAdvertTypeId) {
		logger.info("sysAdvertType.deleteByIdLogic页面请求参数："+sysAdvertTypeId);
		try {
			baseService.deleteByIdLogic(sysAdvertTypeId);
			return resultBeanFactory.getBean("删除成功");
		} catch (Exception e) {
			logger.error("失败原因:{}", e);
			return resultBeanFactory.getException(e);
		}
     }
     

     @ApiOperation(value = "根据主键修改")
     @ApiImplicitParam(name = "sysAdvertType", value = "广告类型对象", required = true, dataType = "SysAdvertType")
     @RequestMapping(value = "/updateObjById", method = RequestMethod.POST)
     public ResultBean<String> updateObjById(@RequestBody SysAdvertType sysAdvertType) {
         logger.info("sysAdvertType.updateObjById页面请求参数："+sysAdvertType);
		try {
			logger.info("sysAdvertType.update页面请求参数："+sysAdvertType);
			baseService.updateObjById(sysAdvertType);
			return resultBeanFactory.getBean("修改成功");
        } catch (Exception e) {
            logger.error("修改失败:{}", e);
            return resultBeanFactory.getException(e);
        }
     }
     
     

     @ApiOperation(value = "新增")
     @ApiImplicitParam(name = "sysAdvertType", value = "广告类型对象", required = true, dataType = "SysAdvertType")
     @RequestMapping(value = "/insertObj", method = RequestMethod.POST)
     public ResultBean<String> insertObj(@RequestBody SysAdvertType sysAdvertType) {
		try {
			logger.info("sysAdvertType.insertObj页面请求参数："+sysAdvertType);
	        baseService.insert(sysAdvertType);
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



    	@ApiImplicitParam(name = "sysAdvertTypeDelFlag", value = "return-逻辑删除:0-未删除,1-已删除", required = false, dataType = "${dataType}"),
     })
     @RequestMapping(value = "/pageList", method = RequestMethod.POST)
     public ResultBeanFactory.ResultBean<PageInfo<SysAdvertType>> pageList(@RequestBody HashMap<String,Object> params) {
		try {
			logger.info("sysAdvertType.pageList页面请求参数："+params);
            int count = baseService.countNum(params);
            PageInfo<SysAdvertType> result=baseService.pageList(params);
            result.setTotal(count);
            return resultBeanFactory.getBean(result);
		} catch (Exception e) {
			logger.error("SysAdvertTypeController.pageList Exception:{}", e);
			return resultBeanFactory.getException(e);
		}
     }


     @ApiOperation(value = "通过id查询")
     @ApiImplicitParam(name = "sysAdvertTypeId", value = "ID", required = true, dataType = "String")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "sysAdvertTypeId", value = "return-ID", required = false, dataType = "${dataType}"),
    	@ApiImplicitParam(name = "sysAdvertTypeTypeName", value = "return-lvM类型名称", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysAdvertTypeTypeCode", value = "return-lv类型编码", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysAdvertTypeDelFlag", value = "return-逻辑删除:0-未删除,1-已删除", required = false, dataType = "String"),
		})
     @RequestMapping(value = "/selectObjById", method = RequestMethod.GET)
     public ResultBean<HashMap<String,Object>> info(@RequestParam("sysAdvertTypeId") String sysAdvertTypeId) {
			logger.info("sysAdvertType.selectObjById页面请求参数："+sysAdvertTypeId);
         HashMap<String,Object> sysAdvertType = baseService.selectObjById(sysAdvertTypeId);
         return resultBeanFactory.getBean(sysAdvertType);
     }
     
     
	
     @ApiOperation(value = "allList")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "sysAdvertTypeId", value = "return-ID", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysAdvertTypeTypeName", value = "return-lvM类型名称", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysAdvertTypeTypeCode", value = "return-lv类型编码", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysAdvertTypeDelFlag", value = "return-逻辑删除:0-未删除,1-已删除", required = false, dataType = "String")
     })
     @RequestMapping(value = "/allList", method = RequestMethod.POST)
	public ResultBean<List<SysAdvertType>> allList(@RequestBody HashMap<String,Object> param){
			logger.info("sysAdvertType.allList页面请求参数："+param);
		List<SysAdvertType> result = baseService.allList(param);
		 return resultBeanFactory.getBean(result);
	}
	
	     
	

}

