package com.yabushan.web.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yabushan.common.utils.http.HttpClientUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class OAuthLoginService {

    @Value("${OAuth.gf.configs.client_id}")
    public String clientId;

    @Value("${OAuth.gf.configs.client_name}")
    public String clientName;

    @Value("${OAuth.gf.configs.client_desc}")
    public String clientDesc;

    @Value("${OAuth.gf.configs.redirect_uri}")
    public String redirectUri;

    @Value("${OAuth.gf.configs.client_secret}")
    public String clientSecret;

    @Value("${OAuth.gf.configs.grant_type}")
    public String grantType;

    @Value("${OAuth.gf.configs.getaccess_tokenhost}")
    public String accessTokenHost;

    @Value("${OAuth.gf.configs.chaccess_tokenhost}")
    public String chAccessTokenHost;

    @Value("${OAuth.gf.configs.logouthost}")
    public String logoutHost;
    @Value("${OAuth.gf.configs.getuserinfo_by_access_token}")
    public String userInfo;

    public final static String LTAP_TOKEN_NAME = "LtpaToken2";

    /**
     * 根据code获取access_token，并且验证access_token是否正确返回所有验证信息
     * @param code
     * @return
     */
    public JSONObject codeLogin(String code) {
        //添加请求参数
        Map<String, Object> params = new HashMap<>();
       /* params.put("client_id", clientId);
        params.put("client_name", clientName);
        params.put("client_desc", clientDesc);
        params.put("redirect_uri", redirectUri);
        params.put("client_secret", clientSecret);
        params.put("grant_type", grantType);
        params.put("code",code);*/

        params.put("response_type","token");
        params.put("client_id",clientId);
        params.put("client_secret", clientSecret);
        params.put("grant_type", grantType);
        params.put("code",code);

        try {
            //根据code请求地址获取access_token
            String result = HttpClientUtil.doPost(accessTokenHost, params);
            JSONObject tokenJson = JSONUtil.parseObj(result);
            //判断是否返回access_token
            if (tokenJson.containsKey("access_token")) {
                final String access_token = tokenJson.getStr("access_token");
                //根据access_token请求地址验证该access_token是否正确
                final String url = chAccessTokenHost + access_token;
                final String userInfoStr = HttpClientUtil.doGet(url);
                JSONObject json = JSONUtil.parseObj(userInfoStr);
                //判断回参是否有cookie
                if (json.containsKey("cookie")) {
                    return json;
                } else {
                    JSONObject jsonKey = new JSONObject();
                    jsonKey.put("error_code", json.getInt("error_code"));
                    jsonKey.put("error_description", json.getStr("error_description"));
                    return jsonKey;
                }
            } else {
                JSONObject jsonKey = new JSONObject();
                jsonKey.put("error_code", tokenJson.getInt("error_code"));
                jsonKey.put("error_description", tokenJson.getStr("error_description"));
                return jsonKey;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 根据access_token进行退出
     * @param access_token
     * @return
     */
    public boolean logout(String access_token) {
        final String url = logoutHost + access_token;
        String result = "{code:-1}";
        try {
            result = HttpClientUtil.doPost(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject resultJson = JSONUtil.parseObj(result);
        Integer code = resultJson.getInt("code");
        if (BeanUtil.isEmpty(code)) {
            return true;
        }
        return (code == -1);
    }

    private static String readFromConfig(final String key, final String def) {
        //Read from config file or else
        return def;
    }

    public static void setLtpaCookie(HttpServletResponse response, String ltpaToken) {
        Cookie cookie = new Cookie("LtpaToken2", ltpaToken);
        cookie.setMaxAge(60 * 1000 * 30);
        response.addCookie(cookie);
        response.setHeader("Set-Cookie", "LtpaToken2=" + ltpaToken + ";Domain=.nangs.vip;HttpOnly;Path=/;expires=" + new Date().getTime() + 60 * 1000 * 30);
    }

    public  String isLogin(String token){
        final String url = chAccessTokenHost + token;
        final String userInfoStr = HttpClientUtil.doGet(url);
        return userInfoStr;
    }


}
