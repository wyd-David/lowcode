package com.yabushan.system.mapper;

import java.util.List;
import com.yabushan.system.domain.SysFlowMain;

/**
 * 流程主表Mapper接口
 *
 * @author CJH
 * @date 2021-09-15
 */
public interface SysFlowMainMapper
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
     * @param list 流程主表
     * @return 结果
     */
    public int bathInsertSysFlowMain(List<SysFlowMain> list);

    /**
     * 修改流程主表
     *
     * @param sysFlowMain 流程主表
     * @return 结果
     */
    public int updateSysFlowMain(SysFlowMain sysFlowMain);

    /**
     * 删除流程主表
     *
     * @param id 流程主表ID
     * @return 结果
     */
    public int deleteSysFlowMainById(String id);

    /**
     * 批量删除流程主表
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysFlowMainByIds(String[] ids);

    /**
     * 修改流程主表
     *
     * @param sysFlowMain
     * @return 结果
     */
    public int updateSysFlowMainByDocId(SysFlowMain sysFlowMain);


    /**
     * 通过DocId查询流程主表信息
     *
     * @param docId
     * @return 结果
     */
    public SysFlowMain selectByDocId (String docId);



}
