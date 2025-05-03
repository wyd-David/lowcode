package com.yabushan.web.service.impl;

import com.yabushan.web.mapper.ManagementBackgroundMapper;
import com.yabushan.web.model.entity.YmxOrderInfo;
import com.yabushan.web.service.ManagementBackgroundApiService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description ManagementBackgroundApiServiceImpl
 * @Author huihui
 * @Date 2021-03-30 14:03:45
 * @Version 1.0
 */
@Service
public class ManagementBackgroundApiServiceImpl implements ManagementBackgroundApiService {

    @Resource
    private ManagementBackgroundMapper backgroundMapper;

    /**
     * 查询订单
     *
     * @param orderId 订单ID
     * @return 订单
     */
    @Override
    public YmxOrderInfo selectYmxOrderInfoById(String orderId) {
        return backgroundMapper.selectYmxOrderInfoById(orderId);
    }

    /**
     * 查询订单列表
     *
     * @param ymxOrderInfo 订单
     * @return 订单
     */
    @Override
    public List<YmxOrderInfo> selectYmxOrderInfoList(YmxOrderInfo ymxOrderInfo) {
        return backgroundMapper.selectYmxOrderInfoList(ymxOrderInfo);
    }

    /**
     * 新增订单
     *
     * @param ymxOrderInfo 订单
     * @return 结果
     */
    @Override
    public int insertYmxOrderInfo(YmxOrderInfo ymxOrderInfo) {
        return backgroundMapper.insertYmxOrderInfo(ymxOrderInfo);
    }

    /**
     * 修改订单
     *
     * @param ymxOrderInfo 订单
     * @return 结果
     */
    @Override
    public int updateYmxOrderInfo(YmxOrderInfo ymxOrderInfo) {
        return backgroundMapper.updateYmxOrderInfo(ymxOrderInfo);
    }

    /**
     * 批量删除订单
     *
     * @param orderIds 需要删除的订单ID
     * @return 结果
     */
    @Override
    public int deleteYmxOrderInfoByIds(String[] orderIds) {
        return backgroundMapper.deleteYmxOrderInfoByIds(orderIds);
    }

    /**
     * 删除订单信息
     *
     * @param orderId 订单ID
     * @return 结果
     */
    @Override
    public int deleteYmxOrderInfoById(String orderId) {
        return backgroundMapper.deleteYmxOrderInfoById(orderId);
    }

}
