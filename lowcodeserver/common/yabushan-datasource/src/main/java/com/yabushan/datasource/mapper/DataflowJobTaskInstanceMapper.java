package com.yabushan.datasource.mapper;

import java.util.List;
import com.yabushan.datasource.domain.DataflowJobTaskInstance;

/**
 * 作业任务实例Mapper接口
 * 
 * @author yabushan
 * @date 2023-11-19
 */
public interface DataflowJobTaskInstanceMapper 
{
    /**
     * 查询作业任务实例
     * 
     * @param jobTaskInstanceId 作业任务实例ID
     * @return 作业任务实例
     */
    public DataflowJobTaskInstance selectDataflowJobTaskInstanceById(String jobTaskInstanceId);

    /**
     * 查询作业任务实例列表
     * 
     * @param dataflowJobTaskInstance 作业任务实例
     * @return 作业任务实例集合
     */
    public List<DataflowJobTaskInstance> selectDataflowJobTaskInstanceList(DataflowJobTaskInstance dataflowJobTaskInstance);

    /**
     * 新增作业任务实例
     * 
     * @param dataflowJobTaskInstance 作业任务实例
     * @return 结果
     */
    public int insertDataflowJobTaskInstance(DataflowJobTaskInstance dataflowJobTaskInstance);

    /**
     * 批量新增作业任务实例
     *
     * @param dataflowJobTaskInstance 作业任务实例
     * @return 结果
     */
    public int bathInsertDataflowJobTaskInstance(List<DataflowJobTaskInstance> list);

    /**
     * 修改作业任务实例
     * 
     * @param dataflowJobTaskInstance 作业任务实例
     * @return 结果
     */
    public int updateDataflowJobTaskInstance(DataflowJobTaskInstance dataflowJobTaskInstance);

    /**
     * 删除作业任务实例
     * 
     * @param jobTaskInstanceId 作业任务实例ID
     * @return 结果
     */
    public int deleteDataflowJobTaskInstanceById(String jobTaskInstanceId);

    /**
     * 批量删除作业任务实例
     * 
     * @param jobTaskInstanceIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteDataflowJobTaskInstanceByIds(String[] jobTaskInstanceIds);
}
