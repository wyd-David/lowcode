import request from '@/utils/request'

// 查询人员更替_零星入库申请 列表
export function listSporadicTalentApplyInfo(query) {
  return request({
    url: '/replaceManage/sporadicTalentApplyInfo/list',
    method: 'get',
    params: query
  })
}

// 查询人员更替_零星入库申请 详细
export function getSporadicTalentApplyInfo(applyId) {
  return request({
    url: '/replaceManage/sporadicTalentApplyInfo/' + applyId,
    method: 'get'
  })
}

// 新增人员更替_零星入库申请 
export function addSporadicTalentApplyInfo(data) {
  return request({
    url: '/replaceManage/sporadicTalentApplyInfo',
    method: 'post',
    data: data
  })
}

// 修改人员更替_零星入库申请 
export function updateSporadicTalentApplyInfo(data) {
  return request({
    url: '/replaceManage/sporadicTalentApplyInfo/edit',
    method: 'post',
    data: data
  })
}

// 删除人员更替_零星入库申请 
export function delSporadicTalentApplyInfo(applyId) {
  return request({
    url: '/replaceManage/sporadicTalentApplyInfo/remove?applyId=' + applyId,
    method: 'get'
  })
}

// 导出人员更替_零星入库申请 
export function exportSporadicTalentApplyInfo(query) {
  return request({
    url: '/replaceManage/sporadicTalentApplyInfo/export',
    method: 'get',
    params: query
  })
}
// 下载人员更替_零星入库申请 导入模板
export function importTemplate() {
  return request({
    url: '/replaceManage/sporadicTalentApplyInfo/importTemplate',
    method: 'get'
  })
}
// 人员更替_零星入库申请 禁用/启用
export function disableEnable(query) {
  return request({
    url: '/replaceManage/sporadicTalentApplyInfo/updateByIds',
    method: 'get',
    params: query
  })
}
