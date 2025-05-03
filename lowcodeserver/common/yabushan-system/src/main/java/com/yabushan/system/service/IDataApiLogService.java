package com.yabushan.system.service;

import com.yabushan.system.domain.DataApiLog;

import java.util.List;

/**
 * api日志Service接口
 * 
 * @author yabushan
 * @date 2021-06-13
 */
public interface IDataApiLogService 
{
    /**
     * 查询api日志
     * 
     * @param logId api日志ID
     * @return api日志
     */
    public DataApiLog selectDataApiLogById(Long logId);

    /**
     * 查询api日志列表
     * 
     * @param dataApiLog api日志
     * @return api日志集合
     */
    public List<DataApiLog> selectDataApiLogList(DataApiLog dataApiLog);

    /**
     * 新增api日志
     * 
     * @param dataApiLog api日志
     * @return 结果
     */
    public int insertDataApiLog(DataApiLog dataApiLog);

    /**
     * 修改api日志
     * 
     * @param dataApiLog api日志
     * @return 结果
     */
    public int updateDataApiLog(DataApiLog dataApiLog);

    /**
     * 批量删除api日志
     * 
     * @param logIds 需要删除的api日志ID
     * @return 结果
     */
    public int deleteDataApiLogByIds(Long[] logIds);

    /**
     * 删除api日志信息
     * 
     * @param logId api日志ID
     * @return 结果
     */
    public int deleteDataApiLogById(Long logId);
}
