package com.yabushan.system.service;

import java.util.List;
import com.yabushan.system.domain.EmpSubResume;

/**
 * 员工个人工作经历子集Service接口
 * 
 * @author yabushan
 * @date 2021-03-21
 */
public interface IEmpSubResumeService 
{
    /**
     * 查询员工个人工作经历子集
     * 
     * @param recId 员工个人工作经历子集ID
     * @return 员工个人工作经历子集
     */
    public EmpSubResume selectEmpSubResumeById(String recId);

    /**
     * 查询员工个人工作经历子集列表
     * 
     * @param empSubResume 员工个人工作经历子集
     * @return 员工个人工作经历子集集合
     */
    public List<EmpSubResume> selectEmpSubResumeList(EmpSubResume empSubResume);

    /**
     * 新增员工个人工作经历子集
     * 
     * @param empSubResume 员工个人工作经历子集
     * @return 结果
     */
    public int insertEmpSubResume(EmpSubResume empSubResume);

    /**
     * 修改员工个人工作经历子集
     * 
     * @param empSubResume 员工个人工作经历子集
     * @return 结果
     */
    public int updateEmpSubResume(EmpSubResume empSubResume);

    /**
     * 批量删除员工个人工作经历子集
     * 
     * @param recIds 需要删除的员工个人工作经历子集ID
     * @return 结果
     */
    public int deleteEmpSubResumeByIds(String[] recIds);

    /**
     * 删除员工个人工作经历子集信息
     * 
     * @param recId 员工个人工作经历子集ID
     * @return 结果
     */
    public int deleteEmpSubResumeById(String recId);
}
