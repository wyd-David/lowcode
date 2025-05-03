package com.yabushan.form.controller;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yabushan.common.core.domain.entity.SysDept;
import com.yabushan.common.core.domain.entity.SysUser;
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.common.utils.spring.SpringUtils;
import com.yabushan.form.domain.LowFormFiledInfo;
import com.yabushan.form.domain.LowFormInfo;
import com.yabushan.form.domain.PmisApiLog;
import com.yabushan.form.mapper.PmisApiLogMapper;
import com.yabushan.form.mapper.SqlExecuteMapper;
import com.yabushan.form.service.ILowFormFiledInfoService;
import com.yabushan.form.service.ILowFormInfoService;
import com.yabushan.form.service.impl.PmisApiLogServiceImpl;
import com.yabushan.form.utils.Base64Utils;
import com.yabushan.form.utils.HmacSHA1Signer;
import com.yabushan.system.domain.DataApiInfos;
import com.yabushan.system.service.IDataApiInfosService;
import com.yabushan.framework.web.service.TokenService;
import com.yabushan.system.service.ISysDeptService;
import com.yabushan.system.service.ISysUserService;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.yabushan.common.annotation.Log;
import com.yabushan.common.core.controller.BaseController;
import com.yabushan.common.core.domain.AjaxResult;
import com.yabushan.common.enums.BusinessType;
import com.yabushan.poi.utils.ExcelUtils;
import com.yabushan.common.annotation.RepeatSubmit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.yabushan.common.core.page.TableDataInfo;

/**
 * 数据服务APIController
 *
 * @author yabushan
 * @date 2022-11-11
 */
@RestController
@RequestMapping("/system/apiInfo")
@Api(tags = {"数据服务API"})
public class DataApiInfosController extends BaseController implements Runnable{
    @Autowired
    private IDataApiInfosService dataApiInfosService;


    @Autowired
    private SqlExecuteMapper sqlExecuteMapper;

    @Autowired
    private ILowFormInfoService lowFormInfoService;
    @Autowired
    private ILowFormFiledInfoService lowFormFiledInfoService;
   
    


    @Value("${daas.url}")
    private String url;
    @Value("${daas.accessKeyId}")
    private String accessKeyId;
    @Value("${daas.secertKey}")
    private String secertKey;
    @Value("${daas.projectId}")
    private String projectId;

/**
 * 查询数据服务API列表
 */
@ApiOperation("查询数据服务API列表")
//@Log(title = "查询数据服务API列表", businessType = BusinessType.OTHER)
@PreAuthorize("@ss.hasPermi('system:apiInfo:list')")
@GetMapping("/list")
        public TableDataInfo list(DataApiInfos dataApiInfos) {
        startPage();
        List<DataApiInfos> list = dataApiInfosService.selectDataApiInfosList(dataApiInfos);
        return getDataTable(list);
    }

    /**
     * 导出数据服务API列表
     */
    @ApiOperation("导出数据服务API列表")
    @PreAuthorize("@ss.hasPermi('system:apiInfo:export')")
    @Log(title = "数据服务API", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(DataApiInfos dataApiInfos, HttpServletResponse response) throws IOException {
        List<DataApiInfos> list = dataApiInfosService.selectDataApiInfosList(dataApiInfos);
        AjaxResult ajaxResult = ExcelUtils.exportExcel(list, "数据服务API", "数据服务API", DataApiInfos. class,
        "数据服务API.xlsx", response);
        return ajaxResult;
    }

    /**
     * 获取数据服务API详细信息
     */
    @ApiOperation("获取数据服务API详细信息")
    @PreAuthorize("@ss.hasPermi('system:apiInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(dataApiInfosService.selectDataApiInfosById(id));
    }

    /**
     * 新增数据服务API
     */
    @ApiOperation("新增数据服务API")
    @PreAuthorize("@ss.hasPermi('system:apiInfo:add')")
    @Log(title = "数据服务API", businessType = BusinessType.INSERT)
    @PostMapping
    @RepeatSubmit
    public AjaxResult add(@RequestBody DataApiInfos dataApiInfos) {
        //校验数据与是否存在

        if(StringUtils.isEmpty(dataApiInfos.getApiCode()) || StringUtils.isEmpty(dataApiInfos.getDatasourceId()) ||
                StringUtils.isEmpty(dataApiInfos.getApiDefination())){
            return AjaxResult.error("请输入必填项");

        }
        if(!"1".equals(dataApiInfos.getDatasourceId())){
            String sql ="SELECT COUNT(1) FROM app_unit_instance F WHERE F.instance_id= '"+dataApiInfos.getDatasourceId()+"'";
            Object execute = sqlExecuteMapper.execute(sql);

            if(Integer.valueOf(execute.toString())==0){
                return AjaxResult.error("数据源ID不存在");
            }
            
        }
        
        try {
            
            int i = dataApiInfosService.insertDataApiInfos(dataApiInfos);
            return AjaxResult.success(i);
        }catch (Exception e){
            e.printStackTrace();
            return  AjaxResult.error(e.getMessage());
            
        }

        
    }

    /**
     * 修改数据服务API
     */
    @ApiOperation("修改数据服务API")
    @PreAuthorize("@ss.hasPermi('system:apiInfo:edit')")
    @Log(title = "数据服务API", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @RepeatSubmit
    public AjaxResult edit(@RequestBody DataApiInfos dataApiInfos) {


        return toAjax(dataApiInfosService.updateDataApiInfos(dataApiInfos));
    }

    /**
     * 删除数据服务API
     */
    @ApiOperation("删除数据服务API")
    @PreAuthorize("@ss.hasPermi('system:apiInfo:remove')")
    @Log(title = "数据服务API", businessType = BusinessType.DELETE)
    @GetMapping("/remove")
    @RepeatSubmit
    public AjaxResult remove(@RequestParam("id") Long[] id) {
        return toAjax(dataApiInfosService.deleteDataApiInfosByIds(id));
    }

    /**
    * 数据导入数据库的下载模板
    * @return
    * @throws Exception
    */
    @ApiOperation("数据导入数据库的下载模板")
    @GetMapping("/importTemplate")
    public AjaxResult importTemplate(HttpServletResponse response) throws IOException {
        List<DataApiInfos> list = new ArrayList<>();
        AjaxResult ajaxResult = ExcelUtils.exportExcel(list, "数据服务API模板", "数据服务API", DataApiInfos. class,
        "数据服务API导入模板.xlsx", response);
        return ajaxResult;
    }



    /**
    * 根据传来的id集合修改禁用启用状态
    */
    @ApiOperation("根据传来的id集合修改禁用启用状态")
    @PreAuthorize("@ss.hasPermi('system:apiInfo:updateByIds')")
    @GetMapping("/updateByIds")
    public AjaxResult updateByIds(String ids, String disableEnableState) {
        Boolean isByIds = dataApiInfosService.updateByIds(ids, disableEnableState);
        if (isByIds) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }


    /**
     * 万能接口
     * @param
     * @return
     */
    @RequestMapping(value = "/data/customerApi")
    public AjaxResult getMessageRow(String ApiCode,String param,String rowType) {
        PmisApiLog pmisApiLog = new PmisApiLog();
        pmisApiLog.setLogId(com.yabushan.common.utils.StringUtils.getUUID());
        pmisApiLog.setApiCode(ApiCode);
        pmisApiLog.setApiRowType(rowType);
        pmisApiLog.setApiParam(param);
        //校验API是否可用
        if(StringUtils.isEmpty(ApiCode)){
            return AjaxResult.error("ApiCode不能为空！");
        }
        DataApiInfos dataApiInfos = new DataApiInfos();
        dataApiInfos.setApiCode(ApiCode);
        DataApiInfos dataApiInfos1 = dataApiInfosService.selectDataApiInfosCode(ApiCode);
        if(dataApiInfos1==null){
            return AjaxResult.error("API不存在");

        }

        String apiStatus = dataApiInfos1.getApiStatus();
        if("N".equals(apiStatus)){
            return AjaxResult.error("API已停用，请联系管理员调整！");
        }else if("G".equals(apiStatus)){
            //内部使用
            //判断是否有权限使用,同一个公司的人才能使用
            String username = SecurityUtils.getUsername();
            String createBy = dataApiInfos1.getCreateBy();
            Long companyIdByUserName = dataApiInfosService.getCompanyIdByUserName(username);
            Long companyIdByUserName1 = dataApiInfosService.getCompanyIdByUserName(createBy);
            if(!companyIdByUserName1.equals(companyIdByUserName)){
                return AjaxResult.error("API只允许内部使用，请联系管理员调整！");
            }

        }

        String results = null;
        if(null ==param || "".equals(param)){
            param="{}";
        }else{
            if(!param.startsWith("{")  || !param.endsWith("}")){
                return  AjaxResult.error("param参数必须是格式：{key:value,key2:value2}");
            }
        }
        JSONObject jsonObject=null;
        try{
             jsonObject = JSONObject.parseObject(param);
            results=  jsonObject.toString();
        }catch (Exception e){
            return  AjaxResult.error(results +e.toString());
        }

        if(StringUtils.isEmpty(rowType)){
            rowType="list";
        }else{
            if(!rowType.equals("row") &&   !rowType.equals("list") && !rowType.equals("platform")){
                return AjaxResult.error("类型参数错误！");
            }
        }
        if("platform".equals(rowType)){
            //从平台执行
            //获取到表单所有字段
            String tableName = dataApiInfos1.getApiCode().replace("NGS_QUERY_", "").replace("NGS_DELETE_", "")
                    .replace("NGS_INSERT_", "").replace("NGS_UPDATE_", "");
            LowFormInfo lowFormInfo = lowFormInfoService.selectLowFormInfoByFormEntityName(tableName);
            String exeSql =dataApiInfos1.getApiDefination().replaceAll("\\{","").replaceAll("}","");
            List<LowFormFiledInfo> formFiledInfos = lowFormFiledInfoService.selectLowFormFiledInfoByFormUuid(lowFormInfo.getFormUuid());
            for(int i=0;i<formFiledInfos.size();i++){
                LowFormFiledInfo lowFormFiledInfo = formFiledInfos.get(i);
                //2.根据传入的参数替换sql
                //获取参数
                String filedName = lowFormFiledInfo.getFiledName();
                //获取值
                if(jsonObject.containsKey(filedName)){
                    String value = jsonObject.get(filedName).toString();
                    filedName="#"+filedName+"#";
                    value="'"+value+"'";
                    exeSql = exeSql.replace(filedName, value);
                }

            }
            if(jsonObject.containsKey("id")){
                //如果对象有id
                String valueId="'"+jsonObject.get("id").toString()+"'";
                exeSql=exeSql.replaceAll("#id#",valueId);
            }
            if(jsonObject.containsKey("ID")){
                //如果对象有id
                String valueId="'"+jsonObject.get("ID").toString()+"'";
                exeSql=exeSql.replaceAll("#ID#",valueId);
            }
            //3.执行sql拿到返回结果
            Object execute = null;
            try{
                execute= sqlExecuteMapper.selectPublicItemList(exeSql);
            }catch (Exception e){
                execute= e.toString();
            }
            pmisApiLog.setApiResult(execute.toString());
            //保存日志
            Thread thread = new Thread(new DataApiInfosController(pmisApiLog));
            thread.start();
            //4.返回执行结果给客户端
            return AjaxResult.success(execute);
        }else{
            JSONObject result= getData(ApiCode,param,rowType);
            return AjaxResult.success("success",result);
        }
     
       
    }


    public JSONObject getData(String ApiCode , String param,String rowType ){
        String reslt = result(param, ApiCode,rowType);
        JSONObject jsonObject = JSON.parseObject(reslt);

        return  jsonObject;
    }

    public String getCount(String ApiCode , String param,String rowType ){
        String reslt = result(param, ApiCode,"list");
        JSONObject jsonObject = JSONObject.parseObject(reslt);
        String count = jsonObject.getString("rows");

        count=count.replace("[[\"","");
        count=count.replace("\"]]","");


        return  count;
    }

    public String result(String param, String apiCode,String rowType) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String resourceApiCode = apiCode;//"test2_39"; //根据实际修改,也可以使用resourceId
            String urlPath = url + resourceApiCode;
            HttpPost httpPost = new HttpPost(urlPath);
            httpPost.setHeader("X-Amz-Date", format.format(new Date()));
            StringBuffer stringBuffer = new StringBuffer();
            if("row".equals(rowType)){
                stringBuffer.append("{'sqlMode':'normal','rowType':'row','dataServiceParams':");
            }else{
                stringBuffer.append("{'sqlMode':'normal','rowType':'list','dataServiceParams':");
            }

            stringBuffer.append(param);
            stringBuffer.append("}");
            String requestBody = stringBuffer.toString();
            String signature = Base64Utils.decode64("QmluZ29pbnNpZ2h0") + ":" + accessKeyId + ":" + projectId + ":" + HmacSHA1Signer.getSignString(requestBody, secertKey);
            httpPost.setHeader("Authorization", signature);
            RequestConfig config = RequestConfig.custom().setSocketTimeout(220000).setConnectTimeout(220000).build(); // 握手超时、连接超时配置
            httpPost.setConfig(config);
            httpPost.setEntity(new StringEntity(requestBody, "UTF-8"));
            CloseableHttpClient client = HttpClients.createDefault();
            CloseableHttpResponse response = client.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            String resultJSON = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            return resultJSON;
        } catch (Exception e) {
            return e.toString();
        }

    }

    @Override
    public void run() {
        try {
            PmisApiLogServiceImpl bean = SpringUtils.getBean(PmisApiLogServiceImpl.class);
            bean.insertPmisApiLog(pmisApiLog);
        } catch (Exception ex) {
            logger.error("Error writing log message: " + ex.getMessage());
        }
    }

    private PmisApiLog pmisApiLog;

    public DataApiInfosController(PmisApiLog pmisApiLog) {
        this.pmisApiLog = pmisApiLog;
    }
}
