package com.yabushan.common.bpm.model;


import java.util.List;

public class  BusinessDataDto{
    private Object entity;
    private List<Object> operatelogList;
    private List<Object> voteList;


    public BusinessDataDto() {
        super();
    }

    public Object getEntity() {
        return entity;
    }

    public void setEntity(Object entity) {
        this.entity = entity;
    }

    public List<Object> getOperatelogList() {
        return operatelogList;
    }

    public void setOperatelogList(List<Object> operatelogList) {
        this.operatelogList = operatelogList;
    }

    public List<Object> getVoteList() {
        return voteList;
    }

    public void setVoteList(List<Object> voteList) {
        this.voteList = voteList;
    }
}
