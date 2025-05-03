package com.yabushan.form.mapper;

import java.util.List;
import com.yabushan.form.domain.LowFormFiledInfo;

/**
 * 基础表单字段信息Mapper接口
 * 
 * @author yabushan
 * @date 2022-10-10
 */
public interface LowFormFiledInfoMapper 
{
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
    public int bathInsertLowFormFiledInfo(List<LowFormFiledInfo> list);

    /**
     * 修改基础表单字段信息
     * 
     * @param lowFormFiledInfo 基础表单字段信息
     * @return 结果
     */
    public int updateLowFormFiledInfo(LowFormFiledInfo lowFormFiledInfo);

    /**
     * 删除基础表单字段信息
     * 
     * @param filedId 基础表单字段信息ID
     * @return 结果
     */
    public int deleteLowFormFiledInfoById(String filedId);

    /**
     * 批量删除基础表单字段信息
     * 
     * @param filedIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteLowFormFiledInfoByIds(String[] filedIds);
    
    public List<LowFormFiledInfo> selectLowFormFiledInfoByFormUuid(String formUuid);


}
