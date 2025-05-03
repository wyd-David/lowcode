package com.yabushan.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yabushan.system.mapper.YmxCustomerGiftInfoMapper;
import com.yabushan.system.domain.YmxCustomerGiftInfo;
import com.yabushan.system.service.IYmxCustomerGiftInfoService;

/**
 * 客户礼物Service业务层处理
 *
 * @author yabushan
 * @date 2021-04-02
 */
@Service
public class YmxCustomerGiftInfoServiceImpl implements IYmxCustomerGiftInfoService
{
    @Autowired
    private YmxCustomerGiftInfoMapper ymxCustomerGiftInfoMapper;

    /**
     * 查询客户礼物
     *
     * @param customerGiftId 客户礼物ID
     * @return 客户礼物
     */
    @Override
    public YmxCustomerGiftInfo selectYmxCustomerGiftInfoById(String customerGiftId)
    {
        return ymxCustomerGiftInfoMapper.selectYmxCustomerGiftInfoById(customerGiftId);
    }

    /**
     * 查询客户礼物列表
     *
     * @param ymxCustomerGiftInfo 客户礼物
     * @return 客户礼物
     */
    @Override
    public List<YmxCustomerGiftInfo> selectYmxCustomerGiftInfoList(YmxCustomerGiftInfo ymxCustomerGiftInfo)
    {
        return ymxCustomerGiftInfoMapper.selectYmxCustomerGiftInfoList(ymxCustomerGiftInfo);
    }

    /**
     * 新增客户礼物
     *
     * @param ymxCustomerGiftInfo 客户礼物
     * @return 结果
     */
    @Override
    public int insertYmxCustomerGiftInfo(YmxCustomerGiftInfo ymxCustomerGiftInfo)
    {
        return ymxCustomerGiftInfoMapper.insertYmxCustomerGiftInfo(ymxCustomerGiftInfo);
    }

    /**
     * 修改客户礼物
     *
     * @param ymxCustomerGiftInfo 客户礼物
     * @return 结果
     */
    @Override
    public int updateYmxCustomerGiftInfo(YmxCustomerGiftInfo ymxCustomerGiftInfo)
    {
        return ymxCustomerGiftInfoMapper.updateYmxCustomerGiftInfo(ymxCustomerGiftInfo);
    }

    /**
     * 批量删除客户礼物
     *
     * @param customerGiftIds 需要删除的客户礼物ID
     * @return 结果
     */
    @Override
    public int deleteYmxCustomerGiftInfoByIds(String[] customerGiftIds)
    {
        return ymxCustomerGiftInfoMapper.deleteYmxCustomerGiftInfoByIds(customerGiftIds);
    }

    /**
     * 删除客户礼物信息
     *
     * @param customerGiftId 客户礼物ID
     * @return 结果
     */
    @Override
    public int deleteYmxCustomerGiftInfoById(String customerGiftId)
    {
        return ymxCustomerGiftInfoMapper.deleteYmxCustomerGiftInfoById(customerGiftId);
    }
}
