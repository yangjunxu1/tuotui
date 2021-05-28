package com.zycw.common.util.wxwUtil;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;


@Component
@JsonInclude(JsonInclude.Include.ALWAYS)
public class ResultBeanFactoryMap implements Serializable {

	private static final long serialVersionUID = 7131940554768712753L;

	public static final int SUCCESS = 0;

	public static final int FAIL = 1;

	public static final int NO_PERMISSION = 2;

	public static class ResultBean<T> implements Serializable {

		private static final long serialVersionUID = 1L;
		
		private String msg = "success";
		
		private int code = SUCCESS;
		
		@JsonInclude(JsonInclude.Include.ALWAYS)
		private T data;
		@JsonInclude(JsonInclude.Include.ALWAYS)
		private T other;

		public ResultBean() {

		}

		public ResultBean(T data,T other) {
			this.setData(data);
			this.setOther(other);
		}

		public ResultBean(int code, String msg, T data,T other) {
			this.code = code;
			this.msg = msg;
			this.setData(data);
			this.setOther(other);
		}

		public ResultBean(Throwable e) {
			this.msg = e.getMessage();
			this.code = FAIL;
		}

		public ResultBean(int code, Throwable e) {
			this.code = code;
			this.msg = e.getMessage();
		}

		public T getData() {
			return data;
		}

		public void setData(T data) {
			this.data = data;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public T getOther() {
			return other;
		}

		public void setOther(T other) {
			this.other = other;
		}
	}

	/**
	 * @param code
	 *            0或1 0是成功 1是失败
	 * @param msg
	 *            返回信息
	 * @param data
	 *            返回数据对象
	 * @return
	 */
	public <T> ResultBean<T> getBean(int code, String msg, T data,T other) {
		return new ResultBean<T>(code, msg, data,other);

	}

	/**
	 * @param code
	 *            0或1 0是成功 1是失败
	 * @param msg
	 *            返回信息
	 * @param
	 *
	 * @return
	 */
	public <T> ResultBean<T> getBean(int code, String msg) {
		return new ResultBean<T>(code, msg, null,null);

	}

	/**
	 * 返回值为空的成功请求
	 * 
	 * @param <T>
	 * @param <T>
	 * 
	 * @return
	 */
	public <T> ResultBean<T> getBean() {
		return new ResultBean<T>();
	}

	public <T> ResultBean<T> getBean(T data,T other) {
		return new ResultBean<T>(data,other);
	}

	public <T> ResultBean<T> getException(Throwable e) {
		return new ResultBean<T>(e);
	}

	/**
	 * 
	 * @param msg
	 * @return
	 */
	public <T> ResultBean<T> getException(String msg) {
		return new ResultBean<T>(new RuntimeException(msg));
	}

	public <T> ResultBean<T> getException(int code, String msg) {
		return new ResultBean<T>(code, new RuntimeException(msg));
	}

	/**
	 *
	 *  将对象属性的null值转换为""
	 *  @param obj
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object checkNull(Object obj) {
		Class clazz = obj.getClass();
		// 获取实体类的所有属性，返回Field数组
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (Modifier.isStatic(field.getModifiers())) {
				continue;
			}
			// 可访问私有变量
			field.setAccessible(true);
			// 获取属性类型
			String type = field.getGenericType().toString();
			// 如果type是类类型，则前面包含"class "，后面跟类名
			if ("class java.lang.String".equals(type)) {
				// 将属性的首字母大写
				String methodName = field.getName().replaceFirst(field.getName().substring(0, 1),
						field.getName().substring(0, 1).toUpperCase());
				//System.out.println(methodName);
				try {
					Method methodGet = clazz.getMethod("get" + methodName);
					// 调用getter方法获取属性值
					String str = (String) methodGet.invoke(obj);
					if (StringUtils.isBlank(str)) {
						// Method methodSet = clazz.getMethod("set" +
						// methodName, new Class[] { String.class });
						// methodSet.invoke(o, new Object[] { "" });
						//System.out.println(field.getType()); // class java.lang.String
						// 如果为null的String类型的属性则重新复制为空字符串
						field.set(obj, field.getType().getConstructor(field.getType()).newInstance(""));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return obj;
	}

}
