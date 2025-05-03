import request from "@/utils/request";

// 待办详情接口
export function getTodoInfo(query) {
  return request({
    url: "/ahead/collection/getTodoInfo",
    method: "get",
    params: query,
  });
}


// 待办流程推动
export function fininshFormTask(data) { 
  return request({
    url: "/ahead/collection/fininshFormTask?taskId="+data.taskId,
    method: "post",
    data:data
  });
}

// 待办暂存
export function temporarySaveDeptOrFile(data) {
  return request({
    url: "/ahead/collection/temporarySaveDeptOrFile",
    method: "post",
    data: data,
  });
}
