package com.yabushan.system.service.impl;

import java.util.List;

import com.yabushan.system.domain.vo.ActHiProcinstVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yabushan.system.mapper.ActHiTaskinstMapper;
import com.yabushan.system.domain.ActHiTaskinst;
import com.yabushan.system.service.IActHiTaskinstService;

/**
 * 流程列表Service业务层处理
 *
 * @author yabushan
 * @date 2021-04-25
 */
@Service
public class ActHiTaskinstServiceImpl implements IActHiTaskinstService
{
    @Autowired
    private ActHiTaskinstMapper actHiTaskinstMapper;

    /**
     * 查询流程列表
     *
     * @param id 流程列表ID
     * @return 流程列表
     */
    @Override
    public ActHiTaskinst selectActHiTaskinstById(String id)
    {
        return actHiTaskinstMapper.selectActHiTaskinstById(id);
    }

    /**
     * 查询流程列表列表
     *
     * @param actHiTaskinst 流程列表
     * @return 流程列表
     */
    @Override
    public List<ActHiTaskinst> selectActHiTaskinstList(ActHiTaskinst actHiTaskinst)
    {
        return actHiTaskinstMapper.selectActHiTaskinstList(actHiTaskinst);
    }

    @Override
    public List<ActHiProcinstVo> selectByKeyUserList(ActHiProcinstVo actHiProcinstVo) {
        return actHiTaskinstMapper.selectByKeyUserList(actHiProcinstVo);
    }

    /**
     * 新增流程列表
     *
     * @param actHiTaskinst 流程列表
     * @return 结果
     */
    @Override
    public int insertActHiTaskinst(ActHiTaskinst actHiTaskinst)
    {
        return actHiTaskinstMapper.insertActHiTaskinst(actHiTaskinst);
    }

    /**
     * 修改流程列表
     *
     * @param actHiTaskinst 流程列表
     * @return 结果
     */
    @Override
    public int updateActHiTaskinst(ActHiTaskinst actHiTaskinst)
    {
        return actHiTaskinstMapper.updateActHiTaskinst(actHiTaskinst);
    }

    /**
     * 批量删除流程列表
     *
     * @param ids 需要删除的流程列表ID
     * @return 结果
     */
    @Override
    public int deleteActHiTaskinstByIds(String[] ids)
    {
        return actHiTaskinstMapper.deleteActHiTaskinstByIds(ids);
    }

    /**
     * 删除流程列表信息
     *
     * @param id 流程列表ID
     * @return 结果
     */
    @Override
    public int deleteActHiTaskinstById(String id)
    {
        return actHiTaskinstMapper.deleteActHiTaskinstById(id);
    }
}
