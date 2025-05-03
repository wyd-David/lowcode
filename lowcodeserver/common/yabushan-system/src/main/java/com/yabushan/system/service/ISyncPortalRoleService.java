package com.yabushan.system.service;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.yabushan.system.domain.SyncPortalRole;

/**
 * 组织架构角色Service接口
 *
 * @author yabushan
 * @date 2021-09-17
 */
public interface ISyncPortalRoleService
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
    public int bathInsertSyncPortalRole(List<SyncPortalRole> syncPortalRole);

    /**
     * 修改组织架构角色
     *
     * @param syncPortalRole 组织架构角色
     * @return 结果
     */
    public int updateSyncPortalRole(SyncPortalRole syncPortalRole);
    /**
    * 通过id集合修改状态
    */
    public Boolean updateByIds(String ids, String disableEnableState);

    /**
     * 批量删除组织架构角色
     *
     * @param portalRoleIds 需要删除的组织架构角色ID
     * @return 结果
     */
    public int deleteSyncPortalRoleByIds(String[] portalRoleIds);

    /**
     * 删除组织架构角色信息
     *
     * @param portalRoleId 组织架构角色ID
     * @return 结果
     */
    public int deleteSyncPortalRoleById(String portalRoleId);


    /**
     * 同步用户角色
     *
     * @param appId
     * @return 结果
     */
    public void saveOrUpdateSynOrg(String urlTmp,String appId);


    //json格式化
    JSONArray loadJson(String json);

   }
