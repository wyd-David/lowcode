package com.yabushan.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yabushan.system.mapper.YmxGiftInfoMapper;
import com.yabushan.system.domain.YmxGiftInfo;
import com.yabushan.system.service.IYmxGiftInfoService;

/**
 * 礼物Service业务层处理
 *
 * @author yabushan
 * @date 2021-04-02
 */
@Service
public class YmxGiftInfoServiceImpl implements IYmxGiftInfoService
{
    @Autowired
    private YmxGiftInfoMapper ymxGiftInfoMapper;

    /**
     * 查询礼物
     *
     * @param giftId 礼物ID
     * @return 礼物
     */
    @Override
    public YmxGiftInfo selectYmxGiftInfoById(String giftId)
    {
        return ymxGiftInfoMapper.selectYmxGiftInfoById(giftId);
    }

    /**
     * 查询礼物列表
     *
     * @param ymxGiftInfo 礼物
     * @return 礼物
     */
    @Override
    public List<YmxGiftInfo> selectYmxGiftInfoList(YmxGiftInfo ymxGiftInfo)
    {
        return ymxGiftInfoMapper.selectYmxGiftInfoList(ymxGiftInfo);
    }

    /**
     * 新增礼物
     *
     * @param ymxGiftInfo 礼物
     * @return 结果
     */
    @Override
    public int insertYmxGiftInfo(YmxGiftInfo ymxGiftInfo)
    {
        return ymxGiftInfoMapper.insertYmxGiftInfo(ymxGiftInfo);
    }

    /**
     * 修改礼物
     *
     * @param ymxGiftInfo 礼物
     * @return 结果
     */
    @Override
    public int updateYmxGiftInfo(YmxGiftInfo ymxGiftInfo)
    {
        return ymxGiftInfoMapper.updateYmxGiftInfo(ymxGiftInfo);
    }

    /**
     * 批量删除礼物
     *
     * @param giftIds 需要删除的礼物ID
     * @return 结果
     */
    @Override
    public int deleteYmxGiftInfoByIds(String[] giftIds)
    {
        return ymxGiftInfoMapper.deleteYmxGiftInfoByIds(giftIds);
    }

    /**
     * 删除礼物信息
     *
     * @param giftId 礼物ID
     * @return 结果
     */
    @Override
    public int deleteYmxGiftInfoById(String giftId)
    {
        return ymxGiftInfoMapper.deleteYmxGiftInfoById(giftId);
    }
}
