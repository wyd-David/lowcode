package com.yabushan.system.service.impl;

import java.util.List;
import com.yabushan.common.utils.DateUtils;
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yabushan.system.mapper.EmpSubResumeMapper;
import com.yabushan.system.domain.EmpSubResume;
import com.yabushan.system.service.IEmpSubResumeService;

/**
 * 员工个人工作经历子集Service业务层处理
 *
 * @author yabushan
 * @date 2021-03-21
 */
@Service
public class EmpSubResumeServiceImpl implements IEmpSubResumeService
{
    @Autowired
    private EmpSubResumeMapper empSubResumeMapper;

    /**
     * 查询员工个人工作经历子集
     *
     * @param recId 员工个人工作经历子集ID
     * @return 员工个人工作经历子集
     */
    @Override
    public EmpSubResume selectEmpSubResumeById(String recId)
    {
        return empSubResumeMapper.selectEmpSubResumeById(recId);
    }

    /**
     * 查询员工个人工作经历子集列表
     *
     * @param empSubResume 员工个人工作经历子集
     * @return 员工个人工作经历子集
     */
    @Override
    public List<EmpSubResume> selectEmpSubResumeList(EmpSubResume empSubResume)
    {
        return empSubResumeMapper.selectEmpSubResumeList(empSubResume);
    }

    /**
     * 新增员工个人工作经历子集
     *
     * @param empSubResume 员工个人工作经历子集
     * @return 结果
     */
    @Override
    public int insertEmpSubResume(EmpSubResume empSubResume)
    {
        empSubResume.setCreateTime(DateUtils.getNowDate());
        empSubResume.setRecId(StringUtils.getUUID());
        empSubResume.setCreateBy(SecurityUtils.getUsername());
        return empSubResumeMapper.insertEmpSubResume(empSubResume);
    }

    /**
     * 修改员工个人工作经历子集
     *
     * @param empSubResume 员工个人工作经历子集
     * @return 结果
     */
    @Override
    public int updateEmpSubResume(EmpSubResume empSubResume)
    {
        empSubResume.setUpdateTime(DateUtils.getNowDate());
        empSubResume.setUpdateBy(SecurityUtils.getUsername());
        return empSubResumeMapper.updateEmpSubResume(empSubResume);
    }

    /**
     * 批量删除员工个人工作经历子集
     *
     * @param recIds 需要删除的员工个人工作经历子集ID
     * @return 结果
     */
    @Override
    public int deleteEmpSubResumeByIds(String[] recIds)
    {
        return empSubResumeMapper.deleteEmpSubResumeByIds(recIds);
    }

    /**
     * 删除员工个人工作经历子集信息
     *
     * @param recId 员工个人工作经历子集ID
     * @return 结果
     */
    @Override
    public int deleteEmpSubResumeById(String recId)
    {
        return empSubResumeMapper.deleteEmpSubResumeById(recId);
    }
}
