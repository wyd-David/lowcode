package com.yabushan.system.mapper;

import java.util.List;
import com.yabushan.system.domain.EmpSubEducation;

/**
 * 员工教育子集Mapper接口
 * 
 * @author yabushan
 * @date 2021-03-21
 */
public interface EmpSubEducationMapper 
{
    /**
     * 查询员工教育子集
     * 
     * @param recId 员工教育子集ID
     * @return 员工教育子集
     */
    public EmpSubEducation selectEmpSubEducationById(String recId);

    /**
     * 查询员工教育子集列表
     * 
     * @param empSubEducation 员工教育子集
     * @return 员工教育子集集合
     */
    public List<EmpSubEducation> selectEmpSubEducationList(EmpSubEducation empSubEducation);

    /**
     * 新增员工教育子集
     * 
     * @param empSubEducation 员工教育子集
     * @return 结果
     */
    public int insertEmpSubEducation(EmpSubEducation empSubEducation);

    /**
     * 修改员工教育子集
     * 
     * @param empSubEducation 员工教育子集
     * @return 结果
     */
    public int updateEmpSubEducation(EmpSubEducation empSubEducation);

    /**
     * 删除员工教育子集
     * 
     * @param recId 员工教育子集ID
     * @return 结果
     */
    public int deleteEmpSubEducationById(String recId);

    /**
     * 批量删除员工教育子集
     * 
     * @param recIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteEmpSubEducationByIds(String[] recIds);
}
