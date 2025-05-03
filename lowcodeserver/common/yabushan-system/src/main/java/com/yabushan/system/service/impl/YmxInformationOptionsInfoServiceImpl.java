package com.yabushan.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yabushan.system.mapper.YmxInformationOptionsInfoMapper;
import com.yabushan.system.domain.YmxInformationOptionsInfo;
import com.yabushan.system.service.IYmxInformationOptionsInfoService;

/**
 * 个人信息选项Service业务层处理
 *
 * @author yabushan
 * @date 2021-04-02
 */
@Service
public class YmxInformationOptionsInfoServiceImpl implements IYmxInformationOptionsInfoService
{
    @Autowired
    private YmxInformationOptionsInfoMapper ymxInformationOptionsInfoMapper;

    /**
     * 查询个人信息选项
     *
     * @param optionsId 个人信息选项ID
     * @return 个人信息选项
     */
    @Override
    public YmxInformationOptionsInfo selectYmxInformationOptionsInfoById(String optionsId)
    {
        return ymxInformationOptionsInfoMapper.selectYmxInformationOptionsInfoById(optionsId);
    }

    /**
     * 查询个人信息选项列表
     *
     * @param ymxInformationOptionsInfo 个人信息选项
     * @return 个人信息选项
     */
    @Override
    public List<YmxInformationOptionsInfo> selectYmxInformationOptionsInfoList(YmxInformationOptionsInfo ymxInformationOptionsInfo)
    {
        return ymxInformationOptionsInfoMapper.selectYmxInformationOptionsInfoList(ymxInformationOptionsInfo);
    }

    /**
     * 新增个人信息选项
     *
     * @param ymxInformationOptionsInfo 个人信息选项
     * @return 结果
     */
    @Override
    public int insertYmxInformationOptionsInfo(YmxInformationOptionsInfo ymxInformationOptionsInfo)
    {
        return ymxInformationOptionsInfoMapper.insertYmxInformationOptionsInfo(ymxInformationOptionsInfo);
    }

    /**
     * 修改个人信息选项
     *
     * @param ymxInformationOptionsInfo 个人信息选项
     * @return 结果
     */
    @Override
    public int updateYmxInformationOptionsInfo(YmxInformationOptionsInfo ymxInformationOptionsInfo)
    {
        return ymxInformationOptionsInfoMapper.updateYmxInformationOptionsInfo(ymxInformationOptionsInfo);
    }

    /**
     * 批量删除个人信息选项
     *
     * @param optionsIds 需要删除的个人信息选项ID
     * @return 结果
     */
    @Override
    public int deleteYmxInformationOptionsInfoByIds(String[] optionsIds)
    {
        return ymxInformationOptionsInfoMapper.deleteYmxInformationOptionsInfoByIds(optionsIds);
    }

    /**
     * 删除个人信息选项信息
     *
     * @param optionsId 个人信息选项ID
     * @return 结果
     */
    @Override
    public int deleteYmxInformationOptionsInfoById(String optionsId)
    {
        return ymxInformationOptionsInfoMapper.deleteYmxInformationOptionsInfoById(optionsId);
    }
}
