package com.yabushan.system.service.impl;

import java.util.List;
import com.yabushan.common.utils.DateUtils;
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yabushan.system.mapper.EmpSubPartyWinMapper;
import com.yabushan.system.domain.EmpSubPartyWin;
import com.yabushan.system.service.IEmpSubPartyWinService;

/**
 * 员工奖惩子集Service业务层处理
 *
 * @author yabushan
 * @date 2021-03-21
 */
@Service
public class EmpSubPartyWinServiceImpl implements IEmpSubPartyWinService
{
    @Autowired
    private EmpSubPartyWinMapper empSubPartyWinMapper;

    /**
     * 查询员工奖惩子集
     *
     * @param recId 员工奖惩子集ID
     * @return 员工奖惩子集
     */
    @Override
    public EmpSubPartyWin selectEmpSubPartyWinById(String recId)
    {
        return empSubPartyWinMapper.selectEmpSubPartyWinById(recId);
    }

    /**
     * 查询员工奖惩子集列表
     *
     * @param empSubPartyWin 员工奖惩子集
     * @return 员工奖惩子集
     */
    @Override
    public List<EmpSubPartyWin> selectEmpSubPartyWinList(EmpSubPartyWin empSubPartyWin)
    {
        return empSubPartyWinMapper.selectEmpSubPartyWinList(empSubPartyWin);
    }

    /**
     * 新增员工奖惩子集
     *
     * @param empSubPartyWin 员工奖惩子集
     * @return 结果
     */
    @Override
    public int insertEmpSubPartyWin(EmpSubPartyWin empSubPartyWin)
    {
        empSubPartyWin.setCreateTime(DateUtils.getNowDate());
        empSubPartyWin.setRecId(StringUtils.getUUID());
        empSubPartyWin.setCreateBy(SecurityUtils.getUsername());
        return empSubPartyWinMapper.insertEmpSubPartyWin(empSubPartyWin);
    }

    /**
     * 修改员工奖惩子集
     *
     * @param empSubPartyWin 员工奖惩子集
     * @return 结果
     */
    @Override
    public int updateEmpSubPartyWin(EmpSubPartyWin empSubPartyWin)
    {
        empSubPartyWin.setUpdateTime(DateUtils.getNowDate());
        empSubPartyWin.setUpdateBy(SecurityUtils.getUsername());
        return empSubPartyWinMapper.updateEmpSubPartyWin(empSubPartyWin);
    }

    /**
     * 批量删除员工奖惩子集
     *
     * @param recIds 需要删除的员工奖惩子集ID
     * @return 结果
     */
    @Override
    public int deleteEmpSubPartyWinByIds(String[] recIds)
    {
        return empSubPartyWinMapper.deleteEmpSubPartyWinByIds(recIds);
    }

    /**
     * 删除员工奖惩子集信息
     *
     * @param recId 员工奖惩子集ID
     * @return 结果
     */
    @Override
    public int deleteEmpSubPartyWinById(String recId)
    {
        return empSubPartyWinMapper.deleteEmpSubPartyWinById(recId);
    }
}
