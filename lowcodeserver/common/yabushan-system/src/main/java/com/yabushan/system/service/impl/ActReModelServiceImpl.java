package com.yabushan.system.service.impl;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import cn.hutool.core.util.ObjectUtil;
import com.yabushan.common.utils.DateUtils;
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.system.domain.ServiceStepBtnConf;
import com.yabushan.system.mapper.ServiceStepBtnConfMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yabushan.system.mapper.ActReModelMapper;
import com.yabushan.system.domain.ActReModel;
import com.yabushan.system.service.IActReModelService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 工作流模型Service业务层处理
 *
 * @author yabushan
 * @date 2021-04-21
 */
@Service
public class ActReModelServiceImpl implements IActReModelService
{
    @Autowired
    private ActReModelMapper actReModelMapper;

    @Autowired
    private ServiceStepBtnConfMapper serviceStepBtnConfMapper;



    /**
     * 同步按钮
     * @param procdefId
     * @param procdefKey
     * @param stepMap
     * @return
     */
    public void syncProcessBnts(String procdefId,String procdefKey ,Map stepMap){
        ServiceStepBtnConf paramV=new ServiceStepBtnConf();
        paramV.setProcdefId(procdefId);

        //判断是否所有按钮都没有
        List<ServiceStepBtnConf> procdef_list= serviceStepBtnConfMapper.selectServiceStepBtnConf(paramV);
        if(!ObjectUtil.isEmpty(procdef_list)){
            return ;
        }
        paramV.setProcdefId(procdefKey);
        List<ServiceStepBtnConf> list_new= serviceStepBtnConfMapper.selectLastServiceStepBtnConf(paramV);
        if(ObjectUtil.isEmpty(list_new)){
            return ;
        }

        paramV.setProcdefId(list_new.get(0).getProcdefId());
        List<ServiceStepBtnConf> list_= serviceStepBtnConfMapper.selectServiceStepBtnConf(paramV);
        if(ObjectUtil.isEmpty(list_)){
            return ;
        }

        for(ServiceStepBtnConf item:list_){
            if(stepMap.containsKey(item.getStepCode())){
                item.setProcdefId(procdefId);
                item.setCreatedTime(new Date());
                item.setCreatedBy(SecurityUtils.getUsername());
                serviceStepBtnConfMapper.insert(item);
            }
        }
    }

    /**
     * 通过流程定义获取流程按钮
     * @param param
     * @return
     */
    public List<ServiceStepBtnConf> getProcessBntsByProdefId(ServiceStepBtnConf param){
        ServiceStepBtnConf paramV=new ServiceStepBtnConf();
        paramV.setProcdefId(param.getProcdefId());
        List<ServiceStepBtnConf> list= serviceStepBtnConfMapper.selectServiceStepBtnConfList(paramV);
        return list;
    }

    /**
     * 获取流程按钮
     * @param param
     * @return
     */
    public List<ServiceStepBtnConf> getProcessBnts(ServiceStepBtnConf param){
        List<ServiceStepBtnConf> list= serviceStepBtnConfMapper.selectServiceStepBtnConfList(param);
        return list;
    }

    /**
     * 保存流程按钮
     * @param list
     * @return
     */
    @Transactional(rollbackFor = {Exception.class})
    public int saveProcessBnts(List<ServiceStepBtnConf> list){
        if(ObjectUtil.isEmpty(list)){
            return 0;
        }
        else if(list.size()==1 && StringUtils.isEmpty(list.get(0).getStepName())){
            return 0;
        }
        ServiceStepBtnConf serviceStepBtnConf=new ServiceStepBtnConf();
        serviceStepBtnConf.setProcdefId(list.get(0).getProcdefId());
        serviceStepBtnConf.setStepCode(list.get(0).getStepCode());
        int res=0;
        res= serviceStepBtnConfMapper.deleteByParam(serviceStepBtnConf);
        for(ServiceStepBtnConf item:list){
            item.setCreatedBy(SecurityUtils.getUsername());
            item.setCreatedTime(new Date());
            res= serviceStepBtnConfMapper.insert(item);
        }
//
//
//        List<ServiceStepBtnConf>  old_list= serviceStepBtnConfMapper.selectServiceStepBtnConfList(serviceStepBtnConf);
//        Map<String, ServiceStepBtnConf> oldMap = old_list.stream().collect(Collectors.toMap(ServiceStepBtnConf::getBtnCode, Function.identity(), (key1, key2) -> key2));
//        Map<String, ServiceStepBtnConf> newMap = list.stream().collect(Collectors.toMap(ServiceStepBtnConf::getBtnCode, Function.identity(), (key1, key2) -> key2));
//        for(ServiceStepBtnConf item:list){
//            item.setCreatedBy(SecurityUtils.getUsername());
//            item.setCreatedTime(new Date());
////            修改
//            if(oldMap.containsKey(item.getBtnCode())){
//                res= serviceStepBtnConfMapper.updateByPrimaryKeySelective(item);
//            }else{//新增
//                res= serviceStepBtnConfMapper.insert(item);
//            }
//        }
//        for(ServiceStepBtnConf item:old_list){
////           删除
//            if(!newMap.containsKey(item.getBtnCode())){
//                res= serviceStepBtnConfMapper.deleteByParam(serviceStepBtnConf);
//            }
//        }

        return res;
    }

    /**
     * 查询工作流模型
     *
     * @param id 工作流模型ID
     * @return 工作流模型
     */
    @Override
    public ActReModel selectActReModelById(String id)
    {
        return actReModelMapper.selectActReModelById(id);
    }

    /**
     * 查询工作流模型列表
     *
     * @param actReModel 工作流模型
     * @return 工作流模型
     */
    @Override
    public List<ActReModel> selectActReModelList(ActReModel actReModel)
    {
        return actReModelMapper.selectActReModelList(actReModel);
    }

    /**
     * 新增工作流模型
     *
     * @param actReModel 工作流模型
     * @return 结果
     */
    @Override
    public int insertActReModel(ActReModel actReModel)
    {
        actReModel.setCreateTime(DateUtils.getNowDate());
        return actReModelMapper.insertActReModel(actReModel);
    }

    /**
     * 修改工作流模型
     *
     * @param actReModel 工作流模型
     * @return 结果
     */
    @Override
    public int updateActReModel(ActReModel actReModel)
    {
        return actReModelMapper.updateActReModel(actReModel);
    }

    /**
     * 批量删除工作流模型
     *
     * @param ids 需要删除的工作流模型ID
     * @return 结果
     */
    @Override
    public int deleteActReModelByIds(String[] ids)
    {
        return actReModelMapper.deleteActReModelByIds(ids);
    }

    /**
     * 删除工作流模型信息
     *
     * @param id 工作流模型ID
     * @return 结果
     */
    @Override
    public int deleteActReModelById(String id)
    {
        return actReModelMapper.deleteActReModelById(id);
    }
}
