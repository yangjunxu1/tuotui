package com.zycw.tuotui.controller.chatfriends;


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
import com.zycw.tuotui.iface.chatfriends.IChatFriendsService;
import com.zycw.tuotui.entity.chatfriends.ChatFriends;
import java.util.Date;
import java.util.List;
import java.util.HashMap;


@ApiOperation(value = "")
@Api(value = "接口", tags = { "接口" })
@Slf4j
@RestController
@RequestMapping("/chatFriends")
public class ChatFriendsController extends BaseController <IChatFriendsService, ChatFriends>{

    private static Logger logger = LoggerFactory.getLogger(ChatFriendsController.class);
       
    		   
    @Autowired
    private ResultBeanFactory resultBeanFactory;

     @ApiOperation(value = "根据id物理删除")
     @ApiImplicitParam(name = "chatFriendsId", value = "ID", required = true, dataType = "String")
     @RequestMapping(value = "/deleteById", method = RequestMethod.GET)
     public ResultBean<String> deleteById(@RequestParam("chatFriendsId") String chatFriendsId) {
     	logger.info("chatFriends.deleteById页面请求参数："+chatFriendsId);
		try {
			baseService.deleteById(chatFriendsId);
			return resultBeanFactory.getBean("删除成功");
		} catch (Exception e) {
			logger.error("失败原因:{}", e);
			return resultBeanFactory.getException(e);
		}
     }

     @ApiOperation(value = "根据id逻辑删除")
     @ApiImplicitParam(name = "chatFriendsId", value = "ID", required = true, dataType = "String")
     @RequestMapping(value = "/deleteByIdLogic", method = RequestMethod.GET)
     public ResultBean<String> deleteByIdLogic(@RequestParam("chatFriendsId") String chatFriendsId) {
		logger.info("chatFriends.deleteByIdLogic页面请求参数："+chatFriendsId);
		try {
			baseService.deleteByIdLogic(chatFriendsId);
			return resultBeanFactory.getBean("删除成功");
		} catch (Exception e) {
			logger.error("失败原因:{}", e);
			return resultBeanFactory.getException(e);
		}
     }
     

     @ApiOperation(value = "根据主键修改")
     @ApiImplicitParam(name = "chatFriends", value = "对象", required = true, dataType = "ChatFriends")
     @RequestMapping(value = "/updateObjById", method = RequestMethod.POST)
     public ResultBean<String> updateObjById(@RequestBody ChatFriends chatFriends) {
         logger.info("chatFriends.updateObjById页面请求参数："+chatFriends);
		try {
			logger.info("chatFriends.update页面请求参数："+chatFriends);
			baseService.updateObjById(chatFriends);
			return resultBeanFactory.getBean("修改成功");
        } catch (Exception e) {
            logger.error("修改失败:{}", e);
            return resultBeanFactory.getException(e);
        }
     }
     
     

     @ApiOperation(value = "新增")
     @ApiImplicitParam(name = "chatFriends", value = "对象", required = true, dataType = "ChatFriends")
     @RequestMapping(value = "/insertObj", method = RequestMethod.POST)
     public ResultBean<String> insertObj(@RequestBody ChatFriends chatFriends) {
		try {
			logger.info("chatFriends.insertObj页面请求参数："+chatFriends);
	        baseService.insert(chatFriends);
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



    	@ApiImplicitParam(name = "chatFriendsAddtime", value = "return-", required = false, dataType = "${dataType}"),
     })
     @RequestMapping(value = "/pageList", method = RequestMethod.POST)
     public ResultBeanFactory.ResultBean<PageInfo<ChatFriends>> pageList(@RequestBody HashMap<String,Object> params) {
		try {
			logger.info("chatFriends.pageList页面请求参数："+params);
            int count = baseService.countNum(params);
            PageInfo<ChatFriends> result=baseService.pageList(params);
            result.setTotal(count);
            return resultBeanFactory.getBean(result);
		} catch (Exception e) {
			logger.error("ChatFriendsController.pageList Exception:{}", e);
			return resultBeanFactory.getException(e);
		}
     }


     @ApiOperation(value = "通过id查询")
     @ApiImplicitParam(name = "chatFriendsId", value = "ID", required = true, dataType = "String")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "chatFriendsId", value = "return-", required = false, dataType = "${dataType}"),
    	@ApiImplicitParam(name = "chatFriendsUserid", value = "return-用户id", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "chatFriendsFuserid", value = "return-好友id", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "chatFriendsAddtime", value = "return-", required = false, dataType = "String"),
		})
     @RequestMapping(value = "/selectObjById", method = RequestMethod.GET)
     public ResultBean<HashMap<String,Object>> info(@RequestParam("chatFriendsId") String chatFriendsId) {
			logger.info("chatFriends.selectObjById页面请求参数："+chatFriendsId);
         HashMap<String,Object> chatFriends = baseService.selectObjById(chatFriendsId);
         return resultBeanFactory.getBean(chatFriends);
     }
     
     
	
     @ApiOperation(value = "allList")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "chatFriendsId", value = "return-", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "chatFriendsUserid", value = "return-用户id", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "chatFriendsFuserid", value = "return-好友id", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "chatFriendsAddtime", value = "return-", required = false, dataType = "String")
     })
     @RequestMapping(value = "/allList", method = RequestMethod.POST)
	public ResultBean<List<ChatFriends>> allList(@RequestBody HashMap<String,Object> param){
			logger.info("chatFriends.allList页面请求参数："+param);
		List<ChatFriends> result = baseService.allList(param);
		 return resultBeanFactory.getBean(result);
	}
	
	     
	

}

