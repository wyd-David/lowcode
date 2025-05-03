package com.yabushan.common.bpm.service.imp;

import com.alibaba.fastjson.JSONObject;
import com.yabushan.common.bpm.model.AmsConstants;
import com.yabushan.common.bpm.model.FlowParams;
import com.yabushan.common.bpm.service.FlowService;
import com.yabushan.common.bpm.utils.HttpToolUtil;
import com.yabushan.common.bpm.utils.VarUtil;
import com.yabushan.common.utils.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class FlowServicesImpl implements FlowService {
	private final Logger log = LogManager.getLogger(getClass());
	private String getHttpPortal() {
		return AmsConstants.PBMURL;
	}
	private FlowParams dto = null;
	@Override
	public void initFlow(FlowParams dto){
		this.dto = dto;
		if(this.dto == null){
			this.dto = new FlowParams();
		}
		if(StringUtils.isNotBlank(this.dto.getNextUserInfo())){
			JSONObject o = JSONObject.parseObject(this.dto.getNextUserInfo());
			if(o != null){
				this.dto.setType(o.getString("type"));
				this.dto.setUsers(o.getString("users"));
				this.dto.setSecretary(o.getString("secretary"));
			}
		}
	}
	@Override
	public String start(FlowParams dto){
		String result = "";
		Map<String,Object> params = new HashMap<>();
		initFlow(dto);
        params.put("documentId", this.dto.getDocumentId());//文档编号
		params.put("currentUser", this.dto.getCurrentUser());//当前用户
		params.put("nextActivityId", this.dto.getNextActivityId());//下一环节ID
		params.put("nextUserInfo",  this.dto.getNextUserInfo());
		params.put("approveResult", this.dto.getApproveResult());//处理结果
		params.put("approveNote", this.dto.getApproveNote());//处理意见
		String appId = this.dto.getAppId();
		params.put("appId", appId == null?"":appId);//应用ID
		params.put("chain", this.dto.getChain());//审批链
		params.put("delaySerialNum", this.dto.getDelaySerialNum());//是否延迟创建文号
	   try {
		result = HttpToolUtil.post(params, getHttpPortal()+AmsConstants.PBMURL_Start);
		} catch (Exception e) {
			log.error("流程管理启动异常"+e.getLocalizedMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public String commit(FlowParams dto){
		initFlow(dto);
		String result = "";
		Map<String,Object> params = new HashMap<>();
		params.put("documentId", this.dto.getDocumentId());//文档编号
		params.put("currentUser", this.dto.getCurrentUser());//当前用户
		params.put("taskId", this.dto.getTaskId());//任务编号
		params.put("nextActivityId", this.dto.getNextActivityId());//下一环节ID
		params.put("nextUserInfo",  this.dto.getNextUserInfo());
		params.put("approveResult", this.dto.getApproveResult());//处理结果
		params.put("approveNote", this.dto.getApproveNote());//处理意见
		params.put("back", this.dto.isBack());//默认false 是否下一步骤处理完之后回到此环节
		params.put("backType", this.dto.getBackType());//强制返回方式
		params.put("break", this.dto.isBreak_());//默认false 是否中断会签直接到下一个环节
		params.put("chain", this.dto.getChain());//审批链
		params.put("force", this.dto.isForce());//默认false 是否超时跳转
	   try {
		result = HttpToolUtil.post(params, getHttpPortal()+AmsConstants.PBMURL_Commit);
		} catch (Exception e) {
			log.error("流程管理提交任务异常"+e.getLocalizedMessage());
			e.printStackTrace();
		} 
		return result;
	}
	@Override
	public String assign(){
		String result = "";
		Map<String,Object> params = new HashMap<>();
		params.put("documentId", this.dto.getDocumentId());//文档编号
		params.put("taskId", this.dto.getTaskId());//任务编号
		params.put("currentUser", this.dto.getCurrentUser());//当前用户
		params.put("targetUser", this.dto.getTargetUser());//目标用户
		params.put("approveResult",this.dto.getApproveResult());//处理结果
		params.put("approveNote", this.dto.getApproveNote());//处理意见
	   try {
		result = HttpToolUtil.post(params, getHttpPortal()+AmsConstants.PBMURL_Assign);
		} catch (Exception e) {
			log.error("流程管理转办任务异常"+e.getLocalizedMessage());
			e.printStackTrace();
		} 
		return result;
	}
	
	@Override
	public String reject(FlowParams dto){
		initFlow(dto);
		String result = "";
		Map<String,Object> params = new HashMap<>();
		params.put("documentId", this.dto.getDocumentId());//文档编号
		params.put("taskId", this.dto.getTaskId());//任务编号
		params.put("currentUser", this.dto.getCurrentUser());//当前用户
		params.put("approveResult", this.dto.getApproveResult());//处理结果
		params.put("approveNote", this.dto.getApproveNote());//处理意见
	   try {
		result = HttpToolUtil.post(params, getHttpPortal()+AmsConstants.PBMURL_Reject);
		} catch (Exception e) {
			log.error("流程管理退回任务异常"+e.getLocalizedMessage());
			e.printStackTrace();
		} 
		return result;
	}
	
	@Override
	public String solicit(){
		String result = "";
		Map<String,Object> params = new HashMap<>();
		params.put("documentId", this.dto.getDocumentId());//文档编号
		params.put("taskId", this.dto.getTaskId());//任务编号
		params.put("currentUser", this.dto.getCurrentUser());//当前用户
		params.put("targetUser", this.dto.getTargetUser());//目标用户（多个用户逗号分隔）
		params.put("approveResult", this.dto.getApproveResult());//处理结果
		params.put("approveNote", this.dto.getApproveNote());//处理意见
	   try {
		result = HttpToolUtil.post(params, getHttpPortal()+AmsConstants.PBMURL_Solicit);
		} catch (Exception e) {
			log.error("流程管理加签任务异常"+e.getLocalizedMessage());
			e.printStackTrace();
		} 
		return result;
	}
	@Override
	public String transfer(){
		String result = "";
		Map<String,Object> params = new HashMap<>();
		params.put("documentId", this.dto.getDocumentId());//文档编号
		params.put("taskId", this.dto.getTaskId());//任务编号
		params.put("currentUser", this.dto.getCurrentUser());//当前用户
		params.put("targetUser", this.dto.getTargetUser());//目标用户（多个用户逗号分隔）
		params.put("approveResult", this.dto.getApproveResult());//处理结果
		params.put("approveNote", this.dto.getApproveNote());//处理意见
	   try {
		result = HttpToolUtil.post(params, getHttpPortal()+AmsConstants.PBMURL_Transfer);
		} catch (Exception e) {
			log.error("流程管理综合员转发任务异常"+e.getLocalizedMessage());
			e.printStackTrace();
		} 
		return result;
	}
	@Override
	public String withdraw(){
		String result = "";
		Map<String,Object> params = new HashMap<>();
		params.put("documentId", this.dto.getDocumentId());//文档编号
		params.put("taskId", this.dto.getTaskId());//任务编号
		params.put("currentUser", this.dto.getCurrentUser());//当前用户
		params.put("approveResult", this.dto.getApproveResult());//处理结果
		params.put("approveNote", this.dto.getApproveNote());//处理意见
	   try {
		result = HttpToolUtil.post(params, getHttpPortal()+AmsConstants.PBMURL_Withdraw);
		} catch (Exception e) {
			log.error("流程管理撤回任务异常"+e.getLocalizedMessage());
			e.printStackTrace();
		} 
		return result;
	}

	@Override
	public String resume() {
		String result = "";
		Map<String,Object> params = new HashMap<>();
		params.put("documentId", this.dto.getDocumentId());//文档编号
		params.put("currentUser", this.dto.getCurrentUser());//当前用户
		params.put(AmsConstants.PIE_ACCESS_CODE, VarUtil.get(AmsConstants.PIE_ACCESS_CODE,""));
		params.put(AmsConstants.PIE_ACCESS_TOKEN, VarUtil.get(AmsConstants.PIE_ACCESS_TOKEN,""));
		Map<String,Object> headers = new HashMap<>();
		headers.put(AmsConstants.PIE_ACCESS_CODE, VarUtil.get(AmsConstants.PIE_ACCESS_CODE,""));
		headers.put(AmsConstants.PIE_ACCESS_TOKEN, VarUtil.get(AmsConstants.PIE_ACCESS_TOKEN,""));
		try {
			result = HttpToolUtil.post(params, getHttpPortal()+AmsConstants.PBMURL_Resume,headers);
		} catch (Exception e) {
			log.error("流程管理恢复任务异常"+e.getLocalizedMessage());
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String suspend() {
		String result = "";
		Map<String,Object> params = new HashMap<>();
		params.put("documentId", this.dto.getDocumentId());//文档编号
		params.put("currentUser", this.dto.getCurrentUser());//当前用户
		params.put(AmsConstants.PIE_ACCESS_CODE, VarUtil.get(AmsConstants.PIE_ACCESS_CODE,""));
		params.put(AmsConstants.PIE_ACCESS_TOKEN, VarUtil.get(AmsConstants.PIE_ACCESS_TOKEN,""));
		Map<String,Object> headers = new HashMap<>();
		headers.put(AmsConstants.PIE_ACCESS_CODE, VarUtil.get(AmsConstants.PIE_ACCESS_CODE,""));
		headers.put(AmsConstants.PIE_ACCESS_TOKEN, VarUtil.get(AmsConstants.PIE_ACCESS_TOKEN,""));
		try {
			result = HttpToolUtil.post(params, getHttpPortal()+AmsConstants.PBMURL_Suspend,headers);
		log.info(result);
		} catch (Exception e) {
			log.error("流程管理暂挂任务异常"+e.getLocalizedMessage());
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String cancel(){
		String result = "";
		Map<String,Object> params = new HashMap<>();
		params.put("documentId", this.dto.getDocumentId());//文档编号
		params.put("taskId", this.dto.getTaskId());//任务编号
		params.put("user", this.dto.getUser());//操作用户
	   try {
		result = HttpToolUtil.post(params, getHttpPortal()+AmsConstants.PBMURL_Cancel);
		} catch (Exception e) {
			log.error("流程管理撤销文档异常"+e.getLocalizedMessage());
			e.printStackTrace();
		} 
		return result;
	}
	
	@Override
	public String close(){
		String result = "";
		Map<String,Object> params = new HashMap<>();
		params.put("documentId", this.dto.getDocumentId());//文档编号
		params.put("user", this.dto.getUser());//操作用户
		params.put("type", this.dto.getType());//操作类型
	   try {
		result = HttpToolUtil.post(params, getHttpPortal()+AmsConstants.PBMURL_Close);
		} catch (Exception e) {
			log.error("流程管理关闭文档异常"+e.getLocalizedMessage());
			e.printStackTrace();
		} 
		return result;
	}

	
}
