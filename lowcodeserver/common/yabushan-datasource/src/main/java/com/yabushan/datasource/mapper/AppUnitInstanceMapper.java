package com.yabushan.datasource.mapper;

import java.util.List;
import com.yabushan.datasource.domain.AppUnitInstance;

/**
 * 分配实例Mapper接口
 * 
 * @author yabushan
 * @date 2023-11-19
 */
public interface AppUnitInstanceMapper 
{
    /**
     * 查询分配实例
     * 
     * @param instanceId 分配实例ID
     * @return 分配实例
     */
    public AppUnitInstance selectAppUnitInstanceById(String instanceId);

    /**
     * 查询分配实例列表
     * 
     * @param appUnitInstance 分配实例
     * @return 分配实例集合
     */
    public List<AppUnitInstance> selectAppUnitInstanceList(AppUnitInstance appUnitInstance);

    /**
     * 新增分配实例
     * 
     * @param appUnitInstance 分配实例
     * @return 结果
     */
    public int insertAppUnitInstance(AppUnitInstance appUnitInstance);

    /**
     * 批量新增分配实例
     *
     * @param appUnitInstance 分配实例
     * @return 结果
     */
    public int bathInsertAppUnitInstance(List<AppUnitInstance> list);

    /**
     * 修改分配实例
     * 
     * @param appUnitInstance 分配实例
     * @return 结果
     */
    public int updateAppUnitInstance(AppUnitInstance appUnitInstance);

    /**
     * 删除分配实例
     * 
     * @param instanceId 分配实例ID
     * @return 结果
     */
    public int deleteAppUnitInstanceById(String instanceId);

    /**
     * 批量删除分配实例
     * 
     * @param instanceIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteAppUnitInstanceByIds(String[] instanceIds);
}
