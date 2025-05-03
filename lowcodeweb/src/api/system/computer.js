import request from '@/utils/request'

// 查询员工计算机能力子集列表
export function listComputer(query) {
  return request({
    url: '/system/computer/list',
    method: 'get',
    params: query
  })
}

// 查询员工计算机能力子集详细
export function getComputer(recId) {
  return request({
    url: '/system/computer/' + recId,
    method: 'get'
  })
}

// 新增员工计算机能力子集
export function addComputer(data) {
  return request({
    url: '/system/computer',
    method: 'post',
    data: data
  })
}

// 修改员工计算机能力子集
export function updateComputer(data) {
  return request({
    url: '/system/computer',
    method: 'put',
    data: data
  })
}

// 删除员工计算机能力子集
export function delComputer(recId) {
  return request({
    url: '/system/computer/' + recId,
    method: 'delete'
  })
}

// 导出员工计算机能力子集
export function exportComputer(query) {
  return request({
    url: '/system/computer/export',
    method: 'get',
    params: query
  })
}