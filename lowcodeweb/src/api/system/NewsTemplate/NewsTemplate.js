import request from '@/utils/request'

// 查询消息模板列表
export function listNewsTemplate(query) {
  return request({
    url: '/NewsTemplate/NewsTemplate/list',
    method: 'get',
    params: query
  })
}

// 查询消息模板详细
export function getNewsTemplate(id) {
  return request({
    url: '/NewsTemplate/NewsTemplate/' + id,
    method: 'get'
  })
}

// 新增消息模板
export function addNewsTemplate(data) {
  return request({
    url: '/NewsTemplate/NewsTemplate',
    method: 'post',
    data: data
  })
}

// 修改消息模板
export function updateNewsTemplate(data) {
  return request({
    url: '/NewsTemplate/NewsTemplate',
    method: 'put',
    data: data
  })
}

// 删除消息模板
export function delNewsTemplate(id) {
  return request({
    url: '/NewsTemplate/NewsTemplate/' + id,
    method: 'delete'
  })
}

// 导出消息模板
export function exportNewsTemplate(query) {
  return request({
    url: '/NewsTemplate/NewsTemplate/export',
    method: 'get',
    params: query
  })
}
// 下载消息模板导入模板
export function importTemplate() {
  return request({
    url: '/NewsTemplate/NewsTemplate/importTemplate',
    method: 'get'
  })
}