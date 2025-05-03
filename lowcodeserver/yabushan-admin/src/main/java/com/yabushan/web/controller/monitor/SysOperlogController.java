package com.yabushan.web.controller.monitor;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.yabushan.common.annotation.Log;
import com.yabushan.common.core.controller.BaseController;
import com.yabushan.common.core.domain.AjaxResult;
import com.yabushan.common.core.page.TableDataInfo;
import com.yabushan.common.enums.BusinessType;
import com.yabushan.common.utils.poi.ExcelUtil;
import com.yabushan.system.domain.SysOperLog;
import com.yabushan.system.service.ISysOperLogService;

/**
 * 操作日志记录
 *
 * @author yabushan
 */
@RestController
@RequestMapping("/monitor/operlog")
@Api(tags = "操作日志记录")
public class SysOperlogController extends BaseController
{
    @Autowired
    private ISysOperLogService operLogService;

   // @PreAuthorize("@ss.hasPermi('monitor:operlog:list')")
   // @Log(title = "查询操作日志", businessType = BusinessType.OTHER)
    @GetMapping("/list")
    @ApiOperation(value = "查询操作日志")
    public TableDataInfo list(SysOperLog operLog)
    {
        startPage();
        List<SysOperLog> list = operLogService.selectOperLogList(operLog);
        return getDataTable(list);
    }

    @Log(title = "操作日志", businessType = BusinessType.EXPORT)
    //@PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @GetMapping("/export")
    @ApiOperation(value = "导出操作日志")
    public AjaxResult export(SysOperLog operLog)
    {
        List<SysOperLog> list = operLogService.selectOperLogList(operLog);
        ExcelUtil<SysOperLog> util = new ExcelUtil<SysOperLog>(SysOperLog.class);
        return util.exportExcel(list, "操作日志");
    }

    @PreAuthorize("@ss.hasPermi('monitor:operlog:remove')")
    @DeleteMapping("/{operIds}")
    @ApiOperation(value = "删除操作日志")
    public AjaxResult remove(@PathVariable Long[] operIds)
    {
        return toAjax(operLogService.deleteOperLogByIds(operIds));
    }

    @Log(title = "操作日志", businessType = BusinessType.CLEAN)
   // @PreAuthorize("@ss.hasPermi('monitor:operlog:remove')")
    @DeleteMapping("/clean")
    @ApiOperation(value = "清除操作日志")
    public AjaxResult clean()
    {
        operLogService.cleanOperLog();
        return AjaxResult.success();
    }
}
