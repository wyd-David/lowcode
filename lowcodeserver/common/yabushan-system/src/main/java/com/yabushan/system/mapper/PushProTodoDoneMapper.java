package com.yabushan.system.mapper;


import com.yabushan.system.domain.PushProTodoDone;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 推送待办已办Mapper接口
 *
 * @author yabushan
 * @date 2022-04-28
 */
public interface PushProTodoDoneMapper
{
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
    public int bathInsertPushProTodoDone(List<PushProTodoDone> list);

    /**
     * 修改推送待办已办
     *
     * @param pushProTodoDone 推送待办已办
     * @return 结果
     */
    public int updatePushProTodoDone(PushProTodoDone pushProTodoDone);

    /**
     * 删除推送待办已办
     *
     * @param todoId 推送待办已办ID
     * @return 结果
     */
    public int deletePushProTodoDoneById(String todoId);

    /**
     * 批量删除推送待办已办
     *
     * @param todoIds 需要删除的数据ID
     * @return 结果
     */
    public int deletePushProTodoDoneByIds(String[] todoIds);

    List<PushProTodoDone> selectTodoDonePushData(String pushStatus);

    Integer retryCountIncreByCondi(List<PushProTodoDone> wrapper);
}
