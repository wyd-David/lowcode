package com.yabushan.datasource.service;

import java.util.List;
import com.yabushan.datasource.domain.DataflowJobInstance;

/**
 * 作业运行实例Service接口
 *
 * @author yabushan
 * @date 2023-11-19
 */
public interface IDataflowJobInstanceService
{



    /**
    * 清空返回导入数据的集合
    */
    public void cleardataflowJobInstanceInfo();


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
    public int bathInsertDataflowJobInstance(List<DataflowJobInstance> dataflowJobInstance);

    /**
     * 修改作业运行实例
     *
     * @param dataflowJobInstance 作业运行实例
     * @return 结果
     */
    public int updateDataflowJobInstance(DataflowJobInstance dataflowJobInstance);
    /**
    * 通过id集合修改状态
    */
    public Boolean updateByIds(String ids,String disableEnableState);

    /**
     * 批量删除作业运行实例
     *
     * @param jobInstanceIds 需要删除的作业运行实例ID
     * @return 结果
     */
    public int deleteDataflowJobInstanceByIds(String[] jobInstanceIds);

    /**
     * 删除作业运行实例信息
     *
     * @param jobInstanceId 作业运行实例ID
     * @return 结果
     */
    public int deleteDataflowJobInstanceById(String jobInstanceId);

    /**
     * 导入作业运行实例Excel数据
     *
     * @param dataflowJobInstanceList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public List<DataflowJobInstance> importDataflowJobInstance(List<DataflowJobInstance> dataflowJobInstanceList, Boolean isUpdateSupport, String operName);
}
