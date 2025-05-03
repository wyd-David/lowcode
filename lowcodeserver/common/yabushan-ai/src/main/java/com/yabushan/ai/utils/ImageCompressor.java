package com.yabushan.ai.utils;

import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageCompressor {
    public static void main(String[] args) {
        try {
            File fileio = new File("D:\\work\\lowcode\\lowCode3.0\\low-code3\\lowcodeweb\\src\\assets\\images\\login-background1.jpg");
            File output = new File("D:\\work\\lowcode\\lowCode3.0\\low-code3\\lowcodeweb\\src\\assets\\images\\login-background.jpg");
            Thumbnails.of(fileio)
                    .scale(0.8f) //图片大小（长宽）压缩比例 从0-1，1表示原图
                    .outputQuality(0.8f) //图片质量压缩比例 从0-1，越接近1质量越好
                    .toFile(output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
