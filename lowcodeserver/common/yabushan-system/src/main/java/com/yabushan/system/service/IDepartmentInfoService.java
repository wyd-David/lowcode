package com.yabushan.system.service;

import com.yabushan.system.domain.DepartmentInfo;

import java.util.List;

/**
 * 部门信息 Service接口
 *
 * @author yabushan
 * @date 2021-10-21
 */
public interface IDepartmentInfoService
{



    /**
    * 清空返回导入数据的集合
    */
    public void cleardepartmentInfoInfo();


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
    public int bathInsertDepartmentInfo(List<DepartmentInfo> departmentInfo);

    /**
     * 修改部门信息
     *
     * @param departmentInfo 部门信息
     * @return 结果
     */
    public int updateDepartmentInfo(DepartmentInfo departmentInfo);
    /**
    * 通过id集合修改状态
    */
    public Boolean updateByIds(String ids,String disableEnableState);

    /**
     * 批量删除部门信息
     *
     * @param deptIds 需要删除的部门信息 ID
     * @return 结果
     */
    public int deleteDepartmentInfoByIds(Integer[] deptIds);

    /**
     * 删除部门信息 信息
     *
     * @param deptId 部门信息 ID
     * @return 结果
     */
    public int deleteDepartmentInfoById(Integer deptId);


    /**
     * 导入部门信息 Excel数据
     *
     * @param departmentInfoList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public List<DepartmentInfo> importDepartmentInfo(List<DepartmentInfo> departmentInfoList, Boolean isUpdateSupport, String operName);
}
