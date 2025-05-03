import request from "@/utils/request";

//获取操作日志列表
export function getLogs(requestMapping, params) {
  return request({
    url: `/xuncha/${requestMapping}/logList`,
    method: "get",
    params,
  });
}