package com.yabushan.system.mapper;

import java.util.List;
import com.yabushan.system.domain.NgsOrg;

/**
 * 班级Mapper接口
 * 
 * @author yabushan
 * @date 2021-06-05
 */
public interface NgsOrgMapper 
{
    /**
     * 查询班级
     * 
     * @param orgId 班级ID
     * @return 班级
     */
    public NgsOrg selectNgsOrgById(String orgId);

    /**
     * 查询班级列表
     * 
     * @param ngsOrg 班级
     * @return 班级集合
     */
    public List<NgsOrg> selectNgsOrgList(NgsOrg ngsOrg);

    /**
     * 新增班级
     * 
     * @param ngsOrg 班级
     * @return 结果
     */
    public int insertNgsOrg(NgsOrg ngsOrg);

    /**
     * 修改班级
     * 
     * @param ngsOrg 班级
     * @return 结果
     */
    public int updateNgsOrg(NgsOrg ngsOrg);

    /**
     * 删除班级
     * 
     * @param orgId 班级ID
     * @return 结果
     */
    public int deleteNgsOrgById(String orgId);

    /**
     * 批量删除班级
     * 
     * @param orgIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteNgsOrgByIds(String[] orgIds);
}
