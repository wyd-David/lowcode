package com.yabushan.system.service;

import com.yabushan.system.domain.RoleControl;

import java.util.List;

/**
 *  角色授权控制Service接口
 *
 * @author zcm
 * @date 2021-09-22
 */
public interface IRoleControlService
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
    public int bathInsertRoleControl(List<RoleControl> roleControl);

    /**
     * 修改 角色授权控制
     *
     * @param roleControl  角色授权控制
     * @return 结果
     */
    public int updateRoleControl(RoleControl roleControl);
    /**
    * 通过id集合修改状态
    */
    public Boolean updateByIds(String ids,String disableEnableState);

    /**
     * 批量删除 角色授权控制
     *
     * @param roleControlIds 需要删除的 角色授权控制ID
     * @return 结果
     */
    public int deleteRoleControlByIds(String[] roleControlIds);

    /**
     * 删除 角色授权控制信息
     *
     * @param roleControlId  角色授权控制ID
     * @return 结果
     */
    public int deleteRoleControlById(String roleControlId);



    /**
     * 导入 角色授权控制Excel数据
     *
     * @param roleControlList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public List<RoleControl> importRoleControl(List<RoleControl> roleControlList, Boolean isUpdateSupport, String operName);
}
