package com.yabushan.system.service;

import java.util.List;
import com.yabushan.system.domain.EztPushTodoToread;

/**
 * 推送统一待办消息（包括待办、待阅）Service接口
 *
 * @author yabushan
 * @date 2022-04-28
 */
public interface IEztPushTodoToreadService
{



    /**
    * 清空返回导入数据的集合
    */
    public void cleareztPushTodoToreadInfo();


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
    public int bathInsertEztPushTodoToread(List<EztPushTodoToread> eztPushTodoToread);

    /**
     * 修改推送统一待办消息（包括待办、待阅）
     *
     * @param eztPushTodoToread 推送统一待办消息（包括待办、待阅）
     * @return 结果
     */
    public int updateEztPushTodoToread(EztPushTodoToread eztPushTodoToread);
    /**
    * 通过id集合修改状态
    */
    public Boolean updateByIds(String ids,String disableEnableState);

    /**
     * 批量删除推送统一待办消息（包括待办、待阅）
     *
     * @param ids 需要删除的推送统一待办消息（包括待办、待阅）ID
     * @return 结果
     */
    public int deleteEztPushTodoToreadByIds(String[] ids);

    /**
     * 删除推送统一待办消息（包括待办、待阅）信息
     *
     * @param id 推送统一待办消息（包括待办、待阅）ID
     * @return 结果
     */
    public int deleteEztPushTodoToreadById(String id);

    /**
     * 导入推送统一待办消息（包括待办、待阅）Excel数据
     *
     * @param eztPushTodoToreadList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public List<EztPushTodoToread> importEztPushTodoToread(List<EztPushTodoToread> eztPushTodoToreadList, Boolean isUpdateSupport, String operName);
}
