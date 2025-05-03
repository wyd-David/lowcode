package com.yabushan.quartz.task;

import com.yabushan.system.domain.UumOrganizationinfo;
import com.yabushan.system.domain.UumUserinfo;
import com.yabushan.system.service.IUumOrganizationinfoService;
import com.yabushan.system.service.IUumUserinfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 从预警系统同步组织和用户数据信息
 */
@Component("uumOrgUserTask")
@Slf4j
public class UumOrgUserTask {

    @Autowired
    private IUumUserinfoService uumUserinfoService;

    @Autowired
    private IUumOrganizationinfoService organizationinfoService;


    //同步用户数据
    public void  getYjxtUumUserInfo(){
        //1.获取预警系统的增量用户数据，根据增量时间点获取
        List<UumUserinfo> yjxtUumUserInfos = uumUserinfoService.getYjxtUumUserInfos();
        //2.判断数据是新增还是修改
        if(yjxtUumUserInfos!=null && yjxtUumUserInfos.size()>0){
            for(int i =0;i<yjxtUumUserInfos.size();i++){

                try{
                    UumUserinfo uumUserinfo = uumUserinfoService.selectByUserId(yjxtUumUserInfos.get(i).getUserid());
                    if(uumUserinfo==null){
                        //用户不存在，新增
                        uumUserinfoService.insertUumUserinfo(yjxtUumUserInfos.get(i));
                    }else{
                        //用户存在，更新
                        BeanUtils.copyProperties(yjxtUumUserInfos.get(i), uumUserinfo);
                        uumUserinfoService.updateUumUserinfo(uumUserinfo);
                    }
                }catch (Exception e ){
                    log.error("同步用户出错！"+e.toString());
                    continue;

                }
            }
        }
    }


    //同步用户数据
    public void  getYjxtUumOrgInfo(){
        //1.获取预警系统yj_xt的增量用户数据，根据增量时间点获取
        List<UumOrganizationinfo> yjxtUumOrgInfos = organizationinfoService.getYjxtUumOrgInfo();
        //2.判断数据是新增还是修改
        if(yjxtUumOrgInfos!=null && yjxtUumOrgInfos.size()>0){
            for(int i =0;i<yjxtUumOrgInfos.size();i++){

                try{
                    UumOrganizationinfo uumOrgInfo = organizationinfoService.selectUumOrganizationinfoById(yjxtUumOrgInfos.get(i).getOuguid());
                    if(uumOrgInfo==null){
                        //组织不存在，新增
                        organizationinfoService.insertUumOrganizationinfo(yjxtUumOrgInfos.get(i));
                    }else{
                        //组织存在，更新
                        BeanUtils.copyProperties(yjxtUumOrgInfos.get(i), uumOrgInfo);
                        organizationinfoService.updateUumOrganizationinfo(uumOrgInfo);
                    }
                }catch (Exception e ){
                    log.error("同步组织出错！"+e.toString());
                    continue;

                }
            }
        }
    }

}
