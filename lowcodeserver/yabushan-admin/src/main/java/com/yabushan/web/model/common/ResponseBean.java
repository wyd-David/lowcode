package com.yabushan.web.model.common;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.http.HttpStatus;

/**
 * ResponseBean
 * @author shenxuefa
 * @date 2018/8/30 11:39
 */
@ApiModel(description = "API响应信息")
public class ResponseBean<T> {
    /**
     * HTTP状态码
     */
    @ApiModelProperty(value = "返回编码", required = true)
    public Integer code;

    /**
     * 返回信息
     */
    @ApiModelProperty(value = "提示信息", required = false)
    public String msg;

    /**
     * 返回代码错误信息
     */
    public String errorMsg;



    /**
     * 返回的数据
     */
    @ApiModelProperty(value = "返回数据", required = false)
    public T data;

    protected ResponseBean(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    protected ResponseBean(int code, String msg,String errorMsg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.errorMsg=errorMsg;
    }
    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> ResponseBean<T> success(T data) {
        return new ResponseBean<T>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), data);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     * @param  message 提示信息
     */
    public static <T> ResponseBean<T> success(String message,T data) {
        return new ResponseBean<T>(ResponseCode.SUCCESS.getCode(), message, data);
    }
    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     * @param  message 提示信息
     */
    public static <T> ResponseBean<T> success(IResponseCode responseCode, String message,T data) {
        return new ResponseBean<T>(ResponseCode.SUCCESS.getCode(), message, data);
    }

    /**
     * 失败返回结果
     * @param responseCode 响应码
     */
    public static <T> ResponseBean<T> fail(IResponseCode responseCode) {
        return new ResponseBean<T>(responseCode.getCode(), responseCode.getMessage(), null);
    }

    /**
     * 失败返回结果
     * @param responseCode 响应码
     * @param message 响应信息
     */
    public static <T> ResponseBean<T> fail(IResponseCode responseCode, String message,T data) {
        return new ResponseBean<T>(responseCode.getCode(), message, data);
    }
    /**
     * 失败返回结果
     * @param responseCode 响应码
     * @param message 响应信息
     */
    public static <T> ResponseBean<T> fail(IResponseCode responseCode, String message) {
        return new ResponseBean<T>(responseCode.getCode(), message, null);
    }

    /**
     * 失败返回结果
     * @param message 提示信息
     */
    public static <T> ResponseBean<T> fail(String message) {
        return new ResponseBean<T>(ResponseCode.FAIL.getCode(), message, null);
    }
    /**
     * 失败返回结果
     * @param message 提示信息
     * @param data 数据
     */
    public static <T> ResponseBean<T> fail(String message,T data) {
        return new ResponseBean<T>(ResponseCode.FAIL.getCode(), message, data);
    }

    /**
     * 失败返回结果
     */
    public static <T> ResponseBean<T> fail() {
        return fail(ResponseCode.FAIL);
    }

    /**
     * 失败返回结果
     * @param message 用户提示信息
     * @param  errorMsg 代码错误提示
     */
    public static <T> ResponseBean<T> fail(String message,String errorMsg) {
        return new ResponseBean<T>(ResponseCode.FAIL.getCode(), message,errorMsg, null);
    }

    /**
     * 未登录返回结果
     */
    public static <T> ResponseBean<T> unauthorized(String message,T data) {
        return new ResponseBean<T>(ResponseCode.UNAUTHORIZED.getCode(), message, data);
    }
 /**
     * 未登录返回结果
     */
    public static <T> ResponseBean<T> unauthorized(T data) {
        return new ResponseBean<T>(ResponseCode.UNAUTHORIZED.getCode(), ResponseCode.UNAUTHORIZED.getMessage(), data);
    }

    /**
     * 未授权返回结果
     */
    public static <T> ResponseBean<T> forbidden(T data) {
        return new ResponseBean<T>(ResponseCode.FORBIDDEN.getCode(), ResponseCode.FORBIDDEN.getMessage(), data);
    }

    /**
     * 自定义http请求返回
     * @param httpStatus
     * @param message
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseBean<T> httpResult(HttpStatus httpStatus, String message, T data) {
        return new ResponseBean<T>(httpStatus.value(), message, data);
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
