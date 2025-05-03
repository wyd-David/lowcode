package com.yabushan.web.controller.data;

import java.io.Serializable;

/**
 * @Author yabushan
 * @Date 2021/3/28 19:15
 * @Version 1.0
 */
public class RequstData implements Serializable {


    private static final long serialVersionUID = 6806753651047762735L;
    private String ApiCode;
    private String param;


    public String getApiCode() {
        return ApiCode;
    }

    public void setApiCode(String apiCode) {
        ApiCode = apiCode;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}
