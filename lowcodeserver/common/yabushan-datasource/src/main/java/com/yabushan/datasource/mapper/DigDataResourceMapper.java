package com.yabushan.datasource.mapper;

import com.yabushan.datasource.domain.DigDataResource;

import java.util.List;

/**
 * 数据资源Mapper接口
 *
 * @author yabushan
 * @date 2023-11-19
 */
public interface DigDataResourceMapper
{
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
     * @param dataResource 数据资源
     * @return 结果
     */
    public int bathInsertDataResource(List<DigDataResource> list);

    /**
     * 修改数据资源
     *
     * @param digDataResource 数据资源
     * @return 结果
     */
    public int updateDataResource(DigDataResource digDataResource);

    /**
     * 删除数据资源
     *
     * @param resourceId 数据资源ID
     * @return 结果
     */
    public int deleteDataResourceById(String resourceId);

    /**
     * 批量删除数据资源
     *
     * @param resourceIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteDataResourceByIds(String[] resourceIds);
}
