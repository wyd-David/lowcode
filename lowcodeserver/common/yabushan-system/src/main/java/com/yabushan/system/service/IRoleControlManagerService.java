package com.yabushan.system.service;

import com.yabushan.system.domain.RoleControlManager;

import java.util.List;

/**
 *  角色授权控制管理Service接口
 *
 * @author zcm
 * @date 2021-09-22
 */
public interface IRoleControlManagerService
{
    /**
     * 查询 角色授权控制管理
     *
     * @param roleControlManagerId  角色授权控制管理ID
     * @return  角色授权控制管理
     */
    public RoleControlManager selectRoleControlManagerById(String roleControlManagerId);

    /**
     * 查询 角色授权控制管理列表
     *
     * @param roleControlManager  角色授权控制管理
     * @return  角色授权控制管理集合
     */
    public List<RoleControlManager> selectRoleControlManagerList(RoleControlManager roleControlManager);

    /**
     * 新增 角色授权控制管理
     *
     * @param roleControlManager  角色授权控制管理
     * @return 结果
     */
    public int insertRoleControlManager(RoleControlManager roleControlManager);

    /**
     * 批量新增 角色授权控制管理
     *
     * @param roleControlManager  角色授权控制管理
     * @return 结果
     */
    public int bathInsertRoleControlManager(List<RoleControlManager> roleControlManager);

    /**
     * 修改 角色授权控制管理
     *
     * @param roleControlManager  角色授权控制管理
     * @return 结果
     */
    public int updateRoleControlManager(RoleControlManager roleControlManager);
    /**
    * 通过id集合修改状态
    */
    public Boolean updateByIds(String ids,String disableEnableState);

    /**
     * 批量删除 角色授权控制管理
     *
     * @param roleControlManagerIds 需要删除的 角色授权控制管理ID
     * @return 结果
     */
    public int deleteRoleControlManagerByIds(String[] roleControlManagerIds);

    /**
     * 删除 角色授权控制管理信息
     *
     * @param roleControlManagerId  角色授权控制管理ID
     * @return 结果
     */
    public int deleteRoleControlManagerById(String roleControlManagerId);




    /**
     * 导入 角色授权控制管理Excel数据
     *
     * @param roleControlManagerList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public List<RoleControlManager> importRoleControlManager(List<RoleControlManager> roleControlManagerList, Boolean isUpdateSupport, String operName);
}
