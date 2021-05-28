package com.zycw.common.exception.auth;


import com.zycw.common.constant.CommonConstants;
import com.zycw.common.exception.BaseException;

/**
 * Title: 用户Token异常
 * @author   yangjunxu
 * @version  1.0
 * @since    
 * @Date	 2018年4月4日  上午12:29:56	 
 */
public class UserTokenException extends BaseException {
    public UserTokenException(String message) {
        super(message, CommonConstants.EX_USER_INVALID_CODE);
    }
}
