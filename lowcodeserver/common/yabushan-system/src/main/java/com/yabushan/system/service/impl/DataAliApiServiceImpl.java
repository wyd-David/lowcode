package com.yabushan.system.service.impl;

import com.yabushan.common.utils.DateUtils;
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.system.domain.DataAliApi;
import com.yabushan.system.mapper.DataAliApiMapper;
import com.yabushan.system.service.IDataAliApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 聚合数据Service业务层处理
 *
 * @author yabushan
 * @date 2021-08-08
 */
@Service
public class DataAliApiServiceImpl implements IDataAliApiService
{
    @Autowired
    private DataAliApiMapper dataAliApiMapper;

    /**
     * 查询聚合数据
     *
     * @param keyId 聚合数据ID
     * @return 聚合数据
     */
    @Override
    public DataAliApi selectDataAliApiById(String keyId)
    {
        return dataAliApiMapper.selectDataAliApiById(keyId);
    }

    /**
     * 查询聚合数据列表
     *
     * @param dataAliApi 聚合数据
     * @return 聚合数据
     */
    @Override
    public List<DataAliApi> selectDataAliApiList(DataAliApi dataAliApi)
    {
        return dataAliApiMapper.selectDataAliApiList(dataAliApi);
    }

    /**
     * 新增聚合数据
     *
     * @param dataAliApi 聚合数据
     * @return 结果
     */
    @Override
    public int insertDataAliApi(DataAliApi dataAliApi)
    {
        dataAliApi.setCreateBy(SecurityUtils.getUsername());
        dataAliApi.setKeyId(StringUtils.getUUID());
        dataAliApi.setCreatedTime(DateUtils.getNowDate());
        dataAliApi.setOwnerName(SecurityUtils.getUsername());
        return dataAliApiMapper.insertDataAliApi(dataAliApi);
    }

    /**
     * 修改聚合数据
     *
     * @param dataAliApi 聚合数据
     * @return 结果
     */
    @Override
    public int updateDataAliApi(DataAliApi dataAliApi)
    {
        dataAliApi.setUpdatedBy(SecurityUtils.getUsername());
        dataAliApi.setUpdatedTime(DateUtils.getNowDate());
        return dataAliApiMapper.updateDataAliApi(dataAliApi);
    }

    /**
     * 批量删除聚合数据
     *
     * @param keyIds 需要删除的聚合数据ID
     * @return 结果
     */
    @Override
    public int deleteDataAliApiByIds(String[] keyIds)
    {
        return dataAliApiMapper.deleteDataAliApiByIds(keyIds);
    }

    /**
     * 删除聚合数据信息
     *
     * @param keyId 聚合数据ID
     * @return 结果
     */
    @Override
    public int deleteDataAliApiById(String keyId)
    {
        return dataAliApiMapper.deleteDataAliApiById(keyId);
    }
}
