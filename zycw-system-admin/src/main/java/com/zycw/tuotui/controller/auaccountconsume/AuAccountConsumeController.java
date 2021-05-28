package com.zycw.tuotui.controller.auaccountconsume;


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
import com.zycw.tuotui.iface.auaccountconsume.IAuAccountConsumeService;
import com.zycw.tuotui.entity.auaccountconsume.AuAccountConsume;
import java.util.Date;
import java.util.List;
import java.util.HashMap;


@ApiOperation(value = "公司账户消费明细")
@Api(value = "公司账户消费明细接口", tags = { "公司账户消费明细接口" })
@Slf4j
@RestController
@RequestMapping("/auAccountConsume")
public class AuAccountConsumeController extends BaseController <IAuAccountConsumeService, AuAccountConsume>{

    private static Logger logger = LoggerFactory.getLogger(AuAccountConsumeController.class);
       
    		   
    @Autowired
    private ResultBeanFactory resultBeanFactory;

     @ApiOperation(value = "根据id物理删除")
     @ApiImplicitParam(name = "auAccountConsumeId", value = "ID", required = true, dataType = "String")
     @RequestMapping(value = "/deleteById", method = RequestMethod.GET)
     public ResultBean<String> deleteById(@RequestParam("auAccountConsumeId") String auAccountConsumeId) {
     	logger.info("auAccountConsume.deleteById页面请求参数："+auAccountConsumeId);
		try {
			baseService.deleteById(auAccountConsumeId);
			return resultBeanFactory.getBean("删除成功");
		} catch (Exception e) {
			logger.error("失败原因:{}", e);
			return resultBeanFactory.getException(e);
		}
     }

     @ApiOperation(value = "根据id逻辑删除")
     @ApiImplicitParam(name = "auAccountConsumeId", value = "ID", required = true, dataType = "String")
     @RequestMapping(value = "/deleteByIdLogic", method = RequestMethod.GET)
     public ResultBean<String> deleteByIdLogic(@RequestParam("auAccountConsumeId") String auAccountConsumeId) {
		logger.info("auAccountConsume.deleteByIdLogic页面请求参数："+auAccountConsumeId);
		try {
			baseService.deleteByIdLogic(auAccountConsumeId);
			return resultBeanFactory.getBean("删除成功");
		} catch (Exception e) {
			logger.error("失败原因:{}", e);
			return resultBeanFactory.getException(e);
		}
     }
     

     @ApiOperation(value = "根据主键修改")
     @ApiImplicitParam(name = "auAccountConsume", value = "公司账户消费明细对象", required = true, dataType = "AuAccountConsume")
     @RequestMapping(value = "/updateObjById", method = RequestMethod.POST)
     public ResultBean<String> updateObjById(@RequestBody AuAccountConsume auAccountConsume) {
         logger.info("auAccountConsume.updateObjById页面请求参数："+auAccountConsume);
		try {
			logger.info("auAccountConsume.update页面请求参数："+auAccountConsume);
			baseService.updateObjById(auAccountConsume);
			return resultBeanFactory.getBean("修改成功");
        } catch (Exception e) {
            logger.error("修改失败:{}", e);
            return resultBeanFactory.getException(e);
        }
     }
     
     

     @ApiOperation(value = "新增")
     @ApiImplicitParam(name = "auAccountConsume", value = "公司账户消费明细对象", required = true, dataType = "AuAccountConsume")
     @RequestMapping(value = "/insertObj", method = RequestMethod.POST)
     public ResultBean<String> insertObj(@RequestBody AuAccountConsume auAccountConsume) {
		try {
			logger.info("auAccountConsume.insertObj页面请求参数："+auAccountConsume);
	        baseService.insert(auAccountConsume);
	        return resultBeanFactory.getBean("插入记录成功");
        } catch (Exception e) {
            logger.error("插入记录失败:{}", e);
            return resultBeanFactory.getException(e);
        }
     }
     
     @ApiOperation(value = "分页查询")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "auAccountConsumeCompanyId", value = "公司", required = false, dataType = "${dataType}"),
    	@ApiImplicitParam(name = "auAccountConsumeUserId", value = "会员", required = false, dataType = "${dataType}"),
		@ApiImplicitParam(name = "pageSize", value = "每页条数", required = false, dataType = "int"),
		@ApiImplicitParam(name = "pageNum", value = "当前页数", required = false, dataType = "int"),





    	@ApiImplicitParam(name = "auAccountConsumeConsumeTime", value = "return-消费时间", required = false, dataType = "Date"),
     })
     @RequestMapping(value = "/pageList", method = RequestMethod.POST)
     public ResultBeanFactory.ResultBean<PageInfo<AuAccountConsume>> pageList(@RequestBody HashMap<String,Object> params) {
		try {
			logger.info("auAccountConsume.pageList页面请求参数："+params);
            int count = baseService.countNum(params);
            PageInfo<AuAccountConsume> result=baseService.pageList(params);
            result.setTotal(count);
            return resultBeanFactory.getBean(result);
		} catch (Exception e) {
			logger.error("AuAccountConsumeController.pageList Exception:{}", e);
			return resultBeanFactory.getException(e);
		}
     }


     @ApiOperation(value = "通过id查询")
     @ApiImplicitParam(name = "auAccountConsumeId", value = "ID", required = true, dataType = "String")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "auAccountConsumeId", value = "return-ID", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auCompanyName", value = "return-lvM企业名称", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auUserMobile", value = "return-lvM手机号", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auAccountConsumePrice", value = "return-消费金额", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auAccountConsumeTaskId", value = "return-任务", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auAccountConsumeConsumeTime", value = "return-消费时间", required = false, dataType = "Date"),
		})
     @RequestMapping(value = "/selectObjById", method = RequestMethod.GET)
     public ResultBean<HashMap<String,Object>> info(@RequestParam("auAccountConsumeId") String auAccountConsumeId) {
			logger.info("auAccountConsume.selectObjById页面请求参数："+auAccountConsumeId);
         HashMap<String,Object> auAccountConsume = baseService.selectObjById(auAccountConsumeId);
         return resultBeanFactory.getBean(auAccountConsume);
     }
     
     
	
     @ApiOperation(value = "allList")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "auAccountConsumeCompanyId", value = "公司", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auAccountConsumeUserId", value = "会员", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auAccountConsumeId", value = "return-ID", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auCompanyName", value = "return-lvM企业名称", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auUserMobile", value = "return-lvM手机号", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auAccountConsumePrice", value = "return-消费金额", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auAccountConsumeTaskId", value = "return-任务", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auAccountConsumeConsumeTime", value = "return-消费时间", required = false, dataType = "Date"),
     })
     @RequestMapping(value = "/allList", method = RequestMethod.POST)
	public ResultBean<List<AuAccountConsume>> allList(@RequestBody HashMap<String,Object> param){
			logger.info("auAccountConsume.allList页面请求参数："+param);
		List<AuAccountConsume> result = baseService.allList(param);
		 return resultBeanFactory.getBean(result);
	}
	
	     
	

}

