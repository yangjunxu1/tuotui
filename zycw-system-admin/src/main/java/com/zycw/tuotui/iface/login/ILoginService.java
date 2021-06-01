package com.zycw.tuotui.iface.login;

import com.zycw.common.exception.BaseException;
import com.zycw.common.jwt.IJWTInfo;
import com.zycw.common.jwt.JWTHelper;
import com.zycw.common.jwt.JWTInfo;
import com.zycw.common.util.StringUtil;
import com.zycw.tuotui.entity.auuser.AuUser;
import com.zycw.tuotui.readdao.auuser.AuUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author Inspiry
 * Created on 2021/6/1.
 */
@Service("loginService")
public class ILoginService {


    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private AuUserMapper auUserMapper;

    /**
     * 登陆业务
     *
     * @param uid
     * @param password
     * @param code
     * @param deviceNo
     * @return
     */
    public String login(String uid, String password, String code, String deviceNo) {
        //1.如果验证码不为null,先校验验证码 TODO 验证码校验
        if (!StringUtil.isEmpty(code)) {
            //a 校验验证码
            //b 校验手机号是否存在,存在即更新设备信息,不存在新增用户信息
            AuUser auUser = new AuUser();
            auUser.setAuUserMobile(uid);
            auUser = auUserMapper.selectOne(auUser);
            //注册用户新增
            if (Objects.isNull(auUser)) {
                auUser = new AuUser();
                auUser.setAuUserMobile(uid);
                auUser.setAuUserUseruuid(deviceNo);
                auUser.setAuUserPassword(password);
                auUserMapper.insert(auUser);
            } else {
                //更新用户设备信息
                auUser.setAuUserUseruuid(deviceNo);
                auUserMapper.updateObjById(auUser);
            }
            return generateToken(uid, deviceNo);
        } else {
            //2.先根据手机号+设备号查询用户信息
            AuUser auUser = new AuUser();
            auUser.setAuUserMobile(uid);
            auUser.setAuUserUseruuid(deviceNo);
            auUser = auUserMapper.selectOne(auUser);
            if (Objects.isNull(auUser))
                throw new BaseException("登陆失败,用户不存在");
            //3.用户信息存在,校验密码
            if (!auUser.getAuUserPassword().equals(password))
                throw new BaseException("登陆失败,用户不存在");
            return generateToken(uid, deviceNo);
        }
    }

    private String generateToken(String uid, String deviceNo) {
        AuUser auUser = new AuUser();
        auUser.setAuUserMobile(uid);
        auUser.setAuUserUseruuid(deviceNo);
        AuUser userInfo = auUserMapper.selectOne(auUser);
        if (Objects.isNull(userInfo)) throw new BaseException("登陆失败,用户不存在");
        JWTInfo jwtInfo = new JWTInfo(userInfo.getAuUserTrueName(), userInfo.getAuUserId() + "", userInfo.getAuUserNick());
        String token = "";
        try {
            token = JWTHelper.generateToken(jwtInfo, "per.key", 30);
        } catch (Exception e) {
            throw new BaseException("登陆失败,token生成异常");
        }
//        redisTemplate.opsForValue().set();
        return token;
    }
}
