package com.yabushan.system.service;

import java.util.List;
import com.yabushan.system.domain.NgsAttendance;

/**
 * 考勤记录Service接口
 * 
 * @author yabushan
 * @date 2021-06-06
 */
public interface INgsAttendanceService 
{
    /**
     * 查询考勤记录
     * 
     * @param atdId 考勤记录ID
     * @return 考勤记录
     */
    public NgsAttendance selectNgsAttendanceById(String atdId);

    /**
     * 查询考勤记录列表
     * 
     * @param ngsAttendance 考勤记录
     * @return 考勤记录集合
     */
    public List<NgsAttendance> selectNgsAttendanceList(NgsAttendance ngsAttendance);

    /**
     * 新增考勤记录
     * 
     * @param ngsAttendance 考勤记录
     * @return 结果
     */
    public int insertNgsAttendance(NgsAttendance ngsAttendance);

    /**
     * 修改考勤记录
     * 
     * @param ngsAttendance 考勤记录
     * @return 结果
     */
    public int updateNgsAttendance(NgsAttendance ngsAttendance);

    /**
     * 批量删除考勤记录
     * 
     * @param atdIds 需要删除的考勤记录ID
     * @return 结果
     */
    public int deleteNgsAttendanceByIds(String[] atdIds);

    /**
     * 删除考勤记录信息
     * 
     * @param atdId 考勤记录ID
     * @return 结果
     */
    public int deleteNgsAttendanceById(String atdId);
}
