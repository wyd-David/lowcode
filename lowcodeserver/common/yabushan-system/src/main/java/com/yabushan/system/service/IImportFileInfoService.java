package com.yabushan.system.service;

import com.yabushan.system.domain.ImportFileInfo;

import java.util.List;

/**
 * 文件上传记录Service接口
 *
 * @author 姜森焱
 * @date 2021-09-08
 */
public interface IImportFileInfoService
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
    * 通过id集合修改状态
    */
    public void updateByIds(List<String> ids, String disableEnableState);

    /**
     * 批量删除文件上传记录
     *
     * @param ids 需要删除的文件上传记录ID
     * @return 结果
     */
    public int deleteImportFileInfoByIds(Long[] ids);

    /**
     * 删除文件上传记录信息
     *
     * @param id 文件上传记录ID
     * @return 结果
     */
    public int deleteImportFileInfoById(Long id);


    /**
     * 导入文件上传记录Excel数据
     *
     * @param importFileInfoList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public List<ImportFileInfo> importImportFileInfo(List<ImportFileInfo> importFileInfoList, Boolean isUpdateSupport, String operName);
}
