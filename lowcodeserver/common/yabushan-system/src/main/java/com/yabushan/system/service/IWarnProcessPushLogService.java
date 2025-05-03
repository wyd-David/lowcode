package com.yabushan.system.service;

import java.util.List;
import com.yabushan.system.domain.WarnProcessPushLog;

/**
 * 待办推送记录Service接口
 *
 * @author yabushan
 * @date 2022-04-28
 */
public interface IWarnProcessPushLogService
{



    /**
    * 清空返回导入数据的集合
    */
    public void clearwarnProcessPushLogInfo();


    /**
     * 查询待办推送记录
     *
     * @param id 待办推送记录ID
     * @return 待办推送记录
     */
    public WarnProcessPushLog selectWarnProcessPushLogById(Long id);

    /**
     * 查询待办推送记录列表
     *
     * @param warnProcessPushLog 待办推送记录
     * @return 待办推送记录集合
     */
    public List<WarnProcessPushLog> selectWarnProcessPushLogList(WarnProcessPushLog warnProcessPushLog);

    /**
     * 新增待办推送记录
     *
     * @param warnProcessPushLog 待办推送记录
     * @return 结果
     */
    public int insertWarnProcessPushLog(WarnProcessPushLog warnProcessPushLog);

    /**
     * 批量新增待办推送记录
     *
     * @param warnProcessPushLog 待办推送记录
     * @return 结果
     */
    public int bathInsertWarnProcessPushLog(List<WarnProcessPushLog> warnProcessPushLog);

    /**
     * 修改待办推送记录
     *
     * @param warnProcessPushLog 待办推送记录
     * @return 结果
     */
    public int updateWarnProcessPushLog(WarnProcessPushLog warnProcessPushLog);
    /**
    * 通过id集合修改状态
    */
    public Boolean updateByIds(String ids,String disableEnableState);

    /**
     * 批量删除待办推送记录
     *
     * @param ids 需要删除的待办推送记录ID
     * @return 结果
     */
    public int deleteWarnProcessPushLogByIds(Long[] ids);

    /**
     * 删除待办推送记录信息
     *
     * @param id 待办推送记录ID
     * @return 结果
     */
    public int deleteWarnProcessPushLogById(Long id);

    /**
     * 导入待办推送记录Excel数据
     *
     * @param warnProcessPushLogList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public List<WarnProcessPushLog> importWarnProcessPushLog(List<WarnProcessPushLog> warnProcessPushLogList, Boolean isUpdateSupport, String operName);
}
