package com.yabushan.system.service;

import java.util.List;
import com.yabushan.system.domain.DataResource;

/**
 * 数据资源Service接口
 *
 * @author yabushan
 * @date 2021-01-16
 */
public interface IDataResourceService
{
    /**
     * 查询数据资源
     *
     * @param resourceId 数据资源ID
     * @return 数据资源
     */
    public DataResource selectDataResourceById(String resourceId);

    /**
     * 查询数据资源列表
     *
     * @param dataResource 数据资源
     * @return 数据资源集合
     */
    public List<DataResource> selectDataResourceList(DataResource dataResource);

    /**
     * 新增数据资源
     *
     * @param resourceIds 数据资源
     * @return 结果
     */
    public int insertDataResource(String[] resourceIds);

    /**
     * 修改数据资源
     *
     * @param dataResource 数据资源
     * @return 结果
     */
    public int updateDataResource(DataResource dataResource);

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
}
