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
import com.yabushan.system.domain.SysLogininfor;
import com.yabushan.system.service.ISysLogininforService;

/**
 * 系统访问记录
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/monitor/logininfor")
@Api(tags = "系统访问记录")
public class SysLogininforController extends BaseController
{
    @Autowired
    private ISysLogininforService logininforService;

   // @PreAuthorize("@ss.hasPermi('monitor:logininfor:list')")
    @Log(title = "系统访问日志列表", businessType = BusinessType.OTHER)
    @GetMapping("/list")
    @ApiOperation(value = "系统访问日志列表")
    public TableDataInfo list(SysLogininfor logininfor)
    {
        startPage();
        if(!"1".equals(logininfor.getIsRepeat())){
            logininfor.setShowDate("");
        }
        List<SysLogininfor> list = logininforService.selectLogininforList(logininfor);
        return getDataTable(list);
    }

    @Log(title = "登录日志", businessType = BusinessType.EXPORT)
    //@PreAuthorize("@ss.hasPermi('monitor:logininfor:export')")
    @GetMapping("/export")
    @ApiOperation(value = "导出系统登录日志")
    public AjaxResult export(SysLogininfor logininfor)
    {
        List<SysLogininfor> list = logininforService.selectLogininforList(logininfor);
        ExcelUtil<SysLogininfor> util = new ExcelUtil<SysLogininfor>(SysLogininfor.class);
        return util.exportExcel(list, "登录日志");
    }

   // @PreAuthorize("@ss.hasPermi('monitor:logininfor:remove')")
    @Log(title = "登录日志", businessType = BusinessType.DELETE)
    @DeleteMapping("/{infoIds}")
    @ApiOperation(value = "删除登录日志")
    public AjaxResult remove(@PathVariable Long[] infoIds)
    {
        return toAjax(logininforService.deleteLogininforByIds(infoIds));
    }

    @PreAuthorize("@ss.hasPermi('monitor:logininfor:remove')")
    @Log(title = "登录日志", businessType = BusinessType.CLEAN)
    @DeleteMapping("/clean")
    @ApiOperation(value = "清除登录日志")
    public AjaxResult clean()
    {
        logininforService.cleanLogininfor();
        return AjaxResult.success();
    }
}
