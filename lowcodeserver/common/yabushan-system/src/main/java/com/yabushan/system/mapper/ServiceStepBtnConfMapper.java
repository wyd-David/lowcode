package com.yabushan.system.mapper;


import com.yabushan.system.domain.ServiceStepBtnConf;

import java.util.List;

public interface ServiceStepBtnConfMapper {
    int deleteByPrimaryKey(Long keyId);

    int insert(ServiceStepBtnConf record);

    int insertSelective(ServiceStepBtnConf record);

    ServiceStepBtnConf selectByPrimaryKey(Long keyId);

    int updateByPrimaryKeySelective(ServiceStepBtnConf record);

    int updateByPrimaryKey(ServiceStepBtnConf record);

    List<ServiceStepBtnConf> selectServiceStepBtnConfList(ServiceStepBtnConf serviceStepBtnConf);

    int deleteByParam(ServiceStepBtnConf record);

    List<ServiceStepBtnConf> selectServiceStepBtnConf(ServiceStepBtnConf serviceStepBtnConf);

    List<ServiceStepBtnConf> selectLastServiceStepBtnConf(ServiceStepBtnConf serviceStepBtnConf);


}
