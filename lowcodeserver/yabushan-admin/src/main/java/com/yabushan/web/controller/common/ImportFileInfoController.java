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
import com.yabushan.system.domain.ImportFileInfo;
import com.yabushan.system.mapper.ImportFileInfoMapper;
import com.yabushan.system.service.IImportFileInfoService;
import com.yabushan.system.service.impl.ImportFileInfoServiceImpl;
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
 * 文件上传记录Controller
 *
 * @author 姜森焱
 * @date 2021-09-08
 */
@RestController
@RequestMapping("/ImportFileInfo/ImportFileInfo")
@Api(tags = {"文件上传记录"})
public class ImportFileInfoController extends BaseController {
    @Autowired
    private IImportFileInfoService importFileInfoService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private ImportFileInfoServiceImpl importFileInfoServiceimpl;

    @Autowired
    private ImportFileInfoMapper importFileInfoMapper;

    /**
     * 查询文件上传记录列表
     */
    @ApiOperation("查询文件上传记录列表")
    @PreAuthorize("@ss.hasPermi('ImportFileInfo:ImportFileInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(ImportFileInfo importFileInfo) {
        startPage();
        List<ImportFileInfo> list = importFileInfoService.selectImportFileInfoList(importFileInfo);
        return getDataTable(list);
    }

    /**
     * 导出文件上传记录列表
     */
    @ApiOperation("导出文件上传记录列表")
    @PreAuthorize("@ss.hasPermi('ImportFileInfo:ImportFileInfo:export')")
    @Log(title = "文件上传记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ImportFileInfo importFileInfo, HttpServletResponse response) throws IOException {
        List<ImportFileInfo> list = importFileInfoService.selectImportFileInfoList(importFileInfo);
        AjaxResult ajaxResult = ExcelUtils.exportExcel(list, "文件上传记录", "文件上传记录", ImportFileInfo.class, "文件上传记录.xlsx", response);
        return ajaxResult;
    }

    /**
     * 获取文件上传记录详细信息
     */
    @ApiOperation("获取文件上传记录详细信息")
    @PreAuthorize("@ss.hasPermi('ImportFileInfo:ImportFileInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(importFileInfoService.selectImportFileInfoById(id));
    }

    /**
     * 新增文件上传记录
     */
    @ApiOperation("新增文件上传记录")
    @PreAuthorize("@ss.hasPermi('ImportFileInfo:ImportFileInfo:add')")
    @Log(title = "文件上传记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ImportFileInfo importFileInfo) {
        return toAjax(importFileInfoService.insertImportFileInfo(importFileInfo));
    }

    /**
     * 修改文件上传记录
     */
    @ApiOperation("修改文件上传记录")
    @PreAuthorize("@ss.hasPermi('ImportFileInfo:ImportFileInfo:edit')")
    @Log(title = "文件上传记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ImportFileInfo importFileInfo) {
        return toAjax(importFileInfoService.updateImportFileInfo(importFileInfo));
    }

    /**
     * 删除文件上传记录
     */
    @ApiOperation("删除文件上传记录")
    @PreAuthorize("@ss.hasPermi('ImportFileInfo:ImportFileInfo:remove')")
    @Log(title = "文件上传记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(importFileInfoService.deleteImportFileInfoByIds(ids));
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
        List<ImportFileInfo> list = new ArrayList<>();
        AjaxResult ajaxResult = ExcelUtils.exportExcel(list, "文件上传记录模板", "文件上传记录", ImportFileInfo.class, "文件上传记录导入模板.xlsx", response);
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
    @Log(title = "文件上传记录", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('ImportFileInfo:ImportFileInfo:import')")
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
        ExcelImportResult<ImportFileInfo> requestList = ExcelImportUtil.importExcelMore(inputStream, ImportFileInfo.class, importParams);
        //导入的相应数据
        List<ImportFileInfo> list = requestList.getList();
        //调用getImgName方法获取图片名，这里是根据你配置的地址来存放的，也可以在实体类里面配置存放地址，需要可以开放
        //List<WxPortalApplyInfo> infoList = getImgName(list);
        //判断是否有错误
        if (requestList.isVerfiyFail()) {
            //getFailList()里面的就是所有校验失败的excel数据
            List<ImportFileInfo> failList = requestList.getFailList();
            ExportParams params = new ExportParams();
            params.setSheetName("文件上传记录错误数据");
            Workbook workbook = ExcelExportUtil.exportExcel(params, ImportFileInfo.class, failList);
            return EasypoiUtil.downLoadExcel("文件上传记录错误数据.xlsx", response, workbook);
        } else {
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            String operName = loginUser.getUsername();
            List<ImportFileInfo> importFileInfos = importFileInfoService.importImportFileInfo(list, false, operName);
            //清理掉里面缓存的数据
            importFileInfoServiceimpl.clearimportFileInfoInfo();
            ExportParams params = new ExportParams();
            params.setSheetName("文件上传记录导入数据");
            Workbook workbook = ExcelExportUtil.exportExcel(params, ImportFileInfo.class, importFileInfos);
            return EasypoiUtil.downLoadExcel("文件上传记录导入数据.xlsx", response, workbook);
        }
    }

    /**
     * 根据传来的id集合修改禁用启用状态
     */
    @ApiOperation("根据传来的id集合修改禁用启用状态")
    @PreAuthorize("@ss.hasPermi('ImportFileInfo:ImportFileInfo:updateByIds')")
    @GetMapping("/updateByIds")
    public AjaxResult updateByIds(List<String> ids, String disableEnableState) {
        importFileInfoService.updateByIds(ids, disableEnableState);
        return AjaxResult.success();
    }

    /**
     * 根据fileKey去删除数据，那个fileKey字段也是唯一的
     */
    @ApiOperation("根据fileKey去删除数据")
    @Log(title = "文件上传记录", businessType = BusinessType.DELETE)
    @GetMapping("/deleteByBusinessKey")
    public AjaxResult deleteByFileKey(String businessKey) {
        return toAjax(importFileInfoMapper.deleteByBusinessKey(businessKey));
    }
}
