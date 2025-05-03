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
import com.yabushan.system.domain.Attachment;
import com.yabushan.system.service.IAttachmentService;
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
 * 附件信息Controller
 *
 * @author yabushan
 * @date 2022-05-10
 */
@RestController
@RequestMapping("/system/attachment")
@Api(tags = {"附件信息"})
public class AttachmentController extends BaseController {
    @Autowired
    private IAttachmentService attachmentService;
    @Autowired
    private TokenService tokenService;

    /**
     * 查询附件信息列表
     */
    @ApiOperation("查询附件信息列表")
   // @Log(title = "查询附件信息列表", businessType = BusinessType.OTHER)
    @PreAuthorize("@ss.hasPermi('system:attachment:list')")
    @GetMapping("/list")
    public TableDataInfo list(Attachment attachment) {
        startPage();
        List<Attachment> list = attachmentService.selectAttachmentList(attachment);
        return getDataTable(list);
    }

    /**
     * 导出附件信息列表
     */
    @ApiOperation("导出附件信息列表")
    @PreAuthorize("@ss.hasPermi('system:attachment:export')")
   // @Log(title = "附件信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(Attachment attachment, HttpServletResponse response) throws IOException {
        List<Attachment> list = attachmentService.selectAttachmentList(attachment);
        AjaxResult ajaxResult = ExcelUtils.exportExcel(list, "附件信息", "附件信息", Attachment.class,
                "附件信息.xlsx", response);
        return ajaxResult;
    }

    /**
     * 获取附件信息详细信息
     */
    @ApiOperation("获取附件信息详细信息")
    @PreAuthorize("@ss.hasPermi('system:attachment:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return AjaxResult.success(attachmentService.selectAttachmentById(id));
    }

    /**
     * 新增附件信息
     */
    @ApiOperation("新增附件信息")
    @PreAuthorize("@ss.hasPermi('system:attachment:add')")
   // @Log(title = "附件信息", businessType = BusinessType.INSERT)
    @PostMapping
    @RepeatSubmit
    public AjaxResult add(@RequestBody Attachment attachment) {
        return toAjax(attachmentService.insertAttachment(attachment));
    }

    /**
     * 修改附件信息
     */
    @ApiOperation("修改附件信息")
    @PreAuthorize("@ss.hasPermi('system:attachment:edit')")
   // @Log(title = "附件信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @RepeatSubmit
    public AjaxResult edit(@RequestBody Attachment attachment) {
        return toAjax(attachmentService.updateAttachment(attachment));
    }

    /**
     * 删除附件信息
     */
    @ApiOperation("删除附件信息")
    @PreAuthorize("@ss.hasPermi('system:attachment:remove')")
    @Log(title = "附件信息", businessType = BusinessType.DELETE)
    @GetMapping("/remove")
    @RepeatSubmit
    public AjaxResult remove(@RequestParam("id") String[] id) {
        return toAjax(attachmentService.deleteAttachmentByIds(id));
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
        List<Attachment> list = new ArrayList<>();
        AjaxResult ajaxResult = ExcelUtils.exportExcel(list, "附件信息模板", "附件信息", Attachment.class,
                "附件信息导入模板.xlsx", response);
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
    @Log(title = "附件信息", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('system:attachment:import')")
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
        ExcelImportResult<Attachment> requestList = ExcelImportUtil.importExcelMore(inputStream, Attachment.class,
                importParams);
        //导入的相应数据
        List<Attachment> list = requestList.getList();
        //调用getImgName方法获取图片名，这里是根据你配置的地址来存放的，也可以在实体类里面配置存放地址，需要可以开放
        //List<WxPortalApplyInfo> infoList = getImgName(list);
        //判断是否有错误
        if (requestList.isVerfiyFail()) {
            //getFailList()里面的就是所有校验失败的excel数据
            List<Attachment> failList = requestList.getFailList();
            ExportParams params = new ExportParams();
            params.setSheetName("附件信息错误数据");
            Workbook workbook = ExcelExportUtil.exportExcel(params, Attachment.class, failList);
            return EasypoiUtil.downLoadExcel("附件信息错误数据.xlsx", response, workbook);
        } else {
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            String operName = loginUser.getUsername();
            List<Attachment> attachments = attachmentService.importAttachment(list, false, operName);
            //清理掉里面缓存的数据
            attachmentService.clearattachmentInfo();
            ExportParams params = new ExportParams();
            params.setSheetName("附件信息导入数据");
            Workbook workbook = ExcelExportUtil.exportExcel(params, Attachment.class, attachments);
            return EasypoiUtil.downLoadExcel("附件信息导入数据.xlsx", response, workbook);
        }
    }

    /**
     * 根据传来的id集合修改禁用启用状态
     */
    @ApiOperation("根据传来的id集合修改禁用启用状态")
    @PreAuthorize("@ss.hasPermi('system:attachment:updateByIds')")
    @GetMapping("/updateByIds")
    public AjaxResult updateByIds(String ids, String disableEnableState) {
        Boolean isByIds = attachmentService.updateByIds(ids, disableEnableState);
        if (isByIds) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }
}
