import request from '@/utils/request'

// 查询巡察报告资料库_文件清单 列表
export function listXunchaReportContent(query) {
  return request({
    url: '/xunchaReportContent/xunchaReportContent/list',
    method: 'get',
    params: query
  })
}

// 查询巡察报告资料库_文件清单 详细
export function getXunchaReportContent(contentId) {
  return request({
    url: '/xunchaReportContent/xunchaReportContent/' + contentId,
    method: 'get'
  })
}

// 新增巡察报告资料库_文件清单
export function addXunchaReportContent(data) {
  return request({
    url: '/xunchaReportContent/xunchaReportContent',
    method: 'post',
    data: data
  })
}

// 修改巡察报告资料库_文件清单
export function updateXunchaReportContent(data) {
  return request({
    url: '/xunchaReportContent/xunchaReportContent/edit',
    method: 'post',
    data: data
  })
}

// 删除巡察报告资料库_文件清单
export function delXunchaReportContent(contentId) {
  return request({
    url: '/xunchaReportContent/xunchaReportContent/remove?contentId=' + contentId,
    method: 'get'
  })
}

// 导出巡察报告资料库_文件清单
export function exportXunchaReportContent(query) {
  return request({
    url: '/xunchaReportContent/xunchaReportContent/export',
    method: 'get',
    params: query
  })
}
// 下载巡察报告资料库_文件清单 导入模板
export function importTemplate() {
  return request({
    url: '/xunchaReportContent/xunchaReportContent/importTemplate',
    method: 'get'
  })
}
// 巡察报告资料库_文件清单 禁用/启用
export function disableEnable(query) {
  return request({
    url: '/xunchaReportContent/xunchaReportContent/updateByIds',
    method: 'get',
    params: query
  })
}

// 查询巡察报告资料库_文件清单 列表
export function treeXunchaReportContent(query) {
  return request({
    url: '/xunchaReportContent/xunchaReportContent/tree',
    method: 'get',
    params: query
  })
}
