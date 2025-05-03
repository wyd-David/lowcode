package com.yabushan.datasource.mapper;

import java.util.List;
import com.yabushan.datasource.domain.MetadataDatasourceType;

/**
 * 数据源类型Mapper接口
 * 
 * @author yabushan
 * @date 2023-11-19
 */
public interface MetadataDatasourceTypeMapper 
{
    /**
     * 查询数据源类型
     * 
     * @param typeCode 数据源类型ID
     * @return 数据源类型
     */
    public MetadataDatasourceType selectMetadataDatasourceTypeById(String typeCode);

    /**
     * 查询数据源类型列表
     * 
     * @param metadataDatasourceType 数据源类型
     * @return 数据源类型集合
     */
    public List<MetadataDatasourceType> selectMetadataDatasourceTypeList(MetadataDatasourceType metadataDatasourceType);

    /**
     * 新增数据源类型
     * 
     * @param metadataDatasourceType 数据源类型
     * @return 结果
     */
    public int insertMetadataDatasourceType(MetadataDatasourceType metadataDatasourceType);

    /**
     * 批量新增数据源类型
     *
     * @param metadataDatasourceType 数据源类型
     * @return 结果
     */
    public int bathInsertMetadataDatasourceType(List<MetadataDatasourceType> list);

    /**
     * 修改数据源类型
     * 
     * @param metadataDatasourceType 数据源类型
     * @return 结果
     */
    public int updateMetadataDatasourceType(MetadataDatasourceType metadataDatasourceType);

    /**
     * 删除数据源类型
     * 
     * @param typeCode 数据源类型ID
     * @return 结果
     */
    public int deleteMetadataDatasourceTypeById(String typeCode);

    /**
     * 批量删除数据源类型
     * 
     * @param typeCodes 需要删除的数据ID
     * @return 结果
     */
    public int deleteMetadataDatasourceTypeByIds(String[] typeCodes);
}
