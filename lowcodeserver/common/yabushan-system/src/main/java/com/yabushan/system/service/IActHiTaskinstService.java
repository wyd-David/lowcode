package com.yabushan.system.service;

import java.util.List;
import com.yabushan.system.domain.ActHiTaskinst;
import com.yabushan.system.domain.vo.ActHiProcinstVo;

/**
 * 流程列表Service接口
 *
 * @author yabushan
 * @date 2021-04-25
 */
public interface IActHiTaskinstService
{
    /**
     * 查询流程列表
     *
     * @param id 流程列表ID
     * @return 流程列表
     */
    public ActHiTaskinst selectActHiTaskinstById(String id);

    /**
     * 查询流程列表列表
     *
     * @param actHiTaskinst 流程列表
     * @return 流程列表集合
     */
    public List<ActHiTaskinst> selectActHiTaskinstList(ActHiTaskinst actHiTaskinst);
    /**
     * 根据业务ID、用户账号，状态，去获取任务taskId信息
     *
     */
    public List<ActHiProcinstVo> selectByKeyUserList(ActHiProcinstVo actHiProcinstVo);

    /**
     * 新增流程列表
     *
     * @param actHiTaskinst 流程列表
     * @return 结果
     */
    public int insertActHiTaskinst(ActHiTaskinst actHiTaskinst);

    /**
     * 修改流程列表
     *
     * @param actHiTaskinst 流程列表
     * @return 结果
     */
    public int updateActHiTaskinst(ActHiTaskinst actHiTaskinst);

    /**
     * 批量删除流程列表
     *
     * @param ids 需要删除的流程列表ID
     * @return 结果
     */
    public int deleteActHiTaskinstByIds(String[] ids);

    /**
     * 删除流程列表信息
     *
     * @param id 流程列表ID
     * @return 结果
     */
    public int deleteActHiTaskinstById(String id);
}
