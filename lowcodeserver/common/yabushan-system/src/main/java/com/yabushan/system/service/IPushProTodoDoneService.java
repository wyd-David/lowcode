package com.yabushan.system.service;

import java.util.List;
import com.yabushan.system.domain.PushProTodoDone;
import com.yabushan.system.enums.BusinessType;

/**
 * 推送待办已办Service接口
 *
 * @author yabushan
 * @date 2022-04-28
 */
public interface IPushProTodoDoneService
{



    /**
     * 清空返回导入数据的集合
     */
    public void clearpushProTodoDoneInfo();


    /**
     * 查询推送待办已办
     *
     * @param todoId 推送待办已办ID
     * @return 推送待办已办
     */
    public PushProTodoDone selectPushProTodoDoneById(String todoId);

    /**
     * 查询推送待办已办列表
     *
     * @param pushProTodoDone 推送待办已办
     * @return 推送待办已办集合
     */
    public List<PushProTodoDone> selectPushProTodoDoneList(PushProTodoDone pushProTodoDone);

    /**
     * 新增推送待办已办
     *
     * @param pushProTodoDone 推送待办已办
     * @return 结果
     */
    public int insertPushProTodoDone(PushProTodoDone pushProTodoDone);

    /**
     * 批量新增推送待办已办
     *
     * @param pushProTodoDone 推送待办已办
     * @return 结果
     */
    public int bathInsertPushProTodoDone(List<PushProTodoDone> pushProTodoDone);

    /**
     * 修改推送待办已办
     *
     * @param pushProTodoDone 推送待办已办
     * @return 结果
     */
    public int updatePushProTodoDone(PushProTodoDone pushProTodoDone);
    /**
     * 通过id集合修改状态
     */
    public Boolean updateByIds(String ids,String disableEnableState);

    /**
     * 批量删除推送待办已办
     *
     * @param todoIds 需要删除的推送待办已办ID
     * @return 结果
     */
    public int deletePushProTodoDoneByIds(String[] todoIds);

    /**
     * 删除推送待办已办信息
     *
     * @param todoId 推送待办已办ID
     * @return 结果
     */
    public int deletePushProTodoDoneById(String todoId);

    /**
     * 导入推送待办已办Excel数据
     *
     * @param pushProTodoDoneList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public List<PushProTodoDone> importPushProTodoDone(List<PushProTodoDone> pushProTodoDoneList, Boolean isUpdateSupport, String operName);

    boolean push(PushProTodoDone pushProTodoDone) throws Exception;
    //    推送数据到portal
    boolean pushDataToPortal(BusinessType businessType);
}
