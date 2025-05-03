package com.yabushan.system.service.impl;

import com.yabushan.common.utils.DateUtils;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.system.domain.UumSyncLog;
import com.yabushan.system.mapper.UumSyncLogMapper;
import com.yabushan.system.service.IUumSyncLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 同步时间记录表Service业务层处理
 *
 * @author yabushan
 * @date 2022-04-27
 */
@Service
public class UumSyncLogServiceImpl implements IUumSyncLogService
{
    private static final Logger log = LoggerFactory.getLogger(UumSyncLogServiceImpl.class);
    @Autowired
    private UumSyncLogMapper uumSyncLogMapper;

    //存放导入所有的成功失败数据
    private static List<UumSyncLog> uumSyncLogInfo = new ArrayList<>();

    /**
    * 清空返回导入数据的集合
    */
    public void clearuumSyncLogInfo() {
        if (StringUtils.isNotNull(uumSyncLogInfo) && uumSyncLogInfo.size() > 0) {
                uumSyncLogInfo.clear();
        }
    }

    /**
     * 查询同步时间记录表
     *
     * @param id 同步时间记录表ID
     * @return 同步时间记录表
     */
    @Override
    public UumSyncLog selectUumSyncLogById(String id)
    {
        return uumSyncLogMapper.selectUumSyncLogById(id);
    }

    /**
     * 查询同步时间记录表列表
     *
     * @param uumSyncLog 同步时间记录表
     * @return 同步时间记录表
     */
    @Override
    public List<UumSyncLog> selectUumSyncLogList(UumSyncLog uumSyncLog)
    {
        return uumSyncLogMapper.selectUumSyncLogList(uumSyncLog);
    }

    /**
     * 新增同步时间记录表
     *
     * @param uumSyncLog 同步时间记录表
     * @return 结果
     */
    @Override
    public int insertUumSyncLog(UumSyncLog uumSyncLog)
    {
        uumSyncLog.setCreateTime(DateUtils.getNowDate());
        return uumSyncLogMapper.insertUumSyncLog(uumSyncLog);
    }

    /**
     * 批量新增同步时间记录表
     *
     * @param uumSyncLog 同步时间记录表
     * @return 结果
     */
    @Override
    public int bathInsertUumSyncLog(List<UumSyncLog> uumSyncLog)
    {
        return uumSyncLogMapper.bathInsertUumSyncLog(uumSyncLog);
    }


    /**
     * 修改同步时间记录表
     *
     * @param uumSyncLog 同步时间记录表
     * @return 结果
     */
    @Override
    public int updateUumSyncLog(UumSyncLog uumSyncLog)
    {
        return uumSyncLogMapper.updateUumSyncLog(uumSyncLog);
    }

    /**
     * 批量删除同步时间记录表
     *
     * @param ids 需要删除的同步时间记录表ID
     * @return 结果
     */
    @Override
    public int deleteUumSyncLogByIds(String[] ids)
    {
        return uumSyncLogMapper.deleteUumSyncLogByIds(ids);
    }

    /**
     * 删除同步时间记录表信息
     *
     * @param id 同步时间记录表ID
     * @return 结果
     */
    @Override
    public int deleteUumSyncLogById(String id)
    {
        return uumSyncLogMapper.deleteUumSyncLogById(id);
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
                UumSyncLog uumSyncLog=uumSyncLogMapper.selectUumSyncLogById(idslist[i]);
                uumSyncLog.setDisableEnableState(disableEnableState);
                int isappinfo = uumSyncLogMapper.updateUumSyncLog(uumSyncLog);
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
     * 导入同步时间记录表Excel数据
     *
     * @param uumSyncLogList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public List<UumSyncLog>  importUumSyncLog(List<UumSyncLog> uumSyncLogList, Boolean isUpdateSupport, String operName)
    {
        for (int i = 0; i < uumSyncLogList.size(); i++) {
            UumSyncLog uumSyncLog=uumSyncLogList.get(i);
        try {
            // 验证是否存在这个用户
            UumSyncLog u = uumSyncLogMapper.selectUumSyncLogById(uumSyncLog.getId());
            if (StringUtils.isNull(u)) {
                    uumSyncLogMapper.insertUumSyncLog(uumSyncLog);
                    uumSyncLog.setImportStatus("导入成功");
                    uumSyncLogInfo.add(uumSyncLog);
            } else if (isUpdateSupport) {
                    uumSyncLogMapper.updateUumSyncLog(uumSyncLog);
                    uumSyncLog.setImportStatus("更新成功");
                    uumSyncLogInfo.add(uumSyncLog);
            } else {
                    uumSyncLog.setImportStatus("已存在当前数据");
                    uumSyncLogInfo.add(uumSyncLog);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            uumSyncLog.setImportStatus("数据格式有问题，请认证检查数据！");
            uumSyncLogInfo.add(uumSyncLog);
            //如果sql插入数据报错，或者更新报错，都会进行数据的回调，直到excel的数据读取完
            importUumSyncLog(uumSyncLogList.subList(i+1,uumSyncLogList.size()), isUpdateSupport, operName);
            return uumSyncLogInfo;
        }

    }
        return uumSyncLogInfo;
    }
}
