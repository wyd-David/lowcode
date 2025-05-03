package com.yabushan.quartz.task;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import com.yabushan.activiti.service.ActivitiService;
import com.yabushan.common.constant.Constants;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.framework.manager.AsyncManager;
import com.yabushan.framework.manager.factory.AsyncFactory;
import com.yabushan.system.domain.PushProToreadRead;
import com.yabushan.system.domain.TodoInfoPush;
import com.yabushan.system.domain.vo.PushProTodoDoneNew;
import com.yabushan.system.domain.vo.PushProTodoDoneVo;
import com.yabushan.system.domain.vo.PushProToreadReadNew;
import com.yabushan.system.domain.vo.TodoInfoParam;
import com.yabushan.system.enums.BusinessType;
import com.yabushan.system.service.IPushProTodoDoneService;
import com.yabushan.system.service.IPushProToreadReadService;
import com.yabushan.system.service.ITodoInfoPushService;
import com.yabushan.system.utils.PushDataToPortal;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
//@RestController
//@RequestMapping("/weixin/test/todo/")

/**
 * 推送待办待阅消息到portal
 */
@Slf4j
@Component("pushTodoInfoPortalTask")
public class PushTodoInfoPortalTask {
    /**
     * 推送待办待阅到portal待办
     */
    @Autowired
    private ITodoInfoPushService todoInfoPushService;

    @Autowired
    private PushDataToPortal pushDataToPortal;

   /* @Autowired
    private ActivitiService activitiService;*/

//    //测试生成待办
//    @GetMapping("/test1")
//    public void test(String todoId){
//        if(todoId==null){
//            //测试待办待阅
//            TodoInfoParam todoInfoParam = new TodoInfoParam();
//
//            activitiService.UserInsertOneTodoInfo(todoInfoParam);
//            todoInfoParam.setTaskId(StringUtils.getUUID());
//            activitiService.UserInsertOneToReadInfo(todoInfoParam);
//        }else{
//            //测试已办已阅
//            activitiService.UserUpdateOneToReadToDone(todoId);
//            activitiService.UserUpdateOneTodoToDone(todoId);
//
//
//        }
//
//
//
//    }



   // @GetMapping("/push")
    public void PushTodoInfo(){

        //获取待办
        List<PushProTodoDoneNew> todoInfoToPush = todoInfoPushService.getTodoInfoToPush("1");
        toPushTodo(todoInfoToPush,BusinessType.TODO.getVal());


        //获取已办
        List<PushProTodoDoneNew> doneInfoToPush = todoInfoPushService.getTodoInfoToPush("2");
        toPushTodo(doneInfoToPush,BusinessType.DONE.getVal());

        //获取待阅
        List<PushProToreadReadNew> readInfoToPush = todoInfoPushService.getToReadInfoToPush("3");
        toPushRead(readInfoToPush,BusinessType.TOREAD.getVal());

        //获取已阅
        List<PushProToreadReadNew> hasReadInfoToPush = todoInfoPushService.getToReadInfoToPush("4");
        toPushRead(hasReadInfoToPush,BusinessType.READ.getVal());

    }

    /**
     * 推送待办已办
     * @param datas
     * @param BusinessType
     */
    public void toPushTodo( List<PushProTodoDoneNew>  datas,String BusinessType){
        String jsonStr = null;
        PushProTodoDoneVo pushProTodoDoneVo=null;
        if(datas.size()>0){
            for (int i=0;i<datas.size();i++) {
                TodoInfoPush todoInfoPush= todoInfoPushService.selectTodoInfoPushById(datas.get(i).getId());
                try{
                    pushProTodoDoneVo  = new PushProTodoDoneVo();
                    BeanUtil.copyProperties(datas.get(i), pushProTodoDoneVo);
                    jsonStr = pushDataToPortal.push(BusinessType, JSONUtil.toJsonStr(Lists.newArrayList(pushProTodoDoneVo)));
                    if(StringUtils.isNotEmpty(jsonStr)) {
                        com.alibaba.fastjson.JSONObject jsonObject = JSONUtil.toBean(jsonStr, com.alibaba.fastjson.JSONObject.class);
                        String code = jsonObject.getString("code");
                        if("000".equals(code)) {
                            //成功
                            log.error("推送待办已办成功:设置为已推送");
                            //修改推送数据的 pushStatus 改为PushStatus.PUSHED
                            todoInfoPush.setPushStatus("2");//设置为已推送
                        }else{
                            log.error("推送待办已办失败:设置为推送失败");
                            todoInfoPush.setPushStatus("5");//设置为推送失败
                        }
                        String message = jsonObject.getString("message");
                        //AsyncManager.me().execute(AsyncFactory.recordLogininfor("toPushTodo>>>"+todoInfoPush.getTodoId(), Constants.LOGIN_SUCCESS, "推送待办失败"+message));
                        todoInfoPushService.updateTodoInfoPush(todoInfoPush);
                        log.error("待办已办数据更新成功");
                    }
                }catch (Exception e){
                    log.error("推送待办已办失败异常:设置为推送失败"+e.toString());
                    todoInfoPush.setPushStatus("5");//设置为推送失败
                    //AsyncManager.me().execute(AsyncFactory.recordLogininfor("toPushTodo>>>"+todoInfoPush.getTodoId(), Constants.LOGIN_SUCCESS, "推送待办失败"+e.toString()));
                    todoInfoPushService.updateTodoInfoPush(todoInfoPush);

                }
            }
        }


    }
    /**
     * 推送待阅已阅
     * @param datas
     * @param BusinessType
     */
    public void toPushRead(List<PushProToreadReadNew>  datas, String BusinessType){
        String jsonStr = null;
        PushProToreadReadNew pushProToreadReadNew=null;
        if(datas.size()>0){
            for (int i=0;i<datas.size();i++) {
                TodoInfoPush todoInfoPush= todoInfoPushService.selectTodoInfoPushById(datas.get(i).getId());
                try{
                    pushProToreadReadNew  = new PushProToreadReadNew();
                    BeanUtil.copyProperties(datas.get(i), pushProToreadReadNew);
                    jsonStr = pushDataToPortal.push(BusinessType, JSONUtil.toJsonStr(Lists.newArrayList(pushProToreadReadNew)));
                    if(StringUtils.isNotEmpty(jsonStr)) {
                        com.alibaba.fastjson.JSONObject jsonObject = JSONUtil.toBean(jsonStr, com.alibaba.fastjson.JSONObject.class);
                        String code = jsonObject.getString("code");
                        if("000".equals(code)) {
                            //成功
                            //修改推送数据的 pushStatus 改为PushStatus.PUSHED
                            log.error("推送待阅已阅成功:设置为已推送");
                            todoInfoPush.setPushStatus("2");//设置为已推送
                        }else{
                            log.error("推送待阅已阅失败:设置为推送失败");
                            todoInfoPush.setPushStatus("5");//设置为推送失败
                        }
                        String message = jsonObject.getString("message");
                        //AsyncManager.me().execute(AsyncFactory.recordLogininfor("toPushRead>>>"+todoInfoPush.getTodoId(), Constants.LOGIN_SUCCESS, "推送待办失败"+message));
                        todoInfoPushService.updateTodoInfoPush(todoInfoPush);
                        log.error("更新待阅已阅数据状态");
                    }
                }catch (Exception e){
                    log.error("推送待阅已阅失败异常:设置为推送失败");
                    todoInfoPush.setPushStatus("5");//设置为推送失败
                    //AsyncManager.me().execute(AsyncFactory.recordLogininfor("toPushRead>>>"+todoInfoPush.getTodoId(), Constants.LOGIN_SUCCESS, "推送待办失败"+e.toString()));
                    todoInfoPushService.updateTodoInfoPush(todoInfoPush);

                }
            }
        }

    }


}
