package com.yabushan.datasource.mapper;

import java.util.List;
import com.yabushan.datasource.domain.DataflowJobTaskInstanceLastState;

/**
 * 节点运行的最后状态Mapper接口
 * 
 * @author yabushan
 * @date 2023-11-19
 */
public interface DataflowJobTaskInstanceLastStateMapper 
{
    /**
     * 查询节点运行的最后状态
     * 
     * @param jobTaskId 节点运行的最后状态ID
     * @return 节点运行的最后状态
     */
    public DataflowJobTaskInstanceLastState selectDataflowJobTaskInstanceLastStateById(String jobTaskId);

    /**
     * 查询节点运行的最后状态列表
     * 
     * @param dataflowJobTaskInstanceLastState 节点运行的最后状态
     * @return 节点运行的最后状态集合
     */
    public List<DataflowJobTaskInstanceLastState> selectDataflowJobTaskInstanceLastStateList(DataflowJobTaskInstanceLastState dataflowJobTaskInstanceLastState);

    /**
     * 新增节点运行的最后状态
     * 
     * @param dataflowJobTaskInstanceLastState 节点运行的最后状态
     * @return 结果
     */
    public int insertDataflowJobTaskInstanceLastState(DataflowJobTaskInstanceLastState dataflowJobTaskInstanceLastState);

    /**
     * 批量新增节点运行的最后状态
     *
     * @param dataflowJobTaskInstanceLastState 节点运行的最后状态
     * @return 结果
     */
    public int bathInsertDataflowJobTaskInstanceLastState(List<DataflowJobTaskInstanceLastState> list);

    /**
     * 修改节点运行的最后状态
     * 
     * @param dataflowJobTaskInstanceLastState 节点运行的最后状态
     * @return 结果
     */
    public int updateDataflowJobTaskInstanceLastState(DataflowJobTaskInstanceLastState dataflowJobTaskInstanceLastState);

    /**
     * 删除节点运行的最后状态
     * 
     * @param jobTaskId 节点运行的最后状态ID
     * @return 结果
     */
    public int deleteDataflowJobTaskInstanceLastStateById(String jobTaskId);

    /**
     * 批量删除节点运行的最后状态
     * 
     * @param jobTaskIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteDataflowJobTaskInstanceLastStateByIds(String[] jobTaskIds);
}
