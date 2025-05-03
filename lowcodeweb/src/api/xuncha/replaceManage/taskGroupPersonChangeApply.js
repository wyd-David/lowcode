import request from '@/utils/request'

// 查询人员更替_巡察任务组成员人员更替申请 列表
export function listTaskGroupPersonChangeApply(query) {
  return request({
    url: '/replaceManage/taskGroupPersonChangeApply/list',
    method: 'get',
    params: query
  })
}

// 获取人员更替_巡察任务组成员人员更替申请 详细信息
export function getTaskGroupPersonChangeApply(replaceNo) {
  return request({
    url: '/replaceManage/taskGroupPersonChangeApply/'+replaceNo,
    method: 'get',
  })
}
// 新增人员更替_巡察任务组成员人员更替申请 
export function addTaskGroupPersonChangeApply(data) {
  return request({
    url: '/replaceManage/taskGroupPersonChangeApply',
    method: 'post',
    data: data
  })
}

// 修改人员更替_巡察任务组成员人员更替申请 
export function updateTaskGroupPersonChangeApply(data) {
  return request({
    url: '/replaceManage/taskGroupPersonChangeApply/edit',
    method: 'post',
    data: data
  })
}

// 删除人员更替_巡察任务组成员人员更替申请 
export function delTaskGroupPersonChangeApply(applyId) {
  return request({
    url: '/replaceManage/taskGroupPersonChangeApply/remove?applyId=' + applyId,
    method: 'get'
  })
}

// 导出人员更替_巡察任务组成员人员更替申请 
export function exportTaskGroupPersonChangeApply(query) {
  return request({
    url: '/replaceManage/taskGroupPersonChangeApply/export',
    method: 'get',
    params: query
  })
}
// 下载人员更替_巡察任务组成员人员更替申请 导入模板
export function importTemplate() {
  return request({
    url: '/replaceManage/taskGroupPersonChangeApply/importTemplate',
    method: 'get'
  })
}
// 人员更替_巡察任务组成员人员更替申请 禁用/启用
export function disableEnable(query) {
  return request({
    url: '/replaceManage/taskGroupPersonChangeApply/updateByIds',
    method: 'get',
    params: query
  })
}

// 人员更替流程审核
export function fininshGroupToExamine(data) {
  return request({
    url: '/replaceManage/taskGroupPersonChangeApply/fininshGroupToExamine',
    method: 'post',
    data: data
  })
}