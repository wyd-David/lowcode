package com.yabushan.system.service.impl;

import java.util.List;
import com.yabushan.common.utils.DateUtils;
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yabushan.system.mapper.EmpSubQuaCertifyMapper;
import com.yabushan.system.domain.EmpSubQuaCertify;
import com.yabushan.system.service.IEmpSubQuaCertifyService;

/**
 * 员工证书子集Service业务层处理
 *
 * @author yabushan
 * @date 2021-03-21
 */
@Service
public class EmpSubQuaCertifyServiceImpl implements IEmpSubQuaCertifyService
{
    @Autowired
    private EmpSubQuaCertifyMapper empSubQuaCertifyMapper;

    /**
     * 查询员工证书子集
     *
     * @param recId 员工证书子集ID
     * @return 员工证书子集
     */
    @Override
    public EmpSubQuaCertify selectEmpSubQuaCertifyById(String recId)
    {
        return empSubQuaCertifyMapper.selectEmpSubQuaCertifyById(recId);
    }

    /**
     * 查询员工证书子集列表
     *
     * @param empSubQuaCertify 员工证书子集
     * @return 员工证书子集
     */
    @Override
    public List<EmpSubQuaCertify> selectEmpSubQuaCertifyList(EmpSubQuaCertify empSubQuaCertify)
    {
        return empSubQuaCertifyMapper.selectEmpSubQuaCertifyList(empSubQuaCertify);
    }

    /**
     * 新增员工证书子集
     *
     * @param empSubQuaCertify 员工证书子集
     * @return 结果
     */
    @Override
    public int insertEmpSubQuaCertify(EmpSubQuaCertify empSubQuaCertify)
    {
        empSubQuaCertify.setCreateTime(DateUtils.getNowDate());
        empSubQuaCertify.setRecId(StringUtils.getUUID());
        empSubQuaCertify.setCreateBy(SecurityUtils.getUsername());
        return empSubQuaCertifyMapper.insertEmpSubQuaCertify(empSubQuaCertify);
    }

    /**
     * 修改员工证书子集
     *
     * @param empSubQuaCertify 员工证书子集
     * @return 结果
     */
    @Override
    public int updateEmpSubQuaCertify(EmpSubQuaCertify empSubQuaCertify)
    {
        empSubQuaCertify.setUpdateTime(DateUtils.getNowDate());
        empSubQuaCertify.setUpdateBy(SecurityUtils.getUsername());
        return empSubQuaCertifyMapper.updateEmpSubQuaCertify(empSubQuaCertify);
    }

    /**
     * 批量删除员工证书子集
     *
     * @param recIds 需要删除的员工证书子集ID
     * @return 结果
     */
    @Override
    public int deleteEmpSubQuaCertifyByIds(String[] recIds)
    {
        return empSubQuaCertifyMapper.deleteEmpSubQuaCertifyByIds(recIds);
    }

    /**
     * 删除员工证书子集信息
     *
     * @param recId 员工证书子集ID
     * @return 结果
     */
    @Override
    public int deleteEmpSubQuaCertifyById(String recId)
    {
        return empSubQuaCertifyMapper.deleteEmpSubQuaCertifyById(recId);
    }
}
