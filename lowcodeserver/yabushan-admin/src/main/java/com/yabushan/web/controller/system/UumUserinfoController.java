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
import com.yabushan.system.domain.UumUserinfo;
import com.yabushan.system.service.IUumUserinfoService;
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
 * 同步用户信息Controller
 *
 * @author yabushan
 * @date 2022-04-27
 */
@RestController
@RequestMapping("/system/userinfo")
@Api(tags = {"同步用户信息"})
public class UumUserinfoController extends BaseController {
    @Autowired
    private IUumUserinfoService uumUserinfoService;
    @Autowired
    private TokenService tokenService;

/**
 * 查询同步用户信息列表
 */
@ApiOperation("查询同步用户信息列表")
@Log(title = "查询同步用户信息列表", businessType = BusinessType.OTHER)
@PreAuthorize("@ss.hasPermi('system:userinfo:list')")
@GetMapping("/list")
        public TableDataInfo list(UumUserinfo uumUserinfo) {
        startPage();
        List<UumUserinfo> list = uumUserinfoService.selectUumUserinfoList(uumUserinfo);
        return getDataTable(list);
    }
    
    /**
     * 导出同步用户信息列表
     */
    @ApiOperation("导出同步用户信息列表")
    @PreAuthorize("@ss.hasPermi('system:userinfo:export')")
    @Log(title = "同步用户信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(UumUserinfo uumUserinfo, HttpServletResponse response) throws IOException {
        List<UumUserinfo> list = uumUserinfoService.selectUumUserinfoList(uumUserinfo);
        AjaxResult ajaxResult = ExcelUtils.exportExcel(list, "同步用户信息", "同步用户信息", UumUserinfo. class,
        "同步用户信息.xlsx", response);
        return ajaxResult;
    }

    /**
     * 获取同步用户信息详细信息
     */
    @ApiOperation("获取同步用户信息详细信息")
    @PreAuthorize("@ss.hasPermi('system:userinfo:query')")
    @GetMapping(value = "/{employee}")
    public AjaxResult getInfo(@PathVariable("employee") String employee) {
        return AjaxResult.success(uumUserinfoService.selectUumUserinfoById(employee));
    }

    /**
     * 新增同步用户信息
     */
    @ApiOperation("新增同步用户信息")
    @PreAuthorize("@ss.hasPermi('system:userinfo:add')")
    @Log(title = "同步用户信息", businessType = BusinessType.INSERT)
    @PostMapping
    @RepeatSubmit
    public AjaxResult add(@RequestBody UumUserinfo uumUserinfo) {
        return toAjax(uumUserinfoService.insertUumUserinfo(uumUserinfo));
    }

    /**
     * 修改同步用户信息
     */
    @ApiOperation("修改同步用户信息")
    @PreAuthorize("@ss.hasPermi('system:userinfo:edit')")
    @Log(title = "同步用户信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @RepeatSubmit
    public AjaxResult edit(@RequestBody UumUserinfo uumUserinfo) {
        return toAjax(uumUserinfoService.updateUumUserinfo(uumUserinfo));
    }

    /**
     * 删除同步用户信息
     */
    @ApiOperation("删除同步用户信息")
    @PreAuthorize("@ss.hasPermi('system:userinfo:remove')")
    @Log(title = "同步用户信息", businessType = BusinessType.DELETE)
    @GetMapping("/remove")
    @RepeatSubmit
    public AjaxResult remove(@RequestParam("employee") String[] employee) {
        return toAjax(uumUserinfoService.deleteUumUserinfoByIds(employee));
    }


    /**
    * 数据导入数据库的下载模板
    * @return
    * @throws Exception
    */
    @ApiOperation("数据导入数据库的下载模板")
    @GetMapping("/importTemplate")
    public AjaxResult importTemplate(HttpServletResponse response) throws IOException {
        List<UumUserinfo> list = new ArrayList<>();
        AjaxResult ajaxResult = ExcelUtils.exportExcel(list, "同步用户信息模板", "同步用户信息", UumUserinfo. class,
        "同步用户信息导入模板.xlsx", response);
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
    @Log(title = "同步用户信息", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('system:userinfo:import')")
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
        ExcelImportResult<UumUserinfo> requestList = ExcelImportUtil.importExcelMore(inputStream, UumUserinfo. class,
        importParams);
        //导入的相应数据
        List<UumUserinfo> list = requestList.getList();
        //调用getImgName方法获取图片名，这里是根据你配置的地址来存放的，也可以在实体类里面配置存放地址，需要可以开放
        //List<WxPortalApplyInfo> infoList = getImgName(list);
        //判断是否有错误
        if (requestList.isVerfiyFail()) {
            //getFailList()里面的就是所有校验失败的excel数据
            List<UumUserinfo> failList = requestList.getFailList();
            ExportParams params = new ExportParams();
            params.setSheetName("同步用户信息错误数据");
            Workbook workbook = ExcelExportUtil.exportExcel(params, UumUserinfo. class,failList);
            return EasypoiUtil.downLoadExcel("同步用户信息错误数据.xlsx", response, workbook);
        } else {
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            String operName = loginUser.getUsername();
            List<UumUserinfo> uumUserinfos = uumUserinfoService.importUumUserinfo(list, false, operName);
            //清理掉里面缓存的数据
                uumUserinfoService.clearuumUserinfoInfo();
            ExportParams params = new ExportParams();
            params.setSheetName("同步用户信息导入数据");
            Workbook workbook = ExcelExportUtil.exportExcel(params, UumUserinfo. class,uumUserinfos);
            return EasypoiUtil.downLoadExcel("同步用户信息导入数据.xlsx", response, workbook);
        }
    }

    /**
    * 根据传来的id集合修改禁用启用状态
    */
    @ApiOperation("根据传来的id集合修改禁用启用状态")
    @PreAuthorize("@ss.hasPermi('system:userinfo:updateByIds')")
    @GetMapping("/updateByIds")
    public AjaxResult updateByIds(String ids, String disableEnableState) {
        Boolean isByIds = uumUserinfoService.updateByIds(ids, disableEnableState);
        if (isByIds) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }
}
