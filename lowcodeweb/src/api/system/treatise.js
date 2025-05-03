import request from '@/utils/request'

// 查询员工论文著作子集列表
export function listTreatise(query) {
  return request({
    url: '/system/treatise/list',
    method: 'get',
    params: query
  })
}

// 查询员工论文著作子集详细
export function getTreatise(recId) {
  return request({
    url: '/system/treatise/' + recId,
    method: 'get'
  })
}

// 新增员工论文著作子集
export function addTreatise(data) {
  return request({
    url: '/system/treatise',
    method: 'post',
    data: data
  })
}

// 修改员工论文著作子集
export function updateTreatise(data) {
  return request({
    url: '/system/treatise',
    method: 'put',
    data: data
  })
}

// 删除员工论文著作子集
export function delTreatise(recId) {
  return request({
    url: '/system/treatise/' + recId,
    method: 'delete'
  })
}

// 导出员工论文著作子集
export function exportTreatise(query) {
  return request({
    url: '/system/treatise/export',
    method: 'get',
    params: query
  })
}