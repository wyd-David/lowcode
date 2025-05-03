import request from '@/utils/request'

// 查询人员抽调_巡察任务抽调信息 列表
export function listTaskTransferInfo(query) {
  return request({
    url: '/transferManage/taskTransferInfo/list',
    method: 'get',
    params: query
  })
}

// 查询人员抽调_巡察任务抽调信息 详细
export function getTaskTransferInfoDetail(query) {
  return request({
    url: '/transferManage/taskTransferInfo/getTaskTransferInfoDetail',
    method: 'get',
    params: query
  })
}

// 新增人员抽调_巡察任务抽调信息 
export function addTaskTransferInfo(data) {
  return request({
    url: '/transferManage/taskTransferInfo',
    method: 'post',
    data: data
  })
}

// 修改人员抽调_巡察任务抽调信息 
export function updateTaskTransferInfo(data) {
  return request({
    url: '/transferManage/taskTransferInfo/edit',
    method: 'post',
    data: data
  })
}

// 删除人员抽调_巡察任务抽调信息 
export function delTaskTransferInfo(transferId) {
  return request({
    url: '/transferManage/taskTransferInfo/remove?transferId=' + transferId,
    method: 'get'
  })
}

// 导出人员抽调_巡察任务抽调信息 
export function exportTaskTransferInfo(query) {
  return request({
    url: '/transferManage/taskTransferInfo/export',
    method: 'get',
    params: query
  })
}
// 下载人员抽调_巡察任务抽调信息 导入模板
export function importTemplate() {
  return request({
    url: '/transferManage/taskTransferInfo/importTemplate',
    method: 'get'
  })
}
// 人员抽调_巡察任务抽调信息 禁用/启用
export function disableEnable(query) {
  return request({
    url: '/transferManage/taskTransferInfo/updateByIds',
    method: 'get',
    params: query
  })
}

// 人员抽调_获取通知内容
export function getNoticContent(query) {
  return request({
    url: '/transferManage/taskTransferInfo/getNoticContent',
    method: 'get',
    params: query
  })
}
// 人员抽调_发送通知
export function sendTaskTransferInfoNotic(query) {
  return request({
    url: '/transferManage/taskTransferInfo/sendTaskTransferInfoNotic',
    method: 'get',
    params: query
  })
}

