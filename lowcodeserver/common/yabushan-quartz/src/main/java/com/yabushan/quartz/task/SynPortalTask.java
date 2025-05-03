package com.yabushan.quartz.task;

import com.yabushan.system.service.IHtGfRolePersonService;
import com.yabushan.system.service.ISyncOrgService;
import com.yabushan.system.service.ISyncPortalRoleService;
import com.yabushan.system.service.ISyncUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 同步用户/组织信息定时任务
 *
 * @author ruoyi
 */
@Component("synPortalTask")
public class SynPortalTask
{

    @Autowired
    private ISyncOrgService syncOrgService;
    @Autowired
    private ISyncUserService syncUserService;


    public void synOrg()
    {
        syncOrgService.saveOrUpdateSynOrg();
    }

    public void synUser()
    {
        syncUserService.insertOrUpdateSynUser();
    }

}
