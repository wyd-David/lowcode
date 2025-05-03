package com.yabushan.system.domain.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * <p>
 * 推送待阅已阅表
 * </p>
 *
 * @author zhoucp
 * @since 2021-04-19
 */
//这个是类注解,表示该类实例化的对象里，值为null的字段不参与序列化
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PushProToreadReadVo implements Serializable {

    @JSONField(ordinal = 1)
    private String toreadId;
    @JSONField(ordinal = 2)
    private String appName;
    @JSONField(ordinal = 3)
    private String owner;
    @JSONField(ordinal = 4)
    private String title;
    @JSONField(ordinal = 5)
    private String url;
    @JSONField(ordinal = 6)
    private String createTime;
    @JSONField(ordinal = 7)
    private String modifyTime;
    @JSONField(ordinal = 8)
    private String type;


    public String getToreadId() {
        return toreadId;
    }

    public void setToreadId(String toreadId) {
        this.toreadId = toreadId;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
