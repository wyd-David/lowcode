import request from '@/utils/request'

// 查询巡前资料_基础清单列表
export function listInventory(query) {
  return request({
    url: '/ahead/inventory/list',
    method: 'get',
    params: query
  })
}

// 查询巡前资料_基础清单详细
export function getInventory(id) {
  return request({
    url: '/ahead/inventory/' + id,
    method: 'get'
  })
}

// 新增巡前资料_基础清单
export function addInventory(data) {
  return request({
    url: '/ahead/inventory',
    method: 'post',
    data: data
  })
}

// 修改巡前资料_基础清单
export function updateInventory(data) {
  return request({
    url: '/ahead/inventory/edit',
    method: 'post',
    data: data
  })
}

// 删除巡前资料_基础清单
export function delInventory(id) {
  return request({
    url: '/ahead/inventory/remove?id=' + id,
    method: 'get'
  })
}

// 导出巡前资料_基础清单
export function exportInventory(query) {
  return request({
    url: '/ahead/inventory/export',
    method: 'get',
    params: query
  })
}
// 下载巡前资料_基础清单导入模板
export function importTemplate() {
  return request({
    url: '/ahead/inventory/importTemplate',
    method: 'get'
  })
}
// 巡前资料_基础清单禁用/启用
export function disableEnable(query) {
  return request({
    url: '/ahead/inventory/updateByIds',
    method: 'get',
    params: query
  })
}
