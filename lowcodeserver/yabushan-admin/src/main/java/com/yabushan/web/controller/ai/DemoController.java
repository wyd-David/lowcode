package com.yabushan.web.controller.ai;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @RequestMapping("/demo")
    public boolean demo(@RequestParam("userId") String userId, @RequestParam("message") String mesage) {
        //给前端web推送数据
        WebSocketServer.sendInfo(userId, mesage);
        return true;
    }
}
