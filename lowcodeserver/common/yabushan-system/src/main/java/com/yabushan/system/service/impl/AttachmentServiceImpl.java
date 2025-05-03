package com.yabushan.system.service.impl;

import com.yabushan.common.utils.StringUtils;
import com.yabushan.system.domain.Attachment;
import com.yabushan.system.mapper.AttachmentMapper;
import com.yabushan.system.service.IAttachmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 附件信息Service业务层处理
 *
 * @author yabushan
 * @date 2022-05-10
 */
@Service
public class AttachmentServiceImpl implements IAttachmentService {
    private static final Logger log = LoggerFactory.getLogger(AttachmentServiceImpl.class);
    @Autowired
    private AttachmentMapper attachmentMapper;

    //存放导入所有的成功失败数据
    private static List<Attachment> attachmentInfo = new ArrayList<>();

    /**
     * 清空返回导入数据的集合
     */
    public void clearattachmentInfo() {
        if (StringUtils.isNotNull(attachmentInfo) && attachmentInfo.size() > 0) {
            attachmentInfo.clear();
        }
    }

    /**
     * 查询附件信息
     *
     * @param id 附件信息ID
     * @return 附件信息
     */
    @Override
    public Attachment selectAttachmentById(String id) {
        return attachmentMapper.selectAttachmentById(id);
    }

    /**
     * 查询附件信息列表
     *
     * @param attachment 附件信息
     * @return 附件信息
     */
    @Override
    public List<Attachment> selectAttachmentList(Attachment attachment) {
        return attachmentMapper.selectAttachmentList(attachment);
    }

    /**
     * 新增附件信息
     *
     * @param attachment 附件信息
     * @return 结果
     */
    @Override
    public int insertAttachment(Attachment attachment) {
        attachment.setCreatedDate(new Date());
        //attachment.setCreateBy(SecurityUtils.getNickName());
        return attachmentMapper.insertAttachment(attachment);
    }

    /**
     * 批量新增附件信息
     *
     * @param attachment 附件信息
     * @return 结果
     */
    @Override
    public int bathInsertAttachment(List<Attachment> attachment) {
        return attachmentMapper.bathInsertAttachment(attachment);
    }


    /**
     * 修改附件信息
     *
     * @param attachment 附件信息
     * @return 结果
     */
    @Override
    public int updateAttachment(Attachment attachment) {
        return attachmentMapper.updateAttachment(attachment);
    }

    /**
     * 批量删除附件信息
     *
     * @param ids 需要删除的附件信息ID
     * @return 结果
     */
    @Override
    public int deleteAttachmentByIds(String[] ids) {
        return attachmentMapper.deleteAttachmentByIds(ids);
    }

    /**
     * 删除附件信息信息
     *
     * @param id 附件信息ID
     * @return 结果
     */
    @Override
    public int deleteAttachmentById(String id) {
        return attachmentMapper.deleteAttachmentById(id);
    }

    /**
     * 根据传来的id集合修改禁用启用状态
     *
     * @param ids
     * @param disableEnableState
     * @return
     */
    @Transactional
    @Override
    public Boolean updateByIds(String ids, String disableEnableState) {
        int count = 0;
        String idslist[] = ids.split(",", -1);
        if (StringUtils.isNotNull(idslist) && idslist.length >= 1) {
            for (int i = 0; i < idslist.length; i++) {
                Attachment attachment = attachmentMapper.selectAttachmentById(idslist[i]);
                attachment.setDisableEnableState(disableEnableState);
                int isappinfo = attachmentMapper.updateAttachment(attachment);
                if (isappinfo == 1) {
                    count++;
                }
            }
            if (count == idslist.length) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * 导入附件信息Excel数据
     *
     * @param attachmentList  数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public List<Attachment> importAttachment(List<Attachment> attachmentList, Boolean isUpdateSupport, String operName) {
        for (int i = 0; i < attachmentList.size(); i++) {
            Attachment attachment = attachmentList.get(i);
            try {
                // 验证是否存在这个用户
                Attachment u = attachmentMapper.selectAttachmentById(attachment.getId());
                if (StringUtils.isNull(u)) {
                    attachmentMapper.insertAttachment(attachment);
                    attachment.setImportStatus("导入成功");
                    attachmentInfo.add(attachment);
                } else if (isUpdateSupport) {
                    attachmentMapper.updateAttachment(attachment);
                    attachment.setImportStatus("更新成功");
                    attachmentInfo.add(attachment);
                } else {
                    attachment.setImportStatus("已存在当前数据");
                    attachmentInfo.add(attachment);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                attachment.setImportStatus("数据格式有问题，请认证检查数据！");
                attachmentInfo.add(attachment);
                //如果sql插入数据报错，或者更新报错，都会进行数据的回调，直到excel的数据读取完
                importAttachment(attachmentList.subList(i + 1, attachmentList.size()), isUpdateSupport, operName);
                return attachmentInfo;
            }

        }
        return attachmentInfo;
    }
}
