package com.yabushan.web.controller.datasource;

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
import com.yabushan.datasource.service.impl.DataStructuredColumnServiceImpl;
import com.yabushan.datasource.domain.DataStructuredColumn;
import com.yabushan.datasource.service.IDataStructuredColumnService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.yabushan.common.core.page.TableDataInfo;

/**
 * 字段元数据Controller
 *
 * @author yabushan
 * @date 2024-01-14
 */
@RestController
@RequestMapping("/datasource/dataStructuredColumn")
@Api(tags = {"字段元数据"})
public class DataStructuredColumnController extends BaseController {
    @Autowired
    private IDataStructuredColumnService dataStructuredColumnService;
    @Autowired
    private TokenService tokenService;

/**
 * 查询字段元数据列表
 */
@ApiOperation("查询字段元数据列表")

@PreAuthorize("@ss.hasPermi('datasource:dataStructuredColumn:list')")
@GetMapping("/list")
        public TableDataInfo list(DataStructuredColumn dataStructuredColumn) {
    if(!"admin".equals(SecurityUtils.getUsername())){
        dataStructuredColumn.setCreateBy(SecurityUtils.getUsername());
    }
        startPage();
        List<DataStructuredColumn> list = dataStructuredColumnService.selectDataStructuredColumnList(dataStructuredColumn);
        return getDataTable(list);
    }

    /**
     * 导出字段元数据列表
     */
    @ApiOperation("导出字段元数据列表")
    @PreAuthorize("@ss.hasPermi('datasource:dataStructuredColumn:export')")
    @Log(title = "字段元数据", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(DataStructuredColumn dataStructuredColumn, HttpServletResponse response) throws IOException {
        List<DataStructuredColumn> list = dataStructuredColumnService.selectDataStructuredColumnList(dataStructuredColumn);
        AjaxResult ajaxResult = ExcelUtils.exportExcel(list, "字段元数据", "字段元数据", DataStructuredColumn. class,
        "字段元数据.xlsx", response);
        return ajaxResult;
    }

    /**
     * 获取字段元数据详细信息
     */
    @ApiOperation("获取字段元数据详细信息")
    @PreAuthorize("@ss.hasPermi('datasource:dataStructuredColumn:query')")
    @GetMapping(value = "/{columnId}")
    public AjaxResult getInfo(@PathVariable("columnId") String columnId) {
        return AjaxResult.success(dataStructuredColumnService.selectDataStructuredColumnById(columnId));
    }

    /**
     * 新增字段元数据
     */
    @ApiOperation("新增字段元数据")
    @PreAuthorize("@ss.hasPermi('datasource:dataStructuredColumn:add')")
    @PostMapping
    @RepeatSubmit
    public AjaxResult add(@RequestBody DataStructuredColumn dataStructuredColumn) {
        return toAjax(dataStructuredColumnService.insertDataStructuredColumn(dataStructuredColumn));
    }

    /**
     * 修改字段元数据
     */
    @ApiOperation("修改字段元数据")
    @PreAuthorize("@ss.hasPermi('datasource:dataStructuredColumn:edit')")
    @Log(title = "字段元数据", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @RepeatSubmit
    public AjaxResult edit(@RequestBody DataStructuredColumn dataStructuredColumn) {
        return toAjax(dataStructuredColumnService.updateDataStructuredColumn(dataStructuredColumn));
    }

    /**
     * 删除字段元数据
     */
    @ApiOperation("删除字段元数据")
    @PreAuthorize("@ss.hasPermi('datasource:dataStructuredColumn:remove')")
    @Log(title = "字段元数据", businessType = BusinessType.DELETE)
    @GetMapping("/remove")
    @RepeatSubmit
    public AjaxResult remove(@RequestParam("columnId") String[] columnId) {
        return toAjax(dataStructuredColumnService.deleteDataStructuredColumnByIds(columnId));
    }

    /**
    * 数据导入数据库的下载模板
    * @return
    * @throws Exception
    */
    @ApiOperation("数据导入数据库的下载模板")
    @GetMapping("/importTemplate")
    public AjaxResult importTemplate(HttpServletResponse response) throws IOException {
        List<DataStructuredColumn> list = new ArrayList<>();
        AjaxResult ajaxResult = ExcelUtils.exportExcel(list, "字段元数据模板", "字段元数据", DataStructuredColumn. class,
        "字段元数据导入模板.xlsx", response);
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
    @Log(title = "字段元数据", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('datasource:dataStructuredColumn:import')")
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
        ExcelImportResult<DataStructuredColumn> requestList = ExcelImportUtil.importExcelMore(inputStream, DataStructuredColumn. class,
        importParams);
        //导入的相应数据
        List<DataStructuredColumn> list = requestList.getList();
        //调用getImgName方法获取图片名，这里是根据你配置的地址来存放的，也可以在实体类里面配置存放地址，需要可以开放
        //List<WxPortalApplyInfo> infoList = getImgName(list);
        //判断是否有错误
        if (requestList.isVerfiyFail()) {
            //getFailList()里面的就是所有校验失败的excel数据
            List<DataStructuredColumn> failList = requestList.getFailList();
            ExportParams params = new ExportParams();
            params.setSheetName("字段元数据错误数据");
            Workbook workbook = ExcelExportUtil.exportExcel(params, DataStructuredColumn. class,failList);
            return EasypoiUtil.downLoadExcel("字段元数据错误数据.xlsx", response, workbook);
        } else {
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            String operName = loginUser.getUsername();
            List<DataStructuredColumn> dataStructuredColumns = dataStructuredColumnService.importDataStructuredColumn(list, false, operName);
            //清理掉里面缓存的数据
                dataStructuredColumnService.cleardataStructuredColumnInfo();
            ExportParams params = new ExportParams();
            params.setSheetName("字段元数据导入数据");
            Workbook workbook = ExcelExportUtil.exportExcel(params, DataStructuredColumn. class,dataStructuredColumns);
            return EasypoiUtil.downLoadExcel("字段元数据导入数据.xlsx", response, workbook);
        }
    }

    /**
    * 根据传来的id集合修改禁用启用状态
    */
    @ApiOperation("根据传来的id集合修改禁用启用状态")
    @PreAuthorize("@ss.hasPermi('datasource:dataStructuredColumn:updateByIds')")
    @GetMapping("/updateByIds")
    public AjaxResult updateByIds(String ids, String disableEnableState) {
        Boolean isByIds = dataStructuredColumnService.updateByIds(ids, disableEnableState);
        if (isByIds) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }
}
