package com.yabushan.system.service;

import com.yabushan.system.domain.TodoInfo;
import com.yabushan.system.domain.TodoInfoPush;
import com.yabushan.system.domain.vo.PushProTodoDoneNew;
import com.yabushan.system.domain.vo.PushProToreadReadNew;

import java.util.List;

/**
 * 待办待阅Service接口
 *
 * @author 姜森焱
 * @date 2021-09-24
 */
public interface ITodoInfoPushService {
    /**
     * 查询待办待阅
     *
     * @param id 待办待阅ID
     * @return 待办待阅
     */
    public TodoInfoPush selectTodoInfoPushById(String id);

    /**
     * 根据待办待阅状态查询最大时间记录
     * @param todoDataType
     * @return
     */
    public TodoInfoPush selectTodoInfoPushListBytodoDataType(String todoDataType);

    /**
     * 查询待办待阅列表
     *
     * @param todoInfoPush 待办待阅
     * @return 待办待阅集合
     */
    public List<TodoInfoPush> selectTodoInfoPushList(TodoInfoPush todoInfoPush);

    /**
     * 新增待办待阅
     *
     * @param todoInfoPush 待办待阅
     * @return 结果
     */
    public int insertTodoInfoPush(TodoInfoPush todoInfoPush);


    /**
     * 从工作流的待办取值增加到待办待阅推送记录表
     *
     * @param todoInfo 待办待阅
     * @return 结果
     */
    public int insertTodoInfoPushByTodoInfo(TodoInfo todoInfo,boolean isdouble,String taskId);

    /**
     * 批量新增待办待阅
     *
     * @param todoInfoPush 待办待阅
     * @return 结果
     */
    public int bathInsertTodoInfoPush(List<TodoInfoPush> todoInfoPush);

    /**
     * 修改待办待阅
     *
     * @param todoInfoPush 待办待阅
     * @return 结果
     */
    public int updateTodoInfoPush(TodoInfoPush todoInfoPush);

    /**
     * 通过id集合修改状态
     */
    public Boolean updateByIds(String ids, String disableEnableState);

    /**
     * 批量删除待办待阅
     *
     * @param ids 需要删除的待办待阅ID
     * @return 结果
     */
    public int deleteTodoInfoPushByIds(String[] ids);

    /**
     * 删除待办待阅信息
     *
     * @param id 待办待阅ID
     * @return 结果
     */
    public int deleteTodoInfoPushById(String id);


    /**
     * 导入待办待阅Excel数据
     *
     * @param todoInfoPushList 数据列表
     * @param isUpdateSupport  是否更新支持，如果已存在，则进行更新数据
     * @param operName         操作用户
     * @return 结果
     */
    public List<TodoInfoPush> importTodoInfoPush(List<TodoInfoPush> todoInfoPushList, Boolean isUpdateSupport, String operName);



    /**
     * 根据businesskey删除待办待阅信息
     *
     * @param businesskey 业务id
     * @return 结果
     */
    public int deleteTodoInfoPushByBusinesskey(String businesskey);


    /**
     * 查询已推送未处理待办待阅列表
     *
     * @param todoInfoPush 待办待阅
     * @return 待办待阅集合
     */
    public List<TodoInfoPush> selectUnHandlePushList(TodoInfoPush todoInfoPush);


    /**
     * 查询待办已办推送portal
     * @param todoDataType
     * @return
     */
    public List<PushProTodoDoneNew> getTodoInfoToPush(String todoDataType);

    /**
     * 查询待阅已阅推送portal
     * @param todoDataType
     * @return
     */
    public List<PushProToreadReadNew> getToReadInfoToPush(String todoDataType);


}
