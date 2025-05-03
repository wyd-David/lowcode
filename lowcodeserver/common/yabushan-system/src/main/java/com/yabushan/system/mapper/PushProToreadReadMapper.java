package com.yabushan.system.mapper;

import java.util.List;

import com.yabushan.system.domain.PushProToreadRead;
import org.apache.ibatis.annotations.Param;

/**
 * 推送待阅已阅Mapper接口
 *
 * @author yabushan
 * @date 2022-04-28
 */
public interface PushProToreadReadMapper
{
    /**
     * 查询推送待阅已阅
     *
     * @param toreadId 推送待阅已阅ID
     * @return 推送待阅已阅
     */
    public PushProToreadRead selectPushProToreadReadById(String toreadId);

    /**
     * 查询推送待阅已阅列表
     *
     * @param pushProToreadRead 推送待阅已阅
     * @return 推送待阅已阅集合
     */
    public List<PushProToreadRead> selectPushProToreadReadList(PushProToreadRead pushProToreadRead);

    /**
     * 新增推送待阅已阅
     *
     * @param pushProToreadRead 推送待阅已阅
     * @return 结果
     */
    public int insertPushProToreadRead(PushProToreadRead pushProToreadRead);

    /**
     * 批量新增推送待阅已阅
     *
     * @param pushProToreadRead 推送待阅已阅
     * @return 结果
     */
    public int bathInsertPushProToreadRead(List<PushProToreadRead> list);

    /**
     * 修改推送待阅已阅
     *
     * @param pushProToreadRead 推送待阅已阅
     * @return 结果
     */
    public int updatePushProToreadRead(PushProToreadRead pushProToreadRead);

    /**
     * 删除推送待阅已阅
     *
     * @param toreadId 推送待阅已阅ID
     * @return 结果
     */
    public int deletePushProToreadReadById(String toreadId);

    /**
     * 批量删除推送待阅已阅
     *
     * @param toreadIds 需要删除的数据ID
     * @return 结果
     */
    public int deletePushProToreadReadByIds(String[] toreadIds);

    List<PushProToreadRead> selectToreadReadPushData(String pushStatus);

    Integer retryCountIncreByCondi(List<PushProToreadRead> wrapper);
}
