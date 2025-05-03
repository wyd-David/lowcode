package com.yabushan.quartz.task;

import com.yabushan.system.service.ITodoInfoPushService;
import com.yabushan.system.service.IWarnSmsLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component("sMSSendTask")
public class SMSSendTask {

    private static final Logger log = LoggerFactory.getLogger(ITodoInfoPushService.class);
    @Resource
    private IWarnSmsLogService ismsWebServiceSender;


    /**
     * 发送短信定时任务
     */
    public void smsSendJobHandler() throws Exception {
        log.info("发送短信定时任务");
        ismsWebServiceSender.send();
    }

    /**
     * 根据工单定时写入短信信息
     */
    public void insertSummaryToSMSLogJobHandler() throws Exception {
        log.info("进入定时写入短信信息定时任务");
        ismsWebServiceSender.insertSMSSendLog();
    }




}
