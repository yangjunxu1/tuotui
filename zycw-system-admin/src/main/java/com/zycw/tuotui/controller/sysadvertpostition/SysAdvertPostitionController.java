package com.zycw.tuotui.controller.sysadvertpostition;


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
import com.zycw.tuotui.iface.sysadvertpostition.ISysAdvertPostitionService;
import com.zycw.tuotui.entity.sysadvertpostition.SysAdvertPostition;
import java.util.Date;
import java.util.List;
import java.util.HashMap;


@ApiOperation(value = "b广告协议位置")
@Api(value = "b广告协议位置接口", tags = { "b广告协议位置接口" })
@Slf4j
@RestController
@RequestMapping("/sysAdvertPostition")
public class SysAdvertPostitionController extends BaseController <ISysAdvertPostitionService, SysAdvertPostition>{

    private static Logger logger = LoggerFactory.getLogger(SysAdvertPostitionController.class);
       
    		   
    @Autowired
    private ResultBeanFactory resultBeanFactory;

     @ApiOperation(value = "根据id物理删除")
     @ApiImplicitParam(name = "sysAdvertPostitionId", value = "ID", required = true, dataType = "String")
     @RequestMapping(value = "/deleteById", method = RequestMethod.GET)
     public ResultBean<String> deleteById(@RequestParam("sysAdvertPostitionId") String sysAdvertPostitionId) {
     	logger.info("sysAdvertPostition.deleteById页面请求参数："+sysAdvertPostitionId);
		try {
			baseService.deleteById(sysAdvertPostitionId);
			return resultBeanFactory.getBean("删除成功");
		} catch (Exception e) {
			logger.error("失败原因:{}", e);
			return resultBeanFactory.getException(e);
		}
     }

     @ApiOperation(value = "根据id逻辑删除")
     @ApiImplicitParam(name = "sysAdvertPostitionId", value = "ID", required = true, dataType = "String")
     @RequestMapping(value = "/deleteByIdLogic", method = RequestMethod.GET)
     public ResultBean<String> deleteByIdLogic(@RequestParam("sysAdvertPostitionId") String sysAdvertPostitionId) {
		logger.info("sysAdvertPostition.deleteByIdLogic页面请求参数："+sysAdvertPostitionId);
		try {
			baseService.deleteByIdLogic(sysAdvertPostitionId);
			return resultBeanFactory.getBean("删除成功");
		} catch (Exception e) {
			logger.error("失败原因:{}", e);
			return resultBeanFactory.getException(e);
		}
     }
     

     @ApiOperation(value = "根据主键修改")
     @ApiImplicitParam(name = "sysAdvertPostition", value = "b广告协议位置对象", required = true, dataType = "SysAdvertPostition")
     @RequestMapping(value = "/updateObjById", method = RequestMethod.POST)
     public ResultBean<String> updateObjById(@RequestBody SysAdvertPostition sysAdvertPostition) {
         logger.info("sysAdvertPostition.updateObjById页面请求参数："+sysAdvertPostition);
		try {
			logger.info("sysAdvertPostition.update页面请求参数："+sysAdvertPostition);
			baseService.updateObjById(sysAdvertPostition);
			return resultBeanFactory.getBean("修改成功");
        } catch (Exception e) {
            logger.error("修改失败:{}", e);
            return resultBeanFactory.getException(e);
        }
     }
     
     

     @ApiOperation(value = "新增")
     @ApiImplicitParam(name = "sysAdvertPostition", value = "b广告协议位置对象", required = true, dataType = "SysAdvertPostition")
     @RequestMapping(value = "/insertObj", method = RequestMethod.POST)
     public ResultBean<String> insertObj(@RequestBody SysAdvertPostition sysAdvertPostition) {
		try {
			logger.info("sysAdvertPostition.insertObj页面请求参数："+sysAdvertPostition);
	        baseService.insert(sysAdvertPostition);
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


    	@ApiImplicitParam(name = "sysAdvertPostitionCode", value = "return-位置编码", required = false, dataType = "String"),
     })
     @RequestMapping(value = "/pageList", method = RequestMethod.POST)
     public ResultBeanFactory.ResultBean<PageInfo<SysAdvertPostition>> pageList(@RequestBody HashMap<String,Object> params) {
		try {
			logger.info("sysAdvertPostition.pageList页面请求参数："+params);
            int count = baseService.countNum(params);
            PageInfo<SysAdvertPostition> result=baseService.pageList(params);
            result.setTotal(count);
            return resultBeanFactory.getBean(result);
		} catch (Exception e) {
			logger.error("SysAdvertPostitionController.pageList Exception:{}", e);
			return resultBeanFactory.getException(e);
		}
     }


     @ApiOperation(value = "通过id查询")
     @ApiImplicitParam(name = "sysAdvertPostitionId", value = "ID", required = true, dataType = "String")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "sysAdvertPostitionId", value = "return-ID", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysAdvertPostitionName", value = "return-lnM位置名称", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysAdvertPostitionCode", value = "return-位置编码", required = false, dataType = "String"),
		})
     @RequestMapping(value = "/selectObjById", method = RequestMethod.GET)
     public ResultBean<HashMap<String,Object>> info(@RequestParam("sysAdvertPostitionId") String sysAdvertPostitionId) {
			logger.info("sysAdvertPostition.selectObjById页面请求参数："+sysAdvertPostitionId);
         HashMap<String,Object> sysAdvertPostition = baseService.selectObjById(sysAdvertPostitionId);
         return resultBeanFactory.getBean(sysAdvertPostition);
     }
     
     
	
     @ApiOperation(value = "allList")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "sysAdvertPostitionId", value = "return-ID", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysAdvertPostitionName", value = "return-lnM位置名称", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysAdvertPostitionCode", value = "return-位置编码", required = false, dataType = "String"),
     })
     @RequestMapping(value = "/allList", method = RequestMethod.POST)
	public ResultBean<List<SysAdvertPostition>> allList(@RequestBody HashMap<String,Object> param){
			logger.info("sysAdvertPostition.allList页面请求参数："+param);
		List<SysAdvertPostition> result = baseService.allList(param);
		 return resultBeanFactory.getBean(result);
	}
	
	     
	

}

