package com.yabushan.web.controller.system;

import com.yabushan.common.annotation.Log;
import com.yabushan.common.constant.Constants;
import com.yabushan.common.core.domain.AjaxResult;
import com.yabushan.common.core.domain.entity.SysUser;
import com.yabushan.common.core.domain.model.LoginUser;
import com.yabushan.common.enums.BusinessType;
import com.yabushan.common.exception.CustomException;
import com.yabushan.common.utils.MessageUtils;
import com.yabushan.framework.manager.AsyncManager;
import com.yabushan.framework.manager.factory.AsyncFactory;
import com.yabushan.framework.web.service.SysPermissionService;
import com.yabushan.framework.web.service.TokenService;
import com.yabushan.framework.web.service.UserDetailsServiceImpl;
import com.yabushan.system.portal.types.UIPService.AuthResult;
import com.yabushan.system.portal.types.UIPService.UserInfo;
import com.yabushan.system.portal.wsdl.UIPService.UIPServiceIFProxy;
import com.yabushan.system.service.ISysUserService;
import com.yabushan.web.model.vo.SsoToken;
import com.yabushan.web.service.OAuthLoginService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.rmi.RemoteException;
@Api(tags = "SS0登录", value = "SS0登录")
@RestController
@Slf4j
public class SsoLoginController {




    @Value("${gmcc.SYSTEMID}")
    private String SYSTEMID;

    @Value("${gmcc.SYSTEMACCOUNT}")
    private String SYSTEMACCOUNT;

    @Value("${gmcc.SYSTEMPWD}")
    private String SYSTEMPWD;

    @Value("${gmcc.SOAPURL}")
    private String SOAPURL;

    @Autowired
    OAuthLoginService oAuthLoginService;

    @Autowired
    private ISysUserService iSysUserService;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private SysPermissionService permissionService;

    /**
     * 单点登陆
     */
    @PostMapping("/gmcc/ssoLogin")
    @ResponseBody
    public AjaxResult  ssoLogin(@RequestBody SsoToken ssoToken) {
        UIPServiceIFProxy proxy = new UIPServiceIFProxy();
        AjaxResult ajax = AjaxResult.success();
        String queryUser=null;
        try {
            //参数1 当前登录PORTAL用户的令牌,该值可以从request.getCookie()上遍历到，该值的名字为：iPlanetDirectoryPro
            //参数2 为portal分配的系统ID，该值需要提接入申请单给PORTAL进行备案
            AuthResult result = proxy.getUIPServiceIF().validateToken(ssoToken.getiPlanetDirectoryPro(), SYSTEMID);//验证portal令牌
            if(result.isAuthResult()){//令牌验证成功
                 queryUser = result.getAccount();
                //获取用户详细信息
                UserInfo info = proxy.getUIPServiceIF().getUserInfoByUserID(queryUser, SYSTEMACCOUNT, SYSTEMPWD);
                log.info("token校验成功，用户账号为：" + queryUser + "，手机号：" + info.getMobilePhone());

                //根据账号查询pc端的用户表
                SysUser sysUser = iSysUserService.selectUserByUserName(queryUser);
                //传入账号角色
                LoginUser loginUser = new LoginUser(sysUser, permissionService.getMenuPermission(sysUser));

                userDetailsService.loadUserByUsername(sysUser.getUserName());
                //创建token
                String token = tokenService.createToken(loginUser);

                ajax.put(Constants.TOKEN, token);
                if("APP".equals(ssoToken.getFlag())){
                    AsyncManager.me().execute(AsyncFactory.recordLogininfor(queryUser, Constants.LOGIN_SUCCESS, MessageUtils.message("user.app.login.success")));
                }else{
                    AsyncManager.me().execute(AsyncFactory.recordLogininfor(queryUser, Constants.LOGIN_SUCCESS, "portal授权登录成功"));
                }
                return ajax;

            }else{
                ajax.put("errmsg","令牌验证失败，原因是：" + result.getAuthMsg());
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(queryUser, Constants.LOGIN_FAIL, "令牌验证失败，原因是：" + result.getAuthMsg()));
                return ajax;
            }
        } catch (RemoteException e) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(queryUser, Constants.LOGIN_FAIL, e.getMessage()));
            throw new CustomException(e.getMessage());
        }
    }



}
