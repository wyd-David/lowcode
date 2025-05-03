package com.yabushan.system.mapper;

import java.util.List;
import com.yabushan.system.domain.SyncPortalRole;

/**
 * 组织架构角色Mapper接口
 * 
 * @author yabushan
 * @date 2021-09-17
 */
public interface SyncPortalRoleMapper 
{
    /**
     * 查询组织架构角色
     * 
     * @param portalRoleId 组织架构角色ID
     * @return 组织架构角色
     */
    public SyncPortalRole selectSyncPortalRoleById(String portalRoleId);

    /**
     * 查询组织架构角色列表
     * 
     * @param syncPortalRole 组织架构角色
     * @return 组织架构角色集合
     */
    public List<SyncPortalRole> selectSyncPortalRoleList(SyncPortalRole syncPortalRole);

    /**
     * 新增组织架构角色
     * 
     * @param syncPortalRole 组织架构角色
     * @return 结果
     */
    public int insertSyncPortalRole(SyncPortalRole syncPortalRole);

    /**
     * 批量新增组织架构角色
     *
     * @param syncPortalRole 组织架构角色
     * @return 结果
     */
    public int bathInsertSyncPortalRole(List<SyncPortalRole> list);

    /**
     * 修改组织架构角色
     * 
     * @param syncPortalRole 组织架构角色
     * @return 结果
     */
    public int updateSyncPortalRole(SyncPortalRole syncPortalRole);

    /**
     * 删除组织架构角色
     * 
     * @param portalRoleId 组织架构角色ID
     * @return 结果
     */
    public int deleteSyncPortalRoleById(String portalRoleId);

    /**
     * 批量删除组织架构角色
     * 
     * @param portalRoleIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSyncPortalRoleByIds(String[] portalRoleIds);

    /**
     * 清除表数据
     * @return 结果
     */
    public int trunCateSyncRole();
}
