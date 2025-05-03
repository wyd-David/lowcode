import request from '@/utils/request'

// 查询文件上传记录列表
export function listImportFileInfo(query) {
  return request({
    url: '/ImportFileInfo/ImportFileInfo/list',
    method: 'get',
    params: query
  })
}

// 查询文件上传记录详细
export function getImportFileInfo(id) {
  return request({
    url: '/ImportFileInfo/ImportFileInfo/' + id,
    method: 'get'
  })
}

// 新增文件上传记录
export function addImportFileInfo(data) {
  return request({
    url: '/ImportFileInfo/ImportFileInfo',
    method: 'post',
    data: data
  })
}

// 修改文件上传记录
export function updateImportFileInfo(data) {
  return request({
    url: '/ImportFileInfo/ImportFileInfo',
    method: 'put',
    data: data
  })
}

// 删除文件上传记录
export function delImportFileInfo(id) {
  return request({
    url: '/ImportFileInfo/ImportFileInfo/' + id,
    method: 'delete'
  })
}

// 导出文件上传记录
export function exportImportFileInfo(query) {
  return request({
    url: '/ImportFileInfo/ImportFileInfo/export',
    method: 'get',
    params: query
  })
}
// 下载文件上传记录导入模板
export function importTemplate() {
  return request({
    url: '/ImportFileInfo/ImportFileInfo/importTemplate',
    method: 'get'
  })
}