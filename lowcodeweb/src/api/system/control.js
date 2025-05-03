import request from '@/utils/request'

// 查询 角色授权控制列表
export function listControl(query) {
  return request({
    url: '/system/control/list',
    method: 'get',
    params: query
  })
}

// 查询 角色授权控制详细
export function getControl(roleControlId) {
  return request({
    url: '/system/control/' + roleControlId,
    method: 'get'
  })
}

// 新增 角色授权控制
export function addControl(data) {
  return request({
    url: '/system/control',
    method: 'post',
    data: data
  })
}

// 修改 角色授权控制
export function updateControl(data) {
  return request({
    url: '/system/control',
    method: 'put',
    data: data
  })
}

// 删除 角色授权控制
export function delControl(roleControlId) {
  return request({
    url: '/system/control/' + roleControlId,
    method: 'delete'
  })
}

// 导出 角色授权控制
export function exportControl(query) {
  return request({
    url: '/system/control/export',
    method: 'get',
    params: query
  })
}
// 下载 角色授权控制导入模板
export function importTemplate() {
  return request({
    url: '/system/control/importTemplate',
    method: 'get'
  })
}
//  角色授权控制禁用/启用
export function disableEnable(query) {
  return request({
    url: '/system/control/updateByIds',
    method: 'get',
    params: query
  })
}
