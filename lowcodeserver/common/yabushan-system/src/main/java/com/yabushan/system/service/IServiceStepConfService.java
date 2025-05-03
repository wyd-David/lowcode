package com.yabushan.system.service;

import java.util.List;
import com.yabushan.system.domain.ServiceStepConf;

/**
 * stepService接口
 *
 * @author yabushan
 * @date 2021-04-21
 */
public interface IServiceStepConfService
{
    /**
     * 查询step
     *
     * @param keyId stepID
     * @return step
     */
    public ServiceStepConf selectServiceStepConfById(Long keyId);

    /**
     * 查询step列表
     *
     * @param serviceStepConf step
     * @return step集合
     */
    public List<ServiceStepConf> selectServiceStepConfList(ServiceStepConf serviceStepConf);

    /**
     * 新增step
     *
     * @param serviceStepConf step
     * @return 结果
     */
    public int insertServiceStepConf(ServiceStepConf serviceStepConf);

    /**
     * 修改step
     *
     * @param serviceStepConf step
     * @return 结果
     */
    public int updateServiceStepConf(ServiceStepConf serviceStepConf);

    /**
     * 批量删除step
     *
     * @param keyIds 需要删除的stepID
     * @return 结果
     */
    public int deleteServiceStepConfByIds(Long[] keyIds);

    /**
     * 删除step信息
     *
     * @param keyId stepID
     * @return 结果
     */
    public int deleteServiceStepConfById(Long keyId);


}
