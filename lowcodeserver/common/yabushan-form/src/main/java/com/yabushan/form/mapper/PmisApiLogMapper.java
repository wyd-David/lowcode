package com.yabushan.form.mapper;

import com.yabushan.form.domain.PmisApiLog;

import java.util.List;

/**
 * API日志Mapper接口
 * 
 * @author yabushan
 * @date 2023-07-29
 */

public interface PmisApiLogMapper 
{
    /**
     * 查询API日志
     * 
     * @param logId API日志ID
     * @return API日志
     */
    public PmisApiLog selectPmisApiLogById(String logId);

    /**
     * 查询API日志列表
     * 
     * @param pmisApiLog API日志
     * @return API日志集合
     */
    public List<PmisApiLog> selectPmisApiLogList(PmisApiLog pmisApiLog);

    /**
     * 新增API日志
     * 
     * @param pmisApiLog API日志
     * @return 结果
     */
    public int insertPmisApiLog(PmisApiLog pmisApiLog);

    /**
     * 批量新增API日志
     *
     * @param pmisApiLog API日志
     * @return 结果
     */
    public int bathInsertPmisApiLog(List<PmisApiLog> list);

    /**
     * 修改API日志
     * 
     * @param pmisApiLog API日志
     * @return 结果
     */
    public int updatePmisApiLog(PmisApiLog pmisApiLog);

    /**
     * 删除API日志
     * 
     * @param logId API日志ID
     * @return 结果
     */
    public int deletePmisApiLogById(String logId);

    /**
     * 批量删除API日志
     * 
     * @param logIds 需要删除的数据ID
     * @return 结果
     */
    public int deletePmisApiLogByIds(String[] logIds);
}
