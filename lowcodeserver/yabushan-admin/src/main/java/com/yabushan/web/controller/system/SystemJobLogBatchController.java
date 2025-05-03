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
import com.yabushan.system.domain.SystemJobLogBatch;
import com.yabushan.system.service.ISystemJobLogBatchService;
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
 * 日志数据批次 Controller
 *
 * @author yabushan
 * @date 2021-11-16
 */
@RestController
@RequestMapping("/system/sysJobLogBatch")
@Api(tags = {"日志数据批次 "})
public class SystemJobLogBatchController extends BaseController {
    @Autowired
    private ISystemJobLogBatchService systemJobLogBatchService;
    @Autowired
    private TokenService tokenService;

    /**
     * 查询日志数据批次 列表
     */
    @ApiOperation("查询日志数据批次 列表")
    @Log(title = "查询日志数据批次列表", businessType = BusinessType.OTHER)
    @GetMapping("/list")
    public TableDataInfo list(SystemJobLogBatch systemJobLogBatch) {
        startPage();
        List<SystemJobLogBatch> list = systemJobLogBatchService.selectSystemJobLogBatchList(systemJobLogBatch);
        return getDataTable(list);
    }

    /**
     * 导出日志数据批次 列表
     */
    @ApiOperation("导出日志数据批次 列表")
    @Log(title = "日志数据批次 ", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SystemJobLogBatch systemJobLogBatch, HttpServletResponse response) throws IOException {
        List<SystemJobLogBatch> list = systemJobLogBatchService.selectSystemJobLogBatchList(systemJobLogBatch);
        AjaxResult ajaxResult = ExcelUtils.exportExcel(list, "日志数据批次 ", "日志数据批次 ", SystemJobLogBatch.class, "日志数据批次 .xlsx", response);
        return ajaxResult;
    }

    /**
     * 获取日志数据批次 详细信息
     */
    @ApiOperation("获取日志数据批次 详细信息")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return AjaxResult.success(systemJobLogBatchService.selectSystemJobLogBatchById(id));
    }

    /**
     * 新增日志数据批次
     */
    @ApiOperation("新增日志数据批次 ")
    @Log(title = "日志数据批次 ", businessType = BusinessType.INSERT)
    @PostMapping
    @RepeatSubmit
    public AjaxResult add(@RequestBody SystemJobLogBatch systemJobLogBatch) {
        return toAjax(systemJobLogBatchService.insertSystemJobLogBatch(systemJobLogBatch));
    }

    /**
     * 修改日志数据批次
     */
    @ApiOperation("修改日志数据批次 ")
    @Log(title = "日志数据批次 ", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @RepeatSubmit
    public AjaxResult edit(@RequestBody SystemJobLogBatch systemJobLogBatch) {
        return toAjax(systemJobLogBatchService.updateSystemJobLogBatch(systemJobLogBatch));
    }

    /**
     * 删除日志数据批次
     */
    @ApiOperation("删除日志数据批次 ")
    @Log(title = "日志数据批次 ", businessType = BusinessType.DELETE)
    @GetMapping("/remove")
    @RepeatSubmit
    public AjaxResult remove(@RequestParam("id") Long[] id) {
        return toAjax(systemJobLogBatchService.deleteSystemJobLogBatchByIds(id));
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
        List<SystemJobLogBatch> list = new ArrayList<>();
        AjaxResult ajaxResult = ExcelUtils.exportExcel(list, "日志数据批次 模板", "日志数据批次 ", SystemJobLogBatch.class, "日志数据批次 导入模板.xlsx", response);
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
    @Log(title = "日志数据批次 ", businessType = BusinessType.IMPORT)
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
        ExcelImportResult<SystemJobLogBatch> requestList = ExcelImportUtil.importExcelMore(inputStream, SystemJobLogBatch.class, importParams);
        //导入的相应数据
        List<SystemJobLogBatch> list = requestList.getList();
        //调用getImgName方法获取图片名，这里是根据你配置的地址来存放的，也可以在实体类里面配置存放地址，需要可以开放
        //List<WxPortalApplyInfo> infoList = getImgName(list);
        //判断是否有错误
        if (requestList.isVerfiyFail()) {
            //getFailList()里面的就是所有校验失败的excel数据
            List<SystemJobLogBatch> failList = requestList.getFailList();
            ExportParams params = new ExportParams();
            params.setSheetName("日志数据批次 错误数据");
            Workbook workbook = ExcelExportUtil.exportExcel(params, SystemJobLogBatch.class, failList);
            return EasypoiUtil.downLoadExcel("日志数据批次 错误数据.xlsx", response, workbook);
        } else {
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            String operName = loginUser.getUsername();
            List<SystemJobLogBatch> systemJobLogBatchs = systemJobLogBatchService.importSystemJobLogBatch(list, false, operName);
            //清理掉里面缓存的数据
            systemJobLogBatchService.clearsystemJobLogBatchInfo();
            ExportParams params = new ExportParams();
            params.setSheetName("日志数据批次 导入数据");
            Workbook workbook = ExcelExportUtil.exportExcel(params, SystemJobLogBatch.class, systemJobLogBatchs);
            return EasypoiUtil.downLoadExcel("日志数据批次 导入数据.xlsx", response, workbook);
        }
    }

    /**
     * 根据传来的id集合修改禁用启用状态
     */
    @ApiOperation("根据传来的id集合修改禁用启用状态")
    @Log(title = "根据传来的id集合修改禁用启用状态", businessType = BusinessType.UPDATE)
    @GetMapping("/updateByIds")
    public AjaxResult updateByIds(String ids, String disableEnableState) {
        Boolean isByIds = systemJobLogBatchService.updateByIds(ids, disableEnableState);
        if (isByIds) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }

}
