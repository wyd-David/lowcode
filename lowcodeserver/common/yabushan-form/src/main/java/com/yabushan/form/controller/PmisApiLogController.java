package com.yabushan.form.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.yabushan.common.annotation.Log;
import com.yabushan.common.annotation.RepeatSubmit;
import com.yabushan.common.core.controller.BaseController;
import com.yabushan.common.core.domain.AjaxResult;
import com.yabushan.common.core.domain.model.LoginUser;
import com.yabushan.common.core.page.TableDataInfo;
import com.yabushan.common.enums.BusinessType;
import com.yabushan.common.utils.ServletUtils;
import com.yabushan.form.domain.PmisApiLog;
import com.yabushan.form.service.IPmisApiLogService;
import com.yabushan.framework.web.service.TokenService;
import com.yabushan.poi.utils.EasypoiUtil;
import com.yabushan.poi.utils.ExcelUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * API日志Controller
 *
 * @author yabushan
 * @date 2023-07-29
 */
@RestController
@RequestMapping("/ApiLog/ApiLog")
@Api(tags = {"API日志"})
public class PmisApiLogController extends BaseController {
    @Autowired
    private IPmisApiLogService pmisApiLogService;
    @Autowired
    private TokenService tokenService;

/**
 * 查询API日志列表
 */
@ApiOperation("查询API日志列表")
@Log(title = "查询API日志列表", businessType = BusinessType.OTHER)
@PreAuthorize("@ss.hasPermi('ApiLog:ApiLog:list')")
@GetMapping("/list")
        public TableDataInfo list(PmisApiLog pmisApiLog) {
        startPage();
        List<PmisApiLog> list = pmisApiLogService.selectPmisApiLogList(pmisApiLog);
        return getDataTable(list);
    }
    
    /**
     * 导出API日志列表
     */
    @ApiOperation("导出API日志列表")
    @PreAuthorize("@ss.hasPermi('ApiLog:ApiLog:export')")
    @Log(title = "API日志", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(PmisApiLog pmisApiLog, HttpServletResponse response) throws IOException {
        List<PmisApiLog> list = pmisApiLogService.selectPmisApiLogList(pmisApiLog);
        AjaxResult ajaxResult = ExcelUtils.exportExcel(list, "API日志", "API日志", PmisApiLog. class,
        "API日志.xlsx", response);
        return ajaxResult;
    }

    /**
     * 获取API日志详细信息
     */
    @ApiOperation("获取API日志详细信息")
    @PreAuthorize("@ss.hasPermi('ApiLog:ApiLog:query')")
    @GetMapping(value = "/{logId}")
    public AjaxResult getInfo(@PathVariable("logId") String logId) {
        return AjaxResult.success(pmisApiLogService.selectPmisApiLogById(logId));
    }

    /**
     * 新增API日志
     */
    @ApiOperation("新增API日志")
    @PreAuthorize("@ss.hasPermi('ApiLog:ApiLog:add')")
    @Log(title = "API日志", businessType = BusinessType.INSERT)
    @PostMapping
    @RepeatSubmit
    public AjaxResult add(@RequestBody PmisApiLog pmisApiLog) {
        return toAjax(pmisApiLogService.insertPmisApiLog(pmisApiLog));
    }

    /**
     * 修改API日志
     */
    @ApiOperation("修改API日志")
    @PreAuthorize("@ss.hasPermi('ApiLog:ApiLog:edit')")
    @Log(title = "API日志", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @RepeatSubmit
    public AjaxResult edit(@RequestBody PmisApiLog pmisApiLog) {
        return toAjax(pmisApiLogService.updatePmisApiLog(pmisApiLog));
    }

    /**
     * 删除API日志
     */
    @ApiOperation("删除API日志")
    @PreAuthorize("@ss.hasPermi('ApiLog:ApiLog:remove')")
    @Log(title = "API日志", businessType = BusinessType.DELETE)
    @GetMapping("/remove")
    @RepeatSubmit
    public AjaxResult remove(@RequestParam("logId") String[] logId) {
        return toAjax(pmisApiLogService.deletePmisApiLogByIds(logId));
    }

    /**
    * 数据导入数据库的下载模板
    * @return
    * @throws Exception
    */
    @ApiOperation("数据导入数据库的下载模板")
    @GetMapping("/importTemplate")
    public AjaxResult importTemplate(HttpServletResponse response) throws IOException {
        List<PmisApiLog> list = new ArrayList<>();
        AjaxResult ajaxResult = ExcelUtils.exportExcel(list, "API日志模板", "API日志", PmisApiLog. class,
        "API日志导入模板.xlsx", response);
        return ajaxResult;
    }

    /**
    * 数据导入数据库
    * @param file 数据列表
    * @param updateSupport 是否更新支持，如果已存在，则进行更新数据
    * @return
    * @throws Exception
    */
    @ApiOperation("数据导入数据库")
    @Log(title = "API日志", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('ApiLog:ApiLog:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport, HttpServletResponse response) throws Exception {
        InputStream inputStream = file.getInputStream();
        ImportParams importParams = new ImportParams();
        importParams.setHeadRows(1);
        //标题
        importParams.setTitleRows(1);
        // 是否需要验证
        importParams.setNeedVerfiy(true);
        //
        ExcelImportResult<PmisApiLog> requestList = ExcelImportUtil.importExcelMore(inputStream, PmisApiLog. class,
        importParams);
        //导入的相应数据
        List<PmisApiLog> list = requestList.getList();
        //调用getImgName方法获取图片名，这里是根据你配置的地址来存放的，也可以在实体类里面配置存放地址，需要可以开放
        //List<WxPortalApplyInfo> infoList = getImgName(list);
        //判断是否有错误
        if (requestList.isVerfiyFail()) {
            //getFailList()里面的就是所有校验失败的excel数据
            List<PmisApiLog> failList = requestList.getFailList();
            ExportParams params = new ExportParams();
            params.setSheetName("API日志错误数据");
            Workbook workbook = ExcelExportUtil.exportExcel(params, PmisApiLog. class,failList);
            return EasypoiUtil.downLoadExcel("API日志错误数据.xlsx", response, workbook);
        } else {
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            String operName = loginUser.getUsername();
            List<PmisApiLog> pmisApiLogs = pmisApiLogService.importPmisApiLog(list, false, operName);
            //清理掉里面缓存的数据
                pmisApiLogService.clearpmisApiLogInfo();
            ExportParams params = new ExportParams();
            params.setSheetName("API日志导入数据");
            Workbook workbook = ExcelExportUtil.exportExcel(params, PmisApiLog. class,pmisApiLogs);
            return EasypoiUtil.downLoadExcel("API日志导入数据.xlsx", response, workbook);
        }
    }

    /**
    * 根据传来的id集合修改禁用启用状态
    */
    @ApiOperation("根据传来的id集合修改禁用启用状态")
    @PreAuthorize("@ss.hasPermi('ApiLog:ApiLog:updateByIds')")
    @GetMapping("/updateByIds")
    public AjaxResult updateByIds(String ids, String disableEnableState) {
        Boolean isByIds = pmisApiLogService.updateByIds(ids, disableEnableState);
        if (isByIds) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }
}
