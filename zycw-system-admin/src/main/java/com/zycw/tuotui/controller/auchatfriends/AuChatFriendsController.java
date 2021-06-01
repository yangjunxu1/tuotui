package com.zycw.tuotui.controller.auchatfriends;


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
import com.zycw.tuotui.iface.auchatfriends.IAuChatFriendsService;
import com.zycw.tuotui.entity.auchatfriends.AuChatFriends;
import java.util.Date;
import java.util.List;
import java.util.HashMap;


@ApiOperation(value = "")
@Api(value = "接口", tags = { "接口" })
@Slf4j
@RestController
@RequestMapping("/auChatFriends")
public class AuChatFriendsController extends BaseController <IAuChatFriendsService, AuChatFriends>{

    private static Logger logger = LoggerFactory.getLogger(AuChatFriendsController.class);
       
    		   
    @Autowired
    private ResultBeanFactory resultBeanFactory;

     @ApiOperation(value = "根据id物理删除")
     @ApiImplicitParam(name = "auChatFriendsId", value = "ID", required = true, dataType = "String")
     @RequestMapping(value = "/deleteById", method = RequestMethod.GET)
     public ResultBean<String> deleteById(@RequestParam("auChatFriendsId") String auChatFriendsId) {
     	logger.info("auChatFriends.deleteById页面请求参数："+auChatFriendsId);
		try {
			baseService.deleteById(auChatFriendsId);
			return resultBeanFactory.getBean("删除成功");
		} catch (Exception e) {
			logger.error("失败原因:{}", e);
			return resultBeanFactory.getException(e);
		}
     }

     @ApiOperation(value = "根据id逻辑删除")
     @ApiImplicitParam(name = "auChatFriendsId", value = "ID", required = true, dataType = "String")
     @RequestMapping(value = "/deleteByIdLogic", method = RequestMethod.GET)
     public ResultBean<String> deleteByIdLogic(@RequestParam("auChatFriendsId") String auChatFriendsId) {
		logger.info("auChatFriends.deleteByIdLogic页面请求参数："+auChatFriendsId);
		try {
			baseService.deleteByIdLogic(auChatFriendsId);
			return resultBeanFactory.getBean("删除成功");
		} catch (Exception e) {
			logger.error("失败原因:{}", e);
			return resultBeanFactory.getException(e);
		}
     }
     

     @ApiOperation(value = "根据主键修改")
     @ApiImplicitParam(name = "auChatFriends", value = "对象", required = true, dataType = "AuChatFriends")
     @RequestMapping(value = "/updateObjById", method = RequestMethod.POST)
     public ResultBean<String> updateObjById(@RequestBody AuChatFriends auChatFriends) {
         logger.info("auChatFriends.updateObjById页面请求参数："+auChatFriends);
		try {
			auChatFriends.setAuChatFriendsAddtime(new Date());
			logger.info("auChatFriends.update页面请求参数："+auChatFriends);
			baseService.updateObjById(auChatFriends);
			return resultBeanFactory.getBean("修改成功");
        } catch (Exception e) {
            logger.error("修改失败:{}", e);
            return resultBeanFactory.getException(e);
        }
     }
     
     

     @ApiOperation(value = "新增")
     @ApiImplicitParam(name = "auChatFriends", value = "对象", required = true, dataType = "AuChatFriends")
     @RequestMapping(value = "/insertObj", method = RequestMethod.POST)
     public ResultBean<String> insertObj(@RequestBody AuChatFriends auChatFriends) {
		try {
			logger.info("auChatFriends.insertObj页面请求参数："+auChatFriends);
			auChatFriends.setAuChatFriendsAddtime(new Date());
	        baseService.insert(auChatFriends);
	        return resultBeanFactory.getBean("插入记录成功");
        } catch (Exception e) {
            logger.error("插入记录失败:{}", e);
            return resultBeanFactory.getException(e);
        }
     }
     
     @ApiOperation(value = "分页查询")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "auChatFriendsUserId", value = "用户", required = false, dataType = "${dataType}"),
    	@ApiImplicitParam(name = "auChatFriendsFuserId", value = "好友", required = false, dataType = "${dataType}"),
		@ApiImplicitParam(name = "pageSize", value = "每页条数", required = false, dataType = "int"),
		@ApiImplicitParam(name = "pageNum", value = "当前页数", required = false, dataType = "int"),




    	@ApiImplicitParam(name = "auChatFriendsDelFlag", value = "return-逻辑删除:0-未删除,1-已删除", required = false, dataType = "${dataType}"),
     })
     @RequestMapping(value = "/pageList", method = RequestMethod.POST)
     public ResultBeanFactory.ResultBean<PageInfo<AuChatFriends>> pageList(@RequestBody HashMap<String,Object> params) {
		try {
			logger.info("auChatFriends.pageList页面请求参数："+params);
            int count = baseService.countNum(params);
            PageInfo<AuChatFriends> result=baseService.pageList(params);
            result.setTotal(count);
            return resultBeanFactory.getBean(result);
		} catch (Exception e) {
			logger.error("AuChatFriendsController.pageList Exception:{}", e);
			return resultBeanFactory.getException(e);
		}
     }


     @ApiOperation(value = "通过id查询")
     @ApiImplicitParam(name = "auChatFriendsId", value = "ID", required = true, dataType = "String")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "auChatFriendsId", value = "return-ID", required = false, dataType = "${dataType}"),
    	@ApiImplicitParam(name = "auUserMobile", value = "return-lvM手机号", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysUserUsername", value = "return-lvM用户名", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auChatFriendsAddtime", value = "return-i添加时间", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auChatFriendsDelFlag", value = "return-逻辑删除:0-未删除,1-已删除", required = false, dataType = "Date"),
		})
     @RequestMapping(value = "/selectObjById", method = RequestMethod.GET)
     public ResultBean<HashMap<String,Object>> info(@RequestParam("auChatFriendsId") String auChatFriendsId) {
			logger.info("auChatFriends.selectObjById页面请求参数："+auChatFriendsId);
         HashMap<String,Object> auChatFriends = baseService.selectObjById(auChatFriendsId);
         return resultBeanFactory.getBean(auChatFriends);
     }
     
     
	
     @ApiOperation(value = "allList")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "auChatFriendsUserId", value = "用户", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auChatFriendsFuserId", value = "好友", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auChatFriendsId", value = "return-ID", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auUserMobile", value = "return-lvM手机号", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "sysUserUsername", value = "return-lvM用户名", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auChatFriendsAddtime", value = "return-i添加时间", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auChatFriendsDelFlag", value = "return-逻辑删除:0-未删除,1-已删除", required = false, dataType = "Date")
     })
     @RequestMapping(value = "/allList", method = RequestMethod.POST)
	public ResultBean<List<AuChatFriends>> allList(@RequestBody HashMap<String,Object> param){
			logger.info("auChatFriends.allList页面请求参数："+param);
		List<AuChatFriends> result = baseService.allList(param);
		 return resultBeanFactory.getBean(result);
	}
	
	     
	

}

