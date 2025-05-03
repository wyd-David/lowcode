package com.yabushan.system.service;

import java.util.List;
import com.yabushan.system.domain.SystemJobLogBatch;

/**
 * 日志数据批次 Service接口
 *
 * @author yabushan
 * @date 2021-11-16
 */
public interface ISystemJobLogBatchService
{



    /**
    * 清空返回导入数据的集合
    */
    public void clearsystemJobLogBatchInfo();


    /**
     * 查询日志数据批次 
     *
     * @param id 日志数据批次 ID
     * @return 日志数据批次 
     */
    public SystemJobLogBatch selectSystemJobLogBatchById(String id);

    /**
     * 查询日志数据批次 列表
     *
     * @param systemJobLogBatch 日志数据批次 
     * @return 日志数据批次 集合
     */
    public List<SystemJobLogBatch> selectSystemJobLogBatchList(SystemJobLogBatch systemJobLogBatch);

    /**
     * 新增日志数据批次 
     *
     * @param systemJobLogBatch 日志数据批次 
     * @return 结果
     */
    public int insertSystemJobLogBatch(SystemJobLogBatch systemJobLogBatch);

    /**
     * 批量新增日志数据批次 
     *
     * @param systemJobLogBatch 日志数据批次 
     * @return 结果
     */
    public int bathInsertSystemJobLogBatch(List<SystemJobLogBatch> systemJobLogBatch);

    /**
     * 修改日志数据批次 
     *
     * @param systemJobLogBatch 日志数据批次 
     * @return 结果
     */
    public int updateSystemJobLogBatch(SystemJobLogBatch systemJobLogBatch);
    /**
    * 通过id集合修改状态
    */
    public Boolean updateByIds(String ids, String disableEnableState);

    /**
     * 批量删除日志数据批次 
     *
     * @param ids 需要删除的日志数据批次 ID
     * @return 结果
     */
    public int deleteSystemJobLogBatchByIds(Long[] ids);

    /**
     * 删除日志数据批次 信息
     *
     * @param id 日志数据批次 ID
     * @return 结果
     */
    public int deleteSystemJobLogBatchById(Long id);


    /**
     * 导入日志数据批次 Excel数据
     *
     * @param systemJobLogBatchList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public List<SystemJobLogBatch> importSystemJobLogBatch(List<SystemJobLogBatch> systemJobLogBatchList, Boolean isUpdateSupport, String operName);
}
