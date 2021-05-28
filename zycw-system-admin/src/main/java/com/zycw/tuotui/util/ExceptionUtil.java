package com.zycw.tuotui.util;

import com.zycw.common.exception.RuleVerifiRuntimeException;

public class ExceptionUtil {

    /**
     * 生成异常
     * 
     * @param errorMessage
     */
    public static void runTimeException(String errorMessage) {
        throw new RuleVerifiRuntimeException(errorMessage);
    }
}
