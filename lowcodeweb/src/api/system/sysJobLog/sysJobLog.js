import request from '@/utils/request'

// 查询定时任务日志 列表
export function listSysJobLog(query) {
  return request({
    url: '/system/sysJobLog/list',
    method: 'get',
    params: query
  })
}

// 查询定时任务日志 详细
export function getSysJobLog(id) {
  return request({
    url: '/system/sysJobLog/' + id,
    method: 'get'
  })
}

// 新增定时任务日志 
export function addSysJobLog(data) {
  return request({
    url: '/system/sysJobLog',
    method: 'post',
    data: data
  })
}

// 修改定时任务日志 
export function updateSysJobLog(data) {
  return request({
    url: '/system/sysJobLog/edit',
    method: 'post',
    data: data
  })
}

// 删除定时任务日志 
export function delSysJobLog(id) {
  return request({
    url: '/system/sysJobLog/remove?id=' + id,
    method: 'get'
  })
}

// 导出定时任务日志 
export function exportSysJobLog(query) {
  return request({
    url: '/system/sysJobLog/export',
    method: 'get',
    params: query
  })
}
// 下载定时任务日志 导入模板
export function importTemplate() {
  return request({
    url: '/system/sysJobLog/importTemplate',
    method: 'get'
  })
}
// 定时任务日志 禁用/启用
export function disableEnable(query) {
  return request({
    url: '/system/sysJobLog/updateByIds',
    method: 'get',
    params: query
  })
}
