package com.zycw.tuotui.controller.auoccupation;


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
import com.zycw.tuotui.iface.auoccupation.IAuOccupationService;
import com.zycw.tuotui.entity.auoccupation.AuOccupation;
import java.util.Date;
import java.util.List;
import java.util.HashMap;


@ApiOperation(value = "b职业")
@Api(value = "b职业接口", tags = { "b职业接口" })
@Slf4j
@RestController
@RequestMapping("/auOccupation")
public class AuOccupationController extends BaseController <IAuOccupationService, AuOccupation>{

    private static Logger logger = LoggerFactory.getLogger(AuOccupationController.class);
       
    		   
    @Autowired
    private ResultBeanFactory resultBeanFactory;

     @ApiOperation(value = "根据id物理删除")
     @ApiImplicitParam(name = "auOccupationId", value = "ID", required = true, dataType = "String")
     @RequestMapping(value = "/deleteById", method = RequestMethod.GET)
     public ResultBean<String> deleteById(@RequestParam("auOccupationId") String auOccupationId) {
     	logger.info("auOccupation.deleteById页面请求参数："+auOccupationId);
		try {
			baseService.deleteById(auOccupationId);
			return resultBeanFactory.getBean("删除成功");
		} catch (Exception e) {
			logger.error("失败原因:{}", e);
			return resultBeanFactory.getException(e);
		}
     }

     @ApiOperation(value = "根据id逻辑删除")
     @ApiImplicitParam(name = "auOccupationId", value = "ID", required = true, dataType = "String")
     @RequestMapping(value = "/deleteByIdLogic", method = RequestMethod.GET)
     public ResultBean<String> deleteByIdLogic(@RequestParam("auOccupationId") String auOccupationId) {
		logger.info("auOccupation.deleteByIdLogic页面请求参数："+auOccupationId);
		try {
			baseService.deleteByIdLogic(auOccupationId);
			return resultBeanFactory.getBean("删除成功");
		} catch (Exception e) {
			logger.error("失败原因:{}", e);
			return resultBeanFactory.getException(e);
		}
     }
     

     @ApiOperation(value = "根据主键修改")
     @ApiImplicitParam(name = "auOccupation", value = "b职业对象", required = true, dataType = "AuOccupation")
     @RequestMapping(value = "/updateObjById", method = RequestMethod.POST)
     public ResultBean<String> updateObjById(@RequestBody AuOccupation auOccupation) {
         logger.info("auOccupation.updateObjById页面请求参数："+auOccupation);
		try {
			logger.info("auOccupation.update页面请求参数："+auOccupation);
			baseService.updateObjById(auOccupation);
			return resultBeanFactory.getBean("修改成功");
        } catch (Exception e) {
            logger.error("修改失败:{}", e);
            return resultBeanFactory.getException(e);
        }
     }
     
     

     @ApiOperation(value = "新增")
     @ApiImplicitParam(name = "auOccupation", value = "b职业对象", required = true, dataType = "AuOccupation")
     @RequestMapping(value = "/insertObj", method = RequestMethod.POST)
     public ResultBean<String> insertObj(@RequestBody AuOccupation auOccupation) {
		try {
			logger.info("auOccupation.insertObj页面请求参数："+auOccupation);
	        baseService.insert(auOccupation);
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

    	@ApiImplicitParam(name = "auOccupationOccupationName", value = "return-Mlv职业", required = false, dataType = "String"),
     })
     @RequestMapping(value = "/pageList", method = RequestMethod.POST)
     public ResultBeanFactory.ResultBean<PageInfo<AuOccupation>> pageList(@RequestBody HashMap<String,Object> params) {
		try {
			logger.info("auOccupation.pageList页面请求参数："+params);
            int count = baseService.countNum(params);
            PageInfo<AuOccupation> result=baseService.pageList(params);
            result.setTotal(count);
            return resultBeanFactory.getBean(result);
		} catch (Exception e) {
			logger.error("AuOccupationController.pageList Exception:{}", e);
			return resultBeanFactory.getException(e);
		}
     }


     @ApiOperation(value = "通过id查询")
     @ApiImplicitParam(name = "auOccupationId", value = "ID", required = true, dataType = "String")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "auOccupationId", value = "return-id", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auOccupationOccupationName", value = "return-Mlv职业", required = false, dataType = "String"),
		})
     @RequestMapping(value = "/selectObjById", method = RequestMethod.GET)
     public ResultBean<HashMap<String,Object>> info(@RequestParam("auOccupationId") String auOccupationId) {
			logger.info("auOccupation.selectObjById页面请求参数："+auOccupationId);
         HashMap<String,Object> auOccupation = baseService.selectObjById(auOccupationId);
         return resultBeanFactory.getBean(auOccupation);
     }
     
     
	
     @ApiOperation(value = "allList")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "auOccupationId", value = "return-id", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auOccupationOccupationName", value = "return-Mlv职业", required = false, dataType = "String"),
     })
     @RequestMapping(value = "/allList", method = RequestMethod.POST)
	public ResultBean<List<AuOccupation>> allList(@RequestBody HashMap<String,Object> param){
			logger.info("auOccupation.allList页面请求参数："+param);
		List<AuOccupation> result = baseService.allList(param);
		 return resultBeanFactory.getBean(result);
	}
	
	     
	

}

