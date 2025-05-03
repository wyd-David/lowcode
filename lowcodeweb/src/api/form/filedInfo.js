import request from '@/utils/request'

// 查询基础表单字段信息列表
export function listFiledInfo(query) {
  return request({
    url: '/form/filedInfo/list',
    method: 'get',
    params: query
  })
}

// 查询基础表单字段信息详细
export function getFiledInfo(filedId) {
  return request({
    url: '/form/filedInfo/' + filedId,
    method: 'get'
  })
}

// 新增基础表单字段信息
export function addFiledInfo(data) {
  return request({
    url: '/form/filedInfo',
    method: 'post',
    data: data
  })
}

// 修改基础表单字段信息
export function updateFiledInfo(data) {
  return request({
    url: '/form/filedInfo/edit',
    method: 'post',
    data: data
  })
}

// 删除基础表单字段信息
export function delFiledInfo(filedId) {
  return request({
    url: '/form/filedInfo/remove?filedId=' + filedId,
    method: 'get'
  })
}

// 导出基础表单字段信息
export function exportFiledInfo(query) {
  return request({
    url: '/form/filedInfo/export',
    method: 'get',
    params: query
  })
}
// 下载基础表单字段信息导入模板
export function importTemplate() {
  return request({
    url: '/form/filedInfo/importTemplate',
    method: 'get'
  })
}
// 基础表单字段信息禁用/启用
export function disableEnable(query) {
  return request({
    url: '/form/filedInfo/updateByIds',
    method: 'get',
    params: query
  })
}
