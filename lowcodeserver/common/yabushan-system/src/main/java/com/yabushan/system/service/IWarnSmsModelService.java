package com.yabushan.system.service;

import java.util.List;
import com.yabushan.system.domain.WarnSmsModel;

/**
 * 短信模型Service接口
 *
 * @author yabushan
 * @date 2022-05-10
 */
public interface IWarnSmsModelService
{



    /**
    * 清空返回导入数据的集合
    */
    public void clearwarnSmsModelInfo();


    /**
     * 查询短信模型
     *
     * @param id 短信模型ID
     * @return 短信模型
     */
    public WarnSmsModel selectWarnSmsModelById(String id);

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
    public int bathInsertWarnSmsModel(List<WarnSmsModel> warnSmsModel);

    /**
     * 修改短信模型
     *
     * @param warnSmsModel 短信模型
     * @return 结果
     */
    public int updateWarnSmsModel(WarnSmsModel warnSmsModel);
    /**
    * 通过id集合修改状态
    */
    public Boolean updateByIds(String ids,String disableEnableState);

    /**
     * 批量删除短信模型
     *
     * @param ids 需要删除的短信模型ID
     * @return 结果
     */
    public int deleteWarnSmsModelByIds(String[] ids);

    /**
     * 删除短信模型信息
     *
     * @param id 短信模型ID
     * @return 结果
     */
    public int deleteWarnSmsModelById(String id);



    /**
     * 导入短信模型Excel数据
     *
     * @param warnSmsModelList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public List<WarnSmsModel> importWarnSmsModel(List<WarnSmsModel> warnSmsModelList, Boolean isUpdateSupport, String operName);
}
