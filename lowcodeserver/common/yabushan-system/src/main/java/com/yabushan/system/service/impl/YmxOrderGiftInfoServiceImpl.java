package com.yabushan.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yabushan.system.mapper.YmxOrderGiftInfoMapper;
import com.yabushan.system.domain.YmxOrderGiftInfo;
import com.yabushan.system.service.IYmxOrderGiftInfoService;

/**
 * 订单礼物Service业务层处理
 *
 * @author yabushan
 * @date 2021-04-02
 */
@Service
public class YmxOrderGiftInfoServiceImpl implements IYmxOrderGiftInfoService
{
    @Autowired
    private YmxOrderGiftInfoMapper ymxOrderGiftInfoMapper;

    /**
     * 查询订单礼物
     *
     * @param orderGiftId 订单礼物ID
     * @return 订单礼物
     */
    @Override
    public YmxOrderGiftInfo selectYmxOrderGiftInfoById(String orderGiftId)
    {
        return ymxOrderGiftInfoMapper.selectYmxOrderGiftInfoById(orderGiftId);
    }

    /**
     * 查询订单礼物列表
     *
     * @param ymxOrderGiftInfo 订单礼物
     * @return 订单礼物
     */
    @Override
    public List<YmxOrderGiftInfo> selectYmxOrderGiftInfoList(YmxOrderGiftInfo ymxOrderGiftInfo)
    {
        return ymxOrderGiftInfoMapper.selectYmxOrderGiftInfoList(ymxOrderGiftInfo);
    }

    /**
     * 新增订单礼物
     *
     * @param ymxOrderGiftInfo 订单礼物
     * @return 结果
     */
    @Override
    public int insertYmxOrderGiftInfo(YmxOrderGiftInfo ymxOrderGiftInfo)
    {
        return ymxOrderGiftInfoMapper.insertYmxOrderGiftInfo(ymxOrderGiftInfo);
    }

    /**
     * 修改订单礼物
     *
     * @param ymxOrderGiftInfo 订单礼物
     * @return 结果
     */
    @Override
    public int updateYmxOrderGiftInfo(YmxOrderGiftInfo ymxOrderGiftInfo)
    {
        return ymxOrderGiftInfoMapper.updateYmxOrderGiftInfo(ymxOrderGiftInfo);
    }

    /**
     * 批量删除订单礼物
     *
     * @param orderGiftIds 需要删除的订单礼物ID
     * @return 结果
     */
    @Override
    public int deleteYmxOrderGiftInfoByIds(String[] orderGiftIds)
    {
        return ymxOrderGiftInfoMapper.deleteYmxOrderGiftInfoByIds(orderGiftIds);
    }

    /**
     * 删除订单礼物信息
     *
     * @param orderGiftId 订单礼物ID
     * @return 结果
     */
    @Override
    public int deleteYmxOrderGiftInfoById(String orderGiftId)
    {
        return ymxOrderGiftInfoMapper.deleteYmxOrderGiftInfoById(orderGiftId);
    }
}
