package com.yabushan.common.bpm.model;
/**
 * @ClassName:       PieDocument
 * @Description:    流程文档信息
 * @author:            yl
 * @date:            2019年5月22日        上午9:31:52
 */
public class PieDocument {
	private String id="";
	private String title="";//流程标题
	private String serviceCode="";//流程编号
	private String serviceDetailCode="";//流程细类编号
	private Integer xpProdFlowType=0;//信披的流程类型 0 集合单一 2 ABS 5 投顾 20200929
	private String serviceVersion="";//流程版本号
	private String serviceName="";
	private String currentActivity="";
	private String currentOwner="";
	private String status="";
	private String creatorName="";
	private String creatorDeptName="";
	private String createdBy="";
	private String startedBy="";
	private String updatedBy="";
	private String createdTime="";
	private String startedTime="";
	private String updatedTime="";
	private String serialNumber="";
	private String draftTaskId="";
	private String processId="";//流程ID
	//20200228新增字段
	private String corpId="";//记录流程归属的集团信息
	private String [] currentActivities;//自己 根据 taskId处理
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	public String getServiceVersion() {
		return serviceVersion;
	}
	public void setServiceVersion(String serviceVersion) {
		this.serviceVersion = serviceVersion;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getCurrentActivity() {
		return currentActivity;
	}
	public void setCurrentActivity(String currentActivity) {
		this.currentActivity = currentActivity;
	}
	public String getCurrentOwner() {
		return currentOwner;
	}
	public void setCurrentOwner(String currentOwner) {
		this.currentOwner = currentOwner;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreatorName() {
		return creatorName;
	}
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	public String getCreatorDeptName() {
		return creatorDeptName;
	}
	public void setCreatorDeptName(String creatorDeptName) {
		this.creatorDeptName = creatorDeptName;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getStartedBy() {
		return startedBy;
	}
	public void setStartedBy(String startedBy) {
		this.startedBy = startedBy;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	public String getStartedTime() {
		return startedTime;
	}
	public void setStartedTime(String startedTime) {
		this.startedTime = startedTime;
	}
	public String getUpdatedTime() {
		return updatedTime;
	}
	public void setUpdatedTime(String updatedTime) {
		this.updatedTime = updatedTime;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getDraftTaskId() {
		return draftTaskId;
	}
	public void setDraftTaskId(String draftTaskId) {
		this.draftTaskId = draftTaskId;
	}
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	public PieDocument(String id, String title, String serviceCode,
			String serviceVersion, String serviceName, String currentActivity,
			String currentOwner, String status, String creatorName,
			String creatorDeptName, String createdBy, String startedBy,
			String updatedBy, String createdTime, String startedTime,
			String updatedTime, String serialNumber, String draftTaskId,
			String processId) {
		super();
		this.id = id;
		this.title = title;
		this.serviceCode = serviceCode;
		this.serviceVersion = serviceVersion;
		this.serviceName = serviceName;
		this.currentActivity = currentActivity;
		this.currentOwner = currentOwner;
		this.status = status;
		this.creatorName = creatorName;
		this.creatorDeptName = creatorDeptName;
		this.createdBy = createdBy;
		this.startedBy = startedBy;
		this.updatedBy = updatedBy;
		this.createdTime = createdTime;
		this.startedTime = startedTime;
		this.updatedTime = updatedTime;
		this.serialNumber = serialNumber;
		this.draftTaskId = draftTaskId;
		this.processId = processId;
	}

	public String[] getCurrentActivities() {
		return currentActivities;
	}

	public void setCurrentActivities(String[] currentActivities) {
		this.currentActivities = currentActivities;
	}

	public PieDocument() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public String getServiceDetailCode() {
		return serviceDetailCode;
	}

	public void setServiceDetailCode(String serviceDetailCode) {
		this.serviceDetailCode = serviceDetailCode;
	}

	public Integer getXpProdFlowType() {
		return xpProdFlowType;
	}

	public void setXpProdFlowType(Integer xpProdFlowType) {
		this.xpProdFlowType = xpProdFlowType;
	}
}
