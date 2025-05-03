package com.yabushan.system.service.impl;

import java.util.List;
import com.yabushan.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yabushan.system.mapper.LpkCustomerMapper;
import com.yabushan.system.domain.LpkCustomer;
import com.yabushan.system.service.ILpkCustomerService;

/**
 * 外卖Service业务层处理
 *
 * @author yabushan
 * @date 2021-06-05
 */
@Service
public class LpkCustomerServiceImpl implements ILpkCustomerService
{
    @Autowired
    private LpkCustomerMapper lpkCustomerMapper;

    /**
     * 查询外卖
     *
     * @param keyId 外卖ID
     * @return 外卖
     */
    @Override
    public LpkCustomer selectLpkCustomerById(Long keyId)
    {
        return lpkCustomerMapper.selectLpkCustomerById(keyId);
    }

    /**
     * 查询外卖列表
     *
     * @param lpkCustomer 外卖
     * @return 外卖
     */
    @Override
    public List<LpkCustomer> selectLpkCustomerList(LpkCustomer lpkCustomer)
    {
        return lpkCustomerMapper.selectLpkCustomerList(lpkCustomer);
    }

    /**
     * 新增外卖
     *
     * @param lpkCustomer 外卖
     * @return 结果
     */
    @Override
    public int insertLpkCustomer(LpkCustomer lpkCustomer)
    {
        lpkCustomer.setCreateTime(DateUtils.getNowDate());
        return lpkCustomerMapper.insertLpkCustomer(lpkCustomer);
    }

    /**
     * 修改外卖
     *
     * @param lpkCustomer 外卖
     * @return 结果
     */
    @Override
    public int updateLpkCustomer(LpkCustomer lpkCustomer)
    {
        return lpkCustomerMapper.updateLpkCustomer(lpkCustomer);
    }

    /**
     * 批量删除外卖
     *
     * @param keyIds 需要删除的外卖ID
     * @return 结果
     */
    @Override
    public int deleteLpkCustomerByIds(Long[] keyIds)
    {
        return lpkCustomerMapper.deleteLpkCustomerByIds(keyIds);
    }

    /**
     * 删除外卖信息
     *
     * @param keyId 外卖ID
     * @return 结果
     */
    @Override
    public int deleteLpkCustomerById(Long keyId)
    {
        return lpkCustomerMapper.deleteLpkCustomerById(keyId);
    }
}
