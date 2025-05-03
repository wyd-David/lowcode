package com.yabushan.system.service;

import java.util.List;
import com.yabushan.system.domain.YmxFileInfo;

/**
 * 附件Service接口
 * 
 * @author yabushan
 * @date 2021-04-02
 */
public interface IYmxFileInfoService 
{
    /**
     * 查询附件
     * 
     * @param fileId 附件ID
     * @return 附件
     */
    public YmxFileInfo selectYmxFileInfoById(String fileId);

    /**
     * 查询附件列表
     * 
     * @param ymxFileInfo 附件
     * @return 附件集合
     */
    public List<YmxFileInfo> selectYmxFileInfoList(YmxFileInfo ymxFileInfo);

    /**
     * 新增附件
     * 
     * @param ymxFileInfo 附件
     * @return 结果
     */
    public int insertYmxFileInfo(YmxFileInfo ymxFileInfo);

    /**
     * 修改附件
     * 
     * @param ymxFileInfo 附件
     * @return 结果
     */
    public int updateYmxFileInfo(YmxFileInfo ymxFileInfo);

    /**
     * 批量删除附件
     * 
     * @param fileIds 需要删除的附件ID
     * @return 结果
     */
    public int deleteYmxFileInfoByIds(String[] fileIds);

    /**
     * 删除附件信息
     * 
     * @param fileId 附件ID
     * @return 结果
     */
    public int deleteYmxFileInfoById(String fileId);
}
