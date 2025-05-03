package com.yabushan.quartz.task;

import com.yabushan.system.enums.BusinessType;
import com.yabushan.system.service.IPushProTodoDoneService;
import com.yabushan.system.service.IPushProToreadReadService;
import com.yabushan.system.service.ITodoInfoPushService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PushProDataTask {

    private static final Logger log = LoggerFactory.getLogger(ITodoInfoPushService.class);

    @Autowired
    private IPushProTodoDoneService pushProTodoDoneService;
    @Autowired
    private IPushProToreadReadService pushProToreadReadService;




    public void pushProData(){
        log.info("开始重试推送待办、已办、待阅、已阅数据接口定时任务");
        try {
            log.info("***开始重试推送待办数据***");
            boolean todoFlag = pushProTodoDoneService.pushDataToPortal(BusinessType.TODO);
            if(!todoFlag) {
                log.info("***重试推送待办数据失败***");
            }else {
                log.info("***成功重试推送待办数据***");
            }

        }catch (Exception e){
            log.info("重试推送待办数据失败：{}",e.getMessage());
        }
        try {

            log.info("***开始重试推送已办数据***");
            boolean doneFlag = pushProTodoDoneService.pushDataToPortal(BusinessType.DONE);
            if(!doneFlag) {
                log.info("***重试推送已办数据失败***");
            }else {
                log.info("***成功重试推送已办数据***");
            }

        }catch (Exception e){
            log.info("重试推送已办数据失败：{}",e.getMessage());
        }
        try {

            log.info("***开始重试推送待阅数据***");
            boolean toreadFlag = pushProToreadReadService.pushDataToPortal(BusinessType.TOREAD);
            if(!toreadFlag) {
                log.info("***重试推送待阅数据失败***");
            }else{
                log.info("***成功重试推送待阅数据***");
            }

        }catch (Exception e){
            log.info("重试推送待阅数据失败：{}",e.getMessage());
        }
        try {

            log.info("***开始重试推送已阅数据***");
            boolean readFlag = pushProToreadReadService.pushDataToPortal(BusinessType.READ);
            if(!readFlag) {
                log.info("***重试推送已阅数据失败***");
            }else{
                log.info("***成功重试推送已阅数据***");
            }
        }catch (Exception e){
            log.info("重试推送已阅数据失败：{}",e.getMessage());
        }


    }


}
