package com.yabushan.system.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.yabushan.common.utils.DateUtils;
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.system.utils.OrgTreeSelect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yabushan.system.mapper.NgsOrgMapper;
import com.yabushan.system.domain.NgsOrg;
import com.yabushan.system.service.INgsOrgService;

/**
 * 班级Service业务层处理
 *
 * @author yabushan
 * @date 2021-06-05
 */
@Service
public class NgsOrgServiceImpl implements INgsOrgService
{
    @Autowired
    private NgsOrgMapper ngsOrgMapper;

    /**
     * 查询班级
     *
     * @param orgId 班级ID
     * @return 班级
     */
    @Override
    public NgsOrg selectNgsOrgById(String orgId)
    {
        return ngsOrgMapper.selectNgsOrgById(orgId);
    }

    /**
     * 查询班级列表
     *
     * @param ngsOrg 班级
     * @return 班级
     */
    @Override
    public List<NgsOrg> selectNgsOrgList(NgsOrg ngsOrg)
    {
        return ngsOrgMapper.selectNgsOrgList(ngsOrg);
    }

    /**
     * 新增班级
     *
     * @param ngsOrg 班级
     * @return 结果
     */
    @Override
    public int insertNgsOrg(NgsOrg ngsOrg)
    {
        ngsOrg.setCreateTime(new Date());
        ngsOrg.setCreateBy(SecurityUtils.getUsername());
        ngsOrg.setOrgId(StringUtils.getUUID());
        return ngsOrgMapper.insertNgsOrg(ngsOrg);
    }

    /**
     * 修改班级
     *
     * @param ngsOrg 班级
     * @return 结果
     */
    @Override
    public int updateNgsOrg(NgsOrg ngsOrg)
    {
        ngsOrg.setUpdateTime(DateUtils.getNowDate());
        return ngsOrgMapper.updateNgsOrg(ngsOrg);
    }

    /**
     * 批量删除班级
     *
     * @param orgIds 需要删除的班级ID
     * @return 结果
     */
    @Override
    public int deleteNgsOrgByIds(String[] orgIds)
    {
        return ngsOrgMapper.deleteNgsOrgByIds(orgIds);
    }

    /**
     * 删除班级信息
     *
     * @param orgId 班级ID
     * @return 结果
     */
    @Override
    public int deleteNgsOrgById(String orgId)
    {
        return ngsOrgMapper.deleteNgsOrgById(orgId);
    }




    @Override
    public List<OrgTreeSelect> buildDeptTreeSelect(List<NgsOrg> depts) {
        List<NgsOrg> deptTrees = buildDeptTree(depts);
        return deptTrees.stream().map(OrgTreeSelect::new).collect(Collectors.toList());
    }

    public List<NgsOrg> buildDeptTree(List<NgsOrg> depts) {
        List<NgsOrg> returnList = new ArrayList<>();
        List<NgsOrg> tempList = new ArrayList<>();
        NgsOrg rootOrg = new NgsOrg();
        for (NgsOrg dept : depts){
            if(StringUtils.isEmpty(dept.getParentId())){
                rootOrg=dept;
            }else{
                tempList.add(dept);
            }
        }

        NgsOrg childs = getChilds(tempList, rootOrg);
        returnList.add(childs);
        return returnList;
    }


    private NgsOrg getChilds(List<NgsOrg> list, NgsOrg root){
        List<NgsOrg> childrens = new ArrayList<>();

        for(NgsOrg child :list){
            if(child.getParentId().equals(root.getOrgId())){
                childrens.add(child);
                getChilds(list, child);
            }
        }
        root.setChildren(childrens);
        return root;
    }
}
