package com.yabushan.system.service;

import java.util.List;
import com.yabushan.system.domain.EmpSubSocity;

/**
 * 员工家庭关系子集Service接口
 * 
 * @author yabushan
 * @date 2021-03-21
 */
public interface IEmpSubSocityService 
{
    /**
     * 查询员工家庭关系子集
     * 
     * @param recId 员工家庭关系子集ID
     * @return 员工家庭关系子集
     */
    public EmpSubSocity selectEmpSubSocityById(String recId);

    /**
     * 查询员工家庭关系子集列表
     * 
     * @param empSubSocity 员工家庭关系子集
     * @return 员工家庭关系子集集合
     */
    public List<EmpSubSocity> selectEmpSubSocityList(EmpSubSocity empSubSocity);

    /**
     * 新增员工家庭关系子集
     * 
     * @param empSubSocity 员工家庭关系子集
     * @return 结果
     */
    public int insertEmpSubSocity(EmpSubSocity empSubSocity);

    /**
     * 修改员工家庭关系子集
     * 
     * @param empSubSocity 员工家庭关系子集
     * @return 结果
     */
    public int updateEmpSubSocity(EmpSubSocity empSubSocity);

    /**
     * 批量删除员工家庭关系子集
     * 
     * @param recIds 需要删除的员工家庭关系子集ID
     * @return 结果
     */
    public int deleteEmpSubSocityByIds(String[] recIds);

    /**
     * 删除员工家庭关系子集信息
     * 
     * @param recId 员工家庭关系子集ID
     * @return 结果
     */
    public int deleteEmpSubSocityById(String recId);
}
