import request from '@/utils/request'

// 查询分组管理_飞行小组信息 列表
export function listFlyGroupRelation(query) {
  return request({
    url: '/groupManage/flyGroupRelation/list',
    method: 'get',
    params: query
  })
}

// 查询分组管理_飞行小组信息 详细
export function getFlyGroupRelation(relationId) {
  return request({
    url: '/groupManage/flyGroupRelation/' + relationId,
    method: 'get'
  })
}

// 绑定飞行分组
export function addFlyGroupRelation(data) {
  return request({
    url: '/groupManage/flyGroupRelation',
    method: 'post',
    data: data
  })
}
// 解绑飞行分组
export function deleteXunchaCheckFlyGroupRelation(data) {
  return request({
    url: '/groupManage/flyGroupRelation/deleteXunchaCheckFlyGroupRelation',
    method: 'post',
    data: data
  })
}

// 修改分组管理_飞行小组信息 
export function updateFlyGroupRelation(data) {
  return request({
    url: '/groupManage/flyGroupRelation/edit',
    method: 'post',
    data: data
  })
}

// 删除分组管理_飞行小组信息 
export function delFlyGroupRelation(relationId) {
  return request({
    url: '/groupManage/flyGroupRelation/remove?relationId=' + relationId,
    method: 'get'
  })
}

// 导出分组管理_飞行小组信息 
export function exportFlyGroupRelation(query) {
  return request({
    url: '/groupManage/flyGroupRelation/export',
    method: 'get',
    params: query
  })
}
// 下载分组管理_飞行小组信息 导入模板
export function importTemplate() {
  return request({
    url: '/groupManage/flyGroupRelation/importTemplate',
    method: 'get'
  })
}
// 分组管理_飞行小组信息 禁用/启用
export function disableEnable(query) {
  return request({
    url: '/groupManage/flyGroupRelation/updateByIds',
    method: 'get',
    params: query
  })
}
