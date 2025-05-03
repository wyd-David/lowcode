package com.yabushan.datasource.service;

import java.util.List;
import com.yabushan.datasource.domain.DataflowJobTaskInstanceBadData;

/**
 * 作业脏数据Service接口
 *
 * @author yabushan
 * @date 2023-11-19
 */
public interface IDataflowJobTaskInstanceBadDataService
{



    /**
    * 清空返回导入数据的集合
    */
    public void cleardataflowJobTaskInstanceBadDataInfo();


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
    public int bathInsertDataflowJobTaskInstanceBadData(List<DataflowJobTaskInstanceBadData> dataflowJobTaskInstanceBadData);

    /**
     * 修改作业脏数据
     *
     * @param dataflowJobTaskInstanceBadData 作业脏数据
     * @return 结果
     */
    public int updateDataflowJobTaskInstanceBadData(DataflowJobTaskInstanceBadData dataflowJobTaskInstanceBadData);
    /**
    * 通过id集合修改状态
    */
    public Boolean updateByIds(String ids,String disableEnableState);

    /**
     * 批量删除作业脏数据
     *
     * @param badDataIds 需要删除的作业脏数据ID
     * @return 结果
     */
    public int deleteDataflowJobTaskInstanceBadDataByIds(String[] badDataIds);

    /**
     * 删除作业脏数据信息
     *
     * @param badDataId 作业脏数据ID
     * @return 结果
     */
    public int deleteDataflowJobTaskInstanceBadDataById(String badDataId);

    /**
     * 导入作业脏数据Excel数据
     *
     * @param dataflowJobTaskInstanceBadDataList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public List<DataflowJobTaskInstanceBadData> importDataflowJobTaskInstanceBadData(List<DataflowJobTaskInstanceBadData> dataflowJobTaskInstanceBadDataList, Boolean isUpdateSupport, String operName);
}
