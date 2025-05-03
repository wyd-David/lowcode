package com.yabushan.activiti.tasklistener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

import java.util.Arrays;

public class GroupTaskListener implements TaskListener{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void notify(DelegateTask delegateTask) {
		//获取传入的userId
		String nextDealUser= delegateTask.getVariable("nextDealUser").toString();

//		String BID = (String) delegateTask.getVariable("BID");
//		String title = (String) delegateTask.getVariable("title");
//		String applyId = (String) delegateTask.getVariable("applyId");
//		String todoText = (String) delegateTask.getVariable("content");
//		String requestUser = (String) delegateTask.getVariable("requestUser");
//		String requestName = (String) delegateTask.getVariable("requestName");
//		String todoUrl = (String) delegateTask.getVariable("todoUrl");
//		String pcTodoUrl = (String) delegateTask.getVariable("pcTodoUrl");


//		ITodoInfoService todoInfoService = SpringUtils.getBean(ITodoInfoService.class);
//		ISysUserService iSysUserService = SpringUtils.getBean(ISysUserService.class);
//		TodoInfo todoInfo = null;
		if(nextDealUser!=null){
			String[] userIds=nextDealUser.split(",");
			if(userIds.length==1){
				delegateTask.addCandidateUser(userIds[0]);
			}else{
				delegateTask.addCandidateUsers(Arrays.asList(userIds));
			}
			/*for(int i=0;i<userIds.length;i++){

				*//**
				 * 生成对应审批人的待办信息
				 *//*
				todoInfo = new TodoInfo();
				todoInfo.setTodoId(StringUtils.getUUID());
				todoInfo.setTodoId(StringUtils.getUUID());
				todoInfo.setBusinessKey(BID);
				todoInfo.setTodoTitle(title);
				todoInfo.setApplyId(applyId);
				todoInfo.setTodoText(todoText);
				todoInfo.setTodoApplicant(requestUser);
				todoInfo.setTodoUrl(todoUrl);
				todoInfo.setTodoHandler(userIds[i]);
				//根据账号查询pc端的用户表
				SysUser sysUser = iSysUserService.selectUserByUserName(userIds[i]);
				todoInfo.setTodoHandlerName(sysUser.getNickName());
				todoInfo.setTodoTpName("工作流程");
				todoInfo.setTodoType("0");//未处理
				todoInfo.setTodoDataType("1");//待办
				todoInfo.setPushStatus("1");//未推送
				todoInfo.setTaskType("1");
				todoInfo.setTaskId(delegateTask.getId());
				todoInfo.setPcUrl(pcTodoUrl);
				//插入待办
				todoInfoService.insertTodoInfo(todoInfo);

				*//**
				 * 推送待办信息到同步对列表
				 *//*
				//TODO




			}*/

		}
	}

}
