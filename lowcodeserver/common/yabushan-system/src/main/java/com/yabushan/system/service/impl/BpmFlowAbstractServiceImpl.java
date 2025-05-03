package com.yabushan.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yabushan.common.bpm.model.*;
import com.yabushan.common.bpm.service.DocumentService;
import com.yabushan.common.bpm.service.FlowService;
import com.yabushan.common.bpm.utils.ToolUtil;
import com.yabushan.common.exception.CommonException;
import com.yabushan.common.utils.DateUtils;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.system.domain.SysFlowMain;
import com.yabushan.system.mapper.SysFlowMainMapper;
import com.yabushan.system.service.BpmFlowService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public abstract class BpmFlowAbstractServiceImpl<T> implements BpmFlowService<T> {
    private final Logger log = LogManager.getLogger(getClass());
    public static String flowContentF = "%s，请各位领导、同事审阅批示，谢谢！";
    @Autowired
    public HttpServletRequest request;
    @Autowired
    public FlowService flowService;
    @Autowired
    public DocumentService documentServices;
    @Autowired
    private PlatformTransactionManager platformTransactionManager;//手动的事物

    public Map<Integer, Integer> currentStatusMap = new HashMap<Integer, Integer>();

    @Autowired
    public SysFlowMainMapper sysFlowMainMapper;

    @Override
    public PieFlowTDto<T> initParams(Object o, PieFlowTDto<T> dto, String actionType)  throws CommonException {
        //获取状态和节点对应的值
        DocParams docParams = dto.getDocParams();
        if (docParams == null) {
            docParams = new DocParams();
        }
        FlowParams flowParams = dto.getFlowParams();
        if (flowParams == null) {
            flowParams = new FlowParams();
        }
        String userId = "";
        String taskId = "";
        String title = "";
        String nextActivityId = "";
        String nextUserInfo = "";
        String approveResult = "";
        String documentId = "";
        String approveNote = "";
        String targetUser = "";
        String nextSpecialUserInfo = "";
        String flowContent = "";
        //传阅
        String toUserIds = "";
        String messageTitle = "";
        String message = "";
        if (o instanceof HttpServletRequest) {
            HttpServletRequest request = (HttpServletRequest) o;
            //用于自发流程
            String applyUser = request.getParameter("applyUser");
            userId = "wangshuo";
            taskId = request.getParameter("taskId");
            title = request.getParameter("title");
            nextActivityId = request.getParameter("nextActivityId");
            nextUserInfo = request.getParameter("nextUserInfo");
            approveResult = request.getParameter("approveResult");
            documentId = request.getParameter("documentId");
            approveNote = request.getParameter("approveNote");
            targetUser = request.getParameter("targetUser");
            nextSpecialUserInfo = request.getParameter("nextSpecialUserInfo");
            flowContent = request.getParameter("flowContent");
            //传阅
            toUserIds = request.getParameter("toUserIds");
            messageTitle = request.getParameter("messageTitle");
            message = request.getParameter("message");
        } else if (o instanceof ParamsFlowInitDto) {
            ParamsFlowInitDto paramO = (ParamsFlowInitDto) o;
            userId = paramO.getCurrentUser();
            taskId = paramO.getTaskId();
            title = paramO.getTitle();
            nextActivityId = paramO.getNextActivityId();
            nextUserInfo = paramO.getNextUserInfo();
            approveResult = paramO.getApproveResult();
            approveNote = paramO.getApproveNote();
            targetUser = paramO.getTargetUser();
            nextSpecialUserInfo = paramO.getNextSpecialUserInfo();
            flowContent = paramO.getFlowContent();
            //传阅
            toUserIds = paramO.getToUserIds();
            messageTitle = paramO.getMessageTitle();
            message = paramO.getMessage();
        }
        //下一步处理人
        NextUser nextUser = new NextUser();
        nextUser.setUsers(userId);
        docParams.setService(getFlowCode());
        if (StringUtils.isBlank(title)) {
            title = dto.getTitle();
        } else {
            dto.setTitle(title);
        }
        docParams.setTitle(title);
        docParams.setUser(userId);
        //传阅
        if (SHARE.equalsIgnoreCase(actionType)) {
            docParams.setTitle(messageTitle);
            docParams.setMessage(message);
            docParams.setFromUserId(userId);
            docParams.setToUserIds(toUserIds);
        }
        flowParams.setCurrentUser(userId);//
        flowParams.setUser(userId);//撤销的时候
        flowParams.setTaskId(taskId);
       // flowParams.setNextActivityId(nextActivityId);
        flowParams.setApproveNote(approveNote);
        flowParams.setApproveResult(approveResult);
        flowParams.setTargetUser(targetUser);

        if (StringUtils.isBlank(dto.getDocId()) && StringUtils.isNotBlank(documentId)) {
            dto.setDocId(documentId);
        }
        docParams.setDocumentId(dto.getDocId());
        flowParams.setDocumentId(dto.getDocId());
        if (StringUtils.isNotBlank(dto.getDocId())) {//判断是否有docID
            this.dealWtidhInitOpenBPM(dto, docParams, flowParams);
        }
        //有DOCID的时候dealWtidhInitOpenBPM 会赋值
        PieDocument pieDocument = dto.getPieDocument();
        // this.getCurrentStatusMap(pieDocument != null ? pieDocument.getCreatedBy() : null, ToolUtil.getAmopFlowNodeModelByCode("AMOP_001",detailCode));
        String keyId = flowParams.getCurrentActivityId();//用于非抢办的设置
        Integer keyI = null;
        if (StringUtils.isNotBlank(keyId)) {//设置当前状态的节点但是一般是设置下一步处理节点的
            keyI = Integer.valueOf(keyId);
            dto.setCurrentStatusI(currentStatusMap.get(keyI));
        }
        List<BpmSpecialUserInfo> jsonArraySpecial = null;
        Integer keyNI = null;
        if (StringUtils.isNotBlank(flowParams.getNextActivityId())) {
            try {
                keyNI = currentStatusMap.get(Integer.parseInt(flowParams.getNextActivityId()));
            } catch (NumberFormatException e) {
                log.error("获取下一步操作节点异常：" + e.getLocalizedMessage());
            }
        }
        jsonArraySpecial = dealUserInfo(nextUser, nextSpecialUserInfo, keyI, keyNI);//合并特殊处理人
        String str = nextUser.getUsers();
        if (!StringUtils.isNotBlank(str)) {
            nextUser.setUsers(null);
        }
        dto.setListBpmSpecialUserInfos(jsonArraySpecial);
        if (StringUtils.isBlank(flowParams.getNextUserInfo())) {
            dto.setNextUser(nextUser);
            flowParams.setNextUserInfo(JSONObject.toJSONString(nextUser));
        } else {
            String d = flowParams.getNextUserInfo();
            dto.setNextUser(JSON.parseObject(d, NextUser.class));
        }
        if (StringUtils.isBlank(flowContent)) {
            dto.setFlowContent(String.format(flowContentF, title));
        } else {
            dto.setFlowContent(flowContent);
        }
        dto.setDocParams(docParams);
        if ("99".equalsIgnoreCase(flowParams.getNextActivityId())) {//下一节点是退回拟稿人的
            flowParams.setBack(true);
        }
        dto.setFlowParams(flowParams);
        dto.setActionType(actionType);
        return dto;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    @Override
    public int saveOrUpdate(Object request, PieFlowTDto<T> dto, String actionType) throws CommonException {
        initParams(request, dto, actionType);
        int flag = 0;
        // 处理业务信息
        DocParams docP = dto.getDocParams();
        FlowParams flowP = dto.getFlowParams();
        PieDocument pieDocument = dto.getPieDocument();
        if (pieDocument == null) {
            pieDocument = new PieDocument();
        }
        String user = docP.getUser();
        //20201218  特殊文档的使用，需要在子流程实现对应的操作
        if ("1".equalsIgnoreCase(flowP.getCurrentActivityId()) && ("start".equalsIgnoreCase(actionType))) { // 当前节点为1的时候启动
            if (StringUtils.isBlank(flowP.getDocumentId())) {
                //处理业务数据在发起的时候
                String oa_urgency = docP.getOa_urgency();
                String oaUrgency = "0";
                if (StringUtils.isBlank(oa_urgency)) {
                    String approveResult = flowP.getApproveResult();
                    if (StringUtils.isNotBlank(approveResult)) {
                        try {
                            JSONObject o = JSONObject.parseObject(approveResult);
                            if (o.containsKey("oaUrgency")) {
                                oaUrgency = o.getString("oaUrgency");
                            }
                        } catch (Exception e) {

                        }
                    }
                    docP.setOa_urgency(oaUrgency);
                } else {
                    oaUrgency = oa_urgency;
                }
                documentServices.initDoc(docP);
                String[] acA = null;
                String resultDoc = documentServices.create(docP);
                //流程主表
                SysFlowMain sysFlowMain = new SysFlowMain();
                if (StringUtils.isNotBlank(resultDoc)) {//
                    FlowResult flowResult = ToolUtil.dealWithReturn(resultDoc);
                    if (flowResult != null) {
                        PieDocument pieDocumentA = flowResult.getData().getDocument();
                        flowP.setDocumentId(pieDocumentA.getId());
                        acA = getCurrentActivities(flowResult.getData().getTasks(), flowP);
                        pieDocument.setTitle(pieDocumentA.getTitle());
                        pieDocument.setCurrentActivity(pieDocumentA.getCurrentActivity());
                        pieDocument.setCurrentActivities(acA);
                        pieDocument.setStatus(pieDocumentA.getStatus());
                        pieDocument.setCurrentOwner(pieDocumentA.getCurrentOwner());
                        pieDocument.setId(pieDocumentA.getId());
                        pieDocument.setProcessId(pieDocumentA.getProcessId());
                        pieDocument.setId(pieDocumentA.getId());
                        dto.setDocId(pieDocumentA.getId());
                        //打开流程
                        dealWtidhInitOpenBPM(dto, docP, flowP);
                        if (START.equalsIgnoreCase(actionType)) {//发起流程啊
                            String nextActivityId = flowP.getNextActivityId();
                            if (StringUtils.isNotBlank(nextActivityId)) {//如果有下一步节点就是提交
                                dto.setCurrentStatusI(currentStatusMap.get(Integer.parseInt(nextActivityId)));
                                flowService.initFlow(flowP);
                                String resultFlow = flowService.start(flowP);
                                if (StringUtils.isNotBlank(resultFlow)) {
                                    FlowResult flowResult1 = ToolUtil.dealWithReturn(resultFlow);
                                    PieDocument pieDocumentf = flowResult1.getData().getDocument();
                                    //赋值id
                                    acA = getCurrentActivities(flowResult1.getData().getTasks(), flowP);
                                    pieDocument.setTitle(pieDocumentf.getTitle());
                                    pieDocument.setCurrentActivity(pieDocumentf.getCurrentActivity());
                                    pieDocument.setCurrentActivities(acA);
                                    pieDocument.setStatus(pieDocumentf.getStatus());
                                    pieDocument.setCurrentOwner(pieDocumentf.getCurrentOwner());
                                    pieDocument.setId(pieDocumentf.getId());
                                    pieDocument.setProcessId(pieDocumentf.getProcessId());
                                    //流程主表
                                    sysFlowMain.setDocId(dto.getDocId());
                                    sysFlowMain.setCreateUser(pieDocumentA.getCreatedBy());
                                    sysFlowMain.setCurrentDealUser(flowResult1.getData().getTasks().get(0).getOwnerId());
                                    sysFlowMain.setCurrentActivity(pieDocumentA.getCurrentActivity());
                                    sysFlowMain.setCreateTime(DateUtils.getNowDate());
                                    sysFlowMain.setService(pieDocumentA.getServiceCode());
                                    //走自己的业务逻辑，每个需要走流程的Service都需要重写这个方法
                                    sysFlowMain.setId(UUID.randomUUID().toString().replaceAll("-", ""));
                                    sysFlowMain.setActivityId(flowP.getCurrentActivityId());
                                    flag= sysFlowMainMapper.insertSysFlowMain(sysFlowMain);
                                    saveService(dto);
                                }
                            }
                        }
                    }
                }
            }
        }
        //当前节点
        dto.setNodeIndex(Integer.getInteger(flowP.getCurrentActivityId()));
        return flag;
    }


    public String[] getCurrentActivities(List<PieTask> list, FlowParams flowParams) {
        String[] a = null;
        if (list != null && list.size() > 0) {
            Set<String> set = new HashSet<String>();
            for (int i = 0; i < list.size(); i++) {
                set.add(list.get(i).getActivityId());
            }
            a = set.toArray(new String[set.size()]);
        } else {
            if (flowParams != null && StringUtils.isNotBlank(flowParams.getNextActivityId())) {
                a = new String[]{flowParams.getNextActivityId()};
            }
        }
        return a;
    }


    @Override
    public void dealWtidhInitOpenBPM(PieFlowTDto<T> dto, DocParams docParams,
                                     FlowParams flowParams) {
        String docId = dto.getDocId();
        if (StringUtils.isNotBlank(docId)) {
            String openParams = openDocumentFlow(docId, docParams.getUser(), false);
            if (StringUtils.isNotBlank(openParams)) {
                FlowResult r = JSONObject.parseObject(openParams, FlowResult.class);
                if (r.getHttpCode() == 200) {//返回成功的时候进行处理
                    PieDocTask pieDocTask = r.getData();
                    if (pieDocTask != null) {
                        dto.setPieDocTask(pieDocTask);
                        PieDocument pieDocument = pieDocTask.getDocument();
                        dto.setPieDocument(pieDocument);
                        //赋值细类
                        JSONObject currentActivity = pieDocTask.getCurrentActivity();
                        JSONArray nextActivities = pieDocTask.getNextActivities();
                        PieTask userTask = pieDocTask.getUserTask();
                        if (StringUtils.isBlank(flowParams.getNextActivityId())
                                || "null".equalsIgnoreCase(flowParams.getNextActivityId())
                                || "undefined".equalsIgnoreCase(flowParams.getNextActivityId())) {
                            flowParams.setNextActivityId(dealNextActivities(nextActivities));
                        }
                        if (StringUtils.isBlank(flowParams.getCurrentActivityId()) && currentActivity != null) {
                            flowParams.setCurrentActivityId(currentActivity.getString("id"));
                        }
                        if (StringUtils.isBlank(flowParams.getTaskId()) && userTask != null) {
                            flowParams.setTaskId(userTask.getId());
                        }
                    }
                }
            }
            docParams.setDocumentId(docId);
            flowParams.setDocumentId(docId);
        }
    }

    /**
     * @param
     * @return String    返回类型
     * @throws
     * @Title: openFlow
     * @Description: 打开流程
     * @author : CJH
     * @date : 2021年9月11日 上午10:19:01
     */
    public String openDocumentFlow(String documentId, String user, boolean returnData) {
        DocParams docP = new DocParams();
        docP.setDocumentId(documentId);
        docP.setUser(user);
        docP.setReturnData(returnData);
        documentServices.initDoc(docP);
        return documentServices.open(docP);
    }


    /**
     * @param
     * @return String    返回类型
     * @throws
     * @
     * @Title: commit
     * @Description: TODO(提交下一步)
     * @author : CJH
     * @date : 2021年9月18日 下午2:02:18
     */
    public String commit(PieFlowTDto<T> PieFlowTDto)  throws CommonException {
        String result = "";
        TransactionStatus state = null;
        try {
            state = platformTransactionManager.getTransaction(new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW));
           // boolean flag = beforeCommitService(PieFlowTDto);
            if ( PieFlowTDto.isCommitFlow()) {
                NextUser nu = PieFlowTDto.getNextUser();
                if (StringUtils.isBlank(nu.getUsers())) {//空的时候设置为不需要下一步处理人
                    NextUser nextUser = new NextUser();
                    nextUser.setUsers(null); //users.toString()
                    nextUser.setType("user");
                    nextUser.setSecretary("");
                    PieFlowTDto.getFlowParams().setNextUserInfo(JSONObject.toJSONString(nextUser));
                }
                flowService.initFlow(PieFlowTDto.getFlowParams());
                result = flowService.commit(PieFlowTDto.getFlowParams());
                FlowResult resultO = ToolUtil.dealWithReturn(result);
                PieDocument pieDocument = resultO.getData().getDocument();
                String[] acA = getCurrentActivities(resultO.getData().getTasks(), PieFlowTDto.getFlowParams());
                pieDocument.setCurrentActivities(acA);
                //修改主流程
                dealModelUpdateAmopFiFlow(resultO.getData().getTasks().get(0));
            }
        } catch (Exception e) {
            log.error("流程提交失败！" + e.getLocalizedMessage());
            throw e;
        } finally {
            if (state != null && !state.isCompleted()) {
                platformTransactionManager.commit(state);
            } else {
                platformTransactionManager.rollback(state);
            }
        }
        return result;
    }

    @Override
    public String saveCommit(PieFlowTDto<T> PieFlowTDto) {
        String result = "";
        TransactionStatus state = null;
        boolean flagC = false;
        try {
            state = platformTransactionManager.getTransaction(new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW));
            boolean flag = beforeSaveCommitService(PieFlowTDto);
            if (flag) {
                PieDocument pieDocumentO = PieFlowTDto.getPieDocument();
                //保存不修改处理人
                String flowStatus = pieDocumentO.getStatus();
                //这里暂时手改的
                if ("active".equalsIgnoreCase(flowStatus)) {///如果是Active 的时候才能提交否则直接保存业务数据
                    NextUser nu = new NextUser();
                    String owner = ToolUtil.dealWithFlowOwner(pieDocumentO.getCurrentOwner());
                    if (StringUtils.isBlank(owner)) {//空的时候设置为不需要下一步处理人
                        owner = PieFlowTDto.getDocParams().getUser();
                    }
                    nu.setUsers(owner);
                    nu.setType("user");
                    nu.setSecretary("");
                    FlowParams flowParams = PieFlowTDto.getFlowParams();
                    flowParams.setNextUserInfo(JSONObject.toJSONString(nu));
                    flowParams.setNextActivityId(flowParams.getCurrentActivityId());
                    flowService.initFlow(flowParams);
                    result = flowService.commit(flowParams);
                    FlowResult resultO = ToolUtil.dealWithReturn(result);
                    PieDocument pieDocumentN = resultO.getData().getDocument();
                    String[] acA = getCurrentActivities(resultO.getData().getTasks(), PieFlowTDto.getFlowParams());
                    pieDocumentN.setCurrentActivities(acA);
                    //修改主流程
                    dealModelUpdateAmopFiFlow(resultO.getData().getTasks().get(0));
                    saveCommitService(PieFlowTDto, pieDocumentN);
                }
                if (state != null && !state.isCompleted()) {
                    flagC = true;
                }
            }
        } catch (Exception e) {
            log.error("流程保存失败！" + e.getLocalizedMessage());
            throw e;
        } finally {
            if (state != null && !state.isCompleted() && flagC) {
                platformTransactionManager.commit(state);
            } else {
                platformTransactionManager.rollback(state);
            }
        }

        return result;
    }

    @Override
    public boolean beforeSaveCommitService(PieFlowTDto<T> dto) {
        //    throw new CommonException(400,"后端没有处理保存接口");
        return true;
    }

    @Override
    public boolean saveCommitService(PieFlowTDto<T> dto, PieDocument pieDocument) {
        // throw new CommonException(400,"后端没有处理保存接口");
        return true;
    }

    @Override
    public String reject(PieFlowTDto<T> PieFlowTDto) {
        String result = "";
        boolean flag = beforeRejectService(PieFlowTDto);
        if (flag) {
            flowService.initFlow(PieFlowTDto.getFlowParams());
            result = flowService.reject(PieFlowTDto.getFlowParams());
            FlowResult resultO = ToolUtil.dealWithReturn(result);
            PieDocument pieDocument = resultO.getData().getDocument();
            String[] acA = getCurrentActivities(resultO.getData().getTasks(), PieFlowTDto.getFlowParams());
            if (acA != null) {
                pieDocument.setCurrentActivities(acA);
            }
            //修改主流程
            dealModelUpdateAmopFiFlow(resultO.getData().getTasks().get(0));
            rejectService(PieFlowTDto, resultO.getData());

        }
        return result;
    }

    @Override
    public String cancel(PieFlowTDto<T> dto) {
        String result = "";
        boolean flag = beforeCancelService(dto);
        if (flag) {
            flowService.initFlow(dto.getFlowParams());
            result = flowService.cancel();
            FlowResult resultO = ToolUtil.dealWithReturn(result);
            PieDocument pieDocument = resultO.getData().getDocument();
            String[] acA = getCurrentActivities(resultO.getData().getTasks(), dto.getFlowParams());
            if (acA != null) {
                pieDocument.setCurrentActivities(acA);
            }
            //修改主流程
            dealModelUpdateAmopFiFlow(resultO.getData().getTasks().get(0));
            cancelService(dto, pieDocument);
            if (pieDocument != null) {
                //解除自转流程的暂挂
                //      amopProdSkeletonWfService.resumeFlowByChildDocId(pieDocument.getId());
            }
        }
        return result;
    }

    @Override
    public String assign(PieFlowTDto<T> PieFlowTDto) {
        String result = "";
        boolean flag = beforeAssignService(PieFlowTDto);
        if (flag) {
            flowService.initFlow(PieFlowTDto.getFlowParams());
            result = flowService.assign();
            FlowResult resultO = ToolUtil.dealWithReturn(result);
            PieDocument pieDocument = resultO.getData().getDocument();
            String[] acA = getCurrentActivities(resultO.getData().getTasks(), PieFlowTDto.getFlowParams());
            if (acA != null) {
                pieDocument.setCurrentActivities(acA);
            }
            //修改主流程
            dealModelUpdateAmopFiFlow(resultO.getData().getTasks().get(0));
            assignService(PieFlowTDto, pieDocument);
        }
        return result;
    }

    @Override
    public String close(PieFlowTDto<T> PieFlowTDto) {
        String result = "";
        boolean flag = beforeCloseService(PieFlowTDto);
        if (flag) {
            flowService.initFlow(PieFlowTDto.getFlowParams());
            result = flowService.close();
            FlowResult resultO = ToolUtil.dealWithReturn(result);
            PieDocument document = resultO.getData().getDocument();
            if (document != null) {
                //    document.setStatus(ProductDesignInfoEnum.flowStatus_handle_closed.getCode());
            }
            String[] acA = getCurrentActivities(resultO.getData().getTasks(), PieFlowTDto.getFlowParams());
            if (acA != null && document != null) {
                document.setCurrentActivities(acA);
            }
            //修改主流程
            dealModelUpdateAmopFiFlow(resultO.getData().getTasks().get(0));
            closeService(PieFlowTDto, document);
            if (document != null) {
                //解除自转流程的暂挂
                // amopProdSkeletonWfService.resumeFlowByChildDocId(document.getId());
            }
        }
        return result;
    }

    @Override
    public String solicit(PieFlowTDto<T> PieFlowTDto) {
        String result = "";
        boolean flag = beforeSolicitService(PieFlowTDto);
        if (flag) {
            flowService.initFlow(PieFlowTDto.getFlowParams());
            result = flowService.solicit();
            FlowResult resultO = ToolUtil.dealWithReturn(result);
            PieDocument pieDocument = resultO.getData().getDocument();
            String[] acA = getCurrentActivities(resultO.getData().getTasks(), PieFlowTDto.getFlowParams());
            if (acA != null) {
                pieDocument.setCurrentActivities(acA);
            }
            //修改主流程
            dealModelUpdateAmopFiFlow(resultO.getData().getTasks().get(0));
            solicitService(PieFlowTDto, pieDocument);
        }
        return result;
    }

    @Override
    public String transfer(PieFlowTDto<T> PieFlowTDto) {
        String result = "";
        boolean flag = beforeTansferService(PieFlowTDto);
        if (flag) {
            flowService.initFlow(PieFlowTDto.getFlowParams());
            result = flowService.transfer();
            FlowResult resultO = ToolUtil.dealWithReturn(result);
            PieDocument pieDocument = resultO.getData().getDocument();
            String[] acA = getCurrentActivities(resultO.getData().getTasks(), PieFlowTDto.getFlowParams());
            if (acA != null) {
                pieDocument.setCurrentActivities(acA);
            }
            //修改主流程
            dealModelUpdateAmopFiFlow(resultO.getData().getTasks().get(0));
            transferService(PieFlowTDto, pieDocument);

        }
        return result;
    }

    @Override
    public String withdraw(PieFlowTDto<T> PieFlowTDto) {
        String result = "";
        boolean flag = beforeWithdrawService(PieFlowTDto);
        if (flag) {
            flowService.initFlow(PieFlowTDto.getFlowParams());
            result = flowService.withdraw();
            FlowResult resultO = ToolUtil.dealWithReturn(result);
            PieDocument pieDocument = resultO.getData().getDocument();
            String[] acA = getCurrentActivities(resultO.getData().getTasks(), PieFlowTDto.getFlowParams());
            if (acA != null) {
                pieDocument.setCurrentActivities(acA);
            }
            //修改主流程
            resultO.getData().getTasks().get(0);
            withdrawService(PieFlowTDto, resultO.getData());
        }
        return result;
    }

    @Override
    public String suspend(PieFlowTDto<T> PieFlowTDto) {
        String result = "";
        flowService.initFlow(PieFlowTDto.getFlowParams());
        result = flowService.suspend();
        return result;
    }

    @Override
    public String resume(PieFlowTDto<T> PieFlowTDto) {
        String result = "";
        flowService.initFlow(PieFlowTDto.getFlowParams());
        result = flowService.resume();
        return result;
    }


    /**
     * @MethodName: com.gf.gfam_ams.serviceImp.workFlow.BpmFlowAbstractServiceImpl.dealModelUpdateAmopFiFlow
     * @Description: 处理AmopFiFlowModel 进行更新
     * @Param: [pieDocument]
     * @Auth: YvonneLee
     * @Date: 2019/12/20 10:04
     * @Return: void
     */
    private void dealModelUpdateAmopFiFlow(PieTask task) {
          if(null!=task){
              SysFlowMain sysFlowMain=new SysFlowMain();
              sysFlowMain.setDocId(task.getDocumentId());
              sysFlowMain.setCurrentActivity(task.getName());
              sysFlowMain.setActivityId(task.getActivityId());
              sysFlowMain.setCurrentDealUser(task.getOwnerId());
              sysFlowMainMapper.updateSysFlowMainByDocId(sysFlowMain);
          }
    }

    @Override
    public String dealNextActivities(JSONArray jsonArray) {
        return ToolUtil.dealNextActivities(jsonArray);
    }


    /* *
     * @MethodName: com.gf.gfam_ams.serviceImp.workFlow.BpmFlowAbstractServiceImpl.dealUserInfo
     * @Description: 初始化的时候根据
     * @Param: [nextUser, nextSpecialUserInfo, keyI, keyNI]
     * @Auth: YvonneLee
     * @Date: 2019/9/20 9:12
     * @Return:  java.util.List<com.gf.gfam_ams.model.customOrther.bpm.BpmSpecialUserInfo>
     */
    public List<BpmSpecialUserInfo> dealUserInfo(NextUser nextUser, String nextSpecialUserInfo, Integer keyI, Integer keyNI) {
        List<BpmSpecialUserInfo> jsonArrayMap = new ArrayList<>();
        String nextUsers = "";
        if (nextUser != null) {//自定义人
            nextUsers = nextUser.getUsers();
            if (StringUtils.isNotBlank(nextUsers)) {
                String[] a = nextUsers.split(",");
                for (String string : a) {
                    BpmSpecialUserInfo oa = new BpmSpecialUserInfo();
                    oa.setUserid(string);
                    oa.setGroupid("100");
                    jsonArrayMap.add(oa);
                }
            }
        }
        if (StringUtils.isNotBlank(nextSpecialUserInfo)) {//前端传来的特殊人员 优先
            try {
                List<BpmSpecialUserInfo> jsonArray = JSONObject.parseArray(nextSpecialUserInfo, BpmSpecialUserInfo.class);
                StringBuffer users = new StringBuffer(nextUsers);
                if (jsonArray != null && jsonArray.size() > 0) {
                    for (int i = 0; i < jsonArray.size(); i++) {
                        BpmSpecialUserInfo job = jsonArray.get(i);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                        jsonArrayMap.add(job);
                        if (users.length() == 0) {
                            users.append(job.getUserid());
                        } else {
                            users.append(",");
                            users.append(job.getUserid());
                        }
                    }

                    nextUsers = users.toString();
                    String s = ToolUtil.distinctStringWithDot(nextUsers);
                    if(nextUser!=null){
                        nextUser.setUsers(s); //users.toString()
                        nextUser.setType("user");
                        nextUser.setSecretary("");
                    }


                }

            } catch (Exception e1) {
                log.error("下一步特殊处理人异常:" + e1.getLocalizedMessage());
            }
        }

//        if (StringUtils.isBlank(nextUsers)) {//角色的特殊处理人角色为空的时候
//            log.info("提交时后端直接获取下一步人: keyI="+keyI+"keyNI="+keyNI);
//            if (keyI != null && businessNodeInfo.containsKey(keyI.toString())) {
//                log.info("businessNodeInfo: "+businessNodeInfo.containsKey(keyI.toString()));
//                AmopFlowModel currentN = businessNodeInfo.get(keyI.toString());
//                List<BpmSpecialUserInfo> t = currentN.getSpecialUserInfos();
//                StringBuffer users = new StringBuffer();
//                if (t != null && t.size() > 0) {
//                    for (BpmSpecialUserInfo job : t) {
//                        if (keyNI != null) {
//                            if (job.getNextNodeCode() != null) {
//                                if (job.getNextNodeCode().intValue() == keyNI.intValue()) {
//                                    jsonArrayMap.add(job);
//                                    if (users.length() == 0) {
//                                        users.append(job.getUserid());
//                                    } else {
//                                        users.append(",");
//                                        users.append(job.getUserid());
//                                    }
//                                }
//                            } else {
//                                jsonArrayMap.add(job);
//                                if (users.length() == 0) {
//                                    users.append(job.getUserid());
//                                } else {
//                                    users.append(",");
//                                    users.append(job.getUserid());
//                                }
//                            }
//                        } else {
//                            jsonArrayMap.add(job);
//                            if (users.length() == 0) {
//                                users.append(job.getUserid());
//                            } else {
//                                users.append(",");
//                                users.append(job.getUserid());
//                            }
//                            log.info("获取特殊人根据 下一步节点角色获取得时候没有下一步操作节点的id");
//                        }
//                    }
//                    String s = ToolUtil.distinctStringWithDot(users.toString());
//                    nextUser.setUsers(s);//users.toString()
//                    nextUser.setType("user");
//                    nextUser.setSecretary("");
//                }
//            }
//        }
        return jsonArrayMap;
    }


    @Override
    public void dealWithSpecialNode(PieDocTask pieDocTask) {
        if (pieDocTask != null) {
            PieDocument dos = pieDocTask.getDocument();
            //    getCurrentStatusMap(dos.getCreatedBy(), ToolUtil.getAmopFlowNodeModelByCode("AMOP_001",getFlowDetailCode()));
            PieAction action = pieDocTask.getAction();
            if (action == null) {
                action = new PieAction();
            }
            action.setEditBusiness(false);
            action.setUseRoleSelectUser(false);
//            action.setAllowCommit(true);
            action.setDeleteFlowAttach(false);
            //20200724撤销按钮去除
            action.setAllowCancel(false);
            action.setViewMode(false);
            JSONObject currentActivity = pieDocTask.getCurrentActivity();
            pieDocTask.setBpmShowData(false);
            if (currentActivity != null) {
                action.setViewMode(true);
                action.setDeleteFlowAttach(true);
                pieDocTask.setBpmShowData(true);
                JSONArray nextActivities = pieDocTask.getNextActivities();
                //  dealWithDraftAdd(nextActivities,action);
                String key = (String) currentActivity.get("id");
                String nextKeyT = dealNextActivities(nextActivities);
                Integer next = this.currentStatusMap.get(Integer.valueOf(nextKeyT));
                //20210708 保存
                if ("active".equalsIgnoreCase(dos.getStatus())) {
                    action.setAllowSave(true);
                }
                pieDocTask.setAction(action);
                //      setBpmSpecialUser(pieDocTask, Integer.valueOf(key), next, null);
                dealWithSpecialNodeChildren(pieDocTask, key);
            } else {//
                //已办的时候也会当前对象为空
//                if(dos != null){//如果当前节点对象为null 的时候但是document 却为结束的时候,手动将节点设为100
//                    if("closed".equalsIgnoreCase(dos.getStatus())){
                JSONObject currentA = new JSONObject();
                currentA.put("id", "100");
                pieDocTask.setCurrentActivity(currentA);
//                    }
//                }
            }
        }
    }


    @Override
    public void dealWithNextUserParent(PieDocTask pieDocTask, String nextKey) {
        if (pieDocTask != null) {
            PieDocument dos = pieDocTask.getDocument();
            //        getCurrentStatusMap(dos.getCreatedBy(), ToolUtil.getAmopFlowNodeModelByCode("AMOP_001",getFlowDetailCode()));
            JSONObject currentActivity = pieDocTask.getCurrentActivity();
            if (currentActivity != null) {
                JSONArray nextActivities = pieDocTask.getNextActivities();
                String nextKeyT = dealNextActivities(nextActivities);
                String key = (String) currentActivity.get("id");
                Integer next = StringUtils.isBlank(nextKey) ? this.currentStatusMap.get(Integer.valueOf(nextKeyT)) : this.currentStatusMap.get(Integer.valueOf(nextKey));
                //       setBpmSpecialUser(pieDocTask, Integer.valueOf(key), next, null);
                dealWithNextUserChildren(pieDocTask, key, next == null ? "" : next.intValue() + "");
            }
        }
    }

    @Override
    public void getNextNodeUserByGroup(PieDocTask pieDocTask, String nextKey, String codes) {
        if (pieDocTask != null) {
            PieDocument dos = pieDocTask.getDocument();
            //      getCurrentStatusMap(dos.getCreatedBy(), ToolUtil.getAmopFlowNodeModelByCode("AMOP_001",getFlowDetailCode()));
            JSONObject currentActivity = pieDocTask.getCurrentActivity();
            if (currentActivity != null) {
                JSONArray nextActivities = pieDocTask.getNextActivities();
                String nextKeyT = dealNextActivities(nextActivities);
                String key = (String) currentActivity.get("id");
                Integer next = StringUtils.isBlank(nextKey) ? this.currentStatusMap.get(Integer.valueOf(nextKeyT)) : this.currentStatusMap.get(Integer.valueOf(nextKey));
                //          setBpmSpecialUser(pieDocTask, Integer.valueOf(key), next, codes);
                getNextNodeUserByGroupChildren(pieDocTask, key, next == null ? "" : next.intValue() + "", codes);
            }
        }
    }

    @Override
    public void share(PieFlowTDto<T> dto, PieDocument pieDocument) {
//        String flowCode = "AMOP_001";
//        String flowDetail = getFlowDetailCode();
        try {
            String docId = pieDocument.getId();
            String title = pieDocument.getTitle();
            if (StringUtils.isBlank(title)) {
                title = dto.getTitle();
            }
            FlowParams flowParams = dto.getFlowParams();
            String userId = pieDocument.getStartedBy();
            if (StringUtils.isBlank(userId)) {
                userId = flowParams.getCurrentUser();
            }
            String currentActivityId = flowParams.getCurrentActivityId();
            String nextActivityId = flowParams.getNextActivityId();
            String message = title + "-请求阅知";
            String toUserIds = ToolUtil.getBpmReader(docId, currentActivityId, nextActivityId);
            if (StringUtils.isNotBlank(toUserIds)) {
                //    commonService.createDocShare(message,userId,docId,title,toUserIds);
            } else {
                log.info("流程阅知未触发：" + docId + ",请联系管理员配置阅知");
            }
        } catch (Exception e) {
            log.error("流程阅知触发异常：", e);
        }

    }

}
