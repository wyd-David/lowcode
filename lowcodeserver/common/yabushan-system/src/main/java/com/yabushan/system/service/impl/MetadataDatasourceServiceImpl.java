package com.yabushan.system.service.impl;

import com.yabushan.system.domain.MetadataDatasource;
import com.yabushan.system.mapper.MetadataDatasourceMapper;
import com.yabushan.system.service.IMetadataDatasourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据源Service业务层处理
 *
 * @author yabushan
 * @date 2021-01-16
 */
@Service
public class MetadataDatasourceServiceImpl implements IMetadataDatasourceService
{
    @Autowired
    private MetadataDatasourceMapper metadataDatasourceMapper;

    /**
     * 查询数据源
     *
     * @param datasourceId 数据源ID
     * @return 数据源
     */
    @Override
    public MetadataDatasource selectMetadataDatasourceById(String datasourceId)
    {
        return metadataDatasourceMapper.selectMetadataDatasourceById(datasourceId);
    }

    /**
     * 查询数据源列表
     *
     * @param metadataDatasource 数据源
     * @return 数据源
     */
    @Override
    public List<MetadataDatasource> selectMetadataDatasourceList(MetadataDatasource metadataDatasource)
    {
        return metadataDatasourceMapper.selectMetadataDatasourceList(metadataDatasource);
    }

    /**
     * 新增数据源
     *
     * @return 结果
     */
    @Override
    public int insertMetadataDatasource()
    {
        return metadataDatasourceMapper.insertMetadataDatasource();
    }

    /**
     * 修改数据源
     *
     * @param metadataDatasource 数据源
     * @return 结果
     */
    @Override
    public int updateMetadataDatasource(MetadataDatasource metadataDatasource)
    {
        return metadataDatasourceMapper.updateMetadataDatasource(metadataDatasource);
    }

    /**
     * 批量删除数据源
     *
     * @param datasourceIds 需要删除的数据源ID
     * @return 结果
     */
    @Override
    public int deleteMetadataDatasourceByIds(String[] datasourceIds)
    {
        return metadataDatasourceMapper.deleteMetadataDatasourceByIds(datasourceIds);
    }

    /**
     * 删除数据源信息
     *
     * @param datasourceId 数据源ID
     * @return 结果
     */
    @Override
    public int deleteMetadataDatasourceById(String datasourceId)
    {
        return metadataDatasourceMapper.deleteMetadataDatasourceById(datasourceId);
    }
}
