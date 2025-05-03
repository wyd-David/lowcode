package com.yabushan.web.controller.system;

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
import com.yabushan.system.domain.SysNotice;
import com.yabushan.system.service.ISysNoticeService;
import com.yabushan.system.service.impl.SysNoticeServiceImpl;
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
 * 通知公告Controller
 *
 * @author yabushan
 * @date 2021-09-01
 */
@RestController
@RequestMapping("/system/notice")
@Api(tags = {"通知公告"})
public class SysNoticeController extends BaseController
{
    @Autowired
    private ISysNoticeService sysNoticeService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private SysNoticeServiceImpl sysNoticeServiceimpl;

    /**
     * 查询通知公告列表
     */
    @ApiOperation("查询通知公告列表")
    @Log(title = "查询通知公告列表", businessType = BusinessType.OTHER)
    @GetMapping("/list")
    public TableDataInfo list(SysNotice sysNotice)
    {
        startPage();
        List<SysNotice> list = sysNoticeService.selectSysNoticeList(sysNotice);
        return getDataTable(list);
    }

    /**
     * 导出通知公告列表
     */
    @ApiOperation("导出通知公告列表")
    @PreAuthorize("@ss.hasPermi('system:notice:export')")
    @Log(title = "通知公告", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysNotice sysNotice,HttpServletResponse response)throws IOException
    {
        List<SysNotice> list = sysNoticeService.selectSysNoticeList(sysNotice);
        AjaxResult ajaxResult = ExcelUtils.exportExcel(list, "通知公告", "通知公告", SysNotice.class, "通知公告.xlsx", response);
        return ajaxResult;
    }

    /**
     * 获取通知公告详细信息
     */
    @ApiOperation("获取通知公告详细信息")
//    @PreAuthorize("@ss.hasPermi('system:notice:query')")
    @GetMapping(value = "/{noticeId}")
    public AjaxResult getInfo(@PathVariable("noticeId") Long noticeId)
    {
        return AjaxResult.success(sysNoticeService.selectSysNoticeById(noticeId));
    }

    /**
     * 新增通知公告
     */
    @ApiOperation("新增通知公告")
//    @PreAuthorize("@ss.hasPermi('system:notice:add')")
    @Log(title = "通知公告", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysNotice sysNotice)
    {
        return toAjax(sysNoticeService.insertSysNotice(sysNotice));
    }

    /**
     * 修改通知公告
     */
    @ApiOperation("修改通知公告")
//    @PreAuthorize("@ss.hasPermi('system:notice:edit')")
    @Log(title = "通知公告", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysNotice sysNotice)
    {
        return toAjax(sysNoticeService.updateSysNotice(sysNotice));
    }

    /**
     * 删除通知公告
     */
    @ApiOperation("删除通知公告")
//    @PreAuthorize("@ss.hasPermi('system:notice:remove')")
    @Log(title = "通知公告", businessType = BusinessType.DELETE)
    @DeleteMapping("/{noticeIds}")
    public AjaxResult remove(@PathVariable Long[] noticeIds)
    {
        return toAjax(sysNoticeService.deleteSysNoticeByIds(noticeIds));
    }


    /**
     * 数据导入数据库的下载模板
     * @return
     * @throws Exception
     */
    @ApiOperation("数据导入数据库的下载模板")
    @GetMapping("/importTemplate")
    public AjaxResult importTemplate(HttpServletResponse response) throws IOException
    {
        List<SysNotice> list = new ArrayList<>();
        AjaxResult ajaxResult = ExcelUtils.exportExcel(list, "通知公告模板", "通知公告", SysNotice.class, "通知公告导入模板.xlsx", response);
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
    @Log(title = "通知公告", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('system:notice:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport,HttpServletResponse response) throws Exception {
        InputStream inputStream = file.getInputStream();
        ImportParams importParams = new ImportParams();
        importParams.setHeadRows(1);
        //标题
        importParams.setTitleRows(1);
        // 是否需要验证
        importParams.setNeedVerfiy(true);
        //
        ExcelImportResult<SysNotice> requestList = ExcelImportUtil.importExcelMore(inputStream, SysNotice.class, importParams);
        //导入的相应数据
        List<SysNotice> list = requestList.getList();
        //调用getImgName方法获取图片名，这里是根据你配置的地址来存放的，也可以在实体类里面配置存放地址，需要可以开放
        //List<WxPortalApplyInfo> infoList = getImgName(list);
        //判断是否有错误
        if (requestList.isVerfiyFail()) {
            //getFailList()里面的就是所有校验失败的excel数据
            List<SysNotice> failList = requestList.getFailList();
            ExportParams params = new ExportParams();
            params.setSheetName("通知公告错误数据");
            Workbook workbook = ExcelExportUtil.exportExcel(params, SysNotice.class, failList);
            return EasypoiUtil.downLoadExcel("通知公告错误数据.xlsx", response, workbook);
        } else {
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            String operName = loginUser.getUsername();
            List<SysNotice> sysNotices = sysNoticeService.importSysNotice(list, false, operName);
            //清理掉里面缓存的数据
            sysNoticeServiceimpl.clearsysNoticeInfo();
            ExportParams params = new ExportParams();
            params.setSheetName("通知公告导入数据");
            Workbook workbook = ExcelExportUtil.exportExcel(params, SysNotice.class, sysNotices);
            return EasypoiUtil.downLoadExcel("通知公告导入数据.xlsx", response, workbook);
        }
    }
}
