package com.yabushan.web.controller.common;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.yabushan.common.annotation.Log;
import com.yabushan.common.core.controller.BaseController;
import com.yabushan.common.core.domain.AjaxResult;
import com.yabushan.common.core.domain.entity.SysRole;
import com.yabushan.common.core.domain.model.LoginUser;
import com.yabushan.common.core.page.TableDataInfo;
import com.yabushan.common.enums.BusinessType;
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.common.utils.ServletUtils;
import com.yabushan.framework.web.service.TokenService;
import com.yabushan.poi.utils.EasypoiUtil;
import com.yabushan.poi.utils.ExcelUtils;
import com.yabushan.system.domain.ViewEmpInfo;
import com.yabushan.system.service.IViewEmpInfoService;
import com.yabushan.system.service.impl.ViewEmpInfoServiceImpl;
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
 * VIEWController
 *
 * @author 姜森焱
 * @date 2021-09-09
 */
@RestController
@RequestMapping("/ViewEmpInfo/ViewEmpInfo")
@Api(tags = {"VIEW"})
public class ViewEmpInfoController extends BaseController {
    @Autowired
    private IViewEmpInfoService viewEmpInfoService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private ViewEmpInfoServiceImpl viewEmpInfoServiceimpl;

    /**
     * 查询VIEW列表
     */
    @ApiOperation("查询VIEW列表")
    @GetMapping("/list")
    public TableDataInfo list(ViewEmpInfo viewEmpInfo) {
        startPage();
        List<ViewEmpInfo> list = viewEmpInfoService.selectViewEmpInfoList(viewEmpInfo);
        return getDataTable(list);
    }

    /**
     * 导出VIEW列表
     */
    @ApiOperation("导出VIEW列表")
    @Log(title = "VIEW", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ViewEmpInfo viewEmpInfo, HttpServletResponse response) throws IOException {
        List<ViewEmpInfo> list = viewEmpInfoService.selectViewEmpInfoList(viewEmpInfo);
        AjaxResult ajaxResult = ExcelUtils.exportExcel(list, "VIEW", "VIEW", ViewEmpInfo.class, "VIEW.xlsx", response);
        return ajaxResult;
    }

    /**
     * 获取VIEW详细信息
     */
    @ApiOperation("获取VIEW详细信息")
    @GetMapping(value = "/{loginid}")
    public AjaxResult getInfo(@PathVariable("loginid") String loginid) {
        return AjaxResult.success(viewEmpInfoService.selectViewEmpInfoById(loginid));
    }

    /**
     * 新增VIEW
     */
    @ApiOperation("新增VIEW")
    @Log(title = "VIEW", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ViewEmpInfo viewEmpInfo) {
        return toAjax(viewEmpInfoService.insertViewEmpInfo(viewEmpInfo));
    }

    /**
     * 修改VIEW
     */
    @ApiOperation("修改VIEW")
    @Log(title = "VIEW", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ViewEmpInfo viewEmpInfo) {
        return toAjax(viewEmpInfoService.updateViewEmpInfo(viewEmpInfo));
    }

    /**
     * 删除VIEW
     */
    @ApiOperation("删除VIEW")
    @Log(title = "VIEW", businessType = BusinessType.DELETE)
    @DeleteMapping("/{loginids}")
    public AjaxResult remove(@PathVariable String[] loginids) {
        return toAjax(viewEmpInfoService.deleteViewEmpInfoByIds(loginids));
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
        List<ViewEmpInfo> list = new ArrayList<>();
        AjaxResult ajaxResult = ExcelUtils.exportExcel(list, "VIEW模板", "VIEW", ViewEmpInfo.class, "VIEW导入模板.xlsx", response);
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
    @Log(title = "VIEW", businessType = BusinessType.IMPORT)
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
        ExcelImportResult<ViewEmpInfo> requestList = ExcelImportUtil.importExcelMore(inputStream, ViewEmpInfo.class, importParams);
        //导入的相应数据
        List<ViewEmpInfo> list = requestList.getList();
        //调用getImgName方法获取图片名，这里是根据你配置的地址来存放的，也可以在实体类里面配置存放地址，需要可以开放
        //List<WxPortalApplyInfo> infoList = getImgName(list);
        //判断是否有错误
        if (requestList.isVerfiyFail()) {
            //getFailList()里面的就是所有校验失败的excel数据
            List<ViewEmpInfo> failList = requestList.getFailList();
            ExportParams params = new ExportParams();
            params.setSheetName("VIEW错误数据");
            Workbook workbook = ExcelExportUtil.exportExcel(params, ViewEmpInfo.class, failList);
            return EasypoiUtil.downLoadExcel("VIEW错误数据.xlsx", response, workbook);
        } else {
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            String operName = loginUser.getUsername();
            List<ViewEmpInfo> viewEmpInfos = viewEmpInfoService.importViewEmpInfo(list, false, operName);
            //清理掉里面缓存的数据
            viewEmpInfoServiceimpl.clearviewEmpInfoInfo();
            ExportParams params = new ExportParams();
            params.setSheetName("VIEW导入数据");
            Workbook workbook = ExcelExportUtil.exportExcel(params, ViewEmpInfo.class, viewEmpInfos);
            return EasypoiUtil.downLoadExcel("VIEW导入数据.xlsx", response, workbook);
        }
    }

    /**
     * 根据传来的id集合修改禁用启用状态
     */
    @ApiOperation("根据传来的id集合修改禁用启用状态")
    @GetMapping("/updateByIds")
    public AjaxResult updateByIds(String ids, String disableEnableState) {
        Boolean isByIds = viewEmpInfoService.updateByIds(ids, disableEnableState);
        if (isByIds) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }

    /**
     * 获取公司视图
     */
    @ApiOperation("获取公司视图,不包括省公司")
    @GetMapping("/getAllCompany")
    public AjaxResult getAllCompany(String companyId) {
        return AjaxResult.success(viewEmpInfoService.getViewCompanyInfo(companyId));
    }

    /**
     * 查询VIEW列表
     */
    @ApiOperation("查询VIEW列表")
    @GetMapping("/getListByPermission")
    public TableDataInfo getListByPermission(ViewEmpInfo viewEmpInfo) {
        startPage();
        String username = SecurityUtils.getUsername();
        if(!"admin".equals(username)){
            viewEmpInfo.setCreateBy(username);
           /* boolean flag=true;
            List<SysRole> roles = SecurityUtils.getLoginUser().getUser().getRoles();
            if(null!=roles && roles.size()>0){
                for(SysRole role:roles){
                    //巡察办主管也能看所有
                    if("XUNCHABAN_MAINTAINER".equals(role.getRoleKey())){
                        flag=false;
                        break;
                    }
                }
            }
            if(flag) {
                viewEmpInfo.setCompanyDpid(SecurityUtils.getLoginUser().getUser().getCompanyId());
            }*/
        }
        List<ViewEmpInfo> list = viewEmpInfoService.selectViewEmpInfoList(viewEmpInfo);
        return getDataTable(list);
    }
}
