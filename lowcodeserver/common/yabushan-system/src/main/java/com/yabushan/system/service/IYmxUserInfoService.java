package com.yabushan.system.service;

import java.util.List;
import com.yabushan.system.domain.YmxUserInfo;

/**
 * 用户Service接口
 * 
 * @author yabushan
 * @date 2021-04-02
 */
public interface IYmxUserInfoService 
{
    /**
     * 查询用户
     * 
     * @param userId 用户ID
     * @return 用户
     */
    public YmxUserInfo selectYmxUserInfoById(String userId);

    /**
     * 查询用户列表
     * 
     * @param ymxUserInfo 用户
     * @return 用户集合
     */
    public List<YmxUserInfo> selectYmxUserInfoList(YmxUserInfo ymxUserInfo);

    /**
     * 新增用户
     * 
     * @param ymxUserInfo 用户
     * @return 结果
     */
    public int insertYmxUserInfo(YmxUserInfo ymxUserInfo);

    /**
     * 修改用户
     * 
     * @param ymxUserInfo 用户
     * @return 结果
     */
    public int updateYmxUserInfo(YmxUserInfo ymxUserInfo);

    /**
     * 批量删除用户
     * 
     * @param userIds 需要删除的用户ID
     * @return 结果
     */
    public int deleteYmxUserInfoByIds(String[] userIds);

    /**
     * 删除用户信息
     * 
     * @param userId 用户ID
     * @return 结果
     */
    public int deleteYmxUserInfoById(String userId);
}
