package com.yabushan.system.service.impl;

import com.yabushan.common.utils.StringUtils;
import com.yabushan.system.domain.RoleControl;
import com.yabushan.system.mapper.RoleControlMapper;
import com.yabushan.system.service.IRoleControlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 *  角色授权控制Service业务层处理
 *
 * @author zcm
 * @date 2021-09-22
 */
@Service
public class RoleControlServiceImpl implements IRoleControlService
{
    private static final Logger log = LoggerFactory.getLogger(RoleControlServiceImpl.class);
    @Autowired
    private RoleControlMapper roleControlMapper;
    //存放导入所有的成功失败数据
    private static List<RoleControl> roleControlInfo = new ArrayList<>();

    /**
    * 清空返回导入数据的集合
    */
    public void clearroleControlInfo() {
        if (StringUtils.isNotNull(roleControlInfo) && roleControlInfo.size() > 0) {
                roleControlInfo.clear();
        }
    }

    /**
     * 查询 角色授权控制
     *
     * @param roleControlId  角色授权控制ID
     * @return  角色授权控制
     */
    @Override
    public RoleControl selectRoleControlById(String roleControlId)
    {
        return roleControlMapper.selectRoleControlById(roleControlId);
    }

    /**
     * 查询 角色授权控制列表
     *
     * @param roleControl  角色授权控制
     * @return  角色授权控制
     */
    @Override
    public List<RoleControl> selectRoleControlList(RoleControl roleControl)
    {
        return roleControlMapper.selectRoleControlList(roleControl);
    }

    /**
     * 新增 角色授权控制
     *
     * @param roleControl  角色授权控制
     * @return 结果
     */
    @Override
    public int insertRoleControl(RoleControl roleControl)
    {
        roleControl.setRoleControlId(StringUtils.getUUID());
        return roleControlMapper.insertRoleControl(roleControl);
    }

    /**
     * 批量新增 角色授权控制
     *
     * @param roleControl  角色授权控制
     * @return 结果
     */
    @Override
    public int bathInsertRoleControl(List<RoleControl> roleControl)
    {
        return roleControlMapper.bathInsertRoleControl(roleControl);
    }


    /**
     * 修改 角色授权控制
     *
     * @param roleControl  角色授权控制
     * @return 结果
     */
    @Override
    public int updateRoleControl(RoleControl roleControl)
    {
        return roleControlMapper.updateRoleControl(roleControl);
    }

    /**
     * 批量删除 角色授权控制
     *
     * @param roleControlIds 需要删除的 角色授权控制ID
     * @return 结果
     */
    @Override
    public int deleteRoleControlByIds(String[] roleControlIds)
    {
        return roleControlMapper.deleteRoleControlByIds(roleControlIds);
    }

    /**
     * 删除 角色授权控制信息
     *
     * @param roleControlId  角色授权控制ID
     * @return 结果
     */
    @Override
    public int deleteRoleControlById(String roleControlId)
    {
        return roleControlMapper.deleteRoleControlById(roleControlId);
    }

    /**
    * 根据传来的id集合修改禁用启用状态
    * @param ids
    * @param disableEnableState
    * @return
    */
    @Transactional
    @Override
    public Boolean updateByIds(String ids, String disableEnableState) {
        int count = 0;
        String idslist[]=ids.split(",",-1);
        if(StringUtils.isNotNull(idslist)&&idslist.length>=1){
            for (int i = 0; i < idslist.length; i++) {
                //Long.valueOf(ids.get(i))
                RoleControl roleControl=roleControlMapper.selectRoleControlById(ids);
                roleControl.setDisableEnableState(disableEnableState);
                int isappinfo = roleControlMapper.updateRoleControl(roleControl);
                if (isappinfo == 1) {
                    count++;
                }
            }
            if (count == idslist.length) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }




    /**
     * 导入 角色授权控制Excel数据
     *
     * @param roleControlList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public List<RoleControl>  importRoleControl(List<RoleControl> roleControlList, Boolean isUpdateSupport, String operName)
    {
        for (int i = 0; i < roleControlList.size(); i++) {
            RoleControl roleControl=roleControlList.get(i);
        try {
            // 验证是否存在这个用户
            RoleControl u = roleControlMapper.selectRoleControlById(roleControl.getRoleControlId());
            if (StringUtils.isNull(u)) {
                    roleControlMapper.insertRoleControl(roleControl);
                    roleControl.setImportStatus("导入成功");
                    roleControlInfo.add(roleControl);
            } else if (isUpdateSupport) {
                    roleControlMapper.updateRoleControl(roleControl);
                    roleControl.setImportStatus("更新成功");
                    roleControlInfo.add(roleControl);
            } else {
                    roleControl.setImportStatus("已存在当前数据");
                    roleControlInfo.add(roleControl);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            roleControl.setImportStatus("数据格式有问题，请认证检查数据！");
            roleControlInfo.add(roleControl);
            //如果sql插入数据报错，或者更新报错，都会进行数据的回调，直到excel的数据读取完
            importRoleControl(roleControlList.subList(i+1,roleControlList.size()), isUpdateSupport, operName);
            return roleControlInfo;
        }

    }
        return roleControlInfo;
    }
}
