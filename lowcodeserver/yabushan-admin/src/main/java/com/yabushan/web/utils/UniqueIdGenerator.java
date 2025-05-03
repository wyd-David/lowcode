package com.yabushan.web.utils;

import java.time.Instant;

public class UniqueIdGenerator {
    public static String generateUniqueId() {
        long timestamp = Instant.now().toEpochMilli(); // 获取当前时间戳（毫秒）
        String randomString = RandomStringUtils.generateRandomString(16); // 生成8字节的随机字符串
        return timestamp + "-" + randomString; // 组合时间戳和随机字符串
    }

    public static void main(String[] args) {
        String uniqueId = UniqueIdGenerator.generateUniqueId();
        System.out.println("Generated Unique ID: " + uniqueId);
    }
}

