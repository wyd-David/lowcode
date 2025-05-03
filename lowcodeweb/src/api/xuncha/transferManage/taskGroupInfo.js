import request from '@/utils/request'

// 查询人员抽调_巡察任务预分组信息 列表
export function listTaskGroupInfo(query) {
  return request({
    url: '/transferManage/taskGroupInfo/list',
    method: 'get',
    params: query
  })
}
// 根据条件获取巡察组列表
export function getXunchaTaskGroupInfoData(data) {
  return request({
    url: '/transferManage/taskGroupInfo/getXunchaTaskGroupInfoData',
    method: 'post',
    data: data
  })
}

// 查看分组信息
export function getXunchaGroupInfoList(data) {
  return request({
    url: '/transferManage/taskGroupInfo/getXunchaGroupInfoList',
    method: 'post',
    data: data
  })
}

// 查询人员抽调_巡察任务预分组信息 详细
export function getTaskGroupInfo(groupId) {
  return request({
    url: '/transferManage/taskGroupInfo/' + groupId,
    method: 'get'
  })
}

// 新增人员抽调_巡察任务预分组信息
export function addTaskGroupInfo(data) {
  return request({
    url: '/transferManage/taskGroupInfo',
    method: 'post',
    data: data
  })
}

// 修改人员抽调_巡察任务预分组信息
export function updateTaskGroupInfo(data) {
  return request({
    url: '/transferManage/taskGroupInfo/edit',
    method: 'post',
    data: data
  })
}

// 删除人员抽调_巡察任务预分组信息
export function delTaskGroupInfo(groupId) {
  return request({
    url: '/transferManage/taskGroupInfo/remove?groupId=' + groupId,
    method: 'get'
  })
}

// 导出人员抽调_巡察任务预分组信息
export function exportTaskGroupInfo(query) {
  return request({
    url: '/transferManage/taskGroupInfo/export',
    method: 'get',
    params: query
  })
}
// 下载人员抽调_巡察任务预分组信息 导入模板
export function importTemplate() {
  return request({
    url: '/transferManage/taskGroupInfo/importTemplate',
    method: 'get'
  })
}
// 人员抽调_巡察任务预分组信息 禁用/启用
export function disableEnable(query) {
  return request({
    url: '/transferManage/taskGroupInfo/updateByIds',
    method: 'get',
    params: query
  })
}

// 人员抽调_预分组抽调
export function addGroupingTransfer(data) {
  return request({
    url: '/transferManage/taskGroupInfo/addGroupingTransferPersonnel',
    method: 'post',
    data: data
  })
}
// 分组成员信息_列表
export function getGroupPersonInfoList(groupPiciNo) {
  return request({
    url: '/transferManage/taskGroupInfo/getGroupPersonInfoList?groupPiciNo='+groupPiciNo,
    method: 'get',
  })
}

// 删除预分组
export function removeTaskGroup(query) {
  return request({
    url: '/transferManage/taskGroupInfo/removeTaskGroup',
    method: 'get',
    params:query
  })
}
// 重新预分组
export function reGroupPersionnel(query) {
  return request({
    url: '/transferManage/taskGroupInfo/reGroupPersionnel',
    method: 'get',
    params:query
  })
}

// 根据id查看分组详情信息
export function getGroupInfoDetail(query) {
  return request({
    url: '/transferManage/taskGroupInfo/getGroupInfoDetail',
    method: 'get',
    params:query
  })
}
// 根据批次号获取审核信息
export function getTaskGroupInfoList(query) {
  return request({
    url: '/transferManage/taskGroupInfo/getTaskGroupInfoList',
    method: 'get',
    params:query
  })
}

// 发起审核
export function startGroupToExamine(data) {
  return request({
    url: '/transferManage/taskGroupInfo/startGroupToExamine',
    method: 'post',
    data:data
  })
}
// 巡察预分组_列表
export function grouplist(data) {
  return request({
    url: '/transferManage/taskGroupInfo/grouplist',
    method: 'post',
    data:data
  })
}

// 根据分组Id获取审核信息
export function getXunChaGroupSHInfo(query) {
  return request({
    url: '/transferManage/taskGroupInfo/getXunChaGroupSHInfo',
    method: 'get',
    params:query
  })
}

// 预分组流程审核
export function fininshGroupToExamine(data) {
  return request({
    url: '/transferManage/taskGroupInfo/fininshGroupToExamine',
    method: 'post',
    data:data
  })
}

// 查询未绑定巡察小组信息 列表
export function getUnboundGroupList(query) {
  return request({
    url: '/transferManage/taskGroupInfo/unboundGroupList',
    method: 'get',
    params:query
  })
}

// 根据流程名称及节点名称获取下一步处理人
export function getNextDealUser(query) {
  return request({
    url: '/transferManage/taskGroupInfo/getNextDealUser',
    method: 'get',
    params:query
  })
}


// 预分组审核 催办
export function sendGroupList(query) {
  return request({
    url: '/transferManage/taskGroupInfo/sendXunChaGroupNotic',
    method: 'get',
    params: query
  })
}
//导出巡查分组信息
export function exportGroupInfo(query) {
  return request({
    url: "/transferManage/taskGroupInfo/exportGroupInfo",
    method: "get",
    params: query,
  });
}