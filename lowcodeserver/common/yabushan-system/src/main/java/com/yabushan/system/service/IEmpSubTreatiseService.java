package com.yabushan.system.service;

import java.util.List;
import com.yabushan.system.domain.EmpSubTreatise;

/**
 * 员工论文著作子集Service接口
 * 
 * @author yabushan
 * @date 2021-03-21
 */
public interface IEmpSubTreatiseService 
{
    /**
     * 查询员工论文著作子集
     * 
     * @param recId 员工论文著作子集ID
     * @return 员工论文著作子集
     */
    public EmpSubTreatise selectEmpSubTreatiseById(String recId);

    /**
     * 查询员工论文著作子集列表
     * 
     * @param empSubTreatise 员工论文著作子集
     * @return 员工论文著作子集集合
     */
    public List<EmpSubTreatise> selectEmpSubTreatiseList(EmpSubTreatise empSubTreatise);

    /**
     * 新增员工论文著作子集
     * 
     * @param empSubTreatise 员工论文著作子集
     * @return 结果
     */
    public int insertEmpSubTreatise(EmpSubTreatise empSubTreatise);

    /**
     * 修改员工论文著作子集
     * 
     * @param empSubTreatise 员工论文著作子集
     * @return 结果
     */
    public int updateEmpSubTreatise(EmpSubTreatise empSubTreatise);

    /**
     * 批量删除员工论文著作子集
     * 
     * @param recIds 需要删除的员工论文著作子集ID
     * @return 结果
     */
    public int deleteEmpSubTreatiseByIds(String[] recIds);

    /**
     * 删除员工论文著作子集信息
     * 
     * @param recId 员工论文著作子集ID
     * @return 结果
     */
    public int deleteEmpSubTreatiseById(String recId);
}
