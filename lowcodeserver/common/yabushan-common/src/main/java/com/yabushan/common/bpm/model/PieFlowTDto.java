package com.yabushan.common.bpm.model;

import java.util.List;

public class PieFlowTDto<T> {
	private DocParams docParams;
	private FlowParams flowParams;
	private String handler;
	private Integer currentStatusI;
	private String title;
	private  String flowContent;//
	private String docId;
	private Integer nodeIndex;
	private NextUser nextUser;
	private String nodeName;
//	private boolean isWithDraw= false;//是否撤回
	private Integer domianDetail=0;//流程细类，例如投资经理清算等流程，默认是0
	private Integer xpProdFlowType=0;//信披的流程类型 0 集合单一 2 ABS 5 投顾 20200929
	private Integer sysSourceType=0;//系统来源：0、慧心运营系统(amop) 1、战心系统.... 20201217
	private boolean commitFlow = true;
	private String prodName;//用于检测产品名称

	private PieDocTask pieDocTask;
	private PieDocument pieDocument;
	private String actionType;//发起的时操作类型：START 提交 SAVE 暂存

	private T entity;

	private  List<BpmSpecialUserInfo> listBpmSpecialUserInfos;


	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public PieFlowTDto() {
	}

	public List<BpmSpecialUserInfo> getListBpmSpecialUserInfos() {
		return listBpmSpecialUserInfos;
	}

	public void setListBpmSpecialUserInfos(List<BpmSpecialUserInfo> listBpmSpecialUserInfos) {
		this.listBpmSpecialUserInfos = listBpmSpecialUserInfos;
	}

	public DocParams getDocParams() {
		return docParams;
	}
	public void setDocParams(DocParams docParams) {
		this.docParams = docParams;
	}
	public FlowParams getFlowParams() {
		return flowParams;
	}
	public void setFlowParams(FlowParams flowParams) {
		this.flowParams = flowParams;
	}
	public String getHandler() {
		return handler;
	}
	public void setHandler(String handler) {
		this.handler = handler;
	}
	public Integer getCurrentStatusI() {
		return currentStatusI;
	}
	public void setCurrentStatusI(Integer currentStatusI) {
		this.currentStatusI = currentStatusI;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	public Integer getNodeIndex() {
		return nodeIndex;
	}
	public void setNodeIndex(Integer nodeIndex) {
		this.nodeIndex = nodeIndex;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public T getEntity() {
		return entity;
	}
	public void setEntity(T entity) {
		this.entity = entity;
	}
	public NextUser getNextUser() {
		return nextUser;
	}
	public void setNextUser(NextUser nextUser) {
		this.nextUser = nextUser;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public boolean isCommitFlow() {
		return commitFlow;
	}

	public void setCommitFlow(boolean commitFlow) {
		this.commitFlow = commitFlow;
	}

//
//	public boolean isWithDraw() {
//		return isWithDraw;
//	}
//
//	public void setWithDraw(boolean withDraw) {
//		isWithDraw = withDraw;
//	}

	public Integer getXpProdFlowType() {
		return xpProdFlowType;
	}

	public void setXpProdFlowType(Integer xpProdFlowType) {
		this.xpProdFlowType = xpProdFlowType;
	}

	public Integer getDomianDetail() {
		return domianDetail;
	}

	public void setDomianDetail(Integer domianDetail) {
		if( domianDetail == null){
			domianDetail = 0;
		}
		this.domianDetail = domianDetail;
	}

	public String getFlowContent() {
		return flowContent;
	}

	public void setFlowContent(String flowContent) {
		this.flowContent = flowContent;
	}

	public Integer getSysSourceType() {
		return sysSourceType;
	}

	public void setSysSourceType(Integer sysSourceType) {
		this.sysSourceType = sysSourceType;
	}

	public PieDocTask getPieDocTask() {
		return pieDocTask;
	}

	public void setPieDocTask(PieDocTask pieDocTask) {
		this.pieDocTask = pieDocTask;
	}

	public PieDocument getPieDocument() {
		return pieDocument;
	}

	public void setPieDocument(PieDocument pieDocument) {
		this.pieDocument = pieDocument;
	}
}
