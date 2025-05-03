package com.yabushan.system.mapper;

import java.util.List;
import com.yabushan.system.domain.WarnSmsModel;

/**
 * 短信模型Mapper接口
 * 
 * @author yabushan
 * @date 2022-05-10
 */
public interface WarnSmsModelMapper 
{
    /**
     * 查询短信模型
     * 
     * @param id 短信模型ID
     * @return 短信模型
     */
    public WarnSmsModel selectWarnSmsModelById(String id);

    WarnSmsModel selectWarnSmsModelByModelId(String modelId);


    /**
     * 查询短信模型列表
     * 
     * @param warnSmsModel 短信模型
     * @return 短信模型集合
     */
    public List<WarnSmsModel> selectWarnSmsModelList(WarnSmsModel warnSmsModel);

    /**
     * 新增短信模型
     * 
     * @param warnSmsModel 短信模型
     * @return 结果
     */
    public int insertWarnSmsModel(WarnSmsModel warnSmsModel);

    /**
     * 批量新增短信模型
     *
     * @param warnSmsModel 短信模型
     * @return 结果
     */
    public int bathInsertWarnSmsModel(List<WarnSmsModel> list);

    /**
     * 修改短信模型
     * 
     * @param warnSmsModel 短信模型
     * @return 结果
     */
    public int updateWarnSmsModel(WarnSmsModel warnSmsModel);

    /**
     * 删除短信模型
     * 
     * @param id 短信模型ID
     * @return 结果
     */
    public int deleteWarnSmsModelById(String id);

    /**
     * 批量删除短信模型
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWarnSmsModelByIds(String[] ids);
}
