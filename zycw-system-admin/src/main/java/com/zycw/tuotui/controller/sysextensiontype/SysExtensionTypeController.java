package com.zycw.tuotui.controller.sysextensiontype;


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
import com.zycw.tuotui.iface.sysextensiontype.ISysExtensionTypeService;
import com.zycw.tuotui.entity.sysextensiontype.SysExtensionType;
import java.util.Date;
import java.util.List;
import java.util.HashMap;


@ApiOperation(value = "b推广类型·")
@Api(value = "b推广类型·接口", tags = { "b推广类型·接口" })
@Slf4j
@RestController
@RequestMapping("/sysExtensionType")
public class SysExtensionTypeController extends BaseController <ISysExtensionTypeService, SysExtensionType>{

    private static Logger logger = LoggerFactory.getLogger(SysExtensionTypeController.class);
       
    		   
    @Autowired
    private ResultBeanFactory resultBeanFactory;

     @ApiOperation(value = "根据id物理删除")
     @ApiImplicitParam(name = "sysExtensionTypeId", value = "ID", required = true, dataType = "String")
     @RequestMapping(value = "/deleteById", method = RequestMethod.GET)
     public ResultBean<String> deleteById(@RequestParam("sysExtensionTypeId") String sysExtensionTypeId) {
     	logger.info("sysExtensionType.deleteById页面请求参数："+sysExtensionTypeId);
		try {
			baseService.deleteById(sysExtensionTypeId);
			return resultBeanFactory.getBean("删除成功");
		} catch (Exception e) {
			logger.error("失败原因:{}", e);
			return resultBeanFactory.getException(e);
		}
     }

     @ApiOperation(value = "根据id逻辑删除")
     @ApiImplicitParam(name = "sysExtensionTypeId", value = "ID", required = true, dataType = "String")
     @RequestMapping(value = "/deleteByIdLogic", method = RequestMethod.GET)
     public ResultBean<String> deleteByIdLogic(@RequestParam("sysExtensionTypeId") String sysExtensionTypeId) {
		logger.info("sysExtensionType.deleteByIdLogic页面请求参数："+sysExtensionTypeId);
		try {
			baseService.deleteByIdLogic(sysExtensionTypeId);
			return resultBeanFactory.getBean("删除成功");
		} catch (Exception e) {
			logger.error("失败原因:{}", e);
			return resultBeanFactory.getException(e);
		}
     }
     

     @ApiOperation(value = "根据主键修改")
     @ApiImplicitParam(name = "sysExtensionType", value = "b推广类型·对象", required = true, dataType = "SysExtensionType")
     @RequestMapping(value = "/updateObjById", method = RequestMethod.POST)
     public ResultBean<String> updateObjById(@RequestBody SysExtensionType sysExtensionType) {
         logger.info("sysExtensionType.updateObjById页面请求参数："+sysExtensionType);
		try {
			logger.info("sysExtensionType.update页面请求参数："+sysExtensionType);
			baseService.updateObjById(sysExtensionType);
			return resultBeanFactory.getBean("修改成功");
        } catch (Exception e) {
            logger.error("修改失败:{}", e);
            return resultBeanFactory.getException(e);
        }
     }
     
     

     @ApiOperation(value = "新增")
     @ApiImplicitParam(name = "sysExtensionType", value = "b推广类型·对象", required = true, dataType = "SysExtensionType")
     @RequestMapping(value = "/insertObj", method = RequestMethod.POST)
     public ResultBean<String> insertObj(@RequestBody SysExtensionType sysExtensionType) {
		try {
			logger.info("sysExtensionType.insertObj页面请求参数："+sysExtensionType);
	        baseService.insert(sysExtensionType);
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




    	@ApiImplicitParam(name = "sysExtensionTypeDelFlag", value = "return-逻辑删除:0-未删除,1-已删除", required = false, dataType = "${dataType}"),
     })
     @RequestMapping(value = "/pageList", method = RequestMethod.POST)
     public ResultBeanFactory.ResultBean<PageInfo<SysExtensionType>> pageList(@RequestBody HashMap<String,Object> params) {
		try {
			logger.info("sysExtensionType.pageList页面请求参数："+params);
            int count = baseService.countNum(params);
            PageInfo<SysExtensionType> result=baseService.pageList(params);
            result.setTotal(count);
            return resultBeanFactory.getBean(result);
		} catch (Exception e) {
			logger.error("SysExtensionTypeController.pageList Exception:{}", e);
			return resultBeanFactory.getException(e);
		}
     }


     @ApiOperation(value = "通过id查询")
     @ApiImplicitParam(name = "sysExtensionTypeId", value = "ID", required = true, dataType = "String")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "sysExtensionTypeId", value = "return-ID", required = false, dataType = "${dataType}"),
    	@ApiImplicitParam(name = "sysExtensionTypeTitel", value = "return-lvM标题·", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysExtensionTypePrice", value = "return-lv价格", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysExtensionTypeContent", value = "return-", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysExtensionTypeDelFlag", value = "return-逻辑删除:0-未删除,1-已删除", required = false, dataType = "String"),
		})
     @RequestMapping(value = "/selectObjById", method = RequestMethod.GET)
     public ResultBean<HashMap<String,Object>> info(@RequestParam("sysExtensionTypeId") String sysExtensionTypeId) {
			logger.info("sysExtensionType.selectObjById页面请求参数："+sysExtensionTypeId);
         HashMap<String,Object> sysExtensionType = baseService.selectObjById(sysExtensionTypeId);
         return resultBeanFactory.getBean(sysExtensionType);
     }
     
     
	
     @ApiOperation(value = "allList")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "sysExtensionTypeId", value = "return-ID", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysExtensionTypeTitel", value = "return-lvM标题·", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysExtensionTypePrice", value = "return-lv价格", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysExtensionTypeContent", value = "return-", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysExtensionTypeDelFlag", value = "return-逻辑删除:0-未删除,1-已删除", required = false, dataType = "String")
     })
     @RequestMapping(value = "/allList", method = RequestMethod.POST)
	public ResultBean<List<SysExtensionType>> allList(@RequestBody HashMap<String,Object> param){
			logger.info("sysExtensionType.allList页面请求参数："+param);
		List<SysExtensionType> result = baseService.allList(param);
		 return resultBeanFactory.getBean(result);
	}
	
	     
	

}

