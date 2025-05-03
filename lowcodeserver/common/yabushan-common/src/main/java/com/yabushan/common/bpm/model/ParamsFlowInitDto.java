package com.yabushan.common.bpm.model;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: ParamsFlowInitDto
 * @Description: TODO
 * @Auth: YvonneLee
 * @Date: 2019/11/26 17:10
 */
public class ParamsFlowInitDto  implements Serializable {
    private String currentUser;
    private String taskId;
    private String title;
    private String flowContent;
    private String nextActivityId;
    private String nextUserInfo;
    private String approveResult;
    private String approveNote;
    private String targetUser;
    private String toUserIds;
    private String messageTitle;
    private String message;
//    private String documentId;
    private String childDocId;
    private String nextSpecialUserInfo;
    private String prodCode;
    private String currentActivityId="";
    private String selectFileIds;//选择上个流程的附件 参考首次使用产品委员会 用于流程附件表
    private String specialFileIds;//特殊的附件需要在子类里实现
    private Map<String, List<File>> attachFile;//

    public String getChildDocId() {
        return childDocId;
    }

    public void setChildDocId(String childDocId) {
        this.childDocId = childDocId;
    }

    public String getProdCode() {
        return prodCode;
    }

    public void setProdCode(String prodCode) {
        this.prodCode = prodCode;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNextActivityId() {
        return nextActivityId;
    }

    public void setNextActivityId(String nextActivityId) {
        this.nextActivityId = nextActivityId;
    }

    public String getNextUserInfo() {
        return nextUserInfo;
    }

    public void setNextUserInfo(String nextUserInfo) {
        this.nextUserInfo = nextUserInfo;
    }

    public String getApproveResult() {
        return approveResult;
    }

    public void setApproveResult(String approveResult) {
        this.approveResult = approveResult;
    }

    public String getApproveNote() {
        return approveNote;
    }

    public void setApproveNote(String approveNote) {
        this.approveNote = approveNote;
    }

    public String getTargetUser() {
        return targetUser;
    }

    public void setTargetUser(String targetUser) {
        this.targetUser = targetUser;
    }

    public String getToUserIds() {
        return toUserIds;
    }

    public void setToUserIds(String toUserIds) {
        this.toUserIds = toUserIds;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

//    public String getDocumentId() {
//        return documentId;
//    }
//
//    public void setDocumentId(String documentId) {
//        this.documentId = documentId;
//    }

    public String getCurrentActivityId() {
        return currentActivityId;
    }

    public void setCurrentActivityId(String currentActivityId) {
        this.currentActivityId = currentActivityId;
    }

    public String getNextSpecialUserInfo() {
        return nextSpecialUserInfo;
    }

    public void setNextSpecialUserInfo(String nextSpecialUserInfo) {
        this.nextSpecialUserInfo = nextSpecialUserInfo;
    }

    public String getFlowContent() {
        return flowContent;
    }

    public void setFlowContent(String flowContent) {
        this.flowContent = flowContent;
    }

    public String getSelectFileIds() {
        return selectFileIds;
    }

    public void setSelectFileIds(String selectFileIds) {
        this.selectFileIds = selectFileIds;
    }

    public String getSpecialFileIds() {
        return specialFileIds;
    }

    public Map<String, List<File>> getAttachFile() {
        return attachFile;
    }

    public void setAttachFile(Map<String, List<File>> attachFile) {
        this.attachFile = attachFile;
    }

    public void setSpecialFileIds(String specialFileIds) {
        this.specialFileIds = specialFileIds;
    }


}
