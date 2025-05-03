package com.yabushan.form.controller;

import java.util.List;
import java.util.ArrayList;
import java.io.InputStream;

import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import org.apache.poi.ss.usermodel.Workbook;
import com.yabushan.common.utils.ServletUtils;
import com.yabushan.common.core.domain.model.LoginUser;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.yabushan.framework.web.service.TokenService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.yabushan.common.annotation.Log;
import com.yabushan.common.core.controller.BaseController;
import com.yabushan.common.core.domain.AjaxResult;
import com.yabushan.common.enums.BusinessType;
import com.yabushan.poi.utils.EasypoiUtil;
import com.yabushan.poi.utils.ExcelUtils;
import com.yabushan.common.annotation.RepeatSubmit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.yabushan.form.service.impl.LowFormFiledInfoServiceImpl;
import com.yabushan.form.domain.LowFormFiledInfo;
import com.yabushan.form.service.ILowFormFiledInfoService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.yabushan.common.core.page.TableDataInfo;

/**
 * 基础表单字段信息Controller
 *
 * @author yabushan
 * @date 2022-10-10
 */
@RestController
@RequestMapping("/form/filedInfo")
@Api(tags = {"基础表单字段信息"})
public class LowFormFiledInfoController extends BaseController {
    @Autowired
    private ILowFormFiledInfoService lowFormFiledInfoService;
    @Autowired
    private TokenService tokenService;

/**
 * 查询基础表单字段信息列表
 */
@ApiOperation("查询基础表单字段信息列表")
@Log(title = "查询基础表单字段信息列表", businessType = BusinessType.OTHER)
@PreAuthorize("@ss.hasPermi('form:filedInfo:list')")
@GetMapping("/list")
        public TableDataInfo list(LowFormFiledInfo lowFormFiledInfo) {
        startPage();
        List<LowFormFiledInfo> list = lowFormFiledInfoService.selectLowFormFiledInfoList(lowFormFiledInfo);
        return getDataTable(list);
    }
    
    /**
     * 导出基础表单字段信息列表
     */
    @ApiOperation("导出基础表单字段信息列表")
    @PreAuthorize("@ss.hasPermi('form:filedInfo:export')")
    @Log(title = "基础表单字段信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(LowFormFiledInfo lowFormFiledInfo, HttpServletResponse response) throws IOException {
        List<LowFormFiledInfo> list = lowFormFiledInfoService.selectLowFormFiledInfoList(lowFormFiledInfo);
        AjaxResult ajaxResult = ExcelUtils.exportExcel(list, "基础表单字段信息", "基础表单字段信息", LowFormFiledInfo. class,
        "基础表单字段信息.xlsx", response);
        return ajaxResult;
    }

    /**
     * 获取基础表单字段信息详细信息
     */
    @ApiOperation("获取基础表单字段信息详细信息")
    @PreAuthorize("@ss.hasPermi('form:filedInfo:query')")
    @GetMapping(value = "/{filedId}")
    public AjaxResult getInfo(@PathVariable("filedId") String filedId) {
        return AjaxResult.success(lowFormFiledInfoService.selectLowFormFiledInfoById(filedId));
    }

    /**
     * 新增基础表单字段信息
     */
    @ApiOperation("新增基础表单字段信息")
    @PreAuthorize("@ss.hasPermi('form:filedInfo:add')")
    @Log(title = "基础表单字段信息", businessType = BusinessType.INSERT)
    @PostMapping
    @RepeatSubmit
    public AjaxResult add(@RequestBody LowFormFiledInfo lowFormFiledInfo) {
        return toAjax(lowFormFiledInfoService.insertLowFormFiledInfo(lowFormFiledInfo));
    }

    /**
     * 修改基础表单字段信息
     */
    @ApiOperation("修改基础表单字段信息")
    @PreAuthorize("@ss.hasPermi('form:filedInfo:edit')")
    @Log(title = "基础表单字段信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @RepeatSubmit
    public AjaxResult edit(@RequestBody LowFormFiledInfo lowFormFiledInfo) {
        return toAjax(lowFormFiledInfoService.updateLowFormFiledInfo(lowFormFiledInfo));
    }

    /**
     * 删除基础表单字段信息
     */
    @ApiOperation("删除基础表单字段信息")
    @PreAuthorize("@ss.hasPermi('form:filedInfo:remove')")
    @Log(title = "基础表单字段信息", businessType = BusinessType.DELETE)
    @GetMapping("/remove")
    @RepeatSubmit
    public AjaxResult remove(@RequestParam("filedId") String[] filedId) {
        return toAjax(lowFormFiledInfoService.deleteLowFormFiledInfoByIds(filedId));
    }

    /**
    * 数据导入数据库的下载模板
    * @return
    * @throws Exception
    */
    @ApiOperation("数据导入数据库的下载模板")
    @GetMapping("/importTemplate")
    public AjaxResult importTemplate(HttpServletResponse response) throws IOException {
        List<LowFormFiledInfo> list = new ArrayList<>();
        AjaxResult ajaxResult = ExcelUtils.exportExcel(list, "基础表单字段信息模板", "基础表单字段信息", LowFormFiledInfo. class,
        "基础表单字段信息导入模板.xlsx", response);
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
    @Log(title = "基础表单字段信息", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('form:filedInfo:import')")
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
        ExcelImportResult<LowFormFiledInfo> requestList = ExcelImportUtil.importExcelMore(inputStream, LowFormFiledInfo. class,
        importParams);
        //导入的相应数据
        List<LowFormFiledInfo> list = requestList.getList();
        //调用getImgName方法获取图片名，这里是根据你配置的地址来存放的，也可以在实体类里面配置存放地址，需要可以开放
        //List<WxPortalApplyInfo> infoList = getImgName(list);
        //判断是否有错误
        if (requestList.isVerfiyFail()) {
            //getFailList()里面的就是所有校验失败的excel数据
            List<LowFormFiledInfo> failList = requestList.getFailList();
            ExportParams params = new ExportParams();
            params.setSheetName("基础表单字段信息错误数据");
            Workbook workbook = ExcelExportUtil.exportExcel(params, LowFormFiledInfo. class,failList);
            return EasypoiUtil.downLoadExcel("基础表单字段信息错误数据.xlsx", response, workbook);
        } else {
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            String operName = loginUser.getUsername();
            List<LowFormFiledInfo> lowFormFiledInfos = lowFormFiledInfoService.importLowFormFiledInfo(list, false, operName);
            //清理掉里面缓存的数据
                lowFormFiledInfoService.clearlowFormFiledInfoInfo();
            ExportParams params = new ExportParams();
            params.setSheetName("基础表单字段信息导入数据");
            Workbook workbook = ExcelExportUtil.exportExcel(params, LowFormFiledInfo. class,lowFormFiledInfos);
            return EasypoiUtil.downLoadExcel("基础表单字段信息导入数据.xlsx", response, workbook);
        }
    }

    /**
    * 根据传来的id集合修改禁用启用状态
    */
    @ApiOperation("根据传来的id集合修改禁用启用状态")
    @PreAuthorize("@ss.hasPermi('form:filedInfo:updateByIds')")
    @GetMapping("/updateByIds")
    public AjaxResult updateByIds(String ids, String disableEnableState) {
        Boolean isByIds = lowFormFiledInfoService.updateByIds(ids, disableEnableState);
        if (isByIds) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }
}
