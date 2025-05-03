import request from "@/utils/request";

// 列表
export function list(data) {
  return request({
    url: "/archives/regulationPerm/list",
    method: "post",
    data: data,
    params:data
  });
}
// 详情
export function getById(params) {
  return request({
    url: "/archives/regulationPerm/getById",
    method: "get",
    params:params
  });
}
// 编辑
export function save(data) {
  return request({
    url: "/archives/regulationPerm/save",
    method: "post",
    data:data
  });
}

// 获取可见范围详情
export function getXunchaList(query) {
  return request({
    url: "/archives/regulationPerm/list",
    method: "get",
    params: query,
  });
}

// 可见范围同步删除
export function xunchaRemove(query) {
  return request({
    url: "/archives/regulationPerm/remove",
    method: "get",
    params: query,
  });
}
