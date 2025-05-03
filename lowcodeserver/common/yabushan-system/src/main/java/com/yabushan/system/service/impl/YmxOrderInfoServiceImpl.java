package com.yabushan.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yabushan.system.mapper.YmxOrderInfoMapper;
import com.yabushan.system.domain.YmxOrderInfo;
import com.yabushan.system.service.IYmxOrderInfoService;

/**
 * 订单Service业务层处理
 *
 * @author yabushan
 * @date 2021-04-02
 */
@Service
public class YmxOrderInfoServiceImpl implements IYmxOrderInfoService
{
    @Autowired
    private YmxOrderInfoMapper ymxOrderInfoMapper;

    /**
     * 查询订单
     *
     * @param orderId 订单ID
     * @return 订单
     */
    @Override
    public YmxOrderInfo selectYmxOrderInfoById(String orderId)
    {
        return ymxOrderInfoMapper.selectYmxOrderInfoById(orderId);
    }

    /**
     * 查询订单列表
     *
     * @param ymxOrderInfo 订单
     * @return 订单
     */
    @Override
    public List<YmxOrderInfo> selectYmxOrderInfoList(YmxOrderInfo ymxOrderInfo)
    {
        return ymxOrderInfoMapper.selectYmxOrderInfoList(ymxOrderInfo);
    }

    /**
     * 新增订单
     *
     * @param ymxOrderInfo 订单
     * @return 结果
     */
    @Override
    public int insertYmxOrderInfo(YmxOrderInfo ymxOrderInfo)
    {
        return ymxOrderInfoMapper.insertYmxOrderInfo(ymxOrderInfo);
    }

    /**
     * 修改订单
     *
     * @param ymxOrderInfo 订单
     * @return 结果
     */
    @Override
    public int updateYmxOrderInfo(YmxOrderInfo ymxOrderInfo)
    {
        return ymxOrderInfoMapper.updateYmxOrderInfo(ymxOrderInfo);
    }

    /**
     * 批量删除订单
     *
     * @param orderIds 需要删除的订单ID
     * @return 结果
     */
    @Override
    public int deleteYmxOrderInfoByIds(String[] orderIds)
    {
        return ymxOrderInfoMapper.deleteYmxOrderInfoByIds(orderIds);
    }

    /**
     * 删除订单信息
     *
     * @param orderId 订单ID
     * @return 结果
     */
    @Override
    public int deleteYmxOrderInfoById(String orderId)
    {
        return ymxOrderInfoMapper.deleteYmxOrderInfoById(orderId);
    }
}
