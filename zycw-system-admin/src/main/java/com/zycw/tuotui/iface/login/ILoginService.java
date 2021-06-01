package com.zycw.tuotui.iface.login;

import com.zycw.common.constant.CommonConstants;
import com.zycw.common.exception.BaseException;
import com.zycw.common.jwt.JWTHelper;
import com.zycw.common.jwt.JWTInfo;
import com.zycw.common.redis.RedisClient;
import com.zycw.common.util.StringUtil;
import com.zycw.tuotui.entity.auuser.AuUser;
import com.zycw.tuotui.iface.auuser.IAuUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @author Inspiry
 * Created on 2021/6/1.
 */
@Service("loginService")
public class ILoginService {
    @Autowired
    private IAuUserService iAuUserService;

    /**
     * 登陆业务
     *
     * @return
     */
    public String login(HashMap<String, Object> params) {
        String auUserMobile = (String) params.get("auUserMobile");
        String auUserUseruuid = (String) params.get("auUserUseruuid");
        String auUserPassword = (String) params.get("auUserPassword");
        if (StringUtil.isEmpty(auUserMobile) || StringUtil.isEmpty(auUserPassword) || StringUtil.isEmpty(auUserUseruuid))
            throw new BaseException("参数错误");
        //1.如果验证码不为null,先校验验证码 TODO 验证码校验
        if (!StringUtil.isEmpty(params.get("code"))) {
            //b 校验手机号是否存在,存在即更新设备信息,不存在新增用户信息
            //a 校验验证码
            List<AuUser> auUsers = iAuUserService.getUserByMobileAndUUiD(params);
            //注册用户新增
            if (Objects.isNull(auUsers) || auUsers.isEmpty()) {
                AuUser auUser = new AuUser();
                auUser.setAuUserMobile(auUserMobile);
                auUser.setAuUserUseruuid(auUserUseruuid);
                auUser.setAuUserPassword(auUserPassword);
                iAuUserService.insert(auUser);
            } else {
                //更新用户设备信息
                AuUser auUser = auUsers.get(0);
                auUser.setAuUserUseruuid((String) params.get("auUserUseruuid"));
                iAuUserService.updateObjById(auUser);
            }
            return generateToken(auUserMobile, auUserUseruuid);
        } else {
            //2.先根据手机号+设备号查询用户信息
            List<AuUser> auUsers = iAuUserService.getUserByMobileAndUUiD(params);
            if (Objects.isNull(auUsers) || auUsers.isEmpty())
                throw new BaseException("登陆失败,用户不存在");
            //3.用户信息存在,校验密码
            if (!auUserPassword.equals(auUsers.get(0).getAuUserPassword()))
                throw new BaseException("登陆失败,密码错误");
            return generateToken(auUserMobile, auUserUseruuid);
        }
    }

    private String generateToken(String uid, String deviceNo) {
        AuUser auUser = new AuUser();
        auUser.setAuUserMobile(uid);
        auUser.setAuUserUseruuid(deviceNo);
        AuUser userInfo = iAuUserService.selectOne(auUser);
        if (Objects.isNull(userInfo)) throw new BaseException("登陆失败,用户不存在");
        JWTInfo jwtInfo = new JWTInfo(userInfo.getAuUserTrueName(), userInfo.getAuUserId() + "", userInfo.getAuUserNick());
        String token = "";
        try {
            token = JWTHelper.generateToken(jwtInfo, "per.key", 30);
        } catch (Exception e) {
            throw new BaseException("登陆失败,token生成异常");
        }
        RedisClient.put(CommonConstants.REDIS_USER_TOKEN_PREFIX + userInfo.getAuUserId(), token);
        return token;
    }
}
