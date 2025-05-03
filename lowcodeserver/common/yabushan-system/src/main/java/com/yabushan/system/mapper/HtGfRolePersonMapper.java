package com.yabushan.system.mapper;

import java.util.List;
import com.yabushan.system.domain.HtGfRolePerson;

/**
 *
 *
 * @author yabushan
 * @date 2021-09-24
 */
public interface HtGfRolePersonMapper
{
    /**
     *
     *
     * @param id
     * @return
     */
    public HtGfRolePerson selectHtGfRolePersonById(String id);

    /**
     *
     *
     *
     * @return
     */
    public List<HtGfRolePerson> selectHtGfRolePersonList(HtGfRolePerson htGfRolePerson);

    /**
     *
     *
     * @param htGfRolePerson
     * @return 结果
     */
    public int insertHtGfRolePerson(HtGfRolePerson htGfRolePerson);

    /**
     *
     *
     * @param htGfRolePerson
     * @return 结果
     */
    public int bathInsertHtGfRolePerson(List<HtGfRolePerson> list);

    /**
     * 修改部门角色用户
     *
     * @param htGfRolePerson 部门角色用户
     * @return 结果
     */
    public int updateHtGfRolePerson(HtGfRolePerson htGfRolePerson);

    /**
     * 删除部门角色用户
     *
     * @param id 部门角色用户ID
     * @return 结果
     */
    public int deleteHtGfRolePersonById(String id);

    /**
     * 批量删除部门角色用户
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteHtGfRolePersonByIds(String[] ids);

    public int getPersonByLoginId(String loginId);
}
