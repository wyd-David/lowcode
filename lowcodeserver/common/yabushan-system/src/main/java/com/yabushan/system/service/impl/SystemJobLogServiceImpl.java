package com.yabushan.system.service.impl;

import com.yabushan.common.utils.StringUtils;
import com.yabushan.system.domain.SystemJobLog;
import com.yabushan.system.mapper.SystemJobLogMapper;
import com.yabushan.system.service.ISystemJobLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * 定时任务日志 Service业务层处理
 *
 * @author yabushan
 * @date 2021-11-16
 */
@Service
public class SystemJobLogServiceImpl implements ISystemJobLogService
{
    private static final Logger log = LoggerFactory.getLogger(SystemJobLogServiceImpl.class);
    @Autowired
    private SystemJobLogMapper systemJobLogMapper;

    //存放导入所有的成功失败数据
    private static List<SystemJobLog> systemJobLogInfo = new ArrayList<>();

    /**
    * 清空返回导入数据的集合
    */
    public void clearsystemJobLogInfo() {
        if (StringUtils.isNotNull(systemJobLogInfo) && systemJobLogInfo.size() > 0) {
                systemJobLogInfo.clear();
        }
    }

    /**
     * 查询定时任务日志 
     *
     * @param id 定时任务日志 ID
     * @return 定时任务日志 
     */
    @Override
    public SystemJobLog selectSystemJobLogById(String id)
    {
        return systemJobLogMapper.selectSystemJobLogById(id);
    }

    /**
     * 查询定时任务日志 列表
     *
     * @param systemJobLog 定时任务日志 
     * @return 定时任务日志 
     */
    @Override
    public List<SystemJobLog> selectSystemJobLogList(SystemJobLog systemJobLog)
    {
        return systemJobLogMapper.selectSystemJobLogList(systemJobLog);
    }

    /**
     * 新增定时任务日志 
     *
     * @param systemJobLog 定时任务日志 
     * @return 结果
     */
    @Override
    public int insertSystemJobLog(SystemJobLog systemJobLog)
    {
        systemJobLog.setId(StringUtils.getUUID());
        systemJobLog.setJobIpAddr(getIpAddr());
        return systemJobLogMapper.insertSystemJobLog(systemJobLog);
    }

    /**
     * 批量新增定时任务日志 
     *
     * @param systemJobLog 定时任务日志 
     * @return 结果
     */
    @Override
    public int bathInsertSystemJobLog(List<SystemJobLog> systemJobLog)
    {
        return systemJobLogMapper.bathInsertSystemJobLog(systemJobLog);
    }


    /**
     * 修改定时任务日志 
     *
     * @param systemJobLog 定时任务日志 
     * @return 结果
     */
    @Override
    public int updateSystemJobLog(SystemJobLog systemJobLog)
    {
        return systemJobLogMapper.updateSystemJobLog(systemJobLog);
    }

    /**
     * 批量删除定时任务日志 
     *
     * @param ids 需要删除的定时任务日志 ID
     * @return 结果
     */
    @Override
    public int deleteSystemJobLogByIds(Long[] ids)
    {
        return systemJobLogMapper.deleteSystemJobLogByIds(ids);
    }

    /**
     * 删除定时任务日志 信息
     *
     * @param id 定时任务日志 ID
     * @return 结果
     */
    @Override
    public int deleteSystemJobLogById(Long id)
    {
        return systemJobLogMapper.deleteSystemJobLogById(id);
    }

    /**
    * 根据传来的id集合修改禁用启用状态
    * @param ids
    * @param disableEnableState
    * @return
    */
    @Transactional
    @Override
    public Boolean updateByIds(String ids, String disableEnableState) {
        int count = 0;
        String idslist[]=ids.split(",",-1);
        if(StringUtils.isNotNull(idslist)&&idslist.length>=1){
            for (int i = 0; i < idslist.length; i++) {
                SystemJobLog systemJobLog=systemJobLogMapper.selectSystemJobLogById(idslist[i]);
                systemJobLog.setDisableEnableState(disableEnableState);
                int isappinfo = systemJobLogMapper.updateSystemJobLog(systemJobLog);
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
     * 导入定时任务日志 Excel数据
     *
     * @param systemJobLogList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public List<SystemJobLog>  importSystemJobLog(List<SystemJobLog> systemJobLogList, Boolean isUpdateSupport, String operName)
    {
        for (int i = 0; i < systemJobLogList.size(); i++) {
            SystemJobLog systemJobLog=systemJobLogList.get(i);
        try {
            // 验证是否存在这个用户
            SystemJobLog u = systemJobLogMapper.selectSystemJobLogById(systemJobLog.getId());
            if (StringUtils.isNull(u)) {
                    systemJobLogMapper.insertSystemJobLog(systemJobLog);
                    systemJobLog.setImportStatus("导入成功");
                    systemJobLogInfo.add(systemJobLog);
            } else if (isUpdateSupport) {
                    systemJobLogMapper.updateSystemJobLog(systemJobLog);
                    systemJobLog.setImportStatus("更新成功");
                    systemJobLogInfo.add(systemJobLog);
            } else {
                    systemJobLog.setImportStatus("已存在当前数据");
                    systemJobLogInfo.add(systemJobLog);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            systemJobLog.setImportStatus("数据格式有问题，请认证检查数据！");
            systemJobLogInfo.add(systemJobLog);
            //如果sql插入数据报错，或者更新报错，都会进行数据的回调，直到excel的数据读取完
            importSystemJobLog(systemJobLogList.subList(i+1,systemJobLogList.size()), isUpdateSupport, operName);
            return systemJobLogInfo;
        }

    }
        return systemJobLogInfo;
    }

    private String getIpAddr(){
        InetAddress ip4 = null;
        try {
            ip4 = Inet4Address.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        if(ip4!=null){
            //System.out.println(ip4.getHostAddress());
            return ip4.getHostAddress();
        }else {
            return "";
        }

    }
}
