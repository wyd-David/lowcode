package com.yabushan.datasource.service;

import java.util.List;
import com.yabushan.datasource.domain.AppUnitInfo;

/**
 * 动态数据源管理Service接口
 *
 * @author yabushan
 * @date 2024-01-13
 */
public interface IAppUnitInfoService
{



    /**
    * 清空返回导入数据的集合
    */
    
    public void clearappUnitInfoInfo();


    /**
     * 查询动态数据源管理
     *
     * @param datasourceid 动态数据源管理ID
     * @return 动态数据源管理
     */
    public AppUnitInfo selectAppUnitInfoById(String datasourceid);
    
    public AppUnitInfo selectAppUnitInfoByIdNoPassword(String datasourceid);
    /**
     * 查询动态数据源管理列表
     *
     * @param appUnitInfo 动态数据源管理
     * @return 动态数据源管理集合
     */
    public List<AppUnitInfo> selectAppUnitInfoList(AppUnitInfo appUnitInfo);

    /**
     * 新增动态数据源管理
     *
     * @param appUnitInfo 动态数据源管理
     * @return 结果
     */
    public int insertAppUnitInfo(AppUnitInfo appUnitInfo);

    /**
     * 批量新增动态数据源管理
     *
     * @param appUnitInfo 动态数据源管理
     * @return 结果
     */
    public int bathInsertAppUnitInfo(List<AppUnitInfo> appUnitInfo);

    /**
     * 修改动态数据源管理
     *
     * @param appUnitInfo 动态数据源管理
     * @return 结果
     */
    public int updateAppUnitInfo(AppUnitInfo appUnitInfo);
    /**
    * 通过id集合修改状态
    */
    public Boolean updateByIds(String ids,String disableEnableState);

    /**
     * 批量删除动态数据源管理
     *
     * @param datasourceids 需要删除的动态数据源管理ID
     * @return 结果
     */
    public int deleteAppUnitInfoByIds(String[] datasourceids);

    /**
     * 删除动态数据源管理信息
     *
     * @param datasourceid 动态数据源管理ID
     * @return 结果
     */
    public int deleteAppUnitInfoById(String datasourceid);

    /**
     * 导入动态数据源管理Excel数据
     *
     * @param appUnitInfoList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public List<AppUnitInfo> importAppUnitInfo(List<AppUnitInfo> appUnitInfoList, Boolean isUpdateSupport, String operName);
    
    
 
}
