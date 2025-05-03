package com.yabushan.framework.config;

import com.yabushan.framework.security.filter.JwtAuthenticationTokenFilter;
import com.yabushan.framework.security.handle.AuthenticationEntryPointImpl;
import com.yabushan.framework.security.handle.LogoutSuccessHandlerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.filter.CorsFilter;

/**
 * spring security配置
 *
 * @author ruoyi
 */
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    private Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
    /**
     * 自定义用户认证逻辑
     */
   @Autowired
    private UserDetailsService userDetailsService;

    /**
     * 认证失败处理类
     */
    @Autowired
    private AuthenticationEntryPointImpl unauthorizedHandler;

    /**
     * 退出处理类
     */
    @Autowired
    private LogoutSuccessHandlerImpl logoutSuccessHandler;

    /**
     * token认证过滤器
     */
    @Autowired
    private JwtAuthenticationTokenFilter authenticationTokenFilter;

    /**
     * 跨域过滤器
     */
    @Autowired
    private CorsFilter corsFilter;

    /**
     * 解决 无法直接注入 AuthenticationManager
     *
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception
    {
        return super.authenticationManagerBean();
    }

    /**
     * anyRequest          |   匹配所有请求路径
     * access              |   SpringEl表达式结果为true时可以访问
     * anonymous           |   匿名可以访问
     * denyAll             |   用户不能访问
     * fullyAuthenticated  |   用户完全认证可以访问（非remember-me下自动登录）
     * hasAnyAuthority     |   如果有参数，参数表示权限，则其中任何一个权限可以访问
     * hasAnyRole          |   如果有参数，参数表示角色，则其中任何一个角色可以访问
     * hasAuthority        |   如果有参数，参数表示权限，则其权限可以访问
     * hasIpAddress        |   如果有参数，参数表示IP地址，如果用户IP和参数匹配，则可以访问
     * hasRole             |   如果有参数，参数表示角色，则其角色可以访问
     * permitAll           |   用户可以任意访问
     * rememberMe          |   允许通过remember-me登录的用户访问
     * authenticated       |   用户登录后可访问
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception
    {
        httpSecurity
                // CSRF禁用，因为不使用session
                .csrf().disable()
                // 认证失败处理类
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // 过滤请求
                .authorizeRequests()
                // 对于登录login 验证码captchaImage 允许匿名访问
                .antMatchers("/login","/datasource/wx/login" ,"/captchaImage","/captchaImageXcx","/yzm","/APPDL","/system/syncUser/getUserInfoByObjectImages","/gfxz/SyncOneCardInfo/fileDownUtil","/gmcc/ssoLogin","/gmcc/eztLogin").anonymous()
                .antMatchers("/datasource/login","/sqldesign/**").anonymous()
                .antMatchers("/wxdatasource/**").anonymous()
                //对于数据平台，允许匿名访问
                .antMatchers("/data/getMessageRow","/data/getMessageList","/TodoInfoPushGFController/pushNewLogInfo","/weixin/user/gftde/openid","/weixin/user/openid","/weixin/user/gft/openid","/weixin/user/gft/oaLogin","/data/aliApi","/system/user/registerUser","/TodoInfoPushGFController/newPushNewLogInfo").anonymous()
                //minio文件相关
                .antMatchers("/file/minio/download*").anonymous()
                .antMatchers("/robot/**").anonymous()

//                .antMatchers("/search/**").anonymous()
                .antMatchers("/websocket/**").anonymous()
                //微信相关接口
                .antMatchers("/weixin/**").anonymous()
                //ai相关接口
                .antMatchers("/openAi/**").anonymous()

                //匿名提交表单
                .antMatchers("/form/info/getInfoNimin/**","/form/info/saveFormInfoNimin/**").anonymous()
                //APP应用接口
                .antMatchers("/base/capacity/**").anonymous()
        /*        //创建报表接口
                .antMatchers("/datasource/createReport").anonymous()*/
                //app登录
                .antMatchers("/weixin/user/gftde/**").anonymous()
                .antMatchers("/jmreport/**").anonymous()
                //短信推送，短信校验
               // .antMatchers("/msgCode/**").anonymous()
                //新员工入职文件下载链接
                .antMatchers("/entry/EntryUserManage/downloadFileToZip").anonymous()
                .antMatchers("/visit/visitMainInfo/add","/visit/visitMainInfo/getVisitEnum","/visit/visitMainInfo/getCodeByPhone","/visit/visitMainInfo/checkPhone").anonymous()
                .antMatchers(
                        HttpMethod.GET,
//                        "/*.html",
//                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js"
                ).permitAll()
                .antMatchers("/profile/**").anonymous()
                .antMatchers("/common/download**").anonymous()
                .antMatchers("/common/download/resource**").anonymous()
                .antMatchers("/doc.html/**").anonymous()
                .antMatchers("/swagger-ui.html").anonymous()
                .antMatchers("/swagger-resources/**").anonymous()
                .antMatchers("/webjars/**").anonymous()
                .antMatchers("/*/api-docs").anonymous()
                //websocket
                .antMatchers("/myWs/**").anonymous()
                //测试类可以不用登录
                .antMatchers("/test/**").anonymous()
                .antMatchers("/druid/**").anonymous()
                .antMatchers("/ymx-api/**").anonymous()
               // .antMatchers("/activiti/**/**").anonymous()
                .antMatchers("/common/**").anonymous()
                //.antMatchers("/tool/gen/batchGenCode").anonymous()

                //文件上传到本地

                .antMatchers("/local/upload").anonymous()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated()
                .and()
                .headers().frameOptions().disable();
        httpSecurity.logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler);
        // 添加JWT filter
        httpSecurity.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        // 添加CORS filter
        httpSecurity.addFilterBefore(corsFilter, JwtAuthenticationTokenFilter.class);
        httpSecurity.addFilterBefore(corsFilter, LogoutFilter.class);
    }


    /**
     * 强散列哈希加密实现
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    /**
     * 身份认证接口
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }





}
