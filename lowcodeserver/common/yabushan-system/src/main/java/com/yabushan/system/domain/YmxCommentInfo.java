package com.yabushan.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yabushan.common.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 评论对象 ymx_comment_info
 *
 * @author yabushan
 * @date 2021-04-02
 */
public class YmxCommentInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 评论表主键 */
    private String commentId;

    /** 订单表主键 */
    @Excel(name = "订单表主键")
    private String orderId;

    /** 评论图片 */
    @Excel(name = "评论图片")
    private String commentImage;

    /** 评论星级 */
    @Excel(name = "评论星级")
    private String commentStarRating;

    /** 感谢语 */
    @Excel(name = "感谢语")
    private String commentThankYou;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createdBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdTime;

    /** 更新人 */
    @Excel(name = "更新人")
    private String lastUpdatedBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastUpdatedTime;

    public void setCommentId(String commentId)
    {
        this.commentId = commentId;
    }

    public String getCommentId()
    {
        return commentId;
    }
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }

    public String getOrderId()
    {
        return orderId;
    }
    public void setCommentImage(String commentImage)
    {
        this.commentImage = commentImage;
    }

    public String getCommentImage()
    {
        return commentImage;
    }
    public void setCommentStarRating(String commentStarRating)
    {
        this.commentStarRating = commentStarRating;
    }

    public String getCommentStarRating()
    {
        return commentStarRating;
    }
    public void setCommentThankYou(String commentThankYou)
    {
        this.commentThankYou = commentThankYou;
    }

    public String getCommentThankYou()
    {
        return commentThankYou;
    }
    public void setCreatedBy(String createdBy)
    {
        this.createdBy = createdBy;
    }

    public String getCreatedBy()
    {
        return createdBy;
    }
    public void setCreatedTime(Date createdTime)
    {
        this.createdTime = createdTime;
    }

    public Date getCreatedTime()
    {
        return createdTime;
    }
    public void setLastUpdatedBy(String lastUpdatedBy)
    {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public String getLastUpdatedBy()
    {
        return lastUpdatedBy;
    }
    public void setLastUpdatedTime(Date lastUpdatedTime)
    {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public Date getLastUpdatedTime()
    {
        return lastUpdatedTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("commentId", getCommentId())
            .append("orderId", getOrderId())
            .append("commentImage", getCommentImage())
            .append("commentStarRating", getCommentStarRating())
            .append("commentThankYou", getCommentThankYou())
            .append("createdBy", getCreatedBy())
            .append("createdTime", getCreatedTime())
            .append("lastUpdatedBy", getLastUpdatedBy())
            .append("lastUpdatedTime", getLastUpdatedTime())
            .toString();
    }
}
