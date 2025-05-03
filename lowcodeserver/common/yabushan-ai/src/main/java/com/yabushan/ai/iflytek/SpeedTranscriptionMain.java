package com.yabushan.ai.iflytek;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.yabushan.ai.iflytek.request.*;
import com.yabushan.ai.iflytek.util.*;
import okhttp3.OkHttpClient;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class SpeedTranscriptionMain {
    // 控制台获取以下信息


    private static String FILE_URL_PREFIX = "https://upload-ost-api.xfyun.cn/file"; // 上传文件的地址开头

    private static String OPEN_URL_PREFIX = "https://ost-api.xfyun.cn/v2"; // 创建、查询任务的地址开头
    private static OkHttpClient client = new OkHttpClient.Builder().build();
    private static final Gson gson=new Gson();
   // private static final String AUDIO_PATH="audioExample/audio_sample_little.wav"; // 在此指定音频文件路径
    private static final int SLICE_SIZE = 15728640;// 15M，每块范围 5M~32M




    public static String voiceToStr(String APP_ID,String API_SECRET,String API_KEY,String AUDIO_PATH,String fileName) throws InterruptedException, IOException {
        StringBuffer buffer = new StringBuffer();
        File audio = new File(AUDIO_PATH); // 需极速转写的音频文件
        FileInputStream fis = new FileInputStream(audio);
        FileCaller fileCaller = FileCaller.builder().apiKey(API_KEY).apiSecret(API_SECRET).client(client).ulrPrefix(FILE_URL_PREFIX).build();
        JSONObject jsonObjectUploadRespOrCompleteResp;
        /** 1. 上传音频文件 */
        if(audio.length()<31457280){
            /** 1.1 如果是小文件走单个上传接口，建议不大于30M */
            FileResp<FileResp.UploadData> uploadResp = fileCaller.fileUpload(FileReq.Upload.builder().
                    appId(APP_ID).
                    fileName(fileName).
                    requestId("2021121315202311").
                    data(IOUtils.toByteArray(fis))
                    .build());
            System.out.println("小文件上传返回的信息："+uploadResp);
            jsonObjectUploadRespOrCompleteResp=JSON.parseObject(JSON.toJSONString(uploadResp.getData()));
        }else{ // 如果是大文件走分块上传接口
            /** 1.2 如果是大文件走分块上传接口*/
            /** 1.2.1 初始化分块信息 */
            FileResp<FileResp.InitData> initResp = fileCaller.fileInit(FileReq.Init.builder().
                    requestId("20211213155434").
                    appId(APP_ID)
                    .build());
            JSONObject jsonObjectInitResp=JSON.parseObject(JSON.toJSONString(initResp.getData()));
            System.out.println("初始化分块信息-返回的upload_id："+jsonObjectInitResp.get("upload_id"));
            /** 1.2.2 分块上传 */
            // 分片上传文件
            int len = 0;
            byte[] slice = new byte[SLICE_SIZE];
            int sliceIdIndex=1;
            while ((len = fis.read(slice)) > 0) {
                // 上传分片
                if (fis.available() == 0) {
                    slice = Arrays.copyOfRange(slice, 0, len);
                }
                FileResp<Void> partUploadResp = fileCaller.filePartUpload(FileReq.PartUpload.builder().
                        requestId("20211213152023").
                        appId(APP_ID).
                        uploadId(jsonObjectInitResp.get("upload_id").toString()). // 使用初始化分块信息返回的upload_id
                                sliceId(sliceIdIndex).
                        data(slice).
                        build());
                System.out.println("第"+sliceIdIndex+"块分块上传-返回的信息："+partUploadResp);
                sliceIdIndex++;
            }
            /** 1.2.3 分块上传完成 */
            FileResp<Void> completeResp = fileCaller.fileUploadComplete(FileReq.Complete.builder().
                    appId(APP_ID).requestId("2021164834").
                    uploadId(jsonObjectInitResp.get("upload_id").toString()). // 使用初始化分块信息返回的upload_id
                            build());
            System.out.println("分块上传完成-返回的信息："+completeResp);
            jsonObjectUploadRespOrCompleteResp=JSON.parseObject(JSON.toJSONString(completeResp.getData()));
        }

        /** 3. 创建任务 */
        OpenCaller openCaller = OpenCaller.builder().apiKey(API_KEY).apiSecret(API_SECRET).
                client(client).ulrPrefix(OPEN_URL_PREFIX).build();
        String requestId="20211213173212"; // 可以自定义
        OpenResp createResp = openCaller.create(OpenReq.Create.builder().
                common(OpenReq.Common.builder().appId(APP_ID).build()).
                business(OpenReq.Business.builder().
                        requestId(requestId).
                        // callbackUrl("http://IP:端口号/xxx").
                                accent("mandarin").
                        language("zh_cn").
                        domain("pro_ost_ed").
                        build()).
                data(OpenReq.Data.builder().audioUrl(jsonObjectUploadRespOrCompleteResp.get("url").toString()). // 上传文件或分块上传完成返回的url
                        encoding("raw").format("audio/L16;rate=16000").audioSrc("http").build()).
                build());
        System.out.println("创建任务-返回的信息："+createResp);
        JSONObject jsonObjectCreateResp=JSON.parseObject(JSON.toJSONString(createResp.getData()));

        /** 4. 查询任务 */
        OpenResp queryResp = openCaller.query(OpenReq.Query.builder().
                common(OpenReq.Common.builder().appId(APP_ID).build()).
                business(OpenReq.QueryBusiness.builder().taskId(jsonObjectCreateResp.get("task_id").toString()).build()). // 创建任务返回的task_id
                        build());
        System.out.println("查询任务-返回的信息："+queryResp);
        JSONObject jsonObjectQueryResp=JSON.parseObject(JSON.toJSONString(queryResp.getData()));
        while(true){
            if(jsonObjectQueryResp.get("task_status").equals("5")){
                System.out.println("极速转写-最终结果==>：任务已取消..."); // 控制台打印取消信息
                break; // 跳出循环
            }else if(jsonObjectQueryResp.get("task_status").equals("3")||jsonObjectQueryResp.get("task_status").equals("4")){
                // System.out.println("极速转写-最终结果==>：\n"+queryResp); // 控制台打印最终转写结果
                System.out.println("极速转写-最终结果（更多字段请参考queryResp进行解析）==>：");
                JsonParse jsonParse=gson.fromJson(jsonObjectQueryResp.toJSONString(),JsonParse.class);
                List<Lattice> latticeList=jsonParse.result.lattice;
                for(int i=0;i<latticeList.size();i++){
                    Lattice tempLattice=latticeList.get(i);
                    // String rl=tempLattice.json_1best.st.rl;
                    List<Rt> rtList=tempLattice.json_1best.st.rt;
                    for(int j=0;j<rtList.size();j++){
                        Rt tempRt=rtList.get(j);
                        List<Ws> wsList=tempRt.ws;
                        for(int k=0;k<wsList.size();k++){
                            Ws tempWs=wsList.get(k);
                            List<Cw> cwList=tempWs.cw;
                            for(int l=0;l<cwList.size();l++){
                                Cw tempCw=cwList.get(l);
                                buffer.append(tempCw.w);
                               // System.out.print(tempCw.w);
                            }
                        }
                    }
                }
                break; // 跳出循环
            }else{
                Thread.sleep(2000); // 两秒查询一次
                // 再次查询任务
                queryResp = openCaller.query(OpenReq.Query.builder().
                        common(OpenReq.Common.builder().appId(APP_ID).build()).
                        business(OpenReq.QueryBusiness.builder().taskId(jsonObjectCreateResp.get("task_id").toString()).build()). // 创建任务返回的task_id
                                build());
                jsonObjectQueryResp=JSON.parseObject(JSON.toJSONString(queryResp.getData()));
                System.out.println("极速转写-转写中...");
            }
        }

        return buffer.toString();


    }

    public static void main(String[] args) throws IOException, InterruptedException {
        /**
         *         path: D:/project/ngs/uploadfile/ai_voice/
         *         APP_ID: "63771e26"
         *         API_SECRET: ZGNkYmRhYjIwZGU0Y2VjMjJlNThkOWEy
         *         API_KEY: 65b068091f6431bc74aa149c248f6c9b
         */
            String APP_ID = "63771e26";
            String API_SECRET = "ZGNkYmRhYjIwZGU0Y2VjMjJlNThkOWEy";
            String API_KEY = "65b068091f6431bc74aa149c248f6c9b";
            String AUDIO_PATH="D:\\TEMP\\kedaxufei\\raasr_demo\\src\\main\\resources\\audio\\合成音频.wav";
        String s = voiceToStr(APP_ID, API_SECRET, API_KEY, AUDIO_PATH,"合成音频.wav");

        System.out.println(s);

    }
    /** 解析极速转写结果 */
    public class JsonParse{
        String task_id;
        String task_status;
        String task_type;
        String force_refresh;
        Result result;
    }
    class Result{
        List<Lattice> lattice;
    }
    class Lattice{
        Json_1best json_1best;
    }
    class Json_1best{
        St st;
    }
    class St{
        List<Rt> rt;
        String rl;
    }
    class Rt{
        List<Ws> ws;
    }
    class Ws{
        List<Cw> cw;
    }
    class Cw{
        String w;
    }
}
