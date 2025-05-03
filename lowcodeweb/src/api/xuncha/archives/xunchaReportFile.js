import request from '@/utils/request'

// 查询巡察报告资料库_文件信息 列表
export function listFile(query) {
  return request({
    url: '/archives/xunchaReportFile/list',
    method: 'get',
    params: query
  })
}

// 查询巡察报告资料库_文件信息 详细
export function getFile(id) {
  return request({
    url: '/archives/xunchaReportFile/' + id,
    method: 'get'
  })
}

// 新增巡察报告资料库_文件信息
export function addFile(data) {
  return request({
    url: '/archives/xunchaReportFile',
    method: 'post',
    data: data
  })
}

// 修改巡察报告资料库_文件信息
export function updateFile(data) {
  return request({
    url: '/archives/xunchaReportFile/edit',
    method: 'post',
    data: data
  })
}
// 修改巡察报告资料库_文件信息
export function addReportFiles(data, applyId) {
  return request({
    url: '/archives/xunchaReportFile/addFiles?applyId=' + applyId,
    method: 'post',
    data: data
  })
}

// 删除巡察报告资料库_文件信息
export function delFile(id) {
  return request({
    url: '/archives/xunchaReportFile/remove?id=' + id,
    method: 'get'
  })
}

// 导出巡察报告资料库_文件信息
export function exportFile(query) {
  return request({
    url: '/archives/xunchaReportFile/export',
    method: 'get',
    params: query
  })
}
// 下载巡察报告资料库_文件信息 导入模板
export function importTemplate() {
  return request({
    url: '/archives/xunchaReportFile/importTemplate',
    method: 'get'
  })
}
// 巡察报告资料库_文件信息 禁用/启用
export function disableEnable(query) {
  return request({
    url: '/archives/xunchaReportFile/updateByIds',
    method: 'get',
    params: query
  })
}

// 文件预览量和下载量 +1 操作，type 区分是预览量（preview）还是下载量（download）,默认为下载量
export function downloadAndPreviewPlusMuch(ids, type) {
  return request({
    url: `/archives/xunchaReportFile/downloadAndPreviewPlusMuch?ids=${ids}&type=${type}`,
    method: 'get'
  })
}

//巡察办主管不走审批上传资料的新增接口
export function addReportFile(data) {
  return request({
    url: "/archives/xunchaReportFile/addReportFile",
    method: "post",
    data
  });
}

//巡察办主管不走审批上传资料的修改接口
export function editFileAndContent(data) {
  return request({
    url: "/archives/xunchaReportFile/edit",
    method: "post",
    data,
  });
}

//巡察办主管不走审批上传资料的删除接口
export function remove(data) {
  return request({
    url: "/archives/xunchaReportFile/remove",
    method: "get",
    params:data
  });
}
