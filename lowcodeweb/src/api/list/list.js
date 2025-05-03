import request from '@/utils/request'

// 查询业务列列表
export function listList(query) {
  return request({
    url: '/list/list/list',
    method: 'get',
    params: query
  })
}

// 查询业务列详细
export function getList(listId) {
  return request({
    url: '/list/list/' + listId,
    method: 'get'
  })
}

// 新增业务列
export function addList(data) {
  return request({
    url: '/list/list',
    method: 'post',
    data: data
  })
}

// 修改业务列
export function updateList(data) {
  return request({
    url: '/list/list/edit',
    method: 'post',
    data: data
  })
}

// 删除业务列
export function delList(listId) {
  return request({
    url: '/list/list/remove?listId=' + listId,
    method: 'get'
  })
}

// 导出业务列
export function exportList(data) {
  return request({
    url: '/list/list/ExportList',
    method: 'post',
    params: data
  })
}
// 下载业务列导入模板
export function importTemplate(param) {
  return request({
    url: '/list/list/importTemplate',
    method: 'get',
    params: param
  })
}
// 业务列禁用/启用
export function disableEnable(query) {
  return request({
    url: '/list/list/updateByIds',
    method: 'get',
    params: query
  })
}

//查询动态列表数据
export function selectAutoTableData(data) {
  return request({
    url: '/list/list/selectAutoTableData',
    method: 'post',
    params: data
  })
}
export function selectChildAutoTableData(data) {
  return request({
    url: '/list/list/selectChildAutoTableData',
    method: 'post',
    params: data
  })
}



// 删除动态列表的数据
export function autoRemove(data) {
  return request({
    url: '/list/list/autoRemove',
    method: 'get',
    params: data
  })
}
