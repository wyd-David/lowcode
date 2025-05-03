package com.yabushan.common.bpm.model;
/**
 * @ClassName:     FlowParams.java
 * @Description:   发起流程需要的参数 
 * @author:        yl
 * @version:       V1.0  
 * @Date:           2019年3月7日 下午4:48:17
 */
public class FlowParams {

	//参数下一处理人
	private String type = "user";//"user"
	private String users = "";//"lizhiyong"
	private String secretary = "";//
	//流程信息
	private String documentId="";//文档编号docid:12F5D63699EA4E86894CEA62FF6E1BBB
	private String currentUser = "";//当前用户
	private String nextActivityId ="";//下一环节ID
	private String currentActivityId ="";//当前节点
	private String nextUserInfo="";//下一部处理人{type,users,secretary} 需要处理
	/**
	*{
	 * "appraiseResult" :"审批结果0/1",
	 * "voteResult":"投票结果0/1",
	 *  "ixXP":"是否信披0/1",//存在公共流程表
	 *}
	**/
	private String approveResult ="";//处理结果.
	private String approveNote ="";//处理意见
	private String appId="";//应用ID
	private String chain="[]";//审批链
	private String delaySerialNum="false";//
	private String targetUser="";

	private String taskId="";//
	private boolean back=false;//默认false 是否下一步骤处理完之后回到此环节
	private int backType=0;//强制返回方式
	private boolean break_ =false;//默认false 是否中断会签直接到下一个环节
	private boolean force = false;//默认false 是否超时跳转
	
	private String user = "";//"lizhiyong"
	
	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUsers() {
		return users;
	}

	public void setUsers(String users) {
		this.users = users;
	}

	public String getSecretary() {
		return secretary;
	}

	public void setSecretary(String secretary) {
		this.secretary = secretary;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public String getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(String currentUser) {
		this.currentUser = currentUser;
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
		return approveResult == null? "":approveResult;
	}

	public void setApproveResult(String approveResult) {
		this.approveResult = approveResult;
	}

	public String getApproveNote() {
		return approveNote== null? "":approveNote;
	}

	public void setApproveNote(String approveNote) {
		this.approveNote = approveNote;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getChain() {
		return chain;
	}

	public void setChain(String chain) {
		this.chain = chain;
	}

	public String getDelaySerialNum() {
		return delaySerialNum;
	}

	public void setDelaySerialNum(String delaySerialNum) {
		this.delaySerialNum = delaySerialNum;
	}

	public String getTargetUser() {
		return targetUser;
	}

	public void setTargetUser(String targetUser) {
		this.targetUser = targetUser;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public boolean isBack() {
		return back;
	}

	public void setBack(boolean back) {
		this.back = back;
	}

	public int getBackType() {
		return backType;
	}

	public void setBackType(int backType) {
		this.backType = backType;
	}

	public boolean isBreak_() {
		return break_;
	}

	public void setBreak_(boolean break_) {
		this.break_ = break_;
	}

	public boolean isForce() {
		return force;
	}

	public void setForce(boolean force) {
		this.force = force;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public FlowParams() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCurrentActivityId() {
		return currentActivityId;
	}

	public void setCurrentActivityId(String currentActivityId) {
		this.currentActivityId = currentActivityId;
	}
	
	

}
