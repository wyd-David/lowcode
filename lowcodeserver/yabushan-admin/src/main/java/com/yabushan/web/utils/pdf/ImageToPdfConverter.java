package com.yabushan.web.utils.pdf;

import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

public class ImageToPdfConverter {
    public static void main(String[] args) {
        try {
            // 创建文档对象  
            Document document = new Document();
            // 创建PDF写入器  
            PdfWriter.getInstance(document, new FileOutputStream("D:\\temp\\tt\\tt.pdf"));
            // 打开文档  
            document.open();
            String[] imagePaths= {"D:\\temp\\tt\\0.png","D:\\temp\\tt\\1.png","D:\\temp\\tt\\2.png","D:\\temp\\tt\\3.png","D:\\temp\\tt\\4.png"};
            // 添加图片到文档  
            for (String imagePath : imagePaths) {
                Image image = Image.getInstance(imagePath);
                document.add(image);
            }
            // 关闭文档  
            document.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }
}
