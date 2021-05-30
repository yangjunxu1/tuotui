package com.zycw.tuotui.controller.aucompany;


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
import com.zycw.tuotui.iface.aucompany.IAuCompanyService;
import com.zycw.tuotui.entity.aucompany.AuCompany;
import java.util.Date;
import java.util.List;
import java.util.HashMap;


@ApiOperation(value = "企业信息")
@Api(value = "企业信息接口", tags = { "企业信息接口" })
@Slf4j
@RestController
@RequestMapping("/auCompany")
public class AuCompanyController extends BaseController <IAuCompanyService, AuCompany>{

    private static Logger logger = LoggerFactory.getLogger(AuCompanyController.class);
       
    		   
    @Autowired
    private ResultBeanFactory resultBeanFactory;

     @ApiOperation(value = "根据id物理删除")
     @ApiImplicitParam(name = "auCompanyId", value = "ID", required = true, dataType = "String")
     @RequestMapping(value = "/deleteById", method = RequestMethod.GET)
     public ResultBean<String> deleteById(@RequestParam("auCompanyId") String auCompanyId) {
     	logger.info("auCompany.deleteById页面请求参数："+auCompanyId);
		try {
			baseService.deleteById(auCompanyId);
			return resultBeanFactory.getBean("删除成功");
		} catch (Exception e) {
			logger.error("失败原因:{}", e);
			return resultBeanFactory.getException(e);
		}
     }

     @ApiOperation(value = "根据id逻辑删除")
     @ApiImplicitParam(name = "auCompanyId", value = "ID", required = true, dataType = "String")
     @RequestMapping(value = "/deleteByIdLogic", method = RequestMethod.GET)
     public ResultBean<String> deleteByIdLogic(@RequestParam("auCompanyId") String auCompanyId) {
		logger.info("auCompany.deleteByIdLogic页面请求参数："+auCompanyId);
		try {
			baseService.deleteByIdLogic(auCompanyId);
			return resultBeanFactory.getBean("删除成功");
		} catch (Exception e) {
			logger.error("失败原因:{}", e);
			return resultBeanFactory.getException(e);
		}
     }
     

     @ApiOperation(value = "根据主键修改")
     @ApiImplicitParam(name = "auCompany", value = "企业信息对象", required = true, dataType = "AuCompany")
     @RequestMapping(value = "/updateObjById", method = RequestMethod.POST)
     public ResultBean<String> updateObjById(@RequestBody AuCompany auCompany) {
         logger.info("auCompany.updateObjById页面请求参数："+auCompany);
		try {
			auCompany.setAuCompanyVerifyTime(new Date());
			auCompany.setAuCompanyReviewTime(new Date());
			auCompany.setAuCompanyCreatedTime(new Date());
			auCompany.setAuCompanyUpdatedTime(new Date());
			logger.info("auCompany.update页面请求参数："+auCompany);
			baseService.updateObjById(auCompany);
			return resultBeanFactory.getBean("修改成功");
        } catch (Exception e) {
            logger.error("修改失败:{}", e);
            return resultBeanFactory.getException(e);
        }
     }
     
     

     @ApiOperation(value = "新增")
     @ApiImplicitParam(name = "auCompany", value = "企业信息对象", required = true, dataType = "AuCompany")
     @RequestMapping(value = "/insertObj", method = RequestMethod.POST)
     public ResultBean<String> insertObj(@RequestBody AuCompany auCompany) {
		try {
			logger.info("auCompany.insertObj页面请求参数："+auCompany);
			auCompany.setAuCompanyVerifyTime(new Date());
			auCompany.setAuCompanyReviewTime(new Date());
			auCompany.setAuCompanyCreatedTime(new Date());
			auCompany.setAuCompanyUpdatedTime(new Date());
	        baseService.insert(auCompany);
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


















    	@ApiImplicitParam(name = "auCompanyUpdatedTime", value = "return-u修改时间", required = false, dataType = "Date"),
     })
     @RequestMapping(value = "/pageList", method = RequestMethod.POST)
     public ResultBeanFactory.ResultBean<PageInfo<AuCompany>> pageList(@RequestBody HashMap<String,Object> params) {
		try {
			logger.info("auCompany.pageList页面请求参数："+params);
            int count = baseService.countNum(params);
            PageInfo<AuCompany> result=baseService.pageList(params);
            result.setTotal(count);
            return resultBeanFactory.getBean(result);
		} catch (Exception e) {
			logger.error("AuCompanyController.pageList Exception:{}", e);
			return resultBeanFactory.getException(e);
		}
     }


     @ApiOperation(value = "通过id查询")
     @ApiImplicitParam(name = "auCompanyId", value = "ID", required = true, dataType = "String")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "auCompanyId", value = "return-id", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auCompanyName", value = "return-lvM企业名称", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCompanyCreditCode", value = "return-lv企业信用编码", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCompanyBusinessLicense", value = "return-lv营业执照照片", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCompanyCorporation", value = "return-lv法人姓名", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCompanyContactor", value = "return-lv联系人姓名", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCompanyContactsPhone", value = "return-lv联系方式", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCompanyContactsProxyStatement", value = "return-lv联系人委托书", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCompanyAddress", value = "return-lv企业地址", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCompanyAccountPrice", value = "return-h公司账户金额", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCompanyUserId", value = "return-lv用户id", required = false, dataType = "int"),
    	@ApiImplicitParam(name = "auCompanyVerifyStatus", value = "return-lv审核状态:0-通过,1-未通过", required = false, dataType = "int"),
    	@ApiImplicitParam(name = "auCompanyVerifyRefuse", value = "return-拒绝原因", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCompanyVerifyTime", value = "return-lvul审核时间", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auCompanyReviewStatus", value = "return-lv复审状态:0-通过,1-未通过", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auCompanyReviewRefuse", value = "return-v复审拒绝理由", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCompanyReviewTime", value = "return-lv复审时间", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auCompanyCreatedTime", value = "return-il创建时间", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auCompanyUpdatedTime", value = "return-u修改时间", required = false, dataType = "Date"),
		})
     @RequestMapping(value = "/selectObjById", method = RequestMethod.GET)
     public ResultBean<HashMap<String,Object>> info(@RequestParam("auCompanyId") String auCompanyId) {
			logger.info("auCompany.selectObjById页面请求参数："+auCompanyId);
         HashMap<String,Object> auCompany = baseService.selectObjById(auCompanyId);
         return resultBeanFactory.getBean(auCompany);
     }
     
     
	
     @ApiOperation(value = "allList")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "auCompanyId", value = "return-id", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auCompanyName", value = "return-lvM企业名称", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCompanyCreditCode", value = "return-lv企业信用编码", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCompanyBusinessLicense", value = "return-lv营业执照照片", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCompanyCorporation", value = "return-lv法人姓名", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCompanyContactor", value = "return-lv联系人姓名", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCompanyContactsPhone", value = "return-lv联系方式", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCompanyContactsProxyStatement", value = "return-lv联系人委托书", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCompanyAddress", value = "return-lv企业地址", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCompanyAccountPrice", value = "return-h公司账户金额", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCompanyUserId", value = "return-lv用户id", required = false, dataType = "int"),
    	@ApiImplicitParam(name = "auCompanyVerifyStatus", value = "return-lv审核状态:0-通过,1-未通过", required = false, dataType = "int"),
    	@ApiImplicitParam(name = "auCompanyVerifyRefuse", value = "return-拒绝原因", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCompanyVerifyTime", value = "return-lvul审核时间", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auCompanyReviewStatus", value = "return-lv复审状态:0-通过,1-未通过", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auCompanyReviewRefuse", value = "return-v复审拒绝理由", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auCompanyReviewTime", value = "return-lv复审时间", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auCompanyCreatedTime", value = "return-il创建时间", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auCompanyUpdatedTime", value = "return-u修改时间", required = false, dataType = "Date"),
     })
     @RequestMapping(value = "/allList", method = RequestMethod.POST)
	public ResultBean<List<AuCompany>> allList(@RequestBody HashMap<String,Object> param){
			logger.info("auCompany.allList页面请求参数："+param);
		List<AuCompany> result = baseService.allList(param);
		 return resultBeanFactory.getBean(result);
	}
	
	     
	

}

