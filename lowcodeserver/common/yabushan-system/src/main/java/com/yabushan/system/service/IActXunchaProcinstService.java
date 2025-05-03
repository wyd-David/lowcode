package com.yabushan.system.service;


import com.yabushan.system.domain.ActXunchaProcinst;
import com.yabushan.system.domain.vo.ActXunchaProcinstVO;

import java.util.List;

/**
 * 我的工单Service接口
 *
 * @author yabushan
 * @date 2022-07-07
 */
public interface IActXunchaProcinstService {


    /**
     * 清空返回导入数据的集合
     */
    public void clearactXunchaProcinstInfo();


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
     * @param actXunchaProcinst 我的工单
     * @return 结果
     */
    public int bathInsertActXunchaProcinst(List<ActXunchaProcinst> actXunchaProcinst);

    /**
     * 修改我的工单
     *
     * @param actXunchaProcinst 我的工单
     * @return 结果
     */
    public int updateActXunchaProcinst(ActXunchaProcinst actXunchaProcinst);

    /**
     * 通过id集合修改状态
     */
    public Boolean updateByIds(String ids, String disableEnableState);

    /**
     * 批量删除我的工单
     *
     * @param procinstIds 需要删除的我的工单ID
     * @return 结果
     */
    public int deleteActXunchaProcinstByIds(Long[] procinstIds);

    /**
     * 删除我的工单信息
     *
     * @param procinstId 我的工单ID
     * @return 结果
     */
    public int deleteActXunchaProcinstById(Long procinstId);


    /**
     * 通过流水号和类型删除我的工单信息
     *
     * @param businessKey 我的工单ID
     * @return 结果
     */
    public int deleteActXunchaProcinstByBusinessKey(String  businessKey);

    /**
     * 导入我的工单Excel数据
     *
     * @param actXunchaProcinstList 数据列表
     * @param isUpdateSupport       是否更新支持，如果已存在，则进行更新数据
     * @param operName              操作用户
     * @return 结果
     */
    public List<ActXunchaProcinst> importActXunchaProcinst(List<ActXunchaProcinst> actXunchaProcinstList, Boolean isUpdateSupport, String operName);

    List<ActXunchaProcinstVO> myWorkList(ActXunchaProcinst actXunchaProcinst);

    List<ActXunchaProcinst> myWorkListNew(ActXunchaProcinst actXunchaProcinst);
}
