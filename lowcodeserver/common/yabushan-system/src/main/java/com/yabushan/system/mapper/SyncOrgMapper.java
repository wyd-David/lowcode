package com.yabushan.system.mapper;

import com.yabushan.common.core.domain.entity.SysDept;
import com.yabushan.system.domain.SyncOrg;
import com.yabushan.system.domain.vo.OrgRoleUserVo;
import com.yabushan.system.domain.vo.RoleEmpInfoVo;
import com.yabushan.system.domain.vo.SyncOrgVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 同步组织信息Mapper接口
 * 
 * @author yabushan
 * @date 2021-08-24
 */
public interface SyncOrgMapper 
{
    /**
     * 查询同步组织信息
     * 
     * @param id 同步组织信息ID
     * @return 同步组织信息
     */
    public SyncOrg selectSyncOrgById(Long id);

    /**
     * 查询同步组织信息列表
     * 
     * @param syncOrg 同步组织信息
     * @return 同步组织信息集合
     */
    public List<SyncOrg> selectSyncOrgList(SyncOrg syncOrg);

    /**
     * 查询所有同步组织信息列表
     *
     * @param syncOrg 同步组织信息
     * @return 同步组织信息集合
     */
    public List<SyncOrg> selectSyncOrgListAll(SyncOrg syncOrg);

    /**
     * 新增同步组织信息
     * 
     * @param syncOrg 同步组织信息
     * @return 结果
     */
    public int insertSyncOrg(SyncOrg syncOrg);

    /**
     * 修改同步组织信息
     * 
     * @param syncOrg 同步组织信息
     * @return 结果
     */
    public int updateSyncOrg(SyncOrg syncOrg);

    /**
     * 删除同步组织信息
     * 
     * @param id 同步组织信息ID
     * @return 结果
     */
    public int deleteSyncOrgById(Long id);

    /**
     * 根据部门Id删除信息
     *
     * @param depId 部门ID
     * @return 结果
     */
    public int deleteSyncOrgByDepId(@Param("depId") String depId);

    /**
     * 批量删除同步组织信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSyncOrgByIds(Long[] ids);

    /**
     * 根据部门Id查询信息
     *
     * @param depId 部门ID
     * @return 结果
     */
    SyncOrg selectSyncOrgByDepId(@Param("depId") String depId);


    /**
     * 根据系统部门Id查询信息
     *
     * @param deptId
     * @return 结果
     */
    SyncOrg selectSyncOrgBySysDep(@Param("deptId") String deptId);

    /**
     * 根据id是否有子部门
     *
     * @param parentdpid
     * @return 结果
     */
    int selectSyncOrgByParentId(@Param("parentdpid") String parentdpid);


    /**
     * 清除表数据
     * @return 结果
     */
    public int trunCateSyncOrg();

    /**
     * 查询所有一级部门
     *
     *
     * @return 部门集合
     */
    public List<SyncOrg> selectSysSyncDepAllParentLevel();
    /**
     * 查询所有一级部门下的部门
     *
     *
     * @return 部门集合
     */
    public List<SyncOrg> selectSysSyncDepAllSenndLevel(SyncOrg syncOrg);
    /**
     * 查询所有一级部门
     *
     *
     * @return 部门集合
     */
    public List<SyncOrg> selectSysSyncDepAll();


    List<RoleEmpInfoVo> getLcjsEmpInfo(OrgRoleUserVo orgRoleUserVo);

    List<RoleEmpInfoVo> getJsEmpInfo(OrgRoleUserVo orgRoleUserVo);


    List<SyncOrgVo> selectSyncDepAllVo(SyncOrg syncOrg);

    public SyncOrg getSyncOrgBySyncDep(@Param("deptId") String deptId);


    /**
     * 根据ID查询所有子部门
     *
     * @param deptId 部门ID
     * @return 部门列表
     */
    public List<SyncOrg> selectChildrenSyncDeptById(@Param("deptId") String deptId);


    /**
     * 修改子元素关系
     *
     * @param depts 子元素
     * @return 结果
     */
    public int updateSyncDeptChildren(@Param("depts") List<SyncOrg> depts);
}
