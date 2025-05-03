package com.yabushan.form.mapper;

import java.util.List;
import com.yabushan.form.domain.LowBusinessList;

/**
 * 业务列Mapper接口
 * 
 * @author yabushan
 * @date 2022-10-15
 */
public interface LowBusinessListMapper 
{
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
    public int bathInsertLowBusinessList(List<LowBusinessList> list);

    /**
     * 修改业务列
     * 
     * @param lowBusinessList 业务列
     * @return 结果
     */
    public int updateLowBusinessList(LowBusinessList lowBusinessList);

    /**
     * 删除业务列
     * 
     * @param listId 业务列ID
     * @return 结果
     */
    public int deleteLowBusinessListById(String listId);

    /**
     * 批量删除业务列
     * 
     * @param listIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteLowBusinessListByIds(String[] listIds);
}
