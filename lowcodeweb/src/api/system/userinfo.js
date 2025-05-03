import request from '@/utils/request'

// 查询租户用户列表
export function listUserinfo(query) {
  return request({
    url: '/system/userinfo/list',
    method: 'get',
    params: query
  })
}

// 查询租户用户详细
export function getUserinfo(employeeId) {
  return request({
    url: '/system/userinfo/' + employeeId,
    method: 'get'
  })
}

// 新增租户用户
export function addUserinfo(data) {
  return request({
    url: '/system/userinfo',
    method: 'post',
    data: data
  })
}

// 修改租户用户
export function updateUserinfo(data) {
  return request({
    url: '/system/userinfo',
    method: 'put',
    data: data
  })
}

// 删除租户用户
export function delUserinfo(employeeId) {
  return request({
    url: '/system/userinfo/' + employeeId,
    method: 'delete'
  })
}

// 导出租户用户
export function exportUserinfo(query) {
  return request({
    url: '/system/userinfo/export',
    method: 'get',
    params: query
  })
}