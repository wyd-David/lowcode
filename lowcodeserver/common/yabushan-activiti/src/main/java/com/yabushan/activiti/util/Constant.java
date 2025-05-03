package com.yabushan.activiti.util;

public  class  Constant {

	/**
	 * 流程发起人变量设置，流程图中设置
	 */
	public final static String REQUEST_USER="requestUser";
	public final static String REQUEST_USER_NAME="requestUserName";

	/**
	 * 流程发起人变量设置，流程图中设置
	 */
	public final static String NEXTDEAL_USER="nextDealUser";


	/**
	 * 业务主键ID
	 */
	public final static String BUSINESS_ID="BID";

	/**
	 * 用户任务节点存储的流程变量（绑定任务）
	 */
	public final static String STEP_INFO ="STEP_INFO";

	/**
	 * 分隔符
	 */
	public final static String  SEPARATOR=",";
	/**
	 * 分页开始值
	 */
	public final static Integer firstResult=0;
	/**
	 * 分页结束值
	 */
	public final static Integer maxResults=100000000;

	/**
	 * //部署工作流规则，文件存放根目录
	 */
	public final static String PROCESS_FILE_ROOT_PATH="process";

	/**
	 * 二级经理正职请假规则文件
	 */
	public final static String VACATION_FLOW_BPMN="VacationProcess.bpmn";
	/**
	 * 二级经理正职请假规则图片
	 */
	public final static String VACATION_FLOW_PNG="VacationProcess.png";


	/**
	 * 二级经理副职请假规则文件
	 */
	public final static String DEPUTY_FLOW_BPMN="DeputyProcess.bpmn";
	/**
	 * 二级经理副职请假规则图片
	 */
	public final static String DEPUTY_FLOW_PNG="DeputyProcess.png";

	/**
	 * 探亲假规则文件
	 */
	public final static String VISIT_FLOW_BPMN="VisitProcess.bpmn";
	/**
	 * 探亲假请假规则图片
	 */
	public final static String VISIT_FLOW_PNG="VisitProcess.png";

	/**
	 * 探亲假规则文件
	 */
	public final static String CANCEL_FLOW_BPMN="CancelProcess.bpmn";
	/**
	 * 探亲假请假规则图片
	 */
	public final static String CANCEL_FLOW_PNG="CancelProcess.png";

	/**
	 * 协作满意度评分申请规则文件
	 */
	public final static String JOBEVALUATION_FLOW_BPMN="JobEvaluationProcess.bpmn";

	/**
	 * 协作满意度评分申请规则图片
	 */
	public final static String JOBEVALUATION_FLOW_PNG="JobEvaluationProcess.png";


	/**
	 * 二级经理正职出国护照使用申请规则文件
	 */
	public final static String PPMLEADER_FLOW_BPMN="PpmLeaderProcess.bpmn";

	/**
	 * 二级经理正职出国护照使用申请规则图片
	 */
	public final static String PPMLEADER_FLOW_PNG="PpmLeaderProcess.png";

	/**
	 * 二级经理副职出国护照使用申请规则文件
	 */
	public final static String PPMDEPUTY_FLOW_BPMN="PpmDeputyProcess.bpmn";

	/**
	 * 二级经理副职出国护照使用申请规则图片
	 */
	public final static String PPMDEPUTY_FLOW_PNG="PpmDeputyProcess.png";

	/**
	 * 内训课程培训申请流程规则文件
	 */
	public final static String TRICOURSEAWT_FLOW_BPMN="TriCourseAwtProcess.bpmn";
	/**
	 * 内训课程预约审批规则文件
	 */
	public final static String TRICOURSEIOA_FLOW_BPMN="TriCourseIoaProcess.bpmn";
	/**
	 * 内训课程开课流程规则文件
	 */
	public final static String TRICOURSEIOCB_FLOW_BPMN="TriCourseIocbProcess.bpmn";
	/**
	 * 地市公司公开课程报名流程规则文件
	 */
	public final static String TRICOURSEOCOBO_FLOW_BPMN="TriCourseOcoBoProcess.bpmn";
	/**
	 * 省公司公开课程报名流程规则文件
	 */
	public final static String TRICOURSEOCOHQ_FLOW_BPMN="TriCourseOcoHqProcess.bpmn";
	/**
	 * 地市公司公开课培训申请流程规则文件
	 */
	public final static String TRICOURSETRIBO_FLOW_BPMN="TriCourseTriBoProcess.bpmn";
	/**
	 * 省公司公开课培训申请流程规则文件
	 */
	public final static String TRICOURSETRIHQ_FLOW_BPMN="TriCourseTriHqProcess.bpmn";
	/**
	 * 内训课程认证发布流程规则文件
	 */
	public final static String TRICOURSEREVIEWIT_FLOW_BPMN="TriCourseReviewItProcess.bpmn";
	/**
	 * 书卡兑换流程规则文件
	 */
	public final static String TRIEXCHANGEBOOKS_FLOW_BPMN="TriExchangeBooksProcess.bpmn";
	/**
	 * 培训管理计划外培训管理规则文件
	 */
	public final static String TRIOUTOFPLAN_FLOW_BPMN="TriOutOfPlanProcess.bpmn";
	/**
	 * 地市公司培训实施流程规则文件
	 */
	public final static String TRITRAININGBO_FLOW_BPMN="TriTrainingBoProcess.bpmn";
	/**
	 * 省公司培训实施流程规则文件
	 */
	public final static String TRITRAININGHQ_FLOW_BPMN="TriTrainingHqProcess.bpmn";
	/**
	 * 地市公司培训计划审批流程规则文件
	 */
	public final static String TRITRAINPLANBO_FLOW_BPMN="TriTrainPlanBoProcess.bpmn";
	/**
	 * 省公司培训计划审批流程规则文件
	 */
	public final static String TRITRAINPLANHQ_FLOW_BPMN="TriTrainPlanHqProcess.bpmn";
	/**
	 * 省公司培训计划汇总审批流程规则文件
	 */
	public final static String TRITRAINPLANPOHQ_FLOW_BPMN="TriTrainPlanPoHqProcess.bpmn";
	/**
	 * 地市公司培计划省审批流程规则文件
	 */
	public final static String TRITRAINPLANBOHQ_FLOW_BPMN="TriTrainPlanBoHqProcess.bpmn";
	/**
	 * 省公司培训总结费用审批流程规则文件
	 */
	public final static String TRITRAININGPROBOHQ_FLOW_BPMN="TriTrainingProBoHqProcess.bpmn";
	/**
	/**
	 * 地市公司培训总结费用审批流程规则文件
	 */
	public final static String TRITRAININGCITBOHQ_FLOW_BPMN="TriTrainingCitBoHqProcess.bpmn";
	/**
	 * 培训计划提交审批流程规则文件
	 */
	public final static String TRITRAINPLANBEFORECOLLECT_FLOW_BPMN = "TriTrainPlanBeforeCollect.bpmn";
	/**
	 * 培训实施开始后承办人新增参培人员审批流程（省公司）
	 */
	public final static String TRIPROIMPSIGNUPAFTERDEADLINE_FLOW_BPMN = "TriProImpSignUpAfterDeadline.bpmn";
	/**
	 * 培训实施开始后承办人新增参培人员审批流程（地市公司）
	 */
	public final static String TRICITIMPSIGNUPAFTERDEADLINE_FLOW_BPMN = "TriCitImpSignUpAfterDeadline.bpmn";
	/**
	 * 考勤加班申请审批流程规则文件
	 */
	public final static String ATTOVERTIMEAPPLY_FLOW_BPMN="AttOvertimeApplyProcess.bpmn";
	/**
	 * 考勤零星请假申请审批流程规则文件
	 */
	public final static String ATTLEAVEAPPLY_FLOW_BPMN="AttLeaveApplyProcess.bpmn";
	/**
	 * 考勤异常处理审批流程规则文件
	 */
	public final static String ATTEXPTIONHANDLE_FLOW_BPMN="AttExptionHandleProcess.bpmn";

	/**
	 * 考勤部门反馈审批流程规则文件
	 */
	public final static String ATTDEPTFEEDBACK_FLOW_BPMN="AttDeptFeedbackProcess.bpmn";

	/**
	 * 内训课程培训申请流程规则图片
	 */
	public final static String TRICOURSEAWT_FLOW_PNG="TriCourseAwtProcess.png";
	/**
	 * 内训课程预约审批规则图片
	 */
	public final static String TRICOURSEIOA_FLOW_PNG="TriCourseIoaProcess.png";
	/**
	 * 内训课程开课流程规则图片
	 */
	public final static String TRICOURSEIOCB_FLOW_PNG="TriCourseIocbProcess.png";
	/**
	 * 地市公司公开课程报名流程规则图片
	 */
	public final static String TRICOURSEOCOBO_FLOW_PNG="TriCourseOcoBoProcess.png";
	/**
	 * 省公司公开课程报名流程规则图片
	 */
	public final static String TRICOURSEOCOHQ_FLOW_PNG="TriCourseOcoHqProcess.png";
	/**
	 * 地市公司公开课培训申请流程规则图片
	 */
	public final static String TRICOURSETRIBO_FLOW_PNG="TriCourseTriBoProcess.png";
	/**
	 * 省公司公开课培训申请流程规则图片
	 */
	public final static String TRICOURSETRIHQ_FLOW_PNG="TriCourseTriHqProcess.png";
	/**
	 * 内训课程认证发布流程规则图片
	 */
	public final static String TRICOURSEREVIEWIT_FLOW_PNG="TriCourseReviewItProcess.png";
	/**
	 * 书卡兑换流程规则图片
	 */
	public final static String TRIEXCHANGEBOOKS_FLOW_PNG="TriExchangeBooksProcess.png";
	/**
	 * 培训管理计划外培训管理规则图片
	 */
	public final static String TRIOUTOFPLAN_FLOW_PNG="TriOutOfPlanProcess.png";
	/**
	 * 地市公司培训实施流程规则图片
	 */
	public final static String TRITRAININGBO_FLOW_PNG="TriTrainingBoProcess.png";
	/**
	 * 省公司培训实施流程规则图片
	 */
	public final static String TRITRAININGHQ_FLOW_PNG="TriTrainingHqProcess.png";
	/**
	 * 地市公司培训计划审批流程规则图片
	 */
	public final static String TRITRAINPLANBO_FLOW_PNG="TriTrainPlanBoProcess.png";
	/**
	 * 省公司培训计划审批流程规则图片
	 */
	public final static String TRITRAINPLANHQ_FLOW_PNG="TriTrainPlanHqProcess.png";
	/**
	 * 省公司培训计划汇总审批流程规则图片
	 */
	public final static String TRITRAINPLANPOHQ_FLOW_PNG="TriTrainPlanPoHqProcess.png";
	/**
	 * 地市公司培计划省审批流程规则图片
	 */
	public final static String TRITRAINPLANBOHQ_FLOW_PNG="TriTrainPlanBoHqProcess.png";
	/**
	 * 省公司培训总结费用审批流程规则图片
	 */
	public final static String TRITRAININGPROBOHQ_FLOW_PNG="TriTrainingProBoHqProcess.png";
	/**
	/**
	 * 地市公司培训总结费用审批流程规则图片
	 */
	public final static String TRITRAININGCITBOHQ_FLOW_PNG="TriTrainingCitBoHqProcess.png";
	/**
	 * 培训计划提交审批流程
	 */
	public final static String TRITRAINPLANBEFORECOLLECT_FLOW_PNG = "TriTrainPlanBeforeCollect.png";
	/**
	 * 培训实施开始后承办人新增参培人员审批流程（省公司）
	 */
	public final static String TRIPROIMPSIGNUPAFTERDEADLINE_FLOW_PNG = "TriProImpSignUpAfterDeadline.png";
	/**
	 * 培训实施开始后承办人新增参培人员审批流程（地市公司）
	 */
	public final static String TRICITIMPSIGNUPAFTERDEADLINE_FLOW_PNG = "TriCitImpSignUpAfterDeadline.png";
	/**
	 * 考勤加班申请审批流程规则图片
	 */
	public final static String ATTOVERTIMEAPPLY_FLOW_PNG="AttOvertimeApplyProcess.png";
	/**
	 * 考勤零星请假申请审批流程规则图片
	 */
	public final static String ATTLEAVEAPPLY_FLOW_PNG="AttLeaveApplyProcess.png";
	/**
	 * 考勤异常处理审批流程规则图片
	 */
	public final static String ATTEXPTIONHANDLE_FLOW_PNG="AttExptionHandleProcess.png";

	/**
	 * 考勤部门考勤情况确认审批流程规则图片
	 */
	public final static String ATTDEPTFEEDBACK_FLOW_PNG="AttDeptFeedbackProcess.png";



	/**
	 * 请假流程用户角色
	 */
	public final static String APPLY_USER="usertask1";//发起请假人

	public final static String FIRST_DEAL_NODE="usertask2";//第一个处理节点

	public final static String SECOND_DEAL_NODE="usertask3";//第二个处理节点

	public final static String FIRST_DEAL_NODE_TRI="usertask4";//第一个处理节点【培训计划审批】

	public final static String PPM_DIHR_FORK_NODE="parallelgateway1";//人力资源部/纪检审批分支网关

	/**
	 * 公司分管经理审批
	 */
	public final static String MANAGER_LEADER_ROLE_CODE="charge";

	/**
	 * 公司总经理审批
	 */
	public final static String LEADER_ROLE_CODE="manager";

	/**
	 * 人力资源部归档
	 */
	public final static String HR_ROLE_CODE="hr";

	/**
	 * 所在公司/部门正总
	 */
	public final static String LEADER_COMPANY="companyLeader";

	/**
	 * 所在部门正总
	 */
	public final static String LEADER_DEPT="deptLeader";

	/**
	 * 发展战略部部门正总
	 */
	public final static String DS_LEADER="dsl";


	/**
	 * 省公司纪检监察室主任和省公司人力资源部总经理
	 */
	public final static String PPM_DI_HR_LEADER="diahr";

	/**
	 * 护照使用申请人
	 */
	public final static String PPM_PRP="prp";

	/**
	 * 省公司人力资源部护照管理员
	 */
	public final static String PPM_PMHR="pmhr";

	/**
	 * 培训管理人力资源部部门经理
	 */
	public final static String TRI_HR_LEADER_COMPANY="triHrLeader";

	/**
	 * 培训管理人力资源部培训管理员
	 */
	public final static String TRI_HR_TM="triHrTm";

	/**
	 * 默认流程步骤
	 */
	public final static String DEFULT_STEP="提交";


	/**
	 * 流程类型
	 */
	/**
	 * 二级经理正职请假流程KEy值
	 */
	public final static String VACATION_PROCESS="VacationProcess";

	/**
	 * 二级经理副职请假流程KEY值
	 */
	public final static String DEPUTY_PROCESS="DeputyProcess";

	/**
	 * 探亲假流程KEY
	 */
	public final static String VISIT_PROCESS="VisitProcess";

	/**
	 * 销假流程KEY
	 */
	public final static String CANCEL_PROCESS="CancelProcess";

	/**
	 * 协作满意度评分申请流程KEY
	 */
	public final static String JOBEVALUATION_PROCESS="JobEvaluationProcess";

	/**
	 * 二级经理正职出国护照使用申请流程KEY
	 */
	public final static String PPMLEADER_PROCESS="PpmLeaderProcess";

	/**
	 * 二级经理副职出国护照使用申请流程KEY
	 */
	public final static String PPMDEPUTY_PROCESS="PpmDeputyProcess";

	/**
	 * 内训课程培训申请流程KEY
	 */
	public final static String TRICOURSEAWT_PROCESS = "TriCourseAwtProcess";
	/**
	 * 内训课程预约审批流程KEY
	 */
	public final static String TRICOURSEIOA_PROCESS = "TriCourseIoaProcess";
	/**
	 * 内训课程开课流程KEY
	 */
	public final static String TRICOURSEIOCB_PROCESS = "TriCourseIocbProcess";
	/**
	 * 地市公司公开课程报名流程KEY
	 */
	public final static String TRICOURSEOCOBO_PROCESS = "TriCourseOcoBoProcess";
	/**
	 * 省公司公开课程报名流程KEY
	 */
	public final static String TRICOURSEOCOHQ_PROCESS = "TriCourseOcoHqProcess";
	/**
	 * 地市公开课培训申请流程KEY
	 */
	public final static String TRICOURSETRIBO_PROCESS = "TriCourseTriBoProcess";
	/**
	 * 省公司公开课培训申请流程KEY
	 */
	public final static String TRICOURSETRIHQ_PROCESS = "TriCourseTriHqProcess";
	/**
	 * 内训课程认证发布流程KEY
	 */
	public final static String TRICOURSEREVIEWIT_PROCESS = "TriCourseReviewItProcess";
	/**
	 * 书卡兑换流程KEY
	 */
	public final static String TRIEXCHANGEBOOKS_PROCESS = "TriExchangeBooksProcess";
	/**
	 * 培训管理计划外培训管理流程KEY
	 */
	public final static String TRIOUTOFPLAN_PROCESS = "TriOutOfPlanProcess";
	/**
	 * 地市公司培训实施流程KEY
	 */
	public final static String TRITRAININGBO_PROCESS = "TriTrainingBoProcess";
	/**
	 * 地市公司培训实施流程KEY
	 */
	public final static String TRITRAININGHQ_PROCESS = "TriTrainingHqProcess";
	/**
	 * 地市公司培训计划审批流程KEY
	 */
	public final static String TRITRAINPLANBO_PROCESS = "TriTrainPlanBoProcess";
	/**
	 * 省公司培训计划审批流程KEY
	 */
	public final static String TRITRAINPLANHQ_PROCESS = "TriTrainPlanHqProcess";
	/**
	 * 省公司培训计划汇总审批流程KEY
	 */
	public final static String TRITRAINPLANPOHQ_PROCESS = "TriTrainPlanPoHqProcess";
	/**
	 * 地市公司培计划省审批流程KEY
	 */
	public final static String TRITRAINPLANBOHQ_PROCESS = "TriTrainPlanBoHqProcess";
	/**
	 * 省公司培训总结费用审批流程KEY
	 */
	public final static String TRITRAININGPROBOHQ_PROCESS = "TriTrainingProBoHqProcess";

	/**
	 * 地市公司培训总结费用审批流程KEY
	 */
	public final static String TRITRAININGCITBOHQ_PROCESS = "TriTrainingCitBoHqProcess";
	/**
	 * 培训计划提交审批流程
	 */
	public final static String TRITRAINPLANBEFORECOLLECT_PROCESS = "TriTrainPlanBeforeCollect";

	/**
	 * 培训实施开始截止报名后承办人新增参培人员审批流程（省公司）
	 */
	public final static String TRIPROIMPSIGNUP_AFTERDEADLINE_PROCESS = "TriProImpSignUpAfterDeadline";
	/**
	 * 培训实施开始后承办人新增参培人员审批流程（地市公司）
	 */
	public final static String TRICITIMPSIGNUPAFTERDEADLINE_PROCESS = "TriCitImpSignUpAfterDeadline";

	/**
	 * 考勤加班申请审批流程KEY
	 */
	public final static String ATTOVERTIMEAPPLY_PROCESS="AttOvertimeApplyProcess";
	/**
	 * 考勤零星请假审批流程KEY
	 */
	public final static String ATTLEAVEAPPLY_PROCESS="AttLeaveApplyProcess";
	/**
	 * 考勤异常处理审批流程KEY
	 */
	public final static String ATTEXPTIONHANDLE_PROCESS="AttExptionHandleProcess";

	/**
	 * 考勤部分反馈情况确认审批流程KEY
	 */
	public final static String ATTDEPTFEEDBACK_PROCESS="AttDeptFeedbackProcess";

	/**
	 * 地市公司二级经理人员请假
	 */
	public final static String CITY_APPLY="2";
	/**
	 * 省公司二级经理人员请假
	 */
	public final static String PROVINCE_APPLY="1";


	public final static String BACK_OPERATION_NAME = "不同意，退回";

	public final static String PPM_MULTI_TASK_NAME = "人力资源部/纪检审批";

	public final static String PPM_MANAGER_TASK_NAME = "公司总经理审批";

	public final static String PPM_TASK_BOOKING = "usertaskBooking";//预约护照领取任务id

	public final static String PPM_TASK_DRAW = "usertaskDraw";//领取护照任务id

	public final static String PPM_TASK_RPID = "usertaskReturnPassport";//上缴护照任务id

	public final static String PPM_TASK_MANAGER = "usertaskManager";//公司总经理审批任务id

	public final static String PPM_TASK_MULTI_STARTWITH = "usertaskDihr";//人力资源部/纪检审批任务id开头

	public final static String PPM_TASK_MULTI1 = PPM_TASK_MULTI_STARTWITH + "1";//人力资源部/纪检审批任务id

	public final static String PPM_FIRST_TASK_NAME = "提交护照使用申请";//流程第一步任务名


	/**
	 * 省公司薪酬核算申请流程规则文件
	 */
	public final static String SALCALCHQPROCESS_FLOW_BPMN="SalCalcHqProcess.bpmn";
	/**
	 * 省公司薪酬核算申请流程规则图片
	 */
	public final static String SALCALCHQPROCESS_FLOW_PNG="SalCalcHqProcess.png";
	/**
	 * 省公司薪酬核算申请流程KEY
	 */
	public final static String SALCALCHQ_PROCESS = "SalCalcHqProcess";
	/**
	 * 地市公司薪酬核算申请流程规则文件
	 */
	public final static String SALCALCBOPROCESS_FLOW_BPMN="SalCalcBoProcess.bpmn";
	/**
	 * 地市公司薪酬核算申请流程规则图片
	 */
	public final static String SALCALCBOPROCESS_FLOW_PNG="SalCalcBoProcess.png";
	/**
	 * 地市公司薪酬核算申请流程KEY
	 */
	public final static String SALCALCBO_PROCESS = "SalCalcBoProcess";
	/**
	 * 省公司部门奖金分配流程规则文件
	 */
	public final static String SALDEPTBONUSHQPROCESS_FLOW_BPMN="SalDeptBonusHqProcess.bpmn";
	/**
	 * 省公司部门奖金分配流程规则图片
	 */
	public final static String SALDEPTBONUSHQPROCESS_FLOW_PNG="SalDeptBonusHqProcess.png";
	/**
	 * 省公司部门奖金分配流程KEY
	 */
	public final static String SALDEPTBONUSHQ_PROCESS = "SalDeptBonusHqProcess";
	/**
	 * 地市公司部门奖金分配流程规则文件
	 */
	public final static String SALDEPTBONUSBOPROCESS_FLOW_BPMN="SalDeptBonusBoProcess.bpmn";
	/**
	 * 地市公司部门奖金分配流程规则图片
	 */
	public final static String SALDEPTBONUSBOPROCESS_FLOW_PNG="SalDeptBonusBoProcess.png";
	/**
	 * 地市公司部门奖金分配流程KEY
	 */
	public final static String SALDEPTBONUSBO_PROCESS = "SalDeptBonusBoProcess";
	/**
	 * 省公司员工绩效奖金分配流程规则文件
	 */
	public final static String SALPFMBONUSHQPROCESS_FLOW_BPMN="SalPfmBonusHqProcess.bpmn";
	/**
	 * 省公司员工绩效奖金分配流程规则图片
	 */
	public final static String SALPFMBONUSHQPROCESS_FLOW_PNG="SalPfmBonusHqProcess.png";
	/**
	 * 省公司员工绩效奖金分配流程KEY
	 */
	public final static String SALPFMBONUSHQ_PROCESS = "SalPfmBonusHqProcess";
	/**
	 * 地市公司员工绩效奖金分配流程规则文件
	 */
	public final static String SALPFMBONUSBOPROCESS_FLOW_BPMN="SalPfmBonusBoProcess.bpmn";
	/**
	 * 地市公司员工绩效奖金分配流程规则图片
	 */
	public final static String SALPFMBONUSBOPROCESS_FLOW_PNG="SalPfmBonusBoProcess.png";
	/**
	 * 地市公司员工绩效奖金分配流程KEY
	 */
	public final static String SALPFMBONUSBO_PROCESS = "SalPfmBonusBoProcess";

	/**
	 * 员工基础数据维护流程规则文件
	 */
	public final static String SALEMPBASEDATAPROCESS_FLOW_BPMN="SalEmpBaseDataProcess.bpmn";
	/**
	 * 员工基础数据维护流程规则图片
	 */
	public final static String SALEMPBASEDATAPROCESS_FLOW_PNG="SalEmpBaseDataProcess.png";
	/**
	 * 员工基础数据维护流程KEY
	 */
	public final static String SALEMPBASEDATA_PROCESS = "SalEmpBaseDataProcess";

	/**
	 * 薪酬补扣申请流程规则文件
	 */
	public final static String SALMINUSAPPLYPROCESS_FLOW_BPMN="SalMinusApplyProcess.bpmn";
	/**
	 * 薪酬补扣申请流程规则图片
	 */
	public final static String SALMINUSAPPLYPROCESS_FLOW_PNG="SalMinusApplyProcess.png";
	/**
	 * 薪酬补扣申请流程KEY
	 */
	public final static String SALMINUSAPPLY_PROCESS = "SalMinusApplyProcess";

	/**
	 * 收入证明申请流程规则文件
	 */
	public final static String SALINCOMECERTIFYPROCESS_FLOW_BPMN="SalIncomeCertifyProcess.bpmn";
	/**
	 * 收入证明申请流程规则图片
	 */
	public final static String SALINCOMECERTIFYPROCESS_FLOW_PNG="SalIncomeCertifyProcess.png";
	/**
	 * 收入证明申请流程KEY
	 */
	public final static String SALINCOMECERTIFY_PROCESS = "SalIncomeCertifyProcess";

	/**
	 * 取消审核
	 */
	public final static String FLOW_NAME_CANCEL = "取消审核";

	/**
	 * 地市公司内训师主动授课流程
	 */
	public final static String TRICOURSEAWT_CITY_PROCESS="TriCourseAwtCityProcess";
	public final static String TRICOURSEAWT_CITY_FLOW_PNG="TriCourseAwtCityProcess.png";
	public final static String TRICOURSEAWT_CITY_FLOW_BPM="TriCourseAwtCityProcess.bpmn";

	/**
	 * 省公司内训师主动授课流程
	 */
	public final static String TRICOURSEAWT_PROVINCE_PROCESS="TriCourseAwtProvinceProcess";
	public final static String TRICOURSEAWT_PROVINCE_FLOW_PNG="TriCourseAwtProvinceProcess.png";
	public final static String TRICOURSEAWT_PROVINCE_FLOW_BPM="TriCourseAwtProvinceProcess.bpmn";

	/**
	 * 地市公司内训课程预约申请流程
	 */
	public final static String TRICOURSEIOA_CITY_PROCESS="TriCourseIoaCityProcess";
	public final static String TRICOURSEIOA_CITY_FLOW_PNG="TriCourseIoaCityProcess.png";
	public final static String TRICOURSEIOA_CITY_FLOW_BPM="TriCourseIoaCityProcess.bpmn";

	/**
	 * 省公司内训课程预约流程
	 */
	public final static String TRICOURSEIOA_PROVINCE_PROCESS="TriCourseIoaProvinceProcess";
	public final static String TRICOURSEIOA_PROVINCE_FLOW_PNG="TriCourseIoaProvinceProcess.png";
	public final static String TRICOURSEIOA_PROVINCE_FLOW_BPM="TriCourseIoaProvinceProcess.bpmn";

	/**
	 * 省公司内训课程认证流程
	 */
	public final static String TRICOURSEREVIEWIT_PROVINCE_PROCESS="TriCourseReviewItProvinceProcess";
	public final static String TRICOURSEREVIEWIT_PROVINCE_FLOW_PNG="TriCourseReviewItProvinceProcess.png";
	public final static String TRICOURSEREVIEWIT_PROVINCE_FLOW_BPM="TriCourseReviewItProvinceProcess.bpmn";

	/**
	 * 地市公司内训课程认证流程
	 */
	public final static String TRICOURSEREVIEWIT_CITY_PROCESS="TriCourseReviewItCityProcess";
	public final static String TRICOURSEREVIEWIT_CITY_FLOW_PNG="TriCourseReviewItCityProcess.png";
	public final static String TRICOURSEREVIEWIT_CITY_FLOW_BPM="TriCourseReviewItCityProcess.bpmn";



	/**
	 *  领导人员薪酬核算申请流程规则文件
	 */
	public final static String SALLEADERCALCHQPROCESS_FLOW_BPMN="SalLeaderCalcHqProcess.bpmn";
	/**
	 *  领导人员薪酬核算申请规则图片
	 */
	public final static String SALLEADERCALCHQPROCESS_FLOW_PNG="SalLeaderCalcHqProcess.png";
	/**
	 *  领导人员薪酬核算申请流程KEY
	 */
	public final static String SALLEADERCALCHQ_PROCESS = "SalLeaderCalcHqProcess";

	/**
	 *  市二级经理薪酬核算申报流程规则文件
	 */
	public final static String SALMANAGERCALCBOPROCESS_FLOW_BPMN="SalManagerCalcBoProcess.bpmn";
	/**
	 *  市二级经理薪酬核算申报规则图片
	 */
	public final static String SALMANAGERCALCBOPROCESS_FLOW_PNG="SalManagerCalcBoProcess.png";
	/**
	 *  市二级经理薪酬核算申报流程KEY
	 */
	public final static String SALMANAGERCALCBO_PROCESS = "SalManagerCalcBoProcess";


	/**
	 *  省二级经理薪酬核算审核流程规则文件
	 */
	public final static String SALMANAGERCALCHQPROCESS_FLOW_BPMN="SalManagerCalcHqProcess.bpmn";
	/**
	 *  省二级经理薪酬核算审核规则图片
	 */
	public final static String SALMANAGERCALCHQPROCESS_FLOW_PNG="SalManagerCalcHqProcess.png";
	/**
	 *  省二级经理薪酬核算审核流程KEY
	 */
	public final static String SALMANAGERCALCHQ_PROCESS = "SalManagerCalcHqProcess";


	/**
	 *  部门月度绩效奖金审批流程规则文件
	 */
	public final static String SALDEPTPERFORMANCEBONUSPROCESS_FLOW_BPMN="SalDeptPerformanceBonusProcess.bpmn";
	/**
	 *  部门月度绩效奖金审批规则图片
	 */
	public final static String SALDEPTPERFORMANCEBONUSPROCESS_FLOW_PNG="SalDeptPerformanceBonusProcess.png";
	/**
	 *  部门月度绩效奖金审批流程KEY
	 */
	public final static String SALDEPTPERFORMANCEBONUS_PROCESS = "SalDeptPerformanceBonusProcess";

}
