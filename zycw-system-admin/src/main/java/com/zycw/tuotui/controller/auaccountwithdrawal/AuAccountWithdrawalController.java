package com.zycw.tuotui.controller.auaccountwithdrawal;


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
import com.zycw.tuotui.iface.auaccountwithdrawal.IAuAccountWithdrawalService;
import com.zycw.tuotui.entity.auaccountwithdrawal.AuAccountWithdrawal;
import java.util.Date;
import java.util.List;
import java.util.HashMap;


@ApiOperation(value = "提现明细表")
@Api(value = "提现明细表接口", tags = { "提现明细表接口" })
@Slf4j
@RestController
@RequestMapping("/auAccountWithdrawal")
public class AuAccountWithdrawalController extends BaseController <IAuAccountWithdrawalService, AuAccountWithdrawal>{

    private static Logger logger = LoggerFactory.getLogger(AuAccountWithdrawalController.class);
       
    		   
    @Autowired
    private ResultBeanFactory resultBeanFactory;

     @ApiOperation(value = "根据id物理删除")
     @ApiImplicitParam(name = "auAccountWithdrawalId", value = "ID", required = true, dataType = "String")
     @RequestMapping(value = "/deleteById", method = RequestMethod.GET)
     public ResultBean<String> deleteById(@RequestParam("auAccountWithdrawalId") String auAccountWithdrawalId) {
     	logger.info("auAccountWithdrawal.deleteById页面请求参数："+auAccountWithdrawalId);
		try {
			baseService.deleteById(auAccountWithdrawalId);
			return resultBeanFactory.getBean("删除成功");
		} catch (Exception e) {
			logger.error("失败原因:{}", e);
			return resultBeanFactory.getException(e);
		}
     }

     @ApiOperation(value = "根据id逻辑删除")
     @ApiImplicitParam(name = "auAccountWithdrawalId", value = "ID", required = true, dataType = "String")
     @RequestMapping(value = "/deleteByIdLogic", method = RequestMethod.GET)
     public ResultBean<String> deleteByIdLogic(@RequestParam("auAccountWithdrawalId") String auAccountWithdrawalId) {
		logger.info("auAccountWithdrawal.deleteByIdLogic页面请求参数："+auAccountWithdrawalId);
		try {
			baseService.deleteByIdLogic(auAccountWithdrawalId);
			return resultBeanFactory.getBean("删除成功");
		} catch (Exception e) {
			logger.error("失败原因:{}", e);
			return resultBeanFactory.getException(e);
		}
     }
     

     @ApiOperation(value = "根据主键修改")
     @ApiImplicitParam(name = "auAccountWithdrawal", value = "提现明细表对象", required = true, dataType = "AuAccountWithdrawal")
     @RequestMapping(value = "/updateObjById", method = RequestMethod.POST)
     public ResultBean<String> updateObjById(@RequestBody AuAccountWithdrawal auAccountWithdrawal) {
         logger.info("auAccountWithdrawal.updateObjById页面请求参数："+auAccountWithdrawal);
		try {
			auAccountWithdrawal.setAuAccountWithdrawalWithdrawalTime(new Date());
			logger.info("auAccountWithdrawal.update页面请求参数："+auAccountWithdrawal);
			baseService.updateObjById(auAccountWithdrawal);
			return resultBeanFactory.getBean("修改成功");
        } catch (Exception e) {
            logger.error("修改失败:{}", e);
            return resultBeanFactory.getException(e);
        }
     }
     
     

     @ApiOperation(value = "新增")
     @ApiImplicitParam(name = "auAccountWithdrawal", value = "提现明细表对象", required = true, dataType = "AuAccountWithdrawal")
     @RequestMapping(value = "/insertObj", method = RequestMethod.POST)
     public ResultBean<String> insertObj(@RequestBody AuAccountWithdrawal auAccountWithdrawal) {
		try {
			logger.info("auAccountWithdrawal.insertObj页面请求参数："+auAccountWithdrawal);
			auAccountWithdrawal.setAuAccountWithdrawalWithdrawalTime(new Date());
	        baseService.insert(auAccountWithdrawal);
	        return resultBeanFactory.getBean("插入记录成功");
        } catch (Exception e) {
            logger.error("插入记录失败:{}", e);
            return resultBeanFactory.getException(e);
        }
     }
     
     @ApiOperation(value = "分页查询")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "auAccountWithdrawalUserId", value = "lv用户", required = false, dataType = "${dataType}"),
    	@ApiImplicitParam(name = "auAccountWithdrawalTaskId", value = "lv任务", required = false, dataType = "${dataType}"),
		@ApiImplicitParam(name = "pageSize", value = "每页条数", required = false, dataType = "int"),
		@ApiImplicitParam(name = "pageNum", value = "当前页数", required = false, dataType = "int"),





    	@ApiImplicitParam(name = "auAccountWithdrawalWithdrawalPath", value = "return-提现途径:0-微信,1-支付宝,2-银行卡", required = false, dataType = "${dataType}"),
     })
     @RequestMapping(value = "/pageList", method = RequestMethod.POST)
     public ResultBeanFactory.ResultBean<PageInfo<AuAccountWithdrawal>> pageList(@RequestBody HashMap<String,Object> params) {
		try {
			logger.info("auAccountWithdrawal.pageList页面请求参数："+params);
            int count = baseService.countNum(params);
            PageInfo<AuAccountWithdrawal> result=baseService.pageList(params);
            result.setTotal(count);
            return resultBeanFactory.getBean(result);
		} catch (Exception e) {
			logger.error("AuAccountWithdrawalController.pageList Exception:{}", e);
			return resultBeanFactory.getException(e);
		}
     }


     @ApiOperation(value = "通过id查询")
     @ApiImplicitParam(name = "auAccountWithdrawalId", value = "ID", required = true, dataType = "String")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "auAccountWithdrawalId", value = "return-ID", required = false, dataType = "${dataType}"),
    	@ApiImplicitParam(name = "auUserMobile", value = "return-lvM手机号", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCustomTaskTaskTitle", value = "return-lvM任务标题", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auAccountWithdrawalWithdrawalTime", value = "return-lv提现时间", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auAccountWithdrawalWithdrawalStatus", value = "return-提现状态:0-已到账,1-未到账", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auAccountWithdrawalWithdrawalPath", value = "return-提现途径:0-微信,1-支付宝,2-银行卡", required = false, dataType = "Date"),
		})
     @RequestMapping(value = "/selectObjById", method = RequestMethod.GET)
     public ResultBean<HashMap<String,Object>> info(@RequestParam("auAccountWithdrawalId") String auAccountWithdrawalId) {
			logger.info("auAccountWithdrawal.selectObjById页面请求参数："+auAccountWithdrawalId);
         HashMap<String,Object> auAccountWithdrawal = baseService.selectObjById(auAccountWithdrawalId);
         return resultBeanFactory.getBean(auAccountWithdrawal);
     }
     
     
	
     @ApiOperation(value = "allList")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "auAccountWithdrawalUserId", value = "lv用户", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auAccountWithdrawalTaskId", value = "lv任务", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auAccountWithdrawalId", value = "return-ID", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auUserMobile", value = "return-lvM手机号", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCustomTaskTaskTitle", value = "return-lvM任务标题", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auAccountWithdrawalWithdrawalTime", value = "return-lv提现时间", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auAccountWithdrawalWithdrawalStatus", value = "return-提现状态:0-已到账,1-未到账", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auAccountWithdrawalWithdrawalPath", value = "return-提现途径:0-微信,1-支付宝,2-银行卡", required = false, dataType = "Date")
     })
     @RequestMapping(value = "/allList", method = RequestMethod.POST)
	public ResultBean<List<AuAccountWithdrawal>> allList(@RequestBody HashMap<String,Object> param){
			logger.info("auAccountWithdrawal.allList页面请求参数："+param);
		List<AuAccountWithdrawal> result = baseService.allList(param);
		 return resultBeanFactory.getBean(result);
	}
	
	     
	

}

