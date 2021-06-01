package com.zycw.tuotui.controller.login;

import com.zycw.common.util.wxwUtil.ResultBeanFactory;
import com.zycw.tuotui.controller.sysadvert.SysAdvertController;
import com.zycw.tuotui.iface.login.ILoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Inspiry
 * Created on 2021/6/1.
 */
@ApiOperation(value = "登陆相关接口")
@Api(value = "登陆接口", tags = {"登陆接口"})
@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {

    private static Logger logger = LoggerFactory.getLogger(SysAdvertController.class);


    @Autowired
    private ResultBeanFactory resultBeanFactory;

    @Autowired
    private ILoginService loginService;

    @PostMapping(value = "/app/doLogin")
    @ApiOperation(value = "登陆注册", notes = "app登陆注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid", value = "账号/手机号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "code", value = "密码/验证码", required = false, dataType = "String"),
            @ApiImplicitParam(name = "deviceNo", value = "设备编号", required = true)
    })
    public ResultBeanFactory.ResultBean<String> login(String uid, String password, String code, String deviceNo) {
        logger.info("[LoginController] uid:" + uid + "code:" + code + "deviceNo:" + deviceNo);
        try {
            String token = loginService.login(uid, password, code, deviceNo);
            return resultBeanFactory.getBean(token);
        } catch (Exception e) {
            logger.error("失败原因:{}", e);
            return resultBeanFactory.getException(e);
        }
    }

}
