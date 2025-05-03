package com.yabushan.datasource.service;

import java.util.List;
import com.yabushan.datasource.domain.AppAccount;

/**
 * 应用实例账号Service接口
 *
 * @author yabushan
 * @date 2023-11-19
 */
public interface IAppAccountService
{



    /**
     * 清空返回导入数据的集合
     */
    public void clearappAccountInfo();


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
    public int bathInsertAppAccount(List<AppAccount> appAccount);

    /**
     * 修改应用实例账号
     *
     * @param appAccount 应用实例账号
     * @return 结果
     */
    public int updateAppAccount(AppAccount appAccount);
    /**
     * 通过id集合修改状态
     */
    public Boolean updateByIds(String ids,String disableEnableState);

    /**
     * 批量删除应用实例账号
     *
     * @param accountIds 需要删除的应用实例账号ID
     * @return 结果
     */
    public int deleteAppAccountByIds(String[] accountIds);

    /**
     * 删除应用实例账号信息
     *
     * @param accountId 应用实例账号ID
     * @return 结果
     */
    public int deleteAppAccountById(String accountId);

    /**
     * 导入应用实例账号Excel数据
     *
     * @param appAccountList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public List<AppAccount> importAppAccount(List<AppAccount> appAccountList, Boolean isUpdateSupport, String operName);
}
