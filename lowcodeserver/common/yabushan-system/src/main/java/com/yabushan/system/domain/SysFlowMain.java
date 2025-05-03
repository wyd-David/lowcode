package com.yabushan.system.domain;


import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 流程主表对象 sys_flow_main
 *
 * @author CJH
 * @date 2021-09-15
 */

@ApiModel(value = "流程主表对象")
public class SysFlowMain extends BaseEntity {
    private static final long serialVersionUID = 1L;


    /**
     * 主键id
     */
    @ApiModelProperty("主键id")
    private String id;
    /**
     * 流程文档id
     */
    @Excel(name = "流程文档id")
    @ApiModelProperty("流程文档id")
    private String docId;
    /**
     * 业务表主键id
     */
    @Excel(name = "业务表主键id")
    @ApiModelProperty("业务表主键id")
    private String mainId;
    /**
     * 当前节点
     */
    @Excel(name = "当前节点")
    @ApiModelProperty("当前节点")
    private String activityId;
    /**
     * 当前用户
     */
    @Excel(name = "当前用户")
    @ApiModelProperty("当前用户")
    private String currentDealUser;
    /**
     * 当前节点名称
     */
    @Excel(name = "当前节点名称")
    @ApiModelProperty("当前节点名称")
    private String currentActivity;
    @Excel(name = "导入状态")
    private String importStatus;

    /** 流程编号*/
    private String service;

    private String CreateUser;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public String getCreateUser() {
        return CreateUser;
    }

    public void setCreateUser(String createUser) {
        CreateUser = createUser;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCurrentDealUser() {
        return currentDealUser;
    }

    public void setCurrentDealUser(String currentDealUser) {
        this.currentDealUser = currentDealUser;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
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

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getDocId() {
        return docId;
    }

    public void setMainId(String mainId) {
        this.mainId = mainId;
    }

    public String getMainId() {
        return mainId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setCurrentActivity(String currentActivity) {
        this.currentActivity = currentActivity;
    }

    public String getCurrentActivity() {
        return currentActivity;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("docId", getDocId())
                .append("mainId", getMainId())
                .append("activityId", getActivityId())
                .append("currentDealUser", getCurrentDealUser())
                .append("currentActivity", getCurrentActivity())
                .toString();
    }
}
