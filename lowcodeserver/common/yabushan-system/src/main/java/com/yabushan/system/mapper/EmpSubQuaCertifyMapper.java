package com.yabushan.system.mapper;

import java.util.List;
import com.yabushan.system.domain.EmpSubQuaCertify;

/**
 * 员工证书子集Mapper接口
 * 
 * @author yabushan
 * @date 2021-03-21
 */
public interface EmpSubQuaCertifyMapper 
{
    /**
     * 查询员工证书子集
     * 
     * @param recId 员工证书子集ID
     * @return 员工证书子集
     */
    public EmpSubQuaCertify selectEmpSubQuaCertifyById(String recId);

    /**
     * 查询员工证书子集列表
     * 
     * @param empSubQuaCertify 员工证书子集
     * @return 员工证书子集集合
     */
    public List<EmpSubQuaCertify> selectEmpSubQuaCertifyList(EmpSubQuaCertify empSubQuaCertify);

    /**
     * 新增员工证书子集
     * 
     * @param empSubQuaCertify 员工证书子集
     * @return 结果
     */
    public int insertEmpSubQuaCertify(EmpSubQuaCertify empSubQuaCertify);

    /**
     * 修改员工证书子集
     * 
     * @param empSubQuaCertify 员工证书子集
     * @return 结果
     */
    public int updateEmpSubQuaCertify(EmpSubQuaCertify empSubQuaCertify);

    /**
     * 删除员工证书子集
     * 
     * @param recId 员工证书子集ID
     * @return 结果
     */
    public int deleteEmpSubQuaCertifyById(String recId);

    /**
     * 批量删除员工证书子集
     * 
     * @param recIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteEmpSubQuaCertifyByIds(String[] recIds);
}
