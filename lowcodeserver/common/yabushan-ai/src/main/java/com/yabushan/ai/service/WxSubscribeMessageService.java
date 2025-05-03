package com.yabushan.ai.service;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class WxSubscribeMessageService {
    @Autowired
    private WxConfig wxConfig;

    private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
    private static final String SUBSCRIBE_MSG_URL = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send";

    /**
     * 获取访问令牌
     */
    public String getAccessToken() {
        String url = String.format(ACCESS_TOKEN_URL, wxConfig.getAppId(), wxConfig.getAppSecret());
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);
        // 解析响应获取access_token
        // 建议将token缓存到Redis，避免频繁请求
        JSONObject jsonObject = JSONObject.parseObject(response);
        return jsonObject.getString("access_token");
    }

    /**
     * 发送订阅消息
     */
    public boolean sendSubscribeMessage(String openId, Map<String, Object> data) {
        try {
            String accessToken = getAccessToken();
            String url = SUBSCRIBE_MSG_URL + "?access_token=" + accessToken;

            Map<String, Object> message = new HashMap<>();
            message.put("touser", openId);
            message.put("template_id", wxConfig.getTemplateId());
            message.put("data", data);

            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.postForObject(url, message, String.class);

            JSONObject result = JSONObject.parseObject(response);
            return result.getInteger("errcode") == 0;
        } catch (Exception e) {
            log.error("发送订阅消息失败", e);
            return false;
        }
    }
}