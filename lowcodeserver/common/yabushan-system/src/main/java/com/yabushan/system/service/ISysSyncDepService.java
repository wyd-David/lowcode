package com.yabushan.system.service;

import java.util.List;
import com.yabushan.system.domain.SysSyncDep;

/**
 * 系统部门与对接部门中间Service接口
 *
 * @author yabushan
 * @date 2021-09-02
 */
public interface ISysSyncDepService
{
    /**
     * 查询系统部门与对接部门中间
     *
     * @param id 系统部门与对接部门中间ID
     * @return 系统部门与对接部门中间
     */
    public SysSyncDep selectSysSyncDepById(Long id);

    /**
     * 查询系统部门与对接部门中间列表
     *
     * @param sysSyncDep 系统部门与对接部门中间
     * @return 系统部门与对接部门中间集合
     */
    public List<SysSyncDep> selectSysSyncDepList(SysSyncDep sysSyncDep);

    /**
     * 新增系统部门与对接部门中间
     *
     * @param sysSyncDep 系统部门与对接部门中间
     * @return 结果
     */
    public int insertSysSyncDep(SysSyncDep sysSyncDep);

    /**
     * 修改系统部门与对接部门中间
     *
     * @param sysSyncDep 系统部门与对接部门中间
     * @return 结果
     */
    public int updateSysSyncDep(SysSyncDep sysSyncDep);

    /**
     * 批量删除系统部门与对接部门中间
     *
     * @param ids 需要删除的系统部门与对接部门中间ID
     * @return 结果
     */
    public int deleteSysSyncDepByIds(Long[] ids);

    /**
     * 删除系统部门与对接部门中间信息
     *
     * @param id 系统部门与对接部门中间ID
     * @return 结果
     */
    public int deleteSysSyncDepById(Long id);

}
