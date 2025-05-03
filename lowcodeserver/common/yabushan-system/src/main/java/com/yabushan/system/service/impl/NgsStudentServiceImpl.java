package com.yabushan.system.service.impl;

import java.util.List;
import com.yabushan.common.utils.DateUtils;
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yabushan.system.mapper.NgsStudentMapper;
import com.yabushan.system.domain.NgsStudent;
import com.yabushan.system.service.INgsStudentService;

/**
 * 学生Service业务层处理
 *
 * @author yabushan
 * @date 2021-06-05
 */
@Service
public class NgsStudentServiceImpl implements INgsStudentService
{
    @Autowired
    private NgsStudentMapper ngsStudentMapper;

    /**
     * 查询学生
     *
     * @param stId 学生ID
     * @return 学生
     */
    @Override
    public NgsStudent selectNgsStudentById(String stId)
    {
        return ngsStudentMapper.selectNgsStudentById(stId);
    }

    /**
     * 查询学生列表
     *
     * @param ngsStudent 学生
     * @return 学生
     */
    @Override
    public List<NgsStudent> selectNgsStudentList(NgsStudent ngsStudent)
    {
        return ngsStudentMapper.selectNgsStudentList(ngsStudent);
    }

    /**
     * 新增学生
     *
     * @param ngsStudent 学生
     * @return 结果
     */
    @Override
    public int insertNgsStudent(NgsStudent ngsStudent)
    {
        ngsStudent.setCreateTime(DateUtils.getNowDate());
        ngsStudent.setCreateBy(SecurityUtils.getUsername());
        ngsStudent.setStId(StringUtils.getUUID());
        return ngsStudentMapper.insertNgsStudent(ngsStudent);
    }

    /**
     * 修改学生
     *
     * @param ngsStudent 学生
     * @return 结果
     */
    @Override
    public int updateNgsStudent(NgsStudent ngsStudent)
    {
        ngsStudent.setUpdateTime(DateUtils.getNowDate());
        return ngsStudentMapper.updateNgsStudent(ngsStudent);
    }

    /**
     * 批量删除学生
     *
     * @param stIds 需要删除的学生ID
     * @return 结果
     */
    @Override
    public int deleteNgsStudentByIds(String[] stIds)
    {
        return ngsStudentMapper.deleteNgsStudentByIds(stIds);
    }

    /**
     * 删除学生信息
     *
     * @param stId 学生ID
     * @return 结果
     */
    @Override
    public int deleteNgsStudentById(String stId)
    {
        return ngsStudentMapper.deleteNgsStudentById(stId);
    }
}
