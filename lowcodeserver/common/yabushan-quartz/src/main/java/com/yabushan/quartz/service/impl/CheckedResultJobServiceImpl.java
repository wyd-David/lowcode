package com.yabushan.quartz.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yabushan.activiti.service.ActivitiService;
import com.yabushan.common.events.ICommonHandler;
import com.yabushan.common.utils.spring.SpringUtils;
import com.yabushan.quartz.domain.XunchaCheckedApplyInfoVo;
import com.yabushan.quartz.domain.XunchaCheckedProResultVo;
import com.yabushan.quartz.mapper.XunchaResultMapper;
import com.yabushan.quartz.service.CheckedResultJobService;
import com.yabushan.system.domain.TodoInfo;
import com.yabushan.system.service.ITodoInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Copyright: ...
 * Company: ...
 * Version: 1.0.0
 * Author: ...
 * Date: 2022/7/19
 * Description:
 */
@Service
@Slf4j
public class CheckedResultJobServiceImpl implements CheckedResultJobService {

    @Autowired
    private XunchaResultMapper xunchaResultMapper;
    @Autowired
    private ActivitiService activitiService;
    @Autowired
    private ITodoInfoService todoInfoService;

    public void checkedResult(){

        /*
         * 每分钟启动执行
         * 获取计算历史记录，获取最后同步时间（精确到时分），需要处理最后一次处理时间到当前时间内每分钟相关的记录
         * 获取当前时间相关截止时间的评分人记录
         * 获取所有相关评分人评分记录
         * 根据规则计算以下分数：
         *   1、领导小组评分结果：每个分组相关组长、副组长得分；
         *   2、领导小组评分结果：每个分组相关得分
         *   3、巡查办评分结果：每个分组相关得分
         *   4、被巡查单位结果：每个分组相关得分
         *   5、巡察组当前批次最终得分
         *   6、巡察员评分：每个分组成员个人得分
         * 更新相关评分人记录状态：
         *   1、更新评分关系记录状态；
         *   2、更新申请评分明细记录状态；
         *   3、判断更新申请记录状态
         * 生成优秀优良巡察员明细记录（待申报状态）
         * 回收未评分的评分人待办
         * 记录同步历史（同步时间、同步统计结果、同步处理数据量等）
         * */

        //当前时间点（日期时分）
        String format = "yyyyMMddHHmm";
        String endTime = new SimpleDateFormat(format).format(new Date());
        //获取历史同步成功时间点
        String hisLastTime = "";

        //TODO 清理结果表
        xunchaResultMapper.deleteResult();
//        xunchaResultMapper.deleteFine();

        //小数后两位
        DecimalFormat df = new DecimalFormat("#0.00");

        /*获取相关截止时间点内所有评分人记录(包括申请记录、申请明细记录、考核关系记录、考核分数明细等)*/
        Map<String,Object> params = new HashMap<>();
//        params.put("endTime" , endTime);
//        params.put("startTime" , hisLastTime);
        //需要计算分数相关的申请记录、申请明细记录
        List<Map<String,Object>> needCheckedList = xunchaResultMapper.getNeedCheckedList(params);
        Map<String,Object> detailParam = new HashMap<>();
        if(needCheckedList != null && needCheckedList.size() > 0){
            for(Map<String,Object> needChecked : needCheckedList){
                String applyId = String.valueOf(needChecked.get("applyId"));//申请记录ID
                String detailId = String.valueOf(needChecked.get("detailId"));//申请明细记录ID
                String groupId = String.valueOf(needChecked.get("groupId"));
                String applyType = String.valueOf(needChecked.get("applyType"));
                String fromUserType = String.valueOf(needChecked.get("fromUserType"));

                params.put("groupId" , groupId);
                params.put("applyId" , applyId);
                params.put("detailId", detailId );
                params.put("fromUserType" , fromUserType);
                params.put("applyType" , applyType);

                detailParam = new HashMap<>();
                detailParam.put("detailId",detailId);

                //获取申请记录
                XunchaCheckedApplyInfoVo applyInfo = xunchaResultMapper.getApplyInfoById(applyId);

                if("1".equals(applyType)){//巡察组考核，需要计算巡察组相关领导小组评分、巡察办评分、被考核单位评分结果
                    double groupScope = 0.00;//分组最终得分
                    if("1".equals(fromUserType)){//领导小组评分，需要计算巡察组这一类总分(占总分70%)、每个小组组长、副组长得分
                        //获取每个分组组长、副组长相关分数
                        List<Map<String,Object>> leaderResultList = xunchaResultMapper.getLeaderResultList(params);
                        double leaderFinalResult = 0.00;
                        int leaderCount = 1;
                        if(leaderResultList != null && leaderResultList.size() > 0){
                            leaderCount = leaderResultList.size();
                            for(Map<String,Object> leaderResult : leaderResultList){
                                XunchaCheckedProResultVo result = new XunchaCheckedProResultVo();
                                result.setId(UUID.randomUUID().toString().replace("-",""));
                                result.setScopeType("person");
                                result.setBatchId(applyInfo.getBatchId());
                                result.setYear(applyInfo.getYear());
                                double scope = Double.parseDouble(String.valueOf(leaderResult.get("scope")));
                                leaderFinalResult += scope;
                                result.setScope(scope);
                                result.setGroupId(groupId);
                                result.setToUserId(String.valueOf(leaderResult.get("toUserId")));
                                result.setToUserName(String.valueOf(leaderResult.get("toUserName")));
                                result.setToUserTypeId(String.valueOf(leaderResult.get("userTypeId")));
                                result.setCreatedTime(new Date());
                                result.setUpdatedTime(new Date());
                                XunchaCheckedProResultVo vo = xunchaResultMapper.getByTouserIdAndGroupId(result);
                                if(null==vo) {
                                    xunchaResultMapper.insetResult(result);
                                }else{
                                    result.setId(vo.getId());
                                    xunchaResultMapper.updateResult(result);
                                }
                            }
                        }
                        //更新分组内领导小组最终得分
                        groupScope = Double.parseDouble(df.format((leaderFinalResult/leaderCount) * 70 /100));
                        detailParam.put("groupLeaderScope" , Double.parseDouble(df.format(leaderFinalResult/leaderCount)));
                        detailParam.put("groupLeaderStatus" , "1");
                        detailParam.put("updatedTime" , new Date());
                        xunchaResultMapper.updateDetailById(detailParam);

                    }else if("2".equals(fromUserType) || "3".equals(fromUserType)){//巡察办评分 ，占比 20% ，被巡察单位
                        Map<String,Object> groupResult = xunchaResultMapper.getGroupResult(params);//每个分组只有一个得分
                        double scope = Double.parseDouble(String.valueOf(groupResult.get("scope")));
                        //更新分组内领导小组最终得分
                        if("2".equals(fromUserType)){
                            groupScope = Double.parseDouble(df.format(scope * 20 /100));
                            detailParam.put("cruisingScope" , scope);
                            detailParam.put("cruisingStatus" , "1");
                        }else if ("3".equals(fromUserType)){
                            if( scope == 0){
                                scope = 100.00;
                                groupScope = Double.parseDouble(df.format(10));
                            }else{
                                scope = 0.00;
                            }
                            detailParam.put("toCruisingScope" , scope);
                            detailParam.put("toCruisingStatus" , "1");
                        }
                        detailParam.put("updatedTime" , new Date());
                        xunchaResultMapper.updateDetailById(detailParam);

                    }
                    //计算巡察组最终得分
                    params.put("scopeType","group");
                    XunchaCheckedProResultVo groupResult = xunchaResultMapper.getResult(params);
                    if(groupResult != null){
                        groupResult.setScope(groupResult.getScope() + groupScope);
                        groupResult.setUpdatedTime(new Date());
                        xunchaResultMapper.updateResult(groupResult);
                    }else{
                        groupResult= new XunchaCheckedProResultVo();
                        groupResult.setId(UUID.randomUUID().toString().replace("-",""));
                        groupResult.setScopeType("group");
                        groupResult.setBatchId(applyInfo.getBatchId());
                        groupResult.setYear(applyInfo.getYear());
                        groupResult.setScope(groupScope);
                        groupResult.setGroupId(groupId);
                        groupResult.setToUserId(groupId);
                        groupResult.setCreatedTime(new Date());
                        groupResult.setUpdatedTime(new Date());
                        xunchaResultMapper.insetResult(groupResult);
                    }

                }
//                else{//巡察员考核，1、需要计算每个分组成员(除组长、副组长)评分结果 ； 2、需要根据计算得分判断取出优秀优良巡察员
//
//
//                    List<Map<String,Object>> resultList = xunchaResultMapper.getLeaderResultList(params);
//                    List<Map<String,Object>> fineList = new ArrayList<>();
//
//                    if(resultList != null && resultList.size() > 0){
//                        for(Map<String,Object> leaderResult : resultList){
//                            XunchaCheckedProResultVo result = new XunchaCheckedProResultVo();
//                            result.setId(UUID.randomUUID().toString().replace("-",""));
//                            result.setScopeType("person");
//                            result.setBatchId(applyInfo.getBatchId());
//                            result.setYear(applyInfo.getYear());
//                            double scope = Double.parseDouble(String.valueOf(leaderResult.get("scope")));
//                            if(scope >= 90){
//                                leaderResult.put("fineTypeId" , "1");
//                                leaderResult.put("fineTypeName" , "优秀巡察员");
//                                fineList.add(leaderResult);
//                            }else if(scope >= 80){
//                                leaderResult.put("fineTypeId" , "2");
//                                leaderResult.put("fineTypeName" , "优良巡察员");
//                                fineList.add(leaderResult);
//                            }
//                            result.setScope(scope);
//                            result.setGroupId(groupId);
//                            result.setToUserId(String.valueOf(leaderResult.get("toUserId")));
//                            result.setToUserName(String.valueOf(leaderResult.get("toUserName")));
//                            result.setToUserTypeId(String.valueOf(leaderResult.get("userTypeId")));
//                            result.setCreatedTime(new Date());
//                            result.setUpdatedTime(new Date());
//                            xunchaResultMapper.insetResult(result);
//                        }
//                    }
//                    //更新分组内领导小组最终得分
//                    detailParam.put("groupPerStatus" , "1");
//                    detailParam.put("updatedTime" , new Date());
//                    xunchaResultMapper.updateDetailById(detailParam);
//
//                    //统计优秀优良员工
//                    if(fineList != null && fineList.size() > 0){
//                        for(Map<String,Object> fine : fineList){
//                            XunchaCheckedFinePerDetailVo detail1 = new XunchaCheckedFinePerDetailVo();
//                            detail1.setId(UUID.randomUUID().toString().replace("-",""));
//                            detail1.setApplyId(applyId);
//                            detail1.setFineTypeId(String.valueOf(fine.get("fineTypeId")));
//                            detail1.setFineTypeName(String.valueOf(fine.get("fineTypeName")));
//                            detail1.setUpTypeId("1");
//                            detail1.setUpTypeName("组内评优");
//                            detail1.setUpdatedTime(new Date());
//                            detail1.setCreatedTime(new Date());
//                            detail1.setUserId(String.valueOf(fine.get("toUserId")));
//                            detail1.setGroupId(groupId);
//                            detail1.setFinalResult(Double.parseDouble(String.valueOf(fine.get("scope"))));
//                            detail1.setIfSubmit("0");
//                            detail1.setBatchId(applyInfo.getBatchId());
//                            detail1.setBatchName(applyInfo.getBatchName());
//                            xunchaResultMapper.insetFineDetail(detail1);
//                        }
//                    }
//
//                }
            }
        }
        //回收到时间未评分的代办
        List<Map<String,Object>> list=xunchaResultMapper.selectCanRecycle();
        if(null!=list && list.size()>0){
            String []ids=new String[list.size()];
            String taskId = "";
            String userId="";
            JSONObject object = new JSONObject();
            object.put("message", "系统自动过期");
            object.put("content", "系统自动过期");
            object.put("nextDealUser", "");
            object.put("options","系统自动过期");
            for(int i=0;i<list.size();i++){
                ids[i]=list.get(i).get("id").toString();
                TodoInfo  todoInfo=new TodoInfo();
                todoInfo.setBusinessKey(list.get(i).get("businessKey").toString());
                todoInfo.setTodoType("0");
                todoInfo.setTodoHandler(list.get(i).get("userId").toString());
                List<TodoInfo> toDoList = todoInfoService.getToDoList(todoInfo);
                if (null != toDoList && toDoList.size() > 0) {
                    log.info("回收代办数据："+toDoList.toString());
                    for (TodoInfo info : toDoList) {
                            taskId = info.getTaskId();
                            userId = info.getTodoHandler();
                            activitiService.autoFininshFormTask(taskId, userId, "", object.toJSONString(), "",userId,"系统系统自动过期结束");
                    }
                }
            }
            xunchaResultMapper.updateExamRelationStatus(ids);
        }

        //更新分组状态，判断分组评分是否已完全完成评分（包括巡察组三种类型、巡察员一种类型评分）
        //获取所有类型已经完成评分的分组
        List<String> fileIds = xunchaResultMapper.getFileIds();
        String[] fileIdArr = new String[]{};
        if(fileIds != null && fileIds.size() > 0 ){
            xunchaResultMapper.updateXunchaProcessFileByIds(fileIds.toArray(fileIdArr));

            try{
                ICommonHandler commonHandler=SpringUtils.getBean(ICommonHandler.class);
                commonHandler.handle("ARCHIVE_DISABLE_PROCESS_FILE",fileIds.toArray(fileIdArr));
            }catch (Exception e){
                log.error(e.getMessage(),e);
            }

        }
    }
}
