import request from '@/utils/request'

// 查询员工教育子集列表
export function listEducation(query) {
  return request({
    url: '/system/education/list',
    method: 'get',
    params: query
  })
}

// 查询员工教育子集详细
export function getEducation(recId) {
  return request({
    url: '/system/education/' + recId,
    method: 'get'
  })
}

// 新增员工教育子集
export function addEducation(data) {
  return request({
    url: '/system/education',
    method: 'post',
    data: data
  })
}

// 修改员工教育子集
export function updateEducation(data) {
  return request({
    url: '/system/education',
    method: 'put',
    data: data
  })
}

// 删除员工教育子集
export function delEducation(recId) {
  return request({
    url: '/system/education/' + recId,
    method: 'delete'
  })
}

// 导出员工教育子集
export function exportEducation(query) {
  return request({
    url: '/system/education/export',
    method: 'get',
    params: query
  })
}