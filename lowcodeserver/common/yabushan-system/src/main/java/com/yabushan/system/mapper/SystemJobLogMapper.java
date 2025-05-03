package com.yabushan.system.mapper;

import com.yabushan.system.domain.SystemJobLog;

import java.util.List;

/**
 * 定时任务日志 Mapper接口
 * 
 * @author yabushan
 * @date 2021-11-16
 */
public interface SystemJobLogMapper 
{
    /**
     * 查询定时任务日志 
     * 
     * @param id 定时任务日志 ID
     * @return 定时任务日志 
     */
    public SystemJobLog selectSystemJobLogById(String id);

    /**
     * 查询定时任务日志 列表
     * 
     * @param systemJobLog 定时任务日志 
     * @return 定时任务日志 集合
     */
    public List<SystemJobLog> selectSystemJobLogList(SystemJobLog systemJobLog);

    /**
     * 新增定时任务日志 
     * 
     * @param systemJobLog 定时任务日志 
     * @return 结果
     */
    public int insertSystemJobLog(SystemJobLog systemJobLog);

    /**
     * 批量新增定时任务日志 
     *
     * @param systemJobLog 定时任务日志 
     * @return 结果
     */
    public int bathInsertSystemJobLog(List<SystemJobLog> list);

    /**
     * 修改定时任务日志 
     * 
     * @param systemJobLog 定时任务日志 
     * @return 结果
     */
    public int updateSystemJobLog(SystemJobLog systemJobLog);

    /**
     * 删除定时任务日志 
     * 
     * @param id 定时任务日志 ID
     * @return 结果
     */
    public int deleteSystemJobLogById(Long id);

    /**
     * 批量删除定时任务日志 
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSystemJobLogByIds(Long[] ids);
}
