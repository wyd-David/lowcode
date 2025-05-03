package com.yabushan.form.service;

import java.util.List;
import com.yabushan.form.domain.LowFormFiledInfo;

/**
 * 基础表单字段信息Service接口
 *
 * @author yabushan
 * @date 2022-10-10
 */
public interface ILowFormFiledInfoService
{



    /**
    * 清空返回导入数据的集合
    */
    public void clearlowFormFiledInfoInfo();


    /**
     * 查询基础表单字段信息
     *
     * @param filedId 基础表单字段信息ID
     * @return 基础表单字段信息
     */
    public LowFormFiledInfo selectLowFormFiledInfoById(String filedId);

    /**
     * 查询基础表单字段信息列表
     *
     * @param lowFormFiledInfo 基础表单字段信息
     * @return 基础表单字段信息集合
     */
    public List<LowFormFiledInfo> selectLowFormFiledInfoList(LowFormFiledInfo lowFormFiledInfo);

    /**
     * 新增基础表单字段信息
     *
     * @param lowFormFiledInfo 基础表单字段信息
     * @return 结果
     */
    public int insertLowFormFiledInfo(LowFormFiledInfo lowFormFiledInfo);

    /**
     * 批量新增基础表单字段信息
     *
     * @param lowFormFiledInfo 基础表单字段信息
     * @return 结果
     */
    public int bathInsertLowFormFiledInfo(List<LowFormFiledInfo> lowFormFiledInfo);

    /**
     * 修改基础表单字段信息
     *
     * @param lowFormFiledInfo 基础表单字段信息
     * @return 结果
     */
    public int updateLowFormFiledInfo(LowFormFiledInfo lowFormFiledInfo);
    /**
    * 通过id集合修改状态
    */
    public Boolean updateByIds(String ids,String disableEnableState);

    /**
     * 批量删除基础表单字段信息
     *
     * @param filedIds 需要删除的基础表单字段信息ID
     * @return 结果
     */
    public int deleteLowFormFiledInfoByIds(String[] filedIds);

    /**
     * 删除基础表单字段信息信息
     *
     * @param filedId 基础表单字段信息ID
     * @return 结果
     */
    public int deleteLowFormFiledInfoById(String filedId);

    /**
     * 导入基础表单字段信息Excel数据
     *
     * @param lowFormFiledInfoList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public List<LowFormFiledInfo> importLowFormFiledInfo(List<LowFormFiledInfo> lowFormFiledInfoList, Boolean isUpdateSupport, String operName);

    public  List<LowFormFiledInfo>   selectLowFormFiledInfoByFormUuid(String formUuid);
}
