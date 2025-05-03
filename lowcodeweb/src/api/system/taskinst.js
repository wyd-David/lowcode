import request from '@/utils/request'

// 查询流程列表列表
export function listTaskinst(query) {
  return request({
    url: '/system/taskinst/list',
    method: 'get',
    params: query
  })
}

// 查询流程列表详细
export function getTaskinst(id) {
  return request({
    url: '/system/taskinst/' + id,
    method: 'get'
  })
}

// 新增流程列表
export function addTaskinst(data) {
  return request({
    url: '/system/taskinst',
    method: 'post',
    data: data
  })
}

// 修改流程列表
export function updateTaskinst(data) {
  return request({
    url: '/system/taskinst',
    method: 'put',
    data: data
  })
}

// 删除流程列表
export function delTaskinst(id) {
  return request({
    url: '/system/taskinst/' + id,
    method: 'delete'
  })
}

// 导出流程列表
export function exportTaskinst(query) {
  return request({
    url: '/system/taskinst/export',
    method: 'get',
    params: query
  })
}