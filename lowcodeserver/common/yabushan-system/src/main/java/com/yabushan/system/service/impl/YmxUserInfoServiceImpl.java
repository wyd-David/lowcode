package com.yabushan.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yabushan.system.mapper.YmxUserInfoMapper;
import com.yabushan.system.domain.YmxUserInfo;
import com.yabushan.system.service.IYmxUserInfoService;

/**
 * 用户Service业务层处理
 *
 * @author yabushan
 * @date 2021-04-02
 */
@Service
public class YmxUserInfoServiceImpl implements IYmxUserInfoService
{
    @Autowired
    private YmxUserInfoMapper ymxUserInfoMapper;

    /**
     * 查询用户
     *
     * @param userId 用户ID
     * @return 用户
     */
    @Override
    public YmxUserInfo selectYmxUserInfoById(String userId)
    {
        return ymxUserInfoMapper.selectYmxUserInfoById(userId);
    }

    /**
     * 查询用户列表
     *
     * @param ymxUserInfo 用户
     * @return 用户
     */
    @Override
    public List<YmxUserInfo> selectYmxUserInfoList(YmxUserInfo ymxUserInfo)
    {
        return ymxUserInfoMapper.selectYmxUserInfoList(ymxUserInfo);
    }

    /**
     * 新增用户
     *
     * @param ymxUserInfo 用户
     * @return 结果
     */
    @Override
    public int insertYmxUserInfo(YmxUserInfo ymxUserInfo)
    {
        return ymxUserInfoMapper.insertYmxUserInfo(ymxUserInfo);
    }

    /**
     * 修改用户
     *
     * @param ymxUserInfo 用户
     * @return 结果
     */
    @Override
    public int updateYmxUserInfo(YmxUserInfo ymxUserInfo)
    {
        return ymxUserInfoMapper.updateYmxUserInfo(ymxUserInfo);
    }

    /**
     * 批量删除用户
     *
     * @param userIds 需要删除的用户ID
     * @return 结果
     */
    @Override
    public int deleteYmxUserInfoByIds(String[] userIds)
    {
        return ymxUserInfoMapper.deleteYmxUserInfoByIds(userIds);
    }

    /**
     * 删除用户信息
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public int deleteYmxUserInfoById(String userId)
    {
        return ymxUserInfoMapper.deleteYmxUserInfoById(userId);
    }
}
