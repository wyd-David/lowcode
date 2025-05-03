package com.yabushan.system.service;

import com.alibaba.fastjson.JSONArray;
import com.yabushan.system.domain.SyncOrg;
import com.yabushan.system.domain.vo.*;

import java.util.List;

/**
 * 同步组织信息Service接口
 * 
 * @author yabushan
 * @date 2021-08-24
 */
public interface ISyncOrgService 
{
    /**
     * 查询同步组织信息
     * 
     * @param id 同步组织信息ID
     * @return 同步组织信息
     */
    public SyncOrg selectSyncOrgById(Long id);

    /**
     * 查询同步组织信息列表
     * 
     * @param syncOrg 同步组织信息
     * @return 同步组织信息集合
     */
    public List<SyncOrg> selectSyncOrgList(SyncOrg syncOrg);

    /**
     * 新增同步组织信息
     * 
     * @param syncOrg 同步组织信息
     * @return 结果
     */
    public int insertSyncOrg(SyncOrg syncOrg);

    /**
     * 修改同步组织信息
     * 
     * @param syncOrg 同步组织信息
     * @return 结果
     */
    public int updateSyncOrg(SyncOrg syncOrg);

    /**
     * 批量删除同步组织信息
     * 
     * @param ids 需要删除的同步组织信息ID
     * @return 结果
     */
    public int deleteSyncOrgByIds(Long[] ids);

    /**
     * 删除同步组织信息信息
     * 
     * @param id 同步组织信息ID
     * @return 结果
     */
    public int deleteSyncOrgById(Long id);


    /**
     * 导入同步组织信息Excel数据
     *
     * @param syncOrgList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importSyncOrg(List<SyncOrg> syncOrgList, Boolean isUpdateSupport, String operName);


    /**
     * 同步部门组织信息
     *
     * @param urlTmp url
     * @param appId appId
     * @param start 状态
     * @param limit 条数
     * @return 结果
     */
    public void saveOrUpdateSynOrg();

    /**
     * 解析Json数据总条数
     * @return 结果
     */
    public int getJsonTotalCount(String json);

    /**
     * 解析Json数据为json数组
     * @return 结果
     */
    public JSONArray loadJson(String json);

    /**
     * 根据系统登录名查询所属对接部门
     *
     * @param userName 部门ID
     * @return 结果
     */
    String selectSyncOrgBySysDep(String userName);


    /**
     * 根据系统部门Id查询信息
     *
     * @param deptId
     * @return 结果
     */
    SyncOrg getSyncOrgBySysDep(String deptId);

    /**
     * 根据同步部门Id查询信息
     *
     * @param deptId
     * @return 结果
     */
    SyncOrg getSyncOrgBySyncDep(String deptId);


    //根据部门获取子级部门信息以及该部门的用户信息
    DepUserVo initOrgUserList(String orgId,String nickName);


    /**
     * 查询部门集合树形结构
     *
     *
     * @return 部门集合
     */
    public List<SyncOrg> selectSysSyncDepAllTree();

    /**
     * 查询部门集合
     *
     *
     * @return 部门集合
     */
    public List<SyncOrg> selectSysSyncDepAll(SyncOrg syncOrg);

    //获取我的部门信息
    MyDeptVo getMyDeptInfo();
    //获取我的部门信息
    MyDeptVo getMyDeptInfoByLoginId(String loginId);


    String getDeptNameByLoginId(String loginId);

    //根据登录id获取系统部门
    MyDeptVo getSysDeptInfo(String loginId);

    /**
     * 按流程角色名称、组织ID   获取人员
     * @param orgRoleUserVo
     * @return
     */
    List<RoleEmpInfoVo> getLcjsEmpInfo(OrgRoleUserVo orgRoleUserVo);


    List<SyncOrgVo> selectSyncDepAllVo(SyncOrg syncOrg);
}
