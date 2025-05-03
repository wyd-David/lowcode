package com.yabushan.web.controller.datasource;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.io.InputStream;

import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.datasource.domain.AppUnitInstance;
import com.yabushan.datasource.domain.DigDataResource;
import com.yabushan.datasource.service.DynamicDataSourceFactory;
import com.yabushan.datasource.service.IAppUnitInstanceService;
import com.yabushan.datasource.service.IDigDataResourceService;
import com.yabushan.datasource.utils.SQLValidator;
import com.yabushan.system.domain.DataApiInfos;
import com.yabushan.system.service.IDataApiInfosService;
import com.yabushan.web.Threads.EtlThreadServer;
import org.apache.poi.ss.usermodel.Workbook;
import com.yabushan.common.utils.ServletUtils;
import com.yabushan.common.core.domain.model.LoginUser;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.yabushan.framework.web.service.TokenService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.yabushan.common.annotation.Log;
import com.yabushan.common.core.controller.BaseController;
import com.yabushan.common.core.domain.AjaxResult;
import com.yabushan.common.enums.BusinessType;
import com.yabushan.poi.utils.EasypoiUtil;
import com.yabushan.poi.utils.ExcelUtils;
import com.yabushan.common.annotation.RepeatSubmit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.yabushan.datasource.domain.EtlWorkCollect;
import com.yabushan.datasource.service.IEtlWorkCollectService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.yabushan.common.core.page.TableDataInfo;

/**
 * 作业开发Controller
 *
 * @author yabushan
 * @date 2024-01-21
 */
@RestController
@RequestMapping("/datasource/etlDev")
@Api(tags = {"作业开发"})
public class EtlWorkCollectController extends BaseController {
    @Autowired
    private IEtlWorkCollectService etlWorkCollectService;
    @Autowired
    private TokenService tokenService;

    @Autowired
    private IDataApiInfosService dataApiInfosService;


    @Autowired
    private IAppUnitInstanceService iAppUnitInstanceService;

    @Autowired
    private IDigDataResourceService digDataResourceService;

/**
 * 查询作业开发列表
 */
@ApiOperation("查询作业开发列表")
//@Log(title = "查询作业开发列表", businessType = BusinessType.OTHER)
@PreAuthorize("@ss.hasPermi('datasource:etlDev:list')")
@GetMapping("/list")
        public TableDataInfo list(EtlWorkCollect etlWorkCollect) {
    if(!"admin".equals(SecurityUtils.getUsername())){
        etlWorkCollect.setCreateBy(SecurityUtils.getUsername());
    }
        startPage();
        List<EtlWorkCollect> list = etlWorkCollectService.selectEtlWorkCollectList(etlWorkCollect);
        return getDataTable(list);
    }

    /**
     * 导出作业开发列表
     */
    @ApiOperation("导出作业开发列表")
    @PreAuthorize("@ss.hasPermi('datasource:etlDev:export')")
    @Log(title = "作业开发", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(EtlWorkCollect etlWorkCollect, HttpServletResponse response) throws IOException {
        List<EtlWorkCollect> list = etlWorkCollectService.selectEtlWorkCollectList(etlWorkCollect);
        AjaxResult ajaxResult = ExcelUtils.exportExcel(list, "作业开发", "作业开发", EtlWorkCollect. class,
        "作业开发.xlsx", response);
        return ajaxResult;
    }

    /**
     * 获取作业开发详细信息
     */
    @ApiOperation("获取作业开发详细信息")
    @PreAuthorize("@ss.hasPermi('datasource:etlDev:query')")
    @GetMapping(value = "/{collectId}")
    public AjaxResult getInfo(@PathVariable("collectId") String collectId) {
        return AjaxResult.success(etlWorkCollectService.selectEtlWorkCollectById(collectId));
    }

    /**
     * 新增作业开发
     */
    @ApiOperation("新增作业开发")
    @PreAuthorize("@ss.hasPermi('datasource:etlDev:add')")
    @Log(title = "作业开发", businessType = BusinessType.INSERT)
    @PostMapping
    @RepeatSubmit
    public AjaxResult add(@RequestBody EtlWorkCollect etlWorkCollect) {
        //校验
        //增量1作业时，增量字段必填、增量字段类型必填
        if("1".equals(etlWorkCollect.getCollectAdd())){
            if(StringUtils.isNull(etlWorkCollect.getFieldName()) || StringUtils.isNull(etlWorkCollect.getFieldType())){
                return  AjaxResult.error("增量字段必填、增量字段类型必填");
            }
        }

        //检验执行前和执行后sql是否正确
        boolean b=true;
        String s = null;
        if(StringUtils.isNotNull(etlWorkCollect.getBeforeSql()) && StringUtils.isNotEmpty(etlWorkCollect.getBeforeSql())){
            s = DynamicDataSourceFactory.validateApiSql(etlWorkCollect.getBeforeSql());
            b = SQLValidator.validateSQL(s);
            if(b){
                //  return AjaxResult.success("校验通过");
            }else{
                return AjaxResult.error("校验执行前sql失败!sql为:"+s);
            }
        }

        if(StringUtils.isNotNull(etlWorkCollect.getAfterSql()) && StringUtils.isNotEmpty(etlWorkCollect.getAfterSql())){
            s = DynamicDataSourceFactory.validateApiSql(etlWorkCollect.getAfterSql());
            b = SQLValidator.validateSQL(s);
            if(b){
                //  return AjaxResult.success("校验通过");
            }else{
                return AjaxResult.error("校验执行前sql失败!sql为:"+s);
            }
        }

        //作业类型为抽取2时，读取和写入表的节点必填
        if("2".equals(etlWorkCollect.getCollectType())){
            if(StringUtils.isNull(etlWorkCollect.getApiCodeIn()) ){
                return  AjaxResult.error("抽取作业类型时，读取服务不能为空");

            }
            DataApiInfos dataApiInfos = dataApiInfosService.selectDataApiInfosCode(etlWorkCollect.getApiCodeIn());
            if(dataApiInfos==null){
                return  AjaxResult.error("读取服务不存在");
            }

            //校验写入表是否存在
            DigDataResource digDataResource = new DigDataResource();
            digDataResource.setDatasourceId(etlWorkCollect.getCollectToDatasourceid());
            digDataResource.setResourceTableName(etlWorkCollect.getCollectToTableName());
            List<DigDataResource> digDataResources = digDataResourceService.selectDataResourceList(digDataResource);
            if(digDataResources.size()==0){
                return  AjaxResult.error("抽取写入表不存在");
            }
          /*  DataApiInfos dataApiInfos2 = dataApiInfosService.selectDataApiInfosCode(etlWorkCollect.getApiCodeOut());
            if(dataApiInfos2==null){
                return  AjaxResult.error("读取或写入服务不存在");
            }*/
        }else{
            //校验采集表是否存在
            DigDataResource digDataResource = new DigDataResource();
            digDataResource.setDatasourceId(etlWorkCollect.getCollectResouceId());
            digDataResource.setResourceTableName(etlWorkCollect.getCollectTableName());
            List<DigDataResource> digDataResources = digDataResourceService.selectDataResourceList(digDataResource);
            if(digDataResources.size()==0){
                return  AjaxResult.error("采集引擎或表不存在");

            }


        }
        if(StringUtils.isNotNull(etlWorkCollect.getCollectToDatasourceid())){
            AppUnitInstance appUnitInstance = iAppUnitInstanceService.selectAppUnitInstanceById(etlWorkCollect.getCollectToDatasourceid());
            if(appUnitInstance==null){
                return  AjaxResult.error("写入数据引擎不存在");
            }
        }else{
            return  AjaxResult.error("写入数据引擎不能为空");

        }


        return toAjax(etlWorkCollectService.insertEtlWorkCollect(etlWorkCollect));
    }

    /**
     * 修改作业开发
     */
    @ApiOperation("修改作业开发")
    @PreAuthorize("@ss.hasPermi('datasource:etlDev:edit')")
    @Log(title = "作业开发", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @RepeatSubmit
    public AjaxResult edit(@RequestBody EtlWorkCollect etlWorkCollect) {
        //校验
        //增量1作业时，增量字段必填、增量字段类型必填
        if("1".equals(etlWorkCollect.getCollectAdd())){
            if(StringUtils.isNull(etlWorkCollect.getFieldName()) || StringUtils.isNull(etlWorkCollect.getFieldType())){
                return  AjaxResult.error("增量字段必填、增量字段类型必填");
            }
        }

        //检验执行前和执行后sql是否正确
        boolean b=true;
        String s = null;
        if(StringUtils.isNotNull(etlWorkCollect.getBeforeSql()) && StringUtils.isNotEmpty(etlWorkCollect.getBeforeSql())){
            s = DynamicDataSourceFactory.validateApiSql(etlWorkCollect.getBeforeSql());
            b = SQLValidator.validateSQL(s);
            if(b){
              //  return AjaxResult.success("校验通过");
            }else{
                return AjaxResult.error("校验执行前sql失败!sql为:"+s);
            }
        }

        if(StringUtils.isNotNull(etlWorkCollect.getAfterSql()) && StringUtils.isNotEmpty(etlWorkCollect.getAfterSql())){
            s = DynamicDataSourceFactory.validateApiSql(etlWorkCollect.getAfterSql());
            b = SQLValidator.validateSQL(s);
            if(b){
                //  return AjaxResult.success("校验通过");
            }else{
                return AjaxResult.error("校验执行前sql失败!sql为:"+s);
            }
        }

        if(StringUtils.isNotNull(etlWorkCollect.getCollectToDatasourceid())){
            AppUnitInstance appUnitInstance = iAppUnitInstanceService.selectAppUnitInstanceById(etlWorkCollect.getCollectToDatasourceid());
            if(appUnitInstance==null){
                return  AjaxResult.error("写入数据引擎不存在");
            }
        }else{
            return  AjaxResult.error("写入数据引擎不能为空");

        }

        //作业类型为抽取2时，读取和写入的节点必填
        if("2".equals(etlWorkCollect.getCollectType())){
            if(StringUtils.isNull(etlWorkCollect.getApiCodeIn()) ){
                return  AjaxResult.error("抽取作业类型时，读取节点和写入表不能为空");

            }
            DataApiInfos dataApiInfos = dataApiInfosService.selectDataApiInfosCode(etlWorkCollect.getApiCodeIn());
            if(dataApiInfos==null){
                return  AjaxResult.error("读取服务不存在");
            }

            //校验写入表是否存在
            DigDataResource digDataResource = new DigDataResource();
            digDataResource.setDatasourceId(etlWorkCollect.getCollectToDatasourceid());
            digDataResource.setResourceTableName(etlWorkCollect.getCollectToTableName());
            List<DigDataResource> digDataResources = digDataResourceService.selectDataResourceList(digDataResource);
            if(digDataResources.size()==0){
                return  AjaxResult.error("抽取写入表不存在");
            }
           /* DataApiInfos dataApiInfos2 = dataApiInfosService.selectDataApiInfosCode(etlWorkCollect.getApiCodeOut());

            if(dataApiInfos2==null){
                return  AjaxResult.error("读取或写入服务不存在");
            }*/
        }else{
            //采集时，数据源不能为空
            DigDataResource digDataResource = new DigDataResource();
            digDataResource.setDatasourceId(etlWorkCollect.getCollectResouceId());
            digDataResource.setResourceTableName(etlWorkCollect.getCollectTableName());
            List<DigDataResource> digDataResources = digDataResourceService.selectDataResourceList(digDataResource);
            if(digDataResources.size()==0){
                return  AjaxResult.error("采集引擎或表不存在");

            }
        }

        EtlWorkCollect etlWorkCollect1 = etlWorkCollectService.selectEtlWorkCollectById(etlWorkCollect.getCollectId());
        etlWorkCollect1.setUpdateTime(new Date());
        etlWorkCollect1.setUpdateBy(SecurityUtils.getUsername());
        etlWorkCollect1.setCollectName(etlWorkCollect.getCollectName());
        etlWorkCollect1.setCollectResouceId(etlWorkCollect.getCollectResouceId());
        etlWorkCollect1.setCollectTableName(etlWorkCollect.getCollectTableName());
        etlWorkCollect1.setCollectToDatasourceid(etlWorkCollect.getCollectToDatasourceid());
        etlWorkCollect1.setCollectToTableName(etlWorkCollect.getCollectToTableName());
        etlWorkCollect1.setCollectType(etlWorkCollect.getCollectType());
        etlWorkCollect1.setCollectAdd(etlWorkCollect.getCollectAdd());
        etlWorkCollect1.setFieldName(etlWorkCollect.getFieldName());
        etlWorkCollect1.setFieldValue(etlWorkCollect.getFieldValue());
        etlWorkCollect1.setFieldType(etlWorkCollect.getFieldType());
        etlWorkCollect1.setApiCodeIn(etlWorkCollect.getApiCodeIn());
        etlWorkCollect1.setApiCodeOut(etlWorkCollect.getApiCodeOut());
        etlWorkCollect1.setRunNodeId(etlWorkCollect.getRunNodeId());
        etlWorkCollect1.setBeforeSql(etlWorkCollect.getBeforeSql());
        etlWorkCollect1.setAfterSql(etlWorkCollect.getAfterSql());

        return toAjax(etlWorkCollectService.updateEtlWorkCollect(etlWorkCollect1));
    }

    /**
     * 删除作业开发
     */
    @ApiOperation("删除作业开发")
    @PreAuthorize("@ss.hasPermi('datasource:etlDev:remove')")
    @Log(title = "作业开发", businessType = BusinessType.DELETE)
    @GetMapping("/remove")
    @RepeatSubmit
    public AjaxResult remove(@RequestParam("collectId") String[] collectId) {
        return toAjax(etlWorkCollectService.deleteEtlWorkCollectByIds(collectId));
    }

    /**
    * 数据导入数据库的下载模板
    * @return
    * @throws Exception
    */
    @ApiOperation("数据导入数据库的下载模板")
    @GetMapping("/importTemplate")
    public AjaxResult importTemplate(HttpServletResponse response) throws IOException {
        List<EtlWorkCollect> list = new ArrayList<>();
        AjaxResult ajaxResult = ExcelUtils.exportExcel(list, "作业开发模板", "作业开发", EtlWorkCollect. class,
        "作业开发导入模板.xlsx", response);
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
    @Log(title = "作业开发", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('datasource:etlDev:import')")
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
        ExcelImportResult<EtlWorkCollect> requestList = ExcelImportUtil.importExcelMore(inputStream, EtlWorkCollect. class,
        importParams);
        //导入的相应数据
        List<EtlWorkCollect> list = requestList.getList();
        //调用getImgName方法获取图片名，这里是根据你配置的地址来存放的，也可以在实体类里面配置存放地址，需要可以开放
        //List<WxPortalApplyInfo> infoList = getImgName(list);
        //判断是否有错误
        if (requestList.isVerfiyFail()) {
            //getFailList()里面的就是所有校验失败的excel数据
            List<EtlWorkCollect> failList = requestList.getFailList();
            ExportParams params = new ExportParams();
            params.setSheetName("作业开发错误数据");
            Workbook workbook = ExcelExportUtil.exportExcel(params, EtlWorkCollect. class,failList);
            return EasypoiUtil.downLoadExcel("作业开发错误数据.xlsx", response, workbook);
        } else {
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            String operName = loginUser.getUsername();
            List<EtlWorkCollect> etlWorkCollects = etlWorkCollectService.importEtlWorkCollect(list, false, operName);
            //清理掉里面缓存的数据
                etlWorkCollectService.clearetlWorkCollectInfo();
            ExportParams params = new ExportParams();
            params.setSheetName("作业开发导入数据");
            Workbook workbook = ExcelExportUtil.exportExcel(params, EtlWorkCollect. class,etlWorkCollects);
            return EasypoiUtil.downLoadExcel("作业开发导入数据.xlsx", response, workbook);
        }
    }

    /**
    * 根据传来的id集合修改禁用启用状态
    */
    @ApiOperation("根据传来的id集合修改禁用启用状态")
    @PreAuthorize("@ss.hasPermi('datasource:etlDev:updateByIds')")
    @GetMapping("/updateByIds")
    public AjaxResult updateByIds(String ids, String disableEnableState) {
        Boolean isByIds = etlWorkCollectService.updateByIds(ids, disableEnableState);
        if (isByIds) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }


    //立即运行
    @ApiOperation("立即运行")
    @GetMapping("/dataFlowRun")
    public AjaxResult dataFlowRun(String ids) {
        // digDatasourceService.dataFlow(ids,"dataFlow");
        //判断作业所属类型是采集还是抽取
        EtlWorkCollect etlWorkCollect = etlWorkCollectService.selectEtlWorkCollectById(ids);
        if("1".equals(etlWorkCollect.getLastStatus())){
            return  AjaxResult.error("作业已在运行中，无法再此运行");

        }
        if("1".equals(etlWorkCollect.getCollectType())){
            //采集
            Thread thread = new Thread(new EtlThreadServer(ids,"dataCollect"));
            thread.start();
            return AjaxResult.success("数据采集中......");
        }else if("2".equals(etlWorkCollect.getCollectType())){
            //抽取
            Thread thread = new Thread(new EtlThreadServer(ids,"dataFlow"));
            thread.start();
            return AjaxResult.success("数据抽取中......");
        }
        return  AjaxResult.error("传入的作业信息为:"+ids);

    }




    //数据抽取
    @ApiOperation("数据抽取")
    @GetMapping("/dataFlow")
    public AjaxResult dataflow(String ids) {
       // digDatasourceService.dataFlow(ids,"dataFlow");
        //保存日志
        Thread thread = new Thread(new EtlThreadServer(ids,"dataflow"));
        thread.start();
        return AjaxResult.success("数据抽取中......");
    }




    //数据采集
    @ApiOperation("数据采集")
    @GetMapping("/dataCollect")
    public AjaxResult dataCollect(String ids) {
       // digDatasourceService.dataFlow(ids,"dataCollect");
        //保存日志
        Thread thread = new Thread(new EtlThreadServer(ids,"dataCollect"));
        thread.start();
        return AjaxResult.success("数据抽取中......");
    }


}
