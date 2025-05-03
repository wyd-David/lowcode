import request from '@/utils/request'

// 查询离库管申请列表
export function listApply(query) {
  return request({
    url: "/leaveApply/list",
    method: "get",
    params: query,
  });
}

// 查询离库管申请详细
export function getApply(id) {
  return request({
    url: "/leaveApply/" + id,
    method: "get",
  });
}

// 新增离库管申请
export function addApply(data) {
  return request({
    url: "/leaveApply",
    method: "post",
    data: data,
  });
}

// 暂存离库管申请
export function temporarySaveApply(data) {
  return request({
    url: "/leaveApply/temporarySave",
    method: "post",
    data: data,
  });
}

// 修改离库管申请
export function updateApply(data) {
  return request({
    url: '/talent/apply/edit',
    method: 'post',
    data: data
  })
}

// 删除离库管申请
export function delApply(id) {
  return request({
    url: "/leaveApply/remove?orderNumber=" + id,
    method: "get",
  });
}

//流程推动
export function fininshFormTask(data) {
  return request({
    url: "/leaveApply/fininshFormTask?taskId="+data.taskId,
    method: "post",
    data: data,
  });
}

//查看已离库人才
export function getLeavePersonList(data) {
  return request({
    url: "/leaveApply/getLeavePersonList",
    method: "get",
    params: data,
  });
}

// 导出离库管申请
export function exportApply(query) {
  return request({
    url: '/talent/apply/export',
    method: 'get',
    params: query
  })
}
// 下载离库管申请导入模板
export function importTemplate() {
  return request({
    url: '/talent/apply/importTemplate',
    method: 'get'
  })
}
// 离库管申请禁用/启用
export function disableEnable(query) {
  return request({
    url: '/talent/apply/updateByIds',
    method: 'get',
    params: query
  })
}

