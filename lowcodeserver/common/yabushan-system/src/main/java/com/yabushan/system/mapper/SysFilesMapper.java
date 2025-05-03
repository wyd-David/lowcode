package com.yabushan.system.mapper;

import java.util.List;
import com.yabushan.system.domain.SysFiles;

/**
 * 文件 Mapper接口
 *
 * @author yabushan
 * @date 2022-05-16
 */
public interface SysFilesMapper
{
    /**
     * 通过ids查文件信息
     * @param fileIds
     * @return
     */
    public List<SysFiles> selectSysFileListByIds(String[] fileIds);
    /**
     * 查询文件
     *
     * @param fileId 文件 ID
     * @return 文件
     */
    public SysFiles selectSysFilesById(String fileId);

    /**
     * 查询文件 列表
     *
     * @param sysFiles 文件
     * @return 文件 集合
     */
    public List<SysFiles> selectSysFilesList(SysFiles sysFiles);

    /**
     * 新增文件
     *
     * @param sysFiles 文件
     * @return 结果
     */
    public int insertSysFiles(SysFiles sysFiles);

    /**
     * 批量新增文件
     *
     * @param list 文件
     * @return 结果
     */
    public int bathInsertSysFiles(List<SysFiles> list);

    /**
     * 修改文件
     *
     * @param sysFiles 文件
     * @return 结果
     */
    public int updateSysFiles(SysFiles sysFiles);

    /**
     * 删除文件
     *
     * @param fileId 文件 ID
     * @return 结果
     */
    public int deleteSysFilesById(Long fileId);

    /**
     * 批量删除文件
     *
     * @param fileIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysFilesByIds(Long[] fileIds);
}
