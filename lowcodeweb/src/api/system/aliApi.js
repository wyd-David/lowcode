import request from '@/utils/request'

// 查询聚合数据列表
export function listAliApi(query) {
  return request({
    url: '/system/aliApi/list',
    method: 'get',
    params: query
  })
}

// 查询聚合数据详细
export function getAliApi(keyId) {
  return request({
    url: '/system/aliApi/' + keyId,
    method: 'get'
  })
}

// 新增聚合数据
export function addAliApi(data) {
  return request({
    url: '/system/aliApi',
    method: 'post',
    data: data
  })
}

// 修改聚合数据
export function updateAliApi(data) {
  return request({
    url: '/system/aliApi',
    method: 'put',
    data: data
  })
}

// 删除聚合数据
export function delAliApi(keyId) {
  return request({
    url: '/system/aliApi/' + keyId,
    method: 'delete'
  })
}

// 导出聚合数据
export function exportAliApi(query) {
  return request({
    url: '/system/aliApi/export',
    method: 'get',
    params: query
  })
}