package com.zycw.tuotui.util;

import org.slf4j.Logger;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

/**
 * 参数校验类
 * 
 * @date 2018.04.11
 */
public class ValidationUtil {

	/**
	 * Controller层Validation统一校验方法1(使用MessageFormat)
	 * 
	 * @param bindingResult
	 *            校验结果类
	 * @param logger
	 *            当前Controller层Logger对象
	 * @param loggerPrintMessage
	 *            要打印的MessageFormat模板
	 * @param param
	 *            MessageFormat的参数
	 */
	public static void validationHandler(BindingResult bindingResult, Logger logger, String loggerPrintMessage,
                                         Object... param) {
		if (bindingResult.hasErrors()) {
			List<ObjectError> errorList = bindingResult.getAllErrors();
			param = Arrays.copyOf(param, param.length + 1);
			param[param.length - 1] = errorList.get(0).getDefaultMessage();

			/*
			 * 组合报错信息到Message中
			 */
			String errorMessage = MessageFormat.format(loggerPrintMessage, param);
			logger.warn(errorMessage);
			ExceptionUtil.runTimeException(errorList.get(0).getDefaultMessage());
		}
	}

	/**
	 * Controller层Validation统一校验方法2(不使用MessageFormat)
	 * 
	 * @param bindingResult
	 *            校验结果类
	 * @param logger
	 *            当前Controller层Logger对象
	 * @param loggerPrintMessage
	 *            要打印的Message
	 */
	public static void validationHandler(BindingResult bindingResult, Logger logger, String loggerPrintMessage) {
		if (bindingResult.hasErrors()) {
			List<ObjectError> errorList = bindingResult.getAllErrors();
			logger.warn(loggerPrintMessage);
			ExceptionUtil.runTimeException(errorList.get(0).getDefaultMessage());
		}
	}
}
