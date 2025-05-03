package com.yabushan.system.domain;


import cn.afterturn.easypoi.excel.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 消息模板对象 sys_news_template
 *
 * @author 姜森焱
 * @date 2021-09-06
 */

@ApiModel(value = "消息模板对象")
public class SysNewsTemplate extends BaseEntity {
    private static final long serialVersionUID = 1L;


    /**
     * 主键ID
     */
    @ApiModelProperty("主键ID")
    private String id;
    /**
     * 模板内容
     */
    @Excel(name = "模板内容")
    @ApiModelProperty("模板内容")
    private String templateText;
    /**
     * 模板标题
     */
    @Excel(name = "模板标题")
    @ApiModelProperty("模板标题")
    private String templateTitle;
    /**
     * 业务类型
     */
    @Excel(name = "业务类型")
    @ApiModelProperty("业务类型")
    private String businessType;
    /**
     * 应用ID
     */
    @Excel(name = "应用ID")
    @ApiModelProperty("应用ID")
    private String appllyId;
    /**
     * 跳转详情url
     */
    @Excel(name = "跳转详情url")
    @ApiModelProperty("跳转详情url")
    private String templateUrl;

    /**
     * 模板标识
     */
    private String templateIdentification;

    public String getTemplateIdentification() {
        return templateIdentification;
    }

    public void setTemplateIdentification(String templateIdentification) {
        this.templateIdentification = templateIdentification;
    }

    @Excel(name = "导入状态")
    private String importStatus;

    public String getImportStatus() {
        return importStatus;
    }

    public void setImportStatus(String importStatus) {
        this.importStatus = importStatus;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setTemplateText(String templateText) {
        this.templateText = templateText;
    }

    public String getTemplateText() {
        return templateText;
    }

    public void setTemplateTitle(String templateTitle) {
        this.templateTitle = templateTitle;
    }

    public String getTemplateTitle() {
        return templateTitle;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setAppllyId(String appllyId) {
        this.appllyId = appllyId;
    }

    public String getAppllyId() {
        return appllyId;
    }

    public void setTemplateUrl(String templateUrl) {
        this.templateUrl = templateUrl;
    }

    public String getTemplateUrl() {
        return templateUrl;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("templateText", getTemplateText())
                .append("templateTitle", getTemplateTitle())
                .append("businessType", getBusinessType())
                .append("appllyId", getAppllyId())
                .append("templateUrl", getTemplateUrl())
                .append("createTime", getCreateTime())
                .append("createBy", getCreateBy())
                .append("updateTime", getUpdateTime())
                .append("updateBy", getUpdateBy())
                .toString();
    }
}
