package com.yabushan.system.service.impl;

import java.util.List;
import com.yabushan.common.utils.DateUtils;
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yabushan.system.mapper.EmpSubTreatiseMapper;
import com.yabushan.system.domain.EmpSubTreatise;
import com.yabushan.system.service.IEmpSubTreatiseService;

/**
 * 员工论文著作子集Service业务层处理
 *
 * @author yabushan
 * @date 2021-03-21
 */
@Service
public class EmpSubTreatiseServiceImpl implements IEmpSubTreatiseService
{
    @Autowired
    private EmpSubTreatiseMapper empSubTreatiseMapper;

    /**
     * 查询员工论文著作子集
     *
     * @param recId 员工论文著作子集ID
     * @return 员工论文著作子集
     */
    @Override
    public EmpSubTreatise selectEmpSubTreatiseById(String recId)
    {
        return empSubTreatiseMapper.selectEmpSubTreatiseById(recId);
    }

    /**
     * 查询员工论文著作子集列表
     *
     * @param empSubTreatise 员工论文著作子集
     * @return 员工论文著作子集
     */
    @Override
    public List<EmpSubTreatise> selectEmpSubTreatiseList(EmpSubTreatise empSubTreatise)
    {
        return empSubTreatiseMapper.selectEmpSubTreatiseList(empSubTreatise);
    }

    /**
     * 新增员工论文著作子集
     *
     * @param empSubTreatise 员工论文著作子集
     * @return 结果
     */
    @Override
    public int insertEmpSubTreatise(EmpSubTreatise empSubTreatise)
    {
        empSubTreatise.setCreateTime(DateUtils.getNowDate());
        empSubTreatise.setRecId(StringUtils.getUUID());
        empSubTreatise.setCreateBy(SecurityUtils.getUsername());
        return empSubTreatiseMapper.insertEmpSubTreatise(empSubTreatise);
    }

    /**
     * 修改员工论文著作子集
     *
     * @param empSubTreatise 员工论文著作子集
     * @return 结果
     */
    @Override
    public int updateEmpSubTreatise(EmpSubTreatise empSubTreatise)
    {
        empSubTreatise.setUpdateTime(DateUtils.getNowDate());
        empSubTreatise.setUpdateBy(SecurityUtils.getUsername());
        return empSubTreatiseMapper.updateEmpSubTreatise(empSubTreatise);
    }

    /**
     * 批量删除员工论文著作子集
     *
     * @param recIds 需要删除的员工论文著作子集ID
     * @return 结果
     */
    @Override
    public int deleteEmpSubTreatiseByIds(String[] recIds)
    {
        return empSubTreatiseMapper.deleteEmpSubTreatiseByIds(recIds);
    }

    /**
     * 删除员工论文著作子集信息
     *
     * @param recId 员工论文著作子集ID
     * @return 结果
     */
    @Override
    public int deleteEmpSubTreatiseById(String recId)
    {
        return empSubTreatiseMapper.deleteEmpSubTreatiseById(recId);
    }
}
