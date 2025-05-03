package com.yabushan.system.mapper;


import com.yabushan.system.domain.TodoInfo;
import com.yabushan.system.domain.vo.TodoInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 待办待阅Mapper接口
 *
 * @author 姜森焱
 * @date 2021-09-02
 */
public interface TodoInfoMapper {
    /**
     * 查询待办待阅
     *
     * @param todoId 待办待阅ID
     * @return 待办待阅
     */
    public TodoInfo selectTodoInfoById(String todoId);

    /**
     * 根据taskid查询上一步处理人信息
     *
     * @param taskId
     * @return
     */
    public TodoInfoVo selectTodoInfoByTaskId(@Param("taskId") String taskId);

    /**
     * 我的申请，小程序待办我的申请
     */
    public List<TodoInfoVo> selectMyTodoInfoList(TodoInfoVo todoInfo);

    /**
     * 查询待办待阅列表
     *
     * @param todoInfo 待办待阅
     * @return 待办待阅集合
     */
    public List<TodoInfo> selectTodoInfoList(TodoInfo todoInfo);

    /**
     * 根据处理人查询待办待阅的总数
     */
    public List<TodoInfoVo> selectTodoInfoCount(@Param("todoHandler") String todoHandler);

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
     * 删除待办待阅
     *
     * @param todoId 待办待阅ID
     * @return 结果
     */
    public int deleteTodoInfoById(String todoId);



    /**
     * 根据businesskey删除待办待阅信息
     *
     * @param businesskey 业务id
     * @return 结果
     */
    public int deleteTodoInfoByBusinesskey(String businesskey);



    /**
     * 批量删除待办待阅
     *
     * @param todoIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteTodoInfoByIds(String[] todoIds);

    /**
     * 获取待办数量
     *
     * @param applyId
     * @param todoHandler
     * @return
     */
    public List<Map<String, Object>> getToDoInfoCount(@Param("applyId") String applyId, @Param("todoHandler") String todoHandler, @Param("applyType") String applyType);

    //通过parentId获取某一类应用的待办
    public List<TodoInfo> getToDoList(TodoInfo todoInfo);

    TodoInfo selectTodoInfoByBusinessKeyAndTodoHandler(@Param("businessKey") String businessKey, @Param("todoHandler") String todoHandler);

    //获取待办权限信息
    public List<TodoInfo> getAuthorizeToDoList(Map param);

    public int updateTodoInfoByTaskIdAndTodoHandler(@Param("taskId") String taskId, @Param("todoHandler") String todoHandler);
    public int deleteTodoInfoByBusinesskeys(String[] businesskeys);

    public int updateTodoInfoByBusinessKey(TodoInfo todoInfo);
    public int updateTodoInfoByTaskId(TodoInfo todoInfo);


}
