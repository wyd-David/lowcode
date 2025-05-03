package com.yabushan.system.mapper;

import java.util.List;
import com.yabushan.system.domain.EmpSubForeignLanguage;

/**
 * 员工外国语子集Mapper接口
 *
 * @author yabushan
 * @date 2021-03-21
 */
public interface EmpSubForeignLanguageMapper
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
     * 删除员工外国语子集
     *
     * @param recId 员工外国语子集ID
     * @return 结果
     */
    public int deleteEmpSubForeignLanguageById(String recId);

    /**
     * 批量删除员工外国语子集
     *
     * @param recIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteEmpSubForeignLanguageByIds(String[] recIds);
}
