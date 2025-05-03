package com.yabushan.system.mapper;

import java.util.List;
import java.util.Map;

import com.yabushan.system.domain.XunchaBaseLog;

/**
 * 巡查基本数据操作日志Mapper接口
 *
 * @author yabushan
 * @date 2022-07-08
 */
public interface XunchaBaseLogMapper
{
    /**
     * 查询巡查基本数据操作日志
     *
     * @param id 巡查基本数据操作日志ID
     * @return 巡查基本数据操作日志
     */
    public XunchaBaseLog selectXunchaBaseLogById(Long id);

    /**
     * 查询巡查基本数据操作日志列表
     *
     * @param xunchaBaseLog 巡查基本数据操作日志
     * @return 巡查基本数据操作日志集合
     */
    public List<XunchaBaseLog> selectXunchaBaseLogList(XunchaBaseLog xunchaBaseLog);

    /**
     * 新增巡查基本数据操作日志
     *
     * @param xunchaBaseLog 巡查基本数据操作日志
     * @return 结果
     */
    public int insertXunchaBaseLog(XunchaBaseLog xunchaBaseLog);

    /**
     * 批量新增巡查基本数据操作日志
     *
     * @param list 巡查基本数据操作日志
     * @return 结果
     */
    public int bathInsertXunchaBaseLog(List<XunchaBaseLog> list);

    /**
     * 修改巡查基本数据操作日志
     *
     * @param xunchaBaseLog 巡查基本数据操作日志
     * @return 结果
     */
    public int updateXunchaBaseLog(XunchaBaseLog xunchaBaseLog);

    /**
     * 删除巡查基本数据操作日志
     *
     * @param id 巡查基本数据操作日志ID
     * @return 结果
     */
    public int deleteXunchaBaseLogById(Long id);

    /**
     * 批量删除巡查基本数据操作日志
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteXunchaBaseLogByIds(Long[] ids);

    public List<Map<String, Object>> getTableColumns(String tableName);
}
