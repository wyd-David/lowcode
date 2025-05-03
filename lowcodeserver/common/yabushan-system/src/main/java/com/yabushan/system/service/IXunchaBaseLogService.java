package com.yabushan.system.service;

import com.yabushan.system.domain.XunchaBaseLog;

import java.util.List;

/**
 * 操作日志 服务层
 *
 * @author ruoyi
 */
public interface IXunchaBaseLogService
{
    /**
     * 新增操作日志
     *
     * @param xunchaBaseLog 操作日志对象
     */
    public void insertXunchaBaseLog(XunchaBaseLog xunchaBaseLog);

    /**
     * 查询系统操作日志集合
     *
     * @param xunchaBaseLog 操作日志对象
     * @return 操作日志集合
     */
    public List<XunchaBaseLog> selectXunchaBaseLogList(XunchaBaseLog xunchaBaseLog);

    /**
     * 批量删除系统操作日志
     *
     * @param operIds 需要删除的操作日志ID
     * @return 结果
     */
    public int deleteXunchaBaseLogByIds(Long[] operIds);

    /**
     * 查询操作日志详细
     *
     * @param operId 操作ID
     * @return 操作日志对象
     */
    public XunchaBaseLog selectXunchaBaseLogById(Long operId);

}
