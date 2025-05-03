package com.yabushan.ai.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
@Configuration
@Data
public class WxConfig {

        @Value("${XCX.APP_ID}")
        private String appId;

        @Value("${XCX.APP_SECRET}")
        private String appSecret;

        @Value("${XCX.TEMPLATE_ID}")
        private String templateId;

}
