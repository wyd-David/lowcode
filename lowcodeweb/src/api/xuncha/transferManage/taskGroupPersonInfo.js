import request from '@/utils/request'

// 查询人员抽调_巡察任务组成员信息 列表
export function listTaskGroupPersonInfo(query) {
  return request({
    url: '/transferManage/taskGroupPersonInfo/list',
    method: 'get',
    params: query
  })
}

// 查询人员抽调_巡察任务组成员信息 详细
export function getTaskGroupPersonInfo(personId) {
  return request({
    url: '/transferManage/taskGroupPersonInfo/' + personId,
    method: 'get'
  })
}

// 新增人员抽调_巡察任务组成员信息 
export function addTaskGroupPersonInfo(data) {
  return request({
    url: '/transferManage/taskGroupPersonInfo',
    method: 'post',
    data: data
  })
}

// 修改人员抽调_巡察任务组成员信息 
export function updateTaskGroupPersonInfo(data) {
  return request({
    url: '/transferManage/taskGroupPersonInfo/edit',
    method: 'post',
    data: data
  })
}

// 分组人员重抽
export function groupOnAgain(data) {
  return request({
    url: '/transferManage/taskGroupPersonInfo/groupOnAgain',
    method: 'post',
    data: data
  })
}

// 删除人员抽调_巡察任务组成员信息 
export function delTaskGroupPersonInfo(personId) {
  return request({
    url: '/transferManage/taskGroupPersonInfo/remove?personId=' + personId,
    method: 'get'
  })
}

// 导出人员抽调_巡察任务组成员信息 
export function exportTaskGroupPersonInfo(query) {
  return request({
    url: '/transferManage/taskGroupPersonInfo/export',
    method: 'get',
    params: query
  })
}
// 下载人员抽调_巡察任务组成员信息 导入模板
export function importTemplate() {
  return request({
    url: '/transferManage/taskGroupPersonInfo/importTemplate',
    method: 'get'
  })
}
// 人员抽调_巡察任务组成员信息 禁用/启用
export function disableEnable(query) {
  return request({
    url: '/transferManage/taskGroupPersonInfo/updateByIds',
    method: 'get',
    params: query
  })
}
// 人员抽调_根据分组获取人员
export function getGroupUserList(query) {
  return request({
    url: '/transferManage/taskGroupPersonInfo/getGroupUserList',
    method: 'get',
    params: query
  })
}

// 查询最新巡察批次人员信息
export function getGroupPersonInfoReplaceList(query) {
  return request({
    url: '/transferManage/taskGroupPersonInfo/getGroupPersonInfoReplaceList',
    method: 'get',
    params: query
  })
}


