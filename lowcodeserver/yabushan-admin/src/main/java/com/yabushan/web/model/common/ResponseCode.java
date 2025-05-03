package com.yabushan.web.model.common;

/**
 * 响应码
 * @author liuy
 * @date 2020/9/8 14:01
 */
public enum ResponseCode implements IResponseCode{
    FAIL(-1, "操作失败"),
    SUCCESS(200, "操作成功"),
    BAD_REQUEST(400, "请求失败"),
    NOT_FOUND(404, "请求不存在"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "没有相关权限"),
    INTERNAL_SERVER_ERROR(500, "内部错误");

    private Integer code;
    private String message;

    private ResponseCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
