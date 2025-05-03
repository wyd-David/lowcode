package com.yabushan.system.mapper;

import java.util.List;
import com.yabushan.system.domain.ActReModel;

/**
 * 工作流模型Mapper接口
 * 
 * @author yabushan
 * @date 2021-04-21
 */
public interface ActReModelMapper 
{
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
     * 删除工作流模型
     * 
     * @param id 工作流模型ID
     * @return 结果
     */
    public int deleteActReModelById(String id);

    /**
     * 批量删除工作流模型
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteActReModelByIds(String[] ids);
}
