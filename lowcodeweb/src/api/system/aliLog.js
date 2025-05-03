import request from '@/utils/request'

// 查询聚合日志列表
export function listAliLog(query) {
  return request({
    url: '/system/aliLog/list',
    method: 'get',
    params: query
  })
}

// 查询聚合日志详细
export function getAliLog(logId) {
  return request({
    url: '/system/aliLog/' + logId,
    method: 'get'
  })
}

// 新增聚合日志
export function addAliLog(data) {
  return request({
    url: '/system/aliLog',
    method: 'post',
    data: data
  })
}

// 修改聚合日志
export function updateAliLog(data) {
  return request({
    url: '/system/aliLog',
    method: 'put',
    data: data
  })
}

// 删除聚合日志
export function delAliLog(logId) {
  return request({
    url: '/system/aliLog/' + logId,
    method: 'delete'
  })
}

// 导出聚合日志
export function exportAliLog(query) {
  return request({
    url: '/system/aliLog/export',
    method: 'get',
    params: query
  })
}