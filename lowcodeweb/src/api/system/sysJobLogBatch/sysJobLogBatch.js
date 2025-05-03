import request from '@/utils/request'

// 查询日志数据批次 列表
export function listSysJobLogBatch(query) {
  return request({
    url: '/system/sysJobLogBatch/list',
    method: 'get',
    params: query
  })
}

// 查询日志数据批次 详细
export function getSysJobLogBatch(id) {
  return request({
    url: '/system/sysJobLogBatch/' + id,
    method: 'get'
  })
}

// 新增日志数据批次 
export function addSysJobLogBatch(data) {
  return request({
    url: '/system/sysJobLogBatch',
    method: 'post',
    data: data
  })
}

// 修改日志数据批次 
export function updateSysJobLogBatch(data) {
  return request({
    url: '/system/sysJobLogBatch/edit',
    method: 'post',
    data: data
  })
}

// 删除日志数据批次 
export function delSysJobLogBatch(id) {
  return request({
    url: '/system/sysJobLogBatch/remove?id=' + id,
    method: 'get'
  })
}

// 导出日志数据批次 
export function exportSysJobLogBatch(query) {
  return request({
    url: '/system/sysJobLogBatch/export',
    method: 'get',
    params: query
  })
}
// 下载日志数据批次 导入模板
export function importTemplate() {
  return request({
    url: '/system/sysJobLogBatch/importTemplate',
    method: 'get'
  })
}
// 日志数据批次 禁用/启用
export function disableEnable(query) {
  return request({
    url: '/system/sysJobLogBatch/updateByIds',
    method: 'get',
    params: query
  })
}
