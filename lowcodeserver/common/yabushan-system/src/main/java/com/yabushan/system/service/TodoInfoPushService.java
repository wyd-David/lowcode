package com.yabushan.system.service;

import com.yabushan.common.utils.StringUtils;
import com.yabushan.system.domain.SystemJobLog;
import com.yabushan.system.domain.SystemJobLogBatch;
import com.yabushan.system.domain.TodoInfoPush;
import com.yabushan.system.enums.EnumsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 姜森焱 on 2021/10/21 19:46
 */
@Service
public class TodoInfoPushService {
    @Autowired
    private ITodoInfoPushService iTodoInfoPushService;

    @Autowired
    private ISysConfigService iSysConfigService;
    @Autowired
    private ISystemJobLogService systemJobLogService;
    @Autowired
    private ISystemJobLogBatchService systemJobLogBatchService;
    /**
     * 待办推送记录推送
     */
    public void pushNewLogInfo() {

    }

    /**
     * 待办推送记录推送
     */
    public void newPushNewLogInfo() {

    }
}
