package com.yabushan.common.bpm.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.beust.jcommander.internal.Lists;
import com.yabushan.common.bpm.utils.ToolUtil;

public class PieDocTask {
    private BusinessDataDto businessDataDto;
    private String currentUser;
    private boolean isBpmShowData = false;//bpm流程可以查看
    private PieAcl acl;
    private PieAction action;
    private JSONObject currentActivity;
    private PieDocument document;
    private String downloadAll;
    private JSONObject innovation;
    private JSONObject lock;
    private JSONArray nextActivities;
    private JSONObject records;
    private JSONObject service;
    private PieTask userTask;
    //非openBPM
    private List<PieTask> tasks;
    //非openBPM
    //打开流程的传阅
    private JSONObject shareRecord;
    private PieDocumentData documentData;
    //不是 BPM参数开始
    //下一步操作人的角色
    private List<BpmSpecialRole> nextNodeRoles;
    //根据角色的人员
    private List<BpmSpecialUserInfo> nextNodeUsersByRole;
    //被选中的角色
    private String selectedNextNodeRoles;
    //下一步处理人
    private List<Map<String,Object>> nextUsers;
    //下一步节点
    private String nextActivitieId;
    //是否显示特殊内容(默认为true)
    private Boolean showSpecialContent=true;
    //托管行评分显示
    private Boolean scoreSetting = false;
    //显示投资经理
    private Boolean ifShowTzManage = true;
    //流程的一些特色值 titleTips 提交提示
    private Map<String,Object> otherV= new HashMap<>();
    //不是 BPM参数结束
    public Boolean getIfShowTzManage() {
        return ifShowTzManage;
    }

    public void setIfShowTzManage(Boolean ifShowTzManage) {
        this.ifShowTzManage = ifShowTzManage;
    }


    public Boolean getScoreSetting() {
        return scoreSetting;
    }

    public void setScoreSetting(Boolean scoreSetting) {
        this.scoreSetting = scoreSetting;
    }

    public Boolean getShowSpecialContent() {
        return showSpecialContent;
    }

    public void setShowSpecialContent(Boolean showSpecialContent) {
        this.showSpecialContent = showSpecialContent;
    }

    public String getNextActivitieId() {
        return nextActivitieId ;
    }

    public void setNextActivitieId(String nextActivitieId) {
        this.nextActivitieId = nextActivitieId== null? ToolUtil.dealNextActivities(nextActivities):nextActivitieId.trim();
    }

    public List<Map<String, Object>> getNextUsers() {
        return nextUsers;
    }



    public void setNextUsers(List<Map<String, Object>> nextUsers) {
        List<Map<String, Object>> objects = Lists.newArrayList();

        if(nextUsers==null ||nextUsers.isEmpty()){
            this.nextUsers=objects;
        }
        this.nextUsers = nextUsers;
    }

    public PieDocument getDocument() {
        return document;
    }

    public void setDocument(PieDocument document) {
        this.document = document;
    }

    public List<PieTask> getTasks() {
        return tasks;
    }

    public void setTasks(List<PieTask> tasks) {
        this.tasks = tasks;
    }

    public PieDocTask() {
        // TODO Auto-generated constructor stub
    }

    public PieDocumentData getDocumentData() {
        return documentData;
    }

    public void setDocumentData(PieDocumentData documentData) {
        this.documentData = documentData;
    }

    public PieAcl getAcl() {
        return acl;
    }

    public void setAcl(PieAcl acl) {
        this.acl = acl;
    }

    public PieAction getAction() {
        return action;
    }

    public void setAction(PieAction action) {
        this.action = action;
    }

    public JSONObject getCurrentActivity() {
        return currentActivity;
    }

    public void setCurrentActivity(JSONObject currentActivity) {
        this.currentActivity = currentActivity;
    }

    public String getDownloadAll() {
        return downloadAll;
    }

    public void setDownloadAll(String downloadAll) {
        this.downloadAll = downloadAll;
    }

    public JSONObject getInnovation() {
        return innovation;
    }

    public void setInnovation(JSONObject innovation) {
        this.innovation = innovation;
    }

    public JSONObject getLock() {
        return lock;
    }

    public void setLock(JSONObject lock) {
        this.lock = lock;
    }

    public JSONArray getNextActivities() {
        return nextActivities;
    }

    public void setNextActivities(JSONArray nextActivities) {
        this.nextActivities = nextActivities;

    }

    public JSONObject getRecords() {
        return records;
    }

    public void setRecords(JSONObject records) {
        this.records = records;
    }

    public JSONObject getService() {
        return service;
    }

    public void setService(JSONObject service) {
        this.service = service;
    }

    public PieTask getUserTask() {
        return userTask;
    }

    public void setUserTask(PieTask userTask) {
        this.userTask = userTask;
    }

	public JSONObject getShareRecord() {
		return shareRecord;
	}

	public void setShareRecord(JSONObject shareRecord) {
		this.shareRecord = shareRecord;
	}

	public List<BpmSpecialRole> getNextNodeRoles() {
		return nextNodeRoles;
	}

	public void setNextNodeRoles(List<BpmSpecialRole> nextNodeRoles) {
		this.nextNodeRoles = nextNodeRoles;
	}

	public String getSelectedNextNodeRoles() {
		return selectedNextNodeRoles;
	}

	public void setSelectedNextNodeRoles(String selectedNextNodeRoles) {
		this.selectedNextNodeRoles = selectedNextNodeRoles;
	}

	public List<BpmSpecialUserInfo> getNextNodeUsersByRole() {
		return nextNodeUsersByRole;
	}

	public void setNextNodeUsersByRole(List<BpmSpecialUserInfo> nextNodeUsersByRole) {
		this.nextNodeUsersByRole = nextNodeUsersByRole;
	}

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }
    public BusinessDataDto getBusinessDataDto() {
        return businessDataDto;
    }

    public void setBusinessDataDto(BusinessDataDto businessDataDto) {
        this.businessDataDto = businessDataDto;
    }

    public boolean isBpmShowData() {
        return isBpmShowData;
    }

    public void setBpmShowData(boolean bpmShowData) {
        isBpmShowData = bpmShowData;
    }

    public Map<String, Object> getOtherV() {
        return otherV;
    }

    public void setOtherV(Map<String, Object> otherV) {
        this.otherV = otherV;
    }
}
