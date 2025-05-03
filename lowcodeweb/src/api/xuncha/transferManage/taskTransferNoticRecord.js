import request from '@/utils/request'

// 查询批次抽调通知记录列表
export function listTaskTransferNoticRecord(query) {
  return request({
    url: '/transferManage/taskTransferNoticRecord/list',
    method: 'get',
    params: query
  })
}

// 查询批次抽调通知记录详细
export function getTaskTransferNoticRecord(recordId) {
  return request({
    url: '/transferManage/taskTransferNoticRecord/' + recordId,
    method: 'get'
  })
}

// 新增批次抽调通知记录
export function addTaskTransferNoticRecord(data) {
  return request({
    url: '/transferManage/taskTransferNoticRecord',
    method: 'post',
    data: data
  })
}

// 修改批次抽调通知记录
export function updateTaskTransferNoticRecord(data) {
  return request({
    url: '/transferManage/taskTransferNoticRecord/edit',
    method: 'post',
    data: data
  })
}

// 删除批次抽调通知记录
export function delTaskTransferNoticRecord(recordId) {
  return request({
    url: '/transferManage/taskTransferNoticRecord/remove?recordId=' + recordId,
    method: 'get'
  })
}

// 导出批次抽调通知记录
export function exportTaskTransferNoticRecord(query) {
  return request({
    url: '/transferManage/taskTransferNoticRecord/export',
    method: 'get',
    params: query
  })
}
// 下载批次抽调通知记录导入模板
export function importTemplate() {
  return request({
    url: '/transferManage/taskTransferNoticRecord/importTemplate',
    method: 'get'
  })
}
// 批次抽调通知记录禁用/启用
export function disableEnable(query) {
  return request({
    url: '/transferManage/taskTransferNoticRecord/updateByIds',
    method: 'get',
    params: query
  })
}
