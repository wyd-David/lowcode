package com.yabushan.datasource.service;

import java.util.List;
import com.yabushan.datasource.domain.DataflowJobTaskInstance;

/**
 * 作业任务实例Service接口
 *
 * @author yabushan
 * @date 2023-11-19
 */
public interface IDataflowJobTaskInstanceService
{



    /**
    * 清空返回导入数据的集合
    */
    public void cleardataflowJobTaskInstanceInfo();


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
    public int bathInsertDataflowJobTaskInstance(List<DataflowJobTaskInstance> dataflowJobTaskInstance);

    /**
     * 修改作业任务实例
     *
     * @param dataflowJobTaskInstance 作业任务实例
     * @return 结果
     */
    public int updateDataflowJobTaskInstance(DataflowJobTaskInstance dataflowJobTaskInstance);
    /**
    * 通过id集合修改状态
    */
    public Boolean updateByIds(String ids,String disableEnableState);

    /**
     * 批量删除作业任务实例
     *
     * @param jobTaskInstanceIds 需要删除的作业任务实例ID
     * @return 结果
     */
    public int deleteDataflowJobTaskInstanceByIds(String[] jobTaskInstanceIds);

    /**
     * 删除作业任务实例信息
     *
     * @param jobTaskInstanceId 作业任务实例ID
     * @return 结果
     */
    public int deleteDataflowJobTaskInstanceById(String jobTaskInstanceId);

    /**
     * 导入作业任务实例Excel数据
     *
     * @param dataflowJobTaskInstanceList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public List<DataflowJobTaskInstance> importDataflowJobTaskInstance(List<DataflowJobTaskInstance> dataflowJobTaskInstanceList, Boolean isUpdateSupport, String operName);
}
