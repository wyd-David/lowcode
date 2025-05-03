package com.yabushan.system.service.impl;

import java.util.List;
import com.yabushan.common.utils.DateUtils;
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yabushan.system.mapper.EmpSubEducationMapper;
import com.yabushan.system.domain.EmpSubEducation;
import com.yabushan.system.service.IEmpSubEducationService;

/**
 * 员工教育子集Service业务层处理
 *
 * @author yabushan
 * @date 2021-03-21
 */
@Service
public class EmpSubEducationServiceImpl implements IEmpSubEducationService
{
    @Autowired
    private EmpSubEducationMapper empSubEducationMapper;

    /**
     * 查询员工教育子集
     *
     * @param recId 员工教育子集ID
     * @return 员工教育子集
     */
    @Override
    public EmpSubEducation selectEmpSubEducationById(String recId)
    {
        return empSubEducationMapper.selectEmpSubEducationById(recId);
    }

    /**
     * 查询员工教育子集列表
     *
     * @param empSubEducation 员工教育子集
     * @return 员工教育子集
     */
    @Override
    public List<EmpSubEducation> selectEmpSubEducationList(EmpSubEducation empSubEducation)
    {
        return empSubEducationMapper.selectEmpSubEducationList(empSubEducation);
    }

    /**
     * 新增员工教育子集
     *
     * @param empSubEducation 员工教育子集
     * @return 结果
     */
    @Override
    public int insertEmpSubEducation(EmpSubEducation empSubEducation)
    {
        empSubEducation.setCreateTime(DateUtils.getNowDate());
        empSubEducation.setRecId(StringUtils.getUUID());
        empSubEducation.setCreateBy(SecurityUtils.getUsername());
        return empSubEducationMapper.insertEmpSubEducation(empSubEducation);
    }

    /**
     * 修改员工教育子集
     *
     * @param empSubEducation 员工教育子集
     * @return 结果
     */
    @Override
    public int updateEmpSubEducation(EmpSubEducation empSubEducation)
    {
        empSubEducation.setUpdateTime(DateUtils.getNowDate());
        empSubEducation.setUpdateBy(SecurityUtils.getUsername());
        return empSubEducationMapper.updateEmpSubEducation(empSubEducation);
    }

    /**
     * 批量删除员工教育子集
     *
     * @param recIds 需要删除的员工教育子集ID
     * @return 结果
     */
    @Override
    public int deleteEmpSubEducationByIds(String[] recIds)
    {
        return empSubEducationMapper.deleteEmpSubEducationByIds(recIds);
    }

    /**
     * 删除员工教育子集信息
     *
     * @param recId 员工教育子集ID
     * @return 结果
     */
    @Override
    public int deleteEmpSubEducationById(String recId)
    {
        return empSubEducationMapper.deleteEmpSubEducationById(recId);
    }
}
