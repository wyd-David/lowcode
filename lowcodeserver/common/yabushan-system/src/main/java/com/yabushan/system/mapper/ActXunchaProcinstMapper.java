package com.yabushan.system.mapper;

import com.yabushan.system.domain.ActXunchaProcinst;
import com.yabushan.system.domain.vo.ActXunchaProcinstVO;

import java.util.List;

/**
 * 我的工单Mapper接口
 *
 * @author yabushan
 * @date 2022-07-07
 */
public interface ActXunchaProcinstMapper {
    /**
     * 查询我的工单
     *
     * @param procinstId 我的工单ID
     * @return 我的工单
     */
    public ActXunchaProcinst selectActXunchaProcinstById(Long procinstId);

    /**
     * 查询我的工单列表
     *
     * @param actXunchaProcinst 我的工单
     * @return 我的工单集合
     */
    public List<ActXunchaProcinst> selectActXunchaProcinstList(ActXunchaProcinst actXunchaProcinst);

    /**
     * 新增我的工单
     *
     * @param actXunchaProcinst 我的工单
     * @return 结果
     */
    public int insertActXunchaProcinst(ActXunchaProcinst actXunchaProcinst);

    /**
     * 批量新增我的工单
     *
     * @param list 我的工单
     * @return 结果
     */
    public int bathInsertActXunchaProcinst(List<ActXunchaProcinst> list);

    /**
     * 修改我的工单
     *
     * @param actXunchaProcinst 我的工单
     * @return 结果
     */
    public int updateActXunchaProcinst(ActXunchaProcinst actXunchaProcinst);

    /**
     * 删除我的工单
     *
     * @param procinstId 我的工单ID
     * @return 结果
     */
    public int deleteActXunchaProcinstById(Long procinstId);
    /**
     * 通过流水号删除我的工单
     *
     * @param businessKey 我的工单ID
     * @return 结果
     */
    public int deleteActXunchaProcinstByBusinessKey(String businessKey);

    /**
     * 批量删除我的工单
     *
     * @param procinstIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteActXunchaProcinstByIds(Long[] procinstIds);

    List<ActXunchaProcinstVO> myWorkList(ActXunchaProcinst actXunchaProcinst);
}
