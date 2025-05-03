package com.yabushan.system.service;

import java.util.List;
import com.yabushan.system.domain.YmxGiftInfo;

/**
 * 礼物Service接口
 * 
 * @author yabushan
 * @date 2021-04-02
 */
public interface IYmxGiftInfoService 
{
    /**
     * 查询礼物
     * 
     * @param giftId 礼物ID
     * @return 礼物
     */
    public YmxGiftInfo selectYmxGiftInfoById(String giftId);

    /**
     * 查询礼物列表
     * 
     * @param ymxGiftInfo 礼物
     * @return 礼物集合
     */
    public List<YmxGiftInfo> selectYmxGiftInfoList(YmxGiftInfo ymxGiftInfo);

    /**
     * 新增礼物
     * 
     * @param ymxGiftInfo 礼物
     * @return 结果
     */
    public int insertYmxGiftInfo(YmxGiftInfo ymxGiftInfo);

    /**
     * 修改礼物
     * 
     * @param ymxGiftInfo 礼物
     * @return 结果
     */
    public int updateYmxGiftInfo(YmxGiftInfo ymxGiftInfo);

    /**
     * 批量删除礼物
     * 
     * @param giftIds 需要删除的礼物ID
     * @return 结果
     */
    public int deleteYmxGiftInfoByIds(String[] giftIds);

    /**
     * 删除礼物信息
     * 
     * @param giftId 礼物ID
     * @return 结果
     */
    public int deleteYmxGiftInfoById(String giftId);
}
