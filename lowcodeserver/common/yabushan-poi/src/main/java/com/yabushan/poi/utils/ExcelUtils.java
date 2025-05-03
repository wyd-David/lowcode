package com.yabushan.poi.utils;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.yabushan.common.config.RuoYiConfig;
import com.yabushan.common.core.domain.AjaxResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExcelUtils {
    /**
     * excel 导出
     *
     * @param list           数据
     * @param title          标题
     * @param sheetName      sheet名称
     * @param pojoClass      pojo类型
     * @param fileName       文件名称
     * @param isCreateHeader 是否创建表头
     * @param response
     */
    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName, boolean isCreateHeader, HttpServletResponse response) throws IOException {
        ExportParams exportParams = new ExportParams(title, sheetName, ExcelType.XSSF);
        exportParams.setCreateHeadRows(isCreateHeader);
        defaultExport(list, pojoClass, fileName, response, exportParams);
    }

    /**
     * excel  (有下拉框的情况)导出
     *
     * @param list      数据
     * @param title     标题
     * @param sheetName sheet名称
     * @param pojoClass pojo类型
     * @param fileName  文件名称
     * @param response
     */
    public static AjaxResult exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName, HttpServletResponse response, Map<Integer, String[]> map) throws IOException {
        return defaultExport(list, pojoClass, fileName, sheetName, response, new ExportParams(title, sheetName, ExcelType.XSSF), map);
    }
    /**
     * excel  (有下拉框的情况)导出增加颜色
     *
     * @param list      数据
     * @param title     标题
     * @param sheetName sheet名称
     * @param pojoClass pojo类型
     * @param fileName  文件名称
     * @param response
     */
    public static AjaxResult exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName, HttpServletResponse response, Map<Integer, String[]> map,int row) throws IOException {
        return defaultExport(list, pojoClass, fileName, sheetName, response, new ExportParams(title, sheetName, ExcelType.XSSF), map,row);
    }

    /**
     * excel 导出
     *
     * @param list      数据
     * @param title     标题
     * @param sheetName sheet名称
     * @param pojoClass pojo类型
     * @param fileName  文件名称
     * @param response
     */
    public static AjaxResult exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName, HttpServletResponse response) throws IOException {
        return defaultExport(list, pojoClass, fileName, response, new ExportParams(title, sheetName, ExcelType.XSSF));
    }

    /**
     * excel 导出
     *
     * @param list      数据
     * @param title     标题
     * @param sheetName sheet名称
     * @param pojoClass pojo类型
     *                  row 多少行
     * @param fileName  文件名称
     * @param response
     */
    public static AjaxResult exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName, HttpServletResponse response, int row) throws IOException {
        return defaultExport(list, pojoClass, fileName, response, new ExportParams(title, sheetName, ExcelType.XSSF), row);
    }


    /**
     * excel 导出
     *
     * @param list         数据
     * @param pojoClass    pojo类型
     * @param fileName     文件名称
     * @param response
     * @param exportParams 导出参数
     */
    public static void exportExcel(List<?> list, Class<?> pojoClass, String fileName, ExportParams exportParams, HttpServletResponse response) throws IOException {
        defaultExport(list, pojoClass, fileName, response, exportParams);
    }

    /**
     * excel 导出
     *
     * @param list     数据
     * @param fileName 文件名称
     * @param response
     */
    public static void exportExcel(List<Map<String, Object>> list, String fileName, HttpServletResponse response) throws IOException {
        defaultExport(list, fileName, response);
    }

    /**
     * 默认的 excel 导出
     *
     * @param list         数据
     * @param pojoClass    pojo类型
     * @param fileName     文件名称
     * @param response
     * @param exportParams 导出参数
     */
    private static AjaxResult defaultExport(List<?> list, Class<?> pojoClass, String fileName, HttpServletResponse response, ExportParams exportParams) throws IOException {
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
        return downLoadExcel(fileName, response, workbook);
    }

    /**
     * 默认的 excel 导出
     *
     * @param list         数据
     * @param pojoClass    pojo类型
     * @param fileName     文件名称
     * @param response     row 多少行
     * @param exportParams 导出参数
     */
    private static AjaxResult defaultExport(List<?> list, Class<?> pojoClass, String fileName, HttpServletResponse response, ExportParams exportParams, int row) throws IOException {
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
        Sheet sheetAt = workbook.getSheetAt(0);
        // 表格行数

        int lastRowNum = sheetAt.getLastRowNum();

        // 获取列数

        int physicalNumberOfCells = sheetAt.getRow(0).getPhysicalNumberOfCells();

        //开始遍历单元格并进行判断是否渲染

        for (int i = 0; i < row; i++) {

            //获取每行对象

            Row rows = sheetAt.getRow(i);

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

                cellStyle.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.getIndex());
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
        return downLoadExcel(fileName, response, workbook);
    }
    /**
     * 有下拉框的 excel 导出
     *
     * @param list         数据
     * @param pojoClass    pojo类型
     * @param fileName     文件名称
     * @param response
     * @param exportParams 导出参数
     */
    private static AjaxResult defaultExport(List<?> list, Class<?> pojoClass, String fileName, String
            sheetName, HttpServletResponse response, ExportParams exportParams, Map<Integer, String[]> map) throws
            IOException {
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
        selectList(workbook, map, sheetName);
        return downLoadExcel(fileName, response, workbook);
    }
    /**
     * 有下拉框的 excel 导出 增加表头颜色
     *
     * @param list         数据
     * @param pojoClass    pojo类型
     * @param fileName     文件名称
     * @param response
     * @param exportParams 导出参数
     */
    private static AjaxResult defaultExport(List<?> list, Class<?> pojoClass, String fileName, String
            sheetName, HttpServletResponse response, ExportParams exportParams, Map<Integer, String[]> map,int row) throws
            IOException {
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
        selectList(workbook, map, sheetName);
        return downLoadExcel(fileName, response, workbook);
    }

    /**
     * 默认的 excel 导出
     *
     * @param list     数据
     * @param fileName 文件名称
     * @param response
     */
    private static void defaultExport(List<Map<String, Object>> list, String fileName, HttpServletResponse
            response) throws IOException {
        Workbook workbook = ExcelExportUtil.exportExcel(list, ExcelType.HSSF);
        downLoadExcel(fileName, response, workbook);
    }


    /**
     * 下载
     *
     * @param fileName 文件名称
     * @param response
     * @param workbook excel数据
     */
    public static AjaxResult downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) throws
            IOException {
        try {
            OutputStream out = null;
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/json");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName + "." + ExcelTypeEnum.XLSX.getValue(), "UTF-8"));
            out = new FileOutputStream(getAbsoluteFile(fileName));
            // out = response.getOutputStream();
            workbook.write(out);
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
        return AjaxResult.success(fileName);
    }

    /**
     * firstRow 開始行號 根据此项目，默认为2(下标0开始)
     * lastRow  根据此项目，默认为最大65535
     * firstCol 区域中第一个单元格的列号 (下标0开始)
     * lastCol 区域中最后一个单元格的列号
     * strings 下拉内容
     */
    public static void selectList(Workbook workbook, Map<Integer, String[]> map, String sheetName) {
        Sheet sheet = workbook.getSheet(sheetName);
        for (int key : map.keySet()) {
            if (sheet instanceof XSSFSheet) {
                XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper((XSSFSheet) sheet);
                XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper
                        .createExplicitListConstraint(map.get(key));
                CellRangeAddressList addressList = new CellRangeAddressList(2, 65535, key - 1, key - 1);
                XSSFDataValidation validation = (XSSFDataValidation) dvHelper.createValidation(dvConstraint, addressList);
                validation.setSuppressDropDownArrow(true);
                validation.setShowErrorBox(true);
                sheet.addValidationData(validation);
            } else if (sheet instanceof HSSFSheet) {
                CellRangeAddressList addressList = new CellRangeAddressList(2, 65535, key - 1, key - 1);
                DVConstraint dvConstraint = DVConstraint.createExplicitListConstraint(map.get(key));
                DataValidation validation = new HSSFDataValidation(addressList, dvConstraint);
                validation.setSuppressDropDownArrow(true);
                validation.setShowErrorBox(true);
                sheet.addValidationData(validation);
            }
        }
    }

    /**
     * 获取下载路径
     *
     * @param filename 文件名称
     */
    public static String getAbsoluteFile(String filename) {
        String downloadPath = RuoYiConfig.getDownloadPath() + filename;
        File desc = new File(downloadPath);
        if (!desc.getParentFile().exists()) {
            desc.getParentFile().mkdirs();
        }
        return downloadPath;
    }

    /**
     * excel 导入
     *
     * @param filePath   excel文件路径
     * @param titleRows  标题行
     * @param headerRows 表头行
     * @param pojoClass  pojo类型
     * @param <T>
     * @return
     */
    public static <
            T> List<T> importExcel(String filePath, Integer titleRows, Integer headerRows, Class<T> pojoClass) throws
            IOException {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        params.setNeedSave(true);
        params.setSaveUrl("/excel/");
        try {
            return ExcelImportUtil.importExcel(new File(filePath), pojoClass, params);
        } catch (NoSuchElementException e) {
            throw new IOException("模板不能为空");
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }

    /**
     * excel 导入
     *
     * @param file      excel文件
     * @param pojoClass pojo类型
     * @param <T>
     * @return
     */
    public static <T> List<T> importExcel(MultipartFile file, Class<T> pojoClass) throws IOException {
        return importExcel(file, 1, 1, pojoClass);
    }

    /**
     * excel 导入
     *
     * @param file       excel文件
     * @param titleRows  标题行
     * @param headerRows 表头行
     * @param pojoClass  pojo类型
     * @param <T>
     * @return
     */
    public static <
            T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows, Class<T> pojoClass) throws
            IOException {
        return importExcel(file, titleRows, headerRows, false, pojoClass);
    }

    /**
     * excel 导入
     *
     * @param file       上传的文件
     * @param titleRows  标题行
     * @param headerRows 表头行
     * @param needVerfiy 是否检验excel内容
     * @param pojoClass  pojo类型
     * @param <T>
     * @return
     */
    public static <T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows,
                                          boolean needVerfiy, Class<T> pojoClass) throws IOException {
        if (file == null) {
            return null;
        }
        try {
            return importExcel(file.getInputStream(), titleRows, headerRows, needVerfiy, pojoClass);
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }

    /**
     * excel 导入
     *
     * @param inputStream 文件输入流
     * @param titleRows   标题行
     * @param headerRows  表头行
     * @param needVerfiy  是否检验excel内容
     * @param pojoClass   pojo类型
     * @param <T>
     * @return
     */
    public static <T> List<T> importExcel(InputStream inputStream, Integer titleRows, Integer headerRows,
                                          boolean needVerfiy, Class<T> pojoClass) throws IOException {
        if (inputStream == null) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        params.setSaveUrl("/excel/");
        params.setNeedSave(true);
        params.setNeedVerfiy(needVerfiy);
        try {
            return ExcelImportUtil.importExcel(inputStream, pojoClass, params);
        } catch (NoSuchElementException e) {
            throw new IOException("excel文件不能为空");
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }

    /**
     * Excel 类型枚举
     */
    enum ExcelTypeEnum {
        XLS("xls"), XLSX("xlsx");
        private String value;

        ExcelTypeEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }



    public static AjaxResult replaceExcel(String fileName, List<Map<String,Object>> list,HttpServletResponse response) throws IOException, org.apache.poi.openxml4j.exceptions.InvalidFormatException {
         InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
         Workbook wb = WorkbookFactory.create(is);
        for(int n=0;n<=list.size()-1;n++){
            Sheet sheet = wb.getSheetAt(n);//获取Excel的工作表sheet，下标从0开始。
            Map<String,Object> params=list.get(n);
            String name = (String) list.get(n).get("name");
            wb.setSheetName(n,"("+(n+1)+")"+"名片申请详情-"+name);
            if(n<=list.size()-2){
                wb.cloneSheet(n);
            }
            int trLength = sheet.getLastRowNum();//获取Excel的行数
            for (int i = 0; i < trLength; i++) {
                Row row = sheet.getRow(i);//获取Excel的行，下标从0开始
                if (row == null) {//若行为空，则遍历下一行
                    continue;
                }
                int minColIx = row.getFirstCellNum();
                int maxColIx = row.getLastCellNum();
                for (int colIx = minColIx; colIx < maxColIx; colIx++) {
                    Cell cell = row.getCell(colIx);//获取指定单元格，单元格从左到右下标从0开始
                    String runText = cell.getStringCellValue();
                    if (runText.equals("")){
                        continue;
                    }
                    System.out.println(cell);
                    Matcher matcher = matcher(runText);
                    if (matcher.find()) {
                        while ((matcher = matcher(runText)).find()) {
                            runText = matcher.replaceFirst(String.valueOf(params.get(matcher.group(1))));
                            if("null".equals(runText)){
                                runText="";
                            }
                        }
                        cell.setCellValue(runText);
                    }
                }
            }
        }
        return downLoadExcel("名片信息.xls", response, wb);

//        OutputStream out = new FileOutputStream(new File(outPath));
//        wb.write(out);
//        is.close();
//        out.close();
    }


    /**
     * 正则匹配字符串
     * @param str
     * @return
     */
    private static Matcher matcher(String str) {
        Pattern pattern = Pattern.compile("\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        return matcher;
    }

}
