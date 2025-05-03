import request from '@/utils/request'

// 查询巡察案例方法库流程业务 列表
export function listXunchaCaseProcess(query) {
  return request({
    url: '/archives/xunchaCaseProcess/list',
    method: 'get',
    params: query
  })
}

// 查询巡察案例方法库流程业务 详细
export function getXunchaCaseProcess(processId) {
  return request({
    url: '/archives/xunchaCaseProcess/' + processId,
    method: 'get'
  })
}

// 新增巡察案例方法库流程业务
export function addXunchaCaseProcess(data) {
  return request({
    url: '/archives/xunchaCaseProcess',
    method: 'post',
    data: data
  })
}

// 修改巡察案例方法库流程业务
export function updateXunchaCaseProcess(data) {
  return request({
    url: '/archives/xunchaCaseProcess/edit',
    method: 'post',
    data: data
  })
}

// 删除巡察案例方法库流程业务
export function delXunchaCaseProcess(processId) {
  return request({
    url: '/archives/xunchaCaseProcess/remove?processId=' + processId,
    method: 'get'
  })
}

// 导出巡察案例方法库流程业务
export function exportXunchaCaseProcess(query) {
  return request({
    url: '/archives/xunchaCaseProcess/export',
    method: 'get',
    params: query
  })
}
// 下载巡察案例方法库流程业务 导入模板
export function importTemplate() {
  return request({
    url: '/archives/xunchaCaseProcess/importTemplate',
    method: 'get'
  })
}
// 巡察案例方法库流程业务 禁用/启用
export function disableEnable(query) {
  return request({
    url: '/archives/xunchaCaseProcess/updateByIds',
    method: 'get',
    params: query
  })
}

// 传入文件 ID，解析 Eccel 文件的案例数量
export function analysisExcelRow(fileId) {
  return request({
    url: '/archives/xunchaCaseProcess/analysisExcelRow?fileId=' + fileId,
    method: 'get'
  })
}

// 传入文件 ID，解析 Eccel 文件的案例数量
export function getCaseHistoryInfo(caseId) {
  return request({
    url: '/archives/xunchaCaseProcess/getCaseHistoryInfo?caseId=' + caseId,
    method: 'get'
  })
}

