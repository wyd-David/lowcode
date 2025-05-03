package com.yabushan.web.controller.datasource;

import java.util.ArrayList;
import java.io.InputStream;

import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.datasource.domain.DigDataResource;
import com.yabushan.datasource.service.IDigDataResourceService;
import com.yabushan.form.mapper.SqlExecuteMapper;
import org.apache.poi.ss.usermodel.Workbook;
import com.yabushan.common.utils.ServletUtils;
import com.yabushan.common.core.domain.model.LoginUser;
import cn.afterturn.easypoi.excel.entity.ExportParams;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.yabushan.common.core.page.TableDataInfo;

/**
 * 数据资源Controller
 *
 * @author yabushan
 * @date 2024-01-14
 */
@RestController
@RequestMapping("/datasource/dataResource")
@Api(tags = {"数据资源"})
public class DigDataResourceController extends BaseController {
    @Autowired
    private IDigDataResourceService dataResourceService;
    @Autowired
    private TokenService tokenService;

    @Autowired
    private SqlExecuteMapper sqlExecuteMapper;

/**
 * 查询数据资源列表
 */
@ApiOperation("查询数据资源列表")

@PreAuthorize("@ss.hasPermi('datasource:dataResource:list')")
@GetMapping("/list")
        public TableDataInfo list(DigDataResource dataResource) {
     if(!SecurityUtils.getUsername().equals("admin")){
         dataResource.setCreateUserName(SecurityUtils.getUsername());
     }
        startPage();
        List<DigDataResource> list = dataResourceService.selectDataResourceList(dataResource);
        return getDataTable(list);
    }

    /**
     * 导出数据资源列表
     */
    @ApiOperation("导出数据资源列表")
    @PreAuthorize("@ss.hasPermi('datasource:dataResource:export')")
    @Log(title = "数据资源", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(DigDataResource dataResource, HttpServletResponse response) throws IOException {
        List<DigDataResource> list = dataResourceService.selectDataResourceList(dataResource);
        AjaxResult ajaxResult = ExcelUtils.exportExcel(list, "数据资源", "数据资源", DigDataResource. class,
        "数据资源.xlsx", response);
        return ajaxResult;
    }

    /**
     * 获取数据资源详细信息
     */
    @ApiOperation("获取数据资源详细信息")
    @PreAuthorize("@ss.hasPermi('datasource:dataResource:query')")
    @GetMapping(value = "/{resourceId}")
    public AjaxResult getInfo(@PathVariable("resourceId") String resourceId) {
        return AjaxResult.success(dataResourceService.selectDataResourceById(resourceId));
    }

    /**
     * 创建数据资源报表
     */
    @ApiOperation("创建数据资源报表")
    @GetMapping(value = "/credateReport/{resourceId}")
    public AjaxResult credateReport(@PathVariable("resourceId") String resourceId) {
        String sql = "REPLACE INTO jimu_report(id,CODE,NAME) VALUES('"+resourceId+"','"+resourceId+"','"+resourceId+"')";
        sqlExecuteMapper.execute(sql);
        return AjaxResult.success("创建成功！");
    }

    /**
     * 新增数据资源
     */
    @ApiOperation("新增数据资源")
    @PreAuthorize("@ss.hasPermi('datasource:dataResource:add')")
    @Log(title = "数据资源", businessType = BusinessType.INSERT)
    @PostMapping
    @RepeatSubmit
    public AjaxResult add(@RequestBody DigDataResource dataResource) {
        return toAjax(dataResourceService.insertDataResource(dataResource));
    }

    /**
     * 修改数据资源
     */
    @ApiOperation("修改数据资源")
    @PreAuthorize("@ss.hasPermi('datasource:dataResource:edit')")
    @Log(title = "数据资源", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @RepeatSubmit
    public AjaxResult edit(@RequestBody DigDataResource dataResource) {
        return toAjax(dataResourceService.updateDataResource(dataResource));
    }

    /**
     * 删除数据资源
     */
    @ApiOperation("删除数据资源")
    @PreAuthorize("@ss.hasPermi('datasource:dataResource:remove')")
    @Log(title = "数据资源", businessType = BusinessType.DELETE)
    @GetMapping("/remove")
    @RepeatSubmit
    public AjaxResult remove(@RequestParam("resourceId") String[] resourceId) {
        return toAjax(dataResourceService.deleteDataResourceByIds(resourceId));
    }

    /**
    * 数据导入数据库的下载模板
    * @return
    * @throws Exception
    */
    @ApiOperation("数据导入数据库的下载模板")
    @GetMapping("/importTemplate")
    public AjaxResult importTemplate(HttpServletResponse response) throws IOException {
        List<DigDataResource> list = new ArrayList<>();
        AjaxResult ajaxResult = ExcelUtils.exportExcel(list, "数据资源模板", "数据资源", DigDataResource. class,
        "数据资源导入模板.xlsx", response);
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
    @Log(title = "数据资源", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('datasource:dataResource:import')")
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
        ExcelImportResult<DigDataResource> requestList = ExcelImportUtil.importExcelMore(inputStream, DigDataResource. class,
        importParams);
        //导入的相应数据
        List<DigDataResource> list = requestList.getList();
        //调用getImgName方法获取图片名，这里是根据你配置的地址来存放的，也可以在实体类里面配置存放地址，需要可以开放
        //List<WxPortalApplyInfo> infoList = getImgName(list);
        //判断是否有错误
        if (requestList.isVerfiyFail()) {
            //getFailList()里面的就是所有校验失败的excel数据
            List<DigDataResource> failList = requestList.getFailList();
            ExportParams params = new ExportParams();
            params.setSheetName("数据资源错误数据");
            Workbook workbook = ExcelExportUtil.exportExcel(params, DigDataResource. class,failList);
            return EasypoiUtil.downLoadExcel("数据资源错误数据.xlsx", response, workbook);
        } else {
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            String operName = loginUser.getUsername();
            List<DigDataResource> dataResources = dataResourceService.importDataResource(list, false, operName);
            //清理掉里面缓存的数据
                dataResourceService.cleardataResourceInfo();
            ExportParams params = new ExportParams();
            params.setSheetName("数据资源导入数据");
            Workbook workbook = ExcelExportUtil.exportExcel(params, DigDataResource. class,dataResources);
            return EasypoiUtil.downLoadExcel("数据资源导入数据.xlsx", response, workbook);
        }
    }

    /**
    * 根据传来的id集合修改禁用启用状态
    */
    @ApiOperation("根据传来的id集合修改禁用启用状态")
    @PreAuthorize("@ss.hasPermi('datasource:dataResource:updateByIds')")
    @GetMapping("/updateByIds")
    public AjaxResult updateByIds(String ids, String disableEnableState) {
        Boolean isByIds = dataResourceService.updateByIds(ids, disableEnableState);
        if (isByIds) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }
}
