package com.yabushan.capacity.utils;


import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AESEncrypt {

    public static void main(String[] args) {
        try {
            // 未加密数据
            String msg = "{\n" +
                    "\"type\":\"tenantCreate\",\n" +
                    "\"data\":{\n" +
                    "        \"parentId\": \"-2\",\n" +
                    "        \"level\": 1,\n" +
                    "        \"tenantName\": \"测试空间082901\",\n" +
                    "        \"departmentId\": \"1039725970\",\n" +
                    "        \"path\": \"/-2\",\n" +
                    "        \"tenantId\": \"1039725970\",\n" +
                    "         \"userId\": [\n" +
                    "            \"443415158156165120\"\n" +
                    "        ],\n" +
                    "        \"createId\": \"440970952095563776\",\n" +
                    "        \"createTime\": 1693276405000,\n" +
                    "        \"updateId\": \"440970952095563776\",\n" +
                    "        \"updateTime\": 1693276405000\n" +
                    "       }\n" +
                    "}";
            // 应用clienId
            String clientId = "7acacddd80fd4a8b82ad015dd2787da8";
            byte[] keyBytes = clientId.getBytes("UTF-8");

            // 使用密钥字节创建密钥
            SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");

            // 创建Cipher实例
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            // 加密明文
            byte[] encryptedBytes = cipher.doFinal(msg.getBytes(StandardCharsets.UTF_8));

            // 将加密的字节编码为Base64以便更好地显示
            String encryptedText = Base64.getEncoder().encodeToString(encryptedBytes);
            System.out.println("encryptedText = " + encryptedText);

            // 计算MD5值
            String md5Hash = DigestUtils.md5Hex(msg);

            // 拼接数据
            String encrypt = "0" + md5Hash + encryptedText;

            // base64编码
            String result = Base64.getEncoder().encodeToString(encrypt.getBytes());
            System.out.println("result = " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
