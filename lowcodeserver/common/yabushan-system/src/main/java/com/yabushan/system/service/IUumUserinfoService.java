package com.yabushan.system.service;

import com.yabushan.system.domain.UumUserinfo;

import java.util.List;

/**
 * 同步用户信息Service接口
 *
 * @author yabushan
 * @date 2022-04-27
 */
public interface IUumUserinfoService
{



    /**
     * 清空返回导入数据的集合
     */
    public void clearuumUserinfoInfo();


    /**
     * 查询同步用户信息
     *
     * @param employee 同步用户信息ID
     * @return 同步用户信息
     */
    public UumUserinfo selectUumUserinfoById(String employee);

    /**
     * 查询同步用户信息列表
     *
     * @param uumUserinfo 同步用户信息
     * @return 同步用户信息集合
     */
    public List<UumUserinfo> selectUumUserinfoList(UumUserinfo uumUserinfo);

    /**
     * 新增同步用户信息
     *
     * @param uumUserinfo 同步用户信息
     * @return 结果
     */
    public int insertUumUserinfo(UumUserinfo uumUserinfo);

    /**
     * 批量新增同步用户信息
     *
     * @param uumUserinfo 同步用户信息
     * @return 结果
     */
    public int bathInsertUumUserinfo(List<UumUserinfo> uumUserinfo);

    /**
     * 修改同步用户信息
     *
     * @param uumUserinfo 同步用户信息
     * @return 结果
     */
    public int updateUumUserinfo(UumUserinfo uumUserinfo);
    /**
     * 通过id集合修改状态
     */
    public Boolean updateByIds(String ids,String disableEnableState);

    /**
     * 批量删除同步用户信息
     *
     * @param employees 需要删除的同步用户信息ID
     * @return 结果
     */
    public int deleteUumUserinfoByIds(String[] employees);

    /**
     * 删除同步用户信息信息
     *
     * @param employee 同步用户信息ID
     * @return 结果
     */
    public int deleteUumUserinfoById(String employee);

    /**
     * 导入同步用户信息Excel数据
     *
     * @param uumUserinfoList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public List<UumUserinfo> importUumUserinfo(List<UumUserinfo> uumUserinfoList, Boolean isUpdateSupport, String operName);


    public UumUserinfo selectByUserId(String userId);
    public List<UumUserinfo> getYjxtUumUserInfos();

}
