import request from '@/utils/request'

// 查询数据服务API列表
export function listInfos(query) {
  return request({
    url: '/system/infos/list',
    method: 'get',
    params: query
  })
}

// 查询数据服务API详细
export function getInfos(id) {
  return request({
    url: '/system/infos/' + id,
    method: 'get'
  })
}

// 新增数据服务API
export function addInfos(data) {
  return request({
    url: '/system/infos',
    method: 'post',
    data: data
  })
}

// 修改数据服务API
export function updateInfos(data) {
  return request({
    url: '/system/infos',
    method: 'put',
    data: data
  })
}

// 删除数据服务API
export function delInfos(id) {
  return request({
    url: '/system/infos/' + id,
    method: 'delete'
  })
}

// 导出数据服务API
export function exportInfos(query) {
  return request({
    url: '/system/infos/export',
    method: 'get',
    params: query
  })
}