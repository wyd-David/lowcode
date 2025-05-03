package com.yabushan.web.service;

import com.yabushan.web.model.entity.YmxOrderInfo;

import java.util.List;

/**
 * @Description ManagementBackgroundApiService
 * @Author huihui
 * @Date 2021-03-30 13:56:32
 * @Version 1.0
 */
public interface ManagementBackgroundApiService {

    /**
     * 查询订单
     *
     * @param orderId 订单ID
     * @return 订单
     */
    public YmxOrderInfo selectYmxOrderInfoById(String orderId);

    /**
     * 查询订单列表
     *
     * @param ymxOrderInfo 订单
     * @return 订单集合
     */
    public List<YmxOrderInfo> selectYmxOrderInfoList(YmxOrderInfo ymxOrderInfo);

    /**
     * 新增订单
     *
     * @param ymxOrderInfo 订单
     * @return 结果
     */
    public int insertYmxOrderInfo(YmxOrderInfo ymxOrderInfo);

    /**
     * 修改订单
     *
     * @param ymxOrderInfo 订单
     * @return 结果
     */
    public int updateYmxOrderInfo(YmxOrderInfo ymxOrderInfo);

    /**
     * 批量删除订单
     *
     * @param orderIds 需要删除的订单ID
     * @return 结果
     */
    public int deleteYmxOrderInfoByIds(String[] orderIds);

    /**
     * 删除订单信息
     *
     * @param orderId 订单ID
     * @return 结果
     */
    public int deleteYmxOrderInfoById(String orderId);

}
