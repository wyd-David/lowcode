package com.yabushan.capacity.utils;

import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class ShumengAesUtils {
    public  static void main(String[] args) {
        try {
            // 未加密数据
            String msg = "{\n" +
                    "\"type\":\"tenantCreate\",\n" +
                    "\"data\":{\n" +
                    "\"parentId\": \"-2\",\n" +
                    "\"level\": 1,\n" +
                    "],\n" +
                    "\"createId\": \"440970952095563776\",\n" +
                    "\"createTime\": 1693276405000,\n" +
                    "\"updateId\": \"440970952095563776\",\n" +
                    "\"updateTime\": 1693276405000\n" +
                    "}\n" +
                    "}";
            String clientId ="123456778906788";
            String s = AesEncrypt(msg,clientId );
            
            System.out.println("result = " + s);

            String decode = Decode(s, clientId);
            System.out.println("decode="+decode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加密
     * @param msg 字符串
     * @param clientId  clientId
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static String AesEncrypt(String msg,String clientId) 
            throws UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
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
       // System.out.println("encryptedText = " + encryptedText);
        // 计算MD5值
        String md5Hash = DigestUtils.md5Hex(msg);
        // 拼接数据
        String encrypt = "0" + md5Hash + encryptedText;
        // base64编码
        String result = Base64.getEncoder().encodeToString(encrypt.getBytes());
        return result;
    }

    /**
     * 解密
     * @param encryptedText  密文
     * @param clientId  客户端ID
     * @return
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static String Decode(String encryptedText,String clientId) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        // 加密数据
     
        // 分发的clientId
        String keyString = clientId;
        // base64解密
        byte[] decode = Base64.getDecoder().decode(encryptedText);
        String s = new String(decode);

        // 去除第一位和MD5hash值，得到加密数据
        encryptedText = s.substring(33);

        // 解密，使用密钥字节创建密钥
        SecretKey secretKey = new SecretKeySpec(keyString.getBytes(), "AES");

        // 创建Cipher实例
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        // 解码Base64加密文本到字节
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);

        // 解密加密的字节
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        // 将解密的字节转换为明文
        String decryptedText = new String(decryptedBytes, StandardCharsets.UTF_8);

        return decryptedText;
        
    }
}
