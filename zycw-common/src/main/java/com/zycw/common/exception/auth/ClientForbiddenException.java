package com.zycw.common.exception.auth;


import com.zycw.common.constant.CommonConstants;
import com.zycw.common.exception.BaseException;

/**
 * Title: 客户端不允许访问异常
 * @author   yangjunxu
 * @version  1.0
 * @since    
 * @Date	 2018年4月4日  上午12:29:56	 
 */
public class ClientForbiddenException extends BaseException {
    public ClientForbiddenException(String message) {
        super(message, CommonConstants.EX_CLIENT_FORBIDDEN_CODE);
    }

}
