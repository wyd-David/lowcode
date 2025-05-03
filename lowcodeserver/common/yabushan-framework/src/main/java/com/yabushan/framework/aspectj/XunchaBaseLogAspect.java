package com.yabushan.framework.aspectj;

import com.alibaba.fastjson.JSON;
import com.yabushan.common.annotation.BaseLog;
import com.yabushan.common.core.domain.model.LoginUser;
import com.yabushan.common.enums.BusinessStatus;
import com.yabushan.common.enums.HttpMethod;
import com.yabushan.common.utils.ServletUtils;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.common.utils.ip.IpUtils;
import com.yabushan.common.utils.spring.SpringUtils;
import com.yabushan.framework.manager.AsyncManager;
import com.yabushan.framework.manager.factory.AsyncFactory;
import com.yabushan.framework.web.service.TokenService;
import com.yabushan.system.domain.XunchaBaseLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 操作日志记录处理
 *
 * @author ruoyi
 */
@Aspect
@Component
public class XunchaBaseLogAspect {
    private static final Logger log = LoggerFactory.getLogger(XunchaBaseLogAspect.class);

    // 配置织入点
    @Pointcut("@annotation(com.yabushan.common.annotation.BaseLog)")
    public void logPointCut() {
    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "logPointCut()", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Object jsonResult) {
        handleLog(joinPoint, null, jsonResult);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        handleLog(joinPoint, e, null);
    }

    protected void handleLog(final JoinPoint joinPoint, final Exception e, Object jsonResult) {
        try {
            // 获得注解
            BaseLog controllerLog = getAnnotationLog(joinPoint);
            if (controllerLog == null) {
                return;
            }

            // 获取当前的用户
            LoginUser loginUser = null;
            try {
                loginUser = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest());
            } catch (Exception ee) {
                log.error("获取当前用户信息出错！", e.getMessage());
            }

            // *========数据库日志=========*//
            XunchaBaseLog xcLog = new XunchaBaseLog();
            xcLog.setStatus(BusinessStatus.SUCCESS.ordinal());
            // 请求的地址
            String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
            xcLog.setOperIp(ip);
            // 返回参数
            if (null != jsonResult) {
                Integer num = jsonResult.toString().length();
                if (num > 20000) {
                    //长度超过限制，不记录返回结果
                } else {
                    xcLog.setJsonResult(JSON.toJSONString(jsonResult));
                }
            }
            xcLog.setOperUrl(ServletUtils.getRequest().getRequestURI());
            if (loginUser != null) {
                xcLog.setOperName(loginUser.getUsername());
            }

            if (e != null) {
                xcLog.setStatus(BusinessStatus.FAIL.ordinal());
                xcLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
            }
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            xcLog.setMethod(className + "." + methodName + "()");
            // 设置请求方式
            xcLog.setRequestMethod(ServletUtils.getRequest().getMethod());
            // 处理设置注解上的参数
            getControllerMethodDescription(joinPoint, controllerLog, xcLog);
            // 保存数据库
            AsyncManager.me().execute(AsyncFactory.recordOper(xcLog));
        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param log   日志
     * @param xcLog 操作日志
     * @throws Exception
     */
    public void getControllerMethodDescription(JoinPoint joinPoint, BaseLog log, XunchaBaseLog xcLog) throws Exception {
        // 设置action动作
        xcLog.setBusinessType(log.businessType().ordinal());
        // 设置标题
        xcLog.setTitle(log.title());
        // 设置操作人类别
        // xcLog.setOperatorType(log.operatorType().ordinal());
        // 是否需要保存request，参数和值
        if (log.isSaveRequestData()) {
            // 获取参数的信息，传入到数据库中。
            setRequestValue(joinPoint, xcLog);
        }
    }

    /**
     * 获取请求的参数，放到log中
     *
     * @param xcLog 操作日志
     * @throws Exception 异常
     */
    private void setRequestValue(JoinPoint joinPoint, XunchaBaseLog xcLog) throws Exception {
        String requestMethod = xcLog.getRequestMethod();
        if (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod)) {
            String params = argsArrayToString(joinPoint.getArgs());
            xcLog.setOperParam(StringUtils.substring(params, 0, 2000));
        } else {
            Map<?, ?> paramsMap = (Map<?, ?>) ServletUtils.getRequest().getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            xcLog.setOperParam(StringUtils.substring(paramsMap.toString(), 0, 2000));
        }
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private BaseLog getAnnotationLog(JoinPoint joinPoint) throws Exception {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getAnnotation(BaseLog.class);
        }
        return null;
    }

    /**
     * 参数拼装
     */
    private String argsArrayToString(Object[] paramsArray) {
        String params = "";
        if (paramsArray != null && paramsArray.length > 0) {
            for (int i = 0; i < paramsArray.length; i++) {
                if (!isFilterObject(paramsArray[i])) {
                    Object jsonObj = JSON.toJSON(paramsArray[i]);
                    params += jsonObj.toString() + " ";
                }
            }
        }
        return params.trim();
    }

    /**
     * 判断是否需要过滤的对象。
     *
     * @param o 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    public boolean isFilterObject(final Object o) {
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse;
    }
}
