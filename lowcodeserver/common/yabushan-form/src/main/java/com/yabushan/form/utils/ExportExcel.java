package com.yabushan.form.utils;
import com.yabushan.common.core.domain.AjaxResult;
import com.yabushan.form.domain.LowFormFiledInfo;
import com.yabushan.poi.utils.ExcelUtils;
import org.apache.poi.ss.usermodel.Workbook;
import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
public class ExportExcel {

    public AjaxResult exportDeliveryExpense(HttpServletResponse response, List< Map<String, Object>> map,  List<LowFormFiledInfo> formFiledInfos,String fileName) {
        //获取第一行的字段名称
        StringBuffer cnName = new StringBuffer();
        //获取数据的key值
        StringBuffer enName = new StringBuffer();
        for(int i =0 ;i<formFiledInfos.size();i++){
            cnName.append(formFiledInfos.get(i).getFiledLabel()).append(",");
            enName.append(formFiledInfos.get(i).getEntityName()).append(",");

        }
        cnName.append("创建时间").append(",").append("创建人");
        enName.append("CREATED_TIME").append(",").append("CREATED_BY");

        List<ExcelExportEntity> entityAll = new ArrayList<>();

        List<ExcelExportEntity> entity = setExcelExportEntity(cnName.toString().split(","), enName.toString().split(","));

        // 文件导出
        return export(response, entity, map, fileName);
    }

    public List<ExcelExportEntity> setExcelExportEntity(String[] nameArr, String[] codeArr) {
        List<ExcelExportEntity> entity = new ArrayList<>();
        for(int i=0; i < nameArr.length; i++) {
            entity.add(new ExcelExportEntity(nameArr[i], codeArr[i]));
        }
        return entity;
    }

    public AjaxResult export(HttpServletResponse response, List<ExcelExportEntity> entity, List<Map<String, Object>> dtoLst, String fileName) {
        try {
            // easyPoi操作excel
            Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(), entity, dtoLst);
            return ExcelUtils.downLoadExcel(fileName, response, workbook);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
