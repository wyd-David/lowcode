import request from '@/utils/request'

// 查询业务字典管理列表
export function listXunchaDictType(query) {
  return request({
    url: '/xuncha/xunchaDictType/list',
    method: 'get',
    params: query
  })
}

// 查询业务字典管理详细
export function getXunchaDictType(dictId) {
  return request({
    url: '/xuncha/xunchaDictType/' + dictId,
    method: 'get'
  })
}

// 新增业务字典管理
export function addXunchaDictType(data) {
  return request({
    url: '/xuncha/xunchaDictType',
    method: 'post',
    data: data
  })
}

// 修改业务字典管理
export function updateXunchaDictType(data) {
  return request({
    url: '/xuncha/xunchaDictType/edit',
    method: 'post',
    data: data
  })
}

// 删除业务字典管理
export function delXunchaDictType(dictId) {
  return request({
    url: '/xuncha/xunchaDictType/remove?dictId=' + dictId,
    method: 'get'
  })
}

// 导出业务字典管理
export function exportXunchaDictType(query) {
  return request({
    url: '/xuncha/xunchaDictType/export',
    method: 'get',
    params: query
  })
}
// 下载业务字典管理导入模板
export function importTemplate() {
  return request({
    url: '/xuncha/xunchaDictType/importTemplate',
    method: 'get'
  })
}
// 业务字典管理禁用/启用
export function disableEnable(query) {
  return request({
    url: '/xuncha/xunchaDictType/updateByIds',
    method: 'get',
    params: query
  })
}
