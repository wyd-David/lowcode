package com.yabushan.common.bpm.service;


import com.yabushan.common.bpm.model.FlowParams;

/**
 * @ClassName:     FlowManageService.java
 * @Description:   调用PIE流程引擎
 * @author:        CJH
 * @version:       V1.0
 * @Date:           2021年9月14日 上午10:06:53
 */
public interface FlowService {
	void initFlow(FlowParams dto);
	/**
	 * @Title: start
	 * @Description: 启动任务 1
	 * @param: 下一步人type/users/secretary
	 * 文档documentId/currentUser/nextActivityId/
	 *     nextUserInfo/approveResult/approveNote/appId/chain/delaySerialNum
	 * @author : CJH...
	 * @date : 2021年9月14日 下午5:37:31
	 * @return String    返回类型
	 * @throws
	 */
	String start(FlowParams dto);
	/**
	 * @Title: commit
	 * @Description: 提交任务 N
	 * @param:  下一步人type/users/secretary
	 * 文档documentId/currentUser/taskId/nextActivityId
	 *     nextUserInfo/approveResult/approveNote/back/backType/break/chain/force
	 * @author : CJH
	 * @date : 2021年9月14日 下午5:37:14
	 * @return String    返回类型
	 * @throws
	 */
	String commit(FlowParams dto);

	/**
	 * @Title: assign
	 * @Description: 转办任务
	 * @param: 下一步人type/users/secretary
	 *       文档documentId/taskId/currentUser/targetUser/approveResult/approveNote
	 * @author : CJH
	 * @date : 2021年9月14日 下午5:38:20
	 * @return String    返回类型
	 * @throws
	 */
	String assign();

	/**
	 * @Title: reject
	 * @Description: 退回任务
	 * @param: documentId/taskId/currentUser/approveResult/approveNote
	 * @author : CJH
	 * @date : 2021年9月14日 下午5:37:14
	 * @return String    返回类型
	 * @throws
	 */
	String reject(FlowParams dto);
	/**
	 * @Title: solicit
	 * @Description: 加签任务
	 * @param: documentId/taskId/currentUser/targetUser/approveResult/approveNote
	 * @author : CJH
	 * @date : 2021年9月14日 下午5:37:14
	 * @return String    返回类型
	 * @throws
	 */
	String solicit();

	/**
	 * @Title: transfer
	 * @Description: 综合员转发任务
	 * @param: documentId/taskId/currentUser/targetUser/approveResult/approveNote
	 * @author : CJH
	 * @date : 2021年9月14日 下午5:37:14
	 * @return String    返回类型
	 * @throws
	 */
	String transfer();

	/**
	 * @Title: withdraw
	 * @Description: 撤回任务
	 * @param: documentId/taskId/currentUser/approveResult/approveNote
	 * @author : CJH
	 * @date : 2021年9月14日 下午5:37:14
	 * @return String    返回类型
	 * @throws
	 */
	String withdraw();

	/**
	 *@MethodName resume
	 *@Desc 恢复文档
	 *@Author YvonneLee
	 *@Param [ documentId/currentUser]
	 *@return java.lang.String
	 *@Date 2020/2/26 18:48
	 **/
	String resume();
	/**
	 *@MethodName suspend
	 *@Desc 暂挂文档（暂时不出现在待办）
	 *@Author YvonneLee
	 *@Param [documentId/currentUser]
	 *@return java.lang.String
	 *@Date 2020/2/26 18:50
	 **/
	String suspend();

	/**
	 * @Title: cancel
	 * @Description: 撤销文档()
	 * @param: documentId/taskId/user
	 * @author : CJH
	 * @date : 2021年9月14日 下午5:37:14
	 * @return String    返回类型
	 * @throws
	 */
	String cancel();

	/**
	 * @Title: close
	 * @Description: 关闭文档（必须是拟稿人才能关闭）
	 * @param: documentId/user/type
	 * @author : CJH
	 * @date : 2021年9月14日 下午5:37:14
	 * @return String    返回类型
	 * @throws
	 */
	String close();
}
