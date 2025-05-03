package com.yabushan.system.service.impl;

import java.util.List;
import com.yabushan.common.utils.DateUtils;
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yabushan.system.mapper.EmpSubQualificationMapper;
import com.yabushan.system.domain.EmpSubQualification;
import com.yabushan.system.service.IEmpSubQualificationService;

/**
 * 员工职业资格认证子集Service业务层处理
 *
 * @author yabushan
 * @date 2021-03-21
 */
@Service
public class EmpSubQualificationServiceImpl implements IEmpSubQualificationService
{
    @Autowired
    private EmpSubQualificationMapper empSubQualificationMapper;

    /**
     * 查询员工职业资格认证子集
     *
     * @param recId 员工职业资格认证子集ID
     * @return 员工职业资格认证子集
     */
    @Override
    public EmpSubQualification selectEmpSubQualificationById(String recId)
    {
        return empSubQualificationMapper.selectEmpSubQualificationById(recId);
    }

    /**
     * 查询员工职业资格认证子集列表
     *
     * @param empSubQualification 员工职业资格认证子集
     * @return 员工职业资格认证子集
     */
    @Override
    public List<EmpSubQualification> selectEmpSubQualificationList(EmpSubQualification empSubQualification)
    {
        return empSubQualificationMapper.selectEmpSubQualificationList(empSubQualification);
    }

    /**
     * 新增员工职业资格认证子集
     *
     * @param empSubQualification 员工职业资格认证子集
     * @return 结果
     */
    @Override
    public int insertEmpSubQualification(EmpSubQualification empSubQualification)
    {
        empSubQualification.setCreateTime(DateUtils.getNowDate());
        empSubQualification.setRecId(StringUtils.getUUID());
        empSubQualification.setCreateBy(SecurityUtils.getUsername());
        return empSubQualificationMapper.insertEmpSubQualification(empSubQualification);
    }

    /**
     * 修改员工职业资格认证子集
     *
     * @param empSubQualification 员工职业资格认证子集
     * @return 结果
     */
    @Override
    public int updateEmpSubQualification(EmpSubQualification empSubQualification)
    {
        empSubQualification.setUpdateTime(DateUtils.getNowDate());
        empSubQualification.setUpdateBy(SecurityUtils.getUsername());
        return empSubQualificationMapper.updateEmpSubQualification(empSubQualification);
    }

    /**
     * 批量删除员工职业资格认证子集
     *
     * @param recIds 需要删除的员工职业资格认证子集ID
     * @return 结果
     */
    @Override
    public int deleteEmpSubQualificationByIds(String[] recIds)
    {
        return empSubQualificationMapper.deleteEmpSubQualificationByIds(recIds);
    }

    /**
     * 删除员工职业资格认证子集信息
     *
     * @param recId 员工职业资格认证子集ID
     * @return 结果
     */
    @Override
    public int deleteEmpSubQualificationById(String recId)
    {
        return empSubQualificationMapper.deleteEmpSubQualificationById(recId);
    }
}
