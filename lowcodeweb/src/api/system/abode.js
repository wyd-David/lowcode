import request from '@/utils/request'

// 查询员工家庭住址子集列表
export function listAbode(query) {
  return request({
    url: '/system/abode/list',
    method: 'get',
    params: query
  })
}

// 查询员工家庭住址子集详细
export function getAbode(recId) {
  return request({
    url: '/system/abode/' + recId,
    method: 'get'
  })
}

// 新增员工家庭住址子集
export function addAbode(data) {
  return request({
    url: '/system/abode',
    method: 'post',
    data: data
  })
}

// 修改员工家庭住址子集
export function updateAbode(data) {
  return request({
    url: '/system/abode',
    method: 'put',
    data: data
  })
}

// 删除员工家庭住址子集
export function delAbode(recId) {
  return request({
    url: '/system/abode/' + recId,
    method: 'delete'
  })
}

// 导出员工家庭住址子集
export function exportAbode(query) {
  return request({
    url: '/system/abode/export',
    method: 'get',
    params: query
  })
}