package com.yabushan.system.service;

import java.util.List;
import com.yabushan.system.domain.SysFlowMain;

/**
 * 流程主表Service接口
 *
 * @author CJH
 * @date 2021-09-15
 */
public interface ISysFlowMainService
{
    /**
     * 查询流程主表
     *
     * @param id 流程主表ID
     * @return 流程主表
     */
    public SysFlowMain selectSysFlowMainById(String id);

    /**
     * 查询流程主表列表
     *
     * @param sysFlowMain 流程主表
     * @return 流程主表集合
     */
    public List<SysFlowMain> selectSysFlowMainList(SysFlowMain sysFlowMain);

    /**
     * 新增流程主表
     *
     * @param sysFlowMain 流程主表
     * @return 结果
     */
    public int insertSysFlowMain(SysFlowMain sysFlowMain);

    /**
     * 批量新增流程主表
     *
     * @param sysFlowMain 流程主表
     * @return 结果
     */
    public int bathInsertSysFlowMain(List<SysFlowMain> sysFlowMain);

    /**
     * 修改流程主表
     *
     * @param sysFlowMain 流程主表
     * @return 结果
     */
    public int updateSysFlowMain(SysFlowMain sysFlowMain);
    /**
    * 通过id集合修改状态
    */
    public Boolean updateByIds(String ids, String disableEnableState);

    /**
     * 批量删除流程主表
     *
     * @param ids 需要删除的流程主表ID
     * @return 结果
     */
    public int deleteSysFlowMainByIds(String[] ids);

    /**
     * 删除流程主表信息
     *
     * @param id 流程主表ID
     * @return 结果
     */
    public int deleteSysFlowMainById(String id);


}
