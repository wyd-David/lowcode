package com.yabushan.web.controller.common;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.yabushan.common.annotation.Log;
import com.yabushan.common.core.controller.BaseController;
import com.yabushan.common.core.domain.AjaxResult;
import com.yabushan.common.core.domain.model.LoginUser;
import com.yabushan.common.core.page.TableDataInfo;
import com.yabushan.common.enums.BusinessType;
import com.yabushan.common.utils.ServletUtils;
import com.yabushan.framework.web.service.TokenService;
import com.yabushan.poi.utils.EasypoiUtil;
import com.yabushan.poi.utils.ExcelUtils;
import com.yabushan.system.domain.SysConfigCalendar;
import com.yabushan.system.service.ISysConfigCalendarService;
import com.yabushan.system.service.impl.SysConfigCalendarServiceImpl;
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
 * 日历配置Controller
 *
 * @author 姜森焱
 * @date 2021-09-08
 */
@RestController
@RequestMapping("/SysConfigCalendar/SysConfigCalendar")
@Api(tags = {"日历配置"})
public class SysConfigCalendarController extends BaseController {
    @Autowired
    private ISysConfigCalendarService sysConfigCalendarService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private SysConfigCalendarServiceImpl sysConfigCalendarServiceimpl;

    /**
     * 查询日历配置列表
     */
    @ApiOperation("查询日历配置列表")
//    @PreAuthorize("@ss.hasPermi('SysConfigCalendar:SysConfigCalendar:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysConfigCalendar sysConfigCalendar) {
        startPage();
        List<SysConfigCalendar> list = sysConfigCalendarService.selectSysConfigCalendarList(sysConfigCalendar);
        return getDataTable(list);
    }

    /**
     * 导出日历配置列表
     */
    @ApiOperation("导出日历配置列表")
    @PreAuthorize("@ss.hasPermi('SysConfigCalendar:SysConfigCalendar:export')")
    @Log(title = "日历配置", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysConfigCalendar sysConfigCalendar, HttpServletResponse response) throws IOException {
        List<SysConfigCalendar> list = sysConfigCalendarService.selectSysConfigCalendarList(sysConfigCalendar);
        AjaxResult ajaxResult = ExcelUtils.exportExcel(list, "日历配置", "日历配置", SysConfigCalendar.class, "日历配置.xlsx", response);
        return ajaxResult;
    }

    /**
     * 获取日历配置详细信息
     */
    @ApiOperation("获取日历配置详细信息")
    @PreAuthorize("@ss.hasPermi('SysConfigCalendar:SysConfigCalendar:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return AjaxResult.success(sysConfigCalendarService.selectSysConfigCalendarById(id));
    }

    /**
     * 新增日历配置
     */
    @ApiOperation("新增日历配置")
//    @PreAuthorize("@ss.hasPermi('SysConfigCalendar:SysConfigCalendar:add')")
    @Log(title = "日历配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysConfigCalendar sysConfigCalendar) {

        return toAjax(sysConfigCalendarService.insertSysConfigCalendar(sysConfigCalendar));
    }

    /**
     * 修改日历配置
     */
    @ApiOperation("修改日历配置")
    @PreAuthorize("@ss.hasPermi('SysConfigCalendar:SysConfigCalendar:edit')")
    @Log(title = "日历配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysConfigCalendar sysConfigCalendar) {
        return toAjax(sysConfigCalendarService.updateSysConfigCalendar(sysConfigCalendar));
    }

    /**
     * 删除日历配置
     */
    @ApiOperation("删除日历配置")
    @PreAuthorize("@ss.hasPermi('SysConfigCalendar:SysConfigCalendar:remove')")
    @Log(title = "日历配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(sysConfigCalendarService.deleteSysConfigCalendarByIds(ids));
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
        List<SysConfigCalendar> list = new ArrayList<>();
        AjaxResult ajaxResult = ExcelUtils.exportExcel(list, "日历配置模板", "日历配置", SysConfigCalendar.class, "日历配置导入模板.xlsx", response);
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
    @Log(title = "日历配置", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('SysConfigCalendar:SysConfigCalendar:import')")
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
        ExcelImportResult<SysConfigCalendar> requestList = ExcelImportUtil.importExcelMore(inputStream, SysConfigCalendar.class, importParams);
        //导入的相应数据
        List<SysConfigCalendar> list = requestList.getList();
        //调用getImgName方法获取图片名，这里是根据你配置的地址来存放的，也可以在实体类里面配置存放地址，需要可以开放
        //List<WxPortalApplyInfo> infoList = getImgName(list);
        //判断是否有错误
        if (requestList.isVerfiyFail()) {
            //getFailList()里面的就是所有校验失败的excel数据
            List<SysConfigCalendar> failList = requestList.getFailList();
            ExportParams params = new ExportParams();
            params.setSheetName("日历配置错误数据");
            Workbook workbook = ExcelExportUtil.exportExcel(params, SysConfigCalendar.class, failList);
            return EasypoiUtil.downLoadExcel("日历配置错误数据.xlsx", response, workbook);
        } else {
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            String operName = loginUser.getUsername();
            List<SysConfigCalendar> sysConfigCalendars = sysConfigCalendarService.importSysConfigCalendar(list, false, operName);
            //清理掉里面缓存的数据
            sysConfigCalendarServiceimpl.clearsysConfigCalendarInfo();
            ExportParams params = new ExportParams();
            params.setSheetName("日历配置导入数据");
            Workbook workbook = ExcelExportUtil.exportExcel(params, SysConfigCalendar.class, sysConfigCalendars);
            return EasypoiUtil.downLoadExcel("日历配置导入数据.xlsx", response, workbook);
        }
    }

    /**
     * 根据传来的id集合修改禁用启用状态
     */
    @ApiOperation("根据传来的id集合修改禁用启用状态")
    @PreAuthorize("@ss.hasPermi('SysConfigCalendar:SysConfigCalendar:updateByIds')")
    @GetMapping("/updateByIds")
    public AjaxResult updateByIds(String ids, String disableEnableState) {
        Boolean isByIds = sysConfigCalendarService.updateByIds(ids, disableEnableState);
        if (isByIds) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }
}
