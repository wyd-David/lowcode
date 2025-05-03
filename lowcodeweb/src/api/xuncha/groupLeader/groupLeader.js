import request from '@/utils/request'

// 查询在库管理_组长管理列表
export function listGroupLeader(query) {
  return request({
    url: '/xuncha/groupLeader/list',
    method: 'get',
    params: query
  })
}

// 查询在库管理_组长管理详细
export function getGroupLeader(id) {
  return request({
    url: '/xuncha/groupLeader/' + id,
    method: 'get'
  })
}

// 新增在库管理_组长管理
export function addGroupLeader(data) {
  return request({
    url: '/xuncha/groupLeader',
    method: 'post',
    data: data
  })
}

// 修改在库管理_组长管理
export function updateGroupLeader(data) {
  return request({
    url: '/xuncha/groupLeader/edit',
    method: 'post',
    data: data
  })
}
// 删除组长标签
export function updateGroupLeaderLabel(query) {
  return request({
    url: '/xuncha/groupLeader/updateGroupLeaderLabel',
    method: 'get',
    params:query
  })
}

// 删除在库管理_组长管理
export function delGroupLeader(id) {
  return request({
    url: '/xuncha/groupLeader/remove?id=' + id,
    method: 'get'
  })
}

// 导出在库管理_组长管理
export function exportGroupLeader(query) {
  return request({
    url: '/xuncha/groupLeader/export',
    method: 'get',
    params: query
  })
}
// 下载在库管理_组长管理导入模板
export function importTemplate() {
  return request({
    url: '/xuncha/groupLeader/importTemplate',
    method: 'get'
  })
}
// 在库管理_组长专业信息
export function getTransferGroupLeaderFieldKey(query) {
  return request({
    url: '/xuncha/groupLeader/getTransferGroupLeaderFieldKey',
    method: 'get',
    params: query
  })
}
// 在库管理_组长离库
export function removeLeader(query) {
  return request({
    url: '/xuncha/groupLeader/removeLeader',
    method: 'get',
    params: query
  })
}

export function queryUnUserList(name) {
  return request({
    url: '/xuncha/groupLeader/queryUnUserList?username=' + name,
    method: 'get',
  })
}
