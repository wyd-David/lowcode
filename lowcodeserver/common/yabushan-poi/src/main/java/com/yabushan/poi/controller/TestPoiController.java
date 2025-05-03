package com.yabushan.poi.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.yabushan.common.core.domain.AjaxResult;
import com.yabushan.poi.utils.EasypoiUtil;
import com.yabushan.poi.utils.ExcelUtils;
import com.yabushan.poi.vo.PersonExportVo;
import com.yabushan.poi.vo.WorkSheetExcelRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class TestPoiController {


    /**
     * 导入
     *
     * @param file
     */
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public AjaxResult importExcel(@RequestParam("file") MultipartFile file) {
        long start = System.currentTimeMillis();
       try{
           List<PersonExportVo> personVoList = ExcelUtils.importExcel(file, PersonExportVo.class);
           log.debug(personVoList.toString());
       }catch(Exception e){
           log.error("导入失败！"+e.toString());
       }
        log.debug("导入excel所花时间：" + (System.currentTimeMillis() - start));
        return AjaxResult.success("导入成功！");
    }


    /**
     * 导出
     *
     * @param response
     */
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void exportExcel(HttpServletResponse response) throws IOException {
        long start = System.currentTimeMillis();
        List<PersonExportVo> personList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            PersonExportVo personVo = new PersonExportVo();
            personVo.setName("张三" + i);
            personVo.setUsername("张三" + i);
            personVo.setPhoneNumber("18888888888");
            personVo.setImageUrl("");
            personList.add(personVo);
        }
        log.debug("导出excel所花时间：" + (System.currentTimeMillis() - start));
        ExcelUtils.exportExcel(personList, "员工信息表", "员工信息", PersonExportVo.class, "员工信息", response);
    }



    @GetMapping("/workSheet/upload")
    public void importExcel(@RequestParam("file") MultipartFile file, HttpServletResponse response) throws Exception {
        String fileName = file.getOriginalFilename();
        boolean fileIsExcel = Objects.requireNonNull(fileName).matches("^.+\\.(?i)(xls)$") || fileName.matches("^.+\\.(?i)(xlsx)$");

        InputStream inputStream = file.getInputStream();
        ImportParams importParams = new ImportParams();
        // 需要验证
        importParams.setNeedVerfiy(true);
        ExcelImportResult<WorkSheetExcelRequest> requestList = ExcelImportUtil.importExcelMore(inputStream, WorkSheetExcelRequest.class, importParams);
        List<WorkSheetExcelRequest> list = requestList.getList();
        list = list.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(WorkSheetExcelRequest :: getWorkSheetName))), ArrayList::new));

       // workSheetService.importExcel(list);

        //判断是否有错误
        if(requestList.isVerfiyFail()){
            //getFailList()里面的就是所有校验失败的excel数据
            List<WorkSheetExcelRequest> failList = requestList.getFailList();
            System.out.println(failList);
            ExportParams params = new ExportParams();
            params.setSheetName("工单批量导入错误数据");
            Workbook workbook = ExcelExportUtil.exportExcel(params, WorkSheetExcelRequest.class, failList);
            EasypoiUtil.downLoadExcel("workSheet_error.xls", response, workbook);
        }
    }


    //从项目根路径导出
    @PostMapping( "/workSheet/download")
    public void exportExcel1(HttpServletResponse response) throws IOException {
        ClassPathResource resource = new ClassPathResource("excel/工单.xlsx");
        Workbook workbook = new XSSFWorkbook(resource.getInputStream());
        try {
            EasypoiUtil.downLoadExcel("WorkOrderTemplate", response, workbook);
        } catch (Exception e) {
           log.error(e.toString());
        }
    }


}
