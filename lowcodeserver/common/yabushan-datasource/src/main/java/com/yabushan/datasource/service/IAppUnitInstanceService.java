package com.yabushan.datasource.service;

import java.util.List;
import com.yabushan.datasource.domain.AppUnitInstance;

/**
 * 分配实例Service接口
 *
 * @author yabushan
 * @date 2023-11-19
 */
public interface IAppUnitInstanceService
{



    /**
    * 清空返回导入数据的集合
    */
    public void clearappUnitInstanceInfo();


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
    public int bathInsertAppUnitInstance(List<AppUnitInstance> appUnitInstance);

    /**
     * 修改分配实例
     *
     * @param appUnitInstance 分配实例
     * @return 结果
     */
    public int updateAppUnitInstance(AppUnitInstance appUnitInstance);
    /**
    * 通过id集合修改状态
    */
    public Boolean updateByIds(String ids,String disableEnableState);

    /**
     * 批量删除分配实例
     *
     * @param instanceIds 需要删除的分配实例ID
     * @return 结果
     */
    public int deleteAppUnitInstanceByIds(String[] instanceIds);

    /**
     * 删除分配实例信息
     *
     * @param instanceId 分配实例ID
     * @return 结果
     */
    public int deleteAppUnitInstanceById(String instanceId);

    /**
     * 导入分配实例Excel数据
     *
     * @param appUnitInstanceList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public List<AppUnitInstance> importAppUnitInstance(List<AppUnitInstance> appUnitInstanceList, Boolean isUpdateSupport, String operName);
}
