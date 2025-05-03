package com.yabushan.framework.web.service;

import javax.annotation.Resource;

import com.yabushan.common.core.domain.entity.SysDept;
import com.yabushan.common.exception.user.CaptchaException;
import com.yabushan.common.exception.user.CaptchaExpireException;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.system.service.ISysConfigService;
import com.yabushan.system.service.ISysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.yabushan.common.constant.Constants;
import com.yabushan.common.core.domain.model.LoginUser;
import com.yabushan.common.core.redis.RedisCache;
import com.yabushan.common.exception.CustomException;
import com.yabushan.common.exception.user.UserPasswordNotMatchException;
import com.yabushan.common.utils.MessageUtils;
import com.yabushan.framework.manager.AsyncManager;
import com.yabushan.framework.manager.factory.AsyncFactory;

import java.util.Arrays;
import java.util.List;

/**
 * 登录校验方法
 *
 * @author ruoyi
 */
@Component
public class SysLoginService
{
    @Autowired
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private ISysConfigService iSysConfigService;

    @Autowired
    private ISysDeptService sysDeptService;

    @Autowired
    private RedisCache redisCache;
    @Value("${yzmCode.code}")
    private String yzmCode;



    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @param code 验证码
     * @param uuid 唯一标识
     * @return 结果
     */
    public String login(String username, String password, String code, String uuid)
    {
        //获取系统配置参数判断允许哪些部门的员工可以登录
      //  String loginDep = iSysConfigService.selectConfigByKey("login_dep");
        // 去掉验证码验证
        if(!yzmCode.equals(code) && !"Dig".equals(uuid)) {
            //验证码
            String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
            String captcha = redisCache.getCacheObject(verifyKey);
            redisCache.deleteObject(verifyKey);
            if (captcha == null) {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire")));
                throw new CaptchaExpireException();
            }
            if (!code.equalsIgnoreCase(captcha)) {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
                throw new CaptchaException();
            }
        }
        // 用户验证
        Authentication authentication = null;
        try
        {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        }
        catch (Exception e)
        {
            if (e instanceof BadCredentialsException)
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
                throw new UserPasswordNotMatchException();
            }
            else
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
                throw new CustomException(e.getMessage());
            }
        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 生成token
        return tokenService.createToken(loginUser);
        /*if(isLoginDep(loginUser.getUser().getDeptId(),loginDep)){
            return tokenService.createToken(loginUser);
        }else {
            throw new CustomException("暂无权限登录,请联系管理员");
        }*/

    }

    public boolean isLoginDep(Long depId,String loginDep){
        SysDept dept = sysDeptService.selectDeptById(depId);
        if(StringUtils.isNotEmpty(loginDep)){
            List<String> depList = Arrays.asList(loginDep.split(","));
            boolean isParent = depList.stream().anyMatch(list -> list.contains(String.valueOf(depId)));
            if(isParent){
                return isParent;
            }else {
                if(dept != null){
                    for(String key : depList){
                        boolean isDep = depList.stream().anyMatch(list -> dept.getAncestors().contains(key));
                        if(isDep){
                            return true;
                        }
                    }
                    return false;
                }else{
                    //新员工入职没有部门，允许登录填写入职资料
                    return true;
                }
            }
        }else {
            return true;
        }

    }

    /**
     * APP登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @return 结果
     */
    public String AppLogin(String username, String password)
    {
      
        // 用户验证
        Authentication authentication = null;
        try
        {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        }
        catch (Exception e)
        {
            if (e instanceof BadCredentialsException)
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
                throw new UserPasswordNotMatchException();
            }
            else
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
                throw new CustomException(e.getMessage());
            }
        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 生成token
        return tokenService.createToken(loginUser);
        /*if(isLoginDep(loginUser.getUser().getDeptId(),loginDep)){
            return tokenService.createToken(loginUser);
        }else {
            throw new CustomException("暂无权限登录,请联系管理员");
        }*/

    }
}
