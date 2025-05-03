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
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.common.utils.ServletUtils;
import com.yabushan.framework.web.service.TokenService;
import com.yabushan.poi.utils.EasypoiUtil;
import com.yabushan.poi.utils.ExcelUtils;
import com.yabushan.system.domain.SysNewsPushLog;
import com.yabushan.system.domain.vo.SysNewsPushLogVo;
import com.yabushan.system.service.ISysNewsPushLogService;
import com.yabushan.system.service.impl.SysNewsPushLogServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 消息推送记录Controller
 *
 * @author 姜森焱
 * @date 2021-09-06
 */
@RestController
@RequestMapping("/SysNewsPushLog/SysNewsPushLog")
@Api(tags = {"消息推送记录"})
public class SysNewsPushLogController extends BaseController {
    @Autowired
    private ISysNewsPushLogService sysNewsPushLogService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private SysNewsPushLogServiceImpl sysNewsPushLogServiceimpl;

    /**
     * 查询消息推送记录列表
     */
    @ApiOperation("查询消息推送记录列表")
//    @PreAuthorize("@ss.hasPermi('SysNewsPushLog:SysNewsPushLog:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysNewsPushLogVo sysNewsPushLogVo) {
        startPage();
        //只能查看当前人的消息记录
        if(!SecurityUtils.getUsername().equals("admin")){
            sysNewsPushLogVo.setSendOutBy(SecurityUtils.getUsername());
        }
        List<SysNewsPushLogVo> list = sysNewsPushLogService.selectSysNewsPushLogVoList(sysNewsPushLogVo);
        return getDataTable(list);
    }

    /**
     * 导出消息推送记录列表
     */
    @ApiOperation("导出消息推送记录列表")
//    @PreAuthorize("@ss.hasPermi('SysNewsPushLog:SysNewsPushLog:export')")
    @Log(title = "消息推送记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysNewsPushLog sysNewsPushLog, HttpServletResponse response) throws IOException {
        List<SysNewsPushLog> list = sysNewsPushLogService.selectSysNewsPushLogList(sysNewsPushLog);
        AjaxResult ajaxResult = ExcelUtils.exportExcel(list, "消息推送记录", "消息推送记录", SysNewsPushLog.class, "消息推送记录.xlsx", response);
        return ajaxResult;
    }

    /**
     * 获取消息推送记录详细信息
     */
    @ApiOperation("获取消息推送记录详细信息")
//    @PreAuthorize("@ss.hasPermi('SysNewsPushLog:SysNewsPushLog:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(sysNewsPushLogService.selectSysNewsPushLogById(id));
    }

    /**
     * 新增消息推送记录
     */
    @ApiOperation("新增消息推送记录")
//    @PreAuthorize("@ss.hasPermi('SysNewsPushLog:SysNewsPushLog:add')")
    @Log(title = "消息推送记录", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody SysNewsPushLog sysNewsPushLog) {
        return toAjax(sysNewsPushLogService.insertSysNewsPushLog(sysNewsPushLog));
    }

    /**
     * 修改消息推送记录
     */
    @ApiOperation("修改消息推送记录")
//    @PreAuthorize("@ss.hasPermi('SysNewsPushLog:SysNewsPushLog:edit')")
    @Log(title = "消息推送记录", businessType = BusinessType.UPDATE)
    @PostMapping("/update")
    public AjaxResult edit(@RequestBody SysNewsPushLog sysNewsPushLog) {
        return toAjax(sysNewsPushLogService.updateSysNewsPushLog(sysNewsPushLog));
    }

    /**
     * 批量修改消息状态接口
     */
    @ApiOperation("批量修改消息状态接口")
//    @PreAuthorize("@ss.hasPermi('SysNewsPushLog:SysNewsPushLog:edit')")
    @Log(title = "消息推送记录", businessType = BusinessType.UPDATE)
    @GetMapping("/updateSysNewsPushLogBySendOutBy")
    public AjaxResult updateSysNewsPushLogBySendOutBy() {
        return toAjax(sysNewsPushLogService.updateSysNewsPushLogBySendOutBy(new SysNewsPushLog()));
    }
    /**
     * 查询已读未读统计数量
     */
    @GetMapping("/selectReadCount")
    public AjaxResult selectReadCount(){
        return AjaxResult.success(sysNewsPushLogService.selectReadCount(SecurityUtils.getUsername()));
    }
    /**
     * 删除消息推送记录
     */
    @ApiOperation("删除消息推送记录")
//    @PreAuthorize("@ss.hasPermi('SysNewsPushLog:SysNewsPushLog:remove')")
    @Log(title = "消息推送记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(sysNewsPushLogService.deleteSysNewsPushLogByIds(ids));
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
        List<SysNewsPushLog> list = new ArrayList<>();
        AjaxResult ajaxResult = ExcelUtils.exportExcel(list, "消息推送记录模板", "消息推送记录", SysNewsPushLog.class, "消息推送记录导入模板.xlsx", response);
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
    @Log(title = "消息推送记录", businessType = BusinessType.IMPORT)
//    @PreAuthorize("@ss.hasPermi('SysNewsPushLog:SysNewsPushLog:import')")
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
        ExcelImportResult<SysNewsPushLog> requestList = ExcelImportUtil.importExcelMore(inputStream, SysNewsPushLog.class, importParams);
        //导入的相应数据
        List<SysNewsPushLog> list = requestList.getList();
        //调用getImgName方法获取图片名，这里是根据你配置的地址来存放的，也可以在实体类里面配置存放地址，需要可以开放
        //List<WxPortalApplyInfo> infoList = getImgName(list);
        //判断是否有错误
        if (requestList.isVerfiyFail()) {
            //getFailList()里面的就是所有校验失败的excel数据
            List<SysNewsPushLog> failList = requestList.getFailList();
            ExportParams params = new ExportParams();
            params.setSheetName("消息推送记录错误数据");
            Workbook workbook = ExcelExportUtil.exportExcel(params, SysNewsPushLog.class, failList);
            return EasypoiUtil.downLoadExcel("消息推送记录错误数据.xlsx", response, workbook);
        } else {
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            String operName = loginUser.getUsername();
            List<SysNewsPushLog> sysNewsPushLogs = sysNewsPushLogService.importSysNewsPushLog(list, false, operName);
            //清理掉里面缓存的数据
            sysNewsPushLogServiceimpl.clearsysNewsPushLogInfo();
            ExportParams params = new ExportParams();
            params.setSheetName("消息推送记录导入数据");
            Workbook workbook = ExcelExportUtil.exportExcel(params, SysNewsPushLog.class, sysNewsPushLogs);
            return EasypoiUtil.downLoadExcel("消息推送记录导入数据.xlsx", response, workbook);
        }
    }
}
