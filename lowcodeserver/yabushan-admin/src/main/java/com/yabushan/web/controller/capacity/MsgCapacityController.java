package com.yabushan.web.controller.capacity;

import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.yabushan.capacity.utils.AesSafeText;
import com.yabushan.capacity.utils.SendCodeUtils;
import com.yabushan.capacity.utils.WordToMP3;
import com.yabushan.common.config.RuoYiConfig;
import com.yabushan.common.core.controller.BaseController;
import com.yabushan.common.core.domain.AjaxResult;
import com.yabushan.common.core.domain.model.MsgCode;
import com.yabushan.common.utils.QRCodeGeneratorUtils;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.common.utils.file.FileUtils;
import com.yabushan.form.controller.DataApiInfosController;
import com.yabushan.form.domain.PmisApiLog;
import com.yabushan.form.mapper.SqlExecuteMapper;
import com.yabushan.web.controller.tool.TextToSpeech;
import com.yabushan.web.utils.AppTokenUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/msgCode")
@Api(tags = "短信能力")
public class MsgCapacityController extends BaseController {


    @Autowired
    private SqlExecuteMapper sqlExecuteMapper;

    /**
     * 发送验证码短信 @Value("${daas.datasourceId}")
     */
    @PostMapping("/sendCode")
    public AjaxResult sendCode(@RequestBody MsgCode msgCode) throws Exception {
        String[] strings = AppTokenUtils.validateValue(msgCode.getAppToken());
        String s=null;
        PmisApiLog pmisApiLog = new PmisApiLog();
        pmisApiLog.setLogId(com.yabushan.common.utils.StringUtils.getUUID());
        pmisApiLog.setApiCode(AppTokenUtils.getAppKey(strings));
        pmisApiLog.setApiRowType("SMS");
        pmisApiLog.setApiParam(msgCode.toString());
        
        if(strings==null ){
            return AjaxResult.error("AppToken无效");
        }
        if(msgCode.getSmsCode()==null ){
            return AjaxResult.error("短信编码错误");
        }
        if(StringUtils.isEmpty(msgCode.getPhoneNumber())){
            return AjaxResult.error("手机号错误");
        }
        String appKey = AppTokenUtils.getAppKey(strings);
        String sql ="SELECT  S.MASTER_SECRET,S.APP_KEY,S.TEMPLATE_ID,S.SIGN_ID FROM   ngs_sms_template s,  ngs_app_manager_list a WHERE a.CREATED_BY = s.CREATED_BY   AND a.app_key = '"+appKey+"'   AND S.APP_CODE = '"+msgCode.getSmsCode()+"'   AND A.del_flag='0'  AND S.del_flag='0'   AND A.APP_STATUS='上线'";
        List<Map<String, Object>> maps = sqlExecuteMapper.selectPublicItemList(sql);
        if(maps.size()!=1){
            return AjaxResult.error("无短信模板配置");
        }
        Map<String, Object> stringObjectMap = maps.get(0);
        Integer code = SendCodeUtils.getRandomInteger();
        
        if(StringUtils.isNotNull(msgCode.getSmsType()) && msgCode.getSmsType()==1){
            //短信类模板
          s= SendCodeUtils.testSendTemplateSMS(stringObjectMap.get("MASTER_SECRET").toString(), stringObjectMap.get("APP_KEY").toString(),
                    Integer.valueOf(stringObjectMap.get("TEMPLATE_ID").toString()), Integer.valueOf(stringObjectMap.get("SIGN_ID").toString()),msgCode.getPhoneNumber());
        }else{
            //通知类模板
             s = SendCodeUtils.testSendNoiceTemplateSMS(stringObjectMap.get("MASTER_SECRET").toString(), stringObjectMap.get("APP_KEY").toString(),
                    Integer.valueOf(stringObjectMap.get("TEMPLATE_ID").toString()), Integer.valueOf(stringObjectMap.get("SIGN_ID").toString()),String.valueOf(code),msgCode.getPhoneNumber());
        }
        
        
        pmisApiLog.setApiResult(s.toString());
        //保存日志
        Thread thread = new Thread(new DataApiInfosController(pmisApiLog));
        thread.start();
        
        if(s==null){
            return AjaxResult.error("短信发送失败",s);
        }
        return AjaxResult.success(s);
    }

   


    /**
     * 校验短信是否通过
     */
    @PostMapping("/validateCode")
    public AjaxResult validateCode(@RequestBody MsgCode msgCode) throws Exception {
        String[] strings = AppTokenUtils.validateValue(msgCode.getAppToken());

        PmisApiLog pmisApiLog = new PmisApiLog();
        pmisApiLog.setLogId(com.yabushan.common.utils.StringUtils.getUUID());
        pmisApiLog.setApiCode(AppTokenUtils.getAppKey(strings));
        pmisApiLog.setApiRowType("SMS_VALIDATE");
        pmisApiLog.setApiParam(msgCode.toString());
                
        if(strings==null ){
            return AjaxResult.error("AppToken无效");
        }
        if(msgCode.getSmsCode()==null ){
            return AjaxResult.error("短信编码错误");
        }
       
        String appKey = AppTokenUtils.getAppKey(strings);
        String sql ="SELECT  S.MASTER_SECRET,S.APP_KEY,S.TEMPLATE_ID,S.SIGN_ID FROM   ngs_sms_template s,  ngs_app_manager_list a WHERE a.CREATED_BY = s.CREATED_BY   AND a.app_key = '"+appKey+"'   AND S.APP_CODE = '"+msgCode.getSmsCode()+"'   AND A.del_flag='0'  AND S.del_flag='0'   AND A.APP_STATUS='上线'";
        List<Map<String, Object>> maps = sqlExecuteMapper.selectPublicItemList(sql);
        if(maps.size()!=1){
            return AjaxResult.error("无短信模板配置");
        }
        Map<String, Object> stringObjectMap = maps.get(0);
        Boolean aBoolean = SendCodeUtils.checkSms(stringObjectMap.get("MASTER_SECRET").toString(), stringObjectMap.get("APP_KEY").toString(), msgCode.getMsgId(), msgCode.getCode());
       
        if(aBoolean){
            pmisApiLog.setApiResult("校验成功");
            //保存日志
            Thread thread = new Thread(new DataApiInfosController(pmisApiLog));
            thread.start();
            return AjaxResult.success("校验成功");
        }else{
            pmisApiLog.setApiResult("校验失败");
            //保存日志
            Thread thread = new Thread(new DataApiInfosController(pmisApiLog));
            thread.start();
            return  AjaxResult.error("校验失败，请重新获取验证码");
        }

       
   
    }


    

    /**
     * 字符串转mp3
     */
    @GetMapping("/textToVoid")
    public AjaxResult commonresourceDownload(String text) throws Exception {
        // 本地资源路径
        String localPath = RuoYiConfig.getDownloadPath();
        
        String  downloadPath=null;
        String property = System.getProperty("os.name");
        if(property.contains("Windows")){
            downloadPath=WordToMP3.textToSpeechIO(text, localPath);
        }else{
            downloadPath=  TextToSpeech.convertTextToSpeech(text,localPath);
        }
        return  AjaxResult.success(downloadPath);
        //删除文件
       // FileUtils.deleteFile(downloadPath);
    }


    /**
     * 生成二维码
     *
     * @param content 二维码的跳转地址,内容
     */
    @GetMapping(value = "/createCode")
    public void getCode(String content, HttpServletResponse response) throws IOException, WriterException {
        // 设置响应流信息
        response.setContentType("image/jpg");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        OutputStream stream = response.getOutputStream();
        //获取一个二维码图片
        BitMatrix bitMatrix = QRCodeGeneratorUtils.generateQRCodeImageIO(content);
        //以流的形式输出到前端
        MatrixToImageWriter.writeToStream(bitMatrix, "jpg", stream);
    }


   

}
