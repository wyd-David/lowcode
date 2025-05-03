package com.yabushan.activiti.service;

import com.yabushan.activiti.domain.HistoryTaskInfo;
import com.yabushan.activiti.util.StepCodeName;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class workflowUtilService {

	@Autowired
	private EngineService engineService;
	@Autowired
	private FlowService flowEngineService;

	@Autowired
	private ProcessDefinitionService processDefinitionService;

	public void WriterStatus(DelegateExecution exception){
		System.out.println("自动节点！");
	}

	/**
	 * 获取所有流程人工节点
	 * @param processDefId 流程ID
	 * @return 节点名称name 和 code(即id)的集合.<br/>
	 * 如：[{name=开始,code=start},{name=步骤1,code=step1}]
	 */
	public List<StepCodeName> getJbpmNode(String processDefId) {
		ProcessDefinitionEntity  pd = (ProcessDefinitionEntity) engineService.getRepositoryService().getProcessDefinition(processDefId);
		List<ActivityImpl> list = pd.getActivities() ;
		List<StepCodeName> codeNames = new ArrayList<StepCodeName>();
		StepCodeName stepCodeName=null;
		for (ActivityImpl act : list) {
			if(!"exclusiveGateway".equals(act.getProperty("type").toString())) {// 去除Gateway节点
				stepCodeName = new StepCodeName();
				Map<String,String> map = new HashMap<String, String>() ;
				stepCodeName.setStepCode(act.getId());
				stepCodeName.setStepName(act.getProperty("name")+"");
				codeNames.add(stepCodeName);
			}
		}
		return codeNames ;
	}

	/**
	 * 获取所有流程人工节点
	 * @param processDefId 流程ID
	 * @return 节点名称name 和 code(即id)的集合.<br/>
	 * 如：[{name=开始,code=start},{name=步骤1,code=step1}]
	 */
	public List<Map<String,String>> getJbpmNodeMap(String processDefId) {
		ProcessDefinitionEntity  pd = (ProcessDefinitionEntity) engineService.getRepositoryService().getProcessDefinition(processDefId);
		List<ActivityImpl> list = pd.getActivities() ;
		List<Map<String,String>> nodeMap = new ArrayList<Map<String,String>>() ;
		for (ActivityImpl act : list) {
			//子流程特殊处理
			if("subProcess".equals(act.getProperty("type").toString())) {
				List<ActivityImpl> subList = act.getActivities();
				for (ActivityImpl subAct : subList) {
					if(!"exclusiveGateway".equals(subAct.getProperty("type").toString()) && !"parallelGateway".equals(subAct.getProperty("type").toString())
							&& !"startEvent".equals(subAct.getProperty("type").toString()) && !"endEvent".equals(subAct.getProperty("type").toString())) {// 去除Gateway节点
						Map<String,String> map = new HashMap<String, String>() ;
						map.put("name", subAct.getProperty("name").toString()) ;
						map.put("code", subAct.getId()) ;
						nodeMap.add(map) ;
					}
				}
			}
			else if(!"exclusiveGateway".equals(act.getProperty("type").toString()) && "userTask".equals(act.getProperty("type").toString()) && !"parallelGateway".equals(act.getProperty("type").toString())) {// 去除Gateway节点
				Map<String,String> map = new HashMap<String, String>() ;
				map.put("name", act.getProperty("name")+"") ;
				map.put("code", act.getId()+"") ;
				nodeMap.add(map) ;
			}
		}
		return nodeMap ;
	}

	/**
	 * 根据当前任务获取当前任务连线到的的下一个用户任务（可能是多个节点）
	 * @param task
	 * @return
	 */
	public List<PvmActivity> getNextUserActivity(Task task) {
		List<PvmActivity> list = new ArrayList<PvmActivity>();
		String processDefinitonId=task.getProcessDefinitionId();
		//1.根据当前任务获取当前流程的流程定义，然后根据流程定义获得所有的节点：
		ProcessDefinitionEntity def = (ProcessDefinitionEntity)
				engineService.getRepositoryService().getProcessDefinition(processDefinitonId);
		List<ActivityImpl> activitiList = def.getActivities();  //rs是指RepositoryService的实例
		//2.根据任务获取当前流程执行ID，执行实例以及当前流程节点的ID：
		String excId = task.getExecutionId();
		ExecutionEntity execution = (ExecutionEntity) engineService.getRuntimeService().createExecutionQuery().executionId(excId).singleResult();
		String activitiId = execution.getActivityId();
		//3.循环activitiList并判断出当前流程所处节点然后得到当前节点实例，
		//根据节点实例获取所有从当前节点出发的路径，然后根据路径获得下一个节点实例：
		for (ActivityImpl activityImpl : activitiList) {
			if("userTask".equals(activityImpl.getProperty("type")) && activitiId.equals(activityImpl.getId())){
    				//获取该节点出去的所有线路
    				List<PvmTransition> outTransitions =activityImpl.getOutgoingTransitions();
    				List<PvmTransition> outTransitions1 = null;
    				PvmActivity activity = null;
    				PvmActivity activity1 = null;
    				for (PvmTransition pvmTransition : outTransitions) {
    					//获取线路的终点节点
    					activity=pvmTransition.getDestination();
    					if(activity.getProperty("type").toString().equals("userTask")){
    						list.add(activity);
    					} else {
    						outTransitions1 = activity.getOutgoingTransitions();
    						for(PvmTransition pvmTransition1 : outTransitions1) {
    	    						//获取线路的终点节点
    	    						activity1 = pvmTransition1.getDestination();
    							if(activity1.getProperty("type").toString().equals("userTask")){
    	    							list.add(activity1);
    	    						}
    						}
    					}
				}
			}
		}
		return list;
	}




	//根据流程实例ID封装历史流程步骤
	public List<HistoryTaskInfo> getAllHistoryStep(String processDefId){
		List<HistoryTaskInfo> result =new ArrayList<HistoryTaskInfo>();
		HistoryTaskInfo historyTaskInfo=null;
		List<HistoricTaskInstance> list=flowEngineService.getHistoricProcessInstanceByProcessDefId(processDefId);
		if(list!=null && list.size()>0){
			for (HistoricTaskInstance historicTaskInstance : list) {

				historyTaskInfo=new HistoryTaskInfo();
				historyTaskInfo.setHistoricTaskInstance(historicTaskInstance);
				if(historicTaskInstance.getEndTime()!=null){
				List<HistoricVariableInstance> historicVariableInstances=flowEngineService.getHistoricVariableInstancesByTaskId(historicTaskInstance.getId());
					for (HistoricVariableInstance historicVariableInstance : historicVariableInstances) {
						if(historicVariableInstance.getVariableName().equals("info")){
							historyTaskInfo.setInfo(historicVariableInstance.getValue().toString());
						}
					}

				}else{
					historyTaskInfo.setInfo("");
				}
				result.add(historyTaskInfo);
			}
		}
		return result;
	}

	public  byte[] readBody(HttpServletRequest request) throws IOException {
        //获取请求文本字节长度
        int formDataLength = request.getContentLength();
		byte body[] = new byte[formDataLength];
		DataInputStream dataStream=null;
        //取得ServletInputStream输入流对象
       try{
		   dataStream = new DataInputStream(request.getInputStream());
		   int totalBytes = 0;
		   while (totalBytes < formDataLength) {
			   int bytes = dataStream.read(body, totalBytes, formDataLength);
			   totalBytes += bytes;
		   }
	   }catch (Exception e){
		   System.out.println("获取请求文本字节长度失败"+e);
	   }finally {
        if(dataStream!=null){
        	dataStream.close();
        }

	   }
        return body;
    }
    public  String getFileName(String requestBody,String name) {
            String fileName = requestBody.substring(requestBody.indexOf(name)+name.length()+1).trim();
            fileName=fileName.replaceAll("\n", "").replaceAll(" ", "");
            fileName=fileName.substring(0,fileName.indexOf("-"));
           // fileName = fileName.substring(fileName.length(), fileName.indexOf("\n")).trim();
          /*  fileName = fileName.substring(fileName.indexOf("\n") + 1, fileName.indexOf("\""));*/
            return fileName;
    }
    //取得上传文件名
    public  String getFileName(Part part) {
        String header = part.getHeader("Content-Disposition");
        String fileName = header.substring(header.indexOf("filename=\"") + 10, header.lastIndexOf("\""));

        return fileName;
    }

    //存储文件
   /* public String writeTo(String fileName, Part part) throws IOException, FileNotFoundException {
    	String path = "E:/workspace/" + fileName;
        InputStream in = part.getInputStream();
		OutputStream out=null;
       try{
		    out = new FileOutputStream(path);
		   byte[] buffer = new byte[1024];
		   int length = -1;
		   while ((length = in.read(buffer)) != -1) {
			   out.write(buffer, 0, length);
		   }
	   }
       catch (Exception e){
       	System.out.println(e.toString());
	   }finally {
		   in.close();
		   out.close();
	   }
		return path;
    }*/


	public static void main(String[] args) {
		String requestBody="sdfc    1dsf 2sdfdsf     3sdfd";
		 System.out.println(requestBody.replaceAll(" ", ""));
	}
}


