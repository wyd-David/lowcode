package com.yabushan.system.service.impl;

import com.yabushan.common.utils.StringUtils;
import com.yabushan.system.domain.WarnProcessPushLog;
import com.yabushan.system.mapper.WarnProcessPushLogMapper;
import com.yabushan.system.service.IWarnProcessPushLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 待办推送记录Service业务层处理
 *
 * @author yabushan
 * @date 2022-04-28
 */
@Service
public class WarnProcessPushLogServiceImpl implements IWarnProcessPushLogService {
    private static final Logger log = LoggerFactory.getLogger(WarnProcessPushLogServiceImpl.class);
    @Autowired
    private WarnProcessPushLogMapper warnProcessPushLogMapper;

    //存放导入所有的成功失败数据
    private static List<WarnProcessPushLog> warnProcessPushLogInfo = new ArrayList<>();

    /**
    * 清空返回导入数据的集合
    */
    public void clearwarnProcessPushLogInfo() {
        if (StringUtils.isNotNull(warnProcessPushLogInfo) && warnProcessPushLogInfo.size() > 0) {
                warnProcessPushLogInfo.clear();
        }
    }

    /**
     * 查询待办推送记录
     *
     * @param id 待办推送记录ID
     * @return 待办推送记录
     */
    @Override
    public WarnProcessPushLog selectWarnProcessPushLogById(Long id) {
        return warnProcessPushLogMapper.selectWarnProcessPushLogById(id);
    }

    /**
     * 查询待办推送记录列表
     *
     * @param warnProcessPushLog 待办推送记录
     * @return 待办推送记录
     */
    @Override
    public List<WarnProcessPushLog> selectWarnProcessPushLogList(WarnProcessPushLog warnProcessPushLog) {
        return warnProcessPushLogMapper.selectWarnProcessPushLogList(warnProcessPushLog);
    }

    /**
     * 新增待办推送记录
     *
     * @param warnProcessPushLog 待办推送记录
     * @return 结果
     */
    @Override
    public int insertWarnProcessPushLog(WarnProcessPushLog warnProcessPushLog) {
                                                                                                                                                                                                                                                                                    return warnProcessPushLogMapper.insertWarnProcessPushLog(warnProcessPushLog);
    }

    /**
     * 批量新增待办推送记录
     *
     * @param warnProcessPushLog 待办推送记录
     * @return 结果
     */
    @Override
    public int bathInsertWarnProcessPushLog(List<WarnProcessPushLog> warnProcessPushLog) {
        return warnProcessPushLogMapper.bathInsertWarnProcessPushLog(warnProcessPushLog);
    }


    /**
     * 修改待办推送记录
     *
     * @param warnProcessPushLog 待办推送记录
     * @return 结果
     */
    @Override
    public int updateWarnProcessPushLog(WarnProcessPushLog warnProcessPushLog) {
                                                                                                                                                                                                                                                                                    return warnProcessPushLogMapper.updateWarnProcessPushLog(warnProcessPushLog);
    }

    /**
     * 批量删除待办推送记录
     *
     * @param ids 需要删除的待办推送记录ID
     * @return 结果
     */
    @Override
    public int deleteWarnProcessPushLogByIds(Long[] ids) {
        return warnProcessPushLogMapper.deleteWarnProcessPushLogByIds(ids);
    }

    /**
     * 删除待办推送记录信息
     *
     * @param id 待办推送记录ID
     * @return 结果
     */
    @Override
    public int deleteWarnProcessPushLogById(Long id) {
        return warnProcessPushLogMapper.deleteWarnProcessPushLogById(id);
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
        String idslist[] = ids.split("," , -1);
        if (StringUtils.isNotNull(idslist) && idslist.length >= 1) {
            for (int i = 0; i < idslist.length; i++) {
                WarnProcessPushLog warnProcessPushLog=warnProcessPushLogMapper.selectWarnProcessPushLogById(0l);
                warnProcessPushLog.setDisableEnableState(disableEnableState);
                int isappinfo = warnProcessPushLogMapper.updateWarnProcessPushLog(warnProcessPushLog);
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
     * 导入待办推送记录Excel数据
     *
     * @param warnProcessPushLogList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public List<WarnProcessPushLog>  importWarnProcessPushLog(
    List<WarnProcessPushLog> warnProcessPushLogList, Boolean
    isUpdateSupport,
    String operName)

    {
        for (int i = 0; i < warnProcessPushLogList.size(); i++) {
            WarnProcessPushLog warnProcessPushLog=warnProcessPushLogList.get(i);
            try {
                // 验证是否存在这个用户
                WarnProcessPushLog u = warnProcessPushLogMapper.selectWarnProcessPushLogById(warnProcessPushLog.getId());
                if (StringUtils.isNull(u)) {
                        warnProcessPushLogMapper.insertWarnProcessPushLog(warnProcessPushLog);
                    warnProcessPushLog.setImportStatus("导入成功");
                        warnProcessPushLogInfo.add(warnProcessPushLog);
                } else if (isUpdateSupport) {
                        warnProcessPushLogMapper.updateWarnProcessPushLog(warnProcessPushLog);
                    warnProcessPushLog.setImportStatus("更新成功");
                        warnProcessPushLogInfo.add(warnProcessPushLog);
                } else {
                    warnProcessPushLog.setImportStatus("已存在当前数据");
                        warnProcessPushLogInfo.add(warnProcessPushLog);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                warnProcessPushLog.setImportStatus("数据格式有问题，请认证检查数据！");
                    warnProcessPushLogInfo.add(warnProcessPushLog);
                //如果sql插入数据报错，或者更新报错，都会进行数据的回调，直到excel的数据读取完
            importWarnProcessPushLog(warnProcessPushLogList.subList(i + 1, warnProcessPushLogList.size()), isUpdateSupport, operName);
                return warnProcessPushLogInfo;
            }

        }
        return warnProcessPushLogInfo;
    }
}
