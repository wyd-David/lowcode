package com.yabushan.system.mapper;

import java.util.List;
import com.yabushan.system.domain.WarnProcessPushLog;

/**
 * 待办推送记录Mapper接口
 * 
 * @author yabushan
 * @date 2022-04-28
 */
public interface WarnProcessPushLogMapper 
{
    /**
     * 查询待办推送记录
     * 
     * @param id 待办推送记录ID
     * @return 待办推送记录
     */
    public WarnProcessPushLog selectWarnProcessPushLogById(Long id);

    /**
     * 查询待办推送记录列表
     * 
     * @param warnProcessPushLog 待办推送记录
     * @return 待办推送记录集合
     */
    public List<WarnProcessPushLog> selectWarnProcessPushLogList(WarnProcessPushLog warnProcessPushLog);

    /**
     * 新增待办推送记录
     * 
     * @param warnProcessPushLog 待办推送记录
     * @return 结果
     */
    public int insertWarnProcessPushLog(WarnProcessPushLog warnProcessPushLog);

    /**
     * 批量新增待办推送记录
     *
     * @param warnProcessPushLog 待办推送记录
     * @return 结果
     */
    public int bathInsertWarnProcessPushLog(List<WarnProcessPushLog> list);

    /**
     * 修改待办推送记录
     * 
     * @param warnProcessPushLog 待办推送记录
     * @return 结果
     */
    public int updateWarnProcessPushLog(WarnProcessPushLog warnProcessPushLog);

    /**
     * 删除待办推送记录
     * 
     * @param id 待办推送记录ID
     * @return 结果
     */
    public int deleteWarnProcessPushLogById(Long id);

    /**
     * 批量删除待办推送记录
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWarnProcessPushLogByIds(Long[] ids);
}
