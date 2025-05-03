package com.yabushan.form.utils;


import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;

public class AESUtil
{
    private static final String key = "6B7I7N0G9O3D9I4G";

    public static void main(String[] args) {

       String s = decrypt4AES("FHEUALyoVvc0+d6eGVPrSA==");
       // String s = encrypt4AES("report_SXF.");
        System.out.println(s);
    }

    private static byte[] getKeyByte()
    {
        try
        {
            return "6B7I7N0G9O3D9I4G".getBytes("utf-8");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static String encrypt4AES(String content)
    {
        try
        {
            byte[] raw = getKeyByte();
            IvParameterSpec zeroIv = new IvParameterSpec(raw);
            SecretKeySpec key1 = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(1, key1, zeroIv);
            byte[] encryptedData = cipher.doFinal(content.getBytes("utf-8"));
            String encryptResultStr = new BASE64Encoder().encode(encryptedData);
            return encryptResultStr.replace("\r\n", "").replace("\n", "");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return "";
    }

    public static String decrypt4AES(String content)
    {
        try
        {
            byte[] raw = getKeyByte();
            byte[] decryptFrom = new BASE64Decoder().decodeBuffer(content);
            IvParameterSpec zeroIv = new IvParameterSpec(raw);
            SecretKeySpec key1 = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(2, key1, zeroIv);
            byte[] decryptedData = cipher.doFinal(decryptFrom);
            return new String(decryptedData, "UTF-8");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return "";
    }
}
