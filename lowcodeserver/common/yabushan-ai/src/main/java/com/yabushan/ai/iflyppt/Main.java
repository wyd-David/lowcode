package com.yabushan.ai.iflyppt;

import com.alibaba.fastjson.JSON;

import java.io.File;

/**
 * 智能PPT生成（新版）demo，注意：新版的接口和旧版的不能混用~
 * 正式调用前，请先阅读接口文档中的调用流程图！！！
 * 接口文档地址：https://www.xfyun.cn/doc/spark/PPTv2.html
 * 控制台地址：https://console.xfyun.cn/services/zwapi
 * 产品页地址：https://www.xfyun.cn/services/aippt
 */
public class Main {
    public static final String APPID="";
    public static final String APISecret="";

    public static void main(String[] args) throws Exception {
        // 获取请求头中需要携带的参数 appId（控制台获取）, timestamp（时间戳，单位：秒，与服务端时间相差五分钟之内）, signature（签名）
        long timestamp = System.currentTimeMillis()/1000;
        String ts = String.valueOf(timestamp);

        ApiAuthAlgorithm auth = new ApiAuthAlgorithm();
        String signature = auth.getSignature(APPID, APISecret, timestamp);

        // 建立链接
        ApiClient client = new ApiClient("https://zwapi.xfyun.cn/api/ppt/v2");

    /*    // PPT主题列表查询【所有模板均免费使用】
        String templateResult = client.getTemplateList(APPID, ts, signature);
        System.out.println(templateResult);*/

        // 大纲生成 createOutline
    /*    String outlineQuery = "这是一个大纲生成的测试";
        String outlineResp = client.createOutline(APPID, ts, signature,outlineQuery);
        System.out.println(outlineResp);*/

      /*  //大纲生成 createOutlineByDoc
        File outlineFile = new File("D:\\TEMP\\ppt-java\\src\\main\\resources\\test.txt");//根据文档生成大纲
        String outlineByDoc = client.createOutlineByDoc(APPID, ts, signature, outlineFile);
        System.out.println(outlineByDoc);*/

        //获取大纲生成接口得到的大纲
     /*   CreateResponse outlineResponse = JSON.parseObject(outlineByDoc, CreateResponse.class);
        String outline = outlineResponse.getData().getOutline();
        System.out.println("生成的大纲如下：" + outline);*/
        String outline="{\"outline\":{\"title\":\"大纲生成测试指南\",\"subTitle\":\"掌握高效内容创作技巧\",\"chapters\":[{\"chapterTitle\":\"大纲生成测试\",\"chapterContents\":[{\"chapterTitle\":\"主题要求解读\"},{\"chapterTitle\":\"格式要求说明\"},{\"chapterTitle\":\"示例格式展示\"}]}]}}";
 /*
        //PPT生成 （通过大纲生成PPT）
        String resp1 = client.createPptByOutline(APPID, ts, signature,outline);
        System.out.println(resp1);*/

        //PPT生成 （直接生成PPT）
        File pptFile = new File("D:\\TEMP\\ppt-java\\src\\main\\resources\\test.txt");
        String resp2 = client.create(APPID, ts, signature, pptFile);
        System.out.println(resp2);

        //根据PPT生成接口返回的sid，得到最终PPT的下载链接
        CreateResponse pptResponse = JSON.parseObject(resp2, CreateResponse.class);
        System.out.println("sid="+pptResponse.getData().getSid());
        for(int i =0;i<10;i++){
            try{
                Thread.sleep(3 * 1000);
                String s = client.checkProgress(APPID, ts, signature, pptResponse.getData().getSid());
                ProgressResponse progressResponse = JSON.parseObject(s, ProgressResponse.class);
                if(progressResponse.getCode()==0){
                    String pptUrl = progressResponse.getData().getPptUrl();
                    System.out.println("生成的ppt下载地址为："+pptUrl);

                }
            }catch (Exception e){
                  e.getMessage();
            }
        }
//        String sid="这里填写PPT生成接口返回的sid";
//        String progressResult = client.checkProgress(APPID, ts, signature, sid);
//        System.out.println(progressResult);

    }
}
