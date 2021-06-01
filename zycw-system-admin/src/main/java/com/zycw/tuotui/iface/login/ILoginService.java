package com.zycw.tuotui.iface.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author Inspiry
 * Created on 2021/6/1.
 */
@Service("loginService")
public class ILoginService {


    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 登陆业务
     *
     * @param uid
     * @param password
     * @param code
     * @param type
     * @param deviceNo
     * @return
     */
    public String login(String uid, String password, String code, Integer type, String deviceNo) {


        //1.如果验证码不为null,先校验验证码 TODO 验证码校验


        //2.先根据手机号+设备号查询用户信息
        return "";


    }
}
