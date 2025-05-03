package com.yabushan.system.service.impl;

import java.util.List;
import com.yabushan.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yabushan.system.mapper.NgsAttendanceMapper;
import com.yabushan.system.domain.NgsAttendance;
import com.yabushan.system.service.INgsAttendanceService;

/**
 * 考勤记录Service业务层处理
 *
 * @author yabushan
 * @date 2021-06-06
 */
@Service
public class NgsAttendanceServiceImpl implements INgsAttendanceService
{
    @Autowired
    private NgsAttendanceMapper ngsAttendanceMapper;

    /**
     * 查询考勤记录
     *
     * @param atdId 考勤记录ID
     * @return 考勤记录
     */
    @Override
    public NgsAttendance selectNgsAttendanceById(String atdId)
    {
        return ngsAttendanceMapper.selectNgsAttendanceById(atdId);
    }

    /**
     * 查询考勤记录列表
     *
     * @param ngsAttendance 考勤记录
     * @return 考勤记录
     */
    @Override
    public List<NgsAttendance> selectNgsAttendanceList(NgsAttendance ngsAttendance)
    {
        return ngsAttendanceMapper.selectNgsAttendanceList(ngsAttendance);
    }

    /**
     * 新增考勤记录
     *
     * @param ngsAttendance 考勤记录
     * @return 结果
     */
    @Override
    public int insertNgsAttendance(NgsAttendance ngsAttendance)
    {
        ngsAttendance.setCreateTime(DateUtils.getNowDate());
        return ngsAttendanceMapper.insertNgsAttendance(ngsAttendance);
    }

    /**
     * 修改考勤记录
     *
     * @param ngsAttendance 考勤记录
     * @return 结果
     */
    @Override
    public int updateNgsAttendance(NgsAttendance ngsAttendance)
    {
        return ngsAttendanceMapper.updateNgsAttendance(ngsAttendance);
    }

    /**
     * 批量删除考勤记录
     *
     * @param atdIds 需要删除的考勤记录ID
     * @return 结果
     */
    @Override
    public int deleteNgsAttendanceByIds(String[] atdIds)
    {
        return ngsAttendanceMapper.deleteNgsAttendanceByIds(atdIds);
    }

    /**
     * 删除考勤记录信息
     *
     * @param atdId 考勤记录ID
     * @return 结果
     */
    @Override
    public int deleteNgsAttendanceById(String atdId)
    {
        return ngsAttendanceMapper.deleteNgsAttendanceById(atdId);
    }
}
