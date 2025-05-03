import request from '@/utils/request'

// 查询标签管理 列表
export function listXunchaLabelManage(query) {
  return request({
    url: '/xuncha/XunchaLabelManage/list',
    method: 'get',
    params: query
  })
}

// 查询标签管理 详细
export function getXunchaLabelManage(labelId) {
  return request({
    url: '/xuncha/XunchaLabelManage/' + labelId,
    method: 'get'
  })
}

// 新增标签管理 
export function addXunchaLabelManage(data) {
  return request({
    url: '/xuncha/XunchaLabelManage',
    method: 'post',
    data: data
  })
}

// 修改标签管理 
export function updateXunchaLabelManage(data) {
  return request({
    url: '/xuncha/XunchaLabelManage/edit',
    method: 'post',
    data: data
  })
}

// 删除标签管理 
export function delXunchaLabelManage(labelId) {
  return request({
    url: '/xuncha/XunchaLabelManage/remove?labelId=' + labelId,
    method: 'get'
  })
}

// 导出标签管理 
export function exportXunchaLabelManage(query) {
  return request({
    url: '/xuncha/XunchaLabelManage/export',
    method: 'get',
    params: query
  })
}
// 下载标签管理 导入模板
export function importTemplate() {
  return request({
    url: '/xuncha/XunchaLabelManage/importTemplate',
    method: 'get'
  })
}
// 标签管理 禁用/启用
export function disableEnable(query) {
  return request({
    url: '/xuncha/XunchaLabelManage/updateByIds',
    method: 'get',
    params: query
  })
}
