import request from '@/utils/request'

// 查询数据服务API列表
export function listApiInfo(query) {
  return request({
    url: '/system/apiInfo/list',
    method: 'get',
    params: query
  })
}

// 查询数据服务API详细
export function getApiInfo(id) {
  return request({
    url: '/system/apiInfo/' + id,
    method: 'get'
  })
}

// 新增数据服务API
export function addApiInfo(data) {
  return request({
    url: '/system/apiInfo',
    method: 'post',
    data: data
  })
}
export function validateSql(data) {
  return request({
    url: '/datasource/validateSql',
    method: 'post',
    data: data
  })
}
// 修改数据服务API
export function updateApiInfo(data) {
  return request({
    url: '/system/apiInfo/edit',
    method: 'post',
    data: data
  })
}

// 删除数据服务API
export function delApiInfo(id) {
  return request({
    url: '/system/apiInfo/remove?id=' + id,
    method: 'get'
  })
}

// 导出数据服务API
export function exportApiInfo(query) {
  return request({
    url: '/system/apiInfo/export',
    method: 'get',
    params: query
  })
}
// 下载数据服务API导入模板
export function importTemplate() {
  return request({
    url: '/system/apiInfo/importTemplate',
    method: 'get'
  })
}
// 数据服务API禁用/启用
export function disableEnable(query) {
  return request({
    url: '/system/apiInfo/updateByIds',
    method: 'get',
    params: query
  })
}
