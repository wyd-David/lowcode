package com.yabushan.system.service.impl;

import com.yabushan.common.utils.StringUtils;
import com.yabushan.system.domain.UumOrganizationinfo;
import com.yabushan.system.domain.UumUserinfo;
import com.yabushan.system.domain.vo.MyDeptVo;
import com.yabushan.system.mapper.UumOrganizationinfoMapper;
import com.yabushan.system.mapper.UumUserinfoMapper;
import com.yabushan.system.service.IUumOrganizationinfoService;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 同步组织信息Service业务层处理
 *
 * @author yabushan
 * @date 2022-04-27
 */
@Service
public class UumOrganizationinfoServiceImpl implements IUumOrganizationinfoService
{
    private static final Logger log = LoggerFactory.getLogger(UumOrganizationinfoServiceImpl.class);
    @Autowired
    private UumOrganizationinfoMapper uumOrganizationinfoMapper;
    @Autowired
    private UumUserinfoMapper uumUserinfoMapper;


    //存放导入所有的成功失败数据
    private static List<UumOrganizationinfo> uumOrganizationinfoInfo = new ArrayList<>();

    /**
     * 清空返回导入数据的集合
     */
    public void clearuumOrganizationinfoInfo() {
        if (StringUtils.isNotNull(uumOrganizationinfoInfo) && uumOrganizationinfoInfo.size() > 0) {
            uumOrganizationinfoInfo.clear();
        }
    }

    /**
     * 查询同步组织信息
     *
     * @param ouguid 同步组织信息ID
     * @return 同步组织信息
     */
    @Override
    public UumOrganizationinfo selectUumOrganizationinfoById(String ouguid)
    {
        return uumOrganizationinfoMapper.selectUumOrganizationinfoById(ouguid);
    }

    /**
     * 查询同步组织信息列表
     *
     * @param uumOrganizationinfo 同步组织信息
     * @return 同步组织信息
     */
    @Override
    public List<UumOrganizationinfo> selectUumOrganizationinfoList(UumOrganizationinfo uumOrganizationinfo)
    {
        return uumOrganizationinfoMapper.selectUumOrganizationinfoList(uumOrganizationinfo);
    }

    /**
     * 新增同步组织信息
     *
     * @param uumOrganizationinfo 同步组织信息
     * @return 结果
     */
    @Override
    public int insertUumOrganizationinfo(UumOrganizationinfo uumOrganizationinfo)
    {
        return uumOrganizationinfoMapper.insertUumOrganizationinfo(uumOrganizationinfo);
    }

    /**
     * 批量新增同步组织信息
     *
     * @param uumOrganizationinfo 同步组织信息
     * @return 结果
     */
    @Override
    public int bathInsertUumOrganizationinfo(List<UumOrganizationinfo> uumOrganizationinfo)
    {
        return uumOrganizationinfoMapper.bathInsertUumOrganizationinfo(uumOrganizationinfo);
    }


    /**
     * 修改同步组织信息
     *
     * @param uumOrganizationinfo 同步组织信息
     * @return 结果
     */
    @Override
    public int updateUumOrganizationinfo(UumOrganizationinfo uumOrganizationinfo)
    {
        return uumOrganizationinfoMapper.updateUumOrganizationinfo(uumOrganizationinfo);
    }

    /**
     * 批量删除同步组织信息
     *
     * @param ouguids 需要删除的同步组织信息ID
     * @return 结果
     */
    @Override
    public int deleteUumOrganizationinfoByIds(String[] ouguids)
    {
        return uumOrganizationinfoMapper.deleteUumOrganizationinfoByIds(ouguids);
    }

    /**
     * 删除同步组织信息信息
     *
     * @param ouguid 同步组织信息ID
     * @return 结果
     */
    @Override
    public int deleteUumOrganizationinfoById(String ouguid)
    {
        return uumOrganizationinfoMapper.deleteUumOrganizationinfoById(ouguid);
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
                UumOrganizationinfo uumOrganizationinfo=uumOrganizationinfoMapper.selectUumOrganizationinfoById(idslist[i]);
                uumOrganizationinfo.setDisableEnableState(disableEnableState);
                int isappinfo = uumOrganizationinfoMapper.updateUumOrganizationinfo(uumOrganizationinfo);
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
     * 导入同步组织信息Excel数据
     *
     * @param uumOrganizationinfoList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public List<UumOrganizationinfo>  importUumOrganizationinfo(List<UumOrganizationinfo> uumOrganizationinfoList, Boolean isUpdateSupport, String operName)
    {
        for (int i = 0; i < uumOrganizationinfoList.size(); i++) {
            UumOrganizationinfo uumOrganizationinfo=uumOrganizationinfoList.get(i);
            try {
                // 验证是否存在这个用户
                UumOrganizationinfo u = uumOrganizationinfoMapper.selectUumOrganizationinfoById(uumOrganizationinfo.getOuguid());
                if (StringUtils.isNull(u)) {
                    uumOrganizationinfoMapper.insertUumOrganizationinfo(uumOrganizationinfo);
                    uumOrganizationinfo.setImportStatus("导入成功");
                    uumOrganizationinfoInfo.add(uumOrganizationinfo);
                } else if (isUpdateSupport) {
                    uumOrganizationinfoMapper.updateUumOrganizationinfo(uumOrganizationinfo);
                    uumOrganizationinfo.setImportStatus("更新成功");
                    uumOrganizationinfoInfo.add(uumOrganizationinfo);
                } else {
                    uumOrganizationinfo.setImportStatus("已存在当前数据");
                    uumOrganizationinfoInfo.add(uumOrganizationinfo);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                uumOrganizationinfo.setImportStatus("数据格式有问题，请认证检查数据！");
                uumOrganizationinfoInfo.add(uumOrganizationinfo);
                //如果sql插入数据报错，或者更新报错，都会进行数据的回调，直到excel的数据读取完
                importUumOrganizationinfo(uumOrganizationinfoList.subList(i+1,uumOrganizationinfoList.size()), isUpdateSupport, operName);
                return uumOrganizationinfoInfo;
            }

        }
        return uumOrganizationinfoInfo;
    }

    @Override
    public String getDeptNameByLoginId(String userName) {
        MyDeptVo myDeptInfoByLoginId = getDeptInfo(userName);
        if(myDeptInfoByLoginId!=null){
            return  myDeptInfoByLoginId.getDeptName();
        }
        return null;
    }

    private MyDeptVo getDeptInfo(String userName) {
        UumUserinfo user =new UumUserinfo();
        MyDeptVo deptVo = new MyDeptVo();
        user.setUserid(userName);
        List<UumUserinfo> uumUserinfos = uumUserinfoMapper.selectUumUserinfoList(user);
        if(null!=uumUserinfos && uumUserinfos.size()>0){
            String parentOrgId= null;
            Boolean flag = true;
            UumOrganizationinfo org = uumOrganizationinfoMapper.selectUumOrganizationinfoById(uumUserinfos.get(0).getWorkouguid());
            parentOrgId=org.getParentouguid();
            if(null!=org && "UM".equals(org.getOrgtypeid())){
                deptVo.setDeptName(org.getOuname());
                deptVo.setDeptId(org.getOuguid());
            }
            else {
                while (true){
                    UumOrganizationinfo pOrg = uumOrganizationinfoMapper.selectUumOrganizationinfoById(parentOrgId);
                    parentOrgId=pOrg.getParentouguid();
                }
            }
        }
        return null;
    }


    public List<UumOrganizationinfo>  getYjxtUumOrgInfo(){

     return    uumOrganizationinfoMapper.getYjxtUumOrgInfo();

    }

}
