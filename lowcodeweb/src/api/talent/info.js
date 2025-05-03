import request from "@/utils/request";

// 查询在库管理_人才库 列表
export function listInfo(query) {
  return request({
    url: "/talent/info/list",
    method: "get",
    params: query,
  });
}

// 查询在库管理_人才库 详细
export function getInfo(id) {
  return request({
    url: "/talent/info/" + id,
    method: "get",
  });
}
// 查询在库未抽调人员 
export function getInLibraryUserlist(query) {
  return request({
    url: "/talent/info/getInLibraryUserlist",
    method: "get",
    params: query,
  });
}
// 新增在库管理_人才库
export function addInfo(data) {
  return request({
    url: "/talent/info",
    method: "post",
    data: data,
  });
}

// 修改在库管理_人才库
export function updateInfo(data) {
  return request({
    url: "/talent/info/edit",
    method: "post",
    data: data,
  });
}

// 删除在库管理_人才库
export function delInfo(id) {
  return request({
    url: "/talent/info/remove?id=" + id,
    method: "get",
  });
}


// 导出在库管理_人才库
export function exportInfo(query) {
  return request({
    url: "/talent/info/export",
    method: "get",
    params: query,
  });
}
// 下载在库管理_人才库 导入模板
export function importTemplate() {
  return request({
    url: "/talent/info/importTemplate",
    method: "get",
  });
}
// 在库管理_人才库 禁用/启用
export function disableEnable(query) {
  return request({
    url: "/talent/info/updateByIds",
    method: "get",
    params: query,
  });
}
