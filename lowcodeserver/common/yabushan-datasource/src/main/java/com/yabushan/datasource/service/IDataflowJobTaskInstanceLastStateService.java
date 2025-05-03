package com.yabushan.datasource.service;

import java.util.List;
import com.yabushan.datasource.domain.DataflowJobTaskInstanceLastState;

/**
 * 节点运行的最后状态Service接口
 *
 * @author yabushan
 * @date 2023-11-19
 */
public interface IDataflowJobTaskInstanceLastStateService
{



    /**
    * 清空返回导入数据的集合
    */
    public void cleardataflowJobTaskInstanceLastStateInfo();


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
    public int bathInsertDataflowJobTaskInstanceLastState(List<DataflowJobTaskInstanceLastState> dataflowJobTaskInstanceLastState);

    /**
     * 修改节点运行的最后状态
     *
     * @param dataflowJobTaskInstanceLastState 节点运行的最后状态
     * @return 结果
     */
    public int updateDataflowJobTaskInstanceLastState(DataflowJobTaskInstanceLastState dataflowJobTaskInstanceLastState);
    /**
    * 通过id集合修改状态
    */
    public Boolean updateByIds(String ids,String disableEnableState);

    /**
     * 批量删除节点运行的最后状态
     *
     * @param jobTaskIds 需要删除的节点运行的最后状态ID
     * @return 结果
     */
    public int deleteDataflowJobTaskInstanceLastStateByIds(String[] jobTaskIds);

    /**
     * 删除节点运行的最后状态信息
     *
     * @param jobTaskId 节点运行的最后状态ID
     * @return 结果
     */
    public int deleteDataflowJobTaskInstanceLastStateById(String jobTaskId);

    /**
     * 导入节点运行的最后状态Excel数据
     *
     * @param dataflowJobTaskInstanceLastStateList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public List<DataflowJobTaskInstanceLastState> importDataflowJobTaskInstanceLastState(List<DataflowJobTaskInstanceLastState> dataflowJobTaskInstanceLastStateList, Boolean isUpdateSupport, String operName);
}
