package com.yabushan.web.controller.weixin;

import com.yabushan.ai.service.WxSubscribeMessageService;
import com.yabushan.web.model.SubscribeMessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/wx/subscribe")
public class WxSubscribeController {
    @Autowired
    private WxSubscribeMessageService subscribeMessageService;

    @PostMapping("/send")
    public ResponseEntity<?> sendSubscribeMessage(@RequestBody SubscribeMessageRequest request) {
        // 构建消息数据
        Map<String, Object> data = new HashMap<>();
        /**
         * 计划标题
         * {{thing13.DATA}}
         *
         * 提醒内容
         * {{thing2.DATA}}
         *
         * 计划时间
         * {{time14.DATA}}
         */

        data.put("thing13", new HashMap<String, String>() {{
            put("value", request.getTitle());
        }});

        data.put("time14", new HashMap<String, String>() {{
            put("value", request.getPlanTime());
        }});

        data.put("thing2", new HashMap<String, String>() {{
            put("value", request.getContent());
        }});



        boolean success = subscribeMessageService.sendSubscribeMessage(request.getOpenId(), data);

        if (success) {
            return ResponseEntity.ok("发送成功");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("发送失败");
        }
    }
}