package com.yabushan.system.service;

import java.util.List;
import com.yabushan.system.domain.YmxInformationOptionsInfo;

/**
 * 个人信息选项Service接口
 * 
 * @author yabushan
 * @date 2021-04-02
 */
public interface IYmxInformationOptionsInfoService 
{
    /**
     * 查询个人信息选项
     * 
     * @param optionsId 个人信息选项ID
     * @return 个人信息选项
     */
    public YmxInformationOptionsInfo selectYmxInformationOptionsInfoById(String optionsId);

    /**
     * 查询个人信息选项列表
     * 
     * @param ymxInformationOptionsInfo 个人信息选项
     * @return 个人信息选项集合
     */
    public List<YmxInformationOptionsInfo> selectYmxInformationOptionsInfoList(YmxInformationOptionsInfo ymxInformationOptionsInfo);

    /**
     * 新增个人信息选项
     * 
     * @param ymxInformationOptionsInfo 个人信息选项
     * @return 结果
     */
    public int insertYmxInformationOptionsInfo(YmxInformationOptionsInfo ymxInformationOptionsInfo);

    /**
     * 修改个人信息选项
     * 
     * @param ymxInformationOptionsInfo 个人信息选项
     * @return 结果
     */
    public int updateYmxInformationOptionsInfo(YmxInformationOptionsInfo ymxInformationOptionsInfo);

    /**
     * 批量删除个人信息选项
     * 
     * @param optionsIds 需要删除的个人信息选项ID
     * @return 结果
     */
    public int deleteYmxInformationOptionsInfoByIds(String[] optionsIds);

    /**
     * 删除个人信息选项信息
     * 
     * @param optionsId 个人信息选项ID
     * @return 结果
     */
    public int deleteYmxInformationOptionsInfoById(String optionsId);
}
