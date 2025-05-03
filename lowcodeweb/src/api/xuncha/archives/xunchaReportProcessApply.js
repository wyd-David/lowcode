import request from '@/utils/request'

// 查询巡察过程资料库_文件申请 列表
export function listXunchaProcessApply(query) {
  query.applyType = 2;
  return request({
    url: '/archives/xunchaProcessApply/list',
    method: 'get',
    params: query
  })
}

// 查询巡察过程资料库_文件申请 详细
export function getXunchaProcessApply(applyId) {
  return request({
    url: '/archives/xunchaProcessApply/' + applyId,
    method: 'get'
  })
}

// 新增巡察过程资料库_文件申请
export function addXunchaProcessApply(data) {
  return request({
    url: '/archives/xunchaProcessApply',
    method: 'post',
    data: data
  })
}

// 修改巡察过程资料库_文件申请
export function updateXunchaProcessApply(data) {
  return request({
    url: '/archives/xunchaProcessApply/edit',
    method: 'post',
    data: data
  })
}

// 删除巡察过程资料库_文件申请
export function delXunchaProcessApply(applyId) {
  return request({
    url: '/archives/xunchaProcessApply/remove?applyId=' + applyId,
    method: 'get'
  })
}

// 导出巡察过程资料库_文件申请
export function exportXunchaProcessApply(query) {
  return request({
    url: '/archives/xunchaProcessApply/export',
    method: 'get',
    params: query
  })
}
// 下载巡察过程资料库_文件申请 导入模板
export function importTemplate() {
  return request({
    url: '/archives/xunchaProcessApply/importTemplate',
    method: 'get'
  })
}
// 巡察过程资料库_文件申请 禁用/启用
export function disableEnable(query) {
  return request({
    url: '/archives/xunchaProcessApply/updateByIds',
    method: 'get',
    params: query
  })
}

//保存申请信息
export function saveProcessApplyData(data) {
  return request({
    url: '/archives/xunchaProcessApply/saveProcessApplyData',
    method: 'post',
    data: data
  })
}

//获取申请基础信息详情
export function getProcessApplyAndContent(query) {
  return request({
    url: '/archives/xunchaProcessApply/getProcessApplyAndContent',
    method: 'get',
    params: query
  })
}
//获取申请文件信息和共享人信息详情
export function getContentFileAndParticipant(query) {
  return request({
    url: '/archives/xunchaProcessApply/getContentFileAndParticipant',
    method: 'get',
    params: query
  })
}

//保存共享人
export function saveParticipants(data) {
  return request({
    url: '/archives/xunchaProcessParticipant',
    method: 'post',
    data: data
  })
}
//共享人完成
export function endTodo(data) {
  return request({
    url: '/archives/xunchaProcessParticipant/edit',
    method: 'post',
    data: data
  })
}


//保存清单资料
export function saveContentFile(query,data) {
  return request({
    url: '/archives/xunchaProcessApply/saveContentFile',
    method: 'post',
    params: query,
    data: data
  })
}

//共享人提交
export function participantSubmit(query,data) {
  return request({
    url: '/archives/xunchaProcessApply/participantSubmit',
    method: 'post',
    params: query,
    data: data
  })
}


