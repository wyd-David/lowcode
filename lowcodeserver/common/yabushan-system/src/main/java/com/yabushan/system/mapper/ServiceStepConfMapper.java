package com.yabushan.system.mapper;

import java.util.List;
import com.yabushan.system.domain.ServiceStepConf;

/**
 * stepMapper接口
 *
 * @author yabushan
 * @date 2021-04-21
 */
public interface ServiceStepConfMapper
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
     * 删除step
     *
     * @param keyId stepID
     * @return 结果
     */
    public int deleteServiceStepConfById(Long keyId);

    /**
     * 批量删除step
     *
     * @param keyIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteServiceStepConfByIds(Long[] keyIds);



}
