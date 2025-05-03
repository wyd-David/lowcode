package com.yabushan.web.utils;

import java.security.SecureRandom;
import java.util.Base64;

public class RandomStringUtils {
    private static final SecureRandom secureRandom = new SecureRandom(); // 用于生成随机数
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder().withoutPadding(); // 用于编码

    public static String generateRandomString(int length) {
        byte[] randomBytes = new byte[length];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }
}

