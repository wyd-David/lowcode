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
 * 优秀员工申报名单 对象 xuncha_checked_fine_per_detail
 *
 * @author yabushan
 * @date 2022-06-30
 */

@ApiModel(value = "优秀员工申报名单 对象")
public class XunchaCheckedFinePerDetailVo extends BaseEntity
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
            /** 被推荐人ID */
    @Excel(name = "被推荐人ID")
    @ApiModelProperty("被推荐人ID")
    private String userId;
            /** 是否破格 */
    @Excel(name = "是否破格")
    @ApiModelProperty("是否破格")
    private String ifOther;
            /** 推荐方式ID */
    @Excel(name = "推荐方式ID")
    @ApiModelProperty("推荐方式ID")
    private String upTypeId;
            /**  */
    @Excel(name = "")
    @ApiModelProperty("")
    private String applyId;
            /** 分组ID */
    @Excel(name = "分组ID")
    @ApiModelProperty("分组ID")
    private String groupId;
            /** 分组名 */
    @Excel(name = "分组名")
    @ApiModelProperty("分组名")
    private String groupName;
            /** 批次ID */
    @Excel(name = "批次ID")
    @ApiModelProperty("批次ID")
    private String batchId;
            /** 批次名 */
    @Excel(name = "批次名")
    @ApiModelProperty("批次名")
    private String batchName;
            /** 最终得分 */
    @Excel(name = "最终得分")
    @ApiModelProperty("最终得分")
    private Double finalResult;
            /** 推荐方式名 */
    @Excel(name = "推荐方式名")
    @ApiModelProperty("推荐方式名")
    private String upTypeName;
            /** 是否提交申报，0否1是 */
    @Excel(name = "是否提交申报，0否1是")
    @ApiModelProperty("是否提交申报，0否1是")
    private String ifSubmit;
            @Excel(name = "导入状态")
        private String importStatus;

        public String getImportStatus() {
            return importStatus;
        }

        public void setImportStatus(String importStatus) {
            this.importStatus = importStatus;
        }
        /**
        * 禁用状态  0启用,1禁用
        */
        private String disableEnableState;

        private String fineTypeId;
        private String fineTypeName;

        private String delFlag;

            public String getDelFlag() {
                return delFlag;
            }

            public void setDelFlag(String delFlag) {
                this.delFlag = delFlag;
            }

            public String getFineTypeName() {
                return fineTypeName;
            }

            public void setFineTypeName(String fineTypeName) {
                this.fineTypeName = fineTypeName;
            }

            public String getFineTypeId() {
                return fineTypeId;
            }

            public void setFineTypeId(String fineTypeId) {
                this.fineTypeId = fineTypeId;
            }

            public String getDisableEnableState() {
                return disableEnableState;
        }

        public void setDisableEnableState(String disableEnableState) {
                this.disableEnableState = disableEnableState;
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
                            public void setUserId(String userId)
            {
            this.userId = userId;
            }

    public String getUserId()
            {
            return userId;
            }
                            public void setIfOther(String ifOther)
            {
            this.ifOther = ifOther;
            }

    public String getIfOther()
            {
            return ifOther;
            }
                            public void setUpTypeId(String upTypeId)
            {
            this.upTypeId = upTypeId;
            }

    public String getUpTypeId()
            {
            return upTypeId;
            }
                            public void setApplyId(String applyId)
            {
            this.applyId = applyId;
            }

    public String getApplyId()
            {
            return applyId;
            }
                            public void setGroupId(String groupId)
            {
            this.groupId = groupId;
            }

    public String getGroupId()
            {
            return groupId;
            }
                            public void setGroupName(String groupName)
            {
            this.groupName = groupName;
            }

    public String getGroupName()
            {
            return groupName;
            }
                            public void setBatchId(String batchId)
            {
            this.batchId = batchId;
            }

    public String getBatchId()
            {
            return batchId;
            }
                            public void setBatchName(String batchName)
            {
            this.batchName = batchName;
            }

    public String getBatchName()
            {
            return batchName;
            }

            public Double getFinalResult() {
                return finalResult;
            }

            public void setFinalResult(Double finalResult) {
                this.finalResult = finalResult;
            }

            public void setUpTypeName(String upTypeName)
            {
            this.upTypeName = upTypeName;
            }

    public String getUpTypeName()
            {
            return upTypeName;
            }
                            public void setIfSubmit(String ifSubmit)
            {
            this.ifSubmit = ifSubmit;
            }

    public String getIfSubmit()
            {
            return ifSubmit;
            }
    
@Override
public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                                .append("createdBy", getCreatedBy())
                                .append("createdTime", getCreatedTime())
                                .append("updatedBy", getUpdatedBy())
                                .append("updatedTime", getUpdatedTime())
                                .append("id", getId())
                                .append("userId", getUserId())
                                .append("ifOther", getIfOther())
                                .append("upTypeId", getUpTypeId())
                                .append("applyId", getApplyId())
                                .append("groupId", getGroupId())
                                .append("groupName", getGroupName())
                                .append("batchId", getBatchId())
                                .append("batchName", getBatchName())
                                .append("finalResult", getFinalResult())
                                .append("upTypeName", getUpTypeName())
                                .append("ifSubmit", getIfSubmit())
            .toString();
        }
        }
