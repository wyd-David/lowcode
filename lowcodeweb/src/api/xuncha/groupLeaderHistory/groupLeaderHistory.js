import request from '@/utils/request'

// 查询在库管理_组长管理_历史记录列表
export function listGroupLeaderHistory(query) {
  return request({
    url: '/xuncha/groupLeaderHistory/list',
    method: 'get',
    params: query
  })
}

// 查询在库管理_组长管理_历史记录详细
export function getGroupLeaderHistory(id) {
  return request({
    url: '/xuncha/groupLeaderHistory/' + id,
    method: 'get'
  })
}

// 新增在库管理_组长管理_历史记录
export function addGroupLeaderHistory(data) {
  return request({
    url: '/xuncha/groupLeaderHistory',
    method: 'post',
    data: data
  })
}

// 修改在库管理_组长管理_历史记录
export function updateGroupLeaderHistory(data) {
  return request({
    url: '/xuncha/groupLeaderHistory/edit',
    method: 'post',
    data: data
  })
}

// 删除在库管理_组长管理_历史记录
export function delGroupLeaderHistory(id) {
  return request({
    url: '/xuncha/groupLeaderHistory/remove?id=' + id,
    method: 'get'
  })
}

// 导出在库管理_组长管理_历史记录
export function exportGroupLeaderHistory(query) {
  return request({
    url: '/xuncha/groupLeaderHistory/export',
    method: 'get',
    params: query
  })
}
// 下载在库管理_组长管理_历史记录导入模板
export function importTemplate() {
  return request({
    url: '/xuncha/groupLeaderHistory/importTemplate',
    method: 'get'
  })
}
// 在库管理_组长管理_历史记录禁用/启用
export function disableEnable(query) {
  return request({
    url: '/xuncha/groupLeaderHistory/updateByIds',
    method: 'get',
    params: query
  })
}
