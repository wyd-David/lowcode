package com.yabushan.system.mapper;

import com.yabushan.system.domain.SystemJobLogBatch;

import java.util.List;

/**
 * 日志数据批次 Mapper接口
 * 
 * @author yabushan
 * @date 2021-11-16
 */
public interface SystemJobLogBatchMapper 
{
    /**
     * 查询日志数据批次 
     * 
     * @param id 日志数据批次 ID
     * @return 日志数据批次 
     */
    public SystemJobLogBatch selectSystemJobLogBatchById(String id);

    /**
     * 查询日志数据批次 列表
     * 
     * @param systemJobLogBatch 日志数据批次 
     * @return 日志数据批次 集合
     */
    public List<SystemJobLogBatch> selectSystemJobLogBatchList(SystemJobLogBatch systemJobLogBatch);

    /**
     * 新增日志数据批次 
     * 
     * @param systemJobLogBatch 日志数据批次 
     * @return 结果
     */
    public int insertSystemJobLogBatch(SystemJobLogBatch systemJobLogBatch);

    /**
     * 批量新增日志数据批次 
     *
     * @param systemJobLogBatch 日志数据批次 
     * @return 结果
     */
    public int bathInsertSystemJobLogBatch(List<SystemJobLogBatch> list);

    /**
     * 修改日志数据批次 
     * 
     * @param systemJobLogBatch 日志数据批次 
     * @return 结果
     */
    public int updateSystemJobLogBatch(SystemJobLogBatch systemJobLogBatch);

    /**
     * 删除日志数据批次 
     * 
     * @param id 日志数据批次 ID
     * @return 结果
     */
    public int deleteSystemJobLogBatchById(Long id);

    /**
     * 批量删除日志数据批次 
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSystemJobLogBatchByIds(Long[] ids);
}
