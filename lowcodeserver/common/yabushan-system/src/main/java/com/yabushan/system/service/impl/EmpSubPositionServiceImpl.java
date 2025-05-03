package com.yabushan.system.service.impl;

import java.util.List;
import com.yabushan.common.utils.DateUtils;
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yabushan.system.mapper.EmpSubPositionMapper;
import com.yabushan.system.domain.EmpSubPosition;
import com.yabushan.system.service.IEmpSubPositionService;

/**
 * 员工从事岗位子集Service业务层处理
 *
 * @author yabushan
 * @date 2021-03-21
 */
@Service
public class EmpSubPositionServiceImpl implements IEmpSubPositionService
{
    @Autowired
    private EmpSubPositionMapper empSubPositionMapper;

    /**
     * 查询员工从事岗位子集
     *
     * @param recId 员工从事岗位子集ID
     * @return 员工从事岗位子集
     */
    @Override
    public EmpSubPosition selectEmpSubPositionById(String recId)
    {
        return empSubPositionMapper.selectEmpSubPositionById(recId);
    }

    /**
     * 查询员工从事岗位子集列表
     *
     * @param empSubPosition 员工从事岗位子集
     * @return 员工从事岗位子集
     */
    @Override
    public List<EmpSubPosition> selectEmpSubPositionList(EmpSubPosition empSubPosition)
    {
        return empSubPositionMapper.selectEmpSubPositionList(empSubPosition);
    }

    /**
     * 新增员工从事岗位子集
     *
     * @param empSubPosition 员工从事岗位子集
     * @return 结果
     */
    @Override
    public int insertEmpSubPosition(EmpSubPosition empSubPosition)
    {
        empSubPosition.setCreateTime(DateUtils.getNowDate());
        empSubPosition.setRecId(StringUtils.getUUID());
        empSubPosition.setCreateBy(SecurityUtils.getUsername());
        return empSubPositionMapper.insertEmpSubPosition(empSubPosition);
    }

    /**
     * 修改员工从事岗位子集
     *
     * @param empSubPosition 员工从事岗位子集
     * @return 结果
     */
    @Override
    public int updateEmpSubPosition(EmpSubPosition empSubPosition)
    {
        empSubPosition.setUpdateTime(DateUtils.getNowDate());
        empSubPosition.setUpdateBy(SecurityUtils.getUsername());
        return empSubPositionMapper.updateEmpSubPosition(empSubPosition);
    }

    /**
     * 批量删除员工从事岗位子集
     *
     * @param recIds 需要删除的员工从事岗位子集ID
     * @return 结果
     */
    @Override
    public int deleteEmpSubPositionByIds(String[] recIds)
    {
        return empSubPositionMapper.deleteEmpSubPositionByIds(recIds);
    }

    /**
     * 删除员工从事岗位子集信息
     *
     * @param recId 员工从事岗位子集ID
     * @return 结果
     */
    @Override
    public int deleteEmpSubPositionById(String recId)
    {
        return empSubPositionMapper.deleteEmpSubPositionById(recId);
    }
}
