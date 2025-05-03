package com.yabushan.datasource.mapper;

import java.util.List;
import com.yabushan.datasource.domain.DataflowJobInstanceLastState;

/**
 * 作业运行的最后状态Mapper接口
 * 
 * @author yabushan
 * @date 2023-11-19
 */
public interface DataflowJobInstanceLastStateMapper 
{
    /**
     * 查询作业运行的最后状态
     * 
     * @param jobId 作业运行的最后状态ID
     * @return 作业运行的最后状态
     */
    public DataflowJobInstanceLastState selectDataflowJobInstanceLastStateById(String jobId);

    /**
     * 查询作业运行的最后状态列表
     * 
     * @param dataflowJobInstanceLastState 作业运行的最后状态
     * @return 作业运行的最后状态集合
     */
    public List<DataflowJobInstanceLastState> selectDataflowJobInstanceLastStateList(DataflowJobInstanceLastState dataflowJobInstanceLastState);

    /**
     * 新增作业运行的最后状态
     * 
     * @param dataflowJobInstanceLastState 作业运行的最后状态
     * @return 结果
     */
    public int insertDataflowJobInstanceLastState(DataflowJobInstanceLastState dataflowJobInstanceLastState);

    /**
     * 批量新增作业运行的最后状态
     *
     * @param dataflowJobInstanceLastState 作业运行的最后状态
     * @return 结果
     */
    public int bathInsertDataflowJobInstanceLastState(List<DataflowJobInstanceLastState> list);

    /**
     * 修改作业运行的最后状态
     * 
     * @param dataflowJobInstanceLastState 作业运行的最后状态
     * @return 结果
     */
    public int updateDataflowJobInstanceLastState(DataflowJobInstanceLastState dataflowJobInstanceLastState);

    /**
     * 删除作业运行的最后状态
     * 
     * @param jobId 作业运行的最后状态ID
     * @return 结果
     */
    public int deleteDataflowJobInstanceLastStateById(String jobId);

    /**
     * 批量删除作业运行的最后状态
     * 
     * @param jobIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteDataflowJobInstanceLastStateByIds(String[] jobIds);
}
