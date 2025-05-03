import request from '@/utils/request'

// 查询巡察批次管理 列表
export function listXunchaPiciManage(query) {
  return request({
    url: '/xuncha/XunchaPiciManage/list',
    method: 'get',
    params: query
  })
}

// 查询巡察批次管理 详细
export function getXunchaPiciManage(piciId) {
  return request({
    url: '/xuncha/XunchaPiciManage/' + piciId,
    method: 'get'
  })
}

// 新增巡察批次管理
export function addXunchaPiciManage(data) {
  return request({
    url: '/xuncha/XunchaPiciManage',
    method: 'post',
    data: data
  })
}

// 修改巡察批次管理
export function updateXunchaPiciManage(data) {
  return request({
    url: '/xuncha/XunchaPiciManage/edit',
    method: 'post',
    data: data
  })
}

// 删除巡察批次管理
export function delXunchaPiciManage(piciId) {
  return request({
    url: '/xuncha/XunchaPiciManage/remove?piciId=' + piciId,
    method: 'get'
  })
}

// 导出巡察批次管理
export function exportXunchaPiciManage(query) {
  return request({
    url: '/xuncha/XunchaPiciManage/export',
    method: 'get',
    params: query
  })
}
// 下载巡察批次管理 导入模板
export function importTemplate() {
  return request({
    url: '/xuncha/XunchaPiciManage/importTemplate',
    method: 'get'
  })
}
// 巡察批次管理 禁用/启用
export function disableEnable(query) {
  return request({
    url: '/xuncha/XunchaPiciManage/updateByIds',
    method: 'get',
    params: query
  })
}

// 在新增和修改批次时，获得批次名称下拉框数。如：['第一批', '第二批' ...]
export function getPiciNameList(piciId, year) {
  return request({
    url: `/xuncha/XunchaPiciManage/getPiciNameList?piciId=${piciId}&year=${year}`,
    method: 'get'
  })
}
