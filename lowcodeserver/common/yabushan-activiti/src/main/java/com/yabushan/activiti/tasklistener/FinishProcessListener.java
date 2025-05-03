package com.yabushan.activiti.tasklistener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;

/**
 * 请假流程
 * @author yabushan
 *
 */
@Service
public class FinishProcessListener implements TaskListener, JavaDelegate {

	@Override
	public void notify(DelegateTask execution)  {


	}

	@Override
	public void execute(DelegateExecution execution) throws Exception {





	}
}
