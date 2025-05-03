import request from '@/utils/request'

// 查询巡察组管理 列表
export function listXunchaTaskGroupManage(query) {
  return request({
    url: '/xuncha/XunchaTaskGroupManage/list',
    method: 'get',
    params: query
  })
}

// 查询巡察组管理 详细
export function getXunchaTaskGroupManage(groupId) {
  return request({
    url: '/xuncha/XunchaTaskGroupManage/' + groupId,
    method: 'get'
  })
}

// 新增巡察组管理 
export function addXunchaTaskGroupManage(data) {
  return request({
    url: '/xuncha/XunchaTaskGroupManage',
    method: 'post',
    data: data
  })
}

// 修改巡察组管理 
export function updateXunchaTaskGroupManage(data) {
  return request({
    url: '/xuncha/XunchaTaskGroupManage/edit',
    method: 'post',
    data: data
  })
}

// 删除巡察组管理 
export function delXunchaTaskGroupManage(groupId) {
  return request({
    url: '/xuncha/XunchaTaskGroupManage/remove?groupId=' + groupId,
    method: 'get'
  })
}

// 导出巡察组管理 
export function exportXunchaTaskGroupManage(query) {
  return request({
    url: '/xuncha/XunchaTaskGroupManage/export',
    method: 'get',
    params: query
  })
}
// 下载巡察组管理 导入模板
export function importTemplate() {
  return request({
    url: '/xuncha/XunchaTaskGroupManage/importTemplate',
    method: 'get'
  })
}
// 巡察组管理 禁用/启用
export function disableEnable(query) {
  return request({
    url: '/xuncha/XunchaTaskGroupManage/updateByIds',
    method: 'get',
    params: query
  })
}
