package com.yabushan.system.domain;


import cn.afterturn.easypoi.excel.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 日历配置对象 sys_config_calendar
 *
 * @author 姜森焱
 * @date 2021-09-08
 */

@ApiModel(value = "日历配置对象")
public class SysConfigCalendar extends BaseEntity {
    private static final long serialVersionUID = 1L;


    /**
     * $column.columnComment
     */
    @ApiModelProperty("$column.columnComment")
    private String id;
    /**
     * 节日日期
     */
    @Excel(name = "节日日期", width = 30)
    @ApiModelProperty("节日日期")
    private String festivalTime;
    /**
     * 日期类型,0:上班,1:休假
     */
    @Excel(name = "日期类型,0:上班,1:休假")
    @ApiModelProperty("日期类型,0:上班,1:休假")
    private String calendarType;
    /**
     * 日期描述
     */
    @Excel(name = "日期描述")
    @ApiModelProperty("日期描述")
    private String remarks;
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

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setFestivalTime(String festivalTime) {
        this.festivalTime = festivalTime;
    }

    public String getFestivalTime() {
        return festivalTime;
    }

    public void setCalendarType(String calendarType) {
        this.calendarType = calendarType;
    }

    public String getCalendarType() {
        return calendarType;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRemarks() {
        return remarks;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("festivalTime", getFestivalTime())
                .append("calendarType", getCalendarType())
                .append("remarks", getRemarks())
                .append("createTime", getCreateTime())
                .append("createBy", getCreateBy())
                .append("updateTime", getUpdateTime())
                .append("updateBy", getUpdateBy())
                .toString();
    }
}
