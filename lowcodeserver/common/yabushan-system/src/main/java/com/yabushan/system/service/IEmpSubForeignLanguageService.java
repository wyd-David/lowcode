package com.yabushan.system.service;

import java.util.List;
import com.yabushan.system.domain.EmpSubForeignLanguage;

/**
 * 员工外国语子集Service接口
 *
 * @author yabushan
 * @date 2021-03-21
 */
public interface IEmpSubForeignLanguageService
{
    /**
     * 查询员工外国语子集
     *
     * @param recId 员工外国语子集ID
     * @return 员工外国语子集
     */
    public EmpSubForeignLanguage selectEmpSubForeignLanguageById(String recId);

    /**
     * 查询员工外国语子集列表
     *
     * @param empSubForeignLanguage 员工外国语子集
     * @return 员工外国语子集集合
     */
    public List<EmpSubForeignLanguage> selectEmpSubForeignLanguageList(EmpSubForeignLanguage empSubForeignLanguage);

    /**
     * 新增员工外国语子集
     *
     * @param empSubForeignLanguage 员工外国语子集
     * @return 结果
     */
    public int insertEmpSubForeignLanguage(EmpSubForeignLanguage empSubForeignLanguage);

    /**
     * 修改员工外国语子集
     *
     * @param empSubForeignLanguage 员工外国语子集
     * @return 结果
     */
    public int updateEmpSubForeignLanguage(EmpSubForeignLanguage empSubForeignLanguage);

    /**
     * 批量删除员工外国语子集
     *
     * @param recIds 需要删除的员工外国语子集ID
     * @return 结果
     */
    public int deleteEmpSubForeignLanguageByIds(String[] recIds);

    /**
     * 删除员工外国语子集信息
     *
     * @param recId 员工外国语子集ID
     * @return 结果
     */
    public int deleteEmpSubForeignLanguageById(String recId);
}
