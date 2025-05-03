package com.yabushan.web.controller.capacity;

import cn.hutool.core.date.DateUtil;
import com.yabushan.common.annotation.Log;
import com.yabushan.common.config.RuoYiConfig;
import com.yabushan.common.constant.Constants;
import com.yabushan.common.constant.UserConstants;
import com.yabushan.common.core.controller.BaseController;
import com.yabushan.common.core.domain.AjaxResult;
import com.yabushan.common.core.domain.entity.SysUser;
import com.yabushan.common.core.domain.model.AppUserPwd;
import com.yabushan.common.core.domain.model.LoginBody;
import com.yabushan.common.core.domain.model.LoginUser;
import com.yabushan.common.core.domain.model.UserPwd;
import com.yabushan.common.core.redis.RedisCache;
import com.yabushan.common.enums.BusinessType;
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.common.utils.ServletUtils;
import com.yabushan.common.utils.file.FileUploadUtils;
import com.yabushan.common.utils.spring.SpringUtils;
import com.yabushan.form.mapper.SqlExecuteMapper;
import com.yabushan.framework.web.service.SysLoginService;
import com.yabushan.framework.web.service.TokenService;
import com.yabushan.system.service.ISysUserService;
import com.yabushan.web.model.entity.AppLoginBody;
import com.yabushan.web.utils.AESUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 基础能力
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/base/capacity")
@Api(tags = "基础能力")
public class BaseCapacityController  extends BaseController {

    @Autowired
    private SqlExecuteMapper sqlExecuteMapper;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private SysLoginService loginService;

    
    
    //获取APP访问密钥
    @PostMapping("/getAppToken")
    public AjaxResult getAppToken( @RequestParam(value = "appKey", required = true) String appKey,
                                   @RequestParam(value = "appSecret", required = true) String appSecret
                                   ) throws Exception {
        
        
        RedisCache redisCache = SpringUtils.getBean(RedisCache.class);
        Object cacheObject = redisCache.getCacheObject(appKey+"@"+appSecret);
        boolean validateApp = true;
        if(cacheObject==null){
            //校验不通过，从数据库重新获取
            String sql = "SELECT COUNT(1) FROM ngs_app_manager_list L WHERE  L.APP_STATUS='上线' AND L.APP_KEY='"+appKey+"' AND L.APP_SECRET='"+appSecret+"'";
            Object execute = sqlExecuteMapper.execute(sql);
            Long integer = Long.valueOf((Long) execute);
            if(integer>0L){
                //校验通过，应用存在数据库,过期时间为10分钟
                redisCache.setCacheObject(appKey+"@"+appSecret, 1, 10, TimeUnit.MINUTES);
            }else{
                validateApp=false;
                
            }
        }
        String yyyyMMdd = DateUtil.format(new Date(),"yyyyMMdd");
        if(validateApp){
            //重新生成应用的访问Token
            Object cacheObject1 = redisCache.getCacheObject(appKey + "@" + appSecret + "@secret");
            if(cacheObject1!=null){
                String[] keyValues  =AESUtil.aesDecrypt(cacheObject1.toString()).split("@");
                if(keyValues.length != 3){
                    return AjaxResult.error("AppToken无效！");
                }else{
                    if(keyValues[2].equals(yyyyMMdd)){
                        //返回密钥
                        return AjaxResult.success(cacheObject1.toString());
                    }else{
                        //重新生成密钥
                        String key = appKey + "@" + appSecret+"@"+yyyyMMdd;
                        String s = AESUtil.aesEncrypt(key);
                        redisCache.setCacheObject(appKey + "@" + appSecret + "@secret", s, 10, TimeUnit.DAYS);
                        return  AjaxResult.success(s);
                    }
                }                
            }else{
                //重新生成密钥
                String key = appKey + "@" + appSecret+"@"+yyyyMMdd;
                String s = AESUtil.aesEncrypt(key);
                redisCache.setCacheObject(appKey + "@" + appSecret + "@secret", s, 10, TimeUnit.DAYS);
                return  AjaxResult.success(s);
            }
        }else{
            return AjaxResult.error("应用不存在或者应用未上线！");
        }
    }
    
    
    
    
    //用户注册接口
    @PostMapping("/registerUser")
    public AjaxResult registerUser(@RequestParam(value = "loginId", required = true) String loginId,
                                   @RequestParam(value = "password", required = true) String password,
                                   @RequestParam(value = "phonenumber", required = false) String phonenumber,
                                   @RequestParam(value = "appToken", required = true) String appToken
                                   ) throws Exception {
        
        
      
        //解析appToken
        String[] keyValues  =validateValue(appToken);
        if(keyValues==null){
            return AjaxResult.error("AppToken无效！");
        }else{
            String sql = "SELECT l.APP_KEY,u.dept_id as DEPT_ID,u.user_name AS USER_NAME FROM ngs_app_manager_list l,sys_user u WHERE u.user_name=l.CREATED_BY and l.APP_KEY='"+keyValues[0] +"'  AND L.APP_SECRET='"+ keyValues[1]+"'" ;
            List<Map<String, Object>> maps = sqlExecuteMapper.selectPublicItemList(sql);
            if(maps.size()!=1){
                return AjaxResult.error("Appkey和AppSecret不匹配");
            }
            String syncUser = maps.get(0).get("USER_NAME").toString();
            String deptId = maps.get(0).get("DEPT_ID").toString();
            String APP_KEY = maps.get(0).get("APP_KEY").toString();
            String userName = APP_KEY+"_"+loginId;

            if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(userName))) {
                return AjaxResult.error("账号已存在！");
            }

            //解密
            String s = AESUtil.aesDecrypt(password);
            String replace = s.replace(APP_KEY + "_", "");
            
            SysUser user = new SysUser(userName, APP_KEY, replace, phonenumber);
            user.setDeptId(Long.valueOf(deptId));
            user.setUserType("11");
            user.setSyncUserId(syncUser);
            user.setMaindepartment("true");
            int i = userService.AppRegisterUser(user);
            if(i>0){
                //写入用户表
                sql = "INSERT INTO ngs_app_user_manager(CREATED_BY,APP_KEY,LOGIN_NAME) VALUES('"+syncUser+"','"+APP_KEY+"','"+userName+"')";
                sqlExecuteMapper.execute(sql);
            }
        }
        return AjaxResult.success("注册成功");
    }


    /**
     * 加密
     * @param text
     * @param appToken
     * @return
     * @throws Exception
     */
    @PostMapping("/encryptWord")
    public AjaxResult encryptWord(  @RequestParam(value = "text", required = true) String text,
                                    @RequestParam(value = "appToken", required = true) String appToken
                                ) throws Exception {

        String[] strings = validateValue(appToken);
        if(strings==null){
            return  AjaxResult.error("应用无效");
        }
        String s = AESUtil.aesEncrypt(strings[0]+"_"+text);
        return AjaxResult.success(s);
    }

    /**
     * 解密
     * @param text
     * @param appToken
     * @return
     * @throws Exception
     */
    @PostMapping("/dncryptWord")
    public AjaxResult dncryptWord(@RequestParam(value = "text", required = true) String text,
                                    @RequestParam(value = "appToken", required = true) String appToken
    ) throws Exception {

        String[] strings = validateValue(appToken);
        if(strings==null){
            return  AjaxResult.error("应用无效");
        }
        String s = AESUtil.aesDecrypt(text);
        String replace = s.replace(strings[0] + "_", "");
        return AjaxResult.success(replace);
    }
    
    
    //用户登录接口
    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/AppLogin")
    public AjaxResult login(@RequestBody AppLoginBody loginBody) throws Exception {
        AjaxResult ajax = AjaxResult.success();
        String password = "";
        try {
            password = AESUtil.aesDecrypt(loginBody.getPassword());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        
        String[] strings = validateValue(loginBody.getApptoken());
        if(strings==null){
            return AjaxResult.error("appToken无效!");
        }
        password = password.replace(strings[0] + "_", "");

        // 生成令牌
        String token = loginService.AppLogin(getAppKey(strings)+"_"+loginBody.getUsername(), password);
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }

   

    
    @SneakyThrows
    public static void main(String[] args) {
       
        System.out.println(AESUtil.aesDecrypt("zl3PH6AwJa7rws6N267pxCQL+pM1oCoSU5aUjzSRA0s=\r\n"));


    }



    private static String[] validateValue(String appToken) throws Exception {
        String[] keyValues  =AESUtil.aesDecrypt(appToken).split("@");

        String yyyyMMdd = DateUtil.format(new Date(),"yyyyMMdd");
        if(keyValues.length != 3){
            return null;
        }else{
            if(!keyValues[2].equals(yyyyMMdd)){
                return null;
            }
        }
        return keyValues;

    }




    /**
     * 获取APPkey
     * @param values
     * @return
     */
    private  static String getAppKey(String[] values){
        return  values[0];
    }


}
