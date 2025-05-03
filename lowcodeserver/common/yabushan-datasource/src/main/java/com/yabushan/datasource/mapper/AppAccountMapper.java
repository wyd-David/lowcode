package com.yabushan.datasource.mapper;

import java.util.List;
import com.yabushan.datasource.domain.AppAccount;

/**
 * 应用实例账号Mapper接口
 *
 * @author yabushan
 * @date 2023-11-19
 */
public interface AppAccountMapper
{
    /**
     * 查询应用实例账号
     *
     * @param accountId 应用实例账号ID
     * @return 应用实例账号
     */
    public AppAccount selectAppAccountById(String accountId);

    /**
     * 查询应用实例账号列表
     *
     * @param appAccount 应用实例账号
     * @return 应用实例账号集合
     */
    public List<AppAccount> selectAppAccountList(AppAccount appAccount);

    /**
     * 新增应用实例账号
     *
     * @param appAccount 应用实例账号
     * @return 结果
     */
    public int insertAppAccount(AppAccount appAccount);

    /**
     * 批量新增应用实例账号
     *
     * @param appAccount 应用实例账号
     * @return 结果
     */
    public int bathInsertAppAccount(List<AppAccount> list);

    /**
     * 修改应用实例账号
     *
     * @param appAccount 应用实例账号
     * @return 结果
     */
    public int updateAppAccount(AppAccount appAccount);

    /**
     * 删除应用实例账号
     *
     * @param accountId 应用实例账号ID
     * @return 结果
     */
    public int deleteAppAccountById(String accountId);

    /**
     * 批量删除应用实例账号
     *
     * @param accountIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteAppAccountByIds(String[] accountIds);
}
