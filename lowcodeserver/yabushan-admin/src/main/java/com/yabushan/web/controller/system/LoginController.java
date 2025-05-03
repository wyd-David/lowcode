package com.yabushan.web.controller.system;

import cn.hutool.core.util.XmlUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import com.yabushan.common.constant.Constants;
import com.yabushan.common.core.controller.BaseController;
import com.yabushan.common.core.domain.AjaxResult;
import com.yabushan.common.core.domain.entity.SysUser;
import com.yabushan.common.core.domain.model.LoginUser;
import com.yabushan.common.exception.CustomException;
import com.yabushan.common.utils.MessageUtils;
import com.yabushan.framework.manager.AsyncManager;
import com.yabushan.framework.manager.factory.AsyncFactory;
import com.yabushan.framework.web.service.SysPermissionService;
import com.yabushan.framework.web.service.TokenService;
import com.yabushan.framework.web.service.UserDetailsServiceImpl;
import com.yabushan.system.service.ISysUserService;
import com.yabushan.web.service.OAuthLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.Map;

//@MethodLog
@Api(tags = "OAuth单点登录", value = "OAuth单点登录")
@RestController
//@RequestMapping("api/erp/sso/oauth")
@Slf4j
public class LoginController extends BaseController {
    @Value("${domain.xzfw}")
    private String domain;
    @Value("${weixin.callback}")
    private String callback;

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

    @ApiOperation(value = "登录回调方法", notes = "登录回调方法")
    @GetMapping("/login/callback")
    public JSONObject login(
            @ApiParam(name = "code", value = "OAuth登录回调Code") @RequestParam(name = "code") String code,
            @ApiParam(name = "syscode", value = "系统ID") @RequestParam(name = "syscode",required = false) Integer syscode,
            @ApiParam(name = "state", value = "跳转状态") @RequestParam(name = "state", required = false) String state,
            @ApiParam(name = "todoUrl", value = "跳转地址") @RequestParam(name = "todoUrl", required = false) String todoUrl,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws Exception {

        log.info("in callback....");
        JSONObject result = oAuthLoginService.codeLogin(code);
        log.info("result:{}", result);
        assert result != null;

        //根据账号查询pc端的用户表
        SysUser sysUser = iSysUserService.selectUserByUserName(result.get("user_id").toString());
        //传入账号角色
        LoginUser loginUser = new LoginUser(sysUser, permissionService.getMenuPermission(sysUser));
        userDetailsService.loadUserByUsername(sysUser.getUserName());
        //创建token
        String  token = tokenService.createToken(loginUser);
        System.out.println("token....>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+token);
        System.out.println("state....>>>>>>>>>>>>>state>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+state);
        String encode = URLEncoder.encode("callback.html", "UTF-8");
        System.out.println(encode);


        if (result.size() != 0) {

            String ltpa_token = result.getStr("LtpaToken2");

            //log.info("cookies:"+request.getCookies().toString());
            //权限检查，检查该用户是否存在该系统的权限
           // int erpNumber = result.getInt("employeeid");

            OAuthLoginService.setLtpaCookie(response, ltpa_token);
            result.put("redirect", domain);

            HttpSession session = request.getSession(false);

            if (session != null) {
                //清空旧session数据
                session.invalidate();
                session = request.getSession();
                log.info("new session Id:{}", session.getId());
            } else {
                session = request.getSession();
            }

            String sessionId = session.getId();
            log.info("exit session Id:{}", sessionId);
//            session.setAttribute(SystemConstant.USERNAME, result.getStr("uid").toUpperCase());
//            session.setAttribute(SystemConstant.SYSTEMID, syscode);
            session.setAttribute("gft_access_token", result.getStr("access_token"));
            session.setAttribute("token", token);
            session.setAttribute("", syscode);
            log.info(">>>>>>>>>>login>" + ltpa_token);
            session.setAttribute("oa_session_token", ltpa_token);
            session.setAttribute("oaSessionToken", ltpa_token);




            Cookie cookie = new Cookie("Admin-Token", sessionId);
            cookie.setDomain("10.51.167.134");
            cookie.setPath("/#"+todoUrl+"&Admin-Token="+token);
            cookie.setMaxAge(60 * 1000 * 30);
            response.addCookie(cookie);
            log.info("update systemId:{}...", syscode);
            if(StringUtils.isNotEmpty(todoUrl)){
                response.sendRedirect(domain+"/#"+todoUrl+"&token="+token);
                return null;
            }
            if (StringUtils.isNotEmpty(state)) {
                if(state.contains("callback.html")){
                    response.sendRedirect(callback+"?token="+token+"&userName="+result.get("user_id").toString());
                     return null;
                }
                String[] temp = state.split(",");
                if(temp.length==2){
                    response.setStatus(302);
                    response.sendRedirect(temp[0]);
                }
                return null;
            } else {
                return result;
            }
        } else {
            return result;
        }
    }

    @ApiOperation(value = "登出", notes = "登出")
    @GetMapping("/logout")
    public Object logout() {
        return null;
    }


    /**
     *
     * @param userId
     * @return
     */
    @PostMapping("/login/callback/autoLogin")
    public String isLogin(@ApiParam(name = "userId", value = "用户账号") @RequestParam(name = "userId") String userId){

        //根据账号查询pc端的用户表
        SysUser sysUser = iSysUserService.selectUserByUserName(userId);
        //传入账号角色
        LoginUser loginUser = new LoginUser(sysUser, permissionService.getMenuPermission(sysUser));
        userDetailsService.loadUserByUsername(sysUser.getUserName());
        //创建token
        String  token = tokenService.createToken(loginUser);
        return  token;

    }


}
