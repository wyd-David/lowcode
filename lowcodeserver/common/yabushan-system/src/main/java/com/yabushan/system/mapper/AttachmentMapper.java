package com.yabushan.system.mapper;

import java.util.List;
import com.yabushan.system.domain.Attachment;

/**
 * 附件信息Mapper接口
 * 
 * @author yabushan
 * @date 2022-05-10
 */
public interface AttachmentMapper 
{
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
    public int bathInsertAttachment(List<Attachment> list);

    /**
     * 修改附件信息
     * 
     * @param attachment 附件信息
     * @return 结果
     */
    public int updateAttachment(Attachment attachment);

    /**
     * 删除附件信息
     * 
     * @param id 附件信息ID
     * @return 结果
     */
    public int deleteAttachmentById(String id);

    /**
     * 批量删除附件信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAttachmentByIds(String[] ids);
}
