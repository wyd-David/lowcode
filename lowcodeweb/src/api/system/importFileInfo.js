import request from '@/utils/request'

// 查询文件上传信息列表
export function listImportFileInfo(query) {
  return request({
    url: '/system/importFileInfo/list',
    method: 'get',
    params: query
  })
}

// 查询文件上传信息详细
export function getImportFileInfo(uid) {
  return request({
    url: '/system/importFileInfo/' + uid,
    method: 'get'
  })
}

// 新增文件上传信息
export function addImportFileInfo(data) {
  return request({
    url: '/system/importFileInfo',
    method: 'post',
    data: data
  })
}

// 修改文件上传信息
export function updateImportFileInfo(data) {
  return request({
    url: '/system/importFileInfo',
    method: 'put',
    data: data
  })
}

// 删除文件上传信息
export function delImportFileInfo(uid) {
  return request({
    url: '/system/importFileInfo/' + uid,
    method: 'delete'
  })
}

// 导出文件上传信息
export function exportImportFileInfo(query) {
  return request({
    url: '/system/importFileInfo/export',
    method: 'get',
    params: query
  })
}
// 下载文件上传信息导入模板
export function importTemplate() {
  return request({
    url: '/system/importFileInfo/importTemplate',
    method: 'get'
  })
}