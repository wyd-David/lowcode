package com.yabushan.datasource.service;

import java.util.List;
import com.yabushan.datasource.domain.DataApiResource;

/**
 * 数据服务Service接口
 *
 * @author yabushan
 * @date 2023-11-19
 */
public interface IDataApiResourceService
{



    /**
     * 清空返回导入数据的集合
     */
    public void cleardataApiResourceInfo();


    /**
     * 查询数据服务
     *
     * @param resourceId 数据服务ID
     * @return 数据服务
     */
    public DataApiResource selectDataApiResourceById(String resourceId);

    /**
     * 查询数据服务列表
     *
     * @param dataApiResource 数据服务
     * @return 数据服务集合
     */
    public List<DataApiResource> selectDataApiResourceList(DataApiResource dataApiResource);

    /**
     * 新增数据服务
     *
     * @param dataApiResource 数据服务
     * @return 结果
     */
    public int insertDataApiResource(DataApiResource dataApiResource);

    /**
     * 批量新增数据服务
     *
     * @param dataApiResource 数据服务
     * @return 结果
     */
    public int bathInsertDataApiResource(List<DataApiResource> dataApiResource);

    /**
     * 修改数据服务
     *
     * @param dataApiResource 数据服务
     * @return 结果
     */
    public int updateDataApiResource(DataApiResource dataApiResource);
    /**
     * 通过id集合修改状态
     */
    public Boolean updateByIds(String ids,String disableEnableState);

    /**
     * 批量删除数据服务
     *
     * @param resourceIds 需要删除的数据服务ID
     * @return 结果
     */
    public int deleteDataApiResourceByIds(String[] resourceIds);

    /**
     * 删除数据服务信息
     *
     * @param resourceId 数据服务ID
     * @return 结果
     */
    public int deleteDataApiResourceById(String resourceId);

    /**
     * 导入数据服务Excel数据
     *
     * @param dataApiResourceList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public List<DataApiResource> importDataApiResource(List<DataApiResource> dataApiResourceList, Boolean isUpdateSupport, String operName);
}
