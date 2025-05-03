import request from '@/utils/request'

// 查询员工合同子集列表
export function listContract(query) {
  return request({
    url: '/system/contract/list',
    method: 'get',
    params: query
  })
}

// 查询员工合同子集详细
export function getContract(recId) {
  return request({
    url: '/system/contract/' + recId,
    method: 'get'
  })
}

// 新增员工合同子集
export function addContract(data) {
  return request({
    url: '/system/contract',
    method: 'post',
    data: data
  })
}

// 修改员工合同子集
export function updateContract(data) {
  return request({
    url: '/system/contract',
    method: 'put',
    data: data
  })
}

// 删除员工合同子集
export function delContract(recId) {
  return request({
    url: '/system/contract/' + recId,
    method: 'delete'
  })
}

// 导出员工合同子集
export function exportContract(query) {
  return request({
    url: '/system/contract/export',
    method: 'get',
    params: query
  })
}