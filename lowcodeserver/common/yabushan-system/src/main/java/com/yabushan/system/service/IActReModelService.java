package com.yabushan.system.service;

import java.util.List;
import java.util.Map;

import com.yabushan.system.domain.ActReModel;
import com.yabushan.system.domain.ServiceStepBtnConf;

/**
 * 工作流模型Service接口
 *
 * @author yabushan
 * @date 2021-04-21
 */
public interface IActReModelService
{

    /**
     * 同步按钮信息
     * @param procdefId
     * @param procdefKey
     * @param stepMap
     * @return
     */
    public void syncProcessBnts(String procdefId,String procdefKey ,Map stepMap);

    /**
     * 获取流程按钮
     * @param param
     * @return
     */
    public List<ServiceStepBtnConf> getProcessBntsByProdefId(ServiceStepBtnConf param);

    /**
     * 获取流程按钮
     * @param param
     * @return
     */
    public List<ServiceStepBtnConf> getProcessBnts(ServiceStepBtnConf param);


        /**
         * 保存流程按钮
         * @param list
         * @return
         */
    public int saveProcessBnts(List<ServiceStepBtnConf> list);

    /**
     * 查询工作流模型
     *
     * @param id 工作流模型ID
     * @return 工作流模型
     */
    public ActReModel selectActReModelById(String id);

    /**
     * 查询工作流模型列表
     *
     * @param actReModel 工作流模型
     * @return 工作流模型集合
     */
    public List<ActReModel> selectActReModelList(ActReModel actReModel);

    /**
     * 新增工作流模型
     *
     * @param actReModel 工作流模型
     * @return 结果
     */
    public int insertActReModel(ActReModel actReModel);

    /**
     * 修改工作流模型
     *
     * @param actReModel 工作流模型
     * @return 结果
     */
    public int updateActReModel(ActReModel actReModel);

    /**
     * 批量删除工作流模型
     *
     * @param ids 需要删除的工作流模型ID
     * @return 结果
     */
    public int deleteActReModelByIds(String[] ids);

    /**
     * 删除工作流模型信息
     *
     * @param id 工作流模型ID
     * @return 结果
     */
    public int deleteActReModelById(String id);
}
