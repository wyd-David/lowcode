package com.yabushan.capacity.controller;

import java.util.List;
import java.util.ArrayList;
import java.io.InputStream;

import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import com.yabushan.common.utils.SecurityUtils;
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
import com.yabushan.capacity.service.impl.NgsTextVoidInfoServiceImpl;
import com.yabushan.capacity.domain.NgsTextVoidInfo;
import com.yabushan.capacity.service.INgsTextVoidInfoService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.yabushan.common.core.page.TableDataInfo;

/**
 * 文字转声音Controller
 *
 * @author yabushan
 * @date 2024-02-20
 */
@RestController
@RequestMapping("/capacity/textvoid")
@Api(tags = {"文字转声音"})
public class NgsTextVoidInfoController extends BaseController {
    @Autowired
    private INgsTextVoidInfoService ngsTextVoidInfoService;
    @Autowired
    private TokenService tokenService;

/**
 * 查询文字转声音列表
 */
@ApiOperation("查询文字转声音列表")
@Log(title = "查询文字转声音列表", businessType = BusinessType.OTHER)
@PreAuthorize("@ss.hasPermi('capacity:textvoid:list')")
@GetMapping("/list")
        public TableDataInfo list(NgsTextVoidInfo ngsTextVoidInfo) {

        ngsTextVoidInfo.setCreatedBy(SecurityUtils.getUsername());
        startPage();
        List<NgsTextVoidInfo> list = ngsTextVoidInfoService.selectNgsTextVoidInfoList(ngsTextVoidInfo);
        return getDataTable(list);
    }
    
    /**
     * 导出文字转声音列表
     */
    @ApiOperation("导出文字转声音列表")
    @PreAuthorize("@ss.hasPermi('capacity:textvoid:export')")
    @Log(title = "文字转声音", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(NgsTextVoidInfo ngsTextVoidInfo, HttpServletResponse response) throws IOException {
        List<NgsTextVoidInfo> list = ngsTextVoidInfoService.selectNgsTextVoidInfoList(ngsTextVoidInfo);
        AjaxResult ajaxResult = ExcelUtils.exportExcel(list, "文字转声音", "文字转声音", NgsTextVoidInfo. class,
        "文字转声音.xlsx", response);
        return ajaxResult;
    }

    /**
     * 获取文字转声音详细信息
     */
    @ApiOperation("获取文字转声音详细信息")
    @PreAuthorize("@ss.hasPermi('capacity:textvoid:query')")
    @GetMapping(value = "/{textId}")
    public AjaxResult getInfo(@PathVariable("textId") String textId) {
        return AjaxResult.success(ngsTextVoidInfoService.selectNgsTextVoidInfoById(textId));
    }

    /**
     * 新增文字转声音
     */
    @ApiOperation("新增文字转声音")
    @PreAuthorize("@ss.hasPermi('capacity:textvoid:add')")
    @Log(title = "文字转声音", businessType = BusinessType.INSERT)
    @PostMapping
    @RepeatSubmit
    public AjaxResult add(@RequestBody NgsTextVoidInfo ngsTextVoidInfo) {
        return toAjax(ngsTextVoidInfoService.insertNgsTextVoidInfo(ngsTextVoidInfo));
    }

    /**
     * 修改文字转声音
     */
    @ApiOperation("修改文字转声音")
    @PreAuthorize("@ss.hasPermi('capacity:textvoid:edit')")
    @Log(title = "文字转声音", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @RepeatSubmit
    public AjaxResult edit(@RequestBody NgsTextVoidInfo ngsTextVoidInfo) {
        return toAjax(ngsTextVoidInfoService.updateNgsTextVoidInfo(ngsTextVoidInfo));
    }

    /**
     * 删除文字转声音
     */
    @ApiOperation("删除文字转声音")
    @PreAuthorize("@ss.hasPermi('capacity:textvoid:remove')")
    @Log(title = "文字转声音", businessType = BusinessType.DELETE)
    @GetMapping("/remove")
    @RepeatSubmit
    public AjaxResult remove(@RequestParam("textId") String[] textId) {
        return toAjax(ngsTextVoidInfoService.deleteNgsTextVoidInfoByIds(textId));
    }

    /**
    * 数据导入数据库的下载模板
    * @return
    * @throws Exception
    */
    @ApiOperation("数据导入数据库的下载模板")
    @GetMapping("/importTemplate")
    public AjaxResult importTemplate(HttpServletResponse response) throws IOException {
        List<NgsTextVoidInfo> list = new ArrayList<>();
        AjaxResult ajaxResult = ExcelUtils.exportExcel(list, "文字转声音模板", "文字转声音", NgsTextVoidInfo. class,
        "文字转声音导入模板.xlsx", response);
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
    @Log(title = "文字转声音", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('capacity:textvoid:import')")
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
        ExcelImportResult<NgsTextVoidInfo> requestList = ExcelImportUtil.importExcelMore(inputStream, NgsTextVoidInfo. class,
        importParams);
        //导入的相应数据
        List<NgsTextVoidInfo> list = requestList.getList();
        //调用getImgName方法获取图片名，这里是根据你配置的地址来存放的，也可以在实体类里面配置存放地址，需要可以开放
        //List<WxPortalApplyInfo> infoList = getImgName(list);
        //判断是否有错误
        if (requestList.isVerfiyFail()) {
            //getFailList()里面的就是所有校验失败的excel数据
            List<NgsTextVoidInfo> failList = requestList.getFailList();
            ExportParams params = new ExportParams();
            params.setSheetName("文字转声音错误数据");
            Workbook workbook = ExcelExportUtil.exportExcel(params, NgsTextVoidInfo. class,failList);
            return EasypoiUtil.downLoadExcel("文字转声音错误数据.xlsx", response, workbook);
        } else {
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            String operName = loginUser.getUsername();
            List<NgsTextVoidInfo> ngsTextVoidInfos = ngsTextVoidInfoService.importNgsTextVoidInfo(list, false, operName);
            //清理掉里面缓存的数据
                ngsTextVoidInfoService.clearngsTextVoidInfoInfo();
            ExportParams params = new ExportParams();
            params.setSheetName("文字转声音导入数据");
            Workbook workbook = ExcelExportUtil.exportExcel(params, NgsTextVoidInfo. class,ngsTextVoidInfos);
            return EasypoiUtil.downLoadExcel("文字转声音导入数据.xlsx", response, workbook);
        }
    }

    /**
    * 根据传来的id集合修改禁用启用状态
    */
    @ApiOperation("根据传来的id集合修改禁用启用状态")
    @PreAuthorize("@ss.hasPermi('capacity:textvoid:updateByIds')")
    @GetMapping("/updateByIds")
    public AjaxResult updateByIds(String ids, String disableEnableState) {
        Boolean isByIds = ngsTextVoidInfoService.updateByIds(ids, disableEnableState);
        if (isByIds) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }
}
