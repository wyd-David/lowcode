package com.yabushan.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.common.utils.http.HttpUtils;
import com.yabushan.system.domain.HtGfRolePerson;
import com.yabushan.system.domain.SyncPortalRole;
import com.yabushan.system.mapper.HtGfRolePersonMapper;
import com.yabushan.system.service.IHtGfRolePersonService;
import com.yabushan.system.service.ISyncPortalRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 部门角色用户Service业务层处理
 *
 * @author yabushan
 * @date 2021-09-24
 */
@Service
public class HtGfRolePersonServiceImpl implements IHtGfRolePersonService
{
    private static final Logger log = LoggerFactory.getLogger(HtGfRolePersonServiceImpl.class);
    @Autowired
    private HtGfRolePersonMapper htGfRolePersonMapper;
    @Autowired
    private ISyncPortalRoleService syncPortalRoleService;
    //存放导入所有的成功失败数据
    private static List<HtGfRolePerson> htGfRolePersonInfo = new ArrayList<>();

    /**
    * 清空返回导入数据的集合
    */
    public void clearhtGfRolePersonInfo() {
        if (StringUtils.isNotNull(htGfRolePersonInfo) && htGfRolePersonInfo.size() > 0) {
                htGfRolePersonInfo.clear();
        }
    }

    /**
     * 查询部门角色用户
     *
     * @param id 部门角色用户ID
     * @return 部门角色用户
     */
    @Override
    public HtGfRolePerson selectHtGfRolePersonById(String id)
    {
        return htGfRolePersonMapper.selectHtGfRolePersonById(id);
    }

    /**
     * 查询部门角色用户列表
     *
     * @param htGfRolePerson 部门角色用户
     * @return 部门角色用户
     */
    @Override
    public List<HtGfRolePerson> selectHtGfRolePersonList(HtGfRolePerson htGfRolePerson)
    {
        return htGfRolePersonMapper.selectHtGfRolePersonList(htGfRolePerson);
    }

    /**
     * 新增部门角色用户
     *
     * @param htGfRolePerson 部门角色用户
     * @return 结果
     */
    @Override
    public int insertHtGfRolePerson(HtGfRolePerson htGfRolePerson)
    {
        return htGfRolePersonMapper.insertHtGfRolePerson(htGfRolePerson);
    }

    /**
     * 批量新增部门角色用户
     *
     * @param htGfRolePerson 部门角色用户
     * @return 结果
     */
    @Override
    public int bathInsertHtGfRolePerson(List<HtGfRolePerson> htGfRolePerson)
    {
        return htGfRolePersonMapper.bathInsertHtGfRolePerson(htGfRolePerson);
    }


    /**
     * 修改部门角色用户
     *
     * @param htGfRolePerson 部门角色用户
     * @return 结果
     */
    @Override
    public int updateHtGfRolePerson(HtGfRolePerson htGfRolePerson)
    {
        return htGfRolePersonMapper.updateHtGfRolePerson(htGfRolePerson);
    }

    /**
     * 批量删除部门角色用户
     *
     * @param ids 需要删除的部门角色用户ID
     * @return 结果
     */
    @Override
    public int deleteHtGfRolePersonByIds(String[] ids)
    {
        return htGfRolePersonMapper.deleteHtGfRolePersonByIds(ids);
    }

    /**
     * 删除部门角色用户信息
     *
     * @param id 部门角色用户ID
     * @return 结果
     */
    @Override
    public int deleteHtGfRolePersonById(String id)
    {
        return htGfRolePersonMapper.deleteHtGfRolePersonById(id);
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
                HtGfRolePerson htGfRolePerson=htGfRolePersonMapper.selectHtGfRolePersonById(idslist[i]);
                htGfRolePerson.setDisableEnableState(disableEnableState);
                int isappinfo = htGfRolePersonMapper.updateHtGfRolePerson(htGfRolePerson);
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

    public void initHtGfRolePerson(String urlTmp,String appId){
        List<SyncPortalRole> syncPortalRoles = syncPortalRoleService.selectSyncPortalRoleList(new SyncPortalRole());
        for (SyncPortalRole portalRole:syncPortalRoles){
            try {
                String rolename = URLEncoder.encode(portalRole.getPersonName(),"utf-8");
                String url = urlTmp + "/api/portal/userrole?appId=" + appId + "&rolename=" + rolename;
                String json = HttpUtils.get(url);
                JSONArray jsonArray = new JSONArray();
                if (StringUtils.isNotBlank(json)) {
                    jsonArray = syncPortalRoleService.loadJson(json);
                    if (jsonArray.size() > 0) {
                        for (int j = 0; j < jsonArray.size(); j++) {
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject(j);
                                String jsonobj = jsonObject.toString();
                                HtGfRolePerson person = new HtGfRolePerson();
                                //json转换为实体
                                person = (HtGfRolePerson) JSON.parseObject(jsonobj, HtGfRolePerson.class);
                                int count = htGfRolePersonMapper.getPersonByLoginId(person.getPersonLoginId());
                                if(count > 0){
                                    htGfRolePersonMapper.updateHtGfRolePerson(person);
                                }else{
                                    person.setId(StringUtils.getUUID());
                                    htGfRolePersonMapper.insertHtGfRolePerson(person);
                                }
                                //System.out.println(role.toString());
                                //syncPortalRoleMapper.insertSyncPortalRole(new SyncPortalRole(role.getRoleid(),role.getRolename()));
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
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}
