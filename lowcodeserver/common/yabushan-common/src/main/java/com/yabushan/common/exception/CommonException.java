package com.yabushan.common.exception;

import java.util.List;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;


public class CommonException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = -8655083457836824829L;

    private static final Logger logger = LogManager.getLogger(CommonException.class);

    private Integer errorCode;

    private String errorMsg;

    public CommonException(Integer errorCode) {
        super(CommonErrorCode.errorMsgMap.get(errorCode));
        setErrorCode(errorCode);
        setErrorMsg(super.getMessage());

    }

    public CommonException(Integer errorCode, String errorMsg) {
        super(errorMsg);
        setErrorCode(errorCode);
        setErrorMsg(super.getMessage());
    }


    public CommonException(Integer errorCode, List errorObjs) {
        super(proccessErrorMsg(errorCode, CommonErrorCode.errorMsgMap.get(errorCode), errorObjs));
        setErrorCode(errorCode);
        setErrorMsg(super.getMessage());

    }

    public CommonException(Integer errorCode, String errorMsg, List<Object> errorObjs) {
        super(proccessErrorMsg(errorCode, errorMsg, errorObjs));
        setErrorCode(errorCode);
        setErrorMsg(super.getMessage());

    }

    public static String proccessErrorMsg(Integer errorCode, String errorMsg, List<Object> errorObjs) {
        String logMsg = CommonErrorCode.logMsgMap.get(errorCode);
        if (logMsg == null) {
            logMsg = "";
        }
        if (!CollectionUtils.isEmpty(errorObjs)) {
            for (int i = 0; i < errorObjs.size(); i++) {
                errorMsg = errorMsg.replace("{" + i + "}", errorObjs.get(i).toString());
                logMsg = logMsg.replace("{" + i + "}", errorObjs.get(i).toString());
            }
        }
        if (StringUtils.isEmpty(logMsg)) {
            logger.error(logMsg);
        }
        return errorMsg;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

}
