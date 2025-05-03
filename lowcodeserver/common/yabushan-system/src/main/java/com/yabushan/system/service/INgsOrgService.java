package com.yabushan.system.service;

import com.yabushan.system.domain.NgsOrg;
import com.yabushan.system.utils.OrgTreeSelect;

import java.util.List;

/**
 * 班级Service接口
 *
 * @author yabushan
 * @date 2021-06-05
 */
public interface INgsOrgService
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
     * 批量删除班级
     *
     * @param orgIds 需要删除的班级ID
     * @return 结果
     */
    public int deleteNgsOrgByIds(String[] orgIds);

    /**
     * 删除班级信息
     *
     * @param orgId 班级ID
     * @return 结果
     */
    public int deleteNgsOrgById(String orgId);

    /**
     * 构建前端所需要下拉树结构
     *
     * @param depts 部门列表
     * @return 下拉树结构列表
     */
    public List<OrgTreeSelect> buildDeptTreeSelect(List<NgsOrg> depts);
}
