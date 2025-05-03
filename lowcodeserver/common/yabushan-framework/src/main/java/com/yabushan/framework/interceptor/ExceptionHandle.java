package com.yabushan.framework.interceptor;

import com.yabushan.common.core.domain.AjaxResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by 姜森焱 on 2021/12/23 9:53
 * 拦截异常
 */
@ControllerAdvice
public class ExceptionHandle {
    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);
    @ExceptionHandler(value = DataIntegrityViolationException.class)
    @ResponseBody
    public AjaxResult Handle(DataIntegrityViolationException e){
        //判断数据库字段长度过长异常拦截
        if(e instanceof DataIntegrityViolationException){
            if(e.getMessage().contains("Data too long")) {
                logger.error(e.getMessage());
                return AjaxResult.error("网络繁忙，请稍后重试！");
            }
        }else{
            logger.error(e.getMessage());
            return AjaxResult.error("网络繁忙，请稍后重试！");
        }
        return AjaxResult.error("网络繁忙，请稍后重试！");
    }
}
