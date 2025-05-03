package com.yabushan.common.bpm.model;

public class FlowResult {
	private Integer httpCode;
	private String msg;
	private Long timestamp;
	private String node;
	private PieDocTask data;//主要返回信息
	private String openFlowUrl;
	public Integer getHttpCode() {
		return httpCode;
	}
	public void setHttpCode(Integer httpCode) {
		this.httpCode = httpCode;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	public String getNode() {
		return node;
	}
	public void setNode(String node) {
		this.node = node;
	}
	public PieDocTask getData() {
		return data;
	}
	public void setData(PieDocTask data) {
		this.data = data;
	}
	public FlowResult(Integer httpCode, String msg, Long timestamp,
					  String node, PieDocTask data) {
		super();
		this.httpCode = httpCode;
		this.msg = msg;
		this.timestamp = timestamp;
		this.node = node;
		this.data = data;
	}
	public FlowResult() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getOpenFlowUrl() {
		return openFlowUrl;
	}
	public void setOpenFlowUrl(String openFlowUrl) {
		this.openFlowUrl = openFlowUrl;
	}

}
