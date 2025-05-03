package com.yabushan.system.service.impl;

import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.system.domain.DataResource;
import com.yabushan.system.mapper.DataResourceMapper;
import com.yabushan.system.service.IDataResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据资源Service业务层处理
 *
 * @author yabushan
 * @date 2021-01-16
 */
@Service
public class DataResourceServiceImpl implements IDataResourceService
{
    @Autowired
    private DataResourceMapper dataResourceMapper;

    /**
     * 查询数据资源
     *
     * @param resourceId 数据资源ID
     * @return 数据资源
     */
    @Override
    public DataResource selectDataResourceById(String resourceId)
    {
        return dataResourceMapper.selectDataResourceById(resourceId);
    }

    /**
     * 查询数据资源列表
     *
     * @param dataResource 数据资源
     * @return 数据资源
     */
    @Override
    public List<DataResource> selectDataResourceList(DataResource dataResource)
    {
        if(!SecurityUtils.getUsername().equals("admin")){
            dataResource.setDatasourceId(SecurityUtils.getLoginUser().getUser().getUserId()+"");
        }
        return dataResourceMapper.selectDataResourceList(dataResource);
    }

    /**
     * 新增数据资源
     *
     * @return 结果
     */
    @Override
    public int insertDataResource(String[] resourceIds)
    {
        return dataResourceMapper.insertDataResource(resourceIds);
    }

    /**
     * 修改数据资源
     *
     * @param dataResource 数据资源
     * @return 结果
     */
    @Override
    public int updateDataResource(DataResource dataResource)
    {
        return dataResourceMapper.updateDataResource(dataResource);
    }

    /**
     * 批量删除数据资源
     *
     * @param resourceIds 需要删除的数据资源ID
     * @return 结果
     */
    @Override
    public int deleteDataResourceByIds(String[] resourceIds)
    {
        return dataResourceMapper.deleteDataResourceByIds(resourceIds);
    }

    /**
     * 删除数据资源信息
     *
     * @param resourceId 数据资源ID
     * @return 结果
     */
    @Override
    public int deleteDataResourceById(String resourceId)
    {
        return dataResourceMapper.deleteDataResourceById(resourceId);
    }
}
