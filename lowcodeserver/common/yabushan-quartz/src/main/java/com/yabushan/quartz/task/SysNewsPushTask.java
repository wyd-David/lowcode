package com.yabushan.quartz.task;


import com.yabushan.common.newspush.NewsPushService;
import com.yabushan.system.domain.SysNewsPushLog;
import com.yabushan.system.service.ISysNewsPushLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by 姜森焱 on 2021/9/6 17:31
 * 定时推送消息
 */
@Component("sysNewsPushTask")
public class SysNewsPushTask {

    @Autowired
    private ISysNewsPushLogService iSysNewsPushLogService;
    @Autowired
    private NewsPushService newsPushService;

    public void sysNewspushInfo() {
        //获取未推送消息列表
        SysNewsPushLog sysNewsPushLog = new SysNewsPushLog();
        sysNewsPushLog.setSendOutState("1");
        List<SysNewsPushLog> sysNewsPushLogs = iSysNewsPushLogService.selectSysNewsPushLogList(sysNewsPushLog);
        for (int i = 0; i < sysNewsPushLogs.size(); i++) {
            if (newsPushService.sendMessage(sysNewsPushLogs.get(i).getSendOutBy(), sysNewsPushLogs.get(i).getSendOutText())) {
                SysNewsPushLog newsPushLog = sysNewsPushLogs.get(i);
                newsPushLog.setSendOutState("2");
                newsPushLog.setSendOutTime(new Date());
                iSysNewsPushLogService.updateSysNewsPushLog(newsPushLog);
            }
        }
    }
}
