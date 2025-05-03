import request from '@/utils/request'

// 查询部门信息 列表
export function getDepartmentInfoList(query) {
  return request({
    url: '/system/departmentInfo/list',
    method: 'get',
    params: query
  })
}
// 查询部门信息 列表
export function listChineseAndEnglish(query) {
  return request({
    url: '/system/syncOrg/listChineseAndEnglish',
    method: 'get',
    params: query
  })
}
// 查询部门信息 详细
export function getDepartmentInfo(deptId) {
  return request({
    url: '/system/departmentInfo/' + deptId,
    method: 'get'
  })
}

// 新增部门信息
export function addDepartmentInfo(data) {
  return request({
    url: '/system/departmentInfo',
    method: 'post',
    data: data
  })
}

// 修改部门信息
export function updateDepartmentInfo(data) {
  return request({
    url: '/system/departmentInfo/edit',
    method: 'post',
    data: data
  })
}

// 删除部门信息
export function delDepartmentInfo(deptId) {
  return request({
    url: '/system/departmentInfo/remove?deptId=' + deptId,
    method: 'get'
  })
}

// 导出部门信息
export function exportDepartmentInfo(query) {
  return request({
    url: '/system/departmentInfo/export',
    method: 'get',
    params: query
  })
}
// 下载部门信息 导入模板
export function importTemplate() {
  return request({
    url: '/system/departmentInfo/importTemplate',
    method: 'get'
  })
}
// 部门信息 禁用/启用
export function disableEnable(query) {
  return request({
    url: '/system/departmentInfo/updateByIds',
    method: 'get',
    params: query
  })
}
// 修改部门信息
export function updateChineseAndEnglish(data) {
  return request({
    url: '/system/syncOrg/updateChineseAndEnglish',
    method: 'post',
    data: data
  })
}
