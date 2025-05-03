package com.yabushan.system.service;

import java.util.List;

import com.yabushan.system.domain.SysConfigCalendar;

/**
 * 日历配置Service接口
 *
 * @author 姜森焱
 * @date 2021-09-08
 */
public interface ISysConfigCalendarService {
    /**
     * 查询日历配置
     *
     * @param id 日历配置ID
     * @return 日历配置
     */
    public SysConfigCalendar selectSysConfigCalendarById(String id);

    /**
     * 查询日历配置列表
     *
     * @param sysConfigCalendar 日历配置
     * @return 日历配置集合
     */
    public List<SysConfigCalendar> selectSysConfigCalendarList(SysConfigCalendar sysConfigCalendar);

    /**
     * 新增日历配置
     *
     * @param sysConfigCalendar 日历配置
     * @return 结果
     */
    public int insertSysConfigCalendar(SysConfigCalendar sysConfigCalendar);

    /**
     * 批量新增日历配置
     *
     * @param sysConfigCalendar 日历配置
     * @return 结果
     */
    public int bathInsertSysConfigCalendar(List<SysConfigCalendar> sysConfigCalendar);

    /**
     * 修改日历配置
     *
     * @param sysConfigCalendar 日历配置
     * @return 结果
     */
    public int updateSysConfigCalendar(SysConfigCalendar sysConfigCalendar);

    /**
     * 通过id集合修改状态
     */
    public Boolean updateByIds(String ids, String disableEnableState);

    /**
     * 批量删除日历配置
     *
     * @param ids 需要删除的日历配置ID
     * @return 结果
     */
    public int deleteSysConfigCalendarByIds(String[] ids);

    /**
     * 删除日历配置信息
     *
     * @param id 日历配置ID
     * @return 结果
     */
    public int deleteSysConfigCalendarById(String id);


    /**
     * 导入日历配置Excel数据
     *
     * @param sysConfigCalendarList 数据列表
     * @param isUpdateSupport       是否更新支持，如果已存在，则进行更新数据
     * @param operName              操作用户
     * @return 结果
     */
    public List<SysConfigCalendar> importSysConfigCalendar(List<SysConfigCalendar> sysConfigCalendarList, Boolean isUpdateSupport, String operName);
}
