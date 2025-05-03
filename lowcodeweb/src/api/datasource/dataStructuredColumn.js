import request from '@/utils/request'

// 查询字段元数据列表
export function listDataStructuredColumn(query) {
  return request({
    url: '/datasource/dataStructuredColumn/list',
    method: 'get',
    params: query
  })
}

// 查询字段元数据详细
export function getDataStructuredColumn(columnId) {
  return request({
    url: '/datasource/dataStructuredColumn/' + columnId,
    method: 'get'
  })
}

// 新增字段元数据
export function addDataStructuredColumn(data) {
  return request({
    url: '/datasource/dataStructuredColumn',
    method: 'post',
    data: data
  })
}

// 修改字段元数据
export function updateDataStructuredColumn(data) {
  return request({
    url: '/datasource/dataStructuredColumn/edit',
    method: 'post',
    data: data
  })
}

// 删除字段元数据
export function delDataStructuredColumn(columnId) {
  return request({
    url: '/datasource/dataStructuredColumn/remove?columnId=' + columnId,
    method: 'get'
  })
}

// 导出字段元数据
export function exportDataStructuredColumn(query) {
  return request({
    url: '/datasource/dataStructuredColumn/export',
    method: 'get',
    params: query
  })
}
// 下载字段元数据导入模板
export function importTemplate() {
  return request({
    url: '/datasource/dataStructuredColumn/importTemplate',
    method: 'get'
  })
}
// 字段元数据禁用/启用
export function disableEnable(query) {
  return request({
    url: '/datasource/dataStructuredColumn/updateByIds',
    method: 'get',
    params: query
  })
}
