package com.yabushan.capacity.domain;


import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.afterturn.easypoi.excel.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 文字转声音对象 ngs_text_void_info
 *
 * @author yabushan
 * @date 2024-02-20
 */

@ApiModel(value = "文字转声音对象")
public class NgsTextVoidInfo extends BaseEntity
        {
private static final long serialVersionUID = 1L;


        /** 主键 */
    @ApiModelProperty("主键")
    private String textId;
            /** 标题 */
    @Excel(name = "标题")
    @ApiModelProperty("标题")
    private String textTitle;
            /** 内容 */
    @Excel(name = "内容")
    @ApiModelProperty("内容")
    private String textValue;
            /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, format = "yyyy-MM-dd")
    @ApiModelProperty("创建时间")
    private Date createdTime;
            /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, format = "yyyy-MM-dd")
    @ApiModelProperty("更新时间")
    private Date updatedTime;
            /** 创建人 */
    @Excel(name = "创建人")
    @ApiModelProperty("创建人")
    private String createdBy;
            /** 更新人 */
    @Excel(name = "更新人")
    @ApiModelProperty("更新人")
    private String updatedBy;
            /** 下载次数 */
    @Excel(name = "下载次数")
    @ApiModelProperty("下载次数")
    private Long dowmloadNumber;
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

        public String getDisableEnableState() {
                return disableEnableState;
        }

        public void setDisableEnableState(String disableEnableState) {
                this.disableEnableState = disableEnableState;
        }
                        public void setTextId(String textId)
            {
            this.textId = textId;
            }

    public String getTextId()
            {
            return textId;
            }
                            public void setTextTitle(String textTitle)
            {
            this.textTitle = textTitle;
            }

    public String getTextTitle()
            {
            return textTitle;
            }
                            public void setTextValue(String textValue)
            {
            this.textValue = textValue;
            }

    public String getTextValue()
            {
            return textValue;
            }
                            public void setCreatedTime(Date createdTime)
            {
            this.createdTime = createdTime;
            }

    public Date getCreatedTime()
            {
            return createdTime;
            }
                            public void setUpdatedTime(Date updatedTime)
            {
            this.updatedTime = updatedTime;
            }

    public Date getUpdatedTime()
            {
            return updatedTime;
            }
                            public void setCreatedBy(String createdBy)
            {
            this.createdBy = createdBy;
            }

    public String getCreatedBy()
            {
            return createdBy;
            }
                            public void setUpdatedBy(String updatedBy)
            {
            this.updatedBy = updatedBy;
            }

    public String getUpdatedBy()
            {
            return updatedBy;
            }
                            public void setDowmloadNumber(Long dowmloadNumber)
            {
            this.dowmloadNumber = dowmloadNumber;
            }

    public Long getDowmloadNumber()
            {
            return dowmloadNumber;
            }
    
@Override
public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                                .append("textId", getTextId())
                                .append("textTitle", getTextTitle())
                                .append("textValue", getTextValue())
                                .append("createdTime", getCreatedTime())
                                .append("updatedTime", getUpdatedTime())
                                .append("createdBy", getCreatedBy())
                                .append("updatedBy", getUpdatedBy())
                                .append("dowmloadNumber", getDowmloadNumber())
            .toString();
        }
        }
