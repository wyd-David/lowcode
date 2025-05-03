package com.yabushan.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yabushan.system.mapper.YmxLogInfoMapper;
import com.yabushan.system.domain.YmxLogInfo;
import com.yabushan.system.service.IYmxLogInfoService;

/**
 * 日志Service业务层处理
 *
 * @author yabushan
 * @date 2021-04-02
 */
@Service
public class YmxLogInfoServiceImpl implements IYmxLogInfoService
{
    @Autowired
    private YmxLogInfoMapper ymxLogInfoMapper;

    /**
     * 查询日志
     *
     * @param logId 日志ID
     * @return 日志
     */
    @Override
    public YmxLogInfo selectYmxLogInfoById(String logId)
    {
        return ymxLogInfoMapper.selectYmxLogInfoById(logId);
    }

    /**
     * 查询日志列表
     *
     * @param ymxLogInfo 日志
     * @return 日志
     */
    @Override
    public List<YmxLogInfo> selectYmxLogInfoList(YmxLogInfo ymxLogInfo)
    {
        return ymxLogInfoMapper.selectYmxLogInfoList(ymxLogInfo);
    }

    /**
     * 新增日志
     *
     * @param ymxLogInfo 日志
     * @return 结果
     */
    @Override
    public int insertYmxLogInfo(YmxLogInfo ymxLogInfo)
    {
        return ymxLogInfoMapper.insertYmxLogInfo(ymxLogInfo);
    }

    /**
     * 修改日志
     *
     * @param ymxLogInfo 日志
     * @return 结果
     */
    @Override
    public int updateYmxLogInfo(YmxLogInfo ymxLogInfo)
    {
        return ymxLogInfoMapper.updateYmxLogInfo(ymxLogInfo);
    }

    /**
     * 批量删除日志
     *
     * @param logIds 需要删除的日志ID
     * @return 结果
     */
    @Override
    public int deleteYmxLogInfoByIds(String[] logIds)
    {
        return ymxLogInfoMapper.deleteYmxLogInfoByIds(logIds);
    }

    /**
     * 删除日志信息
     *
     * @param logId 日志ID
     * @return 结果
     */
    @Override
    public int deleteYmxLogInfoById(String logId)
    {
        return ymxLogInfoMapper.deleteYmxLogInfoById(logId);
    }
}
