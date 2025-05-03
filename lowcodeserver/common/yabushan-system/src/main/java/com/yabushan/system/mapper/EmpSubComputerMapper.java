package com.yabushan.system.mapper;

import java.util.List;
import com.yabushan.system.domain.EmpSubComputer;

/**
 * 员工计算机能力子集Mapper接口
 * 
 * @author yabushan
 * @date 2021-03-21
 */
public interface EmpSubComputerMapper 
{
    /**
     * 查询员工计算机能力子集
     * 
     * @param recId 员工计算机能力子集ID
     * @return 员工计算机能力子集
     */
    public EmpSubComputer selectEmpSubComputerById(String recId);

    /**
     * 查询员工计算机能力子集列表
     * 
     * @param empSubComputer 员工计算机能力子集
     * @return 员工计算机能力子集集合
     */
    public List<EmpSubComputer> selectEmpSubComputerList(EmpSubComputer empSubComputer);

    /**
     * 新增员工计算机能力子集
     * 
     * @param empSubComputer 员工计算机能力子集
     * @return 结果
     */
    public int insertEmpSubComputer(EmpSubComputer empSubComputer);

    /**
     * 修改员工计算机能力子集
     * 
     * @param empSubComputer 员工计算机能力子集
     * @return 结果
     */
    public int updateEmpSubComputer(EmpSubComputer empSubComputer);

    /**
     * 删除员工计算机能力子集
     * 
     * @param recId 员工计算机能力子集ID
     * @return 结果
     */
    public int deleteEmpSubComputerById(String recId);

    /**
     * 批量删除员工计算机能力子集
     * 
     * @param recIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteEmpSubComputerByIds(String[] recIds);
}
