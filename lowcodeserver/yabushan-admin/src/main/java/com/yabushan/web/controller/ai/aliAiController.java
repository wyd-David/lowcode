package com.yabushan.web.controller.ai;

import com.alibaba.dashscope.aigc.imagesynthesis.ImageSynthesis;
import com.alibaba.dashscope.aigc.imagesynthesis.ImageSynthesisListResult;
import com.alibaba.dashscope.aigc.imagesynthesis.ImageSynthesisParam;
import com.alibaba.dashscope.aigc.imagesynthesis.ImageSynthesisResult;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.task.AsyncTaskListParam;
import com.alibaba.dashscope.utils.JsonUtils;
import com.yabushan.web.controller.tool.AliyunTextToPicTools;
import org.apache.kafka.common.errors.ApiException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/aliAi")
public class aliAiController {

    /**
     * 获取图片
     * @return
     * @throws ApiException
     */
    @PostMapping("/textToPic")
    public String getImage(String prompt,String key,String model,Integer number,Integer width,Integer height) throws ApiException {
        String s = basicCall(prompt, key,model,
                number, width, height);
        List<String> urlFromJson = AliyunTextToPicTools.getUrlFromJson(s);
        return urlFromJson.get(0);
    }

    public static String basicCall(String prompt,String key,String model,Integer number,Integer width,Integer height) throws ApiException {
     //   String prompt = "近景镜头，18岁的中国女孩，古代服饰，圆脸，正面看着镜头，民族优雅的服装，商业摄影，室外，电影级光照，半身特写，精致的淡妆，锐利的边缘。";
        String size  =width+"*" +height;
        ImageSynthesisParam param =
                ImageSynthesisParam.builder()
                        .apiKey(key)
                        .model(model)
                        .prompt(prompt)
                        .style("<watercolor>")
                        .n(number)
                        .size(size)
                        .build();

        ImageSynthesis imageSynthesis = new ImageSynthesis();
        ImageSynthesisResult result = null;
        try {
            System.out.println("---sync call, please wait a moment----");
            result = imageSynthesis.call(param);
        } catch (ApiException | NoApiKeyException e){
            throw new RuntimeException(e.getMessage());
        }
       return JsonUtils.toJson(result);
    }

    public static void listTask() throws ApiException, NoApiKeyException {
        ImageSynthesis is = new ImageSynthesis();
        AsyncTaskListParam param = AsyncTaskListParam.builder().build();
        ImageSynthesisListResult result = is.list(param);
        System.out.println(result);
    }

    public void fetchTask() throws ApiException, NoApiKeyException {
        String taskId = "your task id";
        ImageSynthesis is = new ImageSynthesis();
        // If set DASHSCOPE_API_KEY environment variable, apiKey can null.
        ImageSynthesisResult result = is.fetch(taskId, null);
        System.out.println(result.getOutput());
        System.out.println(result.getUsage());
    }

    public static void main(String[] args){
        try{
            String prompt = "近景镜头，18岁的中国女孩，古代服饰，圆脸，正面看着镜头，民族优雅的服装，商业摄影，室外，电影级光照，半身特写，精致的淡妆，锐利的边缘。";
            String key = "";
            String model = "wanx2.1-t2i-turbo";
            Integer number = 1;
            Integer width = 512;
            Integer height = 1024;
            String s = basicCall(prompt, key, model, number, width, height);
            System.out.println(s);
            //listTask();
        }catch(ApiException e){
            System.out.println(e.getMessage());
        }
    }
}
