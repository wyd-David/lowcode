import {
  getAllFlowStep,
  validateAuthorize,
  getOneTaskInfo,
  getWorkflowStepBtns,
} from "@/api/workflow";
import router from "@/router";
let process = {
  //业务id
  businessKey: "",
  //待办id
  todoId: "",
  //0无权限 1可查看 2可处理
  authorizeFlag: "-1",
  //流程实例id
  processInstanceId: null,
  //流程定义key
  processDefinitionId: null,
  //任务定义key
  taskDefinitionKey: null,
  //当前节点用户
  curStepUser: "",
  //当前节点名称
  curStepName: "",
  //任务id
  curtaskId: "",
  //下一个节点
  nextStepAndUser: [],
  //退回节点
  backNextStepAndUser: [],
  //流程轨迹
  allFlowStep: [],
  //按钮信息
  hasBtnPerms: [],
  //节点名称-转办用的
  taskName: "",
};

//步骤  1-授权  2-流程轨迹获取基本信息  3-获取操作按钮权限 4-获取下一步处理人
// flag (默认校验，1:不校验) 是否校验权限->用于列表详情查看
export async function fetchWorkFlowInfo(businessKey, todoId, call, flag) {
  if (businessKey) {
    process.businessKey = businessKey;
    process.todoId = todoId;
    process.allFlowStep = [];
    await authorize(flag);
    call(process);
  }else{
    call(process);
  }
}

/**
 * 跳转没有权限页面 0-无权限 1-数据异常
 */
function jumpNoPermission(type) {
  process.authorizeFlag = 0;

  if (process.authorizeFlag == 0) {
    router.push("/noPermission");
  }
}

/**
 * 校验权限
 * @param businessKey
 * @param todoId
 */
async function authorize(call) {
  let that = this;
  let param = {
    businessKey: process.businessKey,
    todoId: process.todoId,
  };

  await validateAuthorize(param).then(async (res) => {
    let authorizeFlag = res.data.flag;
    console.log(authorizeFlag == "0" && call != 1);
    if (authorizeFlag == "0" && call != 1) {
      jumpNoPermission();
      return;
    }
    process.authorizeFlag = authorizeFlag;
    process.todoId = res.data.todoId;
    await getAllFlow(call);
  });
}

//流程日志
async function getAllFlow(call) {
  let fd = new FormData();
  fd.append("flag", 1);
  fd.append("Id", process.businessKey);
  let that = this;
  await getAllFlowStep(fd).then(async (res) => {
    let data = res.data || [];
    if (!data || data.length == 0 || !data[0].historicTaskInstance) {
      if (!call) { 
        jumpNoPermission(1);
      }
      return;
    }
    let allFlowStep = data.map((item) => {
      return {
        name: item.historicTaskInstance ? item.historicTaskInstance.name : "",
        taskDefinitionKey: item.historicTaskInstance ? item.historicTaskInstance.taskDefinitionKey : "",
        historicTaskInstance: item.historicTaskInstance,
        todoUserLoginId: item.todoUserLoginId || "",
        startTime: item.startDateTime ? item.startDateTime : "",
        endTime: item.endDateTime ? item.endDateTime : "",
        message: item.varialbes ? item.varialbes.message : "",
        option: item.varialbes ? item.varialbes.option : "",
        btnMessage: item.varialbes ? item.varialbes.btnMessage : "",
        nextStep: item.nextStep || "",
        nextTodoUser: item.nextTodoUser || "",
        varialbes: item.varialbes || {},
      };
    });

    process.allFlowStep = allFlowStep;
    process.curStepUser = allFlowStep[0].todoUserLoginId || "";
    process.curStepName = allFlowStep[0].name || "";
    console.log(process.curStepName);
    let thisTaskinfo = data[0].historicTaskInstance;
    process.curtaskId = thisTaskinfo.id;
    process.processDefinitionId = thisTaskinfo.processDefinitionId;
    process.processInstanceId = thisTaskinfo.processInstanceId;
    process.taskDefinitionKey = thisTaskinfo.taskDefinitionKey;
    //已完成则无待办
    if (thisTaskinfo.deleteReason == "completed") {
      process.authorizeFlag = 1;
    } else {
      //判断是否有待办
      if (process.authorizeFlag == 2) {
        await initWorkflowStepBtns();
        await initNextStep();
      }
    }
  });
}

/**
 * 初始化节点按钮
 * @returns {Promise<void>}
 */
async function initWorkflowStepBtns() {
  let data = {
    procdefId: process.processDefinitionId,
    stepCode: process.taskDefinitionKey,
  };
  await getWorkflowStepBtns(data).then((res) => {
    process.hasBtnPerms = res.data;
    // process.hasBtnPerms=[{'test':"fdsa"}]
  });
}

/**
 * 下一个节点
 * @param call
 */
async function initNextStep() {
  let fm = new FormData();
  fm.append("taskId", process.curtaskId);
  await getOneTaskInfo(fm).then((res) => {
    if (res.code == 200) {
      let nextStepAndUser = [];
      let backNextStepAndUser = [];


      // 历史处理人
      let historyTask={}
      process.allFlowStep.forEach((item) => {
        if(item.historicTaskInstance.assignee&&!historyTask[item.historicTaskInstance.name]){
          historyTask[item.historicTaskInstance.name]={
            nextUserId:item.historicTaskInstance.assignee,
            nextUserName:item.todoUserLoginId
          }
        }
      })

      res.data.nextStepAndUser.forEach((item) => {
        let nextStepUser = [];
		if(item.nextStep==='结束'){
		
		}else{
			let ids = item.nextUserId.split(",");
			let names = item.nextUserName.split(",");
			for (let i = 0; i < ids.length; i++) {
			  let temp = {
			    nextUserId: ids[i],
			    nextUserName: names[i],
			  };
			  if (ids[i]) {
			    nextStepUser.push(temp);
			  }
			}
		}
        
        let tempNext = {
          nextStep: item.nextStep,
          outLine: item.outLine,
          nextStepUser: nextStepUser,
        };
        // 退回
        if (item.outLine.indexOf("退回") == -1) {
          nextStepAndUser.push(tempNext);
        } else {
          if(historyTask[tempNext.nextStep]){
            tempNext.nextStepUser=[]
            tempNext.nextStepUser.push(historyTask[tempNext.nextStep])
          }
          backNextStepAndUser.push(tempNext);
        }
      });
      process.nextStepAndUser = nextStepAndUser;
      process.backNextStepAndUser = backNextStepAndUser;
      process.taskName = res.data.taskName;
    }
  });
}
