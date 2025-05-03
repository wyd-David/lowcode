package com.yabushan.form.controller;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.io.InputStream;

import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.form.domain.LowBusinessList;
import com.yabushan.form.service.ILowBusinessListService;
import com.yabushan.form.vo.AutoParamVo;
import com.yabushan.framework.web.domain.server.Sys;
import org.apache.poi.ss.usermodel.Workbook;
import com.yabushan.common.utils.ServletUtils;
import com.yabushan.common.core.domain.model.LoginUser;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.yabushan.framework.web.service.TokenService;
import org.aspectj.weaver.loadtime.Aj;
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
import com.yabushan.form.service.impl.LowFormInfoServiceImpl;
import com.yabushan.form.domain.LowFormInfo;
import com.yabushan.form.service.ILowFormInfoService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import com.yabushan.common.core.page.TableDataInfo;

/**
 * 基础单信息Controller
 *
 * @author yabushan
 * @date 2022-10-10
 */
@RestController
@RequestMapping("/form/info")
@Api(tags = {"基础单信息"})
public class LowFormInfoController extends BaseController {
    @Autowired
    private ILowFormInfoService lowFormInfoService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private ILowBusinessListService lowBusinessListService;


/**
 * 查询基础单信息列表
 */
@ApiOperation("查询基础单信息列表")
@Log(title = "查询基础单信息列表", businessType = BusinessType.OTHER)
//@PreAuthorize("@ss.hasPermi('form:info:list')")
@GetMapping("/list")
        public TableDataInfo list(LowFormInfo lowFormInfo) {
        startPage();
        List<LowFormInfo> list = lowFormInfoService.selectLowFormInfoList(lowFormInfo);

        return getDataTable(list);
    }

    /**
     * 导出基础单信息列表
     */
    @ApiOperation("导出基础单信息列表")
   // @PreAuthorize("@ss.hasPermi('form:info:export')")
    @Log(title = "基础单信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(LowFormInfo lowFormInfo, HttpServletResponse response) throws IOException {
        List<LowFormInfo> list = lowFormInfoService.selectLowFormInfoList(lowFormInfo);
        AjaxResult ajaxResult = ExcelUtils.exportExcel(list, "基础单信息", "基础单信息", LowFormInfo. class,
        "基础单信息.xlsx", response);
        return ajaxResult;
    }

    /**
     * 获取基础单信息详细信息
     */
    @ApiOperation("获取基础单信息详细信息")
   // @PreAuthorize("@ss.hasPermi('form:info:query')")
    @GetMapping(value = "/{formUuid}")
    public AjaxResult getInfo(@PathVariable("formUuid") String formUuid) {
        return AjaxResult.success(lowFormInfoService.selectLowFormInfoById(formUuid));
    }

    //匿名获取基础表单信息
    @GetMapping(value = "/getInfoNimin/{formUuid}")
    public AjaxResult getInfoNimin(@PathVariable("formUuid") String formUuid) {
        return AjaxResult.success(lowFormInfoService.selectLowFormInfoById(formUuid));
    }



    /**
     * 新增基础单信息
     */
    @ApiOperation("新增基础单信息")
   // @PreAuthorize("@ss.hasPermi('form:info:add')")
    @Log(title = "基础单信息", businessType = BusinessType.INSERT)
    @PostMapping
    @RepeatSubmit
    public AjaxResult add(@RequestBody LowFormInfo lowFormInfo) {
        return toAjax(lowFormInfoService.insertLowFormInfo(lowFormInfo));
    }

    /**
     * 修改基础单信息
     */
    @ApiOperation("修改基础单信息")
   // @PreAuthorize("@ss.hasPermi('form:info:edit')")
    @Log(title = "基础单信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @RepeatSubmit
    public AjaxResult edit(@RequestBody LowFormInfo lowFormInfo) {
        return toAjax(lowFormInfoService.updateLowFormInfo(lowFormInfo));
    }

    /**
     * 删除基础单信息
     */
    @ApiOperation("删除基础单信息")
  //  @PreAuthorize("@ss.hasPermi('form:info:remove')")
    @Log(title = "基础单信息", businessType = BusinessType.DELETE)
    @GetMapping("/remove")
    @RepeatSubmit
    public AjaxResult remove(@RequestParam("formUuid") String[] formUuid) {
        return toAjax(lowFormInfoService.deleteLowFormInfoByIds(formUuid));
    }

    /**
    * 数据导入数据库的下载模板
    * @return
    * @throws Exception
    */
    @ApiOperation("数据导入数据库的下载模板")
    @GetMapping("/importTemplate")
    public AjaxResult importTemplate(HttpServletResponse response) throws IOException {
        List<LowFormInfo> list = new ArrayList<>();
        AjaxResult ajaxResult = ExcelUtils.exportExcel(list, "基础单信息模板", "基础单信息", LowFormInfo. class,
        "基础单信息导入模板.xlsx", response);
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
    @Log(title = "基础单信息", businessType = BusinessType.IMPORT)
    //@PreAuthorize("@ss.hasPermi('form:info:import')")
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
        ExcelImportResult<LowFormInfo> requestList = ExcelImportUtil.importExcelMore(inputStream, LowFormInfo. class,
        importParams);
        //导入的相应数据
        List<LowFormInfo> list = requestList.getList();
        //调用getImgName方法获取图片名，这里是根据你配置的地址来存放的，也可以在实体类里面配置存放地址，需要可以开放
        //List<WxPortalApplyInfo> infoList = getImgName(list);
        //判断是否有错误
        if (requestList.isVerfiyFail()) {
            //getFailList()里面的就是所有校验失败的excel数据
            List<LowFormInfo> failList = requestList.getFailList();
            ExportParams params = new ExportParams();
            params.setSheetName("基础单信息错误数据");
            Workbook workbook = ExcelExportUtil.exportExcel(params, LowFormInfo. class,failList);
            return EasypoiUtil.downLoadExcel("基础单信息错误数据.xlsx", response, workbook);
        } else {
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            String operName = loginUser.getUsername();
            List<LowFormInfo> lowFormInfos = lowFormInfoService.importLowFormInfo(list, false, operName);
            //清理掉里面缓存的数据
                lowFormInfoService.clearlowFormInfoInfo();
            ExportParams params = new ExportParams();
            params.setSheetName("基础单信息导入数据");
            Workbook workbook = ExcelExportUtil.exportExcel(params, LowFormInfo. class,lowFormInfos);
            return EasypoiUtil.downLoadExcel("基础单信息导入数据.xlsx", response, workbook);
        }
    }

    /**
    * 根据传来的id集合修改禁用启用状态
    */
    @ApiOperation("根据传来的id集合修改禁用启用状态")
    //@PreAuthorize("@ss.hasPermi('form:info:updateByIds')")
    @GetMapping("/updateByIds")
    public AjaxResult updateByIds(String ids, String disableEnableState) {
        Boolean isByIds = lowFormInfoService.updateByIds(ids, disableEnableState);
        if (isByIds) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }


    //根据ID动态查询一条数据
    @ApiOperation("根据ID动态查询一条数据")
    @PostMapping("/queryOneAutoSqlInfo")
    public AjaxResult queryOneAutoSqlInfo(@RequestBody AutoParamVo autoParamVo){
        if(StringUtils.isEmpty(autoParamVo.getFormId()) || StringUtils.isEmpty(autoParamVo.getKey())){
            return null;
        }

       if(autoParamVo.getListId()!=null && !autoParamVo.getListId().equals("null") && StringUtils.isNotEmpty(autoParamVo.getListId())){
           //判断是从数据中台获取还是从本业务平台获取
           String listId= autoParamVo.getListId();
           LowBusinessList lowBusinessList = lowBusinessListService.selectLowBusinessListById(listId);
           if(StringUtils.isNotEmpty(lowBusinessList.getDataApi())){
               //从数据中台获取
               String[] split = lowBusinessList.getDataApi().split(",");
               if(split.length!=4){
                   return AjaxResult.error("请确认业务表中的API格式是否正确，QUERY,ADD,UPDATE,DELETE");
               }
               String API = split[0];
               String param = "{'id':'"+autoParamVo.getKey()+"'"
                       +",'formId':'"+autoParamVo.getFormId()+"'"
                       +",'key':'"+autoParamVo.getKey()+"'"
                       +",'ID':'"+autoParamVo.getKey()+"'"
                       +",'listId':'"+autoParamVo.getListId()+"'}";
               JSONObject row = lowFormInfoService.getData(API,param, "row");
               Map<String, Object> useOrDisable = new HashMap<>();

               System.out.println(row.toString());
               String rows = row.get("rows").toString();
               JSONArray rows1 = row.getJSONArray("rows");
               Object o = rows1.get(0);


               return AjaxResult.success(o);


           }
       }


        List<Map<String, Object>> o = lowFormInfoService.queryOneAutoSqlInfo(autoParamVo.getFormId(), autoParamVo.getKey());
        if(o.size()==0){
            return null;
        }else if(!o.get(0).get("CREATED_BY").toString().equals(SecurityUtils.getUsername())){
           // return  AjaxResult.error("您无权限查看该数据！");
        }
        Map<String, Object> stringObjectMap = o.get(0);
        Map<String, Object> useOrDisable = new HashMap<>();
        for (Map.Entry<String, Object> entry : stringObjectMap.entrySet()) {
            String mapKey = entry.getKey();
            String mapValue = entry.getValue()+"".trim();
            if(mapValue.equals("null")){
                mapValue="";
                stringObjectMap.put(mapKey,"");continue;
            }


            if((mapKey.contains("file") || mapKey.contains("picture")) && mapValue!=null && mapValue.contains("[")  && mapValue.contains("]")){
                //处理文件
               
                    List<Map<String,String>> listObjectSec = JSONArray.parseObject(mapValue,List.class);
                    stringObjectMap.put(mapKey,listObjectSec.equals("null")?"":listObjectSec);
               
                
               /* for(Map<String,String> mapList: listObjectSec){
                    System.out.println(entry.getKey()+">>"+entry.getValue());
                }*/

               

            }else{
                //处理非文件
                if(mapValue.contains("[\"") && mapValue.contains("\"]")){
                    //该字段为数组[]time-range-field
                    mapValue =  mapValue.replace("[\"","").replace("\"]","").replace("\"","");
                    String[] result = mapValue.split(",");
                    stringObjectMap.put(mapKey,result.equals("null")?"":result);
                }
                if(mapValue.contains("[") && mapValue.contains("]")){
                    //该字段为数组[]time-range-field
                    mapValue =  mapValue.replace("[","").replace("]","").replace("\"","");
                    String[] result = mapValue.split(",");
                    if(mapKey.contains("checkbox") || mapKey.contains("cascader")){
                        String[] num=new String[result.length];
                        for(int i=0;i<num.length;i++){
                            num[i]=result[i].trim();
                        }
                        stringObjectMap.put(mapKey,num.equals("null")?"":num);
                    }else{
                        stringObjectMap.put(mapKey,result.equals("null")?"":result);
                    }
                }
                if(mapKey.contains("rate") || mapKey.contains("select")){
                    stringObjectMap.put(mapKey,mapValue.trim().equals("null")?"":mapValue.trim());
                }
                if(mapKey.contains("radio") ){
                //    Integer value = Integer.valueOf(mapValue);
                    stringObjectMap.put(mapKey,mapValue.trim().equals("null")?"":mapValue.trim());
                }
                if(mapKey.contains("switch")){
                    if(mapValue.equals("true")){
                        stringObjectMap.put(mapKey,true);
                    }else{
                        stringObjectMap.put(mapKey,false);
                    }
                }
            }


        }
        stringObjectMap.putAll(useOrDisable);
        return AjaxResult.success(stringObjectMap);

    }


    //保存表单数据
    @ApiOperation("保存表单数据数据")
    @PostMapping("/saveFormInfo")
    @Log(title = "数据修改", businessType = BusinessType.UPDATE)
    public AjaxResult saveFormInfo(@RequestBody Map paramMap) throws Exception {

        return  lowFormInfoService.saveFormInfo(paramMap);

    }
    @ApiOperation("保存表单数据数据")
    @PostMapping("/saveFormInfoNimin")
    @Log(title = "数据修改", businessType = BusinessType.UPDATE)
    public AjaxResult saveFormInfoNimin(@RequestBody Map paramMap) throws Exception {

        return  lowFormInfoService.saveFormInfo(paramMap);

    }



    /**
     * 将Map字符串转换为Map
     *
     * @param str Map字符串
     * @return Map
     */
    public static Map<String,String> mapStringToMap(String str){
        str = str.substring(1, str.length()-1);
        String[] strs = str.split(",");
        Map<String,String> map = new HashMap<String, String>();
        for (String string : strs) {
            String key = string.split("=")[0];
            String value = string.split("=")[1];
            // 去掉头部空格
            String key1 = key.trim();
            String value1 = value.trim();
            map.put(key1, value1);
        }
        return map;
    }

    public static Map<String, String> stringToMap(String str) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> map = objectMapper.readValue(str, new TypeReference<Map<String,String>>(){});
        return map;
    }


    //保存主子表单数据
    @ApiOperation("保存表单数据数据")
    @PostMapping("/saveChildFormInfo")
    @Log(title = "数据修改", businessType = BusinessType.UPDATE)
    public AjaxResult saveChildFormInfo(@RequestBody Map paramMap) throws Exception {

        return  lowFormInfoService.saveChildFormInfo(paramMap);

    }

}
