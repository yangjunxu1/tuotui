package com.zycw.tuotui.controller.syshobby;


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
import com.zycw.tuotui.iface.syshobby.ISysHobbyService;
import com.zycw.tuotui.entity.syshobby.SysHobby;
import java.util.Date;
import java.util.List;
import java.util.HashMap;


@ApiOperation(value = "b爱好")
@Api(value = "b爱好接口", tags = { "b爱好接口" })
@Slf4j
@RestController
@RequestMapping("/sysHobby")
public class SysHobbyController extends BaseController <ISysHobbyService, SysHobby>{

    private static Logger logger = LoggerFactory.getLogger(SysHobbyController.class);
       
    		   
    @Autowired
    private ResultBeanFactory resultBeanFactory;

     @ApiOperation(value = "根据id物理删除")
     @ApiImplicitParam(name = "sysHobbyId", value = "ID", required = true, dataType = "String")
     @RequestMapping(value = "/deleteById", method = RequestMethod.GET)
     public ResultBean<String> deleteById(@RequestParam("sysHobbyId") String sysHobbyId) {
     	logger.info("sysHobby.deleteById页面请求参数："+sysHobbyId);
		try {
			baseService.deleteById(sysHobbyId);
			return resultBeanFactory.getBean("删除成功");
		} catch (Exception e) {
			logger.error("失败原因:{}", e);
			return resultBeanFactory.getException(e);
		}
     }

     @ApiOperation(value = "根据id逻辑删除")
     @ApiImplicitParam(name = "sysHobbyId", value = "ID", required = true, dataType = "String")
     @RequestMapping(value = "/deleteByIdLogic", method = RequestMethod.GET)
     public ResultBean<String> deleteByIdLogic(@RequestParam("sysHobbyId") String sysHobbyId) {
		logger.info("sysHobby.deleteByIdLogic页面请求参数："+sysHobbyId);
		try {
			baseService.deleteByIdLogic(sysHobbyId);
			return resultBeanFactory.getBean("删除成功");
		} catch (Exception e) {
			logger.error("失败原因:{}", e);
			return resultBeanFactory.getException(e);
		}
     }
     

     @ApiOperation(value = "根据主键修改")
     @ApiImplicitParam(name = "sysHobby", value = "b爱好对象", required = true, dataType = "SysHobby")
     @RequestMapping(value = "/updateObjById", method = RequestMethod.POST)
     public ResultBean<String> updateObjById(@RequestBody SysHobby sysHobby) {
         logger.info("sysHobby.updateObjById页面请求参数："+sysHobby);
		try {
			logger.info("sysHobby.update页面请求参数："+sysHobby);
			baseService.updateObjById(sysHobby);
			return resultBeanFactory.getBean("修改成功");
        } catch (Exception e) {
            logger.error("修改失败:{}", e);
            return resultBeanFactory.getException(e);
        }
     }
     
     

     @ApiOperation(value = "新增")
     @ApiImplicitParam(name = "sysHobby", value = "b爱好对象", required = true, dataType = "SysHobby")
     @RequestMapping(value = "/insertObj", method = RequestMethod.POST)
     public ResultBean<String> insertObj(@RequestBody SysHobby sysHobby) {
		try {
			logger.info("sysHobby.insertObj页面请求参数："+sysHobby);
	        baseService.insert(sysHobby);
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

    	@ApiImplicitParam(name = "sysHobbyHobbyTitle", value = "return-lvM爱好", required = false, dataType = "String"),
     })
     @RequestMapping(value = "/pageList", method = RequestMethod.POST)
     public ResultBeanFactory.ResultBean<PageInfo<SysHobby>> pageList(@RequestBody HashMap<String,Object> params) {
		try {
			logger.info("sysHobby.pageList页面请求参数："+params);
            int count = baseService.countNum(params);
            PageInfo<SysHobby> result=baseService.pageList(params);
            result.setTotal(count);
            return resultBeanFactory.getBean(result);
		} catch (Exception e) {
			logger.error("SysHobbyController.pageList Exception:{}", e);
			return resultBeanFactory.getException(e);
		}
     }


     @ApiOperation(value = "通过id查询")
     @ApiImplicitParam(name = "sysHobbyId", value = "ID", required = true, dataType = "String")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "sysHobbyId", value = "return-id", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysHobbyHobbyTitle", value = "return-lvM爱好", required = false, dataType = "String"),
		})
     @RequestMapping(value = "/selectObjById", method = RequestMethod.GET)
     public ResultBean<HashMap<String,Object>> info(@RequestParam("sysHobbyId") String sysHobbyId) {
			logger.info("sysHobby.selectObjById页面请求参数："+sysHobbyId);
         HashMap<String,Object> sysHobby = baseService.selectObjById(sysHobbyId);
         return resultBeanFactory.getBean(sysHobby);
     }
     
     
	
     @ApiOperation(value = "allList")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "sysHobbyId", value = "return-id", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysHobbyHobbyTitle", value = "return-lvM爱好", required = false, dataType = "String")
     })
     @RequestMapping(value = "/allList", method = RequestMethod.POST)
	public ResultBean<List<SysHobby>> allList(@RequestBody HashMap<String,Object> param){
			logger.info("sysHobby.allList页面请求参数："+param);
		List<SysHobby> result = baseService.allList(param);
		 return resultBeanFactory.getBean(result);
	}
	
	     
	

}

