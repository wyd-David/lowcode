package com.yabushan.web.controller.common;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import com.yabushan.common.core.domain.model.LoginUser;
import com.yabushan.framework.web.service.TokenService;
import com.yabushan.web.model.common.AdminToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.*;
import com.google.code.kaptcha.Producer;
import com.yabushan.common.constant.Constants;
import com.yabushan.common.core.domain.AjaxResult;
import com.yabushan.common.core.redis.RedisCache;
import com.yabushan.common.utils.sign.Base64;
import com.yabushan.common.utils.uuid.IdUtils;

/**
 * 验证码操作处理
 *
 * @author yabushan
 */
@RestController
@Api(tags = "验证码")
@Slf4j
public class CaptchaController
{
    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    @Autowired
    private RedisCache redisCache;

    // 验证码类型
    @Value("${yabushan.captchaType}")
    private String captchaType;

    @Autowired
    private TokenService tokenService;

    /**
     * 生成验证码
     */
    @GetMapping("/captchaImage")
    @ApiOperation(value = "获取验证码")
    public AjaxResult getCode(HttpServletResponse response) throws IOException
    {
        // 保存验证码信息
        String uuid = IdUtils.simpleUUID();
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
        log.info("获取验证码的verifyKey---------------"+verifyKey);
        String capStr = null, code = null;
        BufferedImage image = null;

        // 生成验证码
        if ("math".equals(captchaType))
        {
            String capText = captchaProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            image = captchaProducerMath.createImage(capStr);
        }
        else if ("char".equals(captchaType))
        {
            capStr = code = captchaProducer.createText();
            image = captchaProducer.createImage(capStr);
        }
        redisCache.setCacheObject(verifyKey, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);

        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try
        {
            ImageIO.write(image, "jpg", os);
        }
        catch (IOException e)
        {
            return AjaxResult.error(e.getMessage());
        }

        AjaxResult ajax = AjaxResult.success();
        ajax.put("uuid", uuid);
        ajax.put("img", Base64.encode(os.toByteArray()));
        return ajax;
    }

    @GetMapping("/captchaImageXcx")
    @ApiOperation(value = "小程序获取验证码")
    public AjaxResult getAppCode(HttpServletResponse response) throws IOException
    {
        // 保存验证码信息
        String uuid = IdUtils.simpleUUID();
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
        log.info("获取验证码的verifyKey---------------"+verifyKey);
        String capStr = null, code = null;
        BufferedImage image = null;

        // 生成验证码
        if ("math".equals(captchaType))
        {
            String capText = captchaProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            image = captchaProducerMath.createImage(capStr);
        }
        else if ("char".equals(captchaType))
        {
            capStr = code = captchaProducer.createText();
            image = captchaProducer.createImage(capStr);
        }

        redisCache.setCacheObject(verifyKey, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try
        {
            ImageIO.write(image, "jpg", os);
        }
        catch (IOException e)
        {
            return AjaxResult.error(e.getMessage());
        }

        AjaxResult ajax = AjaxResult.success();
        ajax.put("uuid", uuid);
        ajax.put("img", Base64.encode(os.toByteArray()));
        return ajax;
    }

    @GetMapping("/yzm")
    @ApiOperation(value = "小程序获取验证码")
    public AjaxResult getAppCodeYzm(HttpServletResponse response) throws IOException
    {
        // 保存验证码信息
        String uuid = IdUtils.simpleUUID();
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
        log.info("获取验证码的verifyKey---------------"+verifyKey);
        String capStr = null, code = null;
        BufferedImage image = null;

        // 生成验证码
        if ("math".equals(captchaType))
        {
            String capText = captchaProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            image = captchaProducerMath.createImage(capStr);
        }
        else if ("char".equals(captchaType))
        {
            capStr = code = captchaProducer.createText();
            image = captchaProducer.createImage(capStr);
        }

        redisCache.setCacheObject(verifyKey, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try
        {
            ImageIO.write(image, "jpg", os);
        }
        catch (IOException e)
        {
            return AjaxResult.error(e.getMessage());
        }

        AjaxResult ajax = AjaxResult.success();
        ajax.put("uuid", uuid);
        ajax.put("img", Base64.encode(os.toByteArray()));
        return ajax;
    }



    static void getValidation(String tel) {

        try {
            RedisCache redisCache=new RedisCache();
            //获取电话号码
            String phoneNo = tel;
            String countKey = phoneNo + ":count";
            String codeKey = phoneNo + ":code";
            //获取指定的电话号码发送的验证码次数
            String cnt = redisCache.getCacheObject(countKey);
            //对次数进行判断
            if (cnt == null) {
                //没有发送过验证码
                redisCache.setCacheObject(countKey,1, 60 * 60 * 24,TimeUnit.SECONDS);
                //发送验证码，假设生成的验证码
                StringBuffer code = new StringBuffer();
                for (int i = 0; i < 6; i++) {
                    code.append(new Random().nextInt(10));
                }
                System.out.println("code:" + code);
                //缓存中添加验证码
                redisCache.setCacheObject(codeKey,code.toString(), 60 * 2, TimeUnit.SECONDS);
            } else {
                if (Integer.parseInt(cnt) < 3) {
                    //发送验证码，假设生成的验证码
                    StringBuffer code = new StringBuffer();
                    for (int i = 0; i < 6; i++) {
                        code.append(new Random().nextInt(10));
                    }
                    System.out.println("code:" + code);
                    //缓存中添加验证码
                    redisCache.setCacheObject(codeKey,code.toString(), 60 * 2, TimeUnit.SECONDS);
                    //递增手机发送数量
                    redisCache.increment(countKey);
                } else {
                    //返回超出3次，禁止发送
                    System.out.println("超出3次，禁止发送");
                }
            }
        } catch (Exception e) {
            //这边其实是需要回滚下redis
            e.printStackTrace();
        } finally {

        }
    }
    static void checkValidation(String code, String tel) {
        try {
            RedisCache redisCache=new RedisCache();
            String codeKey = tel + ":code";
            String validation = redisCache.getCacheObject(codeKey);
            if (validation == null) {
                System.out.println("验证码未发送或者失效");
            } else {
                if (validation.equals(code)) {
                    System.out.println("验证成功");
                } else {
                    System.out.println("验证失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }


    @PostMapping("/captchaImage/ssoLogin")
    @ApiOperation(value = "单点登录")
    public AjaxResult  validateToken( String  adminToken) {
        
        LoginUser loginUser = tokenService.getLoginUser(adminToken);
        return AjaxResult.success(loginUser.getUsername());
    }

}
