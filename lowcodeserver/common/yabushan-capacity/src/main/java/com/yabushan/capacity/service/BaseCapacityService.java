package com.yabushan.capacity.service;

import com.yabushan.common.constant.Constants;
import com.yabushan.common.core.domain.entity.SysRole;
import com.yabushan.common.core.domain.entity.SysUser;
import com.yabushan.common.exception.user.CaptchaException;
import com.yabushan.common.exception.user.CaptchaExpireException;
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.form.mapper.SqlExecuteMapper;
import com.yabushan.system.domain.SyncUser;
import com.yabushan.system.enums.EnumsConfig;
import com.yabushan.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 基础能力
 */
@Service
public class BaseCapacityService {

    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private SqlExecuteMapper sqlExecuteMapper;
    
    //用户注册
    public int registerUser(SysUser user, String code, String uuid) {
        int count = 0;
        user.setDeptId(Long.valueOf("8421"));
        user.setUserType("11");
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        user.setSyncUserId(user.getUserName());
        user.setMaindepartment("true");
        count = userMapper.insertUser(user);
        sqlExecuteMapper.execute("");
        return count;
    }
}
