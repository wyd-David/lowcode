package com.yabushan.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yabushan.common.core.domain.entity.SysRole;
import com.yabushan.common.core.domain.entity.SysUser;
import com.yabushan.common.exception.CustomException;
import com.yabushan.common.utils.DateUtils;
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.common.utils.http.HttpUtils;
import com.yabushan.system.domain.*;
import com.yabushan.system.enums.EnumsConfig;
import com.yabushan.system.mapper.*;
import com.yabushan.system.service.ISyncOrgService;
import com.yabushan.system.service.ISyncUserService;
import com.yabushan.system.service.ISysRoleService;
import com.yabushan.system.service.ISystemJobLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 同步用户信息Service业务层处理
 *
 * @author yabushan
 * @date 2021-08-24
 */
@Service
public class SyncUserServiceImpl implements ISyncUserService {
    private static final Logger log = LoggerFactory.getLogger(SyncUserServiceImpl.class);
    @Autowired
    private SyncUserMapper syncUserMapper;
    @Autowired
    private ISyncOrgService orgService;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysSyncDepMapper sysSyncDepMapper;
    @Autowired
    private SysDictDataMapper sysDictDataMapper;
    @Autowired
    private SysPostMapper sysPostMapper;
    @Autowired
    private SysUserPostMapper sysUserPostMapper;
    @Autowired
    private ISystemJobLogService systemJobLogService;
    @Autowired
    private ISysRoleService sysRoleService;
    @Autowired
    private UumSyncLogMapper uumSyncLogMapper;
    @Autowired
    private UumUserinfoMapper uumUserinfoMapper;


    /**
     * 查询同步用户信息
     *
     * @param id 同步用户信息ID
     * @return 同步用户信息
     */
    @Override
    public SyncUser selectSyncUserById(Long id) {
        return syncUserMapper.selectSyncUserById(id);
    }

    /**
     * 查询同步用户信息列表
     *
     * @param syncUser 同步用户信息
     * @return 同步用户信息
     */
    @Override
    public List<SyncUser> selectSyncUserList(SyncUser syncUser) {
        return syncUserMapper.selectSyncUserList(syncUser);
    }

    /**
     * 新增同步用户信息
     *
     * @param syncUser 同步用户信息
     * @return 结果
     */
    @Override
    public int insertSyncUser(SyncUser syncUser) {
        return syncUserMapper.insertSyncUser(syncUser);
    }

    /**
     * 修改同步用户信息
     *
     * @param syncUser 同步用户信息
     * @return 结果
     */
    @Override
    public int updateSyncUser(SyncUser syncUser) {
        return syncUserMapper.updateSyncUser(syncUser);
    }

    /**
     * 批量删除同步用户信息
     *
     * @param ids 需要删除的同步用户信息ID
     * @return 结果
     */
    @Override
    public int deleteSyncUserByIds(Long[] ids) {
        return syncUserMapper.deleteSyncUserByIds(ids);
    }

    /**
     * 删除同步用户信息信息
     *
     * @param id 同步用户信息ID
     * @return 结果
     */
    @Override
    public int deleteSyncUserById(Long id) {
        return syncUserMapper.deleteSyncUserById(id);
    }


    /**
     * 导入同步用户信息Excel数据
     *
     * @param syncUserList    数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public String importSyncUser(List<SyncUser> syncUserList, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(syncUserList) || syncUserList.size() == 0) {
            throw new CustomException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (SyncUser syncUser : syncUserList) {
            try {
                // 验证是否存在这个用户
                SyncUser u = syncUserMapper.selectSyncUserById(syncUser.getId());
                if (StringUtils.isNull(u)) {
                    syncUserMapper.insertSyncUser(syncUser);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + operName + " 导入成功");
                } else if (isUpdateSupport) {
                    syncUserMapper.updateSyncUser(syncUser);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + operName + " 更新成功");
                } else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、账号 " + operName + " 已存在");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、账号 " + operName + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
                throw new CustomException(failureMsg.toString());
            } else {
                successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
            }
        }
        return successMsg.toString();
    }

    @Override
    public void insertOrUpdateSynUser() {
        int totalCount = 0; //总数量
        String batchId = StringUtils.getUUID(); //数据批次id
        SystemJobLog jobLog = new SystemJobLog();
        jobLog.setApplyName("公共模块");
        jobLog.setApplyMiaos("同步用户数据");
        jobLog.setDataBatchId(batchId);
        jobLog.setJobName("同步用户信息");
        jobLog.setExeMethod("saveOrUpdateSynOrg");
        try {
            UumSyncLog uumSyncLog = uumSyncLogMapper.selectUumSyncLogByType("1");
            UumUserinfo uumUserinfo = new UumUserinfo();
            if (uumSyncLog != null && StringUtils.isNotEmpty(uumSyncLog.getMaxTime())) {
                uumUserinfo.setLastmodifytime(DateUtils.dateTime("yyyy-MM-dd HH:mm:ss" , uumSyncLog.getMaxTime()));
            }
            uumUserinfo.setSortField("LASTMODIFYTIME");
            List<UumUserinfo> uumUserinfoList = uumUserinfoMapper.selectUumUserinfoList(uumUserinfo);
            //清除表数据重新录入
            for (UumUserinfo userinfo : uumUserinfoList) {
                SystemJobLogBatch logBatch = new SystemJobLogBatch();
                logBatch.setDataBatchId(batchId);
                try {
                    //获取部门
                    String depId = sysSyncDepMapper.selectDepIdBySync(userinfo.getOuguid());
                    SysUser sysUser = sysUserMapper.selectUserByUserName(userinfo.getUserid());
                    if (sysUser != null) {
                        sysUser.setNickName(userinfo.getFullname());
                        sysUser.setEmail(userinfo.getEmail());
                        sysUser.setPhonenumber(userinfo.getTelephone());
                        sysUser.setDeptId(Long.valueOf(depId));
                        sysUserMapper.updateUser(sysUser);
                    } else {
                        sysUser = new SysUser();
                        sysUser.setDeptId(Long.valueOf(depId));
                        sysUser.setUserName(userinfo.getUserid());
                        sysUser.setNickName(userinfo.getFullname());
                        sysUser.setEmail(userinfo.getEmail());
                        sysUser.setPassword(SecurityUtils.encryptPassword(EnumsConfig.INIT_PASSWORD.getValue()));
                        sysUser.setPhonenumber(userinfo.getTelephone());
                        sysUser.setUserType("00");
                        sysUserMapper.insertUser(sysUser);
                        SysRole role = sysRoleService.getRoleByKey(EnumsConfig.ROLE_KEY_INSIDE.getValue());
                        if (role != null) {
                            int num = sysRoleService.selectRoleBySyncUserId(sysUser.getSyncUserId(), String.valueOf(role.getRoleId()));
                            if (num == 0) {
                                sysRoleService.insertUserRole(sysUser.getUserId(), String.valueOf(role.getRoleId()));
                            }
                        }
                    }


                    //日志
                    //start++;
                } catch (Exception e) {
                    logBatch.setJobStatus(EnumsConfig.LOG_STATUS_FAIL.getValue());
                    logBatch.setJobExceptionInfo(e.getMessage());
                    //异常信息默认显示（始终）
                    log.info(e.getMessage());
                }
//                finally {
//                    systemJobLogBatchService.insertSystemJobLogBatch(logBatch);
//                }
            }
            if(uumUserinfoList.size() > 0){
                totalCount = uumUserinfoList.size();
                String maxTime = DateUtils.parseDateToStr("yyyy-MM-dd HH:mm:ss",uumUserinfoList.get(uumUserinfoList.size()-1).getLastmodifytime());
                uumSyncLogMapper.insertUumSyncLog(new UumSyncLog(StringUtils.getUUID(),maxTime,"200",null,Long.valueOf(totalCount),"1"));
            }

        } catch (Exception e) {
            jobLog.setJobStatus(EnumsConfig.LOG_STATUS_FAIL.getValue());
            jobLog.setJobExceptionInfo(e.getMessage());
            log.error("获取同步用户信息错误:" , e.getMessage());
            uumSyncLogMapper.insertUumSyncLog(new UumSyncLog(StringUtils.getUUID(),DateUtils.getTime(),"500",e.getMessage(),0l,"1"));
        } finally {
            jobLog.setDataNum(String.valueOf(totalCount));
            systemJobLogService.insertSystemJobLog(jobLog);
        }
    }

    public void initUserPost(String postName, Long userId) {
        //判断岗位是否存在，不存在则新增岗位信息
        SysPost sysPost = sysPostMapper.checkPostNameUnique(postName);
        if (org.springframework.util.StringUtils.isEmpty(sysPost)) {
            SysPost post = new SysPost();
            post.setPostCode(StringUtils.getUUID());
            post.setPostName(postName);
            post.setStatus(EnumsConfig.POST_STATUS_ENABLE.getValue());
            post.setCreateTime(new Date());
            sysPostMapper.insertPost(post);
        }
        sysUserPostMapper.deleteUserPostByUserId(userId);
        SysUserPost userPost = new SysUserPost();
        userPost.setPostId(sysPost.getPostId());
        userPost.setUserId(userId);
        sysUserPostMapper.insertSysUserPost(userPost);
    }


    public void initUser() {
        try {
            //获取系统中不存在岗位信息并写入到系统中
            List<String> postList = syncUserMapper.getSysPost();
            for (String key : postList) {
                //判断岗位是否存在，不存在则新增岗位信息
                SysPost sysPost = sysPostMapper.checkPostNameUnique(key);
                if (org.springframework.util.StringUtils.isEmpty(sysPost)) {
                    SysPost post = new SysPost();
                    post.setPostCode(StringUtils.getUUID());
                    post.setPostName(key);
                    post.setStatus(EnumsConfig.POST_STATUS_ENABLE.getValue());
                    post.setCreateTime(new Date());
                    sysPostMapper.insertPost(post);
                }
            }
            //获取所有同步用户信息
            List<SyncUser> list = syncUserMapper.selectSyncUserList(new SyncUser());
            for (SyncUser user : list) {
                long postId = 0;
                long userId = 0;
                //判断岗位信息不为空时获取岗位信息进行与系统用户关联
                if (!StringUtils.isEmpty(user.getPositioncat())) {
                    SysPost sysPost = sysPostMapper.checkPostNameUnique(user.getPositioncat());
                    if (!org.springframework.util.StringUtils.isEmpty(sysPost)) {
                        postId = sysPost.getPostId();
                    }
                }
                //根据同步组织信息获取关联系统部门信息
                String depId = sysSyncDepMapper.selectDepIdBySync(user.getDpid());
                //int count = sysUserMapper.checkSyncUserId(user.getUserid());
                SysUser sysUser1 = sysUserMapper.selectUserByUserName(user.getLoginid());
                //从数据字典中获取性别
                String sex = "";
                if (StringUtils.isNotEmpty(user.getSex())) {
                    sex = sysDictDataMapper.selectDictValue("sys_user_sex" , user.getSex());
                }
                //判断用户是否存在，存在则更新用户信息，不存在则新增用户信息
//                if(count > 0){
                if (sysUser1 != null && sysUser1.getMaindepartment().equals("true")) {
                    //SysUser sysUser = sysUserMapper.selectUserBySyncUserId(user.getUserid());
                    SysUser sysUser = sysUserMapper.selectUserByUserName(user.getLoginid());
                    sysUser.setNickName(user.getUsername());
                    sysUser.setEmail(user.getMailbox());
                    sysUser.setPhonenumber(user.getMobile());
                    sysUser.setSex(sex);
                    sysUser.setSyncUserId(user.getUserid());
                    sysUser.setMaindepartment(user.getMaindepartment());
                    sysUser.setDeptId(Long.valueOf(depId));
                    sysUserMapper.updateUser(sysUser);
                    userId = sysUser.getUserId();
                } else {
                    SysUser sysUser = new SysUser();
                    sysUser.setDeptId(Long.valueOf(depId));
                    sysUser.setUserName(user.getLoginid());
                    sysUser.setNickName(user.getUsername());
                    sysUser.setEmail(user.getMailbox());
                    sysUser.setPassword(SecurityUtils.encryptPassword(EnumsConfig.INIT_PASSWORD.getValue()));
                    sysUser.setPhonenumber(user.getMobile());
                    sysUser.setSyncUserId(user.getUserid());
                    sysUser.setMaindepartment(user.getMaindepartment());
                    sysUser.setSex(sex);
                    sysUser.setUserType("00");
                    sysUserMapper.insertUser(sysUser);
                    userId = sysUser.getUserId();
                    SysRole role = sysRoleService.getRoleByKey(EnumsConfig.ROLE_KEY_INSIDE.getValue());
                    if (role != null) {
                        int num = sysRoleService.selectRoleBySyncUserId(sysUser.getSyncUserId(), String.valueOf(role.getRoleId()));
                        if (num == 0) {
                            sysRoleService.insertUserRole(userId, String.valueOf(role.getRoleId()));
                        }
                    }
                }
                //获取到岗位信息后将用户与岗位信息关联
                if (postId != 0) {
                    sysUserPostMapper.deleteUserPostByUserId(userId);
                    SysUserPost userPost = new SysUserPost();
                    userPost.setPostId(postId);
                    userPost.setUserId(userId);
                    sysUserPostMapper.insertSysUserPost(userPost);
                }
            }
        } catch (Exception e) {
            log.error("同步写入用户信息错误：" , e.getMessage());
        }

    }


    /**
     * 计算页数
     *
     * @param count 查询数据库后获得的返回结果
     */
    public int doPage(int count, int pageSize) {
        int totalPage = 1;
        totalPage = count / pageSize;    //计算出一共有多少页
        if (count % pageSize != 0) {    //如果有余数
            totalPage += 1;        //总页数+1
        }

        return totalPage;
    }

    @Override
    public boolean isDepartmentHead(String positioncat, String loginid, String erp) {
        Integer result = syncUserMapper.isDepartmentHead(positioncat, loginid, erp);
        if (result == 0) {
            //等于0说明该员工不是部门领导
            return false;
        } else {
            //该员工是部门领导
            return true;
        }

    }

    @Override
    public List<SyncUser> isDepartmentHeadList(String positioncat, String loginid, String erp) {

        return syncUserMapper.isDepartmentHeadList(positioncat, loginid, erp);
    }

    @Override
    public List<SyncUser> setlecDealUserByDept(SyncUser user) {
        return syncUserMapper.setlecDealUserByDept(user);
    }

}
