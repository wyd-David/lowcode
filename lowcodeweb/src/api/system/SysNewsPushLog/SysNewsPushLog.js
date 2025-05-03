import request from '@/utils/request'

// 查询消息推送记录列表
export function listSysNewsPushLog(query) {
  return request({
    url: '/SysNewsPushLog/SysNewsPushLog/list',
    method: 'get',
    params: query
  })
}

// 查询消息推送记录详细
export function getSysNewsPushLog(id) {
  return request({
    url: '/SysNewsPushLog/SysNewsPushLog/' + id,
    method: 'get'
  })
}

// 新增消息推送记录
export function addSysNewsPushLog(data) {
  return request({
    url: '/SysNewsPushLog/SysNewsPushLog',
    method: 'post',
    data: data
  })
}

// 修改消息推送记录2
export function updateSysNewsPushLog2(data) {
  return request({
    url: '/SysNewsPushLog/SysNewsPushLog/update',
    method: 'post',
    data: data
  })
}

// 修改消息推送记录
export function updateSysNewsPushLog(data) {
  return request({
    url: '/SysNewsPushLog/SysNewsPushLog',
    method: 'put',
    data: data
  })
}

// 删除消息推送记录
export function delSysNewsPushLog(id) {
  return request({
    url: '/SysNewsPushLog/SysNewsPushLog/' + id,
    method: 'delete'
  })
}

// 导出消息推送记录
export function exportSysNewsPushLog(query) {
  return request({
    url: '/SysNewsPushLog/SysNewsPushLog/export',
    method: 'get',
    params: query
  })
}
// 下载消息推送记录导入模板
export function importTemplate() {
  return request({
    url: '/SysNewsPushLog/SysNewsPushLog/importTemplate',
    method: 'get'
  })
}
// 消息已读
export function updateSysNewsPushLogBySendOutBy() {
  return request({
    url: '/SysNewsPushLog/SysNewsPushLog/updateSysNewsPushLogBySendOutBy',
    method: 'get'
  })
}
// 消息统计
export function selectReadCount() {
  return request({
    url: '/SysNewsPushLog/SysNewsPushLog/selectReadCount',
    method: 'get'
  })
}