import request from '@/utils/request'

// 查询人员抽调_预分组抽调规则信息 列表
export function listGroupRuleInfo(query) {
  return request({
    url: '/transferManage/groupRuleInfo/list',
    method: 'get',
    params: query
  })
}

// 查询人员抽调_预分组抽调规则信息 详细
export function getGroupRuleInfo(groupRuleId) {
  return request({
    url: '/transferManage/groupRuleInfo/' + groupRuleId,
    method: 'get'
  })
}

// 新增人员抽调_预分组抽调规则信息 
export function addGroupRuleInfo(data) {
  return request({
    url: '/transferManage/groupRuleInfo',
    method: 'post',
    data: data
  })
}

// 修改人员抽调_预分组抽调规则信息 
export function updateGroupRuleInfo(data) {
  return request({
    url: '/transferManage/groupRuleInfo/edit',
    method: 'post',
    data: data
  })
}

// 删除人员抽调_预分组抽调规则信息 
export function delGroupRuleInfo(groupRuleId) {
  return request({
    url: '/transferManage/groupRuleInfo/remove?groupRuleId=' + groupRuleId,
    method: 'get'
  })
}

// 导出人员抽调_预分组抽调规则信息 
export function exportGroupRuleInfo(query) {
  return request({
    url: '/transferManage/groupRuleInfo/export',
    method: 'get',
    params: query
  })
}
// 下载人员抽调_预分组抽调规则信息 导入模板
export function importTemplate() {
  return request({
    url: '/transferManage/groupRuleInfo/importTemplate',
    method: 'get'
  })
}
// 人员抽调_预分组抽调规则信息 禁用/启用
export function disableEnable(query) {
  return request({
    url: '/transferManage/groupRuleInfo/updateByIds',
    method: 'get',
    params: query
  })
}
// 删除人员抽调_预分组抽调规则信息
export function remove(query) {
  return request({
    url: '/transferManage/groupRuleInfo/remove',
    method: 'get',
    params: query
  })
}
