package com.yabushan.system.service.impl;

import java.util.List;
import com.yabushan.common.utils.DateUtils;
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yabushan.system.mapper.EmpSubSocityMapper;
import com.yabushan.system.domain.EmpSubSocity;
import com.yabushan.system.service.IEmpSubSocityService;

/**
 * 员工家庭关系子集Service业务层处理
 *
 * @author yabushan
 * @date 2021-03-21
 */
@Service
public class EmpSubSocityServiceImpl implements IEmpSubSocityService
{
    @Autowired
    private EmpSubSocityMapper empSubSocityMapper;

    /**
     * 查询员工家庭关系子集
     *
     * @param recId 员工家庭关系子集ID
     * @return 员工家庭关系子集
     */
    @Override
    public EmpSubSocity selectEmpSubSocityById(String recId)
    {
        return empSubSocityMapper.selectEmpSubSocityById(recId);
    }

    /**
     * 查询员工家庭关系子集列表
     *
     * @param empSubSocity 员工家庭关系子集
     * @return 员工家庭关系子集
     */
    @Override
    public List<EmpSubSocity> selectEmpSubSocityList(EmpSubSocity empSubSocity)
    {
        return empSubSocityMapper.selectEmpSubSocityList(empSubSocity);
    }

    /**
     * 新增员工家庭关系子集
     *
     * @param empSubSocity 员工家庭关系子集
     * @return 结果
     */
    @Override
    public int insertEmpSubSocity(EmpSubSocity empSubSocity)
    {
        empSubSocity.setCreateTime(DateUtils.getNowDate());
        empSubSocity.setRecId(StringUtils.getUUID());
        empSubSocity.setCreateBy(SecurityUtils.getUsername());
        return empSubSocityMapper.insertEmpSubSocity(empSubSocity);
    }

    /**
     * 修改员工家庭关系子集
     *
     * @param empSubSocity 员工家庭关系子集
     * @return 结果
     */
    @Override
    public int updateEmpSubSocity(EmpSubSocity empSubSocity)
    {
        empSubSocity.setUpdateTime(DateUtils.getNowDate());
        empSubSocity.setUpdateBy(SecurityUtils.getUsername());
        return empSubSocityMapper.updateEmpSubSocity(empSubSocity);
    }

    /**
     * 批量删除员工家庭关系子集
     *
     * @param recIds 需要删除的员工家庭关系子集ID
     * @return 结果
     */
    @Override
    public int deleteEmpSubSocityByIds(String[] recIds)
    {
        return empSubSocityMapper.deleteEmpSubSocityByIds(recIds);
    }

    /**
     * 删除员工家庭关系子集信息
     *
     * @param recId 员工家庭关系子集ID
     * @return 结果
     */
    @Override
    public int deleteEmpSubSocityById(String recId)
    {
        return empSubSocityMapper.deleteEmpSubSocityById(recId);
    }
}
