import request from '@/utils/request'

// 查询同步组织信息列表
export function listSyncOrg(query) {
  return request({
    url: '/system/syncOrg/list',
    method: 'get',
    params: query
  })
}

// 查询同步组织信息详细
export function getSyncOrg(id) {
  return request({
    url: '/system/syncOrg/' + id,
    method: 'get'
  })
}

// 新增同步组织信息
export function addSyncOrg(data) {
  return request({
    url: '/system/syncOrg',
    method: 'post',
    data: data
  })
}

// 修改同步组织信息
export function updateSyncOrg(data) {
  return request({
    url: '/system/syncOrg',
    method: 'put',
    data: data
  })
}

// 删除同步组织信息
export function delSyncOrg(id) {
  return request({
    url: '/system/syncOrg/' + id,
    method: 'delete'
  })
}

// 导出同步组织信息
export function exportSyncOrg(query) {
  return request({
    url: '/system/syncOrg/export',
    method: 'get',
    params: query
  })
}
// 下载同步组织信息导入模板
export function importTemplate() {
  return request({
    url: '/system/syncOrg/importTemplate',
    method: 'get'
  })
}