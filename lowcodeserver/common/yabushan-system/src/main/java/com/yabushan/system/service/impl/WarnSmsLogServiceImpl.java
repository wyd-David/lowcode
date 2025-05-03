package com.yabushan.system.service.impl;

import com.yabushan.common.utils.DateUtils;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.system.domain.WarnSmsLog;
import com.yabushan.system.mapper.WarnSmsLogMapper;
import com.yabushan.system.service.IWarnSmsLogService;
import com.yabushan.system.utils.SMSSend2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 短信日志记录Service业务层处理
 *
 * @author yabushan
 * @date 2022-05-10
 */
@Service
public class WarnSmsLogServiceImpl implements IWarnSmsLogService {
    private static final Logger log = LoggerFactory.getLogger(WarnSmsLogServiceImpl.class);
    @Autowired
    private WarnSmsLogMapper warnSmsLogMapper;
    @Value("${sms.uid}")
    private String uid;
    @Value("${sms.password}")
    private String password;

    private String sessionId = null;

    //存放导入所有的成功失败数据
    private static List<WarnSmsLog> warnSmsLogInfo = new ArrayList<>();

    /**
     * 清空返回导入数据的集合
     */
    public void clearwarnSmsLogInfo() {
        if (StringUtils.isNotNull(warnSmsLogInfo) && warnSmsLogInfo.size() > 0) {
            warnSmsLogInfo.clear();
        }
    }

    /**
     * 查询短信日志记录
     *
     * @param id 短信日志记录ID
     * @return 短信日志记录
     */
    @Override
    public WarnSmsLog selectWarnSmsLogById(String id) {
        return warnSmsLogMapper.selectWarnSmsLogById(id);
    }

    /**
     * 查询短信日志记录列表
     *
     * @param warnSmsLog 短信日志记录
     * @return 短信日志记录
     */
    @Override
    public List<WarnSmsLog> selectWarnSmsLogList(WarnSmsLog warnSmsLog) {
        return warnSmsLogMapper.selectWarnSmsLogList(warnSmsLog);
    }

    /**
     * 新增短信日志记录
     *
     * @param warnSmsLog 短信日志记录
     * @return 结果
     */
    @Override
    public int insertWarnSmsLog(WarnSmsLog warnSmsLog) {
        warnSmsLog.setCreateTime(DateUtils.getNowDate());
        warnSmsLog.setId(StringUtils.getUUID());
        return warnSmsLogMapper.insertWarnSmsLog(warnSmsLog);
    }

    /**
     * 批量新增短信日志记录
     *
     * @param warnSmsLog 短信日志记录
     * @return 结果
     */
    @Override
    public int bathInsertWarnSmsLog(List<WarnSmsLog> warnSmsLog) {
        return warnSmsLogMapper.bathInsertWarnSmsLog(warnSmsLog);
    }


    /**
     * 修改短信日志记录
     *
     * @param warnSmsLog 短信日志记录
     * @return 结果
     */
    @Override
    public int updateWarnSmsLog(WarnSmsLog warnSmsLog) {
        warnSmsLog.setUpdateTime(DateUtils.getNowDate());
        return warnSmsLogMapper.updateWarnSmsLog(warnSmsLog);
    }

    /**
     * 批量删除短信日志记录
     *
     * @param ids 需要删除的短信日志记录ID
     * @return 结果
     */
    @Override
    public int deleteWarnSmsLogByIds(String[] ids) {
        return warnSmsLogMapper.deleteWarnSmsLogByIds(ids);
    }

    /**
     * 删除短信日志记录信息
     *
     * @param id 短信日志记录ID
     * @return 结果
     */
    @Override
    public int deleteWarnSmsLogById(String id) {
        return warnSmsLogMapper.deleteWarnSmsLogById(id);
    }

    /**
     * 根据传来的id集合修改禁用启用状态
     *
     * @param ids
     * @param disableEnableState
     * @return
     */
    @Transactional
    @Override
    public Boolean updateByIds(String ids, String disableEnableState) {
        int count = 0;
        String idslist[] = ids.split(",", -1);
        if (StringUtils.isNotNull(idslist) && idslist.length >= 1) {
            for (int i = 0; i < idslist.length; i++) {
                WarnSmsLog warnSmsLog = warnSmsLogMapper.selectWarnSmsLogById(idslist[i]);
                warnSmsLog.setDisableEnableState(disableEnableState);
                int isappinfo = warnSmsLogMapper.updateWarnSmsLog(warnSmsLog);
                if (isappinfo == 1) {
                    count++;
                }
            }
            if (count == idslist.length) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * 导入短信日志记录Excel数据
     *
     * @param warnSmsLogList  数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public List<WarnSmsLog> importWarnSmsLog(List<WarnSmsLog> warnSmsLogList, Boolean isUpdateSupport, String operName) {
        for (int i = 0; i < warnSmsLogList.size(); i++) {
            WarnSmsLog warnSmsLog = warnSmsLogList.get(i);
            try {
                // 验证是否存在这个用户
                WarnSmsLog u = warnSmsLogMapper.selectWarnSmsLogById(warnSmsLog.getId());
                if (StringUtils.isNull(u)) {
                    warnSmsLogMapper.insertWarnSmsLog(warnSmsLog);
                    warnSmsLog.setImportStatus("导入成功");
                    warnSmsLogInfo.add(warnSmsLog);
                } else if (isUpdateSupport) {
                    warnSmsLogMapper.updateWarnSmsLog(warnSmsLog);
                    warnSmsLog.setImportStatus("更新成功");
                    warnSmsLogInfo.add(warnSmsLog);
                } else {
                    warnSmsLog.setImportStatus("已存在当前数据");
                    warnSmsLogInfo.add(warnSmsLog);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                warnSmsLog.setImportStatus("数据格式有问题，请认证检查数据！");
                warnSmsLogInfo.add(warnSmsLog);
                //如果sql插入数据报错，或者更新报错，都会进行数据的回调，直到excel的数据读取完
                importWarnSmsLog(warnSmsLogList.subList(i + 1, warnSmsLogList.size()), isUpdateSupport, operName);
                return warnSmsLogInfo;
            }

        }
        return warnSmsLogInfo;
    }


    public void send()  {

        log.info("--------调用webservice接口begin-------");
        try {
            List<WarnSmsLog> sendLogList = warnSmsLogMapper.getSMSSendLogByStatus("0");
            SMSSend2 smsSend = new SMSSend2(uid,password);
            for (WarnSmsLog sendLog : sendLogList){
                try {
                    log.info("名称:"+sendLog.getName()+" ，电话："+sendLog.getPhone() +",内容:"+sendLog.getContent());
                    smsSend.send(sendLog.getContent(), sendLog.getPhone(), "8929");
                    WarnSmsLog entity = new WarnSmsLog();
                    entity.setId(sendLog.getId());
                    entity.setStatus("1");
                    entity.setUpdateTime(DateUtils.getNowDate());
                    if(entity.getNum() != null){
                        long num = entity.getNum() +1;
                        entity.setNum(num);
                    }else {
                        entity.setNum(1l);
                    }
                    warnSmsLogMapper.updateWarnSmsLog(entity);
                }catch (Exception e){
                    log.error(e.getMessage());
                }

            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        log.info("--------调用webservice接口end-------");
    }

    @Override
    public void sendTest() {
        System.out.println("进入sendTest");
        // 创建动态客户端
        try {
            // 对应的targetNamespace
            SMSSend2 smsSend = new SMSSend2("sgsqryjfkpt","qryjfkptgmcc");
            //13688888999#13566666666#13988880000#13631233154#13823078875#13928088873#13877777777#13822222222
            smsSend.send("哟西你的哟", "13760869551", "8929");
            System.out.println("发送成功");
        } catch (java.lang.Exception e) {
            System.out.println("错误信息："+e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public void insertSMSSendLog() {
        String days = DateUtils.getWeekOfDate(new Date());
        if(!"6".equals(days) && !"7".equals(days)){
            Calendar calendar = Calendar.getInstance();
            Integer hour = calendar.get(Calendar.HOUR_OF_DAY);
            log.info("现在是:"+hour+"点");
            if(hour == 9){
                insert("0","是");
            }else if(hour == 10){
                insert("3","否");
            }else if(hour == 13){
                insert("1","是");
            }else if(hour == 15){
                insert("3","是");
            }
            log.info("查询完成");
        }
    }

    public void insert(String days,String isExpired){
        try {

        }catch (Exception e){

            log.info(e.getMessage());
        }

    }



    /*
     * @see java.lang.Object#finalize()
     */
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        // 强制退出session
        /*SMSServerStub.forceLogout(uid, password);*/
    }

    /**
     * @param uid
     *            the uid to set
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
