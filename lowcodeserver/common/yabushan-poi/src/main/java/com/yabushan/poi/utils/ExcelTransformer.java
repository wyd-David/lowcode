package com.yabushan.poi.utils;


import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.converter.ExcelToHtmlConverter;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;

/**
 * excel07... 转03
 */
public class ExcelTransformer {

    private int lastColumn = 0;
    private HashMap<Integer, HSSFCellStyle> styleMap = new HashMap();
    public static final String EXCEL_XLS = ".xls";
    public static final String EXCEL_XLSX = ".xlsx";

    public void transformXSSF(XSSFWorkbook workbookOld, HSSFWorkbook workbookNew) {
        HSSFSheet sheetNew;
        XSSFSheet sheetOld;

        workbookNew.setMissingCellPolicy(workbookOld.getMissingCellPolicy());

        for (int i = 0; i < workbookOld.getNumberOfSheets(); i++) {
            sheetOld = workbookOld.getSheetAt(i);
            sheetNew = workbookNew.getSheet(sheetOld.getSheetName());
            sheetNew = workbookNew.createSheet(sheetOld.getSheetName());
            this.transform(workbookOld, workbookNew, sheetOld, sheetNew);
        }
    }

    private void transform(XSSFWorkbook workbookOld, HSSFWorkbook workbookNew,
                           XSSFSheet sheetOld, HSSFSheet sheetNew) {

        sheetNew.setDisplayFormulas(sheetOld.isDisplayFormulas());
        sheetNew.setDisplayGridlines(sheetOld.isDisplayGridlines());
        sheetNew.setDisplayGuts(sheetOld.getDisplayGuts());
        sheetNew.setDisplayRowColHeadings(sheetOld.isDisplayRowColHeadings());
        sheetNew.setDisplayZeros(sheetOld.isDisplayZeros());
        sheetNew.setFitToPage(sheetOld.getFitToPage());

        sheetNew.setHorizontallyCenter(sheetOld.getHorizontallyCenter());
        sheetNew.setMargin(Sheet.BottomMargin,
                sheetOld.getMargin(Sheet.BottomMargin));
        sheetNew.setMargin(Sheet.FooterMargin,
                sheetOld.getMargin(Sheet.FooterMargin));
        sheetNew.setMargin(Sheet.HeaderMargin,
                sheetOld.getMargin(Sheet.HeaderMargin));
        sheetNew.setMargin(Sheet.LeftMargin,
                sheetOld.getMargin(Sheet.LeftMargin));
        sheetNew.setMargin(Sheet.RightMargin,
                sheetOld.getMargin(Sheet.RightMargin));
        sheetNew.setMargin(Sheet.TopMargin, sheetOld.getMargin(Sheet.TopMargin));
        sheetNew.setPrintGridlines(sheetNew.isPrintGridlines());
        sheetNew.setRightToLeft(sheetNew.isRightToLeft());
        sheetNew.setRowSumsBelow(sheetNew.getRowSumsBelow());
        sheetNew.setRowSumsRight(sheetNew.getRowSumsRight());
        sheetNew.setVerticallyCenter(sheetOld.getVerticallyCenter());

        HSSFRow rowNew;
        for (Row row : sheetOld) {
            rowNew = sheetNew.createRow(row.getRowNum());
            if (rowNew != null)
                this.transform(workbookOld, workbookNew, (XSSFRow) row, rowNew);
        }

        for (int i = 0; i < this.lastColumn; i++) {
            sheetNew.setColumnWidth(i, sheetOld.getColumnWidth(i));
            sheetNew.setColumnHidden(i, sheetOld.isColumnHidden(i));
        }

        for (int i = 0; i < sheetOld.getNumMergedRegions(); i++) {
            CellRangeAddress merged = sheetOld.getMergedRegion(i);
            sheetNew.addMergedRegion(merged);
        }
    }

    private void transform(XSSFWorkbook workbookOld, HSSFWorkbook workbookNew,
                           XSSFRow rowOld, HSSFRow rowNew) {
        HSSFCell cellNew;
        rowNew.setHeight(rowOld.getHeight());

        for (Cell cell : rowOld) {
            cellNew = rowNew.createCell(cell.getColumnIndex(),
                    cell.getCellType());
            if (cellNew != null)
                this.transform(workbookOld, workbookNew, (XSSFCell) cell,
                        cellNew);
        }
        this.lastColumn = Math.max(this.lastColumn, rowOld.getLastCellNum());
    }

    private void transform(XSSFWorkbook workbookOld, HSSFWorkbook workbookNew,
                           XSSFCell cellOld, HSSFCell cellNew) {
        cellNew.setCellComment(cellOld.getCellComment());

        Integer hash = cellOld.getCellStyle().hashCode();
        if (this.styleMap != null && !this.styleMap.containsKey(hash)) {
            this.transform(workbookOld, workbookNew, hash,
                    cellOld.getCellStyle(),
                    (HSSFCellStyle) workbookNew.createCellStyle());
        }
        cellNew.setCellStyle(this.styleMap.get(hash));

        switch (cellOld.getCellType()) {
            case Cell.CELL_TYPE_BLANK:
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                cellNew.setCellValue(cellOld.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_ERROR:
                cellNew.setCellValue(cellOld.getErrorCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA:
                cellNew.setCellValue(cellOld.getCellFormula());
                break;
            case Cell.CELL_TYPE_NUMERIC:
                cellNew.setCellValue(cellOld.getNumericCellValue());
                break;
            case Cell.CELL_TYPE_STRING:
                cellNew.setCellValue(cellOld.getStringCellValue());
                break;
            default:
                System.out.println("transform: Unbekannter Zellentyp "
                        + cellOld.getCellType());
        }
    }

    private void transform(XSSFWorkbook workbookOld, HSSFWorkbook workbookNew,
                           Integer hash, XSSFCellStyle styleOld, HSSFCellStyle styleNew) {
        styleNew.setAlignment(styleOld.getAlignment());
        styleNew.setBorderBottom(styleOld.getBorderBottom());
        styleNew.setBorderLeft(styleOld.getBorderLeft());
        styleNew.setBorderRight(styleOld.getBorderRight());
        styleNew.setBorderTop(styleOld.getBorderTop());
        styleNew.setDataFormat(this.transform(workbookOld, workbookNew,
                styleOld.getDataFormat()));
        styleNew.setFillBackgroundColor(styleOld.getFillBackgroundColor());
        styleNew.setFillForegroundColor(styleOld.getFillForegroundColor());
        styleNew.setFillPattern(styleOld.getFillPattern());
        styleNew.setFont(this.transform(workbookNew,
                (XSSFFont) styleOld.getFont()));
        styleNew.setHidden(styleOld.getHidden());
        styleNew.setIndention(styleOld.getIndention());
        styleNew.setLocked(styleOld.getLocked());
        styleNew.setVerticalAlignment(styleOld.getVerticalAlignment());
        styleNew.setWrapText(styleOld.getWrapText());
        this.styleMap.put(hash, styleNew);
    }

    private short transform(XSSFWorkbook workbookOld, HSSFWorkbook workbookNew,
                            short index) {
        DataFormat formatOld = workbookOld.createDataFormat();
        DataFormat formatNew = workbookNew.createDataFormat();
        return formatNew.getFormat(formatOld.getFormat(index));
    }

    private HSSFFont transform(HSSFWorkbook workbookNew, XSSFFont fontOld) {
        HSSFFont fontNew = workbookNew.createFont();
        fontNew.setBoldweight(fontOld.getBoldweight());
        fontNew.setCharSet(fontOld.getCharSet());
        fontNew.setColor(fontOld.getColor());
        fontNew.setFontName(fontOld.getFontName());
        fontNew.setFontHeight(fontOld.getFontHeight());
        fontNew.setItalic(fontOld.getItalic());
        fontNew.setStrikeout(fontOld.getStrikeout());
        fontNew.setTypeOffset(fontOld.getTypeOffset());
        fontNew.setUnderline(fontOld.getUnderline());
        return fontNew;
    }

    /**
     * 转换成html
     * @param fileType
     * @param input
     * @return
     * @throws Exception
     */
    public byte[] transforToHtml(String fileType,InputStream input) throws Exception {
        HSSFWorkbook excelBook = new HSSFWorkbook();
        // 判断Excel文件将07+版本转换为03版本
        if (EXCEL_XLSX.equals(fileType)) { // Excel 2007/2010
            ExcelTransformer excelTransformer = new ExcelTransformer();
            XSSFWorkbook workbookOld = new XSSFWorkbook(input);
            excelTransformer.transformXSSF(workbookOld, excelBook);
        }else {
            excelBook = new HSSFWorkbook(input);
        }
        ExcelToHtmlConverter excelToHtmlConverter = new ExcelToHtmlConverter(
                DocumentBuilderFactory.newInstance().newDocumentBuilder()
                        .newDocument());
        excelToHtmlConverter.setOutputColumnHeaders(false);
        excelToHtmlConverter.setOutputRowNumbers(false);
        excelToHtmlConverter.processWorkbook(excelBook);
//      List pics = excelBook.getAllPictures();
//      if (pics != null) {
//          for (int i = 0; i < pics.size(); i++) {
//              Picture pic = (Picture) pics.get(i);
//              try {
//                  pic.writeImageContent(new FileOutputStream(
//                          path + pic.suggestFullFileName()));
//              } catch (FileNotFoundException e) {
//                  e.printStackTrace();
//              }
//          }
//      }
        Document htmlDocument = excelToHtmlConverter.getDocument();
        prettyHtml(htmlDocument);
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        DOMSource domSource = new DOMSource(htmlDocument);
        StreamResult streamResult = new StreamResult(outStream);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer serializer = tf.newTransformer();
        serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");
        serializer.setOutputProperty(OutputKeys.METHOD, "html");
        serializer.transform(domSource, streamResult);
        outStream.close();
        byte[] htmlFile= outStream.toByteArray();
        return htmlFile;
    }

    /**
     * 美化多页签excel
     * @param htmlDocument
     */
    private void prettyHtml(Document htmlDocument) {
        // 处理
        Node head = htmlDocument.getElementsByTagName("head").item(0);
        Node body = htmlDocument.getElementsByTagName("body").item(0);
        Element tabNode = htmlDocument.createElement("div");
        Element style = (Element) htmlDocument.getElementsByTagName("style").item(0);
        style.getFirstChild().setNodeValue(style.getFirstChild().getNodeValue() +
                ".titleList.active,.titleList:hover{background-color:#0097e5}\r\n" +
                ".titleList{cursor:pointer;padding:3px 15px;display:inline-block;font-weight:400;font-size:18px;background:#00a8ff;color:#fff;border-radius:4px}\r\n" +
                ".tableList:not(:first){display:none}");
        Element scriptNode1 = htmlDocument.createElement("script");
        scriptNode1.setAttribute("type", "text/javascript");
        scriptNode1.setAttribute("src", "https://code.jquery.com/jquery-1.9.1.js");
        head.appendChild(scriptNode1);
        Element scriptNode2 = htmlDocument.createElement("script");
        scriptNode2.setAttribute("type", "text/javascript");
        Text scriptNodeText2 = htmlDocument.createTextNode("$(document).ready(function(){$('.titleList').click(function(){$(this).addClass('active').siblings('.titleList').removeClass('active');$('.tableList:eq('+$(this).index()+')').show().siblings('.tableList').hide()})});");
        scriptNode2.appendChild(scriptNodeText2);
        head.appendChild(scriptNode2);
        NodeList headList = htmlDocument.getElementsByTagName("h2");
        for(int i = 0;i<headList.getLength();i++) {
            Element node = (Element)headList.item(i);
            if(i == 0) {
                node.setAttribute("class", "titleList active");
                body.insertBefore(tabNode, node);
            }else {
                node.setAttribute("class", "titleList");
            }
            Element tableNode = (Element)node.getNextSibling();
            tableNode.setAttribute("class", tableNode.getAttribute("class") + " tableList");
            body.removeChild(node);
            tabNode.appendChild(node);
        }
    }

    public static boolean excelToHtml(String excelPath,String htmlPath) throws Exception {
        InputStream input = new FileInputStream(excelPath);
        ExcelTransformer excelTransformer = new ExcelTransformer();
        byte[] htmlData = excelTransformer.transforToHtml(".xlsx", input);
        FileUtils.writeByteArrayToFile(new File(htmlPath), htmlData);
        return true;
    }

    public static void main(String args[]) throws Exception {
        String path="D:\\test\\";
        InputStream input = new FileInputStream(path + "test-convert.xlsx");
        ExcelTransformer excelTransformer = new ExcelTransformer();
        byte[] htmlData = excelTransformer.transforToHtml(".xlsx", input);
        FileUtils.writeByteArrayToFile(new File(path, "convert1111111.html"), htmlData);
        System.out.println("Success!");
    }
}
