package com.yabushan.datasource.mapper;

import java.util.List;
import com.yabushan.datasource.domain.DataflowJob;

/**
 * 作业Mapper接口
 * 
 * @author yabushan
 * @date 2023-11-19
 */
public interface DataflowJobMapper 
{
    /**
     * 查询作业
     * 
     * @param jobId 作业ID
     * @return 作业
     */
    public DataflowJob selectDataflowJobById(String jobId);

    /**
     * 查询作业列表
     * 
     * @param dataflowJob 作业
     * @return 作业集合
     */
    public List<DataflowJob> selectDataflowJobList(DataflowJob dataflowJob);

    /**
     * 新增作业
     * 
     * @param dataflowJob 作业
     * @return 结果
     */
    public int insertDataflowJob(DataflowJob dataflowJob);

    /**
     * 批量新增作业
     *
     * @param dataflowJob 作业
     * @return 结果
     */
    public int bathInsertDataflowJob(List<DataflowJob> list);

    /**
     * 修改作业
     * 
     * @param dataflowJob 作业
     * @return 结果
     */
    public int updateDataflowJob(DataflowJob dataflowJob);

    /**
     * 删除作业
     * 
     * @param jobId 作业ID
     * @return 结果
     */
    public int deleteDataflowJobById(String jobId);

    /**
     * 批量删除作业
     * 
     * @param jobIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteDataflowJobByIds(String[] jobIds);
}
