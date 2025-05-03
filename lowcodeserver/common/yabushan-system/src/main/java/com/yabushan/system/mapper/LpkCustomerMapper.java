package com.yabushan.system.mapper;

import java.util.List;
import com.yabushan.system.domain.LpkCustomer;

/**
 * 外卖Mapper接口
 * 
 * @author yabushan
 * @date 2021-06-05
 */
public interface LpkCustomerMapper 
{
    /**
     * 查询外卖
     * 
     * @param keyId 外卖ID
     * @return 外卖
     */
    public LpkCustomer selectLpkCustomerById(Long keyId);

    /**
     * 查询外卖列表
     * 
     * @param lpkCustomer 外卖
     * @return 外卖集合
     */
    public List<LpkCustomer> selectLpkCustomerList(LpkCustomer lpkCustomer);

    /**
     * 新增外卖
     * 
     * @param lpkCustomer 外卖
     * @return 结果
     */
    public int insertLpkCustomer(LpkCustomer lpkCustomer);

    /**
     * 修改外卖
     * 
     * @param lpkCustomer 外卖
     * @return 结果
     */
    public int updateLpkCustomer(LpkCustomer lpkCustomer);

    /**
     * 删除外卖
     * 
     * @param keyId 外卖ID
     * @return 结果
     */
    public int deleteLpkCustomerById(Long keyId);

    /**
     * 批量删除外卖
     * 
     * @param keyIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteLpkCustomerByIds(Long[] keyIds);
}
