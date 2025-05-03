package com.yabushan.web.controller.capacity;

import cn.hutool.core.date.DateUtil;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.yabushan.ai.domain.AiResultVo;
import com.yabushan.ai.service.WenZiShibieService;
import com.yabushan.ai.utils.AipOcrUtils;
import com.yabushan.capacity.utils.AesSafeText;
import com.yabushan.capacity.utils.ShumengAesUtils;
import com.yabushan.capacity.utils.WordToMP3;
import com.yabushan.common.annotation.Log;
import com.yabushan.common.config.RuoYiConfig;
import com.yabushan.common.constant.Constants;
import com.yabushan.common.core.controller.BaseController;
import com.yabushan.common.core.domain.AjaxResult;
import com.yabushan.common.core.domain.model.AppUserPwd;
import com.yabushan.common.core.domain.model.LoginUser;
import com.yabushan.common.enums.BusinessType;
import com.yabushan.common.utils.QRCodeGeneratorUtils;
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.common.utils.ServletUtils;
import com.yabushan.common.utils.file.FileUploadUtils;
import com.yabushan.common.utils.file.FileUtils;
import com.yabushan.common.utils.uuid.IdUtils;
import com.yabushan.form.controller.DataApiInfosController;
import com.yabushan.form.domain.LowFormFiledInfo;
import com.yabushan.form.domain.LowFormInfo;
import com.yabushan.form.domain.PmisApiLog;
import com.yabushan.form.mapper.SqlExecuteMapper;
import com.yabushan.form.service.ILowFormFiledInfoService;
import com.yabushan.form.service.ILowFormInfoService;
import com.yabushan.framework.web.service.SysLoginService;
import com.yabushan.framework.web.service.TokenService;
import com.yabushan.system.domain.DataApiInfos;
import com.yabushan.system.service.IDataApiInfosService;
import com.yabushan.system.service.ISysUserService;
import com.yabushan.web.utils.AESUtil;
import com.yabushan.web.utils.AppTokenUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/capacity")
@Api(tags = "能力")
public class CapacityController  extends BaseController {
    @Autowired
    private SqlExecuteMapper sqlExecuteMapper;

    @Autowired
    private ISysUserService userService;

   

    @Autowired
    private TokenService tokenService;

    @Autowired
    private IDataApiInfosService dataApiInfosService;

    @Autowired
    private ILowFormInfoService lowFormInfoService;
    @Autowired
    private ILowFormFiledInfoService lowFormFiledInfoService;
    
    
    @Autowired
    private WenZiShibieService wenZiShibieService;


    @Value("${BAIDU_AI.WZSB.FILE_SIZE_MB}")
    private Long fileSize;

    /**
     * 重置密码
     */
    @ApiOperation(value = "重置密码")
    @Log(title = "重置密码", businessType = BusinessType.UPDATE)
    @PostMapping("/appUpdatePwd")
    public AjaxResult updatePwd(@RequestBody AppUserPwd userPwd) throws Exception {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String[] strings = AppTokenUtils.validateValue(userPwd.getAppToken());
        if(strings==null){
            return AjaxResult.error("修改密码异常，请联系管理员");
        }
        //先解密
        String s = AESUtil.aesDecrypt(userPwd.getNewPassword());
        String replace = s.replace(strings[0] + "_", "");
        
        if (userService.resetUserPwd(loginUser.getUsername(), SecurityUtils.encryptPassword(replace)) > 0)
        {
            // 更新缓存用户密码
            loginUser.getUser().setPassword(SecurityUtils.encryptPassword(replace));
            tokenService.setLoginUser(loginUser);
            return AjaxResult.success();
        }
        return AjaxResult.error("修改密码异常，请联系管理员");
    }

    /**
     * 头像上传
     */
    @PostMapping("/appAvatar")
    @Log(title = "头像上传", businessType = BusinessType.UPDATE)
    public AjaxResult avatar(@RequestParam("avatarfile") MultipartFile file) throws IOException
    {
        if (!file.isEmpty())
        {
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            String avatar = FileUploadUtils.upload(RuoYiConfig.getAvatarPath(), file);
            if (userService.updateUserAvatar(loginUser.getUsername(), avatar))
            {
                AjaxResult ajax = AjaxResult.success();
                ajax.put("imgUrl", avatar);
                // 更新缓存用户头像
                loginUser.getUser().setAvatar(avatar);
                tokenService.setLoginUser(loginUser);
                String sql ="UPDATE  ngs_app_user_manager g SET g.USER_IMG='"+avatar+"' WHERE g.USER_NAME='"+loginUser.getUsername()+"'";
                sqlExecuteMapper.execute(sql);
                return ajax;
            }
        }
        return AjaxResult.error("上传图片异常，请联系管理员");
    }

    /**
     * 万能查询接口
     * @param
     * @return
     */
    @RequestMapping(value = "/data/customerApi")
    public AjaxResult getMessageRow(String ApiCode,String param,String rowType,String appToken) {

        String[] strings = null;
        try{
            strings = AppTokenUtils.validateValue(appToken);
            if(strings==null){
                return AjaxResult.error("应用Token失效或已过期");
            }
        }catch (Exception e ){
            return AjaxResult.error(e.toString());
        }
        PmisApiLog pmisApiLog = new PmisApiLog();
        pmisApiLog.setLogId(com.yabushan.common.utils.StringUtils.getUUID());
        pmisApiLog.setApiCode(ApiCode+"_"+AppTokenUtils.getAppKey(strings));
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
            if(!rowType.equals("row") &&  !rowType.equals("list")){
                return AjaxResult.error("类型参数错误！");
            }
        }
   
            //从平台执行
            //获取到表单所有字段
            String[] s = dataApiInfos1.getApiCode().split("@");
            String tableName= s[s.length-1];
             tableName =tableName.replace("NGS_QUERY_", "").replace("NGS_DELETE_", "")
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
            if(jsonObject.containsKey("CURRENT_USER_NAME")){
                //如果有loginName，则替换为当前登录人
                exeSql=exeSql.replaceAll("#CURRENT_USER_NAME#","'"+SecurityUtils.getUsername()+"'");
            }
            
            if(jsonObject.containsKey("CURRENT_APP_KEY")){
                //如果APP_KEY
                exeSql=exeSql.replaceAll("#CURRENT_APP_KEY#","'"+AppTokenUtils.getAppKey(strings)+"'");
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

    }

    /**
     * 加密
     * @param aesSafeText
     * @return
     */
    @PostMapping(value = "AesEncrypt")
    public AjaxResult AesEncrypt(@RequestBody AesSafeText aesSafeText){
        String[] strings = null;
        try{
            strings = AppTokenUtils.validateValue(aesSafeText.getAppToken());
            if(strings==null){
                return AjaxResult.error("应用Token失效或已过期");
            }
            String s = ShumengAesUtils.AesEncrypt(aesSafeText.getText(), aesSafeText.getKey());
            return AjaxResult.success(s);
        }catch (Exception e ){
            return AjaxResult.error(e.toString());
        }
    }

    /**
     * 解密
     * @param aesSafeText
     * @return
     */
    @PostMapping(value = "/AesDncrypt")
    public AjaxResult AesDncrypt(@RequestBody AesSafeText aesSafeText){
        String[] strings = null;
        try{
            strings = AppTokenUtils.validateValue(aesSafeText.getAppToken());
            if(strings==null){
                return AjaxResult.error("应用Token失效或已过期");
            }
            String s = ShumengAesUtils.Decode(aesSafeText.getText(), aesSafeText.getKey());
            return AjaxResult.success(s);
        }catch (Exception e ){
            return AjaxResult.error(e.toString());
        }
    }


    /**
     * 图片识别通用能力
     * @param file
     * @return
     */
    @PostMapping(value = "/OcrText")
    public AjaxResult OcrText(MultipartFile file) throws IOException {
        long size = file.getSize();
        long limitSize = 1024*1024*fileSize;
        if(size>limitSize){
           return  AjaxResult.error("文件大小超出限制"+fileSize+"MB");
        }
        //获取名字
        String name = file.getOriginalFilename();
        //如果不是xls、xlsx  结尾的则为图片识别的数据
        InputStream inputStream = file.getInputStream();

        // 保存的文件路径
        String basePath = RuoYiConfig.getUploadPath()+ "/ai/"+ IdUtils.simpleUUID();
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
        //删除文件
        AipOcrUtils.deleteDirectory(basePath);
        return  AjaxResult.success(message);
     
    }
    



}
