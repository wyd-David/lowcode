package com.yabushan.system.mapper;

import java.util.List;
import com.yabushan.system.domain.YmxOrderInfo;

/**
 * 订单Mapper接口
 * 
 * @author yabushan
 * @date 2021-04-02
 */
public interface YmxOrderInfoMapper 
{
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
     * 删除订单
     * 
     * @param orderId 订单ID
     * @return 结果
     */
    public int deleteYmxOrderInfoById(String orderId);

    /**
     * 批量删除订单
     * 
     * @param orderIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteYmxOrderInfoByIds(String[] orderIds);
}
