package com.yabushan.system.mapper;

import java.util.List;
import com.yabushan.system.domain.EmpSubTraining;

/**
 * 员工培训子集Mapper接口
 * 
 * @author yabushan
 * @date 2021-03-21
 */
public interface EmpSubTrainingMapper 
{
    /**
     * 查询员工培训子集
     * 
     * @param recId 员工培训子集ID
     * @return 员工培训子集
     */
    public EmpSubTraining selectEmpSubTrainingById(String recId);

    /**
     * 查询员工培训子集列表
     * 
     * @param empSubTraining 员工培训子集
     * @return 员工培训子集集合
     */
    public List<EmpSubTraining> selectEmpSubTrainingList(EmpSubTraining empSubTraining);

    /**
     * 新增员工培训子集
     * 
     * @param empSubTraining 员工培训子集
     * @return 结果
     */
    public int insertEmpSubTraining(EmpSubTraining empSubTraining);

    /**
     * 修改员工培训子集
     * 
     * @param empSubTraining 员工培训子集
     * @return 结果
     */
    public int updateEmpSubTraining(EmpSubTraining empSubTraining);

    /**
     * 删除员工培训子集
     * 
     * @param recId 员工培训子集ID
     * @return 结果
     */
    public int deleteEmpSubTrainingById(String recId);

    /**
     * 批量删除员工培训子集
     * 
     * @param recIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteEmpSubTrainingByIds(String[] recIds);
}
