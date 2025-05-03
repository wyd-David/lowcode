import request from '@/utils/request'

// 查询员工职业资格认证子集列表
export function listQualification(query) {
  return request({
    url: '/system/qualification/list',
    method: 'get',
    params: query
  })
}

// 查询员工职业资格认证子集详细
export function getQualification(recId) {
  return request({
    url: '/system/qualification/' + recId,
    method: 'get'
  })
}

// 新增员工职业资格认证子集
export function addQualification(data) {
  return request({
    url: '/system/qualification',
    method: 'post',
    data: data
  })
}

// 修改员工职业资格认证子集
export function updateQualification(data) {
  return request({
    url: '/system/qualification',
    method: 'put',
    data: data
  })
}

// 删除员工职业资格认证子集
export function delQualification(recId) {
  return request({
    url: '/system/qualification/' + recId,
    method: 'delete'
  })
}

// 导出员工职业资格认证子集
export function exportQualification(query) {
  return request({
    url: '/system/qualification/export',
    method: 'get',
    params: query
  })
}