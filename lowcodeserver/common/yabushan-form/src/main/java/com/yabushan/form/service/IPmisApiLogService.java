package com.yabushan.form.service;

import com.yabushan.form.domain.PmisApiLog;

import java.util.List;

/**
 * API日志Service接口
 *
 * @author yabushan
 * @date 2023-07-29
 */

public interface IPmisApiLogService
{



    /**
    * 清空返回导入数据的集合
    */
    public void clearpmisApiLogInfo();


    /**
     * 查询API日志
     *
     * @param logId API日志ID
     * @return API日志
     */
    public PmisApiLog selectPmisApiLogById(String logId);

    /**
     * 查询API日志列表
     *
     * @param pmisApiLog API日志
     * @return API日志集合
     */
    public List<PmisApiLog> selectPmisApiLogList(PmisApiLog pmisApiLog);

    /**
     * 新增API日志
     *
     * @param pmisApiLog API日志
     * @return 结果
     */
    public int insertPmisApiLog(PmisApiLog pmisApiLog);

    /**
     * 批量新增API日志
     *
     * @param pmisApiLog API日志
     * @return 结果
     */
    public int bathInsertPmisApiLog(List<PmisApiLog> pmisApiLog);

    /**
     * 修改API日志
     *
     * @param pmisApiLog API日志
     * @return 结果
     */
    public int updatePmisApiLog(PmisApiLog pmisApiLog);
    /**
    * 通过id集合修改状态
    */
    public Boolean updateByIds(String ids,String disableEnableState);

    /**
     * 批量删除API日志
     *
     * @param logIds 需要删除的API日志ID
     * @return 结果
     */
    public int deletePmisApiLogByIds(String[] logIds);

    /**
     * 删除API日志信息
     *
     * @param logId API日志ID
     * @return 结果
     */
    public int deletePmisApiLogById(String logId);

    /**
     * 导入API日志Excel数据
     *
     * @param pmisApiLogList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public List<PmisApiLog> importPmisApiLog(List<PmisApiLog> pmisApiLogList, Boolean isUpdateSupport, String operName);
}
