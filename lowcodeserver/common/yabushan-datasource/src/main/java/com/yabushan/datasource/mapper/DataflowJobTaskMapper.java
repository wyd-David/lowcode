package com.yabushan.datasource.mapper;

import java.util.List;
import com.yabushan.datasource.domain.DataflowJobTask;

/**
 * 作业任务Mapper接口
 * 
 * @author yabushan
 * @date 2023-11-19
 */
public interface DataflowJobTaskMapper 
{
    /**
     * 查询作业任务
     * 
     * @param jobTaskId 作业任务ID
     * @return 作业任务
     */
    public DataflowJobTask selectDataflowJobTaskById(String jobTaskId);

    /**
     * 查询作业任务列表
     * 
     * @param dataflowJobTask 作业任务
     * @return 作业任务集合
     */
    public List<DataflowJobTask> selectDataflowJobTaskList(DataflowJobTask dataflowJobTask);

    /**
     * 新增作业任务
     * 
     * @param dataflowJobTask 作业任务
     * @return 结果
     */
    public int insertDataflowJobTask(DataflowJobTask dataflowJobTask);

    /**
     * 批量新增作业任务
     *
     * @param dataflowJobTask 作业任务
     * @return 结果
     */
    public int bathInsertDataflowJobTask(List<DataflowJobTask> list);

    /**
     * 修改作业任务
     * 
     * @param dataflowJobTask 作业任务
     * @return 结果
     */
    public int updateDataflowJobTask(DataflowJobTask dataflowJobTask);

    /**
     * 删除作业任务
     * 
     * @param jobTaskId 作业任务ID
     * @return 结果
     */
    public int deleteDataflowJobTaskById(String jobTaskId);

    /**
     * 批量删除作业任务
     * 
     * @param jobTaskIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteDataflowJobTaskByIds(String[] jobTaskIds);
}
