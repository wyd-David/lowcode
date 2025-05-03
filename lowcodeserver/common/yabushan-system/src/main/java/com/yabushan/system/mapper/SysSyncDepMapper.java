package com.yabushan.system.mapper;

import com.yabushan.system.domain.SyncUser;
import com.yabushan.system.domain.SysSyncDep;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统部门与对接部门中间Mapper接口
 * 
 * @author yabushan
 * @date 2021-09-02
 */
public interface SysSyncDepMapper 
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
     * 删除系统部门与对接部门中间
     * 
     * @param id 系统部门与对接部门中间ID
     * @return 结果
     */
    public int deleteSysSyncDepById(Long id);

    /**
     * 批量删除系统部门与对接部门中间
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysSyncDepByIds(Long[] ids);

    /**
     * 根据对接部门Id查询是否同步到系统
     *
     * @param syncDepId 对接部门id
     * @return 结果
     */
    String selectDepIdBySync(@Param("syncDepId") String syncDepId);


    /**
     * 查询系统部门与对接部门中间列表
     *
     * @param sysSyncDep 系统部门与对接部门中间
     * @return 系统部门与对接部门中间集合
     */
    public SysSyncDep selectSysSyncDep(SysSyncDep sysSyncDep);


    public SysSyncDep selectSysSyncBydept(String  deptId);

    List<SyncUser> setlecDealUserByDept(String dpid);

}
