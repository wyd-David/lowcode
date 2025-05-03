package com.yabushan.system.mapper;

import java.util.List;

import com.yabushan.system.domain.EztPushTodoToread;

/**
 * 推送统一待办消息（包括待办、待阅）Mapper接口
 *
 * @author yabushan
 * @date 2022-04-28
 */
public interface EztPushTodoToreadMapper {
    /**
     * 查询推送统一待办消息（包括待办、待阅）
     *
     * @param id 推送统一待办消息（包括待办、待阅）ID
     * @return 推送统一待办消息（包括待办、待阅）
     */
    public EztPushTodoToread selectEztPushTodoToreadById(String id);

    /**
     * 查询推送统一待办消息（包括待办、待阅）列表
     *
     * @param eztPushTodoToread 推送统一待办消息（包括待办、待阅）
     * @return 推送统一待办消息（包括待办、待阅）集合
     */
    public List<EztPushTodoToread> selectEztPushTodoToreadList(EztPushTodoToread eztPushTodoToread);

    /**
     * 新增推送统一待办消息（包括待办、待阅）
     *
     * @param eztPushTodoToread 推送统一待办消息（包括待办、待阅）
     * @return 结果
     */
    public int insertEztPushTodoToread(EztPushTodoToread eztPushTodoToread);

    /**
     * 批量新增推送统一待办消息（包括待办、待阅）
     *
     * @param eztPushTodoToread 推送统一待办消息（包括待办、待阅）
     * @return 结果
     */
    public int bathInsertEztPushTodoToread(List<EztPushTodoToread> list);

    /**
     * 修改推送统一待办消息（包括待办、待阅）
     *
     * @param eztPushTodoToread 推送统一待办消息（包括待办、待阅）
     * @return 结果
     */
    public int updateEztPushTodoToread(EztPushTodoToread eztPushTodoToread);

    /**
     * 删除推送统一待办消息（包括待办、待阅）
     *
     * @param id 推送统一待办消息（包括待办、待阅）ID
     * @return 结果
     */
    public int deleteEztPushTodoToreadById(String id);

    /**
     * 批量删除推送统一待办消息（包括待办、待阅）
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteEztPushTodoToreadByIds(String[] ids);
}
