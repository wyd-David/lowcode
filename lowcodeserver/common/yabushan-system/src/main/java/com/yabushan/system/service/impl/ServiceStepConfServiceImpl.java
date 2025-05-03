package com.yabushan.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yabushan.system.mapper.ServiceStepConfMapper;
import com.yabushan.system.domain.ServiceStepConf;
import com.yabushan.system.service.IServiceStepConfService;

/**
 * stepService业务层处理
 *
 * @author yabushan
 * @date 2021-04-21
 */
@Service
public class ServiceStepConfServiceImpl implements IServiceStepConfService
{
    @Autowired
    private ServiceStepConfMapper serviceStepConfMapper;

    /**
     * 查询step
     *
     * @param keyId stepID
     * @return step
     */
    @Override
    public ServiceStepConf selectServiceStepConfById(Long keyId)
    {
        return serviceStepConfMapper.selectServiceStepConfById(keyId);
    }

    /**
     * 查询step列表
     *
     * @param serviceStepConf step
     * @return step
     */
    @Override
    public List<ServiceStepConf> selectServiceStepConfList(ServiceStepConf serviceStepConf)
    {
        return serviceStepConfMapper.selectServiceStepConfList(serviceStepConf);
    }

    /**
     * 新增step
     *
     * @param serviceStepConf step
     * @return 结果
     */
    @Override
    public int insertServiceStepConf(ServiceStepConf serviceStepConf)
    {
        return serviceStepConfMapper.insertServiceStepConf(serviceStepConf);
    }

    /**
     * 修改step
     *
     * @param serviceStepConf step
     * @return 结果
     */
    @Override
    public int updateServiceStepConf(ServiceStepConf serviceStepConf)
    {
        return serviceStepConfMapper.updateServiceStepConf(serviceStepConf);
    }

    /**
     * 批量删除step
     *
     * @param keyIds 需要删除的stepID
     * @return 结果
     */
    @Override
    public int deleteServiceStepConfByIds(Long[] keyIds)
    {
        return serviceStepConfMapper.deleteServiceStepConfByIds(keyIds);
    }

    /**
     * 删除step信息
     *
     * @param keyId stepID
     * @return 结果
     */
    @Override
    public int deleteServiceStepConfById(Long keyId)
    {
        return serviceStepConfMapper.deleteServiceStepConfById(keyId);
    }
}
