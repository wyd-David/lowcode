package com.yabushan.system.service.impl;

import java.util.List;
import com.yabushan.common.utils.DateUtils;
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yabushan.system.mapper.EmpSubForeignLanguageMapper;
import com.yabushan.system.domain.EmpSubForeignLanguage;
import com.yabushan.system.service.IEmpSubForeignLanguageService;

/**
 * 员工外国语子集Service业务层处理
 *
 * @author yabushan
 * @date 2021-03-21
 */
@Service
public class EmpSubForeignLanguageServiceImpl implements IEmpSubForeignLanguageService
{
    @Autowired
    private EmpSubForeignLanguageMapper empSubForeignLanguageMapper;

    /**
     * 查询员工外国语子集
     *
     * @param recId 员工外国语子集ID
     * @return 员工外国语子集
     */
    @Override
    public EmpSubForeignLanguage selectEmpSubForeignLanguageById(String recId)
    {
        return empSubForeignLanguageMapper.selectEmpSubForeignLanguageById(recId);
    }

    /**
     * 查询员工外国语子集列表
     *
     * @param empSubForeignLanguage 员工外国语子集
     * @return 员工外国语子集
     */
    @Override
    public List<EmpSubForeignLanguage> selectEmpSubForeignLanguageList(EmpSubForeignLanguage empSubForeignLanguage)
    {
        return empSubForeignLanguageMapper.selectEmpSubForeignLanguageList(empSubForeignLanguage);
    }

    /**
     * 新增员工外国语子集
     *
     * @param empSubForeignLanguage 员工外国语子集
     * @return 结果
     */
    @Override
    public int insertEmpSubForeignLanguage(EmpSubForeignLanguage empSubForeignLanguage)
    {
        empSubForeignLanguage.setCreateTime(DateUtils.getNowDate());
        empSubForeignLanguage.setRecId(StringUtils.getUUID());
        empSubForeignLanguage.setCreateBy(SecurityUtils.getUsername());
        return empSubForeignLanguageMapper.insertEmpSubForeignLanguage(empSubForeignLanguage);
    }

    /**
     * 修改员工外国语子集
     *
     * @param empSubForeignLanguage 员工外国语子集
     * @return 结果
     */
    @Override
    public int updateEmpSubForeignLanguage(EmpSubForeignLanguage empSubForeignLanguage)
    {
        empSubForeignLanguage.setUpdateTime(DateUtils.getNowDate());
        empSubForeignLanguage.setUpdateBy(SecurityUtils.getUsername());
        return empSubForeignLanguageMapper.updateEmpSubForeignLanguage(empSubForeignLanguage);
    }

    /**
     * 批量删除员工外国语子集
     *
     * @param recIds 需要删除的员工外国语子集ID
     * @return 结果
     */
    @Override
    public int deleteEmpSubForeignLanguageByIds(String[] recIds)
    {
        return empSubForeignLanguageMapper.deleteEmpSubForeignLanguageByIds(recIds);
    }

    /**
     * 删除员工外国语子集信息
     *
     * @param recId 员工外国语子集ID
     * @return 结果
     */
    @Override
    public int deleteEmpSubForeignLanguageById(String recId)
    {
        return empSubForeignLanguageMapper.deleteEmpSubForeignLanguageById(recId);
    }
}
