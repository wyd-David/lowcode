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
import com.yabushan.system.domain.WarnSmsModel;
import com.yabushan.system.service.IWarnSmsModelService;
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
 * 短信模型Controller
 *
 * @author yabushan
 * @date 2022-05-10
 */
@RestController
@RequestMapping("/system/warnSmsModel")
@Api(tags = {"短信模型"})
public class WarnSmsModelController extends BaseController {
    @Autowired
    private IWarnSmsModelService warnSmsModelService;
    @Autowired
    private TokenService tokenService;

    /**
     * 查询短信模型列表
     */
    @ApiOperation("查询短信模型列表")
    @Log(title = "查询短信模型列表", businessType = BusinessType.OTHER)
    @PreAuthorize("@ss.hasPermi('system:warnSmsModel:list')")
    @GetMapping("/list")
    public TableDataInfo list(WarnSmsModel warnSmsModel) {
        startPage();
        List<WarnSmsModel> list = warnSmsModelService.selectWarnSmsModelList(warnSmsModel);
        return getDataTable(list);
    }

    /**
     * 导出短信模型列表
     */
    @ApiOperation("导出短信模型列表")
    @PreAuthorize("@ss.hasPermi('system:warnSmsModel:export')")
    @Log(title = "短信模型", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(WarnSmsModel warnSmsModel, HttpServletResponse response) throws IOException {
        List<WarnSmsModel> list = warnSmsModelService.selectWarnSmsModelList(warnSmsModel);
        AjaxResult ajaxResult = ExcelUtils.exportExcel(list, "短信模型", "短信模型", WarnSmsModel.class,
                "短信模型.xlsx", response);
        return ajaxResult;
    }

    /**
     * 获取短信模型详细信息
     */
    @ApiOperation("获取短信模型详细信息")
    @PreAuthorize("@ss.hasPermi('system:warnSmsModel:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return AjaxResult.success(warnSmsModelService.selectWarnSmsModelById(id));
    }

    /**
     * 新增短信模型
     */
    @ApiOperation("新增短信模型")
    @PreAuthorize("@ss.hasPermi('system:warnSmsModel:add')")
    @Log(title = "短信模型", businessType = BusinessType.INSERT)
    @PostMapping
    @RepeatSubmit
    public AjaxResult add(@RequestBody WarnSmsModel warnSmsModel) {
        return toAjax(warnSmsModelService.insertWarnSmsModel(warnSmsModel));
    }

    /**
     * 修改短信模型
     */
    @ApiOperation("修改短信模型")
    @PreAuthorize("@ss.hasPermi('system:warnSmsModel:edit')")
    @Log(title = "短信模型", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @RepeatSubmit
    public AjaxResult edit(@RequestBody WarnSmsModel warnSmsModel) {
        return toAjax(warnSmsModelService.updateWarnSmsModel(warnSmsModel));
    }

    /**
     * 删除短信模型
     */
    @ApiOperation("删除短信模型")
    @PreAuthorize("@ss.hasPermi('system:warnSmsModel:remove')")
    @Log(title = "短信模型", businessType = BusinessType.DELETE)
    @GetMapping("/remove")
    @RepeatSubmit
    public AjaxResult remove(@RequestParam("id") String[] id) {
        return toAjax(warnSmsModelService.deleteWarnSmsModelByIds(id));
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
        List<WarnSmsModel> list = new ArrayList<>();
        AjaxResult ajaxResult = ExcelUtils.exportExcel(list, "短信模型模板", "短信模型", WarnSmsModel.class,
                "短信模型导入模板.xlsx", response);
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
    @Log(title = "短信模型", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('system:warnSmsModel:import')")
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
        ExcelImportResult<WarnSmsModel> requestList = ExcelImportUtil.importExcelMore(inputStream, WarnSmsModel.class,
                importParams);
        //导入的相应数据
        List<WarnSmsModel> list = requestList.getList();
        //调用getImgName方法获取图片名，这里是根据你配置的地址来存放的，也可以在实体类里面配置存放地址，需要可以开放
        //List<WxPortalApplyInfo> infoList = getImgName(list);
        //判断是否有错误
        if (requestList.isVerfiyFail()) {
            //getFailList()里面的就是所有校验失败的excel数据
            List<WarnSmsModel> failList = requestList.getFailList();
            ExportParams params = new ExportParams();
            params.setSheetName("短信模型错误数据");
            Workbook workbook = ExcelExportUtil.exportExcel(params, WarnSmsModel.class, failList);
            return EasypoiUtil.downLoadExcel("短信模型错误数据.xlsx", response, workbook);
        } else {
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            String operName = loginUser.getUsername();
            List<WarnSmsModel> warnSmsModels = warnSmsModelService.importWarnSmsModel(list, false, operName);
            //清理掉里面缓存的数据
            warnSmsModelService.clearwarnSmsModelInfo();
            ExportParams params = new ExportParams();
            params.setSheetName("短信模型导入数据");
            Workbook workbook = ExcelExportUtil.exportExcel(params, WarnSmsModel.class, warnSmsModels);
            return EasypoiUtil.downLoadExcel("短信模型导入数据.xlsx", response, workbook);
        }
    }

    /**
     * 根据传来的id集合修改禁用启用状态
     */
    @ApiOperation("根据传来的id集合修改禁用启用状态")
    @PreAuthorize("@ss.hasPermi('system:warnSmsModel:updateByIds')")
    @GetMapping("/updateByIds")
    public AjaxResult updateByIds(String ids, String disableEnableState) {
        Boolean isByIds = warnSmsModelService.updateByIds(ids, disableEnableState);
        if (isByIds) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }
}
