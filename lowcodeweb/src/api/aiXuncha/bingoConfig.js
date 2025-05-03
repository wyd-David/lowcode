import request from '@/utils/request'

// 查询Ai 配置管理列表
export function listBingoConfig(query) {
  return request({
    url: '/ai/bingoConfig/list',
    method: 'get',
    params: query
  })
}

// 查询Ai 配置管理详细
export function getBingoConfig(id) {
  return request({
    url: '/ai/bingoConfig/' + id,
    method: 'get'
  })
}

// 新增Ai 配置管理
export function addBingoConfig(data) {
  return request({
    url: '/ai/bingoConfig',
    method: 'post',
    data: data
  })
}

// 修改Ai 配置管理
export function updateBingoConfig(data) {
  return request({
    url: '/ai/bingoConfig/edit',
    method: 'post',
    data: data
  })
}

// 删除Ai 配置管理
export function delBingoConfig(id) {
  return request({
    url: '/ai/bingoConfig/remove?id=' + id,
    method: 'get'
  })
}

// 导出Ai 配置管理
export function exportBingoConfig(query) {
  return request({
    url: '/ai/bingoConfig/export',
    method: 'get',
    params: query
  })
}
// 下载Ai 配置管理导入模板
export function importTemplate() {
  return request({
    url: '/ai/bingoConfig/importTemplate',
    method: 'get'
  })
}

// 下载Ai 配置管理导入模板
export function getValueByName(name) {
  return request({
    url: `/ai/bingoConfig/getValueByName?name=${name}`,
    method: 'get'
  })
}

