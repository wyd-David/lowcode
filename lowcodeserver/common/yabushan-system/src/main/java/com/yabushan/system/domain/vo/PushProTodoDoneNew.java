package com.yabushan.system.domain.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * <p>
 * 推送待办已办表
 * </p>
 *
 * @author zhoucp
 * @since 2021-04-19
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PushProTodoDoneNew implements Serializable {
    @JSONField(ordinal = 10)
    private String id;
    @JSONField(ordinal = 1)
    private String todoId;
    @JSONField(ordinal = 2)
    private String appName;
    @JSONField(ordinal = 3)
    private String owner;
    @JSONField(ordinal = 4)
    private String parentOwner;
    @JSONField(ordinal = 5)
    private String todoTitle;
    @JSONField(ordinal = 6)
    private String url;
    @JSONField(ordinal = 7)
    private String createTime;
    @JSONField(ordinal = 8)
    private String modifyTime;
    @JSONField(ordinal = 9)
    private String type;
    private String todoDataType;//'1-待办，2-已办,3-待阅，4-已阅'
    private String pushStatus;//1:未推送

    public String getTodoId() {
        return todoId;
    }

    public void setTodoId(String todoId) {
        this.todoId = todoId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getParentOwner() {
        return parentOwner;
    }

    public void setParentOwner(String parentOwner) {
        this.parentOwner = parentOwner;
    }

    public String getTodoTitle() {
        return todoTitle;
    }

    public void setTodoTitle(String todoTitle) {
        this.todoTitle = todoTitle;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTodoDataType() {
        return todoDataType;
    }

    public void setTodoDataType(String todoDataType) {
        this.todoDataType = todoDataType;
    }

    public String getPushStatus() {
        return pushStatus;
    }

    public void setPushStatus(String pushStatus) {
        this.pushStatus = pushStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
