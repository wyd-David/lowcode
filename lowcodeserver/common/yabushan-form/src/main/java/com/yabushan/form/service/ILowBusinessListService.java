package com.yabushan.form.service;

import java.util.List;
import com.yabushan.form.domain.LowBusinessList;

/**
 * 业务列Service接口
 *
 * @author yabushan
 * @date 2022-10-15
 */
public interface ILowBusinessListService
{



    /**
    * 清空返回导入数据的集合
    */
    public void clearlowBusinessListInfo();


    /**
     * 查询业务列
     *
     * @param listId 业务列ID
     * @return 业务列
     */
    public LowBusinessList selectLowBusinessListById(String listId);

    /**
     * 查询业务列列表
     *
     * @param lowBusinessList 业务列
     * @return 业务列集合
     */
    public List<LowBusinessList> selectLowBusinessListList(LowBusinessList lowBusinessList);

    public List<LowBusinessList> selectLowBusinessListListPage(LowBusinessList lowBusinessList);

    /**
     * 新增业务列
     *
     * @param lowBusinessList 业务列
     * @return 结果
     */
    public int insertLowBusinessList(LowBusinessList lowBusinessList);

    /**
     * 批量新增业务列
     *
     * @param lowBusinessList 业务列
     * @return 结果
     */
    public int bathInsertLowBusinessList(List<LowBusinessList> lowBusinessList);

    /**
     * 修改业务列
     *
     * @param lowBusinessList 业务列
     * @return 结果
     */
    public int updateLowBusinessList(LowBusinessList lowBusinessList);
    /**
    * 通过id集合修改状态
    */
    public Boolean updateByIds(String ids,String disableEnableState);

    /**
     * 批量删除业务列
     *
     * @param listIds 需要删除的业务列ID
     * @return 结果
     */
    public int deleteLowBusinessListByIds(String[] listIds);

    /**
     * 删除业务列信息
     *
     * @param listId 业务列ID
     * @return 结果
     */
    public int deleteLowBusinessListById(String listId);

    /**
     * 导入业务列Excel数据
     *
     * @param lowBusinessListList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public List<LowBusinessList> importLowBusinessList(List<LowBusinessList> lowBusinessListList, Boolean isUpdateSupport, String operName);
}
