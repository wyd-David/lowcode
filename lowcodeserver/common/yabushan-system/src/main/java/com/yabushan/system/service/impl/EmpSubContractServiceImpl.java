package com.yabushan.system.service.impl;

import java.util.List;
import com.yabushan.common.utils.DateUtils;
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yabushan.system.mapper.EmpSubContractMapper;
import com.yabushan.system.domain.EmpSubContract;
import com.yabushan.system.service.IEmpSubContractService;

/**
 * 员工合同子集Service业务层处理
 *
 * @author yabushan
 * @date 2021-03-21
 */
@Service
public class EmpSubContractServiceImpl implements IEmpSubContractService
{
    @Autowired
    private EmpSubContractMapper empSubContractMapper;

    /**
     * 查询员工合同子集
     *
     * @param recId 员工合同子集ID
     * @return 员工合同子集
     */
    @Override
    public EmpSubContract selectEmpSubContractById(String recId)
    {
        return empSubContractMapper.selectEmpSubContractById(recId);
    }

    /**
     * 查询员工合同子集列表
     *
     * @param empSubContract 员工合同子集
     * @return 员工合同子集
     */
    @Override
    public List<EmpSubContract> selectEmpSubContractList(EmpSubContract empSubContract)
    {
        return empSubContractMapper.selectEmpSubContractList(empSubContract);
    }

    /**
     * 新增员工合同子集
     *
     * @param empSubContract 员工合同子集
     * @return 结果
     */
    @Override
    public int insertEmpSubContract(EmpSubContract empSubContract)
    {
        empSubContract.setCreateTime(DateUtils.getNowDate());
        empSubContract.setRecId(StringUtils.getUUID());
        empSubContract.setCreateBy(SecurityUtils.getUsername());
        return empSubContractMapper.insertEmpSubContract(empSubContract);
    }

    /**
     * 修改员工合同子集
     *
     * @param empSubContract 员工合同子集
     * @return 结果
     */
    @Override
    public int updateEmpSubContract(EmpSubContract empSubContract)
    {
        empSubContract.setUpdateTime(DateUtils.getNowDate());
        empSubContract.setUpdateBy(SecurityUtils.getUsername());
        return empSubContractMapper.updateEmpSubContract(empSubContract);
    }

    /**
     * 批量删除员工合同子集
     *
     * @param recIds 需要删除的员工合同子集ID
     * @return 结果
     */
    @Override
    public int deleteEmpSubContractByIds(String[] recIds)
    {
        return empSubContractMapper.deleteEmpSubContractByIds(recIds);
    }

    /**
     * 删除员工合同子集信息
     *
     * @param recId 员工合同子集ID
     * @return 结果
     */
    @Override
    public int deleteEmpSubContractById(String recId)
    {
        return empSubContractMapper.deleteEmpSubContractById(recId);
    }
}
