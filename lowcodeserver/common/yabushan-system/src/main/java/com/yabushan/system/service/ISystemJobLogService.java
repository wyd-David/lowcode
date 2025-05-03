package com.yabushan.system.service;

import com.yabushan.system.domain.SystemJobLog;

import java.util.List;

/**
 * 定时任务日志 Service接口
 *
 * @author yabushan
 * @date 2021-11-16
 */
public interface ISystemJobLogService
{



    /**
    * 清空返回导入数据的集合
    */
    public void clearsystemJobLogInfo();


    /**
     * 查询定时任务日志 
     *
     * @param id 定时任务日志 ID
     * @return 定时任务日志 
     */
    public SystemJobLog selectSystemJobLogById(String id);

    /**
     * 查询定时任务日志 列表
     *
     * @param systemJobLog 定时任务日志 
     * @return 定时任务日志 集合
     */
    public List<SystemJobLog> selectSystemJobLogList(SystemJobLog systemJobLog);

    /**
     * 新增定时任务日志 
     *
     * @param systemJobLog 定时任务日志 
     * @return 结果
     */
    public int insertSystemJobLog(SystemJobLog systemJobLog);

    /**
     * 批量新增定时任务日志 
     *
     * @param systemJobLog 定时任务日志 
     * @return 结果
     */
    public int bathInsertSystemJobLog(List<SystemJobLog> systemJobLog);

    /**
     * 修改定时任务日志 
     *
     * @param systemJobLog 定时任务日志 
     * @return 结果
     */
    public int updateSystemJobLog(SystemJobLog systemJobLog);
    /**
    * 通过id集合修改状态
    */
    public Boolean updateByIds(String ids, String disableEnableState);

    /**
     * 批量删除定时任务日志 
     *
     * @param ids 需要删除的定时任务日志 ID
     * @return 结果
     */
    public int deleteSystemJobLogByIds(Long[] ids);

    /**
     * 删除定时任务日志 信息
     *
     * @param id 定时任务日志 ID
     * @return 结果
     */
    public int deleteSystemJobLogById(Long id);


    /**
     * 导入定时任务日志 Excel数据
     *
     * @param systemJobLogList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public List<SystemJobLog> importSystemJobLog(List<SystemJobLog> systemJobLogList, Boolean isUpdateSupport, String operName);
}
