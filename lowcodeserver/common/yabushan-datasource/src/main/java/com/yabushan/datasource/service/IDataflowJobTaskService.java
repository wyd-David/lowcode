package com.yabushan.datasource.service;

import java.util.List;
import com.yabushan.datasource.domain.DataflowJobTask;

/**
 * 作业任务Service接口
 *
 * @author yabushan
 * @date 2023-11-19
 */
public interface IDataflowJobTaskService
{



    /**
    * 清空返回导入数据的集合
    */
    public void cleardataflowJobTaskInfo();


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
    public int bathInsertDataflowJobTask(List<DataflowJobTask> dataflowJobTask);

    /**
     * 修改作业任务
     *
     * @param dataflowJobTask 作业任务
     * @return 结果
     */
    public int updateDataflowJobTask(DataflowJobTask dataflowJobTask);
    /**
    * 通过id集合修改状态
    */
    public Boolean updateByIds(String ids,String disableEnableState);

    /**
     * 批量删除作业任务
     *
     * @param jobTaskIds 需要删除的作业任务ID
     * @return 结果
     */
    public int deleteDataflowJobTaskByIds(String[] jobTaskIds);

    /**
     * 删除作业任务信息
     *
     * @param jobTaskId 作业任务ID
     * @return 结果
     */
    public int deleteDataflowJobTaskById(String jobTaskId);

    /**
     * 导入作业任务Excel数据
     *
     * @param dataflowJobTaskList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public List<DataflowJobTask> importDataflowJobTask(List<DataflowJobTask> dataflowJobTaskList, Boolean isUpdateSupport, String operName);
}
