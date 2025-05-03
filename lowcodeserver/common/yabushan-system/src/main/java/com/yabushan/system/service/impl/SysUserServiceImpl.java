package com.yabushan.system.service.impl;

import com.yabushan.common.constant.Constants;
import com.yabushan.common.constant.UserConstants;
import com.yabushan.common.core.domain.entity.SysRole;
import com.yabushan.common.core.domain.entity.SysUser;
import com.yabushan.common.core.redis.RedisCache;
import com.yabushan.common.exception.CustomException;
import com.yabushan.common.exception.user.CaptchaException;
import com.yabushan.common.exception.user.CaptchaExpireException;
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.system.domain.*;
import com.yabushan.system.domain.vo.DepUserVo;
import com.yabushan.system.domain.vo.SysUserVo;
import com.yabushan.system.enums.EnumsConfig;
import com.yabushan.system.mapper.*;
import com.yabushan.system.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户 业务层处理
 *
 * @author ruoyi
 */
@Service
public class SysUserServiceImpl implements ISysUserService {
    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private SysRoleMapper roleMapper;
    @Autowired
    private SysPostMapper postMapper;
    @Autowired
    private SysUserRoleMapper userRoleMapper;
    @Autowired
    private SysUserPostMapper userPostMapper;
    @Autowired
    private ISysConfigService configService;
    @Autowired
    private ISyncOrgService syncOrgService;
    @Autowired
    private ISyncUserService syncUserService;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ISysRoleService sysRoleService;
    @Autowired
    private SysSyncDepMapper sysSyncDepMapper;


    /**
     * 根据条件分页查询用户列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
//    @DataScope(deptAlias = "d", userAlias = "u.user_id")
    public List<SysUser> selectUserList(SysUser user) {
        String username = SecurityUtils.getUsername();
        if(!"admin".equals(username)){
                user.setCreateBy(username);
        }
        return userMapper.selectUserList(user);
    }

    /**
     * 根据条件分页查询用户列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    public List<SysUser> selectUserListByRoleId(SysUserVo user) {
        return userMapper.selectUserListByRoleId(user);
    }

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserByUserName(String userName) {
        return userMapper.selectUserByUserName(userName);
    }

    @Override
    public SysUser selectUserByphoneNumber(String phoneNumber) {

        return userMapper.selectUserByphonenumber(phoneNumber);
    }

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserById(Long userId) {
        return userMapper.selectUserById(userId);
    }

    /**
     * 查询用户所属角色组
     *
     * @param userName 用户名
     * @return 结果
     */
    @Override
    public String selectUserRoleGroup(String userName) {
        List<SysRole> list = roleMapper.selectRolesByUserName(userName);
        StringBuffer idsStr = new StringBuffer();
        for (SysRole role : list) {
            idsStr.append(role.getRoleName()).append(",");
        }
        if (StringUtils.isNotEmpty(idsStr.toString())) {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }

    /**
     * 查询用户所属岗位组
     *
     * @param userName 用户名
     * @return 结果
     */
    @Override
    public String selectUserPostGroup(String userName) {
        List<SysPost> list = postMapper.selectPostsByUserName(userName);
        StringBuffer idsStr = new StringBuffer();
        for (SysPost post : list) {
            idsStr.append(post.getPostName()).append(",");
        }
        if (StringUtils.isNotEmpty(idsStr.toString())) {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }

    /**
     * 校验用户名称是否唯一
     *
     * @param userName 用户名称
     * @return 结果
     */
    @Override
    public String checkUserNameUnique(String userName) {
        int count = userMapper.checkUserNameUnique(userName);
        if (count > 0) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验用户名称是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public String checkPhoneUnique(SysUser user) {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = userMapper.checkPhoneUnique(user.getPhonenumber());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验email是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public String checkEmailUnique(SysUser user) {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = userMapper.checkEmailUnique(user.getEmail());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验用户是否允许操作
     *
     * @param user 用户信息
     */
    @Override
    public void checkUserAllowed(SysUser user) {
       /* if (StringUtils.isNotNull(user.getUserId()) && user.isAdmin()) {
            throw new CustomException("不允许操作超级管理员用户");
        }*/
    }

    /**
     * 新增保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional
    public int insertUser(SysUser user) {
        // 新增用户信息
        user.setUserType("11");
        user.setMaindepartment("true");
        int rows = userMapper.insertUser(user);
        // 新增用户岗位关联
        insertUserPost(user);
        // 新增用户与角色管理
        insertUserRole(user);

        SysRole role = sysRoleService.getRoleByKey(EnumsConfig.ROLE_KEY_EXTERNAL.getValue());
        if (role != null) {
            int num = sysRoleService.selectRoleByUserId(user.getUserName(), String.valueOf(role.getRoleId()));
            if (num == 0) {
                sysRoleService.insertUserRole(user.getUserId(), String.valueOf(role.getRoleId()));
            }
        }
        return rows;
    }

    /**
     * 修改保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateUser(SysUser user) {
        Long userId = user.getUserId();
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 新增用户与角色管理
        insertUserRole(user);
        // 删除用户与岗位关联
        userPostMapper.deleteUserPostByUserId(userId);
        // 新增用户与岗位管理
        insertUserPost(user);
        return userMapper.updateUser(user);
      /*  SysSyncDep sysSyncDep = sysSyncDepMapper.selectSysSyncBydept(String.valueOf(user.getDeptId()));
        SyncUser syncUser = new SyncUser();
        syncUser.setUserid(user.getUserName());
        syncUser.setDpid(sysSyncDep!=null ? sysSyncDep.getSyncDepId():"");
        return syncUserService.updateSyncUser(syncUser);*/
    }

    /**
     * 修改用户状态
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUserStatus(SysUser user) {
        return userMapper.updateUser(user);
    }

    /**
     * 修改用户基本信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUserProfile(SysUser user) {
        return userMapper.updateUser(user);
    }

    /**
     * 修改用户头像
     *
     * @param userName 用户名
     * @param avatar   头像地址
     * @return 结果
     */
    @Override
    public boolean updateUserAvatar(String userName, String avatar) {
        return userMapper.updateUserAvatar(userName, avatar) > 0;
    }

    /**
     * 重置用户密码
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int resetPwd(SysUser user) {
        return userMapper.updateUser(user);
    }

    /**
     * 重置用户密码
     *
     * @param userName 用户名
     * @param password 密码
     * @return 结果
     */
    @Override
    public int resetUserPwd(String userName, String password) {
        return userMapper.resetUserPwd(userName, password);
    }

    /**
     * 新增用户角色信息
     *
     * @param user 用户对象
     */
    public void insertUserRole(SysUser user) {
        Long[] roles = user.getRoleIds();
        if (StringUtils.isNotNull(roles)) {
            // 新增用户与角色管理
            List<SysUserRole> list = new ArrayList<SysUserRole>();
            for (Long roleId : roles) {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(user.getUserId());
                ur.setRoleId(roleId);
                list.add(ur);
            }
            if (list.size() > 0) {
                userRoleMapper.batchUserRole(list);
            }
        }
    }

    /**
     * 新增用户岗位信息
     *
     * @param user 用户对象
     */
    public void insertUserPost(SysUser user) {
        Long[] posts = user.getPostIds();
        if (StringUtils.isNotNull(posts)) {
            // 新增用户与岗位管理
            List<SysUserPost> list = new ArrayList<SysUserPost>();
            for (Long postId : posts) {
                SysUserPost up = new SysUserPost();
                up.setUserId(user.getUserId());
                up.setPostId(postId);
                list.add(up);
            }
            if (list.size() > 0) {
                userPostMapper.batchUserPost(list);
            }
        }
    }

    /**
     * 通过用户ID删除用户
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public int deleteUserById(Long userId) {
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 删除用户与岗位表
        userPostMapper.deleteUserPostByUserId(userId);
        return userMapper.deleteUserById(userId);
    }

    /**
     * 批量删除用户信息
     *
     * @param userIds 需要删除的用户ID
     * @return 结果
     */
    @Override
    public int deleteUserByIds(Long[] userIds) {
        for (Long userId : userIds) {
            checkUserAllowed(new SysUser(userId));
        }
        return userMapper.deleteUserByIds(userIds);
    }

    /**
     * 导入用户数据
     *
     * @param userList        用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName        操作用户
     * @return 结果
     */
    @Override
    public String importUser(List<SysUser> userList, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(userList) || userList.size() == 0) {
            throw new CustomException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String password = configService.selectConfigByKey("sys.user.initPassword");
        for (SysUser user : userList) {
            try {
                // 验证是否存在这个用户
                SysUser u = userMapper.selectUserByUserName(user.getUserName());
                if (StringUtils.isNull(u)) {
                    user.setPassword(SecurityUtils.encryptPassword(password));
                    user.setCreateBy(operName);
                    this.insertUser(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + user.getUserName() + " 导入成功");
                } else if (isUpdateSupport) {
                    user.setUpdateBy(operName);
                    this.updateUser(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + user.getUserName() + " 更新成功");
                } else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、账号 " + user.getUserName() + " 已存在");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、账号 " + user.getUserName() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new CustomException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    /**
     * 通过用户名查询用户
     *
     * @param nickName 用户名
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserByNickName(String nickName) {
        return userMapper.selectUserByNickName(nickName);
    }


    public List<SysUserVo> getUserList(DepUserVo depUserVo) {
        List<SysUserVo> userList = new ArrayList<>();
        try {
            if (StringUtils.isEmpty(depUserVo.getDeptId()) && StringUtils.isEmpty(depUserVo.getCompany()) &&
                    StringUtils.isEmpty(depUserVo.getRoleId()) && !StringUtils.isEmpty(depUserVo.getUserName())) {
                String depId = syncOrgService.selectSyncOrgBySysDep(depUserVo.getUserName());
                depUserVo.setDeptId(depId);
            }
            userList = userMapper.getSysUserByDep(depUserVo);
        } catch (Exception e) {
            //System.out.println(e.getMessage());
        }

        return userList;
    }

    @Override
    public List<SyncUser> getUserInfoList(SysUserVo vo) {
        List<SyncUser> result = new ArrayList<>();
        //1.获取用户所在组织信息
        SyncUser user = new SyncUser();
        user.setLoginid(vo.getUserName());
        List<SyncUser> syncUsers = syncUserService.selectSyncUserList(user);
        //递归获取该用户的直接上级
        Boolean flag = true;
        while (flag) {
            if (syncUsers.size() > 0) {
                SyncUser supLeader = syncUsers.get(0);
                if (vo.getPostName().equals(supLeader.getPositioncat())) {
                    //如果人员的岗位信息和需要获取的岗位信息一致，则添加到队列中
                    result.add(supLeader);
                    //是否只要查找到就立即返回
                    break;
                }
                if (StringUtils.isNotEmpty(supLeader.getSuperiorleader())) {
                    //如果上级不为空则递归查找上级
                    user = new SyncUser();
                    user.setErpid(supLeader.getSuperiorleader());
                    syncUsers = syncUserService.selectSyncUserList(user);
                    if (syncUsers.size() == 0) {
                        //查不到上级返回空
                        flag = false;
                    } else {
                        //存在上级，将上级添加到结果集中
                        //if(){}
                    }
                } else {
                    flag = false;
                }
            } else {

                flag = false;
            }
        }


        // try {
           /* if(syncUsers.size()>0){
                SyncUser user1 = syncUsers.get(0);
                SyncOrg org = new SyncOrg();
                org.setDpid(user1.getDpid());
                List<SyncOrg> syncOrgs = syncOrgService.selectSyncOrgList(org);
                if(syncOrgs.size()>0){
                    String[] orgIdS =syncOrgs.get(0).getDpcode().split("\\.");

                    for (String dpid:orgIdS) {
                        user = new SyncUser();
                        user.setPositioncat(vo.getPostName());
                        user.setDpid(dpid);
                        List<SyncUser> syncUsers1 = syncUserService.selectSyncUserList(user);
                        if(syncUsers1.size()>0){
                            result.addAll(syncUsers1);
                           // result.add(syncUsers1.get(0));
                            //break;
                        }
                    }
                }
            }*/
        //  }catch (Exception e){
        //    System.out.println(e.getMessage());
        //}

        return result;
    }

    @Override
    public List<SysUser> getUserByRoleId(Long roleId) {
        return userMapper.getUserByRoleId(roleId);
    }

    @Override
    public List<SysUser> getUserByRoleKey(String roleKey) {
        return userMapper.getUserByRoleKey(roleKey);
    }

    public int registerUser(SysUser user, String code, String uuid) {
        int count = 0;
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null) {
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha)) {
            throw new CaptchaException();
        }
        user.setDeptId(Long.valueOf("8421"));
        user.setUserType("11");
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        user.setSyncUserId(user.getUserName());
        user.setMaindepartment("true");
        count = userMapper.insertUser(user);

        SyncUser syncUser = new SyncUser();
        syncUser.setUserid(user.getUserName());
        syncUser.setLoginid(user.getUserName());
        syncUser.setUsername(user.getNickName());
        syncUser.setDpid("E90F38A9FEF00B0048257708001027D2");
        syncUser.setPhone(user.getPhonenumber());
        syncUser.setMaindepartment("true");
        syncUser.setStatus("true");
        syncUserService.insertSyncUser(syncUser);

        SysRole role = sysRoleService.getRoleByKey(EnumsConfig.ROLE_KEY_EXTERNAL.getValue());
        if (role != null) {
            int num = sysRoleService.selectRoleByUserId(user.getUserName(), String.valueOf(role.getRoleId()));
            if (num == 0) {
                count = sysRoleService.insertUserRole(user.getUserId(), String.valueOf(role.getRoleId()));
            }
        }
        return count;
    }

    @Override
    public List<SyncUser> setlecDealUserByDept(SyncUser user) {
        SysSyncDep dep = sysSyncDepMapper.selectSysSyncBydept(user.getDpid());
        List<SyncUser> syncUsers = new ArrayList<>();
        boolean flag =true;
        if (null != dep) {
            user.setDpid(dep.getSyncDepId());
            syncUsers = syncUserService.selectSyncUserList(user);
            //没找到,往上递归
           if(null==syncUsers || syncUsers.size()==0){
               while(flag){
                   SyncOrg syncOrgBySyncDep = syncOrgService.getSyncOrgBySyncDep(user.getDpid());
                   if(syncOrgBySyncDep.getDplv()==1){
                       flag=false;
                       syncUsers=syncUserService.selectSyncUserList(user);
                   }else {
                       user.setDpid(syncOrgBySyncDep.getParentdpid());
                       syncUsers = syncUserService.selectSyncUserList(user);
                       if(null!=syncUsers && syncUsers.size()>0){
                         flag=false;
                     }
                   }
               }
             }
            }
        return syncUsers;
    }

    @Override
    public int checkIsInternal(String applyUser) {
     return  userMapper.checkIsInternal(applyUser);
    }


    public int AppRegisterUser(SysUser user) {
        
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        int  count = userMapper.insertUser(user);
        SysRole role = sysRoleService.getRoleByKey(EnumsConfig.ROLE_KEY_EXTERNAL.getValue());
        if (role != null) {
            int num = sysRoleService.selectRoleByUserId(user.getUserName(), String.valueOf(role.getRoleId()));
            if (num == 0) {
                count = sysRoleService.insertUserRole(user.getUserId(), String.valueOf(role.getRoleId()));
            }
        }
        return count;
    }
}
