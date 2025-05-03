package com.yabushan.system.mapper;

import java.util.List;
import com.yabushan.system.domain.DataAliApi;

/**
 * 聚合数据Mapper接口
 * 
 * @author yabushan
 * @date 2021-08-08
 */
public interface DataAliApiMapper 
{
    /**
     * 查询聚合数据
     * 
     * @param keyId 聚合数据ID
     * @return 聚合数据
     */
    public DataAliApi selectDataAliApiById(String keyId);

    /**
     * 查询聚合数据列表
     * 
     * @param dataAliApi 聚合数据
     * @return 聚合数据集合
     */
    public List<DataAliApi> selectDataAliApiList(DataAliApi dataAliApi);

    /**
     * 新增聚合数据
     * 
     * @param dataAliApi 聚合数据
     * @return 结果
     */
    public int insertDataAliApi(DataAliApi dataAliApi);

    /**
     * 修改聚合数据
     * 
     * @param dataAliApi 聚合数据
     * @return 结果
     */
    public int updateDataAliApi(DataAliApi dataAliApi);

    /**
     * 删除聚合数据
     * 
     * @param keyId 聚合数据ID
     * @return 结果
     */
    public int deleteDataAliApiById(String keyId);

    /**
     * 批量删除聚合数据
     * 
     * @param keyIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteDataAliApiByIds(String[] keyIds);
}
