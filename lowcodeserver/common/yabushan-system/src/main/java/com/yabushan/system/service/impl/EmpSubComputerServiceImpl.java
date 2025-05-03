package com.yabushan.system.service.impl;

import java.util.List;
import com.yabushan.common.utils.DateUtils;
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yabushan.system.mapper.EmpSubComputerMapper;
import com.yabushan.system.domain.EmpSubComputer;
import com.yabushan.system.service.IEmpSubComputerService;

/**
 * 员工计算机能力子集Service业务层处理
 *
 * @author yabushan
 * @date 2021-03-21
 */
@Service
public class EmpSubComputerServiceImpl implements IEmpSubComputerService
{
    @Autowired
    private EmpSubComputerMapper empSubComputerMapper;

    /**
     * 查询员工计算机能力子集
     *
     * @param recId 员工计算机能力子集ID
     * @return 员工计算机能力子集
     */
    @Override
    public EmpSubComputer selectEmpSubComputerById(String recId)
    {
        return empSubComputerMapper.selectEmpSubComputerById(recId);
    }

    /**
     * 查询员工计算机能力子集列表
     *
     * @param empSubComputer 员工计算机能力子集
     * @return 员工计算机能力子集
     */
    @Override
    public List<EmpSubComputer> selectEmpSubComputerList(EmpSubComputer empSubComputer)
    {
        return empSubComputerMapper.selectEmpSubComputerList(empSubComputer);
    }

    /**
     * 新增员工计算机能力子集
     *
     * @param empSubComputer 员工计算机能力子集
     * @return 结果
     */
    @Override
    public int insertEmpSubComputer(EmpSubComputer empSubComputer)
    {
        empSubComputer.setCreateTime(DateUtils.getNowDate());
        empSubComputer.setRecId(StringUtils.getUUID());
        empSubComputer.setCreateBy(SecurityUtils.getUsername());
        return empSubComputerMapper.insertEmpSubComputer(empSubComputer);
    }

    /**
     * 修改员工计算机能力子集
     *
     * @param empSubComputer 员工计算机能力子集
     * @return 结果
     */
    @Override
    public int updateEmpSubComputer(EmpSubComputer empSubComputer)
    {
        empSubComputer.setUpdateTime(DateUtils.getNowDate());
        empSubComputer.setUpdateBy(SecurityUtils.getUsername());
        return empSubComputerMapper.updateEmpSubComputer(empSubComputer);
    }

    /**
     * 批量删除员工计算机能力子集
     *
     * @param recIds 需要删除的员工计算机能力子集ID
     * @return 结果
     */
    @Override
    public int deleteEmpSubComputerByIds(String[] recIds)
    {
        return empSubComputerMapper.deleteEmpSubComputerByIds(recIds);
    }

    /**
     * 删除员工计算机能力子集信息
     *
     * @param recId 员工计算机能力子集ID
     * @return 结果
     */
    @Override
    public int deleteEmpSubComputerById(String recId)
    {
        return empSubComputerMapper.deleteEmpSubComputerById(recId);
    }
}
