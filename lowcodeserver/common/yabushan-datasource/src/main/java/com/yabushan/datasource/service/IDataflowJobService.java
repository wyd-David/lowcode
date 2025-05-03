package com.yabushan.datasource.service;

import java.util.List;
import com.yabushan.datasource.domain.DataflowJob;

/**
 * 作业Service接口
 *
 * @author yabushan
 * @date 2023-11-19
 */
public interface IDataflowJobService
{



    /**
    * 清空返回导入数据的集合
    */
    public void cleardataflowJobInfo();


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
    public int bathInsertDataflowJob(List<DataflowJob> dataflowJob);

    /**
     * 修改作业
     *
     * @param dataflowJob 作业
     * @return 结果
     */
    public int updateDataflowJob(DataflowJob dataflowJob);
    /**
    * 通过id集合修改状态
    */
    public Boolean updateByIds(String ids,String disableEnableState);

    /**
     * 批量删除作业
     *
     * @param jobIds 需要删除的作业ID
     * @return 结果
     */
    public int deleteDataflowJobByIds(String[] jobIds);

    /**
     * 删除作业信息
     *
     * @param jobId 作业ID
     * @return 结果
     */
    public int deleteDataflowJobById(String jobId);

    /**
     * 导入作业Excel数据
     *
     * @param dataflowJobList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public List<DataflowJob> importDataflowJob(List<DataflowJob> dataflowJobList, Boolean isUpdateSupport, String operName);
}
