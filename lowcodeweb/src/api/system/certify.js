import request from '@/utils/request'

// 查询员工证书子集列表
export function listCertify(query) {
  return request({
    url: '/system/certify/list',
    method: 'get',
    params: query
  })
}

// 查询员工证书子集详细
export function getCertify(recId) {
  return request({
    url: '/system/certify/' + recId,
    method: 'get'
  })
}

// 新增员工证书子集
export function addCertify(data) {
  return request({
    url: '/system/certify',
    method: 'post',
    data: data
  })
}

// 修改员工证书子集
export function updateCertify(data) {
  return request({
    url: '/system/certify',
    method: 'put',
    data: data
  })
}

// 删除员工证书子集
export function delCertify(recId) {
  return request({
    url: '/system/certify/' + recId,
    method: 'delete'
  })
}

// 导出员工证书子集
export function exportCertify(query) {
  return request({
    url: '/system/certify/export',
    method: 'get',
    params: query
  })
}