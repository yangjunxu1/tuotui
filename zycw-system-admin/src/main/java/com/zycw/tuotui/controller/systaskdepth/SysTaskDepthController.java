package com.zycw.tuotui.controller.systaskdepth;


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
import com.zycw.tuotui.iface.systaskdepth.ISysTaskDepthService;
import com.zycw.tuotui.entity.systaskdepth.SysTaskDepth;
import java.util.Date;
import java.util.List;
import java.util.HashMap;


@ApiOperation(value = "b任务深度")
@Api(value = "b任务深度接口", tags = { "b任务深度接口" })
@Slf4j
@RestController
@RequestMapping("/sysTaskDepth")
public class SysTaskDepthController extends BaseController <ISysTaskDepthService, SysTaskDepth>{

    private static Logger logger = LoggerFactory.getLogger(SysTaskDepthController.class);
       
    		   
    @Autowired
    private ResultBeanFactory resultBeanFactory;

     @ApiOperation(value = "根据id物理删除")
     @ApiImplicitParam(name = "sysTaskDepthId", value = "ID", required = true, dataType = "String")
     @RequestMapping(value = "/deleteById", method = RequestMethod.GET)
     public ResultBean<String> deleteById(@RequestParam("sysTaskDepthId") String sysTaskDepthId) {
     	logger.info("sysTaskDepth.deleteById页面请求参数："+sysTaskDepthId);
		try {
			baseService.deleteById(sysTaskDepthId);
			return resultBeanFactory.getBean("删除成功");
		} catch (Exception e) {
			logger.error("失败原因:{}", e);
			return resultBeanFactory.getException(e);
		}
     }

     @ApiOperation(value = "根据id逻辑删除")
     @ApiImplicitParam(name = "sysTaskDepthId", value = "ID", required = true, dataType = "String")
     @RequestMapping(value = "/deleteByIdLogic", method = RequestMethod.GET)
     public ResultBean<String> deleteByIdLogic(@RequestParam("sysTaskDepthId") String sysTaskDepthId) {
		logger.info("sysTaskDepth.deleteByIdLogic页面请求参数："+sysTaskDepthId);
		try {
			baseService.deleteByIdLogic(sysTaskDepthId);
			return resultBeanFactory.getBean("删除成功");
		} catch (Exception e) {
			logger.error("失败原因:{}", e);
			return resultBeanFactory.getException(e);
		}
     }
     

     @ApiOperation(value = "根据主键修改")
     @ApiImplicitParam(name = "sysTaskDepth", value = "b任务深度对象", required = true, dataType = "SysTaskDepth")
     @RequestMapping(value = "/updateObjById", method = RequestMethod.POST)
     public ResultBean<String> updateObjById(@RequestBody SysTaskDepth sysTaskDepth) {
         logger.info("sysTaskDepth.updateObjById页面请求参数："+sysTaskDepth);
		try {
			logger.info("sysTaskDepth.update页面请求参数："+sysTaskDepth);
			baseService.updateObjById(sysTaskDepth);
			return resultBeanFactory.getBean("修改成功");
        } catch (Exception e) {
            logger.error("修改失败:{}", e);
            return resultBeanFactory.getException(e);
        }
     }
     
     

     @ApiOperation(value = "新增")
     @ApiImplicitParam(name = "sysTaskDepth", value = "b任务深度对象", required = true, dataType = "SysTaskDepth")
     @RequestMapping(value = "/insertObj", method = RequestMethod.POST)
     public ResultBean<String> insertObj(@RequestBody SysTaskDepth sysTaskDepth) {
		try {
			logger.info("sysTaskDepth.insertObj页面请求参数："+sysTaskDepth);
	        baseService.insert(sysTaskDepth);
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



    	@ApiImplicitParam(name = "sysTaskDepthDepthContent", value = "return-v简介", required = false, dataType = "String"),
     })
     @RequestMapping(value = "/pageList", method = RequestMethod.POST)
     public ResultBeanFactory.ResultBean<PageInfo<SysTaskDepth>> pageList(@RequestBody HashMap<String,Object> params) {
		try {
			logger.info("sysTaskDepth.pageList页面请求参数："+params);
            int count = baseService.countNum(params);
            PageInfo<SysTaskDepth> result=baseService.pageList(params);
            result.setTotal(count);
            return resultBeanFactory.getBean(result);
		} catch (Exception e) {
			logger.error("SysTaskDepthController.pageList Exception:{}", e);
			return resultBeanFactory.getException(e);
		}
     }


     @ApiOperation(value = "通过id查询")
     @ApiImplicitParam(name = "sysTaskDepthId", value = "ID", required = true, dataType = "String")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "sysTaskDepthId", value = "return-ID", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysTaskDepthDepthTitle", value = "return-lvM任务深度标题", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysTaskDepthDepthPrice", value = "return-lv价格", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysTaskDepthDepthContent", value = "return-v简介", required = false, dataType = "String"),
		})
     @RequestMapping(value = "/selectObjById", method = RequestMethod.GET)
     public ResultBean<HashMap<String,Object>> info(@RequestParam("sysTaskDepthId") String sysTaskDepthId) {
			logger.info("sysTaskDepth.selectObjById页面请求参数："+sysTaskDepthId);
         HashMap<String,Object> sysTaskDepth = baseService.selectObjById(sysTaskDepthId);
         return resultBeanFactory.getBean(sysTaskDepth);
     }
     
     
	
     @ApiOperation(value = "allList")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "sysTaskDepthId", value = "return-ID", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysTaskDepthDepthTitle", value = "return-lvM任务深度标题", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysTaskDepthDepthPrice", value = "return-lv价格", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysTaskDepthDepthContent", value = "return-v简介", required = false, dataType = "String"),
     })
     @RequestMapping(value = "/allList", method = RequestMethod.POST)
	public ResultBean<List<SysTaskDepth>> allList(@RequestBody HashMap<String,Object> param){
			logger.info("sysTaskDepth.allList页面请求参数："+param);
		List<SysTaskDepth> result = baseService.allList(param);
		 return resultBeanFactory.getBean(result);
	}
	
	     
	

}

