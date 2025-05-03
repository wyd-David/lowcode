package com.yabushan.system.mapper;

import com.yabushan.system.domain.ImportFileInfo;

import java.util.List;

/**
 * 文件上传记录Mapper接口
 *
 * @author 姜森焱
 * @date 2021-09-08
 */
public interface ImportFileInfoMapper
{
    /**
     * 查询文件上传记录
     *
     * @param id 文件上传记录ID
     * @return 文件上传记录
     */
    public ImportFileInfo selectImportFileInfoById(Long id);

    /**
     * 查询文件上传记录列表
     *
     * @param importFileInfo 文件上传记录
     * @return 文件上传记录集合
     */
    public List<ImportFileInfo> selectImportFileInfoList(ImportFileInfo importFileInfo);

    /**
     * 新增文件上传记录
     *
     * @param importFileInfo 文件上传记录
     * @return 结果
     */
    public int insertImportFileInfo(ImportFileInfo importFileInfo);

    /**
     * 修改文件上传记录
     *
     * @param importFileInfo 文件上传记录
     * @return 结果
     */
    public int updateImportFileInfo(ImportFileInfo importFileInfo);

    /**
     * 删除文件上传记录
     *
     * @param id 文件上传记录ID
     * @return 结果
     */
    public int deleteImportFileInfoById(Long id);

    /**
     * 批量删除文件上传记录
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteImportFileInfoByIds(Long[] ids);

    /**
     * 根据deleteByBusinessKey删除文件数据
     * @param businessKey
     * @return
     */
    public int deleteByBusinessKey(String businessKey);
}
