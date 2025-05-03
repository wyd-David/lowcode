package com.yabushan.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yabushan.system.mapper.YmxCustomerInformationInfoMapper;
import com.yabushan.system.domain.YmxCustomerInformationInfo;
import com.yabushan.system.service.IYmxCustomerInformationInfoService;

/**
 * 客户信息Service业务层处理
 *
 * @author yabushan
 * @date 2021-04-02
 */
@Service
public class YmxCustomerInformationInfoServiceImpl implements IYmxCustomerInformationInfoService
{
    @Autowired
    private YmxCustomerInformationInfoMapper ymxCustomerInformationInfoMapper;

    /**
     * 查询客户信息
     *
     * @param customerInformationId 客户信息ID
     * @return 客户信息
     */
    @Override
    public YmxCustomerInformationInfo selectYmxCustomerInformationInfoById(String customerInformationId)
    {
        return ymxCustomerInformationInfoMapper.selectYmxCustomerInformationInfoById(customerInformationId);
    }

    /**
     * 查询客户信息列表
     *
     * @param ymxCustomerInformationInfo 客户信息
     * @return 客户信息
     */
    @Override
    public List<YmxCustomerInformationInfo> selectYmxCustomerInformationInfoList(YmxCustomerInformationInfo ymxCustomerInformationInfo)
    {
        return ymxCustomerInformationInfoMapper.selectYmxCustomerInformationInfoList(ymxCustomerInformationInfo);
    }

    /**
     * 新增客户信息
     *
     * @param ymxCustomerInformationInfo 客户信息
     * @return 结果
     */
    @Override
    public int insertYmxCustomerInformationInfo(YmxCustomerInformationInfo ymxCustomerInformationInfo)
    {
        return ymxCustomerInformationInfoMapper.insertYmxCustomerInformationInfo(ymxCustomerInformationInfo);
    }

    /**
     * 修改客户信息
     *
     * @param ymxCustomerInformationInfo 客户信息
     * @return 结果
     */
    @Override
    public int updateYmxCustomerInformationInfo(YmxCustomerInformationInfo ymxCustomerInformationInfo)
    {
        return ymxCustomerInformationInfoMapper.updateYmxCustomerInformationInfo(ymxCustomerInformationInfo);
    }

    /**
     * 批量删除客户信息
     *
     * @param customerInformationIds 需要删除的客户信息ID
     * @return 结果
     */
    @Override
    public int deleteYmxCustomerInformationInfoByIds(String[] customerInformationIds)
    {
        return ymxCustomerInformationInfoMapper.deleteYmxCustomerInformationInfoByIds(customerInformationIds);
    }

    /**
     * 删除客户信息信息
     *
     * @param customerInformationId 客户信息ID
     * @return 结果
     */
    @Override
    public int deleteYmxCustomerInformationInfoById(String customerInformationId)
    {
        return ymxCustomerInformationInfoMapper.deleteYmxCustomerInformationInfoById(customerInformationId);
    }
}
