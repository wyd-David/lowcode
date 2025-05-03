import request from '@/utils/request'

// 查询商品功能（测试）列表
export function listTestGoods(query) {
  return request({
    url: '/testGoods/list',
    method: 'get',
    params: query
  })
}

// 查询商品功能（测试）详细
export function getTestGoods(id) {
  return request({
    url: '/testGoods/' + id,
    method: 'get'
  })
}

// 新增商品功能（测试）
export function addTestGoods(data) {
  return request({
    url: '/testGoods',
    method: 'post',
    data: data
  })
}

// 修改商品功能（测试）
export function updateTestGoods(data) {
  return request({
    url: '/testGoods/edit',
    method: 'post',
    data: data
  })
}

// 删除商品功能（测试）
export function delTestGoods(id) {
  return request({
    url: '/testGoods/remove?id=' + id,
    method: 'get'
  })
}

// 导出商品功能（测试）
export function exportTestGoods(query) {
  return request({
    url: '/testGoods/export',
    method: 'get',
    params: query
  })
}
// 下载商品功能（测试）导入模板
export function importTemplate() {
  return request({
    url: '/testGoods/importTemplate',
    method: 'get'
  })
}
// 商品功能（测试）禁用/启用
export function disableEnable(query) {
  return request({
    url: '/testGoods/updateByIds',
    method: 'get',
    params: query
  })
}
