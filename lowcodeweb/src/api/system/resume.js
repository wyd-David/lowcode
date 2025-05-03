import request from '@/utils/request'

// 查询员工个人工作经历子集列表
export function listResume(query) {
  return request({
    url: '/system/resume/list',
    method: 'get',
    params: query
  })
}

// 查询员工个人工作经历子集详细
export function getResume(recId) {
  return request({
    url: '/system/resume/' + recId,
    method: 'get'
  })
}

// 新增员工个人工作经历子集
export function addResume(data) {
  return request({
    url: '/system/resume',
    method: 'post',
    data: data
  })
}

// 修改员工个人工作经历子集
export function updateResume(data) {
  return request({
    url: '/system/resume',
    method: 'put',
    data: data
  })
}

// 删除员工个人工作经历子集
export function delResume(recId) {
  return request({
    url: '/system/resume/' + recId,
    method: 'delete'
  })
}

// 导出员工个人工作经历子集
export function exportResume(query) {
  return request({
    url: '/system/resume/export',
    method: 'get',
    params: query
  })
}