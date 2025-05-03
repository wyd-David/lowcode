import request from "@/utils/request";

// 列表
export function list(data) {
  return request({
    url: "/archives/regulationApply/workOrderList",
    method: "post",
    data: data,
    params:data
  });
}

// 保存
export function save(data) {
  return request({
    url: "/archives/regulationApply/save",
    method: "post",
    data: data,
  });
}

// 编辑
export function edit(data) {
  return request({
    url: "/archives/regulationApply/edit",
    method: "post",
    data: data,
  });
}
// 编辑
export function deleteById(query) {
  return request({
    url: "/archives/regulationApply/deleteById",
    method: "get",
    params: query,
  });
}

// 详情
export function getById(query) {
  return request({
    url: "/archives/regulationApply/getById",
    method: "get",
    params: query,
  });
}

// 记录下载
export function updateDownloadSum(query) {
  return request({
    url: "/archives/regulationApply/updateDownloadSum",
    method: "get",
    params: query,
  });
}
// 预览
export function preview(query) {
  return request({
    url: "/archives/regulationApply/preview",
    method: "get",
    params: query,
  });
}
// 搜索对应落地制度下拉数据
export function regulationList(data) {
  return request({
    url: "/archives/regulationApply/regulationList",
    method: "post",
    data: data,
  });
}
// 获取制度库资文件列表
export function applyFileList(data) {
  return request({
    url: "/archives/regulationApply/applyFileList",
    method: "post",
    data: data,
    params:data
  });
}

