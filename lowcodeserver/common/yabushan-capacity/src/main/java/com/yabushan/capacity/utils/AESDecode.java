package com.yabushan.capacity.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AESDecode {

    public static void main(String[] args) {
        try {
            // 加密数据
            String encryptedText = "MGIyNWJlOWNmZjQ1NzE0YTlhZDViOTRiNzZmZjAzNzcxUE1rWG9GK2RSYjlVUGxUa2xWL3p6bG5IZG1uYkwvV3Eyd3lXUXN0NU5FaDNJTzlTdUI4WjdPR2VRMGJVa0RNb2MvbW00dTgrZHZaaVNQcXhEU2dJMGwrdmsrWFA0QldEbC9UMWtKTUthaVA5UktaamFIbUJHam5nYXdLYXVPWHgydWNTeWlOZW52VHhobjNIQk5xNTVpeDlnb0ozcUpUS1VYZWdQcTZJTjgydU44SU95VU5UeVMzRHdQbnRkY1VEWnl1SGpzT29mNVpKMSsvQ0kyQXdLU1pWLy9PN2N3SGY5NG1qOE16QitJdU9WVDI5WlRGenNFQnJXa2RFR1BYaFdFK2FyQ0o1NEtNVFkrRDQ4dm5iaEI2eDNnZXJTU0tZM2xsck4rcVlIS1lzbXJCTEd1MkdTZ0xLejlyTml1L3VPSml1MDcxeDVEQU1PTDdNRCtyYkxtaUhIeGllVVdXUG1RbE1QdUp5UjRBcGZIQU5QUmltWXFYc085a3VmYm1HTHJ1NDd1MnU2OHNiR2d4ekJwUVVScWpkMXE2eHd0NlVITVA0K2VEazBxWmtUc1U0OE1MOXpUN2dlV3FYNk5QRnNmbzVmanQ3VnNCUWpiMU5kSG9yNHJqOW5LaUYvbS9hTndDMjlzYUtDM21oK01HSlJUY3RFN2JDanRXaDZCOCtVZE1Za2JsUGRsaVJJVDZTVm52QjBUV0RhdnlGWlEzNFV2amxIME51WWFQNk1pckNFaTMzb1FMYTdyNkdRQUo1ZFI4UTlxZjdhUVpBUlhVTjZrd0t6OGpQU1VNZHFPd0ExeHE4QzllUTZtWT0=";

            // 分发的clientId
            String keyString = "7acacddd80fd4a8b82ad015dd2787da8";
            // base64解密
            byte[] decode = Base64.getDecoder().decode(encryptedText);
            String s = new String(decode);

            // 去除第一位和MD5hash值，得到加密数据
            encryptedText = s.substring(33);
            System.out.println("substring = " + encryptedText);


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

            System.out.println("Encrypted Text: " + encryptedText);
            System.out.println("Decrypted Text: " + decryptedText);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
