package com.zycw.tuotui.controller.auchatmsg;


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
import com.zycw.tuotui.iface.auchatmsg.IAuChatMsgService;
import com.zycw.tuotui.entity.auchatmsg.AuChatMsg;
import java.util.Date;
import java.util.List;
import java.util.HashMap;


@ApiOperation(value = "客服信息记录")
@Api(value = "客服信息记录接口", tags = { "客服信息记录接口" })
@Slf4j
@RestController
@RequestMapping("/auChatMsg")
public class AuChatMsgController extends BaseController <IAuChatMsgService, AuChatMsg>{

    private static Logger logger = LoggerFactory.getLogger(AuChatMsgController.class);
       
    		   
    @Autowired
    private ResultBeanFactory resultBeanFactory;

     @ApiOperation(value = "根据id物理删除")
     @ApiImplicitParam(name = "auChatMsgId", value = "ID", required = true, dataType = "String")
     @RequestMapping(value = "/deleteById", method = RequestMethod.GET)
     public ResultBean<String> deleteById(@RequestParam("auChatMsgId") String auChatMsgId) {
     	logger.info("auChatMsg.deleteById页面请求参数："+auChatMsgId);
		try {
			baseService.deleteById(auChatMsgId);
			return resultBeanFactory.getBean("删除成功");
		} catch (Exception e) {
			logger.error("失败原因:{}", e);
			return resultBeanFactory.getException(e);
		}
     }

     @ApiOperation(value = "根据id逻辑删除")
     @ApiImplicitParam(name = "auChatMsgId", value = "ID", required = true, dataType = "String")
     @RequestMapping(value = "/deleteByIdLogic", method = RequestMethod.GET)
     public ResultBean<String> deleteByIdLogic(@RequestParam("auChatMsgId") String auChatMsgId) {
		logger.info("auChatMsg.deleteByIdLogic页面请求参数："+auChatMsgId);
		try {
			baseService.deleteByIdLogic(auChatMsgId);
			return resultBeanFactory.getBean("删除成功");
		} catch (Exception e) {
			logger.error("失败原因:{}", e);
			return resultBeanFactory.getException(e);
		}
     }
     

     @ApiOperation(value = "根据主键修改")
     @ApiImplicitParam(name = "auChatMsg", value = "客服信息记录对象", required = true, dataType = "AuChatMsg")
     @RequestMapping(value = "/updateObjById", method = RequestMethod.POST)
     public ResultBean<String> updateObjById(@RequestBody AuChatMsg auChatMsg) {
         logger.info("auChatMsg.updateObjById页面请求参数："+auChatMsg);
		try {
			auChatMsg.setAuChatMsgSendtime(new Date());
			logger.info("auChatMsg.update页面请求参数："+auChatMsg);
			baseService.updateObjById(auChatMsg);
			return resultBeanFactory.getBean("修改成功");
        } catch (Exception e) {
            logger.error("修改失败:{}", e);
            return resultBeanFactory.getException(e);
        }
     }
     
     

     @ApiOperation(value = "新增")
     @ApiImplicitParam(name = "auChatMsg", value = "客服信息记录对象", required = true, dataType = "AuChatMsg")
     @RequestMapping(value = "/insertObj", method = RequestMethod.POST)
     public ResultBean<String> insertObj(@RequestBody AuChatMsg auChatMsg) {
		try {
			logger.info("auChatMsg.insertObj页面请求参数："+auChatMsg);
			auChatMsg.setAuChatMsgSendtime(new Date());
	        baseService.insert(auChatMsg);
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





    	@ApiImplicitParam(name = "auChatMsgMsgtype", value = "return-消息类型", required = false, dataType = "String"),
     })
     @RequestMapping(value = "/pageList", method = RequestMethod.POST)
     public ResultBeanFactory.ResultBean<PageInfo<AuChatMsg>> pageList(@RequestBody HashMap<String,Object> params) {
		try {
			logger.info("auChatMsg.pageList页面请求参数："+params);
            int count = baseService.countNum(params);
            PageInfo<AuChatMsg> result=baseService.pageList(params);
            result.setTotal(count);
            return resultBeanFactory.getBean(result);
		} catch (Exception e) {
			logger.error("AuChatMsgController.pageList Exception:{}", e);
			return resultBeanFactory.getException(e);
		}
     }


     @ApiOperation(value = "通过id查询")
     @ApiImplicitParam(name = "auChatMsgId", value = "ID", required = true, dataType = "String")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "auChatMsgId", value = "return-ID", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auChatMsgSenduserid", value = "return-lv用户", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auChatMsgReciveuserid", value = "return-lv客服", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auChatMsgSendtime", value = "return-lvi聊天时间", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auChatMsgSendtext", value = "return-vl聊天内容", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auChatMsgMsgtype", value = "return-消息类型", required = false, dataType = "String"),
		})
     @RequestMapping(value = "/selectObjById", method = RequestMethod.GET)
     public ResultBean<HashMap<String,Object>> info(@RequestParam("auChatMsgId") String auChatMsgId) {
			logger.info("auChatMsg.selectObjById页面请求参数："+auChatMsgId);
         HashMap<String,Object> auChatMsg = baseService.selectObjById(auChatMsgId);
         return resultBeanFactory.getBean(auChatMsg);
     }
     
     
	
     @ApiOperation(value = "allList")
     @ApiImplicitParams({
    	@ApiImplicitParam(name = "auChatMsgId", value = "return-ID", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auChatMsgSenduserid", value = "return-lv用户", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auChatMsgReciveuserid", value = "return-lv客服", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auChatMsgSendtime", value = "return-lvi聊天时间", required = false, dataType = "Date"),
    	@ApiImplicitParam(name = "auChatMsgSendtext", value = "return-vl聊天内容", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "auChatMsgMsgtype", value = "return-消息类型", required = false, dataType = "String"),
     })
     @RequestMapping(value = "/allList", method = RequestMethod.POST)
	public ResultBean<List<AuChatMsg>> allList(@RequestBody HashMap<String,Object> param){
			logger.info("auChatMsg.allList页面请求参数："+param);
		List<AuChatMsg> result = baseService.allList(param);
		 return resultBeanFactory.getBean(result);
	}
	
	     
	

}

