package com.yabushan.quartz.task;

import com.yabushan.ai.service.WxSubscribeMessageService;
import com.yabushan.datasource.service.impl.WeixinServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("weixinFileTask")
@Slf4j
public class WeixinFileTask {




    @Autowired
    private WeixinServiceImpl weixinService;

    @Autowired
    private WxSubscribeMessageService subscribeMessageService;


    /**
     *
     * @param rootPath  读取的磁盘路径
     * @param tableName  写入的表名
     * @param fileColoumName  文件名称  字段
     * @param filePathColoumName  文件存储路径 字段
     * @param filePathColoumName  支持的文件类型
     */
    public void saveFile(String rootPath ,String tableName,String fileColoumName,String filePathColoumName,String fileType){
        weixinService.SaveFile(rootPath,tableName,fileColoumName,filePathColoumName,fileType);
    }



    public void sendTodoInfo(){
        List<Map<String, Object>> sendTodoInfo = weixinService.getSendTodoInfo();
        if(sendTodoInfo.size()>0){
            for (Map map:sendTodoInfo
                 ) {
                String openid = (String) map.get("openid");
                String title = (String) map.get("title");
                String dateTimeStr = (String) map.get("dateTimeStr");
                String content = (String) map.get("content");
                Integer id = (Integer) map.get("id");
                try{
                    //推送短信
                    Map<String, Object> data =new HashMap<>();
                    data.put("thing13", new HashMap<String, String>() {{
                        put("value",title);
                    }});

                    data.put("time14", new HashMap<String, String>() {{
                        put("value",dateTimeStr);
                    }});

                    data.put("thing2", new HashMap<String, String>() {{
                        put("value", content);
                    }});

                    subscribeMessageService.sendSubscribeMessage(openid,data);
                    //更新待办
                    weixinService.updateTodoInfo(id,null);

                    //更新签到次数
                    weixinService.udpateQiandaoCishu(openid);
                }catch (Exception e ){
                    weixinService.updateTodoInfo(id,e.getMessage());
                }

            }
        }
    }




}
