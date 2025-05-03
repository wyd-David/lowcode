import request from '@/utils/request'

// 查询数据资源列表
export function listDataResource(query) {
  return request({
    url: '/datasource/dataResource/list',
    method: 'get',
    params: query
  })
}

// 查询数据资源详细
export function getDataResource(resourceId) {
  return request({
    url: '/datasource/dataResource/' + resourceId,
    method: 'get'
  })
}
//创建报表
export function credateReport(resourceId) {
  return request({
    url: '/datasource/dataResource/credateReport/' + resourceId,
    method: 'get'
  })
}

// 新增数据资源
export function addDataResource(data) {
  return request({
    url: '/datasource/dataResource',
    method: 'post',
    data: data
  })
}

// 修改数据资源
export function updateDataResource(data) {
  return request({
    url: '/datasource/dataResource/edit',
    method: 'post',
    data: data
  })
}

// 删除数据资源
export function delDataResource(resourceId) {
  return request({
    url: '/datasource/dataResource/remove?resourceId=' + resourceId,
    method: 'get'
  })
}

// 导出数据资源
export function exportDataResource(query) {
  return request({
    url: '/datasource/dataResource/export',
    method: 'get',
    params: query
  })
}
// 下载数据资源导入模板
export function importTemplate() {
  return request({
    url: '/datasource/dataResource/importTemplate',
    method: 'get'
  })
}
// 数据资源禁用/启用
export function disableEnable(query) {
  return request({
    url: '/datasource/dataResource/updateByIds',
    method: 'get',
    params: query
  })
}
