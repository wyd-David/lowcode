import request from "@/utils/request";

//人才推荐部门待办流程推动
export function finishFormTask(id,data) { 
  return request({
    url: "/talent/company/fininshFormTask?taskId=" + id,
    method: "post",
    data: data,
  });
}

//人才推荐进度催办
export function urge(id) {
    console.log(id);
  let fm = new FormData();
  fm.append("ids", id);
  return request({
    url: "/talent/company/urge",
    method: "post",
    data: fm,
  });
}