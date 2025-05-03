package com.yabushan.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TodoVo {

    /**
     * 待办表主键，已办或已阅时，必须传入
     */
    private String todoId;


    /**
     * 链接
     */
    private String url;


    /**
     * 应用ID
     */
    private String applyId;

    /**
     * 业务主键UUID
     */
    private String businessKey;

    /**
     * 待办标题
     */
    private String todoTitle;

    /**
     * 待办内容
     */
    private String todoText;

    /**
     * 申请人
     */
    private String todoApplicant;

    /**
     * 待办处理人
     */
    private String todoHandler;

    /**
     * 待办处理人名称
     */
    private String todoHandlerName;

    /**
     * 当前待办流程环节名称
     */
    private String todoTpLink;


    public String getTodoId() {
        return todoId;
    }

    public void setTodoId(String todoId) {
        this.todoId = todoId;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getTodoTitle() {
        return todoTitle;
    }

    public void setTodoTitle(String todoTitle) {
        this.todoTitle = todoTitle;
    }

    public String getTodoText() {
        return todoText;
    }

    public void setTodoText(String todoText) {
        this.todoText = todoText;
    }

    public String getTodoApplicant() {
        return todoApplicant;
    }

    public void setTodoApplicant(String todoApplicant) {
        this.todoApplicant = todoApplicant;
    }

    public String getTodoHandler() {
        return todoHandler;
    }

    public void setTodoHandler(String todoHandler) {
        this.todoHandler = todoHandler;
    }

    public String getTodoHandlerName() {
        return todoHandlerName;
    }

    public void setTodoHandlerName(String todoHandlerName) {
        this.todoHandlerName = todoHandlerName;
    }

    public String getTodoTpLink() {
        return todoTpLink;
    }

    public void setTodoTpLink(String todoTpLink) {
        this.todoTpLink = todoTpLink;
    }

    public String getTodoDataType() {
        return todoDataType;
    }

    public void setTodoDataType(String todoDataType) {
        this.todoDataType = todoDataType;
    }

    /**
     * 待办类型
     * 1-待办，2-已办,3-待阅，4-已阅
     */
    private String todoDataType;


    @Override
    public String toString() {
        return "TodoVo{" +
                "todoId='" + todoId + '\'' +
                ", businessKey='" + businessKey + '\'' +
                ", todoTitle='" + todoTitle + '\'' +
                ", todoText='" + todoText + '\'' +
                ", todoApplicant='" + todoApplicant + '\'' +
                ", todoHandler='" + todoHandler + '\'' +
                ", todoHandlerName='" + todoHandlerName + '\'' +
                ", todoTpLink='" + todoTpLink + '\'' +
                ", todoDataType='" + todoDataType + '\'' +
                '}';
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
