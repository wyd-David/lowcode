package com.yabushan.system.mapper;

import java.util.List;
import com.yabushan.system.domain.UumUserinfo;
import org.apache.ibatis.annotations.Param;

/**
 * 同步用户信息Mapper接口
 *
 * @author yabushan
 * @date 2022-04-27
 */
public interface UumUserinfoMapper
{
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
    public int bathInsertUumUserinfo(List<UumUserinfo> list);

    /**
     * 修改同步用户信息
     *
     * @param uumUserinfo 同步用户信息
     * @return 结果
     */
    public int updateUumUserinfo(UumUserinfo uumUserinfo);

    /**
     * 删除同步用户信息
     *
     * @param employee 同步用户信息ID
     * @return 结果
     */
    public int deleteUumUserinfoById(String employee);

    /**
     * 批量删除同步用户信息
     *
     * @param employees 需要删除的数据ID
     * @return 结果
     */
    public int deleteUumUserinfoByIds(String[] employees);

    UumUserinfo selectByUserId(String userId);

    List<UumUserinfo> getYjxtUumUserInfos();

    List<UumUserinfo> selectByUserIds(@Param("userIds") String[] userIds);
}
