package com.yabushan.system.service;

import com.alibaba.fastjson.JSONArray;
import com.yabushan.common.bpm.model.*;
import com.yabushan.common.exception.CommonException;

import java.util.List;
import java.util.Map;

public interface BpmFlowService<T> {
    public static final String ASSIGN = "assign";//转办任务
    public static final String CANCEL = "cancel";//撤销文档
    public static final String CLOSE = "close";//关闭文档
    public static final String COMMIT = "commit";//提交任务
    public static final String REJECT = "reject";//退回任务
    public static final String SOLICIT = "solicit";//加签任务
    public static final String START = "start";//启动流程
    public static final String TRANSFER = "transfer";//综合员转发任务
    public static final String WITHDRAW = "withdraw";//撤回任务
    public static final String SUSPEND = "suspend";//暂挂文档
    public static final String RESUME = "resume";//恢复文档
    public static final String SAVE = "save";//保存
    public static final String SHARE = "share";//传阅

    //发起流程
    public static final String BASE_TART_FLOW_URL = "/startFlow";

    //提交流程
    public static final String BASE_COMMIT_FLOW_URL = "/commitFlow";

    //转办流程
    public static final String BASE_ASSIGN_FLOW_URL = "/assignFlow";

    //回退流程
    public static final String BASE_REJECT_FLOW_URL = "/rejectFlow";

    //测回流程
    public static final String BASE_WITHDRAW_FLOW_URL = "/withdrawFlow";

    //开始节点
    public static final String START_NODE = "1";


    /**
     * @param
     * @return String    返回类型
     * @throws
     * @Title: openDocumentFlow
     * @Description: TODO(打开bpm流程的信息)
     * @author : CJH
     * @date : 2021年9月14日 下午5:24:38
     */
    String openDocumentFlow(String documentId, String user, boolean returnData);

    /**
     * @param
     * @return int    返回类型
     * @throws
     * @Title: saveOrUpdate
     * @Description: TODO(暂存和提交时处理业务 ， 和保存附件)
     * @author : CJH
     * @date : 2021年9月14日 下午5:25:11
     */
    int saveOrUpdate(Object request, PieFlowTDto<T> dto, String actionType) throws CommonException;

    /**
     * @param
     * @return PieFlowTDto    返回类型
     * @throws
     * @Title: initParams
     * @Description: TODO(初始化参数)
     * @author : CJH
     * @date : 2021年9月14日 下午5:25:47
     */
    PieFlowTDto<T> initParams(Object request, PieFlowTDto<T> dto, String actionType) throws CommonException;


    /**
     * @return java.lang.String
     * @MethodName saveCommit
     * @Desc 提交的时候的保存
     * @Author YvonneLee
     * @Param [PieFlowTDto]
     * @Date 2020/10/26 15:20
     **/
    String saveCommit(PieFlowTDto<T> PieFlowTDto) throws CommonException;

    /**
     * @param
     * @return String    返回类型
     * @throws
     * @Title: cancel
     * @Description: TODO(撤销文档)
     * @author : CJH
     * @date : 2021年9月9日 上午9:55:11
     */
    String cancel(PieFlowTDto<T> dto) throws CommonException;

    /**
     * @param
     * @return String    返回类型
     * @throws
     * @Title: commit
     * @Description: TODO(提交流程)
     * @author : CJH
     * @date : 2021年9月9日 上午9:55:24
     */
    String commit(PieFlowTDto<T> PieFlowTDto) throws CommonException;

    /**
     * @param
     * @return String    返回类型
     * @throws
     * @Title: reject
     * @Description: TODO(退回任务)
     * @author : CJH
     * @date : 2021年9月9日 上午9:56:10
     */
    String reject(PieFlowTDto<T> PieFlowTDto) throws CommonException;

    /**
     * @param
     * @return String    返回类型
     * @throws
     * @Title: close
     * @Description: TODO(关闭文档)
     * @author : CJH
     * @date : 2021年9月9日 上午9:56:59
     */
    String close(PieFlowTDto<T> PieFlowTDto) throws CommonException;

    /**
     * @param
     * @return String    返回类型
     * @throws
     * @Title: assign
     * @Description: TODO(转办任务)
     * @author : CJH
     * @date : 2021年9月9日 上午9:57:28
     */
    String assign(PieFlowTDto<T> PieFlowTDto) throws CommonException;

    /**
     * @param
     * @return String    返回类型
     * @throws
     * @Title: solicit
     * @Description: TODO(加签任务)
     * @author : CJH
     * @date : 2021年9月9日 上午9:58:20
     */
    String solicit(PieFlowTDto<T> PieFlowTDto) throws CommonException;

    /**
     * @param
     * @return String    返回类型
     * @throws
     * @Title: transfer
     * @Description: TODO(综合员转发任务)
     * @author : CJH
     * @date : 2021年9月9日 上午9:59:16
     */
    String transfer(PieFlowTDto<T> PieFlowTDto) throws CommonException;

    /**
     * @param
     * @return String    返回类型
     * @throws
     * @Title: withdraw
     * @Description: TODO(撤回任务)
     * @author : CJH
     * @date : 2021年9月9日 上午10:00:40
     */
    String withdraw(PieFlowTDto<T> PieFlowTDto) throws CommonException;

    /**
     * @return java.lang.String
     * @MethodName suspend
     * @Desc TODO(暂挂文档)
     * @Author YvonneLee
     * @Param [PieFlowTDto]
     * @Date 2020/2/26 18:44
     **/
    String suspend(PieFlowTDto<T> PieFlowTDto) throws CommonException;

    /**
     * @return java.lang.String
     * @MethodName resume
     * @Desc TODO(恢复文档)
     * @Author YvonneLee
     * @Param [PieFlowTDto]
     * @Date 2020/2/26 18:45
     **/
    String resume(PieFlowTDto<T> PieFlowTDto) throws CommonException;

    /**
     * @return java.lang.String
     * @MethodName share
     * @Desc 传阅，此处为公共接口在提交以后触发
     * @Author YvonneLee
     * @Param [PieFlowTDto, pieDocument]
     * @Date 2021/1/5 13:27
     **/
    void share(PieFlowTDto<T> PieFlowTDto, PieDocument pieDocument) throws CommonException;

    /**
     * @param
     * @return boolean    返回类型
     * @throws
     * @Title: saveService
     * @Description: TODO(添加业务数据)
     * @author : CJH
     * @date : 2021年9月14日 下午5:26:59
     */
    boolean saveService(PieFlowTDto<T> dto) ;

    /**
     * @param
     * @return boolean    返回类型
     * @throws
     * @Title: updateService
     * @Description: TODO(更新业务)
     * @author : CJH
     * @date : 2021年9月14日 下午5:27:19
     */
    boolean updateService(PieFlowTDto<T> dto, PieDocument pieDocument);

    boolean beforeSaveCommitService(PieFlowTDto<T> dto);

    boolean saveCommitService(PieFlowTDto<T> dto, PieDocument pieDocument);


    boolean beforeCommitService(PieFlowTDto<T> dto);

    boolean commitService(PieFlowTDto<T> dto, PieDocument pieDocument);

    //转办任务的时候
    boolean beforeAssignService(PieFlowTDto<T> dto);

    boolean assignService(PieFlowTDto<T> dto, PieDocument pieDocument);

    /* *
     * @MethodName: com.gf.gfam_ams.service.workFlow.BpmFlowService.beforeCloseService
     * @Description: 判断是否可以关闭
     * @Param: [dto]
     * @Auth: YvonneLee
     * @Date: 2019/9/30 16:47
     * @Return:  boolean
     */
    boolean beforeCloseService(PieFlowTDto<T> dto);

    boolean closeService(PieFlowTDto<T> dto, PieDocument pieDocument);

    boolean beforeRejectService(PieFlowTDto<T> dto);

    boolean rejectService(PieFlowTDto<T> dto, PieDocTask pieDocTask);

    boolean beforeSolicitService(PieFlowTDto<T> dto);

    boolean solicitService(PieFlowTDto<T> dto, PieDocument pieDocument);

    boolean beforeTansferService(PieFlowTDto<T> dto);

    boolean transferService(PieFlowTDto<T> dto, PieDocument pieDocument);

    boolean beforeWithdrawService(PieFlowTDto<T> dto);

    boolean withdrawService(PieFlowTDto<T> dto, PieDocTask pieDocTask);

    boolean beforeCancelService(PieFlowTDto<T> dto);

    boolean cancelService(PieFlowTDto<T> dto, PieDocument pieDocument);
    //转办任务的时候


    /**
     * @param
     * @return String    返回类型
     * @throws
     * @Title: dealNextActivities
     * @Description: TODO(获取下一步节点)
     * @author : CJH
     * @date : 2021年9月19日 上午9:30:56
     */
    String dealNextActivities(JSONArray jsonArray);

    /**
     * @param
     * @return void    返回类型
     * @throws
     * @Title: dealWtidhInitOpenBPM
     * @Description:初始化的时候处理公共的节点
     * @author : CJH
     * @date : 2019年5月22日 下午4:58:06
     */
    void dealWtidhInitOpenBPM(PieFlowTDto<T> dto, DocParams docParams, FlowParams flowParams);


    /**
     * 处理特殊任务
     *
     * @param
     * @return Map<String, JSONObject>    返回类型
     * @throws
     * @Title: dealUserInfo
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @author : CJH
     * @date : 2019年5月29日 上午9:12:43
     */
    public List<BpmSpecialUserInfo> dealUserInfo(NextUser nextUser, String nextSpecialUserInfo, Integer keyI, Integer keyNI);


    /**
     * @param
     * @return void    返回类型
     * @throws
     * @Title: getNextNodeUserByGroup
     * @Description: 通过分组获取人员
     * @author : CJH
     * @date : 2019年7月30日 上午9:32:19
     */
    public void getNextNodeUserByGroup(PieDocTask pieDocTask, String nextKey, String codes);

    public void getNextNodeUserByGroupChildren(PieDocTask pieDocTask, String key, String nextKey, String codes);

    /**
     * @param
     * @return void    返回类型
     * @throws
     * @Title: dealWithNextUserParent
     * @Description: 处理下一步操作人和默认全部的操作人
     * @author : CJH
     * @date : 2019年7月29日 下午1:32:26
     */
    public void dealWithNextUserParent(PieDocTask pieDocTask, String nextKey);

    public void dealWithNextUserChildren(PieDocTask pieDocTask, String key, String nextKey);

    ///处理节点开始

    /**
     * @param
     * @return void    返回类型
     * @throws
     * @Title: dealWithSpecialNode
     * @Description: 处理业务特有节点
     * @author : CJH
     * @date : 2019年5月22日 下午2:50:32
     */
    void dealWithSpecialNode(PieDocTask pieDocTask);

    void dealWithSpecialNodeChildren(PieDocTask pieDocTask, String key);
    ///处理节点结束

    /**
     * @MethodName: com.gf.gfam_ams.mapper.prodManage.HomeProductInfoMapper.callPrAmopCheckFlowUniqueUse
     * @Description: 检查流程时候已经存在有效流程
     * @Param: [map]
     * @Auth: YvonneLee
     * @Date: 2019/9/30 14:0
     * @Return: void
     */
    String callPrAmopCheckFlowUniqueUse(Map<String, Object> map);

    String getFlowCode();

}
