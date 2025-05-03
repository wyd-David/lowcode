package com.yabushan.system.utils;

import com.yabushan.common.bpm.utils.HttpToolUtil;
import com.yabushan.common.utils.http.HttpClientUtil;
import org.apache.groovy.util.Maps;
import com.yabushan.common.utils.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.util.Date;
import java.util.UUID;

/**
 * @Deseription 数据推送到Portal工具类
 * @Author zhoucp
 * @Date 2021/4/19 11:30
 */
@Component
public class PushDataToPortal {
    private final static Logger log = LogManager.getLogger(HttpToolUtil.class);

    @Value("${portal.pushUrl}")
    private String pushUrl;
    @Value("${portal.appName}")
    private  String appName; //portal分配的应用名
    @Value("${portal.userid}")
    private String userid; //Portal分配用户名
    @Value("${portal.password}")
    private String password;//Portal分配密码

    public String push(String businessType,String json) throws Exception {

        String url = String.format("%s?seqno=%s&appname=%s&business=%s&uid=%s&password=%s&timestamp=%s",
                pushUrl, UUID.randomUUID(),URLEncoder.encode(appName, "UTF-8"), businessType, userid,
                password, DateUtils.parseDateToStr("yyyyMMddHHmmssSSS",new Date()));
        log.info("请求推送--类型为{}，url:{},参数为：{}",businessType,url,json);
        String res = HttpClientUtil.doPost(url, json, Maps.of("Content-type", "text/html; charset=UTF-8"));
        log.info("推送返回值：{}",res);
        return res;
    }


}
