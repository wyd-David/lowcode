import request from '@/utils/request'

// 查询巡察报告资料库_文件申请清单 列表
export function listContent(query) {
  return request({
    url: '/archives/xunchaReportApplyContent/list',
    method: 'get',
    params: query
  })
}

// 查询巡察报告资料库_文件申请清单 详细
export function getContent(id) {
  return request({
    url: '/archives/xunchaReportApplyContent/' + id,
    method: 'get'
  })
}

// 新增巡察报告资料库_文件申请清单
export function addContent(data) {
  return request({
    url: '/archives/xunchaReportApplyContent',
    method: 'post',
    data: data
  })
}

// 修改巡察报告资料库_文件申请清单
export function updateContent(data) {
  return request({
    url: '/archives/xunchaReportApplyContent/edit',
    method: 'post',
    data: data
  })
}
// 保存报告库申请
export function saveReportApplyData(data) {
  return request({
    url: '/archives/xunchaReportApply/saveReportApplyData',
    method: 'post',
    data: data
  })
}
// 报告库申请任务共享
export function addShare(data) {
  return request({
    url: '/archives/xunchaReportApply/addShare',
    method: 'post',
    data: data
  })
}

// 删除巡察报告资料库_文件申请清单
export function delContent(id) {
  return request({
    url: '/archives/xunchaReportApplyContent/remove?id=' + id,
    method: 'get'
  })
}

// 导出巡察报告资料库_文件申请清单
export function exportContent(query) {
  return request({
    url: '/archives/xunchaReportApplyContent/export',
    method: 'get',
    params: query
  })
}
// 下载巡察报告资料库_文件申请清单 导入模板
export function importTemplate() {
  return request({
    url: '/archives/xunchaReportApplyContent/importTemplate',
    method: 'get'
  })
}
// 巡察报告资料库_文件申请清单 禁用/启用
export function disableEnable(query) {
  return request({
    url: '/archives/xunchaReportApplyContent/updateByIds',
    method: 'get',
    params: query
  })
}
