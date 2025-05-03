package com.yabushan.common.bpm.model;

public class PieDocumentData {
	private String businessData;
	private String attributeData;
	private int versionNumber;
	public PieDocumentData(String businessData, String attributeData,
			int versionNumber) {
		super();
		this.businessData = businessData;
		this.attributeData = attributeData;
		this.versionNumber = versionNumber;
	}
	public PieDocumentData() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getBusinessData() {
		return businessData;
	}
	public void setBusinessData(String businessData) {
		this.businessData = businessData;
	}
	public String getAttributeData() {
		return attributeData;
	}
	public void setAttributeData(String attributeData) {
		this.attributeData = attributeData;
	}
	public int getVersionNumber() {
		return versionNumber;
	}
	public void setVersionNumber(int versionNumber) {
		this.versionNumber = versionNumber;
	}
	
	

}
