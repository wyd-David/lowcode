package com.yabushan.datasource.mapper;

import java.util.List;
import com.yabushan.datasource.domain.AppConnector;

/**
 * 应用实例连接器记录Mapper接口
 * 
 * @author yabushan
 * @date 2023-11-25
 */
public interface AppConnectorMapper 
{
    /**
     * 查询应用实例连接器记录
     * 
     * @param connectorId 应用实例连接器记录ID
     * @return 应用实例连接器记录
     */
    public AppConnector selectAppConnectorById(String connectorId);

    /**
     * 查询应用实例连接器记录列表
     * 
     * @param appConnector 应用实例连接器记录
     * @return 应用实例连接器记录集合
     */
    public List<AppConnector> selectAppConnectorList(AppConnector appConnector);

    /**
     * 新增应用实例连接器记录
     * 
     * @param appConnector 应用实例连接器记录
     * @return 结果
     */
    public int insertAppConnector(AppConnector appConnector);

    /**
     * 批量新增应用实例连接器记录
     *
     * @param appConnector 应用实例连接器记录
     * @return 结果
     */
    public int bathInsertAppConnector(List<AppConnector> list);

    /**
     * 修改应用实例连接器记录
     * 
     * @param appConnector 应用实例连接器记录
     * @return 结果
     */
    public int updateAppConnector(AppConnector appConnector);

    /**
     * 删除应用实例连接器记录
     * 
     * @param connectorId 应用实例连接器记录ID
     * @return 结果
     */
    public int deleteAppConnectorById(String connectorId);

    /**
     * 批量删除应用实例连接器记录
     * 
     * @param connectorIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteAppConnectorByIds(String[] connectorIds);
}
