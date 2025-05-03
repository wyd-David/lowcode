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
import com.yabushan.system.domain.WarnProcessPushLog;
import com.yabushan.system.service.IWarnProcessPushLogService;
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
 * 待办推送记录Controller
 *
 * @author yabushan
 * @date 2022-04-28
 */
@RestController
@RequestMapping("/system/warnProcessPushLog")
@Api(tags = {"待办推送记录"})
public class WarnProcessPushLogController extends BaseController {
    @Autowired
    private IWarnProcessPushLogService warnProcessPushLogService;
    @Autowired
    private TokenService tokenService;

    /**
     * 查询待办推送记录列表
     */
    @ApiOperation("查询待办推送记录列表")
    @Log(title = "查询待办推送记录列表", businessType = BusinessType.OTHER)
    @PreAuthorize("@ss.hasPermi('system:warnProcessPushLog:list')")
    @GetMapping("/list")
    public TableDataInfo list(WarnProcessPushLog warnProcessPushLog) {
        startPage();
        List<WarnProcessPushLog> list = warnProcessPushLogService.selectWarnProcessPushLogList(warnProcessPushLog);
        return getDataTable(list);
    }

    /**
     * 导出待办推送记录列表
     */
    @ApiOperation("导出待办推送记录列表")
    @PreAuthorize("@ss.hasPermi('system:warnProcessPushLog:export')")
    @Log(title = "待办推送记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(WarnProcessPushLog warnProcessPushLog, HttpServletResponse response) throws IOException {
        List<WarnProcessPushLog> list = warnProcessPushLogService.selectWarnProcessPushLogList(warnProcessPushLog);
        AjaxResult ajaxResult = ExcelUtils.exportExcel(list, "待办推送记录", "待办推送记录", WarnProcessPushLog.class,
                "待办推送记录.xlsx", response);
        return ajaxResult;
    }

    /**
     * 获取待办推送记录详细信息
     */
    @ApiOperation("获取待办推送记录详细信息")
    @PreAuthorize("@ss.hasPermi('system:warnProcessPushLog:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(warnProcessPushLogService.selectWarnProcessPushLogById(id));
    }

    /**
     * 新增待办推送记录
     */
    @ApiOperation("新增待办推送记录")
    @PreAuthorize("@ss.hasPermi('system:warnProcessPushLog:add')")
    @Log(title = "待办推送记录", businessType = BusinessType.INSERT)
    @PostMapping
    @RepeatSubmit
    public AjaxResult add(@RequestBody WarnProcessPushLog warnProcessPushLog) {
        return toAjax(warnProcessPushLogService.insertWarnProcessPushLog(warnProcessPushLog));
    }

    /**
     * 修改待办推送记录
     */
    @ApiOperation("修改待办推送记录")
    @PreAuthorize("@ss.hasPermi('system:warnProcessPushLog:edit')")
    @Log(title = "待办推送记录", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @RepeatSubmit
    public AjaxResult edit(@RequestBody WarnProcessPushLog warnProcessPushLog) {
        return toAjax(warnProcessPushLogService.updateWarnProcessPushLog(warnProcessPushLog));
    }

    /**
     * 删除待办推送记录
     */
    @ApiOperation("删除待办推送记录")
    @PreAuthorize("@ss.hasPermi('system:warnProcessPushLog:remove')")
    @Log(title = "待办推送记录", businessType = BusinessType.DELETE)
    @GetMapping("/remove")
    @RepeatSubmit
    public AjaxResult remove(@RequestParam("id") Long[] id) {
        return toAjax(warnProcessPushLogService.deleteWarnProcessPushLogByIds(id));
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
        List<WarnProcessPushLog> list = new ArrayList<>();
        AjaxResult ajaxResult = ExcelUtils.exportExcel(list, "待办推送记录模板", "待办推送记录", WarnProcessPushLog.class,
                "待办推送记录导入模板.xlsx", response);
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
    @Log(title = "待办推送记录", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('system:warnProcessPushLog:import')")
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
        ExcelImportResult<WarnProcessPushLog> requestList = ExcelImportUtil.importExcelMore(inputStream, WarnProcessPushLog.class,
                importParams);
        //导入的相应数据
        List<WarnProcessPushLog> list = requestList.getList();
        //调用getImgName方法获取图片名，这里是根据你配置的地址来存放的，也可以在实体类里面配置存放地址，需要可以开放
        //List<WxPortalApplyInfo> infoList = getImgName(list);
        //判断是否有错误
        if (requestList.isVerfiyFail()) {
            //getFailList()里面的就是所有校验失败的excel数据
            List<WarnProcessPushLog> failList = requestList.getFailList();
            ExportParams params = new ExportParams();
            params.setSheetName("待办推送记录错误数据");
            Workbook workbook = ExcelExportUtil.exportExcel(params, WarnProcessPushLog.class, failList);
            return EasypoiUtil.downLoadExcel("待办推送记录错误数据.xlsx", response, workbook);
        } else {
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            String operName = loginUser.getUsername();
            List<WarnProcessPushLog> warnProcessPushLogs = warnProcessPushLogService.importWarnProcessPushLog(list, false, operName);
            //清理掉里面缓存的数据
            warnProcessPushLogService.clearwarnProcessPushLogInfo();
            ExportParams params = new ExportParams();
            params.setSheetName("待办推送记录导入数据");
            Workbook workbook = ExcelExportUtil.exportExcel(params, WarnProcessPushLog.class, warnProcessPushLogs);
            return EasypoiUtil.downLoadExcel("待办推送记录导入数据.xlsx", response, workbook);
        }
    }

    /**
     * 根据传来的id集合修改禁用启用状态
     */
    @ApiOperation("根据传来的id集合修改禁用启用状态")
    @PreAuthorize("@ss.hasPermi('system:warnProcessPushLog:updateByIds')")
    @GetMapping("/updateByIds")
    public AjaxResult updateByIds(String ids, String disableEnableState) {
        Boolean isByIds = warnProcessPushLogService.updateByIds(ids, disableEnableState);
        if (isByIds) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }
}
