package com.yabushan.system.service.impl;

import com.yabushan.common.utils.DateUtils;
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.system.domain.SysConfigCalendar;
import com.yabushan.system.mapper.SysConfigCalendarMapper;
import com.yabushan.system.service.ISysConfigCalendarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 日历配置Service业务层处理
 *
 * @author 姜森焱
 * @date 2021-09-08
 */
@Service
public class SysConfigCalendarServiceImpl implements ISysConfigCalendarService {
    private static final Logger log = LoggerFactory.getLogger(SysConfigCalendarServiceImpl.class);
    @Autowired
    private SysConfigCalendarMapper sysConfigCalendarMapper;
    //存放导入所有的成功失败数据
    private static List<SysConfigCalendar> sysConfigCalendarInfo = new ArrayList<>();

    /**
     * 清空返回导入数据的集合
     */
    public void clearsysConfigCalendarInfo() {
        if (StringUtils.isNotNull(sysConfigCalendarInfo) && sysConfigCalendarInfo.size() > 0) {
            sysConfigCalendarInfo.clear();
        }
    }

    /**
     * 查询日历配置
     *
     * @param id 日历配置ID
     * @return 日历配置
     */
    @Override
    public SysConfigCalendar selectSysConfigCalendarById(String id) {
        return sysConfigCalendarMapper.selectSysConfigCalendarById(id);
    }

    /**
     * 查询日历配置列表
     *
     * @param sysConfigCalendar 日历配置
     * @return 日历配置
     */
    @Override
    public List<SysConfigCalendar> selectSysConfigCalendarList(SysConfigCalendar sysConfigCalendar) {
        return sysConfigCalendarMapper.selectSysConfigCalendarList(sysConfigCalendar);
    }

    /**
     * 新增日历配置
     *
     * @param sysConfigCalendar 日历配置
     * @return 结果
     */
    @Override
    public int insertSysConfigCalendar(SysConfigCalendar sysConfigCalendar) {
        sysConfigCalendar.setId(StringUtils.getUUID().toString());
        sysConfigCalendar.setCreateTime(DateUtils.getNowDate());
        sysConfigCalendar.setCreateBy(SecurityUtils.getUsername());
        return sysConfigCalendarMapper.insertSysConfigCalendar(sysConfigCalendar);
    }

    /**
     * 批量新增日历配置
     *
     * @param sysConfigCalendar 日历配置
     * @return 结果
     */
    @Override
    public int bathInsertSysConfigCalendar(List<SysConfigCalendar> sysConfigCalendar) {
        return sysConfigCalendarMapper.bathInsertSysConfigCalendar(sysConfigCalendar);
    }


    /**
     * 修改日历配置
     *
     * @param sysConfigCalendar 日历配置
     * @return 结果
     */
    @Override
    public int updateSysConfigCalendar(SysConfigCalendar sysConfigCalendar) {
        sysConfigCalendar.setUpdateBy(SecurityUtils.getUsername());
        sysConfigCalendar.setUpdateTime(DateUtils.getNowDate());
        return sysConfigCalendarMapper.updateSysConfigCalendar(sysConfigCalendar);
    }

    /**
     * 批量删除日历配置
     *
     * @param ids 需要删除的日历配置ID
     * @return 结果
     */
    @Override
    public int deleteSysConfigCalendarByIds(String[] ids) {
        return sysConfigCalendarMapper.deleteSysConfigCalendarByIds(ids);
    }

    /**
     * 删除日历配置信息
     *
     * @param id 日历配置ID
     * @return 结果
     */
    @Override
    public int deleteSysConfigCalendarById(String id) {
        return sysConfigCalendarMapper.deleteSysConfigCalendarById(id);
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
                SysConfigCalendar sysConfigCalendar = sysConfigCalendarMapper.selectSysConfigCalendarById(idslist[i]);
                sysConfigCalendar.setDisableEnableState(disableEnableState);
                int isappinfo = sysConfigCalendarMapper.updateSysConfigCalendar(sysConfigCalendar);
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
     * 导入日历配置Excel数据
     *
     * @param sysConfigCalendarList 数据列表
     * @param isUpdateSupport       是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public List<SysConfigCalendar> importSysConfigCalendar(List<SysConfigCalendar> sysConfigCalendarList, Boolean isUpdateSupport, String operName) {
        for (int i = 0; i < sysConfigCalendarList.size(); i++) {
            SysConfigCalendar sysConfigCalendar = sysConfigCalendarList.get(i);
            try {
                // 验证是否存在这个用户
                SysConfigCalendar u = sysConfigCalendarMapper.selectSysConfigCalendarById(sysConfigCalendar.getId());
                if (StringUtils.isNull(u)) {
                    sysConfigCalendarMapper.insertSysConfigCalendar(sysConfigCalendar);
                    sysConfigCalendar.setImportStatus("导入成功");
                    sysConfigCalendarInfo.add(sysConfigCalendar);
                } else if (isUpdateSupport) {
                    sysConfigCalendarMapper.updateSysConfigCalendar(sysConfigCalendar);
                    sysConfigCalendar.setImportStatus("更新成功");
                    sysConfigCalendarInfo.add(sysConfigCalendar);
                } else {
                    sysConfigCalendar.setImportStatus("已存在当前数据");
                    sysConfigCalendarInfo.add(sysConfigCalendar);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                sysConfigCalendar.setImportStatus("数据格式有问题，请认证检查数据！");
                sysConfigCalendarInfo.add(sysConfigCalendar);
                //如果sql插入数据报错，或者更新报错，都会进行数据的回调，直到excel的数据读取完
                importSysConfigCalendar(sysConfigCalendarList.subList(i + 1, sysConfigCalendarList.size()), isUpdateSupport, operName);
                return sysConfigCalendarInfo;
            }

        }
        return sysConfigCalendarInfo;
    }
}
