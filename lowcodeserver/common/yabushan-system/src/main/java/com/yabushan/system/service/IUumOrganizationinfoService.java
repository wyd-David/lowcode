package com.yabushan.system.service;

import com.yabushan.system.domain.UumOrganizationinfo;

import java.util.List;

/**
 * 同步组织信息Service接口
 *
 * @author yabushan
 * @date 2022-04-27
 */
public interface IUumOrganizationinfoService
{



    /**
     * 清空返回导入数据的集合
     */
    public void clearuumOrganizationinfoInfo();


    /**
     * 查询同步组织信息
     *
     * @param ouguid 同步组织信息ID
     * @return 同步组织信息
     */
    public UumOrganizationinfo selectUumOrganizationinfoById(String ouguid);

    /**
     * 查询同步组织信息列表
     *
     * @param uumOrganizationinfo 同步组织信息
     * @return 同步组织信息集合
     */
    public List<UumOrganizationinfo> selectUumOrganizationinfoList(UumOrganizationinfo uumOrganizationinfo);

    /**
     * 新增同步组织信息
     *
     * @param uumOrganizationinfo 同步组织信息
     * @return 结果
     */
    public int insertUumOrganizationinfo(UumOrganizationinfo uumOrganizationinfo);

    /**
     * 批量新增同步组织信息
     *
     * @param uumOrganizationinfo 同步组织信息
     * @return 结果
     */
    public int bathInsertUumOrganizationinfo(List<UumOrganizationinfo> uumOrganizationinfo);

    /**
     * 修改同步组织信息
     *
     * @param uumOrganizationinfo 同步组织信息
     * @return 结果
     */
    public int updateUumOrganizationinfo(UumOrganizationinfo uumOrganizationinfo);
    /**
     * 通过id集合修改状态
     */
    public Boolean updateByIds(String ids,String disableEnableState);

    /**
     * 批量删除同步组织信息
     *
     * @param ouguids 需要删除的同步组织信息ID
     * @return 结果
     */
    public int deleteUumOrganizationinfoByIds(String[] ouguids);

    /**
     * 删除同步组织信息信息
     *
     * @param ouguid 同步组织信息ID
     * @return 结果
     */
    public int deleteUumOrganizationinfoById(String ouguid);

    /**
     * 导入同步组织信息Excel数据
     *
     * @param uumOrganizationinfoList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public List<UumOrganizationinfo> importUumOrganizationinfo(List<UumOrganizationinfo> uumOrganizationinfoList, Boolean isUpdateSupport, String operName);

    public String  getDeptNameByLoginId(String userName);

    public List<UumOrganizationinfo>  getYjxtUumOrgInfo();
}
