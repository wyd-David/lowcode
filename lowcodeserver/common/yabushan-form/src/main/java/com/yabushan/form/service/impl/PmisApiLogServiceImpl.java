package com.yabushan.form.service.impl;

import com.yabushan.common.utils.DateUtils;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.form.domain.PmisApiLog;
import com.yabushan.form.mapper.PmisApiLogMapper;
import com.yabushan.form.service.IPmisApiLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * API日志Service业务层处理
 *
 * @author yabushan
 * @date 2023-07-29
 */
@Service
public class PmisApiLogServiceImpl implements IPmisApiLogService {
    private static final Logger log = LoggerFactory.getLogger(PmisApiLogServiceImpl.class);
    @Autowired
    private PmisApiLogMapper pmisApiLogMapper;

    //存放导入所有的成功失败数据
    private static List<PmisApiLog> pmisApiLogInfo = new ArrayList<>();

    /**
    * 清空返回导入数据的集合
    */
    @Override
    public void clearpmisApiLogInfo() {
        if (StringUtils.isNotNull(pmisApiLogInfo) && pmisApiLogInfo.size() > 0) {
                pmisApiLogInfo.clear();
        }
    }

    /**
     * 查询API日志
     *
     * @param logId API日志ID
     * @return API日志
     */
    @Override
    public PmisApiLog selectPmisApiLogById(String logId) {
        return pmisApiLogMapper.selectPmisApiLogById(logId);
    }

    /**
     * 查询API日志列表
     *
     * @param pmisApiLog API日志
     * @return API日志
     */
    @Override
    public List<PmisApiLog> selectPmisApiLogList(PmisApiLog pmisApiLog) {
        return pmisApiLogMapper.selectPmisApiLogList(pmisApiLog);
    }

    /**
     * 新增API日志
     *
     * @param pmisApiLog API日志
     * @return 结果
     */
    @Override
    public int insertPmisApiLog(PmisApiLog pmisApiLog) {
                                                                            pmisApiLog.setCreateTime(DateUtils.getNowDate());
                                                                                        return pmisApiLogMapper.insertPmisApiLog(pmisApiLog);
    }

    /**
     * 批量新增API日志
     *
     * @param pmisApiLog API日志
     * @return 结果
     */
    @Override
    public int bathInsertPmisApiLog(List<PmisApiLog> pmisApiLog) {
        return pmisApiLogMapper.bathInsertPmisApiLog(pmisApiLog);
    }


    /**
     * 修改API日志
     *
     * @param pmisApiLog API日志
     * @return 结果
     */
    @Override
    public int updatePmisApiLog(PmisApiLog pmisApiLog) {
                                                                                                                                        return pmisApiLogMapper.updatePmisApiLog(pmisApiLog);
    }

    /**
     * 批量删除API日志
     *
     * @param logIds 需要删除的API日志ID
     * @return 结果
     */
    @Override
    public int deletePmisApiLogByIds(String[] logIds) {
        return pmisApiLogMapper.deletePmisApiLogByIds(logIds);
    }

    /**
     * 删除API日志信息
     *
     * @param logId API日志ID
     * @return 结果
     */
    @Override
    public int deletePmisApiLogById(String logId) {
        return pmisApiLogMapper.deletePmisApiLogById(logId);
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
                // todo idslist[i] 返回的是一个字符串，如果实体id是其他类型，需要转换，如是int类型就修改成Integer.parseInt(idslist[i])
                PmisApiLog pmisApiLog=pmisApiLogMapper.selectPmisApiLogById(idslist[i]);
                pmisApiLog.setDisableEnableState(disableEnableState);
                int isappinfo = pmisApiLogMapper.updatePmisApiLog(pmisApiLog);
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
     * 导入API日志Excel数据
     *
     * @param pmisApiLogList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public List<PmisApiLog>  importPmisApiLog(
    List<PmisApiLog> pmisApiLogList, Boolean
    isUpdateSupport,
    String operName)

    {
        for (int i = 0; i < pmisApiLogList.size(); i++) {
            PmisApiLog pmisApiLog=pmisApiLogList.get(i);
            try {
                // 验证是否存在这个用户
                // todo getLogId() 换成获取实体 id 的方法
                PmisApiLog u = pmisApiLogMapper.selectPmisApiLogById(pmisApiLog.getLogId());
                if (StringUtils.isNull(u)) {
                        pmisApiLogMapper.insertPmisApiLog(pmisApiLog);
                    pmisApiLog.setImportStatus("导入成功");
                        pmisApiLogInfo.add(pmisApiLog);
                } else if (isUpdateSupport) {
                        pmisApiLogMapper.updatePmisApiLog(pmisApiLog);
                    pmisApiLog.setImportStatus("更新成功");
                        pmisApiLogInfo.add(pmisApiLog);
                } else {
                    pmisApiLog.setImportStatus("已存在当前数据");
                        pmisApiLogInfo.add(pmisApiLog);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                pmisApiLog.setImportStatus("数据格式有问题，请认证检查数据！");
                    pmisApiLogInfo.add(pmisApiLog);
                //如果sql插入数据报错，或者更新报错，都会进行数据的回调，直到excel的数据读取完
            importPmisApiLog(pmisApiLogList.subList(i + 1, pmisApiLogList.size()), isUpdateSupport, operName);
                return pmisApiLogInfo;
            }

        }
        return pmisApiLogInfo;
    }
}
