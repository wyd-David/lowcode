package com.yabushan.system.service;

import java.util.List;
import com.yabushan.system.domain.HtGfRolePerson;

/**
 * 部门角色用户Service接口
 *
 * @author yabushan
 * @date 2021-09-24
 */
public interface IHtGfRolePersonService
{
    /**
     * 查询部门角色用户
     *
     * @param id 部门角色用户ID
     * @return 部门角色用户
     */
    public HtGfRolePerson selectHtGfRolePersonById(String id);

    /**
     * 查询部门角色用户列表
     *
     * @param htGfRolePerson 部门角色用户
     * @return 部门角色用户集合
     */
    public List<HtGfRolePerson> selectHtGfRolePersonList(HtGfRolePerson htGfRolePerson);

    /**
     * 新增部门角色用户
     *
     * @param htGfRolePerson 部门角色用户
     * @return 结果
     */
    public int insertHtGfRolePerson(HtGfRolePerson htGfRolePerson);

    /**
     * 批量新增部门角色用户
     *
     * @param htGfRolePerson 部门角色用户
     * @return 结果
     */
    public int bathInsertHtGfRolePerson(List<HtGfRolePerson> htGfRolePerson);

    /**
     * 修改部门角色用户
     *
     * @param htGfRolePerson 部门角色用户
     * @return 结果
     */
    public int updateHtGfRolePerson(HtGfRolePerson htGfRolePerson);
    /**
    * 通过id集合修改状态
    */
    public Boolean updateByIds(String ids, String disableEnableState);

    /**
     * 批量删除部门角色用户
     *
     * @param ids 需要删除的部门角色用户ID
     * @return 结果
     */
    public int deleteHtGfRolePersonByIds(String[] ids);

    /**
     * 删除部门角色用户信息
     *
     * @param id 部门角色用户ID
     * @return 结果
     */
    public int deleteHtGfRolePersonById(String id);


    public void initHtGfRolePerson(String urlTmp,String appId);
}
