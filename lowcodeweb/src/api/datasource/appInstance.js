import request from '@/utils/request'

// 查询分配实例列表
export function listAppInstance(query) {
  return request({
    url: '/datasource/appInstance/list',
    method: 'get',
    params: query
  })
}

// 查询分配实例详细
export function getAppInstance(instanceId) {
  return request({
    url: '/datasource/appInstance/' + instanceId,
    method: 'get'
  })
}

// 新增分配实例
export function addAppInstance(data) {
  return request({
    url: '/datasource/appInstance',
    method: 'post',
    data: data
  })
}

// 修改分配实例
export function updateAppInstance(data) {
  return request({
    url: '/datasource/appInstance/edit',
    method: 'post',
    data: data
  })
}

// 删除分配实例
export function delAppInstance(instanceId) {
  return request({
    url: '/datasource/appInstance/remove?instanceId=' + instanceId,
    method: 'get'
  })
}

// 导出分配实例
export function exportAppInstance(query) {
  return request({
    url: '/datasource/appInstance/export',
    method: 'get',
    params: query
  })
}
// 下载分配实例导入模板
export function importTemplate() {
  return request({
    url: '/datasource/appInstance/importTemplate',
    method: 'get'
  })
}
// 分配实例禁用/启用
export function disableEnable(query) {
  return request({
    url: '/datasource/appInstance/updateByIds',
    method: 'get',
    params: query
  })
}
