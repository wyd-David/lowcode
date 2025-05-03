package com.yabushan.ai.service;


import com.alibaba.fastjson.JSON;
import com.baidu.aip.ocr.AipOcr;
import com.yabushan.ai.domain.AiResultVo;
import com.yabushan.ai.utils.AipOcrUtils;
import com.yabushan.common.utils.file.ZipFileUtils;
import com.yabushan.common.utils.uuid.IdUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 文字识别服务类
 */
@Service
public class WenZiShibieService {

   private static AipOcr client = null;

    @Value("${BAIDU_AI.WZSB.APP_ID}")
   private String APP_ID;
    @Value("${BAIDU_AI.WZSB.API_KEY}")
   private String API_KEY;
    @Value("${BAIDU_AI.WZSB.SECRET_KEY}")
   private String SECRET_KEY;
   private  AipOcr getBaiduInstanceClient(){
       if(client==null){
           client= AipOcrUtils.getBadiAipClient(APP_ID,API_KEY,SECRET_KEY);
       }
       return client;
   }

   
   //
   
   
   
   
   
    /**
     * 识别图片获取文字
     * @param filePath
     * @param unzipFileDirectory
     * @return
     * @throws IOException
     */
   public List<AiResultVo> getMessage(String filePath,String unzipFileDirectory) throws IOException {
       String fileType = filePath.substring(filePath.lastIndexOf(".")+1);
       ArrayList<String> strings = new ArrayList<>();
       if("zip".equals(fileType)){
           //解压文件，获取单个文件
           String s = unzipFileDirectory+"/"+IdUtils.simpleUUID();
           ZipFileUtils.unzip(filePath,s);
           File file = new File(s);
           List<String> strings1 = ZipFileUtils.listFiles(file);
           strings.addAll(strings1);

       }else if("jpg".equals(fileType) || "JPG".equals(fileType) 
               || "png".equals(fileType) || "PNG".equals(fileType)
           || "jpeg".equals(fileType) || "JPEG".equals(fileType)){
           strings.add(filePath);
       }

       List<AiResultVo> list = getList(strings);
       return  list;

   }

    /**
     * 识别图片
     * @param filePathLists
     * @return
     */
   public List<AiResultVo> getList(List<String> filePathLists){
       ArrayList<AiResultVo> aiResultVos = new ArrayList<>();
       for(int i=0;i<filePathLists.size();i++){
           AipOcr baiduInstanceClient = getBaiduInstanceClient();
           JSONObject res = baiduInstanceClient.basicGeneral(filePathLists.get(i), new HashMap<String, String>());
           String s = JSON.toJSONString(res);
           AiResultVo aiResultVo = com.alibaba.fastjson.JSONObject.parseObject(s, AiResultVo.class);
           aiResultVos.add(aiResultVo);
       }
       return  aiResultVos;
   }
   
   
   
    
    
}
