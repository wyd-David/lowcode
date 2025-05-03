package com.yabushan.form.controller;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yabushan.ai.domain.AiResultVo;
import com.yabushan.ai.service.WenZiShibieService;
import com.yabushan.ai.utils.AipOcrUtils;
import com.yabushan.common.config.RuoYiConfig;
import com.yabushan.common.constant.Constants;
import com.yabushan.common.core.domain.entity.SysRole;
import com.yabushan.common.core.redis.RedisCache;
import com.yabushan.common.utils.DateUtils;
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.common.utils.file.FileUploadUtils;
import com.yabushan.common.utils.file.FileUtils;
import com.yabushan.common.utils.spring.SpringUtils;
import com.yabushan.common.utils.uuid.IdUtils;
import com.yabushan.form.domain.LowFormFiledInfo;
import com.yabushan.form.domain.LowFormInfo;
import com.yabushan.form.domain.OutExcelTitle;
import com.yabushan.form.mapper.SqlExecuteMapper;
import com.yabushan.form.service.ILowFormFiledInfoService;
import com.yabushan.form.service.ILowFormInfoService;
import com.yabushan.form.service.impl.LowFormInfoServiceImpl;
import com.yabushan.form.utils.Base64Utils;
import com.yabushan.form.utils.ExportExcel;
import com.yabushan.form.utils.HmacSHA1Signer;
import com.yabushan.form.vo.AutoData;
import com.yabushan.form.vo.ListTitleVo;
import com.yabushan.framework.config.ServerConfig;
import com.yabushan.framework.mybatis.ExecuteSqlException;
import com.yabushan.framework.web.service.TokenService;
import com.yabushan.system.domain.DataApiInfos;
import com.yabushan.system.service.IDataApiInfosService;
import net.coobird.thumbnailator.Thumbnails;
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
import com.yabushan.poi.utils.EasypoiUtil;
import com.yabushan.poi.utils.ExcelUtils;
import com.yabushan.common.annotation.RepeatSubmit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.yabushan.form.domain.LowBusinessList;
import com.yabushan.form.service.ILowBusinessListService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yabushan.common.core.page.TableDataInfo;

import static com.yabushan.common.utils.file.MultipartFileToFile.multipartFileToFile;

/**
 * 业务列Controller
 *
 * @author yabushan
 * @date 2022-10-15
 */
@RestController
@RequestMapping("/list/list")
@Api(tags = {"业务列"})
public class LowBusinessListController extends BaseController {
    @Autowired
    private ILowBusinessListService lowBusinessListService;
    @Autowired
    private TokenService tokenService;

    @Autowired
    private ILowFormInfoService lowFormInfoService;
    @Autowired
    private ILowFormFiledInfoService lowFormFiledInfoService;
    @Autowired
    private SqlExecuteMapper sqlExecuteMapper;


    @Autowired
    private com.yabushan.form.utils.parseExcel parseExcel;
    @Autowired
    private ExportExcel exportExcel;


    @Autowired
    private IDataApiInfosService dataApiInfosService;

    @Autowired
    private WenZiShibieService wenZiShibieService;


    @Value("${daas.url}")
    private String url;
    @Value("${daas.accessKeyId}")
    private String accessKeyId;
    @Value("${daas.secertKey}")
    private String secertKey;
    @Value("${daas.projectId}")
    private String projectId;

    @Value("${BAIDU_AI.WZSB.FILE_SIZE_MB}")
    private Long fileSize;

    @Autowired
    private ServerConfig serverConfig;

/**
 * 查询业务列列表
 */
@ApiOperation("查询业务列列表")
//@Log(title = "查询业务列列表", businessType = BusinessType.OTHER)
@PreAuthorize("@ss.hasPermi('list:list:list')")
@GetMapping("/list")
        public TableDataInfo list(LowBusinessList lowBusinessList) {
        startPage();
        List<LowBusinessList> list = lowBusinessListService.selectLowBusinessListListPage(lowBusinessList);
        return getDataTable(list);
    }

    /**
     * 导出业务列列表
     */
    @ApiOperation("导出业务列列表")
    @PreAuthorize("@ss.hasPermi('list:list:export')")
    @Log(title = "业务列", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public AjaxResult export(LowBusinessList lowBusinessList, HttpServletResponse response) throws IOException {

        List<LowBusinessList> list = lowBusinessListService.selectLowBusinessListList(lowBusinessList);
        AjaxResult ajaxResult = ExcelUtils.exportExcel(list, "业务列", "业务列", LowBusinessList. class,
        "业务列.xlsx", response);
        return ajaxResult;
    }

    /**
     * 获取业务列详细信息
     */
    @ApiOperation("获取业务列详细信息")
    //@PreAuthorize("@ss.hasPermi('list:list:query')")
    @GetMapping(value = "/{listId}")
    public AjaxResult getInfo(@PathVariable("listId") String listId) {
        return AjaxResult.success(lowBusinessListService.selectLowBusinessListById(listId));
    }

    /**
     * 新增业务列
     */
    @ApiOperation("新增业务列")
   // @PreAuthorize("@ss.hasPermi('list:list:add')")
    @Log(title = "业务列", businessType = BusinessType.INSERT)
    @PostMapping
    @RepeatSubmit
    public AjaxResult add(@RequestBody LowBusinessList lowBusinessList) {
        return toAjax(lowBusinessListService.insertLowBusinessList(lowBusinessList));
    }

    /**
     * 修改业务列
     */
    @ApiOperation("修改业务列")
  //  @PreAuthorize("@ss.hasPermi('list:list:edit')")
    @Log(title = "业务列", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @RepeatSubmit
    public AjaxResult edit(@RequestBody LowBusinessList lowBusinessList) {
        return toAjax(lowBusinessListService.updateLowBusinessList(lowBusinessList));
    }

    /**
     * 删除业务列
     */
    @ApiOperation("删除业务列")
  //  @PreAuthorize("@ss.hasPermi('list:list:remove')")
    @Log(title = "业务列", businessType = BusinessType.DELETE)
    @GetMapping("/remove")
    @RepeatSubmit
    public AjaxResult remove(@RequestParam("listId") String[] listId) {
        return toAjax(lowBusinessListService.deleteLowBusinessListByIds(listId));
    }

    /**
    * 数据导入数据库的下载模板
    * @return
    * @throws Exception
    */
    @ApiOperation("数据导入数据库的下载模板")
    @GetMapping("/importTemplate")
    public AjaxResult importTemplate(HttpServletResponse response, String formId) throws IOException {

        //根据formId获取字段中文名称
        LowFormFiledInfo  lowFormFiledInfo = new LowFormFiledInfo();
        lowFormFiledInfo.setFormUuid(formId);
        List<LowFormFiledInfo> formFiledInfos = lowFormFiledInfoService.selectLowFormFiledInfoList(lowFormFiledInfo);
        StringBuffer buf = new StringBuffer();
        for(int i =0;i<formFiledInfos.size();i++){
            buf.append(formFiledInfos.get(i).getFiledLabel()).append(",");
        }
        String title = "正式数据从第三行开始，将第二行的字段1,2,3,4,5,6替换成数据模板字段: "+buf.toString();
        List<OutExcelTitle> list = new ArrayList<>();
        AjaxResult ajaxResult = ExcelUtils.exportExcel(list, title, "业务数据模板", OutExcelTitle. class,
        "业务数据.xlsx", response);
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
    @Log(title = "业务列", businessType = BusinessType.IMPORT)
   // @PreAuthorize("@ss.hasPermi('list:list:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport, String formId, String listId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        long size = file.getSize();
        long limitSize = 1024*1024*fileSize;
        /*if(size>limitSize){
            throw  new Exception("文件大小超出限制"+fileSize+"MB");
        }
       */

        InputStream inputStream = file.getInputStream();
        //根据formId获取字段中文名称
        LowFormFiledInfo  lowFormFiledInfo = new LowFormFiledInfo();
        lowFormFiledInfo.setFormUuid(formId);
        List<LowFormFiledInfo> formFiledInfos = lowFormFiledInfoService.selectLowFormFiledInfoList(lowFormFiledInfo);
        LowFormInfo lowFormInfo = lowFormInfoService.selectLowFormInfoById(formId);

        //获取名字
        String name = file.getOriginalFilename();
        //如果不是xls、xlsx  结尾的则为图片识别的数据
        String extString = name.substring(name.lastIndexOf(".")+1);
        List<Map<String, String>> maps =new ArrayList<>();
        if("xls".equals(extString) || "xlsx".equals(extString)){
          maps = parseExcel.printExcel(formFiledInfos.size(), inputStream, name);
        }else if(name.contains("图片压缩")){
            RedisCache redisCache = SpringUtils.getBean(RedisCache.class);

            String STR = LowFormInfoServiceImpl.getOrderNumber("YABUSHAN_IMG");
           /* if(STR.endsWith("5")){
                throw new Exception("体验账号每天只能压缩5张图片！请联系管理员微信：yabushan，进行开通专属账号");
            }*/
            //图片压缩
            File fileio = multipartFileToFile(file);
            String basePath = RuoYiConfig.getUploadPath()+ "/imgZip/"+IdUtils.simpleUUID();
            String absoluteFile = AipOcrUtils.getAbsoluteFile(basePath, name);
            File yasuofileio = new File(absoluteFile);
            Thumbnails.of(fileio)
                    .scale(0.8f) //图片大小（长宽）压缩比例 从0-1，1表示原图
                    .outputQuality(0.8f) //图片质量压缩比例 从0-1，越接近1质量越好
                    .toFile(yasuofileio);

            int dirLastIndex = RuoYiConfig.getProfile().length() + 1;
            String currentDir = StringUtils.substring(basePath, dirLastIndex);
            String pathFileName = Constants.RESOURCE_PREFIX + "/" + currentDir + "/" + name;

            String url = serverConfig.getUrl() + pathFileName;

            HashMap<String, String> objectObjectHashMap = new HashMap<>();
            objectObjectHashMap.put("文件名称",name);
            objectObjectHashMap.put("文件下载地址",url);
            maps.add(objectObjectHashMap);


        }else{
            String OCR = LowFormInfoServiceImpl.getOrderNumber("YABUSHAN_OCR");
            if(OCR.endsWith("10")){
                throw new Exception("体验账号每天只能识别10次！请联系管理员微信：yabushan，进行开通专属账号");
            }
            // 保存的文件路径
            String basePath = RuoYiConfig.getUploadPath()+ "/ai/"+IdUtils.simpleUUID();
            String absoluteFile = AipOcrUtils.getAbsoluteFile(basePath, name);
            OutputStream outputStream = new FileOutputStream(absoluteFile);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            inputStream.close();
            outputStream.close();
            //获取到数据
            List<AiResultVo> message = wenZiShibieService.getMessage(absoluteFile, basePath);
            StringBuffer buf = new StringBuffer();
            buf.append("notes");
            //封装需要解析的参数
            for(int i=0;i<formFiledInfos.size();i++){
                String filedLabel = formFiledInfos.get(i).getFiledLabel();
                buf.append(",").append(filedLabel);
            }
            String param = buf.toString();
            for(int jj=0;jj<message.size();jj++){
                Map finallyData = AipOcrUtils.getFinallyData(param, message.get(jj));
                maps.add(finallyData);
            }
            //删除文件
            AipOcrUtils.deleteDirectory(basePath);

        }


        //判断是不是走数据中台
        LowBusinessList lowBusinessList = lowBusinessListService.selectLowBusinessListById(listId);
        if(StringUtils.isNotEmpty(lowBusinessList.getDataApi())){
            //走数据中台API的UPT接口
            String[] split = lowBusinessList.getDataApi().split(",");
            if(split.length!=4){
                return AjaxResult.error("请核实API是否配置4个");
            }
            String API = split[1];//新增接口
            Integer success =0;//成功数
            Integer fail = 0;//失败数
            try{
                for(int i=0;i<maps.size();i++){
                    Map<String, String> stringStringMap = maps.get(i);
                    StringBuffer results =new StringBuffer();
                    results.append("{");
                    //TODO
                    //遍历MAP对象组装json串
                    for (Object obj : stringStringMap.keySet()){
                        for(int j=0;j<formFiledInfos.size();j++){
                            if(formFiledInfos.get(j).getFiledLabel().equals(obj)){
                                results.append("'").append(formFiledInfos.get(j).getEntityName()).append("'").append(":");
                                break;
                            }
                        }
                        results.append("'").append(stringStringMap.get(obj)).append("'").append(",");
                        //System.out.println("key为："+obj+"值为："+stringStringMap.get(obj));
                    }
                    results.append("}");
                    String str = results.toString();
                    str= str.replace(",}","}");
                    JSONObject row = lowFormInfoService.getData(API,str, "row");
                    String resultCode = row.get("resultCode").toString();
                    if("200".equals(resultCode)){
                        success++;
                    }else{
                        fail++;
                    }
                }
            }catch ( Exception e){
                logger.error("插入失败！",e.getMessage());
            }
            return AjaxResult.success("导入成功:"+success+"条数据！导入失败："+fail+"条！");
        }

        //走业务中台
        StringBuffer sqlbuffer = new StringBuffer();//总sql
        StringBuffer fieldBuf = new StringBuffer();//字段sql
        StringBuffer valueBuf = new StringBuffer();//值sql
        Integer inNumber = 0;//导入成功的数据
        Integer errorNumber =0;//导入失败的数据
        java.lang.String sql="insert into "+lowFormInfo.getFormEntityName() +"(";
        //封装导入数据
        if(maps.size()>0){
            for(int i=0;i<maps.size();i++){
                sqlbuffer =new StringBuffer();
                fieldBuf = new StringBuffer();
                valueBuf = new StringBuffer();
                sqlbuffer.append(sql);
                valueBuf.append("values(");

                for(int j =0;j<formFiledInfos.size();j++){
                    //封装sql
                    fieldBuf.append(formFiledInfos.get(j).getEntityName()).append(",");
                    valueBuf.append("'").append(maps.get(i).get(formFiledInfos.get(j).getFiledLabel())).append("',");
                }

                //封装处理人
                fieldBuf.append(" CREATED_BY ").append(",");
                valueBuf.append("'").append(SecurityUtils.getUsername()).append("',");

                //封装序号
                String msg = LowFormInfoServiceImpl.getOrderNumber("XH_BATCH_");
                fieldBuf.append("ORDER_NUMBER, ");
                valueBuf.append("'").append(msg).append("',");

                //封装日期
                String s = DateUtils.dateTimeNow("yyyy-MM-dd HH:mm:ss");
                fieldBuf.append(" CREATED_TIME ").append(" )");
                valueBuf.append("'").append(s).append("')");



                String sqlStr =sqlbuffer.toString() +fieldBuf.toString()+valueBuf.toString();
                try{
                    sqlExecuteMapper.execute(sqlStr);
                    inNumber++;
                }catch (ExecuteSqlException e ){
                    errorNumber++;
                    logger.error("导入数据出错了！"+e.toString());
                }
            }
        }

        return AjaxResult.success("导入成功:"+inNumber+"条数据！导入失败："+errorNumber+"条！");
    }

    /**
    * 根据传来的id集合修改禁用启用状态
    */
    @ApiOperation("根据传来的id集合修改禁用启用状态")
  //  @PreAuthorize("@ss.hasPermi('list:list:updateByIds')")
    @GetMapping("/updateByIds")
    public AjaxResult updateByIds(String ids, String disableEnableState) {
        Boolean isByIds = lowBusinessListService.updateByIds(ids, disableEnableState);
        if (isByIds) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }


    /**
     * 获取表数据
     * @param listId 列表ID
     * @param param  参数
     * @return
     */
    @PostMapping("selectAutoTableData")
    public AjaxResult selectAutoTableData(String listId,String param,String status48624,Integer pageNum,Integer pageSize){
        //根据listId获取到表单信息
        LowBusinessList lowBusinessList = lowBusinessListService.selectLowBusinessListById(listId);
        if(pageNum==null ){pageNum=1;}
        if(pageSize==null){pageSize=10;}
        pageNum=(pageNum-1)*pageSize;

        //判断API字段中是否为空，如果不为空。则认为是要走数据中台
        if(!StringUtils.isEmpty(lowBusinessList.getDataApi())){
            String[] dataApi = lowBusinessList.getDataApi().split(",");
            if(dataApi.length!=4 && dataApi.length!=1){
                return  AjaxResult.error("请按照格式QUERY,ADD,UPDATE,DELETE 用英文逗号隔开配置查询、新增、更新的四个API接口");
            }


            lowBusinessList.setDataApi(dataApi[0]);//默认第一个为查询API
            //  pageSize=10000;
            //走数据API获取数据
            DataApiInfos dataApiInfos = new DataApiInfos();
            dataApiInfos.setApiCode(lowBusinessList.getDataApi());
            DataApiInfos dataApiInfos1 = dataApiInfosService.selectDataApiInfosCode(lowBusinessList.getDataApi());
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

            /**
             * //报表，走平台，固定入参参数
             */
            if(dataApi.length == 1){
                String sql = dataApiInfos1.getApiDefination();
                if(null!= param && StringUtils.isNotEmpty(param)){
                    sql=sql.replace("#QUERY_FILED#", param);
                }else{
                    sql=sql.replace("=#QUERY_FILED#", " is not null ");
                    sql=sql.replace("= #QUERY_FILED#", " is not null ");
                }
                if(sql.contains("@Env(loginId)")){
                   sql= sql.replace("@Env(loginId)"," '"+SecurityUtils.getUsername()+"' ");
                }
                List<Map<String, Object>> mapstotal  = sqlExecuteMapper.selectPublicItemList(sql);

                sql= sql +" limit "+pageNum +", "+ pageSize;
                List<Map<String, Object>> maps = sqlExecuteMapper.selectPublicItemList(sql);
                AutoData autoData = new AutoData();
                autoData.setTotal(mapstotal.size());
                autoData.setRows(maps);
                return AjaxResult.success(autoData);
            }else if(dataApi.length==4){ //走数据中台

                if(null ==param || "".equals(param)){
                    param="{";
                }else{

                    //如果是报表属性的话，需要解析列表头，获取到字段
                    String jsonTitle = lowBusinessList.getListMemo();
                    //第三种方式
                    JSONArray listObjectThir = JSONArray.parseArray(jsonTitle);
                    StringBuffer buf = new StringBuffer();
                    buf.append("{");
                    // System.out.println("利用JSONArray中的parseArray方法来解析json数组字符串");
                    for(Object mapList : listObjectThir){
                        for (Object entry : ((Map)mapList).entrySet()){
                            if(((Map.Entry)entry).getKey().equals("prop")){
                                buf.append("'").append(((Map.Entry)entry).getValue()).append("'").append(":").append("'").append(param).append("'").append(",");
                                //  System.out.println(((Map.Entry)entry).getKey()  + "  " +((Map.Entry)entry).getValue());
                            }
                        }
                    }
                    //添加分页参数
                    buf.append("'").append("pageSize").append("'").append(":").append(pageSize);
                    // buf.append("}");
                    param=buf.toString();
                }

                if(param.equals("{")){
                    param+= "'pageSize':'"+pageSize+"'";
                    param+= ",'pageNum':'"+pageNum+"'";// pageNum,Integer pageSize
                    if(!SecurityUtils.getUsername().equals("admin")){//不是管理员，则加上当前创建人
                        if(validateHasTuanduiDataPermission()){
                            //是团队数据管理员
                            param+= ",'DATA_MANAGER_DEPT_ID':'"+SecurityUtils.getLoginUser().getUser().getDeptId()+"'";
                        }
                        param+= ",'CREATED_BY':'"+SecurityUtils.getUsername()+"'";
                    }


                }


                param+="}";

                try{
                    JSONObject jsonObject = JSONObject.parseObject(param);
                    jsonObject.replace("CREATED_BY",SecurityUtils.getUsername());//加上创建人条件
                    results=  jsonObject.toString();
                }catch (Exception e){
                    return  AjaxResult.error(results +e.toString());
                }
                AutoData autoData = new AutoData();
                //获取数据
                JSONObject result= getData(lowBusinessList.getDataApi(),results,"row");

                //获取数量
                JSONObject numberCount= getData(lowBusinessList.getDataApi()+"_COUNT",results,"row");

                JSONArray rows = numberCount.getJSONArray("rows");
                Map resultMap = (Map) rows.get(0);
                Integer number =Integer.valueOf(resultMap.get("NUMBERS").toString()) ;
                autoData.setTotal(number);


                JSONArray rows1 = result.getJSONArray("rows");
                List<Map<String, Object>> maps =new ArrayList<>();
                for(int i=0;i<rows1.size();i++){
                    Map<String, Object> data = (Map<String, Object>) rows1.get(i);
                    maps.add(data);
                }

                autoData.setRows(maps);
                return AjaxResult.success(autoData);
            }




        }

        //获取表信息
        LowFormInfo lowFormInfo = lowFormInfoService.selectLowFormInfoById(lowBusinessList.getFormUuid());
        //根据表单ID获取到字段
        LowFormFiledInfo lowFormFiledInfo = new LowFormFiledInfo();
        lowFormFiledInfo.setFormUuid(lowBusinessList.getFormUuid());
        List<LowFormFiledInfo> formFiledInfos = lowFormFiledInfoService.selectLowFormFiledInfoList(lowFormFiledInfo);
        StringBuffer buffer = new StringBuffer();//参数字段
        StringBuffer whereBuffer = new StringBuffer();//条件
        boolean empty=true;//默认为空
        if(!StringUtils.isEmpty(param)){
            empty=false;
        }
        boolean tuandui_data_mamager = validateHasTuanduiDataPermission();
        if(tuandui_data_mamager){
            //如果有团队管理员角色，则获取团队下所有人员的
            whereBuffer.append(" where CREATED_BY in( ")
                 .append("SELECT   CONVERT (USER_NAME USING utf8) COLLATE utf8_unicode_ci  from SYS_USER U WHERE  U.DEPT_ID=").append(SecurityUtils.getLoginUser().getUser().getDeptId())
             .append(")");
            //查看没有被删除的数据
            whereBuffer.append( " and del_flag = '0' ");

        }else if(!lowBusinessList.getCreatedBy().equals(SecurityUtils.getUsername())){
            //如果登录人不是列表的创建人，则只能查看自己创建的数据
            whereBuffer.append(" where CREATED_BY = '").append(SecurityUtils.getUsername()).append("' ");
            //查看没有被删除的数据
            whereBuffer.append( " and del_flag = '0' ");
        }else{
            //否则查全部
            whereBuffer.append(" where del_flag = '0' ");

        }


        buffer.append("select id, ");
        boolean statusIsNull =true;
        if(!StringUtils.isEmpty(status48624)){//状态传值不为空
            statusIsNull= false;
            whereBuffer.append(" and status48624 ='").append(status48624).append("' ");
           /* if(!empty){//参数不为空
                whereBuffer.append(" and  ( ");
            }*/

        }else{//状态传值为空


          /*  if(!empty){//参数不为空
                whereBuffer.append(" where  ");
            }*/

        }

        if(!empty){//参数不为空
            whereBuffer.append(" and  ( ");
        }

        for (LowFormFiledInfo lowformFiled: formFiledInfos
             ) {
            buffer.append(lowformFiled.getEntityName()).append(",");
            if(!empty){

                if(lowformFiled.getEntityName().equals("select48624")){

                }else{
                    whereBuffer.append(lowformFiled.getEntityName()).append(" like '%").append(param).append("%' ").append(" or ");
                }
            }

        }
        buffer.append(" CREATED_BY ,DATE_FORMAT(CREATED_TIME,'%Y-%m-%d %H:%i:%s') AS CREATED_TIME,UPDATED_BY ,DATE_FORMAT(UPDATED_TIME,'%Y-%m-%d %H:%i:%s') AS UPDATED_TIME ,status48624,order_number").append(" from ").append(lowFormInfo.getFormEntityName()).append("  ");
        String  countStr = "select count(1) from "+lowFormInfo.getFormEntityName()+" ";

        if(!empty){
          //  whereBuffer.append("  CREATED_BY like '%").append(param).append("%'");
            whereBuffer.append("  CREATED_TIME like '%").append(param).append("%'");
           // if(!statusIsNull){
                whereBuffer.append(")");
           // }

        }
        countStr +=whereBuffer.toString();
        //查询总数
        Object execute = sqlExecuteMapper.execute(countStr);


        whereBuffer.append(" order by CREATED_TIME desc limit ").append(pageNum).append(",").append(pageSize);
        //拼接sql字段获取数据

        String sqlStr = buffer.toString()+whereBuffer.toString();
        List<Map<String, Object>> maps = sqlExecuteMapper.selectPublicItemList(sqlStr);
        AutoData autoData = new AutoData();
        autoData.setTotal(execute);
        autoData.setRows(maps);
        return AjaxResult.success(autoData);
    }

    private static boolean validateHasTuanduiDataPermission() {
        List<SysRole> roles = SecurityUtils.getLoginUser().getUser().getRoles();
        if(roles.size()>0){
            for(int i =0 ;i<roles.size();i++){
                if(roles.get(i).getRoleKey().equals("TUANDUI_DATA_MAMAGER")){
                    return true;
                }
            }
        }
        return false;
    }


    //删除数据
    @Log(title = "动态列表删除数据", businessType = BusinessType.DELETE)
    @GetMapping("/autoRemove")
    @RepeatSubmit
    public AjaxResult autoRemove(String formId, Integer id,String flag,String listId) {

        //判断是否走API
        LowBusinessList lowBusinessList = lowBusinessListService.selectLowBusinessListById(listId);
        if(StringUtils.isNotEmpty(lowBusinessList.getDataApi())){
            //走API的UPT接口
            String[] split = lowBusinessList.getDataApi().split(",");
            if(split.length!=4){
                return AjaxResult.error("请核实API是否配置4个");
            }
            String API = split[3];//删除接口

            String param = "{'id':'"+id+"'"
                    +",'key':'"+id+"'"
                    +",'ID':'"+id+"'"
                    +",'formId':'"+formId+"'"
                    +",'flag':'"+flag+"'"
                    +",'listId':'"+listId+"'}";
            JSONObject row = lowFormInfoService.getData(API,param, "row");
            String resultCode = row.get("resultCode").toString();
            if("200".equals(resultCode)){
                return AjaxResult.success("操作成功!");
            }else{
                return AjaxResult.success("操作失败！"+row.get("exception").toString());
            }


        }

        LowFormInfo lowFormInfo = lowFormInfoService.selectLowFormInfoById(formId);
       /* if(!lowFormInfo.getCreatedBy().equals(SecurityUtils.getUsername())){
            return AjaxResult.error("只有业务管理人员才能禁用/删除/启用，请联系管理员！");
        }*/
        //拼接数据
        String sql =null;
        if("delete".equals(flag)){
        //    sql=  "delete from "+lowFormInfo.getFormEntityName() +" where id = "+ id;
            sql=  "update  "+lowFormInfo.getFormEntityName() +" set del_flag='1' where id = "+ id;
        }else if("use".equals(flag)){
            sql=  "update  "+lowFormInfo.getFormEntityName() +" set status48624='启用' where id = "+ id;
        }else if("disable".equals(flag)){
            sql=  "update  "+lowFormInfo.getFormEntityName() +" set status48624='禁用' where id = "+ id;
        }
        sqlExecuteMapper.execute(sql);
        return AjaxResult.success("删除成功！");
    }





    @ApiOperation("导出动态列表")
    @Log(title = "导出动态列表", businessType = BusinessType.OTHER)
   // @PreAuthorize("@ss.hasPermi('list:list:list')")
    @PostMapping("/ExportList")
    public AjaxResult ExportList(HttpServletResponse response,String listId,String param,String status48624,Integer pageNum,Integer pageSize){
        //根据listId获取到表单信息
        LowBusinessList lowBusinessList = lowBusinessListService.selectLowBusinessListById(listId);
        //获取表信息
        LowFormInfo lowFormInfo = lowFormInfoService.selectLowFormInfoById(lowBusinessList.getFormUuid());
        //根据表单ID获取到字段
        LowFormFiledInfo lowFormFiledInfo = new LowFormFiledInfo();
        lowFormFiledInfo.setFormUuid(lowBusinessList.getFormUuid());
        List<LowFormFiledInfo> formFiledInfos = lowFormFiledInfoService.selectLowFormFiledInfoList(lowFormFiledInfo);

        //判断是否走数据中台
        if(StringUtils.isNotEmpty(lowBusinessList.getDataApi())){
            //不为空，走数据中台
            String[] split = lowBusinessList.getDataApi().split(",");
            if(split.length!=4){
                return AjaxResult.success("请核对API编码是否满足4个API要求格式：QUERY,ADD,UPDATE,DELETE");
            }

            String API = split[0];
            String params = "{'pageNum':'"+pageNum+"'"
                    +",'pageSize':'"+pageSize+"'"
                    +",'param':'"+param+"'"
                    +",'del_flag':'"+1+"'"
                    +",'status48624':'"+status48624+"'"
                    +",'listId':'"+listId+"'}";
            JSONObject row = lowFormInfoService.getData(API,params, "row");
            String resultCode = row.get("resultCode").toString();
            if("200".equals(resultCode)){
                JSONArray rows = row.getJSONArray("rows");
                List< Map<String, Object>> result = new ArrayList<>();

                for(int i=0;i<rows.size();i++){
                  Map<String, Object> data = (Map<String, Object>) rows.get(i);
                    result.add(data);
                }
                AjaxResult ajaxResult = exportExcel.exportDeliveryExpense(response, result, formFiledInfos, lowFormInfo.getFormName()+".xlsx");
                return ajaxResult;
            }else{
                return AjaxResult.success("操作失败！"+row.get("exception").toString());
            }



        }


        StringBuffer buffer = new StringBuffer();//参数字段
        StringBuffer whereBuffer = new StringBuffer();//条件
        boolean empty=true;//默认为空
        if(!StringUtils.isEmpty(param)){
            empty=false;
        }

        if(pageNum==null ){pageNum=1;}
        if(pageSize==null){pageSize=10; }
        pageNum=(pageNum-1)*pageSize;
        buffer.append("select id, ");
        boolean statusIsNull =true;
        if(!StringUtils.isEmpty(status48624)){//状态传值不为空
            statusIsNull= false;
            whereBuffer.append(" where status48624 ='").append(status48624).append("' ");
            if(!empty){//参数不为空
                whereBuffer.append(" and  ( ");
            }
        }else{//状态传值为空
            if(!empty){//参数不为空
                whereBuffer.append(" where  ");
            }
        }
        for (LowFormFiledInfo lowformFiled: formFiledInfos
        ) {
            buffer.append(lowformFiled.getEntityName()).append(",");
            if(!empty){
                if(lowformFiled.getEntityName().equals("select48624")){
                }else{
                    whereBuffer.append(lowformFiled.getEntityName()).append(" like '%").append(param).append("%' ").append(" or ");
                }
            }
        }
        buffer.append(" CREATED_BY ,DATE_FORMAT(CREATED_TIME,'%Y-%m-%d %H:%i:%s') AS CREATED_TIME,UPDATED_BY ,DATE_FORMAT(UPDATED_TIME,'%Y-%m-%d %H:%i:%s') AS UPDATED_TIME ,status48624").append(" from ").append(lowFormInfo.getFormEntityName()).append("  ");
       // String  countStr = "select count(1) from "+lowFormInfo.getFormEntityName()+" ";
        if(!empty){//如果不为空
            whereBuffer.append("  CREATED_BY like '%").append(param).append("%'");
            whereBuffer.append(" or CREATED_TIME like '%").append(param).append("%'");
            if(!statusIsNull){
                whereBuffer.append(")");
            }
           // countStr +=whereBuffer.toString();
        }
        String user =SecurityUtils.getUsername();
        boolean tuandui_data_mamager = validateHasTuanduiDataPermission();

        if(!"admin".equals(user)){//不管是否传入参数，如果不是管理员默认只导出本人导入的数据
           if(whereBuffer.toString().equals(" where ")){
               if(tuandui_data_mamager){//数据管理员权限，导出组织下的
                   whereBuffer.append(" and CREATED_BY in( '")
                           .append("SELECT USER_NAME+''  from SYS_USER U WHERE U.DEPT_ID=").append(SecurityUtils.getLoginUser().getUser().getDeptId())
                           .append(")");
                   //查看没有被删除的数据
                  // whereBuffer.append( " and del_flag = '0' ");
               }else{//没有权限，导出个人的
                   whereBuffer.append("  and CREATED_BY= '").append(user).append("'");
                 //  whereBuffer.append( " and del_flag = '0' ");
               }
           }else{
               if(tuandui_data_mamager) {//数据管理员权限，导出组织下的
                   if(empty){
                       whereBuffer.append(" where ");
                   }else{
                       whereBuffer.append(" and ");
                   }
                   whereBuffer.append("  CREATED_BY in( ")
                           .append("SELECT USER_NAME+''  from SYS_USER U WHERE U.DEPT_ID=").append(SecurityUtils.getLoginUser().getUser().getDeptId())
                           .append(")");
                   //查看没有被删除的数据
                  // whereBuffer.append( " and del_flag = '0' ");
               }else{
                   if(empty){
                       whereBuffer.append(" where ");
                   }else{
                       whereBuffer.append(" and ");
                   }
                   whereBuffer.append("   CREATED_BY= '").append(user).append("'");
                 //  whereBuffer.append( " and del_flag = '0' ");
               }

           }

        }
        if(whereBuffer.toString().contains("where")){
            whereBuffer.append( " and del_flag = '0' ");
        }else{
            whereBuffer.append( " where del_flag = '0' ");

        }

        whereBuffer.append(" order by CREATED_TIME desc ");
        //拼接sql字段获取数据
        String sqlStr = buffer.toString()+whereBuffer.toString();
        List<Map<String, Object>> maps = sqlExecuteMapper.selectPublicItemList(sqlStr);
        AjaxResult ajaxResult = exportExcel.exportDeliveryExpense(response, maps, formFiledInfos, lowFormInfo.getFormName()+".xlsx");
        return ajaxResult;
    }


    public JSONObject getData(String ApiCode , String param,String rowType ){
        String reslt = result(param, ApiCode,rowType);
        JSONObject jsonObject = JSON.parseObject(reslt);

        return  jsonObject;
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



    /**
     * 获取表数据
     * @param listId 列表ID
     * @param param  参数
     * @return
     */
    @PostMapping("selectChildAutoTableData")
    public AjaxResult selectChildAutoTableData(String listId,String param,String status48624,Integer pageNum,Integer pageSize) throws IOException {
        //根据listId获取到表单信息
        LowBusinessList lowBusinessList = lowBusinessListService.selectLowBusinessListById(listId);
        if(pageNum==null ){pageNum=1;}
        if(pageSize==null){pageSize=10;}
        pageNum=(pageNum-1)*pageSize;


        Map<String, String> stringStringMap = LowFormInfoController.stringToMap(param);

        String listMemoChild = lowBusinessList.getListMemoChild();
        List<ListTitleVo> list = JSON.parseArray(listMemoChild, ListTitleVo.class);
        param = null;
        for(int i=0;i<list.size();i++){
            if("1".equals(list.get(i).getPk())){
                //获取到需要查询的字段值
                param=stringStringMap.get(list.get(i).getProp());
                break;

            }

        }



        //获取表信息
        LowFormInfo lowFormInfo = lowFormInfoService.selectLowFormInfoById(status48624);
        //根据表单ID获取到字段
        LowFormFiledInfo lowFormFiledInfo = new LowFormFiledInfo();
        lowFormFiledInfo.setFormUuid(status48624);
        List<LowFormFiledInfo> formFiledInfos = lowFormFiledInfoService.selectLowFormFiledInfoList(lowFormFiledInfo);
        StringBuffer buffer = new StringBuffer();//参数字段
        StringBuffer whereBuffer = new StringBuffer();//条件
        boolean empty=true;//默认为空
        if(!StringUtils.isEmpty(param)){
            empty=false;
        }
        boolean tuandui_data_mamager = validateHasTuanduiDataPermission();
        if(tuandui_data_mamager){
            //如果有团队管理员角色，则获取团队下所有人员的
            whereBuffer.append(" where CREATED_BY in( ")
                    .append("SELECT   CONVERT (USER_NAME USING utf8) COLLATE utf8_unicode_ci  from SYS_USER U WHERE  U.DEPT_ID=").append(SecurityUtils.getLoginUser().getUser().getDeptId())
                    .append(")");
            //查看没有被删除的数据
            whereBuffer.append( " and del_flag = '0' ");

        }else if(!lowBusinessList.getCreatedBy().equals(SecurityUtils.getUsername())){
            //如果登录人不是列表的创建人，则只能查看自己创建的数据
            whereBuffer.append(" where CREATED_BY = '").append(SecurityUtils.getUsername()).append("' ");
            //查看没有被删除的数据
            whereBuffer.append( " and del_flag = '0' ");
        }else{
            //否则查全部
            whereBuffer.append(" where del_flag = '0' ");

        }


        buffer.append("select id, ");


        if(!empty){//参数不为空
            whereBuffer.append(" and  ( ");
        }

        for (LowFormFiledInfo lowformFiled: formFiledInfos
        ) {
            buffer.append(lowformFiled.getEntityName()).append(",");
            if(!empty){

                if(lowformFiled.getEntityName().equals("select48624")){

                }else{
                    whereBuffer.append(lowformFiled.getEntityName()).append(" = '").append(param).append("' ").append(" or ");
                }
            }

        }
        buffer.append(" CREATED_BY ,DATE_FORMAT(CREATED_TIME,'%Y-%m-%d %H:%i:%s') AS CREATED_TIME,UPDATED_BY ,DATE_FORMAT(UPDATED_TIME,'%Y-%m-%d %H:%i:%s') AS UPDATED_TIME ,status48624,order_number").append(" from ").append(lowFormInfo.getFormEntityName()).append("  ");
        String  countStr = "select count(1) from "+lowFormInfo.getFormEntityName()+" ";

        if(!empty){
              whereBuffer.append("  CREATED_BY = '").append(param).append("'");
            // if(!statusIsNull){
            whereBuffer.append(")");
            // }

        }
        countStr +=whereBuffer.toString();
        //查询总数
        Object execute = sqlExecuteMapper.execute(countStr);


        whereBuffer.append(" order by CREATED_TIME desc limit ").append(pageNum).append(",").append(pageSize);
        //拼接sql字段获取数据

        String sqlStr = buffer.toString()+whereBuffer.toString();
        List<Map<String, Object>> maps = sqlExecuteMapper.selectPublicItemList(sqlStr);
        AutoData autoData = new AutoData();
        autoData.setTotal(execute);
        autoData.setRows(maps);
        return AjaxResult.success(autoData);
    }
}
