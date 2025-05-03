package com.yabushan.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yabushan.system.mapper.YmxFileInfoMapper;
import com.yabushan.system.domain.YmxFileInfo;
import com.yabushan.system.service.IYmxFileInfoService;

/**
 * 附件Service业务层处理
 *
 * @author yabushan
 * @date 2021-04-02
 */
@Service
public class YmxFileInfoServiceImpl implements IYmxFileInfoService
{
    @Autowired
    private YmxFileInfoMapper ymxFileInfoMapper;

    /**
     * 查询附件
     *
     * @param fileId 附件ID
     * @return 附件
     */
    @Override
    public YmxFileInfo selectYmxFileInfoById(String fileId)
    {
        return ymxFileInfoMapper.selectYmxFileInfoById(fileId);
    }

    /**
     * 查询附件列表
     *
     * @param ymxFileInfo 附件
     * @return 附件
     */
    @Override
    public List<YmxFileInfo> selectYmxFileInfoList(YmxFileInfo ymxFileInfo)
    {
        return ymxFileInfoMapper.selectYmxFileInfoList(ymxFileInfo);
    }

    /**
     * 新增附件
     *
     * @param ymxFileInfo 附件
     * @return 结果
     */
    @Override
    public int insertYmxFileInfo(YmxFileInfo ymxFileInfo)
    {
        return ymxFileInfoMapper.insertYmxFileInfo(ymxFileInfo);
    }

    /**
     * 修改附件
     *
     * @param ymxFileInfo 附件
     * @return 结果
     */
    @Override
    public int updateYmxFileInfo(YmxFileInfo ymxFileInfo)
    {
        return ymxFileInfoMapper.updateYmxFileInfo(ymxFileInfo);
    }

    /**
     * 批量删除附件
     *
     * @param fileIds 需要删除的附件ID
     * @return 结果
     */
    @Override
    public int deleteYmxFileInfoByIds(String[] fileIds)
    {
        return ymxFileInfoMapper.deleteYmxFileInfoByIds(fileIds);
    }

    /**
     * 删除附件信息
     *
     * @param fileId 附件ID
     * @return 结果
     */
    @Override
    public int deleteYmxFileInfoById(String fileId)
    {
        return ymxFileInfoMapper.deleteYmxFileInfoById(fileId);
    }
}
