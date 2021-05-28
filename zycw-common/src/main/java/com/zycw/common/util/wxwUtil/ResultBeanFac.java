package com.zycw.common.util.wxwUtil;

import java.io.Serializable;

import lombok.Data;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
@Data
@Component
public class ResultBeanFac implements Serializable {

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

        public ResultBean(T data) {
            this.setData(data);
        }

        public ResultBean(T data,T other){
            this.setData(data);
            this.setOther(other);
        }



        public ResultBean(int code, String msg, T data) {
            this.code = code;
            this.msg = msg;
            this.setData(data);
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
    public <T> ResultBean<T> getBean(int code, String msg, T data) {
        return new ResultBean<T>(code, msg, data);

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
        return new ResultBean<T>(code, msg, null);

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

    public <T> ResultBean<T> getBean(T data) {
        return new ResultBean<T>(data);
    }

    public <T> ResultBean<T> getBean(T data,T other){
        return  new ResultBean<>(data,other);
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

}
