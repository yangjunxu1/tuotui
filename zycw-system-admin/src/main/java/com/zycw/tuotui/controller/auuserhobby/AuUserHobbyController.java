package com.zycw.tuotui.controller.auuserhobby;


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
import com.zycw.tuotui.iface.auuserhobby.IAuUserHobbyService;
import com.zycw.tuotui.entity.auuserhobby.AuUserHobby;
import java.util.Date;
import java.util.List;
import java.util.HashMap;


@ApiOperation(value = "用户爱好关联")
@Api(value = "用户爱好关联接口", tags = { "用户爱好关联接口" })
@Slf4j
@RestController
@RequestMapping("/auUserHobby")
public class AuUserHobbyController extends BaseController <IAuUserHobbyService, AuUserHobby>{

    private static Logger logger = LoggerFactory.getLogger(AuUserHobbyController.class);
       
    		   
    @Autowired
    private ResultBeanFactory resultBeanFactory;

     @ApiOperation(value = "根据id物理删除")
     @ApiImplicitParam(name = "auUserHobbyId", value = "ID", required = true, dataType = "String")
     @RequestMapping(value = "/deleteById", method = RequestMethod.GET)
     public ResultBean<String> deleteById(@RequestParam("auUserHobbyId") String auUserHobbyId) {
     	logger.info("auUserHobby.deleteById页面请求参数："+auUserHobbyId);
		try {
			baseService.deleteById(auUserHobbyId);
			return resultBeanFactory.getBean("删除成功");
		} catch (Exception e) {
			logger.error("失败原因:{}", e);
			return resultBeanFactory.getException(e);
		}
     }

     @ApiOperation(value = "根据id逻辑删除")
     @ApiImplicitParam(name = "auUserHobbyId", value = "ID", required = true, dataType = "String")
     @RequestMapping(value = "/deleteByIdLogic", method = RequestMethod.GET)
     public ResultBean<String> deleteByIdLogic(@RequestParam("auUserHobbyId") String auUserHobbyId) {
		logger.info("auUserHobby.deleteByIdLogic页面请求参数："+auUserHobbyId);
		try {
			baseService.deleteByIdLogic(auUserHobbyId);
			return resultBeanFactory.getBean("删除成功");
		} catch (Exception e) {
			logger.error("失败原因:{}", e);
			return resultBeanFactory.getException(e);
		}
     }
     

     @ApiOperation(value = "根据主键修改")
     @ApiImplicitParam(name = "auUserHobby", value = "用户爱好关联对象", required = true, dataType = "AuUserHobby")
     @RequestMapping(value = "/updateObjById", method = RequestMethod.POST)
     public ResultBean<String> updateObjById(@RequestBody AuUserHobby auUserHobby) {
         logger.info("auUserHobby.updateObjById页面请求参数："+auUserHobby);
		try {
			logger.info("auUserHobby.update页面请求参数："+auUserHobby);
			baseService.updateObjById(auUserHobby);
			return resultBeanFactory.getBean("修改成功");
        } catch (Exception e) {
            logger.error("修改失败:{}", e);
            return resultBeanFactory.getException(e);
        }
     }
     
     

     @ApiOperation(value = "新增")
     @ApiImplicitParam(name = "auUserHobby", value = "用户爱好关联对象", required = true, dataType = "AuUserHobby")
     @RequestMapping(value = "/insertObj", method = RequestMethod.POST)
     public ResultBean<String> insertObj(@RequestBody AuUserHobby auUserHobby) {
		try {
			logger.info("auUserHobby.insertObj页面请求参数："+auUserHobby);
	        baseService.insert(auUserHobby);
	        return resultBeanFactory.getBean("插入记录成功");
        } catch (Exception e) {
            logger.error("插入记录失败:{}", e);
            return resultBeanFactory.getException(e);
        }
     }
     
     @ApiOperation(value = "分页查询")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "auUserHobbyHobbyId", value = "爱好id", required = false, dataType = "${dataType}"),
    	@ApiImplicitParam(name = "auUserHobbyUserId", value = "用户id", required = false, dataType = "${dataType}"),
		@ApiImplicitParam(name = "pageSize", value = "每页条数", required = false, dataType = "int"),
		@ApiImplicitParam(name = "pageNum", value = "当前页数", required = false, dataType = "int"),


    	@ApiImplicitParam(name = "sysHobbyHobbyTitle", value = "return-lvM爱好", required = false, dataType = "String")
     })
     @RequestMapping(value = "/pageList", method = RequestMethod.POST)
     public ResultBeanFactory.ResultBean<PageInfo<AuUserHobby>> pageList(@RequestBody HashMap<String,Object> params) {
		try {
			logger.info("auUserHobby.pageList页面请求参数："+params);
            int count = baseService.countNum(params);
            PageInfo<AuUserHobby> result=baseService.pageList(params);
            result.setTotal(count);
            return resultBeanFactory.getBean(result);
		} catch (Exception e) {
			logger.error("AuUserHobbyController.pageList Exception:{}", e);
			return resultBeanFactory.getException(e);
		}
     }


     @ApiOperation(value = "通过id查询")
     @ApiImplicitParam(name = "auUserHobbyId", value = "ID", required = true, dataType = "String")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "auUserHobbyId", value = "return-id", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysHobbyHobbyTitle", value = "return-lvM爱好", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysHobbyHobbyTitle", value = "return-lvM爱好", required = false, dataType = "String")
		})
     @RequestMapping(value = "/selectObjById", method = RequestMethod.GET)
     public ResultBean<HashMap<String,Object>> info(@RequestParam("auUserHobbyId") String auUserHobbyId) {
			logger.info("auUserHobby.selectObjById页面请求参数："+auUserHobbyId);
         HashMap<String,Object> auUserHobby = baseService.selectObjById(auUserHobbyId);
         return resultBeanFactory.getBean(auUserHobby);
     }
     
     
	
     @ApiOperation(value = "allList")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "auUserHobbyHobbyId", value = "爱好id", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auUserHobbyUserId", value = "用户id", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auUserHobbyId", value = "return-id", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysHobbyHobbyTitle", value = "return-lvM爱好", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysHobbyHobbyTitle", value = "return-lvM爱好", required = false, dataType = "String")
     })
     @RequestMapping(value = "/allList", method = RequestMethod.POST)
	public ResultBean<List<AuUserHobby>> allList(@RequestBody HashMap<String,Object> param){
			logger.info("auUserHobby.allList页面请求参数："+param);
		List<AuUserHobby> result = baseService.allList(param);
		 return resultBeanFactory.getBean(result);
	}
	
	     
	

}

