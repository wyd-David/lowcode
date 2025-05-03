import request from "@/utils/request";

//获取下一步处理人
export function getNextDealUser(e) {
  return request({
    url: "/talent/collection/getNextDealUser",
    method: "get",
  });
}

//获取审核记录
export function getAllFlowStep(data) {
  return request({
    url: "/activiti/getAllFlowStep",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded",
    },
    method: "post",
    data: data,
  });
}
//获取审核记录
export function getAllFlowStepMany(data) {
  return request({
    url: "/activiti/getAllFlowStepMany",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded",
    },
    method: "post",
    data: data,
  });
}

//获取流程按钮
export function getOneTaskInfo(data) {
  return request({
    url: "/activiti/getOneTaskInfo",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded",
    },
    method: "post",
    data: data,
  });
}

//流程启动
export function startFormTask(data) {
  return request({
    url: "/activiti/startFormTask",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded",
    },
    method: "post",
    data: data,
  });
}

//流程启动草稿
export function startDraftFormTask(data) {
  let fm = new FormData();
  fm.append("businessKey", data.businessKey);
  fm.append("isAutoFinishFirstStep", "0");
  fm.append("processDefinitionKey", data.processDefinitionKey);
  fm.append("requestUserId", data.requestUserId);
  fm.append(
    "variables",
    JSON.stringify({
      content: "拟稿",
      title: "过程库申报",
      applyId: "xuncha",
      todoUrl: "https://www.baidu.com",
      pcTodoUrl: "https://www.baidu.com",
      urlParam: "type=申报&date=2022-05-27",
      message: "",
    })
  );

  return request({
    url: "/activiti/startFormTask",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded",
    },
    method: "post",
    data: fm,
  });
}

//流程推动
export function fininshFormTask(data) {
  return request({
    url: "/activiti/fininshFormTask",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded",
    },
    method: "post",
    data: data,
  });
}
//转办
export function accidentDeal(data) {
  return request({
    url: "/activiti/fininshFormTask",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded",
    },
    method: "post",
    data: data,
  });
}

//获取所有节点
export function getAllFlowNode(data) {
  return request({
    url: "/activiti/getAllFlowNode",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded",
    },
    method: "post",
    data: data,
  });
}

//校验操作权限
export function validateAuthorize(data) {
  return request({
    url: "/todoinfo/todoinfo/validateAuthorize",
    headers: {
      "Content-Type": "application/json",
    },
    method: "post",
    data: data,
  });
}

//获取节点按钮信息
export function getWorkflowStepBtns(data) {
  return request({
    url: "/todoinfo/todoinfo/getWorkflowStepBtns",
    headers: {
      "Content-Type": "application/json",
    },
    method: "post",
    data: data,
  });
}


//新增常用处理意见
export function addOpinion(data) {
  return request({
    url: "/userController/addOpinion?opinion=" + data.opinion,
    method: "post",
    data: data,
  });
}

//获取常用处理意见
export function getOpinions(query) {
  return request({
    url: "/userController/getOpinions",
    method: "post",
    params:query,
  });
}
