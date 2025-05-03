package com.yabushan.system.service;

import java.util.List;
import com.yabushan.system.domain.EmpSubAbroad;

/**
 * 员工出国出境子集Service接口
 * 
 * @author yabushan
 * @date 2021-03-21
 */
public interface IEmpSubAbroadService 
{
    /**
     * 查询员工出国出境子集
     * 
     * @param recId 员工出国出境子集ID
     * @return 员工出国出境子集
     */
    public EmpSubAbroad selectEmpSubAbroadById(String recId);

    /**
     * 查询员工出国出境子集列表
     * 
     * @param empSubAbroad 员工出国出境子集
     * @return 员工出国出境子集集合
     */
    public List<EmpSubAbroad> selectEmpSubAbroadList(EmpSubAbroad empSubAbroad);

    /**
     * 新增员工出国出境子集
     * 
     * @param empSubAbroad 员工出国出境子集
     * @return 结果
     */
    public int insertEmpSubAbroad(EmpSubAbroad empSubAbroad);

    /**
     * 修改员工出国出境子集
     * 
     * @param empSubAbroad 员工出国出境子集
     * @return 结果
     */
    public int updateEmpSubAbroad(EmpSubAbroad empSubAbroad);

    /**
     * 批量删除员工出国出境子集
     * 
     * @param recIds 需要删除的员工出国出境子集ID
     * @return 结果
     */
    public int deleteEmpSubAbroadByIds(String[] recIds);

    /**
     * 删除员工出国出境子集信息
     * 
     * @param recId 员工出国出境子集ID
     * @return 结果
     */
    public int deleteEmpSubAbroadById(String recId);
}
