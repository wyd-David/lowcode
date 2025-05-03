package com.yabushan.system.mapper;

import java.util.List;
import com.yabushan.system.domain.DataAliApiLog;

/**
 * 聚合日志Mapper接口
 * 
 * @author yabushan
 * @date 2021-08-08
 */
public interface DataAliApiLogMapper 
{
    /**
     * 查询聚合日志
     * 
     * @param logId 聚合日志ID
     * @return 聚合日志
     */
    public DataAliApiLog selectDataAliApiLogById(Long logId);

    /**
     * 查询聚合日志列表
     * 
     * @param dataAliApiLog 聚合日志
     * @return 聚合日志集合
     */
    public List<DataAliApiLog> selectDataAliApiLogList(DataAliApiLog dataAliApiLog);

    /**
     * 新增聚合日志
     * 
     * @param dataAliApiLog 聚合日志
     * @return 结果
     */
    public int insertDataAliApiLog(DataAliApiLog dataAliApiLog);

    /**
     * 修改聚合日志
     * 
     * @param dataAliApiLog 聚合日志
     * @return 结果
     */
    public int updateDataAliApiLog(DataAliApiLog dataAliApiLog);

    /**
     * 删除聚合日志
     * 
     * @param logId 聚合日志ID
     * @return 结果
     */
    public int deleteDataAliApiLogById(Long logId);

    /**
     * 批量删除聚合日志
     * 
     * @param logIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteDataAliApiLogByIds(Long[] logIds);
}
