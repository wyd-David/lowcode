import request from '@/utils/request'

// 查询人员抽调_抽调领域信息 列表
export function listTransferFieldInfo(query) {
  return request({
    url: '/transferManage/transferFieldInfo/list',
    method: 'get',
    params: query
  })
}

// 查询人员抽调_抽调领域信息 详细
export function getTransferFieldInfo(query) {
  return request({
    url: '/xuncha/XunchaLabelManage/applyList',
    method: 'get',
    params:query
  })
}

// 新增人员抽调_抽调领域信息
export function addTransferFieldInfo(data) {
  return request({
    url: '/transferManage/transferFieldInfo',
    method: 'post',
    data: data
  })
}

// 修改人员抽调_抽调领域信息
export function updateTransferFieldInfo(data) {
  return request({
    url: '/transferManage/transferFieldInfo/edit',
    method: 'post',
    data: data
  })
}

// 删除人员抽调_抽调领域信息
export function delTransferFieldInfo(transferFieldId) {
  return request({
    url: '/transferManage/transferFieldInfo/remove?transferFieldId=' + transferFieldId,
    method: 'get'
  })
}

// 导出人员抽调_抽调领域信息
export function exportTransferFieldInfo(query) {
  return request({
    url: '/transferManage/transferFieldInfo/export',
    method: 'get',
    params: query
  })
}
// 下载人员抽调_抽调领域信息 导入模板
export function importTemplate() {
  return request({
    url: '/transferManage/transferFieldInfo/importTemplate',
    method: 'get'
  })
}
// 人员抽调_抽调领域信息 禁用/启用
export function disableEnable(query) {
  return request({
    url: '/transferManage/transferFieldInfo/updateByIds',
    method: 'get',
    params: query
  })
}


// 根据标签获取人员领域信息
export function getProfessionTabInfo(query) {
  return request({
    url: '/xuncha/xunchaDictData/getProfessionTabInfo',
    method: 'get',
    params: query
  })
}

// 根据标签获取人员领域信息
export function getProfessionUserCount(data) {
  return request({
    url: '/transferManage/transferPersonnelInfo/getProfessionUserCount',
    method: 'post',
    data: data
  })
}
