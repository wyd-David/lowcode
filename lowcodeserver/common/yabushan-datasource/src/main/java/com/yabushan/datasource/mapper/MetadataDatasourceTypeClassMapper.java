package com.yabushan.datasource.mapper;

import java.util.List;
import com.yabushan.datasource.domain.MetadataDatasourceTypeClass;

/**
 * 数据源类型分类Mapper接口
 * 
 * @author yabushan
 * @date 2023-11-19
 */
public interface MetadataDatasourceTypeClassMapper 
{
    /**
     * 查询数据源类型分类
     * 
     * @param classCode 数据源类型分类ID
     * @return 数据源类型分类
     */
    public MetadataDatasourceTypeClass selectMetadataDatasourceTypeClassById(String classCode);

    /**
     * 查询数据源类型分类列表
     * 
     * @param metadataDatasourceTypeClass 数据源类型分类
     * @return 数据源类型分类集合
     */
    public List<MetadataDatasourceTypeClass> selectMetadataDatasourceTypeClassList(MetadataDatasourceTypeClass metadataDatasourceTypeClass);

    /**
     * 新增数据源类型分类
     * 
     * @param metadataDatasourceTypeClass 数据源类型分类
     * @return 结果
     */
    public int insertMetadataDatasourceTypeClass(MetadataDatasourceTypeClass metadataDatasourceTypeClass);

    /**
     * 批量新增数据源类型分类
     *
     * @param metadataDatasourceTypeClass 数据源类型分类
     * @return 结果
     */
    public int bathInsertMetadataDatasourceTypeClass(List<MetadataDatasourceTypeClass> list);

    /**
     * 修改数据源类型分类
     * 
     * @param metadataDatasourceTypeClass 数据源类型分类
     * @return 结果
     */
    public int updateMetadataDatasourceTypeClass(MetadataDatasourceTypeClass metadataDatasourceTypeClass);

    /**
     * 删除数据源类型分类
     * 
     * @param classCode 数据源类型分类ID
     * @return 结果
     */
    public int deleteMetadataDatasourceTypeClassById(String classCode);

    /**
     * 批量删除数据源类型分类
     * 
     * @param classCodes 需要删除的数据ID
     * @return 结果
     */
    public int deleteMetadataDatasourceTypeClassByIds(String[] classCodes);
}
