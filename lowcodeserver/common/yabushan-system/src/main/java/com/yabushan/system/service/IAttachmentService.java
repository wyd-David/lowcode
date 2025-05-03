package com.yabushan.system.service;

import java.util.List;
import com.yabushan.system.domain.Attachment;

/**
 * 附件信息Service接口
 *
 * @author yabushan
 * @date 2022-05-10
 */
public interface IAttachmentService
{



    /**
    * 清空返回导入数据的集合
    */
    public void clearattachmentInfo();


    /**
     * 查询附件信息
     *
     * @param id 附件信息ID
     * @return 附件信息
     */
    public Attachment selectAttachmentById(String id);

    /**
     * 查询附件信息列表
     *
     * @param attachment 附件信息
     * @return 附件信息集合
     */
    public List<Attachment> selectAttachmentList(Attachment attachment);

    /**
     * 新增附件信息
     *
     * @param attachment 附件信息
     * @return 结果
     */
    public int insertAttachment(Attachment attachment);

    /**
     * 批量新增附件信息
     *
     * @param attachment 附件信息
     * @return 结果
     */
    public int bathInsertAttachment(List<Attachment> attachment);

    /**
     * 修改附件信息
     *
     * @param attachment 附件信息
     * @return 结果
     */
    public int updateAttachment(Attachment attachment);
    /**
    * 通过id集合修改状态
    */
    public Boolean updateByIds(String ids,String disableEnableState);

    /**
     * 批量删除附件信息
     *
     * @param ids 需要删除的附件信息ID
     * @return 结果
     */
    public int deleteAttachmentByIds(String[] ids);

    /**
     * 删除附件信息信息
     *
     * @param id 附件信息ID
     * @return 结果
     */
    public int deleteAttachmentById(String id);


    /**
     * 导入附件信息Excel数据
     *
     * @param attachmentList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public List<Attachment> importAttachment(List<Attachment> attachmentList, Boolean isUpdateSupport, String operName);
}
