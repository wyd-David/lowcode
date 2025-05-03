package com.yabushan.datasource.service;

import java.util.List;
import com.yabushan.datasource.domain.DataflowJobInstanceLastState;

/**
 * 作业运行的最后状态Service接口
 *
 * @author yabushan
 * @date 2023-11-19
 */
public interface IDataflowJobInstanceLastStateService
{



    /**
    * 清空返回导入数据的集合
    */
    public void cleardataflowJobInstanceLastStateInfo();


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
    public int bathInsertDataflowJobInstanceLastState(List<DataflowJobInstanceLastState> dataflowJobInstanceLastState);

    /**
     * 修改作业运行的最后状态
     *
     * @param dataflowJobInstanceLastState 作业运行的最后状态
     * @return 结果
     */
    public int updateDataflowJobInstanceLastState(DataflowJobInstanceLastState dataflowJobInstanceLastState);
    /**
    * 通过id集合修改状态
    */
    public Boolean updateByIds(String ids,String disableEnableState);

    /**
     * 批量删除作业运行的最后状态
     *
     * @param jobIds 需要删除的作业运行的最后状态ID
     * @return 结果
     */
    public int deleteDataflowJobInstanceLastStateByIds(String[] jobIds);

    /**
     * 删除作业运行的最后状态信息
     *
     * @param jobId 作业运行的最后状态ID
     * @return 结果
     */
    public int deleteDataflowJobInstanceLastStateById(String jobId);

    /**
     * 导入作业运行的最后状态Excel数据
     *
     * @param dataflowJobInstanceLastStateList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public List<DataflowJobInstanceLastState> importDataflowJobInstanceLastState(List<DataflowJobInstanceLastState> dataflowJobInstanceLastStateList, Boolean isUpdateSupport, String operName);
}
