package com.yabushan.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yabushan.system.mapper.YmxDiscountCodeInfoMapper;
import com.yabushan.system.domain.YmxDiscountCodeInfo;
import com.yabushan.system.service.IYmxDiscountCodeInfoService;

/**
 * 折扣与保修信息Service业务层处理
 *
 * @author yabushan
 * @date 2021-04-02
 */
@Service
public class YmxDiscountCodeInfoServiceImpl implements IYmxDiscountCodeInfoService
{
    @Autowired
    private YmxDiscountCodeInfoMapper ymxDiscountCodeInfoMapper;

    /**
     * 查询折扣与保修信息
     *
     * @param discountCodeId 折扣与保修信息ID
     * @return 折扣与保修信息
     */
    @Override
    public YmxDiscountCodeInfo selectYmxDiscountCodeInfoById(String discountCodeId)
    {
        return ymxDiscountCodeInfoMapper.selectYmxDiscountCodeInfoById(discountCodeId);
    }

    /**
     * 查询折扣与保修信息列表
     *
     * @param ymxDiscountCodeInfo 折扣与保修信息
     * @return 折扣与保修信息
     */
    @Override
    public List<YmxDiscountCodeInfo> selectYmxDiscountCodeInfoList(YmxDiscountCodeInfo ymxDiscountCodeInfo)
    {
        return ymxDiscountCodeInfoMapper.selectYmxDiscountCodeInfoList(ymxDiscountCodeInfo);
    }

    /**
     * 新增折扣与保修信息
     *
     * @param ymxDiscountCodeInfo 折扣与保修信息
     * @return 结果
     */
    @Override
    public int insertYmxDiscountCodeInfo(YmxDiscountCodeInfo ymxDiscountCodeInfo)
    {
        return ymxDiscountCodeInfoMapper.insertYmxDiscountCodeInfo(ymxDiscountCodeInfo);
    }

    /**
     * 修改折扣与保修信息
     *
     * @param ymxDiscountCodeInfo 折扣与保修信息
     * @return 结果
     */
    @Override
    public int updateYmxDiscountCodeInfo(YmxDiscountCodeInfo ymxDiscountCodeInfo)
    {
        return ymxDiscountCodeInfoMapper.updateYmxDiscountCodeInfo(ymxDiscountCodeInfo);
    }

    /**
     * 批量删除折扣与保修信息
     *
     * @param discountCodeIds 需要删除的折扣与保修信息ID
     * @return 结果
     */
    @Override
    public int deleteYmxDiscountCodeInfoByIds(String[] discountCodeIds)
    {
        return ymxDiscountCodeInfoMapper.deleteYmxDiscountCodeInfoByIds(discountCodeIds);
    }

    /**
     * 删除折扣与保修信息信息
     *
     * @param discountCodeId 折扣与保修信息ID
     * @return 结果
     */
    @Override
    public int deleteYmxDiscountCodeInfoById(String discountCodeId)
    {
        return ymxDiscountCodeInfoMapper.deleteYmxDiscountCodeInfoById(discountCodeId);
    }
}
