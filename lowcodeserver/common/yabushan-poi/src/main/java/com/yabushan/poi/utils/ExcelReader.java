package com.yabushan.poi.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 读取Excel文件内容
 * @author leij
 * @date 2021-10-27 10:56:19
 */
public class ExcelReader {
	Workbook wb = null;

	public ExcelReader(String path) {
		try {
			try {
				wb = new XSSFWorkbook(new FileInputStream(path));
			} catch (Exception ex) {
				wb = new HSSFWorkbook(new FileInputStream(path));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public ExcelReader(File file) {
		try {
			try {
				wb = new XSSFWorkbook(new FileInputStream(file));
			} catch (Exception ex) {
				wb = new HSSFWorkbook(new FileInputStream(file));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ExcelReader(MultipartFile file) {
		try {
			wb = new XSSFWorkbook(file.getInputStream());
		} catch (Exception ex) {
			try {
				wb = new HSSFWorkbook(file.getInputStream());
			} catch (Exception e) {
				wb = null;
				e.printStackTrace();
			} 
		}
	}
	
	public static void importExcel(File file){
		
	}
	
	public int getSheetCount(){
		return wb.getNumberOfSheets();
	}
	
	public Sheet getSheetByName(String sheetName){
		return wb.getSheet(sheetName);
	}
	
	public Sheet getSheetByIndex(int index){
		return wb.getSheetAt(index);
	}
	
	/**
	 * 取Excel所有数据
	 * @param reader
	 * @return
	 * @author Xiexp
	 * @date 2017年11月16日下午2:27:17
	 */
	public Map<Integer, List<String[]>> getAllData(ExcelReader reader) {
		if(reader == null || reader.wb == null) return null;
		Map<Integer, List<String[]>> map = new HashMap<Integer, List<String[]>>();
		int sheetCount = reader.getSheetCount();  //工作簿的数量
		if(sheetCount > 0){
			for(int rowIndex = 0; rowIndex < sheetCount; rowIndex++){
				Sheet sheet = reader.getSheetByIndex(rowIndex);
				if(sheet!=null){
					List<String[]> dataList = new ArrayList<String[]>();
					int columnNum = 0;
					if (sheet.getRow(0) != null) {
						columnNum= sheet.getRow(0).getLastCellNum();
					}
					if (columnNum > 0) {
						for (Row row : sheet) {
							String[] singleRow = new String[columnNum];
							int n = 0;
							for (short i = 0; i < columnNum; i++) {
								Cell cell = row.getCell(i, Row.CREATE_NULL_AS_BLANK);
								switch (cell.getCellType()) {
								case Cell.CELL_TYPE_BLANK:
									singleRow[n] = "";
									if(isMergedRegion(sheet, row.getRowNum(), i)){ //如果是合并单元格,value为上一行列值
										if(dataList.size() > 0) {
											singleRow[n] = dataList.get(dataList.size() - 1)[i];
										}
									}
									break;
								case Cell.CELL_TYPE_BOOLEAN:
									singleRow[n] = Boolean.toString(cell.getBooleanCellValue());
									break;
								// 数值
								case Cell.CELL_TYPE_NUMERIC:
									if (DateUtil.isCellDateFormatted(cell)) {
										Date date = cell.getDateCellValue();
										DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
										singleRow[n] = formater.format(date);
									} else {
										cell.setCellType(Cell.CELL_TYPE_STRING);
										String temp = cell.getStringCellValue();
										// 判断是否包含小数点，如果不含小数点，则以字符串读取，如果含小数点，则转换为Double类型的字符串
										if (temp.indexOf(".") > -1) {
											singleRow[n] = String.valueOf(new Double(temp)).trim();
										} else {
											singleRow[n] = temp.trim();
										}
									}
									break;
								case Cell.CELL_TYPE_STRING:
									singleRow[n] = cell.getStringCellValue().trim();
									break;
								case Cell.CELL_TYPE_ERROR:
									singleRow[n] = "";
									break;
								case Cell.CELL_TYPE_FORMULA:
									cell.setCellType(Cell.CELL_TYPE_STRING);
									singleRow[n] = cell.getStringCellValue();
									if (singleRow[n] != null) {
										singleRow[n] = singleRow[n].replaceAll("#N/A", "").trim();
									}
									break;
								default:
									singleRow[n] = "";
									break;
								}
								n++;
							}
							boolean isEmpty = true;
							for(String s: singleRow){
								if(s!=null && !"".equals(s)){
									isEmpty = false;
									break;
								}
							}
							// 如果为空行，跳过
							if (isEmpty) {
								continue;
							}
							dataList.add(singleRow);
						}
					}
					map.put(rowIndex+1, dataList);
				}
			}
		}
		
		return map;
	}
	
	/**
	 * 判断指定的单元格是否是合并单元格 
	 * @param sheet
	 * @param row 行下标   
	 * @param column 列下标
	 * @return
	 * @author Xiexp
	 * @data 2017年11月16日下午2:27:40
	 */
	private boolean isMergedRegion(Sheet sheet, int row , int column) {
    	int sheetMergeCount = sheet.getNumMergedRegions();  
        for (int i = 0; i < sheetMergeCount; i++) {  
        	CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();  
            int firstRow = range.getFirstRow();  
            int lastRow = range.getLastRow();  
            if(row >= firstRow && row <= lastRow){
            	if(column >= firstColumn && column <= lastColumn){
                	return true;  
            	}
            }  
        }
        return false;  
     }
	
}
