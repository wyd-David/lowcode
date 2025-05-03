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
import com.yabushan.system.domain.DepartmentInfo;
import com.yabushan.system.domain.SyncOrg;
import com.yabushan.system.domain.vo.SyncOrgVo;
import com.yabushan.system.service.IDepartmentInfoService;
import com.yabushan.system.service.ISyncOrgService;
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
 * 部门信息 Controller
 *
 * @author yabushan
 * @date 2021-10-21
 */
@RestController
@RequestMapping("/system/departmentInfo")
@Api(tags = {"部门信息 "})
public class DepartmentInfoController extends BaseController {
    @Autowired
    private IDepartmentInfoService departmentInfoService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private ISyncOrgService syncOrgService;

    /**
     * 查询部门信息 列表
     */
    @ApiOperation("查询部门信息 列表")
    @Log(title = "查询部门信息列表", businessType = BusinessType.OTHER)
//@PreAuthorize("@ss.hasPermi('department:departmentInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(DepartmentInfo departmentInfo) {
        startPage();
        List<DepartmentInfo> list = departmentInfoService.selectDepartmentInfoList(departmentInfo);
        return getDataTable(list);
    }

    /**
     * 导出部门信息 列表
     */
    @ApiOperation("导出部门信息 列表")
    @Log(title = "部门信息 ", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SyncOrg syncOrg, HttpServletResponse response) throws IOException {
        List<SyncOrgVo> list = syncOrgService.selectSyncDepAllVo(syncOrg);
        AjaxResult ajaxResult = ExcelUtils.exportExcel(list, "部门信息", "部门信息", SyncOrgVo.class, "部门信息.xlsx", response);
        return ajaxResult;
    }

    /**
     * 获取部门信息 详细信息
     */
    @ApiOperation("获取部门信息 详细信息")
//    @PreAuthorize("@ss.hasPermi('department:departmentInfo:query')")
    @GetMapping(value = "/{deptId}")
    public AjaxResult getInfo(@PathVariable("deptId") Integer deptId) {
        return AjaxResult.success(departmentInfoService.selectDepartmentInfoById(deptId));
    }

    /**
     * 新增部门信息
     */
    @ApiOperation("新增部门信息 ")
//    @PreAuthorize("@ss.hasPermi('department:departmentInfo:add')")
    @Log(title = "部门信息 ", businessType = BusinessType.INSERT)
    @PostMapping
    @RepeatSubmit
    public AjaxResult add(@RequestBody DepartmentInfo departmentInfo) {
        return toAjax(departmentInfoService.insertDepartmentInfo(departmentInfo));
    }

    /**
     * 修改部门信息
     */
    @ApiOperation("修改部门信息 ")
//    @PreAuthorize("@ss.hasPermi('department:departmentInfo:edit')")
    @Log(title = "部门信息 ", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @RepeatSubmit
    public AjaxResult edit(@RequestBody DepartmentInfo departmentInfo) {
        return toAjax(departmentInfoService.updateDepartmentInfo(departmentInfo));
    }

    /**
     * 删除部门信息
     */
    @ApiOperation("删除部门信息 ")
//    @PreAuthorize("@ss.hasPermi('department:departmentInfo:remove')")
    @Log(title = "部门信息 ", businessType = BusinessType.DELETE)
    @GetMapping("/remove")
    @RepeatSubmit
    public AjaxResult remove(@RequestParam("deptId") Integer[] deptId) {
        return toAjax(departmentInfoService.deleteDepartmentInfoByIds(deptId));
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
        List<SyncOrgVo> list = new ArrayList<>();
        AjaxResult ajaxResult = ExcelUtils.exportExcel(list, "部门信息模板", "部门信息", SyncOrgVo.class, "部门信息导入模板.xlsx", response);
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
    @Log(title = "部门信息 ", businessType = BusinessType.IMPORT)
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
        ExcelImportResult<DepartmentInfo> requestList = ExcelImportUtil.importExcelMore(inputStream, DepartmentInfo.class, importParams);
        //导入的相应数据
        List<DepartmentInfo> list = requestList.getList();
        //调用getImgName方法获取图片名，这里是根据你配置的地址来存放的，也可以在实体类里面配置存放地址，需要可以开放
        //List<WxPortalApplyInfo> infoList = getImgName(list);
        //判断是否有错误
        if (requestList.isVerfiyFail()) {
            //getFailList()里面的就是所有校验失败的excel数据
            List<DepartmentInfo> failList = requestList.getFailList();
            ExportParams params = new ExportParams();
            params.setSheetName("部门信息 错误数据");
            Workbook workbook = ExcelExportUtil.exportExcel(params, DepartmentInfo.class, failList);
            return EasypoiUtil.downLoadExcel("部门信息 错误数据.xlsx", response, workbook);
        } else {
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            String operName = loginUser.getUsername();
            List<DepartmentInfo> departmentInfos = departmentInfoService.importDepartmentInfo(list, false, operName);
            //清理掉里面缓存的数据
            departmentInfoService.cleardepartmentInfoInfo();
            ExportParams params = new ExportParams();
            params.setSheetName("部门信息 导入数据");
            Workbook workbook = ExcelExportUtil.exportExcel(params, DepartmentInfo.class, departmentInfos);
            return EasypoiUtil.downLoadExcel("部门信息 导入数据.xlsx", response, workbook);
        }
    }

    /**
     * 根据传来的id集合修改禁用启用状态
     */
    @ApiOperation("根据传来的id集合修改禁用启用状态")
    @Log(title = "根据传来的id集合修改禁用启用状态", businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermi('department:departmentInfo:updateByIds')")
    @GetMapping("/updateByIds")
    public AjaxResult updateByIds(String ids, String disableEnableState) {
        Boolean isByIds = departmentInfoService.updateByIds(ids, disableEnableState);
        if (isByIds) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }
}
