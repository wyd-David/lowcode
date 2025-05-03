package com.yabushan.system.mapper;

import java.util.List;
import com.yabushan.system.domain.EmpSubContract;

/**
 * 员工合同子集Mapper接口
 *
 * @author yabushan
 * @date 2021-03-21
 */
public interface EmpSubContractMapper
{
    /**
     * 查询员工合同子集
     *
     * @param recId 员工合同子集ID
     * @return 员工合同子集
     */
    public EmpSubContract selectEmpSubContractById(String recId);

    /**
     * 查询员工合同子集列表
     *
     * @param empSubContract 员工合同子集
     * @return 员工合同子集集合
     */
    public List<EmpSubContract> selectEmpSubContractList(EmpSubContract empSubContract);

    /**
     * 新增员工合同子集
     *
     * @param empSubContract 员工合同子集
     * @return 结果
     */
    public int insertEmpSubContract(EmpSubContract empSubContract);

    /**
     * 修改员工合同子集
     *
     * @param empSubContract 员工合同子集
     * @return 结果
     */
    public int updateEmpSubContract(EmpSubContract empSubContract);

    /**
     * 删除员工合同子集
     *
     * @param recId 员工合同子集ID
     * @return 结果
     */
    public int deleteEmpSubContractById(String recId);

    /**
     * 批量删除员工合同子集
     *
     * @param recIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteEmpSubContractByIds(String[] recIds);
}
