package com.yabushan.system.service;

import java.util.List;
import com.yabushan.system.domain.EmpSubExamine;

/**
 * 员工考核情况子集Service接口
 * 
 * @author yabushan
 * @date 2021-03-21
 */
public interface IEmpSubExamineService 
{
    /**
     * 查询员工考核情况子集
     * 
     * @param recId 员工考核情况子集ID
     * @return 员工考核情况子集
     */
    public EmpSubExamine selectEmpSubExamineById(String recId);

    /**
     * 查询员工考核情况子集列表
     * 
     * @param empSubExamine 员工考核情况子集
     * @return 员工考核情况子集集合
     */
    public List<EmpSubExamine> selectEmpSubExamineList(EmpSubExamine empSubExamine);

    /**
     * 新增员工考核情况子集
     * 
     * @param empSubExamine 员工考核情况子集
     * @return 结果
     */
    public int insertEmpSubExamine(EmpSubExamine empSubExamine);

    /**
     * 修改员工考核情况子集
     * 
     * @param empSubExamine 员工考核情况子集
     * @return 结果
     */
    public int updateEmpSubExamine(EmpSubExamine empSubExamine);

    /**
     * 批量删除员工考核情况子集
     * 
     * @param recIds 需要删除的员工考核情况子集ID
     * @return 结果
     */
    public int deleteEmpSubExamineByIds(String[] recIds);

    /**
     * 删除员工考核情况子集信息
     * 
     * @param recId 员工考核情况子集ID
     * @return 结果
     */
    public int deleteEmpSubExamineById(String recId);
}
