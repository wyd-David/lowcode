package com.yabushan.quartz.task;

import com.yabushan.system.service.ISysConfigService;
import com.yabushan.system.service.ISysOperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 同步用户/组织信息定时任务
 *
 * @author ruoyi
 */
@Component("sysOpenLogTask")
public class SysOpenLogTask
{


    @Autowired
    private ISysOperLogService operLogService;
    @Autowired
    private ISysConfigService iSysConfigService;


    public void clearOpenLog()
    {
        String openLogClearDay = iSysConfigService.selectConfigByKey("openLogClearDay");
        operLogService.deleteOperLogByTask(Integer.valueOf(openLogClearDay));
    }


}
