package com.yabushan.datasource.service;

import java.util.List;
import com.yabushan.datasource.domain.MetadataDatasourceTypeClass;

/**
 * 数据源类型分类Service接口
 *
 * @author yabushan
 * @date 2023-11-19
 */
public interface IMetadataDatasourceTypeClassService
{



    /**
    * 清空返回导入数据的集合
    */
    public void clearmetadataDatasourceTypeClassInfo();


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
    public int bathInsertMetadataDatasourceTypeClass(List<MetadataDatasourceTypeClass> metadataDatasourceTypeClass);

    /**
     * 修改数据源类型分类
     *
     * @param metadataDatasourceTypeClass 数据源类型分类
     * @return 结果
     */
    public int updateMetadataDatasourceTypeClass(MetadataDatasourceTypeClass metadataDatasourceTypeClass);
    /**
    * 通过id集合修改状态
    */
    public Boolean updateByIds(String ids,String disableEnableState);

    /**
     * 批量删除数据源类型分类
     *
     * @param classCodes 需要删除的数据源类型分类ID
     * @return 结果
     */
    public int deleteMetadataDatasourceTypeClassByIds(String[] classCodes);

    /**
     * 删除数据源类型分类信息
     *
     * @param classCode 数据源类型分类ID
     * @return 结果
     */
    public int deleteMetadataDatasourceTypeClassById(String classCode);

    /**
     * 导入数据源类型分类Excel数据
     *
     * @param metadataDatasourceTypeClassList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public List<MetadataDatasourceTypeClass> importMetadataDatasourceTypeClass(List<MetadataDatasourceTypeClass> metadataDatasourceTypeClassList, Boolean isUpdateSupport, String operName);
}
