package com.yabushan.system.service.impl;

import com.yabushan.common.utils.DateUtils;
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.system.domain.DataApiLog;
import com.yabushan.system.mapper.DataApiLogMapper;
import com.yabushan.system.service.IDataApiLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * api日志Service业务层处理
 *
 * @author yabushan
 * @date 2021-06-13
 */
@Service
public class DataApiLogServiceImpl implements IDataApiLogService
{
    @Autowired
    private DataApiLogMapper dataApiLogMapper;

    /**
     * 查询api日志
     *
     * @param logId api日志ID
     * @return api日志
     */
    @Override
    public DataApiLog selectDataApiLogById(Long logId)
    {
        return dataApiLogMapper.selectDataApiLogById(logId);
    }

    /**
     * 查询api日志列表
     *
     * @param dataApiLog api日志
     * @return api日志
     */
    @Override
    public List<DataApiLog> selectDataApiLogList(DataApiLog dataApiLog)
    {
        dataApiLog.setCreateBy(SecurityUtils.getUsername());
        return dataApiLogMapper.selectDataApiLogList(dataApiLog);
    }

    /**
     * 新增api日志
     *
     * @param dataApiLog api日志
     * @return 结果
     */
    @Override
    public int insertDataApiLog(DataApiLog dataApiLog)
    {
        dataApiLog.setCreateTime(DateUtils.getNowDate());
        return dataApiLogMapper.insertDataApiLog(dataApiLog);
    }

    /**
     * 修改api日志
     *
     * @param dataApiLog api日志
     * @return 结果
     */
    @Override
    public int updateDataApiLog(DataApiLog dataApiLog)
    {
        return dataApiLogMapper.updateDataApiLog(dataApiLog);
    }

    /**
     * 批量删除api日志
     *
     * @param logIds 需要删除的api日志ID
     * @return 结果
     */
    @Override
    public int deleteDataApiLogByIds(Long[] logIds)
    {
        return dataApiLogMapper.deleteDataApiLogByIds(logIds);
    }

    /**
     * 删除api日志信息
     *
     * @param logId api日志ID
     * @return 结果
     */
    @Override
    public int deleteDataApiLogById(Long logId)
    {
        return dataApiLogMapper.deleteDataApiLogById(logId);
    }
}
