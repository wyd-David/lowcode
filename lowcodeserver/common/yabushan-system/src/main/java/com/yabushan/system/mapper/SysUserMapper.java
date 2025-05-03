package com.yabushan.system.mapper;

import java.util.List;

import com.yabushan.system.domain.vo.DepUserVo;
import com.yabushan.system.domain.vo.SysUserVo;
import org.apache.ibatis.annotations.Param;
import com.yabushan.common.core.domain.entity.SysUser;

/**
 * 用户表 数据层
 *
 * @author ruoyi
 */
public interface SysUserMapper
{
    /**
     * 根据条件分页查询用户列表
     *
     * @param sysUser 用户信息
     * @return 用户信息集合信息
     */
    public List<SysUser> selectUserList(SysUser sysUser);

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    public SysUser selectUserByUserName(String userName);

    /**
     * 通过同步用户id查询用户
     *
     * @param SyncUserId 同步用户id
     * @return 用户对象信息
     */
    public SysUser selectUserBySyncUserId(String SyncUserId);

    /**
     * 通过手机号查询用户
     *
     * @param phonenumber 手机号
     * @return 用户对象信息
     */
    public SysUser selectUserByphonenumber(String phonenumber);

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    public SysUser selectUserById(Long userId);

    /**
     * 新增用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    public int insertUser(SysUser user);

    /**
     * 修改用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    public int updateUser(SysUser user);

    /**
     * 修改用户头像
     *
     * @param userName 用户名
     * @param avatar 头像地址
     * @return 结果
     */
    public int updateUserAvatar(@Param("userName") String userName, @Param("avatar") String avatar);

    /**
     * 重置用户密码
     *
     * @param userName 用户名
     * @param password 密码
     * @return 结果
     */
    public int resetUserPwd(@Param("userName") String userName, @Param("password") String password);

    /**
     * 通过用户ID删除用户
     *
     * @param userId 用户ID
     * @return 结果
     */
    public int deleteUserById(Long userId);

    /**
     * 批量删除用户信息
     *
     * @param userIds 需要删除的用户ID
     * @return 结果
     */
    public int deleteUserByIds(Long[] userIds);

    /**
     * 校验用户名称是否唯一
     *
     * @param userName 用户名称
     * @return 结果
     */
    public int checkUserNameUnique(String userName);

    /**
     * 校验手机号码是否唯一
     *
     * @param phonenumber 手机号码
     * @return 结果
     */
    public SysUser checkPhoneUnique(String phonenumber);

    /**
     * 校验email是否唯一
     *
     * @param email 用户邮箱
     * @return 结果
     */
    public SysUser checkEmailUnique(String email);


    /**
     * 校验同步用户id是否唯一
     *
     * @param syncUserId 同步用户id
     * @return 结果
     */
    public int checkSyncUserId(String syncUserId);

    /**
     * 通过用户名查询用户
     *selectUserByNickName
     * @param nickName 用户名
     * @return 用户对象信息
     */
    public SysUser selectUserByNickName(String nickName);


    /**
     * 通过部门查询所有用户
     *getSysUserByDep
     * @param depUserVo
     * @return 用户对象信息
     */
    List<SysUserVo> getSysUserByDep(DepUserVo depUserVo);


    /**
     * 获取用户同组织同岗位的所有用户
     *getSysUserByDep
     * @param vo
     * @return 用户对象信息
     */
    List<SysUserVo> getUserInfoList(SysUserVo vo);


    /**
     * 根据角色Id获取人员信息
     * @param roleId
     * @return
     */
    public List<SysUser> getUserByRoleId(Long roleId);


    /**
     * 根据角色key获取人员信息
     * @param roleKey
     * @return
     */
    public List<SysUser> getUserByRoleKey(String roleKey);


    //根据角色Id获取用户
    public List<SysUser> selectUserListByRoleId(SysUserVo user);

    /**
     * 根据岗位Id获取人员信息
     * @param postId
     * @return
     */
    public List<SysUser> getUserByPostId(Long postId);

    /**
     * 判断是否是内部员工
     * @param userName
     * @return
     */
    public int checkIsInternal(String userName);
}
