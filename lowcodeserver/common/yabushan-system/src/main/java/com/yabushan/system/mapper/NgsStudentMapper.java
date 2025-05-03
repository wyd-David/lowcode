package com.yabushan.system.mapper;

import java.util.List;
import com.yabushan.system.domain.NgsStudent;

/**
 * 学生Mapper接口
 * 
 * @author yabushan
 * @date 2021-06-05
 */
public interface NgsStudentMapper 
{
    /**
     * 查询学生
     * 
     * @param stId 学生ID
     * @return 学生
     */
    public NgsStudent selectNgsStudentById(String stId);

    /**
     * 查询学生列表
     * 
     * @param ngsStudent 学生
     * @return 学生集合
     */
    public List<NgsStudent> selectNgsStudentList(NgsStudent ngsStudent);

    /**
     * 新增学生
     * 
     * @param ngsStudent 学生
     * @return 结果
     */
    public int insertNgsStudent(NgsStudent ngsStudent);

    /**
     * 修改学生
     * 
     * @param ngsStudent 学生
     * @return 结果
     */
    public int updateNgsStudent(NgsStudent ngsStudent);

    /**
     * 删除学生
     * 
     * @param stId 学生ID
     * @return 结果
     */
    public int deleteNgsStudentById(String stId);

    /**
     * 批量删除学生
     * 
     * @param stIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteNgsStudentByIds(String[] stIds);
}
