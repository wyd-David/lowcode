package com.yabushan.system.mapper;

import java.util.List;
import com.yabushan.system.domain.YmxCommentInfo;

/**
 * 评论Mapper接口
 * 
 * @author yabushan
 * @date 2021-04-02
 */
public interface YmxCommentInfoMapper 
{
    /**
     * 查询评论
     * 
     * @param commentId 评论ID
     * @return 评论
     */
    public YmxCommentInfo selectYmxCommentInfoById(String commentId);

    /**
     * 查询评论列表
     * 
     * @param ymxCommentInfo 评论
     * @return 评论集合
     */
    public List<YmxCommentInfo> selectYmxCommentInfoList(YmxCommentInfo ymxCommentInfo);

    /**
     * 新增评论
     * 
     * @param ymxCommentInfo 评论
     * @return 结果
     */
    public int insertYmxCommentInfo(YmxCommentInfo ymxCommentInfo);

    /**
     * 修改评论
     * 
     * @param ymxCommentInfo 评论
     * @return 结果
     */
    public int updateYmxCommentInfo(YmxCommentInfo ymxCommentInfo);

    /**
     * 删除评论
     * 
     * @param commentId 评论ID
     * @return 结果
     */
    public int deleteYmxCommentInfoById(String commentId);

    /**
     * 批量删除评论
     * 
     * @param commentIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteYmxCommentInfoByIds(String[] commentIds);
}
