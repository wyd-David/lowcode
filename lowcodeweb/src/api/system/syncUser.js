import request from '@/utils/request'

// 查询同步用户信息列表
export function listSyncUser(query) {
  return request({
    url: '/system/syncUser/list',
    method: 'get',
    params: query
  })
}

// 查询同步用户信息详细
export function getSyncUser(id) {
  return request({
    url: '/system/syncUser/' + id,
    method: 'get'
  })
}

// 新增同步用户信息
export function addSyncUser(data) {
  return request({
    url: '/system/syncUser',
    method: 'post',
    data: data
  })
}

// 修改同步用户信息
export function updateSyncUser(data) {
  return request({
    url: '/system/syncUser',
    method: 'put',
    data: data
  })
}

// 删除同步用户信息
export function delSyncUser(id) {
  return request({
    url: '/system/syncUser/' + id,
    method: 'delete'
  })
}

// 导出同步用户信息
export function exportSyncUser(query) {
  return request({
    url: '/system/syncUser/export',
    method: 'get',
    params: query
  })
}
// 下载同步用户信息导入模板
export function importTemplate() {
  return request({
    url: '/system/syncUser/importTemplate',
    method: 'get'
  })
}