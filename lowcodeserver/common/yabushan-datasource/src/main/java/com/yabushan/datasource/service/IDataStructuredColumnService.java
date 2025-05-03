package com.yabushan.datasource.service;

import java.util.List;
import com.yabushan.datasource.domain.DataStructuredColumn;

/**
 * 字段元数据Service接口
 *
 * @author yabushan
 * @date 2023-11-19
 */
public interface IDataStructuredColumnService
{



    /**
     * 清空返回导入数据的集合
     */
    public void cleardataStructuredColumnInfo();


    /**
     * 查询字段元数据
     *
     * @param columnId 字段元数据ID
     * @return 字段元数据
     */
    public DataStructuredColumn selectDataStructuredColumnById(String columnId);

    /**
     * 查询字段元数据列表
     *
     * @param dataStructuredColumn 字段元数据
     * @return 字段元数据集合
     */
    public List<DataStructuredColumn> selectDataStructuredColumnList(DataStructuredColumn dataStructuredColumn);

    /**
     * 新增字段元数据
     *
     * @param dataStructuredColumn 字段元数据
     * @return 结果
     */
    public int insertDataStructuredColumn(DataStructuredColumn dataStructuredColumn);

    /**
     * 批量新增字段元数据
     *
     * @param dataStructuredColumn 字段元数据
     * @return 结果
     */
    public int bathInsertDataStructuredColumn(List<DataStructuredColumn> dataStructuredColumn);

    /**
     * 修改字段元数据
     *
     * @param dataStructuredColumn 字段元数据
     * @return 结果
     */
    public int updateDataStructuredColumn(DataStructuredColumn dataStructuredColumn);
    /**
     * 通过id集合修改状态
     */
    public Boolean updateByIds(String ids,String disableEnableState);

    /**
     * 批量删除字段元数据
     *
     * @param columnIds 需要删除的字段元数据ID
     * @return 结果
     */
    public int deleteDataStructuredColumnByIds(String[] columnIds);

    /**
     * 删除字段元数据信息
     *
     * @param columnId 字段元数据ID
     * @return 结果
     */
    public int deleteDataStructuredColumnById(String columnId);

    /**
     * 导入字段元数据Excel数据
     *
     * @param dataStructuredColumnList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public List<DataStructuredColumn> importDataStructuredColumn(List<DataStructuredColumn> dataStructuredColumnList, Boolean isUpdateSupport, String operName);
}
