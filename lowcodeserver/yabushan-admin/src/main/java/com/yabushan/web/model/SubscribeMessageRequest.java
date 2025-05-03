package com.yabushan.web.model;

import lombok.Data;

@Data
public class SubscribeMessageRequest {
    private String title;
    private String content;

    private String planTime;

    private String openId;
}
