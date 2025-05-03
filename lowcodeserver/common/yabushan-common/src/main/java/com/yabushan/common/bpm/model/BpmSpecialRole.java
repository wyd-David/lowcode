package com.yabushan.common.bpm.model;

import java.io.Serializable;

/**
 * @ClassName: BpmSpecialRole
 * @Description: 流程特殊角色配置的枚举
 * @Auth: YvonneLee
 * @Date: 2019/11/1 13:24
 */
public class BpmSpecialRole implements Serializable {
    private String id;
    private String code;
    private String type;
    private String name;
    private String content;



    @Override
    public String toString() {
        return "BpmSpecialRole{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", type=" + type +
                ", name='" + name + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
