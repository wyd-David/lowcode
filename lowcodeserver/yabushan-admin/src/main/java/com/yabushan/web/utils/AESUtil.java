package com.yabushan.web.utils;
import java.net.URLDecoder;
import java.net.URLEncoder;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import java.nio.charset.StandardCharsets;

/**
 * @author FaceSun
 * @title: AESUtil
 */
public class AESUtil {
    // 密钥 (需要前端和后端保持一致)十六位作为密钥
    private static final String KEY = "q6Jxw7XJ8IrDvdsK";
    // 密钥偏移量 (需要前端和后端保持一致)十六位作为密钥偏移量
    private static final String IV = "q6Jxw7XJ8IrDvdsV";
    // 算法
    private static final String ALGORITHMSTR = "AES/CBC/PKCS5Padding";

    public static void main(String[] args) {
        try {

//            Base64.decodeBase64(url)
            byte[] res=Base64.encodeBase64("http://127.0.0.1:8012/picturesPreview?urls=342342".getBytes());
            System.out.printf("结果：");
//            String encodedFileName = URLDecoder.decode(res, "UTF-8");
            String encodedFileName = URLEncoder.encode(new String(res), "UTF-8");

            System.out.print(encodedFileName);
            System.out.printf("\n\t：");


            String res1 = new String(Base64.decodeBase64(URLDecoder.decode(encodedFileName,"UTF-8")), StandardCharsets.UTF_8);
            System.out.printf("结果2：");

            System.out.print(res1);
            System.out.printf("\n\t：");



        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * base 64 decode
     * @param base64Code 待解码的base 64 code
     * @return 解码后的byte[]
     * @throws Exception
     */
    public static byte[] base64Decode(String base64Code) throws Exception{
        return StringUtils.isEmpty(base64Code) ? null : new BASE64Decoder().decodeBuffer(base64Code);
    }

    /**
     * AES===========解密
     * @param encryptStr 待解密的
     * @return 解密后的String
     * @throws Exception Exception
     */
    public static String aesDecrypt(String encryptStr) throws Exception {
        if (StringUtils.isNotBlank(encryptStr)){
            Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
            byte[] temp = IV.getBytes(StandardCharsets.UTF_8);
            IvParameterSpec iv = new IvParameterSpec(temp);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(KEY.getBytes(), "AES"), iv);
            byte[] decryptBytes = cipher.doFinal(base64Decode(encryptStr));
            return new String(decryptBytes);
        }
        return null;
    }

    /**
     * ==============================================AES加密
     * @param password 待加密的
     * @return 加密后的String
     * @throws Exception Exception
     */
    public static String aesEncrypt(String password) throws Exception {
        if (StringUtils.isNotBlank(password)){
            Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
            byte[] temp = IV.getBytes(StandardCharsets.UTF_8);
            IvParameterSpec iv = new IvParameterSpec(temp);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(KEY.getBytes(), "AES"), iv);
            byte[] encryptBytes = cipher.doFinal(password.getBytes());
            return base64Encoder(encryptBytes);
        }
        return null;
    }

    /**
     * base 64 加码
     */
    public static String base64Encoder(byte[] base64Code){
        return new BASE64Encoder().encodeBuffer(base64Code);
    }
}

