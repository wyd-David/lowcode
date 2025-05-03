package com.yabushan.poi.utils;

import com.spire.xls.*;

public class ExcelToHtmlUtils {


    /**
     * excel转html
     * @param excelPath
     * @param htmlPath
     * @return
     */
    public static String  excleTohtml(String excelPath,String htmlPath) throws Exception {
        boolean b = ExcelTransformer.excelToHtml(excelPath, htmlPath);
        return htmlPath;
    }

    public static void main(String[] args) {
        String excelPath = "D:\\test\\test-convert.xlsx";
        String htmlPath = "D:\\test\\test-convert.html";
       try{
           String s = excleTohtml(excelPath, htmlPath);
           System.out.println(s);
       }catch (Exception e ){
           e.printStackTrace();
       }



    }





}
