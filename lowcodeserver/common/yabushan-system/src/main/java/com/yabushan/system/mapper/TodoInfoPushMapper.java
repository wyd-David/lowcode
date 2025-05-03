package com.yabushan.system.mapper;

import java.util.List;
import com.yabushan.system.domain.TodoInfoPush;
import com.yabushan.system.domain.vo.PushProTodoDoneNew;
import com.yabushan.system.domain.vo.PushProToreadReadNew;
import org.apache.ibatis.annotations.Param;

/**
 * 待办待阅Mapper接口
 *
 * @author 姜森焱
 * @date 2021-09-24
 */
public interface TodoInfoPushMapper
{
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
    public TodoInfoPush selectTodoInfoPushListBytodoDataType(@Param("todoDataType") String todoDataType);

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
     * 批量新增待办待阅
     *
     * @param todoInfoPush 待办待阅
     * @return 结果
     */
    public int bathInsertTodoInfoPush(List<TodoInfoPush> list);

    /**
     * 修改待办待阅
     *
     * @param todoInfoPush 待办待阅
     * @return 结果
     */
    public int updateTodoInfoPush(TodoInfoPush todoInfoPush);

    /**
     * 删除待办待阅
     *
     * @param id 待办待阅ID
     * @return 结果
     */
    public int deleteTodoInfoPushById(String id);

    /**
     * 批量删除待办待阅
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTodoInfoPushByIds(String[] ids);

    /**
     * 查询已推送未处理待办待阅列表
     *
     * @param todoInfoPush 待办待阅
     * @return 待办待阅集合
     */
    public List<TodoInfoPush> selectUnHandlePushList(TodoInfoPush todoInfoPush);

    /**
     * 根据businesskey删除待办待阅
     *
     * @param businesskey 业务id
     * @return 结果
     */
    public int deleteTodoInfoPushByBusinesskey(String businesskey);

    public List<PushProTodoDoneNew> getTodoInfoToPush(String todoDataType);

    public List<PushProToreadReadNew> getToReadInfoToPush(String todoDataType);


}
