package com.yabushan.system.mapper;

import java.util.List;
import com.yabushan.system.domain.RoleControl;

/**
 *  角色授权控制Mapper接口
 * 
 * @author zcm
 * @date 2021-09-22
 */
public interface RoleControlMapper 
{
    /**
     * 查询 角色授权控制
     * 
     * @param roleControlId  角色授权控制ID
     * @return  角色授权控制
     */
    public RoleControl selectRoleControlById(String roleControlId);

    /**
     * 查询 角色授权控制列表
     * 
     * @param roleControl  角色授权控制
     * @return  角色授权控制集合
     */
    public List<RoleControl> selectRoleControlList(RoleControl roleControl);

    /**
     * 新增 角色授权控制
     * 
     * @param roleControl  角色授权控制
     * @return 结果
     */
    public int insertRoleControl(RoleControl roleControl);

    /**
     * 批量新增 角色授权控制
     *
     * @param roleControl  角色授权控制
     * @return 结果
     */
    public int bathInsertRoleControl(List<RoleControl> list);

    /**
     * 修改 角色授权控制
     * 
     * @param roleControl  角色授权控制
     * @return 结果
     */
    public int updateRoleControl(RoleControl roleControl);

    /**
     * 删除 角色授权控制
     * 
     * @param roleControlId  角色授权控制ID
     * @return 结果
     */
    public int deleteRoleControlById(String roleControlId);

    /**
     * 批量删除 角色授权控制
     * 
     * @param roleControlIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteRoleControlByIds(String[] roleControlIds);
}
