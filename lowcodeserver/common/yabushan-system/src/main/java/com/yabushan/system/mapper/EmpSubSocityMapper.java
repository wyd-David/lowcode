package com.yabushan.system.mapper;

import java.util.List;
import com.yabushan.system.domain.EmpSubSocity;
import org.apache.ibatis.annotations.Options;

/**
 * 员工家庭关系子集Mapper接口
 *
 * @author yabushan
 * @date 2021-03-21
 */
public interface EmpSubSocityMapper
{
    /**
     * 查询员工家庭关系子集
     *
     * @param recId 员工家庭关系子集ID
     * @return 员工家庭关系子集
     */
    public EmpSubSocity selectEmpSubSocityById(String recId);

    /**
     * 查询员工家庭关系子集列表
     *
     * @param empSubSocity 员工家庭关系子集
     * @return 员工家庭关系子集集合
     */
    public List<EmpSubSocity> selectEmpSubSocityList(EmpSubSocity empSubSocity);

    /**
     * 新增员工家庭关系子集
     *
     * @param empSubSocity 员工家庭关系子集
     * @return 结果
     */
    @Options(useGeneratedKeys=true, keyProperty="recId", keyColumn="recId")
    public int insertEmpSubSocity(EmpSubSocity empSubSocity);

    /**
     * 修改员工家庭关系子集
     *
     * @param empSubSocity 员工家庭关系子集
     * @return 结果
     */
    public int updateEmpSubSocity(EmpSubSocity empSubSocity);

    /**
     * 删除员工家庭关系子集
     *
     * @param recId 员工家庭关系子集ID
     * @return 结果
     */
    public int deleteEmpSubSocityById(String recId);

    /**
     * 批量删除员工家庭关系子集
     *
     * @param recIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteEmpSubSocityByIds(String[] recIds);
}
