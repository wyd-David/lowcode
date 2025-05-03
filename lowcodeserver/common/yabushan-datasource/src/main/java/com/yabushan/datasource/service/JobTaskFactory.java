package com.yabushan.datasource.service;

import com.yabushan.datasource.domain.DataflowJob;
import com.yabushan.datasource.domain.DataflowJobTask;

import java.util.List;
import java.util.Map;

public interface JobTaskFactory {
    /**
     * 创建作业
     * @param job
     * @param dataflowJobTaskList
     * @return
     */
    boolean createJob(DataflowJob job, List<DataflowJobTask> dataflowJobTaskList);

    /**
     * 编辑、保存作业
     * @param job
     * @param dataflowJobTaskList
     * @return
     */
    boolean updateJob(DataflowJob job, List<DataflowJobTask> dataflowJobTaskList);

    /**
     * 执行作业
     * @param jobId
     * @return
     */
    boolean executeJob(String jobId);

    /**
     * 查看作业详情
     * @param jobInstanceId
     * @return
     */
    Map viewJobDetail(String jobInstanceId);
}
