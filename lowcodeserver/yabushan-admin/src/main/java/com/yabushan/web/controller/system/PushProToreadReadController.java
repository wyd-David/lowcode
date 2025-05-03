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
import com.yabushan.common.utils.StringUtils;
import com.yabushan.framework.web.service.TokenService;
import com.yabushan.poi.utils.EasypoiUtil;
import com.yabushan.poi.utils.ExcelUtils;
import com.yabushan.system.domain.PushProToreadRead;
import com.yabushan.system.service.IPushProToreadReadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.ValidationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 推送待阅已阅Controller
 *
 * @author yabushan
 * @date 2022-04-28
 */
@RestController
@RequestMapping("/system/pushProToreadRead")
@Api(tags = {"推送待阅已阅"})
public class PushProToreadReadController extends BaseController {

    @Value("${portal.appName}")
    private String portalAppName;
    @Autowired
    private IPushProToreadReadService pushProToreadReadService;
    @Autowired
    private TokenService tokenService;

    /**
     * 查询推送待阅已阅列表
     */
    @ApiOperation("查询推送待阅已阅列表")
    @Log(title = "查询推送待阅已阅列表", businessType = BusinessType.OTHER)
    @PreAuthorize("@ss.hasPermi('system:pushProToreadRead:list')")
    @GetMapping("/list")
    public TableDataInfo list(PushProToreadRead pushProToreadRead) {
        startPage();
        List<PushProToreadRead> list = pushProToreadReadService.selectPushProToreadReadList(pushProToreadRead);
        return getDataTable(list);
    }

    /**
     * 导出推送待阅已阅列表
     */
    @ApiOperation("导出推送待阅已阅列表")
    @PreAuthorize("@ss.hasPermi('system:pushProToreadRead:export')")
    @Log(title = "推送待阅已阅", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(PushProToreadRead pushProToreadRead, HttpServletResponse response) throws IOException {
        List<PushProToreadRead> list = pushProToreadReadService.selectPushProToreadReadList(pushProToreadRead);
        AjaxResult ajaxResult = ExcelUtils.exportExcel(list, "推送待阅已阅", "推送待阅已阅", PushProToreadRead. class,
                "推送待阅已阅.xlsx", response);
        return ajaxResult;
    }

    /**
     * 获取推送待阅已阅详细信息
     */
    @ApiOperation("获取推送待阅已阅详细信息")
    @PreAuthorize("@ss.hasPermi('system:pushProToreadRead:query')")
    @GetMapping(value = "/{toreadId}")
    public AjaxResult getInfo(@PathVariable("toreadId") String toreadId) {
        return AjaxResult.success(pushProToreadReadService.selectPushProToreadReadById(toreadId));
    }

    /**
     * 新增推送待阅已阅
     */
    @ApiOperation("新增推送待阅已阅")
    @PreAuthorize("@ss.hasPermi('system:pushProToreadRead:add')")
    @Log(title = "推送待阅已阅", businessType = BusinessType.INSERT)
    @PostMapping
    @RepeatSubmit
    public AjaxResult add(@RequestBody PushProToreadRead pushProToreadRead) {
        return toAjax(pushProToreadReadService.insertPushProToreadRead(pushProToreadRead));
    }

    /**
     * 修改推送待阅已阅
     */
    @ApiOperation("修改推送待阅已阅")
    @PreAuthorize("@ss.hasPermi('system:pushProToreadRead:edit')")
    @Log(title = "推送待阅已阅", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @RepeatSubmit
    public AjaxResult edit(@RequestBody PushProToreadRead pushProToreadRead) {
        return toAjax(pushProToreadReadService.updatePushProToreadRead(pushProToreadRead));
    }

    /**
     * 删除推送待阅已阅
     */
    @ApiOperation("删除推送待阅已阅")
    @PreAuthorize("@ss.hasPermi('system:pushProToreadRead:remove')")
    @Log(title = "推送待阅已阅", businessType = BusinessType.DELETE)
    @GetMapping("/remove")
    @RepeatSubmit
    public AjaxResult remove(@RequestParam("toreadId") String[] toreadId) {
        return toAjax(pushProToreadReadService.deletePushProToreadReadByIds(toreadId));
    }

    /**
     * 数据导入数据库的下载模板
     * @return
     * @throws Exception
     */
    @ApiOperation("数据导入数据库的下载模板")
    @GetMapping("/importTemplate")
    public AjaxResult importTemplate(HttpServletResponse response) throws IOException {
        List<PushProToreadRead> list = new ArrayList<>();
        AjaxResult ajaxResult = ExcelUtils.exportExcel(list, "推送待阅已阅模板", "推送待阅已阅", PushProToreadRead. class,
                "推送待阅已阅导入模板.xlsx", response);
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
    @Log(title = "推送待阅已阅", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('system:pushProToreadRead:import')")
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
        ExcelImportResult<PushProToreadRead> requestList = ExcelImportUtil.importExcelMore(inputStream, PushProToreadRead. class,
                importParams);
        //导入的相应数据
        List<PushProToreadRead> list = requestList.getList();
        //调用getImgName方法获取图片名，这里是根据你配置的地址来存放的，也可以在实体类里面配置存放地址，需要可以开放
        //List<WxPortalApplyInfo> infoList = getImgName(list);
        //判断是否有错误
        if (requestList.isVerfiyFail()) {
            //getFailList()里面的就是所有校验失败的excel数据
            List<PushProToreadRead> failList = requestList.getFailList();
            ExportParams params = new ExportParams();
            params.setSheetName("推送待阅已阅错误数据");
            Workbook workbook = ExcelExportUtil.exportExcel(params, PushProToreadRead. class,failList);
            return EasypoiUtil.downLoadExcel("推送待阅已阅错误数据.xlsx", response, workbook);
        } else {
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            String operName = loginUser.getUsername();
            List<PushProToreadRead> pushProToreadReads = pushProToreadReadService.importPushProToreadRead(list, false, operName);
            //清理掉里面缓存的数据
            pushProToreadReadService.clearpushProToreadReadInfo();
            ExportParams params = new ExportParams();
            params.setSheetName("推送待阅已阅导入数据");
            Workbook workbook = ExcelExportUtil.exportExcel(params, PushProToreadRead. class,pushProToreadReads);
            return EasypoiUtil.downLoadExcel("推送待阅已阅导入数据.xlsx", response, workbook);
        }
    }

    /**
     * 根据传来的id集合修改禁用启用状态
     */
    @ApiOperation("根据传来的id集合修改禁用启用状态")
    @PreAuthorize("@ss.hasPermi('system:pushProToreadRead:updateByIds')")
    @GetMapping("/updateByIds")
    public AjaxResult updateByIds(String ids, String disableEnableState) {
        Boolean isByIds = pushProToreadReadService.updateByIds(ids, disableEnableState);
        if (isByIds) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }


    @CrossOrigin
    @ApiOperation("推送待阅已阅")
    @PostMapping("/push")
    public AjaxResult push(@RequestBody @Valid PushProToreadRead pushProToreadRead, BindingResult result){
        if(result.hasErrors()){
            throw new ValidationException(String.join(",",result.getAllErrors().stream().map(i->i.getDefaultMessage()).collect(Collectors.toList())));
        }
        if(!"D".equalsIgnoreCase(pushProToreadRead.getType())&& StringUtils.isEmpty(pushProToreadRead.getOwner())) {
            throw new ValidationException("只有type为D的时候owner才可以为空");
        }
        //portal侧分配的应用名
        pushProToreadRead.setAppName(portalAppName);

        int flag = pushProToreadReadService.insertPushProToreadRead(pushProToreadRead);
        if(flag<1) {
            return AjaxResult.error("入库失败",1);
        }

        return AjaxResult.success(null);

    }
}
