package com.yabushan.web.controller.datasource;

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
import com.yabushan.datasource.service.impl.AppUnitInstanceServiceImpl;
import com.yabushan.datasource.domain.AppUnitInstance;
import com.yabushan.datasource.service.IAppUnitInstanceService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.yabushan.common.core.page.TableDataInfo;

/**
 * 分配实例Controller
 *
 * @author yabushan
 * @date 2024-01-13
 */
@RestController
@RequestMapping("/datasource/appInstance")
@Api(tags = {"分配实例"})
public class AppUnitInstanceController extends BaseController {
    @Autowired
    private IAppUnitInstanceService appUnitInstanceService;
    @Autowired
    private TokenService tokenService;

    /**
     * 查询分配实例列表
     */
    @ApiOperation("查询分配实例列表")
    @Log(title = "查询分配实例列表", businessType = BusinessType.OTHER)
    @PreAuthorize("@ss.hasPermi('datasource:appInstance:list')")
    @GetMapping("/list")
    public TableDataInfo list(AppUnitInstance appUnitInstance) {
        startPage();
        List<AppUnitInstance> list = appUnitInstanceService.selectAppUnitInstanceList(appUnitInstance);
        return getDataTable(list);
    }

    /**
     * 导出分配实例列表
     */
    @ApiOperation("导出分配实例列表")
    @PreAuthorize("@ss.hasPermi('datasource:appInstance:export')")
    @Log(title = "分配实例", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(AppUnitInstance appUnitInstance, HttpServletResponse response) throws IOException {
        List<AppUnitInstance> list = appUnitInstanceService.selectAppUnitInstanceList(appUnitInstance);
        AjaxResult ajaxResult = ExcelUtils.exportExcel(list, "分配实例", "分配实例", AppUnitInstance. class,
                "分配实例.xlsx", response);
        return ajaxResult;
    }

    /**
     * 获取分配实例详细信息
     */
    @ApiOperation("获取分配实例详细信息")
    @PreAuthorize("@ss.hasPermi('datasource:appInstance:query')")
    @GetMapping(value = "/{instanceId}")
    public AjaxResult getInfo(@PathVariable("instanceId") String instanceId) {
        return AjaxResult.success(appUnitInstanceService.selectAppUnitInstanceById(instanceId));
    }

    /**
     * 新增分配实例
     */
    @ApiOperation("新增分配实例")
    @PreAuthorize("@ss.hasPermi('datasource:appInstance:add')")
    @Log(title = "分配实例", businessType = BusinessType.INSERT)
    @PostMapping
    @RepeatSubmit
    public AjaxResult add(@RequestBody AppUnitInstance appUnitInstance) {
        return toAjax(appUnitInstanceService.insertAppUnitInstance(appUnitInstance));
    }

    /**
     * 修改分配实例
     */
    @ApiOperation("修改分配实例")
    @PreAuthorize("@ss.hasPermi('datasource:appInstance:edit')")
    @Log(title = "分配实例", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @RepeatSubmit
    public AjaxResult edit(@RequestBody AppUnitInstance appUnitInstance) {
        return toAjax(appUnitInstanceService.updateAppUnitInstance(appUnitInstance));
    }

    /**
     * 删除分配实例
     */
    @ApiOperation("删除分配实例")
    @PreAuthorize("@ss.hasPermi('datasource:appInstance:remove')")
    @Log(title = "分配实例", businessType = BusinessType.DELETE)
    @GetMapping("/remove")
    @RepeatSubmit
    public AjaxResult remove(@RequestParam("instanceId") String[] instanceId) {
        return toAjax(appUnitInstanceService.deleteAppUnitInstanceByIds(instanceId));
    }

    /**
     * 数据导入数据库的下载模板
     * @return
     * @throws Exception
     */
    @ApiOperation("数据导入数据库的下载模板")
    @GetMapping("/importTemplate")
    public AjaxResult importTemplate(HttpServletResponse response) throws IOException {
        List<AppUnitInstance> list = new ArrayList<>();
        AjaxResult ajaxResult = ExcelUtils.exportExcel(list, "分配实例模板", "分配实例", AppUnitInstance. class,
                "分配实例导入模板.xlsx", response);
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
    @Log(title = "分配实例", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('datasource:appInstance:import')")
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
        ExcelImportResult<AppUnitInstance> requestList = ExcelImportUtil.importExcelMore(inputStream, AppUnitInstance. class,
                importParams);
        //导入的相应数据
        List<AppUnitInstance> list = requestList.getList();
        //调用getImgName方法获取图片名，这里是根据你配置的地址来存放的，也可以在实体类里面配置存放地址，需要可以开放
        //List<WxPortalApplyInfo> infoList = getImgName(list);
        //判断是否有错误
        if (requestList.isVerfiyFail()) {
            //getFailList()里面的就是所有校验失败的excel数据
            List<AppUnitInstance> failList = requestList.getFailList();
            ExportParams params = new ExportParams();
            params.setSheetName("分配实例错误数据");
            Workbook workbook = ExcelExportUtil.exportExcel(params, AppUnitInstance. class,failList);
            return EasypoiUtil.downLoadExcel("分配实例错误数据.xlsx", response, workbook);
        } else {
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            String operName = loginUser.getUsername();
            List<AppUnitInstance> appUnitInstances = appUnitInstanceService.importAppUnitInstance(list, false, operName);
            //清理掉里面缓存的数据
            appUnitInstanceService.clearappUnitInstanceInfo();
            ExportParams params = new ExportParams();
            params.setSheetName("分配实例导入数据");
            Workbook workbook = ExcelExportUtil.exportExcel(params, AppUnitInstance. class,appUnitInstances);
            return EasypoiUtil.downLoadExcel("分配实例导入数据.xlsx", response, workbook);
        }
    }

    /**
     * 根据传来的id集合修改禁用启用状态
     */
    @ApiOperation("根据传来的id集合修改禁用启用状态")
    @PreAuthorize("@ss.hasPermi('datasource:appInstance:updateByIds')")
    @GetMapping("/updateByIds")
    public AjaxResult updateByIds(String ids, String disableEnableState) {
        Boolean isByIds = appUnitInstanceService.updateByIds(ids, disableEnableState);
        if (isByIds) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }
}
