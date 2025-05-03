package com.yabushan.system.service;

import java.util.List;
import com.yabushan.system.domain.YmxCustomerInformationInfo;

/**
 * 客户信息Service接口
 * 
 * @author yabushan
 * @date 2021-04-02
 */
public interface IYmxCustomerInformationInfoService 
{
    /**
     * 查询客户信息
     * 
     * @param customerInformationId 客户信息ID
     * @return 客户信息
     */
    public YmxCustomerInformationInfo selectYmxCustomerInformationInfoById(String customerInformationId);

    /**
     * 查询客户信息列表
     * 
     * @param ymxCustomerInformationInfo 客户信息
     * @return 客户信息集合
     */
    public List<YmxCustomerInformationInfo> selectYmxCustomerInformationInfoList(YmxCustomerInformationInfo ymxCustomerInformationInfo);

    /**
     * 新增客户信息
     * 
     * @param ymxCustomerInformationInfo 客户信息
     * @return 结果
     */
    public int insertYmxCustomerInformationInfo(YmxCustomerInformationInfo ymxCustomerInformationInfo);

    /**
     * 修改客户信息
     * 
     * @param ymxCustomerInformationInfo 客户信息
     * @return 结果
     */
    public int updateYmxCustomerInformationInfo(YmxCustomerInformationInfo ymxCustomerInformationInfo);

    /**
     * 批量删除客户信息
     * 
     * @param customerInformationIds 需要删除的客户信息ID
     * @return 结果
     */
    public int deleteYmxCustomerInformationInfoByIds(String[] customerInformationIds);

    /**
     * 删除客户信息信息
     * 
     * @param customerInformationId 客户信息ID
     * @return 结果
     */
    public int deleteYmxCustomerInformationInfoById(String customerInformationId);
}
