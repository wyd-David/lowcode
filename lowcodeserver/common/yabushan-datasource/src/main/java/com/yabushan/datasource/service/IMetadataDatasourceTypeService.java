package com.yabushan.datasource.service;

import java.util.List;
import com.yabushan.datasource.domain.MetadataDatasourceType;

/**
 * 数据源类型Service接口
 *
 * @author yabushan
 * @date 2023-11-19
 */
public interface IMetadataDatasourceTypeService
{



    /**
    * 清空返回导入数据的集合
    */
    public void clearmetadataDatasourceTypeInfo();


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
    public int bathInsertMetadataDatasourceType(List<MetadataDatasourceType> metadataDatasourceType);

    /**
     * 修改数据源类型
     *
     * @param metadataDatasourceType 数据源类型
     * @return 结果
     */
    public int updateMetadataDatasourceType(MetadataDatasourceType metadataDatasourceType);
    /**
    * 通过id集合修改状态
    */
    public Boolean updateByIds(String ids,String disableEnableState);

    /**
     * 批量删除数据源类型
     *
     * @param typeCodes 需要删除的数据源类型ID
     * @return 结果
     */
    public int deleteMetadataDatasourceTypeByIds(String[] typeCodes);

    /**
     * 删除数据源类型信息
     *
     * @param typeCode 数据源类型ID
     * @return 结果
     */
    public int deleteMetadataDatasourceTypeById(String typeCode);

    /**
     * 导入数据源类型Excel数据
     *
     * @param metadataDatasourceTypeList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public List<MetadataDatasourceType> importMetadataDatasourceType(List<MetadataDatasourceType> metadataDatasourceTypeList, Boolean isUpdateSupport, String operName);
}
