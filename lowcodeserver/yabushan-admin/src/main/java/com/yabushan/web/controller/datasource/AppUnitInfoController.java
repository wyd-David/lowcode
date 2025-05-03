package com.yabushan.web.controller.datasource;

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
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.common.utils.ServletUtils;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.datasource.Vo.DataSourceDto;
import com.yabushan.datasource.Vo.GetMetaDataDto;
import com.yabushan.datasource.domain.AppUnitInfo;
import com.yabushan.datasource.service.DigDatasourceService;
import com.yabushan.datasource.service.IAppUnitInfoService;
import com.yabushan.framework.web.service.TokenService;
import com.yabushan.poi.utils.EasypoiUtil;
import com.yabushan.poi.utils.ExcelUtils;
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
import java.util.Arrays;
import java.util.List;

/**
 * 动态数据源管理Controller
 *
 * @author yabushan
 * @date 2024-01-13
 */
@RestController
@RequestMapping("/datasource/appInfo")
@Api(tags = {"动态数据源管理"})
public class AppUnitInfoController extends BaseController {
    @Autowired
    private IAppUnitInfoService appUnitInfoService;
    @Autowired
    private TokenService tokenService;

    @Autowired
    private DigDatasourceService digDatasourceService;

/**
 * 查询动态数据源管理列表
 */
@ApiOperation("查询动态数据源管理列表")
//@Log(title = "查询动态数据源管理列表", businessType = BusinessType.OTHER)
@PreAuthorize("@ss.hasPermi('datasource:appInfo:list')")
@GetMapping("/list")
        public TableDataInfo list(AppUnitInfo appUnitInfo) {
      if(!SecurityUtils.getUsername().equals("admin")){
          appUnitInfo.setCreateBy(SecurityUtils.getUsername());
      }
        startPage();
        List<AppUnitInfo> list = appUnitInfoService.selectAppUnitInfoList(appUnitInfo);
        return getDataTable(list);
    }



    /**
     * 新增动态数据源管理
     */
    @ApiOperation("新增动态数据源管理")
    @PreAuthorize("@ss.hasPermi('datasource:appInfo:add')")
    @Log(title = "动态数据源管理", businessType = BusinessType.INSERT)
    @PostMapping
    @RepeatSubmit
    public AjaxResult add(@RequestBody AppUnitInfo appUnitInfo) {
        //1.校验数据源是否正常
        AppUnitInfo appUnitInfo1 = appUnitInfoService.selectAppUnitInfoById(appUnitInfo.getDatasourceid());
        if(appUnitInfo1!=null){
            appUnitInfo.setPassword(appUnitInfo1.getPassword());
        }

        DataSourceDto dataSourceDto = copyByAppUnitInfo(appUnitInfo);
        String tempId= appUnitInfo.getDatasourceid();
        String  instanceId = StringUtils.getUUID();
        dataSourceDto.setInstanceId(instanceId);
        appUnitInfo.setDatasourceid(instanceId);
        try {
            AjaxResult ajaxResult = digDatasourceService.validataDatasource(dataSourceDto);
            if(ajaxResult.get("code").toString().equals("200")){
                //校验成功
                if(StringUtils.isNotNull(appUnitInfo.getImportStatus()) && appUnitInfo.getImportStatus().equals("1") && StringUtils.isEmpty(tempId)){
                    //注册
                    dataSourceDto.setDriverClass(appUnitInfo.getDriverclass());
                    AjaxResult ajaxResult1 = digDatasourceService.registerDatasource(dataSourceDto);
                    if(ajaxResult1.get("code").toString().equals("200")){
                        return toAjax(appUnitInfoService.insertAppUnitInfo(appUnitInfo));
                    }
                } else{
                    //不注册
                    return AjaxResult.success("校验成功!");
                }

            }
            return  AjaxResult.error("保存失败!请联系管理员");
        }catch (Exception e ){
            e.printStackTrace();
            appUnitInfo.setAppStatus("异常");
            appUnitInfoService.updateAppUnitInfo(appUnitInfo);
            return  AjaxResult.error("校验失败！"+e.getMessage());

        }

    }

    /**
     * 修改动态数据源管理
     */
    @ApiOperation("修改动态数据源管理")
    @PreAuthorize("@ss.hasPermi('datasource:appInfo:edit')")
    @Log(title = "动态数据源管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @RepeatSubmit
    public AjaxResult edit(@RequestBody AppUnitInfo appUnitInfo) {
        //抓取元数据
        GetMetaDataDto getMetaDataDto = new GetMetaDataDto();
        getMetaDataDto.setDatasourceId(appUnitInfo.getDatasourceid());
        getMetaDataDto.setDriverClass(appUnitInfo.getDriverclass());
        if(StringUtils.isNotNull(appUnitInfo.getImportStatus())){
            List<String> strings = Arrays.asList(appUnitInfo.getImportStatus().split(","));
            getMetaDataDto.setTableNameList(strings);
        }
      try {
          return  digDatasourceService.getDataSourceMetaData(getMetaDataDto);
      }catch (Exception e){
          e.printStackTrace();
          return  AjaxResult.error(e.getMessage());
      }

    }

    /**
     * 删除动态数据源管理
     */
    @ApiOperation("删除动态数据源管理")
    @PreAuthorize("@ss.hasPermi('datasource:appInfo:remove')")
    @Log(title = "动态数据源管理", businessType = BusinessType.DELETE)
    @GetMapping("/remove")
    @RepeatSubmit
    public AjaxResult remove(@RequestParam("datasourceid") String[] datasourceid) {
        return toAjax(appUnitInfoService.deleteAppUnitInfoByIds(datasourceid));
    }
    /**
     * 导出动态数据源管理列表
     */
    @ApiOperation("导出动态数据源管理列表")
    @PreAuthorize("@ss.hasPermi('datasource:appInfo:export')")
    @Log(title = "动态数据源管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(AppUnitInfo appUnitInfo, HttpServletResponse response) throws IOException {
        List<AppUnitInfo> list = appUnitInfoService.selectAppUnitInfoList(appUnitInfo);
        AjaxResult ajaxResult = ExcelUtils.exportExcel(list, "动态数据源管理", "动态数据源管理", AppUnitInfo. class,
                "动态数据源管理.xlsx", response);
        return ajaxResult;
    }

    /**
     * 获取动态数据源管理详细信息
     */
    @ApiOperation("获取动态数据源管理详细信息")
    @PreAuthorize("@ss.hasPermi('datasource:appInfo:query')")
    @GetMapping(value = "/{datasourceid}")
    public AjaxResult getInfo(@PathVariable("datasourceid") String datasourceid) {
        return AjaxResult.success(appUnitInfoService.selectAppUnitInfoByIdNoPassword(datasourceid));
    }
    /**
    * 数据导入数据库的下载模板
    * @return
    * @throws Exception
    */
    @ApiOperation("数据导入数据库的下载模板")
    @GetMapping("/importTemplate")
    public AjaxResult importTemplate(HttpServletResponse response) throws IOException {
        List<AppUnitInfo> list = new ArrayList<>();
        AjaxResult ajaxResult = ExcelUtils.exportExcel(list, "动态数据源管理模板", "动态数据源管理", AppUnitInfo. class,
        "动态数据源管理导入模板.xlsx", response);
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
    @Log(title = "动态数据源管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('datasource:appInfo:import')")
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
        ExcelImportResult<AppUnitInfo> requestList = ExcelImportUtil.importExcelMore(inputStream, AppUnitInfo. class,
        importParams);
        //导入的相应数据
        List<AppUnitInfo> list = requestList.getList();
        //调用getImgName方法获取图片名，这里是根据你配置的地址来存放的，也可以在实体类里面配置存放地址，需要可以开放
        //List<WxPortalApplyInfo> infoList = getImgName(list);
        //判断是否有错误
        if (requestList.isVerfiyFail()) {
            //getFailList()里面的就是所有校验失败的excel数据
            List<AppUnitInfo> failList = requestList.getFailList();
            ExportParams params = new ExportParams();
            params.setSheetName("动态数据源管理错误数据");
            Workbook workbook = ExcelExportUtil.exportExcel(params, AppUnitInfo. class,failList);
            return EasypoiUtil.downLoadExcel("动态数据源管理错误数据.xlsx", response, workbook);
        } else {
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            String operName = loginUser.getUsername();
            List<AppUnitInfo> appUnitInfos = appUnitInfoService.importAppUnitInfo(list, false, operName);
            //清理掉里面缓存的数据
                appUnitInfoService.clearappUnitInfoInfo();
            ExportParams params = new ExportParams();
            params.setSheetName("动态数据源管理导入数据");
            Workbook workbook = ExcelExportUtil.exportExcel(params, AppUnitInfo. class,appUnitInfos);
            return EasypoiUtil.downLoadExcel("动态数据源管理导入数据.xlsx", response, workbook);
        }
    }

    /**
    * 根据传来的id集合修改禁用启用状态
    */
    @ApiOperation("根据传来的id集合修改禁用启用状态")
    @PreAuthorize("@ss.hasPermi('datasource:appInfo:updateByIds')")
    @GetMapping("/updateByIds")
    public AjaxResult updateByIds(String ids, String disableEnableState) {
        Boolean isByIds = appUnitInfoService.updateByIds(ids, disableEnableState);
        if (isByIds) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }


    private DataSourceDto copyByAppUnitInfo(AppUnitInfo appUnitInfo){
        DataSourceDto dataSourceDto = new DataSourceDto();
        dataSourceDto.setDriverClass(appUnitInfo.getDriverclass());
        dataSourceDto.setDatabase(appUnitInfo.getDatabase());
        dataSourceDto.setInstanceDisplyName(appUnitInfo.getInstancedisplyname());
        dataSourceDto.setVersionCode(appUnitInfo.getVersioncode());
        dataSourceDto.setIp(appUnitInfo.getIp());
        dataSourceDto.setPassword(appUnitInfo.getPassword());
        dataSourceDto.setSchema(appUnitInfo.getSchema());
        dataSourceDto.setPort(appUnitInfo.getPort());
        dataSourceDto.setUserName(appUnitInfo.getUsername());
        return  dataSourceDto;
    }
}
