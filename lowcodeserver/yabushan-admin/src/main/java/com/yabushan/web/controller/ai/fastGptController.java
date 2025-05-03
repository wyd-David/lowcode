package com.yabushan.web.controller.ai;

import com.yabushan.ai.domain.AiChartBoot;
import com.yabushan.ai.service.IAiChartBootService;
import com.yabushan.ai.service.YuyifenciService;
import com.yabushan.common.config.RuoYiConfig;
import com.yabushan.common.core.domain.AjaxResult;
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.common.utils.file.FileUploadUtils;
import com.yabushan.framework.config.ServerConfig;
import com.yabushan.web.model.vo.ScreenshotRequest;
import com.yabushan.web.utils.Base64MultipartFile;
import com.yabushan.web.utils.UniqueIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@CrossOrigin(origins = "*") // 允许所有来源
@RequestMapping("/fastGpt")
public class fastGptController {

    @Autowired
    private ServerConfig serverConfig;

    @Autowired
    private IAiChartBootService aiChartBootService;
    @PostMapping("/upload")
    public AjaxResult uploadScreenshot(@RequestBody ScreenshotRequest request) {
        AjaxResult ajax = com.yabushan.common.core.domain.AjaxResult.success();

        try {
            // 解码 Base64 图像数据
            String base64String = request.getImage().split(",")[1]; // 去掉 "data:image/png;base64," 前缀

            String fileNames = UniqueIdGenerator.generateUniqueId()+".jpg";
            String contentType = "image/jpeg";
            MultipartFile multipartFile = Base64MultipartFile.base64ToMultipartFile(base64String, fileNames, contentType);

            // 上传文件路径
            String filePath = RuoYiConfig.getUploadPath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, multipartFile);
            String url = serverConfig.getUrl() + fileName;
            ajax.put("url", url);
            return ajax;
        } catch (Exception e) {
            e.printStackTrace();
             ajax.put("error", "Error uploading screenshot: " + e.getMessage());
            return ajax;
        }
    }

    //保存为知识点
    @PostMapping("/saveKnowledge")
    public AjaxResult saveKnowledgePoint(@RequestBody ScreenshotRequest request) {
        AjaxResult ajax = com.yabushan.common.core.domain.AjaxResult.success();

        if(request.getImage().trim().isEmpty()){
            return  AjaxResult.error("内容为空");
        }

        YuyifenciService yuyifenciService = new YuyifenciService();
        List<String> targetMessage = yuyifenciService.getTargetMessage(request.getImage());

        //获取到标签
        AiChartBoot aiChartBoot = new AiChartBoot();
        aiChartBoot=  getAiChartBootLabel(aiChartBoot,targetMessage);
        aiChartBoot.setCreatedBy(SecurityUtils.getUsername());
        aiChartBoot.setChartText(request.getImage());
        //保存
        aiChartBootService.insertAiChartBoot(aiChartBoot);


        return  AjaxResult.success(targetMessage.toString());


    }

    private AiChartBoot getAiChartBootLabel(AiChartBoot aiChartBoot,List<String> labelList){

        for(int i=0;i<labelList.size();i++){
            if(i==0){
                aiChartBoot.setLable1(labelList.get(i));
            }else if(i==1){
                aiChartBoot.setLabel2(labelList.get(i));
            }else if(i==2){
                aiChartBoot.setLabel3(labelList.get(i));
            }else if(i==3){
                aiChartBoot.setLabel4(labelList.get(i));
            }else if(i==4){
                aiChartBoot.setLabel5(labelList.get(i));
            }else if(i==5){
                aiChartBoot.setLabel6(labelList.get(i));
            }

        }


        return aiChartBoot;
    }



    @GetMapping("/queryKnowledge")
    public AjaxResult queryKnowledge(@RequestParam String query) {
        // 处理查询逻辑
        YuyifenciService yuyifenciService = new YuyifenciService();
        List<String> targetMessage = yuyifenciService.getTargetMessage(query);
        //获取到标签
        AiChartBoot aiChartBoot = new AiChartBoot();
        aiChartBoot=  getAiChartBootLabel(aiChartBoot,targetMessage);
        aiChartBoot.setCreatedBy(SecurityUtils.getUsername());
        List<AiChartBoot> aiChartBoots=null;
        aiChartBoots = aiChartBootService.selectAiChartBootList(aiChartBoot);
        if(aiChartBoots.size()>0){
            StringBuilder sb = new StringBuilder(aiChartBoots.size());

            for(int i=0;i<aiChartBoots.size();i++){
                int ii =i+1;
                sb.append("第"+ii+"条结果:"+aiChartBoots.get(i).getChartText()+"\r\n");
            }
            return  AjaxResult.success(sb.toString());

        }else {
            return AjaxResult.success("查询结果为空");
        }




    }
}
