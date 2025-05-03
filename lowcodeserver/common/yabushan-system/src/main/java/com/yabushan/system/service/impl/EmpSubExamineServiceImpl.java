package com.yabushan.system.service.impl;

import java.util.List;
import com.yabushan.common.utils.DateUtils;
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yabushan.system.mapper.EmpSubExamineMapper;
import com.yabushan.system.domain.EmpSubExamine;
import com.yabushan.system.service.IEmpSubExamineService;

/**
 * 员工考核情况子集Service业务层处理
 *
 * @author yabushan
 * @date 2021-03-21
 */
@Service
public class EmpSubExamineServiceImpl implements IEmpSubExamineService
{
    @Autowired
    private EmpSubExamineMapper empSubExamineMapper;

    /**
     * 查询员工考核情况子集
     *
     * @param recId 员工考核情况子集ID
     * @return 员工考核情况子集
     */
    @Override
    public EmpSubExamine selectEmpSubExamineById(String recId)
    {
        return empSubExamineMapper.selectEmpSubExamineById(recId);
    }

    /**
     * 查询员工考核情况子集列表
     *
     * @param empSubExamine 员工考核情况子集
     * @return 员工考核情况子集
     */
    @Override
    public List<EmpSubExamine> selectEmpSubExamineList(EmpSubExamine empSubExamine)
    {
        return empSubExamineMapper.selectEmpSubExamineList(empSubExamine);
    }

    /**
     * 新增员工考核情况子集
     *
     * @param empSubExamine 员工考核情况子集
     * @return 结果
     */
    @Override
    public int insertEmpSubExamine(EmpSubExamine empSubExamine)
    {
        empSubExamine.setCreateTime(DateUtils.getNowDate());
        empSubExamine.setRecId(StringUtils.getUUID());
        empSubExamine.setCreateBy(SecurityUtils.getUsername());
        return empSubExamineMapper.insertEmpSubExamine(empSubExamine);
    }

    /**
     * 修改员工考核情况子集
     *
     * @param empSubExamine 员工考核情况子集
     * @return 结果
     */
    @Override
    public int updateEmpSubExamine(EmpSubExamine empSubExamine)
    {
        empSubExamine.setUpdateTime(DateUtils.getNowDate());
        empSubExamine.setUpdateBy(SecurityUtils.getUsername());
        return empSubExamineMapper.updateEmpSubExamine(empSubExamine);
    }

    /**
     * 批量删除员工考核情况子集
     *
     * @param recIds 需要删除的员工考核情况子集ID
     * @return 结果
     */
    @Override
    public int deleteEmpSubExamineByIds(String[] recIds)
    {
        return empSubExamineMapper.deleteEmpSubExamineByIds(recIds);
    }

    /**
     * 删除员工考核情况子集信息
     *
     * @param recId 员工考核情况子集ID
     * @return 结果
     */
    @Override
    public int deleteEmpSubExamineById(String recId)
    {
        return empSubExamineMapper.deleteEmpSubExamineById(recId);
    }
}
