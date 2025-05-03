package com.yabushan.system.service.impl;

import com.yabushan.common.utils.DateUtils;
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.system.domain.SysNewsPushLog;
import com.yabushan.system.domain.vo.SysNewsPushLogVo;
import com.yabushan.system.mapper.SysNewsPushLogMapper;
import com.yabushan.system.service.ISysNewsPushLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 消息推送记录Service业务层处理
 *
 * @author 姜森焱
 * @date 2021-09-06
 */
@Service
public class SysNewsPushLogServiceImpl implements ISysNewsPushLogService
{
    private static final Logger log = LoggerFactory.getLogger(SysNewsPushLogServiceImpl.class);
    @Autowired
    private SysNewsPushLogMapper sysNewsPushLogMapper;
    //存放导入所有的成功失败数据
    private static List<SysNewsPushLog> sysNewsPushLogInfo = new ArrayList<>();

    /**
    * 清空返回导入数据的集合
    */
    public void clearsysNewsPushLogInfo() {
        if (StringUtils.isNotNull(sysNewsPushLogInfo) && sysNewsPushLogInfo.size() > 0) {
                sysNewsPushLogInfo.clear();
        }
    }

    /**
     * 查询消息推送记录
     *
     * @param id 消息推送记录ID
     * @return 消息推送记录
     */
    @Override
    public SysNewsPushLog selectSysNewsPushLogById(Long id)
    {
        return sysNewsPushLogMapper.selectSysNewsPushLogById(id);
    }

    @Override
    public List<SysNewsPushLogVo> selectSysNewsPushLogVoList(SysNewsPushLogVo sysNewsPushLogVo) {
        return sysNewsPushLogMapper.selectSysNewsPushLogVoList(sysNewsPushLogVo);
    }

    @Override
    public SysNewsPushLogVo selectReadCount(String sendOutBy) {
        return sysNewsPushLogMapper.selectReadCount(sendOutBy);
    }

    /**
     * 查询消息推送记录列表
     *
     * @param sysNewsPushLog 消息推送记录
     * @return 消息推送记录
     */
    @Override
    public List<SysNewsPushLog> selectSysNewsPushLogList(SysNewsPushLog sysNewsPushLog)
    {
        return sysNewsPushLogMapper.selectSysNewsPushLogList(sysNewsPushLog);
    }

    /**
     * 新增消息推送记录
     *
     * @param sysNewsPushLog 消息推送记录
     * @return 结果
     */
    @Override
    public int insertSysNewsPushLog(SysNewsPushLog sysNewsPushLog)
    {
        sysNewsPushLog.setCreateTime(DateUtils.getNowDate());
        return sysNewsPushLogMapper.insertSysNewsPushLog(sysNewsPushLog);
    }

    /**
     * 修改消息推送记录
     *
     * @param sysNewsPushLog 消息推送记录
     * @return 结果
     */
    @Override
    public int updateSysNewsPushLog(SysNewsPushLog sysNewsPushLog)
    {
        sysNewsPushLog.setUpdateTime(DateUtils.getNowDate());
        return sysNewsPushLogMapper.updateSysNewsPushLog(sysNewsPushLog);
    }

    /**
     * 批量修改消息状态接口
     * @param sysNewsPushLog
     * @return
     */
    @Override
    public int updateSysNewsPushLogBySendOutBy(SysNewsPushLog sysNewsPushLog) {
        sysNewsPushLog.setSendOutBy(SecurityUtils.getUsername());
        //设置为已读
        sysNewsPushLog.setReadType("2");
        sysNewsPushLog.setUpdateBy(SecurityUtils.getUsername());
        sysNewsPushLog.setUpdateTime(new Date());
        SysNewsPushLogVo sysNewsPushLogVo = sysNewsPushLogMapper.selectReadCount(SecurityUtils.getUsername());
        if(sysNewsPushLogVo!=null){
            sysNewsPushLogMapper.updateSysNewsPushLogBySendOutBy(sysNewsPushLog);
        }
        return 9;
    }

    /**
     * 批量删除消息推送记录
     *
     * @param ids 需要删除的消息推送记录ID
     * @return 结果
     */
    @Override
    public int deleteSysNewsPushLogByIds(Long[] ids)
    {
        return sysNewsPushLogMapper.deleteSysNewsPushLogByIds(ids);
    }

    /**
     * 删除消息推送记录信息
     *
     * @param id 消息推送记录ID
     * @return 结果
     */
    @Override
    public int deleteSysNewsPushLogById(Long id)
    {
        return sysNewsPushLogMapper.deleteSysNewsPushLogById(id);
    }

    /**
     * 导入消息推送记录Excel数据
     *
     * @param sysNewsPushLogList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public List<SysNewsPushLog>  importSysNewsPushLog(List<SysNewsPushLog> sysNewsPushLogList, Boolean isUpdateSupport, String operName)
    {
        for (int i = 0; i < sysNewsPushLogList.size(); i++) {
            SysNewsPushLog sysNewsPushLog=sysNewsPushLogList.get(i);
        try {
            // 验证是否存在这个用户
            SysNewsPushLog u = sysNewsPushLogMapper.selectSysNewsPushLogById(sysNewsPushLog.getId());
            if (StringUtils.isNull(u)) {
                    sysNewsPushLogMapper.insertSysNewsPushLog(sysNewsPushLog);
                    sysNewsPushLog.setImportStatus("导入成功");
                    sysNewsPushLogInfo.add(sysNewsPushLog);
            } else if (isUpdateSupport) {
                    sysNewsPushLogMapper.updateSysNewsPushLog(sysNewsPushLog);
                    sysNewsPushLog.setImportStatus("更新成功");
                    sysNewsPushLogInfo.add(sysNewsPushLog);
            } else {
                    sysNewsPushLog.setImportStatus("已存在当前数据");
                    sysNewsPushLogInfo.add(sysNewsPushLog);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            sysNewsPushLog.setImportStatus("数据格式有问题，请认证检查数据！");
            sysNewsPushLogInfo.add(sysNewsPushLog);
            //如果sql插入数据报错，或者更新报错，都会进行数据的回调，直到excel的数据读取完
            importSysNewsPushLog(sysNewsPushLogList.subList(i+1,sysNewsPushLogList.size()), isUpdateSupport, operName);
            return sysNewsPushLogInfo;
        }

    }
        return sysNewsPushLogInfo;
    }
}
