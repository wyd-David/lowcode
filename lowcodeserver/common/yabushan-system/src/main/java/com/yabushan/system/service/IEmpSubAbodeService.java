package com.yabushan.system.service;

import java.util.List;
import com.yabushan.system.domain.EmpSubAbode;

/**
 * 员工家庭住址子集Service接口
 * 
 * @author yabushan
 * @date 2021-03-21
 */
public interface IEmpSubAbodeService 
{
    /**
     * 查询员工家庭住址子集
     * 
     * @param recId 员工家庭住址子集ID
     * @return 员工家庭住址子集
     */
    public EmpSubAbode selectEmpSubAbodeById(String recId);

    /**
     * 查询员工家庭住址子集列表
     * 
     * @param empSubAbode 员工家庭住址子集
     * @return 员工家庭住址子集集合
     */
    public List<EmpSubAbode> selectEmpSubAbodeList(EmpSubAbode empSubAbode);

    /**
     * 新增员工家庭住址子集
     * 
     * @param empSubAbode 员工家庭住址子集
     * @return 结果
     */
    public int insertEmpSubAbode(EmpSubAbode empSubAbode);

    /**
     * 修改员工家庭住址子集
     * 
     * @param empSubAbode 员工家庭住址子集
     * @return 结果
     */
    public int updateEmpSubAbode(EmpSubAbode empSubAbode);

    /**
     * 批量删除员工家庭住址子集
     * 
     * @param recIds 需要删除的员工家庭住址子集ID
     * @return 结果
     */
    public int deleteEmpSubAbodeByIds(String[] recIds);

    /**
     * 删除员工家庭住址子集信息
     * 
     * @param recId 员工家庭住址子集ID
     * @return 结果
     */
    public int deleteEmpSubAbodeById(String recId);
}
