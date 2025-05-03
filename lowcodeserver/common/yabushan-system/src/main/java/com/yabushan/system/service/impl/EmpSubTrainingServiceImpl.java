package com.yabushan.system.service.impl;

import java.util.List;
import com.yabushan.common.utils.DateUtils;
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yabushan.system.mapper.EmpSubTrainingMapper;
import com.yabushan.system.domain.EmpSubTraining;
import com.yabushan.system.service.IEmpSubTrainingService;

/**
 * 员工培训子集Service业务层处理
 *
 * @author yabushan
 * @date 2021-03-21
 */
@Service
public class EmpSubTrainingServiceImpl implements IEmpSubTrainingService
{
    @Autowired
    private EmpSubTrainingMapper empSubTrainingMapper;

    /**
     * 查询员工培训子集
     *
     * @param recId 员工培训子集ID
     * @return 员工培训子集
     */
    @Override
    public EmpSubTraining selectEmpSubTrainingById(String recId)
    {
        return empSubTrainingMapper.selectEmpSubTrainingById(recId);
    }

    /**
     * 查询员工培训子集列表
     *
     * @param empSubTraining 员工培训子集
     * @return 员工培训子集
     */
    @Override
    public List<EmpSubTraining> selectEmpSubTrainingList(EmpSubTraining empSubTraining)
    {
        return empSubTrainingMapper.selectEmpSubTrainingList(empSubTraining);
    }

    /**
     * 新增员工培训子集
     *
     * @param empSubTraining 员工培训子集
     * @return 结果
     */
    @Override
    public int insertEmpSubTraining(EmpSubTraining empSubTraining)
    {
        empSubTraining.setCreateTime(DateUtils.getNowDate());
        empSubTraining.setRecId(StringUtils.getUUID());
        empSubTraining.setCreateBy(SecurityUtils.getUsername());
        return empSubTrainingMapper.insertEmpSubTraining(empSubTraining);
    }

    /**
     * 修改员工培训子集
     *
     * @param empSubTraining 员工培训子集
     * @return 结果
     */
    @Override
    public int updateEmpSubTraining(EmpSubTraining empSubTraining)
    {
        empSubTraining.setUpdateTime(DateUtils.getNowDate());
        empSubTraining.setUpdateBy(SecurityUtils.getUsername());
        return empSubTrainingMapper.updateEmpSubTraining(empSubTraining);
    }

    /**
     * 批量删除员工培训子集
     *
     * @param recIds 需要删除的员工培训子集ID
     * @return 结果
     */
    @Override
    public int deleteEmpSubTrainingByIds(String[] recIds)
    {
        return empSubTrainingMapper.deleteEmpSubTrainingByIds(recIds);
    }

    /**
     * 删除员工培训子集信息
     *
     * @param recId 员工培训子集ID
     * @return 结果
     */
    @Override
    public int deleteEmpSubTrainingById(String recId)
    {
        return empSubTrainingMapper.deleteEmpSubTrainingById(recId);
    }
}
