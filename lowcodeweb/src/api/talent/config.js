import request from '@/utils/request'

// 查询在库管理_人才标签配置管理 列表
export function listConfig(query) {
  return request({
    url: "/personLabel/list",
    method: "get",
    params: query,
  });
}

//搜索人才进行设置
export function getPersonList(query) { 
  return request({
    url: '/personLabel/getPersonList',
    method: 'get',
    params: query
  })
}
//获取标签列表
export function queryDictList(query) {
  return request({
    url: '/xccommon/base/queryDictList',
    method: 'get',
    params: query
  })
}
//添加标签
export function savePersonLabel(data) {
  return request({
    url: '/personLabel',
    method: 'post',
    data: data
  })
}

//暂存标签
export function savePersonLabelTemporary(data) {
  return request({
    url: '/personLabel/temporarySave',
    method: 'post',
    data: data
  })
}

// 查询在库管理_人才标签配置管理 详细
export function getConfig(id) {
  return request({
    url: "/personLabel/" + id,
    method: "get",
  });
}
//标签管理_流程推动
export function fininshFormTask(data) { 
  return request({
    url: "/personLabel/fininshFormTask?taskId="+data.taskId,
    method: "post",
    data: data,
  });
}

// 新增在库管理_人才标签配置管理 
export function addConfig(data) {
  return request({
    url: '/talent/config',
    method: 'post',
    data: data
  })
}

// 修改在库管理_人才标签配置管理 
export function updateConfig(data) {
  return request({
    url: '/talent/config/edit',
    method: 'post',
    data: data
  })
}

// 删除在库管理_人才标签配置管理 
export function delConfig(id) {
  return request({
    url: "/personLabel/remove?orderNumber=" + id,
    method: "get",
  });
}

// 导出在库管理_人才标签配置管理 
export function exportConfig(query) {
  return request({
    url: '/talent/config/export',
    method: 'get',
    params: query
  })
}
// 下载在库管理_人才标签配置管理 导入模板
export function importTemplate() {
  return request({
    url: '/talent/config/importTemplate',
    method: 'get'
  })
}
// 在库管理_人才标签配置管理 禁用/启用
export function disableEnable(query) {
  return request({
    url: '/talent/config/updateByIds',
    method: 'get',
    params: query
  })
}
