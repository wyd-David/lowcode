import request from '@/utils/request'

// 查询人员更替_零星入库人员信息列表
export function listSporadicTalentUserInfo(query) {
  return request({
    url: '/replaceManage/sporadicTalentUserInfo/list',
    method: 'get',
    params: query
  })
}

// 查询人员更替_零星入库人员信息详细
export function getSporadicTalentUserInfo(sporadicId) {
  return request({
    url: '/replaceManage/sporadicTalentUserInfo/' + sporadicId,
    method: 'get'
  })
}

// 新增人员更替_零星入库人员信息
export function addSporadicTalentUserInfo(data) {
  return request({
    url: '/replaceManage/sporadicTalentUserInfo',
    method: 'post',
    data: data
  })
}

// 修改人员更替_零星入库人员信息
export function updateSporadicTalentUserInfo(data) {
  return request({
    url: '/replaceManage/sporadicTalentUserInfo/edit',
    method: 'post',
    data: data
  })
}

// 零星入库流程审核
export function fininshSporadicToExamine(data) {
  return request({
    url: '/replaceManage/sporadicTalentUserInfo/fininshSporadicToExamine',
    method: 'post',
    data: data
  })
}

// 删除人员更替_零星入库人员信息
export function delSporadicTalentUserInfo(sporadicId) {
  return request({
    url: '/replaceManage/sporadicTalentUserInfo/remove?sporadicId=' + sporadicId,
    method: 'get'
  })
}

// 导出人员更替_零星入库人员信息
export function exportSporadicTalentUserInfo(query) {
  return request({
    url: '/replaceManage/sporadicTalentUserInfo/export',
    method: 'get',
    params: query
  })
}
// 下载人员更替_零星入库人员信息导入模板
export function importTemplate() {
  return request({
    url: '/replaceManage/sporadicTalentUserInfo/importTemplate',
    method: 'get'
  })
}
// 人员更替_零星入库人员信息禁用/启用
export function disableEnable(query) {
  return request({
    url: '/replaceManage/sporadicTalentUserInfo/updateByIds',
    method: 'get',
    params: query
  })
}
// 人员更替_零星入库人员查询
export function selectViewInfoList(data) {
  return request({
    url: '/replaceManage/sporadicTalentUserInfo/selectViewInfoList',
    method: 'post',
    data: data
  })
}
