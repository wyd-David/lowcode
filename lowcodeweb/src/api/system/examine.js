import request from '@/utils/request'

// 查询员工考核情况子集列表
export function listExamine(query) {
  return request({
    url: '/system/examine/list',
    method: 'get',
    params: query
  })
}

// 查询员工考核情况子集详细
export function getExamine(recId) {
  return request({
    url: '/system/examine/' + recId,
    method: 'get'
  })
}

// 新增员工考核情况子集
export function addExamine(data) {
  return request({
    url: '/system/examine',
    method: 'post',
    data: data
  })
}

// 修改员工考核情况子集
export function updateExamine(data) {
  return request({
    url: '/system/examine',
    method: 'put',
    data: data
  })
}

// 删除员工考核情况子集
export function delExamine(recId) {
  return request({
    url: '/system/examine/' + recId,
    method: 'delete'
  })
}

// 导出员工考核情况子集
export function exportExamine(query) {
  return request({
    url: '/system/examine/export',
    method: 'get',
    params: query
  })
}