package com.zycw.common.handler;

import com.zycw.common.jwt.IJWTInfo;
import com.zycw.common.jwt.JWTHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Inspiry
 * Created on 2021/5/31.
 */
@Slf4j
@Service
public class TokenValidateHandler {
    public IJWTInfo doValidate(String authToken) throws Exception {
        log.info("[TokenValidateHandler] doValidate  token={}", authToken);
        return JWTHelper.getInfoFromToken(authToken, "rsaPublicKey.txt");
    }
}
