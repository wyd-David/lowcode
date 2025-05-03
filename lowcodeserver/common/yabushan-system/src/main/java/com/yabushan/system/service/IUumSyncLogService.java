package com.yabushan.system.service;

import java.util.List;
import com.yabushan.system.domain.UumSyncLog;

/**
 * 同步时间记录表Service接口
 *
 * @author yabushan
 * @date 2022-04-27
 */
public interface IUumSyncLogService
{



    /**
    * 清空返回导入数据的集合
    */
    public void clearuumSyncLogInfo();


    /**
     * 查询同步时间记录表
     *
     * @param id 同步时间记录表ID
     * @return 同步时间记录表
     */
    public UumSyncLog selectUumSyncLogById(String id);

    /**
     * 查询同步时间记录表列表
     *
     * @param uumSyncLog 同步时间记录表
     * @return 同步时间记录表集合
     */
    public List<UumSyncLog> selectUumSyncLogList(UumSyncLog uumSyncLog);

    /**
     * 新增同步时间记录表
     *
     * @param uumSyncLog 同步时间记录表
     * @return 结果
     */
    public int insertUumSyncLog(UumSyncLog uumSyncLog);

    /**
     * 批量新增同步时间记录表
     *
     * @param uumSyncLog 同步时间记录表
     * @return 结果
     */
    public int bathInsertUumSyncLog(List<UumSyncLog> uumSyncLog);

    /**
     * 修改同步时间记录表
     *
     * @param uumSyncLog 同步时间记录表
     * @return 结果
     */
    public int updateUumSyncLog(UumSyncLog uumSyncLog);
    /**
    * 通过id集合修改状态
    */
    public Boolean updateByIds(String ids,String disableEnableState);

    /**
     * 批量删除同步时间记录表
     *
     * @param ids 需要删除的同步时间记录表ID
     * @return 结果
     */
    public int deleteUumSyncLogByIds(String[] ids);

    /**
     * 删除同步时间记录表信息
     *
     * @param id 同步时间记录表ID
     * @return 结果
     */
    public int deleteUumSyncLogById(String id);


    /**
     * 导入同步时间记录表Excel数据
     *
     * @param uumSyncLogList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public List<UumSyncLog> importUumSyncLog(List<UumSyncLog> uumSyncLogList, Boolean isUpdateSupport, String operName);
}
