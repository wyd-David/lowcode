package com.yabushan.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yabushan.system.mapper.YmxCommentInfoMapper;
import com.yabushan.system.domain.YmxCommentInfo;
import com.yabushan.system.service.IYmxCommentInfoService;

/**
 * 评论Service业务层处理
 *
 * @author yabushan
 * @date 2021-04-02
 */
@Service
public class YmxCommentInfoServiceImpl implements IYmxCommentInfoService
{
    @Autowired
    private YmxCommentInfoMapper ymxCommentInfoMapper;

    /**
     * 查询评论
     *
     * @param commentId 评论ID
     * @return 评论
     */
    @Override
    public YmxCommentInfo selectYmxCommentInfoById(String commentId)
    {
        return ymxCommentInfoMapper.selectYmxCommentInfoById(commentId);
    }

    /**
     * 查询评论列表
     *
     * @param ymxCommentInfo 评论
     * @return 评论
     */
    @Override
    public List<YmxCommentInfo> selectYmxCommentInfoList(YmxCommentInfo ymxCommentInfo)
    {
        return ymxCommentInfoMapper.selectYmxCommentInfoList(ymxCommentInfo);
    }

    /**
     * 新增评论
     *
     * @param ymxCommentInfo 评论
     * @return 结果
     */
    @Override
    public int insertYmxCommentInfo(YmxCommentInfo ymxCommentInfo)
    {
        return ymxCommentInfoMapper.insertYmxCommentInfo(ymxCommentInfo);
    }

    /**
     * 修改评论
     *
     * @param ymxCommentInfo 评论
     * @return 结果
     */
    @Override
    public int updateYmxCommentInfo(YmxCommentInfo ymxCommentInfo)
    {
        return ymxCommentInfoMapper.updateYmxCommentInfo(ymxCommentInfo);
    }

    /**
     * 批量删除评论
     *
     * @param commentIds 需要删除的评论ID
     * @return 结果
     */
    @Override
    public int deleteYmxCommentInfoByIds(String[] commentIds)
    {
        return ymxCommentInfoMapper.deleteYmxCommentInfoByIds(commentIds);
    }

    /**
     * 删除评论信息
     *
     * @param commentId 评论ID
     * @return 结果
     */
    @Override
    public int deleteYmxCommentInfoById(String commentId)
    {
        return ymxCommentInfoMapper.deleteYmxCommentInfoById(commentId);
    }
}
