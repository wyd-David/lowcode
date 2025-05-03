import request from '@/utils/request'

// 查询用户注册数据源信息列表
export function listMetadatasourcecustomer(query) {
  return request({
    url: '/system/metadatasourcecustomer/list',
    method: 'get',
    params: query
  })
}

// 查询用户注册数据源信息详细
export function getMetadatasourcecustomer(customerId) {
  return request({
    url: '/system/metadatasourcecustomer/' + customerId,
    method: 'get'
  })
}

// 新增用户注册数据源信息
export function addMetadatasourcecustomer(data) {
  return request({
    url: '/system/metadatasourcecustomer',
    method: 'post',
    data: data
  })
}

// 修改用户注册数据源信息
export function updateMetadatasourcecustomer(data) {
  return request({
    url: '/system/metadatasourcecustomer',
    method: 'put',
    data: data
  })
}

// 删除用户注册数据源信息
export function delMetadatasourcecustomer(customerId) {
  return request({
    url: '/system/metadatasourcecustomer/' + customerId,
    method: 'delete'
  })
}

// 导出用户注册数据源信息
export function exportMetadatasourcecustomer(query) {
  return request({
    url: '/system/metadatasourcecustomer/export',
    method: 'get',
    params: query
  })
}