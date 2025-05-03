package com.yabushan.web.controller.ai;

import com.yabushan.ai.domain.AiChartBoot;
import com.yabushan.ai.service.IAiChartBootService;
import com.yabushan.ai.service.YuyifenciService;
import com.yabushan.common.core.domain.model.LoginUser;
import com.yabushan.common.utils.DateUtils;
import com.yabushan.common.utils.spring.SpringUtils;
import com.yabushan.framework.web.service.TokenService;
import com.yabushan.system.service.ISysLogininforService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ServerEndpoint:该注解用于暴漏外部ws的路径，类似@RequestMapping注解。例如服务端口8080，请求地址：ws://localhost:8080/myWs
 * 路径上{userId}  可在onOpen连接成功方法使用@PathParam("userId") String userId接收数据
 */
@ServerEndpoint("/myWs/{userId}")
@Service
public class WebSocketServer {
    //线程安全的map，用来保存每个客户端对应的WebSocket对象
    private static ConcurrentHashMap<String, WebSocketServer> webSocketMap = new ConcurrentHashMap<>();
    //单个客户端的session，通过session与对应客户端通讯
    private Session session;
    //用户id
    private String userId;
  
    @Autowired
    private IAiChartBootService aiChartBootService;
    @Autowired
    private TokenService tokenService;

   
    public void init(){
       
    }
    /**
     * 连接成功
     * @OnOpen注解：websocket 连接成功后，触发该注解修饰的方法
     * @param session
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        
        this.session = session;
        this.userId = userId;
        if (webSocketMap.containsKey(userId)) {
            webSocketMap.remove(userId);
            webSocketMap.put(userId, this);
        } else {
            webSocketMap.put(userId, this);
        }
      //  System.out.println("连接成功");
    }

    /**
     * 连接关闭
     * @OnClose注解：websocket断开连接后，触发该注解修饰的方法
     * @param session
     */
    @OnClose
    public void onCLose(Session session) {
        if (webSocketMap.containsKey(userId)) {
            webSocketMap.remove(userId);
        }
        System.out.println("关闭连接");
    }

    /**
     * 接收消息
     * @OnMessage注解：客户端发送消息时，触发该注解声明的方法
     * @param text
     * @return
     */
    @OnMessage
    public void onMessage(String text) {
        //System.out.println("后端接收前端web发送数据userId:" + userId + ",接收信息：" + text);
        String userName = null;
        aiChartBootService = SpringUtils.getBean("aiChartBootServiceImpl");
        Boolean isProduce=false;
        List<AiChartBoot> aiChartBoots=null;
        if (webSocketMap.containsKey(userId)) {
            try {
                if(!"服务已连接".equals(text)){
                   try{
                       String[] split = text.split("@@@#");
                       YuyifenciService yuyifenciService = new YuyifenciService();
                       List<String> targetMessage = yuyifenciService.getTargetMessage(split[1]);
                       if(split.length<2){
                           webSocketMap.get(userId).session.getBasicRemote().sendText("请选择搜一搜或者记一记");
                       }else{
                           if(userId.contains("_ngs_")){
                               //产品模式
                               userName=userId.split("_ngs_")[0];
                               isProduce=true;
                               
                           }else{
                               //个人模式
                                 //获取当前用户
                               tokenService = SpringUtils.getBean("tokenService");
                               LoginUser loginUser = tokenService.getLoginUser(userId);
                               userName=loginUser.getUsername();
                           }
                           if(targetMessage.size()==0){
                               webSocketMap.get(userId).session.getBasicRemote().sendText("没有找到相关内容");
                               return ;
                           }
                           //获取到标签
                           AiChartBoot aiChartBoot = new AiChartBoot();
                           aiChartBoot=  getAiChartBootLabel(aiChartBoot,targetMessage);
                           aiChartBoot.setCreatedBy(userName);
                           if(split[0].equals("1")){//搜一搜
                               if(isProduce){
                                   //产品
                                    aiChartBoots  = aiChartBootService.selectAiChartBootList2(aiChartBoot);
                               }else{
                                   //个人
                                  aiChartBoots = aiChartBootService.selectAiChartBootList(aiChartBoot);
                                   
                               }
                               Set<String> set =new HashSet<>();
                               if(aiChartBoots.size()>0){
                                   for(int i=0;i<aiChartBoots.size();i++){
                                       set.add(aiChartBoots.get(i).getChartText());
                                   }
                                   String dateTimeNow = DateUtils.dateTimeNow("yyyy/MM/dd HH:mm:ss");
                                   String textStr = "<div>"+dateTimeNow+"</div>为您找到"+set.size()+"条内容:<hr/>\r\n";
                                   StringBuilder sb = new StringBuilder(set.size());
                                   sb.append(textStr);
                                   int jj=1;
                                   for (Iterator<String> iterator = set.iterator(); iterator.hasNext(); ) {
                                       sb.append("<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;").append(jj).append(":").append(iterator.next()).append("\r\n").append("</div>");
                                       jj++;
                                   }
                                   webSocketMap.get(userId).session.getBasicRemote().sendText(sb.toString());
                               }else{
                                   webSocketMap.get(userId).session.getBasicRemote().sendText("没有找到相关内容");
                               }
                           }else if(split[0].equals("2")){
                                //记一记
                               aiChartBoot.setChartText(split[1]);
                               if(isProduce){
                                   aiChartBootService.insertAiChartBoot2(aiChartBoot);
                               }else{
                                   aiChartBootService.insertAiChartBoot(aiChartBoot);
                               }
                                //将用户发送过来的消息进行解析，分析结果后返回
                               if(isProduce){
                                   webSocketMap.get(userId).session.getBasicRemote().sendText("已记录您的宝贵建议,后续可通过关键字搜索哦(●'◡'●),关键字："+targetMessage.toString());
                               }else{
                                   webSocketMap.get(userId).session.getBasicRemote().sendText("已录入到知识库哦(●'◡'●),关键字："+targetMessage.toString());
                               }
                           }
                       }
                   }catch (Exception e){
                       webSocketMap.get(userId).session.getBasicRemote().sendText(e.getMessage());
                   }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

    /**
     * 连接异常
     * @OnError注解：当建立的连接出现异常后，触发该注解修饰的方法
     * @param session
     * @param throwable
     */
    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("websocket连接异常：" + throwable.getMessage());
    }

    /**
     * 服务器给指定WebSocket客户端发送信息
     * @param userId
     * @param message
     */
    public static void sendInfo(String userId, String message) {
        System.out.println("后端发送前端web数据userId:" + userId + "发送消息：" + message);
        if (webSocketMap.containsKey(userId)) {
            try {
                webSocketMap.get(userId).session.getBasicRemote().sendText("后端发送前端web数据userId" + userId + "，内容：" + message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
