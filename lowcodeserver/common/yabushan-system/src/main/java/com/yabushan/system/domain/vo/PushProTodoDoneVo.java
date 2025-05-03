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
//这个是类注解,表示该类实例化的对象里，值为null的字段不参与序列化
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PushProTodoDoneVo implements Serializable {
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
}
