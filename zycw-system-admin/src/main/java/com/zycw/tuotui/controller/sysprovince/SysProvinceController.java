package com.zycw.tuotui.controller.sysprovince;


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
import com.zycw.tuotui.iface.sysprovince.ISysProvinceService;
import com.zycw.tuotui.entity.sysprovince.SysProvince;
import java.util.Date;
import java.util.List;
import java.util.HashMap;


@ApiOperation(value = "b1省")
@Api(value = "b1省接口", tags = { "b1省接口" })
@Slf4j
@RestController
@RequestMapping("/sysProvince")
public class SysProvinceController extends BaseController <ISysProvinceService, SysProvince>{

    private static Logger logger = LoggerFactory.getLogger(SysProvinceController.class);
       
    		   
    @Autowired
    private ResultBeanFactory resultBeanFactory;

     @ApiOperation(value = "根据id物理删除")
     @ApiImplicitParam(name = "sysProvinceId", value = "ID", required = true, dataType = "String")
     @RequestMapping(value = "/deleteById", method = RequestMethod.GET)
     public ResultBean<String> deleteById(@RequestParam("sysProvinceId") String sysProvinceId) {
     	logger.info("sysProvince.deleteById页面请求参数："+sysProvinceId);
		try {
			baseService.deleteById(sysProvinceId);
			return resultBeanFactory.getBean("删除成功");
		} catch (Exception e) {
			logger.error("失败原因:{}", e);
			return resultBeanFactory.getException(e);
		}
     }

     @ApiOperation(value = "根据id逻辑删除")
     @ApiImplicitParam(name = "sysProvinceId", value = "ID", required = true, dataType = "String")
     @RequestMapping(value = "/deleteByIdLogic", method = RequestMethod.GET)
     public ResultBean<String> deleteByIdLogic(@RequestParam("sysProvinceId") String sysProvinceId) {
		logger.info("sysProvince.deleteByIdLogic页面请求参数："+sysProvinceId);
		try {
			baseService.deleteByIdLogic(sysProvinceId);
			return resultBeanFactory.getBean("删除成功");
		} catch (Exception e) {
			logger.error("失败原因:{}", e);
			return resultBeanFactory.getException(e);
		}
     }
     

     @ApiOperation(value = "根据主键修改")
     @ApiImplicitParam(name = "sysProvince", value = "b1省对象", required = true, dataType = "SysProvince")
     @RequestMapping(value = "/updateObjById", method = RequestMethod.POST)
     public ResultBean<String> updateObjById(@RequestBody SysProvince sysProvince) {
         logger.info("sysProvince.updateObjById页面请求参数："+sysProvince);
		try {
			logger.info("sysProvince.update页面请求参数："+sysProvince);
			baseService.updateObjById(sysProvince);
			return resultBeanFactory.getBean("修改成功");
        } catch (Exception e) {
            logger.error("修改失败:{}", e);
            return resultBeanFactory.getException(e);
        }
     }
     
     

     @ApiOperation(value = "新增")
     @ApiImplicitParam(name = "sysProvince", value = "b1省对象", required = true, dataType = "SysProvince")
     @RequestMapping(value = "/insertObj", method = RequestMethod.POST)
     public ResultBean<String> insertObj(@RequestBody SysProvince sysProvince) {
		try {
			logger.info("sysProvince.insertObj页面请求参数："+sysProvince);
	        baseService.insert(sysProvince);
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

    	@ApiImplicitParam(name = "sysProvinceName", value = "return-lvM省名称", required = false, dataType = "String"),
     })
     @RequestMapping(value = "/pageList", method = RequestMethod.POST)
     public ResultBeanFactory.ResultBean<PageInfo<SysProvince>> pageList(@RequestBody HashMap<String,Object> params) {
		try {
			logger.info("sysProvince.pageList页面请求参数："+params);
            int count = baseService.countNum(params);
            PageInfo<SysProvince> result=baseService.pageList(params);
            result.setTotal(count);
            return resultBeanFactory.getBean(result);
		} catch (Exception e) {
			logger.error("SysProvinceController.pageList Exception:{}", e);
			return resultBeanFactory.getException(e);
		}
     }


     @ApiOperation(value = "通过id查询")
     @ApiImplicitParam(name = "sysProvinceId", value = "ID", required = true, dataType = "String")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "sysProvinceId", value = "return-ID", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysProvinceName", value = "return-lvM省名称", required = false, dataType = "String"),
		})
     @RequestMapping(value = "/selectObjById", method = RequestMethod.GET)
     public ResultBean<HashMap<String,Object>> info(@RequestParam("sysProvinceId") String sysProvinceId) {
			logger.info("sysProvince.selectObjById页面请求参数："+sysProvinceId);
         HashMap<String,Object> sysProvince = baseService.selectObjById(sysProvinceId);
         return resultBeanFactory.getBean(sysProvince);
     }
     
     
	
     @ApiOperation(value = "allList")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "sysProvinceId", value = "return-ID", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysProvinceName", value = "return-lvM省名称", required = false, dataType = "String"),
     })
     @RequestMapping(value = "/allList", method = RequestMethod.POST)
	public ResultBean<List<SysProvince>> allList(@RequestBody HashMap<String,Object> param){
			logger.info("sysProvince.allList页面请求参数："+param);
		List<SysProvince> result = baseService.allList(param);
		 return resultBeanFactory.getBean(result);
	}
	
	     
	

}

