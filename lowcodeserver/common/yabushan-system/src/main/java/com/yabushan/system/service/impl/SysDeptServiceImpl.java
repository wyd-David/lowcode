package com.yabushan.system.service.impl;

import com.yabushan.common.annotation.DataScope;
import com.yabushan.common.constant.UserConstants;
import com.yabushan.common.core.domain.TreeSelect;
import com.yabushan.common.core.domain.entity.SysDept;
import com.yabushan.common.core.domain.entity.SysRole;
import com.yabushan.common.exception.CustomException;
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.system.domain.SyncOrg;
import com.yabushan.system.domain.SysSyncDep;
import com.yabushan.system.mapper.SyncOrgMapper;
import com.yabushan.system.mapper.SysDeptMapper;
import com.yabushan.system.mapper.SysRoleMapper;
import com.yabushan.system.service.ISyncOrgService;
import com.yabushan.system.service.ISysDeptService;
import com.yabushan.system.service.ISysSyncDepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 部门管理 服务实现
 *
 * @author ruoyi
 */
@Service
public class SysDeptServiceImpl implements ISysDeptService
{
    @Autowired
    private SysDeptMapper deptMapper;

    @Autowired
    private SysRoleMapper roleMapper;
    @Autowired
    private ISyncOrgService syncOrgService;
    @Autowired
    private ISysSyncDepService sysSyncDepService;
    @Autowired
    private SyncOrgMapper syncOrgMapper;

    /**
     * 查询部门管理数据
     *
     * @param dept 部门信息
     * @return 部门信息集合
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<SysDept> selectDeptList(SysDept dept)
    {
        String userName = SecurityUtils.getUsername();
        if(!"admin".equals(userName)){
            dept.setCreateBy(userName);
        }
        return deptMapper.selectDeptList(dept);
    }

    /**
     * 构建前端所需要树结构
     *
     * @param depts 部门列表
     * @return 树结构列表
     */
    @Override
    public List<SysDept> buildDeptTree(List<SysDept> depts)
    {
        List<SysDept> returnList = new ArrayList<SysDept>();
        List<Long> tempList = new ArrayList<Long>();
        for (SysDept dept : depts)
        {
            tempList.add(dept.getDeptId());
        }
        for (Iterator<SysDept> iterator = depts.iterator(); iterator.hasNext();)
        {
            SysDept dept = (SysDept) iterator.next();
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(dept.getParentId()))
            {
                recursionFn(depts, dept);
                returnList.add(dept);
            }
        }
        if (returnList.isEmpty())
        {
            returnList = depts;
        }
        return returnList;
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param depts 部门列表
     * @return 下拉树结构列表
     */
    @Override
    public List<TreeSelect> buildDeptTreeSelect(List<SysDept> depts)
    {
        List<SysDept> deptTrees = buildDeptTree(depts);
        List<TreeSelect> collect = deptTrees.stream().map(TreeSelect::new).collect(Collectors.toList());

      /*  collect.get(0).getChildren().remove(5);
        collect.get(0).getChildren().remove(4);
        collect.get(0).getChildren().remove(3);
        collect.get(0).getChildren().remove(0);*/
        return collect;
    }

    /**
     * 根据角色ID查询部门树信息
     *
     * @param roleId 角色ID
     * @return 选中部门列表
     */
    @Override
    public List<Integer> selectDeptListByRoleId(Long roleId)
    {
        SysRole role = roleMapper.selectRoleById(roleId);
        return deptMapper.selectDeptListByRoleId(roleId, role.isDeptCheckStrictly());
    }

    /**
     * 根据部门ID查询信息
     *
     * @param deptId 部门ID
     * @return 部门信息
     */
    @Override
    public SysDept selectDeptById(Long deptId)
    {
        return deptMapper.selectDeptById(deptId);
    }

    /**
     * 根据ID查询所有子部门（正常状态）
     *
     * @param deptId 部门ID
     * @return 子部门数
     */
    @Override
    public int selectNormalChildrenDeptById(Long deptId)
    {
        return deptMapper.selectNormalChildrenDeptById(deptId);
    }

    /**
     * 是否存在子节点
     *
     * @param deptId 部门ID
     * @return 结果
     */
    @Override
    public boolean hasChildByDeptId(Long deptId)
    {
        int result = deptMapper.hasChildByDeptId(deptId);
        return result > 0 ? true : false;
    }

    /**
     * 查询部门是否存在用户
     *
     * @param deptId 部门ID
     * @return 结果 true 存在 false 不存在
     */
    @Override
    public boolean checkDeptExistUser(Long deptId)
    {
        int result = deptMapper.checkDeptExistUser(deptId);
        return result > 0 ? true : false;
    }

    /**
     * 校验部门名称是否唯一
     *
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public String checkDeptNameUnique(SysDept dept)
    {
        Long deptId = StringUtils.isNull(dept.getDeptId()) ? -1L : dept.getDeptId();
        SysDept info = deptMapper.checkDeptNameUnique(dept.getDeptName(), dept.getParentId());
        if (StringUtils.isNotNull(info) && info.getDeptId().longValue() != deptId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 新增保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    @Transactional
    @Override
    public int insertDept(SysDept dept)
    {
        SysDept info = deptMapper.selectDeptById(dept.getParentId());
        // 如果父节点不为正常状态,则不允许新增子节点
        if (!UserConstants.DEPT_NORMAL.equals(info.getStatus()))
        {
            throw new CustomException("部门停用，不允许新增");
        }
        dept.setAncestors(info.getAncestors() + "," + dept.getParentId());
        dept.setDeptType("11");
        try {
            //新增系统部门信息
            deptMapper.insertDept(dept);
            String ancestors = dept.getAncestors() +","+dept.getDeptId();
            dept.setAncestors(ancestors);
            deptMapper.updateDept(dept);
            SysSyncDep sysSyncDep = new SysSyncDep();
            sysSyncDep.setSysDepId(dept.getParentId());
            //根据部门关联中间表获取组织表信息
            List<SysSyncDep> sysSyncDeps = sysSyncDepService.selectSysSyncDepList(sysSyncDep);
            if(sysSyncDeps != null && sysSyncDeps.size()>0){
                SyncOrg org = new SyncOrg();
                org.setDpid(sysSyncDeps.get(0).getSyncDepId());
                //获取组织表信息完善新增部门信息
                List<SyncOrg> syncOrgs = syncOrgService.selectSyncOrgList(org);
                if(syncOrgs != null && syncOrgs.size()>0){
                    SyncOrg syncOrg = new SyncOrg();
                    String UUID = StringUtils.getUUID();
                    syncOrg.setIsdept("1");
                    syncOrg.setDpid(UUID);
                    syncOrg.setDpcode(syncOrgs.get(0).getDpcode()+"."+UUID);
                    syncOrg.setDpname(dept.getDeptName());
                    syncOrg.setDpfullname(syncOrgs.get(0).getDpfullname()+">"+dept.getDeptName());
                    syncOrg.setDplv(syncOrgs.get(0).getDplv()+1);
                    syncOrg.setSerialnumber(999999L);
                    syncOrg.setCreatedate(new Date());
                    syncOrg.setModifieddate(new Date());
                    syncOrg.setParentdpid(syncOrgs.get(0).getDpid());
                    syncOrg.setLdapdpid("000000999999");
                    syncOrg.setDn("CN="+dept.getDeptName());
                    syncOrg.setIscrm(1L);
                    syncOrgService.insertSyncOrg(syncOrg);
                    sysSyncDepService.insertSysSyncDep(new SysSyncDep(dept.getDeptId(),UUID,"1"));
                }
            }
        }catch (Exception e){
            throw new CustomException("新增部门信息出错!");
        }
        return 1;
    }


    /**
     * 修改保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public int updateDept(SysDept dept)
    {
        SysDept newParentDept = deptMapper.selectDeptById(dept.getParentId());
        SysDept oldDept = deptMapper.selectDeptById(dept.getDeptId());
        if (StringUtils.isNotNull(newParentDept) && StringUtils.isNotNull(oldDept))
        {
            String newAncestors = newParentDept.getAncestors() + "," + newParentDept.getDeptId() + "," +oldDept.getDeptId();
            String oldAncestors = oldDept.getAncestors();
            dept.setAncestors(newAncestors);
            updateDeptChildren(dept.getDeptId(), newAncestors, oldAncestors);
        }
        int result = deptMapper.updateDept(dept);
        if (UserConstants.DEPT_NORMAL.equals(dept.getStatus()))
        {
            // 如果该部门是启用状态，则启用该部门的所有上级部门
            updateParentDeptStatus(dept);
        }

        //修改同步部门表信息
        SysSyncDep sysSyncDep = new SysSyncDep();
        sysSyncDep.setSysDepId(dept.getDeptId());
        //根据部门关联中间表获取组织表信息
        List<SysSyncDep> sysSyncDeps = sysSyncDepService.selectSysSyncDepList(sysSyncDep);
        if(sysSyncDeps !=null && sysSyncDeps.size() >0){
            SyncOrg org = syncOrgService.getSyncOrgBySyncDep(sysSyncDeps.get(0).getSyncDepId());
            if(org != null){
                sysSyncDep.setSysDepId(dept.getParentId());
                List<SysSyncDep> sysParentSyncDeps = sysSyncDepService.selectSysSyncDepList(sysSyncDep);
                if(sysParentSyncDeps !=null && sysParentSyncDeps.size() >0){
                    SyncOrg parentOrg = syncOrgService.getSyncOrgBySyncDep(sysParentSyncDeps.get(0).getSyncDepId());
                    String newAncestors = parentOrg.getDpcode() + "." + parentOrg.getDpid()+"."+org.getDpid();
                    String newDpFullName = parentOrg.getDpfullname() + ">" + parentOrg.getDpname()+ ">" + dept.getDeptName();
                    String oldAncestors = org.getDpcode();
                    updateSyncOrgChildren(org.getDpid(), newAncestors, oldAncestors);
                    org.setDpcode(newAncestors);
                    org.setDpfullname(newDpFullName);
                    org.setDpname(dept.getDeptName());
                    org.setParentdpid(parentOrg.getDpid());
                    syncOrgMapper.updateSyncOrg(org);
                }

            }
        }
        return result;
    }

    /**
     * 修改该部门的父级部门状态
     *
     * @param dept 当前部门
     */
    private void updateParentDeptStatus(SysDept dept)
    {
        String updateBy = dept.getUpdateBy();
        dept = deptMapper.selectDeptById(dept.getDeptId());
        dept.setUpdateBy(updateBy);
        deptMapper.updateDeptStatus(dept);
    }

    /**
     * 修改子元素关系
     *
     * @param deptId 被修改的部门ID
     * @param newAncestors 新的父ID集合
     * @param oldAncestors 旧的父ID集合
     */
    public void updateDeptChildren(Long deptId, String newAncestors, String oldAncestors)
    {
        List<SysDept> children = deptMapper.selectChildrenDeptById(deptId);
        for (SysDept child : children)
        {
            child.setAncestors(child.getAncestors().replace(oldAncestors, newAncestors));
        }
        if (children.size() > 0)
        {
            deptMapper.updateDeptChildren(children);
        }
    }



    /**
     * 修改子元素关系
     *
     * @param deptId 被修改的部门ID
     * @param newAncestors 新的父ID集合
     * @param oldAncestors 旧的父ID集合
     */
    public void updateSyncOrgChildren(String deptId, String newAncestors, String oldAncestors)
    {
        List<SyncOrg> children = syncOrgMapper.selectChildrenSyncDeptById(deptId);
        for (SyncOrg child : children)
        {
            child.setDpcode(child.getDpcode().replace(oldAncestors, newAncestors));
        }
        if (children.size() > 0)
        {
            syncOrgMapper.updateSyncDeptChildren(children);
        }
    }

    /**
     * 删除部门管理信息
     *
     * @param deptId 部门ID
     * @return 结果
     */
    @Override
    public int deleteDeptById(Long deptId)
    {
        int i = deptMapper.deleteDeptById(deptId);
        SysSyncDep sysSyncDep = new SysSyncDep();
        sysSyncDep.setSysDepId(deptId);
        //根据部门关联中间表获取组织表信息
        List<SysSyncDep> sysSyncDeps = sysSyncDepService.selectSysSyncDepList(sysSyncDep);
        if(sysSyncDeps !=null && sysSyncDeps.size() >0){
            i = syncOrgMapper.deleteSyncOrgByDepId(sysSyncDeps.get(0).getSyncDepId());
            i = sysSyncDepService.deleteSysSyncDepById(sysSyncDeps.get(0).getId());
        }
        return i;
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<SysDept> list, SysDept t)
    {
        // 得到子节点列表
        List<SysDept> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysDept tChild : childList)
        {
            if (hasChild(list, tChild))
            {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysDept> getChildList(List<SysDept> list, SysDept t)
    {
        List<SysDept> tlist = new ArrayList<SysDept>();
        Iterator<SysDept> it = list.iterator();
        while (it.hasNext())
        {
            SysDept n = (SysDept) it.next();
            if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().longValue() == t.getDeptId().longValue())
            {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysDept> list, SysDept t)
    {
        return getChildList(list, t).size() > 0 ? true : false;
    }

}
