import request from '@/utils/request'

// 查询班级列表
export function listOrg(query) {
  return request({
    url: '/system/org/list',
    method: 'get',
    params: query
  })
}

// 查询班级详细
export function getOrg(orgId) {
  return request({
    url: '/system/org/' + orgId,
    method: 'get'
  })
}

// 新增班级
export function addOrg(data) {
  return request({
    url: '/system/org',
    method: 'post',
    data: data
  })
}

// 修改班级
export function updateOrg(data) {
  return request({
    url: '/system/org',
    method: 'put',
    data: data
  })
}

// 删除班级
export function delOrg(orgId) {
  return request({
    url: '/system/org/' + orgId,
    method: 'delete'
  })
}

// 导出班级
export function exportOrg(query) {
  return request({
    url: '/system/org/export',
    method: 'get',
    params: query
  })
}


// 查询部门下拉树结构
export function treeselectOrg() {
  return request({
    url: '/system/org/treeselect',
    method: 'get'
  })
}
