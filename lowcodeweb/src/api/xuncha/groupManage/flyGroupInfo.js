import request from '@/utils/request'

// 查询分组管理_飞行小组信息 列表
export function listFlyGroupInfo(query) {
  return request({
    url: '/groupManage/flyGroupInfo/list',
    method: 'get',
    params: query
  })
}

// 查询分组管理_飞行小组信息 详细
export function getFlyGroupInfo(flyGroupId) {
  return request({
    url: '/groupManage/flyGroupInfo/' + flyGroupId,
    method: 'get'
  })
}
// 查询分组管理_未绑定飞行小组信息 列表
export function getUnboundList(query) {
  return request({
    url: '/groupManage/flyGroupInfo/unboundList',
    method: 'get',
    params:query
  })
}

// 新增分组管理_飞行小组信息
export function addFlyGroupInfo(data) {
  return request({
    url: '/groupManage/flyGroupInfo',
    method: 'post',
    data: data
  })
}
// 修改分组管理_飞行小组信息
export function editFlyGroupInfo(data) {
  return request({
    url: '/groupManage/flyGroupInfo/edit',
    method: 'post',
    data: data
  })
}

// 修改分组管理_飞行小组信息
export function updateFlyGroupInfo(data) {
  return request({
    url: '/groupManage/flyGroupInfo/edit',
    method: 'post',
    data: data
  })
}

// 查看飞行分组信息
export function getXunchaFlyGroupInfoList(data) {
  return request({
    url: '/groupManage/flyGroupInfo/getXunchaFlyGroupInfoList?flyGroupId='+data.flyGroupId,
    method: 'post',
    data: data
  })
}
// 删除分组管理_飞行小组信息
export function delFlyGroupInfo(flyGroupId) {
  return request({
    url: '/groupManage/flyGroupInfo/remove?flyGroupId=' + flyGroupId,
    method: 'get'
  })
}

// 导出分组管理_飞行小组信息
export function exportFlyGroupInfo(query) {
  return request({
    url: '/groupManage/flyGroupInfo/export',
    method: 'get',
    params: query
  })
}

// 导出分组管理_巡察小组信息
export function exportXunCGroupInfo(query) {
  return request({
    url: '/transferManage/taskGroupInfo/export',
    method: 'get',
    params: query
  })
}

// 下载分组管理_飞行小组信息 导入模板
export function importTemplate() {
  return request({
    url: '/groupManage/flyGroupInfo/importTemplate',
    method: 'get'
  })
}
// 分组管理_飞行小组信息 禁用/启用
export function disableEnable(query) {
  return request({
    url: '/groupManage/flyGroupInfo/updateByIds',
    method: 'get',
    params: query
  })
}
