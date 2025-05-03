package com.yabushan.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yabushan.common.core.domain.entity.SysDept;
import com.yabushan.common.core.domain.entity.SysUser;
import com.yabushan.common.exception.CustomException;
import com.yabushan.common.utils.DateUtils;
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.common.utils.http.HttpUtils;
import com.yabushan.system.domain.*;
import com.yabushan.system.domain.vo.*;
import com.yabushan.system.enums.EnumsConfig;
import com.yabushan.system.mapper.*;
import com.yabushan.system.service.ISyncOrgService;
import com.yabushan.system.service.ISystemJobLogBatchService;
import com.yabushan.system.service.ISystemJobLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 同步组织信息Service业务层处理
 *
 * @author yabushan
 * @date 2021-08-24
 */
@Service
public class SyncOrgServiceImpl implements ISyncOrgService
{
    private static final Logger log = LoggerFactory.getLogger(SyncOrgServiceImpl.class);
    @Autowired
    private SyncOrgMapper syncOrgMapper;
    @Autowired
    private SysSyncDepMapper sysSyncDepMapper;
    @Autowired
    private SysDeptMapper sysDeptMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SyncUserMapper syncUserMapper;
    @Autowired
    private ISystemJobLogService systemJobLogService;
    @Autowired
    private UumOrganizationinfoMapper uumOrganizationinfoMapper;
    @Autowired
    private UumSyncLogMapper uumSyncLogMapper;




    /**
     * 查询同步组织信息
     *
     * @param id 同步组织信息ID
     * @return 同步组织信息
     */
    @Override
    public SyncOrg selectSyncOrgById(Long id)
    {
        return syncOrgMapper.selectSyncOrgById(id);
    }

    /**
     * 查询同步组织信息列表
     *
     * @param syncOrg 同步组织信息
     * @return 同步组织信息
     */
    @Override
    public List<SyncOrg> selectSyncOrgList(SyncOrg syncOrg)
    {
        return syncOrgMapper.selectSyncOrgList(syncOrg);
    }

    /**
     * 新增同步组织信息
     *
     * @param syncOrg 同步组织信息
     * @return 结果
     */
    @Override
    public int insertSyncOrg(SyncOrg syncOrg)
    {
        return syncOrgMapper.insertSyncOrg(syncOrg);
    }

    /**
     * 修改同步组织信息
     *
     * @param syncOrg 同步组织信息
     * @return 结果
     */
    @Override
    public int updateSyncOrg(SyncOrg syncOrg)
    {
        return syncOrgMapper.updateSyncOrg(syncOrg);
    }

    /**
     * 批量删除同步组织信息
     *
     * @param ids 需要删除的同步组织信息ID
     * @return 结果
     */
    @Override
    public int deleteSyncOrgByIds(Long[] ids)
    {
        return syncOrgMapper.deleteSyncOrgByIds(ids);
    }

    /**
     * 删除同步组织信息信息
     *
     * @param id 同步组织信息ID
     * @return 结果
     */
    @Override
    public int deleteSyncOrgById(Long id)
    {
        return syncOrgMapper.deleteSyncOrgById(id);
    }


    /**
     * 导入同步组织信息Excel数据
     *
     * @param syncOrgList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public String importSyncOrg(List<SyncOrg> syncOrgList, Boolean isUpdateSupport, String operName)
    {
        if (StringUtils.isNull(syncOrgList) || syncOrgList.size() == 0) {
            throw new CustomException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (SyncOrg syncOrg : syncOrgList) {
        try {
            // 验证是否存在这个用户
            SyncOrg u = syncOrgMapper.selectSyncOrgById(syncOrg.getId());
            if (StringUtils.isNull(u)) {
                    syncOrgMapper.insertSyncOrg(syncOrg);
                successNum++;
                successMsg.append("<br/>" + successNum + "、账号 " + operName + " 导入成功");
            } else if (isUpdateSupport) {
                    syncOrgMapper.updateSyncOrg(syncOrg);
                successNum++;
                successMsg.append("<br/>" + successNum + "、账号 " + operName + " 更新成功");
            } else {
                failureNum++;
                failureMsg.append("<br/>" + failureNum + "、账号 " + operName + " 已存在");
            }
        } catch (Exception e) {
            failureNum++;
            String msg = "<br/>" + failureNum + "、账号 " + operName + " 导入失败：";
            failureMsg.append(msg + e.getMessage());
            log.error(msg, e);
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new CustomException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
    }
        return successMsg.toString();
    }

    @Override
    public void saveOrUpdateSynOrg(){
        int totalCount = 0; //总数量
        String batchId = StringUtils.getUUID(); //数据批次id
        SystemJobLog jobLog = new SystemJobLog();
        jobLog.setApplyName("公共模块");
        jobLog.setApplyMiaos("同步组织数据");
        jobLog.setDataBatchId(batchId);
        jobLog.setJobName("同步组织信息");
        jobLog.setExeMethod("saveOrUpdateSynOrg");
        try {
            UumSyncLog uumSyncLog = uumSyncLogMapper.selectUumSyncLogByType("2");
            UumOrganizationinfo orginfo = new UumOrganizationinfo();
            if(uumSyncLog != null && StringUtils.isNotEmpty(uumSyncLog.getMaxTime())){
                orginfo.setLastmodifytime(DateUtils.dateTime("yyyy-MM-dd HH:mm:ss",uumSyncLog.getMaxTime()));
            }
            orginfo.setSortField("LASTMODIFYTIME");
            List<UumOrganizationinfo> uumOrganizationinfos = uumOrganizationinfoMapper.selectUumOrganizationinfoList(orginfo);
            //先初始化部门信息
            for (UumOrganizationinfo org : uumOrganizationinfos){
                //根据部门Id查询转换id
                String depId = sysSyncDepMapper.selectDepIdBySync(org.getOuguid());
                SysDept dept = new SysDept();
                if(StringUtils.isEmpty(depId)){
                    //如果转换Id为空则在系统表中添加该部门信息为id添加关联
                    dept.setDeptName(org.getOuname());
                    dept.setDeptType("00");
                    dept.setCreateTime(new Date());
                    sysDeptMapper.insertDept(dept);
                    SysSyncDep sysSyncDep = new SysSyncDep();
                    sysSyncDep.setSysDepId(dept.getDeptId());
                    sysSyncDep.setSyncDepId(org.getOuguid());
                    sysSyncDepMapper.insertSysSyncDep(sysSyncDep);
                }else{
                    //如果转换Id不为空则更新部门信息
                    dept.setDeptId(Long.valueOf(depId));
                    dept.setDeptName(org.getOuname());
                    String parentDepId = sysSyncDepMapper.selectDepIdBySync(org.getParentouguid());
                    if(StringUtils.isNotEmpty(parentDepId)){
                        dept.setParentId(Long.valueOf(parentDepId));
                    }
                    dept.setDelFlag("0");
                    dept.setUpdateTime(new Date());
                    sysDeptMapper.updateDept(dept);
                }

            }

            //更新父级部门信息
            for(UumOrganizationinfo org : uumOrganizationinfos){
                try {
                    String parentId = "";
                    String ancestors = "0,1";
                    //boolean isFlag = syncOrgs.stream().anyMatch(list -> list.getDpname().equals("公共群组"));
                    //判断是否有父级部门
                    String dpid = sysSyncDepMapper.selectDepIdBySync(org.getOuguid());
                    if(!StringUtils.isEmpty(org.getParentouguid())){
                        //查询父级部门转换id
                        parentId = sysSyncDepMapper.selectDepIdBySync(org.getParentouguid());
                        //根据对接部门中全部门路径Id拼接祖级列表
                        boolean flag = true;
                        String parentOuguid = org.getParentouguid();
                        while (flag){
                            flag = false;
                            String str = sysSyncDepMapper.selectDepIdBySync(parentOuguid);
                            if(!StringUtils.isEmpty(str)){
                                ancestors += ","+str;
                            }
                            UumOrganizationinfo uumOrganizationinfo = uumOrganizationinfoMapper.selectUumOrganizationinfoById(parentOuguid);
                            if(uumOrganizationinfo != null && !StringUtils.isEmpty(uumOrganizationinfo.getParentouguid())){
                                parentOuguid = uumOrganizationinfo.getParentouguid();
                                flag = true;
                            }
                        }

                        //如查到父级部门转换id与祖级部门列表拼接成功则更新该部门的对应信息
                        SysDept dept = new SysDept();
                        if(!StringUtils.isEmpty(dpid)){
                            //祖级层级需加上自己的id
                            ancestors += ","+dpid;
                            //判断父级节点是否存在，存在则更新父节点Id,不存在则不显示该组织信息
                            if(!StringUtils.isEmpty(parentId)){
                                //获取父节点信息判断父节点是否显示，如祖节点为空或查不到的情况则该组织也不显示
                                SysDept parentDep = sysDeptMapper.selectDeptById(Long.valueOf(parentId));
                                if(parentDep != null){
                                    dept.setParentId(Long.valueOf(parentId));
                                    dept.setAncestors(ancestors);
                                    dept.setDeptId(Long.valueOf(dpid));
                                    sysDeptMapper.updateDept(dept);
                                }else {
                                    //查询该部门是否有子部门
                                    int count = uumOrganizationinfoMapper.selectOrgByParentId(org.getParentouguid());
                                    //父级id为空时并且没有子部门时不显示
                                    if(count == 0){
                                        sysDeptMapper.deleteDeptById(Long.valueOf(dpid));
                                    }
                                }
                            }else {
                                //查询该部门是否有子部门
                                int count = uumOrganizationinfoMapper.selectOrgByParentId(org.getParentouguid());
                                //父级id为空时并且没有子部门时不显示
                                if(count == 0){
                                    sysDeptMapper.deleteDeptById(Long.valueOf(dpid));
                                }
                            }
                        }
                    }else {
                        //查询该部门是否有子部门
                        int count = uumOrganizationinfoMapper.selectOrgByParentId(org.getParentouguid());
                        //父级id为空时并且没有子部门时不显示
                        if(count == 0){
                            sysDeptMapper.deleteDeptById(Long.valueOf(dpid));
                        }
                    }
                }catch (Exception e){
                    log.error("同步组织信息错误：",e.getMessage());
                }

            }
            if(uumOrganizationinfos.size() > 0){
                totalCount = uumOrganizationinfos.size();
                String maxTime = DateUtils.parseDateToStr("yyyy-MM-dd HH:mm:ss",uumOrganizationinfos.get(uumOrganizationinfos.size()-1).getLastmodifytime());
                uumSyncLogMapper.insertUumSyncLog(new UumSyncLog(StringUtils.getUUID(),maxTime,"200",null,Long.valueOf(totalCount),"2"));
            }

        }catch (Exception e){
            jobLog.setJobStatus(EnumsConfig.LOG_STATUS_FAIL.getValue());
            jobLog.setJobExceptionInfo(e.getMessage());
            log.error("同步组织信息错误:"+e.getMessage());
            uumSyncLogMapper.insertUumSyncLog(new UumSyncLog(StringUtils.getUUID(),DateUtils.getTime(),"500",e.getMessage(),0l,"2"));
        }finally {
            jobLog.setDataNum(String.valueOf(totalCount));
            systemJobLogService.insertSystemJobLog(jobLog);
        }


    }

    public int getJsonTotalCount(String json) {
        int total = 0;
        try {
            JSONObject jsonObject = JSONObject.parseObject(json);
            String data = jsonObject.getString("data");
            JSONObject jsonObject1 = JSONObject.parseObject(data);
            total = jsonObject1.getIntValue("total");
        } catch (Exception e) {
            // TODO: handle exception
        }

        return total;
    }

    public JSONArray loadJson(String json) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        String data = jsonObject.getString("data");
        JSONObject jsonObject1 = JSONObject.parseObject(data);
        JSONArray jsonArray = jsonObject1.getJSONArray("data");
        return jsonArray;
    }

    @Override
    public String selectSyncOrgBySysDep(String userName) {
        SysUser sysUser = sysUserMapper.selectUserByUserName(userName);
        SyncOrg org = syncOrgMapper.selectSyncOrgBySysDep(sysUser.getDeptId().toString());
        String deptId = "";
        if(org != null){
            boolean flag = true;
            while(flag){
                if(org.getDplv() != 2){
                    flag = true;
                    org = syncOrgMapper.selectSyncOrgByDepId(org.getParentdpid());
                }else {
                    deptId = sysSyncDepMapper.selectDepIdBySync(org.getDpid());
                    flag = false;
                }
            }
        }
        return deptId;
    }

    @Override
    public SyncOrg getSyncOrgBySysDep(String deptId) {
        return syncOrgMapper.selectSyncOrgBySysDep(deptId);
    }

    @Override
    public SyncOrg getSyncOrgBySyncDep(String deptId) {
        return syncOrgMapper.getSyncOrgBySyncDep(deptId);
    }

    public void syncDepId(){
        //获取所有对接部门信息
        List<SyncOrg> syncOrgs = syncOrgMapper.selectSyncOrgListAll(new SyncOrg());
        //先初始化部门信息
        for (SyncOrg org : syncOrgs){
            //根据部门Id查询转换id
            String depId = sysSyncDepMapper.selectDepIdBySync(org.getDpid());
            SysDept dept = new SysDept();
            if(StringUtils.isEmpty(depId)){
                //如果转换Id为空则在系统表中添加该部门信息为id添加关联
                dept.setDeptName(org.getDpname());
                dept.setDeptType("00");
                dept.setCreateTime(new Date());
                sysDeptMapper.insertDept(dept);
                SysSyncDep sysSyncDep = new SysSyncDep();
                sysSyncDep.setSysDepId(dept.getDeptId());
                sysSyncDep.setSyncDepId(org.getDpid());
                sysSyncDep.setIsDept(org.getIsdept());
                sysSyncDepMapper.insertSysSyncDep(sysSyncDep);
            }else{
                //如果转换Id不为空则更新部门信息
                dept.setDeptId(Long.valueOf(depId));
                dept.setDeptName(org.getDpname());
                String parentDepId = sysSyncDepMapper.selectDepIdBySync(org.getParentdpid());
                if(StringUtils.isNotEmpty(parentDepId)){
                    dept.setParentId(Long.valueOf(parentDepId));
                }
                dept.setDelFlag("0");
                dept.setUpdateTime(new Date());
                sysDeptMapper.updateDept(dept);
            }

        }

        //更新父级部门信息
        for(SyncOrg org : syncOrgs){
            try {
                String parentId = "";
                String ancestors = "0,1";
                //boolean isFlag = syncOrgs.stream().anyMatch(list -> list.getDpname().equals("公共群组"));
                //判断是否有父级部门
                String dpid = sysSyncDepMapper.selectDepIdBySync(org.getDpid());
                if(!StringUtils.isEmpty(org.getParentdpid())){
                    //查询父级部门转换id
                    parentId = sysSyncDepMapper.selectDepIdBySync(org.getParentdpid());
                    //根据对接部门中全部门路径Id拼接祖级列表
                    if(!StringUtils.isEmpty(org.getDpcode())){
                        String[] split = org.getDpcode().split("\\.",-1);
                        for(String key:split){
                            String str = sysSyncDepMapper.selectDepIdBySync(key);
                            if(!StringUtils.isEmpty(str)){
                                ancestors += ","+str;
                            }
                        }
                    }
                    //如查到父级部门转换id与祖级部门列表拼接成功则更新该部门的对应信息
                    SysDept dept = new SysDept();
                    if(!StringUtils.isEmpty(dpid)){
                        //判断父级节点是否存在，存在则更新父节点Id,不存在则不显示该组织信息
                        if(!StringUtils.isEmpty(parentId)){
                            //获取父节点信息判断父节点是否显示，如祖节点为空或查不到的情况则该组织也不显示
                            SysDept parentDep = sysDeptMapper.selectDeptById(Long.valueOf(parentId));
                            if(parentDep != null){
                                dept.setParentId(Long.valueOf(parentId));
                                dept.setAncestors(ancestors);
                                dept.setDeptId(Long.valueOf(dpid));
                                sysDeptMapper.updateDept(dept);
                            }else {
                                //查询该部门是否有子部门
                                int count = syncOrgMapper.selectSyncOrgByParentId(org.getDpid());
                                //父级id为空时并且没有子部门时不显示
                                if(count == 0){
                                    sysDeptMapper.deleteDeptById(Long.valueOf(dpid));
                                }
                            }
                        }else {
                            //查询该部门是否有子部门
                            int count = syncOrgMapper.selectSyncOrgByParentId(org.getDpid());
                            //父级id为空时并且没有子部门时不显示
                            if(count == 0){
                                sysDeptMapper.deleteDeptById(Long.valueOf(dpid));
                            }
                        }
                    }
                }else {
                    //查询该部门是否有子部门
                    int count = syncOrgMapper.selectSyncOrgByParentId(org.getDpid());
                    //父级id为空时并且没有子部门时不显示
                    if(count == 0){
                        sysDeptMapper.deleteDeptById(Long.valueOf(dpid));
                    }
                }
            }catch (Exception e){
                log.error("同步组织信息错误：",e.getMessage());
            }

        }


    }



    public DepUserVo initOrgUserList(String orgId,String nickName){
        DepUserVo vo = new DepUserVo();
        if(StringUtils.isEmpty(nickName)){
            if(StringUtils.isEmpty(orgId)){
                orgId = "dproot";
            }
            List<SyncOrg> syncOrgs = syncOrgMapper.selectSyncOrgList(new SyncOrg(orgId,nickName));
            vo.setOrgList(syncOrgs);
        }
        List<SyncUserVo> syncUsers = syncUserMapper.getSyncUserList(new SyncUserVo(orgId,nickName));
        vo.setUserList(syncUsers);
        return vo;
    }

    /**
     * 查询树形结构
     * @param deptList
     * @param pid
     * @return
     */
    private static List<SyncOrg> buildTree(List<SyncOrg> deptList,String pid){
        List<SyncOrg> treeList = new ArrayList<SyncOrg>();
        for (SyncOrg dept : deptList) {
            if(pid.equals("dproot")){
                if(dept.getParentdpid()!=null){
                    if (dept.getParentdpid().equals(pid) ) {
                        dept.setChilNode(buildTree(deptList, dept.getDpid()));
                        treeList.add(dept);
                    }
                }

            }else{
                if(dept.getIsdept()!=null&&dept.getParentdpid()!=null){
                if (dept.getParentdpid().equals(pid) && "1".equals(dept.getIsdept())) {
                    dept.setChilNode(buildTree(deptList, dept.getDpid()));
                    treeList.add(dept);
                }
                }
            }

        }
        return treeList;
    }

    @Override
    public List<SyncOrg> selectSysSyncDepAllTree(){
        List<SyncOrg> list = syncOrgMapper.selectSysSyncDepAll();
        return buildTree(list,"dproot");
    }
    @Override
    public List<SyncOrg> selectSysSyncDepAll(SyncOrg syncOrg){
        //查询是部门
        List<SyncOrg> list = syncOrgMapper.selectSysSyncDepAllSenndLevel(syncOrg);
        return list;
    }

    /**
     * 获取我的部门信息
     * @return
     */
    @Override
    public MyDeptVo getMyDeptInfo() {
       return getDeptInfo(SecurityUtils.getUsername());
    }

    /**
     * 根据登录账号获取部门信息
     * @param loginId
     * @return
     */
    @Override
    public MyDeptVo getMyDeptInfoByLoginId(String loginId) {
        return getDeptInfo(loginId);
    }


    /**
     * 根据登录获取部门名称
     * @param loginId
     * @return
     */
    @Override
    public String getDeptNameByLoginId(String loginId) {
        MyDeptVo myDeptInfoByLoginId = getDeptInfo(loginId);
        if(myDeptInfoByLoginId!=null){
           return  myDeptInfoByLoginId.getDeptName();
        }
        return null;

    }

    private MyDeptVo getDeptInfo(String loginId){
        SyncUser user=new SyncUser();
        MyDeptVo deptVo = new MyDeptVo();
        user.setLoginid(loginId);
        List<SyncUser> syncUsers = syncUserMapper.selectSyncUserMaindepartmentList(user);

        String parentOrgId= null;
        Boolean flag = true;
        if(syncUsers.size()>0){
            deptVo.setMyPosition(syncUsers.get(0).getPosition());
            SyncOrg org = syncOrgMapper.selectSyncOrgByDepId(syncUsers.get(0).getDpid());
            parentOrgId=org.getParentdpid();
            if(org!=null && "1".equals(org.getIsdept())){
                deptVo.setDeptName(org.getDpname());
                deptVo.setDeptId(org.getDpid());
                //获取英文名
                SysSyncDep sysSyncDep = new SysSyncDep();
                sysSyncDep.setSyncDepId(org.getDpid());
                List<SysSyncDep> sysSyncDeps = sysSyncDepMapper.selectSysSyncDepList(sysSyncDep);
                if(sysSyncDeps.size()>0){
                    deptVo.setDeptEnName(sysSyncDeps.get(0).getDepEnglishName());
                }
            }else{
                //不是部门，递归上级组织找部门
                while (flag){

                    SyncOrg org1 = syncOrgMapper.selectSyncOrgByDepId(parentOrgId);
                    parentOrgId=org1.getParentdpid();
                    if(org1.getParentdpid()==null || "1".equals(org1.getDplv())){
                        //找不到部门
                        flag=false;
                    }
                    if("1".equals(org1.getIsdept())){
                        //是部门
                        SysSyncDep sysSyncDep = new SysSyncDep();
                        sysSyncDep.setSyncDepId(org1.getDpid());
                        List<SysSyncDep> sysSyncDeps = sysSyncDepMapper.selectSysSyncDepList(sysSyncDep);
                        deptVo.setDeptName(org1.getDpname());
                        deptVo.setDeptId(org1.getDpid());
                        if(sysSyncDeps.size()>0){
                            deptVo.setDeptEnName(sysSyncDeps.get(0).getDepEnglishName());
                        }
                        flag=false;
                    }
                }

            }
        }else{
            deptVo.setDeptId("8421");
            deptVo.setDeptName("");
            deptVo.setDeptEnName("");

        }
        return  deptVo;

    }


    public MyDeptVo getSysDeptInfo(String loginId){
        MyDeptVo deptInfo = getDeptInfo(loginId);
        if(StringUtils.isNotEmpty(deptInfo.getDeptId())){
            String depId = sysSyncDepMapper.selectDepIdBySync(deptInfo.getDeptId());
            if(StringUtils.isNotEmpty(depId)){
                SysDept dept = sysDeptMapper.selectDeptById(Long.valueOf(depId));
                if(dept != null){
                    deptInfo.setDeptId(String.valueOf(dept.getDeptId()));
                    deptInfo.setDeptName(dept.getDeptName());
                    return deptInfo;
                }
            }
        }
        return null;
    }


    /**
     * 按流程角色名称、组织ID   获取人员
     * @param orgRoleUserVo
     * @return
     */
    public  List<RoleEmpInfoVo> getLcjsEmpInfo(OrgRoleUserVo orgRoleUserVo){
        Boolean flag = true;
        List<RoleEmpInfoVo> lcjsEmpInfo=null;
        while (flag){
            //根据传入的组织ID和角色ID获取流程角色人员
            if("1".equals(orgRoleUserVo.getRoleType())){
                //角色
                lcjsEmpInfo = syncOrgMapper.getJsEmpInfo(orgRoleUserVo);
            }else{
                //流程角色
                lcjsEmpInfo = syncOrgMapper.getLcjsEmpInfo(orgRoleUserVo);
            }
            if(lcjsEmpInfo.size()>0){
                flag =true;
                break;
            }
            //当前组织下没有人员，往上级组织获取角色人员
            //获取组织信息
            SyncOrg org =new SyncOrg();
            org.setDpid(orgRoleUserVo.getOrgId());
            //获取组织信息
            List<SyncOrg> syncOrgs = syncOrgMapper.selectSyncOrgList(org);
            //封装流程角色人员查询
            if(syncOrgs.size()>0){
                orgRoleUserVo.setOrgId(syncOrgs.get(0).getParentdpid());
            }else{
                return null;
            }
        }
        return lcjsEmpInfo;
    }

    @Override
    public List<SyncOrgVo> selectSyncDepAllVo(SyncOrg syncOrg) {
        return syncOrgMapper.selectSyncDepAllVo(syncOrg);
    }
}
