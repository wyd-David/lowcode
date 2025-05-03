package com.yabushan.system.mapper;

import java.util.List;
import com.yabushan.system.domain.DepartmentInfo;

/**
 * 部门信息 Mapper接口
 * 
 * @author yabushan
 * @date 2021-10-21
 */
public interface DepartmentInfoMapper 
{
    /**
     * 查询部门信息 
     * 
     * @param deptId 部门信息 ID
     * @return 部门信息 
     */
    public DepartmentInfo selectDepartmentInfoById(Integer deptId);

    /**
     * 查询部门信息 列表
     * 
     * @param departmentInfo 部门信息 
     * @return 部门信息 集合
     */
    public List<DepartmentInfo> selectDepartmentInfoList(DepartmentInfo departmentInfo);

    /**
     * 新增部门信息 
     * 
     * @param departmentInfo 部门信息 
     * @return 结果
     */
    public int insertDepartmentInfo(DepartmentInfo departmentInfo);

    /**
     * 批量新增部门信息 
     *
     * @param departmentInfo 部门信息 
     * @return 结果
     */
    public int bathInsertDepartmentInfo(List<DepartmentInfo> list);

    /**
     * 修改部门信息 
     * 
     * @param departmentInfo 部门信息 
     * @return 结果
     */
    public int updateDepartmentInfo(DepartmentInfo departmentInfo);

    /**
     * 删除部门信息 
     * 
     * @param deptId 部门信息 ID
     * @return 结果
     */
    public int deleteDepartmentInfoById(Integer deptId);

    /**
     * 批量删除部门信息 
     * 
     * @param deptIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteDepartmentInfoByIds(Integer[] deptIds);
}
