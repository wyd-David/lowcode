package com.yabushan.system.mapper;

import java.util.List;

import com.yabushan.system.domain.SysConfigCalendar;
import org.apache.ibatis.annotations.Param;

/**
 * 日历配置Mapper接口
 *
 * @author 姜森焱
 * @date 2021-09-08
 */
public interface SysConfigCalendarMapper {
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
    public int bathInsertSysConfigCalendar(List<SysConfigCalendar> list);

    /**
     * 修改日历配置
     *
     * @param sysConfigCalendar 日历配置
     * @return 结果
     */
    public int updateSysConfigCalendar(SysConfigCalendar sysConfigCalendar);

    /**
     * 删除日历配置
     *
     * @param id 日历配置ID
     * @return 结果
     */
    public int deleteSysConfigCalendarById(String id);

    /**
     * 批量删除日历配置
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysConfigCalendarByIds(String[] ids);

    public SysConfigCalendar getSysConfigCalendarByDate(@Param("date") String date);

}
