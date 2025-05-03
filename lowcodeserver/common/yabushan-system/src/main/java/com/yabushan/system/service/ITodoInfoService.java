package com.yabushan.system.service;


import com.yabushan.system.domain.TodoInfo;
import com.yabushan.system.domain.vo.TodoInfoVo;
import com.yabushan.system.domain.vo.TodoVo;

import java.util.List;
import java.util.Map;

/**
 * 待办待阅Service接口
 *
 * @author 姜森焱
 * @date 2021-09-02
 */
public interface ITodoInfoService
{
    /**
     * 查询待办待阅
     *
     * @param todoId 待办待阅ID
     * @return 待办待阅
     */
    public TodoInfo selectTodoInfoById(String todoId);

    /**
     * 根据taskid查询上一步处理人信息
     * @param taskId
     * @return
     */
    public TodoInfoVo selectTodoInfoByTaskId(String taskId);

    /**
     * 查询待办待阅列表
     *
     * @param todoInfo 待办待阅
     * @return 待办待阅集合
     */
    public List<TodoInfo> selectTodoInfoList(TodoInfo todoInfo);

    /**
     * 我的申请，小程序待办我的申请
     */
    public List<TodoInfoVo> selectMyTodoInfoList(TodoInfoVo todoInfo);

    /**
     * PC查询待办待阅列表
     *
     * @param todoInfo 待办待阅
     * @return 待办待阅集合
     */
    public List<TodoInfo> selectTodoInfoListPC(TodoInfo todoInfo);

    Map<String,Integer> selectTodoInfoListPCCount(TodoInfo todoInfo);
    /**
     * 根据处理人查询待办待阅的总数
     *
     */
    public Map<String,Object> selectTodoInfoCount(String todoHandler);

    /**
     * 查询待办待阅列表关联了应用表查询
     *
     * @param todoInfo 待办待阅
     * @return 待办待阅集合
     */
    public List<TodoInfoVo> selectTodoInfoOrApplyinfoList(TodoInfoVo todoInfo);

    /**
     * 获取当前登陆人去重的应用名称跟应用ID
     */
    public List<TodoInfoVo> selectTodoInfoGetAppName(TodoInfoVo todoInfo);

    /**
     * 新增待办待阅
     *
     * @param todoInfo 待办待阅
     * @return 结果
     */
    public int insertTodoInfo(TodoInfo todoInfo);

    /**
     * 修改待办待阅
     *
     * @param todoInfo 待办待阅
     * @return 结果
     */
    public int updateTodoInfo(TodoInfo todoInfo);

    /**
     * 修改待办待阅
     * @param taskId 任务 id
     * @param todoHandler 待办处理人
     * @return
     */
    public int updateTodoInfo(String taskId, String todoHandler);

    /**
     * 批量删除待办待阅
     *
     * @param todoIds 需要删除的待办待阅ID
     * @return 结果
     */
    public int deleteTodoInfoByIds(String[] todoIds);

    /**
     * 删除待办待阅信息
     *
     * @param todoId 待办待阅ID
     * @return 结果
     */
    public int deleteTodoInfoById(String todoId);


    /**
     * 插入待办
     */
    public void batchSaveTodoInfo(List<TodoVo> todoVoList);

    /**
     * 插入已办
     */
    public void batchSaveHadDone(List<TodoVo> todoVoList);

    /**
     * 插入待阅
     */
    public void batchSaveToReadInfo(List<TodoVo> todoVoList);

    /**
     * 插入已阅
     */
    public void batchSaveHadRead(List<TodoVo> todoVoList);

    /**
     * 申请人撤销
     */
    public void applyUserCancel(String todoId);


    /**
     * 导入待办待阅Excel数据
     *
     * @param todoInfoList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public List<TodoInfo> importTodoInfo(List<TodoInfo> todoInfoList, Boolean isUpdateSupport, String operName);

    List<Map<String,Object>>  getToDoInfoCount(String appId, String todoHandler,String applyType);

    //通过parentId获取某一类应用的待办
    public List<TodoInfo> getToDoList(TodoInfo todoInfo);


    /**
     * 根据businesskey删除待办待阅信息
     *
     * @param businesskey 业务id
     * @return 结果
     */
    public int deleteTodoInfoByBusinesskey(String businesskey);

    public int deleteTodoInfoByBusinesskeys(String []businesskeys);


    /**
     * 根据 businessKey 和 todoHandler查询待办待阅信息
     * @param businessKey
     * @param todoHandler
     * @return
     */
    TodoInfo selectTodoInfoByBusinessKeyAndTodoHandler(String businessKey, String todoHandler);

    public List<TodoInfo> getAuthorizeToDoList(Map param) ;

    public int updateTodoInfoByBusinessKey(TodoInfo todoInfo);

}
