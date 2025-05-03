package com.yabushan.form.service.impl;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.yabushan.activiti.service.ActivitiService;
import com.yabushan.common.core.domain.AjaxResult;
import com.yabushan.common.core.domain.entity.SysUser;
import com.yabushan.common.core.redis.RedisCache;
import com.yabushan.common.utils.DateUtils;
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.common.utils.http.HttpUtils;
import com.yabushan.common.utils.spring.SpringUtils;
import com.yabushan.form.domain.LowBusinessList;
import com.yabushan.form.domain.LowFormFiledInfo;
import com.yabushan.form.mapper.LowBusinessListMapper;
import com.yabushan.form.mapper.LowFormFiledInfoMapper;
import com.yabushan.form.mapper.SqlExecuteMapper;
import com.yabushan.form.utils.AESUtil;
import com.yabushan.form.utils.Base64Utils;
import com.yabushan.form.utils.HmacSHA1Signer;
import com.yabushan.form.vo.AutoParamVo;
import com.yabushan.form.vo.FileStr;
import com.yabushan.form.vo.ListTitleVo;
import com.yabushan.framework.web.domain.server.Sys;
import com.yabushan.system.domain.DataApiInfos;
import com.yabushan.system.service.IDataApiInfosService;
import com.yabushan.system.service.ISysUserService;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.common.exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.yabushan.form.mapper.LowFormInfoMapper;
import com.yabushan.form.domain.LowFormInfo;
import com.yabushan.form.service.ILowFormInfoService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 基础单信息Service业务层处理
 *
 * @author yabushan
 * @date 2022-10-10
 */
@Service
public class LowFormInfoServiceImpl implements ILowFormInfoService {
    private static final Logger log = LoggerFactory.getLogger(LowFormInfoServiceImpl.class);
    @Autowired
    private LowFormInfoMapper lowFormInfoMapper;

    @Autowired
    private LowFormFiledInfoMapper lowFormFiledInfoMapper;

    @Autowired
    private SqlExecuteMapper sqlExecuteMapper;

    @Autowired
    private IDataApiInfosService dataApiInfosService;

    @Autowired
    private ActivitiService activitiService;
    @Autowired
    private LowBusinessListMapper lowBusinessListMapper;

    @Autowired
    private ISysUserService userService;


    @Value("${daas.datasourceId}")
    private String datasourceId;


    @Value("${daas.url}")
    private String url;
    @Value("${daas.accessKeyId}")
    private String accessKeyId;
    @Value("${daas.secertKey}")
    private String secertKey;
    @Value("${daas.projectId}")
    private String projectId;


    //存放导入所有的成功失败数据
    private static List<LowFormInfo> lowFormInfoInfo = new ArrayList<>();

    /**
     * 清空返回导入数据的集合
     */
    @Override
    public void clearlowFormInfoInfo() {
        if (StringUtils.isNotNull(lowFormInfoInfo) && lowFormInfoInfo.size() > 0) {
            lowFormInfoInfo.clear();
        }
    }

    /**
     * 查询基础单信息
     *
     * @param formUuid 基础单信息ID
     * @return 基础单信息
     */
    @Override
    public LowFormInfo selectLowFormInfoById(String formUuid) {
        return lowFormInfoMapper.selectLowFormInfoById(formUuid);
    }

    /**
     * 查询基础单信息列表
     *
     * @param lowFormInfo 基础单信息
     * @return 基础单信息
     */
    @Override
    public List<LowFormInfo> selectLowFormInfoList(LowFormInfo lowFormInfo) {
        String username = SecurityUtils.getUsername();
        if(!"admin".equals(username)){
            lowFormInfo.setCreatedBy(SecurityUtils.getUsername());
        }
        List<LowFormInfo> lowFormInfos = lowFormInfoMapper.selectLowFormInfoList(lowFormInfo);
        for (LowFormInfo info: lowFormInfos) {
            String sql ="select count(1) from "+info.getFormEntityName();
            String number =sqlExecuteMapper.execute(sql).toString();
            info.setFormStatus(number);
        }
        return lowFormInfos;
    }

    /**
     * 新增基础单信息
     *
     * @param lowFormInfo 基础单信息
     * @return 结果
     */
    @Override
    @Transactional
    public int insertLowFormInfo(LowFormInfo lowFormInfo) {

        //保存字段
        //封装字段
        String formUuid = StringUtils.getUUID();
        lowFormInfo.setFormUuid(formUuid);
        List<LowFormFiledInfo> formFiledInfoList = new ArrayList<>();
        //  LowFormFiledInfo lowFormFiledInfo = null;
        createFiled(formFiledInfoList,lowFormInfo);

        List<String> filedName = new ArrayList<>();
        filedName.add("ID");
        filedName.add("status48624");
        filedName.add("CREATED_BY");
        filedName.add("UPDATED_BY");
        filedName.add("APPROE_STATUS");

        //创建表
        StringBuffer stringBuffer = new StringBuffer();
        String sql ="create table "+ lowFormInfo.getFormEntityName()+" (ID INT NOT NULL AUTO_INCREMENT ,status48624 VARCHAR(32) DEFAULT '启用',del_flag VARCHAR(1) DEFAULT '0',CREATED_BY VARCHAR(255), CREATED_TIME DATETIME DEFAULT CURRENT_TIMESTAMP ,UPDATED_BY VARCHAR(32),UPDATED_TIME DATETIME DEFAULT CURRENT_TIMESTAMP,ORDER_NUMBER VARCHAR(32),APPROE_STATUS VARCHAR(32),";
        stringBuffer.append(sql);
        //遍历字段创建表
        for(int i =0;i<formFiledInfoList.size();i++){
            String entityName = formFiledInfoList.get(i).getEntityName();
            if(StringUtils.isEmpty(entityName)){
                continue;
            }
            filedName.add(entityName);
            stringBuffer.append(formFiledInfoList.get(i).getEntityName()).append(" ").append(formFiledInfoList.get(i).getEntityType()).append(", ");
        }

        stringBuffer.append("PRIMARY KEY (ID))");
        String sqlParam = stringBuffer.toString();
        //创建表
        sqlExecuteMapper.execute(sqlParam);
        //插入字段
        lowFormFiledInfoMapper.bathInsertLowFormFiledInfo(formFiledInfoList);
        //插入表
        lowFormInfo.setCreatedBy(SecurityUtils.getUsername());
        lowFormInfo.setCreatedTime(new Date());
        //创建API
        String[] objects = filedName.toArray(new String[filedName.size()]);
        addApi(objects,lowFormInfo.getFormEntityName(),lowFormInfo.getFormName());
        //创建报表
        createJimuReport(formUuid,formUuid,lowFormInfo.getFormName());
        return lowFormInfoMapper.insertLowFormInfo(lowFormInfo);
    }

    /**
     * 封装字段信息
     * @param formFiledInfoList
     * @param lowFormInfo
     *
     */
    private static void createFiled(List<LowFormFiledInfo> formFiledInfoList, LowFormInfo lowFormInfo) {
        Map mapType = JSON.parseObject(lowFormInfo.getFormJson(),Map.class);
        String widgetList = mapType.get("widgetList").toString();
        List<Map<String, Object>> maps = JSON.parseObject(widgetList, new TypeReference<List<Map<String, Object>>>() {});
        for(int i=0;i<maps.size();i++){
            LowFormFiledInfo  lowFormFiledInfo = new LowFormFiledInfo();
            Map result = maps.get(i);
            String icon  = result.get("icon").toString();
            String type  = result.get("type").toString();
            String label=null;
            String name=null;
            String formItemFlag=null;
            if("grid".equals(type)){//如果是栅格里面的参数
                String cols = result.get("cols").toString();
                List<Map<String, Object>> mapscol = JSON.parseObject(cols, new TypeReference<List<Map<String, Object>>>() {});
                for(int iol=0;iol< mapscol.size();iol++){
                    LowFormInfo lowFormInfoCol = new LowFormInfo();
                    lowFormInfoCol.setFormJson(JSON.toJSONString(mapscol.get(iol)));
                    lowFormInfoCol.setFormUuid(lowFormInfo.getFormUuid());
                    createFiled(formFiledInfoList,  lowFormInfoCol);
                }

            }else{
                 formItemFlag  = result.get("formItemFlag").toString();
                //获取到字段名称
                String fileds = result.get("options").toString();
                Map filedMap = JSON.parseObject(fileds,Map.class);
                 label = filedMap.get("label").toString();//获取标签
                 name = filedMap.get("name").toString();//获取名字
                if(StringUtils.isEmpty(name) || StringUtils.isEmpty(label) ){
                    continue;
                }
            }

            lowFormFiledInfo.setFiledId(StringUtils.getUUID());
            lowFormFiledInfo.setFormUuid(lowFormInfo.getFormUuid());
            lowFormFiledInfo.setFiledName(name);
            lowFormFiledInfo.setFiledLabel(label);

            //根据字段选择合适的字段类型
            String types="VARCHAR(255)";
            if(icon.equals("picture-upload-field") || icon.equals("file-upload-field")){
                types="VARCHAR(2000)";
            }else if(icon.equals("rich-editor-field") || icon.equals("text-field")){
                types="text";
            }else if(icon.equals("textarea-field")){
                types="VARCHAR(2000)";
            }
            //设置字段类型，默认全部为字符串
            lowFormFiledInfo.setFiledType(types);
            lowFormFiledInfo.setEntityType(types);
            lowFormFiledInfo.setFiledIcon(icon);
            lowFormFiledInfo.setFormItemFlag(formItemFlag);

            lowFormFiledInfo.setEntityName(name);

            lowFormFiledInfo.setCreatedTime(new Date());
            lowFormFiledInfo.setCreatedBy(SecurityUtils.getUsername());
            formFiledInfoList.add(lowFormFiledInfo);
        }
    }

    /**
     * 批量新增基础单信息
     *
     * @param lowFormInfo 基础单信息
     * @return 结果
     */
    @Override
    public int bathInsertLowFormInfo(List<LowFormInfo> lowFormInfo) {
        return lowFormInfoMapper.bathInsertLowFormInfo(lowFormInfo);
    }


    /**
     * 修改基础单信息
     *
     * @param lowFormInfo 基础单信息
     * @return 结果
     */
    @Override
    public int updateLowFormInfo(LowFormInfo lowFormInfo) {
        //获取到字段信息
        List<LowFormFiledInfo> formFiledInfoList = new ArrayList<>();
        createFiled(formFiledInfoList,lowFormInfo);

        //获取旧的字段信息
        //根据formId获取所有字段
        LowFormFiledInfo lowFormFiledInfo = new LowFormFiledInfo();
        lowFormFiledInfo.setFormUuid(lowFormInfo.getFormUuid());
        List<LowFormFiledInfo> formFiledInfosOld = lowFormFiledInfoMapper.selectLowFormFiledInfoList(lowFormFiledInfo);

        //新旧字段循环对比，获取到新增的字段
        if(formFiledInfoList.size()>0){
            if(formFiledInfosOld.size()>0){//原先有字段，在原先基础上继续增加字段
                for(int i=0;i<formFiledInfoList.size();i++){
                    String entityName = formFiledInfoList.get(i).getEntityName();
                    if(StringUtils.isEmpty(entityName)){continue;}
                    if(validateAddFiled(entityName)){//排除内置字段
                        //alter table 表名 add 字段名 varchar(32) DEFAULT XXX comment ‘备注名’;
                        String validate = "SELECT COLUMN_NAME   FROM INFORMATION_SCHEMA.COLUMNS  WHERE TABLE_SCHEMA = 'lowcode_person'  AND TABLE_NAME = '"+lowFormInfo.getFormEntityName()+"' AND COLUMN_NAME='"+formFiledInfoList.get(i).getEntityName()+"'";
                        List<Map<String, Object>> maps = sqlExecuteMapper.selectPublicItemList(validate);
                        if(maps.size()>0){
                            continue;
                        }
                        String SQL = "alter table "+lowFormInfo.getFormEntityName()+" ADD "+ formFiledInfoList.get(i).getEntityName()+ " "+formFiledInfoList.get(i).getEntityType()+"";
                        try{
                            sqlExecuteMapper.execute(SQL);
                            lowFormFiledInfoMapper.insertLowFormFiledInfo(formFiledInfoList.get(i));
                        }catch (Exception e ){
                            log.error("新增字段失败",SQL);
                        }
                    }
                    /*for(int j=0;j<formFiledInfosOld.size();j++){
                        if(formFiledInfoList.get(i).getEntityName().equals(formFiledInfosOld.get(j).getEntityName())){
                            //如果字段存在旧的里边，就不做任何处理
                            continue;
                        }else{//新增字段


                        }
                    }*/
                }
            }else{
                //原先没有字段，本次的所有字段都增加
                for(int i=0;i<formFiledInfoList.size();i++){
                    if(validateAddFiled(formFiledInfoList.get(i).getEntityName())){//排除内置字段
                        //alter table 表名 add 字段名 varchar(32) DEFAULT XXX comment ‘备注名’;
                        String SQL = "alter table "+lowFormInfo.getFormEntityName()+" ADD "+ formFiledInfoList.get(i).getEntityName()+ " "+formFiledInfoList.get(i).getEntityType()+"";
                        try{
                            sqlExecuteMapper.execute(SQL);
                            lowFormFiledInfoMapper.insertLowFormFiledInfo(formFiledInfoList.get(i));
                        }catch (Exception e ){
                            log.error("新增字段失败",SQL);
                        }
                    }
                }
            }
        }

        //保存表单
        return lowFormInfoMapper.updateLowFormInfo(lowFormInfo);
    }


    public boolean validateAddFiled(String str){
        if(str.toUpperCase().equals("ID")
                || str.toUpperCase().equals("status48624") || str.toUpperCase().equals("CREATED_BY") || str.toUpperCase().equals("UPDATED_BY")
                || str.toUpperCase().equals("UPDATED_TIME") || str.toUpperCase().equals("CREATED_TIME")
                || str.toUpperCase().equals("DEL_FLAG")

        ){
            //修改表单时，增加的字段不能是如下字段
            //   filedName.add("ID");
            //        filedName.add("status48624");
            //        filedName.add("CREATED_BY");
            //        filedName.add("UPDATED_BY");
            //        filedName.add("APPROE_STATUS");
            return false;
        }else{
            return true;
        }
    }

    /**
     * 批量删除基础单信息
     *
     * @param formUuids 需要删除的基础单信息ID
     * @return 结果
     */
    @Override
    public int deleteLowFormInfoByIds(String[] formUuids) {
        return lowFormInfoMapper.deleteLowFormInfoByIds(formUuids);
    }

    /**
     * 删除基础单信息信息
     *
     * @param formUuid 基础单信息ID
     * @return 结果
     */
    @Override
    public int deleteLowFormInfoById(String formUuid) {
        return lowFormInfoMapper.deleteLowFormInfoById(formUuid);
    }

    /**
     * 根据传来的id集合修改禁用启用状态
     * @param ids
     * @param disableEnableState
     * @return
     */
    @Transactional
    @Override
    public Boolean updateByIds(String ids, String disableEnableState) {
        int count = 0;
        String idslist[] = ids.split("," , -1);
        if (StringUtils.isNotNull(idslist) && idslist.length >= 1) {
            for (int i = 0; i < idslist.length; i++) {
                // todo idslist[i] 返回的是一个字符串，如果实体id是其他类型，需要转换，如是int类型就修改成Integer.parseInt(idslist[i])
                LowFormInfo lowFormInfo=lowFormInfoMapper.selectLowFormInfoById(idslist[i]);
                lowFormInfo.setDisableEnableState(disableEnableState);
                int isappinfo = lowFormInfoMapper.updateLowFormInfo(lowFormInfo);
                if (isappinfo == 1) {
                    count++;
                }
            }
            if (count == idslist.length) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * 导入基础单信息Excel数据
     *
     * @param lowFormInfoList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public List<LowFormInfo>  importLowFormInfo(
            List<LowFormInfo> lowFormInfoList, Boolean
            isUpdateSupport,
            String operName)

    {
        for (int i = 0; i < lowFormInfoList.size(); i++) {
            LowFormInfo lowFormInfo=lowFormInfoList.get(i);
            try {
                // 验证是否存在这个用户
                //  getLogId() 换成获取实体 id 的方法
                LowFormInfo u = lowFormInfoMapper.selectLowFormInfoById(lowFormInfo.getFormUuid());
                if (StringUtils.isNull(u)) {
                    lowFormInfoMapper.insertLowFormInfo(lowFormInfo);
                    lowFormInfo.setImportStatus("导入成功");
                    lowFormInfoInfo.add(lowFormInfo);
                } else if (isUpdateSupport) {
                    lowFormInfoMapper.updateLowFormInfo(lowFormInfo);
                    lowFormInfo.setImportStatus("更新成功");
                    lowFormInfoInfo.add(lowFormInfo);
                } else {
                    lowFormInfo.setImportStatus("已存在当前数据");
                    lowFormInfoInfo.add(lowFormInfo);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                lowFormInfo.setImportStatus("数据格式有问题，请认证检查数据！");
                lowFormInfoInfo.add(lowFormInfo);
                //如果sql插入数据报错，或者更新报错，都会进行数据的回调，直到excel的数据读取完
                importLowFormInfo(lowFormInfoList.subList(i + 1, lowFormInfoList.size()), isUpdateSupport, operName);
                return lowFormInfoInfo;
            }

        }
        return lowFormInfoInfo;
    }

    @Override
    public  List<Map<String, Object>>  queryOneAutoSqlInfo(String formId, String key) {
        //根据formId获取判断是哪个表
        LowFormInfo lowFormInfo = lowFormInfoMapper.selectLowFormInfoById(formId);
        AutoParamVo autoParamVo  = new AutoParamVo();
        autoParamVo.setFormId(formId);
        autoParamVo.setTableName(lowFormInfo.getFormEntityName());
        autoParamVo.setKey(key);
        autoParamVo.setOrderNumber(key);
        List<Map<String, Object>> stringObjectMap = lowFormInfoMapper.tableDataInfo(autoParamVo);
        
       
        /*if(lowFormInfo!=null){
            //组装sql查询结果
            String sql = "select * from "+lowFormInfo.getFormEntityName() +" where id = "+key;
            Object execute = sqlExecuteMapper.execute(sql);
            return execute;
        }*/


        return stringObjectMap;
    }

    @Override
    public  AjaxResult saveFormInfo(Map paramMap) throws Exception {
        //获取formId
        //获取id
        java.lang.String formId = paramMap.get("formId").toString();
        java.lang.String id = paramMap.get("id")+"";
        java.lang.String listIdApi = paramMap.get("listId")+"";

        //根据formId获取所有字段
        LowFormFiledInfo lowFormFiledInfo = new LowFormFiledInfo();
        lowFormFiledInfo.setFormUuid(formId);
        List<LowFormFiledInfo> formFiledInfos = lowFormFiledInfoMapper.selectLowFormFiledInfoList(lowFormFiledInfo);
        if(formFiledInfos.size()==0) {return AjaxResult.error("没有对应的字段");}
        //获取表名称
        LowFormInfo lowFormInfo = lowFormInfoMapper.selectLowFormInfoById(formId);
        //获取是否匿名提交
        String userName =null;
        try{
            if(!"NM_TABLE".equals(lowFormInfo.getBindDataWay())){
                String username = SecurityUtils.getUsername();
                userName=username;
            }else{
                //匿名提交
                userName= "匿名提交";
            }
        }catch (Exception e ){
            return AjaxResult.error("请先登录！");

        }
        LowBusinessList lowBusinessList =null;
        //判断是走API还是业务平台
        if(StringUtils.isNotEmpty(listIdApi) && !"null".equals(listIdApi)){
            lowBusinessList = lowBusinessListMapper.selectLowBusinessListById(listIdApi);
        }

        if(lowBusinessList!=null && StringUtils.isNotEmpty(lowBusinessList.getDataApi())){
            //走API数据中台
            //如果id为空，则为新增数据，否则为修改数据

            String[] dataApis = lowBusinessList.getDataApi().split(",");
            String API = "";
            if(dataApis.length!=4){
                return  AjaxResult.error("请确认列表配置有按照QUERY,ADD,UPDATE,DELETE配置四个API接口，逗号隔开“,”");
            }


            if(StringUtils.isEmpty(id) || "null".equals(id) || "".equals(id)){
                paramMap.put("CREATED_TIME",new Date());
                paramMap.put("CREATED_BY",SecurityUtils.getUsername());
                paramMap.put("UPDATED_TIME",new Date());
                paramMap.put("UPDATED_BY",SecurityUtils.getUsername());
                paramMap.put("status48624","ADD");
                API=dataApis[1];
            }else{
                //修改数据
                paramMap.put("UPDATED_TIME",new Date());
                paramMap.put("ID",id);
                paramMap.put("UPDATED_BY",SecurityUtils.getUsername());
                paramMap.put("status48624","UPT");
                API=dataApis[2];
            }
            StringBuffer results =new StringBuffer();
            results.append("{");

            //TODO
            //遍历MAP对象组装json串
            for (Object obj : paramMap.keySet()){
                results.append("'").append(obj).append("'").append(":");
                results.append("'").append(paramMap.get(obj)).append("'").append(",");
            }
            results.append("}");
            String str = results.toString();
            str= str.replace(",}","}");
            JSONObject result= getData(API,str,"row");
            return AjaxResult.success("success",result);

        }else{
            //走本业务平台
            String formEntityName = lowFormInfo.getFormEntityName();
            StringBuffer buffer = new StringBuffer();
            StringBuffer filedBuf = new StringBuffer();
            StringBuffer valueBuf = new StringBuffer();
            valueBuf.append( "values(");
            //如果id为空，则为新增数据，否则为修改数据
            if(StringUtils.isEmpty(id) || "null".equals(id) || "".equals(id)){
                //判断是否只能提交一次
                if("Y".equals(lowFormInfo.getIsSingleUser())){
                    String sql = "select count(1) from "+formEntityName + " where created_by = '"+userName+"'";
                    Long  result = (Long) sqlExecuteMapper.execute(sql);
                    if(result>0){
                        return AjaxResult.error("您已提交过了，不能重复提交！");
                    }
                }
                //新增数据
                buffer.append("insert into ");
                buffer.append(formEntityName).append("(");
                for(int i =0;i<formFiledInfos.size();i++){
                    //如果存在改字段且该字段的值不为空
                    if(StringUtils.isNotEmpty(paramMap.get(formFiledInfos.get(i).getEntityName())+"")){
                        if(paramMap.containsKey(formFiledInfos.get(i).getEntityName())){
                            //如果字段包含USER_PHONE字段，且值不为空，则生成登录账号
                            if(formFiledInfos.get(i).getEntityName().equals("USER_PHONE_REMOVE") && StringUtils.isNotEmpty(paramMap.get(formFiledInfos.get(i).getEntityName())+"")){
                                SysUser user=new SysUser();
                                SysUser user1 = userService.selectUserByUserName(SecurityUtils.getUsername());
                                SysUser user2 = userService.selectUserByUserName(paramMap.get(formFiledInfos.get(i).getEntityName()) + "");
                                if(user1!=null && user2==null){
                                    user.setDeptId(user1.getDeptId());
                                    user.setUserName(paramMap.get(formFiledInfos.get(i).getEntityName())+"");
                                    user.setNickName(paramMap.get(formFiledInfos.get(i).getEntityName())+"");
                                    user.setPhonenumber(paramMap.get(formFiledInfos.get(i).getEntityName())+"");
                                    user.setPassword(SecurityUtils.encryptPassword(paramMap.get(formFiledInfos.get(i).getEntityName())+""));
                                    userService.insertUser(user);
                                }
                            }

                            //用户注册时，生成用户信息
                            if(formFiledInfos.get(i).getEntityName().equals("PASS_WORD") && StringUtils.isNotEmpty(paramMap.get(formFiledInfos.get(i).getEntityName())+"")){
                                SysUser user=new SysUser();
                                SysUser user2 = userService.selectUserByUserName(paramMap.get(formFiledInfos.get(i).getEntityName()) + "");
                                if( user2==null){
                                    user.setDeptId(33551L);//默认组织
                                    user.setUserName(paramMap.get(formFiledInfos.get(i).getEntityName())+"");
                                    user.setNickName(paramMap.get(formFiledInfos.get(i).getEntityName())+"");
                                    // user.setPhonenumber(paramMap.get(formFiledInfos.get(i).getEntityName())+"");
                                    user.setIdCardNo(paramMap.get(formFiledInfos.get(i).getEntityName())+"");
                                    user.setPassword(SecurityUtils.encryptPassword(paramMap.get(formFiledInfos.get(i).getEntityName())+""));
                                    userService.insertUser(user);
                                }else{
                                    throw new Exception("用户已存在！");
                                }
                            }



                            if(formFiledInfos.get(i).getEntityName().contains("fileupload") || formFiledInfos.get(i).getEntityName().contains("pictureupload")){
                                //如果是上传的文件，则需要特殊处理
                                String s = paramMap.get(formFiledInfos.get(i).getEntityName()).toString();
                                ArrayList<Map> maps = (ArrayList<Map>) paramMap.get(formFiledInfos.get(i).getEntityName());
                                String result = handleFileUploadList(maps);
                                filedBuf.append(formFiledInfos.get(i).getEntityName()).append(",");
                                valueBuf.append("'").append(result).append("'").append(",");
                            }else{
                                //如果包含该字段，则封装sql
                                filedBuf.append(formFiledInfos.get(i).getEntityName()).append(",");
                                if(formFiledInfos.get(i).getEntityName().equals("DATARESOURCE_PASSWORD")){
                                    //如果字段是数据库密码，则需要进行加密
                                    String s = paramMap.get(formFiledInfos.get(i).getEntityName()) + "";
                                    s= AESUtil.encrypt4AES(s);
                                    valueBuf.append("'").append(s).append("'").append(",");
                                }else{
                                    valueBuf.append("'").append(paramMap.get(formFiledInfos.get(i).getEntityName())+"").append("'").append(",");
                                }


                            }

                        }
                    }

                }
                //插入序号
                String msg = getOrderNumber("XH_");
                filedBuf.append("ORDER_NUMBER, ");
                valueBuf.append("'").append(msg).append("',");
                //插入创建人和创建时间
                filedBuf.append("CREATED_BY ");
                valueBuf.append("'").append(userName).append("'");
                filedBuf.append(")");
                valueBuf.append(")");
                String sql = buffer.toString() +filedBuf.toString()+valueBuf.toString();
                //执行sql插入数据
                sqlExecuteMapper.execute(sql);


                //跑流程
                if(paramMap.containsKey("isSubmit") && "1".equals(paramMap.get("isSubmit").toString())){
                    //根据listId获取到最新的流程信息
                    if(paramMap.containsKey("listId")){
                        String listId = paramMap.get("listId").toString();
                        //根据listId获取到列表对应的流程信息
                        //    LowBusinessList lowBusinessList = lowBusinessListMapper.selectLowBusinessListById(listId);
                        String listFlowProcdefId = lowBusinessList.getListFlowProcdefId();
                        //提交，需要走流程
                        //TODO
                        //String requestUserId, String businessKey, String variables, String isAutoFinishFirstStep
                        Map param = new HashMap();
                        String[] flowDataInfo = listFlowProcdefId.split(",");
                        param.put("nextDealUser",paramMap.get("nextDealUser"));
                        param.put("content",DateUtils.dateTime()+"【"+"-"+flowDataInfo[1]+"】"+SecurityUtils.getNickName()+"发起"+lowBusinessList.getListName()+"的申请");
                        param.put("title",DateUtils.dateTime()+"【"+"-"+flowDataInfo[1]+"】"+SecurityUtils.getNickName()+"发起"+lowBusinessList.getListName()+"的申请");
                        param.put("applyId",flowDataInfo[0]);
                        String url = "/#/exam";
                        param.put("todoUrl",url);
                        param.put("pcTodoUrl",url);
                        param.put("urlParam","id="+formId+"&key="+msg+"&listId="+listId);
                        param.put("message",paramMap.get("btnMessage"));
                        String paramValue = JSON.toJSONString(param);
                        activitiService.starkFormTask(flowDataInfo[0],SecurityUtils.getUsername(),msg,paramValue,"1");
                    }else{
                        return  AjaxResult.error("列表ID未传入，无法发起流程！");
                    }
                }


                return AjaxResult.success(msg);
            }else{
                //判断该条数据是否是当前用户的
            /*String param = "select count(1) from "+formEntityName + "  where created_by = '"+userName+"' and id="+id;
            Object execute = sqlExecuteMapper.execute(param);
            Long  result = (Long) execute;
            if(result==0){
                return AjaxResult.error("您无权限修改该条数据！");
            }*/


                //修改数据
                buffer.append("update ");
                buffer.append(formEntityName).append(" set ");
                for(int i =0;i<formFiledInfos.size();i++){

                    
                    if(paramMap.containsKey(formFiledInfos.get(i).getEntityName())){
                        if(StringUtils.isNotEmpty(paramMap.get(formFiledInfos.get(i).getEntityName())+"")){//判断字段值是否为空
                            //如果包含该字段，则封装sql
                            buffer.append(formFiledInfos.get(i).getEntityName()).append("=");
                            if(formFiledInfos.get(i).getEntityName().contains("fileupload") || formFiledInfos.get(i).getEntityName().contains("pictureupload")){
                                //如果是上传的文件，则需要特殊处理
                                String s = paramMap.get(formFiledInfos.get(i).getEntityName()).toString();
                                ArrayList<Map> maps = (ArrayList<Map>) paramMap.get(formFiledInfos.get(i).getEntityName());
                                String result = handleFileUploadList(maps);
                                buffer.append("'").append(result+"").append("'").append(",");
                            }else{

                                buffer.append("'").append(paramMap.get(formFiledInfos.get(i).getEntityName())+"").append("'").append(",");
                            }
                           

                        }

                    }
                }
                //封装人员
                buffer.append(" UPDATED_BY ").append(" = ");
                buffer.append("'").append(userName).append("'").append(",");

                //封装日期
                String s = DateUtils.dateTimeNow("yyyy-MM-dd HH:mm:ss");
                buffer.append(" UPDATED_TIME ").append(" = ");
                buffer.append("'").append(s).append("'");

                //添加条件
                buffer.append(" where id=").append(id);
                String sql = buffer.toString();
                sqlExecuteMapper.execute(sql);
                return   AjaxResult.success("修改成功！");
            }
        }



    }

    public static String getOrderNumber(String key) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String date = format.format(new Date());
        RedisCache redisCache = SpringUtils.getBean(RedisCache.class);
        return redisCache.getOrderNumber(key + date, 4);
    }

    public static String handleFileUpload(String s){
        // String s = JSON.toJSONString(s);
        JSONArray array = new JSONArray(Collections.singletonList(s));
        // JSONArray array = JSONArray.parseArray(s);
        List<Map> result = new ArrayList<>();
        Map map = new HashMap();
        for(int ii = 0;ii<array.size();ii++){
            String o = array.get(ii).toString();
            Map maps = (Map)JSON.parse(o);
            map.put("size",maps.get("size")+"");
            String response = maps.get("response").toString();
            Map mapss = (Map)JSON.parse(response);
            map.put("name",mapss.get("name")+"");
            map.put("url",mapss.get("url")+"");
            result.add(map);
        }
        //List 转 JSONArray
        String s1 = JSON.toJSONString(result);
        return s1;

    }

    public static String handleFileUploadList(List<Map> mapList){
        List<Map> result = new ArrayList<>();
        for(int ii = 0;ii<mapList.size();ii++){
            Map map = new HashMap();
            Map map1 = mapList.get(ii);
           // map.put("size",map1.get("size")+"");
            map.put("name",map1.get("name")+"");
         //   Map response =(Map) map1.get("response");
            map.put("url",map1.get("url")+"");
            result.add(map);
        }
        //List 转 JSONArray
        String s1 = JSON.toJSONString(result);
        return s1;

    }




    private void addApi(String[] title,String tableName,String  formNmae){
        String datasourceId = this.datasourceId;
        DataApiInfos dataApiInfos = null;
        StringBuffer insertSqlBuffer = new StringBuffer();
        StringBuffer deleteSqlBuffer = new StringBuffer();
        StringBuffer updateSqlBuffer = new StringBuffer();
        StringBuffer querySqlBuffer = new StringBuffer();

        //查询API
        querySqlBuffer.append("select * from ").append(tableName).append(" where 1=1 { and id = #id# }  limit 1000");
        dataApiInfos =  new DataApiInfos();
        dataApiInfos.setDatasourceId(datasourceId);
        dataApiInfos.setResourceId(getUUID());
        dataApiInfos.setResourceCode(getUUID());
        dataApiInfos.setApiCode("NGS_QUERY_"+tableName);
        dataApiInfos.setCreateBy(SecurityUtils.getUsername());
        // dataApiInfos.setCreateTime(new Date());
        dataApiInfos.setUpdateBy(SecurityUtils.getUsername());
        // dataApiInfos.setUpdateTime(new Date());
        dataApiInfos.setResourceName("查询接口-"+formNmae);
        dataApiInfos.setApiDefination(querySqlBuffer.toString());
        dataApiInfosService.insertDataApiInfos(dataApiInfos);


        //删除API
        deleteSqlBuffer.append("delete from ").append(tableName).append(" where id = #id#  ");
        dataApiInfos =  new DataApiInfos();
        dataApiInfos.setDatasourceId(datasourceId);

        dataApiInfos.setResourceId(getUUID());
        dataApiInfos.setResourceCode(getUUID());
        dataApiInfos.setApiCode("NGS_DELETE_"+tableName);
        dataApiInfos.setResourceName("删除接口-"+formNmae);
        dataApiInfos.setCreateBy(SecurityUtils.getUsername());
        dataApiInfos.setCreateTime(new Date());
        dataApiInfos.setUpdateBy(SecurityUtils.getUsername());
        dataApiInfos.setUpdateTime(new Date());
        dataApiInfos.setApiDefination(deleteSqlBuffer.toString());
        dataApiInfosService.insertDataApiInfos(dataApiInfos);

        //新增
        insertSqlBuffer.append("insert into ").append(tableName).append("(");

        //修改
        updateSqlBuffer.append("update ").append(tableName).append(" set ");

        StringBuffer insertValueBuffer = new StringBuffer();
        for (int i = 0; i < title.length; i++) {
            if(title[i].toUpperCase().equals("ID")){
                //新增的API不需要添加ID字段，ID字段默认自增
                continue;
            }
            insertSqlBuffer.append(title[i]);
            insertValueBuffer.append("#").append(title[i]).append("#");
            updateSqlBuffer.append(title[i]).append("=").append("#").append(title[i]).append("#");
            if(i  != title.length-1){
                insertSqlBuffer.append(",");
                insertValueBuffer.append(",");
                updateSqlBuffer.append(",");
            }
        }
        insertSqlBuffer.append(") values(");
        insertValueBuffer.append(")");
        insertSqlBuffer.append(insertValueBuffer);

        updateSqlBuffer.append(" where id = #id#");

        dataApiInfos =  new DataApiInfos();
        dataApiInfos.setDatasourceId(datasourceId);
        dataApiInfos.setResourceId(getUUID());
        dataApiInfos.setResourceCode(getUUID());
        dataApiInfos.setApiCode("NGS_INSERT_"+tableName);
        dataApiInfos.setCreateBy(SecurityUtils.getUsername());
        dataApiInfos.setCreateTime(new Date());
        dataApiInfos.setUpdateBy(SecurityUtils.getUsername());
        dataApiInfos.setUpdateTime(new Date());
        dataApiInfos.setResourceName("新增接口-"+formNmae);
        dataApiInfos.setApiDefination(insertSqlBuffer.toString());
        dataApiInfosService.insertDataApiInfos(dataApiInfos);


        //修改
        dataApiInfos =  new DataApiInfos();
        dataApiInfos.setDatasourceId(datasourceId);
        dataApiInfos.setResourceId(getUUID());
        dataApiInfos.setResourceCode(getUUID());
        dataApiInfos.setApiCode("NGS_UPDATE_"+tableName);
        dataApiInfos.setCreateBy(SecurityUtils.getUsername());
        dataApiInfos.setCreateTime(new Date());
        dataApiInfos.setUpdateBy(SecurityUtils.getUsername());
        dataApiInfos.setUpdateTime(new Date());
        dataApiInfos.setResourceName("修改接口-"+formNmae);
        dataApiInfos.setApiDefination(updateSqlBuffer.toString());
        dataApiInfosService.insertDataApiInfos(dataApiInfos);
    }
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }


    /**
     * 调用中台能力
     * @param ApiCode
     * @param param
     * @param rowType
     * @return
     */

    @Override
    public JSONObject getData(String ApiCode , String param,String rowType ){
        String reslt = result(param, ApiCode,rowType);
        JSONObject jsonObject = JSON.parseObject(reslt);

        return  jsonObject;
    }
    @Override
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


    public void createJimuReport(String id,String code,String name){
        String sql = "INSERT INTO jimu_report(id,CODE,NAME) VALUES('"+id+"','"
                +code+"','"+name+"')";
        sqlExecuteMapper.execute(sql);
    }

    @Override
    public LowFormInfo selectLowFormInfoByFormEntityName(String formEntityName){
        return lowFormInfoMapper.selectLowFormInfoByFormEntityName(formEntityName);
    }

    public static void main(String[] args) {
        String s="[{status=success, name=371ce954-654f-40a0-a4f7-74abcd2ba55b.xlsx, size=16415, percentage=100, uid=1685759709438,\n" +
                " raw={uid=1685759709438}, response={msg=操作成功, fileName=/profile/upload/2023/06/03/e592c711-e7c3-4b4d-9237-f8af140ddd20.xlsx,\n" +
                " code=200, name=371ce954-654f-40a0-a4f7-74abcd2ba55b.xlsx, newFileName=e592c711-e7c3-4b4d-9237-f8af140ddd20.xlsx, \n" +
                " url=http://192.168.71.1:8087/prod-api/profile/upload/2023/06/03/e592c711-e7c3-4b4d-9237-f8af140ddd20.xlsx,\n" +
                " originalFilename=371ce954-654f-40a0-a4f7-74abcd2ba55b.xlsx}}]";
        //    Object parse = JSONObject.parse(s);
        String s1 = JSON.toJSONString(s);

      //  System.out.println(s1);
        //  Object parse = JSON.parseObject(s1);
        // JSONObject result = JSONArray.parseObject(s1);
        JSONArray array = new JSONArray(Collections.singletonList(s));
        Collections.singletonList(s);
        // System.out.println(result.toString());
        List<Map> result = new ArrayList<>();
        Map map = new HashMap();
        for(int ii = 0;ii<array.size();ii++){
            String o = array.get(ii).toString().replace("[","").replace("[","").replace("=",":");
            //  Map maps = JSONObject.parseObject(o, Map.class);
            FileStr fileStr = JSON.parseObject(s1,FileStr.class);
            //  Map maps = (Map)JSON.parse(o);
            //   map.put("size",maps.get("size")+"");
            // String response = maps.get("response").toString();
            //Map mapss = (Map)JSON.parse(response);
            //  map.put("name",mapss.get("name")+"");
            // map.put("url",mapss.get("url")+"");

            // result.add(map);
        }
        //List 转 JSONArray
        String s11 = JSON.toJSONString(result);

    }



    @Override
    public  AjaxResult saveChildFormInfo(Map paramMap) throws Exception{
        
        //获取到主表单
        LowFormInfo lowFormInfo = lowFormInfoMapper.selectLowFormInfoById(paramMap.get("formId").toString());
        //获取到子表单
        LowFormInfo lowFormInfoChild = lowFormInfoMapper.selectLowFormInfoById(paramMap.get("childFormId").toString());
        //获取到主业务列表
        LowBusinessList lowBusinessList = lowBusinessListMapper.selectLowBusinessListById(paramMap.get("listId").toString());
        //封装子表的数据
        ArrayList<Map> childmap = null;
        try {
            childmap = (ArrayList) paramMap.get("childmap");
        }catch (Exception e){
            
        }

        //获取到子表单的列表头信息
        String listMemoChild = lowBusinessList.getListMemoChild();
        List<ListTitleVo> listMemoChildVo = JSON.parseArray(listMemoChild, ListTitleVo.class);
        //遍历获取到主键字段
        String pk=null;
        String pkValue = null;
        for (int i =0;i<listMemoChildVo.size();i++){
            if("1".equals(listMemoChildVo.get(i).getPk()+"")){
                 pk = listMemoChildVo.get(i).getProp();
                break;
            }
        }
        
    
        String key = paramMap.get("ID") + "";
        if(StringUtils.isEmpty(key) || "null".equals(key) || "".equals(key)){
            //新增
            //插入主表信息,得到返回的ORDER_NUMBER
            String s = insertData(lowFormInfo, paramMap);
            //根据ORDER_NUMBER获取到插入的数据
            String sql = "select * from "+lowFormInfo.getFormEntityName()+" where ORDER_NUMBER = '"+s+"'";
            List<Map<String, Object>> maps = sqlExecuteMapper.selectPublicItemList(sql);
            //获取到主子表关联的主键值
            pkValue = maps.get(0).get(pk).toString();
        }else{
            //修改
            //更新主表数据
            update(lowFormInfo,paramMap,key);
            //根据id获取到插入的数据
            String sql = "select * from "+lowFormInfo.getFormEntityName()+" where ID = '"+key+"'";
            List<Map<String, Object>> maps = sqlExecuteMapper.selectPublicItemList(sql);
            //获取到主子表关联的主键值
            pkValue = maps.get(0).get(pk).toString();
            //删除子表数据
            sql = "update "+ lowFormInfoChild.getFormEntityName() +" set del_flag= '1' where " +pk +"='" +pkValue+"'";
            sqlExecuteMapper.execute(sql);
           
        }
        
        //插入子表数据
        if(childmap!=null && childmap.size()>0){
            for(int i = 0;i<childmap.size();i++){
                Map map = childmap.get(i);
                map.put(pk,pkValue);
                //插入一条子表数据
                insertData(lowFormInfoChild,map);
            }

        }


        return AjaxResult.success("保存成功！");
        
    }
    
    
    //插入一条数据,返回插入的ORDER_NUMBER
    public String insertData( LowFormInfo lowFormInfo,Map paramMap) {
        String formEntityName = lowFormInfo.getFormEntityName();
        StringBuffer buffer = new StringBuffer();
        StringBuffer filedBuf = new StringBuffer();
        StringBuffer valueBuf = new StringBuffer();

        //根据formId获取所有字段
        LowFormFiledInfo lowFormFiledInfo = new LowFormFiledInfo();
        lowFormFiledInfo.setFormUuid(lowFormInfo.getFormUuid());
        List<LowFormFiledInfo> formFiledInfos = lowFormFiledInfoMapper.selectLowFormFiledInfoList(lowFormFiledInfo);
        valueBuf.append("values(");
            //新增数据
            buffer.append("insert into ");
            buffer.append(formEntityName).append("(");
            for (int i = 0; i < formFiledInfos.size(); i++) {
                //如果存在改字段且该字段的值不为空
                if (StringUtils.isNotEmpty(paramMap.get(formFiledInfos.get(i).getEntityName()) + "")) {
                    if (paramMap.containsKey(formFiledInfos.get(i).getEntityName())) {
                        
                        if (formFiledInfos.get(i).getEntityName().contains("fileupload") || formFiledInfos.get(i).getEntityName().contains("pictureupload")) {
                            //如果是上传的文件，则需要特殊处理
                            String s = paramMap.get(formFiledInfos.get(i).getEntityName()).toString();
                            ArrayList<Map> maps = (ArrayList<Map>) paramMap.get(formFiledInfos.get(i).getEntityName());
                            String result = handleFileUploadList(maps);
                            filedBuf.append(formFiledInfos.get(i).getEntityName()).append(",");
                            valueBuf.append("'").append(result).append("'").append(",");
                        } else {
                            //如果包含该字段，则封装sql
                            filedBuf.append(formFiledInfos.get(i).getEntityName()).append(",");
                            if (formFiledInfos.get(i).getEntityName().equals("DATARESOURCE_PASSWORD")) {
                                //如果字段是数据库密码，则需要进行加密
                                String s = paramMap.get(formFiledInfos.get(i).getEntityName()) + "";
                                s = AESUtil.encrypt4AES(s);
                                valueBuf.append("'").append(s).append("'").append(",");
                            } else {
                                valueBuf.append("'").append(paramMap.get(formFiledInfos.get(i).getEntityName()) + "").append("'").append(",");
                            }
                        }
                    }
                }

            }
            //插入序号
            String msg = getOrderNumber("XH_");
            filedBuf.append("ORDER_NUMBER, ");
            valueBuf.append("'").append(msg).append("',");
            //插入创建人和创建时间
            filedBuf.append("CREATED_BY ");
            valueBuf.append("'").append(SecurityUtils.getUsername()).append("'");
            filedBuf.append(")");
            valueBuf.append(")");
            String sql = buffer.toString() + filedBuf.toString() + valueBuf.toString();
            //执行sql插入数据
            sqlExecuteMapper.execute(sql);
            return msg;
        
    }
    
    public void update(LowFormInfo lowFormInfo,Map paramMap,String id){
        StringBuffer buffer = new StringBuffer();
        StringBuffer filedBuf = new StringBuffer();
        StringBuffer valueBuf = new StringBuffer();

        //根据formId获取所有字段
        LowFormFiledInfo lowFormFiledInfo = new LowFormFiledInfo();
        lowFormFiledInfo.setFormUuid(lowFormInfo.getFormUuid());
        List<LowFormFiledInfo> formFiledInfos = lowFormFiledInfoMapper.selectLowFormFiledInfoList(lowFormFiledInfo);
        
        //修改数据
        buffer.append("update ");
        buffer.append(lowFormInfo.getFormEntityName()).append(" set ");
        for(int i =0;i<formFiledInfos.size();i++){
            if(paramMap.containsKey(formFiledInfos.get(i).getEntityName())){
                if(StringUtils.isNotEmpty(paramMap.get(formFiledInfos.get(i).getEntityName())+"")){//判断字段值是否为空
                    //如果包含该字段，则封装sql
                    buffer.append(formFiledInfos.get(i).getEntityName()).append("=");
                    buffer.append("'").append(paramMap.get(formFiledInfos.get(i).getEntityName())+"").append("'").append(",");

                }

            }
        }
        //封装人员
        buffer.append(" UPDATED_BY ").append(" = ");
        buffer.append("'").append(SecurityUtils.getUsername()).append("'").append(",");

        //封装日期
        String s = DateUtils.dateTimeNow("yyyy-MM-dd HH:mm:ss");
        buffer.append(" UPDATED_TIME ").append(" = ");
        buffer.append("'").append(s).append("'");

        //添加条件
        buffer.append(" where id=").append(id);
        String sql = buffer.toString();
        sqlExecuteMapper.execute(sql);
        
    }
    
    
    

}
