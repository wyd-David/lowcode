package com.yabushan.quartz.domain;


import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yabushan.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 巡察考核发起记录对象 xuncha_checked_apply_info
 *
 * @author LeeHW
 * @date 2022-05-18
 */

@ApiModel(value = "巡察考核发起记录对象")
public class XunchaCheckedApplyInfoVo extends BaseEntity
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
                    /** 考核类别 (1、巡察组；2、巡察员) */
            @Excel(name = "考核类别 (1、巡察组；2、巡察员)")
            @ApiModelProperty("考核类别 (1、巡察组；2、巡察员)")
            private String type;
                    /** 审批状态 1、审批中；2、审批通过；3、退回；4、不同意 */
            @Excel(name = "审批状态 1、审批中；2、审批通过；3、退回；4、不同意")
            @ApiModelProperty("审批状态 1、审批中；2、审批通过；3、退回；4、不同意")
            private String approveStatus;
                    /** 记录状态 1、进行中；2、已结束 */
            @Excel(name = "记录状态 1、进行中；2、已结束")
            @ApiModelProperty("记录状态 1、进行中；2、已结束")
            private String applyStatus;
                    /** 标题 */
            @Excel(name = "标题")
            @ApiModelProperty("标题")
            private String title;
                    /** 年份 */
            @Excel(name = "年份")
            @ApiModelProperty("年份")
            private String year;
                    /** 工单号 */
            @Excel(name = "工单号")
            @ApiModelProperty("工单号")
            private String applyCode;
                    /** 批次ID */
            @ApiModelProperty("批次ID")
            private String batchId;
            @Excel(name = "导入状态")
            private String importStatus;

            @Excel(name="巡察组")
            private String groupNames;
            //是否删除，0否1是
            private String isDel;
            //批次名
            private String batchName;
            private String groupIds;
            private String groupLeaderNames;

            public String getGroupLeaderNames() {
                return groupLeaderNames;
            }

            public void setGroupLeaderNames(String groupLeaderNames) {
                this.groupLeaderNames = groupLeaderNames;
            }

            /*申请单对应选择的分组详情*/
            private List<Map<String,Object>> list;

            public List<Map<String, Object>> getList() {
                return list;
            }

            public void setList(List<Map<String, Object>> list) {
                this.list = list;
            }

            public String getGroupIds() {
                return groupIds;
            }

            public void setGroupIds(String groupIds) {
                this.groupIds = groupIds;
            }

            public String getBatchName() {
                return batchName;
            }

            public void setBatchName(String batchName) {
                this.batchName = batchName;
            }

            public String getIsDel() {
                return isDel;
            }

            public void setIsDel(String isDel) {
                this.isDel = isDel;
            }

            public String getGroupNames() {
                return groupNames;
            }

            public void setGroupNames(String groupNames) {
                this.groupNames = groupNames;
            }

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
                                    public void setType(String type)
                    {
                    this.type = type;
                    }

            public String getType()
                    {
                    return type;
                    }
                                    public void setApproveStatus(String approveStatus)
                    {
                    this.approveStatus = approveStatus;
                    }

            public String getApproveStatus()
                    {
                    return approveStatus;
                    }
                                    public void setApplyStatus(String applyStatus)
                    {
                    this.applyStatus = applyStatus;
                    }

            public String getApplyStatus()
                    {
                    return applyStatus;
                    }
                                    public void setTitle(String title)
                    {
                    this.title = title;
                    }

            public String getTitle()
                    {
                    return title;
                    }
                                    public void setYear(String year)
                    {
                    this.year = year;
                    }

            public String getYear()
                    {
                    return year;
                    }
                                    public void setApplyCode(String applyCode)
                    {
                    this.applyCode = applyCode;
                    }

            public String getApplyCode()
                    {
                    return applyCode;
                    }
                                    public void setBatchId(String batchId)
                    {
                    this.batchId = batchId;
                    }

            public String getBatchId()
                    {
                    return batchId;
                    }
    
            @Override
            public String toString() {
                    return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                                            .append("createdBy", getCreatedBy())
                                            .append("createdTime", getCreatedTime())
                                            .append("updatedBy", getUpdatedBy())
                                            .append("updatedTime", getUpdatedTime())
                                            .append("id", getId())
                                            .append("type", getType())
                                            .append("approveStatus", getApproveStatus())
                                            .append("applyStatus", getApplyStatus())
                                            .append("title", getTitle())
                                            .append("year", getYear())
                                            .append("applyCode", getApplyCode())
                                            .append("batchId", getBatchId())
                        .toString();
                    }
        }
