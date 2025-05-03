package com.yabushan.system.service;

import java.util.List;
import com.yabushan.system.domain.YmxDiscountCodeInfo;

/**
 * 折扣与保修信息Service接口
 * 
 * @author yabushan
 * @date 2021-04-02
 */
public interface IYmxDiscountCodeInfoService 
{
    /**
     * 查询折扣与保修信息
     * 
     * @param discountCodeId 折扣与保修信息ID
     * @return 折扣与保修信息
     */
    public YmxDiscountCodeInfo selectYmxDiscountCodeInfoById(String discountCodeId);

    /**
     * 查询折扣与保修信息列表
     * 
     * @param ymxDiscountCodeInfo 折扣与保修信息
     * @return 折扣与保修信息集合
     */
    public List<YmxDiscountCodeInfo> selectYmxDiscountCodeInfoList(YmxDiscountCodeInfo ymxDiscountCodeInfo);

    /**
     * 新增折扣与保修信息
     * 
     * @param ymxDiscountCodeInfo 折扣与保修信息
     * @return 结果
     */
    public int insertYmxDiscountCodeInfo(YmxDiscountCodeInfo ymxDiscountCodeInfo);

    /**
     * 修改折扣与保修信息
     * 
     * @param ymxDiscountCodeInfo 折扣与保修信息
     * @return 结果
     */
    public int updateYmxDiscountCodeInfo(YmxDiscountCodeInfo ymxDiscountCodeInfo);

    /**
     * 批量删除折扣与保修信息
     * 
     * @param discountCodeIds 需要删除的折扣与保修信息ID
     * @return 结果
     */
    public int deleteYmxDiscountCodeInfoByIds(String[] discountCodeIds);

    /**
     * 删除折扣与保修信息信息
     * 
     * @param discountCodeId 折扣与保修信息ID
     * @return 结果
     */
    public int deleteYmxDiscountCodeInfoById(String discountCodeId);
}
