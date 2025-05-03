import request from '@/utils/request'

// 查询员工从事岗位子集列表
export function listPosition(query) {
  return request({
    url: '/system/position/list',
    method: 'get',
    params: query
  })
}

// 查询员工从事岗位子集详细
export function getPosition(recId) {
  return request({
    url: '/system/position/' + recId,
    method: 'get'
  })
}

// 新增员工从事岗位子集
export function addPosition(data) {
  return request({
    url: '/system/position',
    method: 'post',
    data: data
  })
}

// 修改员工从事岗位子集
export function updatePosition(data) {
  return request({
    url: '/system/position',
    method: 'put',
    data: data
  })
}

// 删除员工从事岗位子集
export function delPosition(recId) {
  return request({
    url: '/system/position/' + recId,
    method: 'delete'
  })
}

// 导出员工从事岗位子集
export function exportPosition(query) {
  return request({
    url: '/system/position/export',
    method: 'get',
    params: query
  })
}