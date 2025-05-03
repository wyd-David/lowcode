package com.yabushan.poi.utils;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yabushan.common.core.domain.AjaxResult;
import org.apache.poi.ss.usermodel.*;

public abstract class EasypoiUtil {
    public EasypoiUtil() {
    }

    public static void templateExport(TemplateExportParams templateExcel, Map<String, Object> dataMap, String fileName, HttpServletResponse response) throws IOException {
        Workbook workbook = ExcelExportUtil.exportExcel(templateExcel, dataMap);
        if (workbook != null) {
            downLoadExcel(fileName, response, workbook, 2);
        }

    }

    public static AjaxResult downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook, int row) throws IOException {
        OutputStream out = null;
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-Type", "application/json");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        out = new FileOutputStream(ExcelUtils.getAbsoluteFile(fileName));
        workbook.write(out);
        Sheet sheetAt = workbook.getSheetAt(0);
        // 表格行数

        int lastRowNum = sheetAt.getLastRowNum();

        // 获取列数

        int physicalNumberOfCells = sheetAt.getRow(0).getPhysicalNumberOfCells();

        //开始遍历单元格并进行判断是否渲染

        for (int i = 0; i < 3; i++) {

            //获取每行对象
            Row rows = sheetAt.getRow(i);
            if (rows != null) {
                for (int j = 0; j < physicalNumberOfCells; j++) {

                    //获取单元格对象

                    Cell cell = rows.getCell(j);
                    //获取单元格样式对象

                    CellStyle cellStyle = workbook.createCellStyle();
                    //获取单元格内容对象

                    Font font = workbook.createFont();

                    //一定要装入 样式中才会生效

                    cellStyle.setFont(font);


                    //获取当前单元格 中的value

                    //设置单元格背景颜色

                    cellStyle.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
                    cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                    cellStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);
                    //设置用于底部边框的颜色
                    cellStyle.setBottomBorderColor(IndexedColors.WHITE.getIndex());
                    //设置用于左部边框的颜色
                    cellStyle.setLeftBorderColor(IndexedColors.WHITE.getIndex());
                    //设置用于右部边框的颜色
                    cellStyle.setRightBorderColor(IndexedColors.WHITE.getIndex());
                    //设置用于头部边框的颜色
                    cellStyle.setTopBorderColor(IndexedColors.WHITE.getIndex());

                    //设置用于单元格底部边框的边框类型
                    cellStyle.setBorderBottom(BorderStyle.THIN);
                    //设置用于单元格左边框的边框类型
                    cellStyle.setBorderLeft(BorderStyle.THIN);
                    //设置用于单元格右边框的边框类型
                    cellStyle.setBorderRight(BorderStyle.THIN);
                    //设置用于单元格顶部边框的边框类型
                    cellStyle.setBorderTop(BorderStyle.THIN);

                    //设置单元格字体颜色

                    font.setColor(IndexedColors.WHITE.getIndex());
                    font.setBold(true);


                    cell.setCellStyle(cellStyle);

                }
            }
        }
        return AjaxResult.success(fileName);
    }

    public static AjaxResult downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) throws IOException {
        OutputStream out = null;
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-Type", "application/json");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName.split("-", -1)[0], "UTF-8"));
        out = new FileOutputStream(ExcelUtils.getAbsoluteFile(fileName.split("-", -1)[0]));
        workbook.write(out);
        return AjaxResult.success(fileName);
    }

    private static String filename(HttpServletRequest request, String filename) throws Exception {
        String userAgent = request.getHeader("User-Agent");
        if (!userAgent.contains("MSIE") && !userAgent.contains("Trident")) {
            filename = new String(filename.getBytes("UTF-8"), "ISO-8859-1");
        } else {
            filename = URLEncoder.encode(filename, "UTF-8");
        }

        return filename;
    }
}
