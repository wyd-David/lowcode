import request from '@/utils/request'

// 查询人员抽调_预分组批次信息 列表
export function listGroupPiciInfo(query) {
  return request({
    url: '/transferManage/groupPiciInfo/list',
    method: 'get',
    params: query
  })
}

// 查询人员抽调_预分组批次信息 详细
export function getGroupPiciInfo(groupPiciId) {
  return request({
    url: '/transferManage/groupPiciInfo/' + groupPiciId,
    method: 'get'
  })
}

// 新增人员抽调_预分组批次信息 
export function addGroupPiciInfo(data) {
  return request({
    url: '/transferManage/groupPiciInfo',
    method: 'post',
    data: data
  })
}

// 修改人员抽调_预分组批次信息 
export function updateGroupPiciInfo(data) {
  return request({
    url: '/transferManage/groupPiciInfo/edit',
    method: 'post',
    data: data
  })
}

// 删除人员抽调_预分组批次信息 
export function delGroupPiciInfo(groupPiciId) {
  return request({
    url: '/transferManage/groupPiciInfo/remove?groupPiciId=' + groupPiciId,
    method: 'get'
  })
}

// 导出人员抽调_预分组批次信息 
export function exportGroupPiciInfo(query) {
  return request({
    url: '/transferManage/groupPiciInfo/export',
    method: 'get',
    params: query
  })
}
// 下载人员抽调_预分组批次信息 导入模板
export function importTemplate() {
  return request({
    url: '/transferManage/groupPiciInfo/importTemplate',
    method: 'get'
  })
}
// 人员抽调_预分组批次信息 禁用/启用
export function disableEnable(query) {
  return request({
    url: '/transferManage/groupPiciInfo/updateByIds',
    method: 'get',
    params: query
  })
}
