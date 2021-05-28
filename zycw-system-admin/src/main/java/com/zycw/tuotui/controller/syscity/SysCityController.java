package com.zycw.tuotui.controller.syscity;


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
import com.zycw.tuotui.iface.syscity.ISysCityService;
import com.zycw.tuotui.entity.syscity.SysCity;
import java.util.Date;
import java.util.List;
import java.util.HashMap;


@ApiOperation(value = "b2市")
@Api(value = "b2市接口", tags = { "b2市接口" })
@Slf4j
@RestController
@RequestMapping("/sysCity")
public class SysCityController extends BaseController <ISysCityService, SysCity>{

    private static Logger logger = LoggerFactory.getLogger(SysCityController.class);
       
    		   
    @Autowired
    private ResultBeanFactory resultBeanFactory;

     @ApiOperation(value = "根据id物理删除")
     @ApiImplicitParam(name = "sysCityId", value = "ID", required = true, dataType = "String")
     @RequestMapping(value = "/deleteById", method = RequestMethod.GET)
     public ResultBean<String> deleteById(@RequestParam("sysCityId") String sysCityId) {
     	logger.info("sysCity.deleteById页面请求参数："+sysCityId);
		try {
			baseService.deleteById(sysCityId);
			return resultBeanFactory.getBean("删除成功");
		} catch (Exception e) {
			logger.error("失败原因:{}", e);
			return resultBeanFactory.getException(e);
		}
     }

     @ApiOperation(value = "根据id逻辑删除")
     @ApiImplicitParam(name = "sysCityId", value = "ID", required = true, dataType = "String")
     @RequestMapping(value = "/deleteByIdLogic", method = RequestMethod.GET)
     public ResultBean<String> deleteByIdLogic(@RequestParam("sysCityId") String sysCityId) {
		logger.info("sysCity.deleteByIdLogic页面请求参数："+sysCityId);
		try {
			baseService.deleteByIdLogic(sysCityId);
			return resultBeanFactory.getBean("删除成功");
		} catch (Exception e) {
			logger.error("失败原因:{}", e);
			return resultBeanFactory.getException(e);
		}
     }
     

     @ApiOperation(value = "根据主键修改")
     @ApiImplicitParam(name = "sysCity", value = "b2市对象", required = true, dataType = "SysCity")
     @RequestMapping(value = "/updateObjById", method = RequestMethod.POST)
     public ResultBean<String> updateObjById(@RequestBody SysCity sysCity) {
         logger.info("sysCity.updateObjById页面请求参数："+sysCity);
		try {
			logger.info("sysCity.update页面请求参数："+sysCity);
			baseService.updateObjById(sysCity);
			return resultBeanFactory.getBean("修改成功");
        } catch (Exception e) {
            logger.error("修改失败:{}", e);
            return resultBeanFactory.getException(e);
        }
     }
     
     

     @ApiOperation(value = "新增")
     @ApiImplicitParam(name = "sysCity", value = "b2市对象", required = true, dataType = "SysCity")
     @RequestMapping(value = "/insertObj", method = RequestMethod.POST)
     public ResultBean<String> insertObj(@RequestBody SysCity sysCity) {
		try {
			logger.info("sysCity.insertObj页面请求参数："+sysCity);
	        baseService.insert(sysCity);
	        return resultBeanFactory.getBean("插入记录成功");
        } catch (Exception e) {
            logger.error("插入记录失败:{}", e);
            return resultBeanFactory.getException(e);
        }
     }
     
     @ApiOperation(value = "分页查询")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "sysCityProvinceId", value = "省", required = false, dataType = "${dataType}"),
		@ApiImplicitParam(name = "pageSize", value = "每页条数", required = false, dataType = "int"),
		@ApiImplicitParam(name = "pageNum", value = "当前页数", required = false, dataType = "int"),


    	@ApiImplicitParam(name = "sysProvinceName", value = "return-lvM省名称", required = false, dataType = "String")
     })
     @RequestMapping(value = "/pageList", method = RequestMethod.POST)
     public ResultBeanFactory.ResultBean<PageInfo<SysCity>> pageList(@RequestBody HashMap<String,Object> params) {
		try {
			logger.info("sysCity.pageList页面请求参数："+params);
            int count = baseService.countNum(params);
            PageInfo<SysCity> result=baseService.pageList(params);
            result.setTotal(count);
            return resultBeanFactory.getBean(result);
		} catch (Exception e) {
			logger.error("SysCityController.pageList Exception:{}", e);
			return resultBeanFactory.getException(e);
		}
     }


     @ApiOperation(value = "通过id查询")
     @ApiImplicitParam(name = "sysCityId", value = "ID", required = true, dataType = "String")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "sysCityId", value = "return-ID", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysCityName", value = "return-lvM市名称", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysProvinceName", value = "return-lvM省名称", required = false, dataType = "String")
		})
     @RequestMapping(value = "/selectObjById", method = RequestMethod.GET)
     public ResultBean<HashMap<String,Object>> info(@RequestParam("sysCityId") String sysCityId) {
			logger.info("sysCity.selectObjById页面请求参数："+sysCityId);
         HashMap<String,Object> sysCity = baseService.selectObjById(sysCityId);
         return resultBeanFactory.getBean(sysCity);
     }
     
     
	
     @ApiOperation(value = "allList")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "sysCityProvinceId", value = "省", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysCityId", value = "return-ID", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysCityName", value = "return-lvM市名称", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysProvinceName", value = "return-lvM省名称", required = false, dataType = "String")
     })
     @RequestMapping(value = "/allList", method = RequestMethod.POST)
	public ResultBean<List<SysCity>> allList(@RequestBody HashMap<String,Object> param){
			logger.info("sysCity.allList页面请求参数："+param);
		List<SysCity> result = baseService.allList(param);
		 return resultBeanFactory.getBean(result);
	}
	
	     
	

}

