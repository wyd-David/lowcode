package com.yabushan.system.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yabushan.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 通知公告表 sys_notice
 *
 * @author ruoyi
 */
@ApiModel(value = "通知公告对象")
public class SysNotice extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 公告ID
     */
    private Long noticeId;

    /**
     * 公告标题
     */
    private String noticeTitle;

    /**
     * 公告类型（1通知 2公告）
     */
    private String noticeType;

    /**
     * 公告内容
     */
    private String noticeContent;

    /**
     * 公告状态（0正常 1关闭）
     */
    private String status;
    /**
     * 通知开始时间
     */

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "通知开始时间", width = 30, databaseFormat = "yyyy-MM-dd")
    @ApiModelProperty("通知开始时间")
    private Date noticeStartDate;

    /**
     * 通知结束时间
     */

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "通知结束时间", width = 30, databaseFormat = "yyyy-MM-dd")

    @ApiModelProperty("通知结束时间")
    private Date noticeEndDate;

    /**
     * 通知人，可以是个人，也可以是多个部门
     */

    @Excel(name = "通知人，可以是个人，也可以是多个部门")

    @ApiModelProperty("通知人，可以是个人，也可以是多个部门")
    private String noticePeople;

    /**
     * 通知业务类别
     */
    @Excel(name = "通知业务类别")
    @ApiModelProperty("通知业务类别")
    private String noticeBusinessType;

    /**
     * 通知人类型，0：个人，1：部门，2：公司
     */
    @Excel(name = "通知人类型，0：个人，1：部门，2：公司")

    @ApiModelProperty("通知人类型，0：个人，1：部门，2：公司")
    private String noticePeopleType;

    @Excel(name = "导入状态")
    private String importStatus;

    public String getImportStatus() {
        return importStatus;
    }

    public void setImportStatus(String importStatus) {
        this.importStatus = importStatus;
    }

    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }

    public Long getNoticeId() {
        return noticeId;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
    }

    public String getNoticeType() {
        return noticeType;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setNoticeStartDate(Date noticeStartDate) {
        this.noticeStartDate = noticeStartDate;
    }

    public Date getNoticeStartDate() {
        return noticeStartDate;
    }

    public void setNoticeEndDate(Date noticeEndDate) {
        this.noticeEndDate = noticeEndDate;
    }

    public Date getNoticeEndDate() {
        return noticeEndDate;
    }

    public void setNoticePeople(String noticePeople) {
        this.noticePeople = noticePeople;
    }

    public String getNoticePeople() {
        return noticePeople;
    }

    public void setNoticeBusinessType(String noticeBusinessType) {
        this.noticeBusinessType = noticeBusinessType;
    }

    public String getNoticeBusinessType() {
        return noticeBusinessType;
    }

    public void setNoticePeopleType(String noticePeopleType) {
        this.noticePeopleType = noticePeopleType;
    }

    public String getNoticePeopleType() {
        return noticePeopleType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("noticeId", getNoticeId())
                .append("noticeTitle", getNoticeTitle())
                .append("noticeType", getNoticeType())
                .append("noticeContent", getNoticeContent())
                .append("status", getStatus())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .append("noticeStartDate", getNoticeStartDate())
                .append("noticeEndDate", getNoticeEndDate())
                .append("noticePeople", getNoticePeople())
                .append("noticeBusinessType", getNoticeBusinessType())
                .append("noticePeopleType", getNoticePeopleType())
                .toString();
    }
}
