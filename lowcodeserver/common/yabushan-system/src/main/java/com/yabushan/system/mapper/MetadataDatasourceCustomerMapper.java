package com.yabushan.system.mapper;

import java.util.List;
import com.yabushan.system.domain.MetadataDatasourceCustomer;

/**
 * 用户注册数据源信息Mapper接口
 * 
 * @author yabushan
 * @date 2021-06-13
 */
public interface MetadataDatasourceCustomerMapper 
{
    /**
     * 查询用户注册数据源信息
     * 
     * @param customerId 用户注册数据源信息ID
     * @return 用户注册数据源信息
     */
    public MetadataDatasourceCustomer selectMetadataDatasourceCustomerById(Long customerId);

    /**
     * 查询用户注册数据源信息列表
     * 
     * @param metadataDatasourceCustomer 用户注册数据源信息
     * @return 用户注册数据源信息集合
     */
    public List<MetadataDatasourceCustomer> selectMetadataDatasourceCustomerList(MetadataDatasourceCustomer metadataDatasourceCustomer);

    /**
     * 新增用户注册数据源信息
     * 
     * @param metadataDatasourceCustomer 用户注册数据源信息
     * @return 结果
     */
    public int insertMetadataDatasourceCustomer(MetadataDatasourceCustomer metadataDatasourceCustomer);

    /**
     * 修改用户注册数据源信息
     * 
     * @param metadataDatasourceCustomer 用户注册数据源信息
     * @return 结果
     */
    public int updateMetadataDatasourceCustomer(MetadataDatasourceCustomer metadataDatasourceCustomer);

    /**
     * 删除用户注册数据源信息
     * 
     * @param customerId 用户注册数据源信息ID
     * @return 结果
     */
    public int deleteMetadataDatasourceCustomerById(Long customerId);

    /**
     * 批量删除用户注册数据源信息
     * 
     * @param customerIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteMetadataDatasourceCustomerByIds(Long[] customerIds);
}
