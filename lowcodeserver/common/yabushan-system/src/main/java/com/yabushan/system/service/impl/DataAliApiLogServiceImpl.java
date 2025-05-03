package com.yabushan.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yabushan.system.mapper.DataAliApiLogMapper;
import com.yabushan.system.domain.DataAliApiLog;
import com.yabushan.system.service.IDataAliApiLogService;

/**
 * 聚合日志Service业务层处理
 *
 * @author yabushan
 * @date 2021-08-08
 */
@Service
public class DataAliApiLogServiceImpl implements IDataAliApiLogService
{
    @Autowired
    private DataAliApiLogMapper dataAliApiLogMapper;

    /**
     * 查询聚合日志
     *
     * @param logId 聚合日志ID
     * @return 聚合日志
     */
    @Override
    public DataAliApiLog selectDataAliApiLogById(Long logId)
    {
        return dataAliApiLogMapper.selectDataAliApiLogById(logId);
    }

    /**
     * 查询聚合日志列表
     *
     * @param dataAliApiLog 聚合日志
     * @return 聚合日志
     */
    @Override
    public List<DataAliApiLog> selectDataAliApiLogList(DataAliApiLog dataAliApiLog)
    {
        return dataAliApiLogMapper.selectDataAliApiLogList(dataAliApiLog);
    }

    /**
     * 新增聚合日志
     *
     * @param dataAliApiLog 聚合日志
     * @return 结果
     */
    @Override
    public int insertDataAliApiLog(DataAliApiLog dataAliApiLog)
    {
        return dataAliApiLogMapper.insertDataAliApiLog(dataAliApiLog);
    }

    /**
     * 修改聚合日志
     *
     * @param dataAliApiLog 聚合日志
     * @return 结果
     */
    @Override
    public int updateDataAliApiLog(DataAliApiLog dataAliApiLog)
    {
        return dataAliApiLogMapper.updateDataAliApiLog(dataAliApiLog);
    }

    /**
     * 批量删除聚合日志
     *
     * @param logIds 需要删除的聚合日志ID
     * @return 结果
     */
    @Override
    public int deleteDataAliApiLogByIds(Long[] logIds)
    {
        return dataAliApiLogMapper.deleteDataAliApiLogByIds(logIds);
    }

    /**
     * 删除聚合日志信息
     *
     * @param logId 聚合日志ID
     * @return 结果
     */
    @Override
    public int deleteDataAliApiLogById(Long logId)
    {
        return dataAliApiLogMapper.deleteDataAliApiLogById(logId);
    }
}
