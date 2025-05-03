import request from '@/utils/request'

// 查询巡察案例方法库 列表
export function listXunchaCase(query) {
  return request({
    url: '/archives/xunchaCase/list',
    method: 'get',
    params: query
  })
}

// 查询巡察案例方法库 详细
export function getXunchaCase(caseId) {
  return request({
    url: '/archives/xunchaCase/' + caseId,
    method: 'get'
  })
}

// 新增巡察案例方法库
export function addXunchaCase(data) {
  return request({
    url: '/archives/xunchaCase',
    method: 'post',
    data: data
  })
}

// 修改巡察案例方法库
export function updateXunchaCase(data) {
  return request({
    url: '/archives/xunchaCase/edit',
    method: 'post',
    data: data
  })
}

// 删除巡察案例方法库
export function delXunchaCase(caseId) {
  return request({
    url: '/archives/xunchaCase/remove?caseId=' + caseId,
    method: 'get'
  })
}

// 导出巡察案例方法库
export function exportXunchaCase(query) {
  return request({
    url: '/archives/xunchaCase/export',
    method: 'get',
    params: query
  })
}

// 下载巡察案例方法库 导入模板
export function importTemplate() {
  return request({
    url: '/archives/xunchaCase/importTemplate',
    method: 'get'
  })
}

// 巡察案例方法库 禁用/启用
export function disableEnable(query) {
  return request({
    url: '/archives/xunchaCase/updateByIds',
    method: 'get',
    params: query
  })
}

// 下载量、预览量 +1 操作。type：preview 预览量 +1；download 下载量 +1；
export function previewAndDownloadPlusOne(caseId, type) {
  return request({
    url: `/archives/xunchaCase/previewAndDownloadPlusOne?caseId=${caseId}&type=${type}`,
    method: 'get'
  })
}


// 传入文件 ID，解析 Eccel 文件的案例数量
export function downloadPlusMuch(caseIds) {
  return request({
    url: '/archives/xunchaCase/downloadPlusMuch?caseIds=' + caseIds,
    method: 'get'
  })
}
