package com.yabushan.system.mapper;

import java.util.List;
import com.yabushan.system.domain.YmxCustomerGiftInfo;

/**
 * 客户礼物Mapper接口
 * 
 * @author yabushan
 * @date 2021-04-02
 */
public interface YmxCustomerGiftInfoMapper 
{
    /**
     * 查询客户礼物
     * 
     * @param customerGiftId 客户礼物ID
     * @return 客户礼物
     */
    public YmxCustomerGiftInfo selectYmxCustomerGiftInfoById(String customerGiftId);

    /**
     * 查询客户礼物列表
     * 
     * @param ymxCustomerGiftInfo 客户礼物
     * @return 客户礼物集合
     */
    public List<YmxCustomerGiftInfo> selectYmxCustomerGiftInfoList(YmxCustomerGiftInfo ymxCustomerGiftInfo);

    /**
     * 新增客户礼物
     * 
     * @param ymxCustomerGiftInfo 客户礼物
     * @return 结果
     */
    public int insertYmxCustomerGiftInfo(YmxCustomerGiftInfo ymxCustomerGiftInfo);

    /**
     * 修改客户礼物
     * 
     * @param ymxCustomerGiftInfo 客户礼物
     * @return 结果
     */
    public int updateYmxCustomerGiftInfo(YmxCustomerGiftInfo ymxCustomerGiftInfo);

    /**
     * 删除客户礼物
     * 
     * @param customerGiftId 客户礼物ID
     * @return 结果
     */
    public int deleteYmxCustomerGiftInfoById(String customerGiftId);

    /**
     * 批量删除客户礼物
     * 
     * @param customerGiftIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteYmxCustomerGiftInfoByIds(String[] customerGiftIds);
}
