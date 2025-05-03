package com.yabushan.system.service.impl;

import java.util.List;
import com.yabushan.common.utils.DateUtils;
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yabushan.system.mapper.EmpSubAbodeMapper;
import com.yabushan.system.domain.EmpSubAbode;
import com.yabushan.system.service.IEmpSubAbodeService;

/**
 * 员工家庭住址子集Service业务层处理
 *
 * @author yabushan
 * @date 2021-03-21
 */
@Service
public class EmpSubAbodeServiceImpl implements IEmpSubAbodeService
{
    @Autowired
    private EmpSubAbodeMapper empSubAbodeMapper;

    /**
     * 查询员工家庭住址子集
     *
     * @param recId 员工家庭住址子集ID
     * @return 员工家庭住址子集
     */
    @Override
    public EmpSubAbode selectEmpSubAbodeById(String recId)
    {
        return empSubAbodeMapper.selectEmpSubAbodeById(recId);
    }

    /**
     * 查询员工家庭住址子集列表
     *
     * @param empSubAbode 员工家庭住址子集
     * @return 员工家庭住址子集
     */
    @Override
    public List<EmpSubAbode> selectEmpSubAbodeList(EmpSubAbode empSubAbode)
    {
        return empSubAbodeMapper.selectEmpSubAbodeList(empSubAbode);
    }

    /**
     * 新增员工家庭住址子集
     *
     * @param empSubAbode 员工家庭住址子集
     * @return 结果
     */
    @Override
    public int insertEmpSubAbode(EmpSubAbode empSubAbode)
    {
        empSubAbode.setCreateTime(DateUtils.getNowDate());
        empSubAbode.setRecId(StringUtils.getUUID());
        empSubAbode.setCreateBy(SecurityUtils.getUsername());
        return empSubAbodeMapper.insertEmpSubAbode(empSubAbode);
    }

    /**
     * 修改员工家庭住址子集
     *
     * @param empSubAbode 员工家庭住址子集
     * @return 结果
     */
    @Override
    public int updateEmpSubAbode(EmpSubAbode empSubAbode)
    {
        empSubAbode.setUpdateTime(DateUtils.getNowDate());
        empSubAbode.setUpdateBy(SecurityUtils.getUsername());
        return empSubAbodeMapper.updateEmpSubAbode(empSubAbode);
    }

    /**
     * 批量删除员工家庭住址子集
     *
     * @param recIds 需要删除的员工家庭住址子集ID
     * @return 结果
     */
    @Override
    public int deleteEmpSubAbodeByIds(String[] recIds)
    {
        return empSubAbodeMapper.deleteEmpSubAbodeByIds(recIds);
    }

    /**
     * 删除员工家庭住址子集信息
     *
     * @param recId 员工家庭住址子集ID
     * @return 结果
     */
    @Override
    public int deleteEmpSubAbodeById(String recId)
    {
        return empSubAbodeMapper.deleteEmpSubAbodeById(recId);
    }
}
