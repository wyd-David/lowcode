import request from '@/utils/request'

// 查询动态数据源管理列表
export function listAppInfo(query) {
  return request({
    url: '/datasource/appInfo/list',
    method: 'get',
    params: query
  })
}

// 查询动态数据源管理详细
export function getAppInfo(datasourceid) {
  return request({
    url: '/datasource/appInfo/' + datasourceid,
    method: 'get'
  })
}

// 新增动态数据源管理
export function addAppInfo(data) {
  return request({
    url: '/datasource/appInfo',
    method: 'post',
    data: data
  })
}

// 修改动态数据源管理
export function updateAppInfo(data) {
  return request({
    url: '/datasource/appInfo/edit',
    method: 'post',
    data: data
  })
}

// 删除动态数据源管理
export function delAppInfo(datasourceid) {
  return request({
    url: '/datasource/appInfo/remove?datasourceid=' + datasourceid,
    method: 'get'
  })
}

// 导出动态数据源管理
export function exportAppInfo(query) {
  return request({
    url: '/datasource/appInfo/export',
    method: 'get',
    params: query
  })
}
// 下载动态数据源管理导入模板
export function importTemplate() {
  return request({
    url: '/datasource/appInfo/importTemplate',
    method: 'get'
  })
}
// 动态数据源管理禁用/启用
export function disableEnable(query) {
  return request({
    url: '/datasource/appInfo/updateByIds',
    method: 'get',
    params: query
  })
}
