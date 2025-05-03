package com.yabushan.web.controller.weixin;

import com.yabushan.ai.domain.VoiceRecord;
import com.yabushan.ai.iflytek.SpeedTranscriptionMain;
import com.yabushan.ai.service.TextSummaryService;
import com.yabushan.common.core.domain.AjaxResult;
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.common.utils.uuid.UUID;
import com.yabushan.form.mapper.SqlExecuteMapper;
import com.yabushan.web.model.vo.VoiceVo;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api/voice")
@Slf4j
public class VoiceController {

    @Value("${voice.upload.path}")
    private String uploadPath;
    @Value("${voice.upload.APP_ID}")
    private String APP_ID;
    @Value("${voice.upload.API_SECRET}")
    private String API_SECRET;
    @Value("${voice.upload.API_KEY}")
    private String API_KEY;

    @Autowired
    private SqlExecuteMapper sqlExecuteMapper;

    @Autowired
    private TextSummaryService summaryService;

    @PostMapping("/upload")
    public AjaxResult uploadVoice(@RequestParam("file") MultipartFile file,
                                  @RequestParam("userId") String userId) {
        try {
            // 检查文件
            if (file.isEmpty()) {
                return AjaxResult.error("文件为空");
            }

            //校验每个用户每天只能操作10次
            String sqlCount ="SELECT COUNT(1) FROM ai_voice_info F  WHERE F.createTime>CURDATE() AND f.userId='"+SecurityUtils.getUsername()+"'";
            Integer i = Integer.valueOf(sqlExecuteMapper.execute(sqlCount).toString());
            if(!SecurityUtils.getUsername().equals("admin") && i>10){
                return AjaxResult.error("每天只能语音输入10次哦！");
            }
            // 生成文件名
            String fileName = UUID.randomUUID().toString() + ".wav";


            // 确保目录存在
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // 保存文件
            File destFile = new File(uploadPath  + fileName);
            file.transferTo(destFile);

            // 保存记录到数据库
            VoiceRecord record = new VoiceRecord();
            record.setUserId(SecurityUtils.getUsername());
            record.setFileName(fileName);
            record.setFilePath(destFile.getAbsolutePath());
            record.setCreateTime(new Date());
            String sql ="insert into ai_voice_info(fileName,filePath,userId) values('"+record.getFileName()+"','"+record.getFilePath()+"','"+record.getUserId()+"')";
            sqlExecuteMapper.execute(sql);
            String filePath = uploadPath +  fileName;
            //调用API解析数据
            String s =null;
            VoiceVo voiceVo= new VoiceVo();
            try{
                s=SpeedTranscriptionMain.voiceToStr(APP_ID, API_SECRET, API_KEY, filePath,fileName);
                // 使用示例
               // String text = "这是一段很长的文本。包含多个句子。每个句子都有不同的主题。我们需要从中提取重要的内容。生成一个简短的摘要。";
                String summary = summaryService.generateSummary(s, 2);
                System.out.println("摘要：" + summary);
               // log.error("输出的语句为；"+text);
                voiceVo.setContent(s);
                voiceVo.setTitle(summary);
            }catch (Exception e ){
                log.error("输出的语句为；"+e.getMessage());
            }

            return AjaxResult.success(voiceVo);


        } catch (Exception e) {
            log.error("上传语音文件失败", e);
            return AjaxResult.error(e.toString());
        }
    }
}