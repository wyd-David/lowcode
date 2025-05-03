package com.yabushan.activiti.tasklistener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComplateApplyTaskListener implements TaskListener, JavaDelegate {

	/*@Autowired
	private IBillOrderService billOrderService;*/

	@Override
	public void notify(DelegateTask delegateTask) {

		String userName = (String) delegateTask.getVariable("requestUser");
		delegateTask.setAssignee(userName);
	}

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		//流程完成时，修改业务数据表状态
		String BID = (String) execution.getVariable("BID");
		Long bid=Long.valueOf(BID);
		/*BillOrder billOrder = new BillOrder();
		billOrder.setId(bid);
		billOrder.setStatus("draft");
		billOrderService.updateGfBillOrder(billOrder);*/
	}
}
