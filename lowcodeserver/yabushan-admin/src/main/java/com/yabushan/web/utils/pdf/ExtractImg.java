package com.yabushan.web.utils.pdf;

import com.spire.pdf.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class ExtractImg {
    public static void main(String[] args) throws Exception{
        //加载测试文档
        PdfDocument pdf = new PdfDocument();
        pdf.loadFromFile("D:\\temp\\tt\\8300010640.pdf");

        //定义一个int型变量
        int index = 0;

        //遍历PDF每一页
        for (int i= 0;i< pdf.getPages().getCount(); i ++){
            //获取PDF页面
            PdfPageBase page = pdf.getPages().get(i);

            //使用extractImages方法获取页面上图片
            for (BufferedImage image : page.extractImages()) {
                System.out.println(">>>>>>>");
                //指定输出图片名称
                File output = new File( String.format("D:\\temp\\tt\\Image_%d.png", index++));
                //将图片保存为PNG格式文件
                ImageIO.write(image, "PNG", output);
            }
        }
    }
}
