package com.yabushan.datasource.mapper;

import java.util.List;
import com.yabushan.datasource.domain.MetadataDatasourceTypeParam;

/**
 * 数据源类型参数Mapper接口
 * 
 * @author yabushan
 * @date 2023-11-19
 */
public interface MetadataDatasourceTypeParamMapper 
{
    /**
     * 查询数据源类型参数
     * 
     * @param paramId 数据源类型参数ID
     * @return 数据源类型参数
     */
    public MetadataDatasourceTypeParam selectMetadataDatasourceTypeParamById(String paramId);

    /**
     * 查询数据源类型参数列表
     * 
     * @param metadataDatasourceTypeParam 数据源类型参数
     * @return 数据源类型参数集合
     */
    public List<MetadataDatasourceTypeParam> selectMetadataDatasourceTypeParamList(MetadataDatasourceTypeParam metadataDatasourceTypeParam);

    /**
     * 新增数据源类型参数
     * 
     * @param metadataDatasourceTypeParam 数据源类型参数
     * @return 结果
     */
    public int insertMetadataDatasourceTypeParam(MetadataDatasourceTypeParam metadataDatasourceTypeParam);

    /**
     * 批量新增数据源类型参数
     *
     * @param metadataDatasourceTypeParam 数据源类型参数
     * @return 结果
     */
    public int bathInsertMetadataDatasourceTypeParam(List<MetadataDatasourceTypeParam> list);

    /**
     * 修改数据源类型参数
     * 
     * @param metadataDatasourceTypeParam 数据源类型参数
     * @return 结果
     */
    public int updateMetadataDatasourceTypeParam(MetadataDatasourceTypeParam metadataDatasourceTypeParam);

    /**
     * 删除数据源类型参数
     * 
     * @param paramId 数据源类型参数ID
     * @return 结果
     */
    public int deleteMetadataDatasourceTypeParamById(String paramId);

    /**
     * 批量删除数据源类型参数
     * 
     * @param paramIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteMetadataDatasourceTypeParamByIds(String[] paramIds);
}
