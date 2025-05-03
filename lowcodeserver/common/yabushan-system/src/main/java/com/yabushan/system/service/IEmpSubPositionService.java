package com.yabushan.system.service;

import java.util.List;
import com.yabushan.system.domain.EmpSubPosition;

/**
 * 员工从事岗位子集Service接口
 * 
 * @author yabushan
 * @date 2021-03-21
 */
public interface IEmpSubPositionService 
{
    /**
     * 查询员工从事岗位子集
     * 
     * @param recId 员工从事岗位子集ID
     * @return 员工从事岗位子集
     */
    public EmpSubPosition selectEmpSubPositionById(String recId);

    /**
     * 查询员工从事岗位子集列表
     * 
     * @param empSubPosition 员工从事岗位子集
     * @return 员工从事岗位子集集合
     */
    public List<EmpSubPosition> selectEmpSubPositionList(EmpSubPosition empSubPosition);

    /**
     * 新增员工从事岗位子集
     * 
     * @param empSubPosition 员工从事岗位子集
     * @return 结果
     */
    public int insertEmpSubPosition(EmpSubPosition empSubPosition);

    /**
     * 修改员工从事岗位子集
     * 
     * @param empSubPosition 员工从事岗位子集
     * @return 结果
     */
    public int updateEmpSubPosition(EmpSubPosition empSubPosition);

    /**
     * 批量删除员工从事岗位子集
     * 
     * @param recIds 需要删除的员工从事岗位子集ID
     * @return 结果
     */
    public int deleteEmpSubPositionByIds(String[] recIds);

    /**
     * 删除员工从事岗位子集信息
     * 
     * @param recId 员工从事岗位子集ID
     * @return 结果
     */
    public int deleteEmpSubPositionById(String recId);
}
