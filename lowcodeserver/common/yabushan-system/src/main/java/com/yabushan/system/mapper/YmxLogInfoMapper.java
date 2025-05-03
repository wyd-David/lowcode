package com.yabushan.system.mapper;

import java.util.List;
import com.yabushan.system.domain.YmxLogInfo;

/**
 * 日志Mapper接口
 * 
 * @author yabushan
 * @date 2021-04-02
 */
public interface YmxLogInfoMapper 
{
    /**
     * 查询日志
     * 
     * @param logId 日志ID
     * @return 日志
     */
    public YmxLogInfo selectYmxLogInfoById(String logId);

    /**
     * 查询日志列表
     * 
     * @param ymxLogInfo 日志
     * @return 日志集合
     */
    public List<YmxLogInfo> selectYmxLogInfoList(YmxLogInfo ymxLogInfo);

    /**
     * 新增日志
     * 
     * @param ymxLogInfo 日志
     * @return 结果
     */
    public int insertYmxLogInfo(YmxLogInfo ymxLogInfo);

    /**
     * 修改日志
     * 
     * @param ymxLogInfo 日志
     * @return 结果
     */
    public int updateYmxLogInfo(YmxLogInfo ymxLogInfo);

    /**
     * 删除日志
     * 
     * @param logId 日志ID
     * @return 结果
     */
    public int deleteYmxLogInfoById(String logId);

    /**
     * 批量删除日志
     * 
     * @param logIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteYmxLogInfoByIds(String[] logIds);
}
