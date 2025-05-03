package com.yabushan.datasource.service;

import com.yabushan.datasource.domain.DigDataResource;

import java.util.List;

/**
 * 数据资源Service接口
 *
 * @author yabushan
 * @date 2023-11-19
 */
public interface IDigDataResourceService
{



    /**
     * 清空返回导入数据的集合
     */
    public void cleardataResourceInfo();


    /**
     * 查询数据资源
     *
     * @param resourceId 数据资源ID
     * @return 数据资源
     */
    public DigDataResource selectDataResourceById(String resourceId);

    /**
     * 查询数据资源列表
     *
     * @param digDataResource 数据资源
     * @return 数据资源集合
     */
    public List<DigDataResource> selectDataResourceList(DigDataResource digDataResource);

    /**
     * 新增数据资源
     *
     * @param digDataResource 数据资源
     * @return 结果
     */
    public int insertDataResource(DigDataResource digDataResource);

    /**
     * 批量新增数据资源
     *
     * @param digDataResource 数据资源
     * @return 结果
     */
    public int bathInsertDataResource(List<DigDataResource> digDataResource);

    /**
     * 修改数据资源
     *
     * @param digDataResource 数据资源
     * @return 结果
     */
    public int updateDataResource(DigDataResource digDataResource);
    /**
     * 通过id集合修改状态
     */
    public Boolean updateByIds(String ids,String disableEnableState);

    /**
     * 批量删除数据资源
     *
     * @param resourceIds 需要删除的数据资源ID
     * @return 结果
     */
    public int deleteDataResourceByIds(String[] resourceIds);

    /**
     * 删除数据资源信息
     *
     * @param resourceId 数据资源ID
     * @return 结果
     */
    public int deleteDataResourceById(String resourceId);

    /**
     * 导入数据资源Excel数据
     *
     * @param digDataResourceList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public List<DigDataResource> importDataResource(List<DigDataResource> digDataResourceList, Boolean isUpdateSupport, String operName);
}
