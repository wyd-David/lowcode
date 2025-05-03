package com.yabushan.datasource.service;

import java.util.List;
import com.yabushan.datasource.domain.AppConnector;

/**
 * 应用实例连接器记录Service接口
 *
 * @author yabushan
 * @date 2023-11-25
 */
public interface IAppConnectorService
{



    /**
    * 清空返回导入数据的集合
    */
    public void clearappConnectorInfo();


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
    public int bathInsertAppConnector(List<AppConnector> appConnector);

    /**
     * 修改应用实例连接器记录
     *
     * @param appConnector 应用实例连接器记录
     * @return 结果
     */
    public int updateAppConnector(AppConnector appConnector);
    /**
    * 通过id集合修改状态
    */
    public Boolean updateByIds(String ids,String disableEnableState);

    /**
     * 批量删除应用实例连接器记录
     *
     * @param connectorIds 需要删除的应用实例连接器记录ID
     * @return 结果
     */
    public int deleteAppConnectorByIds(String[] connectorIds);

    /**
     * 删除应用实例连接器记录信息
     *
     * @param connectorId 应用实例连接器记录ID
     * @return 结果
     */
    public int deleteAppConnectorById(String connectorId);

    /**
     * 导入应用实例连接器记录Excel数据
     *
     * @param appConnectorList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public List<AppConnector> importAppConnector(List<AppConnector> appConnectorList, Boolean isUpdateSupport, String operName);
}
