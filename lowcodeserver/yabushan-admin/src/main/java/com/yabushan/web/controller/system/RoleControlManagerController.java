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
import com.yabushan.system.domain.RoleControlManager;
import com.yabushan.system.service.IRoleControlManagerService;
import com.yabushan.system.service.impl.RoleControlManagerServiceImpl;
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
 *  角色授权控制管理Controller
 *
 * @author zcm
 * @date 2021-09-22
 */
@RestController
@RequestMapping("/system/roleControlManager")
@Api(tags = {" 角色授权控制管理"})
public class RoleControlManagerController extends BaseController
{
    @Autowired
    private IRoleControlManagerService roleControlManagerService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private RoleControlManagerServiceImpl roleControlManagerServiceimpl;

    /**
     * 查询 角色授权控制管理列表
     */
    @ApiOperation("查询 角色授权控制管理列表")
  //  @Log(title = "查询角色授权控制管理列表", businessType = BusinessType.OTHER)
    @PreAuthorize("@ss.hasPermi('system:roleControlManager:list')")
    @GetMapping("/list")
        public TableDataInfo list(RoleControlManager roleControlManager)
    {
        startPage();
        List<RoleControlManager> list = roleControlManagerService.selectRoleControlManagerList(roleControlManager);
        return getDataTable(list);
    }
    
    /**
     * 导出 角色授权控制管理列表
     */
    @ApiOperation("导出 角色授权控制管理列表")
    @PreAuthorize("@ss.hasPermi('system:roleControlManager:export')")
    @Log(title = " 角色授权控制管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(RoleControlManager roleControlManager,HttpServletResponse response)throws IOException
    {
        List<RoleControlManager> list = roleControlManagerService.selectRoleControlManagerList(roleControlManager);
        AjaxResult ajaxResult = ExcelUtils.exportExcel(list, " 角色授权控制管理", " 角色授权控制管理", RoleControlManager.class, " 角色授权控制管理.xlsx", response);
        return ajaxResult;
    }

    /**
     * 获取 角色授权控制管理详细信息
     */
    @ApiOperation("获取 角色授权控制管理详细信息")
    @PreAuthorize("@ss.hasPermi('system:roleControlManager:query')")
    @GetMapping(value = "/{roleControlManagerId}")
    public AjaxResult getInfo(@PathVariable("roleControlManagerId") String roleControlManagerId)
    {
        return AjaxResult.success(roleControlManagerService.selectRoleControlManagerById(roleControlManagerId));
    }

    /**
     * 新增 角色授权控制管理
     */
    @ApiOperation("新增 角色授权控制管理")
    @PreAuthorize("@ss.hasPermi('system:roleControlManager:add')")
    @Log(title = " 角色授权控制管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RoleControlManager roleControlManager)
    {
        return toAjax(roleControlManagerService.insertRoleControlManager(roleControlManager));
    }

    /**
     * 修改 角色授权控制管理
     */
    @ApiOperation("修改 角色授权控制管理")
    @PreAuthorize("@ss.hasPermi('system:roleControlManager:edit')")
    @Log(title = " 角色授权控制管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RoleControlManager roleControlManager)
    {
        return toAjax(roleControlManagerService.updateRoleControlManager(roleControlManager));
    }

    /**
     * 删除 角色授权控制管理
     */
    @ApiOperation("删除 角色授权控制管理")
    @PreAuthorize("@ss.hasPermi('system:roleControlManager:remove')")
    @Log(title = " 角色授权控制管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{roleControlManagerIds}")
    public AjaxResult remove(@PathVariable String[] roleControlManagerIds)
    {
        return toAjax(roleControlManagerService.deleteRoleControlManagerByIds(roleControlManagerIds));
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
        List<RoleControlManager> list = new ArrayList<>();
        AjaxResult ajaxResult = ExcelUtils.exportExcel(list, " 角色授权控制管理模板", " 角色授权控制管理", RoleControlManager.class, " 角色授权控制管理导入模板.xlsx", response);
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
    @Log(title = " 角色授权控制管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('system:roleControlManager:import')")
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
        ExcelImportResult<RoleControlManager> requestList = ExcelImportUtil.importExcelMore(inputStream, RoleControlManager.class, importParams);
        //导入的相应数据
        List<RoleControlManager> list = requestList.getList();
        //调用getImgName方法获取图片名，这里是根据你配置的地址来存放的，也可以在实体类里面配置存放地址，需要可以开放
        //List<WxPortalApplyInfo> infoList = getImgName(list);
        //判断是否有错误
        if (requestList.isVerfiyFail()) {
            //getFailList()里面的就是所有校验失败的excel数据
            List<RoleControlManager> failList = requestList.getFailList();
            ExportParams params = new ExportParams();
            params.setSheetName(" 角色授权控制管理错误数据");
            Workbook workbook = ExcelExportUtil.exportExcel(params, RoleControlManager.class, failList);
            return EasypoiUtil.downLoadExcel(" 角色授权控制管理错误数据.xlsx", response, workbook);
        } else {
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            String operName = loginUser.getUsername();
            List<RoleControlManager> roleControlManagers = roleControlManagerService.importRoleControlManager(list, false, operName);
            //清理掉里面缓存的数据
            roleControlManagerServiceimpl.clearroleControlManagerInfo();
            ExportParams params = new ExportParams();
            params.setSheetName(" 角色授权控制管理导入数据");
            Workbook workbook = ExcelExportUtil.exportExcel(params, RoleControlManager.class, roleControlManagers);
            return EasypoiUtil.downLoadExcel(" 角色授权控制管理导入数据.xlsx", response, workbook);
        }
    }
    /**
    * 根据传来的id集合修改禁用启用状态
    */
    @ApiOperation("根据传来的id集合修改禁用启用状态")
    @Log(title = "根据传来的id集合修改禁用启用状态", businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermi('system:roleControlManager:updateByIds')")
    @GetMapping("/updateByIds")
    public AjaxResult updateByIds(String ids,String disableEnableState) {
        Boolean isByIds=roleControlManagerService.updateByIds(ids,disableEnableState);
        if(isByIds){
            return AjaxResult.success();
        }else{
            return AjaxResult.error();
        }
    }
}
