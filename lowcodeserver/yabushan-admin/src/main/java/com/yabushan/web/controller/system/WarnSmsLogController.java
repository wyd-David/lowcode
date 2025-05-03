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
import com.yabushan.system.domain.WarnSmsLog;
import com.yabushan.system.service.IWarnSmsLogService;
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
 * 短信日志记录Controller
 *
 * @author yabushan
 * @date 2022-05-10
 */
@RestController
@RequestMapping("/system/warnSmsLog")
@Api(tags = {"短信日志记录"})
public class WarnSmsLogController extends BaseController {
    @Autowired
    private IWarnSmsLogService warnSmsLogService;
    @Autowired
    private TokenService tokenService;

    /**
     * 查询短信日志记录列表
     */
    @ApiOperation("查询短信日志记录列表")
    @Log(title = "查询短信日志记录列表", businessType = BusinessType.OTHER)
    @PreAuthorize("@ss.hasPermi('system:warnSmsLog:list')")
    @GetMapping("/list")
    public TableDataInfo list(WarnSmsLog warnSmsLog) {
        startPage();
        List<WarnSmsLog> list = warnSmsLogService.selectWarnSmsLogList(warnSmsLog);
        return getDataTable(list);
    }

    /**
     * 导出短信日志记录列表
     */
    @ApiOperation("导出短信日志记录列表")
    @PreAuthorize("@ss.hasPermi('system:warnSmsLog:export')")
    @Log(title = "短信日志记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(WarnSmsLog warnSmsLog, HttpServletResponse response) throws IOException {
        List<WarnSmsLog> list = warnSmsLogService.selectWarnSmsLogList(warnSmsLog);
        AjaxResult ajaxResult = ExcelUtils.exportExcel(list, "短信日志记录", "短信日志记录", WarnSmsLog.class,
                "短信日志记录.xlsx", response);
        return ajaxResult;
    }

    /**
     * 获取短信日志记录详细信息
     */
    @ApiOperation("获取短信日志记录详细信息")
    @PreAuthorize("@ss.hasPermi('system:warnSmsLog:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return AjaxResult.success(warnSmsLogService.selectWarnSmsLogById(id));
    }

    /**
     * 新增短信日志记录
     */
    @ApiOperation("新增短信日志记录")
    @PreAuthorize("@ss.hasPermi('system:warnSmsLog:add')")
    @Log(title = "短信日志记录", businessType = BusinessType.INSERT)
    @PostMapping
    @RepeatSubmit
    public AjaxResult add(@RequestBody WarnSmsLog warnSmsLog) {
        return toAjax(warnSmsLogService.insertWarnSmsLog(warnSmsLog));
    }

    /**
     * 修改短信日志记录
     */
    @ApiOperation("修改短信日志记录")
    @PreAuthorize("@ss.hasPermi('system:warnSmsLog:edit')")
    @Log(title = "短信日志记录", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @RepeatSubmit
    public AjaxResult edit(@RequestBody WarnSmsLog warnSmsLog) {
        return toAjax(warnSmsLogService.updateWarnSmsLog(warnSmsLog));
    }

    /**
     * 删除短信日志记录
     */
    @ApiOperation("删除短信日志记录")
    @PreAuthorize("@ss.hasPermi('system:warnSmsLog:remove')")
    @Log(title = "短信日志记录", businessType = BusinessType.DELETE)
    @GetMapping("/remove")
    @RepeatSubmit
    public AjaxResult remove(@RequestParam("id") String[] id) {
        return toAjax(warnSmsLogService.deleteWarnSmsLogByIds(id));
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
        List<WarnSmsLog> list = new ArrayList<>();
        AjaxResult ajaxResult = ExcelUtils.exportExcel(list, "短信日志记录模板", "短信日志记录", WarnSmsLog.class,
                "短信日志记录导入模板.xlsx", response);
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
    @Log(title = "短信日志记录", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('system:warnSmsLog:import')")
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
        ExcelImportResult<WarnSmsLog> requestList = ExcelImportUtil.importExcelMore(inputStream, WarnSmsLog.class,
                importParams);
        //导入的相应数据
        List<WarnSmsLog> list = requestList.getList();
        //调用getImgName方法获取图片名，这里是根据你配置的地址来存放的，也可以在实体类里面配置存放地址，需要可以开放
        //List<WxPortalApplyInfo> infoList = getImgName(list);
        //判断是否有错误
        if (requestList.isVerfiyFail()) {
            //getFailList()里面的就是所有校验失败的excel数据
            List<WarnSmsLog> failList = requestList.getFailList();
            ExportParams params = new ExportParams();
            params.setSheetName("短信日志记录错误数据");
            Workbook workbook = ExcelExportUtil.exportExcel(params, WarnSmsLog.class, failList);
            return EasypoiUtil.downLoadExcel("短信日志记录错误数据.xlsx", response, workbook);
        } else {
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            String operName = loginUser.getUsername();
            List<WarnSmsLog> warnSmsLogs = warnSmsLogService.importWarnSmsLog(list, false, operName);
            //清理掉里面缓存的数据
            warnSmsLogService.clearwarnSmsLogInfo();
            ExportParams params = new ExportParams();
            params.setSheetName("短信日志记录导入数据");
            Workbook workbook = ExcelExportUtil.exportExcel(params, WarnSmsLog.class, warnSmsLogs);
            return EasypoiUtil.downLoadExcel("短信日志记录导入数据.xlsx", response, workbook);
        }
    }

    /**
     * 根据传来的id集合修改禁用启用状态
     */
    @ApiOperation("根据传来的id集合修改禁用启用状态")
    @PreAuthorize("@ss.hasPermi('system:warnSmsLog:updateByIds')")
    @GetMapping("/updateByIds")
    public AjaxResult updateByIds(String ids, String disableEnableState) {
        Boolean isByIds = warnSmsLogService.updateByIds(ids, disableEnableState);
        if (isByIds) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }
}
