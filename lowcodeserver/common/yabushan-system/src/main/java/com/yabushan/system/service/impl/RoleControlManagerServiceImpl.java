package com.yabushan.system.service.impl;

import com.yabushan.common.utils.DateUtils;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.system.domain.RoleControlManager;
import com.yabushan.system.domain.ViewEmpInfo;
import com.yabushan.system.mapper.RoleControlManagerMapper;
import com.yabushan.system.service.IRoleControlManagerService;
import com.yabushan.system.service.IViewEmpInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 *  角色授权控制管理Service业务层处理
 *
 * @author zcm
 * @date 2021-09-22
 */
@Service
public class RoleControlManagerServiceImpl implements IRoleControlManagerService
{
    private static final Logger log = LoggerFactory.getLogger(RoleControlManagerServiceImpl.class);
    @Autowired
    private RoleControlManagerMapper roleControlManagerMapper;
    @Autowired
    private IViewEmpInfoService viewEmpInfoService;
    //存放导入所有的成功失败数据
    private static List<RoleControlManager> roleControlManagerInfo = new ArrayList<>();

    /**
    * 清空返回导入数据的集合
    */
    public void clearroleControlManagerInfo() {
        if (StringUtils.isNotNull(roleControlManagerInfo) && roleControlManagerInfo.size() > 0) {
                roleControlManagerInfo.clear();
        }
    }

    /**
     * 查询 角色授权控制管理
     *
     * @param roleControlManagerId  角色授权控制管理ID
     * @return  角色授权控制管理
     */
    @Override
    public RoleControlManager selectRoleControlManagerById(String roleControlManagerId)
    {

        RoleControlManager roleControlManager = roleControlManagerMapper.selectRoleControlManagerById(roleControlManagerId);
        if(roleControlManager!=null){
            ViewEmpInfo viewEmpInfo = viewEmpInfoService.selectViewEmpInfoById(roleControlManager.getUserId());
            if(viewEmpInfo!=null){
                roleControlManager.setPosition(viewEmpInfo.getPosition());
            }
        }
        return roleControlManager;
    }

    /**
     * 查询 角色授权控制管理列表
     *
     * @param roleControlManager  角色授权控制管理
     * @return  角色授权控制管理
     */
    @Override
    public List<RoleControlManager> selectRoleControlManagerList(RoleControlManager roleControlManager)
    {
        return roleControlManagerMapper.selectRoleControlManagerList(roleControlManager);
    }

    /**
     * 新增 角色授权控制管理
     *
     * @param roleControlManager  角色授权控制管理
     * @return 结果
     */
    @Override
    public int insertRoleControlManager(RoleControlManager roleControlManager)
    {
        roleControlManager.setRoleControlManagerId(StringUtils.getUUID());
        roleControlManager.setCreateTime(DateUtils.getNowDate());
        return roleControlManagerMapper.insertRoleControlManager(roleControlManager);
    }

    /**
     * 批量新增 角色授权控制管理
     *
     * @param roleControlManager  角色授权控制管理
     * @return 结果
     */
    @Override
    public int bathInsertRoleControlManager(List<RoleControlManager> roleControlManager)
    {
        return roleControlManagerMapper.bathInsertRoleControlManager(roleControlManager);
    }


    /**
     * 修改 角色授权控制管理
     *
     * @param roleControlManager  角色授权控制管理
     * @return 结果
     */
    @Override
    public int updateRoleControlManager(RoleControlManager roleControlManager)
    {
        roleControlManager.setUpdateTime(DateUtils.getNowDate());
        return roleControlManagerMapper.updateRoleControlManager(roleControlManager);
    }

    /**
     * 批量删除 角色授权控制管理
     *
     * @param roleControlManagerIds 需要删除的 角色授权控制管理ID
     * @return 结果
     */
    @Override
    public int deleteRoleControlManagerByIds(String[] roleControlManagerIds)
    {
        return roleControlManagerMapper.deleteRoleControlManagerByIds(roleControlManagerIds);
    }

    /**
     * 删除 角色授权控制管理信息
     *
     * @param roleControlManagerId  角色授权控制管理ID
     * @return 结果
     */
    @Override
    public int deleteRoleControlManagerById(String roleControlManagerId)
    {
        return roleControlManagerMapper.deleteRoleControlManagerById(roleControlManagerId);
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
                RoleControlManager roleControlManager=roleControlManagerMapper.selectRoleControlManagerById(ids);
                roleControlManager.setDisableEnableState(disableEnableState);
                int isappinfo = roleControlManagerMapper.updateRoleControlManager(roleControlManager);
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
     * 导入 角色授权控制管理Excel数据
     *
     * @param roleControlManagerList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public List<RoleControlManager>  importRoleControlManager(List<RoleControlManager> roleControlManagerList, Boolean isUpdateSupport, String operName)
    {
        for (int i = 0; i < roleControlManagerList.size(); i++) {
            RoleControlManager roleControlManager=roleControlManagerList.get(i);
        try {
            // 验证是否存在这个用户
            RoleControlManager u = roleControlManagerMapper.selectRoleControlManagerById(roleControlManager.getRoleControlManagerId());
            if (StringUtils.isNull(u)) {
                    roleControlManagerMapper.insertRoleControlManager(roleControlManager);
                    roleControlManager.setImportStatus("导入成功");
                    roleControlManagerInfo.add(roleControlManager);
            } else if (isUpdateSupport) {
                    roleControlManagerMapper.updateRoleControlManager(roleControlManager);
                    roleControlManager.setImportStatus("更新成功");
                    roleControlManagerInfo.add(roleControlManager);
            } else {
                    roleControlManager.setImportStatus("已存在当前数据");
                    roleControlManagerInfo.add(roleControlManager);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            roleControlManager.setImportStatus("数据格式有问题，请认证检查数据！");
            roleControlManagerInfo.add(roleControlManager);
            //如果sql插入数据报错，或者更新报错，都会进行数据的回调，直到excel的数据读取完
            importRoleControlManager(roleControlManagerList.subList(i+1,roleControlManagerList.size()), isUpdateSupport, operName);
            return roleControlManagerInfo;
        }

    }
        return roleControlManagerInfo;
    }
}
