package com.yabushan.system.service;

import java.util.List;
import com.yabushan.system.domain.YmxOrderGiftInfo;

/**
 * 订单礼物Service接口
 * 
 * @author yabushan
 * @date 2021-04-02
 */
public interface IYmxOrderGiftInfoService 
{
    /**
     * 查询订单礼物
     * 
     * @param orderGiftId 订单礼物ID
     * @return 订单礼物
     */
    public YmxOrderGiftInfo selectYmxOrderGiftInfoById(String orderGiftId);

    /**
     * 查询订单礼物列表
     * 
     * @param ymxOrderGiftInfo 订单礼物
     * @return 订单礼物集合
     */
    public List<YmxOrderGiftInfo> selectYmxOrderGiftInfoList(YmxOrderGiftInfo ymxOrderGiftInfo);

    /**
     * 新增订单礼物
     * 
     * @param ymxOrderGiftInfo 订单礼物
     * @return 结果
     */
    public int insertYmxOrderGiftInfo(YmxOrderGiftInfo ymxOrderGiftInfo);

    /**
     * 修改订单礼物
     * 
     * @param ymxOrderGiftInfo 订单礼物
     * @return 结果
     */
    public int updateYmxOrderGiftInfo(YmxOrderGiftInfo ymxOrderGiftInfo);

    /**
     * 批量删除订单礼物
     * 
     * @param orderGiftIds 需要删除的订单礼物ID
     * @return 结果
     */
    public int deleteYmxOrderGiftInfoByIds(String[] orderGiftIds);

    /**
     * 删除订单礼物信息
     * 
     * @param orderGiftId 订单礼物ID
     * @return 结果
     */
    public int deleteYmxOrderGiftInfoById(String orderGiftId);
}
