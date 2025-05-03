package com.yabushan.quartz.task;
import com.yabushan.common.core.domain.entity.SysDictData;
import com.yabushan.common.core.redis.RedisCache;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.common.utils.http.HttpClientUtil;
import com.yabushan.system.domain.SysNewsPushLog;
import com.yabushan.system.service.ISysDictDataService;
import com.yabushan.system.service.ISysNewsPushLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 监控外部接口是否可用
 */
@Component("OutInterfaceTask")
public class OutInterfaceTask {
    @Autowired
    private ISysDictDataService dictDataService;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ISysNewsPushLogService sysNewsPushLogService;

    public void checkInterface(){
        //获取监控外部接口
        SysDictData sysDictData=new SysDictData();
        sysDictData.setDictType("JK_OUT_INTERFACE");
        List<SysDictData> list = dictDataService.selectDictDataList(sysDictData);
        sysDictData.setDictType("JK_YJ_USER");
        List<SysDictData> userList = dictDataService.selectDictDataList(sysDictData);
        if(null!=list && list.size()>0 && null!=userList && userList.size()>0){
            StringBuilder message=new StringBuilder();
            for(SysDictData dictData:list){
                String url=dictData.getDictValue();
                String method=dictData.getRemark();
                String result="";
                if(StringUtils.isNotEmpty(method) && "get".equals(method)){
                    result = HttpClientUtil.doGet(url);

                }
                else {
                    result = HttpClientUtil.doPost(url, null);
                }
                if(StringUtils.isEmpty(result)){
                    message.append(dictData.getDictLabel()+"连接不可用,");
                }
            }
            if(StringUtils.isNotEmpty(message)){
              String  sendMessage=message.substring(0,message.length()-1);
              for(SysDictData data:userList){
                  SysNewsPushLog pushLog = new SysNewsPushLog();
                  pushLog.setSendOutText(sendMessage);
                  pushLog.setSendOutBy(data.getDictValue());
                  pushLog.setSendOutNumber(redisCache.getOrderNumber("XTXX",4));
                  pushLog.setCreateBy("admin");
                  pushLog.setCreateTime(new Date());
                  sysNewsPushLogService.insertSysNewsPushLog(pushLog);
              }
            }
        }
    }

 
}
