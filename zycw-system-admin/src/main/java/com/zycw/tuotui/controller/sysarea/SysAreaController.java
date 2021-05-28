package com.zycw.tuotui.controller.sysarea;


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
import com.zycw.tuotui.iface.sysarea.ISysAreaService;
import com.zycw.tuotui.entity.sysarea.SysArea;
import java.util.Date;
import java.util.List;
import java.util.HashMap;


@ApiOperation(value = "b3区")
@Api(value = "b3区接口", tags = { "b3区接口" })
@Slf4j
@RestController
@RequestMapping("/sysArea")
public class SysAreaController extends BaseController <ISysAreaService, SysArea>{

    private static Logger logger = LoggerFactory.getLogger(SysAreaController.class);
       
    		   
    @Autowired
    private ResultBeanFactory resultBeanFactory;

     @ApiOperation(value = "根据id物理删除")
     @ApiImplicitParam(name = "sysAreaId", value = "ID", required = true, dataType = "String")
     @RequestMapping(value = "/deleteById", method = RequestMethod.GET)
     public ResultBean<String> deleteById(@RequestParam("sysAreaId") String sysAreaId) {
     	logger.info("sysArea.deleteById页面请求参数："+sysAreaId);
		try {
			baseService.deleteById(sysAreaId);
			return resultBeanFactory.getBean("删除成功");
		} catch (Exception e) {
			logger.error("失败原因:{}", e);
			return resultBeanFactory.getException(e);
		}
     }

     @ApiOperation(value = "根据id逻辑删除")
     @ApiImplicitParam(name = "sysAreaId", value = "ID", required = true, dataType = "String")
     @RequestMapping(value = "/deleteByIdLogic", method = RequestMethod.GET)
     public ResultBean<String> deleteByIdLogic(@RequestParam("sysAreaId") String sysAreaId) {
		logger.info("sysArea.deleteByIdLogic页面请求参数："+sysAreaId);
		try {
			baseService.deleteByIdLogic(sysAreaId);
			return resultBeanFactory.getBean("删除成功");
		} catch (Exception e) {
			logger.error("失败原因:{}", e);
			return resultBeanFactory.getException(e);
		}
     }
     

     @ApiOperation(value = "根据主键修改")
     @ApiImplicitParam(name = "sysArea", value = "b3区对象", required = true, dataType = "SysArea")
     @RequestMapping(value = "/updateObjById", method = RequestMethod.POST)
     public ResultBean<String> updateObjById(@RequestBody SysArea sysArea) {
         logger.info("sysArea.updateObjById页面请求参数："+sysArea);
		try {
			logger.info("sysArea.update页面请求参数："+sysArea);
			baseService.updateObjById(sysArea);
			return resultBeanFactory.getBean("修改成功");
        } catch (Exception e) {
            logger.error("修改失败:{}", e);
            return resultBeanFactory.getException(e);
        }
     }
     
     

     @ApiOperation(value = "新增")
     @ApiImplicitParam(name = "sysArea", value = "b3区对象", required = true, dataType = "SysArea")
     @RequestMapping(value = "/insertObj", method = RequestMethod.POST)
     public ResultBean<String> insertObj(@RequestBody SysArea sysArea) {
		try {
			logger.info("sysArea.insertObj页面请求参数："+sysArea);
	        baseService.insert(sysArea);
	        return resultBeanFactory.getBean("插入记录成功");
        } catch (Exception e) {
            logger.error("插入记录失败:{}", e);
            return resultBeanFactory.getException(e);
        }
     }
     
     @ApiOperation(value = "分页查询")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "sysAreaCityId", value = "市", required = false, dataType = "${dataType}"),
    	@ApiImplicitParam(name = "sysAreaProvinceId", value = "省", required = false, dataType = "${dataType}"),
		@ApiImplicitParam(name = "pageSize", value = "每页条数", required = false, dataType = "int"),
		@ApiImplicitParam(name = "pageNum", value = "当前页数", required = false, dataType = "int"),



    	@ApiImplicitParam(name = "sysProvinceName", value = "return-lvM省名称", required = false, dataType = "String")
     })
     @RequestMapping(value = "/pageList", method = RequestMethod.POST)
     public ResultBeanFactory.ResultBean<PageInfo<SysArea>> pageList(@RequestBody HashMap<String,Object> params) {
		try {
			logger.info("sysArea.pageList页面请求参数："+params);
            int count = baseService.countNum(params);
            PageInfo<SysArea> result=baseService.pageList(params);
            result.setTotal(count);
            return resultBeanFactory.getBean(result);
		} catch (Exception e) {
			logger.error("SysAreaController.pageList Exception:{}", e);
			return resultBeanFactory.getException(e);
		}
     }


     @ApiOperation(value = "通过id查询")
     @ApiImplicitParam(name = "sysAreaId", value = "ID", required = true, dataType = "String")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "sysAreaId", value = "return-ID", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysAreaName", value = "return-lvM区名称", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysCityName", value = "return-lvM市名称", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysProvinceName", value = "return-lvM省名称", required = false, dataType = "String")
		})
     @RequestMapping(value = "/selectObjById", method = RequestMethod.GET)
     public ResultBean<HashMap<String,Object>> info(@RequestParam("sysAreaId") String sysAreaId) {
			logger.info("sysArea.selectObjById页面请求参数："+sysAreaId);
         HashMap<String,Object> sysArea = baseService.selectObjById(sysAreaId);
         return resultBeanFactory.getBean(sysArea);
     }
     
     
	
     @ApiOperation(value = "allList")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "sysAreaCityId", value = "市", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysAreaProvinceId", value = "省", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysAreaId", value = "return-ID", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysAreaName", value = "return-lvM区名称", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysCityName", value = "return-lvM市名称", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysProvinceName", value = "return-lvM省名称", required = false, dataType = "String")
     })
     @RequestMapping(value = "/allList", method = RequestMethod.POST)
	public ResultBean<List<SysArea>> allList(@RequestBody HashMap<String,Object> param){
			logger.info("sysArea.allList页面请求参数："+param);
		List<SysArea> result = baseService.allList(param);
		 return resultBeanFactory.getBean(result);
	}
	
	     
	

}

