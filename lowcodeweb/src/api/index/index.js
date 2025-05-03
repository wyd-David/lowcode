import request from "@/utils/request";
export function getTodoList(params) {
    return request({
      url: "/todoinfo/todoinfo/list",
      method: "post",
      params: params,
    });
}
