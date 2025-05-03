package com.yabushan.web.controller.system;

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
import com.yabushan.framework.web.service.TokenService;
import com.yabushan.poi.utils.EasypoiUtil;
import com.yabushan.poi.utils.ExcelUtils;
import com.yabushan.system.domain.SystemJobLog;
import com.yabushan.system.service.ISystemJobLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 定时任务日志 Controller
 *
 * @author yabushan
 * @date 2021-11-16
 */
@RestController
@RequestMapping("/system/sysJobLog")
@Api(tags = {"定时任务日志 "})
public class SystemJobLogController extends BaseController {
    @Autowired
    private ISystemJobLogService systemJobLogService;
    @Autowired
    private TokenService tokenService;

    /**
     * 查询定时任务日志 列表
     */
    @ApiOperation("查询定时任务日志 列表")
    @Log(title = "查询定时任务日志列表", businessType = BusinessType.OTHER)
    @GetMapping("/list")
    public TableDataInfo list(SystemJobLog systemJobLog) {
        startPage();
        List<SystemJobLog> list = systemJobLogService.selectSystemJobLogList(systemJobLog);
        return getDataTable(list);
    }

    /**
     * 导出定时任务日志 列表
     */
    @ApiOperation("导出定时任务日志 列表")
    @Log(title = "定时任务日志 ", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SystemJobLog systemJobLog, HttpServletResponse response) throws IOException {
        List<SystemJobLog> list = systemJobLogService.selectSystemJobLogList(systemJobLog);
        AjaxResult ajaxResult = ExcelUtils.exportExcel(list, "定时任务日志 ", "定时任务日志 ", SystemJobLog.class, "定时任务日志 .xlsx", response);
        return ajaxResult;
    }

    /**
     * 获取定时任务日志 详细信息
     */
    @ApiOperation("获取定时任务日志 详细信息")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return AjaxResult.success(systemJobLogService.selectSystemJobLogById(id));
    }

    /**
     * 新增定时任务日志
     */
    @ApiOperation("新增定时任务日志 ")
    @Log(title = "定时任务日志 ", businessType = BusinessType.INSERT)
    @PostMapping
    @RepeatSubmit
    public AjaxResult add(@RequestBody SystemJobLog systemJobLog) {
        return toAjax(systemJobLogService.insertSystemJobLog(systemJobLog));
    }

    /**
     * 修改定时任务日志
     */
    @ApiOperation("修改定时任务日志 ")
    @Log(title = "定时任务日志 ", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @RepeatSubmit
    public AjaxResult edit(@RequestBody SystemJobLog systemJobLog) {
        return toAjax(systemJobLogService.updateSystemJobLog(systemJobLog));
    }

    /**
     * 删除定时任务日志
     */
    @ApiOperation("删除定时任务日志 ")
    @Log(title = "定时任务日志 ", businessType = BusinessType.DELETE)
    @GetMapping("/remove")
    @RepeatSubmit
    public AjaxResult remove(@RequestParam("id") Long[] id) {
        return toAjax(systemJobLogService.deleteSystemJobLogByIds(id));
    }


    /**
     * 数据导入数据库的下载模板
     *
     * @return
     * @throws Exception
     */
    @ApiOperation("数据导入数据库的下载模板")
    @GetMapping("/importTemplate")
    public AjaxResult importTemplate(HttpServletResponse response) throws IOException {
        List<SystemJobLog> list = new ArrayList<>();
        AjaxResult ajaxResult = ExcelUtils.exportExcel(list, "定时任务日志 模板", "定时任务日志 ", SystemJobLog.class, "定时任务日志 导入模板.xlsx", response);
        return ajaxResult;
    }

    /**
     * 数据导入数据库
     *
     * @param file          数据列表
     * @param updateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return
     * @throws Exception
     */
    @ApiOperation("数据导入数据库")
    @Log(title = "定时任务日志 ", businessType = BusinessType.IMPORT)
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
        ExcelImportResult<SystemJobLog> requestList = ExcelImportUtil.importExcelMore(inputStream, SystemJobLog.class, importParams);
        //导入的相应数据
        List<SystemJobLog> list = requestList.getList();
        //调用getImgName方法获取图片名，这里是根据你配置的地址来存放的，也可以在实体类里面配置存放地址，需要可以开放
        //List<WxPortalApplyInfo> infoList = getImgName(list);
        //判断是否有错误
        if (requestList.isVerfiyFail()) {
            //getFailList()里面的就是所有校验失败的excel数据
            List<SystemJobLog> failList = requestList.getFailList();
            ExportParams params = new ExportParams();
            params.setSheetName("定时任务日志 错误数据");
            Workbook workbook = ExcelExportUtil.exportExcel(params, SystemJobLog.class, failList);
            return EasypoiUtil.downLoadExcel("定时任务日志 错误数据.xlsx", response, workbook);
        } else {
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            String operName = loginUser.getUsername();
            List<SystemJobLog> systemJobLogs = systemJobLogService.importSystemJobLog(list, false, operName);
            //清理掉里面缓存的数据
            systemJobLogService.clearsystemJobLogInfo();
            ExportParams params = new ExportParams();
            params.setSheetName("定时任务日志 导入数据");
            Workbook workbook = ExcelExportUtil.exportExcel(params, SystemJobLog.class, systemJobLogs);
            return EasypoiUtil.downLoadExcel("定时任务日志 导入数据.xlsx", response, workbook);
        }
    }

    /**
     * 根据传来的id集合修改禁用启用状态
     */
    @ApiOperation("根据传来的id集合修改禁用启用状态")
    @Log(title = "根据传来的id集合修改禁用启用状态", businessType = BusinessType.UPDATE)
    @GetMapping("/updateByIds")
    public AjaxResult updateByIds(String ids, String disableEnableState) {
        Boolean isByIds = systemJobLogService.updateByIds(ids, disableEnableState);
        if (isByIds) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }

}
