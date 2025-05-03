package com.yabushan.poi.controller;




import com.spire.xls.FileFormat;
import com.spire.xls.Workbook;

import java.io.*;

public class ExcelToPdfController {

    public static void main(String[] args) throws IOException {

       //创建一个Workbook实例并加载Excel文件
        Workbook workbook = new Workbook();
        workbook.loadFromFile("D:\\test\\test-convert.xlsx");

        //设置转换后的PDF页面高宽适应工作表的内容大小
        workbook.getConverterSetting().setSheetFitToPage(true);

        //将生成的文档保存到指定路径
       // workbook.saveToFile("D:\\test\\test-convert.html", FileFormat.HTML);
        workbook.saveToFile("D:\\test\\test-convert-pdf.pdf", FileFormat.PDF);
        //fileZh("D:\\test\\test-convert.pdf","D:\\test\\test-convert12.pdf");
    }





}
