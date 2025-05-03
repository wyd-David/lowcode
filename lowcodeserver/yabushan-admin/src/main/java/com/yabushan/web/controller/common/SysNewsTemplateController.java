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
import com.yabushan.system.domain.SysNewsTemplate;
import com.yabushan.system.service.ISysNewsTemplateService;
import com.yabushan.system.service.impl.SysNewsTemplateServiceImpl;
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
 * 消息模板Controller
 *
 * @author 姜森焱
 * @date 2021-09-06
 */
@RestController
@RequestMapping("/NewsTemplate/NewsTemplate")
@Api(tags = {"消息模板"})
public class SysNewsTemplateController extends BaseController {
    @Autowired
    private ISysNewsTemplateService sysNewsTemplateService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private SysNewsTemplateServiceImpl sysNewsTemplateServiceimpl;

    /**
     * 查询消息模板列表
     */
    @ApiOperation("查询消息模板列表")
    @Log(title = "查询消息模板列表", businessType = BusinessType.OTHER)
    @PreAuthorize("@ss.hasPermi('NewsTemplate:NewsTemplate:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysNewsTemplate sysNewsTemplate) {
        startPage();
        List<SysNewsTemplate> list = sysNewsTemplateService.selectSysNewsTemplateList(sysNewsTemplate);
        return getDataTable(list);
    }

    /**
     * 导出消息模板列表
     */
    @ApiOperation("导出消息模板列表")
    @PreAuthorize("@ss.hasPermi('NewsTemplate:NewsTemplate:export')")
    @Log(title = "消息模板", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysNewsTemplate sysNewsTemplate, HttpServletResponse response) throws IOException {
        List<SysNewsTemplate> list = sysNewsTemplateService.selectSysNewsTemplateList(sysNewsTemplate);
        AjaxResult ajaxResult = ExcelUtils.exportExcel(list, "消息模板", "消息模板", SysNewsTemplate.class, "消息模板.xlsx", response);
        return ajaxResult;
    }

    /**
     * 获取消息模板详细信息
     */
    @ApiOperation("获取消息模板详细信息")
    @PreAuthorize("@ss.hasPermi('NewsTemplate:NewsTemplate:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return AjaxResult.success(sysNewsTemplateService.selectSysNewsTemplateById(id));
    }

    /**
     * 新增消息模板
     */
    @ApiOperation("新增消息模板")
    @PreAuthorize("@ss.hasPermi('NewsTemplate:NewsTemplate:add')")
    @Log(title = "消息模板", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysNewsTemplate sysNewsTemplate) {
        return toAjax(sysNewsTemplateService.insertSysNewsTemplate(sysNewsTemplate));
    }

    /**
     * 修改消息模板
     */
    @ApiOperation("修改消息模板")
    @PreAuthorize("@ss.hasPermi('NewsTemplate:NewsTemplate:edit')")
    @Log(title = "消息模板", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysNewsTemplate sysNewsTemplate) {
        return toAjax(sysNewsTemplateService.updateSysNewsTemplate(sysNewsTemplate));
    }

    /**
     * 删除消息模板
     */
    @ApiOperation("删除消息模板")
    @PreAuthorize("@ss.hasPermi('NewsTemplate:NewsTemplate:remove')")
    @Log(title = "消息模板", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(sysNewsTemplateService.deleteSysNewsTemplateByIds(ids));
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
        List<SysNewsTemplate> list = new ArrayList<>();
        AjaxResult ajaxResult = ExcelUtils.exportExcel(list, "消息模板模板", "消息模板", SysNewsTemplate.class, "消息模板导入模板.xlsx", response);
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
    @Log(title = "消息模板", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('NewsTemplate:NewsTemplate:import')")
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
        ExcelImportResult<SysNewsTemplate> requestList = ExcelImportUtil.importExcelMore(inputStream, SysNewsTemplate.class, importParams);
        //导入的相应数据
        List<SysNewsTemplate> list = requestList.getList();
        //调用getImgName方法获取图片名，这里是根据你配置的地址来存放的，也可以在实体类里面配置存放地址，需要可以开放
        //List<WxPortalApplyInfo> infoList = getImgName(list);
        //判断是否有错误
        if (requestList.isVerfiyFail()) {
            //getFailList()里面的就是所有校验失败的excel数据
            List<SysNewsTemplate> failList = requestList.getFailList();
            ExportParams params = new ExportParams();
            params.setSheetName("消息模板错误数据");
            Workbook workbook = ExcelExportUtil.exportExcel(params, SysNewsTemplate.class, failList);
            return EasypoiUtil.downLoadExcel("消息模板错误数据.xlsx", response, workbook);
        } else {
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            String operName = loginUser.getUsername();
            List<SysNewsTemplate> sysNewsTemplates = sysNewsTemplateService.importSysNewsTemplate(list, false, operName);
            //清理掉里面缓存的数据
            sysNewsTemplateServiceimpl.clearsysNewsTemplateInfo();
            ExportParams params = new ExportParams();
            params.setSheetName("消息模板导入数据");
            Workbook workbook = ExcelExportUtil.exportExcel(params, SysNewsTemplate.class, sysNewsTemplates);
            return EasypoiUtil.downLoadExcel("消息模板导入数据.xlsx", response, workbook);
        }
    }
}
