import request from '@/utils/request'

// 查询外卖列表
export function listCustomer(query) {
  return request({
    url: '/system/customer/list',
    method: 'get',
    params: query
  })
}

// 查询外卖详细
export function getCustomer(keyId) {
  return request({
    url: '/system/customer/' + keyId,
    method: 'get'
  })
}

// 新增外卖
export function addCustomer(data) {
  return request({
    url: '/system/customer',
    method: 'post',
    data: data
  })
}

// 修改外卖
export function updateCustomer(data) {
  return request({
    url: '/system/customer',
    method: 'put',
    data: data
  })
}

// 删除外卖
export function delCustomer(keyId) {
  return request({
    url: '/system/customer/' + keyId,
    method: 'delete'
  })
}

// 导出外卖
export function exportCustomer(query) {
  return request({
    url: '/system/customer/export',
    method: 'get',
    params: query
  })
}