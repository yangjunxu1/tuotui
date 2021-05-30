package com.zycw.tuotui.controller.sysadvert;


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
import com.zycw.tuotui.iface.sysadvert.ISysAdvertService;
import com.zycw.tuotui.entity.sysadvert.SysAdvert;
import java.util.Date;
import java.util.List;
import java.util.HashMap;


@ApiOperation(value = "广告协议信息")
@Api(value = "广告协议信息接口", tags = { "广告协议信息接口" })
@Slf4j
@RestController
@RequestMapping("/sysAdvert")
public class SysAdvertController extends BaseController <ISysAdvertService, SysAdvert>{

    private static Logger logger = LoggerFactory.getLogger(SysAdvertController.class);
       
    		   
    @Autowired
    private ResultBeanFactory resultBeanFactory;

     @ApiOperation(value = "根据id物理删除")
     @ApiImplicitParam(name = "sysAdvertId", value = "ID", required = true, dataType = "String")
     @RequestMapping(value = "/deleteById", method = RequestMethod.GET)
     public ResultBean<String> deleteById(@RequestParam("sysAdvertId") String sysAdvertId) {
     	logger.info("sysAdvert.deleteById页面请求参数："+sysAdvertId);
		try {
			baseService.deleteById(sysAdvertId);
			return resultBeanFactory.getBean("删除成功");
		} catch (Exception e) {
			logger.error("失败原因:{}", e);
			return resultBeanFactory.getException(e);
		}
     }

     @ApiOperation(value = "根据id逻辑删除")
     @ApiImplicitParam(name = "sysAdvertId", value = "ID", required = true, dataType = "String")
     @RequestMapping(value = "/deleteByIdLogic", method = RequestMethod.GET)
     public ResultBean<String> deleteByIdLogic(@RequestParam("sysAdvertId") String sysAdvertId) {
		logger.info("sysAdvert.deleteByIdLogic页面请求参数："+sysAdvertId);
		try {
			baseService.deleteByIdLogic(sysAdvertId);
			return resultBeanFactory.getBean("删除成功");
		} catch (Exception e) {
			logger.error("失败原因:{}", e);
			return resultBeanFactory.getException(e);
		}
     }
     

     @ApiOperation(value = "根据主键修改")
     @ApiImplicitParam(name = "sysAdvert", value = "广告协议信息对象", required = true, dataType = "SysAdvert")
     @RequestMapping(value = "/updateObjById", method = RequestMethod.POST)
     public ResultBean<String> updateObjById(@RequestBody SysAdvert sysAdvert) {
         logger.info("sysAdvert.updateObjById页面请求参数："+sysAdvert);
		try {
			sysAdvert.setSysAdvertCreatedTime(new Date());
			sysAdvert.setSysAdvertUpdatedTime(new Date());
			logger.info("sysAdvert.update页面请求参数："+sysAdvert);
			baseService.updateObjById(sysAdvert);
			return resultBeanFactory.getBean("修改成功");
        } catch (Exception e) {
            logger.error("修改失败:{}", e);
            return resultBeanFactory.getException(e);
        }
     }
     
     

     @ApiOperation(value = "新增")
     @ApiImplicitParam(name = "sysAdvert", value = "广告协议信息对象", required = true, dataType = "SysAdvert")
     @RequestMapping(value = "/insertObj", method = RequestMethod.POST)
     public ResultBean<String> insertObj(@RequestBody SysAdvert sysAdvert) {
		try {
			logger.info("sysAdvert.insertObj页面请求参数："+sysAdvert);
			sysAdvert.setSysAdvertCreatedTime(new Date());
			sysAdvert.setSysAdvertUpdatedTime(new Date());
	        baseService.insert(sysAdvert);
	        return resultBeanFactory.getBean("插入记录成功");
        } catch (Exception e) {
            logger.error("插入记录失败:{}", e);
            return resultBeanFactory.getException(e);
        }
     }
     
     @ApiOperation(value = "分页查询")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "sysAdvertPostitionId", value = "", required = false, dataType = "${dataType}"),
		@ApiImplicitParam(name = "pageSize", value = "每页条数", required = false, dataType = "int"),
		@ApiImplicitParam(name = "pageNum", value = "当前页数", required = false, dataType = "int"),









    	@ApiImplicitParam(name = "sysAdvertUpdatedTime", value = "return-u修改时间", required = false, dataType = "Date"),
     })
     @RequestMapping(value = "/pageList", method = RequestMethod.POST)
     public ResultBeanFactory.ResultBean<PageInfo<SysAdvert>> pageList(@RequestBody HashMap<String,Object> params) {
		try {
			logger.info("sysAdvert.pageList页面请求参数："+params);
            int count = baseService.countNum(params);
            PageInfo<SysAdvert> result=baseService.pageList(params);
            result.setTotal(count);
            return resultBeanFactory.getBean(result);
		} catch (Exception e) {
			logger.error("SysAdvertController.pageList Exception:{}", e);
			return resultBeanFactory.getException(e);
		}
     }


     @ApiOperation(value = "通过id查询")
     @ApiImplicitParam(name = "sysAdvertId", value = "ID", required = true, dataType = "String")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "sysAdvertId", value = "return-hID", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "sysAdvertTitle", value = "return-lv广告标题", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysAdvertPostitionName", value = "return-lnM位置名称", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysAdvertPicAddr", value = "return-lv广告图片地址", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysAdvertUrl", value = "return-lv外链地址", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysAdvertRouter", value = "return-lv内链地址", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysAdvertType", value = "return-lvS广告类型:0-协议,1-站内广告，2-外链广告", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysAdvertContent", value = "return-文字介绍", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysAdvertCreatedTime", value = "return-il创建时间", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "sysAdvertUpdatedTime", value = "return-u修改时间", required = false, dataType = "Date"),
		})
     @RequestMapping(value = "/selectObjById", method = RequestMethod.GET)
     public ResultBean<HashMap<String,Object>> info(@RequestParam("sysAdvertId") String sysAdvertId) {
			logger.info("sysAdvert.selectObjById页面请求参数："+sysAdvertId);
         HashMap<String,Object> sysAdvert = baseService.selectObjById(sysAdvertId);
         return resultBeanFactory.getBean(sysAdvert);
     }
     
     
	
     @ApiOperation(value = "allList")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "sysAdvertPostitionId", value = "", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "sysAdvertId", value = "return-hID", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "sysAdvertTitle", value = "return-lv广告标题", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysAdvertPostitionName", value = "return-lnM位置名称", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysAdvertPicAddr", value = "return-lv广告图片地址", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysAdvertUrl", value = "return-lv外链地址", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysAdvertRouter", value = "return-lv内链地址", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysAdvertType", value = "return-lvS广告类型:0-协议,1-站内广告，2-外链广告", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysAdvertContent", value = "return-文字介绍", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysAdvertCreatedTime", value = "return-il创建时间", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "sysAdvertUpdatedTime", value = "return-u修改时间", required = false, dataType = "Date")
     })
     @RequestMapping(value = "/allList", method = RequestMethod.POST)
	public ResultBean<List<SysAdvert>> allList(@RequestBody HashMap<String,Object> param){
			logger.info("sysAdvert.allList页面请求参数："+param);
		List<SysAdvert> result = baseService.allList(param);
		 return resultBeanFactory.getBean(result);
	}
	
	     
	

}

