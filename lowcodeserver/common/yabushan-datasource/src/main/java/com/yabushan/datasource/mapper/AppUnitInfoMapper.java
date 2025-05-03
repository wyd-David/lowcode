package com.yabushan.datasource.mapper;

import java.util.List;
import com.yabushan.datasource.domain.AppUnitInfo;

/**
 * 动态数据源管理Mapper接口
 * 
 * @author yabushan
 * @date 2024-01-13
 */
public interface AppUnitInfoMapper 
{
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
     * @param list 动态数据源管理
     * @return 结果
     */
    public int bathInsertAppUnitInfo(List<AppUnitInfo> list);

    /**
     * 修改动态数据源管理
     * 
     * @param appUnitInfo 动态数据源管理
     * @return 结果
     */
    public int updateAppUnitInfo(AppUnitInfo appUnitInfo);

    /**
     * 删除动态数据源管理
     * 
     * @param datasourceid 动态数据源管理ID
     * @return 结果
     */
    public int deleteAppUnitInfoById(String datasourceid);

    /**
     * 批量删除动态数据源管理
     * 
     * @param datasourceids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAppUnitInfoByIds(String[] datasourceids);
}
