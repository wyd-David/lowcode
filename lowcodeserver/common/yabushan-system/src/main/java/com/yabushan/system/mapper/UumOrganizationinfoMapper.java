package com.yabushan.system.mapper;

import com.yabushan.system.domain.UumOrganizationinfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 同步组织信息Mapper接口
 *
 * @author yabushan
 * @date 2022-04-27
 */
public interface UumOrganizationinfoMapper
{
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
    public int bathInsertUumOrganizationinfo(List<UumOrganizationinfo> list);

    /**
     * 修改同步组织信息
     *
     * @param uumOrganizationinfo 同步组织信息
     * @return 结果
     */
    public int updateUumOrganizationinfo(UumOrganizationinfo uumOrganizationinfo);

    /**
     * 删除同步组织信息
     *
     * @param ouguid 同步组织信息ID
     * @return 结果
     */
    public int deleteUumOrganizationinfoById(String ouguid);

    /**
     * 批量删除同步组织信息
     *
     * @param ouguids 需要删除的数据ID
     * @return 结果
     */
    public int deleteUumOrganizationinfoByIds(String[] ouguids);

    /**
     * 根据id是否有子部门
     *
     * @param parentOuguid
     * @return 结果
     */
    int selectOrgByParentId(@Param("parentOuguid") String parentOuguid);

    /**
     * 查询公司信息
     *
     * @param map 同步组织信息
     * @return 同步组织信息集合
     */
    List<UumOrganizationinfo> selectCompanyinfoList(Map<String,Object> map);

    List<UumOrganizationinfo> getYjxtUumOrgInfo();

    public Map<String,Object> getCompanyInfo(Map<String,Object> map);

    public Map<String, Object> getDeptInfo(Map<String, Object> paramMap);
}
