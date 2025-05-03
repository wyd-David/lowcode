package com.yabushan.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yabushan.common.utils.DateUtils;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.common.utils.http.HttpUtils;
import com.yabushan.system.domain.SyncPortalRole;
import com.yabushan.system.domain.vo.SyncRole;
import com.yabushan.system.mapper.SyncPortalRoleMapper;
import com.yabushan.system.service.ISyncOrgService;
import com.yabushan.system.service.ISyncPortalRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 组织架构角色Service业务层处理
 *
 * @author yabushan
 * @date 2021-09-17
 */
@Service
public class SyncPortalRoleServiceImpl implements ISyncPortalRoleService
{
    private static final Logger log = LoggerFactory.getLogger(SyncPortalRoleServiceImpl.class);
    @Autowired
    private SyncPortalRoleMapper syncPortalRoleMapper;
    @Autowired
    private ISyncOrgService syncOrgService;
    //存放导入所有的成功失败数据
    private static List<SyncPortalRole> syncPortalRoleInfo = new ArrayList<>();

    /**
    * 清空返回导入数据的集合
    */
    public void clearsyncPortalRoleInfo() {
        if (StringUtils.isNotNull(syncPortalRoleInfo) && syncPortalRoleInfo.size() > 0) {
                syncPortalRoleInfo.clear();
        }
    }

    /**
     * 查询组织架构角色
     *
     * @param portalRoleId 组织架构角色ID
     * @return 组织架构角色
     */
    @Override
    public SyncPortalRole selectSyncPortalRoleById(String portalRoleId)
    {
        return syncPortalRoleMapper.selectSyncPortalRoleById(portalRoleId);
    }

    /**
     * 查询组织架构角色列表
     *
     * @param syncPortalRole 组织架构角色
     * @return 组织架构角色
     */
    @Override
    public List<SyncPortalRole> selectSyncPortalRoleList(SyncPortalRole syncPortalRole)
    {
        return syncPortalRoleMapper.selectSyncPortalRoleList(syncPortalRole);
    }

    /**
     * 新增组织架构角色
     *
     * @param syncPortalRole 组织架构角色
     * @return 结果
     */
    @Override
    public int insertSyncPortalRole(SyncPortalRole syncPortalRole)
    {
        syncPortalRole.setCreateTime(DateUtils.getNowDate());
        return syncPortalRoleMapper.insertSyncPortalRole(syncPortalRole);
    }

    /**
     * 批量新增组织架构角色
     *
     * @param syncPortalRole 组织架构角色
     * @return 结果
     */
    @Override
    public int bathInsertSyncPortalRole(List<SyncPortalRole> syncPortalRole)
    {
        return syncPortalRoleMapper.bathInsertSyncPortalRole(syncPortalRole);
    }


    /**
     * 修改组织架构角色
     *
     * @param syncPortalRole 组织架构角色
     * @return 结果
     */
    @Override
    public int updateSyncPortalRole(SyncPortalRole syncPortalRole)
    {
        return syncPortalRoleMapper.updateSyncPortalRole(syncPortalRole);
    }

    /**
     * 批量删除组织架构角色
     *
     * @param portalRoleIds 需要删除的组织架构角色ID
     * @return 结果
     */
    @Override
    public int deleteSyncPortalRoleByIds(String[] portalRoleIds)
    {
        return syncPortalRoleMapper.deleteSyncPortalRoleByIds(portalRoleIds);
    }

    /**
     * 删除组织架构角色信息
     *
     * @param portalRoleId 组织架构角色ID
     * @return 结果
     */
    @Override
    public int deleteSyncPortalRoleById(String portalRoleId)
    {
        return syncPortalRoleMapper.deleteSyncPortalRoleById(portalRoleId);
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
                SyncPortalRole syncPortalRole=syncPortalRoleMapper.selectSyncPortalRoleById(idslist[i]);
                syncPortalRole.setDisableEnableState(disableEnableState);
                int isappinfo = syncPortalRoleMapper.updateSyncPortalRole(syncPortalRole);
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


    public void saveOrUpdateSynOrg(String urlTmp,String appId){
        int totalCount = 0; //总数量


        String url = urlTmp + "/api/portal/alluserroles?appId=" + appId;
        // 将url转换为json
        try {
            String json = HttpUtils.get(url);
            JSONArray jsonArray = new JSONArray();
            if (StringUtils.isNotBlank(json)) {
                totalCount = syncOrgService.getJsonTotalCount(json);
                jsonArray = loadJson(json);
                if (jsonArray.size() > 0) {
                    syncPortalRoleMapper.trunCateSyncRole();
                    for (int j = 0; j < jsonArray.size(); j++) {
                        try {
                            JSONObject jsonObject = jsonArray.getJSONObject(j);
                            String jsonobj = jsonObject.toString();
                            SyncRole role = new SyncRole();
                            //json转换为实体
                            role = (SyncRole) JSON.parseObject(jsonobj, SyncRole.class);
                            //System.out.println(role.toString());
                            syncPortalRoleMapper.insertSyncPortalRole(new SyncPortalRole(role.getRoleid(),role.getRolename()));
                        } catch (Exception e) {
                            //异常信息默认显示（始终）
                            log.info(e.getMessage());
                        }
                    }
                    //同步到系统中部门表及添加关联
                    //syncDepId();
                } else {
                    //flag = false;
                }
            }
        }catch (Exception e){
            e.getMessage();
        }

    }

    public JSONArray loadJson(String json) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        String data = jsonObject.getString("data");
        JSONArray jsonArray = JSONArray.parseArray(data);
        return jsonArray;
    }

}
