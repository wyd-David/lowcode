package com.yabushan.datasource.mapper;

import java.util.List;
import com.yabushan.datasource.domain.DataflowJobInstance;

/**
 * 作业运行实例Mapper接口
 * 
 * @author yabushan
 * @date 2023-11-19
 */
public interface DataflowJobInstanceMapper 
{
    /**
     * 查询作业运行实例
     * 
     * @param jobInstanceId 作业运行实例ID
     * @return 作业运行实例
     */
    public DataflowJobInstance selectDataflowJobInstanceById(String jobInstanceId);

    /**
     * 查询作业运行实例列表
     * 
     * @param dataflowJobInstance 作业运行实例
     * @return 作业运行实例集合
     */
    public List<DataflowJobInstance> selectDataflowJobInstanceList(DataflowJobInstance dataflowJobInstance);

    /**
     * 新增作业运行实例
     * 
     * @param dataflowJobInstance 作业运行实例
     * @return 结果
     */
    public int insertDataflowJobInstance(DataflowJobInstance dataflowJobInstance);

    /**
     * 批量新增作业运行实例
     *
     * @param dataflowJobInstance 作业运行实例
     * @return 结果
     */
    public int bathInsertDataflowJobInstance(List<DataflowJobInstance> list);

    /**
     * 修改作业运行实例
     * 
     * @param dataflowJobInstance 作业运行实例
     * @return 结果
     */
    public int updateDataflowJobInstance(DataflowJobInstance dataflowJobInstance);

    /**
     * 删除作业运行实例
     * 
     * @param jobInstanceId 作业运行实例ID
     * @return 结果
     */
    public int deleteDataflowJobInstanceById(String jobInstanceId);

    /**
     * 批量删除作业运行实例
     * 
     * @param jobInstanceIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteDataflowJobInstanceByIds(String[] jobInstanceIds);
}
