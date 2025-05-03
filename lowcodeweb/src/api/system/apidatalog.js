import request from '@/utils/request'

// 查询api日志列表
export function listApidatalog(query) {
  return request({
    url: '/system/apidatalog/list',
    method: 'get',
    params: query
  })
}

// 查询api日志详细
export function getApidatalog(logId) {
  return request({
    url: '/system/apidatalog/' + logId,
    method: 'get'
  })
}

// 新增api日志
export function addApidatalog(data) {
  return request({
    url: '/system/apidatalog',
    method: 'post',
    data: data
  })
}

// 修改api日志
export function updateApidatalog(data) {
  return request({
    url: '/system/apidatalog',
    method: 'put',
    data: data
  })
}

// 删除api日志
export function delApidatalog(logId) {
  return request({
    url: '/system/apidatalog/' + logId,
    method: 'delete'
  })
}

// 导出api日志
export function exportApidatalog(query) {
  return request({
    url: '/system/apidatalog/export',
    method: 'get',
    params: query
  })
}