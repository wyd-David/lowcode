import request from '@/utils/request'

// 查询员工家庭关系子集列表
export function listSocity(query) {
  return request({
    url: '/system/socity/list',
    method: 'get',
    params: query
  })
}

// 查询员工家庭关系子集详细
export function getSocity(recId) {
  return request({
    url: '/system/socity/' + recId,
    method: 'get'
  })
}

// 新增员工家庭关系子集
export function addSocity(data) {
  return request({
    url: '/system/socity',
    method: 'post',
    data: data
  })
}

// 修改员工家庭关系子集
export function updateSocity(data) {
  return request({
    url: '/system/socity',
    method: 'put',
    data: data
  })
}

// 删除员工家庭关系子集
export function delSocity(recId) {
  return request({
    url: '/system/socity/' + recId,
    method: 'delete'
  })
}

// 导出员工家庭关系子集
export function exportSocity(query) {
  return request({
    url: '/system/socity/export',
    method: 'get',
    params: query
  })
}