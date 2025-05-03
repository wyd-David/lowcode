package com.yabushan.ai.utils;

import com.alibaba.fastjson.JSON;
import com.baidu.aip.ocr.AipOcr;
import com.yabushan.ai.domain.AiResultVo;
import com.yabushan.ai.domain.Aiwords;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Slf4j
public class AipOcrUtils {
    //设置APPID/AK/SK
    
    public static AipOcr  getBadiAipClient(String APP_ID,String API_KEY,String SECRET_KEY){
        // 初始化一个AipOcr
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        return client;
        
    }

    /**
     * 根据入参获取结果
     * @param param
     * @param aiResultVo
     */
    public static Map getFinallyData(String param, AiResultVo aiResultVo){
        //最终返回结果
        HashMap<String , String> resultMap = new HashMap<>();
        //记录存在重复的情况
        List<Integer> integers = new ArrayList<>();
        //临时结果
       // ArrayList<String> tempList = new ArrayList<>();
        String[] split = param.split(",");
        List<Aiwords> words_result = aiResultVo.getWords_result();
       // System.out.println(words_result.toString());
  
        for(int i=0;i<split.length;i++){
            String finallyValue="无";
            String key = split[i];
            for(int j=0;j<words_result.size();j++){
                String  Value= words_result.get(j).getWords();
                if(Value.equals(key)){
                   // tempList.add(words_result.get(j+1).getWords());
                    finallyValue=words_result.get(j+1).getWords();
                   // resultMap.put(key,finallyValue);
                    break;
                }else  if(Value.contains(key)){
                    if(Value.startsWith(key)){
                        //如果是以key开头
                        String str = Value.replace(key,"");
                        if(i==split.length-1){
                           // tempList.add(str);
                            finallyValue=str;
                        }else{
                            String[] split1 = str.split(split[i+1]);
                          //  tempList.add(split1[0]);
                            finallyValue=split1[0];
                        }
                    }else{
                        //如果不是以key开头，则上一个
                        integers.add(i);
                        String[] split1 = Value.split(key);
                       // tempList.add(split1[1]);
                        finallyValue=split1[1];
                    }
                  //  resultMap.put(key,finallyValue);
                    break;
                }
            }
            resultMap.put(key,finallyValue);
        }

        resultMap.put("notes",JSON.toJSONString(words_result));
        
      return resultMap;
    }

    //保存文件
    public static final String getAbsoluteFile(String uploadDir, String fileName) throws IOException
    {
        File desc = new File(uploadDir + File.separator + fileName);

        if (!desc.getParentFile().exists())
        {
            desc.getParentFile().mkdirs();
        }
        if (desc.exists())
        {
            desc.delete();
        }
        return desc.getAbsolutePath();
    }

    public static void deleteDirectory(String directory) {
        // 指定要删除的目录路径  
        Path directoryPath = Paths.get(directory);
        try {
            Files.walk(directoryPath).forEach(path -> {
                try {
                    Files.delete(path);
                } catch (IOException e) {
                   // e.printStackTrace();
                    log.error(e.getMessage());
                }
            });
        } catch (IOException e) {
          //  e.printStackTrace();
            log.error(e.getMessage());
        }
    }
}
