import request from '@/utils/request'

// 查询数据源列表
export function listDatasource(query) {
  return request({
    url: '/system/datasource/list',
    method: 'get',
    params: query
  })
}

// 查询数据源详细
export function getDatasource(datasourceId) {
  return request({
    url: '/system/datasource/' + datasourceId,
    method: 'get'
  })
}

// 新增数据源
export function addDatasource(data) {
  return request({
    url: '/system/datasource',
    method: 'post'
  })
}

// 修改数据源
export function updateDatasource(data) {
  return request({
    url: '/system/datasource',
    method: 'put',
    data: data
  })
}

// 删除数据源
export function delDatasource(datasourceId) {
  return request({
    url: '/system/datasource/' + datasourceId,
    method: 'delete'
  })
}

// 导出数据源
export function exportDatasource(query) {
  return request({
    url: '/system/datasource/export',
    method: 'get',
    params: query
  })
}