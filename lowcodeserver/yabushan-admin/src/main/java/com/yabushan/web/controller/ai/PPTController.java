package com.yabushan.web.controller.ai;

import com.alibaba.fastjson.JSON;
import com.yabushan.ai.iflyppt.ApiAuthAlgorithm;
import com.yabushan.ai.iflyppt.ApiClient;
import com.yabushan.ai.iflyppt.CreateResponse;
import com.yabushan.ai.iflyppt.ProgressResponse;
import com.yabushan.common.utils.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/ifly")
public class PPTController {

    public static final String APPID="";
    public static final String APISecret="";
    @PostMapping("/createPPT")
    public String getPPTUrl(@RequestParam  String query ,
                            @RequestParam String outline) throws IOException {
        long timestamp = System.currentTimeMillis()/1000;
        String ts = String.valueOf(timestamp);

        ApiAuthAlgorithm auth = new ApiAuthAlgorithm();
        String signature = auth.getSignature(APPID, APISecret, timestamp);

        // 建立链接
        ApiClient client = new ApiClient("https://zwapi.xfyun.cn/api/ppt/v2");
        //PPT生成 （通过大纲生成PPT）
        String resp1 = client.createPptByOutline(APPID, ts, signature,outline,query);
        //根据PPT生成接口返回的sid，得到最终PPT的下载链接
        CreateResponse pptResponse = JSON.parseObject(resp1, CreateResponse.class);
        String sid = pptResponse.getData().getSid();

        for(int i =0;i<10;i++){
            try{
                Thread.sleep(3 * 1000);
                long timestamp2 = System.currentTimeMillis()/1000;
                String ts1 = String.valueOf(timestamp2);
                String s = client.checkProgress(APPID, ts1, signature, sid);
                ProgressResponse progressResponse = JSON.parseObject(s, ProgressResponse.class);
                if(progressResponse.getCode()==0){
                    String pptUrl = progressResponse.getData().getPptUrl();
                    System.out.println("生成的ppt下载地址为："+pptUrl);
                    return  pptUrl;
                }
            }catch (Exception e){
                    return  e.getMessage();
            }
        }

        return null;

    }

    @PostMapping("/create")
    public String getPPTUrlByText(@RequestParam  String filePath) throws IOException {
        long timestamp = System.currentTimeMillis()/1000;
        String ts = String.valueOf(timestamp);

        if(StringUtils.isEmpty(filePath)){
            filePath="D:\\TEMP\\ppt-java\\src\\main\\resources\\test.txt";
        }

        ApiAuthAlgorithm auth = new ApiAuthAlgorithm();
        String signature = auth.getSignature(APPID, APISecret, timestamp);

        // 建立链接
        ApiClient client = new ApiClient("https://zwapi.xfyun.cn/api/ppt/v2");
        //PPT生成 （通过大纲生成PPT）
        File pptFile = new File(filePath);
        String resp1 = client.create(APPID, ts, signature, pptFile);
        //根据PPT生成接口返回的sid，得到最终PPT的下载链接
        CreateResponse pptResponse = JSON.parseObject(resp1, CreateResponse.class);
        String sid = pptResponse.getData().getSid();

        for(int i =0;i<20;i++){
            try{
                Thread.sleep(3 * 1000);
                String s = client.checkProgress(APPID, ts, signature, sid);
                ProgressResponse progressResponse = JSON.parseObject(s, ProgressResponse.class);
                if(progressResponse.getCode()==0 && StringUtils.isNotEmpty(progressResponse.getData().getPptUrl())){
                    String pptUrl = progressResponse.getData().getPptUrl();
                    System.out.println("生成的ppt下载地址为："+pptUrl);
                    return  pptUrl;
                }
            }catch (Exception e){
                return  e.getMessage();
            }
        }

        return null;

    }

}
