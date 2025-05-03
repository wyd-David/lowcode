package com.yabushan.system.service;

import java.util.List;
import com.yabushan.system.domain.EmpSubPartyWin;

/**
 * 员工奖惩子集Service接口
 * 
 * @author yabushan
 * @date 2021-03-21
 */
public interface IEmpSubPartyWinService 
{
    /**
     * 查询员工奖惩子集
     * 
     * @param recId 员工奖惩子集ID
     * @return 员工奖惩子集
     */
    public EmpSubPartyWin selectEmpSubPartyWinById(String recId);

    /**
     * 查询员工奖惩子集列表
     * 
     * @param empSubPartyWin 员工奖惩子集
     * @return 员工奖惩子集集合
     */
    public List<EmpSubPartyWin> selectEmpSubPartyWinList(EmpSubPartyWin empSubPartyWin);

    /**
     * 新增员工奖惩子集
     * 
     * @param empSubPartyWin 员工奖惩子集
     * @return 结果
     */
    public int insertEmpSubPartyWin(EmpSubPartyWin empSubPartyWin);

    /**
     * 修改员工奖惩子集
     * 
     * @param empSubPartyWin 员工奖惩子集
     * @return 结果
     */
    public int updateEmpSubPartyWin(EmpSubPartyWin empSubPartyWin);

    /**
     * 批量删除员工奖惩子集
     * 
     * @param recIds 需要删除的员工奖惩子集ID
     * @return 结果
     */
    public int deleteEmpSubPartyWinByIds(String[] recIds);

    /**
     * 删除员工奖惩子集信息
     * 
     * @param recId 员工奖惩子集ID
     * @return 结果
     */
    public int deleteEmpSubPartyWinById(String recId);
}
