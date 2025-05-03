package com.yabushan.datasource.mapper;

import java.util.List;
import com.yabushan.datasource.domain.DataStructuredColumn;

/**
 * 字段元数据Mapper接口
 *
 * @author yabushan
 * @date 2023-11-19
 */
public interface DataStructuredColumnMapper
{
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
    public int bathInsertDataStructuredColumn(List<DataStructuredColumn> list);

    /**
     * 修改字段元数据
     *
     * @param dataStructuredColumn 字段元数据
     * @return 结果
     */
    public int updateDataStructuredColumn(DataStructuredColumn dataStructuredColumn);

    /**
     * 删除字段元数据
     *
     * @param columnId 字段元数据ID
     * @return 结果
     */
    public int deleteDataStructuredColumnById(String columnId);

    /**
     * 批量删除字段元数据
     *
     * @param columnIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteDataStructuredColumnByIds(String[] columnIds);
}
