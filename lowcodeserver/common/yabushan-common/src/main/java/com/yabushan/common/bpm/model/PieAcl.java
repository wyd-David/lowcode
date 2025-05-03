package com.yabushan.common.bpm.model;
/**
 * @ClassName:       PieAcl
 * @Description:    打开流程返回的acl
 * @author:            yl
 * @date:            2019年5月22日        上午9:32:57
 */
public class PieAcl {
	private boolean access;
	private String aclCode;
	private boolean admin;
	private boolean allowShare;
	private String documentId;
	private String userCode;
	public boolean isAccess() {
		return access;
	}
	public void setAccess(boolean access) {
		this.access = access;
	}
	public String getAclCode() {
		return aclCode;
	}
	public void setAclCode(String aclCode) {
		this.aclCode = aclCode;
	}
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	public boolean isAllowShare() {
		return allowShare;
	}
	public void setAllowShare(boolean allowShare) {
		this.allowShare = allowShare;
	}
	public String getDocumentId() {
		return documentId;
	}
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public PieAcl(boolean access, String aclCode, boolean admin,
			boolean allowShare, String documentId, String userCode) {
		super();
		this.access = access;
		this.aclCode = aclCode;
		this.admin = admin;
		this.allowShare = allowShare;
		this.documentId = documentId;
		this.userCode = userCode;
	}
	public PieAcl() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "PieAcl [access=" + access + ", aclCode=" + aclCode + ", admin="
				+ admin + ", allowShare=" + allowShare + ", documentId="
				+ documentId + ", userCode=" + userCode + "]";
	}
	
	

}
