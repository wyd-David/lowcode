package com.yabushan.quartz.domain;


import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yabushan.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 巡察员最终得分结果 对象 xuncha_checked_pro_result
 *
 * @author LeeHW
 * @date 2022-05-18
 */

@ApiModel(value = "巡察员最终得分结果 对象")
public class XunchaCheckedProResultVo extends BaseEntity
        {
private static final long serialVersionUID = 1L;


        /** 创建人 */
    @Excel(name = "创建人")
    @ApiModelProperty("创建人")
    private String createdBy;
            /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, format = "yyyy-MM-dd")
    @ApiModelProperty("创建时间")
    private Date createdTime;
            /** 更新人 */
    @Excel(name = "更新人")
    @ApiModelProperty("更新人")
    private String updatedBy;
            /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, format = "yyyy-MM-dd")
    @ApiModelProperty("更新时间")
    private Date updatedTime;
            /** ID */
    @ApiModelProperty("ID")
    private String id;
            /** 被评分人ID */
    @Excel(name = "被评分人ID")
    @ApiModelProperty("被评分人ID")
    private String toUserId;
            /** 被评分人姓名 */
    @Excel(name = "被评分人姓名")
    @ApiModelProperty("被评分人姓名")
    private String toUserName;
            /** 分数 :最终得分 */
    @Excel(name = "分数 :最终得分")
    @ApiModelProperty("分数 :最终得分")
    private Double scope;
            /** 评级 */
    @Excel(name = "评级")
    @ApiModelProperty("评级")
    private String levelId;
            /** 分组ID */
    @Excel(name = "分组ID")
    @ApiModelProperty("分组ID")
    private String groupId;
    private String toUserTypeId;
    private String scopeType;
    private String year;
    private String batchId;

            public String getToUserTypeId() {
                return toUserTypeId;
            }

            public void setToUserTypeId(String toUserTypeId) {
                this.toUserTypeId = toUserTypeId;
            }

            public String getScopeType() {
                return scopeType;
            }

            public void setScopeType(String scopeType) {
                this.scopeType = scopeType;
            }

            public String getYear() {
                return year;
            }

            public void setYear(String year) {
                this.year = year;
            }

            public String getBatchId() {
                return batchId;
            }

            public void setBatchId(String batchId) {
                this.batchId = batchId;
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
                            public void setUpdatedBy(String updatedBy)
            {
            this.updatedBy = updatedBy;
            }

    public String getUpdatedBy()
            {
            return updatedBy;
            }
                            public void setUpdatedTime(Date updatedTime)
            {
            this.updatedTime = updatedTime;
            }

    public Date getUpdatedTime()
            {
            return updatedTime;
            }
                            public void setId(String id)
            {
            this.id = id;
            }

    public String getId()
            {
            return id;
            }
                            public void setToUserId(String toUserId)
            {
            this.toUserId = toUserId;
            }

    public String getToUserId()
            {
            return toUserId;
            }
                            public void setToUserName(String toUserName)
            {
            this.toUserName = toUserName;
            }

    public String getToUserName()
            {
            return toUserName;
            }
                            public void setScope(Double scope)
            {
            this.scope = scope;
            }

    public Double getScope()
            {
            return scope;
            }
                            public void setLevelId(String levelId)
            {
            this.levelId = levelId;
            }

    public String getLevelId()
            {
            return levelId;
            }
                            public void setGroupId(String groupId)
            {
            this.groupId = groupId;
            }

    public String getGroupId()
            {
            return groupId;
            }
    
@Override
public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                                .append("createdBy", getCreatedBy())
                                .append("createdTime", getCreatedTime())
                                .append("updatedBy", getUpdatedBy())
                                .append("updatedTime", getUpdatedTime())
                                .append("id", getId())
                                .append("toUserId", getToUserId())
                                .append("toUserName", getToUserName())
                                .append("scope", getScope())
                                .append("levelId", getLevelId())
                                .append("groupId", getGroupId())
            .toString();
        }
        }
