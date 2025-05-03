package com.yabushan.common.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 姜森焱 on 2021/8/25 16:20
 * 生成二维码工具类
 */
@Slf4j
public class QRCodeGeneratorUtils {


    /**
     * @param text     二维码文件内容
     * @param width    生成二维码的宽度
     * @param height   生成二维码的高度
     * @param filePath 生成二维码的路径
     * @throws WriterException
     * @throws IOException
     */
    public static String generateQRCodeImage(String text, int width, int height, String filePath) {
        try {
            Map hints = new HashMap();
            //设置UTF-8， 防止中文乱码
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            //设置二维码四周白色区域的大小
            hints.put(EncodeHintType.MARGIN, 0);
            //设置二维码的容错性
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);
            Path path = FileSystems.getDefault().getPath(filePath);
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
        } catch (Exception e) {
           log.error(e.getMessage());
        }
        return filePath;
    }

    /**
     * 返回图片IO流
     * @param text     二维码文件内容
     * @param width    生成二维码的宽度
     * @param height   生成二维码的高度
     * @throws WriterException
     * @throws IOException
     */
    public static BitMatrix generateQRCodeImageIO(String text) throws WriterException {
        Map hints = new HashMap();
        //二维码的宽高
        int width = 200;
        int height = 200;
        //设置UTF-8， 防止中文乱码
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        //设置二维码四周白色区域的大小
        hints.put(EncodeHintType.MARGIN, 0);
        //设置二维码的容错性
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);
        return bitMatrix;
    }
}
