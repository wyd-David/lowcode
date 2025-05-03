import request from '@/utils/request'

// 查询租户组织列表
export function listOrganizationinfo(query) {
  return request({
    url: '/system/organizationinfo/list',
    method: 'get',
    params: query
  })
}

// 查询租户组织详细
export function getOrganizationinfo(ouguid) {
  return request({
    url: '/system/organizationinfo/' + ouguid,
    method: 'get'
  })
}

// 新增租户组织
export function addOrganizationinfo(data) {
  return request({
    url: '/system/organizationinfo',
    method: 'post',
    data: data
  })
}

// 修改租户组织
export function updateOrganizationinfo(data) {
  return request({
    url: '/system/organizationinfo',
    method: 'put',
    data: data
  })
}

// 删除租户组织
export function delOrganizationinfo(ouguid) {
  return request({
    url: '/system/organizationinfo/' + ouguid,
    method: 'delete'
  })
}

// 导出租户组织
export function exportOrganizationinfo(query) {
  return request({
    url: '/system/organizationinfo/export',
    method: 'get',
    params: query
  })
}

// 查询部门下拉树结构
export function treeselectOrg() {
  return request({
    url: '/system/organizationinfo/treeselect',
    method: 'get'
  })
}
