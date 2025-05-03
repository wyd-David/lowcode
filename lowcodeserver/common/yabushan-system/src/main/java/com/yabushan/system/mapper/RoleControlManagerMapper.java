package com.yabushan.system.mapper;

import java.util.List;
import com.yabushan.system.domain.RoleControlManager;

/**
 *  角色授权控制管理Mapper接口
 * 
 * @author zcm
 * @date 2021-09-22
 */
public interface RoleControlManagerMapper 
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
    public int bathInsertRoleControlManager(List<RoleControlManager> list);

    /**
     * 修改 角色授权控制管理
     * 
     * @param roleControlManager  角色授权控制管理
     * @return 结果
     */
    public int updateRoleControlManager(RoleControlManager roleControlManager);

    /**
     * 删除 角色授权控制管理
     * 
     * @param roleControlManagerId  角色授权控制管理ID
     * @return 结果
     */
    public int deleteRoleControlManagerById(String roleControlManagerId);

    /**
     * 批量删除 角色授权控制管理
     * 
     * @param roleControlManagerIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteRoleControlManagerByIds(String[] roleControlManagerIds);
}
