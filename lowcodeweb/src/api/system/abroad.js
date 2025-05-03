import request from '@/utils/request'

// 查询员工出国出境子集列表
export function listAbroad(query) {
  return request({
    url: '/system/abroad/list',
    method: 'get',
    params: query
  })
}

// 查询员工出国出境子集详细
export function getAbroad(recId) {
  return request({
    url: '/system/abroad/' + recId,
    method: 'get'
  })
}

// 新增员工出国出境子集
export function addAbroad(data) {
  return request({
    url: '/system/abroad',
    method: 'post',
    data: data
  })
}

// 修改员工出国出境子集
export function updateAbroad(data) {
  return request({
    url: '/system/abroad',
    method: 'put',
    data: data
  })
}

// 删除员工出国出境子集
export function delAbroad(recId) {
  return request({
    url: '/system/abroad/' + recId,
    method: 'delete'
  })
}

// 导出员工出国出境子集
export function exportAbroad(query) {
  return request({
    url: '/system/abroad/export',
    method: 'get',
    params: query
  })
}