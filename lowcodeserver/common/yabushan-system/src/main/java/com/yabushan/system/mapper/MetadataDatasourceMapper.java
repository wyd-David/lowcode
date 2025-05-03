package com.yabushan.system.mapper;

import java.util.List;
import com.yabushan.system.domain.MetadataDatasource;

/**
 * 数据源Mapper接口
 *
 * @author yabushan
 * @date 2021-01-16
 */
public interface MetadataDatasourceMapper
{
    /**
     * 查询数据源
     *
     * @param datasourceId 数据源ID
     * @return 数据源
     */
    public MetadataDatasource selectMetadataDatasourceById(String datasourceId);

    /**
     * 查询数据源列表
     *
     * @param metadataDatasource 数据源
     * @return 数据源集合
     */
    public List<MetadataDatasource> selectMetadataDatasourceList(MetadataDatasource metadataDatasource);

    /**
     * 新增数据源
     *
     * @param metadataDatasource 数据源
     * @return 结果
     */
    public int insertMetadataDatasource( );

    /**
     * 修改数据源
     *
     * @param metadataDatasource 数据源
     * @return 结果
     */
    public int updateMetadataDatasource(MetadataDatasource metadataDatasource);

    /**
     * 删除数据源
     *
     * @param datasourceId 数据源ID
     * @return 结果
     */
    public int deleteMetadataDatasourceById(String datasourceId);

    /**
     * 批量删除数据源
     *
     * @param datasourceIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteMetadataDatasourceByIds(String[] datasourceIds);
}
