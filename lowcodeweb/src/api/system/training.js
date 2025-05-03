import request from '@/utils/request'

// 查询员工培训子集列表
export function listTraining(query) {
  return request({
    url: '/system/training/list',
    method: 'get',
    params: query
  })
}

// 查询员工培训子集详细
export function getTraining(recId) {
  return request({
    url: '/system/training/' + recId,
    method: 'get'
  })
}

// 新增员工培训子集
export function addTraining(data) {
  return request({
    url: '/system/training',
    method: 'post',
    data: data
  })
}

// 修改员工培训子集
export function updateTraining(data) {
  return request({
    url: '/system/training',
    method: 'put',
    data: data
  })
}

// 删除员工培训子集
export function delTraining(recId) {
  return request({
    url: '/system/training/' + recId,
    method: 'delete'
  })
}

// 导出员工培训子集
export function exportTraining(query) {
  return request({
    url: '/system/training/export',
    method: 'get',
    params: query
  })
}