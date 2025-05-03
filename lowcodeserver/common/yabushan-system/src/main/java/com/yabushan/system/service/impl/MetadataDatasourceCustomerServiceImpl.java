package com.yabushan.system.service.impl;

import java.util.Date;
import java.util.List;

import com.yabushan.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yabushan.system.mapper.MetadataDatasourceCustomerMapper;
import com.yabushan.system.domain.MetadataDatasourceCustomer;
import com.yabushan.system.service.IMetadataDatasourceCustomerService;

/**
 * 用户注册数据源信息Service业务层处理
 *
 * @author yabushan
 * @date 2021-06-13
 */
@Service
public class MetadataDatasourceCustomerServiceImpl implements IMetadataDatasourceCustomerService
{
    @Autowired
    private MetadataDatasourceCustomerMapper metadataDatasourceCustomerMapper;

    /**
     * 查询用户注册数据源信息
     *
     * @param customerId 用户注册数据源信息ID
     * @return 用户注册数据源信息
     */
    @Override
    public MetadataDatasourceCustomer selectMetadataDatasourceCustomerById(Long customerId)
    {
        return metadataDatasourceCustomerMapper.selectMetadataDatasourceCustomerById(customerId);
    }

    /**
     * 查询用户注册数据源信息列表
     *
     * @param metadataDatasourceCustomer 用户注册数据源信息
     * @return 用户注册数据源信息
     */
    @Override
    public List<MetadataDatasourceCustomer> selectMetadataDatasourceCustomerList(MetadataDatasourceCustomer metadataDatasourceCustomer)
    {
        return metadataDatasourceCustomerMapper.selectMetadataDatasourceCustomerList(metadataDatasourceCustomer);
    }

    /**
     * 新增用户注册数据源信息
     *
     * @param metadataDatasourceCustomer 用户注册数据源信息
     * @return 结果
     */
    @Override
    public int insertMetadataDatasourceCustomer(MetadataDatasourceCustomer metadataDatasourceCustomer)
    {
        metadataDatasourceCustomer.setCreateId(SecurityUtils.getLoginUser().getUser().getUserId());
        metadataDatasourceCustomer.setCreateBy(SecurityUtils.getUsername());
        metadataDatasourceCustomer.setCreteTime(new Date());
        return metadataDatasourceCustomerMapper.insertMetadataDatasourceCustomer(metadataDatasourceCustomer);
    }

    /**
     * 修改用户注册数据源信息
     *
     * @param metadataDatasourceCustomer 用户注册数据源信息
     * @return 结果
     */
    @Override
    public int updateMetadataDatasourceCustomer(MetadataDatasourceCustomer metadataDatasourceCustomer)
    {
        return metadataDatasourceCustomerMapper.updateMetadataDatasourceCustomer(metadataDatasourceCustomer);
    }

    /**
     * 批量删除用户注册数据源信息
     *
     * @param customerIds 需要删除的用户注册数据源信息ID
     * @return 结果
     */
    @Override
    public int deleteMetadataDatasourceCustomerByIds(Long[] customerIds)
    {
        return metadataDatasourceCustomerMapper.deleteMetadataDatasourceCustomerByIds(customerIds);
    }

    /**
     * 删除用户注册数据源信息信息
     *
     * @param customerId 用户注册数据源信息ID
     * @return 结果
     */
    @Override
    public int deleteMetadataDatasourceCustomerById(Long customerId)
    {
        return metadataDatasourceCustomerMapper.deleteMetadataDatasourceCustomerById(customerId);
    }
}
