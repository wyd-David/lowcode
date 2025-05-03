package com.yabushan.datasource.mapper;

import java.util.List;
import com.yabushan.datasource.domain.DataflowJobTaskInstanceBadData;

/**
 * 作业脏数据Mapper接口
 * 
 * @author yabushan
 * @date 2023-11-19
 */
public interface DataflowJobTaskInstanceBadDataMapper 
{
    /**
     * 查询作业脏数据
     * 
     * @param badDataId 作业脏数据ID
     * @return 作业脏数据
     */
    public DataflowJobTaskInstanceBadData selectDataflowJobTaskInstanceBadDataById(String badDataId);

    /**
     * 查询作业脏数据列表
     * 
     * @param dataflowJobTaskInstanceBadData 作业脏数据
     * @return 作业脏数据集合
     */
    public List<DataflowJobTaskInstanceBadData> selectDataflowJobTaskInstanceBadDataList(DataflowJobTaskInstanceBadData dataflowJobTaskInstanceBadData);

    /**
     * 新增作业脏数据
     * 
     * @param dataflowJobTaskInstanceBadData 作业脏数据
     * @return 结果
     */
    public int insertDataflowJobTaskInstanceBadData(DataflowJobTaskInstanceBadData dataflowJobTaskInstanceBadData);

    /**
     * 批量新增作业脏数据
     *
     * @param dataflowJobTaskInstanceBadData 作业脏数据
     * @return 结果
     */
    public int bathInsertDataflowJobTaskInstanceBadData(List<DataflowJobTaskInstanceBadData> list);

    /**
     * 修改作业脏数据
     * 
     * @param dataflowJobTaskInstanceBadData 作业脏数据
     * @return 结果
     */
    public int updateDataflowJobTaskInstanceBadData(DataflowJobTaskInstanceBadData dataflowJobTaskInstanceBadData);

    /**
     * 删除作业脏数据
     * 
     * @param badDataId 作业脏数据ID
     * @return 结果
     */
    public int deleteDataflowJobTaskInstanceBadDataById(String badDataId);

    /**
     * 批量删除作业脏数据
     * 
     * @param badDataIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteDataflowJobTaskInstanceBadDataByIds(String[] badDataIds);
}
