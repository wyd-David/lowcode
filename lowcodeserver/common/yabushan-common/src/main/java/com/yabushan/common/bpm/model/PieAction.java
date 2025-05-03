package com.yabushan.common.bpm.model;
/**
 * @ClassName:       PieAction
 * @Description:    openBPM
 * @author:            yl
 * @date:            2019年5月22日        上午9:38:19
 */
public class PieAction {
	//自定义 产品立项
	private boolean isViewMode= false;//是否查看模式
	private boolean isEditBusiness= false;//编辑业务数据
	private boolean isUseRoleSelectUser = false;//时候启用角色选择用户
	private boolean isDeleteFlowAttach = false;//时候删除流程附件(用于在线编辑了，因为删除按钮已经不需要了)
	private boolean isShowAppraise = false;//该流程是否展示展示评审
	private boolean allowClose= false;//流程时候可以关闭
	private boolean isSupportMobileCommit= false;//是否支持手机端提交
	private boolean allowBackToApply;//退回拟稿人补充/退回拟稿人发起

	//BPM赋值
	private boolean allowAssign;
	private boolean allowCancel;
	private boolean allowCommit;
	private boolean allowConsult;
	private boolean allowPrint;
	private boolean allowReject;
	private boolean allowSave;
	private boolean allowSelectNextActivity;
	private boolean allowSelectNextUser;
	private boolean allowShare;
	private boolean allowSolicit;
	private boolean allowStart;
	private boolean allowTransfer;
	private boolean allowUpload;
	private boolean allowWithdraw;
	//
	private boolean allowDraftAdd= false;//拟稿人补充把99 节点
	//
	private boolean assign;
	private boolean chain;
	private boolean moved;
	private boolean secretary;
	private boolean solicit;
	public boolean isAllowAssign() {
		return allowAssign;
	}
	public void setAllowAssign(boolean allowAssign) {
		this.allowAssign = allowAssign;
	}
	public boolean isAllowCancel() {
		return allowCancel;
	}
	public void setAllowCancel(boolean allowCancel) {
		this.allowCancel = allowCancel;
	}
	public boolean isAllowCommit() {
		return allowCommit;
	}
	public void setAllowCommit(boolean allowCommit) {
		this.allowCommit = allowCommit;
	}
	public boolean isAllowConsult() {
		return allowConsult;
	}
	public void setAllowConsult(boolean allowConsult) {
		this.allowConsult = allowConsult;
	}
	public boolean isAllowPrint() {
		return allowPrint;
	}
	public void setAllowPrint(boolean allowPrint) {
		this.allowPrint = allowPrint;
	}
	public boolean isAllowReject() {
		return allowReject;
	}
	public void setAllowReject(boolean allowReject) {
		this.allowReject = allowReject;
	}
	public boolean isAllowSave() {
		return allowSave;
	}
	public void setAllowSave(boolean allowSave) {
		this.allowSave = allowSave;
	}
	public boolean isAllowSelectNextActivity() {
		return allowSelectNextActivity;
	}
	public void setAllowSelectNextActivity(boolean allowSelectNextActivity) {
		this.allowSelectNextActivity = allowSelectNextActivity;
	}
	public boolean isAllowSelectNextUser() {
		return allowSelectNextUser;
	}
	public void setAllowSelectNextUser(boolean allowSelectNextUser) {
		this.allowSelectNextUser = allowSelectNextUser;
	}
	public boolean isAllowShare() {
		return allowShare;
	}
	public void setAllowShare(boolean allowShare) {
		this.allowShare = allowShare;
	}
	public boolean isAllowSolicit() {
		return allowSolicit;
	}
	public void setAllowSolicit(boolean allowSolicit) {
		this.allowSolicit = allowSolicit;
	}
	public boolean isAllowStart() {
		return allowStart;
	}
	public void setAllowStart(boolean allowStart) {
		this.allowStart = allowStart;
	}
	public boolean isAllowTransfer() {
		return allowTransfer;
	}
	public void setAllowTransfer(boolean allowTransfer) {
		this.allowTransfer = allowTransfer;
	}
	public boolean isAllowUpload() {
		return allowUpload;
	}
	public void setAllowUpload(boolean allowUpload) {
		this.allowUpload = allowUpload;
	}
	public boolean isAllowWithdraw() {
		return allowWithdraw;
	}
	public void setAllowWithdraw(boolean allowWithdraw) {
		this.allowWithdraw = allowWithdraw;
	}
	public boolean isAssign() {
		return assign;
	}
	public void setAssign(boolean assign) {
		this.assign = assign;
	}
	public boolean isChain() {
		return chain;
	}
	public void setChain(boolean chain) {
		this.chain = chain;
	}
	public boolean isMoved() {
		return moved;
	}
	public void setMoved(boolean moved) {
		this.moved = moved;
	}
	public boolean isSecretary() {
		return secretary;
	}
	public void setSecretary(boolean secretary) {
		this.secretary = secretary;
	}
	public boolean isSolicit() {
		return solicit;
	}
	public void setSolicit(boolean solicit) {
		this.solicit = solicit;
	}
	public PieAction(boolean allowAssign, boolean allowCancel,
			boolean allowCommit, boolean allowConsult, boolean allowPrint,
			boolean allowReject, boolean allowSave,
			boolean allowSelectNextActivity, boolean allowSelectNextUser,
			boolean allowShare, boolean allowSolicit, boolean allowStart,
			boolean allowTransfer, boolean allowUpload, boolean allowWithdraw,
			boolean assign, boolean chain, boolean moved, boolean secretary,
			boolean solicit) {
		super();
		this.allowAssign = allowAssign;
		this.allowCancel = allowCancel;
		this.allowCommit = allowCommit;
		this.allowConsult = allowConsult;
		this.allowPrint = allowPrint;
		this.allowReject = allowReject;
		this.allowSave = allowSave;
		this.allowSelectNextActivity = allowSelectNextActivity;
		this.allowSelectNextUser = allowSelectNextUser;
		this.allowShare = allowShare;
		this.allowSolicit = allowSolicit;
		this.allowStart = allowStart;
		this.allowTransfer = allowTransfer;
		this.allowUpload = allowUpload;
		this.allowWithdraw = allowWithdraw;
		this.assign = assign;
		this.chain = chain;
		this.moved = moved;
		this.secretary = secretary;
		this.solicit = solicit;
	}
	public PieAction() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "PieAction [allowAssign=" + allowAssign + ", allowCancel="
				+ allowCancel + ", allowCommit=" + allowCommit
				+ ", allowConsult=" + allowConsult + ", allowPrint="
				+ allowPrint + ", allowReject=" + allowReject + ", allowSave="
				+ allowSave + ", allowSelectNextActivity="
				+ allowSelectNextActivity + ", allowSelectNextUser="
				+ allowSelectNextUser + ", allowShare=" + allowShare
				+ ", allowSolicit=" + allowSolicit + ", allowStart="
				+ allowStart + ", allowTransfer=" + allowTransfer
				+ ", allowUpload=" + allowUpload + ", allowWithdraw="
				+ allowWithdraw + ", assign=" + assign + ", chain=" + chain
				+ ", moved=" + moved + ", secretary=" + secretary
				+ ", solicit=" + solicit + "]";
	}
	public boolean isEditBusiness() {
		return isEditBusiness;
	}
	public void setEditBusiness(boolean isEditBusiness) {
		this.isEditBusiness = isEditBusiness;
	}
	public boolean isViewMode() {
		return isViewMode;
	}
	public void setViewMode(boolean isViewMode) {
		this.isViewMode = isViewMode;
	}
	public boolean isUseRoleSelectUser() {
		return isUseRoleSelectUser;
	}
	public void setUseRoleSelectUser(boolean isUseRoleSelectUser) {
		this.isUseRoleSelectUser = isUseRoleSelectUser;
	}
	public boolean isDeleteFlowAttach() {
		return isDeleteFlowAttach;
	}
	public void setDeleteFlowAttach(boolean isDeleteFlowAttach) {
		this.isDeleteFlowAttach = isDeleteFlowAttach;
	}

	public boolean isShowAppraise() {
		return isShowAppraise;
	}

	public boolean isAllowClose() {
		return allowClose;
	}

	public void setAllowClose(boolean allowClose) {
		this.allowClose = allowClose;
	}

	public void setShowAppraise(boolean showAppraise) {
		isShowAppraise = showAppraise;
	}

	public boolean isSupportMobileCommit() {
		return isSupportMobileCommit;
	}

	public void setSupportMobileCommit(boolean supportMobileCommit) {
		isSupportMobileCommit = supportMobileCommit;
	}

	public boolean isAllowBackToApply() {
		return allowBackToApply;
	}

	public void setAllowBackToApply(boolean allowBackToApply) {
		this.allowBackToApply = allowBackToApply;
	}

	public boolean isAllowDraftAdd() {
		return allowDraftAdd;
	}

	public void setAllowDraftAdd(boolean allowDraftAdd) {
		this.allowDraftAdd = allowDraftAdd;
	}
}
