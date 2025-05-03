package com.yabushan.datasource.mapper;

import java.util.List;
import com.yabushan.datasource.domain.DataApiResource;

/**
 * 数据服务Mapper接口
 *
 * @author yabushan
 * @date 2023-11-19
 */
public interface DataApiResourceMapper
{
    /**
     * 查询数据服务
     *
     * @param resourceId 数据服务ID
     * @return 数据服务
     */
    public DataApiResource selectDataApiResourceById(String resourceId);

    /**
     * 查询数据服务列表
     *
     * @param dataApiResource 数据服务
     * @return 数据服务集合
     */
    public List<DataApiResource> selectDataApiResourceList(DataApiResource dataApiResource);

    /**
     * 新增数据服务
     *
     * @param dataApiResource 数据服务
     * @return 结果
     */
    public int insertDataApiResource(DataApiResource dataApiResource);

    /**
     * 批量新增数据服务
     *
     * @param dataApiResource 数据服务
     * @return 结果
     */
    public int bathInsertDataApiResource(List<DataApiResource> list);

    /**
     * 修改数据服务
     *
     * @param dataApiResource 数据服务
     * @return 结果
     */
    public int updateDataApiResource(DataApiResource dataApiResource);

    /**
     * 删除数据服务
     *
     * @param resourceId 数据服务ID
     * @return 结果
     */
    public int deleteDataApiResourceById(String resourceId);

    /**
     * 批量删除数据服务
     *
     * @param resourceIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteDataApiResourceByIds(String[] resourceIds);
}
