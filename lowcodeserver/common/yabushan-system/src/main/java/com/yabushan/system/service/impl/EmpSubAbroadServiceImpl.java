package com.yabushan.system.service.impl;

import java.util.List;
import com.yabushan.common.utils.DateUtils;
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yabushan.system.mapper.EmpSubAbroadMapper;
import com.yabushan.system.domain.EmpSubAbroad;
import com.yabushan.system.service.IEmpSubAbroadService;

/**
 * 员工出国出境子集Service业务层处理
 *
 * @author yabushan
 * @date 2021-03-21
 */
@Service
public class EmpSubAbroadServiceImpl implements IEmpSubAbroadService
{
    @Autowired
    private EmpSubAbroadMapper empSubAbroadMapper;

    /**
     * 查询员工出国出境子集
     *
     * @param recId 员工出国出境子集ID
     * @return 员工出国出境子集
     */
    @Override
    public EmpSubAbroad selectEmpSubAbroadById(String recId)
    {
        return empSubAbroadMapper.selectEmpSubAbroadById(recId);
    }

    /**
     * 查询员工出国出境子集列表
     *
     * @param empSubAbroad 员工出国出境子集
     * @return 员工出国出境子集
     */
    @Override
    public List<EmpSubAbroad> selectEmpSubAbroadList(EmpSubAbroad empSubAbroad)
    {
        return empSubAbroadMapper.selectEmpSubAbroadList(empSubAbroad);
    }

    /**
     * 新增员工出国出境子集
     *
     * @param empSubAbroad 员工出国出境子集
     * @return 结果
     */
    @Override
    public int insertEmpSubAbroad(EmpSubAbroad empSubAbroad)
    {
        empSubAbroad.setCreateTime(DateUtils.getNowDate());
        empSubAbroad.setRecId(StringUtils.getUUID());
        empSubAbroad.setCreateBy(SecurityUtils.getUsername());
        return empSubAbroadMapper.insertEmpSubAbroad(empSubAbroad);
    }

    /**
     * 修改员工出国出境子集
     *
     * @param empSubAbroad 员工出国出境子集
     * @return 结果
     */
    @Override
    public int updateEmpSubAbroad(EmpSubAbroad empSubAbroad)
    {
        empSubAbroad.setUpdateTime(DateUtils.getNowDate());
        empSubAbroad.setUpdateBy(SecurityUtils.getUsername());
        return empSubAbroadMapper.updateEmpSubAbroad(empSubAbroad);
    }

    /**
     * 批量删除员工出国出境子集
     *
     * @param recIds 需要删除的员工出国出境子集ID
     * @return 结果
     */
    @Override
    public int deleteEmpSubAbroadByIds(String[] recIds)
    {
        return empSubAbroadMapper.deleteEmpSubAbroadByIds(recIds);
    }

    /**
     * 删除员工出国出境子集信息
     *
     * @param recId 员工出国出境子集ID
     * @return 结果
     */
    @Override
    public int deleteEmpSubAbroadById(String recId)
    {
        return empSubAbroadMapper.deleteEmpSubAbroadById(recId);
    }
}
