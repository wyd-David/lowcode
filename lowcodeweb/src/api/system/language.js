import request from '@/utils/request'

// 查询员工外国语子集列表
export function listLanguage(query) {
  return request({
    url: '/system/language/list',
    method: 'get',
    params: query
  })
}

// 查询员工外国语子集详细
export function getLanguage(recId) {
  return request({
    url: '/system/language/' + recId,
    method: 'get'
  })
}

// 新增员工外国语子集
export function addLanguage(data) {
  return request({
    url: '/system/language',
    method: 'post',
    data: data
  })
}

// 修改员工外国语子集
export function updateLanguage(data) {
  return request({
    url: '/system/language',
    method: 'put',
    data: data
  })
}

// 删除员工外国语子集
export function delLanguage(recId) {
  return request({
    url: '/system/language/' + recId,
    method: 'delete'
  })
}

// 导出员工外国语子集
export function exportLanguage(query) {
  return request({
    url: '/system/language/export',
    method: 'get',
    params: query
  })
}