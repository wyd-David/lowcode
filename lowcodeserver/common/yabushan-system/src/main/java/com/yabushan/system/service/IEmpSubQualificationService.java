package com.yabushan.system.service;

import java.util.List;
import com.yabushan.system.domain.EmpSubQualification;

/**
 * 员工职业资格认证子集Service接口
 * 
 * @author yabushan
 * @date 2021-03-21
 */
public interface IEmpSubQualificationService 
{
    /**
     * 查询员工职业资格认证子集
     * 
     * @param recId 员工职业资格认证子集ID
     * @return 员工职业资格认证子集
     */
    public EmpSubQualification selectEmpSubQualificationById(String recId);

    /**
     * 查询员工职业资格认证子集列表
     * 
     * @param empSubQualification 员工职业资格认证子集
     * @return 员工职业资格认证子集集合
     */
    public List<EmpSubQualification> selectEmpSubQualificationList(EmpSubQualification empSubQualification);

    /**
     * 新增员工职业资格认证子集
     * 
     * @param empSubQualification 员工职业资格认证子集
     * @return 结果
     */
    public int insertEmpSubQualification(EmpSubQualification empSubQualification);

    /**
     * 修改员工职业资格认证子集
     * 
     * @param empSubQualification 员工职业资格认证子集
     * @return 结果
     */
    public int updateEmpSubQualification(EmpSubQualification empSubQualification);

    /**
     * 批量删除员工职业资格认证子集
     * 
     * @param recIds 需要删除的员工职业资格认证子集ID
     * @return 结果
     */
    public int deleteEmpSubQualificationByIds(String[] recIds);

    /**
     * 删除员工职业资格认证子集信息
     * 
     * @param recId 员工职业资格认证子集ID
     * @return 结果
     */
    public int deleteEmpSubQualificationById(String recId);
}
