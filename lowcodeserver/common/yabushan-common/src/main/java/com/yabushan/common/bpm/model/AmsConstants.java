package com.yabushan.common.bpm.model;



public class AmsConstants {

	public static final String LOGIN_USER = "login_user";

	//公司发文接口E
	// BPM流程权限
	public static  final String PIE_ACCESS_CODE="PIE-ACCESS-CODE";
	public static  final String PIE_ACCESS_TOKEN="PIE-ACCESS-TOKEN";
	// 流程引擎接口a
	public static final String PBMURL = "http://127.0.0.1:8080//";
	public static final String PBMURL_Assign = "/api/v2/engine/assign";// POST转办任务
	public static final String PBMURL_Commit = "/api/v2/engine/commit";// POST提交任务
	public static final String PBMURL_Close = "/api/v2/engine/close";// POST关闭文档
	public static final String PBMURL_Cancel = "/api/v2/engine/cancel";// POST撤销文档
	public static final String PBMURL_Reject = "/api/v2/engine/reject";// POST退回任务
	public static final String PBMURL_Solicit = "/api/v2/engine/solicit";// POST加签任务
	public static final String PBMURL_Start = "/api/v2/engine/start";// POST流程启动
	public static final String PBMURL_Transfer = "/api/v2/engine/transfer";// POST综合员转发任务
	public static final String PBMURL_Withdraw = "/api/v2/engine/withdraw";// POST撤回任务
	public static final String PBMURL_Suspend = "/api/v2/engine/suspend";// POST 暂挂文档 2020-02-26
	public static final String PBMURL_Resume = "/api/v2/engine/resume";// POST 恢复文档 2020-02-26

	// 流程引擎接口e
	//流程文档a
	public static final String PBMURLDOC_Draft = "/api/v2/document/draft";// POST拟稿文档
	public static final String PBMURLDOC_Create = "/api/v2/document/create";// POST创建新文档
	public static final String PBMURLDOC_Query = "/api/v2/document/query";//POST  查询文档
	public static final String PBMURLDOC_Share= "/api/v2/document/share";
    public static final String PBMURLDOC_Records= "/api/v2/document/records";
    public static final String PBMURLDOC_Access= "/api/v2/document/access";
    public static final String PBMURLDOC_Acl= "/api/v2/document/acl";
    public static final String PBMURLDOC_Update= "/api/v2/document/update";
    public static final String PBMURLDOC_Open= "/api/v2/document/open";
    public static final String PBMURLDOC_Service ="/api/v2/document/service";//POST  查询流程编号信息
    public static final String PBMURLDOC_Open_Share ="/api/v2/document/open-share"; //POST  打开待阅
    public static final String PBMURLDOC_Rel_Create ="/api/v2/document/relation/create"; //POST  创建流程关联关系
    public static final String PBMURLDOC_Rel_Delete ="/api/v2/document/relation/delete"; //POST  解除关联流程关系
    public static final String PBMURLDOC_Rel_Query ="/api/v2/document/relation/query"; //POST  查询关联流程
    public static final String PBMURLDOC_Rel_Update ="/api/v2/document/relation/update"; //POST  更新流程关联关系


	public static final String HRPOC_004="HRPOC-004";   //食堂流程编号



}
